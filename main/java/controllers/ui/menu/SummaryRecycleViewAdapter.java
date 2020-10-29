package controllers.ui.menu;

import android.app.AlertDialog;
import android.content.Context;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ristodroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import controllers.MainActivity;
import controllers.Utility;
import controllers.ui.summary.SummaryFragment;
import controllers.ui.summary.UpdateSummaryRecycleView;
import model.Order;
import model.OrderDetail;
import model.Variation;

public class SummaryRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<OrderDetail> orderDetailsList;
    private final Context context;
    private manageClickOnButtonCard callManageMethod;

    public interface manageClickOnButtonCard{
        void onDeleteClick(int position);
        void onAddQuantityClick(int position);
        void onRemoveQuantityClick(int position);
    }


    public void setOnItemClickListener (manageClickOnButtonCard listener){
        callManageMethod = listener;
    }


    public SummaryRecycleViewAdapter(List<OrderDetail> orderDetailsList, Context context){
        this.orderDetailsList = orderDetailsList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.layout_row_summary, parent, false);
        return new SummaryRecycleViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final OrderDetail detail = orderDetailsList.get(position);
        String euro = Currency.getInstance(Locale.GERMANY).getSymbol() + " ";
        String quantity = detail.getQuantity() + " " + Utility.convertResourceIdToString(R.string.piece,context);
        String total = euro + Utility.priceToString(detail.getQuantity() * (detail.getDish().getPrice() + OrderDetail.getTotalPriceVariation(detail)));

        if(detail.getVariationPlusList().size()!= 0){
            String addVariationString = Utility.convertResourceIdToString(R.string.plusSymbol,context) + " "
                    + Variation.getVariationsToString(detail.getVariationPlusList());
            ((ViewHolder) holder).textAddVariation.setText(Utility.createIndentedText(addVariationString,0,28));
        }else{
        }

        if(detail.getVariationMinusList().size()!=0){
            String minusVariationString =  Utility.convertResourceIdToString(R.string.minusSymbol,context)+ " "
                    + Variation.getVariationsToString(detail.getVariationMinusList());
            ((ViewHolder) holder).textMinusVariation.setText(Utility.createIndentedText(minusVariationString,0,28));
        }else{
            ((ViewHolder) holder).textMinusVariation.setVisibility(View.GONE);
        }

        ((ViewHolder) holder).textDishTitle.setText(detail.getDish().getName());
        ((ViewHolder) holder).textQuantity.setText(quantity);
        ((ViewHolder) holder).textPrice.setText(total);
        ((ViewHolder) holder).dishImage.setImageBitmap(Utility.byteToBitmap(detail.getDish().getPhoto()));

        ((SummaryRecycleViewAdapter.ViewHolder) holder).buttonAddQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position != RecyclerView.NO_POSITION){
                    callManageMethod.onAddQuantityClick(position);
                }
            }
        });

        ((ViewHolder) holder).buttonRemoveQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position != RecyclerView.NO_POSITION){
                    callManageMethod.onRemoveQuantityClick(position);
                }
            }
        });

        ((SummaryRecycleViewAdapter.ViewHolder) holder).buttonDeleteDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.deleteDishMessage);
                //builder.setMessage("Piatto aggiunto al carrello");
                builder.setIcon(R.drawable.alert_circle_outline);
                builder.setPositiveButton(R.string.ok, (dialog, which) -> {

                    /*Recupero la position della riga su cui ho cliccato ed invoco tramite l'interfacci
                    il metodo onDeleteClick implentato nel fragment che ospita la RecycleVicew*/
                    if(position != RecyclerView.NO_POSITION){
                        callManageMethod.onDeleteClick(position);
                    }

                });
                builder.setNegativeButton(R.string.cancel, (dialog, which) ->{
                    Toast.makeText(context,"Annullato",Toast.LENGTH_LONG).show();

                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderDetailsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textDishTitle;
        private final TextView textAddVariation;
        private final TextView textMinusVariation;
        private final TextView textQuantity;
        private final TextView textPrice;
        private final ImageView dishImage;
        private final Button buttonAddQuantity;
        private final Button buttonRemoveQuantity;
        private final Button buttonDeleteDish;
        private final LinearLayout orderDetailsLinearLayout;
        private final CardView row;
        private final RecyclerView recycleView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textDishTitle = itemView.findViewById(R.id.text_summary_dish_title);
            textAddVariation = itemView.findViewById(R.id.summary_text_add_variations);
            textMinusVariation = itemView.findViewById(R.id.summary_text_minus_variations);
            textQuantity = itemView.findViewById(R.id.summary_text_quantity_summary);
            textPrice = itemView.findViewById(R.id.summary_text_dish_price);
            dishImage = itemView.findViewById(R.id.summary_image_dish);
            buttonAddQuantity = itemView.findViewById(R.id.summary_btn_add_quantity);
            buttonRemoveQuantity = itemView.findViewById(R.id.summary_btn_remove_quantity);
            buttonDeleteDish = itemView.findViewById(R.id.summary_btn_delete_dish);
            orderDetailsLinearLayout = itemView.findViewById(R.id.orderDetails_linearlayout);
            row = itemView.findViewById(R.id.cardview_list_order_dish);
            recycleView = itemView.findViewById(R.id.summary_recycler_view);
        }
    }
}
