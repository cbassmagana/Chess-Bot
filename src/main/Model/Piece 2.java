package Model;

// Represents an abstract piece in the game that is on a spot and belongs to a player's side
public abstract class Piece {
    protected boolean white;
    protected Spot spot;
    protected int value;
    protected Board board;

    public Piece(boolean white, Spot spot, int value, Board board) {
        this.white = white;
        this.spot = spot;
        this.value = value;
        this.board = board;
    }

    public void setSpot(Spot spot) {
        if (this.spot != spot) {
            removeSpot();
            this.spot = spot;
            spot.setPiece(this);
        }
    }

    public void removeSpot() {
        if (spot != null) {
            Spot temp = this.spot;
            this.spot = null;
            temp.removePiece();
        }
    }

    public abstract boolean isValidMove(Spot startSpot, Spot endSpot);

    public boolean isWhite() {
        return white;
    }

    public Spot getSpot() {
        return spot;
    }

    public int getValue() {
        return value;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public abstract Piece copyPiece();
}
