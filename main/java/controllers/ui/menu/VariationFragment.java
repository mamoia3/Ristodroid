package controllers.ui.menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ristodroid.R;

import java.util.List;

import controllers.Dashboard;
import model.Dish;
import model.Variation;

public class VariationFragment extends Fragment {
    private int category;
    private RecyclerView variationsRecyclerView;
    private List<Variation> variations;


    public VariationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //category = getArguments().getInt("category");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_variation, container, false);
        variationsRecyclerView = root.findViewById(R.id.variation_recycler_view);
        variations = Variation.getVariations(getContext(), 4);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        variationsRecyclerView.setLayoutManager(linearLayoutManager);
        variationsRecyclerView.setAdapter(new VariationRecyclerViewAdapter(variations, getContext()));
        variationsRecyclerView.setHasFixedSize(true); //cardview hanno tutte le stesse dimensioni

        return root;
    }


}