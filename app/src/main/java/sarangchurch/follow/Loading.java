package sarangchurch.follow;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Loading extends AppCompatActivity {

    String loading[] = {"로","로 딩","대학8부"};
    TextView textView;
    Boolean bool;
    public  static Activity activity;
    private final long FINSH_INTERVAL_TIME    = 2000;
    private long  backPressedTime        = 0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        activity=this;


        getWindow().getDecorView().setSystemUiVisibility(

                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |

                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |

                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |

                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |

                        View.SYSTEM_UI_FLAG_FULLSCREEN |

                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        );
        textView = (TextView) findViewById(R.id.loading);


        MakeUserInfo.ReadFile();
        if(MakeUserInfo.getName()==null){
            bool=false;
        }
        else{
            bool=true;
        }

        if(bool){
            CompareForLogin temp = new CompareForLogin();
            temp.CompareName(MakeUserInfo.getName(),MakeUserInfo.getBirth(),this);
            bool=CompareForLogin.bool;
            Log.e("check"," "+bool);
        }

        loading1();

    }
    @Override
    public void onBackPressed() {
        long tempTime        = System.currentTimeMillis();
        long intervalTime    = tempTime - backPressedTime;

        if ( 0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime ) {
            super.onBackPressed();
        }
        else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(),"'뒤로'버튼을한번더누르시면종료됩니다.",Toast.LENGTH_SHORT).show();
        }
    }

    private  void loading1() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText(loading[0]);
                loading2();
            }
        },300);
    }
    private  void loading2() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText(loading[1]);
                loading3();
            }
        },300);
    }
    private  void loading3() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText(loading[2]);
                loading4();
            }
        },300);
    }
    private  void loading4() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);

            }
        },500);
    }
}