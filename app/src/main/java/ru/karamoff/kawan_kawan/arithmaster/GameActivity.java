package ru.karamoff.kawan_kawan.arithmaster;

import android.annotation.SuppressLint;
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
    int operation, correctAnswer;

    @SuppressLint("SetTextI18n")
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

        TextView[] textviews = new TextView[3];
//        TextView toBeFilled;
        int toBeFilled;


        textviews[0] = textView1;
        textviews[1] = textView2;
        textviews[2] = textView3;

        switch (gamemode) {
            case 0:
                parent.setBackground(getDrawable(R.drawable.gradient_classic));
                modeTitle.setText(getText(R.string.classic_mode_name));
                toBeFilled = 2;
                textviews[toBeFilled].setBackground(getDrawable(R.drawable.empty_field_classic));
                break;
            default:
                toBeFilled = 2;
                finish();
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

        // метод добавляет цифры в поле ответа (не более четырёх цифр)
        for (int i = 0; i < digits.length; i++) {
            final int digit = i;
            digits[i].setOnClickListener(v -> {
                if (textviews[toBeFilled].getText().length() > 0 && textviews[toBeFilled].getText().charAt(0) == 45 && textviews[toBeFilled].getText().length() <= 4 ||
                        textviews[toBeFilled].getText().length() <= 3) {
                    textviews[toBeFilled].setText(textviews[toBeFilled].getText() + String.valueOf(digit));
                }
            });
        }

        // метод стирает цифры при нажатии на "DEL"
        findViewById(R.id.buttonErase).setOnClickListener(v -> {
            if (textviews[toBeFilled].getText().length() != 0) {
                textviews[toBeFilled].setText(textviews[toBeFilled].getText().subSequence(0, textviews[toBeFilled].getText().length() - 1));
            }
        });

        // метод меняет положительное число на отрицательноеи наоборот
        findViewById(R.id.buttonPlusMinus).setOnClickListener(v -> {
            if (textviews[toBeFilled].getText().length() != 0) {
                if (textviews[toBeFilled].getText().charAt(0) != 45) {
                    textviews[toBeFilled].setText("" + getText(R.string.operationMinus) + textviews[toBeFilled].getText());
                } else {
                    textviews[toBeFilled].setText(textviews[toBeFilled].getText().subSequence(1, textviews[toBeFilled].getText().length()));
                }
            } else {
                textviews[toBeFilled].setText(getText(R.string.operationMinus));
            }

        });

        // метод возвращается в главное меню при нажатии на кнопку
        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        // конец инициализации



        initiate(textviews, toBeFilled);

        findViewById(R.id.buttonSubmit).setOnClickListener(v -> {
                    if (textviews[toBeFilled].getText().length() != 0 && !textviews[toBeFilled].getText().equals(getText(R.string.operationMinus))) {
                        final int userAnswer = Integer.parseInt(String.valueOf(textviews[toBeFilled].getText()));
                        boolean correct = AnswerChecker.checkForClassic(correctAnswer, userAnswer);
                        Toast.makeText(getApplicationContext(),
                                correct ? "Correct!" : "Incorrect!",
                                Toast.LENGTH_SHORT).show();
                        initiate(textviews, toBeFilled);
                    }
                }
        );
    }

    private void initiate(TextView[] textviews, int toBeFilled) {
        int[] set = Randomizer.generate();
        operation = set[0];
        operationField.setText(operationToString(operation));
        for (int i = 0; i < textviews.length; i++){
            if(i != toBeFilled) {
                textviews[i].setText(String.valueOf(set[i + 1]));
            }
        }
        textviews[toBeFilled].setText("");

        correctAnswer = set[3];
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
