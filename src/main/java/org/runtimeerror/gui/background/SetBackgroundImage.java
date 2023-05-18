package org.runtimeerror.gui.background;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/** TODO: CLASS INFORMATION
    - A játék háttérképét beolvassa, skálázza, majd beilleszthető formátumban visszaadja
 */


public class SetBackgroundImage {
    /** A játékosok kijelző méretének lehívása */
    public Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public JLabel setBackgroundImage() throws IOException {
        /** Beolvassa a képet (Desert.jpg) */
        BufferedImage bufferedImage = ImageIO.read(new File("src/main/java/org/runtimeerror/gui/background/Desert.jpg"));

        /** Újra skálázza a játékos kijelzőméretéhez */
        Image image = bufferedImage.getScaledInstance(ScreenSize.width, ScreenSize.height, Image.SCALE_DEFAULT);

        /** Beállítja, hogy hozzá lehessen adni a frame-hez */
        JLabel background = new JLabel(new ImageIcon(image));
        background.setLayout(new GridBagLayout());
        return background;
    }
}
