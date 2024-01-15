package Model;

// Represents a specific piece, a bishop, that extends the abstract Piece class
public class Bishop extends Piece {

    public Bishop(boolean white, Spot spot, Board board) {
        super(white, spot, 3, board);
    }

    // EFFECTS: returns that a move is valid if the bishop moves diagonally
    @Override
    public boolean isValidMove(Spot startSpot, Spot endSpot) {
        int startY = startSpot.getY();
        int startX = startSpot.getX();
        int endY = endSpot.getY();
        int endX = endSpot.getX();
        int deltaX = endX - startX;
        int deltaY = endY - startY;

        if (Math.abs(deltaX) != Math.abs(deltaY) || deltaX == 0) {
            return false;
        }

        int dirY = Math.round(deltaY / Math.abs(deltaY));
        int dirX = Math.round(deltaX / Math.abs(deltaX));

        for (int i = 1; i < Math.abs(deltaY); i++) {
            if (board.getSquare(startY + (i * dirY), startX + (i * dirX)).getPiece() != null) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Piece copyPiece() {
        return new Bishop(white, null, null);
    }

}
