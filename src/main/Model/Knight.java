package Model;

// Represents a specific piece, a knight, that extends the abstract Piece class
public class Knight extends Piece {

    public Knight(boolean white, Spot spot, Board board) {
        super(white, spot, 3, board);
    }

    // EFFECTS: returns that a move is valid if the knight moves in an L shape
    @Override
    public boolean isValidMove(Spot startSpot, Spot endSpot) {
        int deltaX = endSpot.getX() - startSpot.getX();
        int deltaY = endSpot.getY() - startSpot.getY();

        if (Math.abs(deltaX * deltaY) == 2) {
            return true;
        }
        return false;
    }

    @Override
    public Piece copyPiece() {
        return new Knight(white, null, null);
    }
}
