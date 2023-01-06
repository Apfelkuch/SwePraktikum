package de.swe_wi2223_praktikum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PlanSpinnerAdapter extends ArrayAdapter<Med> {
    private Context context;
    private ArrayList<Med> meds = new ArrayList<>();

    public PlanSpinnerAdapter(@NonNull Context context, ArrayList<Med> list, Med placeHolderMed) {
        super(context, R.layout.plan_spinner_item, list);
        this.context = context;
        meds = list;
        meds.add(0, placeHolderMed);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.plan_spinner_item, parent, false);

        Med currentMed = meds.get(position);

        TextView name = view.findViewById(R.id.SpinnerMedName);
        name.setText(currentMed.getMedName());

        return view;
    }

}
