package com.myprey.tictac;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class Mgame extends ActionBarActivity {
    private int server;
    private String ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mgame);
        if (getIntent().getBooleanExtra("RE", false))
        {
            finish();
            finish();
            Intent i=new Intent(this,Choose.class);
            startActivity(i);
        }
        if (getIntent().getBooleanExtra("BACK", false))
        {
            finish();
            finish();
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
        }
        server=getIntent().getIntExtra("server",0);
        if(server==0)ip=getIntent().getStringExtra("ipaddr");
        if(server==1) Toast.makeText(this,"SERVER mode"+server,Toast.LENGTH_SHORT).show();
        if(server==0) Toast.makeText(this,"CLIENT mode"+server+ip,Toast.LENGTH_SHORT).show();
        MEngine e=(MEngine)findViewById(R.id.surfaceView4);
        e.setter(server,ip,this);
    }

}
