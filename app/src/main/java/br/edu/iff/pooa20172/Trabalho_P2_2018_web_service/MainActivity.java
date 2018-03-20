package br.edu.iff.pooa20172.Trabalho_P2_2018_web_service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void BuscarMarcas(View v){
        Intent intent = new Intent(this,MarcaActivity.class);
        startActivity(intent);
    }
}
