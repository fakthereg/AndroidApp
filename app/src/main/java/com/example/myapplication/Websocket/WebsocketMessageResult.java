package com.example.myapplication.Websocket;

import org.json.JSONArray;

public interface WebsocketMessageResult
{
    void onResult(JSONArray result);
    void onError(String message);

}
