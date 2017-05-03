package com.example.uladzislau_nikitsin.socketapp;

import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {

    private final Handler handler;
    private final String message;
    private final String ipNumber;
    private String command;

    private BufferedReader reader;
    private PrintWriter writer;

    private boolean run = false;

    private final MessageCallback listener;

    public TCPClient(final Handler handler, final String message, final String ipNumber, final MessageCallback listener) {
        this.handler = handler;
        this.message = message;
        this.ipNumber = ipNumber;
        this.listener = listener;
    }

    public void sendMessage(final String message) {
        if (writer != null && writer.checkError()) {
            writer.println(message);
            writer.flush();
            handler.sendEmptyMessageDelayed(MainActivity.SENDING, 2000);
            Log.d(""+getClass(), "sendMessage");
        }
    }

    public interface MessageCallback {
        void callbackMessageReceiver(String message);
    }

    public void stopClient() {
        run = false;
    }

    public void run() {
        run = true;

        try {
            InetAddress address = InetAddress.getByName(ipNumber);
            Log.d(""+getClass(), "connectiong");
            handler.sendMessageDelayed(MainActivity.CONNECTING, 2000);
            Socket socket;
            try {
                socket = new Socket(address, 4444);

                try {
                    writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                    reader = new BufferedReader(new InputStreamReader())

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }



            }



        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

}
