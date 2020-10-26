package model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import persistence.RistodroidDBSchema;
import persistence.SqLiteDb;

public class Variation {
    private int id;
    private String name;
    private double price;
    private List<CategoryVariation> categoryVariations;
    private List<VariationDishOrder> variationDishOrders;

    public Variation(int id, String name, double price, List<CategoryVariation> categoryVariations, List<VariationDishOrder> variationDishOrders) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryVariations = categoryVariations;
        this.variationDishOrders = variationDishOrders;
    }

    public static ArrayList<Variation> getVariations(Context context, int category) {

        ArrayList<Variation> variations = new ArrayList<>();

        SQLiteDatabase db = new SqLiteDb(context).getReadableDatabase();

        String queryVariationTable = "SELECT * FROM " + RistodroidDBSchema.VariationTable.NAME + " INNER JOIN "
                + RistodroidDBSchema.CategoryVariationTable.NAME + " ON "
                + RistodroidDBSchema.CategoryVariationTable.Cols.VARIATION + " = "
                + RistodroidDBSchema.VariationTable.Cols.ID
                + " WHERE " + RistodroidDBSchema.CategoryVariationTable.Cols.CATEGORY + "=" + category;


        Cursor variationsCursor = db.rawQuery(queryVariationTable, null);

        while (variationsCursor.moveToNext()) {
            int id = variationsCursor.getInt(variationsCursor.getColumnIndex(RistodroidDBSchema.VariationTable.Cols.ID));
            String name = variationsCursor.getString(variationsCursor.getColumnIndex(RistodroidDBSchema.VariationTable.Cols.NAME));
            double price = variationsCursor.getDouble(variationsCursor.getColumnIndex(RistodroidDBSchema.VariationTable.Cols.PRICE));

            variations.add(new Variation(id, name, price, null, null));
        }

        variationsCursor.close();
        return variations;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public List<CategoryVariation> getCategoryVariations() {
        return categoryVariations;
    }

    public List<VariationDishOrder> getVariationDishOrders() {
        return variationDishOrders;
    }

    @Override
    public String toString() {
        return "Variation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Variation)) return false;
        Variation variation = (Variation) o;
        return id == variation.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
