package controllers.ui.menu;

import android.content.Context;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ristodroid.R;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

import controllers.MainActivity;
import controllers.Utility;
import controllers.ui.summary.SummaryFragment;
import model.Order;
import model.OrderDetail;
import model.Variation;

public class SummaryRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<OrderDetail> orderDetailsList;
    private final Context context;

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

        ((ViewHolder) holder).buttonAddQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ((ViewHolder) holder).buttonRemoveQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ((ViewHolder) holder).buttonDeleteDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        private final ImageButton buttonAddQuantity;
        private final ImageButton buttonRemoveQuantity;
        private final ImageButton buttonDeleteDish;
        private final LinearLayout orderDetailsLinearLayout;


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
        }
    }
}
