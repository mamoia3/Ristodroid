package controllers.ui.menu;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ristodroid.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import controllers.Dashboard;
import controllers.MainActivity;
import controllers.Utility;
import model.Dish;
import model.Order;
import model.OrderDetail;
import model.Variation;

public class VariationFragment extends Fragment {
    private BottomNavigationView navMenu;
    private TextView minusText;
    private TextView plusText;
    private RecyclerView minusRecyclerView;
    private RecyclerView variationsRecyclerView;
    private List<Variation> minusVariations;
    private List<Variation> plusVariations;
    private FloatingActionButton confirm;
    private Dish dish;
    private int quantity;

    public VariationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            dish = getArguments().getParcelable("KEY_DISH_BUNDLE");
            quantity = getArguments().getInt("QUANTITY");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_variation, container, false);
        confirm = root.findViewById(R.id.button_confirmVariations);
        minusText = root.findViewById(R.id.minusText);
        plusText = root.findViewById(R.id.plusText);

        View dashboardView = getActivity().findViewById(R.id.dashboardView);
        navMenu = dashboardView.findViewById(R.id.nav_view);

        LinearLayoutManager minusLinearLayoutManager = new LinearLayoutManager(getContext());
        minusRecyclerView = root.findViewById(R.id._minusvariation_recycler_view);
        minusVariations = Variation.getVariations(getContext(), dish.getCategory().getId(), dish.getIngredientDishes(), Variation.MINUS_VARIATION);
        VariationRecyclerViewAdapter minusAdapter = new VariationRecyclerViewAdapter(minusVariations, getContext(), Variation.MINUS_VARIATION);

        if (minusVariations.size() > 0) {
            minusRecyclerView.setLayoutManager(minusLinearLayoutManager);
            minusRecyclerView.setAdapter(minusAdapter);
            minusRecyclerView.setHasFixedSize(true); //cardview hanno tutte le stesse dimensioni
        } else {
            minusRecyclerView.setVisibility(View.GONE);
            minusText.setVisibility(View.GONE);
        }

        LinearLayoutManager plusLinearLayoutManager = new LinearLayoutManager(getContext());
        variationsRecyclerView = root.findViewById(R.id.variation_recycler_view);
        plusVariations = Variation.getVariations(getContext(), dish.getCategory().getId(), dish.getIngredientDishes(), Variation.PLUS_VARIATION);
        VariationRecyclerViewAdapter plusAdapter = new VariationRecyclerViewAdapter(plusVariations, getContext(), Variation.PLUS_VARIATION);

        if (plusVariations.size() > 0) {
            variationsRecyclerView.setLayoutManager(plusLinearLayoutManager);
            variationsRecyclerView.setAdapter(plusAdapter);
            variationsRecyclerView.setHasFixedSize(true); //cardview hanno tutte le stesse dimensioni
        } else {
            variationsRecyclerView.setVisibility(View.GONE);
            plusText.setVisibility(View.GONE);
        }


        confirm.setOnClickListener(v -> {
            OrderDetail orderDetail = new OrderDetail(MainActivity.getOrder().getId(), dish, quantity);
            orderDetail.setVariationMinusList(minusAdapter.getVariationsMinusOrder());
            orderDetail.setVariationPlusList(plusAdapter.getVariationsPlusOrder());
            MainActivity.getOrder().addToOrder(orderDetail);

            Snackbar.make(navMenu, R.string.addDishToOrder, Snackbar.LENGTH_LONG).setAnchorView(navMenu).show();
            Utility.setSummaryBadge(navMenu);

            Bundle bundle = new Bundle();
            bundle.putInt("id", dish.getCategory().getId());
            bundle.putString("category", dish.getCategory().getName());
                    Navigation.findNavController(getView())
                            .navigate(R.id.action_navigation_variation_to_dish_fragment, bundle);
        });

        return root;
    }
}
