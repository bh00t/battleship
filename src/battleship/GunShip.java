/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

/**
 * @author paagol
 */
public class GunShip extends Ship {
    int missileLastUsed = -100;

    GunShip() {

    }

    public GunShip(GunShip g) {
        this.x = g.x;
        this.y = g.y;
        this.width = g.width;
        this.length = g.length;
        this.incy = g.incy;
        this.incx = g.incx;
        this.body = g.body.clone();
    }

    GunShip(char dir, int _x, int _y) {
        set(dir, _x, _y);
        body = new char[length][];
        for (int i = 0; i < body.length; i++) {
            body[i] = new char[width];
            for (int j = 0; j < body[i].length; j++) {
                body[i][j] = 'G';
            }
//            System.out.println(body[i]);
        }
    }

    GunShip(String s) {
        String[] v = s.split(" ");
        set(v[0].charAt(0), Integer.parseInt(v[1]), Integer.parseInt(v[2]));
        body = new char[length][];
        for (int i = 0; i < body.length; i++) {
            body[i] = new char[width];
            for (int j = 0; j < body[i].length; j++) {
                body[i][j] = 'G';
            }
//            System.out.println(body[i]);
        }
//        System.out.println(s+" <gun> "+x+" "+y);
    }


    void set(char dir, int _x, int _y) {
        if (dir == 'h') {
            incx = 1;
            incy = 0;
            width = FixedValues.GUNSHIP_LENGTH;
            length = FixedValues.GUNSHIP_WIDHT;
        } else {
            incx = 0;
            incy = 1;
            width = FixedValues.GUNSHIP_WIDHT;
            length = FixedValues.GUNSHIP_LENGTH;
        }
        x = _x;
        y = _y;
    }
}
