package com.example.myapplication.Websocket.Wamp;

import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class WampParser
{

    private WampInterface callbackevents;
    private String session_key;
    private String authid;
    private String authrole;
    private String secret;

    public WampParser(WampInterface callback, String login, String password)
    {
        this.authid = login;
        this.secret = password;
        callbackevents = callback;
    }

    public void ParseJson(final String value)
    {
        new Thread(new Runnable() {
            public void run()
            {
                try
                {
                    JSONArray arr = new JSONArray(value);
                    WampEvents cmd_state = WampEvents.getValue( arr.getInt(0) );
                    session_key = arr.getString(1);
                    switch(cmd_state)
                    {
                        case WELCOME:
                        {
                            JSONObject arr_in = arr.getJSONObject(2);
                            authid = arr_in.getString("authid");
                            authrole = arr_in.getString("authrole");
                            callbackevents.onHelloGet(arr);
                            break;
                        }

                        case CHALLENGE:
                        {

                            JSONObject arr_in = arr.getJSONObject(2);
                            String challenge = arr_in.getString("challenge");

                            String salt = arr_in.getString("salt");
                            int keylen = arr_in.getInt("keylen");
                            int iterations =  arr_in.getInt("iterations");

                            try
                            {
                                String secret_key_256 = computeHash(keylen,secret.getBytes("UTF-8"),salt.getBytes("UTF-8"),iterations);
                                Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
                                SecretKeySpec secretKey = new SecretKeySpec(secret_key_256.getBytes("UTF-8"), "HmacSHA256");

                                sha256_HMAC.init( secretKey);
                                byte[] hash = sha256_HMAC.doFinal(challenge.getBytes("UTF-8"));
                                String tesa  = 	    Base64.encodeToString(hash, Base64.NO_WRAP);
                                callbackevents.onChallenge(tesa);
                            }
                            catch(Exception ex)
                            {
                                callbackevents.onErrorEvent(arr);
                            }
                            break;
                        }

                        case EVENT:
                        {
                            //Log.d("WAMP","EVENT");
                            callbackevents.onEvent(arr);
                            break;
                        }
                        case INVOCATION:
                        {
                            //Log.d("WAMP","INVOCATION");
                            callbackevents.onEvent(arr);
                            break;
                        }
                        case ERROR:
                        {
                           // Log.d("WAMP","ERROR");
                            callbackevents.onErrorEvent(arr);
                            break;
                        }
                        case RESULT:
                        {
                            //Log.d("WAMP","RESULT");
                            callbackevents.onEvent(arr);
                            break;
                        }
                        case SUBSCRIBED:
                        {
                            callbackevents.OnSubscribed(arr);
                            break;
                        }
                        case ABORT:
                        {
                            callbackevents.onErrorEvent(arr);
                            break;
                        }
                        default:
                        {
                            callbackevents.onEvent(arr);
                            break;
                        }
                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    String computeHash(int dklen, byte[] password, byte[] salt, int iterationCount)
    {
        try
        {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(password, "HmacSHA256");
            sha256_HMAC.init(secretKey);
            int has_size = 256;

            int hashLength = has_size / 8;
            if ((has_size & 7) != 0)
            {
                hashLength++;
            }
            int keyLength = dklen / hashLength;
            if ((long)dklen > (0xFFFFFFFFL * hashLength) || dklen < 0)
            {
                throw new Exception("exception");
            }
            if (dklen % hashLength != 0)
            {
                keyLength++;
            }
            byte[] extendedkey = new byte[salt.length + 4];
            System.arraycopy( salt, 0, extendedkey, 0, salt.length);

            byte[] dk = null;
            for (int i = 0; i < keyLength; i++)
            {
                extendedkey[salt.length] = (byte)(((i + 1) >> 24) & 0xFF);
                extendedkey[salt.length + 1] = (byte)(((i + 1) >> 16) & 0xFF);
                extendedkey[salt.length + 2] = (byte)(((i + 1) >> 8) & 0xFF);
                extendedkey[salt.length + 3] = (byte)(((i + 1)) & 0xFF);

                byte[] u = sha256_HMAC.doFinal(extendedkey);

                byte[] f = u;
                for (int j = 1; j < iterationCount; j++)
                {
                    u = sha256_HMAC.doFinal(u);
                    for (int k = 0; k < f.length; k++)
                    {
                        f[k] ^= u[k];
                    }
                }
                dk = f;
            }

            String tesa  = Base64.encodeToString(dk, Base64.NO_WRAP);

            return tesa;
        }
        catch(Exception ex)
        {
            return "";
        }
    }


}
