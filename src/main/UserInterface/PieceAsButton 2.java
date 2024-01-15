package UserInterface;

import javax.swing.*;

// represents how each piece behaves as a button
public class PieceAsButton extends JButton {
    private boolean isWhite;

    // EFFECTS: constructs a piece with all properties of a button
    public PieceAsButton(Icon icon, boolean isWhite) {
        super(icon);
        this.isWhite = isWhite;
    }

    // EFFECTS: makes it impossible to select pieces of the opposing player's
    @Override
    public boolean contains(int x, int y) {
        if (isWhite) {
            return super.contains(x,y);
        } else {
            return false;
        }
    }
}
