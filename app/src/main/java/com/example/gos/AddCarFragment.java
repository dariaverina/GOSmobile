package com.example.gos;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCarFragment extends DialogFragment {

    private EditText editTextBrand;
    private EditText editTextModel;
    private EditText editTextYear;
    private EditText editTextNumber;
    private NewCarListener listener;

    public interface NewCarListener {
        void onCarAdded(String brand, String model, int year, String number, String state, String personName);
        void onCarUpdated(int carId, ContentValues values);
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof NewCarListener) {
            listener = (NewCarListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement NewCarListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_car, container, false);

        editTextBrand = view.findViewById(R.id.editTextBrand);
        editTextModel = view.findViewById(R.id.editTextModel);
        editTextYear = view.findViewById(R.id.editTextYear);
        editTextNumber = view.findViewById(R.id.editTextNumber);
        Button buttonAdd = view.findViewById(R.id.buttonAdd);
        Button buttonCancel = view.findViewById(R.id.buttonCancel);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCar();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    private void addCar() {
        String brand = editTextBrand.getText().toString().trim();
        String model = editTextModel.getText().toString().trim();
        String yearStr = editTextYear.getText().toString().trim();
        String number = editTextNumber.getText().toString().trim();
        String state = "p";
        String personName = null;

        if (TextUtils.isEmpty(brand) || TextUtils.isEmpty(model) || TextUtils.isEmpty(yearStr) || TextUtils.isEmpty(number)) {
            Toast.makeText(getContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        int year;
        try {
            year = Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Год выпуска должен быть числом", Toast.LENGTH_SHORT).show();
            return;
        }

        if (listener != null) {
            listener.onCarAdded(brand, model, year, number, state, personName);
        }

        dismiss();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
