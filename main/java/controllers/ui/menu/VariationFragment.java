package controllers.ui.menu;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ristodroid.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import controllers.Dashboard;
import controllers.MainActivity;
import model.Dish;
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
        navMenu= dashboardView.findViewById(R.id.nav_view);


        LinearLayoutManager minusLinearLayoutManager = new LinearLayoutManager(getContext());
        minusRecyclerView = root.findViewById(R.id._minusvariation_recycler_view);
        minusVariations = Variation.getVariations(getContext(), dish.getCategory().getId(), dish.getIngredientDishes(), Variation.MINUS_VARIATION);
        VariationRecyclerViewAdapter minusAdapter = new VariationRecyclerViewAdapter(minusVariations, getContext(), Variation.MINUS_VARIATION);

        if(minusVariations.size()>0) {
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

        if(plusVariations.size()>0) {
            variationsRecyclerView.setLayoutManager(plusLinearLayoutManager);
            variationsRecyclerView.setAdapter(plusAdapter);
            variationsRecyclerView.setHasFixedSize(true); //cardview hanno tutte le stesse dimensioni
        } else {
            variationsRecyclerView.setVisibility(View.GONE);
            plusText.setVisibility(View.GONE);
        }

        confirm.setOnClickListener(v -> {
            OrderDetail orderDetail = new OrderDetail(MainActivity.getOrder(), dish, quantity);
            orderDetail.setVariationMinusList(minusAdapter.getVariationsMinusOrder());
            orderDetail.setVariationPlusList(plusAdapter.getVariationsPlusOrder());
            MainActivity.getOrder().addOrderDetail(orderDetail);

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Inserito");
            builder.setMessage("Piatto aggiunto al carrello");
            builder.setIcon(R.drawable.check);
            builder.setPositiveButton(R.string.ok, (dialog, which) -> {
                setSummaryBadge(navMenu);

                Navigation.findNavController(getView())
                        .navigate(R.id.action_navigation_variation_to_navigation_menu);
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        return root;
    }

    protected void setSummaryBadge(BottomNavigationView navMenu) {
        boolean orderNotNull = MainActivity.getOrder()!=null && MainActivity.getOrder().getOrderDetails().size()>0;
        if(orderNotNull) {
            navMenu.getOrCreateBadge(R.id.navigation_summary).setNumber(OrderDetail.getTotalQuantity(MainActivity.getOrder().getOrderDetails()));
        }
    }
}