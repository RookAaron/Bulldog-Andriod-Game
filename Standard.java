package com.example.bulldog;

public class Standard extends Bot {
    Standard(int x_pos, int y_pos, double angle, int size){
        super(x_pos, y_pos, angle, 6, size);
        this.type = "Standard";
    }

    protected void chasePlayer(Arena a) {
        int[] wanted_pos = a.getPlayer().getPos();
        moveToPosition(a, wanted_pos);
    }

    protected void makeMove(Arena a){
        if (a.getPlayer().isVisible()) {chasePlayer(a);}
        else {randomMove(a);}
    }
}
