package controllers.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ristodroid.R;

import java.util.List;

import controllers.Dashboard;
import model.Category;
import model.Dish;

public class DishesFragment extends Fragment {

    private TextView textEmptyCategory;
    private int id;
    private String name;
    private RecyclerView dishesRecyclerView;
    private List<Dish> dishes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getInt("id");
        name = getArguments().getString("category");
        ((Dashboard) getActivity()).getSupportActionBar().setTitle(name);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dishes, container, false);

        dishesRecyclerView = root.findViewById(R.id.dishes_recycler_view);
        textEmptyCategory = root.findViewById(R.id.text_empty_category);

        dishes = Dish.getDishes(getContext(), id);

        if(dishes.size() != 0) {
            textEmptyCategory.setVisibility(View.GONE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        dishesRecyclerView.setLayoutManager(linearLayoutManager);
        dishesRecyclerView.setAdapter(new DishRecyclerViewAdapter(dishes, getContext()));
        dishesRecyclerView.setHasFixedSize(true); //cardview hanno tutte le stesse dimensioni

        return root;
    }

}