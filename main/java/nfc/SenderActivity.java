package nfc;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ristodroid.R;
import com.victor.loading.rotate.RotateLoading;

import controllers.Dashboard;
import controllers.MainActivity;
import model.Order;
import persistence.RistodroidDBSchema;
import persistence.SqLiteDb;


public class SenderActivity extends AppCompatActivity implements OutcomingNfcManager.NfcActivity {

    private TextView tvOutcomingMessage;
    private NfcAdapter nfcAdapter;
    private OutcomingNfcManager outcomingNfccallback;
    private String order;
    private RotateLoading loading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender);

        loading = findViewById(R.id.rotateloading);
        loading.start();

        SQLiteDatabase db = new SqLiteDb(getApplicationContext()).getReadableDatabase();
        Intent intent = getIntent();
        String key = intent.getExtras().getString("order");
        order = Order.getJsonOrderDb(db, key);
        db.delete(RistodroidDBSchema.JsonOrderTable.NAME, RistodroidDBSchema.JsonOrderTable.Cols.ID + "=?", new String[]{key});


        if (!isNfcSupported()) {
            Toast.makeText(this, R.string.nfc_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, R.string.nfc_disabled, Toast.LENGTH_SHORT).show();

             //Se nfc è disabilitato riapro l'ordine e torno all'app
            //per consentire di abilitare nfc e inviare l'ordine nuovamente
            MainActivity.getOrder().setConfirmed(false);
            startActivity(new Intent(this, Dashboard.class));
        }

        initViews();

        // encapsulate sending logic in a separate class
        this.outcomingNfccallback = new OutcomingNfcManager(this);
        this.nfcAdapter.setOnNdefPushCompleteCallback(outcomingNfccallback, this);
        this.nfcAdapter.setNdefPushMessageCallback(outcomingNfccallback, this);
    }

    private void initViews() {
        this.tvOutcomingMessage = findViewById(R.id.tv_out_label);
        this.tvOutcomingMessage.setText(R.string.label_NFC_SEND);
        setOutGoingMessage();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    private boolean isNfcSupported() {
        this.nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        return this.nfcAdapter != null;
    }

    private void setOutGoingMessage() {
        String outMessage = order;
    }

    @Override
    public String getOutcomingMessage() {
        return this.order;
    }

    @Override
    public void signalResult() {
        // this will be triggered when NFC message is sent to a device.
        // should be triggered on UI thread. We specify it explicitly
        // cause onNdefPushComplete is called from the Binder thread
        runOnUiThread(() -> {
                Toast.makeText(SenderActivity.this, R.string.message_beaming_complete, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Dashboard.class));
        });

    }
}