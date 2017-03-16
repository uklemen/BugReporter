package com.example.klemen.bugreporter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainButtonActivity extends AppCompatActivity {

    public Button mainButton;
    WindowManager wm;
    LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startService(new Intent(MainButtonActivity.this,FloatingWindow.class));
        finish();
    }
}
