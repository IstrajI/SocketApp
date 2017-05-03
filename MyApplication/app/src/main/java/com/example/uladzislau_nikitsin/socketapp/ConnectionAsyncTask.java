package com.example.uladzislau_nikitsin.socketapp;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

public class ConnectionAsyncTask extends AsyncTask<String, String, TCPClient> implements TCPClient.MessageCallback{
    private final String IP = "2.2.2.2";
    private Handler handler;
    private TCPClient client;
    private String message;

    public ConnectionAsyncTask(final Handler handler) {
        this.handler = handler;
    }

    @Override
    protected TCPClient doInBackground(final String... params) {
        Log.d(""+getClass(), "inBackground");
        try {
            this.client = new TCPClient(handler, message, IP, this);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        client.run();

        return null;
    }

    @Override
    protected void onProgressUpdate(String ... values) {
        super.onProgressUpdate(values);
        Log.d(""+getClass(), "onProgressUpdate");
        if (values[0].equals("success")) {
            client.sendMessage(message);
            client.stopClient();
            handler.sendEmptyMessage(MainActivity.SECCESS, 2000);

        } else {
            client.sendMessage("failed");
            handler.sendEmptyMessage(MainActivity.FAILED, 2000);
            client.stopClient();
        }
    }

    @Override
    protected void onPostExecute(TCPClient client) {
        super.onPostExecute(client);
        Log.d(""+getClass(), "onPostExecute");
        if (client!=null && client.isRunning()) {
            client.stopClient();
        }

        handler.sendEmptyMessageDelayed(MainActivity.SENT, 4000);
    }

    @Override
    public void callbackMessageReceiver(final String message) {
        publishProgress(message);
    }
}
