package de.swe_wi2223_praktikum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MedAdapter extends ArrayAdapter<Item>{

    ArrayList<Item> medList;

    public MedAdapter(Context context, int textViewResourceId, ArrayList<Item> objects){
        super(context, textViewResourceId, objects);
        medList = objects;


    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.medi_fragment,null);
        TextView textView = (TextView) v.findViewById(R.id.Med_name);
        textView.setText(medList.get(position).getMedName());
        return v;
    }
}
