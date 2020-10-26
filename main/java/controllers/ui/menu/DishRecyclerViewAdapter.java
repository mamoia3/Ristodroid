package controllers.ui.menu;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class DishRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Dish> dishes;
    private final Context context;


    public DishRecyclerViewAdapter(List<Dish> dishes, Context context) {
        this.dishes = dishes;
        this.context = context;
    }


    //facciamo l'inflate (gonfiaggio) lo riportiamo sul ViewHolder -> grazie al quale andrà a richiamare i vari componenti
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.layout_row_menu_dish, parent, false);
        return new DishRecyclerViewAdapter.ViewHolder(v);
    }

    //imposta gli oggetti presi dalla lista popolata da classi "category"
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        String euro = Currency.getInstance(Locale.GERMANY).getSymbol() + " ";
        final Dish dish = dishes.get(position);
        ((DishRecyclerViewAdapter.ViewHolder) holder).dishTitle.setText(dish.getName());
        ((DishRecyclerViewAdapter.ViewHolder) holder).dishIngredient.setText(Ingredient.getIngredientsToString(dish.getIngredientDishes()));
        ((DishRecyclerViewAdapter.ViewHolder) holder).dishPrice.setText(euro + Utility.priceToString(dish.getPrice()));
        ((DishRecyclerViewAdapter.ViewHolder) holder).dishImage.setImageBitmap(Utility.byteToBitmap(dish.getPhoto()));
        ((DishRecyclerViewAdapter.ViewHolder) holder).dishesLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v)
                        .navigate(R.id.action_dishesFragment_to_navigation_dish_details);
            }
        });

    }

    //restituisce la dimensione della lista
    @Override
    public int getItemCount() {
        return dishes.size();
    }

    //definiamo il viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView dishTitle;
        private final TextView dishIngredient;
        private final TextView dishPrice;
        private final ImageView dishImage;
        private final LinearLayout dishesLinearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dishTitle = itemView.findViewById(R.id.text_dish_title);
            dishIngredient = itemView.findViewById(R.id.text_dish_ingredients);
            dishPrice = itemView.findViewById(R.id.text_dish_price);
            dishImage = itemView.findViewById(R.id.image_menu_dish);
            dishesLinearLayout = itemView.findViewById(R.id.dishes_linearlayout);
        }


    }

}
