package model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import controllers.Utility;
import persistence.RistodroidDBSchema;
import persistence.SqLiteDb;

public class Dish {

    private int id;
    private String name;
    private String description;
    private double price;
    private byte[] photo;
    private Category category;
    private List<Ingredient> ingredientDishes;
    private List<Allergenic> allergenicDishes;
    private List<OrderDetail> orderDetails;
    private List<Availability> availabilities;

    public Dish(int id, String name, String description, double price, byte[] photo, Category category,
                List<Ingredient> ingredientDishes, List<Allergenic> allergenicDishes,
                List<OrderDetail> orderDetails, List<Availability> availabilities) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.photo = photo;
        this.category = category;
        this.ingredientDishes = ingredientDishes;
        this.allergenicDishes = allergenicDishes;
        this.orderDetails = orderDetails;
        this.availabilities = availabilities;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Category getCategory() {
        return category;
    }

    public List<Ingredient> getIngredientDishes() {
        return ingredientDishes;
    }

    public List<Allergenic> getAllergenicDishes() {
        return allergenicDishes;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public static ArrayList<Dish> getDishes(Context context, int idCategoryRequest) {

        ArrayList<Dish> dishes = new ArrayList<>();

        String currentDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());

        SQLiteDatabase db = new SqLiteDb(context).getReadableDatabase();

        String queryDishAvailabilityTable = "SELECT "
                + RistodroidDBSchema.DishTable.NAME + "." + RistodroidDBSchema.DishTable.Cols.ID + ","
                + RistodroidDBSchema.DishTable.NAME + "." + RistodroidDBSchema.DishTable.Cols.NAME + " AS dishname,"
                + RistodroidDBSchema.DishTable.NAME + "." + RistodroidDBSchema.DishTable.Cols.DESCRIPTION + ","
                + RistodroidDBSchema.DishTable.NAME + "." + RistodroidDBSchema.DishTable.Cols.PRICE + ","
                + RistodroidDBSchema.DishTable.NAME + "." + RistodroidDBSchema.DishTable.Cols.PHOTO + ","
                + RistodroidDBSchema.DishTable.NAME + "." + RistodroidDBSchema.DishTable.Cols.CATEGORY + ","
                + RistodroidDBSchema.CategoryTable.NAME + "." + RistodroidDBSchema.CategoryTable.Cols.NAME

                + " FROM " + RistodroidDBSchema.DishTable.NAME + " INNER JOIN "
                + RistodroidDBSchema.CategoryTable.NAME + " ON "
                + RistodroidDBSchema.DishTable.NAME + "." + RistodroidDBSchema.DishTable.Cols.CATEGORY + " = "
                + RistodroidDBSchema.CategoryTable.NAME + "." + RistodroidDBSchema.CategoryTable.Cols.ID
                + " WHERE " + RistodroidDBSchema.DishTable.NAME + "." + RistodroidDBSchema.DishTable.Cols.CATEGORY + "=" + idCategoryRequest + " AND "
                + RistodroidDBSchema.DishTable.NAME + "." + RistodroidDBSchema.DishTable.Cols.ID + " IN "
                + "(SELECT " + RistodroidDBSchema.AvailabilityTable.Cols.DISH
                + " FROM " + RistodroidDBSchema.AvailabilityTable.NAME
                + " WHERE (" + RistodroidDBSchema.AvailabilityTable.Cols.ENDDATE + " IS NULL OR "
                + RistodroidDBSchema.AvailabilityTable.Cols.ENDDATE + ">='" + currentDate + "') AND "
                + RistodroidDBSchema.AvailabilityTable.Cols.STARTDATE + "<='" + currentDate +"')";

        String queryIngredientTable = "SELECT * " +
                "FROM " +
                RistodroidDBSchema.IngredientDishTable.NAME + " INNER JOIN " +
                RistodroidDBSchema.IngredientTable.NAME + " ON " +
                RistodroidDBSchema.IngredientDishTable.NAME + "." + RistodroidDBSchema.IngredientDishTable.Cols.INGREDIENT + "=" +
                RistodroidDBSchema.IngredientTable.NAME + "." + RistodroidDBSchema.IngredientTable.Cols.ID;

        String queryAllergenicTable = "SELECT * " +
                "FROM " +
                RistodroidDBSchema.AllergenicDishTable.NAME + " INNER JOIN " +
                RistodroidDBSchema.AllergenicTable.NAME + " ON " +
                RistodroidDBSchema.AllergenicDishTable.NAME + "." + RistodroidDBSchema.AllergenicDishTable.Cols.ALLERGENIC + "=" +
                RistodroidDBSchema.AllergenicTable.NAME + "." + RistodroidDBSchema.AllergenicTable.Cols.ID;


        Cursor dishesCursor = db.rawQuery(queryDishAvailabilityTable, null);
        Cursor ingredientCursor = db.rawQuery(queryIngredientTable, null);
        Cursor allergenicCursor = db.rawQuery(queryAllergenicTable, null);

        while (dishesCursor.moveToNext()) {

            int id = dishesCursor.getInt(dishesCursor.getColumnIndex(RistodroidDBSchema.DishTable.NAME + "." + RistodroidDBSchema.DishTable.Cols.ID));
            String name = dishesCursor.getString(dishesCursor.getColumnIndex("dishname"));
            byte[] photo = dishesCursor.getBlob(dishesCursor.getColumnIndex(RistodroidDBSchema.DishTable.NAME + "." + RistodroidDBSchema.DishTable.Cols.PHOTO));
            double price = dishesCursor.getDouble(dishesCursor.getColumnIndex(RistodroidDBSchema.DishTable.NAME + "." + RistodroidDBSchema.DishTable.Cols.PRICE));
            String description = dishesCursor.getString(dishesCursor.getColumnIndex(RistodroidDBSchema.DishTable.NAME + "." + RistodroidDBSchema.DishTable.Cols.DESCRIPTION));
            int idCategory = dishesCursor.getInt(dishesCursor.getColumnIndex(RistodroidDBSchema.DishTable.NAME + "." + RistodroidDBSchema.DishTable.Cols.CATEGORY));
            String categoryName = dishesCursor.getString(dishesCursor.getColumnIndex(RistodroidDBSchema.CategoryTable.NAME + "." + RistodroidDBSchema.CategoryTable.Cols.NAME));

            Category myCategory = new Category(idCategory, categoryName, null);
            ArrayList<Ingredient> myIngredientDish = getIngredients(ingredientCursor, id);
            ArrayList<Allergenic> myAllergenicDish = getAllergenics(allergenicCursor, id);

            dishes.add(new Dish(id, name, description, price, photo, myCategory, myIngredientDish, myAllergenicDish, null, null));

        }

        allergenicCursor.close();
        dishesCursor.close();
        ingredientCursor.close();
        return dishes;
    }

    private static ArrayList<Ingredient> getIngredients(Cursor ingredientCursor, int id) {
        ArrayList<Ingredient> myIngredientDish = new ArrayList<>();
        while (ingredientCursor.moveToNext()) {

            int idDish = ingredientCursor.getInt(ingredientCursor.getColumnIndex(RistodroidDBSchema.IngredientDishTable.Cols.DISH));

            if (id == idDish) {
                Ingredient myNewIngredient = new Ingredient(
                        ingredientCursor.getInt(ingredientCursor.getColumnIndex(RistodroidDBSchema.IngredientTable.Cols.ID)),
                        ingredientCursor.getString(ingredientCursor.getColumnIndex(RistodroidDBSchema.IngredientTable.Cols.NAME)));

                myIngredientDish.add(myNewIngredient);
            }
        }
        ingredientCursor.moveToFirst();
        return myIngredientDish;
    }

    private static ArrayList<Allergenic> getAllergenics(Cursor allergenicCursor, int id) {
        ArrayList<Allergenic> myAllergenicDish = new ArrayList<>();
        while (allergenicCursor.moveToNext()) {
            int idDish = allergenicCursor.getInt(allergenicCursor.getColumnIndex(RistodroidDBSchema.AllergenicDishTable.Cols.DISH));

            if (id == idDish) {
                Allergenic myNewAllergenic = new Allergenic(
                        allergenicCursor.getInt(allergenicCursor.getColumnIndex(RistodroidDBSchema.AllergenicTable.Cols.ID)),
                        allergenicCursor.getString(allergenicCursor.getColumnIndex(RistodroidDBSchema.AllergenicTable.Cols.NAME)));
                myAllergenicDish.add(myNewAllergenic);
            }
        }
        allergenicCursor.moveToFirst();
        return myAllergenicDish;
    }

    public String getIngredientsToString (List<Ingredient> ingredients) {
        ArrayList<String> list = new ArrayList<>();
        for(int i=0; i<ingredients.size(); i++) {
            list.add(ingredients.get(i).getName());
        }
        return Utility.listToStringWithDelimiter(list, ", ");
    }

    public String getAllergenicsToString (List<Allergenic> allergenics) {
        ArrayList<String> list = new ArrayList<>();
        for(int i=0; i<allergenics.size(); i++) {
            list.add(allergenics.get(i).getName());
        }
        return Utility.listToStringWithDelimiter(allergenics, ", ");
    }
    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + category +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dish)) return false;
        Dish dish = (Dish) o;
        return id == dish.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
