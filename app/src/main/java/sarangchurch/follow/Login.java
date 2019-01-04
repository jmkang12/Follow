package sarangchurch.follow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button buttonLogin,buttonLogout;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonLogin = (Button)findViewById(R.id.Login);
        buttonLogout = (Button)findViewById(R.id.Logout);
        buttonLogout.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
    }

    public void onClick(View v) {
        if(v==buttonLogin){
            if(Listitem.bool){
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(getApplicationContext(),EditUserInfo.class);
                startActivity(intent);
            }
        }
        if(v==buttonLogout){
            Intent intent = new Intent(getApplicationContext(),EditUserInfo.class);
            startActivity(intent);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
