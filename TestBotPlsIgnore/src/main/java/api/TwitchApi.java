package api;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static api.MapConstants.*;

public class TwitchApi {

    private final String CLIENT_ID = new KeyLoader().loadKey("TWITCH_CLIENT_ID");
    private final String CLIENT_ID_KEY = "Client-ID";
    private final String STREAM_KEY = "stream";
    private final String GAME_KEY = "game";
    private final String CHANNEL_KEY = "channel";
    private final String STATUS_KEY = "status";
    private final String VIEWERS_KEY = "viewers";
    private final String DISPLAY_NAME_KEY = "display_name";
    private final String URL_KEY = "url";
    private final String ERROR_KEY = "error";
    private String baseUrl = "https://api.twitch.tv/kraken";

    public Map<String, String> getStreamStatus(String channelName) {
        Map<String, String> streamStatus = new HashMap<>();
        StringBuilder builder = new StringBuilder(baseUrl);

        String searchUrl = builder
                .append("/streams/")
                .append(channelName)
                .toString();

        StringBuilder result = new StringBuilder();
        URL url;

        try {
            url = new URL(searchUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty(CLIENT_ID_KEY, CLIENT_ID);
            conn.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            JSONObject baseObj = new JSONObject(result.toString());
            if (baseObj.isNull(STREAM_KEY)) {
                checkChannelExists(channelName, streamStatus);
            } else {
                JSONObject streamObj = new JSONObject(baseObj.getJSONObject(STREAM_KEY).toString());
                streamStatus.put(STATUS, "Live");
                streamStatus.put(GAME, streamObj.getString(GAME_KEY));
                streamStatus.put(VIEWERS, String.valueOf(streamObj.getInt(VIEWERS_KEY)));

                JSONObject channelObj = new JSONObject(streamObj.getJSONObject(CHANNEL_KEY).toString());
                streamStatus.put(TITLE, channelObj.getString(STATUS_KEY));
                streamStatus.put(NAME, channelObj.getString(DISPLAY_NAME_KEY));
                streamStatus.put(URL, channelObj.getString(URL_KEY));
            }
        } catch (IOException e) {
            e.printStackTrace();
            streamStatus.put(STATUS, "Error retrieving stream");
        }

        return streamStatus;
    }

    private void checkChannelExists(String channelName, Map<String, String> streamStatus) {
        StringBuilder builder = new StringBuilder(baseUrl);
        String searchUrl = builder
                .append("/channels/")
                .append(channelName)
                .toString();

        StringBuilder result = new StringBuilder();
        URL url;

        try {
            url = new URL(searchUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty(CLIENT_ID_KEY, CLIENT_ID);
            conn.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            JSONObject baseObj = new JSONObject(result.toString());
            if (baseObj.has(ERROR_KEY)) {
                streamStatus.put(STATUS, "null");
            } else {
                streamStatus.put(STATUS, "offline");
                streamStatus.put(NAME, baseObj.getString(DISPLAY_NAME_KEY));

                if (baseObj.isNull(GAME_KEY)) {
                    streamStatus.put(GAME, "nothing");
                } else {
                    streamStatus.put(GAME, baseObj.getString(GAME_KEY));
                }

                if (baseObj.isNull(STATUS_KEY)) {
                    streamStatus.put(TITLE, "-no title-");
                } else {
                    streamStatus.put(TITLE, baseObj.getString(STATUS_KEY));
                }

                streamStatus.put(URL, baseObj.getString(URL_KEY));
            }
        } catch (IOException e) {
            e.printStackTrace();
            streamStatus.put(STATUS, "Error retrieving stream");
        }
    }
}
