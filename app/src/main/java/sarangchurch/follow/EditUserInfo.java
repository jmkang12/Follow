package sarangchurch.follow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditUserInfo extends AppCompatActivity implements View.OnClickListener {

    Button ButtonEditUserInfo;
    EditText editName,editBirth;
    boolean bool;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        editName = (EditText)findViewById(R.id.et_username);
        editBirth = (EditText)findViewById(R.id.et_userbirth);
        ButtonEditUserInfo = (Button)findViewById(R.id.btn_file);
        ButtonEditUserInfo.setOnClickListener(this);



    }




    public void onClick(View v) {

        if(v==ButtonEditUserInfo){
            String name = editName.getText().toString().trim();
            String birth = editBirth.getText().toString().trim();
            MakeUserInfo mui = new MakeUserInfo();
            mui.WriteFile(name, birth);
            Log.e(mui.getName(),mui.getBirth());
            Intent intent = new Intent(getApplicationContext(),Loading.class);
            startActivity(intent);
        }

    }
}
