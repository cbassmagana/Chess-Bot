package Model;

import java.util.HashSet;
import java.util.Set;

// Represents an abstract player of the game with a set of pieces
public abstract class Player {
    protected boolean white;
    protected boolean computer;
    protected Set<Piece> pieces;
    protected Piece king;
    protected Board board;

    public Player(boolean white, boolean computer, Board board) {
        this.white = white;
        this.computer = computer;
        this.board = board;
        this.pieces = new HashSet<>();
    }

    // Adds this player's current pieces on the board to their set of pieces
    public void setPieces() {
        for (Spot s : board.getBoxesAsList()) {
            if (s.getPiece() != null && s.getPiece().white == this.white) {
                pieces.add(s.getPiece());
                if (s.getPiece() instanceof King) {
                    this.king = s.getPiece();
                }
            }
        }
    }

    // Returns if the player is currently in check from the other player
    public boolean isInCheck(Player otherPlayer) {
        for (Piece piece : otherPlayer.getPieces()) {
            Move move = new Move(piece, king.getSpot(), !white);
            if (move.isValid()) {
                return true;
            }
        }
        return false;
    }

    public boolean isWhite() {
        return white;
    }

    public Set<Piece> getPieces() {
        return pieces;
    }

    public void removePiece(Piece piece) {
        pieces.remove(piece);
    }

    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public abstract Player copyPlayer();
}
