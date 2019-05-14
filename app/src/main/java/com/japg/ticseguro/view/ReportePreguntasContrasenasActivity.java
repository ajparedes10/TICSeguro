package com.japg.ticseguro.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.japg.ticseguro.R;

import pl.droidsonroids.gif.GifImageView;

public class ReportePreguntasContrasenasActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    boolean alreadyVisitedActivity = false;

    int puntos;

    TextView tituloReporte;
    GifImageView gifReporte;
    TextView descripcionReporte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_preguntas_contrasenas);
        Toolbar toolbar = findViewById(R.id.reporte_contrasenas_toolbar);
        setSupportActionBar(toolbar);

        tituloReporte = findViewById(R.id.titulo_reporte_preguntas_contrasenas);
        gifReporte = findViewById(R.id.gif_reporte_preguntas_contrasenas);
        descripcionReporte = findViewById(R.id.descripcion_reporte_preguntas_contrasenas);

        checkConnection();

    }

    @Override
    protected void onStart() {
        super.onStart();

        puntos = getIntent().getExtras().getInt("PUNTOS");

        if (puntos == 3)
        {
            tituloReporte.setText(R.string.titulo_muy_bien);
            gifReporte.setImageResource(R.drawable.muy_bien);
            descripcionReporte.setText(R.string.descripcion_muy_bien_contrasenas);
        }
        else if (puntos == 2)
        {
            tituloReporte.setText(R.string.titulo_vas_por_buen_camino);
            gifReporte.setImageResource(R.drawable.vas_por_buen_camino);
            descripcionReporte.setText(R.string.descripcion_vas_por_buen_camino_contrasenas);
        }
        else if (puntos == 1 || puntos == 0)
        {
            tituloReporte.setText(R.string.titulo_sigue_esforzandote);
            gifReporte.setImageResource(R.drawable.sigue_esforzandote);
            descripcionReporte.setText(R.string.descripcion_sigue_esforzandote_contrasenas);
        }

    }

    public void seguirAprendiendo(View view) {

        Intent seguirAprendiendoIntent = new Intent(ReportePreguntasContrasenasActivity.this, ContrasenasActivity.class);
        startActivity(seguirAprendiendoIntent);

    }

    public void volverAInicio(View view) {

        Intent volverAInicioIntent = new Intent(ReportePreguntasContrasenasActivity.this, MainMenuActivity.class);
        startActivity(volverAInicioIntent);

    }

    public void buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No tienes conexión a Internet");
        builder.setMessage("Algunas de las funcionalidades de la app pueden estar desactivadas. Por favor, conéctate lo más pronto a Internet.");

        builder.setPositiveButton("Entendido", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        }).show();

    }

    public void buildDialogInternetRestablished(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("¡Ya tienes conexión a Internet!");
        builder.setMessage("Disfruta de TICSeguro.");

        builder.setPositiveButton("Entendido", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        }).show();

    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showInternetConnectionMessage(isConnected);
    }

    private void showInternetConnectionMessage(boolean isConnected) {

        if (isConnected)
        {
            if (alreadyVisitedActivity)
            {
                buildDialogInternetRestablished(ReportePreguntasContrasenasActivity.this);
            }

            alreadyVisitedActivity = true;
        }
        else
        {
            // Se necesita para que se muestre el mensaje de reconexión si el usuario abrió la app sin conexión a Internet
            alreadyVisitedActivity = true;

            buildDialog(ReportePreguntasContrasenasActivity.this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showInternetConnectionMessage(isConnected);
    }

}
