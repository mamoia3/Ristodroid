package controllers.ui.menu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ristodroid.R;

import java.sql.SQLException;
import java.util.List;

import model.Category;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Category> categories;
    private Context context;

    public CategoryRecyclerViewAdapter(List<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    //facciamo l'inflate (gonfiaggio) lo riportiamo sul ViewHolder -> grazie al quale andrà a richiamare i vari componenti
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.layout_row_menu_category, parent, false);
        return new ViewHolder(v);
    }

    //imposta gli oggetti presi dalla lista popolata da classi "category"
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
         Category category = categories.get(position);
        ((ViewHolder) holder).categoryTitle.setText(category.getName());
            ((ViewHolder) holder).categoryImage.setImageBitmap(byteToBitmap(category.getPhoto()));
        ((ViewHolder) holder).categoriesLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO implementare click categorie
            }
        });
    }

    //restituisce la dimensione della lista
    @Override
    public int getItemCount() {
        return categories.size();
    }

    //definiamo il viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryTitle;
        private ImageView categoryImage;
        private LinearLayout categoriesLinearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.text_menu_category);
            categoryImage = itemView.findViewById(R.id.image_menu_category);
            categoriesLinearLayout = itemView.findViewById(R.id.categories_linearlayout);
        }
    }

    private static Bitmap byteToBitmap(byte[] blob) {
        blob = Base64.decode(blob, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(blob, 0, blob.length);
    }

}
