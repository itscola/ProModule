package top.whitecola.promodule.common;

public class Position {
    public float x;
    public float y;
    public float x2;
    public float y2;

    public Position(){}

    public Position(float x,float y,float x2,float y2){
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
    }

    public float getX() {
        return x;
    }

    public Position setX(float x) {
        this.x = x;
        return this;
    }

    public float getY() {
        return y;
    }

    public Position setY(float y) {
        this.y = y;
        return this;
    }

    public float getX2() {
        return x2;
    }

    public Position setX2(float x2) {
        this.x2 = x2;
        return this;
    }

    public float getY2() {
        return y2;
    }

    public Position setY2(float y2) {
        this.y2 = y2;
        return this;
    }
}
