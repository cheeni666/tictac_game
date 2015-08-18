package com.myprey.tictac;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().getBooleanExtra("vsCPU", false))
        {
            finish();
            finish();
            Intent i=new Intent(this,Game.class);
            startActivity(i);
        }
        if (getIntent().getBooleanExtra("PvP", false))
        {
            finish();
            finish();
            Intent i=new Intent(this,Pvp.class);
            startActivity(i);
        }
        if (getIntent().getBooleanExtra("M", false))
        {
            finish();
            finish();
            Intent i=new Intent(this,Choose.class);
            startActivity(i);
        }
        if (getIntent().getBooleanExtra("EXIT", false))
        {
            finish();
            finish();
        }
        Image v=new Image(this);
    }
}
