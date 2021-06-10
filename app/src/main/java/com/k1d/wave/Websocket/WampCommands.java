package com.k1d.wave.Websocket;

public enum WampCommands
{
    GETUSERINFO(1),
    GETCARLIST(2),
    SENDCARCMD(3),
    GETAGREEMENT(4),
    GETCARHISTORY(5),
    ;


    private final int code;

    WampCommands(int c)
    {
        code = c;
    }

    public static WampCommands getValue(int code_get)
    {
        WampCommands finded = null;
        for( WampCommands key : WampCommands.values() )
        {
            if(key.code == code_get)
            {
                finded  =  key;
            }
        }
        return finded;
    }

    public int getValue() {
        return code;
    }
}
