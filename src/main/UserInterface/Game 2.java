package UserInterface;

import Model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Represents a chess game to be played by the user on a graphical interface
public class Game extends JFrame {
    private boolean whitesTurn;
    private Board board;
    private Player playerOne;
    private Player playerTwo;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Piece selectedPiece;
    private Timer computerMoveTimer;

    // EFFECTS: creates a new game with an interface and an initialized board
    public Game() {
        this.whitesTurn = true;
        this.board = new Board();
        this.board.initializeBoard();
        this.board.initializePieces();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        initializeUserInterface();

        this.playerOne = new PersonPlayer(true, false, board);
        this.playerOne.setPieces();
        this.playerTwo = new ComputerPlayer(false, true, board);
        this.playerTwo.setPieces();

        computerMoveTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!whitesTurn) {
                    makeMove();
                }
            }
        });
        computerMoveTimer.start();
    }

    // EFFECTS: computer strategically selects a move to advance with, then updates the board
    private void makeMove() {
        Move move = generateMove();
        advanceMove(move, playerTwo, playerOne);
        updateGamePanel();
        checkBlackWin();
    }

    // EFFECTS: Returns most favorable move for computer to make via a point evaluation system of different moves
    private Move generateMove() {
        List<Move> bestMoves = new ArrayList<>();
        double maxPointChange = -401;

        for (Piece p : playerTwo.getPieces()) {
            for (Spot s : board.getBoxesAsList()) {
                Move currentMove = new Move(p, s, false);
                if (currentMove.isValid() && !movingIntoCheck(currentMove, playerTwo, playerOne, board)) {

                    int movePoints = currentMove.getKillValue();
                    double tradeDifference = getTradeDifference(currentMove);
                    double pointChange = movePoints - tradeDifference;

                    if (currentMove.isPawnPromotion()) {
                        pointChange += 8;
                    }
                    if (currentMove.getStartSpot().getY() == 7 || currentMove.getStartSpot().getPiece() instanceof Pawn) {
                        pointChange += 0.1;
                    }
                    if (currentMove.getEndSpot().getY() == 7) {
                        pointChange -= 0.1;
                    }

                    if (pointChange > maxPointChange) {
                        maxPointChange = pointChange;
                        bestMoves.clear();
                        bestMoves.add(currentMove);
                    } else if (pointChange == maxPointChange) {
                        bestMoves.add(currentMove);
                    }
                }
            }
        }

        if (bestMoves.size() == 0) {
            if (playerTwo.isInCheck(playerOne)) {
                JOptionPane.showMessageDialog(this, "White wins!",
                        "Game Over", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Draw by stalemate!",
                        "Game Over", JOptionPane.INFORMATION_MESSAGE);
            }
            System.exit(0);
        }

        Random random = new Random();
        int randomInt = random.nextInt(bestMoves.size());

        return bestMoves.get(randomInt);
    }

    // EFFECTS: Copies board then determines response moves by the opposition to improve foresight of generateMove()
    private double getTradeDifference(Move currentMove) {
        Board copyBoard = board.copyBoard();
        Player copyPlayer = playerOne.copyPlayer();
        copyPlayer.setBoard(copyBoard);
        copyPlayer.setPieces();

        Player secondCopyPlayer = playerOne.copyPlayer();
        secondCopyPlayer.setBoard(copyBoard);
        secondCopyPlayer.setPieces();

        Player copyOtherPlayer = playerTwo.copyPlayer();
        copyOtherPlayer.setBoard(copyBoard);
        copyOtherPlayer.setPieces();

        Spot origStartSpot = currentMove.getStartSpot();
        Spot origEndSpot = currentMove.getEndSpot();

        Spot copyStartSpot = copyBoard.getSquare(origStartSpot.getY(), origStartSpot.getX());
        Spot copyEndSpot = copyBoard.getSquare(origEndSpot.getY(), origEndSpot.getX());
        Piece copyPieceMoved = copyStartSpot.getPiece();

        Move copyMove = currentMove.copyMove(copyPieceMoved, copyEndSpot);

        advanceMove(copyMove, copyOtherPlayer, copyPlayer);

        double maxPointChange = -301;

        for (Piece p : copyPlayer.getPieces()) {
            for (Spot s : copyBoard.getBoxesAsList()) {
                Move responseMove = new Move(p, s, true);
                if (responseMove.isValid() && !movingIntoCheck(responseMove, copyPlayer, copyOtherPlayer, copyBoard)) {

                    int movePoints = responseMove.getKillValue();
                    int nextTradeDifference = getNextTradeDifference(responseMove, copyBoard, copyOtherPlayer, secondCopyPlayer);
                    int pointChange = movePoints - nextTradeDifference;
                    if (responseMove.isPawnPromotion()) {
                        pointChange += 8;
                    }

                    if (pointChange > maxPointChange) {
                        maxPointChange = pointChange;
                    }
                }
            }
        }

        if (copyPlayer.isInCheck(copyOtherPlayer)) {
            System.out.println("add the half");
            maxPointChange -= 0.5;
        } else if (!copyPlayer.isInCheck(copyOtherPlayer) && maxPointChange == -301) {
            maxPointChange = 10;
            System.out.println("potential stalemate");
        }

        undoMove(copyMove, copyOtherPlayer, copyPlayer);

        return maxPointChange;
    }

    // EFFECTS: Copies board then determines response moves by the opposition to improve foresight of generateMove()
    private int getNextTradeDifference(Move currentMove, Board firstBoard, Player player, Player otherPlayer) {
        Board copyBoard = firstBoard.copyBoard();
        Player copyPlayer = player.copyPlayer();
        copyPlayer.setBoard(copyBoard);
        copyPlayer.setPieces();

        Player secondCopyPlayer = player.copyPlayer();
        secondCopyPlayer.setBoard(copyBoard);
        secondCopyPlayer.setPieces();

        Player copyOtherPlayer = otherPlayer.copyPlayer();
        copyOtherPlayer.setBoard(copyBoard);
        copyOtherPlayer.setPieces();

        Spot origStartSpot = currentMove.getStartSpot();
        Spot origEndSpot = currentMove.getEndSpot();

        Spot copyStartSpot = copyBoard.getSquare(origStartSpot.getY(), origStartSpot.getX());
        Spot copyEndSpot = copyBoard.getSquare(origEndSpot.getY(), origEndSpot.getX());
        Piece copyPieceMoved = copyStartSpot.getPiece();

        Move copyMove = currentMove.copyMove(copyPieceMoved, copyEndSpot);

        advanceMove(copyMove, copyOtherPlayer, copyPlayer);

        int maxPointChange = -201;

        for (Piece p : copyPlayer.getPieces()) {
            for (Spot s : copyBoard.getBoxesAsList()) {
                Move responseMove = new Move(p, s, false);
                if (responseMove.isValid() && !movingIntoCheck(responseMove, copyPlayer, copyOtherPlayer, copyBoard)) {

                    int movePoints = responseMove.getKillValue();
                    int nextResponsePoints = getPointsBestResponse(responseMove, copyBoard, copyOtherPlayer, secondCopyPlayer);
                    int pointChange = movePoints - nextResponsePoints;
                    if (responseMove.isPawnPromotion()) {
                        pointChange += 8;
                    }

                    if (pointChange > maxPointChange) {
                        maxPointChange = pointChange;
                    }
                }
            }
        }

        undoMove(copyMove, copyOtherPlayer, copyPlayer);

        return maxPointChange;
    }

    // EFFECTS: Copies board then determines response moves by the opposition to improve foresight of generateMove()
    private int getPointsBestResponse(Move currentMove, Board firstCopyBoard, Player player, Player otherPlayer) {

        Board secondCopyBoard = firstCopyBoard.copyBoard();
        Player copyPlayer = player.copyPlayer();
        copyPlayer.setBoard(secondCopyBoard);
        copyPlayer.setPieces();

        Player copyOtherPlayer = otherPlayer.copyPlayer();
        copyOtherPlayer.setBoard(secondCopyBoard);
        copyOtherPlayer.setPieces();

        Spot origStartSpot = currentMove.getStartSpot();
        Spot origEndSpot = currentMove.getEndSpot();

        Spot copyStartSpot = secondCopyBoard.getSquare(origStartSpot.getY(), origStartSpot.getX());
        Spot copyEndSpot = secondCopyBoard.getSquare(origEndSpot.getY(), origEndSpot.getX());
        Piece copyPieceMoved = copyStartSpot.getPiece();

        Move copyMove = currentMove.copyMove(copyPieceMoved, copyEndSpot);

        advanceMove(copyMove, copyOtherPlayer, copyPlayer);

        int maxPointChange = -101;

        for (Piece p : copyPlayer.getPieces()) {
            for (Spot s : secondCopyBoard.getBoxesAsList()) {
                Move nextMove = new Move(p, s, true);
                if (nextMove.isValid() && !movingIntoCheck(nextMove, copyPlayer, copyOtherPlayer, secondCopyBoard)) {

                    if (nextMove.getKillValue() > maxPointChange) {
                        maxPointChange = nextMove.getKillValue();
                        if (nextMove.isPawnPromotion()) {
                            maxPointChange += 8;
                        }
                    }
                }
            }
        }

        undoMove(copyMove, copyOtherPlayer, copyPlayer);

        return maxPointChange;
    }

    // EFFECTS: returns true if a given move is illegal on the basis of a player moving into check
    private boolean movingIntoCheck(Move move, Player player, Player otherPlayer, Board currentBoard) {

        if (move.isPawnPromotion()) {

            Board copiedBoard = currentBoard.copyBoard();
            Player copyPlayer = player.copyPlayer();
            copyPlayer.setBoard(copiedBoard);
            copyPlayer.setPieces();

            Player copyOtherPlayer = otherPlayer.copyPlayer();
            copyOtherPlayer.setBoard(copiedBoard);
            copyOtherPlayer.setPieces();

            Spot origStartSpot = move.getStartSpot();
            Spot origEndSpot = move.getEndSpot();

            Spot copyStartSpot = copiedBoard.getSquare(origStartSpot.getY(), origStartSpot.getX());
            Spot copyEndSpot = copiedBoard.getSquare(origEndSpot.getY(), origEndSpot.getX());
            Piece copyPieceMoved = copyStartSpot.getPiece();

            Move copyMove = move.copyMove(copyPieceMoved, copyEndSpot);
            advanceMove(copyMove, copyPlayer, copyOtherPlayer);

            if (copyPlayer.isInCheck(copyOtherPlayer)) {
                undoMove(copyMove, copyPlayer, copyOtherPlayer);
                return true;
            }
            undoMove(copyMove, copyPlayer, copyOtherPlayer);

        } else {
            advanceMove(move, player, otherPlayer);
            if (player.isInCheck(otherPlayer)) {
                undoMove(move, player, otherPlayer);
                return true;
            }
            undoMove(move, player, otherPlayer);
        }

        return false;
    }

    // EFFECTS: Moves the piece to its new spot and removes any killed pieces from the board
    private void advanceMove(Move move, Player player, Player otherPlayer) {
        Piece pieceMoved = move.getPieceMoved();
        Spot spotMovedTo = move.getEndSpot();
        if (move.getPieceKilled() != null) {
            otherPlayer.removePiece(move.getPieceKilled());
        }
        pieceMoved.removeSpot();
        spotMovedTo.setPiece(pieceMoved);

        if (move.isPawnPromotion()) {
            pieceMoved.removeSpot();
            player.removePiece(pieceMoved);
            Piece upgradedPiece = new Queen(pieceMoved.isWhite(), null, pieceMoved.getBoard());
            spotMovedTo.setPiece(upgradedPiece);
            player.addPiece(upgradedPiece);
        }

        whitesTurn = !whitesTurn;
    }

    // EFFECTS: Undoes a move - necessary after speculative moves are examined to determine whether they're strategic
    private void undoMove(Move move, Player player, Player otherPlayer) {
        Piece pieceMoved = move.getPieceMoved();
        Spot spotMovedTo = move.getEndSpot();
        Spot spotMovedFrom = move.getStartSpot();

        pieceMoved.removeSpot();
        spotMovedFrom.setPiece(pieceMoved);

        if (move.isPawnPromotion()) {
            player.removePiece(move.getEndSpot().getPiece());
            move.getEndSpot().getPiece().removeSpot();
            player.addPiece(pieceMoved);
        }

        if (move.getPieceKilled() != null) {
            otherPlayer.addPiece(move.getPieceKilled());
        }

        spotMovedTo.setPiece(move.getPieceKilled());

        whitesTurn = !whitesTurn;
    }

    // EFFECTS: Exits the game after displaying the match result if the player is checkmated or stalemated
    private void checkBlackWin() {
        List<Move> possibleMoves = new ArrayList<>();

        for (Piece p : playerOne.getPieces()) {
            for (Spot s : board.getBoxesAsList()) {
                Move currentMove = new Move(p, s, true);
                if (currentMove.isValid() && !movingIntoCheck(currentMove, playerOne, playerTwo, board)) {
                    possibleMoves.add(currentMove);
                }
            }
        }

        if (possibleMoves.size() == 0) {
            if (playerOne.isInCheck(playerTwo)) {
                JOptionPane.showMessageDialog(this, "Black wins!",
                        "Game Over", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Draw by stalemate!",
                        "Game Over", JOptionPane.INFORMATION_MESSAGE);
            }
            System.exit(0);
        }
    }

    // Initialize the interface settings
    private void initializeUserInterface() {
        super.add(cardPanel);
        super.setSize(WIDTH, HEIGHT);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(20, 20, 20, 20));
        cardPanel.add(createGamePage(), "gamePage");
        cardLayout.show(cardPanel, "gamePage");
        super.setVisible(true);
    }

    // EFFECTS: returns the screen to be displayed which represents the drawing of the board
    private JPanel createGamePage() {
        JPanel introPanel = new JPanel();
        introPanel.setLayout(new GridLayout(8, 8));

        for (int row = 7; row >= 0; row--) {
            for (int col = 0; col < 8; col++) {

                Board board = this.board;
                Spot spot = board.getSquare(row, col);
                JPanel square = createSquare(spot);

                createMouseListener(spot, square);

                introPanel.add(square);
            }
        }

        return introPanel;
    }

    // EFFECTS: Listens for the player to make a move by selecting a piece and a square to move it to
    private void createMouseListener(Spot spot, JPanel square) {
        square.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedPiece != null) {
                    Move move = new Move(selectedPiece, spot, whitesTurn);
                    if (move.isValid() && !movingIntoCheck(move, playerOne, playerTwo, board)) {
                        advanceMove(move, playerOne, playerTwo);
                        updateGamePanel();
                    }
                }
            }
        });
    }

    // EFFECTS: Redraws the game panel after it has been updated
    private void updateGamePanel() {
        cardPanel.removeAll();
        cardPanel.add(createGamePage(), "gamePage");
        cardLayout.show(cardPanel, "gamePage");
        cardPanel.revalidate();
        cardPanel.repaint();
    }

    // EFFECTS: returns the panel of each square to be drawn on the board
    private JPanel createSquare(Spot spot) {
        JPanel squarePanel = new JPanel();

        if (spot.isWhite()) {
            squarePanel.setBackground(Color.LIGHT_GRAY);
        } else {
            squarePanel.setBackground(Color.GRAY);
        }

        if (spot.getPiece() != null) {
            JButton piece = makePieceOnSquare(spot);
            piece.setSize(100, 100);
            piece.setBorder(new EmptyBorder(5, 5, 5, 5));
            squarePanel.add(piece);
        }

        return squarePanel;
    }

    // EFFECTS: returns each game piece as a button to be selected and drawn on the board
    private JButton makePieceOnSquare(Spot spot) {
        ImageIcon pieceImage;

        if (spot.getPiece() instanceof Pawn) {
            pieceImage = getPieceImage("Pawn", spot.getPiece().isWhite());
        } else if (spot.getPiece() instanceof Knight) {
            pieceImage = getPieceImage("Knight", spot.getPiece().isWhite());
        } else if (spot.getPiece() instanceof Bishop) {
            pieceImage = getPieceImage("Bishop", spot.getPiece().isWhite());
        } else if (spot.getPiece() instanceof Rook) {
            pieceImage = getPieceImage("Rook", spot.getPiece().isWhite());
        } else if (spot.getPiece() instanceof Queen) {
            pieceImage = getPieceImage("Queen", spot.getPiece().isWhite());
        } else {
            pieceImage = getPieceImage("King", spot.getPiece().isWhite());
        }

        int newWidth = 70;
        int newHeight = 70;

        ImageIcon resizedPiece = new ImageIcon(pieceImage.getImage().getScaledInstance(newWidth, newHeight,
                Image.SCALE_SMOOTH));

        JButton pieceButton = new PieceAsButton(resizedPiece, spot.getPiece().isWhite());
        pieceButton.addActionListener(e -> {
            this.selectedPiece = spot.getPiece();
        });

        return pieceButton;
    }

    // EFFECTS: return the corresponding image to the piece of interest
    private ImageIcon getPieceImage(String pieceType, boolean isWhite) {
        String colorPrefix = isWhite ? "Light" : "Dark";
        String imagePath = String.format("./programData/%s%s.png", colorPrefix, pieceType);
        return new ImageIcon(imagePath);
    }
}