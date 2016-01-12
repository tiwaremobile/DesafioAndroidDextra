package dextra.desafio.macosrrosa.bean;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by marcos.fael on 11/01/2016.
 */
public class WsResponse {
    private JSONObject jsonObject = null;
    private int responseCode = 0;
    private Map<String, List<String>> headerFields = null;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public Map<String, List<String>> getHeaderFields() {
        return headerFields;
    }

    public void setHeaderFields(Map<String, List<String>> headerFields) {
        this.headerFields = headerFields;
    }
}
