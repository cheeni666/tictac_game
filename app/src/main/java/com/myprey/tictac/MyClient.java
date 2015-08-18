package com.myprey.tictac;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Ramanan on 7/20/2015.
 */
public class MyClient {
    Thread m_objThreadClient;
    Socket clientSocket;
    String str;
    String send = "22222";
    String get = "22222";
    int flag = 1;

    public MyClient(String y) {
        str = y;
    }

    public void startListening() {
        m_objThreadClient = new Thread(new Runnable() {
            public void run() {
                while (flag == 1) {
                    try {
                        clientSocket = new Socket(InetAddress.getByName(str), 1500);
                        ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                        oos.writeObject(send);
                        ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                        get = (String) ois.readObject();
                        oos.close();
                        ois.close();
                        clientSocket.close();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }
        });

        m_objThreadClient.start();

    }

    public void csend(String txt) {
        send = txt;
    }

    public String cget() {
        return get;
    }

    public void flagset(int x) {
        flag = x;
    }

}
