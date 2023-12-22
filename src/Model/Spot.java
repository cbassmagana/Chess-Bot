package Model;

public class Spot {
    private int x;
    private int y;
    private boolean white;
    private Piece piece;

    public Spot(int y, int x, boolean white, Piece piece) {
        this.x = x;
        this.y = y;
        this.white = white;
        this.piece = piece;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isWhite() {
        return white;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        if (this.piece != piece) {
            removePiece();
            this.piece = piece;
            piece.setSpot(this);
        }
    }

    public void removePiece() {
        if (piece != null) {
            Piece temp = this.piece;
            this.piece = null;
            temp.removeSpot();
        }
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }
}
