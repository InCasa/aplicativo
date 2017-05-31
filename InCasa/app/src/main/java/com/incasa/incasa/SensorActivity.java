package com.incasa.incasa;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import model.User;

public class SensorActivity extends AppCompatActivity {
    String ip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        SharedPreferences mSharedPreferences = getSharedPreferences("ServerAdress", 0);
        this.ip = mSharedPreferences.getString("servidor", " ");

        final String URLTEMPERATURA = "http://"+ip+"/backend/temperaturaValor";
        final String URLUMIDADE = "http://"+ip+"/backend/umidadeValor";
        final String URLLUMINOSIDADE = "http://"+ip+"/backend/luminosidadeValor";
        final String URLPRESENCA = "http://"+ip+"/backend/presencaValor";

        getTemperatura(URLTEMPERATURA);
        getUmidade(URLUMIDADE);
        getLuminosidade(URLLUMINOSIDADE);
        getPresenca(URLPRESENCA);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Id correspondente ao botão Up/Home da actionbar
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getTemperatura(String URLTEMPERATURA) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLTEMPERATURA, null, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {

            String temp = "";

            try {
                TextView txtTemp = (TextView) findViewById(R.id.txtTemp);
                temp = response.getString("valor");

                if(temp.equals("null")){
                    txtTemp.setText("N/A");
                }else {
                    temp =  temp + " ºC";
                    txtTemp.setText(temp);
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

    public void getUmidade(String URLUMIDADE) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLUMIDADE, null, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {

            String umi = "";

            try {
                TextView txtUmi = (TextView) findViewById(R.id.txtUmi);
                umi = response.getString("valor");

                if(umi.equals("null")){
                    txtUmi.setText("N/A");
                }else {
                    txtUmi.setText(umi);
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

    public void getLuminosidade(String URLLUMINOSIDADE) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLLUMINOSIDADE, null, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {

            String lumi = "";

            try {
                TextView txtLumi = (TextView) findViewById(R.id.txtLumi);
                lumi = response.getString("valor");

                if(lumi.equals("null")){
                    txtLumi.setText("N/A");
                }else {
                    lumi =  lumi + "%";
                    txtLumi.setText(lumi);
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
                    txtPresenca.setText("N/A");
                }else {
                    if(presenca.equals("1")) {
                        presenca =  "Detectado";
                        txtPresenca.setText(presenca);
                    } else {
                        presenca =  "Não Detectado";
                        txtPresenca.setText(presenca);
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
