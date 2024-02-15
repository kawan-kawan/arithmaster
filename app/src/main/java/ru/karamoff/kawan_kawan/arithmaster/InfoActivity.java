package ru.karamoff.kawan_kawan.arithmaster;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class InfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // this line hides the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_info);

        TextView version = findViewById(R.id.textView0);
        version.setText(String.format("%s %s", getString(R.string.version), BuildConfig.VERSION_NAME));

        findViewById(R.id.backButton).setOnClickListener(v -> finish());
    }
}
