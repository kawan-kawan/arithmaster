package ru.karamoff.kawan_kawan.arithmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Intent gameIntent = new Intent(MainActivity.this, GameActivity.class);

        findViewById(R.id.classicButton).setOnClickListener(v -> {
            gameIntent.putExtra("gamemode", 0); // передача информации о режиме игры
            startActivity(gameIntent);
        });

        findViewById(R.id.infoButton).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, InfoActivity.class)));
    }
}
