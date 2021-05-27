package com.example.myapplication.Websocket.Wamp;

import org.json.JSONArray;

public interface WampInterface
{
    void OnSubscribed(JSONArray message);
    void onHelloGet(JSONArray message);
    void onChallenge(String key);
    void onEvent(JSONArray message);
    void onErrorEvent(JSONArray message);

    void onDisconnect();
    void onDisconnect(String message);
    void onstartConnect();
    void onConnected();
    void onFailConnect();
    void onError(Exception ex);
    void onError(String message);
    void onMessage(String message);
    void onMessage(byte[] message);
}
