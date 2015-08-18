package com.myprey.tictac;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class MyServer {
    Thread m_objThread;
    ServerSocket m_server;
    Socket connectedSocket;
    InetAddress x;
    String text="22222";
    String strMessage="22222";
    String ipsend;
    int flag=1;

    public MyServer(InetAddress y) {
        x = y;
    }

    public void startListening() {
        m_objThread = new Thread(new Runnable() {
            public void run() {

                while(flag==1){
                    try {
                        m_server = new ServerSocket(1500, 10, x);
                        connectedSocket = m_server.accept();
                        ObjectInputStream ois = new ObjectInputStream(connectedSocket.getInputStream());
                        strMessage = (String) ois.readObject();
                        ObjectOutputStream oos = new ObjectOutputStream(connectedSocket.getOutputStream());
                        ipsend=connectedSocket.toString();
                        oos.writeObject(text);
                        ois.close();
                        oos.close();
                        m_server.close();
                    } catch (Exception e) {}
                }
            }
        });

        m_objThread.start();

    }
    public void csend(String txt) {
        text=txt;
    }
    public String cget(){
        return strMessage;
    }
    public void flagset(int x){
        flag=x;
    }
    public String ipget(){
        return ipsend;
    }
}
