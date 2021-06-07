package com.k1d.wave.Websocket;

import org.json.JSONArray;

public interface WebsocketMessageResult
{
    void onResult(JSONArray result);
    void onError(String message);

}
