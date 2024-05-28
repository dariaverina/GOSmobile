package com.example.gos.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gos.R;
import com.example.gos.Model.Car;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private List<Car> carList;

    public CarAdapter(List<Car> carList) {
        this.carList = carList;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_list_item, parent, false);
        return new CarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.brandTextView.setText(car.getBrand());
        holder.modelTextView.setText(car.getModel());
        holder.yearTextView.setText(String.valueOf(car.getYear()));
        holder.numberTextView.setText(car.getNumber());
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

        TextView brandTextView;
        TextView modelTextView;
        TextView yearTextView;
        TextView numberTextView;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            brandTextView = itemView.findViewById(R.id.textViewBrand);
            modelTextView = itemView.findViewById(R.id.textViewModel);
            yearTextView = itemView.findViewById(R.id.textViewYear);
            numberTextView = itemView.findViewById(R.id.textViewNumber);
        }
    }
}