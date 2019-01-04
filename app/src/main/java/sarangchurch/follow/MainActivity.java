package sarangchurch.follow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonAddItem, buttonGetItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAddItem = (Button)findViewById(R.id.btn_add_item);
        buttonGetItems = (Button)findViewById(R.id.get_test);
        buttonAddItem.setOnClickListener(this);
        buttonGetItems.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v==buttonAddItem){

            Intent intent = new Intent(getApplicationContext(),Additem.class);
            startActivity(intent);
        }

        if(v==buttonGetItems){
           // GetItems getItems = new GetItems(this);
            Intent intent = new Intent(getApplicationContext(),GetItems.class);
            startActivity(intent);
        }

    }
}