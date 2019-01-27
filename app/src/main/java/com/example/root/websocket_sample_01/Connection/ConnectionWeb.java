package com.example.root.websocket_sample_01.Connection;

import android.os.Handler;
import android.os.Message;

import com.example.root.websocket_sample_01.Interface.InterfceListener;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class ConnectionWeb {

    public enum ConnectionStatus {
        DICONNECTION_STATUS,
        CONNECTION_STATUS
    }

    private String url;
    private WebSocket mWebSocket;
    private OkHttpClient mOkHttpClient;
    private InterfceListener interfceListener;
    private Handler HandlerMessage;
    private Handler HandlerStatus;

    public class SocketListener extends WebSocketListener {
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            Message m = HandlerStatus.obtainMessage(0 , ConnectionStatus.CONNECTION_STATUS);
            HandlerStatus.sendMessage(m);
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            Message m = HandlerMessage.obtainMessage(0 , "Receiver : "+text);
            HandlerMessage.sendMessage(m);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
            disconnect();
        }
    }

    public ConnectionWeb(String url) {
        mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        this.url = url;
    }

    public void connection(InterfceListener interfceListener) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        mWebSocket = mOkHttpClient.newWebSocket(request, new SocketListener());
        this.interfceListener = interfceListener;
        HandlerMessage = new Handler(msg -> {
            this.interfceListener.onMessage((String) msg.obj);
            return true;
        });
        HandlerStatus = new Handler(msg -> {
            this.interfceListener.onStatus((ConnectionStatus) msg.obj);
            return true;
        });
    }

    public void disconnect() {
        mWebSocket.cancel();
        interfceListener = null;
        HandlerMessage.removeCallbacksAndMessages(null);
        HandlerStatus.removeCallbacksAndMessages(null);
    }

    public void sendMessage(String message) {
        mWebSocket.send(message);
    }
}

