package Model;

// Represents a move in the game, where a player moves a piece from a start spot to an end spot on the board
public class Move {
    private Piece pieceMoved;
    private Piece pieceKilled;
    private boolean whitesTurn;
    private Spot startSpot;
    private Spot endSpot;
    private boolean pawnPromotion;

    public Move (Piece pieceMoved, Spot endSpot, boolean whitesTurn) {
        this.pieceMoved = pieceMoved;
        this.startSpot = pieceMoved.getSpot();
        this.endSpot = endSpot;
        this.whitesTurn = whitesTurn;
        this.pieceKilled = endSpot.getPiece();
        this.pawnPromotion = isPawnPromotion();
    }

    // Returns that a move is valid if it is that player's turn and the move is legal for the piece of interest
    public boolean isValid() {
        Spot startSpot = pieceMoved.getSpot();
        Piece endPiece = endSpot.getPiece();

        if (endPiece != null && endPiece.isWhite() == whitesTurn) {
            return false;
        }

        if (whitesTurn == pieceMoved.isWhite() && startSpot != endSpot) {
            return pieceMoved.isValidMove(startSpot, endSpot);
        }

        return false;
    }

    // Returns true if the move involves a pawn being promoted by reaching the back rank
    public boolean isPawnPromotion() {
        if (pieceMoved instanceof Pawn) {
            if (pieceMoved.isWhite() && endSpot.getY() == 7) {
                return true;
            }
            if (!pieceMoved.isWhite() && endSpot.getY() == 0) {
                return true;
            }
        }
        return false;
    }

    public int getKillValue() {
        if (this.pieceKilled == null) {
            return 0;
        } else {
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

    public Move copyMove(Piece copyPieceMoved, Spot copyEndSpot) {
        return new Move(copyPieceMoved, copyEndSpot, whitesTurn);
    }
}
