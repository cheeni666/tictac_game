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

public class Engine extends View{
    MediaPlayer player,xm,om;
    Context cont;
    int moveflag;
    Canvas ref;
    float p,ax,ay;
    int animex,animey;
    int i,j;//iterators
    int viewcounter=0,debug=0;
    float width,height;
    float slope;
    int turn,in,move=1;
    Paint brush;
    int state=0,user,cpu;
    Rect yesbound;
    Rect nobound;
    Rect backbound;
    Bitmap wood;
    int[][] brd=new int[3][3];
    int posx,posy,type;
    Random r=new Random();

    int result,ctrdef=0,cnrdef=0,gmcmpt=0,sum,sum1,sum2,sum3,k,l,m,n,o,q=0;

    public Engine(Context context) {
        super(context);
        cont=context;
    }

    public Engine(Context context, AttributeSet attrs) {
        super(context, attrs);
        cont=context;
    }

    public Engine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        cont=context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ref=canvas;
        if(debug==1)return;
        if(viewcounter==0){
            width=canvas.getWidth();
            height=canvas.getHeight();
            brush=new Paint();
            yesbound=new Rect();
            nobound=new Rect();
            backbound=new Rect();
            wood=Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(getResources(),R.drawable.wood),
                    (int)width,(int)height,true);
            slope=height*3/width/4;
            type= r.nextInt(2);
            player=MediaPlayer.create(cont, R.raw.knock);
            xm=MediaPlayer.create(cont, R.raw.playx);
            om=MediaPlayer.create(cont,R.raw.zoop);
        }
        if(state==0){
            canvas.drawBitmap(wood,0,0,null);
            brush.setColor(Color.YELLOW);
            brush.setStyle(Paint.Style.STROKE);
            brush.setTextSize(height / 6);
            brush.getTextBounds("YES", 0, 3, yesbound);
            brush.getTextBounds("NO", 0, 2, nobound);
            canvas.drawText("YES", 0, 2 * height / 5, brush);
            canvas.drawText("NO",2*width/3,2*height/5,brush);
            brush.setTextSize(height / 10);
            brush.setColor(Color.BLUE);
            canvas.drawText("DO U WANT TO", 0, 4 * height / 5, brush);
            canvas.drawText("PLAY FIRST",0,4*height/5+height/10,brush);

        }
        if(state==1){
            staticdisplayboard();
            if(user==1) {
                if (turn == 1) ;
                else if (turn == -1){
                    if(type==0){//ctratk
                        moveflag=0;
                        if (gmcmpt==1)turn*=-1;
                        else if(move==1){
                            animex=1;animey=1;
                            p=animex*width/3;
                            ax=p;
                            ay=animey*height/4;
                            state=2;
                            in=cpu;
                            moveflag=1;
                            if(cpu==3)xm.start();
                            if(cpu==1)om.start();
                        }
                        else if(move==3&&moveflag==0){
                            if(animey+animex==1||animey+animex==3){
                                if(animex!=1){
                                    animex=2-animex;animey=0;
                                    p=animex*width/3;
                                    ax=p;
                                    ay=animey*height/4;
                                    state=2;
                                    in=cpu;
                                    moveflag=1;
                                    if(cpu==3)xm.start();
                                    if(cpu==1)om.start();
                                }
                                else if(animey!=1){
                                    animex=0;animey=2-animey;
                                    p=animex*width/3;
                                    ax=p;
                                    ay=animey*height/4;
                                    state=2;
                                    in=cpu;
                                    moveflag=1;
                                    if(cpu==3)xm.start();
                                    if(cpu==1)om.start();
                                }
                            }
                            else {
                                animex=2-animex;animey=2-animey;
                                p=animex*width/3;
                                ax=p;
                                ay=animey*height/4;
                                state=2;
                                in=cpu;
                                moveflag=1;
                                if(cpu==3)xm.start();
                                if(cpu==1)om.start();
                            }
                        }
                        else{
                            impmov();
                            if(moveflag==0)nilmov();
                            animex=posx;animey=posy;
                            p=animex*width/3;
                            ax=p;
                            ay=animey*height/4;
                            state=2;
                            in=cpu;
                            if(cpu==3)xm.start();
                            if(cpu==1)om.start();
                        }
                    }
                    else if(type==1) {//cnratk
                        moveflag=0;
                        if (gmcmpt==1)state=3;
                        else if(move==1){
                            animex=0;animey=0;
                            p=animex*width/3;
                            ax=p;
                            ay=animey*height/4;
                            state=2;
                            in=cpu;
                            moveflag=1;
                            if(cpu==3)xm.start();
                            if(cpu==1)om.start();
                        }
                        else if(move==3&&moveflag==0) {
                            if(animey==1&&animex==1){
                                animex=2;animey=2;
                                p=animex*width/3;
                                ax=p;
                                ay=animey*height/4;
                                state=2;
                                in=cpu;
                                moveflag=1;
                                if(cpu==3)xm.start();
                                if(cpu==1)om.start();
                            }
                            else if (animex + animey == 2) {
                                    animex = 2 - animex;
                                    animey = 2 - animey;
                                    p = animex * width / 3;
                                    ax = p;
                                    ay = animey * height / 4;
                                    state = 2;
                                    in = cpu;
                                    moveflag = 1;
                                if(cpu==3)xm.start();
                                if(cpu==1)om.start();
                            }

                            else if (animex + animey == 4 || animex + animey == 3 || animex + animey == 1) {
                                    animex = 1;
                                    animey = 1;
                                    p = animex * width / 3;
                                    ax = p;
                                    ay = animey * height / 4;
                                    state = 2;
                                    in = cpu;
                                    moveflag = 1;
                                if(cpu==3)xm.start();
                                if(cpu==1)om.start();
                            }
                        }
                        else{
                            impmov();
                            if(moveflag==0)nilmov();
                            animex=posx;animey=posy;
                            p=animex*width/3;
                            ax=p;
                            ay=animey*height/4;
                            state=2;
                            in=cpu;
                            if(cpu==3)xm.start();
                            if(cpu==1)om.start();
                        }
                    }
                }
            }
            if(user==3) {
                if (turn == 1) ;
                else if (turn == -1){//cnrdef
                    moveflag=0;
                    if (gmcmpt==1)turn*=-1;
                    else if(move==2&&animex==1&&animey==1){
                        animex=0;animey=0;
                        p=animex*width/3;
                        ax=p;
                        ay=animey*height/4;
                        state=2;
                        in=cpu;
                        moveflag=1;
                        if(cpu==3)xm.start();
                        if(cpu==1)om.start();
                    }
                    else if(move==2&&(animex+animey==0||animey+animex==4||animex+animey==2)){
                        animex=1;animey=1;
                        p=animex*width/3;
                        ax=p;
                        ay=animey*height/4;
                        state=2;
                        in=cpu;
                        moveflag=1;
                        if(cpu==3)xm.start();
                        if(cpu==1)om.start();
                    }
                    else if(move==2&&(animex+animey==1||animey+animex==3)){
                        if(animex==1)animex=0;
                        else if(animey==1)animey=0;
                        p=animex*width/3;
                        ax=p;
                        ay=animey*height/4;
                        state=2;
                        in=cpu;
                        moveflag=1;
                        if(cpu==3)xm.start();
                        if(cpu==1)om.start();
                        q=1;
                    }
                    else if(move==4&&brd[1][1]==0&&q==1){
                        impmov();
                        if(moveflag==1){
                            animex=posx;animey=posy;
                            p=animex*width/3;
                            ax=p;
                            ay=animey*height/4;
                            state=2;
                            in=cpu;
                            if(cpu==3)xm.start();
                            if(cpu==1)om.start();
                        }
                        if(moveflag==0){
                            animex=1;animey=1;
                            p=animex*width/3;
                            ax=p;
                            ay=animey*height/4;
                            state=2;
                            in=cpu;
                            moveflag=1;
                            if(cpu==3)xm.start();
                            if(cpu==1)om.start();
                        }
                    }
                    else{
                        impmov();
                        if(moveflag==0)nilmov();
                        animex=posx;animey=posy;
                        p=animex*width/3;
                        ax=p;
                        ay=animey*height/4;
                        state=2;
                        in=cpu;
                        if(cpu==3)xm.start();
                        if(cpu==1)om.start();
                    }
                }
            }
        }
        if(state==-1){
            long time=System.currentTimeMillis()+500;
            Toast.makeText(cont,"LOADING",Toast.LENGTH_SHORT).show();
            while(System.currentTimeMillis()<time);
            state=1;
        }
        if(state==2){
            staticdisplayboard();
            brush.setStrokeWidth(15);
            brush.setColor(Color.WHITE);
            brush.setStyle(Paint.Style.STROKE);
            if(p<ax+ width/3){
                if(in==3){
                    ref.drawLine(ax, ay, p, slope * (p-ax)+ay, brush);
                    ref.drawLine(ax,ay+height/4,p,-slope*(p-ax)+ay+height/4,brush);
                    p+=10;
                }
                if(in==1){
                    ref.drawCircle(ax+width/6,ay+height/8,(p-ax)/2,brush);
                    p+=10;
                }
            }
            else{
                state=1;
                if(brd[animex][animey]==0||turn==-1) {
                    brd[animex][animey] = in;
                    turn *= -1;
                    move++;
                }
            }
        }
        for(i=0,o=0;i<3;i++){
            for(j=0;j<3;j++){
                if(brd[i][j]==0)o++;
            }
        }
        if(o==0)gmcmpt=1;
        if(gmcmpt==1&&viewcounter==1){
            if(result==cpu)Toast.makeText(cont,"YOU LOST\nTOUCH the board to PLAY again",Toast.LENGTH_LONG).show();
            if(result==0)Toast.makeText(cont,"IT IS A DRAW\nTOUCH the board to PLAY again",Toast.LENGTH_LONG).show();
            viewcounter++;
        }
        if(viewcounter==0)viewcounter++;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x=event.getX(),y=event.getY();
        int mevent=event.getAction();
        switch (mevent){
            case MotionEvent.ACTION_DOWN:
                if(state==0){
                    if(x>0&&x<yesbound.width()&&y<2*height/5&&y>2*height/5-yesbound.height()){
                        player.start();
                        state=-1;
                        user=3;
                        cpu=1;
                        turn=1;
                        move=1;
                        //staticdisplayboard();
                    }
                    if(x>2*width/3&&x<2*width/3+nobound.width()&&y<2*height/5&&y>2*height/5-nobound.height()){
                        player.start();
                        state=-1;
                        user=1;
                        cpu=3;
                        turn=-1;
                        move=1;
                        //staticdisplayboard();
                    }
                }
                if(state==1){
                    if(gmcmpt==1){
                        player.start();
                        if(x>width/2-backbound.width()/2&&x<width/2+backbound.width()/2&&y<height-backbound.height()/2&&y>height-3*backbound.height()/2){
                            Intent i=new Intent(cont,Game.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtra("BACK", true);
                            cont.startActivity(i);
                            return true;
                        }
                        Intent i=new Intent(cont,Game.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("RE",true);
                        cont.startActivity(i);
                    }
                    if(x>width/2-backbound.width()/2&&x<width/2+backbound.width()/2&&y<height-backbound.height()/2&&y>height-3*backbound.height()/2){
                        player.start();
                        Intent i=new Intent(cont,Game.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("BACK",true);
                        cont.startActivity(i);
                    }

                    if(x>0&&x<width&&y>0&&y<3*height/4&&turn==1&&gmcmpt==0){
                        animex=(int)(x*3/width);animey=(int)(y*4/height);
                        p=animex*width/3;
                        ax=p;
                        ay=animey*height/4;
                        state=2;
                        in=user;
                        if(user==3)xm.start();
                        if(user==1)om.start();
                        if(move==1){
                            if(animex==animey&&animex+animey==2)ctrdef=1;
                            else cnrdef=1;
                        }
                    }
                }
                break;
        }
        return true;
    }

    public void staticdisplayboard(){
        brush.setStrokeWidth(4);
        brush.setColor(Color.rgb(150,111,51));
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
        for(i=0;i<3;i++){
            for(j=0;j<3;j++){
                if(brd[i][j]==1){
                    ref.drawCircle(i*width/3+width/6,j*height/4+height/8,width/6-10,brush);
                }
                else if (brd[i][j]==3){
                    ref.drawLine(i*width/3+10,j*height/4+10,i*width/3+width/3-10,j*height/4+height/4-10,brush);
                    ref.drawLine(i*width/3+10,j*height/4+height/4-10,i*width/3+width/3-10,j*height/4+10,brush);
                }
            }
        }
        brush.setColor(Color.WHITE);
        brush.setStrokeWidth(4);
        ref.drawLine(width / 3, 0, width / 3, 3 * height / 4, brush);
        ref.drawLine(width/3*2,0,width/3*2,3*height/4,brush);
        ref.drawLine(0,height/4,width,height/4,brush);
        ref.drawLine(0,height/2,width,height/2,brush);

    }
    public void impmov(){
        //win
        sum2=0;sum3=0;
        for(i=0;i<3&&moveflag==0;i++) {
            sum = 0;
            sum1 = 0;
            for (j = 0; j < 3 && moveflag == 0; j++) {
                if (brd[i][j] == 0) {
                    posx = i;
                    posy = j;
                }
                if (brd[j][i] == 0) {
                    ax = j;
                    ay = i;
                }
                if (i == j) {
                    if (brd[i][j] == 0) {
                        k = i;
                        l = j;
                    }
                    sum2 += brd[i][j];
                }
                if (i + j == 2) {
                    if (brd[i][j] == 0) {
                        m = i;
                        n = j;
                    }
                    sum3 += brd[i][j];
                }
                sum += brd[i][j];
                sum1 += brd[j][i];
            }
            if (sum == 2 * cpu&&moveflag==0) {
                //brd[posx][posy] = cpu;
                result = cpu;
                gmcmpt = 1;
                moveflag = 1;
            }
            else if (sum1 == 2 * cpu&&moveflag==0) {
                //brd[(int) ax][(int) ay] = cpu;
                result = cpu;
                gmcmpt = 1;
                posx = (int) ax;
                posy = (int) ay;
                moveflag = 1;
            }
        }
        if(sum2==2*cpu&&moveflag==0){
            //brd[k][l]=cpu;
            result = cpu;
            gmcmpt = 1;
            posx=k;posy=l;
            moveflag=1;
        }
        else if(sum3==2*cpu&&moveflag==0){
            //brd[m][n]=cpu;
            result = cpu;
            gmcmpt = 1;
            posx=m;posy=n;
            moveflag=1;
        }
        //defend
        sum2=0;sum3=0;
        for(i=0;i<3&&moveflag==0;i++) {
            sum = 0;
            sum1 = 0;
            for (j = 0; j < 3 && moveflag == 0; j++) {
                if (brd[i][j] == 0) {
                    posx = i;
                    posy = j;
                }
                if (brd[j][i] == 0) {
                    ax = j;
                    ay = i;
                }
                if (i == j) {
                    if (brd[i][j] == 0) {
                        k = i;
                        l = j;
                    }
                    sum2 += brd[i][j];
                }
                if (i + j == 2) {
                    if (brd[i][j] == 0) {
                        m = i;
                        n = j;
                    }
                    sum3 += brd[i][j];
                }
                sum += brd[i][j];
                sum1 += brd[j][i];
            }
            if (sum == 2 * user&&moveflag==0) {
                //brd[posx][posy] = cpu;
                moveflag = 1;
            }
            else if (sum1 == 2 * user&&moveflag==0) {
                //brd[(int) ax][(int) ay] = cpu;
                posx = (int) ax;
                posy = (int) ay;
                moveflag = 1;
            }
        }
        if(sum2==2*user&&moveflag==0){
            //brd[k][l]=cpu;
            posx=k;posy=l;
            moveflag=1;
        }
        else if(sum3==2*user&&moveflag==0){
            //brd[m][n]=cpu;
            posx=m;posy=n;
            moveflag=1;
        }
    }
    public void nilmov(){
        sum2=0;sum3=0;
        for(i=0;i<3&&moveflag==0;i++){
            sum=0;sum1=0;
            for(j=0;j<3&&moveflag==0;j++){
                if(brd[i][j]==0){
                    posx=i;posy=j;
                }
                if(brd[j][i]==0){
                    ax=j;ay=i;
                }
                if(i==j){
                    if(brd[i][j]==0){
                        k=i;l=j;
                    }
                    sum2+=brd[i][j];
                }
                if(i+j==2){
                    if(brd[i][j]==0){
                        m=i;n=j;
                    }
                    sum3+=brd[i][j];
                }
                sum+=brd[i][j];
                sum1+=brd[j][i];
            }
            if(sum==cpu&&moveflag==0){
                //brd[posx][posy]=cpu;
                moveflag=1;
            }
            else if(sum1==cpu&&moveflag==0){
                //brd[(int)ax][(int)ay]=cpu;
                posx=(int)ax;posy=(int)ay;
                moveflag=1;
            }
        }
        if(sum2==cpu&&moveflag==0){
            //brd[k][l]=cpu;
            posx=k;posy=l;
            moveflag=1;
        }
        else if(sum3==cpu&&moveflag==0){
            //brd[m][n]=cpu;
            posx=m;posy=n;
            moveflag=1;
        }
        for(i=0;i<3&&moveflag==0;i++){
            for(j=0;j<3&&moveflag==0;j++){
                if(brd[i][j]==0){
                        //brd[i][j]=cpu;
                        posx=i;posy=j;
                        moveflag=1;
                }
            }
        }
     }
}
