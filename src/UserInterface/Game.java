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
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Game extends JFrame {
    private boolean stillGoing;
    private boolean whitesTurn;
    private Board board;
    private Player playerOne;
    private Player playerTwo;
    private List<Move> moves;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Piece selectedPiece;
    private Timer computerMoveTimer;

    public Game() {
        this.stillGoing = true;
        this.whitesTurn = true;
        this.board = Board.getInstance();
        this.moves = new LinkedList<>();
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        initializeUserInterface();
        this.playerOne = new PersonPlayer(true, true);
        this.playerTwo = new ComputerPlayer(false, false);

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

    private void makeMove() {
        Move move = generateMove();
        advanceMove(move, playerOne);
        updateGamePanel();
        checkBlackWin();
    }

    private Move generateMove() {
        List<Move> bestMoves = new ArrayList<>();
        int maxPointChange = -101;

        for (Piece p : playerTwo.getPieces()) {
            for (Spot s : Board.getInstance().getBoxesAsList()) {
                Move currentMove = new Move(p, s, false);
                if (currentMove.isValid() && !movingIntoCheck(currentMove, playerTwo)) {

                    int movePoints = currentMove.getKillValue();
                    int responsePoints = getPointsBestResponse(currentMove);
                    int pointChange = movePoints - responsePoints;

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

        System.out.println(bestMoves.size());

        return bestMoves.get(randomInt);
    }

    private int getPointsBestResponse(Move currentMove) {

        advanceMove(currentMove, playerOne);

        int maxPointChange = -101;

        for (Piece p : playerOne.getPieces()) {
            for (Spot s : Board.getInstance().getBoxesAsList()) {
                Move nextMove = new Move(p, s, true);
                if (nextMove.isValid() && !movingIntoCheck(nextMove, playerOne)) {

                    if (nextMove.getKillValue() > maxPointChange) {
                        maxPointChange = nextMove.getKillValue();
                    }
                }
            }
        }

        return maxPointChange;
    }

    private boolean movingIntoCheck(Move move, Player player) {
        Player otherPlayer = getOtherPlayer(player);

        advanceMove(move, otherPlayer);

        if (player.isInCheck(otherPlayer)) {
            undoMove(move, otherPlayer);
            return true;
        }
        undoMove(move, otherPlayer);
        return false;
    }

    private void advanceMove(Move move, Player otherPlayer) {
        Piece pieceMoved = move.getPieceMoved();
        Spot spotMovedTo = move.getEndSpot();
        if (move.getPieceKilled() != null) {
            otherPlayer.removePiece(move.getPieceKilled());
        }
        pieceMoved.removeSpot();
        spotMovedTo.setPiece(pieceMoved);
        whitesTurn = !whitesTurn;
    }

    private void undoMove(Move move, Player otherPlayer) {
        Piece pieceMoved = move.getPieceMoved();
        Spot spotMovedTo = move.getEndSpot();
        Spot spotMovedFrom = move.getStartSpot();

        pieceMoved.removeSpot();
        spotMovedFrom.setPiece(pieceMoved);

        if (move.getPieceKilled() != null) {
            otherPlayer.addPiece(move.getPieceKilled());
        }

        spotMovedTo.setPiece(move.getPieceKilled());

        whitesTurn = !whitesTurn;
    }

    private void checkBlackWin() {
        List<Move> possibleMoves = new ArrayList<>();

        for (Piece p : playerOne.getPieces()) {
            for (Spot s : Board.getInstance().getBoxesAsList()) {
                Move currentMove = new Move(p, s, true);
                if (currentMove.isValid() && !movingIntoCheck(currentMove, playerOne)) {
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

    private JPanel createGamePage() {
        JPanel introPanel = new JPanel();
        introPanel.setLayout(new GridLayout(8, 8));

        for (int row = 7; row >= 0; row--) {
            for (int col = 0; col < 8; col++) {

                Board board = Board.getInstance();
                Spot spot = board.getSquare(row, col);
                JPanel square = createSquare(spot);

                createMouseListener(spot, square);

                introPanel.add(square);
            }
        }

        return introPanel;
    }

    private void createMouseListener(Spot spot, JPanel square) {
        square.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedPiece != null) {
                    Move move = new Move(selectedPiece, spot, whitesTurn);
                    if (move.isValid() && !movingIntoCheck(move, playerOne)) {
                        advanceMove(move, playerTwo);
                        updateGamePanel();
                    }
                }
            }
        });
    }

    private void updateGamePanel() {
        cardPanel.removeAll();
        cardPanel.add(createGamePage(), "gamePage");
        cardLayout.show(cardPanel, "gamePage");
        cardPanel.revalidate();
        cardPanel.repaint();
    }

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

    private ImageIcon getPieceImage(String pieceType, boolean isWhite) {
        String colorPrefix = isWhite ? "Light" : "Dark";
        String imagePath = String.format("./programData/%s%s.png", colorPrefix, pieceType);
        return new ImageIcon(imagePath);
    }

    private Player getOtherPlayer(Player player) {
        if (player == playerOne) {
            return playerTwo;
        }
        return playerOne;
    }
}