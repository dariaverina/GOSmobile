package com.example.gos;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        CarDatabaseHelper dbHelper = new CarDatabaseHelper(this);

        int countParked = dbHelper.getParkedCarCount();
        int countIssued = dbHelper.getIssuedCarCount();
        int countWrittenOff = dbHelper.getWrittenOffCarCount();

        // Находим текстовые поля в макете
        TextView textViewParked = findViewById(R.id.textViewParked);
        TextView textViewIssued = findViewById(R.id.textViewIssued);
        TextView textViewWrittenOff = findViewById(R.id.textViewWrittenOff);

        // Устанавливаем текст для каждого текстового поля
        textViewParked.setText("Количество машин в парке: " + countParked);
        textViewIssued.setText("Количество выданных машин: " + countIssued);
        textViewWrittenOff.setText("Количество списанных машин: " + countWrittenOff);
    }

    // Метод для получения количества машин по заданному состоянию
    private int getCountByState(String state) {
        // Ваш код для получения количества машин по заданному состоянию из базы данных или другого источника данных
        // В этом примере мы просто возвращаем случайное число
        return (int) (Math.random() * 10); // Генерируем случайное число от 0 до 9
    }
}
