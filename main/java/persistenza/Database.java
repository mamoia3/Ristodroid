package com.example.ristodroid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public final static String DB_NAME = "ristodroid";
    public final static int VERSION = 1;

    private final static String TABLE_TYPE = "type";
    private final static String type_id = "id";
    private final static String type_type = "type";

    private final static String TABLE_USER = "user";
    private final static String user_id = "id";
    private final static String user_name = "name";
    private final static String user_type = "type";

    private final static String TABLE_DISHTYPE = "dishtype";
    private final static String dishtype_id = "id";
    private final static String dishtype_type = "type";

    private final static String TABLE_VARIATION = "variation";
    private final static String variation_id = "id";
    private final static String variation_name = "name";
    private final static String variation_price = "price";
    private final static String variation_type = "type";

    private final static String TABLE_DISH = "dish";
    private final static String dish_id = "id";
    private final static String dish_type = "type";
    private final static String dish_name = "name";
    private final static String dish_description = "description";
    private final static String dish_price = "price";

    private final static String TABLE_MENU = "menu";
    private final static String menu_id = "id";
    private final static String menu_name = "name";

    private final static String TABLE_AVAILABILITY = "availability";
    private final static String availability_dish = "dish";
    private final static String availability_menu = "menu";

    private final static String TABLE_ORDER = "order";
    private final static String order_id = "id";
    private final static String order_tagnfc = "tagnfc";
    private final static String order_time = "time";
    private final static String order_user = "user";
    private final static String order_dish = "dish";

    private final static String QUERY_CREATETABLE_TYPE =
            "" + "CREATE TABLE " + TABLE_TYPE + "(" +
                    type_id + " integer PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    type_type + " VARCHAR(20) NOT NULL);";

    private final static String QUERY_CREATETABLE_USER =
            "" + "CREATE TABLE " + TABLE_USER + "(" +
                    user_id + " integer PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    user_name + " VARCHAR(30) NOT NULL, " +
                    user_type + " integer NOT NULL, " +
                    "CONSTRAINT 'type' FOREIGN KEY (`type`) REFERENCES `type` (`id`) ON UPDATE CASCADE);";

    private final static String QUERY_CREATETABLE_DISHTYPE =
            "" + "CREATE TABLE " + TABLE_DISHTYPE + "(" +
                    dishtype_id + " integer PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    dishtype_type + " VARCHAR(255) NOT NULL);";

    private final static String QUERY_CREATETABLE_VARIATION =
            "" + "CREATE TABLE " + TABLE_VARIATION + "(" +
                    variation_id + " integer PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    variation_name + " VARCHAR(30) NOT NULL, " +
                    variation_price + " DECIMAL(10,2) NOT NULL, " +
                    variation_type + " integer NOT NULL, " +
                    "CONSTRAINT `fk_dishtype` FOREIGN KEY (`type`) REFERENCES `dishtype` (`id`) ON UPDATE CASCADE);";

    private final static String QUERY_CREATETABLE_DISH =
            "" + "CREATE TABLE " + TABLE_DISH + "(" +
                    dish_id + " integer PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    dish_type + " integer NOT NULL, " +
                    dish_name + " VARCHAR(50) NOT NULL, " +
                    dish_description + " VARCHAR(255) NOT NULL, " +
                    dish_price + " DECIMAL(7,2) NOT NULL, " +
                    "CONSTRAINT `fk_type` FOREIGN KEY (`type`) REFERENCES `dishtype` (`id`) ON UPDATE CASCADE);";

    private final static String QUERY_CREATETABLE_MENU =
            "" + "CREATE TABLE " + TABLE_MENU + "(" +
                    menu_id + " integer PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    menu_name + " VARCHAR(255) NOT NULL);";

    private final static String QUERY_CREATETABLE_AVAILABILITY =
            "" + "CREATE TABLE " + TABLE_AVAILABILITY + "(" +
                    availability_dish + " integer PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    availability_menu + " integer NOT NULL, " +
                    "CONSTRAINT `fk_dish` FOREIGN KEY (`dish`) REFERENCES `dish` (`id`) ON UPDATE CASCADE, " +
                    "CONSTRAINT `fk_menu` FOREIGN KEY (`menu`) REFERENCES `menu` (`id`) ON UPDATE CASCADE);";

    private final static String QUERY_CREATETABLE_ORDER =
            "" + "CREATE TABLE '" + TABLE_ORDER + "'(" +
                    order_id + " integer PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    order_tagnfc + " VARCHAR(20) NOT NULL, " +
                    order_time + " DATETIME NOT NULL, " +
                    order_user + " integer NOT NULL, " +
                    order_dish + " integer NOT NULL, " +
                    "CONSTRAINT `fk_availability` FOREIGN KEY (`dish`) REFERENCES `availability` (`dish`) ON UPDATE CASCADE, " +
                    "CONSTRAINT `fk_user` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON UPDATE CASCADE);";





    public Database(@Nullable Context context)  {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATETABLE_TYPE);
        db.execSQL(QUERY_CREATETABLE_USER);
        db.execSQL(QUERY_CREATETABLE_DISHTYPE);
        db.execSQL(QUERY_CREATETABLE_VARIATION);
        db.execSQL(QUERY_CREATETABLE_DISH);
        db.execSQL(QUERY_CREATETABLE_MENU);
        db.execSQL(QUERY_CREATETABLE_AVAILABILITY);
        db.execSQL(QUERY_CREATETABLE_ORDER);
    }

    private static String dropTable (String table_name) {
        return "DROP IF TABLE EXISTS " + table_name;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropTable(TABLE_TYPE));
        db.execSQL(dropTable(TABLE_USER));
        db.execSQL(dropTable(TABLE_DISHTYPE));
        db.execSQL(dropTable(TABLE_VARIATION));
        db.execSQL(dropTable(TABLE_DISH));
        db.execSQL(dropTable(TABLE_MENU));
        db.execSQL(dropTable(TABLE_AVAILABILITY));
        db.execSQL(dropTable(TABLE_ORDER));
        onCreate(db);
    }

    public void executeQuery(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
    }

    public Cursor getType() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * from " + TABLE_TYPE;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

}
