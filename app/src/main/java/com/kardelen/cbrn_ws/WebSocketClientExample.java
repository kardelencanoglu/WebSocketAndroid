package com.kardelen.cbrn_ws;

import android.widget.Toast;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

public class WebSocketClientExample extends WebSocketClient {

    MainActivity mainActivity;
    public float trend = 0f;
    public float scCell3RAbsPerBl = 0f;
    public float mOS2_SensorResistanceAbsPerBl = 0f;
    public float ec_Value = 0f;
    public float flow_lpm_std = 0f;

    public WebSocketClientExample(String url, MainActivity mainActivity) throws URISyntaxException {
        super(new URI(url));
        this.mainActivity = mainActivity;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Bağlantı açıldı.");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Gelen veri: " + message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(message);

            trend = jsonNode.get("content").get("PumpPwm").asLong();
            System.out.println("Trend value: " + trend);

            scCell3RAbsPerBl = jsonNode.get("content").get("ScCell3RAbsPerBl").asLong();
            System.out.println("scCell3RAbsPerBl value: " + scCell3RAbsPerBl);

            mOS2_SensorResistanceAbsPerBl = jsonNode.get("content").get("MOS2_SensorResistanceAbsPerBl").asLong();
            System.out.println("mOS2_SensorResistanceAbsPerBl value: " + mOS2_SensorResistanceAbsPerBl);

            ec_Value = jsonNode.get("content").get("EC_Value").asLong();
            System.out.println("ec_Value value: " + ec_Value);

            flow_lpm_std = jsonNode.get("content").get("flow_lpm_std").asLong();
            System.out.println("flow_lpm_std value: " + flow_lpm_std);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
