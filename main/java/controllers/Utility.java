package controllers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

import model.Ingredient;

public class Utility {
    public static Bitmap byteToBitmap(byte[] blob) {
        blob = Base64.decode(blob, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(blob, 0, blob.length);
    }

    public static <T> String listToStringWithDelimiter (List<T> list, String delimiter) {
        return list.stream().map(Object::toString).collect(Collectors.joining(delimiter));
    }

    public static String priceToString(double price) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(price);
    }
}
