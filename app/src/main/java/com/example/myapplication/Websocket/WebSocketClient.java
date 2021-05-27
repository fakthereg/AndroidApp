package com.example.myapplication.Websocket;

import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Base64;


import com.example.myapplication.Websocket.Wamp.WampInterface;
import com.example.myapplication.Websocket.Wamp.WampParser;

import org.json.JSONArray;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class WebSocketClient implements WampInterface
{
    private static final String TAG = "WebSocketClient";
    private static WebSocketClient instance;
    private static WampParser parserdata;
    private static WampInterface callback;
    private URI mURI;
    private Socket mSocket;
    private Thread mThread;
    private HandlerThread mHandlerThread;
    private Handler mHandler;
  //  private List<BasicNameValuePair> mExtraHeaders;
    private HybiParser mParser;
    private String login;
    private String password;
    private final Object mSendLock = new Object();
    public static boolean connected = false;
    private static boolean inited = false;


    X509TrustManager tm = new X509TrustManager() {
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                chain[0].checkValidity();
            } catch (Exception e) {
                throw new CertificateException("Certificate not valid or trusted.");
            }
        }
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };

    private static TrustManager[] sTrustManagers = new TrustManager[]
            {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers()
                        {
                            return null;
                        }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                            try {
                                chain[0].checkValidity();
                            } catch (Exception e) {
                                throw new CertificateException("Certificate not valid or trusted.");
                            }
                        }
                    }
            };

    public static synchronized WebSocketClient getInstance()
    {
        if (instance == null) {
            instance = new WebSocketClient();
        }
        return instance;
    }

    public static void setTrustManagers(TrustManager[] tm) {
        sTrustManagers = tm;
    }

    private WebSocketClient()
    {

    }

    public void Init(URI uri, String user_login, String user_password, WampInterface eventhandler)
    {
        if(inited == false)
        {
            inited = true;
            mURI = uri;

            this.login = user_login;
            this.password = user_password;

            parserdata = new WampParser(this,login,password);
            callback = eventhandler;
            mParser = new HybiParser(this);
            mHandlerThread = new HandlerThread("websocket-thread");
            mHandlerThread.start();
            mHandler = new Handler(mHandlerThread.getLooper());
        }
        else{
            this.login = user_login;
            this.password = user_password;
            parserdata = new WampParser(this,login,password);

            mHandlerThread = new HandlerThread("websocket-thread");
            mHandlerThread.start();
            mHandler = new Handler(mHandlerThread.getLooper());
        }
    }

    public void connect() {

        if (mThread != null && mThread.isAlive()) {
            onConnected();
        }
        else {
            mThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        String secret = createSecret();

                        int port = (mURI.getPort() != -1) ? mURI.getPort() : (mURI.getScheme().equals("wss") ? 443 : 80);
                        port = port > 0 ? port : 80;

                        String path = TextUtils.isEmpty(mURI.getPath()) ? "/" : mURI.getPath();
                        if (!TextUtils.isEmpty(mURI.getQuery())) {
                            path += "?" + mURI.getQuery();
                        }

                        SocketFactory factory = mURI.getScheme().equals("wss") ? getSSLSocketFactory() : SocketFactory.getDefault();

                        mSocket = factory.createSocket(mURI.getHost(), port);
                        if (mURI.getScheme().equals("wss")) {
                            ((SSLSocket) mSocket).setEnabledProtocols(new String[]{"TLSv1.2"});
                        }
     /*                   PrintWriter out = new PrintWriter(mSocket.getOutputStream());
                        out.print("GET " + path + " HTTP/1.1\r\n");
                        out.print("Upgrade: websocket\r\n");
                        out.print("Connection: Upgrade,Keep-Alive\r\n");
                        out.print("Host: " + mURI.getHost() + "\r\n");
                        out.print("Sec-WebSocket-Key: " + secret + "\r\n");
                        //out.print("Sec-Websocket-Protocol:[wamp.2.json]\r\n");
                        out.print("Sec-WebSocket-Version: 13\r\n");
                        if (mExtraHeaders != null) {
                            for (NameValuePair pair : mExtraHeaders) {
                                out.print(String.format("%s: %s\r\n", pair.getName(), pair.getValue()));
                            }
                       }
                         out.print("\r\n");
                        out.flush();
                        HybiParser.HappyDataInputStream stream = new HybiParser.HappyDataInputStream(mSocket.getInputStream());
                        StatusLine statusLine = parseStatusLine(readLine(stream));
                        if (statusLine == null) {
                            throw new HttpException("Received no reply from server.");
                        } else if (statusLine.getStatusCode() != HttpStatus.SC_SWITCHING_PROTOCOLS) {
                            throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
                        }

                        // Read HTTP response headers.
                        String line;
                        boolean validated = false;

                        while (!TextUtils.isEmpty(line = readLine(stream))) {
                            Header header = parseHeader(line);
                            if (header.getName().equals("Sec-WebSocket-Accept")) {
                                String expected = createSecretValidation(secret);
                                String actual = header.getValue().trim();

                                if (!expected.equals(actual)) {
                                    throw new HttpException("Bad Sec-WebSocket-Accept header value.");
                                }

                                validated = true;
                            }
                        }

                        if (!validated) {
                            throw new HttpException("No Sec-WebSocket-Accept header.");
                        }
                        */
                        connected = true;
                        onstartConnect();


                        WampJsonMessage jsmess = new WampJsonMessage();

                        String line_get = jsmess.AuthMessage(login, "WAVE");

                        send(line_get);

       //                 mParser.start(stream);

                    } catch (EOFException ex) {
                        connected = false;
                        onDisconnect("");

                    } catch (SSLException ex) {
                        connected = false;
                        onDisconnect("SSL");

                    } catch (Exception ex) {
                        connected = false;
                        onDisconnect("EOF");
                    }
                }
            });
            mThread.start();
        }
    }

    public void Dispose() {
        if (mHandlerThread != null) {
            mHandlerThread.interrupt();
        }
        if (mThread != null) {
            mThread.interrupt();
        }

        mHandler = null;

    }

    public void disconnect()
    {
        if (mSocket != null)
        {
            if (mHandler != null)
            {
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            mSocket.close();
                            mSocket = null;
                        }
                        catch (Exception ex)
                        {
                            onError(ex);
                        }
                    }
                });
            }
        }
    }

    public void send(String data)
    {
        sendFrame(mParser.frame(data));
    }

    public void send(byte[] data) {
        sendFrame(mParser.frame(data));
    }

 /*   private StatusLine parseStatusLine(String line) {
        if (TextUtils.isEmpty(line)) {
            return null;
        }
        return BasicLineParser.parseStatusLine(line, new BasicLineParser());
    }

    private Header parseHeader(String line) {
        return BasicLineParser.parseHeader(line, new BasicLineParser());
    }*/

    // Can't use BufferedReader because it buffers past the HTTP data.
    private String readLine(HybiParser.HappyDataInputStream reader) throws IOException {
        int readChar = reader.read();
        if (readChar == -1) {
            return null;
        }
        StringBuilder string = new StringBuilder("");
        while (readChar != '\n') {
            if (readChar != '\r') {
                string.append((char) readChar);
            }

            readChar = reader.read();
            if (readChar == -1) {
                return null;
            }
        }
        return string.toString();
    }

    private String createSecret() {
        byte[] nonce = new byte[16];
        for (int i = 0; i < 16; i++) {
            nonce[i] = (byte) (Math.random() * 256);
        }
        return Base64.encodeToString(nonce, Base64.DEFAULT).trim();
    }

    private String createSecretValidation(String secret) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update((secret + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes());
            return Base64.encodeToString(md.digest(), Base64.DEFAULT).trim();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    void sendFrame(final byte[] frame) {
        if (mHandler != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        synchronized (mSendLock) {
                            if (mSocket == null) {
                                throw new IllegalStateException("Socket not connected");
                            }
                            OutputStream outputStream = mSocket.getOutputStream();
                            outputStream.write(frame);
                            outputStream.flush();
                        }
                    }
                    catch (Exception e)
                    {
                      onError(e);
                    }
                }
            });
        }
    }

    private SSLSocketFactory getSSLSocketFactory() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException
    {
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, sTrustManagers , null);
        SSLSocketFactory fact = context.getSocketFactory();
        return fact;
    }


    @Override
    public void onDisconnect() {
        this.callback.onDisconnect();
    }

    @Override
    public void onDisconnect(String message) {
        this.callback.onDisconnect(message);
    }

    @Override
    public void onstartConnect() {
        this.callback.onstartConnect();
    }

    @Override
    public void onConnected() {
        this.callback.onConnected();
    }

    @Override
    public void onFailConnect() {
        this.callback.onFailConnect();
    }

    @Override
    public void onError(Exception ex) {
        this.callback.onError(ex);
    }

    @Override
    public void onError(String message) {
        this.callback.onError(message);
    }

    @Override
    public void onMessage(String message) {

        this.parserdata.ParseJson(message);

    }

    @Override
    public void onMessage(byte[] message) {

    }

    @Override
    public void OnSubscribed(JSONArray message) {
        this.callback.OnSubscribed(message);
    }

    @Override
    public void onHelloGet(JSONArray message) {
        this.onConnected();
    }

    @Override
    public void onChallenge(String key)
    {
        String sender = "[5,\"" + key + "\",{}]";
        this.send(sender);
    }

    @Override
    public void onEvent(JSONArray message) {
        this.callback.onEvent(message);
    }

    @Override
    public void onErrorEvent(JSONArray message) {
        this.callback.onErrorEvent(message);
    }
}
