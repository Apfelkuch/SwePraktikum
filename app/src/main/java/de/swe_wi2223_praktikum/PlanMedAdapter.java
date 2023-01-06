package de.swe_wi2223_praktikum;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlanMedAdapter extends RecyclerView.Adapter<PlanMedAdapter.PlanMedHolder> {

    ArrayList<PlanMed> contentArrayList;
    Context context;

    public PlanMedAdapter(Context context) {
        this.context = context;
    }

    public void setMeds(ArrayList<PlanMed> contentArrayList) {
        this.contentArrayList = contentArrayList;
    }

    @NonNull
    @Override
    public PlanMedAdapter.PlanMedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.plan_med_item, parent, false);
        return new PlanMedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanMedHolder holder, int position) {
        PlanMed content = contentArrayList.get(position);
        if (contentArrayList == null)
            return;

        holder.isActive.setChecked(true);
        holder.name.setText(content.getMed().getMedName());
        holder.amount.setText(String.valueOf(content.getAmount()));


        holder.isActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!holder.isActive.isChecked()) {
                    contentArrayList.remove(holder.getAbsoluteAdapterPosition());
                    notifyDataSetChanged();
                }
            }
        });

        holder.amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String tempAmount = holder.amount.getText().toString();
                content.setAmount(tempAmount.isEmpty() ? 0 : Float.parseFloat(tempAmount));
            }
        });

    }

    @Override
    public int getItemCount() {
        if (contentArrayList == null)
            return 0;
        return contentArrayList.size();
    }

    class PlanMedHolder extends RecyclerView.ViewHolder {
        private CheckBox isActive;
        private TextView name;
        private EditText amount;

        public PlanMedHolder(View view) {
            super(view);

            isActive = view.findViewById(R.id.PlanMedisAktive);
            name = view.findViewById(R.id.PlanMedName);
            amount = view.findViewById(R.id.PlanMedAmount);
        }
    }
}
