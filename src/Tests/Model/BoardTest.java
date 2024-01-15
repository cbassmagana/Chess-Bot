package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Unit tests for book class
public class BoardTest {
    Board b1;

    @BeforeEach
    public void runBefore() {
        b1 = new Board();
        b1.initializeBoard();
        b1.initializePieces();
    }

    @Test
    public void testInitialization() {
        assertTrue(b1.getSquare(0, 0).isWhite());
        assertTrue(b1.getSquare(1, 1).isWhite());
        assertFalse(b1.getSquare(1, 0).isWhite());
        assertFalse(b1.getSquare(0, 1).isWhite());
        assertTrue(b1.getSquare(4,4).getPiece() == null);
        assertTrue(b1.getSquare(0, 1).getPiece() != null);
        assertEquals(64, b1.getBoxesAsList().size());
    }
}