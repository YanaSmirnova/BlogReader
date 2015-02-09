package yanasmirnova.com.blogreader;

import android.app.ListActivity;
import android.os.Bundle;

public class MainListActivity extends ListActivity {

    protected String[] mAndroidNames = {
            "Android beta",
            "Android 1.0",
            "Android 1.1",
            "Cupcake",
            "Donut",
            "Eclair",
            "Froyo",
            "Gingerbread",
            "Honeycomb",
            "Ice Cream Sandwich",
            "Jelly Bean"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
    }

}
