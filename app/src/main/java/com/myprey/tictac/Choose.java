package com.myprey.tictac;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Pattern;


public class Choose extends ActionBarActivity {
    EditText ip;
    static String g="Enter host ip";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        ip=(EditText)findViewById(R.id.editText);
        WifiManager wifiMgr = (WifiManager)getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        ip.setText(g);
    }
    public void join(View view){
        if(validIP(ip.getText().toString())){
            Intent i=new Intent(this,Mgame.class);
            i.putExtra("server",0);
            g=ip.getText().toString();
            i.putExtra("ipaddr", ip.getText().toString());
            ip.setText("");
            finish();
            startActivity(i);
        }
    }
    public void host(View view){
        Intent i=new Intent(this,Mgame.class);
        i.putExtra("server",1);
        finish();
        startActivity(i);
    }
    public static boolean validIP (String ip) {
        try {
            if (ip == null || ip.isEmpty()) {
                return false;
            }

            String[] parts = ip.split( "\\." );
            if ( parts.length != 4 ) {
                return false;
            }

            for ( String s : parts ) {
                int i = Integer.parseInt( s );
                if ( (i < 0) || (i > 255) ) {
                    return false;
                }
            }
            if(ip.endsWith(".")) {
                return false;
            }

            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
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
    private static String intToIP(int ipAddress) {
        String ret = String.format("%d.%d.%d.%d", (ipAddress & 0xff),
                (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff),
                (ipAddress >> 24 & 0xff));

        return ret;
    }

}
