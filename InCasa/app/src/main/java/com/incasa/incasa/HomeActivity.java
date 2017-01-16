package com.incasa.incasa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

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

import model.Aplicativo;
import model.Arduino;
import model.Luminosidade;
import model.Presenca;
import model.Rele1;
import model.Rele2;
import model.Rele3;
import model.Rele4;
import model.Temperatura;
import model.Umidade;
import model.User;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        exibeTelaPrincipalOuSolicitaLogin();

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences mSharedPreferences = getSharedPreferences("ServerAdress", 0);
        this.ip = mSharedPreferences.getString("servidor", " ");

        String URLTEMPERATURA = "http://"+ip+"/backend/temperaturaValor";
        String URLUMIDADE = "http://"+ip+"/backend/umidadeValor";

        getTemperatura(URLTEMPERATURA);
        getUmidade(URLUMIDADE);

        String URLGETTEMPERATURA = "http://"+ip+"/backend/temperatura/1";
        String URLGETUMIDADE = "http://"+ip+"/backend/umidade/1";
        String URLGETPRESENCA = "http://"+ip+"/backend/presenca/1";
        String URLGETLUMINOSISADE = "http://"+ip+"/backend/luminosidade/1";
        getSensorTemperatura(URLGETTEMPERATURA);
        getSensorUmidade(URLGETUMIDADE);
        getSensorPresenca(URLGETPRESENCA);
        getSensorLuminosidade(URLGETLUMINOSISADE);

        String URLGETARDUINO = "http://"+ip+"/backend/arduino/1";
        getArduino(URLGETARDUINO);

        String URLGETCELULAR = "http://"+ip+"/backend/aplicativo/1";
        getCelular(URLGETCELULAR);

        JSONObject jsonBody = new JSONObject();
        User user = User.getInstancia();
        try {
            jsonBody.put("login", user.getLogin());
            jsonBody.put("senha", user.getSenha());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String URLUSER = "http://"+ip+"/backend/getUser";
        getUser(jsonBody, URLUSER);

        String URLGETRELE1 = "http://"+ip+"/backend/rele/1";
        String URLGETRELE2 = "http://"+ip+"/backend/rele/2";
        String URLGETRELE3 = "http://"+ip+"/backend/rele/3";
        String URLGETRELE4 = "http://"+ip+"/backend/rele/4";

        getRele1(URLGETRELE1);
        getRele2(URLGETRELE2);
        getRele3(URLGETRELE3);
        getRele4(URLGETRELE4);

    }

    private void exibeTelaPrincipalOuSolicitaLogin() {
        if(!usuarioIsLogged()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

    }

    private boolean usuarioIsLogged() {
        User user = User.getInstancia();
        if(user.getSenha() == null){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sensor) {
            Intent intent = new Intent(this, SensorActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_rele) {
            Intent intent = new Intent(this, ReleActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_voz) {
            Intent intent = new Intent(this, VozActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_config) {
            Intent intent = new Intent(this, ConfigActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_admin) {
            Intent intent = new Intent(this, AdminActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void clickBtnVoz(View view){
        Intent it = new Intent(this, VozActivity.class);
        startActivity(it);
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
                    umi =  umi + "%";
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

    public void getSensorTemperatura(String URLGETTEMPERATURA) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLGETTEMPERATURA, null, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Temperatura temp = Temperatura.getInstancia();
                    temp.setIdTemperatura(response.getInt("id"));
                    temp.setNome(response.getString("nome"));
                    temp.setDescricao(response.getString("descricao"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            //Em caso de erro
            @Override
            public void onErrorResponse(VolleyError error) {

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

    public void getSensorUmidade(String URLGETUMIDADE) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLGETUMIDADE, null, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Umidade umi = Umidade.getInstancia();
                    umi.setIdUmidade(response.getInt("id"));
                    umi.setNome(response.getString("nome"));
                    umi.setDescricao(response.getString("descricao"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            //Em caso de erro
            @Override
            public void onErrorResponse(VolleyError error) {

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

    public void getSensorLuminosidade(String URLGETLUMINOSISADE) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLGETLUMINOSISADE, null, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Luminosidade lumi = Luminosidade.getInstancia();
                    lumi.setIdLuminosidade(response.getInt("id"));
                    lumi.setNome(response.getString("nome"));
                    lumi.setDescricao(response.getString("descricao"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            //Em caso de erro
            @Override
            public void onErrorResponse(VolleyError error) {

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

    public void getSensorPresenca(String URLGETTEMPERATURA) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLGETTEMPERATURA, null, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Presenca presenca = Presenca.getInstancia();
                    presenca.setIdPresenca(response.getInt("id"));
                    presenca.setNome(response.getString("nome"));
                    presenca.setDescricao(response.getString("descricao"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            //Em caso de erro
            @Override
            public void onErrorResponse(VolleyError error) {

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

    public void getArduino(String URLGETARDUINO) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLGETARDUINO, null, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {
                Arduino arduino = Arduino.getInstancia();
                //adiciona as informações no objeto arduino
                try {
                    arduino.setIdArduino(response.getInt("id"));
                    arduino.setIp(response.getString("ip"));
                    arduino.setMac(response.getString("mac"));
                    arduino.setMask(response.getString("mask"));
                    arduino.setGateway(response.getString("gateway"));
                    arduino.setPorta(response.getString("porta"));

                    arduino.setPinoRele1(response.getString("PinoRele1"));
                    arduino.setPinoRele2(response.getString("PinoRele2"));
                    arduino.setPinoRele3(response.getString("PinoRele3"));
                    arduino.setPinoRele4(response.getString("PinoRele4"));
                    arduino.setPinoDHT22(response.getString("PinoDHT"));
                    arduino.setPinoLDR(response.getString("PinoLDR"));
                    arduino.setPinoPresenca(response.getString("PinoPresenca"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            //Em caso de erro
            @Override
            public void onErrorResponse(VolleyError error) {

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

    public void getCelular(String URLGETCELULAR) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLGETCELULAR, null, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Aplicativo app = Aplicativo.getInstancia();
                    app.setIdAplicativo(response.getInt("id"));
                    app.setNome(response.getString("nome"));
                    app.setMac(response.getString("mac"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            //Em caso de erro
            @Override
            public void onErrorResponse(VolleyError error) {

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

    public void getRele1(String URLGETRELE1) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLGETRELE1, null, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Rele1 rele1 = Rele1.getInstancia();
                    rele1.setIdRele(response.getInt("id"));
                    rele1.setNome(response.getString("nome"));
                    rele1.setDescricao(response.getString("descricao"));
                    rele1.setPorta(response.getInt("porta"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            //Em caso de erro
            @Override
            public void onErrorResponse(VolleyError error) {

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

    public void getRele2(String URLGETRELE2) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLGETRELE2, null, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Rele2 rele2 = Rele2.getInstancia();
                    rele2.setIdRele(response.getInt("id"));
                    rele2.setNome(response.getString("nome"));
                    rele2.setDescricao(response.getString("descricao"));
                    rele2.setPorta(response.getInt("porta"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            //Em caso de erro
            @Override
            public void onErrorResponse(VolleyError error) {

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

    public void getRele3(String URLGETRELE3) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLGETRELE3, null, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Rele3 rele3 = Rele3.getInstancia();
                    rele3.setIdRele(response.getInt("id"));
                    rele3.setNome(response.getString("nome"));
                    rele3.setDescricao(response.getString("descricao"));
                    rele3.setPorta(response.getInt("porta"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            //Em caso de erro
            @Override
            public void onErrorResponse(VolleyError error) {

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

    public void getRele4(String URLGETRELE4) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLGETRELE4, null, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Rele4 rele4 = Rele4.getInstancia();
                    rele4.setIdRele(response.getInt("id"));
                    rele4.setNome(response.getString("nome"));
                    rele4.setDescricao(response.getString("descricao"));
                    rele4.setPorta(response.getInt("porta"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            //Em caso de erro
            @Override
            public void onErrorResponse(VolleyError error) {

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

    public void getUser(JSONObject json, String URLUSER) {

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URLUSER, json, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {

                User user = User.getInstancia();
                try {
                    user.setNome(response.getString("nome"));
                    user.setId(response.getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            //Em caso de erro
            @Override
            public void onErrorResponse(VolleyError error) {

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
