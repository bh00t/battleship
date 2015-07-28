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
public class GameWindow extends JPanel {
    Controller con;
    java.util.Timer tm;
    ArrayList<char[][]> board;
    ArrayList<Point3D[]> fires;
    ArrayList<GunShip[]> gun;
    ArrayList<CargoShip[]> cargo;
    ArrayList<TugBoat[]> tugs;


    //    BufferedReader cin;
    Scanner cin;
    int cur;


    int[] gposX = new int[4];
    int[] gposY = new int[4];
    int[] cposX = new int[4];
    int[] cposY = new int[4];
    int[] tposX = new int[4];
    int[] tposY = new int[4];
    int[] incx = new int[12];

    int[] invisible = new int[8];

    char[][] grid = new char[20][];

    int[] points = new int[2];
    int curMov;
    int winner=-1;

    int delay=500;

    BufferedImage tug, gun_h, gun_v, sub_h, sub_v, bg_p0, bg_p1;

    GameWindow() {
        super();
    }

    GameWindow(Controller _con) {
        super();
        
        cur = 0;
        con = _con;
//        board = con.gs.simuBoard;
//        fires = con.gs.simuFire;
//        gun = con.gs.simuGun;
//        cargo = con.gs.simuCargo;
//        tugs = con.gs.simuTug;

//        try {
//            con.gs.wout.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        tm = new Timer();

        tm.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!FixedValues.debug_on)
                    cur++;
                repaint();
            }
        }, 500, delay);

        setBackground(Color.WHITE);


        for (int i = 0; i < grid.length; i++) {
            grid[i] = new char[40];
        }

        try {
            bg_p0 = ImageIO.read(new File("resources" + File.separator + "p0.jpg"));
            bg_p1 = ImageIO.read(new File("resources" + File.separator + "p1.jpg"));
            tug = ImageIO.read(new File("resources" + File.separator + "tug.png"));
            gun_h = ImageIO.read(new File("resources" + File.separator + "gunship_h.png"));
            gun_v = ImageIO.read(new File("resources" + File.separator + "gunship_v.png"));
            sub_h = ImageIO.read(new File("resources" + File.separator + "submarine_h.jpg"));
            sub_v = ImageIO.read(new File("resources" + File.separator + "submarine_v.jpg"));


//            cin = new BufferedReader(new InputStreamReader(new FileInputStream(new File("run.txt"))));
            cin = new Scanner(new InputStreamReader(new FileInputStream(new File("resources" + File.separator + "run.txt"))));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void getNextPoints() {
        if(winner!=-1)return;
//        try {

        curMov = cin.nextInt();
        winner = cin.nextInt();
        if(winner!=-1)return;
        points[0] = cin.nextInt();
        points[1] = cin.nextInt();

//        System.out.println(points[0]+","+points[1]);

        for (int i = 0; i < 4; i++) {


            gposX[i] = cin.nextInt();
            gposY[i] = cin.nextInt();
            incx[3 * i] = cin.nextInt();
            invisible[2*i] =cin.nextInt();
            cposX[i] = cin.nextInt();
            cposY[i] = cin.nextInt();
            incx[3 * i + 1] = cin.nextInt();
            invisible[2*i+1] = cin.nextInt();
            tposX[i] = cin.nextInt();
            tposY[i] = cin.nextInt();
//            System.out.println("i> ("+invisible[2*i]+","+invisible[2*i+1]+")");
        }
        for (int i = 0; i < 20; i++) {
            String s = cin.next();
            grid[i] = s.toCharArray();
//                System.out.println(grid[i]);
        }

        for (int i = 0; i < 10; i++) {
            char[] tmp = grid[i].clone();
            grid[i] = grid[20 - i - 1].clone();
            grid[20 - i - 1] = tmp.clone();
        }

//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        System.out.println("hic");


        getNextPoints();

        Scanner cin2 = new Scanner(new InputStreamReader(System.in));


        if(winner!=-1){
            g.setColor(new Color(0, 0, 0, 227));
            g.drawRect(0, 0, this.getHeight(), this.getWidth());
            g.drawString("Player"+winner+" wins",100,100);
            return;
        }


        if (curMov >= FixedValues.TOT_MOVES-1) {
            g.setColor(new Color(0, 0, 0, 227));
            g.drawRect(0, 0, this.getHeight(), this.getWidth());
            if(points[0]>points[1]){
                g.drawString("Player0: "+points[0],100,100);
                g.drawString("Player1: "+points[1],100,150);
                g.drawString("Player0 wins ",100,200);

            }else if(points[0]<points[1]){
                g.drawString("Player0: "+points[0],100,100);
                g.drawString("Player1: "+points[1],100,150);
                g.drawString("Player1 wins ",100,200);
            }else{
                g.drawString("Both the player have same point, the game is a draw",100,100);
            }
//            g.drawString(con.gs.getWinner(), 100, 100);
            tm.cancel();
            return;
        }


        System.out.println("Show move->"+curMov);

        int h = FixedValues.PLAYER_Y, w = FixedValues.PLAYER_X * 2;
        int a = this.getHeight(), b = this.getWidth()-100;
        h = a / h;
        w = b / w;


//        g.setColor(new Color(255, 255, 255));
//        g.fillRect(0,0,a,this.getWidth());



        g.drawImage(bg_p0, 0, 0, w * FixedValues.PLAYER_X, h * FixedValues.PLAYER_Y, null);
        g.drawImage(bg_p1, w * FixedValues.PLAYER_X, 0, w * FixedValues.PLAYER_X, h * FixedValues.PLAYER_Y, null);


        //draw the grid
        g.setColor(Color.BLUE);
        for(int i=0;i<FixedValues.PLAYER_Y;i++)
            for (int j = 0; j < FixedValues.PLAYER_X * 2; j++) {
                g.drawRect(j*w,i*h,w,h);
            }



//        System.out.println()

        char[][] tmp = grid;
//        Point3D[] fire = fires.get(cur);


        for (int i = 0; i < 2; i++) {
            System.out.println("g>>(" + gposX[i] + "," + gposY[i] + "," + incx[3 * i] + ")");
            System.out.println("c>>(" + cposX[i] + "," + cposY[i] + "," + incx[3 * i + 1] + ")");
            System.out.println("t>>(" + tposX[i] + "," + tposY[i] + "," + incx[3 * i + 2] + ")");
        }
        for (int i = 2; i < 4; i++) {
            System.out.println("g>>(" + gposX[i] + "," + gposY[i] + "," + incx[3 * i] + ") " +
                    "(" + Util.gx2(gposX[i], incx[3 * i]) + "," + Util.gy2(gposY[i], incx[3 * i]) + ")");
            System.out.println("c>>(" + cposX[i] + "," + cposY[i] + "," + incx[3 * i + 1] + ") " +
                    "(" + Util.cx2(cposX[i], incx[3 * i + 1]) + "," + Util.cy2(cposY[i], incx[3 * i + 1]) + ")");
            System.out.println("t>>(" + tposX[i] + "," + tposY[i] + "," + incx[3 * i + 2] + ") " +
                    "(" + Util.tx2(tposX[i]) + "," + Util.ty2(tposY[i]) + ")");
        }


        //draw player 0 ships
        for (int i = 0; i < 2; i++) {
            int x = gposX[i] * w, y = Util.gy(gposY[i], incx[3 * i]) * h;
            if (incx[3 * i] == 1) { //horizontal
                g.drawImage(gun_h, x, y, w * 5, h * 2, null);
                if(invisible[2*i]==1){
                    g.setColor(new Color(36, 120, 246, 128));
                    g.fillRect(x,y,w*5,h*2);
                }
            } else {
                g.drawImage(gun_v, x, y, w * 2, h * 5, null);
                if(invisible[2*i]==1){
                    g.setColor(new Color(36, 120, 246, 128));
                    g.fillRect(x,y,w*2,h*5);
                }
            }
            x = cposX[i] * w;
            y = Util.cy(cposY[i], incx[3 * i + 1]) * h;
            if (incx[3 * i + 1] == 1) { //horizontal
                g.drawImage(sub_h, x, y, w * 3, h * 1, null);
                if(invisible[2*i+1]==1){
                    g.setColor(new Color(36, 120, 246, 128));
                    g.fillRect(x,y,w*3,h*1);
                }
            } else {
                g.drawImage(sub_v, x, y, w, h * 3, null);
                if(invisible[2*i+1]==1){
                    g.setColor(new Color(36, 120, 246, 128));
                    g.fillRect(x,y,w*1,h*3);
                }
            }

            x = tposX[i];
            y = Util.ty(tposY[i]);
            g.drawImage(tug, x * w, y * h, w + 10, h + 10, null);
            if (x >= FixedValues.PLAYER_X) { //if in enemy territory highlight
                g.setColor(new Color(181, 175, 14, 128));
                g.fillRect(x * w, y * h, w, h);
            }

        }
        //draw player 1 ships
        for (int i = 2; i < 4; i++) {


            int x = Util.gx2(gposX[i], incx[3 * i]) * w, y = Util.gy2(gposY[i], incx[3 * i]) * h;
            if (incx[3 * i] == 1) { //horizontal
                g.drawImage(gun_h, x, y, w * 5, h * 2, null);
                if(invisible[2*i]==1){
                    g.setColor(new Color(36, 120, 246, 128));
                    g.fillRect(x,y,w*5,h*2);
                }
            } else {
                g.drawImage(gun_v, x, y, w * 2, h * 5, null);
                if(invisible[2*i]==1){
                    g.setColor(new Color(36, 120, 246, 128));
                    g.fillRect(x,y,w*2,h*5);
                }
            }
            x = Util.cx2(cposX[i], incx[3 * i + 1]) * w;
            y = Util.cy2(cposY[i], incx[3 * i + 1]) * h;
            if (incx[3 * i + 1] == 1) { //horizontal
                g.drawImage(sub_h, x, y, w * 3, h * 1, null);
                if(invisible[2*i]==1){
                    g.setColor(new Color(36, 120, 246, 128));
                    g.fillRect(x,y,w*3,h*1);
                }
            } else {
                g.drawImage(sub_v, x, y, w, h * 3, null);
                if(invisible[2*i+1]==1){
                    g.setColor(new Color(36, 120, 246, 128));
                    g.fillRect(x,y,w*1,h*3);
                }
            }

            x = Util.tx2(tposX[i]);
            y = Util.ty2(tposY[i]);
            g.drawImage(tug, x * w, y * h, w + 8, h + 8, null);
            if (x < FixedValues.PLAYER_X) { //if in enemy territory highlight
                g.setColor(new Color(181, 175, 14, 128));
                g.fillRect(x * w, y * h, w, h);
            }
        }


        for (int i = 0; i < tmp.length; i++) {
            System.out.print("-->");
            System.out.println(tmp[i]);
        }

        for (int i = tmp.length - 1; i >= 0; i--) {
            for (int j = 0; j < tmp[i].length; j++) {
                if (tmp[i][j] == '.') continue;
                int x = j * w, y = i * h;
                switch (tmp[i][j]) {
                    case '#':
                        g.setColor(Color.RED);
                        g.fillRect(x, y, w, h);
                        break;
                    case 'H':
                        g.setColor(new Color(233, 25, 23, 133));
                        g.fillRect(x, y, w, h);
                        break;
                    case 'f':
//                        System.out.println("firee");
                        g.setColor(new Color(255, 161, 4, 147));
                        g.fillRect(x,y,w,h);
                        break;
                }
            }
        }


        //points + move count
        g.setColor(Color.BLACK);
        g.drawString("Move: "+curMov,b,(a/2)-150);
        g.drawString("POINTS",b,(a/2)-100);
        g.drawString("Player0: "+points[0],b,(a/2)-50);
        g.drawString("Player1: "+points[1],b,(a/2));

        //show fires
//        for (int i = 0; i < fire.length; i++) {
//            g.setColor(new Color(255, 161, 4, 147));
//            int x = fire[i].getX(), y = fire[i].getY();
//            System.out.println("fire>>(" + x + "," + y + ")");
//            x *= w;
//            y *= h;
//            g.fillRect(x, y, w, h);
//        }

//        System.out.println(tmp[0]);
//        System.out.println(cur);
        if (FixedValues.debug_on) {
            System.out.print("Press return to continue...");
            cin2.nextLine();
            cur++;
        }
    }


}
