package com.example.gomoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
    private Button startpvp,settings,startpve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setListen();
    }
    private void setListen() {
        startpvp = (Button) findViewById(R.id.startpvp);
        settings = (Button) findViewById(R.id.settings);
        startpve = (Button) findViewById(R.id.startpve);

        startpvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startpvp();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings();
            }
        });
        startpve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startpve();
            }
        });
    }

    private void startpvp() {
        Intent i = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(i);
    }
    private void settings() {
        Toast.makeText(this,"DISABLED",Toast.LENGTH_SHORT).show();
    }
    private void startpve() {
        Toast.makeText(this,"DISABLED",Toast.LENGTH_SHORT).show();
    }
}
