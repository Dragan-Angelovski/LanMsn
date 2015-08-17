package mk.ukim.finki.mpip.lanmsn.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import mk.ukim.finki.mpip.lanmsn.R;


public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText mUsername;
    private Button mEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUsername = (EditText) findViewById(R.id.txtUsername);
        mEnter = (Button) findViewById(R.id.btnEnter);

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




    }
}
