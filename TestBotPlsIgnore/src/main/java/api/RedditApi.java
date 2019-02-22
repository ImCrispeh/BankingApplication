package api;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.sound.midi.SysexMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RedditApi {
    private final String DATA_KEY = "data";
    private final String CHILDREN_KEY = "children";
    private final String PERMALINK_KEY = "permalink";
    private final String TITLE_KEY = "title";
    private final String USER_AGENT = "User-agent";
    private final String USER_AGENT_PARAM = "Test Bot Pls Ignore";
    private String baseUrl = "https://www.reddit.com";

    public List<String> searchSubreddit(String subreddit, String query) {
        StringBuilder builder = new StringBuilder(baseUrl);

        String searchUrl = builder
                .append("/r/")
                .append(subreddit)
                .append("/search.json?restrict_sr=1&limit=5&t=all&sort=relevance&q=")
                .append(query)
                .toString();

        builder = new StringBuilder(baseUrl);

        String moreUrl = builder
                .append("/r/")
                .append(subreddit)
                .append("/search?restrict_sr=1&t=all&sort=relevance&q=")
                .append(query)
                .toString();

        List<String> results = resultsReturner(searchUrl);

        if (!results.isEmpty()) {
            results.add("More results at: " + moreUrl);
        }

        return results;
    }

    public List<String> topPostsSubreddit(String subreddit) {
        StringBuilder builder = new StringBuilder(baseUrl);

        String searchUrl = builder
                .append("/r/")
                .append(subreddit)
                .append("/top.json?t=all&limit=5")
                .toString();

        builder = new StringBuilder(baseUrl);

        String moreUrl = builder
                .append("/r/")
                .append(subreddit)
                .append("/top?t=all")
                .toString();

        List<String> results = resultsReturner(searchUrl);

        if (!results.isEmpty()) {
            results.add("More results at: " + moreUrl);
        }

        return results;
    }

    private List<String> resultsReturner(String searchUrl) {
        StringBuilder result = new StringBuilder();
        List<String> results = new ArrayList<>();

        URL url;

        try {
            url = new URL(searchUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty(USER_AGENT, USER_AGENT_PARAM);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            JSONObject baseObj = new JSONObject(result.toString());
            JSONArray dataChildrenArray = new JSONArray(baseObj.getJSONObject(DATA_KEY).getJSONArray(CHILDREN_KEY).toString());

            for (int i = 0; i < dataChildrenArray.length(); i++) {
                JSONObject childDataObj = new JSONObject(dataChildrenArray.getJSONObject(i).getJSONObject(DATA_KEY).toString());
                String permalink = childDataObj.getString(PERMALINK_KEY);
                String title = childDataObj.getString(TITLE_KEY);
                String resultUrl = baseUrl + permalink;
                String postDesc = title + "\n" + resultUrl;
                results.add(postDesc);
            }

        } catch (IOException e) {
            e.printStackTrace();
            results.add("Error retrieving results");
        }

        return results;
    }
}
