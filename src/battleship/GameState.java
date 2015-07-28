/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import battleship.Point3D;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * @author paagol
 */
public class GameState {

    ArrayList<char[][]> simuBoard;
    ArrayList<Point3D[]> simuFire;
    ArrayList<GunShip[]> simuGun;
    ArrayList<CargoShip[]> simuCargo;
    ArrayList<TugBoat[]> simuTug;

    char board[][];
    int curMove;
    Controller con;
    boolean isFinished;
    String winner;

    File simulation;
    BufferedWriter sout, wout;


    GameState() {

    }


    GameState(Controller _con) {
        con = _con;
        board = new char[FixedValues.PLAYER_Y][];
        for (int i = 0; i < board.length; i++)
            board[i] = new char[FixedValues.PLAYER_X * 2];
        isFinished = false;
        winner = "";
        simulation = new File(FixedValues.simu_file);
        try {
            sout = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(simulation)));
            wout = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("resources" + File.separator + "run.txt"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        simuBoard = new ArrayList<char[][]>();
        simuFire = new ArrayList<Point3D[]>();
        simuGun = new ArrayList<GunShip[]>();
        simuTug = new ArrayList<TugBoat[]>();
        simuCargo = new ArrayList<CargoShip[]>();

    }


    void rotateBoard() {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length / 2; j++) {
                char t = board[i][j];
                board[i][j] = board[i][board[i].length - 1 - j];
                board[i][board[i].length - 1 - j] = t;
            }
        }

        for (int i = 0; i < board.length / 2; i++) {
            char[] t = board[i];
            board[i] = board[board.length - 1 - i];
            board[board.length - 1 - i] = t;
        }

    }

    public void erasePrev(Player p) {
        if (p.pid == 1) rotateBoard();
        Move mv = new Move(p, curMove);
        for (int i = 0; i < FixedValues.PLAYER_Y; i++) {
            for (int j = 0; j < FixedValues.PLAYER_X; j++) {
                if (board[i][j] == '#' || board[i][j]=='f') continue;
                board[i][j] = '.';
            }
        }
//        Point3D[] ret = new Point3D[2];
//        int cnt=0;
//        for(int i=0;i<2;i++)
//            for (int k = mv.gy[i]; k < mv.gy[i] + mv.ln[i]; k++) {
//                for (int j = mv.gx[i]; j < mv.gx[i] + mv.wd[i]; j++) {
//                    board[k][j] = '.';
//                }
//            }
//
//        for(int i=0;i<2;i++)
//            for (int k = mv.cy[i]; k < mv.cy[i] + mv.ln[2+i]; k++) {
//                for (int j = mv.cx[i]; j < mv.cx[i] + mv.wd[2+i]; j++) {
//                    board[k][j] = '.';
//                }
//            }
//
//
//        for(int i=0;i<2;i++)
//            for (int j = mv.ty[i]; j < mv.ty[i] + 1; j++) {
//                for (int k = mv.tx[i]; k < mv.tx[i] + 1; k++) {
//                    board[j][k] = '.';
//                }
//            }
        if (p.pid == 1) rotateBoard();
    }

    public Point3D[] update(Move mv, Player p) {
        System.out.println("gs update");
        erasePrev(p);
        if (p.pid == 1) rotateBoard();

        Point3D[] ret = new Point3D[mv.fcount];
        System.out.println("fcout>>" + mv.fcount);
        int cnt = 0;


        //update fire
        for (int i = 0; i < mv.fcount; i++) {
            System.out.println("fired at->("+mv.firex[i]+","+mv.firey[i]+")");
            if(mv.firex[i]>=FixedValues.PLAYER_X*2 || mv.firey[i]>=FixedValues.PLAYER_Y)continue;
            if (!con.players[1-p.pid].isInvisible(Util.x(mv.firex[i]),Util.y(mv.firey[i])) &&
                    con.players[1 - p.pid].hit(Util.x(mv.firex[i]), Util.y(mv.firey[i]))) {
                ret[cnt++] = new Point3D(mv.firex[i], mv.firey[i], 1);
                System.out.println("didhit->" + mv.firex[i] + " " + mv.firey[i]);
                p.points += FixedValues.HIT_POINT;
                board[mv.firey[i]][mv.firex[i]] = 'H';
            } else {
                ret[cnt++] = new Point3D(mv.firex[i], mv.firey[i], 0);
                board[mv.firey[i]][mv.firex[i]] = 'f';
            }
        }

        //update invisibility
        for (int i = 0; i < 2; i++) {
            p.gun[i].lastInvisible = mv.invisible[i];
            p.gun[i].invisibleCount += mv.invisibleCount[i];

            p.cargo[i].lastInvisible = mv.invisible[2+i];
            p.cargo[i].invisibleCount += mv.invisibleCount[2+i];
        }

        //update own gunship position
        for (int i = 0; i < 2; i++)
            for (int k = mv.gy[i]; k < mv.gy[i] + mv.ln[i]; k++) {
                for (int j = mv.gx[i]; j < mv.gx[i] + mv.wd[i]; j++) {
                    board[k][j] = p.gun[i].body[k - mv.gy[i]][j - mv.gx[i]];
//                    if(board[k][j]=='H'){
//                        System.out.println("updated fire at ("+j+","+k+")");
//                    }
                }
            }

        //update own cargoship position
        for (int i = 0; i < 2; i++)
            for (int k = mv.cy[i]; k < mv.cy[i] + mv.ln[2 + i]; k++) {
                for (int j = mv.cx[i]; j < mv.cx[i] + mv.wd[2 + i]; j++) {
                    board[k][j] = p.cargo[i].body[k - mv.cy[i]][j - mv.cx[i]];
//                    if(board[k][j]=='H'){
//                        System.out.println("updated fire at ("+j+","+k+")");
//                    }
                }
            }

        //update tugboat position
        for (int i = 0; i < 2; i++)
            for (int j = mv.ty[i]; j < mv.ty[i] + 1; j++) {
                for (int k = mv.tx[i]; k < mv.tx[i] + 1; k++) {
                    if (k >= FixedValues.PLAYER_X)
                        board[j][k] = 't';
                    else
                        board[j][k] = 'T';
//                    board[j][k] = p.tug[i].body[0][0];
                }
            }


        //today update enemy tugboat
        if(con.players[1-p.pid]!=null)
        for (int i = 0; i <2; i++) {
            if(con.players[1-p.pid].tug[i]==null)continue;
            int a = p.con.players[1-p.pid].tug[i].x;
            int b = p.con.players[1-p.pid].tug[i].y;
            if(a<FixedValues.PLAYER_X)continue;
            a = Util.x(a);
            b = Util.y(b);
            board[b][a] = 't';

        }


        //update missile timers
        for (int i = 0; i < 2; i++) {
//            System.out.println("umissile->"+i+" "+mv.missile[i]);
            p.gun[i].missileLastUsed = mv.missile[i];
        }

        ///debug
        for (int j = 0; j < board.length; j++) {
            System.out.println(board[j]);
        }

//        System.out.println("gs update");

        Point3D[] r = new Point3D[cnt];
        System.arraycopy(ret, 0, r, 0, cnt);

        if (p.pid == 1) rotateBoard();

//        mv.p.gun[0].x = mv.gx[0];
//        mv.p.gun[0].y = mv.gy[0];
//        mv.p.gun[1].x = mv.gx[1];
//        mv.p.gun[1].y = mv.gy[1];
//        mv.p.cargo[0].x = mv.cx[0];
//        mv.p.cargo[0].y = mv.cy[0];
//        mv.p.cargo[1].x = mv.cx[1];
//        mv.p.cargo[1].y = mv.cy[1];
//        mv.p.tug[0].x = mv.tx[0];
//        mv.p.tug[0].y = mv.ty[0];
//        mv.p.tug[1].x = mv.tx[1];
//        mv.p.tug[1].y = mv.ty[1];


        return r;
    }


    boolean finished() {
        if (isFinished) return true;

        if (con.players[0].noPower() || con.players[1].noPower()) {
            return true;
        } else if (!con.players[0].noPower() && !con.players[1].noPower()) {
            return con.players[0].tot_moves == FixedValues.TOT_MOVES &&
                    con.players[1].tot_moves == FixedValues.TOT_MOVES;
        }

        return false;
    }

    void endGame() {
        if (isFinished) return;

System.out.println("ended game");
        int ans = -1;
        isFinished = true;
        if (con.players[0].noPower() && con.players[1].noPower()) {
            int t1 = con.players[0].getPoints();
            int t2 = con.players[1].getPoints();
            if (t1 > t2) {
                winner = "Player0: " + t1 + " points\n" + "Player1: " + t2 + " points\n" + "Player 0 has more points. Player 0 wins!!";
            } else if (t2 > t1) {
                winner = "Player0: " + t1 + " points\n" + "Player1: " + t2 + " points\n" + "Player 1 has more points. Player 1 wins!!";
            } else {
                winner = "Player0: " + t1 + " points\n" + "Player1: " + t2 + " points\n" + "Well this is embarrasing. Both have the same points.";

            }
        } else if (!con.players[0].noPower() && !con.players[1].noPower()) {
            int t1 = con.players[0].getPoints();
            int t2 = con.players[1].getPoints();
            if (t1 > t2) {
                winner = "Player0: " + t1 + " points\n" + "Player1: " + t2 + " points\n" + "Player 0 has more points. Player 0 wins!!";
            } else if (t2 > t1) {
                winner = "Player0: " + t1 + " points\n" + "Player1: " + t2 + " points\n" + "Player 1 has more points. Player 1 wins!!";
            } else {
                winner = "Player0: " + t1 + " points\n" + "Player1: " + t2 + " points\n" + "Well this is embarrasing. Both have the same points.";
            }
        } else {
            if (con.players[0].noPower()) {
                winner = "Player 0 has no power left. Player 1 wins!!";
                ans = 1;
            } else {
                winner = "Player 1 has no power left. Player 0 wins!!";
                ans = 0;
            }
        }


        if(curMove<FixedValues.TOT_MOVES){
            int tmp = curMove+1;
            try {
                wout.write(tmp+" "+ans+"\n");
                wout.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        con.players[0].finish();
        con.players[1].finish();

    }

    String getWinner() {
        return winner;
    }


    void writeBoard() {

        try {
            sout.write(con.players[0].points);
            sout.write(" " + Integer.toString(con.players[1].points) + "\n");
            sout.write(con.players[0].tot_moves);
            sout.write(" " + Integer.toString(con.players[1].tot_moves) + "\n");

            wout.write(curMove+" -1\n");
            wout.write(con.players[0].getPoints()+" "+con.players[1].getPoints()+"\n");

            try {
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        sout.write("Player " + i + "\n");
                        sout.write("gun-" + j + " : (" + con.players[i].gun[j].x + "," + con.players[i].gun[j].y + ")\n");
                        sout.write("sub-" + j + " : (" + con.players[i].cargo[j].x + "," + con.players[i].cargo[j].y + ")\n");
                        sout.write("tug-" + j + " : (" + con.players[i].tug[j].x + "," + con.players[i].tug[j].y + ")\n");

                        int x=0,y=0;
                        System.out.println("checking for writing");
                        System.out.println("Player: "+i+"\n"+" j="+j);
                        if(con.players[i].gun[j].isInvisible(curMove)){
                            x= 1;
                            System.out.println("wrote gun invisible");
//                            System.out.println("invis>"+con.players[i].gun[j].lastInvisible+" "+curMove);
                        }
                        if(con.players[i].cargo[j].isInvisible(curMove)){
                            y= 1;
                            System.out.println("wrote cargo invisible");
                        }
                        wout.write(con.players[i].gun[j].x + " " + con.players[i].gun[j].y + " " + con.players[i].gun[j].incx + " "+x+"\n");
                        wout.write(con.players[i].cargo[j].x + " " + con.players[i].cargo[j].y + " " + con.players[i].cargo[j].incx + " "+y+"\n");
                        wout.write(con.players[i].tug[j].x + " " + con.players[i].tug[j].y + "\n");
                    }
                }
            } catch (Exception ex) {
                wout.write("-100 -100 1 0\n");
                wout.write("-100 -100 1 0\n");
                wout.write("-100 -100\n");
                wout.write("-100 -100 1 0\n");
                wout.write("-100 -100 1 0\n");
                wout.write("-100 -100\n");
            }

            System.out.println("In write board");
            for (int i = 0; i < board.length; i++) {
                System.out.println(board[i]);
                for (int j = 0; j < board[i].length; j++) {
                    sout.write(board[i][j]);
                    wout.write(board[i][j]);
                }
                sout.write("\n");
                wout.write("\n");
            }
            System.out.println("End writeboard");

            sout.flush();
            wout.flush();

            char[][] tmp = new char[board.length][];
            for (int i = 0; i < tmp.length; i++) {
                tmp[i] = new char[board[i].length];
                System.arraycopy(board[i], 0, tmp[i], 0, tmp[i].length);
            }
            simuBoard.add(tmp);

        } catch (IOException e) {
            e.printStackTrace();
        }

//        con.window.drawBoard();


    }


    boolean checkObstacle(int tmp[][], Player p) {
        if (p.pid == 1) rotateBoard();

        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[i].length; j++) {
                if (board[i][j] == '#' && tmp[i][j] > 0)
                    return false;
            }
        }

        if (p.pid == 1) rotateBoard();
        return true;
    }

}
