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
        TextView textView1 = findViewById(R.id.operandOne);
        TextView textView2 = findViewById(R.id.operandTwo);
        TextView textView3 = findViewById(R.id.result);
        TextView operationField = findViewById(R.id.operation);

        TextView toBeFilled;

        switch (gamemode) {
            case 0:
                parent.setBackground(getDrawable(R.drawable.gradient_classic));
                modeTitle.setText(getText(R.string.classic_mode_name));
                textView3.setBackground(getDrawable(R.drawable.empty_field_classic));
                toBeFilled = textView3;
                ImageButton buttonSubmit = findViewById(R.id.buttonSubmit);

                buttonSubmit.setOnClickListener(v -> checkAnswerClassic(
                        Integer.parseInt(String.valueOf(textView1.getText())),
                        Integer.parseInt(String.valueOf(textView2.getText())),
                        0,
                        Integer.parseInt(String.valueOf(textView3.getText()))
                ));
                break;
            default:
                parent.setBackground(getDrawable(R.drawable.gradient_main));
                toBeFilled = textView3;
                break;
        }

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
                    toBeFilled.setText(toBeFilled.getText() + String.valueOf(digit)));
        }

        Button buttonErase = findViewById(R.id.buttonErase);

        buttonErase.setOnClickListener(v -> {
            if (toBeFilled.getText().length() != 0) {
                toBeFilled.setText(toBeFilled.getText().subSequence(0, toBeFilled.getText().length() - 1));
            }
        });

        Button buttonPlusMinus = findViewById(R.id.buttonPlusMinus);

        buttonPlusMinus.setOnClickListener(v -> {

            if (toBeFilled.getText().length() != 0) {
                if (toBeFilled.getText().charAt(0) != 45) {
                    toBeFilled.setText("" + getText(R.string.operationMinus) + toBeFilled.getText());
                } else {
                    toBeFilled.setText(toBeFilled.getText().subSequence(1, toBeFilled.getText().length()));
                }
            } else {
                toBeFilled.setText(getText(R.string.operationMinus));
            }

        });

        ImageButton back = findViewById(R.id.backButton);
        back.setOnClickListener(v -> {
            finish();
        });
        // конец инициализации

        int operation = generateOperation();
        operationField.setText(operationToString(operation));
        textView1.setText(String.valueOf(generateNumber()));
        textView2.setText(String.valueOf(generateNumber()));
        textView3.setText(String.valueOf(generateNumber()));
        toBeFilled.setText("");
    }

    private void checkAnswerClassic(int operand1, int operand2, int operation, int result) {
    }

    private int generateOperation() {
        return 0;
    }

    private int generateNumber() {
        return 0;
    }

    private String operationToString(int operation) {
        switch (operation) {
            case 0:
                return String.valueOf(getText(R.string.operationPlus));
            case 1:
                return String.valueOf(getText(R.string.operationMinus));
            case 2:
                return String.valueOf(getText(R.string.operationMult));
            case 3:
                return String.valueOf(getText(R.string.operationDiv));
            default:
                return String.valueOf(getText(R.string.operationPlus));
        }
    }

}
