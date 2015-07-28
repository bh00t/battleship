package battleship;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created by paagol on 4/17/15.
 */
public class Ship {
    public int x, y;
    boolean destroyed;
    char body[][];
    int length, width;
    int incx;
    int incy;
    int lastInvisible;
    int invisibleCount;
    int hitCount;


    Ship() {
        destroyed = false;
        lastInvisible = -100;
        invisibleCount = 0;
        hitCount = 0;
    }

    boolean canBeInvisible(int curMove){
        if(invisibleCount>=FixedValues.MAX_INVISIBLE) return false;
        return true;
    }

    boolean isInvisible(int curMove) {
//        System.out.println("check invisible last:"+lastInvisible+" cur:"+curMove);
        return lastInvisible + FixedValues.INVISIBLE_DURATION >= curMove;
    }

    boolean isInvisible(int a, int b, int curMove) {

        if (isInvisible(curMove) && a >= x && a < x + width && b >= y && b < y + length)
            return true;
//        System.out.println("invisible is false");
        return false;
    }


    void hit(int px, int py) {
        if (px >= x + width || px < 0 || py < 0 || py >= y + length)
            return;
        px -= x;
        py -= y;
        body[y][x] = 'H';
        hitCount++;
        if (hitCount == length * width)
            destroyed = true;
    }

    boolean isHit(int px, int py) {
        if (destroyed) return false;
//       System.out.println("hitt "+px+" "+py+" "+x+" "+y+" "+length+" "+width);
        if (px >= x + width || px < x || py < y || py >= y + length || body[py - y][px - x] == 'H')
            return false;
        System.out.println("hoise " + (py - y) + "," + (px - x) + " " + length + "," + width);
        body[py - y][px - x] = 'H';
        hitCount++;

        for (int i = 0; i < body.length; i++) {
            System.out.println(body[i]);
        }

        if(hitCount==length*width)destroyed = true;
        return true;
    }


    void writeBody(BufferedWriter cout) throws IOException {
        if (length > width) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < length; j++) {
                    cout.write(body[j][i]);
                }
                cout.write("\n");
            }
        } else {
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < width; j++) {
                    cout.write(body[i][j]);
                }
                cout.write("\n");
            }
        }
    }


    boolean checkCollision(int x,int y){
        return (x>=this.x && x<this.x+width && y>=this.y && y<this.y+length);
    }

}
