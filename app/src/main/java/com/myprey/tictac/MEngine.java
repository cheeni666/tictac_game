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
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MEngine extends View {
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
    int state = 0, user=0;
    Rect backbound;
    Bitmap wood;
    int[][] brd = new int[3][3];
    Rect yesbound,nobound;
    String ip;
    int server;
    int setflag=0;
    int turn=0;
    String subnet;
    String mesfromopp="22222";

    int result, gmcmpt = 0, sum, sum1, sum2, sum3, o;

    MyServer serv;
    MyClient clie;
    InetAddress hostip;

    public MEngine(Context context) {
        super(context);
        cont = context;
    }

    public MEngine(Context context, AttributeSet attrs) {
        super(context, attrs);
        cont = context;
    }

    public MEngine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        cont = context;
    }

    public void setter(int x,String y,Context context){
        server=x;
        cont=context;
        ip=y;
        setflag=1;
        WifiManager wifiMgr = (WifiManager) cont.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ipadr = wifiInfo.getIpAddress();
        hostip=intToInetAddress(ipadr);
        subnet = intToIP(ipadr);
        String split[]=subnet.split("\\.");
        subnet=split[0]+"."+split[1]+"."+split[2];
        serv=new MyServer(hostip);
        clie=new MyClient(ip);
    }
    private static String intToIP(int ipAddress) {
        String ret = String.format("%d.%d.%d.%d", (ipAddress & 0xff),
                (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff),
                (ipAddress >> 24 & 0xff));

        return ret;
    }
    public InetAddress intToInetAddress(int hostAddress) {
        InetAddress inetAddress;
        byte[] addressBytes = { (byte)(0xff & hostAddress),
                (byte)(0xff & (hostAddress >> 8)),
                (byte)(0xff & (hostAddress >> 16)),
                (byte)(0xff & (hostAddress >> 24)) };

        try {
            inetAddress = InetAddress.getByAddress(addressBytes);
        } catch(UnknownHostException e) {
            return null;
        }
        return inetAddress;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(setflag==0)return;
        ref = canvas;
        if (debug == 1) return;
        if (viewcounter == 0) {
            if(server==1)serv.startListening();
            if(server==0)clie.startListening();
            width = canvas.getWidth();
            height = canvas.getHeight();
            brush = new Paint();
            yesbound = new Rect();
            nobound = new Rect();
            backbound = new Rect();
            wood = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(getResources(), R.drawable.wood),
                    (int) width, (int) height, true);
            slope = height * 3 / width / 4;
            player=MediaPlayer.create(cont,R.raw.knock);
            xm=MediaPlayer.create(cont,R.raw.playx);
            om=MediaPlayer.create(cont,R.raw.zoop);
            if(server==0){
                turn=1;
                state=-1;
            }
            if(server==1)turn=-1;
        }
        //code to get
        if(server==0&&turn==1){
            mesfromopp=clie.cget();
            parse(mesfromopp);
        }
        if(server==0&&user==0){
            mesfromopp=clie.cget();
            parse(mesfromopp);
        }
        if(server==1&&turn==-1){
            mesfromopp=serv.cget();
            parse(mesfromopp);
        }
        if(state==-1){
            if(user!=0){
                state=1;
                String l=new String();
                if(user==1)l+="O";
                if(user==4)l+="X";
                Toast.makeText(cont,"You are "+l,Toast.LENGTH_SHORT).show();
            }
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
            //other win
            sum2=0;sum3=0;
            for(i=0;i<3;i++){
                sum=0;sum1=0;
                for(j=0;j<3;j++){
                    if(i==j)sum2+=brd[i][j];
                    if(i+j==2)sum3+=brd[i][j];
                    sum+=brd[i][j];
                    sum1+=brd[j][i];
                }
                if(sum==3*(5-user)){
                    gmcmpt=1;
                    result=5-user;
                }
                else if(sum1==3*(5-user)){
                    gmcmpt=1;
                    result=5-user;
                }
            }
            if(sum2==3*(5-user)){
                gmcmpt=1;
                result=5-user;
            }
            else if(sum3==3*(5-user)){
                gmcmpt=1;
                result=5-user;
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
                    turn*=-1;
                    //code to send
                    out();
                }
            }
        }
        if (o == 0) gmcmpt = 1;
        if (gmcmpt == 1 && viewcounter == 1) {
            if (result == 4)
                Toast.makeText(cont, "X Wins\nTOUCH the board to PLAY again", Toast.LENGTH_LONG).show();
            if (result == 1)
                Toast.makeText(cont, "O WINS\nTOUCH the board to PLAY again", Toast.LENGTH_LONG).show();
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
                if(state==0&&server==1){
                    if(x>0&&x<yesbound.width()&&y<2*height/5&&y>2*height/5-yesbound.height()){
                        player.start();
                        state=1;
                        user=4;
                        turn=1;
                        move=1;
                        serv.csend("41000");
                    }
                    if(x>2*width/3&&x<2*width/3+nobound.width()&&y<2*height/5&&y>2*height/5-nobound.height()){
                        player.start();
                        state=1;
                        user=1;
                        turn=-1;
                        move=1;
                        serv.csend("10000");
                    }
                    return true;
                }
                if (state == 1) {
                    if (gmcmpt == 1) {
                        player.start();
                        if (x > width / 2 - backbound.width() / 2 && x < width / 2 + backbound.width() / 2 && y < height - backbound.height() / 2 && y > height - 3*backbound.height()/2) {
                            Intent i = new Intent(cont, Mgame.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtra("BACK", true);
                            if(server==0)clie.flagset(0);
                            if(server==1)serv.flagset(0);
                            cont.startActivity(i);
                            return true;
                        }
                        Intent i = new Intent(cont, Mgame.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("RE", true);
                        if(server==0)clie.flagset(0);
                        if(server==1)serv.flagset(0);
                        cont.startActivity(i);
                    }
                    if (x > width / 2 - backbound.width() / 2 && x < width / 2 + backbound.width() / 2 && y < height - backbound.height() / 2 && y > height - 3*backbound.height()/2) {
                        player.start();
                        Intent i = new Intent(cont, Mgame.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra("BACK", true);
                        if(server==0)clie.flagset(0);
                        if(server==1)serv.flagset(0);
                        cont.startActivity(i);
                    }
                    if (x > 0 && x < width && y > 0 && y < 3 * height / 4 && gmcmpt == 0&&server==1&&turn==1) {
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
                    if (x > 0 && x < width && y > 0 && y < 3 * height / 4 && gmcmpt == 0&&server==0&&turn==-1) {
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
    public void parse(String txt){
        if(server==0){
            if ((txt.charAt(0)=='1'||txt.charAt(0)=='4')&&user==0){
                user=txt.charAt(0)-48;
                user=5-user;
                if(txt.charAt(1)=='0')turn=-1;
                if(txt.charAt(1)=='1')turn=1;
            }
            else if(user!=0&&txt.charAt(1)=='0'&&txt.charAt(4)!='0'){
                animex=txt.charAt(2)-48;
                animey=txt.charAt(3)-48;
                if(brd[animex][animey]==0){
                    turn=-1;
                    brd[animex][animey]=5-user;
                    if(5-user==4)xm.start();
                    if(5-user==1)om.start();
                }
            }
        }
        if(server==1){
            if (txt.charAt(1)=='1'){
                animex=txt.charAt(2)-48;
                animey=txt.charAt(3)-48;
                if(brd[animex][animey]==0){
                    turn=1;
                    brd[animex][animey]=5-user;
                    if(5-user==4)xm.start();
                    if(5-user==1)om.start();
                }
            }
        }
    }
    public void out(){
        int f=0;
        String t=new String();
        if(server==1){
            if(user==1)f=1;
            if(user==4)f=4;
            t+=f;
        }
        else t+=mesfromopp.charAt(0);
        if(turn==-1)t+=0;
        if(turn==1)t+=1;
        t+=animex;
        t+=animey;
        t+=1;
        if(server==1)serv.csend(t);
        if(server==0)clie.csend(t);
    }
}