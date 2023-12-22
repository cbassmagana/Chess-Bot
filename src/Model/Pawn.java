package Model;

public class Pawn extends Piece {

    public Pawn(boolean white, Spot spot) {
        super(white, spot, 1);
    }

    @Override
    public boolean isValidMove(Spot startSpot, Spot endSpot) {
        int deltaX = endSpot.getX() - startSpot.getX();
        int deltaY = endSpot.getY() - startSpot.getY();
        int dir = white ? 1 : -1;

        if (dir == deltaY && deltaX == 0) {
            if (endSpot.getPiece() == null) {
                return true;
            }
        }

        if (dir == 1 && startSpot.getY() == 1) {
            if (deltaY == 2 && deltaX == 0) {
                return (Board.getInstance().getSquare(startSpot.getY() + 1, startSpot.getX()).getPiece() == null
                && Board.getInstance().getSquare(startSpot.getY() + 2, startSpot.getX()).getPiece() == null);
            }
        }

        if (dir == -1 && startSpot.getY() == 6) {
            if (deltaY == -2 && deltaX == 0) {
                return (Board.getInstance().getSquare(startSpot.getY() - 1, startSpot.getX()).getPiece() == null
                && Board.getInstance().getSquare(startSpot.getY() - 2, startSpot.getX()).getPiece() == null);
            }
        }

        if (dir == deltaY && Math.abs(deltaX) == 1) {
            if (endSpot.getPiece() != null) {
                return true;
            }
        }

        return false;
    }
}
