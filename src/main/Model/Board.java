package Model;

import java.util.ArrayList;
import java.util.List;

// Represents a chess board as a two-dimensional array of spots on the board
public class Board {
    private Spot[][] boxes;

    public Board() {
        boxes = new Spot[8][8];
    }

    // EFFECTS: creates an 8 by 8 board
    public void initializeBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boxes[j][i] = new Spot(j, i, (i+j)%2 == 0, null);
            }
        }
    }

    // EFFECTS: places all pieces in their starting positions
    public void initializePieces() {
        boxes[0][0].setPiece(new Rook(true, boxes[0][0], this));
        boxes[0][1].setPiece(new Knight(true, boxes[0][1], this));
        boxes[0][2].setPiece(new Bishop(true, boxes[0][2], this));
        boxes[0][3].setPiece(new Queen(true, boxes[0][3], this));
        boxes[0][4].setPiece(new King(true, boxes[0][4], this));
        boxes[0][5].setPiece(new Bishop(true, boxes[0][5], this));
        boxes[0][6].setPiece(new Knight(true, boxes[0][6], this));
        boxes[0][7].setPiece(new Rook(true, boxes[0][7], this));

        for (int i = 0; i < 8; i++) {
            boxes[1][i].setPiece(new Pawn(true, boxes[1][i], this));
        }

        for (int i = 0; i < 8; i++) {
            boxes[6][i].setPiece(new Pawn(false, boxes[6][i], this));
        }

        boxes[7][0].setPiece(new Rook(false, boxes[7][0], this));
        boxes[7][1].setPiece(new Knight(false, boxes[7][1], this));
        boxes[7][2].setPiece(new Bishop(false, boxes[7][2], this));
        boxes[7][3].setPiece(new Queen(false, boxes[7][3], this));
        boxes[7][4].setPiece(new King(false, boxes[7][4], this));
        boxes[7][5].setPiece(new Bishop(false, boxes[7][5], this));
        boxes[7][6].setPiece(new Knight(false, boxes[7][6], this));
        boxes[7][7].setPiece(new Rook(false, boxes[7][7], this));
    }

    public Spot getSquare(int y, int x) {
        return boxes[y][x];
    }

    public List<Spot> getBoxesAsList() {
        List<Spot> spotList = new ArrayList<>();
        for (Spot[] spots : boxes) {
            for (Spot s : spots) {
                spotList.add(s);
            }
        }
        return spotList;
    }

    public Board copyBoard() {
        Board copy = new Board();
        copy.initializeBoard();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Spot originalSpot = this.getSquare(i, j);
                Piece originalPiece = originalSpot.getPiece();
                Spot copySpot = copy.getSquare(i, j);

                if (originalPiece != null) {
                    Piece copyPiece = originalPiece.copyPiece();
                    copySpot.setPiece(copyPiece);
                    copyPiece.setBoard(copy);
                }
            }
        }

        return copy;
    }
}
