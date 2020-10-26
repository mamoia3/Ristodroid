package controllers.ui.menu;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ristodroid.R;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import controllers.Utility;
import model.Dish;
import model.Ingredient;
import model.Variation;

public class VariationRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Variation> variations;
    private final Context context;


    public VariationRecyclerViewAdapter(List<Variation> variations, Context context) {
        this.variations = variations;
        this.context = context;
    }


    //facciamo l'inflate (gonfiaggio) lo riportiamo sul ViewHolder -> grazie al quale andrà a richiamare i vari componenti
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.layout_row_variation, parent, false);
        return new VariationRecyclerViewAdapter.ViewHolder(v);
    }

    //imposta gli oggetti presi dalla lista popolata da classi "category"
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        String euro = Currency.getInstance(Locale.GERMANY).getSymbol() + " ";
        final Variation variation = variations.get(position);
        ((VariationRecyclerViewAdapter.ViewHolder) holder).variationName.setText(variation.getName());
        ((VariationRecyclerViewAdapter.ViewHolder) holder).variationPrice.setText(euro + Utility.priceToString(variation.getPrice()));
        ((VariationRecyclerViewAdapter.ViewHolder) holder).plusButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((VariationRecyclerViewAdapter.ViewHolder) holder).variationPrice.setTextColor(Color.GREEN);
            }
        });
        ((ViewHolder) holder).minusButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((VariationRecyclerViewAdapter.ViewHolder) holder).variationPrice.setTextColor(Color.RED);
            }
        });





    }

    //restituisce la dimensione della lista
    @Override
    public int getItemCount() {
        return variations.size();
    }

    //definiamo il viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView variationName;
        private final TextView variationPrice;
        private final ImageButton plusButton;
        private final ImageButton minusButton;
        private final LinearLayout variationLinearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            variationName = itemView.findViewById(R.id.variation_name);
            variationPrice = itemView.findViewById(R.id.variation_price);
            plusButton = itemView.findViewById(R.id.plusButton);
            minusButton = itemView.findViewById(R.id.minusButton);
            variationLinearLayout = itemView.findViewById(R.id.variation_linearLayout);
        }


    }

}

