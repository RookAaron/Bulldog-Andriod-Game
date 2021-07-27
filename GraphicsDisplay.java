package com.example.bulldog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;

public class GraphicsDisplay extends View{

    private int[] screen_size;
    private ConsoleLevel console;

    private String screen_display = "main menu"; //game, main, level, etc

    private int[] backgrounds = new int[] {2,4,1};
    private int[] bots = new int[] {6,7,8,9};

    boolean hold = false;

    private Paint game_text;
    private Paint menu_text;

    private Rect top_right_btn;
    private Rect middle_1_btn;
    private Rect middle_2_btn;
    private Rect middle_3_btn;
    private Rect middle_4_btn;
    private Rect middle_5_btn;

    private int[] addBots = new int[] {0, 0, 0};


    GraphicsDisplay(int[] screen_size, Context context) {
        super(context);

        this.screen_size = screen_size;
        getBitmap();

        console = new ConsoleLevel(screen_size, 0, getBitmap());

        game_text = setGameText();
        menu_text = setMenuText();

        int left = 10;
        int right = screen_size[0] -10;
        int middle = screen_size[1]/2;
        int h = 50;

        top_right_btn = new Rect(screen_size[0]-60, 20, screen_size[0]-20, 75);
        middle_1_btn = new Rect(left, middle -5*h, right, middle -3*h);
        middle_2_btn = new Rect(left, middle -3*h, right, middle -h);
        middle_3_btn = new Rect(left, middle -h, right, middle +h);
        middle_4_btn = new Rect(left, middle +h, right, middle +3*h);
        middle_5_btn = new Rect(left, middle +3*h, right, middle +5*h);

    }

    private Bitmap[] getBitmap(){
        Bitmap arena = backgroundList(backgrounds[0]);
        Bitmap safe_zone = backgroundList(backgrounds[1]);
        Bitmap target_zone = backgroundList(backgrounds[2]);
        Bitmap player = botList(bots[0]);
        Bitmap standard = botList(bots[1]);
        Bitmap predict = botList(bots[2]);
        Bitmap horo = botList(bots[3]);

        return new Bitmap[] {arena, safe_zone, target_zone, player, standard, predict, horo};
    }

    private Bitmap backgroundList(int n){
        switch(n){
            case 0: return BitmapFactory.decodeResource(getResources(), R.drawable.autumn_leaves);
            case 1: return BitmapFactory.decodeResource(getResources(), R.drawable.bright_grass_tile);
            case 2: return BitmapFactory.decodeResource(getResources(), R.drawable.dark_grass_tile);
            case 3: return BitmapFactory.decodeResource(getResources(), R.drawable.grass_tile);
            case 4: return BitmapFactory.decodeResource(getResources(), R.drawable.gray_grass_tile);
            case 5: return BitmapFactory.decodeResource(getResources(), R.drawable.night_grass_tile);
            default: return backgroundList(0);
        }
    }

    private Bitmap botList(int n){
        switch(n) {
            case 0: return BitmapFactory.decodeResource(getResources(), R.drawable.black_drone);
            case 1: return BitmapFactory.decodeResource(getResources(), R.drawable.white_drone);
            case 2: return BitmapFactory.decodeResource(getResources(), R.drawable.blue_drone);
            case 3: return BitmapFactory.decodeResource(getResources(), R.drawable.green_drone);
            case 4: return BitmapFactory.decodeResource(getResources(), R.drawable.red_drone);
            case 5: return BitmapFactory.decodeResource(getResources(), R.drawable.black_robot);
            case 6: return BitmapFactory.decodeResource(getResources(), R.drawable.white_robot);
            case 7: return BitmapFactory.decodeResource(getResources(), R.drawable.blue_robot);
            case 8: return BitmapFactory.decodeResource(getResources(), R.drawable.green_robot);
            case 9: return BitmapFactory.decodeResource(getResources(), R.drawable.red_robot);
            default: return botList(0);
        }
    }

    private void changeSettings(String obj){
        switch (obj){
            case "player": bots[0]++; break;
            case "standard": bots[1]++; break;
            case "predict": bots[2]++; break;
            case "horizontal": bots[3]++; break;
            case "arena": backgrounds[0]++; break;
            case "safe zone": backgrounds[1]++; break;
            case "target zone": backgrounds[2]++; break;
        }
        for (int i=0; i<4; i++){
            if (i != 3){
                if (backgrounds[i] > 5) {backgrounds[i] = 0;}
            }
            if (bots[i] > 9) {bots[i] = 0;}
        }

        console = new ConsoleLevel(screen_size, 0, getBitmap());
    }

    private Paint setMenuText(){
        Paint text_style = new Paint();
        text_style.setColor(Color.BLACK);
        text_style.setStyle(Paint.Style.FILL);
        text_style.setTextSize(screen_size[0]/10);
        text_style.setTextAlign(Paint.Align.CENTER);
        //text_style.setLetterSpacing(0.01);
        text_style.setFakeBoldText(true);
        return text_style;
    }

    private Paint setGameText(){
        Paint text_style = new Paint();
        text_style.setColor(Color.WHITE);
        text_style.setStyle(Paint.Style.FILL);
        text_style.setTextSize(screen_size[0]/20);
        text_style.setTextAlign(Paint.Align.LEFT);
        //text_style.setLetterSpacing(0.01);
        text_style.setFakeBoldText(true);
        return text_style;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            console.userTouch((int)(event.getRawX()), (int)(event.getRawY()-100));
            hold = true;
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            // add code here if particle behavior changes on finger lift
            //console.userTouch(-1,-1);
            hold = false;
        }

        if (hold) {}
        else if (screen_display == "main menu") {
            if (middle_1_btn.contains((int)event.getX(), (int)event.getY())) {screen_display = "endless";}
            else if (middle_2_btn.contains((int)event.getX(), (int)event.getY())) {screen_display = "select";}
            else if (middle_3_btn.contains((int)event.getX(), (int)event.getY())) {screen_display = "create";}
            else if (middle_4_btn.contains((int)event.getX(), (int)event.getY())) {screen_display = "settings";}
            else if (middle_5_btn.contains((int)event.getX(), (int)event.getY())) {screen_display = "exit";}
        }
        else if (screen_display == "select") {
            if (middle_1_btn.contains((int)event.getX(), (int)event.getY())) {screen_display = "easy";}
            else if (middle_2_btn.contains((int)event.getX(), (int)event.getY())) {screen_display = "intermediate";}
            else if (middle_3_btn.contains((int)event.getX(), (int)event.getY())) {screen_display = "hard";}
            else if (middle_5_btn.contains((int)event.getX(), (int)event.getY())) {screen_display = "main menu";}
        }
        else if (screen_display == "create") {
            if (middle_1_btn.contains((int)event.getX(), (int)event.getY())) {addBots[0] = addBots[0]+1;}
            else if (middle_2_btn.contains((int)event.getX(), (int)event.getY())) {addBots[1] = addBots[1]+1;}
            else if (middle_3_btn.contains((int)event.getX(), (int)event.getY())) {addBots[2] = addBots[2]+1;}
            else if (middle_4_btn.contains((int)event.getX(), (int)event.getY())) {screen_display = "custom";}
            else if (middle_5_btn.contains((int)event.getX(), (int)event.getY())) {addBots = new int[] {0, 0, 0};}
        }
        else if (screen_display == "settings") {
            if (middle_2_btn.contains((int)event.getX(), (int)event.getY())) {screen_display = "backgrounds";}
            else if (middle_3_btn.contains((int)event.getX(), (int)event.getY())) {screen_display = "bots";}
            else if (middle_5_btn.contains((int)event.getX(), (int)event.getY())) {screen_display = "main menu";}
        }
        else if (screen_display == "backgrounds") {
            if (middle_1_btn.contains((int)event.getX(), (int)event.getY())) {changeSettings("arena");}
            else if (middle_2_btn.contains((int)event.getX(), (int)event.getY())) {changeSettings("safe zone");}
            else if (middle_3_btn.contains((int)event.getX(), (int)event.getY())) {changeSettings("target zone");}
            else if (middle_5_btn.contains((int)event.getX(), (int)event.getY())) {screen_display = "settings";}
        }
        else if (screen_display == "bots") {
            if (middle_1_btn.contains((int)event.getX(), (int)event.getY())) {changeSettings("player");}
            else if (middle_2_btn.contains((int)event.getX(), (int)event.getY())) {changeSettings("standard");}
            else if (middle_3_btn.contains((int)event.getX(), (int)event.getY())) {changeSettings("predict");}
            else if (middle_4_btn.contains((int)event.getX(), (int)event.getY())) {changeSettings("horizontal");}
            else if (middle_5_btn.contains((int)event.getX(), (int)event.getY())) {screen_display = "settings";}
        }
        else if (screen_display == "game") {
            if (top_right_btn.contains((int)event.getX(), (int)event.getY())) {screen_display = "pause";}
        }
        else if (screen_display == "win"){
            screen_display = "main menu";
            console.playing = true;
        }
        else if (screen_display == "pause") {
            console.userTouch(-1,-1);
            if (middle_2_btn.contains((int)event.getX(), (int)event.getY())) {screen_display = "game";}
            else if (middle_4_btn.contains((int)event.getX(), (int)event.getY())) {screen_display = "main menu";}
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        if (screen_display == "main menu") {
            console.drawBackground(canvas);
            console.drawRectText("Endless", menu_text, middle_1_btn, canvas);
            console.drawRectText("Difficulty select", menu_text, middle_2_btn, canvas);
            console.drawRectText("Create your own", menu_text, middle_3_btn, canvas);
            console.drawRectText("Settings", menu_text, middle_4_btn, canvas);
            console.drawRectText("Quit", menu_text, middle_5_btn, canvas);
        }

        else if (screen_display == "win") {
            console.drawBackground(canvas);
            console.drawRectText("Well done", menu_text, middle_1_btn, canvas);
            console.drawRectText("You won!", menu_text, middle_2_btn, canvas);
        }

        else if (screen_display == "select") {
            console.drawBackground(canvas);
            console.drawRectText("Easy", menu_text, middle_1_btn, canvas);
            console.drawRectText("Intermediate", menu_text, middle_2_btn, canvas);
            console.drawRectText("Hard", menu_text, middle_3_btn, canvas);
            console.drawRectText("Back", menu_text, middle_5_btn, canvas);
        }

        else if (screen_display == "create") {
            for (int i=0; i<3; i++) {
                if (addBots[i] > 6) {addBots[i] = 10;}
            }
            console.drawBackground(canvas);
            console.drawRectText("Add Standard: " +addBots[0], menu_text, middle_1_btn, canvas);
            console.drawRectText("Add predict: " +addBots[1], menu_text, middle_2_btn, canvas);
            console.drawRectText("Add Horizontal: " +addBots[2], menu_text, middle_3_btn, canvas);
            console.drawRectText("Start", menu_text, middle_4_btn, canvas);
            console.drawRectText("Reset", menu_text, middle_5_btn, canvas);
        }

        else if (screen_display == "settings"){
            console.drawBackground(canvas);
            console.drawScore(game_text, canvas);
            console.drawRectText("Change backgrounds", menu_text, middle_2_btn, canvas);
            console.drawRectText("Change bots", menu_text, middle_3_btn, canvas);
            console.drawRectText("Back", menu_text, middle_5_btn, canvas);
        }

        else if (screen_display == "backgrounds"){
            console.drawBackground(canvas);
            console.drawRectText("Change arena", menu_text, middle_1_btn, canvas);
            console.drawRectText("Change safe zone", menu_text, middle_2_btn, canvas);
            console.drawRectText("Change target zone", menu_text, middle_3_btn, canvas);
            console.drawRectText("Back", menu_text, middle_5_btn, canvas);
        }

        else if (screen_display == "bots"){
            console.drawBackground(canvas);
            canvas.drawBitmap(getBitmap()[3],null,new Rect(0, 50, 100, 150),null);
            canvas.drawBitmap(getBitmap()[4],null,new Rect(100, 50, 200, 150),null);
            canvas.drawBitmap(getBitmap()[5],null,new Rect(200, 50, 300, 150),null);
            canvas.drawBitmap(getBitmap()[6],null,new Rect(300, 50, 400, 150),null);
            console.drawRectText("Change player", menu_text, middle_1_btn, canvas);
            console.drawRectText("Change standard", menu_text, middle_2_btn, canvas);
            console.drawRectText("Change predict", menu_text, middle_3_btn, canvas);
            console.drawRectText("Change horizontal", menu_text, middle_4_btn, canvas);
            console.drawRectText("Back", menu_text, middle_5_btn, canvas);
        }

        else if (screen_display == "game") {
            console.update();
            console.drawBackground(canvas);
            console.drawBots(canvas);
            console.drawScore(game_text, canvas);
            console.drawRectText("||", game_text, top_right_btn, canvas);
        }

        else if (screen_display == "pause") {
            console.drawBackground(canvas);
            console.drawBots(canvas);
            console.drawScore(game_text, canvas);
            console.drawRectText("Resume", menu_text, middle_2_btn, canvas);
            console.drawRectText("Main menu", menu_text, middle_4_btn, canvas);
        }

        else if (screen_display == "custom") {
            screen_display = "game";
            console.randomArena(addBots);
            addBots  = new int[] {0,0,0};
        }

        else if (screen_display == "exit") {
            System.exit(0);
        }

        else {
            int level;
            switch (screen_display){
                case "endless": level = 0; break;
                case "easy": level = 1; break;
                case "intermediate": level = 2; break;
                case "hard": level = 3; break;
                default: level = -1;
            }
            screen_display = "game";
            console = new ConsoleLevel(screen_size, level, getBitmap());
        }

        if (!(console.playing)) {
            if (console.userWin()) {screen_display = "win";}
        }

        invalidate();
    }
}
