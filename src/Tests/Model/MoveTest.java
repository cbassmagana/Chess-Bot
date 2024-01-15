package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Unit tests for book class
public class MoveTest {
    Board b1;
    Spot s1;
    Spot s2;
    Spot s3;
    Piece p1;
    Piece p2;
    Move m1;
    Move m2;

    @BeforeEach
    public void runBefore() {
        b1 = new Board();
        b1.initializeBoard();
        b1.initializePieces();
        s1 = b1.getSquare(1,4);
        s2 = b1.getSquare(2,4);
        s3 = b1.getSquare(7,7);
        p1 = s1.getPiece();
        p2 = s2.getPiece();
        m1 = new Move(p1, s2, true);
        m2 = new Move(p1, s3, true);
    }

    @Test
    public void testPawnPromotion() {
        assertFalse(m1.isPawnPromotion());
        assertTrue(m2.isPawnPromotion());
    }

    @Test
    public void testIsValid() {
        assertTrue(m1.isValid());
        assertFalse(m2.isValid());
    }
}