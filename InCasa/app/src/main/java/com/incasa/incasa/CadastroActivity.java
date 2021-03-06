package com.incasa.incasa;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class CadastroActivity extends AppCompatActivity {

    String nomec = "";
    String loginc = "";
    String senhac = "";
    String csenhac = "";
    String ip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        final EditText input_nome = (EditText) findViewById(R.id.input_nomec);
        final EditText input_login = (EditText) findViewById(R.id.input_loginc);
        final EditText input_password = (EditText) findViewById(R.id.input_passwordc);
        final EditText input_cpassword = (EditText) findViewById(R.id.input_cpasswordc);

        Button btnCadastrar = (Button) findViewById(R.id.btn_cadastrar);
        Button btnCancelar = (Button) findViewById(R.id.btn_cancelar);
        Button btnServer = (Button) findViewById(R.id.btn_server);

        SharedPreferences mSharedPreferences = getSharedPreferences("ServerAdress", 0);
        this.ip = mSharedPreferences.getString("servidor", " ");

        final String URLCADASTRO = "http://"+ip+"/backend/user";

        btnCadastrar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                nomec = input_nome.getText().toString();
                loginc = input_login.getText().toString();
                senhac = input_password.getText().toString();
                csenhac = input_cpassword.getText().toString();

                if(TextUtils.isEmpty(nomec) || TextUtils.isEmpty(loginc) || TextUtils.isEmpty(senhac) || TextUtils.isEmpty(csenhac)) {
                    if(TextUtils.isEmpty(nomec)) {
                        input_nome.setError("Preencha o campo nome");
                        return;
                    }
                    if(TextUtils.isEmpty(loginc)) {
                        input_login.setError("Preencha o campo login");
                        return;
                    }
                    if(TextUtils.isEmpty(senhac)) {
                        input_password.setError("Preencha o campo senha");
                        return;
                    }
                    if(TextUtils.isEmpty(csenhac)) {
                        input_cpassword.setError("Preencha o campo confirmação de senha");
                        return;
                    }
                } else {
                    senhac = input_password.getText().toString();
                    csenhac = input_cpassword.getText().toString();
                    if(senhac.equals(csenhac)) {
                        //Criação do Json da requisição
                        JSONObject jsonBody = new JSONObject();
                        try {
                            jsonBody.put("nome", nomec);
                            jsonBody.put("login", loginc);
                            jsonBody.put("senha", senhac);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Cadastro(jsonBody, URLCADASTRO);
                    } else {
                        Context context = getApplicationContext();
                        CharSequence text = "As senhas devem ser iguais";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
            }
        });

        btnCancelar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(it);
            }
        });

        btnServer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CadastroActivity.this, ServerActivity.class);
                startActivity(it);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void Cadastro(JSONObject json, String URLCADASTRO) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URLCADASTRO, json, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Intent it = new Intent(CadastroActivity.this, LoginActivity.class);
                        startActivity(it);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Context context = getApplicationContext();
                CharSequence text = "Erro no cadastro";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
        // Add the request to the RequestQueue.
        //fila de requisições
        RequestQueue fila = Volley.newRequestQueue(this);

        //Adiciona a requisição á fila de requisições
        fila.add(req);
    }

}

