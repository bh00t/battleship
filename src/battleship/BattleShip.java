/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * @author paagol
 */
public class BattleShip {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {


        Controller con = new Controller(args);

        // TODO code application logic here
        JFrame frame = new JFrame("JFrame Source Demo");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GameWindow grid = new GameWindow(con);
        grid.setPreferredSize(new Dimension(1000, 500));
        frame.getContentPane().add(grid, BorderLayout.CENTER);
        frame.setTitle("Battleship");
        frame.pack();
        frame.setVisible(true);

    }

}
