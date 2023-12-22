package Model;

public class Move {
    private Piece pieceMoved;
    private Piece pieceKilled;
    private boolean whitesTurn;
    private Spot startSpot;
    private Spot endSpot;

    public Move (Piece pieceMoved, Spot endSpot, boolean whitesTurn) {
        this.pieceMoved = pieceMoved;
        this.startSpot = pieceMoved.getSpot();
        this.endSpot = endSpot;
        this.whitesTurn = whitesTurn;
        this.pieceKilled = endSpot.getPiece();
    }

    public boolean isValid() {
        Spot startSpot = pieceMoved.getSpot();
        Piece endPiece = endSpot.getPiece();

        if (endPiece != null && endPiece.isWhite() == whitesTurn) {
            return false;
        }

        if (whitesTurn == pieceMoved.isWhite() && startSpot != endSpot) {

            if (pieceMoved.isValidMove(startSpot, endSpot)) {
                return true;
            }

        }

        return false;
    }

    public int getKillValue() {
        if (this.pieceKilled == null) {
            System.out.println(0);
            return 0;
        } else {
            System.out.println(this.pieceKilled.getValue());
            return this.pieceKilled.getValue();
        }
    }

    public Piece getPieceKilled() {
        return pieceKilled;
    }

    public Piece getPieceMoved() {
        return pieceMoved;
    }

    public Spot getEndSpot() {
        return endSpot;
    }

    public Spot getStartSpot() {
        return startSpot;
    }

    public boolean isWhitesTurn() {
        return whitesTurn;
    }
}
