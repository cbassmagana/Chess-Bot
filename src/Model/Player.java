package Model;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    private boolean white;
    private boolean computer;
    private List<Piece> pieces;
    private Piece king;

    public Player(boolean white, boolean computer) {
        this.white = white;
        this.computer = computer;
        this.pieces = new ArrayList<>();
        setPieces();
    }

    private void setPieces() {
        for (Spot s : Board.getInstance().getBoxesAsList()) {
            if (s.getPiece() != null && s.getPiece().white == this.white) {
                pieces.add(s.getPiece());
                if (s.getPiece() instanceof King) {
                    this.king = s.getPiece();
                }
            }
        }
    }

    public boolean isWhite() {
        return white;
    }

    public boolean isComputer() {
        return computer;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public int getTotalPoints() {
        int answer = 0;
        for (Piece p : pieces) {
            answer += p.getValue();
        }
        return answer;
    }

    public void removePiece(Piece piece) {
        pieces.remove(piece);
    }

    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    public boolean isInCheck(Player otherPlayer) {
        boolean answer = false;

        for (Piece piece : otherPlayer.getPieces()) {
            Move move = new Move(piece, king.getSpot(), !white);
            if (move.isValid()) {
                answer = true;
            }
        }

        return answer;
    }
}
