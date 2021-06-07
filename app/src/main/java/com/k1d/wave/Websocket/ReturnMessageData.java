package com.k1d.wave.Websocket;

import java.lang.reflect.Method;

public class ReturnMessageData
{
	public String Send;
	public long key;
	public Method method_call;
	public Object CallObject;
	public Object reciver;
	public boolean isSubscribe = false;

}
