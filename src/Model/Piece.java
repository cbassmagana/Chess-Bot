package Model;

public abstract class Piece {
    protected boolean killed;
    protected boolean white;
    protected Spot spot;
    protected int value;

    public Piece(boolean white, Spot spot, int value) {
        this.white = white;
        this.killed = false;
        this.spot = spot;
        this.value = value;
    }

    public boolean isKilled() {
        return killed;
    }

    public boolean isWhite() {
        return white;
    }

    public Spot getSpot() {
        return spot;
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

    public void setKilled(boolean killed) {
        this.killed = killed;
    }

    public int getValue() {
        return value;
    }
}
