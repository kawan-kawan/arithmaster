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

    TextView textView1,
            textView2,
            textView3,
            operationField;

    int operation,
            correctAnswer;

    int round = 0;
    int correctRounds = 0;

    final int MAX_ROUNDS = 10;

    @SuppressLint("SetTextI18n") // не обращать внимания
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // стартовая инициализация
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        // получение информации о режиме игры
        Intent intent = getIntent();
        int gamemode = intent.getIntExtra("gamemode", 0);

        // получение объектов для изменения в дальнейшем
        ConstraintLayout parent = findViewById(R.id.parentLayout); // "родитель" разметки
        TextView modeTitle = findViewById(R.id.modeTitle); // заголовок окна

        textView1 = findViewById(R.id.operandOne); // поле первого числа
        textView2 = findViewById(R.id.operandTwo); // поле второго числа
        textView3 = findViewById(R.id.result); // поле третьего числа
        operationField = findViewById(R.id.operation); // операция

        // объединение полей для чисел в массив
        TextView[] textviews = new TextView[3];
        textviews[0] = textView1;
        textviews[1] = textView2;
        textviews[2] = textView3;

        int toBeFilled; // обозначает индекс того поля, которое заполняет пользователь

        // меняет окно в зависимости от режима игры
        switch (gamemode) {
            case 0: // классика
                parent.setBackground(getDrawable(R.drawable.gradient_classic)); // установка фона
                modeTitle.setText(getText(R.string.classic_mode_name)); // установка заголовка

                // установка номера заполняемого поля - в данном случае последнее
                toBeFilled = 2;
                textviews[toBeFilled].setBackground(getDrawable(R.drawable.empty_field_classic));
                break;
            default: // если не передан один из стандартных режимов - скорее всего ошибка
                toBeFilled = 2;
                finish(); // убиваем activity
                break;
        }

        // объединяем кнопки клавиатуры с цифрами в массив
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
            digits[i].setOnClickListener(v -> {
                // метод добавляет цифры в поле ответа при нажатии (не более четырёх цифр)
                if (textviews[toBeFilled].getText().length() > 0 &&         // если длина больше нуля И
                        textviews[toBeFilled].getText().charAt(0) == 45 &&  // если первый минус И
                        textviews[toBeFilled].getText().length() <= 4 ||    // если длина меньше 5

                        textviews[toBeFilled].getText().length() <= 3) {    // ИЛИ если длина меньше 3
                    textviews[toBeFilled].setText(textviews[toBeFilled].getText() + String.valueOf(digit));
                }
            });
        }

        findViewById(R.id.buttonErase).setOnClickListener(v -> {
            // метод стирает цифры при нажатии на "DEL"
            if (textviews[toBeFilled].getText().length() != 0) {
                textviews[toBeFilled].setText(textviews[toBeFilled].getText().subSequence(0, textviews[toBeFilled].getText().length() - 1));
            }
        });

        findViewById(R.id.buttonPlusMinus).setOnClickListener(v -> {
            // метод меняет положительное число на отрицательноеи наоборот
            if (textviews[toBeFilled].getText().length() != 0) { // если поле не пусто
                if (textviews[toBeFilled].getText().charAt(0) != 45) { // если первый НЕ минус
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


        // запускает игру
        initiate(textviews, toBeFilled);

        // установка метода проверки на кнопку "подтвердить ответ"
        findViewById(R.id.buttonSubmit).setOnClickListener(v -> {
            if (textviews[toBeFilled].getText().length() != 0 &&
                    !textviews[toBeFilled].getText().equals(getText(R.string.operationMinus))) {
                // конвертация ответа пользователя в int
                final int userAnswer = Integer.parseInt(String.valueOf(textviews[toBeFilled].getText()));
                // проверка на правильность ответа
                boolean correct = correctAnswer == userAnswer;
                // высвечивание результата
                Toast.makeText(getApplicationContext(),
                        correct ? "Correct!" : "Incorrect!",
                        Toast.LENGTH_SHORT).show();

                if (correct) {
                    correctRounds++;
                }

                // повторная инициализация; следующий раунд
                initiate(textviews, toBeFilled);
            }
        });
    }


    private void initiate(TextView[] textviews, int toBeFilled) {
        if (round < MAX_ROUNDS) {
            // генерирует операцию и числа
            int[] set = Randomizer.generate();

            // установка операции в поле
            operation = set[0];
            operationField.setText(operationToString(operation));

            // установка чисел в поля, не заполняемые пользователем
            for (int i = 0; i < textviews.length; i++) {
                if (i != toBeFilled) {
                    textviews[i].setText(String.valueOf(set[i + 1]));
                }
            }

            // поле, заполняемое пользователем, очищается
            textviews[toBeFilled].setText("");

            // установка корректного ответа
            correctAnswer = set[3];

            round++;
        } else {
            Toast.makeText(getApplicationContext(),
                    "Nice job! You answered " + correctRounds + " out of " + MAX_ROUNDS + " correctly!",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    // метод принимает номер операции и возвращает соответствующий знак в форме строки
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
