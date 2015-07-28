package battleship;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by paagol on 4/20/15.
 */
public class GameWindowBKP extends JPanel {
    Controller con;
    java.util.Timer tm;
    ArrayList<char[][]> board;
    ArrayList<Point3D[]> fires;
    ArrayList<GunShip[]> gun;
    ArrayList<CargoShip[]> cargo;
    ArrayList<TugBoat[]> tugs;


    BufferedReader cin;

    int cur;


    int[] gposX = new int[4];
    int[] gposY = new int[4];
    int[] cposX = new int[4];
    int[] cposY = new int[4];
    int[] tposX = new int[4];
    int[] tposY = new int[4];
    char[][] grid = new char[20][];

    BufferedImage tug, gun_h, gun_v, sub_h, sub_v, bg_p0, bg_p1;

    GameWindowBKP() {
        super();
    }

    GameWindowBKP(Controller _con) {
        super();
        cur = 0;
        con = _con;
        board = con.gs.simuBoard;
        fires = con.gs.simuFire;
        gun = con.gs.simuGun;
        cargo = con.gs.simuCargo;
        tugs = con.gs.simuTug;

        tm = new Timer();
        tm.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                cur++;
                repaint();
            }
        }, 500, 1000);

        setBackground(Color.CYAN);


        for (int i = 0; i < grid.length; i++) {
            grid[i] = new char[40];
        }

        try {
            bg_p0 = ImageIO.read(new File("resources/p0.jpg"));
            bg_p1 = ImageIO.read(new File("resources/p1.jpg"));
            tug = ImageIO.read(new File("resources/tug.png"));
            gun_h = ImageIO.read(new File("resources/gunship_h.png"));
            gun_v = ImageIO.read(new File("resources/gunship_v.png"));
            sub_h = ImageIO.read(new File("resources/submarine_h.jpg"));
            sub_v = ImageIO.read(new File("resources/submarine_v.jpg"));


            cin = new BufferedReader(new InputStreamReader(new FileInputStream(new File("run.txt"))));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void getNextPoints() {
        try {
            for (int i = 0; i < 4; i++) {
                gposX[i] = cin.read();
                cposX[i] = cin.read();
                tposX[i] = cin.read();
            }
            for (int i = 0; i < 20; i++) {
                String s = cin.readLine();
                grid[i] = s.toCharArray();
                System.out.println(grid[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        System.out.println("hic");


        getNextPoints();

        Scanner cin = new Scanner(new InputStreamReader(System.in));


        super.paintComponent(g);
        if (cur >= board.size()) {
            g.setColor(new Color(200, 200, 200, 100));
            g.drawRect(0, 0, this.getHeight(), this.getWidth());
            g.drawString(con.gs.getWinner(), 100, 100);
            tm.cancel();
            return;
        }


        int h = FixedValues.PLAYER_Y, w = FixedValues.PLAYER_X * 2;
        int a = this.getHeight(), b = this.getWidth();
        h = a / h;
        w = b / w;


        g.drawImage(bg_p0, 0, 0, w * FixedValues.PLAYER_X, h * FixedValues.PLAYER_Y, null);
        g.drawImage(bg_p1, w * FixedValues.PLAYER_X, 0, w * FixedValues.PLAYER_X, h * FixedValues.PLAYER_Y, null);

//        g.drawImage(new)

//        g.setColor(Color.LIGHT_GRAY);
//        g.fillRect(0, 0, w * FixedValues.PLAYER_X, h * FixedValues.PLAYER_Y);
//        g.setColor(Color.cyan);
//        g.fillRect(w * FixedValues.PLAYER_X,0,w * FixedValues.PLAYER_X, h*FixedValues.PLAYER_Y);


        //draw the grid
        g.setColor(Color.BLUE);
        for (int i = 0; i < a; i += h) {
            for (int j = 0; j < b; j += w) {
                g.drawRect(j, i, w, h);
            }
        }


        char[][] tmp = board.get(cur);
        Point3D[] fire = fires.get(cur);


        System.out.println("xz " + gun.size() + " " + cur);
        GunShip[] gn = gun.get(cur);
        CargoShip[] c = cargo.get(cur);
        TugBoat[] t = tugs.get(cur);


//
//
//        for (int i = 0; i < 2; i++) {
//            System.out.println("Gun -> "+gn[i].x+" "+Util.gy(gn[i].y)+" "+gn[i].y);
//            System.out.println("Cargo -> "+gn[i].x+" "+Util.cy(c[i].y)+" "+c[i].y);
//            System.out.println("Tug -> "+gn[i].x+" "+Util.ty(t[i].y)+" "+t[i].y);
//        }
//
//        for (int i = 2; i < 4; i++) {
//            System.out.println("Gun -> ("+Util.x(gn[i].x)+","+Util.gy(Util.y(gn[i].y))+") (" +
//                    gn[i].x+","+gn[i].y+")");
//            System.out.println("Cargo -> ("+Util.x(gn[i].x)+","+Util.cy(Util.y(c[i].y))+") ("+
//                    c[i].x+","+c[i].y+")");
//            System.out.println("Tug -> ("+Util.x(gn[i].x)+","+Util.ty(Util.y(t[i].y))+")" +
//                    " ("+t[i].x+","+t[i].y+")");
//        }


        //draw player 0 ships
//        for (int i = 0; i < 2; i++) {
//            int x = gn[i].x*w,y=Util.gy(gn[i].y)*h;
////            System.out.println(x+" "+y);
//            if(gn[i].incx==1){ //horizontal
//                g.drawImage(gun_h,x,y,w*5,h*2,null);
//            }else{
//                g.drawImage(gun_v,x,y,w*2,h*5,null);
//            }
//            x = c[i].x*w;
//            y = Util.cy(c[i].y)*h;
//            if(c[i].incx==1){ //horizontal
//                g.drawImage(sub_h,x,y,w*3,h*1,null);
//            }else{
//                g.drawImage(sub_v,x,y,w,h*3,null);
//            }
//        }
        //draw player 1 ships
//        for (int i = 2; i < 4; i++) {
//            int x = gn[i].x*w,y=Util.gy(Util.y(gn[i].y))*h;
////            System.out.println(x+" "+y);
//            if(gn[i].incx==1){ //horizontal
//                g.drawImage(gun_h,x,y,w*5,h*2,null);
//            }else{
//                g.drawImage(gun_v,x,y,w*2,h*5,null);
//            }
//            x = c[i].x*w;
//            y = Util.gy(Util.y(c[i].y))*h;
//            if(c[i].incx==1){ //horizontal
//                g.drawImage(sub_h,x,y,w*3,h*1,null);
//            }else{
//                g.drawImage(sub_v,x,y,w,h*3,null);
//            }
//        }


//        for (int i = tmp.length-1; i >=0 ; i--) {
//            for (int j = 0; j < tmp[i].length; j++) {
//                if(tmp[i][j]=='.') continue;
//                int x = j*w, y = i*h;
//                switch (tmp[i][j]){
//                    case '#':
//                        g.setColor(Color.RED);
//                        g.fillRect(x,y,w,h);
//                        break;
//                    case 'T':
//                        g.drawImage(tug,x-8,y-8,w+15,h+15,null);
//                        break;
//                    case 't':
//                        g.drawImage(tug,x-8,y-8,w+15,h+15,null);
//                        g.setColor(new Color(255,0,0,64));
//                        break;
//                }
//            }
//        }

//        for (int i = 0; i <tmp.length; i++) {
//            for (int j = 0; j < tmp[i].length; j++) {
//                if(tmp[i][j]=='.') continue;
//                int x = j*w, y = i*h;
//                switch (tmp[i][j]){
//                    case '#':
//                        g.setColor(Color.RED);
//                        break;
//                    case 'G':
//                        g.setColor(Color.black);
//                        break;
//                    case 'C':
//                        g.setColor(Color.GRAY);
//                        break;
//                    case 'T':
//                        g.setColor(Color.LIGHT_GRAY);
//                        break;
//                    case 'H':
//                        g.setColor(Color.ORANGE);
//                }
//                g.fillRect(x,y,w,h);
//            }
//        }

        //show fires
//        for (int i = 0; i < fire.length; i++) {
//            g.setColor(new Color(255, 161, 4, 147));
//            int x = fire[i].getX(),y=fire[i].getY();
//            x*=w;
//            y*=h;
//            g.fillRect(x,y,w,h);
//        }
//
//        System.out.println(tmp[0]);
//        System.out.println(cur);
//        cin.nextLine();
    }


}
