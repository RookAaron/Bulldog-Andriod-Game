package com.example.bulldog;

public class Horo extends Bot {

    Horo(int x_pos, int y_pos, double angle, int size){
        super(x_pos, y_pos, angle, 4, size);
        this.type = "Horo";
    }

    protected void makeMove(Arena a){
        int current_speed = this.getSpeed();
        Player p = a.getPlayer();

        if (p.isVisible()){
            if (x_pos+size < p.getPos()[0]-p.getSize()) {this.changeAngle(0);}
            else if (x_pos-size > p.getPos()[0]+p.getSize()) {this.changeAngle(Math.PI);}
            else {
                this.changeAngle(Math.atan2(p.getPos()[1]-y_pos, p.getPos()[0]-x_pos));
                this.changeSpeed((int)(current_speed*1.2+1));
            }
        }
        else {
            this.changeSpeed((int)(current_speed*1.2+1));
            if (x_pos+size < a.getWidth()/2) {this.changeAngle(0);}
            else if (x_pos-size > a.getWidth()/2) {this.changeAngle(Math.PI);}
            else {this.changeSpeed(0);}
        }
        this.move(a);

        this.changeSpeed(current_speed);
    }
}
