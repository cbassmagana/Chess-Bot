package Model;

public class Bishop extends Piece {

    public Bishop(boolean white, Spot spot) {
        super(white, spot, 3);
    }

    @Override
    public boolean isValidMove(Spot startSpot, Spot endSpot) {
        int startY = startSpot.getY();
        int startX = startSpot.getX();
        int endY = endSpot.getY();
        int endX = endSpot.getX();
        int deltaX = endX - startX;
        int deltaY = endY - startY;

        if (Math.abs(deltaX) != Math.abs(deltaY)) {
            return false;
        }

        int dirY = Math.round(deltaY / Math.abs(deltaY));
        int dirX = Math.round(deltaX / Math.abs(deltaX));

        for (int i = 1; i < Math.abs(deltaY); i++) {
            if (Board.getInstance().getSquare(startY + (i * dirY), startX + (i * dirX)).getPiece() != null) {
                return false;
            }
        }

        return true;
    }

}
