package com.incasa.incasa;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class CadastroActivity extends AppCompatActivity {

    TextView input_nome;
    TextView input_login;
    TextView input_password;

    private static final String URLCADASTRO = "https://192.168.1.100/backend/userLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

    }

    public void clickBtnCadastro(View view){
        if(input_nome.getText() == null || input_login.getText() == null || input_password.getText() == null) {
            Context context = getApplicationContext();
            CharSequence text = "Preencha todos os campos";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            Cadastro();
        }
    }

    public void clickBtnCancelar(View view){
        Intent it = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(it);
    }

    public void Cadastro() { JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URLCADASTRO, null, new Response.Listener<JSONObject>() {
        //Em caso de sucesso
        @Override
        public void onResponse(JSONObject response) {

            Intent it = new Intent(CadastroActivity.this, HomeActivity.class);
            startActivity(it);

        }
    }, new Response.ErrorListener() {
        //Em caso de erro
        @Override
        public void onErrorResponse(VolleyError error) {
            Context context = getApplicationContext();
            CharSequence text = "Erro no cadastro";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }){

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String>  params = new HashMap<String, String>();
            params.put("nome", input_nome.getText()+"");
            params.put("login", input_login.getText()+"");
            params.put("senha", input_password.getText()+"");

            return params;
        }
    };

        //fila de requisições
        RequestQueue fila = Volley.newRequestQueue(this);

        //Adiciona a requisição á fila de requisições
        fila.add(req);
    }

}

