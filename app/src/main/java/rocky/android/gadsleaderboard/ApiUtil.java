package rocky.android.gadsleaderboard;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ApiUtil {
    private ApiUtil(){}

    public static final String BASE_API_URL = "https://gadsapi.herokuapp.com/api";

    public static URL buildURL(String path) {
        URL url = null;
        Uri uri = Uri.parse(BASE_API_URL).buildUpon()
                .appendPath(path)
                .build();

        try {
            url = new URL(uri.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return url;
    }

    public static String getJSON(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try{
            InputStream stream = connection.getInputStream();
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A");    // means we want to read everything
            boolean hasData = scanner.hasNext();

            if(hasData){
                return scanner.next();
            }
            else{
                return null;
            }
        }
        catch (Exception e){
            Log.d("Error", e.toString());
            return null;
        }
        finally {
            connection.disconnect();
        }
    }

    public static ArrayList<Learner> getLearnersFromJson(String json) {
        final String NAME = "name";
        final String HOURS = "hours";
        final String COUNTRY = "country";
        final String BADGE_URL = "badgeUrl";

        ArrayList<Learner> learners = new ArrayList<>();
        try {
            JSONArray jsonLearners = new JSONArray(json);
            int numberOfLearners = jsonLearners.length();

            for (int i = 0; i < numberOfLearners; i++) {
                JSONObject learnerJSON = jsonLearners.getJSONObject(i);

                Learner learner = new Learner(
                        learnerJSON.getString(NAME),
                        learnerJSON.getInt(HOURS),
                        learnerJSON.getString(COUNTRY),
                        learnerJSON.getString(BADGE_URL)
                );

                learners.add(learner);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return learners;
    }

    public static ArrayList<Skill> getSkillsFromJson(String json) {
        final String NAME = "name";
        final String SCORE = "score";
        final String COUNTRY = "country";
        final String BADGE_URL = "badgeUrl";

        ArrayList<Skill> skills = new ArrayList<>();
        try {
            JSONArray jsonSkills = new JSONArray(json);
            int numberOfSkills = jsonSkills.length();

            for (int i = 0; i < numberOfSkills; i++) {
                JSONObject skillJSON = jsonSkills.getJSONObject(i);

                Skill skill = new Skill(
                        skillJSON.getString(NAME),
                        skillJSON.getInt(SCORE),
                        skillJSON.getString(COUNTRY),
                        skillJSON.getString(BADGE_URL)
                );

                skills.add(skill);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return skills;
    }
}
