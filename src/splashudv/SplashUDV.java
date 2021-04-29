/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package splashudv;

import com.sun.javafx.applet.Splash;

/**
 *
 * @author David
 */
public class SplashUDV {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.out.println("Loading starts...");

        splash Splash = new splash();
        TEST test = new TEST();
        Splash.setVisible(true);
        try {
            for (int i = 0; i <= 100; i++) {
                Thread.sleep(40);
                Splash.progressP.setText(Integer.toString(i) + "%");
                Splash.progressBar.setValue(i);
            }

        } catch (Exception e) {
        }

        System.out.println("Loading process completed. Closing loading window...");
        Splash.dispose();

        test.setVisible(true);

    }
}
