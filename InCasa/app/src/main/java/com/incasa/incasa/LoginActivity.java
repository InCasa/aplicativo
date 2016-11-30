package com.incasa.incasa;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private static final String URLLOGIN = "http://192.168.0.100/backend/userLogin";

    String login = "admin";
    String senha = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText input_loginL = (EditText) findViewById(R.id.input_loginL);
        final EditText input_passwordL = (EditText) findViewById(R.id.input_passwordL);

        Button btnCadastro = (Button) findViewById(R.id.btn_cadastro);
        Button btnLogin = (Button) findViewById(R.id.btn_login);



        btnCadastro.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(it);
            }
        });

        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                login = input_loginL.getText().toString();
                senha = input_passwordL.getText().toString();
                if(TextUtils.isEmpty(login) || TextUtils.isEmpty(senha)) {
                    if(TextUtils.isEmpty(login)) {
                        input_loginL.setError("Preencha o campo login");
                        return;
                    }
                    if(TextUtils.isEmpty(senha)) {
                        input_passwordL.setError("Preencha o campo senha");
                        return;
                    }
                } else {
                    Login();
                }
            }
        });

    }

    public void Login() {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URLLOGIN, null, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {
                Intent it = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(it);
            }
        }, new Response.ErrorListener() {
            //Em caso de erro
            @Override
            public void onErrorResponse(VolleyError error) {
                Context context = getApplicationContext();
                CharSequence text = "Login ou Senha incorretos: ";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s", login, senha);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;
            }
        };

        //fila de requisições
        RequestQueue fila = Volley.newRequestQueue(this);

        //Adiciona a requisição á fila de requisições
        fila.add(req);
    }

}

