package com.incasa.incasa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ServerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        final EditText ip_server = (EditText) findViewById(R.id.ip_server);
        final ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar);
        Button btnOK = (Button) findViewById(R.id.btn_ok);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setProgress(20);

                String ip = "http://"+ip_server.getText().toString()+"/backend/teste";

                if (TextUtils.isEmpty(ip)) {
                    bar.setProgress(0);
                } else {
                    testeServer(ip);
                }
            }
        });
    }

    public void testeServer(String ipServer) {

        final ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, ipServer, null, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {

                bar.setProgress(60);

                Context context = getApplicationContext();
                CharSequence text = "Servidor: OK";
                int duration = Toast.LENGTH_SHORT;

                bar.setProgress(80);

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                bar.setProgress(100);

                Intent it = new Intent(ServerActivity.this, LoginActivity.class);
                startActivity(it);

            }
        }, new Response.ErrorListener() {
            //Em caso de erro
            @Override
            public void onErrorResponse(VolleyError error) {
                Context context = getApplicationContext();
                CharSequence text = "Erro no servidor";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                bar.setProgress(0);
            }
        });

        //fila de requisições
        RequestQueue fila = Volley.newRequestQueue(this);

        //Adiciona a requisição á fila de requisições
        fila.add(req);
    }
}
