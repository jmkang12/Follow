package sarangchurch.follow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonAddItem,buttonReadItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAddItem = (Button)findViewById(R.id.btn_add_item);
        buttonReadItems = (Button)findViewById(R.id.btn_read_item);
        buttonAddItem.setOnClickListener(this);
        buttonReadItems.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v==buttonAddItem){

            Intent intent = new Intent(getApplicationContext(),Additem.class);
            startActivity(intent);
        }
        if(v==buttonReadItems){

            Intent intent = new Intent(getApplicationContext(),Listitem.class);
            startActivity(intent);
        }

    }
}