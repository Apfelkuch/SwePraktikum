package de.swe_wi2223_praktikum;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//Wirft alle erhaltenen Daten, welche über die Schnittstelle bei MainActivity.java reinkommen,
//in einen "Eimer" und greift später bei der Erstellung der "Cards" drauf zu.
public class BestellungenAdapter extends RecyclerView.Adapter<BestellungenAdapter.BestellungenViewHolder> {
    private List<bestellungen> mListBestellungen;

    public void setData(List<bestellungen> list) {
        this.mListBestellungen = list;
    }

//Instanziert die items_bestellungen.xml.
//Hier rüber werden die einzelnen Bestellungen erstellt und returned.
    @NonNull
    @Override
    public BestellungenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_bestellungen, parent, false);
        return new BestellungenViewHolder(view);
    }

//Holt sich die Positionen der Objekte innerhalb der jeweiligen instanziierten "Card"
//und weist den angesprochenen Objekten die Daten zu, welche über die Schnittstelle kamen.
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BestellungenViewHolder holder, int position) {
        bestellungen bestellungen = mListBestellungen.get(position);
        if (bestellungen == null) {
            return;
        }
        holder.name.setText(bestellungen.getName());
        holder.menge.setText(bestellungen.getMenge() + "x");

//Premium DELETE button.
        holder.itemView.findViewById(R.id.delete_button).setOnClickListener(view -> {
            mListBestellungen.remove(holder.getBindingAdapterPosition());
            notifyItemRemoved(holder.getBindingAdapterPosition());
        });

    }

//Falls die Liste leer ist, dann soll nichts zurückgegeben werden, sonst soll der Inhalt
//ausgegeben werden.
    @Override
    public int getItemCount() {
        if (mListBestellungen != null) {
            return mListBestellungen.size();
        }
        return 0;
    }

//Damit man weiß, um welches Objekt es sich handelt, wird über die ID
//das Objekt aus der items_bestellungen.xml gewählt.
    public static class BestellungenViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView menge;

        public BestellungenViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_name);
            menge = itemView.findViewById(R.id.item_menge);
        }
    }
}
