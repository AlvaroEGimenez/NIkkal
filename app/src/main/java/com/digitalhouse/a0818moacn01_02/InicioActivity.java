package com.digitalhouse.a0818moacn01_02;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class InicioActivity extends AppCompatActivity {
    private ImageView imageViewLogo;
    long duracion = 2000;
    public static int MILISEGUNDOS_ESPERA = 4000;
    CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Animation animationSlice;
        animationSlice = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slice_down);
        imageViewLogo = findViewById(R.id.nikkal);
        TextView textViewLetra = findViewById(R.id.nikkalLetra);

        final Button button = findViewById(R.id.botonoffline);
        coordinatorLayout = findViewById(R.id.cordinator);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            AnimacionVertical(imageViewLogo);
            textViewLetra.setAnimation(animationSlice);
        } else {
            animacionHorizontal(imageViewLogo);
            textViewLetra.setAnimation(animationSlice);
        }


        final Intent intent = new Intent(InicioActivity.this, MainActivity.class);

        if (verificarConexion()) {
            esperarYCerrar(MILISEGUNDOS_ESPERA);
        } else {
            button.setVisibility(View.VISIBLE);
            Snackbar.make(coordinatorLayout, "Verificar la conexion a internet", Snackbar.LENGTH_LONG).show();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(intent);
                }
            });

        }
    }

    private boolean verificarConexion() {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();

        return (actNetInfo != null && actNetInfo.isConnected());
    }

    public void esperarYCerrar(Integer milisegundos) {
        final Intent intent = new Intent(InicioActivity.this, MainActivity.class);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(intent);

            }
        }, milisegundos);
    }

    public void AnimacionVertical(View view) {
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageViewLogo, "y", 530f);
        animatorY.setDuration(duracion);
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(imageViewLogo, View.ALPHA, 0.0f, 1.0f);
        alphaAnimation.setDuration(duracion);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY, alphaAnimation);
        animatorSet.start();
    }

    public void animacionHorizontal(View view) {
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageViewLogo, "y", 300);
        animatorY.setDuration(duracion);
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(imageViewLogo, View.ALPHA, 0.0f, 1.0f);
        alphaAnimation.setDuration(duracion);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY, alphaAnimation);
        animatorSet.start();
    }
}
