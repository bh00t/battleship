/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import battleship.Point3D;

import java.io.*;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;
import java.util.Random;

/**
 * @author paagol
 */
public class Player {
    int pid;
    Runtime rt;
    Process proc;
    BufferedReader cin;
    BufferedWriter cout;
    Controller con;

    GunShip gun[] = new GunShip[2];
    CargoShip cargo[] = new CargoShip[2];
    TugBoat tug[] = new TugBoat[2];

    boolean visited[][];


    int points;
    int travarse;
    int tot_point;

    int tot_moves;

    Point3D[] preFired;


    Player() {

    }

    Player(int _pid, String type, Controller _con) {
        pid = _pid;
        initStreams(type);
        con = _con;
        points = 0;
        travarse = 0;
        visited = new boolean[FixedValues.PLAYER_X][];
        for (int i = 0; i < visited.length; i++) {
            visited[i] = new boolean[FixedValues.PLAYER_Y];
        }
        tot_point = -1;
        tot_moves = 0;
        preFired = new Point3D[0];
    }


    public void initStreams(String type) {
        rt = Runtime.getRuntime();
        String path = "player" + pid;
//        String path = "test_bot_new"+pid;
        String cmd = "";
        if (type.equals("java")) {
//            path = path;
            cmd = "java -classpath " + path + "0 Main";
        } else if (type.equals("exe")) {
            path = path + ".exe";
            cmd = path;
        } else if (type.equals("linux")) {
            cmd = "./" + path;
        }
        try {
//            System.out.println(cmd);
            proc = rt.exec(cmd);

        } catch (IOException e) {
            e.printStackTrace();
        }

        cin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        cout = new BufferedWriter(new OutputStreamWriter(proc.getOutputStream()));
    }

    public void setBoard() {
        try {
            //send the board configuration to player
            if (pid == 1) con.gs.rotateBoard();
            cout.write(Integer.toString(FixedValues.PLAYER_X) + " "
                    + Integer.toString(FixedValues.PLAYER_Y) + "\n");
            for (int i = 0; i < FixedValues.PLAYER_Y; i++) {
                for (int j = 0; j < FixedValues.PLAYER_X; j++) {
                    cout.write(con.gs.board[i][j]);
                }
                cout.write("\n");
            }
            cout.flush();
            if (pid == 1) con.gs.rotateBoard();


            //now read in the ships' positions
            System.out.println("Init positions player"+pid);
            String[] v = new String[6];
            for (int i = 0; i < 6; i++) {
                v[i] = cin.readLine();
                System.out.println(v[i]);
            }

            gun[0] = new GunShip(v[0]);
            gun[1] = new GunShip(v[1]);
            cargo[0] = new CargoShip(v[2]);
            cargo[1] = new CargoShip(v[3]);
            tug[0] = new TugBoat(v[4]);
            tug[1] = new TugBoat(v[5]);


            //for invalid board generate a random positioning
            Move mv = new Move(this, 0);
            while (!mv.isValidSetup()) {
                System.out.println("invalid setup");
                for (int i = 0; i < 2; i++) {
                    int a, b, c;
                    a = (new Random()).nextInt() % FixedValues.PLAYER_X;
                    b = (new Random()).nextInt() % FixedValues.PLAYER_Y;
                    c = (new Random()).nextInt() % 2;

                    if (c == 1)
                        gun[i] = new GunShip('h', a, b);
                    else
                        gun[i] = new GunShip('v', a, b);

                    a = (new Random()).nextInt() % FixedValues.PLAYER_X;
                    b = (new Random()).nextInt() % FixedValues.PLAYER_Y;
                    c = (new Random()).nextInt() % 2;

                    if (c == 1)
                        cargo[i] = new CargoShip('h', a, b);
                    else
                        cargo[i] = new CargoShip('v', a, b);

                    a = (new Random()).nextInt() % FixedValues.PLAYER_X;
                    b = (new Random()).nextInt() % FixedValues.PLAYER_Y;
                    tug[i] = new TugBoat(a, b);
                }
                mv = new Move(this, 0);
            }

            System.out.println("init position finish");
            for (int i = 0; i < 2; i++) {
                System.out.println("gun -> ("+gun[i].x+","+gun[i].y+")");
                System.out.println("sub -> ("+cargo[i].x+","+cargo[i].y+")");
                System.out.println("tug -> ("+tug[i].x+","+tug[i].y+")");
            }

            con.gs.writeBoard();

            con.gs.update(new Move(this, con.gs.curMove), this);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void nextMove() {
//        con.players[1 - pid].sendToPlayer(new Point3D[0]); //to undelete
        tot_moves++;
        String[] v = new String[6];
        for (int i = 0; i < 6; i++) {

            try {
                v[i] = cin.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            System.out.println(pid+" -> "+v[i]);
        }
        Move mv = new Move(v, this, con.gs.curMove);
        if (!mv.isValid()) {
//            System.out.println("hic player next move");
            sendToPlayer(new Point3D[0]);
            con.players[1 - pid].sendToPlayer(new Point3D[0]);
            return;
        }

        Point3D[] fire = con.gs.update(mv, this);
        updatePosition(mv);
//        con.gs.update(new Move(con.players[1 - pid], con.gs.curMove), this);
        con.gs.update(new Move(con.players[1 - pid], con.gs.curMove), con.players[1 - pid]);
        sendToPlayer(fire);
        con.players[1 - pid].sendToPlayer(new Point3D[0]); ///to del
    }


    public void nextMove2() {
        tot_moves++;

        responstype1(con.players[1 - pid].preFired);

        String[] v = new String[6];
        for (int i = 0; i < 6; i++) {

            try {
                v[i] = cin.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            System.out.println(pid+" -> "+v[i]);
        }
        Move mv = new Move(v, this, con.gs.curMove);
        if (!mv.isValid()) {
            System.out.println("invalid Move for player"+pid);
            responsetype2(new Point3D[0]);
            preFired = new Point3D[0];
//            con.players[1-pid].responstype1(new Point3D[0]);
            return;
        }


        Point3D[] fire = con.gs.update(mv, this);
        preFired = fire.clone();
        updatePosition(mv);

        Move oppoMv = new Move(con.players[1 - pid], con.gs.curMove);
        con.gs.update(oppoMv, con.players[1 - pid]);
        con.players[1 - pid].updatePosition(oppoMv);

        responsetype2(fire);

        if (pid == 1)
            con.gs.simuFire.add(Util.points(fire));
        else
            con.gs.simuFire.add(fire.clone());
    }

    public void sendToPlayer(Point3D[] fire) {
        try {

//            cout.write("\n\nplayer=> "+pid);

            cout.write(Integer.toString(fire.length) + "\n");

            for (Point3D aFire : fire) {
                if (aFire.getZ() == 1) {
                    cout.write("h " + Integer.toString((int) aFire.getX()) + " " + Integer.toString((int) aFire.getY()) + "\n");
//                    con.players[1-pid].updateHit((int)aFire.getX(), (int) aFire.getY());
                } else
                    cout.write("m " + Integer.toString((int) aFire.getX()) + " " + Integer.toString((int) aFire.getY()) + "\n");
            }
            cout.flush();


            //send own ships' positions
            cout.write(Integer.toString(gun[0].x) + " " + Integer.toString(gun[0].x) + "\n");
            cout.write(Integer.toString(gun[1].x) + " " + Integer.toString(gun[1].x) + "\n");
            cout.write(Integer.toString(cargo[0].x) + " " + Integer.toString(cargo[0].x) + "\n");
            cout.write(Integer.toString(cargo[1].x) + " " + Integer.toString(cargo[1].x) + "\n");
            cout.write(Integer.toString(tug[0].x) + " " + Integer.toString(tug[0].x) + "\n");
            cout.write(Integer.toString(tug[1].x) + " " + Integer.toString(tug[1].x) + "\n");


            if (pid == 1) con.gs.rotateBoard();



            /*
            ///write the board , seems unnecessary
            for (int i = 0; i < FixedValues.PLAYER_Y; i++) {
                for (int j = 0; j < FixedValues.PLAYER_X; j++) {
                    cout.write(con.gs.board[i][j]);
                }
                cout.write("\n");
            }
            */


            gun[0].writeBody(cout);
            gun[1].writeBody(cout);
            cargo[0].writeBody(cout);
            cargo[1].writeBody(cout);
            tug[0].writeBody(cout);
            tug[1].writeBody(cout);


            for (int i = 0; i < 2; i++) {
                if (tug[i].destroyed) {
                    cout.write("###\n");
                    cout.write("###\n");
                    cout.write("###\n");
                    continue;
                }
                for (int j = tug[i].y - 1; j <= tug[i].y + 1; j++) {

                    if (j < 0 || j >= FixedValues.PLAYER_Y) {
                        cout.write("###\n");
                        continue;
                    }
                    for (int k = tug[i].x - 1; k <= tug[i].x + 1; k++) {
//                        System.out.println(" "+j+" "+k);

                        if (k < 0 || k >= FixedValues.PLAYER_X * 2 || con.players[1 - pid].isInvisible(k, j)) {
                            cout.write("#");
                        } else
                            cout.write(con.gs.board[j][k]);
                    }
                    cout.write("\n");
                }
            }


            if (pid == 1) con.gs.rotateBoard();


            cout.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    boolean isInvisible(int x, int y) {
        int cur = con.gs.curMove;
        return gun[0].isInvisible(x, y, cur) || gun[1].isInvisible(x, y, cur) ||
                cargo[0].isInvisible(x, y, cur) || cargo[1].isInvisible(x, y, cur);
    }

    public boolean hit(int x, int y) {

        return gun[0].isHit(x, y) || gun[1].isHit(x, y) ||
                cargo[0].isHit(x, y) || cargo[1].isHit(x, y);

//        for (int i = 0; i < 2; i++) {
//            if (x >= gun[i].x && x < gun[i].x + gun[i].width && y >= gun[i].y && y < gun[i].y + gun[i].length) {
//                return true;
//            }
//        }
//
//        for (int i = 0; i < 2; i++) {
//            if (x >= cargo[i].x && x < cargo[i].x + cargo[i].width && y >= cargo[i].y && y < cargo[i].y + cargo[i].length) {
//                return true;
//            }
//        }
//        for (int i = 0; i < 2; i++) {
//            if (x >= tug[i].x && x < tug[i].x + tug[i].width && y >= tug[i].y && y < tug[i].y + tug[i].length) {
//                return true;
//            }
//        }
//        return false;
    }


    public void updateHit(int x, int y) {
//        if(pid==1) {
        x = Util.x(x);
        y = Util.y(y);
//        }
        gun[0].hit(x, y);
        gun[1].hit(x, y);
        cargo[0].hit(x, y);
        cargo[1].hit(x, y);
        tug[0].hit(x, y);
        tug[1].hit(x, y);

    }


    public boolean noPower() {
        return gun[0].destroyed && gun[1].destroyed &&
                cargo[0].destroyed && cargo[1].destroyed;
    }


    public int getPoints() {
//        if (tot_point != -1) return tot_point;
        travarse = 0;
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[i].length; j++) {
                if (visited[i][j])
                    travarse++;
            }
        }
        tot_point = travarse + points;
        return tot_point;
    }

    public void finish() {
        try {
            cin.close();
            cout.close();

            proc.destroy();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //before move send the opponent's move's effect
    public void responstype1(Point3D fire[]) {
        try {
            cout.write((fire.length) + "\n");
//            System.out.println(fire.length);
            for (int i = 0; i < fire.length; i++) {
                cout.write(fire[i].getX() + " " + fire[i].getY() + "\n");
//                System.out.println(fire[i].getX() + " " + fire[i].getY());
            }

            Player oppo = con.players[1 - pid];
            if (oppo.tug[0].x >= FixedValues.PLAYER_X) {
                cout.write(Util.x(oppo.tug[0].x) + " " + Util.y(oppo.tug[0].y) + "\n");
            } else {
//                System.out.println("hic1");
                cout.write("-1 -1\n");
            }
            cout.flush();
            if (oppo.tug[1].x >= FixedValues.PLAYER_X) {
                cout.write(Util.x(oppo.tug[1].x) + " " + Util.y(oppo.tug[1].y) + "\n");
            } else {
//                System.out.println("hic2");
                cout.write("-1 -1\n");
            }
            cout.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //send own boads state after  move
    public void responsetype2(Point3D fire[]) {
        try {


            cout.write(Integer.toString(fire.length) + "\n");

            for (Point3D aFire : fire) {

                if (aFire.getZ() == 1) {
                    cout.write("h " + Integer.toString((int) aFire.getX()) + " " + Integer.toString((int) aFire.getY()) + "\n");
//                    con.players[1-pid].updateHit(aFire.getX(), aFire.getY());
                } else {
                    cout.write("m " + Integer.toString((int) aFire.getX()) + " " + Integer.toString((int) aFire.getY()) + "\n");
                }
            }
            cout.flush();


            //send own ships' positions
            cout.write(Integer.toString(gun[0].x) + " " + Integer.toString(gun[0].y) + "\n");
            cout.write(Integer.toString(gun[1].x) + " " + Integer.toString(gun[1].y) + "\n");
            cout.write(Integer.toString(cargo[0].x) + " " + Integer.toString(cargo[0].y) + "\n");
            cout.write(Integer.toString(cargo[1].x) + " " + Integer.toString(cargo[1].y) + "\n");
            cout.write(Integer.toString(tug[0].x) + " " + Integer.toString(tug[0].y) + "\n");
            cout.write(Integer.toString(tug[1].x) + " " + Integer.toString(tug[1].y) + "\n");


            if (pid == 1) con.gs.rotateBoard();


            ///write the board , seems unnecessary
            for (int i = 0; i < FixedValues.PLAYER_Y; i++) {
                for (int j = 0; j < FixedValues.PLAYER_X; j++) {
                    if(con.gs.board[i][j]=='f')
                        cout.write('.');
                    else
                        cout.write(con.gs.board[i][j]);
                }
                cout.write("\n");
            }


            //tugboat visibility
            for (int i = 0; i < 2; i++) {
//                if (tug[i].destroyed) {
//                    cout.write("###\n");
//                    cout.write("###\n");
//                    cout.write("###\n");
//                    continue;
//                }
                for (int j = tug[i].y - 1; j <= tug[i].y + 1; j++) {

                    if (j < 0 || j >= FixedValues.PLAYER_Y) {
                        cout.write("###\n");
                        continue;
                    }
                    for (int k = tug[i].x - 1; k <= tug[i].x + 1; k++) {
                        if(k<0 || k>=FixedValues.PLAYER_X*2){
                            cout.write("#");
                            continue;
                        }
//                        System.out.println(" "+j+" "+k);
                        int a = Util.x(k),b=Util.y(j);
                        char ch = con.gs.board[j][k];
                        if(ch=='f')ch='.';
                        if ((ch!='#' && ch!='.' && k>=FixedValues.PLAYER_X && con.players[1 - pid].isInvisible(a, b)) ){
                            cout.write("#");
                        } else
                            cout.write(ch);
                    }
                    cout.write("\n");
                }
            }


            if (pid == 1) con.gs.rotateBoard();


            cout.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    void updatePosition(Move mv) {
        for (int i = 0; i < 2; i++) {
            //gunship
            gun[i].x = mv.gx[i];
            gun[i].y = mv.gy[i];

            //submarine
            cargo[i].x = mv.cx[i];
            cargo[i].y = mv.cy[i];

            //tug
            tug[i].x = mv.tx[i];
            tug[i].y = mv.ty[i];

            //update traverse points;
            if(tug[i].x<FixedValues.PLAYER_X)continue;
            int t= tug[i].x-FixedValues.PLAYER_X;
            visited[tug[i].y][t] = true;
//            System.out.println("move point");
        }
    }

}
