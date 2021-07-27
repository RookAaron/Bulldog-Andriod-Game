package com.example.bulldog;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class ConsoleLevel {

    public Bitmap[] console_images; // main background, safe zone, target zone, player, standard, predict, horo

    private int level;
    protected Arena arena;
    private int[] screen_size;
    private int safe_zone_height;
    private int bot_sizes;

    protected boolean playing;

    ConsoleLevel(int[] screen_size, int level, Bitmap[] console_images) {

        this.console_images = console_images;

        this.bot_sizes = screen_size[1] / 42;
        this.safe_zone_height = (screen_size[1] - (int) (screen_size[1] / 1.25)) / 2;

        playing = true;

        this.level = level;
        this.screen_size = screen_size;
        setArena();
    }

    public void changeImages(Bitmap[] img){
        this.console_images = img;
    }

    private void setArena() {
        switch (level) {
            case 0:
                this.arena = new Arena(screen_size[0], screen_size[1], safe_zone_height, bot_sizes, -1);
                arena.addStandardBot(50, screen_size[1] / 2);
                arena.addPredictBot(screen_size[0] / 2, screen_size[1] / 2);
                arena.addHoroBot(screen_size[0] - 50, screen_size[1] / 2);
                break;
            case 1:
                this.arena = new Arena(screen_size[0], screen_size[1], safe_zone_height, bot_sizes, 2);
                arena.addStandardBot(50, screen_size[1] / 2);
                arena.addPredictBot(screen_size[0] / 2, screen_size[1] / 2);
                arena.addStandardBot(screen_size[0] - 50, screen_size[1] / 2);
                break;
            case 2:
                this.arena = new Arena(screen_size[0], screen_size[1], safe_zone_height, bot_sizes, 6);
                arena.addStandardBot(50, screen_size[1] / 2);
                arena.addPredictBot(screen_size[0] / 2, screen_size[1] / 2);
                arena.addHoroBot(screen_size[0] - 50, screen_size[1] / 2);
            case 3:
                this.arena = new Arena(screen_size[0], screen_size[1], safe_zone_height, bot_sizes, 6);
                arena.addStandardBot(50, screen_size[1] / 2);
                arena.addPredictBot(screen_size[0] / 2, screen_size[1] / 2);
                arena.addHoroBot(screen_size[0] - 50, screen_size[1] / 2);
                arena.addHoroBot(screen_size[0] - 50, screen_size[1] / 2 + bot_sizes*2);
                arena.addHoroBot(50, screen_size[1] / 2 + bot_sizes*2);
            default:
                this.level = 0;
                setArena();
        }
    }

    public void randomArena(int[] bots) {
        this.arena = new Arena(screen_size[0], screen_size[1], safe_zone_height, bot_sizes, -1);

        int x = -1;
        int y = 0;
        for (int i=0; i<3; i++) {
            for (int j=0; j<bots[i]; j++) {
                for (int c=0; c<5; c++) {
                    x = Arena.randomNumber(safe_zone_height + bot_sizes, screen_size[0] - safe_zone_height - bot_sizes);
                    y = Arena.randomNumber(safe_zone_height + bot_sizes, screen_size[1] - safe_zone_height - bot_sizes);
                    if (!(arena.placedOnBot(x, y))) {break;}
                    x = -1;
                }

                if (x != -1) {
                    switch (i) {
                        case 0:
                            arena.addStandardBot(x, y);
                            break;
                        case 1:
                            arena.addPredictBot(x, y);
                            break;
                        case 2:
                            arena.addHoroBot(x, y);
                            break;
                    }
                }
            }
        }
    }

    public void userTouch(int x, int y) {
        arena.changePlayerPointer(x, y);
    }

    protected boolean userWin(){
        if (arena.getPlayer().getLengths() == arena.getTarget()) {return true;}
        return false;
    }

    public void update() {
        if (playing) {
            arena.updateArena();
            playing = !(arena.game_over);
        } else {
            arena.resetArena();
            playing = true;
        }
    }

    public void drawBackground(Canvas canvas) {
        int[] safe_zone_target = new int[]{1, 2};
        if (arena.getPlayer().isTarget_zone()) {
            safe_zone_target[0] = 2;
            safe_zone_target[1] = 1;
        }

        canvas.drawBitmap(
                console_images[0],
                null,
                new Rect(0, safe_zone_height, screen_size[0], screen_size[1] - safe_zone_height),
                null
        );

        canvas.drawBitmap(
                console_images[safe_zone_target[0]],
                null,
                new Rect(0, 0, screen_size[0], safe_zone_height),
                null
        );

        canvas.drawBitmap(
                console_images[safe_zone_target[1]],
                null,
                new Rect(0, screen_size[1] - safe_zone_height, screen_size[0], screen_size[1]),
                null
        );
    }

    public void drawBots(Canvas canvas) {

        int index = 3;
        for (Bot bot : arena.getBots()) {
            if (bot.getType() == "Standard") index = 4;
            else if (bot.getType() == "Predict") index = 5;
            else if (bot.getType() == "Horo") index = 6;
            canvas.drawBitmap(
                    console_images[index],
                    null,
                    bot.getCircle(),
                    null
            );
        }

        //Player player = arena.getPlayer();
        //canvas.drawOval(player.getCircle(), arena.setColour(player));
        canvas.drawBitmap(
                console_images[3],
                null,
                arena.getPlayer().getCircle(),
                null
        );
    }

    public void drawScore(Paint text_style, Canvas canvas) {
        String text = "Score: " + arena.getLengths();
        if (!(arena.getTarget() == -1)) {
            text = text + " / " + arena.getTarget();
        }
        canvas.drawText(text, 20, 50, text_style);
    }

    public void drawText(String text, Paint text_style, Rect pos, Canvas canvas) {

        int x_pos = pos.left + (int) text_style.measureText(text) / 2;
        int y_pos = pos.top + (int) text_style.getTextSize();

        //canvas.drawRect(300-w, 300 - text_size, 300 + w, 300, null);
        canvas.drawText(text, x_pos, y_pos, text_style); //x=300,y=300
    }

    public void drawRectText(String text, Paint text_style, Rect r, Canvas canvas) {
        int width = r.width();

        int numOfChars = text_style.breakText(text, true, width, null);
        int start = (text.length() - numOfChars) / 2;
        canvas.drawText(text, start, start + numOfChars, r.exactCenterX(), r.exactCenterY(), text_style);
    }
}
