package com.kardelen.cbrn_ws;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    private WebSocketClientExample webSocketClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            webSocketClient = new WebSocketClientExample("ws://10.42.0.1/xip/api/device/stream");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void startWebSocket(View view) {
        webSocketClient.connect();
    }

    public void stopWebSocket(View view) {
        webSocketClient.close();
    }
}
