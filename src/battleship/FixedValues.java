/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import sun.tools.jar.Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * @author paagol
 */
public class FixedValues {
    static int PLAYER_X = 20;
    static int PLAYER_Y = 20;

    static int GUNSHIP = 2;
    static int GUNSHIP_LENGTH = 5;
    static int GUNSHIP_WIDHT = 2;

    static int TUG = 2;
    static int TUG_LENGTH = 1;
    static int TUG_WIDTH = 1;

    static int CARGO = 2;
    static int CARGO_LENGTH = 3;
    static int CARGO_WIDTH = 1;


    static int TOT_MOVES = 100;

    static int TOTAL_SHIPS = 6;

    static int MISSILE_INTERVAL = 10;
    static int MAX_MISSILES = 5;
    static int INVISIBLE_INTERVAL = 20;
    static int INVISIBLE_DURATION = 5;
    static int MAX_INVISIBLE = 2;


    static final char OBSTACLE = '#';
    static final char HIT = 'H';


    static final String config_file = "resources" + File.separator + "board.cfg";
    static final String simu_file = "resources" + File.separator + "sim.txt";


    static final int HIT_POINT = 5;

    static boolean debug_on = false;




    public static synchronized void playSound(final String url) {
        new Thread(new Runnable() {
            // The wrapper thread is unnecessary, unless it blocks on the
            // Clip finishing; see comments.
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Main.class.getResourceAsStream("/path/to/sounds/" + url));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
}
