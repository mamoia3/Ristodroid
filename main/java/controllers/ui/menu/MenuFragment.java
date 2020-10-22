package controllers.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ristodroid.R;

import java.util.ArrayList;
import java.util.List;

import model.Category;
import persistence.SqLiteDb;

public class MenuFragment extends Fragment {

    private RecyclerView categoriesRecyclerView;
    private List<Category> categories;
    ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        categoriesRecyclerView = root.findViewById(R.id.categories_recycler_view);
        categories = Category.getCategories(getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        categoriesRecyclerView.setLayoutManager(linearLayoutManager);
        categoriesRecyclerView.setAdapter(new CategoryRecyclerViewAdapter(categories, getContext()));
        categoriesRecyclerView.setHasFixedSize(true); //cardview hanno tutte le stesse dimensioni

        return root;
    }
}