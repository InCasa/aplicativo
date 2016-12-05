package com.incasa.incasa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import model.User;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String URL = "http://httpbin.org/get";

    private static final String URLTEMPERATURA = "http://192.168.0.100/backend/temperaturaValor";
    private static final String URLUMIDADE = "http://192.168.0.100/backend/umidadeValor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        exibeTelaPrincipalOuSolicitaLogin();

        //finish();

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

        getTemperatura();
        getUmidade();

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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void clickBtnVoz(View view){
        Intent it = new Intent(this, VozActivity.class);
        startActivity(it);
    }

    //Metodo responsável pelas requisições dos valores dos sensores
    //metodo cod:
    //1 GET
    //2 POST
    //3 PUT
    //4 DELETE

    public void getTemperatura() {
        //Parametros JsonObjectRequest:
        //1- Metodo da requisição
        //2- url do servidor
        //3- Metodo para sucesso da requisição
        //4- Metodo para falha na requisição
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLTEMPERATURA, null, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {

                Context context = getApplicationContext();
                CharSequence text = "Sucesso na requisição Temperatura: ";
                int duration = Toast.LENGTH_SHORT;

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

}
