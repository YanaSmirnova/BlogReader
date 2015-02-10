package yanasmirnova.com.blogreader;

import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

public class MainListActivity extends ListActivity {

    protected String[] mBlogPostTitles;
    public static final int NUMBER_OF_POSTS = 20;
    public static final String TAG = MainListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        if (isNetworkAvailable()) {
            GetBlogPostsTask getBlogPostsTask = new GetBlogPostsTask();
            getBlogPostsTask.execute();
        }
        else {
            Toast.makeText(this, "Network is unavailable", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isNetworkAvailable() {
        boolean isAvailable = false;

        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }

    private class GetBlogPostsTask extends AsyncTask<Object, Void, String> {

        @Override
        protected String doInBackground(Object[] params) {
            int responseCode = -1;
            JSONObject jsonResponse = null;
            StringBuilder builder = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet("http://blog.teamtreehouse.com/api/get_recent_summary?count=" + NUMBER_OF_POSTS);

            try {
                HttpResponse response = client.execute(httpget);
                StatusLine statusLine = response.getStatusLine();
                responseCode = statusLine.getStatusCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                    String line = "";
                    while((line = reader.readLine()) != null){
                        builder.append(line);
                    }
                    jsonResponse = new JSONObject(builder.toString());

                    String status = jsonResponse.getString("status");
                    Log.v(TAG, status);

                    JSONObject jsonPost = new JSONObject();
                    String title = "";

                    JSONArray jsonPosts = jsonResponse.getJSONArray("posts");
                    Log.v(TAG, "No of posts in array " + jsonPosts.length());
                    for (int i=0; i<jsonPosts.length(); i++) {
                        jsonPost = jsonPosts.getJSONObject(i);
                        title = jsonPost.getString("title");
                        Log.v(TAG, "Post " + i + ": " + title);
                    }
                }
                else {
                    Log.i(TAG, "Unsuccessful Http Request Code: " + responseCode);
                }
            } catch (MalformedURLException e) {
                Log.e(TAG, "Exception caught: ", e);
            } catch (IOException e) {
                Log.e(TAG, "Exception caught: ", e);
            }
            catch (Exception e) {
                Log.e(TAG, "Exception caught: ", e);
            }

            return "Code: " + responseCode;
        }
    }

}
