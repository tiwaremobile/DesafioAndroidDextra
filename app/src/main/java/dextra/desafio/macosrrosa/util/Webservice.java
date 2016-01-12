package dextra.desafio.macosrrosa.util;

import android.util.Log;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import dextra.desafio.macosrrosa.bean.WsResponse;

/**
 * Created by marcos.fael@gmail.com on 11/01/2016.
 */
public class Webservice {
    private final static String TAG = "WEBSERVICE";
    private final static int TIMEOUT_WS = 30000; //30 seconds
    //private final static String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";//"application/json";
    //private final static String DEFAULT_CHARSET = "UTF-8";

    public static WsResponse get(String pUrlApi) {
        WsResponse response = new WsResponse();
        try {
            URL url = new URL(pUrlApi);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            /*
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", DEFAULT_CONTENT_TYPE);//"application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", DEFAULT_CHARSET);
            connection.setConnectTimeout(TIMEOUT_WS);
            connection.setUseCaches(false);
            */

            InputStream in = new BufferedInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String dataResponse;
            while ((dataResponse = reader.readLine()) != null) {
                result.append(dataResponse);
            }
            response.setJsonObject(new JSONObject(result.toString()));
            response.setHeaderFields(connection.getHeaderFields());
            response.setResponseCode(connection.getResponseCode());

            connection.disconnect();
        } catch (Throwable t) {
            Log.e(TAG, "WS Request failed: " + t.toString());
        }
        return response;
    }
}
