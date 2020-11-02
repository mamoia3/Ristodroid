package controllers.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ristodroid.R;

import java.util.List;

import controllers.Dashboard;
import model.Dish;

public class DishesFragment extends Fragment {

    private TextView textEmptyCategory;
    private int id;
    private String name;
    private RecyclerView dishesRecyclerView;
    private List<Dish> dishes;
    private DishRecyclerViewAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getInt("id");
        name = getArguments().getString("category");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_dishes, container, false);
        ((Dashboard) getActivity()).getSupportActionBar().setTitle(name);
        setHasOptionsMenu(true);
        dishesRecyclerView = root.findViewById(R.id.dishes_recycler_view);
        textEmptyCategory = root.findViewById(R.id.text_empty_category);

        dishes = Dish.getDishes(getContext(), id);

        if(dishes.size() != 0) {
            textEmptyCategory.setVisibility(View.GONE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        dishesRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new DishRecyclerViewAdapter(dishes, getContext());
        dishesRecyclerView.setAdapter(adapter);
        dishesRecyclerView.setHasFixedSize(true); //cardview hanno tutte le stesse dimensioni

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.menu_top, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchItem.setActionView(searchView);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchItem.setTitle(R.string.search);
        searchView.setQueryHint(getResources().getString(R.string.search));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                textEmptyCategory.setText(R.string.NoDishFound);
                if(adapter.getItemCount()< 1){
                    textEmptyCategory.setVisibility(View.VISIBLE);
                }else{
                    textEmptyCategory.setVisibility(View.GONE);
                }

                return false;
            }
        });
    }
}