package com.example.bulldog;

public class Player extends Bot {

    private int lengths = 0;
    private boolean visible = true;
    private boolean target_zone = true;

    private int[] target = new int[] {-1, -1};

    Player(int x_pos, int y_pos, double angle, int size){
        super(x_pos, y_pos, angle, 8, size);
        this.type = "Player";
    }

    public int getLengths() {return this.lengths;}
    public int[] getTarget() {return this.target;}
    public boolean isVisible() {return this.visible;}
    public boolean isTarget_zone() {return target_zone;}
    public void changeTarget_zone() {this.target_zone = ! target_zone;}
    public void setLengths(int l) {lengths = l;}
    public void changeVisible(Arena a) {
        if (this.getPos()[1] <= a.getSafe_zone_height() - this.getSize()) {this.visible = false;}
        else if (this.getPos()[1] >= a.getHeight() - a.getSafe_zone_height() + this.getSize()) {this.visible = false;}
        else {this.visible = true;}
    }
    public void changePointer(int x, int y) {this.target = new int[] {x, y};}

    private void updateLengths(Arena a){
        if (target_zone){
            if (y_pos <= a.getSafe_zone_height()) {
                this.target_zone = false;
                this.lengths++;
            }
        }
        else {
            if(y_pos >= a.getHeight()-a.getSafe_zone_height()) {
                this.target_zone = true;
                this.lengths++;
            }
        }
    }

    protected void makeMove(Arena a){
        this.changeAngle(this.nextAngle(target));
        if (!(target[0] == -1 && target[1] == -1)) {
            if (Arena.pythagoras(this.getPos(), target) > this.getSize() / 2) {
                this.changePos(this.nextPos());
            }
        }

        changeVisible(a);
        updateLengths(a);
    }
}
