package com.k1d.wave.Websocket;


import com.k1d.wave.Websocket.Wamp.WampEvents;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.Random;

public class WampJsonMessage 
{
	Random rnd;
	long key;
	
	public WampJsonMessage()
	{
		rnd = new Random( );
		key = 0;
		
		key = (System.currentTimeMillis()  + 1)* (1+rnd.nextInt(99)) + (1+rnd.nextInt(99)) *  (1+ rnd.nextInt(99) + System.currentTimeMillis());
		key = key + 1+ rnd.nextInt(99);
		key = key / 2;
		if(key < 0)
		{
			key = (System.currentTimeMillis()  + 1)* (1+rnd.nextInt(99)) + (1+rnd.nextInt(99)) *  (1+ rnd.nextInt(99) + System.currentTimeMillis());
		}
	}
		
	public JSONArray callTicket(String object_id)
	{
		
		 
		
		
		JSONArray arr_message = new JSONArray();
		arr_message.put(48);
		arr_message.put(key);
		
		
		JSONObject obj_disclose = new JSONObject();
		try
		{
		obj_disclose.put("disclose_me", true);
		}
		catch(Exception exz)
		{
			
		}
		
		arr_message.put(obj_disclose);
		arr_message.put("com.tickets.get");		
		JSONArray arr_objects =  new JSONArray();
		arr_objects.put(object_id);		
		arr_message.put(arr_objects);
					
		return arr_message;
	}
	
	
	public ReturnMessageData sendMessageChat(String realm, String message)
	{
		ReturnMessageData answer = new ReturnMessageData();
		
		JSONArray arr_send = new JSONArray();
		arr_send.put(48);
		arr_send.put(key);
		JSONObject obj_disclose = new JSONObject();
		try
		{
		obj_disclose.put("disclose_me", true);
		}
		catch(Exception exz)
		{
			
		}
		arr_send.put(obj_disclose);
		
		arr_send.put(realm);
		
		JSONArray obj_empty = new JSONArray();
		
		try
		{
		JSONObject mess_ob = new JSONObject();
		mess_ob.put("message", message);
		
		obj_empty.put( mess_ob);
		}
		catch(Exception ex)
		{
			
		}
		arr_send.put(obj_empty);
		
		answer.Send = arr_send.toString();
		answer.key = key;
		return answer; 
	}
	
	public ReturnMessageData callDictonary(String dict_name, JSONObject dict_params, Object reciver, Method InvokeMethod)
	{
	
		ReturnMessageData answer = new ReturnMessageData();
		
		JSONArray arr_send = new JSONArray();
		arr_send.put(48);
		arr_send.put(key);
		JSONObject obj_disclose = new JSONObject();
		try
		{
		obj_disclose.put("disclose_me", true);
		}
		catch(Exception exz)
		{
			
		}
		arr_send.put(obj_disclose);
		
		arr_send.put(dict_name);
		
		JSONArray obj_empty = new JSONArray();
			obj_empty.put(dict_params);		
		arr_send.put(obj_empty);
		
				
		answer.Send = arr_send.toString();
		answer.key = key;
		answer.reciver = reciver;
		answer.method_call = InvokeMethod;
		return answer; 
		
	}


	public static String callFunction(int id, String dict_name, JSONArray dict_params)
	{
		JSONArray arr_send = new JSONArray();
		arr_send.put(WampEvents.CALL.getValue());
		arr_send.put(id);

		JSONObject obj_disclose = new JSONObject();
		try	{obj_disclose.put("disclose_me", true);}
		catch(Exception exz) {exz.printStackTrace();}
		arr_send.put(obj_disclose);

		arr_send.put(dict_name);

		JSONArray obj_empty = new JSONArray();
		if(dict_params != null) {
			obj_empty.put(dict_params);
		}
		arr_send.put(obj_empty);

		return arr_send.toString();
	}



	public ReturnMessageData callDictonary(String dict_name, JSONArray dict_params, Object reciver, Method InvokeMethod)
	{

		ReturnMessageData answer = new ReturnMessageData();

		JSONArray arr_send = new JSONArray();
		arr_send.put(48);
		arr_send.put(key);
		JSONObject obj_disclose = new JSONObject();
		try
		{
			obj_disclose.put("disclose_me", true);
		}
		catch(Exception exz)
		{

		}
		arr_send.put(obj_disclose);

		arr_send.put(dict_name);

		JSONArray obj_empty = dict_params;
		arr_send.put(obj_empty);

		answer.Send = arr_send.toString();
		answer.key = key;
		answer.reciver = reciver;
		answer.method_call = InvokeMethod;
		return answer;

	}


	public ReturnMessageData callDictonary(String dict_name, String dict_params, Object reciver, Method InvokeMethod)
	{
	
		ReturnMessageData answer = new ReturnMessageData();
		
		JSONArray arr_send = new JSONArray();
		arr_send.put(48);
		arr_send.put(key);
		JSONObject obj_disclose = new JSONObject();
		try
		{
		obj_disclose.put("disclose_me", true);
		}
		catch(Exception exz)
		{
			
		}
		arr_send.put(obj_disclose);
		
		arr_send.put(dict_name);
		
		JSONArray obj_empty = new JSONArray();
		obj_empty.put(dict_params);		
		arr_send.put(obj_empty);
		
		answer.Send = arr_send.toString();
		answer.key = key;
		answer.reciver = reciver;
		answer.method_call = InvokeMethod;
		return answer; 
		
	}

	public ReturnMessageData callDictonary(String dict_name, String dict_params, Object reciver, Method InvokeMethod, String object_id)
	{

		ReturnMessageData answer = new ReturnMessageData();

		JSONArray arr_send = new JSONArray();
		arr_send.put(48);
		arr_send.put(key);
		JSONObject obj_disclose = new JSONObject();
		try
		{
			obj_disclose.put("disclose_me", true);
		}
		catch(Exception exz)
		{

		}
		arr_send.put(obj_disclose);

		arr_send.put(dict_name);

		JSONArray obj_empty = new JSONArray();
		obj_empty.put(dict_params);
		obj_empty.put(object_id);
		arr_send.put(obj_empty);

		answer.Send = arr_send.toString();
		answer.key = key;
		answer.reciver = reciver;
		answer.method_call = InvokeMethod;
		return answer;

	}



	public ReturnMessageData callDictonary(String dict_name, String dict_params, Object reciver, Method InvokeMethod, long object_id)
	{

		ReturnMessageData answer = new ReturnMessageData();

		JSONArray arr_send = new JSONArray();
		arr_send.put(48);
		arr_send.put(key);
		JSONObject obj_disclose = new JSONObject();
		try
		{
			obj_disclose.put("disclose_me", true);
		}
		catch(Exception exz)
		{

		}
		arr_send.put(obj_disclose);

		arr_send.put(dict_name);

		JSONArray obj_empty = new JSONArray();
		obj_empty.put(dict_params);
		obj_empty.put(object_id);
		arr_send.put(obj_empty);

		answer.Send = arr_send.toString();
		answer.key = key;
		answer.reciver = reciver;
		answer.method_call = InvokeMethod;
		return answer;

	}
	
	
	public ReturnMessageData subscribe(String dict_name, String dict_params, Object reciver, Method InvokeMethod, int subcount)
	{
	
		ReturnMessageData answer = new ReturnMessageData();
		
		JSONArray arr_send = new JSONArray();
		arr_send.put(32);
		arr_send.put(subcount);
		JSONObject obj_disclose = new JSONObject();
		try
		{
		obj_disclose.put("disclose_me", true);
		}
		catch(Exception exz)
		{
			
		}
		arr_send.put(obj_disclose);		
		arr_send.put(dict_name+"."+dict_params);		
		
		answer.Send = arr_send.toString();
		answer.key = subcount;
		answer.method_call = InvokeMethod;
		answer.reciver = reciver;
		answer.isSubscribe = true;
		return answer; 
		
	}

	
	public ReturnMessageData callDictonary(String dict_name, Method method_name, Object reciver)
	{
	
		ReturnMessageData answer = new ReturnMessageData();
		
		JSONArray arr_send = new JSONArray();
		arr_send.put(48);
		arr_send.put(key);
		JSONObject obj_disclose = new JSONObject();
		try
		{
			obj_disclose.put("disclose_me", true);
		}
		catch(Exception exz)
		{
			
		}
		arr_send.put(obj_disclose);
		
		arr_send.put(dict_name);
		
		JSONArray obj_empty = new JSONArray();
		
		arr_send.put(obj_empty);
		
		answer.Send = arr_send.toString();
		answer.key = key;
		answer.method_call = method_name;
		answer.reciver = reciver;
		return answer; 
		
	}
	
	public String AuthMessage(String user_name, String AuthFunction )
	{		
		JSONObject auth_message = new JSONObject();
		JSONArray arr_auth = new JSONArray();
		String rrr = "";
		try
		{
			arr_auth.put(1);
			arr_auth.put(AuthFunction);
			JSONObject roles = new JSONObject();
			
			JSONObject caller = new JSONObject();
			JSONObject caller_features = new JSONObject();
			caller_features.put("caller_identification", true);
			caller_features.put("progressive_call_results", true);
			caller.put("features", caller_features);
			roles.put("caller", caller);
			
			
			
			JSONObject callee = new JSONObject();
			JSONObject callee_features = new JSONObject();
			callee_features.put("progressive_call_results", true);
			callee.put("features", callee_features);
			roles.put("callee", callee);
			
			
			JSONObject publisher = new JSONObject();
			JSONObject publisher_features = new JSONObject();
			publisher_features.put("subscriber_blackwhite_listing", true);
			publisher_features.put("publisher_exclusion", true);
			publisher_features.put("publisher_identification", true);
			publisher.put("features", publisher_features);
			roles.put("publisher", publisher);
			
			
			JSONObject subscriber = new JSONObject();
			JSONObject subscriber_features = new JSONObject();
			subscriber_features.put("publisher_identification", true);
			subscriber.put("features", subscriber_features);
			roles.put("subscriber", subscriber);						
			auth_message.put("roles",roles);			
			JSONArray arr_methods = new JSONArray();
			arr_methods.put("wampcra");			
			auth_message.put("authmethods",arr_methods);
			auth_message.put("authid", user_name);
			//auth_message.put("push_token", MyavoFirebaseInstantServiceID.getToken());


			
			arr_auth.put(auth_message);
		    rrr = arr_auth.toString();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		
		
		
		
		return rrr;
	}

}

