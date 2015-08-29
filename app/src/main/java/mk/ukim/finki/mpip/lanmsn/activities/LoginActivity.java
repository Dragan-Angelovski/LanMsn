package mk.ukim.finki.mpip.lanmsn.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mk.ukim.finki.mpip.lanmsn.R;
import mk.ukim.finki.mpip.lanmsn.network.NetworkChangeReciever;


public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText mUsername;
    private Button mEnter;
    private NetworkChangeReciever mNetworkChangeReceiver;
    private IntentFilter mIntentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUsername = (EditText) findViewById(R.id.txtUsername);
        mEnter = (Button) findViewById(R.id.btnEnter);
        mNetworkChangeReceiver = new NetworkChangeReciever();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        mIntentFilter.addAction("android.net.wifi.STATE_CHANGE");
        mEnter.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mNetworkChangeReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mNetworkChangeReceiver);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {

        if(v.getId()==mEnter.getId()){

            enterMessanger();
        }

    }

    private void enterMessanger(){


        Intent intent = new Intent(this,DevicesListActivity.class);
        intent.putExtra("username",mUsername.getText().toString());
        startActivityForResult(intent,1);


    }

}
