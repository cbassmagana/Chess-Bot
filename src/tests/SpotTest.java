import Model.Board;
import Model.Piece;
import Model.Spot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Unit tests for book class
public class SpotTest {
    Board b1;
    Spot s1;
    Spot s2;
    Piece p1;

    @BeforeEach
    public void runBefore() {
        b1 = new Board();
        b1.initializeBoard();
        b1.initializePieces();
        s1 = b1.getSquare(1,4);
        s2 = b1.getSquare(2,4);
        p1 = s1.getPiece();
    }

    @Test
    public void testSetAndRemovePiece() {
        assertEquals(p1, s1.getPiece());
        assertEquals(s1, p1.getSpot());
        assertTrue(s2.getPiece() == null);

        s2.setPiece(p1);

        assertEquals(p1, s2.getPiece());
        assertEquals(s2, p1.getSpot());
        assertTrue(s1.getPiece() == null);

        s2.removePiece();

        assertTrue(s2.getPiece() == null);
        assertTrue(p1.getSpot() == null);
        assertTrue(s1.getPiece() == null);
    }
}