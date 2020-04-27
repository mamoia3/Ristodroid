package clientUi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ristodroid.R;

public class MainClient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_client);




        Button btn_menu = (Button) findViewById(R.id.btn_menu);
        btn_menu.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // definisco l'intenzione
                Intent restaurant_main_activity = new Intent(getApplicationContext(), RestaurantMenuActivity.class);
                // passo all'attivazione dell'activity RestaurantMenuActivity.java
                startActivity(restaurant_main_activity);
            }
        });


    }


}
