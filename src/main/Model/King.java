package Model;

// Represents a specific piece, a king, that extends the abstract Piece class
public class King extends Piece {

    public King(boolean white, Spot spot, Board board) {
        super(white, spot, 100, board);
    }

    // EFFECTS: returns that a move is valid if the king moves only 1 square
    @Override
    public boolean isValidMove(Spot startSpot, Spot endSpot) {
        int deltaX = endSpot.getX() - startSpot.getX();
        int deltaY = endSpot.getY() - startSpot.getY();

        if (Math.abs(deltaX) <= 1 && Math.abs(deltaY) <= 1) {
            return true;
        }

        return false;
    }

    @Override
    public Piece copyPiece() {
        return new King(white, null, null);
    }
}
