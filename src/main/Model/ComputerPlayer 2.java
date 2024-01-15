package Model;

public class ComputerPlayer extends Player {

    public ComputerPlayer(boolean white, boolean computer, Board board) {
        super(white, computer, board);
    }

    @Override
    public Player copyPlayer() {
        return new ComputerPlayer(white, computer, null);
    }

}
