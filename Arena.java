package com.example.bulldog;

import java.util.ArrayList;
import java.util.Random;

public class Arena {

    private int width;
    private int height;
    private int safe_zone_height;
    private int bot_sizes;
    private ArrayList<Bot> Bots;
    protected Player player;

    public boolean game_over = false;
    private int target;

    Arena(int width, int height, int safe_zone_height, int bot_sizes, int target){
        this.width = width;
        this. height = height;
        this.safe_zone_height = safe_zone_height;
        this.bot_sizes = bot_sizes;
        this.target = target;

        this.Bots = new ArrayList<Bot>();
        this.player = new Player(width/2, height-safe_zone_height+bot_sizes, Math.PI/2, bot_sizes);
    }

    public int getLengths() {return player.getLengths();}
    public int getTarget() {return this.target;}
    public boolean isGameOver() {return this.game_over;}
    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public int getSafe_zone_height() {return safe_zone_height;}

    public Player getPlayer() {return player;}
    public ArrayList<Bot> getBots() {return Bots;}

    public void addStandardBot(int x_pos, int y_pos){
        Standard bot = new Standard(x_pos, y_pos, 3*Math.PI/2, bot_sizes);
        Bots.add(bot);
    }
    public void addHoroBot(int x_pos, int y_pos){
        Horo bot = new Horo(x_pos, y_pos, 3*Math.PI/2, bot_sizes);
        Bots.add(bot);
    }
    public void addPredictBot(int x_pos, int y_pos){
        Predict bot = new Predict(x_pos, y_pos, 3*Math.PI/2, bot_sizes);
        Bots.add(bot);
    }

    protected boolean placedOnBot(int x, int y) {
        int[] pos = new int[] {x,y};
        for (Bot b:Bots){
            if (bot_sizes*2 > pythagoras(pos, b.getPos())) {return true;}
        }
        return false;
    }

    private boolean hitWall(Bot bot){
        int[] pos = bot.nextPos();
        if (pos[0] < bot_sizes || pos[0] > width-bot_sizes) {return true;}
        if (pos[1] < safe_zone_height+bot_sizes || pos[1] > height-safe_zone_height-bot_sizes) {return true;}
        return false;
    }

    private boolean hitBot(Bot bot){
        int[] pos = bot.nextPos();
        int[] test_pos;
        for(Bot test_bot:Bots) {
            if (bot.getID() == test_bot.getID()) {continue;}

            test_pos = test_bot.getPos();

            if (bot_sizes*2 > pythagoras(pos, test_pos)) {return true;}
        }
        return false;
    }

    public boolean hitWallBot(Bot bot){
        return (this.hitBot(bot) || this.hitWall(bot));
    }

    private boolean playerCaught(){return this.hitBot(player);}

    public void changePlayerPointer(int x, int y){player.changePointer(x, y);}

    private void moveAllBots() {

        for (Bot bot : Bots) {
            bot.makeMove(this);
            bot.updateCircle();
        }

        player.updateCircle();
        player.makeMove(this);
    }

    public void updateArena(){
        moveAllBots();
        if (playerCaught()) {game_over = true;}
        if (player.getLengths() == target) {game_over = true;}
    }

    public void resetArena() {
        game_over = false;
        player.changePos(new int[] {width/2, height-safe_zone_height+bot_sizes});
        if (!(player.isTarget_zone())) {player.changeTarget_zone();}
        player.changePointer(-1, -1);
        player.setLengths(0);
    }

    public static double pythagoras(int[] pos1, int[] pos2) {
        return Math.sqrt( Math.pow(pos1[0]-pos2[0],2) + Math.pow(pos1[1]-pos2[1],2) );
    }

    public static int randomNumber(int min, int max) {
        /* includes the min and max value */
        Random randGen = new Random();

        int addValue = 0;
        if (min < 0) {addValue = -min;}
        min = min+addValue; max = max+addValue;

        int n = randGen.nextInt(max+1-min)+min-addValue;
        return n;
    }
}
