package com.digitalhouse.a0818moacn01_02;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class InicioActivity extends AppCompatActivity {
    private ImageView imageView;
    long duracion=2000;
    public static int MILISEGUNDOS_ESPERA = 4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);


        imageView = findViewById(R.id.nikkal);
        final Button button = findViewById(R.id.botonoffline);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            handleAnimationVertical(imageView);
        }
        else {
            handleAnimationHorizontal(imageView);
        }



        final Intent intent = new Intent(InicioActivity.this,MainActivity.class);

        if (isNetDisponible()) {
            esperarYCerrar(MILISEGUNDOS_ESPERA);
        }
        else {
            Toast.makeText(InicioActivity.this,"Verificar la conexion a internet",Toast.LENGTH_LONG).show();
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(intent);
                }
            });
        }
    }

    private boolean isNetDisponible() {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();

        return (actNetInfo != null && actNetInfo.isConnected());
    }

    public void esperarYCerrar(Integer milisegundos) {
        final Intent intent = new Intent(InicioActivity.this,MainActivity.class);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {public void run() {
            startActivity(intent);

        }
        }, milisegundos);
    }

    public void handleAnimationVertical (View view){
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageView,"y",650f);
        animatorY.setDuration(duracion);
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(imageView,View.ALPHA,0.0f,1.0f);
        alphaAnimation.setDuration(duracion);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY,alphaAnimation);
        animatorSet.start();

    }
    public void handleAnimationHorizontal (View view){
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageView,"y",300);
        animatorY.setDuration(duracion);
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(imageView,View.ALPHA,0.0f,1.0f);
        alphaAnimation.setDuration(duracion);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY,alphaAnimation);
        animatorSet.start();

    }
}

