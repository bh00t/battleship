package battleship;

/**
 * Created by paagol on 4/20/15.
 */
public class Move {
    int firex[];
    int firey[];
    int gx[] = new int[2];
    int gy[] = new int[2];
    int cx[] = new int[2];
    int cy[] = new int[2];
    int tx[] = new int[2];
    int ty[] = new int[2];
    int ln[] = new int[4];
    int wd[] = new int[4];
    int missile[] = new int[2];
    boolean valid;
    String[] v;
    Player p;
    int fcount;
    int curMove;
    int hitCount;
    int hitIndex[];

    int[] invisible = new int[4];
    int[] invisibleCount = new int[4];



    Move() {

    }

    Move(Player _p, int _curMove) {
        valid = true;
        p = _p;
        curMove = _curMove;

        gx[0] = p.gun[0].x;
        gx[1] = p.gun[1].x;
        gy[0] = p.gun[0].y;
        gy[1] = p.gun[1].y;
        missile[0] = p.gun[0].missileLastUsed;
        missile[1] = p.gun[1].missileLastUsed;

        cx[0] = p.cargo[0].x;
        cx[1] = p.cargo[1].x;
        cy[0] = p.cargo[0].y;
        cy[1] = p.cargo[1].y;

        tx[0] = p.tug[0].x;
        tx[1] = p.tug[1].x;
        ty[0] = p.tug[0].y;
        ty[1] = p.tug[1].y;


        ln[0] = p.gun[0].length;
        ln[1] = p.gun[1].length;
        ln[2] = p.cargo[0].length;
        ln[3] = p.cargo[1].length;
        wd[0] = p.gun[0].width;
        wd[1] = p.gun[1].width;
        wd[2] = p.cargo[0].width;
        wd[3] = p.cargo[1].width;


        invisible[0] = p.gun[0].lastInvisible;
        invisible[1] = p.gun[1].lastInvisible;

        invisible[2] = p.cargo[0].lastInvisible;
        invisible[3] = p.cargo[1].lastInvisible;


        fcount = 0;
        hitCount = 0;
        hitIndex = new int[5];
    }

    Move(String[] _v, Player _p, int _curMove) {
        valid = false;
        v = _v;
        p = _p;
        curMove = _curMove;

        gx[0] = p.gun[0].x;
        gx[1] = p.gun[1].x;
        gy[0] = p.gun[0].y;
        gy[1] = p.gun[1].y;
        missile[0] = p.gun[0].missileLastUsed;
        missile[1] = p.gun[1].missileLastUsed;

        cx[0] = p.cargo[0].x;
        cx[1] = p.cargo[1].x;
        cy[0] = p.cargo[0].y;
        cy[1] = p.cargo[1].y;

        tx[0] = p.tug[0].x;
        tx[1] = p.tug[1].x;
        ty[0] = p.tug[0].y;
        ty[1] = p.tug[1].y;


        ln[0] = p.gun[0].length;
        ln[1] = p.gun[1].length;
        ln[2] = p.cargo[0].length;
        ln[3] = p.cargo[1].length;
        wd[0] = p.gun[0].width;
        wd[1] = p.gun[1].width;
        wd[2] = p.cargo[0].width;
        wd[3] = p.cargo[1].width;



        invisible[0] = p.gun[0].lastInvisible;
        invisible[1] = p.gun[1].lastInvisible;

        invisible[2] = p.cargo[0].lastInvisible;
        invisible[3] = p.cargo[1].lastInvisible;

        fcount = 0;
        firex = new int[10];
        firey = new int[10];
        hitCount = 0;
        hitIndex = new int[5];
    }


    boolean checkGun(String s, GunShip g, int i) {
//        System.out.println(s+" i->"+i+" "+g.x+" "+g.y);
        if (g.destroyed)
            return true;
        String[] ret = s.split(" ");
        if ((ret[0].equals("M") && ret.length != 2) ||
                (ret[0].equals("F") && ret.length != 3) ||
                (ret[0].equals("B") && ret.length != 3))
            return false;
        if (ret[0].equals("M")) {
            if (ret[1].equals("1")) {
                gx[i] += g.incx;
//                System.out.println(gy[i]+" "+g.incy);
                gy[i] += g.incy;
            } else if (ret[1].equals("-1")) {
                gx[i] -= g.incx;
                gy[i] -= g.incy;
            } else //nothing to do
                return ret[1].equals("0");
        } else if (ret[0].equals("F")) {
            int x = Integer.parseInt(ret[1]);
            int y = Integer.parseInt(ret[2]);
            if (x <= FixedValues.PLAYER_X || x >= FixedValues.PLAYER_X * 2 || y < 0 || y >= FixedValues.PLAYER_Y) {
                return true;
            }
            firex[fcount] = x;
            firey[fcount] = y;
            fcount++;
//            System.out.println("fire gun");
            hitIndex[hitCount++] = fcount;
        } else if (ret[0].equals("B")) {
//            System.out.println("missile1");
//            System.out.println("->" + g.missileLastUsed + "," + FixedValues.MISSILE_INTERVAL + "," + curMove);
            if (g.missileLastUsed + FixedValues.MISSILE_INTERVAL > curMove)
                return true;
            int x = Integer.parseInt(ret[1]);
            int y = Integer.parseInt(ret[2]);
//            System.out.println("missile2");
            ///need more work upon discussion

//            missile[i] = g.missileLastUsed;
            missile[i] = curMove;


            if (x <= FixedValues.PLAYER_X || x >= FixedValues.PLAYER_X * 2 || y < 0 || y >= FixedValues.PLAYER_Y) {
                return true;
            }
//            System.out.println("missile3");
            for (int j = x; j < x + 2; j++) {
                for (int k = y; k < y + 2; k++) {
                    if (j < FixedValues.PLAYER_X)
                        continue;
                    firex[fcount] = j;
                    firey[fcount] = k;
                    fcount++;
                }
            }
//            System.out.println("missile gun");
            hitIndex[hitCount++] = fcount;
        }else if(ret[0].equals("I")){
            System.out.println("invisible");
            if(g.invisibleCount>=FixedValues.MAX_INVISIBLE)return true;

            if(g.isInvisible(curMove))return true;

            System.out.println("gun invisible hoise p:"+p.pid+" j="+i);

            invisible[i] = curMove;
            invisibleCount[i] = 1;
        }

        return true;
    }

    boolean checkCargo(String s, CargoShip c, int i) {
        if (c.destroyed)
            return true;
        String[] ret = s.split(" ");
        if ((ret[0].equals("M") && ret.length != 2) ||
                (ret[0].equals("F") && ret.length != 3) ||
                (ret[0].equals("B") && ret.length != 3))
            return false;
        if (ret[0].equals("M")) {
            if (ret[1].equals("1")) {
                cx[i] += c.incx;
                cy[i] += c.incy;
            } else if (ret[1].equals("-1")) {
                cx[i] -= c.incx;
                cy[i] -= c.incy;
            } else //nothing to do
                return ret[1].equals("0");
        } else if (ret[0].equals("F")) {
            int x = Integer.parseInt(ret[1]);
            int y = Integer.parseInt(ret[2]);
            if (x <= FixedValues.PLAYER_X || x >= FixedValues.PLAYER_X * 2 || y < 0 || y >= FixedValues.PLAYER_Y) {
                return true;
            }
//            System.out.println("fire cargo");
            firex[fcount] = x;
            firey[fcount] = y;
            fcount++;
            hitIndex[hitCount++] = fcount;
        }else if(ret[0].equals("I")){
            System.out.println("invisible");
            if(!c.canBeInvisible(curMove))return true;

            if(c.isInvisible(curMove))return true;

            System.out.println("cargo invisible hoise p:"+p.pid+" j="+i);

            invisible[2+i] = curMove;
            invisibleCount[2+i] = 1;
        }
        return true;
    }

    boolean checkTug(String s, TugBoat t, int i) {
        if (t.destroyed)
            return true;
        if (s.length() != 1) return false;
        switch (s.charAt(0)) {
            case 'L':
                if (t.x < 1)
                    return false;
                tx[i]--;
                break;
            case 'R':
                if (t.x >= FixedValues.PLAYER_X * 2 - 1)
                    return false;
                tx[i]++;
                break;
            case 'U':
                if (t.y >= FixedValues.PLAYER_Y - 1)
                    return false;
                ty[i]++;
                break;
            case 'D':
                if (t.y < 1)
                    return false;
                ty[i]--;
                break;
            case 'S':
                break;
            default:
                return false;
        }

        return true;
    }


    public boolean checkGrid() {

        int[][] tmp = new int[FixedValues.PLAYER_Y][];
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = new int[FixedValues.PLAYER_X * 2];
        }


        char[][] obs = p.con.gs.board.clone();

//        for (int i = 0; i < tmp.length; i++) {
//            for (int j = 0; j < tmp[i].length; j++) {
//                if(obs[i][j]=='#') {
//                    tmp[i][j]++;
//                    System.out.println("-> ("+i+","+j+") " + obs[i][j] + " " + tmp[i][j]);
//                }
//                if(obs[i][j]=='#')tmp[i][j]++;
//            }
//        }

        //check gunships' position
        for (int i = 0; i < 2; i++) {
            for (int j = gy[i]; j < gy[i] + p.gun[i].length; j++) {
                if (j < 0 || j >= FixedValues.PLAYER_Y) return false;
                for (int k = gx[i]; k < gx[i] + p.gun[i].width; k++) {
//                    System.out.println(i+" "+gy[i]+" "+j+" "+k);
                    if (k < 0 || k >= FixedValues.PLAYER_X || tmp[j][k] > 0 || obs[j][k]=='#')
                        return false;
                    tmp[j][k] = 1;
                }
            }
//            System.out.println("gun->" + i);
        }


        //check cargo position
        for (int i = 0; i < 2; i++) {
            for (int j = cy[i]; j < cy[i] + p.cargo[i].length; j++) {
                if (j < 0 || j >= FixedValues.PLAYER_Y) return false;
                for (int k = cx[i]; k < cx[i] + p.cargo[i].width; k++) {
                    if (k < 0 || k >= FixedValues.PLAYER_X || tmp[j][k] > 0 || obs[j][k]=='#')
                        return false;
                    tmp[j][k] = 1;
//                    System.out.println(i+" "+cy[i]+" "+j+" <c> "+k);

                }
            }
//            System.out.println("cargo->" + i);
        }


        //check tugboat position
        for (int i = 0; i < 2; i++) {
            for (int j = ty[i]; j < ty[i] + p.tug[i].length; j++) {

                if (j < 0 || j >= FixedValues.PLAYER_Y) return false;
                for (int k = tx[i]; k < tx[i] + p.tug[i].width; k++) {
//                    System.out.println(j+" <t> "+k+" "+tmp[j][k]);
                    if (k < 0 || tmp[j][k] > 0 || obs[j][k]=='#')
                        return false;
                    tmp[j][k] = 1;

                }
            }
//            System.out.println("tug->" + i);
        }

        //check collision with enemy tugboat
        if(p.con.players[1-p.pid]!=null)
        for (int i = 0; i < 2; i++) {
            if(p.con.players[1-p.pid].tug[i]==null) continue;
            if(p.con.players[1-p.pid].tug[i].x<FixedValues.PLAYER_X) continue;
            int x = Util.x(p.con.players[1-p.pid].tug[i].x);
            int y = Util.y(p.con.players[1-p.pid].tug[i].y);
            System.out.println("enemy tug ("+p.con.players[1-p.pid].tug[i].x+","+p.con.players[1-p.pid].tug[i].y+") ("+
                    x+","+y+") "+tmp[x][y]);
            if(x<FixedValues.PLAYER_X && tmp[y][x]>0)
                return false;
        }


        for (int i = 0; i < 2; i++) {
            int a = Util.x(tx[i]), b = Util.y(ty[i]);
            for (int j = 0; j < 2; j++) {
                if(p.con.players[1-p.pid].tug[j]==null) continue;
                if(p.con.players[1-p.pid].gun[j]==null) continue;
                if(p.con.players[1-p.pid].cargo[j]==null) continue;
                if(p.con.players[1-p.pid].gun[j].checkCollision(a,b)) {
                    tx[i] = p.tug[i].x;
                    ty[i] = p.tug[i].y;
                    break;
                }
                if(p.con.players[1-p.pid].cargo[j].checkCollision(a,b)) {
                    tx[i] = p.tug[i].x;
                    ty[i] = p.tug[i].y;
                    break;
                }
                if(p.con.players[1-p.pid].tug[j].checkCollision(a,b)) {
                    tx[i] = p.tug[i].x;
                    ty[i] = p.tug[i].y;
                    break;
                }
            }

        }

        //check for obstacles
        return p.con.gs.checkObstacle(tmp, p);

    }

    public boolean isValid() {

        boolean ret;
        ret = checkGun(v[0], p.gun[0], 0);
        ret = checkGun(v[1], p.gun[1], 1) || ret;

        ret = checkCargo(v[2], p.cargo[0], 0) || ret;
        ret = checkCargo(v[3], p.cargo[1], 1) || ret;

        ret = checkTug(v[4], p.tug[0], 0) || ret;
        ret = checkTug(v[5], p.tug[1], 1) || ret;
        if (hitCount > 2) hitCount = 2;
        if (hitCount > 0)
            fcount = hitIndex[hitCount - 1];
//        System.out.println(">>" + fcount + " " + hitCount + " " + ret + " " + checkGrid());

        return ret && checkGrid();
    }


    public boolean isValidSetup() {
        return checkGrid();
    }
}
