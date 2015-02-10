package yanasmirnova.com.blogreader;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainListActivity extends ListActivity {

    protected String[] mBlogPostTitles;
    public static final int NUMBER_OF_POSTS = 20;
    public static final String TAG = MainListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        GetBlogPostsTask getBlogPostsTask = new GetBlogPostsTask();
        getBlogPostsTask.execute();
    }

    private class GetBlogPostsTask extends AsyncTask<Object, Void, String> {

        @Override
        protected String doInBackground(Object[] params) {
            int responseCode = -1;

            try {
                URL blogFeedUrl = new URL("http://blog.teamtreehouse.com/api/get_recent_summary/?count=" + NUMBER_OF_POSTS);
                HttpURLConnection connection = (HttpURLConnection) blogFeedUrl.openConnection();
                connection.connect();
                responseCode = connection.getResponseCode();
                Log.i(TAG, "Code: " + responseCode);
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
