package common;

public class Point {
    private int x;
    private int y;
    private int z;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    public Point(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public long distance(Point a) {
        return (this.x - a.x) * (this.x - a.x) + (this.y - a.y) * (this.y - a.y) + (this.z - a.z) * (this.z - a.z);
    }
    
    public long findHorizontalRelative(Point a){
        return Math.abs(this.x - a.x)+1;
    }
    public long findVerticalRelative(Point a){
        return Math.abs(this.y - a.y)+1;
    }
    
    public String toString() {
        return "(" + Integer.toString(this.x) + "," + Integer.toString(this.y) + "," + Integer.toString(this.z) + ")";
    }

    public int compare(Point a) {
        if(this.x != a.x)
            return Integer.compare(this.x, a.x);
        if(this.y != a.y)
            return Integer.compare(this.y, a.y);
        return Integer.compare(this.z, a.z);
    }
}
