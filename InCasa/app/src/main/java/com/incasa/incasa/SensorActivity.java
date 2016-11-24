package com.incasa.incasa;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SensorActivity extends AppCompatActivity {

    private final String URL = "http://httpbin.org/get";

    private final String URLTEMPERATURA = "http://192.168.1.33/backend/temperaturaValor";
    private final String URLUMIDADE = "https://192.168.1.33/backend/umidadeValor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        atualizaValores(null, 1, null);

        getTemperatura();
        getUmidade();

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Id correspondente ao botão Up/Home da actionbar
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getTemperatura() {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLTEMPERATURA, null, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {

                Context context = getApplicationContext();
                CharSequence text = "Sucesso na requisição Temperatura: ";
                int duration = Toast.LENGTH_SHORT;

                String temp = "";

                try {
                    temp = response.getString("content");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast toast = Toast.makeText(context, text + temp, duration);
                toast.show();

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
        });

        //fila de requisições
        RequestQueue fila = Volley.newRequestQueue(this);

        //Adiciona a requisição á fila de requisições
        fila.add(req);

    }

    public void getUmidade() {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLUMIDADE, null, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {

                Context context = getApplicationContext();
                CharSequence text = "Sucesso na requisição Umidade";
                int duration = Toast.LENGTH_SHORT;

                String umi = "";

                try {
                    umi = response.getString("content");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast toast = Toast.makeText(context, text + umi, duration);
                toast.show();
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
        });

        //fila de requisições
        RequestQueue fila = Volley.newRequestQueue(this);

        //Adiciona a requisição á fila de requisições
        fila.add(req);
    }

    //Metodo responsável pelas requisições dos valores dos sensores
    //metodo cod:
    //1 GET
    //2 POST
    //3 PUT
    //4 DELETE
    public void atualizaValores(String url, int metodo, JSONObject corpo) {

        //Parametros JsonObjectRequest:
        //1- Metodo da requisição
        //2- url do servidor
        //3- Metodo para sucesso da requisição
        //4- Metodo para falha na requisição
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {

                Context context = getApplicationContext();
                CharSequence text = "Sucesso na requisição";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
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
        });

        //fila de requisições
        RequestQueue fila = Volley.newRequestQueue(this);

        //Adiciona a requisição á fila de requisições
        fila.add(req);

    }
}
