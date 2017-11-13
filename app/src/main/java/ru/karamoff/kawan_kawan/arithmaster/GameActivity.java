package ru.karamoff.kawan_kawan.arithmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
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
        TextView operand1 = findViewById(R.id.operandOne);
        TextView operand2 = findViewById(R.id.operandTwo);
        TextView result = findViewById(R.id.result);

        Button[] digits = {
                findViewById(R.id.button0),
                findViewById(R.id.button1),
                findViewById(R.id.button2),
                findViewById(R.id.button3),
                findViewById(R.id.button4),
                findViewById(R.id.button5),
                findViewById(R.id.button6),
                findViewById(R.id.button7),
                findViewById(R.id.button8),
                findViewById(R.id.button9)
        };

        for (int i = 0; i < digits.length; i++) {
            final int digit = i;
            digits[i].setOnClickListener(v ->
                    result.setText(result.getText() + String.valueOf(digit)));
        }

        Button buttonErase = findViewById(R.id.buttonErase);

        buttonErase.setOnClickListener(v -> {
            if (result.getText().length() != 0) {
                result.setText(result.getText().subSequence(0, result.getText().length() - 1));
            }
        });

        Button buttonPlusMinus = findViewById(R.id.buttonPlusMinus);

        buttonPlusMinus.setOnClickListener(v -> {

            if (result.getText().length() != 0) {
                if (result.getText().charAt(0) != 45) {
                    result.setText("" + getText(R.string.operationMinus) + result.getText());
                } else {
                    result.setText(result.getText().subSequence(1, result.getText().length()));
                }
            } else {
                result.setText(getText(R.string.operationMinus));
            }

        });

        ImageButton buttonSubmit = findViewById(R.id.buttonSubmit);

//        buttonSubmit.setOnClickListener(v -> checkAnswer(
//                Integer.parseInt(String.valueOf(operand1.getText())),
//                Integer.parseInt(String.valueOf(operand2.getText())),
//                0,
//                Integer.parseInt(String.valueOf(result.getText()))
//                ));

        switch (gamemode) {
            case 0:
                parent.setBackground(getDrawable(R.drawable.gradient_classic));
                modeTitle.setText(getText(R.string.classic_mode_name));
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

    private void checkAnswer(int operand1, int operand2, int operation, int result) {
    }

}
