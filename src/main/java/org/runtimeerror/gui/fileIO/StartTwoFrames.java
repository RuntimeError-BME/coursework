package org.runtimeerror.gui.fileIO;
import javax.swing.JFrame;


/** TODO: CLASS INFORMATION
 - Ez az osztály valósítja meg a meglévő játék betöltéséhez szükséges két ablakos szélkezelést
 - A konstruktorban kapott ablakot szálkezelhetővé teszi
 - A MenuFrame fogja használni úgy, hogy létrehoz belőle két példányt:
   egyiket a háttérnek, másikat a fájlkezelőnek
 */


public class StartTwoFrames implements Runnable {
    JFrame mainFrame;

    public StartTwoFrames(JFrame frame) {
        this.mainFrame = frame;
    }
    @Override
    public void run() {
        mainFrame.setSize(200, 200);
        mainFrame.setVisible(true);
    }
}