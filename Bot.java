package com.example.bulldog;

import android.graphics.RectF;

public abstract class Bot {
    private static long count = 0;
    private long ID;

    protected int x_pos;
    protected int y_pos;
    protected double angle; // in radians
    protected int speed;
    protected int size;
    protected String type = "Bot";

    private RectF circle;

    Bot(int x_pos, int y_pos, double angle, int speed, int size){
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        changeAngle(angle);
        this.speed = speed;
        this.size = size;

        this.ID = count++;
    }

    public long getID() {return ID;}
    public int[] getPos(){return new int[] {x_pos, y_pos};}
    public double getAngle(){
        //get rid if not needed public
        return angle;
    }
    public int getSpeed(){
        //remove if not needed
        return speed;
    }
    public int getSize(){
        //remove if not needed
        return size;
    }
    public String getType() {return this.type;}

    public RectF getCircle() {return this.circle;}

    protected void changeSpeed(int s) {this.speed = s;}

    protected void changePos(int[] pos){
        this.x_pos = pos[0];
        this.y_pos = pos[1];
    }

    protected void changeAngle(double a){
        while (a >= 2*Math.PI) {a -= 2*Math.PI;}
        while (a < 0) {a += 2*Math.PI;}
        this.angle = a;
    }

    public void updateCircle(){
        this.circle = new RectF(x_pos-size,y_pos-size,x_pos+size,y_pos+size);
    }

    protected int[] nextPos(){
        int[] new_pos = new int[2];
        new_pos[0] = (int)(x_pos + speed * Math.cos(angle));
        new_pos[1] = (int)(y_pos + speed * Math.sin(angle));
        return new_pos;
    }

    protected double nextAngle(int[] wanted_pos){
            return Math.atan2(wanted_pos[1]-y_pos, wanted_pos[0]-x_pos);
    }

    protected boolean collision(Arena a){
        if (a.hitWallBot(this)) {return true;}
        return false;
    }

    protected void randomMove(Arena a){
        int denominator = Arena.randomNumber(1,24);
        int numerator = Arena.randomNumber(0, denominator);
        double angle = (numerator/denominator)*Math.PI;
        angle = angle * Arena.randomNumber(-1, 1);
        changeAngle(this.angle + angle);

        int[] current_pos = this.getPos();
        int current_speed = this.getSpeed();

        this.changePos(this.nextPos());

        while (this.collision(a)){
            this.changePos(current_pos);
            this.changeSpeed(this.getSpeed()-1);

            if (this.getSpeed() <= 0) {
                this.changePos(current_pos);
                break;
            }
            this.changePos(this.nextPos());
        }
        this.changeSpeed(current_speed);
    }

    protected void moveToPosition(Arena a, int[] wanted_pos) {
        this.changeAngle(this.nextAngle(wanted_pos));
        this.move(a);
    }

    protected void move(Arena a){
        int[] current_pos = this.getPos();
        int current_speed = this.getSpeed();

        this.changePos(this.nextPos());

        while (this.collision(a)){
            this.changePos(current_pos);
            this.changeSpeed(this.getSpeed()-1);

            if (this.getSpeed() <= 0) {
                this.changePos(current_pos);
                break;
            }
            this.changePos(this.nextPos());
        }
        this.changeSpeed(current_speed);
    }

    protected void makeMove(Arena a){
        randomMove(a);
    }
}
