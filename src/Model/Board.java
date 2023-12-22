package Model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Spot[][] boxes;
    private static Board board;

    private Board() {
        initializeBoard();
    }

    public static Board getInstance() {
        if (board == null) {
            board = new Board();
        }
        return board;
    }

    public void initializeBoard() {
        boxes = new Spot[8][8];

        boxes[0][0] = new Spot(0,0,true, null);
        boxes[0][0].setPiece(new Rook(true, boxes[0][0]));
        boxes[0][1] = new Spot(0,1,false, null);
        boxes[0][1].setPiece(new Knight(true, boxes[0][1]));
        boxes[0][2] = new Spot(0,2,true, null);
        boxes[0][2].setPiece(new Bishop(true, boxes[0][2]));
        boxes[0][3] = new Spot(0,3,false, null);
        boxes[0][3].setPiece(new Queen(true, boxes[0][3]));
        boxes[0][4] = new Spot(0,4,true, null);
        boxes[0][4].setPiece(new King(true, boxes[0][4]));
        boxes[0][5] = new Spot(0,5,false, null);
        boxes[0][5].setPiece(new Bishop(true, boxes[0][5]));
        boxes[0][6] = new Spot(0,6,true, null);
        boxes[0][6].setPiece(new Knight(true, boxes[0][6]));
        boxes[0][7] = new Spot(0,7,false, null);
        boxes[0][7].setPiece(new Rook(true, boxes[0][7]));

        for (int i = 0; i < 8; i++) {
            boxes[1][i] = new Spot(1,i,i%2 != 0, null);
            boxes[1][i].setPiece(new Pawn(true, boxes[1][i]));
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 2; j < 6; j++) {
                boxes[j][i] = new Spot(j, i, (i+j)%2 == 0, null);
            }
        }

        for (int i = 0; i < 8; i++) {
            boxes[6][i] = new Spot(6,i,i%2 == 0, null);
            boxes[6][i].setPiece(new Pawn(false, boxes[6][i]));
        }

        boxes[7][0] = new Spot(7,0,false, null);
        boxes[7][0].setPiece(new Rook(false, boxes[7][0]));
        boxes[7][1] = new Spot(7,1,true, null);
        boxes[7][1].setPiece(new Knight(false, boxes[7][1]));
        boxes[7][2] = new Spot(7,2,false, null);
        boxes[7][2].setPiece(new Bishop(false, boxes[7][2]));
        boxes[7][3] = new Spot(7,3,true, null);
        boxes[7][3].setPiece(new Queen(false, boxes[7][3]));
        boxes[7][4] = new Spot(7,4,false, null);
        boxes[7][4].setPiece(new King(false, boxes[7][4]));
        boxes[7][5] = new Spot(7,5,true, null);
        boxes[7][5].setPiece(new Bishop(false, boxes[7][5]));
        boxes[7][6] = new Spot(7,6,false, null);
        boxes[7][6].setPiece(new Knight(false, boxes[7][6]));
        boxes[7][7] = new Spot(7,7,true, null);
        boxes[7][7].setPiece(new Rook(false, boxes[7][7]));
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
}
