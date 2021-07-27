package com.example.bulldog;

public class Predict extends Bot {
    Predict(int x_pos, int y_pos, double angle, int size){
        super(x_pos, y_pos, angle, 5, size);
        this.type = "Predict";
    }

    protected int[] playersNextMove(Player p, int step_number){
        int current_speed = p.getSpeed();
        p.changeSpeed(current_speed * step_number);

        int[] pos = p.nextPos();

        p.changeSpeed(current_speed);
        return pos;
    }

    protected void makeMove(Arena a){
        int[] wanted_pos;
        if (a.getPlayer().isVisible()) {wanted_pos = playersNextMove(a.getPlayer(), 35);}
        else {wanted_pos = new int[] {a.getWidth()/2, a.getHeight()/2};}

        moveToPosition(a, wanted_pos);
    }
}
