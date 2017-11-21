package ru.karamoff.kawan_kawan.arithmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {


    TextView textView1, textView2, textView3, operationField;
    int operation;

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


        textView1 = findViewById(R.id.operandOne);
        textView2 = findViewById(R.id.operandTwo);
        textView3 = findViewById(R.id.result);
        operationField = findViewById(R.id.operation);

        TextView toBeFilled;

        switch (gamemode) {
            case 0:
                parent.setBackground(getDrawable(R.drawable.gradient_classic));
                modeTitle.setText(getText(R.string.classic_mode_name));
                textView3.setBackground(getDrawable(R.drawable.empty_field_classic));
                toBeFilled = textView3;
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

        findViewById(R.id.buttonErase).setOnClickListener(v -> {
            if (toBeFilled.getText().length() != 0) {
                toBeFilled.setText(toBeFilled.getText().subSequence(0, toBeFilled.getText().length() - 1));
            }
        });

        findViewById(R.id.buttonPlusMinus).setOnClickListener(v -> {

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

        findViewById(R.id.backButton).setOnClickListener(v -> {
            finish();
        });
        // конец инициализации

        initiate(toBeFilled);

        final int correctAnswer = Solver.solveForClassic(
                Integer.parseInt(String.valueOf(textView1.getText())),
                Integer.parseInt(String.valueOf(textView2.getText())),
                operation
        );

        findViewById(R.id.buttonSubmit).setOnClickListener(v -> {
                    if (toBeFilled.getText().length() != 0) {
                        final int userAnswer = Integer.parseInt(String.valueOf(toBeFilled.getText()));
                        boolean correct = AnswerChecker.checkForClassic(correctAnswer, userAnswer);
                        Toast.makeText(getApplicationContext(),
                                correct ? "Correct!" : "Incorrect!",
                                Toast.LENGTH_SHORT).show();
                        initiate(toBeFilled);
                    }
                }
        );
    }

    private void initiate(TextView toBeFilled) {
        operation = Randomizer.generateOperation();
        operationField.setText(operationToString(operation));
        textView1.setText(String.valueOf(Randomizer.generateNumber()));
        textView2.setText(String.valueOf(Randomizer.generateNumber()));
        textView3.setText(String.valueOf(Randomizer.generateNumber()));
        toBeFilled.setText("");
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
