package com.example.ristodroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import clientUi.MainClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_client = (Button) findViewById(R.id.btn_client);
        btn_client.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // definisco l'intenzione
                Intent MainClient = new Intent(getApplicationContext(), MainClient.class);
                // passo all'attivazione dell'activity MainClient.java
                startActivity(MainClient);
            }
        });
    }

}
