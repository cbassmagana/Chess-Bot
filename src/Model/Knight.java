package Model;

public class Knight extends Piece {

    public Knight(boolean white, Spot spot) {
        super(white, spot, 3);
    }

    @Override
    public boolean isValidMove(Spot startSpot, Spot endSpot) {
        int deltaX = endSpot.getX() - startSpot.getX();
        int deltaY = endSpot.getY() - startSpot.getY();

        if (Math.abs(deltaX * deltaY) == 2) {
            return true;
        }
        return false;
    }
}
