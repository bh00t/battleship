/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

/**
 * @author paagol
 */
public class CargoShip extends Ship {
    CargoShip() {
    }

    public CargoShip(CargoShip g) {
        this.x = g.x;
        this.y = g.y;
        this.width = g.width;
        this.length = g.length;
        this.incy = g.incy;
        this.incx = g.incx;
        this.body = g.body.clone();
    }

    CargoShip(char dir, int _x, int _y) {
        set(dir, _x, _y);
        body = new char[length][];
//        System.out.println(width + " " + length);
        for (int i = 0; i < body.length; i++) {
            body[i] = new char[width];
            for (int j = 0; j < body[i].length; j++) {
                body[i][j] = 'C';
            }
//            System.out.println(body[i]);
        }
    }

    CargoShip(String s) {
        String[] v = s.split(" ");
        set(v[0].charAt(0), Integer.parseInt(v[1]), Integer.parseInt(v[2]));
        body = new char[length][];
//        System.out.println(width + " " + length);
        for (int i = 0; i < body.length; i++) {
            body[i] = new char[width];
            for (int j = 0; j < body[i].length; j++) {
                body[i][j] = 'C';
            }
//            System.out.println(body[i]);
        }
    }

    void set(char dir, int _x, int _y) {
        if (dir == 'h') {
            incx = 1;
            incy = 0;
            width = FixedValues.CARGO_LENGTH;
            length = FixedValues.CARGO_WIDTH;
        } else {
            incx = 0;
            incy = 1;
            width = FixedValues.CARGO_WIDTH;
            length = FixedValues.CARGO_LENGTH;
        }
        x = _x;
        y = _y;
    }

}
