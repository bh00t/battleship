package battleship;

/**
 * Created by paagol on 4/20/15.
 */
public class Util {
    static int min(int x, int y) {
        if (x > y) return y;
        return x;
    }

    static int max(int x, int y) {
        if (x > y) return x;
        return y;
    }

    static int x(int a, Player p) {
        if (p.pid == 0) return a;
        return FixedValues.PLAYER_X * 2 - 1 - a;
    }

    static int y(int b, Player p) {
        if (p.pid == 0) return b;
        return FixedValues.PLAYER_Y - 1 - b;
    }

    static int x(int a) {
        return FixedValues.PLAYER_X * 2 - 1 - a;
    }

    static int y(int b) {
        return FixedValues.PLAYER_Y - 1 - b;
    }

    static Point3D point(Point3D p) {
        return new Point3D(x(p.getX()), y(p.getY()), p.getZ());
    }

    public static Point3D[] points(Point3D[] fire) {
        Point3D[] ret = new Point3D[fire.length];
        for (int i = 0; i < fire.length; i++) {
            ret[i] = new Point3D(x(fire[i].getX()), y(fire[i].getY()), fire[i].getZ());
        }
        return ret;
    }


    static int gy(int x, int incx) {
        if (incx == 0)
            return FixedValues.PLAYER_Y - x - FixedValues.GUNSHIP_LENGTH;
        return FixedValues.PLAYER_Y - x - FixedValues.GUNSHIP_WIDHT;
    }

    static int cy(int x, int incx) {
        if (incx == 0) return FixedValues.PLAYER_Y - x - FixedValues.CARGO_LENGTH;
        return FixedValues.PLAYER_Y - x - FixedValues.CARGO_WIDTH;
    }

    static int ty(int x) {
        return FixedValues.PLAYER_Y - x - FixedValues.TUG_LENGTH;
    }

    static int gx2(int x, int incx) {
        if (incx == 0)
            return Util.x(x) - FixedValues.GUNSHIP_WIDHT + 1;
        else
            return Util.x(x) - FixedValues.GUNSHIP_LENGTH + 1;
    }

    static int cx2(int x, int incx) {
        if (incx == 0)
            return Util.x(x) - FixedValues.CARGO_WIDTH + 1;
        else
            return Util.x(x) - FixedValues.CARGO_LENGTH + 1;
    }

    static int tx2(int x) {
        return Util.x(x);
    }

    static int gy2(int x, int incx) {
        return x;
//        return Util.y(x);
//        if(incx==0)
//            return FixedValues.PLAYER_Y-x-FixedValues.GUNSHIP_LENGTH+1;
//        return FixedValues.PLAYER_Y-x-FixedValues.GUNSHIP_WIDHT;
    }

    static int cy2(int x, int incx) {
        return x;
//        return Util.y(x);
//        if(incx==0) return FixedValues.PLAYER_Y-x-FixedValues.CARGO_LENGTH;
//        return FixedValues.PLAYER_Y-x-FixedValues.CARGO_WIDTH;
    }

    static int ty2(int x) {
        return x;
//        return Util.y(x);
//        return FixedValues.PLAYER_Y-x-FixedValues.TUG_LENGTH;
    }
}


class Point3D {
    private int x, y, z;

    Point3D() {
        x = y = z = 0;
    }

    Point3D(int _x, int _y, int _z) {
        x = _x;
        y = _y;
        z = _z;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getZ() {
        return z;
    }

}