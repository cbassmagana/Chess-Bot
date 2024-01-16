import Model.Board;
import Model.ComputerPlayer;
import Model.PersonPlayer;
import Model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Unit tests for book class
public class PlayerTest {
    Board b1;
    Player p1;
    Player p2;

    @BeforeEach
    public void runBefore() {
        b1 = new Board();
        b1.initializeBoard();
        b1.initializePieces();
        p1 = new PersonPlayer(true, false, b1);
        p2 = new ComputerPlayer(false, true, b1);
    }

    @Test
    public void testSetPieces() {
        p1.setPieces();
        p2.setPieces();
        assertEquals(16, p1.getPieces().size());
        assertEquals(16, p2.getPieces().size());
        assertFalse(p1.isInCheck(p2));
        assertFalse(p2.isInCheck(p1));
    }
}