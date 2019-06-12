//Raul Cicos W1547867
package cw_algo;

import java.util.ArrayList;

/**
 *
 * @author w1547867
 */
public class Spot {

    private int x;
    private int y;
    private int g = 0;
    private double h = 0;
    private double f = 0;
    private int weight;
    private boolean start = false; // if its the starting point
    private boolean finish = false; // if its the goal point
    private boolean path = false; // if its part of the path 
    private boolean openSet = false; // if its in the open set
    private boolean closedSet = false; // if its in the closed set
    private boolean wall = false; // if its a wall

    private ArrayList<Spot> neighbours = new ArrayList<>(); // the neighbours around it
    private Spot previous = null; // what was the previous point it came from in thepath
    private final int SIZE = 20;

    public Spot getPrevious() {
        return previous;
    }

    public void setPrevious(Spot previous) {
        this.previous = previous;
    }

    public Spot(int x, int y, int weight) {
        this.x = x;
        this.y = y;
        this.weight = weight;

    }

    public boolean isWall() {
        return wall;
    }

    public void setWall(boolean wall) {
        this.wall = wall;
    }

    public boolean isOpenSet() {
        return openSet;
    }

    public void setOpenSet(boolean openSet) {
        this.openSet = openSet;
    }

    public boolean isClosedSet() {
        return closedSet;
    }

    public void setClosedSet(boolean closedSet) {
        this.closedSet = closedSet;
    }

    public ArrayList<Spot> getNeighbours() {
        return neighbours;
    }

    public void addNeighbours(Spot[][] spots) { // gets all the spots around it horizontally, vertically and diagonally
        int x = this.x;
        int y = this.y;
        if (x < SIZE - 1) {
            neighbours.add(spots[x + 1][y]);
        }
        if (x > 0) {
            neighbours.add(spots[x - 1][y]);
        }
        if (y < SIZE - 1) {
            neighbours.add(spots[x][y + 1]);
        }
        if (y > 0) {
            neighbours.add(spots[x][y - 1]);
        }

        //diagonals 
        if ((x > 0) && (y > 0)) {
            neighbours.add(spots[x - 1][y - 1]);
        }
        if ((x < SIZE - 1) && (y > 0)) {
            neighbours.add(spots[x + 1][y - 1]);
        }
        if ((x > 0) && (y < SIZE - 1)) {
            neighbours.add(spots[x - 1][y + 1]);
        }
        if ((x < SIZE - 1) && (y < SIZE - 1)) {
            neighbours.add(spots[x + 1][y + 1]);
        }

    }

    public boolean isPath() {
        return path;
    }

    public void setPath(boolean path) {
        this.path = path;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean setStart) {
        this.start = setStart;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean setFinish) {
        this.finish = setFinish;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int w) {
        this.weight = w;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }
}
