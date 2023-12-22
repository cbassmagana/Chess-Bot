package UserInterface;

import javax.swing.*;

public class PieceAsButton extends JButton {
    private boolean isWhite;

    public PieceAsButton(Icon icon, boolean isWhite) {
        super(icon);
        this.isWhite = isWhite;
    }

    @Override
    public boolean contains(int x, int y) {
        if (isWhite) {
            return super.contains(x,y);
        } else {
            return false;
        }
    }
}
