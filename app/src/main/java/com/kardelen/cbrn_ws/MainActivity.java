package com.kardelen.cbrn_ws;

import android.os.Handler;
import android.os.Looper;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private WebSocketClientExample webSocketClient;

    LinearLayout layoutCbrn;
    ToggleButton cbrnBtn;

    private Timer timer;

    public TextView textChem, textBio, textEod, textNuc, textRad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutCbrn = findViewById(R.id.cbrn_layout);
        cbrnBtn = findViewById(R.id.cbrnBtn);
        textChem = findViewById(R.id.textChem);
        textBio = findViewById(R.id.textBio);
        textEod = findViewById(R.id.textEod);
        textNuc = findViewById(R.id.textNuc);
        textRad = findViewById(R.id.textRad);

        timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(this), 0, 500);

        cbrnBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    try {
                        startWebSocket(buttonView);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    stopWebSocket(buttonView);
                }
            }
        });


        try {
            webSocketClient = new WebSocketClientExample("ws://192.168.1.5/xip/api/device/stream", this);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }


    private class MyTimerTask extends TimerTask {
        private Handler handler = new Handler(Looper.getMainLooper());
        MainActivity mainActivity;

        MyTimerTask (MainActivity mainActivity){
            this.mainActivity = mainActivity;
        }
        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    textChem.setText(webSocketClient.trend+"");
                    if(webSocketClient.trend > 100){
                        Toast.makeText(mainActivity, "TEHLİKE", Toast.LENGTH_SHORT).show();
                    }
                    textEod.setText(webSocketClient.ec_Value+"");
                    textRad.setText(webSocketClient.scCell3RAbsPerBl+"");
                    textNuc.setText(webSocketClient.flow_lpm_std+"");
                    textBio.setText(webSocketClient.mOS2_SensorResistanceAbsPerBl+"");
                }
            });
        }
    }


    public void visibleCBRN(){
        layoutCbrn.setVisibility(View.VISIBLE);
    }

    public void invisibleCBRN(){
        layoutCbrn.setVisibility(View.INVISIBLE);
    }

    public void startWebSocket(View view) throws URISyntaxException {
        if (webSocketClient == null || !webSocketClient.isOpen()) {
            webSocketClient = new WebSocketClientExample("ws://192.168.1.5/xip/api/device/stream", this);
            webSocketClient.connect();
            visibleCBRN();
        } else {
            webSocketClient.close();
        }
    }


    public void stopWebSocket(View view) {
        webSocketClient.close();
        invisibleCBRN();
    }
}
