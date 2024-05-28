package com.example.gos.adapters;

import android.content.Context;
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
import com.example.gos.IssueCarFragment;
import com.example.gos.R;
import com.example.gos.Model.Car;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private List<Car> carList;
    private Context context;
    private FragmentManager fragmentManager;

    public CarAdapter(Context context, List<Car> carList, FragmentManager fragmentManager) {
        this.context = context;
        this.carList = carList;
        this.fragmentManager = fragmentManager;
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
        setButtonState(holder.buttonIssue, car.getStatus());

        holder.buttonIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IssueCarFragment issueCarFragment = new IssueCarFragment();
                issueCarFragment.show(fragmentManager, "IssueCarFragment");
            }
        });
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
        Button buttonIssue;
        Button buttonWriteOff;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            brandTextView = itemView.findViewById(R.id.textViewBrand);
            modelTextView = itemView.findViewById(R.id.textViewModel);
            yearTextView = itemView.findViewById(R.id.textViewYear);
            numberTextView = itemView.findViewById(R.id.textViewNumber);
            buttonIssue = itemView.findViewById(R.id.buttonIssue);
            buttonWriteOff = itemView.findViewById(R.id.buttonWriteOff);
        }
    }

    public void setButtonState(Button button, String status) {
        if (status == null || status.equals("p")) {
            button.setText("Выдать");
            button.setBackgroundTintList(ContextCompat.getColorStateList(context, android.R.color.holo_green_light));
            button.setEnabled(true);
        } else if (status.equals("v")) {
            button.setText("Выдано");
            button.setBackgroundTintList(ContextCompat.getColorStateList(context, android.R.color.darker_gray));
            button.setEnabled(false);
        } else if (status.equals("c")) {
            button.setText("Списано");
            button.setBackgroundTintList(ContextCompat.getColorStateList(context, android.R.color.darker_gray));
            button.setEnabled(false);
        }
    }


}
