package yanasmirnova.com.blogreader;

import android.app.ListActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class MainListActivity extends ListActivity {

    protected String[] mAndroidNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        Resources resources = getResources();
        mAndroidNames = resources.getStringArray(R.array.android_names);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mAndroidNames);
        setListAdapter(adapter);
    }

}
