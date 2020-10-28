package controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ristodroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.Iterator;
import java.util.TreeMap;

import model.Order;
import persistence.LoadJson;
import persistence.SqLiteDb;

public class MainActivity extends AppCompatActivity implements Iterable<String> {
    private static Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = "https://www.sabersolutions.it/ristodroid/get.php";

        getJsonResponse(url);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        startActivity(new Intent(getApplicationContext(), Dashboard.class));

    }

    public static void setOrder(Order order) {
        MainActivity.order = order;
    }

    public static Order getOrder() {
        return order;
    }

    /**
     * Procedura per il caricamento del json nel db
     * @param url indirizzo per la richiesta GET
     */
    private void getJsonResponse(String url) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONObject jsonDb = response.getJSONObject("db");
                TreeMap<String, JSONArray> tables = getDbTablesFromJson(jsonDb);
                LoadJson.insertJsonIntoDb(tables, getApplicationContext());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast toast= Toast.makeText(getApplicationContext(),"Sincronizzazione menu fallita," +
                    " verrà visualizzato l'ultimo menu disponibile",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();

            /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.titleFailedConnectionDB);
            builder.setMessage(R.string.messageFailedConnectionDB);
            builder.setPositiveButton(R.string.ok, (dialog, which) -> {


            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            /*se non riesco a scaricare i dati, recupero il db locale
            SQLiteDatabase db = new SqLiteDb(getApplicationContext()).getWritableDatabase();*/
        });

        Volley.newRequestQueue(this).add(jsonRequest);
    }

    /**
     * Ritorna una mappa chiave (nome tabella) valore (row della rispettiva tabella) del db
     * @param db database
     * @return tables
     * @throws JSONException json exception
     */
    private TreeMap<String, JSONArray> getDbTablesFromJson(JSONObject db) throws JSONException {
        TreeMap<String, JSONArray> tables = new TreeMap<>();
        JSONArray keys = db.names();
        for(int i=0; i< db.length(); i++) {
            if (keys != null) {
                tables.put(keys.getString(i) ,db.getJSONArray(keys.getString(i)));
            }
        }
        return tables;
    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }


    @NonNull
    @Override
    public Iterator<String> iterator() {
        return new TreeMap<String, JSONArray>().keySet().iterator();
    }
}
