package Model;

public class King extends Piece {

    public King(boolean white, Spot spot) {
        super(white, spot, 100);
    }

    @Override
    public boolean isValidMove(Spot startSpot, Spot endSpot) {
        int deltaX = endSpot.getX() - startSpot.getX();
        int deltaY = endSpot.getY() - startSpot.getY();

        if (Math.abs(deltaX) <= 1 && Math.abs(deltaY) <= 1) {
            return true;
        }

        return false;
    }
}
