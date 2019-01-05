package sarangchurch.follow;

import android.Manifest;
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

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Loading extends AppCompatActivity {

    String loading[] = {"로","로 딩","로 딩 중"};
    TextView textView;
    Boolean bool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);



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
            temp.CompareName(MakeUserInfo.getName(),this);
            bool=CompareForLogin.bool;
            Log.e("check"," "+bool);
        }

        loading1();



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
                overridePendingTransition(0,0);


            }
        },500);
    }
}