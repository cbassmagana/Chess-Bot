package Model;

// Represents a specific piece, a queen, that extends the abstract Piece class
public class Queen extends Piece {

    public Queen(boolean white, Spot spot, Board board) {
        super(white, spot, 9, board);
    }

    // EFFECTS: returns that a move is valid if the queen moves in diagonally or in a straight line
    @Override
    public boolean isValidMove(Spot startSpot, Spot endSpot) {
        int startY = startSpot.getY();
        int startX = startSpot.getX();
        int endY = endSpot.getY();
        int endX = endSpot.getX();
        int deltaX = endX - startX;
        int deltaY = endY - startY;
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

        if (startSpot.getX() != endSpot.getX() && startSpot.getY() != endSpot.getY()) {
            if (Math.abs(deltaX) != Math.abs(deltaY)) {
                return false;
            }
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

        if (Math.abs(deltaX) == Math.abs(deltaY)) {
            for (int i = 1; i < Math.abs(deltaY); i++) {
                if (board.getSquare(startY + (i * dirY), startX + (i * dirX)).getPiece() != null) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public Piece copyPiece() {
        return new Queen(white, null, null);
    }
}
