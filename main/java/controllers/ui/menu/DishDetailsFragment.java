package controllers.ui.menu;

import android.app.AlertDialog;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.ristodroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.Currency;
import java.util.Locale;

import controllers.MainActivity;
import controllers.Utility;
import model.Allergenic;
import model.Dish;
import model.Ingredient;
import model.Order;


public class DishDetailsFragment extends Fragment {

    private TextView titleDish;
    private TextView descriptionDish;
    private TextView priceDish;
    private TextView titleIngredient;
    private TextView ingredientDish;
    private TextView titleAllergenic;
    private TextView allergenicDish;
    private ImageView imageView;
    private FloatingActionButton addButton;
    private int quantity;

    private Dish dish;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dish = getArguments().getParcelable("KEY_DISH_BUNDLE");
        if(MainActivity.getOrder()==null) {
            MainActivity.setOrder(new Order(null, null, 0));
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_dish_details, container, false);

        String euro = Currency.getInstance(Locale.GERMANY).getSymbol() + " ";

        imageView = root.findViewById(R.id.image_dish_details);
        imageView.setImageBitmap(Utility.byteToBitmap(dish.getPhoto()));

        titleDish = root.findViewById(R.id.text_title_dish_details);
        titleDish.setText(dish.getName());

        descriptionDish = root.findViewById(R.id.text_descption_dish_details);
        descriptionDish.setText(dish.getDescription());

        priceDish = root.findViewById(R.id.text_price_dish_details);
        priceDish.setText(euro + Utility.priceToString(dish.getPrice()));

        int numIngredient = dish.getIngredientDishes().size();
        Resources pluralResource = getResources();
        titleIngredient= root.findViewById(R.id.text_title_ingredient_list);
        titleIngredient.setText(pluralResource.getQuantityString(R.plurals.numberOfIngredients,numIngredient));

        ingredientDish = root.findViewById(R.id.text_list_ingrediets_dish_details);
        ingredientDish.setText(Ingredient.getIngredientsToString(dish.getIngredientDishes()));

        int numAllergenic = dish.getAllergenicDishes().size();
        titleAllergenic= root.findViewById(R.id.text_title_allergenic_list);
        titleAllergenic.setText(pluralResource.getQuantityString(R.plurals.numberOfAllergenic,numAllergenic));

        allergenicDish = root.findViewById(R.id.text_list_allergenic_dish_details);
        allergenicDish.setText(Allergenic.getAllergenicsToString(dish.getAllergenicDishes()));

        addButton = root.findViewById(R.id.button_addDishToOrder);
        addButton.setOnClickListener(v -> {
        numberDickerDialog();
        });

        return root;
    }

    private void numberDickerDialog(){

        NumberPicker numberPicker = new NumberPicker(getContext());
        numberPicker.setMaxValue(50);
        numberPicker.setMinValue(1);
        numberPicker.setWrapSelectorWheel(true);
        NumberPicker.OnValueChangeListener onValueChangeListener = (picker, oldVal, newVal) -> quantity = newVal;

        numberPicker.setOnValueChangedListener(onValueChangeListener);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setView(numberPicker);
        builder.setTitle(R.string.enterQuantityDish);

        builder.setPositiveButton(R.string.ok, (dialog, which) -> {
            Bundle bundle = new Bundle();
            final String key_dish_bundle = "KEY_DISH_BUNDLE";
            bundle.putParcelable(key_dish_bundle, dish);
            bundle.putInt("QUANTITY", quantity);
            Navigation.findNavController(getView())
                    .navigate(R.id.action_navigation_dish_details_to_navigation_variation, bundle);
        });

        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {

        });
        builder.show();
    }
}