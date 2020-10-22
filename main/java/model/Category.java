package model;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ristodroid.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import persistence.RistodroidDBSchema;
import persistence.SqLiteDb;

public class Category {

    private int id;
    private String name;
    private byte[] photo;
    private List<Dish> dishes;
    private List<CategoryVariation> categoryVariations;


    public Category(int id, String name, byte[] photo, List<Dish> dishes, List<CategoryVariation> categoryVariations) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.dishes = dishes;
        this.categoryVariations = categoryVariations;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public List<CategoryVariation> getCategoryVariations() {
        return categoryVariations;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return id == category.id;
    }

    public static ArrayList<Category> getCategories(Context context) {
        ArrayList<Category> categories = new ArrayList<>();
        SQLiteDatabase db = new SqLiteDb(context).getReadableDatabase();
        String[] projection = {
                RistodroidDBSchema.CategoryTable.Cols.ID,
                RistodroidDBSchema.CategoryTable.Cols.NAME,
                RistodroidDBSchema.CategoryTable.Cols.PHOTO
        };

        Cursor cursor = db.query(RistodroidDBSchema.CategoryTable.NAME, projection, null,
                null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(RistodroidDBSchema.CategoryTable.Cols.ID));
            String name = cursor.getString(cursor.getColumnIndex(RistodroidDBSchema.CategoryTable.Cols.NAME));
            byte[] photo = cursor.getBlob(cursor.getColumnIndex(RistodroidDBSchema.CategoryTable.Cols.PHOTO));
            categories.add(new Category(id, name, photo, null, null));
        }

        cursor.close();
        return categories;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
