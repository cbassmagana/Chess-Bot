package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Unit tests for book class
public class PieceTest {
    Board b1;
    Spot s1;
    Spot s2;
    Piece p1;
    Piece p2;

    @BeforeEach
    public void runBefore() {
        b1 = new Board();
        b1.initializeBoard();
        b1.initializePieces();
        s1 = b1.getSquare(1,4);
        s2 = b1.getSquare(7,4);
        p1 = s1.getPiece();
        p2 = s2.getPiece();
    }

    @Test
    public void testSetAndRemovePiece() {
        assertEquals(p1, s1.getPiece());
        assertEquals(s1, p1.getSpot());
        assertEquals(p2, s2.getPiece());
        assertEquals(s2, p2.getSpot());

        p1.setSpot(s2);

        assertEquals(s2, p1.getSpot());
        assertEquals(p1, s2.getPiece());
        assertTrue(s1.getPiece() == null);
        assertTrue(p2.getSpot() == null);

        p1.removeSpot();

        assertTrue(s2.getPiece() == null);
        assertTrue(p1.getSpot() == null);
        assertTrue(s1.getPiece() == null);
        assertTrue(p2.getSpot() == null);
    }
}