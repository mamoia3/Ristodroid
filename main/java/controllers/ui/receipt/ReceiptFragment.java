package controllers.ui.receipt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ristodroid.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import controllers.MainActivity;
import controllers.Utility;
import model.OrderDetail;
import model.Seat;

public class ReceiptFragment extends Fragment {

    private TextView emptyReceipt;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_receipt, container, false);
        emptyReceipt = root.findViewById(R.id.text_receipt_not_available);
        View dashboardView = getActivity().findViewById(R.id.dashboardView);
        BottomNavigationView navMenu = dashboardView.findViewById(R.id.nav_view);
        Utility.setSummaryBadge(navMenu);

        if(MainActivity.getOrder() != null){

            String url = "https://www.sabersolutions.it/ristodroid/getOrder.php?id_order=" + MainActivity.getOrder().getId() +
                    "&lang=" + Locale.getDefault().getLanguage();
            getJsonOrder(url,root);

        }else{
            showReceiptNotAvailable();
        }
        return root;
    }

    private void getJsonOrder(String url, View view){

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try{
                JSONArray jsonOrderData = response.getJSONArray("order_information");

                if(jsonOrderData.length()>0) {
                    JSONObject jsonObjectOrderInformation = jsonOrderData.getJSONObject(0);
                    MainActivity.getOrder().setSeatNumber(jsonObjectOrderInformation.getInt("seatnumber"));

                    int id_seat = jsonObjectOrderInformation.getInt("id");
                    String type_seat = jsonObjectOrderInformation.getString("name");
                    double price_seat = jsonObjectOrderInformation.getDouble("price");
                    MainActivity.getOrder().setSeat(new Seat(id_seat, type_seat, price_seat));
                }

                onResponseFromServer(view);

            }catch(JSONException e){
                e.printStackTrace();
            }

        }, error -> {
            Toast toast= Toast.makeText(getContext(),R.string.OrderDataRequestFailed,Toast.LENGTH_LONG);
            toast.show();
        });

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }


    private void showCardReceipt(View root) {
        CardView cardViewSeat = root.findViewById(R.id.cardview_seat);
        CardView cardViewTotal = root.findViewById(R.id.cardview_total);
        cardViewSeat.setVisibility(View.VISIBLE);
        cardViewTotal.setVisibility(View.VISIBLE);

        TextView seatDescription = root.findViewById(R.id.text_receipt_seat);
        seatDescription.setText(MainActivity.getOrder().getSeat().getName());

        String quantitySeat = "x " + MainActivity.getOrder().getSeatNumber();
        TextView seatQuantity = root.findViewById(R.id.receipt_text_quantity_seat);
        seatQuantity.setText(quantitySeat);

        String euro = Currency.getInstance(Locale.GERMANY).getSymbol() + " ";
        String totalSeat = euro + Utility.priceToString(MainActivity.getOrder().getSeatNumber() * MainActivity.getOrder().getSeat().getPrice());
        TextView seatPrice = root.findViewById(R.id.receipt_text_seat_price);
        seatPrice.setText(totalSeat);

        TextView totalDescription = root.findViewById(R.id.text_receipt_Total);
        totalDescription.setText(R.string.total);

        String totalPay = euro + Utility.priceToString(OrderDetail.getTotalReceipt(MainActivity.getOrder().getOrderDetails()) +
                MainActivity.getOrder().getSeatNumber() * MainActivity.getOrder().getSeat().getPrice());
        TextView totalPayText = root.findViewById(R.id.receipt_text_total_price);
        totalPayText.setText(totalPay);
    }


    private void showReceiptNotAvailable(){
        emptyReceipt.setVisibility(View.VISIBLE);
    }


    private void onResponseFromServer(View root){
        if(MainActivity.getOrder().getSeatNumber()!=0 && MainActivity.getOrder().getSeat() != null) {

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
            showReceiptNotAvailable();
        }
    }
}
