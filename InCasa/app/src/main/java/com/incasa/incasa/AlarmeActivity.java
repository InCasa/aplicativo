package com.incasa.incasa;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Base64;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.incasa.incasa.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import model.User;

public class AlarmeActivity extends AppCompatActivity {
    String ip;
    boolean ativo;
    NotificationCompat.Builder Alerta;
    private static final int IdAlerta = 14022;

    Timer timer = new Timer();
    TimerTask AlarmeThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarme);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        final Switch AlarmeSwitch = (Switch) findViewById(R.id.switchAlarme);

        SharedPreferences mSharedPreferences = getSharedPreferences("ServerAdress", 0);
        this.ip = mSharedPreferences.getString("servidor", " ");

        if(getAlarme() != false){
            AlarmeSwitch.setChecked(true);
        }

        final String URLPRESENCA = "http://"+ip+"/backend/presencaValorNow";

        //Thread para checar se o modo alarme esta ativo a cada 3seg
        timer = new Timer();
        AlarmeThread = new TimerTask() {
            public void run() {
                getPresenca(URLPRESENCA);

                //checa se há presença e se o modo alarme esta ativo.
                if(ativo == true && getAlarme() == true){
                    //chama a notificação
                    Notificacao();
                }

            }
        };
        timer.schedule(AlarmeThread, 0, 3000);

        Alerta = new NotificationCompat.Builder(this);

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

    //Quando a activity for finalizada a thread é finalizada junto
    protected void onDestroy() {
        super.onDestroy();

        timer.cancel();
        timer.purge();
        AlarmeThread.cancel();
    }

    public void Notificacao(){
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Alerta.setSmallIcon(R.drawable.ic_add_alert_black_48dp);
        Alerta.setTicker("Movimento identificado");
        Alerta.setSound(soundUri);
        Alerta.setContentTitle("InCasa - Alarme");
        Alerta.setOnlyAlertOnce(true);
        Alerta.setContentText("Movimento identificado no sensor de presença!");

        Intent resultIntent = new Intent(this, AlarmeActivity.class);

        Intent intent = new Intent(this, AlarmeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Alerta.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(IdAlerta, Alerta.build());
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

    public void getPresenca(String URLPRESENCA) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLPRESENCA, null, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {

                String presenca = "";

                try {
                    TextView txtPresenca = (TextView) findViewById(R.id.txtPresenca);
                    presenca = response.getString("valor");

                    if(presenca.equals("null")){
                        ativo = false;
                    }else {
                        if(presenca.equals("1")) {
                          ativo = true;
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            //Em caso de erro
            @Override
            public void onErrorResponse(VolleyError error) {
                Context context = getApplicationContext();
                CharSequence text = "Erro na requisição";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                // add headers <key,value>
                User user = User.getInstancia();
                String auth = new String(Base64.encode((user.getLogin() + ":" + user.getSenha()).getBytes(), Base64.DEFAULT));
                headers.put("Authorization ", " Basic " + auth);
                return headers;
            }

        };

        //fila de requisições
        RequestQueue fila = Volley.newRequestQueue(this);

        //Adiciona a requisição á fila de requisições
        fila.add(req);
    }
}
