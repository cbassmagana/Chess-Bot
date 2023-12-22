package Model;

public class Queen extends Piece {

    public Queen(boolean white, Spot spot) {
        super(white, spot, 9);
    }

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
                if (Board.getInstance().getSquare(i, startX).getPiece() != null) {
                    return false;
                }
                i = i + dirY;
            }
            return true;
        }

        if (startY == endY) {
            for (int i = startX + dirX; startX < endX ? i < endX : i > endX;) {
                if (Board.getInstance().getSquare(startY, i).getPiece() != null) {
                    return false;
                }
                i = i + dirX;
            }
            return true;
        }

        if (Math.abs(deltaX) == Math.abs(deltaY)) {
            for (int i = 1; i < Math.abs(deltaY); i++) {
                if (Board.getInstance().getSquare(startY + (i * dirY), startX + (i * dirX)).getPiece() != null) {
                    return false;
                }
            }
        }

        return true;
    }
}
