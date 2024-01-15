package Model;

// Represents a specific piece, a pawn, that extends the abstract Piece class
public class Pawn extends Piece {

    public Pawn(boolean white, Spot spot, Board board) {
        super(white, spot, 1, board);
    }

    // EFFECTS: returns true if the pawn moves one (or two on first move) square forwards or captures diagonally
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
                return (board.getSquare(startSpot.getY() + 1, startSpot.getX()).getPiece() == null
                && board.getSquare(startSpot.getY() + 2, startSpot.getX()).getPiece() == null);
            }
        }

        if (dir == -1 && startSpot.getY() == 6) {
            if (deltaY == -2 && deltaX == 0) {
                return (board.getSquare(startSpot.getY() - 1, startSpot.getX()).getPiece() == null
                && board.getSquare(startSpot.getY() - 2, startSpot.getX()).getPiece() == null);
            }
        }

        if (dir == deltaY && Math.abs(deltaX) == 1) {
            if (endSpot.getPiece() != null) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Piece copyPiece() {
        return new Pawn(white, null, null);
    }
}
