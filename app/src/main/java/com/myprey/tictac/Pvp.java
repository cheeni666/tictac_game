package com.myprey.tictac;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class Pvp extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pvp);
        if (getIntent().getBooleanExtra("BACK", false))
        {
            finish();
            finish();
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
        }
        if (getIntent().getBooleanExtra("RE", false))
        {
            finish();
            finish();
            Intent i=new Intent(this,Pvp.class);
            startActivity(i);
        }

        PEngine v=new PEngine(this);
    }

}
