package com.incasa.incasa;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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

import model.User;

public class VozActivity extends AppCompatActivity {
    String ip;
    private TextView txtSpeechInput;
    private View btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voz);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = findViewById(R.id.btnSpeak);

        SharedPreferences mSharedPreferences = getSharedPreferences("ServerAdress", 0);
        this.ip = mSharedPreferences.getString("servidor", " ");

        promptSpeechInput();

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
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

    /**
     * Showing google speech input dialog
     */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                    String preparse = result.get(0).toLowerCase();

                    String[] action = {"ligar", "desligar", "acender", "apagar", "qual"};
                    String[] comando = preparse.split(" ");
                    
                    boolean enviado = false;

                    for (int i = 0; i < action.length; i++) {
                        if (action[i].equals(comando[0])) {
                            JSONObject jsonBody = new JSONObject();
                            try {
                                jsonBody.put("comando", result.get(0));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            String URLCOMANDO = "http://" + ip + "/backend/comando";
                            enviaComando(jsonBody, URLCOMANDO);
                            enviado = true;
                        }

                    }
                    if (!enviado) {
                        Context context = getApplicationContext();
                        CharSequence text = "Comando invalido";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }

                }

            }
            break;
        }

    }

    public void enviaComando(JSONObject json, String URLCOMANDO) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URLCOMANDO, json, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {
                Context context = getApplicationContext();
                CharSequence text = "Comando enviado";
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
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
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
