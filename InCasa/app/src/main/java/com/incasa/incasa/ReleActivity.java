package com.incasa.incasa;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import model.Rele1;
import model.Rele2;
import model.Rele3;
import model.Rele4;
import model.User;

public class ReleActivity extends AppCompatActivity {
    boolean estado1;
    boolean estado2;
    boolean estado3;
    boolean estado4;
    String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rele);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        SharedPreferences mSharedPreferences = getSharedPreferences("ServerAdress", 0);
        this.ip = mSharedPreferences.getString("servidor", " ");

        final String URLRELE1 = "http://"+ip+"/backend/releValor/1";
        final String URLRELE2 = "http://"+ip+"/backend/releValor/2";
        final String URLRELE3 = "http://"+ip+"/backend/releValor/3";
        final String URLRELE4 = "http://"+ip+"/backend/releValor/4";

        final String URLPOSTRELE = "http://"+ip+"/backend/releValor";

        Rele1 Objrele1 = Rele1.getInstancia();
        Rele2 Objrele2 = Rele2.getInstancia();
        Rele3 Objrele3 = Rele3.getInstancia();
        Rele4 Objrele4 = Rele4.getInstancia();

        getRele1(URLRELE1);
        getRele2(URLRELE2);
        getRele3(URLRELE3);
        getRele4(URLRELE4);

        Switch rele1 = (Switch) findViewById(R.id.switch1);
        Switch rele2 = (Switch) findViewById(R.id.switch2);
        Switch rele3 = (Switch) findViewById(R.id.switch3);
        Switch rele4 = (Switch) findViewById(R.id.switch4);

        TextView nomeRele1 = (TextView) findViewById(R.id.lblRele1);
        TextView nomeRele2 = (TextView) findViewById(R.id.lblRele2);
        TextView nomeRele3 = (TextView) findViewById(R.id.lblRele3);
        TextView nomeRele4 = (TextView) findViewById(R.id.lblRele4);

        nomeRele1.setText(Objrele1.getNome());
        nomeRele2.setText(Objrele2.getNome());
        nomeRele3.setText(Objrele3.getNome());
        nomeRele4.setText(Objrele4.getNome());

        rele1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(estado1 == isChecked) {

                } else {
                    if(isChecked) {
                        JSONObject jsonBody = new JSONObject();
                        try {
                            jsonBody.put("valor", true);
                            jsonBody.put("idRele", 1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        postRele(jsonBody, URLPOSTRELE);
                    } else {
                        JSONObject jsonBody = new JSONObject();
                        try {
                            jsonBody.put("valor", false);
                            jsonBody.put("idRele", 1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        postRele(jsonBody, URLPOSTRELE);
                    }

                }
            }
        });

        rele2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(estado2 == isChecked) {

                } else {
                    if(isChecked) {
                        JSONObject jsonBody = new JSONObject();
                        try {
                            jsonBody.put("valor", true);
                            jsonBody.put("idRele", 2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        postRele(jsonBody, URLPOSTRELE);
                    } else {
                        JSONObject jsonBody = new JSONObject();
                        try {
                            jsonBody.put("valor", false);
                            jsonBody.put("idRele", 2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        postRele(jsonBody, URLPOSTRELE);
                    }

                }
            }
        });

        rele3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(estado3 == isChecked) {

                } else {
                    if(isChecked) {
                        JSONObject jsonBody = new JSONObject();
                        try {
                            jsonBody.put("valor", true);
                            jsonBody.put("idRele", 3);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        postRele(jsonBody, URLPOSTRELE);
                    } else {
                        JSONObject jsonBody = new JSONObject();
                        try {
                            jsonBody.put("valor", false);
                            jsonBody.put("idRele", 3);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        postRele(jsonBody, URLPOSTRELE);
                    }

                }
            }
        });

        rele4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(estado4 == isChecked) {

                } else {
                    if(isChecked) {
                        JSONObject jsonBody = new JSONObject();
                        try {
                            jsonBody.put("valor", true);
                            jsonBody.put("idRele", 4);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        postRele(jsonBody, URLPOSTRELE);
                    } else {
                        JSONObject jsonBody = new JSONObject();
                        try {
                            jsonBody.put("valor", false);
                            jsonBody.put("idRele", 4);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        postRele(jsonBody, URLPOSTRELE);
                    }

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

    public void getRele1(String URLRELE1) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLRELE1, null, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {

                String temp = "";

                try {
                    Switch rele1 = (Switch) findViewById(R.id.switch1);
                    temp = response.getString("valor");

                    if(temp.equals("0")){
                        rele1.setChecked(false);
                        estado1 = false;
                    }else {
                        rele1.setChecked(true);
                        estado1 = true;
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

    public void getRele2(String URLRELE2) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLRELE2, null, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {

                String temp = "";

                try {
                    Switch rele2 = (Switch) findViewById(R.id.switch2);
                    temp = response.getString("valor");

                    if(temp.equals("0")){
                        rele2.setChecked(false);
                        estado2 = false;
                    }else {
                        rele2.setChecked(true);
                        estado2 = true;
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

    public void getRele3(String URLRELE3) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLRELE3, null, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {

                String temp = "";

                try {
                    Switch rele3 = (Switch) findViewById(R.id.switch3);
                    temp = response.getString("valor");

                    if(temp.equals("0")){
                        rele3.setChecked(false);
                        estado3 = false;
                    }else {
                        rele3.setChecked(true);
                        estado3 = true;
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

    public void getRele4(String URLRELE4) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLRELE4, null, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {

                String temp = "";

                try {
                    Switch rele4 = (Switch) findViewById(R.id.switch4);
                    temp = response.getString("valor");

                    if(temp.equals("0")){
                        rele4.setChecked(false);
                        estado4 = false;
                    }else {
                        rele4.setChecked(true);
                        estado4 = true;
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

    public void postRele(JSONObject json, String URLPOSTRELE) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URLPOSTRELE, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

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
