package com.incasa.incasa;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.incasa.incasa.R;

public class AlarmeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarme);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        final Switch AlarmeSwitch = (Switch) findViewById(R.id.switchAlarme);

        if(getAlarme() != false){
            AlarmeSwitch.setChecked(true);
        }

        //necessario colocar um thread para viver fazendo requisição sobre o sensor de presença e comparar se o getAlarme = true, caso isso ocorra é preciso disparar uma notificação.
        AlarmeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(getAlarme() == true){
                    saveAlarme(false);
                }else{
                    saveAlarme(true);
                }

        }

        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Id correspondente ao botão Up/Home da actionbar
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveAlarme(boolean status){
        SharedPreferences mSharedPreferences = getSharedPreferences("AlarmStatus", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean("Alarme", status);
        mEditor.apply();
    }

    public boolean getAlarme(){
        SharedPreferences mSharedPreferences = getSharedPreferences("AlarmStatus", MODE_PRIVATE);
        boolean status = mSharedPreferences.getBoolean("Alarme",false);
        return status;
    }
}
