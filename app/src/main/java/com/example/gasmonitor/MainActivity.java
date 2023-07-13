package com.example.gasmonitor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog loadingDialog; // Diálogo de carga
    private ImageButton button; // Botón para mostrar la ventana emergente
    private ProgressBar progressBar; // Barra de progreso
    private static final int REQUEST_PHONE_CALL = 1; // Código de solicitud para realizar una llamada telefónica

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBarGas);
        button = findViewById(R.id.llamarButton);

        int progress = progressBar.getProgress();
        if (progress <= 20) {
            button.setVisibility(View.VISIBLE); // Mostrar el botón si el progreso es menor o igual a 20
        } else {
            button.setVisibility(View.INVISIBLE); // Ocultar el botón si el progreso es mayor a 20
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberPickerDialog(v); // Mostrar la ventana emergente al hacer clic en el botón
            }
        });

        // Mostrar la pantalla de carga
        showLoadingDialog();

        // Simular una tarea de carga
        simulateLoadingTask();
    }

    public void showNumberPickerDialog(View view) {
        final String[] numberOptions = {"Z Gas", "Gas Tomza", "Vela Gas"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccione a tu gasera")
                .setItems(numberOptions, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedOption = numberOptions[which];
                        String phoneNumber = getPhoneNumber(selectedOption);
                        makePhoneCall(phoneNumber);
                    }
                });
        builder.create().show();
    }

    private String getPhoneNumber(String selectedOption) {
        String phoneNumber = "";

        switch (selectedOption) {
            case "Z Gas":
                phoneNumber = "3173820277";
                break;
            case "Gas Tomza":
                phoneNumber = "3173814000";
                break;
            case "Vela Gas":
                phoneNumber = "3331510550";
                break;
        }
        return phoneNumber;
    }

    private void makePhoneCall(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void showLoadingDialog() {
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage("Opteniendo datos del medidor..."); // Mensaje mostrado en el diálogo de carga
        loadingDialog.setCancelable(false); // El diálogo de carga no es cancelable por el usuario
        loadingDialog.show();
    }

    private void simulateLoadingTask() {
        // Simular una tarea de carga
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Ocultar la pantalla de carga
                hideLoadingDialog();

                // Continuar con la carga del contenido principal
                loadMainContent();
            }
        }, 3000); // Simular una carga de 3 segundos
    }

    private void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    private void loadMainContent() {
        // Cargar el contenido de los datos de Firebase
    }

}