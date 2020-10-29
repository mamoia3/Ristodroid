package controllers.ui.summary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ristodroid.R;

import java.util.List;

import controllers.Dashboard;
import controllers.MainActivity;
import controllers.ui.menu.DishRecyclerViewAdapter;
import controllers.ui.menu.SummaryRecycleViewAdapter;
import model.Dish;
import model.OrderDetail;

public class SummaryFragment extends Fragment {

    private TextView emptySummary;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_summary, container, false);

        boolean orderNotEmpty = MainActivity.getOrder()!=null && MainActivity.getOrder().getOrderDetails().size()>0;

        emptySummary = root.findViewById(R.id.text_empty_summary);
        if(orderNotEmpty){
            emptySummary.setVisibility(View.GONE);
            RecyclerView summaryRecyclerView = root.findViewById(R.id.summary_recycler_view);

            List<OrderDetail> details = MainActivity.getOrder().getOrderDetails();

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            summaryRecyclerView.setLayoutManager(linearLayoutManager);
            summaryRecyclerView.setAdapter(new SummaryRecycleViewAdapter(details, getContext()));
            summaryRecyclerView.setHasFixedSize(true); //cardview hanno tutte le stesse dimensioni

        }else {
            emptySummary.setText(R.string.emptySummary);
        }

        return root;
    }

}