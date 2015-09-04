package mk.ukim.finki.mpip.lanmsn.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mk.ukim.finki.mpip.lanmsn.R;

public class LoginActivity extends Activity implements View.OnClickListener {

    public static final String PREFS_NAME ="lanMsnPrefsFile";

    private Button btnLogin;
    private EditText txtUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtUsername = (EditText) findViewById(R.id.txtUsername);

        //Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String username = settings.getString("username","");
        txtUsername.setText(username);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==btnLogin.getId()){

            if(!txtUsername.getText().toString().equals("")) {

                Intent intent = new Intent(LoginActivity.this, WiFiServiceDiscoveryActivity.class);
                String username = txtUsername.getText().toString();
                SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("username",username);

                editor.commit();
                intent.putExtra("username", username);
                startActivity(intent);
            }else{
                Toast.makeText(LoginActivity.this,"You must pick username",Toast.LENGTH_LONG).show();
            }

        }

    }
}
