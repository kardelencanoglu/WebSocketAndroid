package com.kardelen.cbrn_ws;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketClientExample extends WebSocketClient {

    public WebSocketClientExample(String url) throws URISyntaxException {
        super(new URI(url));
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Bağlantı açıldı.");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Gelen veri: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Bağlantı kapatıldı.");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }
}
