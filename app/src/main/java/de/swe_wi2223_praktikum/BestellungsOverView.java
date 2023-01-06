package de.swe_wi2223_praktikum;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BestellungsOverView extends Fragment implements Load {
    ArrayList<Bestellungen> list = new ArrayList<>();

    private RecyclerView recyclerView;
    private BestellungenAdapter adapter;

    private NavigationDrawer navigationDrawer;

    public NavigationDrawer getNavigationDrawer() {
        return navigationDrawer;
    }


    public BestellungsOverView(NavigationDrawer navigationDrawer) {
        this.navigationDrawer = navigationDrawer;
    }

    public void addBestellung(Med med, float menge) {
        list.add(new Bestellungen(med, menge));
        if (adapter != null) {
            adapter.notifyItemInserted(list.size() - 1);
            recyclerView.scrollToPosition(list.size() - 1);
        }
    }

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bestellungs_overview, container, false);

        //Zuweisung der Objekte aus der activity_main.xml
        recyclerView = view.findViewById(R.id.rcvBestellung);

        //Dient dem RecycleViewer, um eine vertikale Liste zu erstellen.
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        adapter = new BestellungenAdapter(requireActivity(), list, this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void load(Object o) {
        if (o == null) return;
        this.list = (ArrayList<Bestellungen>) o;
    }

    @Override
    public Object saveData() {
        return list;
    }
}