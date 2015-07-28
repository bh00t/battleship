/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.io.*;
import java.util.Scanner;

/**
 * @author paagol
 */
public class Controller implements Runnable {
    //    GameWindow window;
    GameState gs;
    Player players[] = new Player[2];
    int firstPlayer;

    String[] obstacleMap;

    Controller() {

    }

    Controller(String[] ary) {
        if(ary.length==1 && ary[0].equals("-s"))return;
//        window = new GameWindow(this);
        if (ary.length < 2) {
            System.out.println("NO player provided");
            System.exit(0);
        }

//        System.out.println("hi");
        gs = new GameState(this);


        if (ary[0].equals("-d")) {
            FixedValues.debug_on = true;
            players[0] = new Player(0, ary[1], this);
            players[1] = new Player(1, ary[2], this);
        } else {
            players[0] = new Player(0, ary[0], this);
            players[1] = new Player(1, ary[1], this);
            run();
        }


//        players[0] = new Player(0,ary[1],this);
//        players[1] = new Player(1,ary[2],this);

//        players[0] = new Player(0,"linux",this);
//        players[1] = new Player(1,"linux",this);



    }


    void initBoard() {
        File cfg = new File(FixedValues.config_file);
        try {
//            BufferedReader cin = new BufferedReader(new FileReader(cfg))
            Scanner cin = new Scanner(new InputStreamReader(new FileInputStream(cfg)));
            FixedValues.PLAYER_X = cin.nextInt();
            FixedValues.PLAYER_Y = cin.nextInt();
//            firstPlayer = cin.nextInt();
            firstPlayer = 0;
            String[] ary = new String[FixedValues.PLAYER_Y];
            for (int i = ary.length - 1; i >= 0; i--) {
                ary[i] = cin.next();
            }

            obstacleMap = ary.clone();

            for (int i = 0; i < ary.length; i++) {
//                System.out.println(">>"+ary[i]);
                for (int j = 0; j < ary[i].length(); j++) {
                    gs.board[i][j] = ary[i].charAt(j);
                }
            }

            gs.rotateBoard();
            for (int i = 0; i < ary.length; i++) {
                for (int j = 0; j < ary[i].length(); j++) {
                    gs.board[i][j] = ary[i].charAt(j);
                }
            }
            gs.rotateBoard();

//            for (int i = 0; i < gs.board.length; i++) {
//                System.out.println(gs.board[i]);
//            }

            cin.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        initBoard();

        players[firstPlayer].setBoard();
        players[1 - firstPlayer].setBoard();


//        window.drawBoard();
////
        for (int i = 0; i < gs.board.length; i++) {
//            System.out.println(gs.board[i]);
        }

        for (int i = 0; i < FixedValues.TOT_MOVES; i++) {
            System.out.println("\n\ncur move-> " + i);
            players[firstPlayer].nextMove2();
            if (gs.finished()) {
                String s = "Player1: points:" + players[0].travarse + "\t travarse:" + players[0].travarse + "\n" +
                        "Player2: points:" + players[1].travarse + "\t travarse:" + players[1].travarse + "\n" +
                        "Winner: " + gs.getWinner();
//                window.showWinner(s);
                break;
            }

            gs.writeBoard();
            players[1 - firstPlayer].nextMove2();
            if (gs.finished()) {
                String s = "Player1: points:" + players[0].travarse + "\t travarse:" + players[0].travarse + "\n" +
                        "Player2: points:" + players[1].travarse + "\t travarse:" + players[1].travarse + "\n" +
                        "Winner: " + gs.getWinner();
//                window.showWinner(s);
                break;
            }
            gs.writeBoard();
            saveShipsSimu();
            gs.curMove++;
//            System.out.println(gs.curMove);
        }

        gs.endGame();
        gs.writeBoard();
        saveShipsSimu();


//        window.showWinner(gs.getWinner());
        System.out.println(gs.getWinner());

    }

    void saveShipsSimu() {
        GunShip[] g = new GunShip[4];
        g[0] = new GunShip(players[0].gun[0]);
        g[1] = new GunShip(players[0].gun[1]);
        g[2] = new GunShip(players[1].gun[0]);
        g[3] = new GunShip(players[1].gun[1]);

        CargoShip[] c = new CargoShip[4];
        c[0] = new CargoShip(players[0].cargo[0]);
        c[1] = new CargoShip(players[0].cargo[1]);
        c[2] = new CargoShip(players[1].cargo[0]);
        c[3] = new CargoShip(players[1].cargo[1]);

        TugBoat[] t = new TugBoat[4];
        t[0] = new TugBoat(players[0].tug[0]);
        t[1] = new TugBoat(players[0].tug[1]);
        t[2] = new TugBoat(players[1].tug[0]);
        t[3] = new TugBoat(players[1].tug[1]);

        gs.simuGun.add(g);
        gs.simuCargo.add(c);
        gs.simuTug.add(t);
//        System.out.println("added-> "+g.length);
    }


}
