package persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import org.json.JSONArray;
import org.json.JSONException;

import java.util.TreeMap;


public class LoadJson {
     public static void insertJsonIntoDb (TreeMap<String, JSONArray> tables, Context context) throws JSONException {
         SQLiteDatabase db = new SqLiteDb(context).getWritableDatabase();
         insertIntoAllergenicTable(tables, db);
         insertIntoDishTable(tables, db);
         insertIntoAllergenicDishTable(tables, db);
         insertIntoAvailabilityTable(tables, db);
         insertIntoCategoryTable(tables, db);
         insertIntoCategoryVariationTable(tables, db);
         insertIntoIngredientTable(tables, db);
         insertIntoIngredientDishTable(tables, db);
         insertIntoMenuTable(tables, db);
         insertIntoSeatTable(tables, db);
         insertIntoTableTable(tables, db);
         insertIntoVariationTable(tables, db);
     }

    private static void insertIntoVariationTable(TreeMap<String, JSONArray> tables, SQLiteDatabase db) throws JSONException {
        ContentValues contentValues;

        for(int i = 0; i<tables.get(RistodroidDBSchema.VariationTable.NAME).length(); i++) {
            contentValues = getContentVariationValue(tables, i);
            db.insert(RistodroidDBSchema.VariationTable.NAME, null, contentValues);
        }
    }
    private static ContentValues getContentVariationValue (TreeMap<String, JSONArray> tables, int count) throws JSONException {
        ContentValues contentValues = new ContentValues();
        JSONArray table = tables.get(RistodroidDBSchema.VariationTable.NAME);
        contentValues.put(RistodroidDBSchema.VariationTable.Cols.ID, table.getJSONObject(count).getInt(RistodroidDBSchema.VariationTable.Cols.ID));
        contentValues.put(RistodroidDBSchema.VariationTable.Cols.NAME, table.getJSONObject(count).getString(RistodroidDBSchema.VariationTable.Cols.NAME));
        contentValues.put(RistodroidDBSchema.VariationTable.Cols.PRICE, table.getJSONObject(count).getDouble(RistodroidDBSchema.VariationTable.Cols.PRICE));

        return contentValues;
    }

    private static void insertIntoTableTable(TreeMap<String, JSONArray> tables, SQLiteDatabase db) throws JSONException {
        ContentValues contentValues;

        for(int i = 0; i<tables.get(RistodroidDBSchema.TableTable.NAME).length(); i++) {
            contentValues = getContentTableValue(tables, i);
            db.insert(RistodroidDBSchema.TableTable.NAME, null, contentValues);
        }
    }
    private static ContentValues getContentTableValue (TreeMap<String, JSONArray> tables, int count) throws JSONException {
        ContentValues contentValues = new ContentValues();
        JSONArray table = tables.get(RistodroidDBSchema.TableTable.NAME);
        contentValues.put(RistodroidDBSchema.TableTable.Cols.ID, table.getJSONObject(count).getString(RistodroidDBSchema.TableTable.Cols.ID));

        return contentValues;
    }

    private static void insertIntoSeatTable(TreeMap<String, JSONArray> tables, SQLiteDatabase db) throws JSONException {
        ContentValues contentValues;

        for(int i = 0; i<tables.get(RistodroidDBSchema.SeatTable.NAME).length(); i++) {
            contentValues = getContentSeatValue(tables, i);
            db.insert(RistodroidDBSchema.SeatTable.NAME, null, contentValues);
        }
    }
    private static ContentValues getContentSeatValue (TreeMap<String, JSONArray> tables, int count) throws JSONException {
        ContentValues contentValues = new ContentValues();
        JSONArray table = tables.get(RistodroidDBSchema.SeatTable.NAME);
        contentValues.put(RistodroidDBSchema.SeatTable.Cols.ID, table.getJSONObject(count).getInt(RistodroidDBSchema.SeatTable.Cols.ID));
        contentValues.put(RistodroidDBSchema.SeatTable.Cols.NAME, table.getJSONObject(count).getString(RistodroidDBSchema.SeatTable.Cols.NAME));
        contentValues.put(RistodroidDBSchema.SeatTable.Cols.PRICE, table.getJSONObject(count).getDouble(RistodroidDBSchema.SeatTable.Cols.PRICE));

        return contentValues;
    }

    private static void insertIntoMenuTable(TreeMap<String, JSONArray> tables, SQLiteDatabase db) throws JSONException {
        ContentValues contentValues;

        for(int i = 0; i<tables.get(RistodroidDBSchema.MenuTable.NAME).length(); i++) {
            contentValues = getContentMenuValue(tables, i);
            db.insert(RistodroidDBSchema.MenuTable.NAME, null, contentValues);
        }
    }
    private static ContentValues getContentMenuValue (TreeMap<String, JSONArray> tables, int count) throws JSONException {
        ContentValues contentValues = new ContentValues();
        JSONArray table = tables.get(RistodroidDBSchema.MenuTable.NAME);
        contentValues.put(RistodroidDBSchema.MenuTable.Cols.ID, table.getJSONObject(count).getInt(RistodroidDBSchema.MenuTable.Cols.ID));
        contentValues.put(RistodroidDBSchema.MenuTable.Cols.NAME, table.getJSONObject(count).getString(RistodroidDBSchema.MenuTable.Cols.NAME));

        return contentValues;
    }

    private static void insertIntoIngredientDishTable(TreeMap<String, JSONArray> tables, SQLiteDatabase db) throws JSONException {
        ContentValues contentValues;

        for(int i = 0; i<tables.get(RistodroidDBSchema.IngredientDishTable.NAME).length(); i++) {
            contentValues = getContentIngredientDishValue(tables, i);
            db.insert(RistodroidDBSchema.IngredientDishTable.NAME, null, contentValues);
        }
    }
    private static ContentValues getContentIngredientDishValue (TreeMap<String, JSONArray> tables, int count) throws JSONException {
        ContentValues contentValues = new ContentValues();
        JSONArray table = tables.get(RistodroidDBSchema.IngredientDishTable.NAME);
        contentValues.put(RistodroidDBSchema.IngredientDishTable.Cols.DISH, table.getJSONObject(count).getInt(RistodroidDBSchema.IngredientDishTable.Cols.DISH));
        contentValues.put(RistodroidDBSchema.IngredientDishTable.Cols.INGREDIENT, table.getJSONObject(count).getInt(RistodroidDBSchema.IngredientDishTable.Cols.INGREDIENT));

        return contentValues;
    }

    private static void insertIntoIngredientTable(TreeMap<String, JSONArray> tables, SQLiteDatabase db) throws JSONException {
        ContentValues contentValues;

        for(int i = 0; i<tables.get(RistodroidDBSchema.IngredientTable.NAME).length(); i++) {
            contentValues = getContentIngredientValue(tables, i);
            db.insert(RistodroidDBSchema.IngredientTable.NAME, null, contentValues);
        }
    }
    private static ContentValues getContentIngredientValue (TreeMap<String, JSONArray> tables, int count) throws JSONException {
        ContentValues contentValues = new ContentValues();
        JSONArray table = tables.get(RistodroidDBSchema.IngredientTable.NAME);
        contentValues.put(RistodroidDBSchema.IngredientTable.Cols.ID, table.getJSONObject(count).getInt(RistodroidDBSchema.IngredientTable.Cols.ID));
        contentValues.put(RistodroidDBSchema.IngredientTable.Cols.NAME, table.getJSONObject(count).getString(RistodroidDBSchema.IngredientTable.Cols.NAME));

        return contentValues;
    }

    private static void insertIntoCategoryVariationTable(TreeMap<String, JSONArray> tables, SQLiteDatabase db) throws JSONException {
        ContentValues contentValues;

        for(int i = 0; i<tables.get(RistodroidDBSchema.CategoryVariationTable.NAME).length(); i++) {
            contentValues = getContentCategoryVariationValue(tables, i);
            db.insert(RistodroidDBSchema.CategoryVariationTable.NAME, null, contentValues);
        }
    }
    private static ContentValues getContentCategoryVariationValue (TreeMap<String, JSONArray> tables, int count) throws JSONException {
        ContentValues contentValues = new ContentValues();
        JSONArray table = tables.get(RistodroidDBSchema.CategoryVariationTable.NAME);
        contentValues.put(RistodroidDBSchema.CategoryVariationTable.Cols.VARIATION, table.getJSONObject(count).getInt(RistodroidDBSchema.CategoryVariationTable.Cols.VARIATION));
        contentValues.put(RistodroidDBSchema.CategoryVariationTable.Cols.CATEGORY, table.getJSONObject(count).getInt(RistodroidDBSchema.CategoryVariationTable.Cols.CATEGORY));

        return contentValues;
    }

    private static void insertIntoCategoryTable(TreeMap<String, JSONArray> tables, SQLiteDatabase db) throws JSONException {
        ContentValues contentValues;

        for(int i = 0; i<tables.get(RistodroidDBSchema.CategoryTable.NAME).length(); i++) {
            contentValues = getContentCategoryValue(tables, i);
            db.insert(RistodroidDBSchema.CategoryTable.NAME, null, contentValues);
        }
    }
    private static ContentValues getContentCategoryValue (TreeMap<String, JSONArray> tables, int count) throws JSONException {
        ContentValues contentValues = new ContentValues();
        JSONArray table = tables.get(RistodroidDBSchema.CategoryTable.NAME);
        contentValues.put(RistodroidDBSchema.CategoryTable.Cols.ID, table.getJSONObject(count).getInt(RistodroidDBSchema.CategoryTable.Cols.ID));
        contentValues.put(RistodroidDBSchema.CategoryTable.Cols.NAME, table.getJSONObject(count).getString(RistodroidDBSchema.CategoryTable.Cols.NAME));

        return contentValues;
    }

    private static void insertIntoAvailabilityTable(TreeMap<String, JSONArray> tables, SQLiteDatabase db) throws JSONException {
        ContentValues contentValues;

        for(int i = 0; i<tables.get(RistodroidDBSchema.AvailabilityTable.NAME).length(); i++) {
            contentValues = getContentAvailabilityValue(tables, i);
            db.insert(RistodroidDBSchema.AvailabilityTable.NAME, null, contentValues);
        }
    }
    private static ContentValues getContentAvailabilityValue (TreeMap<String, JSONArray> tables, int count) throws JSONException {
        ContentValues contentValues = new ContentValues();
        JSONArray table = tables.get(RistodroidDBSchema.AvailabilityTable.NAME);
        contentValues.put(RistodroidDBSchema.AvailabilityTable.Cols.MENU, table.getJSONObject(count).getInt(RistodroidDBSchema.AvailabilityTable.Cols.MENU));
        contentValues.put(RistodroidDBSchema.AvailabilityTable.Cols.DISH, table.getJSONObject(count).getInt(RistodroidDBSchema.AvailabilityTable.Cols.DISH));
        contentValues.put(RistodroidDBSchema.AvailabilityTable.Cols.STARTDATE, table.getJSONObject(count).getString(RistodroidDBSchema.AvailabilityTable.Cols.STARTDATE));
        contentValues.put(RistodroidDBSchema.AvailabilityTable.Cols.ENDDATE, table.getJSONObject(count).getString(RistodroidDBSchema.AvailabilityTable.Cols.ENDDATE));

        return contentValues;
    }

    private static void insertIntoAllergenicDishTable(TreeMap<String, JSONArray> tables, SQLiteDatabase db) throws JSONException {
        ContentValues contentValues;

        for(int i = 0; i<tables.get(RistodroidDBSchema.AllergenicDishTable.NAME).length(); i++) {
            contentValues = getContentAllergenicDishValue(tables, i);
            db.insert(RistodroidDBSchema.AllergenicDishTable.NAME, null, contentValues);
        }
    }
    private static ContentValues getContentAllergenicDishValue (TreeMap<String, JSONArray> tables, int count) throws JSONException {
        ContentValues contentValues = new ContentValues();
        JSONArray table = tables.get(RistodroidDBSchema.AllergenicDishTable.NAME);
        contentValues.put(RistodroidDBSchema.AllergenicDishTable.Cols.DISH, table.getJSONObject(count).getInt(RistodroidDBSchema.AllergenicDishTable.Cols.DISH));
        contentValues.put(RistodroidDBSchema.AllergenicDishTable.Cols.ALLERGENIC, table.getJSONObject(count).getInt(RistodroidDBSchema.AllergenicDishTable.Cols.ALLERGENIC));

        return contentValues;
    }

    private static void insertIntoAllergenicTable(TreeMap<String, JSONArray> tables, SQLiteDatabase db) throws JSONException {
        ContentValues contentValues;

        for(int i = 0; i<tables.get(RistodroidDBSchema.AllergenicTable.NAME).length(); i++) {
            contentValues = getContentAllergenicValue(tables, i);
            db.insert(RistodroidDBSchema.AllergenicTable.NAME, null, contentValues);
        }
    }
    private static ContentValues getContentAllergenicValue (TreeMap<String, JSONArray> tables, int count) throws JSONException {
        ContentValues contentValues = new ContentValues();
        JSONArray table = tables.get(RistodroidDBSchema.AllergenicTable.NAME);
        contentValues.put(RistodroidDBSchema.AllergenicTable.Cols.ID, table.getJSONObject(count).getInt(RistodroidDBSchema.AllergenicTable.Cols.ID));
        contentValues.put(RistodroidDBSchema.AllergenicTable.Cols.NAME, table.getJSONObject(count).getString(RistodroidDBSchema.AllergenicTable.Cols.NAME));

        return contentValues;
    }

    private static void insertIntoDishTable(TreeMap<String, JSONArray> tables, SQLiteDatabase db) throws JSONException {
         ContentValues contentValues;

         for(int i = 0; i<tables.get(RistodroidDBSchema.DishTable.NAME).length(); i++) {
             contentValues = getContentDishValue(tables, i);
             db.insert(RistodroidDBSchema.DishTable.NAME, null, contentValues);
         }
     }
    private static ContentValues getContentDishValue (TreeMap<String, JSONArray> tables, int count) throws JSONException {
         ContentValues contentValues = new ContentValues();
         JSONArray table = tables.get(RistodroidDBSchema.DishTable.NAME);
         contentValues.put(RistodroidDBSchema.DishTable.Cols.ID, table.getJSONObject(count).getInt(RistodroidDBSchema.DishTable.Cols.ID));
         contentValues.put(RistodroidDBSchema.DishTable.Cols.NAME, table.getJSONObject(count).getString(RistodroidDBSchema.DishTable.Cols.NAME));
         contentValues.put(RistodroidDBSchema.DishTable.Cols.DESCRIPTION, table.getJSONObject(count).getString(RistodroidDBSchema.DishTable.Cols.DESCRIPTION));
         contentValues.put(RistodroidDBSchema.DishTable.Cols.PRICE, table.getJSONObject(count).getDouble(RistodroidDBSchema.DishTable.Cols.PRICE));
         contentValues.put(RistodroidDBSchema.DishTable.Cols.CATEGORY, table.getJSONObject(count).getInt(RistodroidDBSchema.DishTable.Cols.CATEGORY));
         contentValues.put(RistodroidDBSchema.DishTable.Cols.PHOTO, table.getJSONObject(count).getString(RistodroidDBSchema.DishTable.Cols.PHOTO));

         return contentValues;
     }
}