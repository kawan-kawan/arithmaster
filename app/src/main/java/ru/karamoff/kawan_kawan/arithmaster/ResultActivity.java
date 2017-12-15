package ru.karamoff.kawan_kawan.arithmaster;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Date;

public class ResultActivity extends AppCompatActivity {

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();

        long[] time = intent.getLongArrayExtra("time");
        Date spent = new Date(time[0]);
        TextView timeTV = findViewById(R.id.timeTextView);
        timeTV.setText(String.format("%tM:%tS", spent, spent));

        int[] correct = intent.getIntArrayExtra("correct");
        TextView correctTV = findViewById(R.id.correctTextView);
        correctTV.setText(String.format("%d/%d", correct[0], correct[1]));

        int score = calculateScore((double) correct[0] / correct[1], (double) time[0] / time[1]);
        TextView scoreTV = findViewById(R.id.scoreTextView);
        scoreTV.setText(getResources().getStringArray(R.array.scores)[score]);

        TextView heading = findViewById(R.id.textViewHeading);
        heading.setText(score >= 3 ? getResources().getString(R.string.end_good)
                : getResources().getString(R.string.end_not_good));

        int gamemode = intent.getIntExtra("gamemode", 0);

        findViewById(R.id.buttonTryAgain).setOnClickListener(v -> {
            Intent gameIntent = new Intent(ResultActivity.this, GameActivity.class);
            gameIntent.putExtra("gamemode", gamemode); // передача информации о режиме игры
            startActivity(gameIntent);
            finish();
        });

        findViewById(R.id.buttonBackToMenu).setOnClickListener(v -> {
            Intent menuIntent = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(menuIntent);
            finish();
        });

        findViewById(R.id.backButton).setOnClickListener(v -> finish());
    }

    int calculateScore(double correctness, double time) {
        if (correctness > time * 0.25 + 0.8) {
            return 0;
        } else if (correctness > time * 0.25 + 0.6) {
            return 1;
        } else if (correctness > time * 0.25 + 0.4) {
            return 2;
        } else if (correctness > time * 0.25 + 0.2) {
            return 3;
        } else if (correctness > time * 0.25) {
            return 4;
        } else {
            return 5;
        }
    }
}
