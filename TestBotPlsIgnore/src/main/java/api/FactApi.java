package api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FactApi {
    public String getFact() {
        StringBuilder result = new StringBuilder();
        String fact = "";

        String urlStr = "http://randomuselessfact.appspot.com/random.json?language=en";
        URL url;

        try {
            url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line = br.readLine()) != null) {
                result.append(line);
            }

            JSONObject jsonObj = new JSONObject(result.toString());
            fact = jsonObj.getString("text");

        } catch (IOException e) {
            fact = "Error retrieving fact";
        }
        return fact;
    }
}
