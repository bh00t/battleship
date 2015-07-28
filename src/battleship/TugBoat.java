/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

/**
 * @author paagol
 */
public class TugBoat extends Ship {
    TugBoat() {
    }

    TugBoat(TugBoat t) {
        this.x = t.x;
        this.y = t.y;
    }

    TugBoat(int _x, int _y) {
        set(_x, _y);
        body = new char[length][];
        for (int i = 0; i < body.length; i++) {
            body[i] = new char[width];
            for (int j = 0; j < body[i].length; j++) {
                body[i][j] = 'T';
            }
//            System.out.println(body[i]);
        }
    }

    TugBoat(String s) {
        String[] v = s.split(" ");
        set(Integer.parseInt(v[0]), Integer.parseInt(v[1]));
        body = new char[length][];
        for (int i = 0; i < body.length; i++) {
            body[i] = new char[width];
            for (int j = 0; j < body[i].length; j++) {
                body[i][j] = 'T';
            }
//            System.out.println(body[i]);
        }
    }

    void set(int _x, int _y) {
        length = 1;
        width = 1;
        x = _x;
        y = _y;
    }

}
