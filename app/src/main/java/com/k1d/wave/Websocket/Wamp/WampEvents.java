package com.k1d.wave.Websocket.Wamp;

public enum WampEvents
{
    HELLO(1),
    WELCOME(2),
    ABORT(3),
    CHALLENGE(4),
    AUTHENTICATE(5),
    GOODBYE (6),
    HEARTBEAT(7),
    ERROR(8),
    PUBLISH1(16),
    PUBLISHED(17),
    SUBSCRIBE(32),
    SUBSCRIBED(33),
    UNSUBSCRIBE(34),
    UNSUBSCRIBED(35),
    EVENT(36),
    CALL(48),
    CANCEL(49),
    RESULT(50),
    REGISTER(64),
    REGISTERED(65),
    UNREGISTER(66),
    UNREGISTERED(67),
    INVOCATION(68),
    INTERRUPT(69),
    YIELD(70);

    private int code;

    private WampEvents(int c)
    {
        code = c;
    }

    public int getValue(){ return code;}

    public static WampEvents getValue(int code_get)
    {
        WampEvents finded = null;
        for( WampEvents key : WampEvents.values() )
        {
            if(key.code == code_get)
            {
                finded  =  key;
            }
        }
        return finded;
    }
}
