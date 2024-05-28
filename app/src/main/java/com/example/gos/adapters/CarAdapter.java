package com.example.gos.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gos.AddCarFragment;
import com.example.gos.CarDatabaseHelper;
import com.example.gos.IssueCarFragment;
import com.example.gos.R;
import com.example.gos.Model.Car;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private List<Car> carList;
    private Context context;
    private CarDatabaseHelper dbHelper;
    private FragmentManager fragmentManager;

    private AddCarFragment.NewCarListener listener;

    public interface NewCarListener {
        void onCarAdded(String brand, String model, int year, String number, String state, String personName);
        void onCarUpdated(int carId, ContentValues values);
    }

    public CarAdapter(Context context, List<Car> carList, FragmentManager fragmentManager, CarDatabaseHelper dbHelper) {
        this.context = context;
        this.carList = carList;
        this.fragmentManager = fragmentManager;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_list_item, parent, false);
        return new CarViewHolder(itemView);
    }
    public void setNewCarListener(AddCarFragment.NewCarListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.idTextView.setText(car.toJSON().toString());

        setButtonState(holder.buttonIssue, car.getState());

        holder.buttonIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int carId = carList.get(position).getId(); // Получаем идентификатор машины
                IssueCarFragment issueCarFragment = new IssueCarFragment();
                Bundle args = new Bundle();
                args.putInt("id", carId); // Передаем идентификатор машины в аргументы
                issueCarFragment.setArguments(args);
                issueCarFragment.show(fragmentManager, "IssueCarFragment");
            }
        });

        holder.buttonWriteOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Обновить состояние машины на "c"
                ContentValues values = new ContentValues();
                values.put("state", "c");
                values.put("person_name", "");
//                dbHelper.updateCar(car.getId(), values);
                if (listener != null) {
                    listener.onCarUpdated(car.getId(), values);
                }
                // Обновить состояние машины в списке
                notifyItemChanged(position);
            }
        });

        // Скрыть кнопку "Списать" если состояние машины "c"
        if ("c".equals(car.getState())) {
            holder.buttonWriteOff.setVisibility(View.GONE);
        } else {
            holder.buttonWriteOff.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public void updateCarList(List<Car> newCarList) {
        this.carList = newCarList;
        notifyDataSetChanged();
    }

    static class CarViewHolder extends RecyclerView.ViewHolder {
        TextView idTextView;
        Button buttonIssue;
        Button buttonWriteOff;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.textViewId);
            buttonIssue = itemView.findViewById(R.id.buttonIssue);
            buttonWriteOff = itemView.findViewById(R.id.buttonWriteOff);
        }
    }

    public void setButtonState(Button button, String state) {
        if (state == null || state.equals("p")) {
            button.setText("Выдать");
            button.setBackgroundTintList(ContextCompat.getColorStateList(context, android.R.color.holo_green_light));
            button.setEnabled(true);
        } else if (state.equals("v")) {
            button.setText("Выдано");
            button.setBackgroundTintList(ContextCompat.getColorStateList(context, android.R.color.darker_gray));
            button.setEnabled(false);
        } else if (state.equals("c")) {
            button.setText("Списано");
            button.setBackgroundTintList(ContextCompat.getColorStateList(context, android.R.color.darker_gray));
            button.setEnabled(false);
        }
    }
}
