package com.example.root.websocket_sample_01.Interface;

import com.example.root.websocket_sample_01.Connection.ConnectionWeb;

public interface InterfceListener {
    void onMessage(String message);
    void onStatus(ConnectionWeb.ConnectionStatus status);
}
