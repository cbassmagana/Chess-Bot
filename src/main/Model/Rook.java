package Model;

// Represents a specific piece, a rook, that extends the abstract Piece class
public class Rook extends Piece {

    public Rook(boolean white, Spot spot, Board board) {
        super(white, spot, 5, board);
    }

    // EFFECTS: returns that a move is valid if the rook moves in a straight line horizontally or vertically
    @Override
    public boolean isValidMove(Spot startSpot, Spot endSpot) {
        int startY = startSpot.getY();
        int startX = startSpot.getX();
        int endY = endSpot.getY();
        int endX = endSpot.getX();
        int dirY = 0;
        int dirX = 0;

        if (startX < endX) {
            dirX = 1;
        } else if (startX > endX) {
            dirX = -1;
        }

        if (startY < endY) {
            dirY = 1;
        } else if (startY > endY) {
            dirY = -1;
        }

        if (startX == endX) {
            for (int i = startY + dirY; startY < endY ? i < endY : i > endY;) {
                if (board.getSquare(i, startX).getPiece() != null) {
                    return false;
                }
                i = i + dirY;
            }
            return true;
        }

        if (startY == endY) {
            for (int i = startX + dirX; startX < endX ? i < endX : i > endX;) {
                if (board.getSquare(startY, i).getPiece() != null) {
                    return false;
                }
                i = i + dirX;
            }
            return true;
        }

        return false;
    }

    @Override
    public Piece copyPiece() {
        return new Rook(white, null, null);
    }

}
