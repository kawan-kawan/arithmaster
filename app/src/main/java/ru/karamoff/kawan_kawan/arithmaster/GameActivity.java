package ru.karamoff.kawan_kawan.arithmaster;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private TextView operationField;

    private int correctAnswer;
    private int gamemode;

    private int round = 0;
    private int correctRounds = 0;
    private final int MAX_ROUNDS = 10;

    private final int TIME = 60 * 1000;

    private Date startTime;
    private Date endTime;


    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // стартовая инициализация
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        // получение объектов для изменения в дальнейшем
        ConstraintLayout parent = findViewById(R.id.parentLayout); // "родитель" разметки
        TextView modeTitle = findViewById(R.id.modeTitle); // заголовок окна

        operationField = findViewById(R.id.operation); // операция

        // объединение полей для чисел в массив
        TextView[] textviews = new TextView[3];
        textviews[0] = findViewById(R.id.operandOne);
        textviews[1] = findViewById(R.id.operandTwo);
        textviews[2] = findViewById(R.id.result);

        int toBeFilled; // обозначает индекс того поля, которое заполняет пользователь

        // получение информации о режиме игры
        Intent intent = getIntent();
        gamemode = intent.getIntExtra("gamemode", 0);

        // меняет окно в зависимости от режима игры
        switch (gamemode) {
            case 0: // классика
                parent.setBackground(AppCompatResources.getDrawable(this, R.drawable.gradient_classic)); // установка фона
                modeTitle.setText(getText(R.string.classic_mode_name)); // установка заголовка

                // установка номера заполняемого поля - в данном случае последнее
                toBeFilled = 2;
                textviews[toBeFilled].setBackground(AppCompatResources.getDrawable(this, R.drawable.empty_field_classic));
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
                // метод добавляет цифры в поле ответа при нажатии (не более четырёх цифр, с минусом
                // или без него)
                if (textviews[toBeFilled].getText().length() > 0
                        && textviews[toBeFilled].getText().charAt(0) == 45
                        && textviews[toBeFilled].getText().length() <= 4
                        || textviews[toBeFilled].getText().length() <= 3) {
                    textviews[toBeFilled].setText(
                            String.format("%s%s", textviews[toBeFilled].getText(), String.valueOf(digit)));
                }
            });
        }

        findViewById(R.id.buttonErase).setOnClickListener(v -> {
            // метод стирает цифры при нажатии на "DEL"
            if (textviews[toBeFilled].getText().length() != 0) {
                textviews[toBeFilled].setText(
                        textviews[toBeFilled].getText()
                                .subSequence(0, textviews[toBeFilled].getText().length() - 1));
            }
        });

        findViewById(R.id.buttonPlusMinus).setOnClickListener(v -> {
            // метод меняет положительное число на отрицательноеи наоборот
            if (textviews[toBeFilled].getText().length() != 0) { // если поле не пусто
                if (textviews[toBeFilled].getText().charAt(0) != 45) { // если первый НЕ минус
                    textviews[toBeFilled].setText(String.format("%s%s", getText(R.string.operation_minus), textviews[toBeFilled].getText()));
                } else {
                    textviews[toBeFilled].setText(
                            textviews[toBeFilled].getText()
                                    .subSequence(1, textviews[toBeFilled].getText().length()));
                }
            } else {
                textviews[toBeFilled].setText(getText(R.string.operation_minus));
            }

        });

        // метод возвращается в главное меню при нажатии на кнопку
        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        // конец инициализации


        // запускает игру
        initiate(textviews, toBeFilled);

        // создаёт таймер
        timer = new Timer();
        TextView timerTV = findViewById(R.id.timer);

        startTime = new Date();
        endTime = new Date(startTime.getTime() + TIME);

        //каждую секунду проверяет, не пора ли закончить игру
        timer.scheduleAtFixedRate(new TimerTask() {
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                Date nowTime = new Date();
                if (nowTime.after(endTime)) {
                    endGame(nowTime);
                } else {
                    long left = Math.round((double) (endTime.getTime() - nowTime.getTime()) / 1000)
                            * 1000;
                    Date timeLeft = new Date(left);
                    runOnUiThread(() -> timerTV.setText(String.format("%tM:%tS", timeLeft, timeLeft)));
                }
            }
        }, 0, 1000);

        // установка метода проверки на кнопку "подтвердить ответ"
        findViewById(R.id.buttonSubmit).setOnClickListener(v -> {
            if (textviews[toBeFilled].getText().length() != 0
                    && !textviews[toBeFilled].getText().equals(getText(R.string.operation_minus))) {

                // конвертация ответа пользователя в int
                final int userAnswer = Integer.parseInt(
                        String.valueOf(textviews[toBeFilled].getText()));

                // проверка на правильность ответа
                if (correctAnswer == userAnswer) {
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
            operationField.setText(operationToString(set[0]));

            // установка чисел в поля, не заполняемые пользователем
            for (int i = 0; i < textviews.length; i++) {
                if (i != toBeFilled) {
                    textviews[i].setText(String.valueOf(set[i + 1]));
                }
            }

            // поле, заполняемое пользователем, очищается
            textviews[toBeFilled].setText("");

            // установка корректного ответа в память для дальнейшей сверки
            correctAnswer = set[3];

            round++;
        } else {
            endGame(new Date());
        }
    }

    private void endGame(Date check) {
        timer.cancel();

        long spent = Math.round((double) (check.getTime() - startTime.getTime()) / 1000) * 1000;
        Intent resultIntent = new Intent(GameActivity.this, ResultActivity.class);
        resultIntent.putExtra("time", new long[]{spent, TIME});
        resultIntent.putExtra("correct", new int[]{correctRounds, MAX_ROUNDS});
        resultIntent.putExtra("gamemode", gamemode);
        startActivity(resultIntent);

        finish();
    }

    // метод принимает номер операции и возвращает соответствующий знак в форме строки
    private String operationToString(int operation) {
        switch (operation) {
            case 0:
                return String.valueOf(getText(R.string.operation_plus));
            case 1:
                return String.valueOf(getText(R.string.operation_minus));
            case 2:
                return String.valueOf(getText(R.string.operation_mult));
            case 3:
                return String.valueOf(getText(R.string.operation_div));
            default:
                return String.valueOf(getText(R.string.operation_plus));
        }
    }

}
