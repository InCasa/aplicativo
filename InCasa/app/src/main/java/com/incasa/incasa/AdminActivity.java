package com.incasa.incasa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import model.User;

public class AdminActivity extends AppCompatActivity {

    EditText userNome;
    EditText userLogin;
    EditText userSenhaAnt;
    EditText userSenhaNov;
    EditText userSenhaC;
    String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        userNome = (EditText) findViewById(R.id.userNome);
        userLogin = (EditText) findViewById(R.id.userLogin);
        userSenhaAnt = (EditText) findViewById(R.id.userSenhaAnt);
        userSenhaNov = (EditText) findViewById(R.id.userSenhaNov);
        userSenhaC = (EditText) findViewById(R.id.userSenhaC);

        Button submitUser = (Button) findViewById(R.id.btnAdm);

        SharedPreferences mSharedPreferences = getSharedPreferences("ServerAdress", 0);
        this.ip = mSharedPreferences.getString("servidor", " ");

        String URLUSER = "http://"+ip+"/backend/getUser";

        JSONObject jsonBody = new JSONObject();
        User user = User.getInstancia();
        try {
            jsonBody.put("login", user.getLogin());
            jsonBody.put("senha", user.getSenha());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getUser(jsonBody, URLUSER);

        userNome.setText(user.getNome());
        userLogin.setText(user.getLogin());

        submitUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
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

    public void submit() {
        String nome = userNome.getText().toString();
        String login = userLogin.getText().toString();
        String senhaAnt = userSenhaAnt.getText().toString();
        String senhaNov = userSenhaNov.getText().toString();
        String senhaC = userSenhaC.getText().toString();

        String PUTUSER = "http://"+ip+"/backend/user/update/";

        if(TextUtils.isEmpty(nome) || TextUtils.isEmpty(login) || TextUtils.isEmpty(senhaAnt) || TextUtils.isEmpty(senhaNov) || TextUtils.isEmpty(senhaC)) {
            if(TextUtils.isEmpty(nome)) {
                userNome.setError("Preencha o campo nome");
                return;
            }
            if(TextUtils.isEmpty(login)) {
                userNome.setError("Preencha o campo login");
                return;
            }
            if(TextUtils.isEmpty(senhaAnt)) {
                userSenhaAnt.setError("Preencha o campo senha antiga");
                return;
            }
            if(TextUtils.isEmpty(senhaNov)) {
                userSenhaNov.setError("Preencha o campo de senha nova");
                return;
            }
            if(TextUtils.isEmpty(senhaC)) {
                userSenhaC.setError("Preencha o campo confirmação de senha nova");
                return;
            }
        } else {
            if(senhaNov.equals(senhaC)) {
                //Criação do Json da requisição
                JSONObject jsonBody = new JSONObject();
                try {
                    User user = User.getInstancia();
                    jsonBody.put("login", user.getLogin());
                    jsonBody.put("senha", user.getSenha());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject jsonBody2 = new JSONObject();
                try {
                    jsonBody2.put("nome", nome);
                    jsonBody2.put("login", login);
                    jsonBody2.put("senha", senhaNov);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                atualizar(jsonBody2, PUTUSER);

                Intent it = new Intent(this, LoginActivity.class);
                startActivity(it);
                this.finish();
            } else {
                Context context = getApplicationContext();
                CharSequence text = "As senhas devem ser iguais";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }

    public void atualizar(JSONObject json, String PUTUSER) {
        User user = User.getInstancia();
        PUTUSER = PUTUSER + user.getId();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, PUTUSER, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Context context = getApplicationContext();
                CharSequence text = "Administrador: Dados atualizados com sucesso !";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                Intent it = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(it);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Context context = getApplicationContext();
                CharSequence text = "Administrador: Falha ao atualizar !";
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
        // Add the request to the RequestQueue.
        //fila de requisições
        RequestQueue fila = Volley.newRequestQueue(this);

        //Adiciona a requisição á fila de requisições
        fila.add(req);
    }

    public void getUser(JSONObject json, String URLUSER) {

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URLUSER, json, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {
                Context context = getApplicationContext();
                CharSequence text = "Usuário: Sucesso !";
                int duration = Toast.LENGTH_SHORT;

                User user = User.getInstancia();
                try {
                    user.setNome(response.getString("nome"));
                    user.setId(response.getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        }, new Response.ErrorListener() {
            //Em caso de erro
            @Override
            public void onErrorResponse(VolleyError error) {
                Context context = getApplicationContext();
                CharSequence text = "Usuário: Falha !";
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

    public void getUserInfo(JSONObject json, String URLUSERINFO) {

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLUSERINFO, json, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {
                Context context = getApplicationContext();
                CharSequence text = "Usuário: Sucesso !";
                int duration = Toast.LENGTH_SHORT;

                User user = User.getInstancia();
                try {
                    user.setNome(response.getString("nome"));
                    user.setId(response.getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }, new Response.ErrorListener() {
            //Em caso de erro
            @Override
            public void onErrorResponse(VolleyError error) {
                Context context = getApplicationContext();
                CharSequence text = "Usuário: Falha !";
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
