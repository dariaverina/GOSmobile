package com.example.gos;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gos.Model.Car;
import com.example.gos.adapters.CarAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddCarFragment.NewCarListener {

    private RecyclerView recyclerView;
    private CarAdapter carAdapter;
    private List<Car> carList;
    private CarDatabaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new CarDatabaseHelper(this);
        carList = new ArrayList<>();
        loadCarsFromDatabase();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        carAdapter = new CarAdapter(this, carList, getSupportFragmentManager());
        recyclerView.setAdapter(carAdapter);

        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                AddCarFragment AddCarFragment = new AddCarFragment();
                AddCarFragment.show(fragmentManager, "AddCarFragment");
            }
        });
    }

    private void loadCarsFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("cars", new String[]{"id", "brand", "model", "year", "number"}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String brand = cursor.getString(cursor.getColumnIndex("brand"));
                String model = cursor.getString(cursor.getColumnIndex("model"));
                int year = cursor.getInt(cursor.getColumnIndex("year"));
                String number = cursor.getString(cursor.getColumnIndex("number")); // Получаем номер машины

                carList.add(new Car(id, brand, model, year, number, null, null)); // Обновляем конструктор
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }

    @Override
    public void onCarAdded(String brand, String model, int year, String number) {
        // Добавление машины в базу данных
        dbHelper.addCar(brand, model, year, number);

        // Обновление списка машин
        carList.clear();
        loadCarsFromDatabase();
        carAdapter.updateCarList(carList);
    }
}