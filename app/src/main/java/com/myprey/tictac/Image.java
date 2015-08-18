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

public class Image extends View {
    int viewcounter=0;
    MediaPlayer player;
    float width,height;
    Bitmap back;
    Paint brush;
    Context cont;
    Rect bounds1,bounds2,bounds3,bounds4;
    public Image(Context context) {
        super(context);
        cont=context;
    }

    public Image(Context context, AttributeSet attrs) {
        super(context, attrs);
        cont=context;
    }

    public Image(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        cont=context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(viewcounter==0){
            width=canvas.getWidth();
            height=canvas.getHeight();
            back=Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(getResources(), R.drawable.wood),
                    (int) width, (int) height, true
            );
            brush=new Paint();
            bounds1=new Rect();
            bounds2=new Rect();
            bounds3=new Rect();
            bounds4=new Rect();
            player=MediaPlayer.create(cont,R.raw.knock);
        }
        canvas.drawBitmap(back,0,0,null);
        brush.setColor(Color.BLACK);
        brush.setStyle(Paint.Style.STROKE);
        brush.setTextSize(height / 10 );
        canvas.drawText("TicTacToe", 0, height/5, brush);
        brush.setColor(Color.LTGRAY);
        brush.setTextSize(height / 10);
        brush.getTextBounds("vsCPU", 0, 5, bounds1);
        canvas.drawText("vsCPU", width / 2 - bounds1.width() / 2, 2 * height / 5, brush);
        brush.getTextBounds("PvsP",0,4,bounds2);
        canvas.drawText("PvsP",width/2-bounds2.width()/2,3*height/5,brush);
        brush.getTextBounds("EXIT",0,4,bounds3);
        canvas.drawText("EXIT",width/2-bounds3.width()/2,4*height/5,brush);
        brush.getTextBounds("MULTIPLAY",0,9,bounds4);
        canvas.drawText("MULTIPLAY",width/2-bounds4.width()/2,height,brush);
        if(viewcounter==0)viewcounter++;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x=event.getX();
        float y=event.getY();
        int mevent=event.getAction();
        switch(mevent){
            case MotionEvent.ACTION_DOWN:
                if(x>width/2 - bounds1.width()/2&&x<width/2+bounds1.width()&&y<2*height/5&&y>2*height/5-bounds1.height()){
                    player.start();
                    Intent i=new Intent(cont,MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("vsCPU",true);
                    cont.startActivity(i);
                }
                if(x>width/2 - bounds2.width()/2&&x<width/2+bounds2.width()&&y<3*height/5&&y>3*height/5-bounds2.height()){
                    player.start();
                    Intent i=new Intent(cont,MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("PvP",true);
                    cont.startActivity(i);
                }
                if(x>width/2 - bounds3.width()/2&&x<width/2+bounds3.width()&&y<4*height/5&&y>4*height/5-bounds3.height()){
                    player.start();
                    Intent i=new Intent(cont,MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("EXIT",true);
                    cont.startActivity(i);
                }
                if(x>width/2 - bounds4.width()/2&&x<width/2+bounds4.width()&&y<height&&y>height-bounds4.height()){
                    player.start();
                    Intent i=new Intent(cont,MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("M",true);
                    cont.startActivity(i);
                }
        }
        return true;
    }
}
