package com.incasa.incasa.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.incasa.incasa.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import model.Rele1;
import model.Rele2;
import model.Rele3;
import model.Rele4;
import model.User;

public class FragmentRele extends Fragment {
    public Rele1 rele1 = Rele1.getInstancia();
    public Rele2 rele2 = Rele2.getInstancia();
    public Rele3 rele3 = Rele3.getInstancia();
    public Rele4 rele4 = Rele4.getInstancia();
    String ip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences mSharedPreferences = this.getActivity().getSharedPreferences("ServerAdress",0);
        this.ip = mSharedPreferences.getString("servidor", null);

        String URLGETRELE1 = "http://"+ip+"/backend/rele/1";
        String URLGETRELE2 = "http://"+ip+"/backend/rele/2";
        String URLGETRELE3 = "http://"+ip+"/backend/rele/3";
        String URLGETRELE4 = "http://"+ip+"/backend/rele/4";

        GetRele1(URLGETRELE1);
        GetRele2(URLGETRELE2);
        GetRele3(URLGETRELE3);
        GetRele4(URLGETRELE4);

        return inflater.inflate(R.layout.fragment_rele, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText nameRele1 = (EditText) getView().findViewById(R.id.editNameRele1);
        final EditText descRele1 = (EditText) getView().findViewById(R.id.editDescRele1);

        final EditText nameRele2 = (EditText) getView().findViewById(R.id.editNameRele2);
        final EditText descRele2 = (EditText) getView().findViewById(R.id.editDescRele2);

        final EditText nameRele3 = (EditText) getView().findViewById(R.id.editNameRele3);
        final EditText descRele3 = (EditText) getView().findViewById(R.id.editDescRele3);

        final EditText nameRele4 = (EditText) getView().findViewById(R.id.editNameRele4);
        final EditText descRele4 = (EditText) getView().findViewById(R.id.editDescRele4);
        Button btnReleSalvar = (Button) getView().findViewById(R.id.btnReleSalvar);

        nameRele1.setText(rele1.getNome());
        descRele1.setText(rele1.getDescricao());

        nameRele2.setText(rele2.getNome());
        descRele2.setText(rele2.getDescricao());

        nameRele3.setText(rele3.getNome());
        descRele3.setText(rele3.getDescricao());

        nameRele4.setText(rele4.getNome());
        descRele4.setText(rele4.getDescricao());

        String URLCADASTRO = "http://"+ip+"/backend/rele";
        final String URLUPDATE = "http://"+ip+"/backend/rele/update/";

        btnReleSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomeRele1 = nameRele1.getText().toString();
                String descricaoRele1 = descRele1.getText().toString();

                String nomeRele2 = nameRele2.getText().toString();
                String descricaoRele2 = descRele2.getText().toString();

                String nomeRele3 = nameRele3.getText().toString();
                String descricaoRele3 = descRele3.getText().toString();

                String nomeRele4 = nameRele4.getText().toString();
                String descricaoRele4 = descRele4.getText().toString();

                if(TextUtils.isEmpty(nomeRele1) || TextUtils.isEmpty(descricaoRele1)|| TextUtils.isEmpty(nomeRele2)|| TextUtils.isEmpty(descricaoRele2)|| TextUtils.isEmpty(nomeRele3)|| TextUtils.isEmpty(descricaoRele3)
                        || TextUtils.isEmpty(nomeRele4)|| TextUtils.isEmpty(descricaoRele4)){

                    if(TextUtils.isEmpty(nomeRele1)){
                        nameRele1.setError("Preencha o campo Nome Rele 1");

                    }if(TextUtils.isEmpty(descricaoRele1)){
                        descRele1.setError("Preencha o campo Descrição Rele 1");
                    }
                    if(TextUtils.isEmpty(nomeRele2)){
                        nameRele1.setError("Preencha o campo Nome Rele 2");

                    }if(TextUtils.isEmpty(descricaoRele2)){
                        descRele1.setError("Preencha o campo Descrição Rele 2");
                    }
                    if(TextUtils.isEmpty(nomeRele3)){
                        nameRele1.setError("Preencha o campo Nome Rele 3");

                    }if(TextUtils.isEmpty(descricaoRele3)){
                        descRele1.setError("Preencha o campo Descrição Rele 3");
                    }
                    if(TextUtils.isEmpty(nomeRele4)){
                        nameRele1.setError("Preencha o campo Nome Rele 4");

                    }if(TextUtils.isEmpty(descricaoRele4)){
                        descRele1.setError("Preencha o campo Descrição Rele 4");
                    }

                }else{
                    JSONObject jsonBody1 = new JSONObject();
                    try {
                        jsonBody1.put("nome", nomeRele1);
                        rele1.setNome(nomeRele1);

                        jsonBody1.put("descricao", descricaoRele1);
                        rele1.setDescricao(descricaoRele1);

                        jsonBody1.put("porta", rele1.getPorta());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JSONObject jsonBody2 = new JSONObject();
                    try {
                        jsonBody2.put("nome", nomeRele2);
                        rele2.setNome(nomeRele2);

                        jsonBody2.put("descricao", descricaoRele2);
                        rele2.setDescricao(descricaoRele2);

                        jsonBody2.put("porta", rele2.getPorta());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JSONObject jsonBody3 = new JSONObject();
                    try {
                        jsonBody3.put("nome", nomeRele3);
                        rele3.setNome(nomeRele3);

                        jsonBody3.put("descricao", descricaoRele3);
                        rele3.setDescricao(descricaoRele3);

                        jsonBody1.put("porta", rele3.getPorta());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JSONObject jsonBody4 = new JSONObject();
                    try {
                        jsonBody4.put("nome", nomeRele4);
                        rele4.setNome(nomeRele4);

                        jsonBody4.put("descricao", descricaoRele4);
                        rele4.setDescricao(descricaoRele4);

                        jsonBody4.put("porta", rele4.getPorta());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    UpdateRele1(jsonBody1, URLUPDATE);
                    UpdateRele2(jsonBody2, URLUPDATE);
                    UpdateRele3(jsonBody3, URLUPDATE);
                    UpdateRele4(jsonBody4, URLUPDATE);
                }

            }
        });

    }

    public void UpdateRele1(JSONObject json, String URLUPDATE) {

        URLUPDATE = URLUPDATE + rele1.getIdRele();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, URLUPDATE, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getActivity(), "Configuração Relê: Atualizado com sucesso !" ,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Configuração Relê: Erro na atualização !" ,Toast.LENGTH_SHORT).show();
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
        RequestQueue fila = Volley.newRequestQueue(getActivity().getApplicationContext());

        //Adiciona a requisição á fila de requisições
        fila.add(req);
    }

    public void UpdateRele2(JSONObject json, String URLUPDATE) {

        URLUPDATE = URLUPDATE + rele2.getIdRele();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, URLUPDATE, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getActivity(), "Configuração Relê: Atualizado com sucesso !" ,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Configuração Relê: Erro na atualização !" ,Toast.LENGTH_SHORT).show();
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
        RequestQueue fila = Volley.newRequestQueue(getActivity().getApplicationContext());

        //Adiciona a requisição á fila de requisições
        fila.add(req);
    }

    public void UpdateRele3(JSONObject json, String URLUPDATE) {

        URLUPDATE = URLUPDATE + rele3.getIdRele();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, URLUPDATE, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getActivity(), "Configuração Relê: Atualizado com sucesso !" ,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Configuração Relê: Erro na atualização !" ,Toast.LENGTH_SHORT).show();
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
        RequestQueue fila = Volley.newRequestQueue(getActivity().getApplicationContext());

        //Adiciona a requisição á fila de requisições
        fila.add(req);
    }

    public void UpdateRele4(JSONObject json, String URLUPDATE) {

        URLUPDATE = URLUPDATE + rele4.getIdRele();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, URLUPDATE, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getActivity(), "Configuração Relê: Atualizado com sucesso !" ,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Configuração Relê: Erro na atualização !" ,Toast.LENGTH_SHORT).show();
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
        RequestQueue fila = Volley.newRequestQueue(getActivity().getApplicationContext());

        //Adiciona a requisição á fila de requisições
        fila.add(req);
    }

    public void GetRele1(String URLGETRELE1) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLGETRELE1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    rele1.setIdRele(response.getInt("id"));
                    rele1.setNome(response.getString("nome"));
                    rele1.setDescricao(response.getString("descricao"));
                    rele1.setPorta(response.getInt("porta"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Configuração Relê: Erro!" ,Toast.LENGTH_SHORT).show();
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
        RequestQueue fila = Volley.newRequestQueue(getActivity().getApplicationContext());

        //Adiciona a requisição á fila de requisições
        fila.add(req);
    }

    public void GetRele2(String URLGETRELE2) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLGETRELE2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    rele2.setIdRele(response.getInt("id"));
                    rele2.setNome(response.getString("nome"));
                    rele2.setDescricao(response.getString("descricao"));
                    rele2.setPorta(response.getInt("porta"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Configuração Relê: Erro!" ,Toast.LENGTH_SHORT).show();
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
        RequestQueue fila = Volley.newRequestQueue(getActivity().getApplicationContext());

        //Adiciona a requisição á fila de requisições
        fila.add(req);
    }

    public void GetRele3(String URLGETRELE3) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLGETRELE3, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    rele3.setIdRele(response.getInt("id"));
                    rele3.setNome(response.getString("nome"));
                    rele3.setDescricao(response.getString("descricao"));
                    rele3.setPorta(response.getInt("porta"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Configuração Relê: Erro!" ,Toast.LENGTH_SHORT).show();
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
        RequestQueue fila = Volley.newRequestQueue(getActivity().getApplicationContext());

        //Adiciona a requisição á fila de requisições
        fila.add(req);
    }

    public void GetRele4(String URLGETRELE4) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLGETRELE4, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    rele4.setIdRele(response.getInt("id"));
                    rele4.setNome(response.getString("nome"));
                    rele4.setDescricao(response.getString("descricao"));
                    rele4.setPorta(response.getInt("porta"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity(), "Configuração Relê: Sucesso!" ,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Configuração Relê: Erro!" ,Toast.LENGTH_SHORT).show();
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
        RequestQueue fila = Volley.newRequestQueue(getActivity().getApplicationContext());

        //Adiciona a requisição á fila de requisições
        fila.add(req);
    }
}
