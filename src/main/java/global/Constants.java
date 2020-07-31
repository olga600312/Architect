package global;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public interface Constants {
    Font CAVIAR_DREAMS = new Font("Caviar Dreams Bold", Font.BOLD, 11);
    Color BACKGROUND = new Color(0xFCFDFD);
    Color FOREGROUND = Color.darkGray;
    int insets = 4;
    Border EMPTY_BORDER = BorderFactory.createEmptyBorder(insets, insets, insets, insets);
}
