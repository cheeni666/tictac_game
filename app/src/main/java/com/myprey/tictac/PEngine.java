package com.myprey.tictac;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class PEngine extends View {
    Context cont;
    MediaPlayer player,xm,om;
    Canvas ref;
    float p, ax, ay;
    int animex, animey;
    int i, j;//iterators
    int viewcounter = 0, debug = 0;
    float width, height;
    float slope;
    int in, move = 1;
    Paint brush;
    int state = 1, user=4;
    Rect backbound;
    Bitmap wood;
    int[][] brd = new int[3][3];
    int posx, posy;

    int result, gmcmpt = 0, sum, sum1, sum2, sum3, o;

    public PEngine(Context context) {
        super(context);
        cont = context;
    }

    public PEngine(Context context, AttributeSet attrs) {
        super(context, attrs);
        cont = context;
    }

    public PEngine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        cont = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ref = canvas;
        if (debug == 1) return;
        if (viewcounter == 0) {
            width = canvas.getWidth();
            height = canvas.getHeight();
            brush = new Paint();
            backbound = new Rect();
            wood = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(getResources(), R.drawable.wood),
                    (int) width, (int) height, true);
            slope = height * 3 / width / 4;
            player=MediaPlayer.create(cont,R.raw.knock);
            xm=MediaPlayer.create(cont,R.raw.playx);
            om=MediaPlayer.create(cont,R.raw.zoop);
        }

        if (state == 1) {
            staticdisplayboard();
            sum2=0;sum3=0;
            for(i=0;i<3;i++){
                sum=0;sum1=0;
                for(j=0;j<3;j++){
                    if(i==j)sum2+=brd[i][j];
                    if(i+j==2)sum3+=brd[i][j];
                    sum+=brd[i][j];
                    sum1+=brd[j][i];
                }
                if(sum==3*user){
                    gmcmpt=1;
                    result=user;
                }
                else if(sum1==3*user){
                    gmcmpt=1;
                    result=user;
                }
            }
            if(sum2==3*user){
                gmcmpt=1;
                result=user;
            }
            else if(sum3==3*user){
                gmcmpt=1;
                result=user;
            }

        }
        for (i = 0, o = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                if (brd[i][j] == 0) o++;
            }
        }

        if (state == 2) {
            staticdisplayboard();
            brush.setStrokeWidth(15);
            brush.setColor(Color.WHITE);
            brush.setStyle(Paint.Style.STROKE);
            if (p < ax + width / 3) {
                if (in == 4) {
                    ref.drawLine(ax, ay, p, slope * (p - ax) + ay, brush);
                    ref.drawLine(ax, ay + height / 4, p, -slope * (p - ax) + ay + height / 4, brush);
                    p += 10;
                }
                if (in == 1) {
                    ref.drawCircle(ax + width / 6, ay + height / 8, (p - ax) / 2, brush);
                    p += 10;
                }
            } else {
                state = 1;
                if (brd[animex][animey] == 0) {
                    brd[animex][animey] = in;
                    move++;
                }
            }
        }
        if (o == 0) gmcmpt = 1;
        if (gmcmpt == 1 && viewcounter == 1) {
            if (result == 4)
                Toast.makeText(cont, "PLAYER 1 Wins\nTOUCH the board to PLAY again", Toast.LENGTH_LONG).show();
            if (result == 1)
                Toast.makeText(cont, "PLAYER 2 WINS\nTOUCH the board to PLAY again", Toast.LENGTH_LONG).show();
            if (result == 0)
                Toast.makeText(cont, "DRAW match\nTOUCH the board to PLAY again", Toast.LENGTH_LONG).show();
            viewcounter++;
        }
        if (viewcounter == 0) viewcounter++;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX(), y = event.getY();
        int mevent = event.getAction();
        switch (mevent) {
            case MotionEvent.ACTION_DOWN:
                if (state == 1) {
                    if (gmcmpt == 1) {
                        player.start();
                        if (x > width / 2 - backbound.width() / 2 && x < width / 2 + backbound.width() / 2 && y < height - backbound.height() / 2 && y > height - 3*backbound.height()/2) {
                            Intent i = new Intent(cont, Pvp.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtra("BACK", true);
                            cont.startActivity(i);
                            return true;
                        }
                        Intent i = new Intent(cont, Pvp.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("RE", true);
                        cont.startActivity(i);
                    }
                    if (x > width / 2 - backbound.width() / 2 && x < width / 2 + backbound.width() / 2 && y < height - backbound.height() / 2 && y > height - 3*backbound.height()/2) {
                        player.start();
                        Intent i = new Intent(cont, Pvp.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("BACK", true);
                        cont.startActivity(i);
                    }
                    if (x > 0 && x < width && y > 0 && y < 3 * height / 4 && gmcmpt == 0) {
                        if(move%2==1)user=4;
                        else user=1;
                        animex = (int) (x * 3 / width);
                        animey = (int) (y * 4 / height);
                        p = animex * width / 3;
                        ax = p;
                        ay = animey * height / 4;
                        state = 2;
                        in = user;
                        if(user==4)xm.start();
                        if(user==1)om.start();
                    }
                }
                break;
        }
        return true;
    }

    public void staticdisplayboard() {
        brush.setStrokeWidth(4);
        brush.setColor(Color.rgb(150, 111, 51));
        brush.setStyle(Paint.Style.FILL);
        ref.drawRect(0, 0, width, height, brush);
        ref.drawBitmap(wood, 0, 0, null);
        brush.setColor(Color.BLACK);
        brush.setTextSize(height / 6);
        brush.setStyle(Paint.Style.STROKE);
        brush.getTextBounds("MENU", 0, 4, backbound);
        ref.drawText("MENU", width / 2 - backbound.width() / 2, height - backbound.height() / 2, brush);
        brush.setColor(Color.WHITE);
        brush.setStrokeWidth(15);
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                if (brd[i][j] == 1) {
                    ref.drawCircle(i * width / 3 + width / 6, j * height / 4 + height / 8, width / 6 - 10, brush);
                } else if (brd[i][j] == 4) {
                    ref.drawLine(i * width / 3 + 10, j * height / 4 + 10, i * width / 3 + width / 3 - 10, j * height / 4 + height / 4 - 10, brush);
                    ref.drawLine(i * width / 3 + 10, j * height / 4 + height / 4 - 10, i * width / 3 + width / 3 - 10, j * height / 4 + 10, brush);
                }
            }
        }
        brush.setColor(Color.WHITE);
        brush.setStrokeWidth(4);
        ref.drawLine(width / 3, 0, width / 3, 3 * height / 4, brush);
        ref.drawLine(width / 3 * 2, 0, width / 3 * 2, 3 * height / 4, brush);
        ref.drawLine(0, height / 4, width, height / 4, brush);
        ref.drawLine(0, height / 2, width, height / 2, brush);

    }
}