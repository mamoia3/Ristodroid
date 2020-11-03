package controllers.ui.receipt;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ristodroid.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import controllers.MainActivity;
import controllers.Utility;
import controllers.ui.summary.SummaryRecycleViewAdapter;
import model.Order;
import model.OrderDetail;
import model.Seat;

public class ReceiptFragment extends Fragment {

    private BottomNavigationView navMenu;
    private TextView emptyReceipt;
    private CardView cardViewTotal, cardViewSeat;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_receipt, container, false);
        TextView emptyReceipt = root.findViewById(R.id.text_receipt_not_available);
        View dashboardView = getActivity().findViewById(R.id.dashboardView);
        navMenu= dashboardView.findViewById(R.id.nav_view);
        setSummaryBadge(navMenu);

        String url = "https://www.sabersolutions.it/ristodroid/getOrder.php";
        //getJsonOrder(url);

        MainActivity.getOrder().setSeat(new Seat(1, "Coperto cena",2.00));
        MainActivity.getOrder().setSeatNumber(5);

        if(MainActivity.getOrder().getSeat() != null && MainActivity.getOrder().getSeatNumber()!=0) {

            //Order prova = MainActivity.getOrder();

            emptyReceipt.setVisibility(View.GONE);
            RecyclerView receiptRecyclerView = root.findViewById(R.id.receipt_recycler_view);
            List<OrderDetail> details = MainActivity.getOrder().getOrderDetails();

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            receiptRecyclerView.setLayoutManager(linearLayoutManager);

            ReceiptRecycleViewAdapter adapter = new ReceiptRecycleViewAdapter(details, getContext());

            receiptRecyclerView.setAdapter(adapter);

            receiptRecyclerView.setHasFixedSize(true); //cardview hanno tutte le stesse dimensioni
            showCardReceipt(root);

        }else{
            emptyReceipt.setText(R.string.receiptAvailable);
            emptyReceipt.setVisibility(View.VISIBLE);
        }
        return root;
    }

    public void getJsonOrder(String url){

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,response -> {
            try{
                JSONObject jsonOrderData = response.getJSONObject("id_order");
                MainActivity.getOrder().setSeatNumber(jsonOrderData.getInt("seat_number"));
                String type_seat = jsonOrderData.getJSONObject("seat").getString("name");
                Double price_seat = jsonOrderData.getJSONObject("seat").getDouble("price");
                int id_seat = jsonOrderData.getJSONObject("seat").getInt("id");
                MainActivity.getOrder().setSeat(new Seat(id_seat,type_seat,price_seat));


            }catch(JSONException e){
                e.printStackTrace();
            }


        }, error -> {
            Toast toast= Toast.makeText(getContext(),R.string.OrderDataRequestFailed,Toast.LENGTH_LONG);
            toast.show();
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id_order",MainActivity.getOrder().getId());

                return params;
            }
        };

        Volley.newRequestQueue(getContext());
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


    private void showCardReceipt(View root){
        cardViewSeat = root.findViewById(R.id.cardview_seat);
        cardViewTotal = root.findViewById(R.id.cardview_total);
        cardViewSeat.setVisibility(View.VISIBLE);
        cardViewTotal.setVisibility(View.VISIBLE);

        TextView seatDescription = root.findViewById(R.id.text_receipt_seat);
        seatDescription.setText(MainActivity.getOrder().getSeat().getName());

        String quantitySeat = "x " + MainActivity.getOrder().getSeatNumber();
        TextView seatQuantity= root.findViewById(R.id.receipt_text_quantity_seat);
        seatQuantity.setText(quantitySeat);

        String euro = Currency.getInstance(Locale.GERMANY).getSymbol() + " ";
        String totalSeat = euro + Utility.priceToString(MainActivity.getOrder().getSeatNumber() * MainActivity.getOrder().getSeat().getPrice());
        TextView seatPrice = root.findViewById(R.id.receipt_text_seat_price);
        seatPrice.setText(totalSeat);

        TextView totalDescription = root.findViewById(R.id.text_receipt_Total);
        totalDescription.setText(R.string.total);

        String totalPay = euro + Utility.priceToString(OrderDetail.getTotalReceipt(MainActivity.getOrder().getOrderDetails()) +
                MainActivity.getOrder().getSeatNumber() * MainActivity.getOrder().getSeat().getPrice() );
        TextView totalPayText = root.findViewById(R.id.receipt_text_total_price);
        totalPayText.setText(totalPay);

    }


}