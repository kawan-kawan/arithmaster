package ru.karamoff.kawan_kawan.arithmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // this line hides the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        int gamemode = intent.getIntExtra("gamemode", 0); // получение информации о режиме игры

        ConstraintLayout parent = findViewById(R.id.parentLayout);
        TextView modeTitle = findViewById(R.id.modeTitle);
        TextView result = findViewById(R.id.result);

        switch (gamemode) {
            case 0:
                parent.setBackground(getDrawable(R.drawable.classic_gradient));
                modeTitle.setText(getText(R.string.classic_mode_name));
                result.setText(getText(R.string.emptyField));
                result.setBackground(getDrawable(R.drawable.empty_field_classic));
                break;
        }

        ImageButton back = findViewById(R.id.backButton);
        back.setOnClickListener(v -> {
//            Intent intent = new Intent(GameActivity.this, MainActivity.class);
//            startActivity(intent);
            finish();
        });
    }

}
