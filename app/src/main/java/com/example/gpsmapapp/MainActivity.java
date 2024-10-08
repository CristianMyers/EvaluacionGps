package com.example.gpsmapapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.MapsInitializer;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int PERMISSION_REQUEST_CODE = 100; // Código para los permisos
    private MapView mapView;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar el MapView
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        // Inicializar Google Maps
        MapsInitializer.initialize(this);  // Asegúrate de que los servicios de Google Maps estén inicializados

        mapView.getMapAsync(this);

        // Verificar y solicitar permisos de ubicación si no están concedidos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        // Verificar nuevamente si se concedieron los permisos antes de habilitar la ubicación
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);  // Habilitar ubicación del usuario en el mapa
        } else {
            // Solicitar nuevamente los permisos si no están disponibles
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }

    // Manejar la respuesta del usuario al solicitar permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, habilitar la funcionalidad de ubicación
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    googleMap.setMyLocationEnabled(true);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}

