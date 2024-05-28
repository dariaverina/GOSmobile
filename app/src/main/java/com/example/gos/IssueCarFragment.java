package com.example.gos;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class IssueCarFragment extends DialogFragment {
    private CarDatabaseHelper dbHelper;
    private AddCarFragment.NewCarListener listener;
    public static IssueCarFragment newInstance() {
        return new IssueCarFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_issue_car, container, false);

        dbHelper = new CarDatabaseHelper(requireContext()); // Инициализация dbHelper

        EditText editTextFullName = view.findViewById(R.id.editTextFullName);
        Button saveButton = view.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получение введенного ФИО
                String fullName = editTextFullName.getText().toString().trim();

                // Получение id машины из аргументов
                int carId = getArguments().getInt("id");
                // Добавление машины в базу данных
                ContentValues values = new ContentValues();
                values.put("state", "v");
                values.put("person_name", fullName);

                dbHelper.updateCar(carId, values);
//                if (listener != null) {
//                    listener.onCarUpdated(carId, values);
//                }

                dismiss();
            }
        });

        return view;
    }

}
