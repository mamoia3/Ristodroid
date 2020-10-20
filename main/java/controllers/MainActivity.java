package controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.Iterator;
import java.util.TreeMap;

import persistence.LoadJson;

public class MainActivity extends AppCompatActivity implements Iterable<String> {


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

    /**
     * Procedura per il caricamento del
     * @param url
     */
    private void getJsonResponse(String url) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonDb = response.getJSONObject("db");
                    TreeMap<String, JSONArray> tables = getDbTablesFromJson(jsonDb);
                    LoadJson.insertJsonIntoDb(tables, getApplicationContext());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ErrorDB", "download fallito");
                Toast toast= Toast.makeText(getApplicationContext(),"Sincronizzazione menu fallita",Toast. LENGTH_SHORT);
                toast.show();
            }
        });

        Volley.newRequestQueue(this).add(jsonRequest);
    }

    /**
     * Ritorna una mappa chiave (nome tabella) valore (row della rispettiva tabella) del db
     * @param db
     * @return tables
     * @throws JSONException
     */
    private TreeMap<String, JSONArray> getDbTablesFromJson(JSONObject db) throws JSONException {
        TreeMap<String, JSONArray> tables = new TreeMap<>();
        JSONArray keys = db.names();
        for(int i=0; i< db.length(); i++) {
            tables.put(keys.getString(i) ,db.getJSONArray(keys.getString(i)));
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
