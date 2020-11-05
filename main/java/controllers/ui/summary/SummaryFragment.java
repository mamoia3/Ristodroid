package controllers.ui.summary;

import android.app.AlertDialog;
import android.content.Intent;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import controllers.MainActivity;
import model.Order;
import model.OrderDetail;
import nfc.SenderActivity;

public class SummaryFragment extends Fragment {

    private BottomNavigationView navMenu;
    private TextView emptySummary;
    private FloatingActionButton confirmButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_summary, container, false);

        boolean orderNotEmpty = MainActivity.getOrder()!=null && MainActivity.getOrder().getOrderDetails().size()>0;

        emptySummary = root.findViewById(R.id.text_empty_summary);

        View dashboardView = getActivity().findViewById(R.id.dashboardView);
        navMenu= dashboardView.findViewById(R.id.nav_view);
        confirmButton = root.findViewById(R.id.button_CloseOrder);

        if(orderNotEmpty){
            emptySummary.setVisibility(View.GONE);

            if(!MainActivity.getOrder().isConfirmed()){
                confirmButton.setVisibility(View.VISIBLE);
            }


            RecyclerView summaryRecyclerView = root.findViewById(R.id.summary_recycler_view);

            List<OrderDetail> details = MainActivity.getOrder().getOrderDetails();

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            summaryRecyclerView.setLayoutManager(linearLayoutManager);

            SummaryRecycleViewAdapter adapter = new SummaryRecycleViewAdapter(details,getContext());
            summaryRecyclerView.setAdapter(adapter);

            summaryRecyclerView.setHasFixedSize(true); //cardview hanno tutte le stesse dimensioni

            summaryRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                    if (dy > 0)
                        confirmButton.hide();
                    else if (dy < 0)
                        confirmButton.show();
                }
            });

            adapter.setOnItemClickListener(new SummaryRecycleViewAdapter.manageClickOnButtonCard() {
                @Override
                public void onDeleteClick(int position) {
                    details.remove(position);
                    summaryRecyclerView.removeViewAt(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position,details.size());

                    setSummaryBadge(navMenu);

                    if(details.size() == 0){
                        manageVisibilityOrderEmpty();
                    }
                }

                @Override
                public void onAddQuantityClick(int position) {
                    details.get(position).setQuantity(details.get(position).getQuantity() + 1);
                    adapter.notifyDataSetChanged();
                    setSummaryBadge(navMenu);
                    Snackbar.make(root,R.string.addDishToOrder,Snackbar.LENGTH_SHORT).show();
                }

                @Override
                public void onRemoveQuantityClick(int position) {
                    int oldQuantity = details.get(position).getQuantity();

                    if((oldQuantity - 1) > 0){
                        details.get(position).setQuantity(oldQuantity - 1);
                        adapter.notifyDataSetChanged();
                    }else{
                        details.remove(position);
                        summaryRecyclerView.removeViewAt(position);
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(position,details.size());

                        if(details.size() == 0){
                            manageVisibilityOrderEmpty();
                        }
                    }
                    Snackbar.make(root,R.string.removeDishToOrder,Snackbar.LENGTH_SHORT).show();
                    setSummaryBadge(navMenu);
                }
            });


            if(!MainActivity.getOrder().isConfirmed()){
                confirmButton.setOnClickListener(v-> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(R.string.closeOrderTitle);
                    builder.setMessage(R.string.confirmOrderMessage);
                    builder.setIcon(R.drawable.check);
                    builder.setPositiveButton(R.string.ok, (dialog, which) -> {
                        MainActivity.getOrder().setConfirmed(true);
                        //Chiamo attraverso l'adapter il metodo che aggiorna la recycle view in modo
                        //da aggiornare tutti i button rendendoli non visibili
                        adapter.HideButtonsAfterConfirm();
                        this.confirmButton.setVisibility(View.INVISIBLE);
                        setSummaryBadge(navMenu);
                        String json = Order.convertToJson(MainActivity.getOrder());
                        Intent intent = new Intent(getContext(), SenderActivity.class);
                        intent.putExtra("order", json);
                        //startActivity(intent);
                    });
                    builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                });
            }

        }else {
            emptySummary.setText(R.string.emptySummary);
        }

        return root;
    }

    private void manageVisibilityOrderEmpty (){
        emptySummary.setText(R.string.emptySummary);
        emptySummary.setVisibility(View.VISIBLE);
        confirmButton.setVisibility(View.GONE);
    }

    protected void setSummaryBadge (BottomNavigationView navMenu) {
        boolean orderNotNull = MainActivity.getOrder()!=null;
        if(orderNotNull) {
            if((MainActivity.getOrder().getOrderDetails().size() == 0) ||(MainActivity.getOrder().isConfirmed())){
                navMenu.removeBadge(R.id.navigation_summary);
            }else {
                navMenu.getOrCreateBadge(R.id.navigation_summary).setNumber(OrderDetail.getTotalQuantity(MainActivity.getOrder().getOrderDetails()));
            }
        }
    }

}