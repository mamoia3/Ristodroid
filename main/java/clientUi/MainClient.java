package clientUi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ristodroid.MainActivity;
import com.example.ristodroid.R;

public class MainClient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_client);

        // add fragment to mainClient
        FragmentMenu fragment = new FragmentMenu();
        FragmentManager manager = getSupportFragmentManager();

        manager.beginTransaction().add(R.id.tab_primipiatti,fragment).commit();


        Button btn_menu = (Button) findViewById(R.id.btn_menu);
        btn_menu.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // definisco l'intenzione
                Intent FragmentMenu = new Intent(MainClient.this, FragmentMenu.class);
                // passo all'attivazione dell'activity MainClient.java
                startActivity(FragmentMenu);
            }
        });


    }


}
