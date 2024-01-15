package Model;

public class PersonPlayer extends Player {

    public PersonPlayer(boolean white, boolean computer, Board board) {
        super(white, computer, board);
    }

    @Override
    public Player copyPlayer() {
        return new PersonPlayer(white, computer, null);
    }

}
