package com.incasa.incasa.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
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

import com.incasa.incasa.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import model.Luminosidade;
import model.Presenca;
import model.Temperatura;
import model.Umidade;
import model.User;


public class FragmentSensor extends Fragment {

    String ip;
    public Luminosidade lumi = Luminosidade.getInstancia();
    public Presenca presenca = Presenca.getInstancia();
    public Temperatura temp = Temperatura.getInstancia();
    public Umidade umi = Umidade.getInstancia();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sensor, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences mSharedPreferences = this.getActivity().getSharedPreferences("ServerAdress",0);
        this.ip = mSharedPreferences.getString("servidor", null);

        String URLGETTEMPERATURA = "http://"+ip+"/backend/temperatura/1";
        String URLGETUMIDADE = "http://"+ip+"/backend/umidade/1";
        String URLGETPRESENCA = "http://"+ip+"/backend/presenca/1";
        String URLGETLUMINOSISADE = "http://"+ip+"/backend/luminosidade/1";

        final String URLUPDATETEMPERATURA = "http://"+ip+"/backend/temperatura/update/";
        final String URLUPDATEUMIDADE = "http://"+ip+"/backend/umidade/update/";
        final String URLUPDATEPRESENCA = "http://"+ip+"/backend/presenca/update/";
        final String URLUPDATELUMINOSIDADE = "http://"+ip+"/backend/luminosidade/update/";

        GetTemperatura(URLGETTEMPERATURA);
        GetUmidade(URLGETUMIDADE);
        GetPresenca(URLGETPRESENCA);
        GetLuminosidade(URLGETLUMINOSISADE);

        final EditText nameTemp = (EditText) getView().findViewById(R.id.editNameTemp);
        final EditText descTemp = (EditText) getView().findViewById(R.id.editDescTemp);

        final EditText nameUmi = (EditText) getView().findViewById(R.id.editNameUmi);
        final EditText descUmi = (EditText) getView().findViewById(R.id.editDescUmi);

        final EditText namePresenca = (EditText) getView().findViewById(R.id.editNamePresenca);
        final EditText descPresenca = (EditText) getView().findViewById(R.id.editDescPresenca);

        final EditText nameLumi = (EditText) getView().findViewById(R.id.editNameLumi);
        final EditText descLumi = (EditText) getView().findViewById(R.id.editDescLumi);
        Button btnSensoresSalvar = (Button) getView().findViewById(R.id.btnSensorSalvar);

        nameTemp.setText(temp.getNome());
        descTemp.setText(temp.getDescricao());

        nameUmi.setText(umi.getNome());
        descUmi.setText(umi.getDescricao());

        namePresenca.setText(presenca.getNome());
        descPresenca.setText(presenca.getDescricao());

        nameLumi.setText(lumi.getNome());
        descLumi.setText(lumi.getDescricao());

        btnSensoresSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomeTemp = nameTemp.getText().toString();
                String descricaoTemp = descTemp.getText().toString();

                String nomeUmi = nameUmi.getText().toString();
                String descricaoUmi = descUmi.getText().toString();

                String nomePresenca = namePresenca.getText().toString();
                String descricaoPresenca = descPresenca.getText().toString();

                String nomeLumi = nameLumi.getText().toString();
                String descricaoLumi = descLumi.getText().toString();

                if(TextUtils.isEmpty(nomeTemp) || TextUtils.isEmpty(descricaoTemp)|| TextUtils.isEmpty(nomeUmi)|| TextUtils.isEmpty(descricaoUmi)|| TextUtils.isEmpty(nomePresenca)|| TextUtils.isEmpty(descricaoPresenca)
                        || TextUtils.isEmpty(nomeLumi)|| TextUtils.isEmpty(descricaoLumi)){

                    if(TextUtils.isEmpty(nomeTemp)){
                        nameTemp.setError("Preencha o campo Nome Sensor Temperatura");

                    }if(TextUtils.isEmpty(descricaoTemp)){
                        descTemp.setError("Preencha o campo Descrição Sensor Temperatura");
                    }
                    if(TextUtils.isEmpty(nomeUmi)){
                        nameUmi.setError("Preencha o campo Nome Sensor Umidade");

                    }if(TextUtils.isEmpty(descricaoUmi)){
                        descUmi.setError("Preencha o campo Descrição Sensor Umidade");
                    }
                    if(TextUtils.isEmpty(nomePresenca)){
                        namePresenca.setError("Preencha o campo Nome Sensor Luminosidade");

                    }if(TextUtils.isEmpty(descricaoPresenca)){
                        descPresenca.setError("Preencha o campo Descrição Sensor Luminosidade");
                    }
                    if(TextUtils.isEmpty(nomeLumi)){
                        nameLumi.setError("Preencha o campo Nome Sensor Presença");

                    }if(TextUtils.isEmpty(descricaoLumi)){
                        descLumi.setError("Preencha o campo Descrição Sensor Presença");
                    }

                }else{
                    JSONObject jsonBody1 = new JSONObject();
                    try {
                        jsonBody1.put("nome", nomeTemp);
                        temp.setNome(nomeTemp);

                        jsonBody1.put("descricao", descricaoTemp);
                        temp.setDescricao(descricaoTemp);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JSONObject jsonBody2 = new JSONObject();
                    try {
                        jsonBody2.put("nome", nomeUmi);
                        umi.setNome(nomeUmi);

                        jsonBody2.put("descricao", descricaoUmi);
                        umi.setDescricao(descricaoUmi);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JSONObject jsonBody3 = new JSONObject();
                    try {
                        jsonBody3.put("nome", nomePresenca);
                        presenca.setNome(nomePresenca);

                        jsonBody3.put("descricao", descricaoPresenca);
                        presenca.setDescricao(descricaoPresenca);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JSONObject jsonBody4 = new JSONObject();
                    try {
                        jsonBody4.put("nome", nomeLumi);
                        lumi.setNome(nomeLumi);

                        jsonBody4.put("descricao", descricaoLumi);
                        lumi.setDescricao(descricaoLumi);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    UpdateTemperatura(jsonBody1, URLUPDATETEMPERATURA);
                    UpdateUmidade(jsonBody2, URLUPDATEUMIDADE);
                    UpdatePresenca(jsonBody3, URLUPDATEPRESENCA);
                    UpdateLuminosidade(jsonBody4, URLUPDATELUMINOSIDADE);
                }

            }
        });


    }

    public void GetTemperatura(String URLGETTEMPERATURA) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLGETTEMPERATURA, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    temp.setIdTemperatura(response.getInt("id"));
                    temp.setNome(response.getString("nome"));
                    temp.setDescricao(response.getString("descricao"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Configuração Temperatura: Erro!" ,Toast.LENGTH_SHORT).show();
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

    public void GetUmidade(String URLGETUMIDADE) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLGETUMIDADE, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    umi.setIdUmidade(response.getInt("id"));
                    umi.setNome(response.getString("nome"));
                    umi.setDescricao(response.getString("descricao"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Configuração Umidade: Erro!" ,Toast.LENGTH_SHORT).show();
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

    public void GetLuminosidade(String URLGETLUMINOSISADE) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLGETLUMINOSISADE, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    lumi.setIdLuminosidade(response.getInt("id"));
                    lumi.setNome(response.getString("nome"));
                    lumi.setDescricao(response.getString("descricao"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Configuração Luminosidade: Erro!" ,Toast.LENGTH_SHORT).show();
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

    public void GetPresenca(String URLGETPRESENCA) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLGETPRESENCA, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    presenca.setIdPresenca(response.getInt("id"));
                    presenca.setNome(response.getString("nome"));
                    presenca.setDescricao(response.getString("descricao"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Configuração Presença: Erro!" ,Toast.LENGTH_SHORT).show();
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

    public void UpdateTemperatura(JSONObject json, String URLUPDATE) {

        URLUPDATE = URLUPDATE + temp.getIdTemperatura();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, URLUPDATE, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getActivity(), "Configuração Temperatura: Atualizado com sucesso !" ,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Configuração Temperatura: Erro na atualização !" ,Toast.LENGTH_SHORT).show();
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

    public void UpdateUmidade(JSONObject json, String URLUPDATE) {

        URLUPDATE = URLUPDATE + umi.getIdUmidade();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, URLUPDATE, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getActivity(), "Configuração Umidade: Atualizado com sucesso !" ,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Configuração Umidade: Erro na atualização !" ,Toast.LENGTH_SHORT).show();
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

    public void UpdatePresenca(JSONObject json, String URLUPDATE) {

        URLUPDATE = URLUPDATE + presenca.getIdPresenca();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, URLUPDATE, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getActivity(), "Configuração Presença: Atualizado com sucesso !" ,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Configuração Presença: Erro na atualização !" ,Toast.LENGTH_SHORT).show();
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

    public void UpdateLuminosidade(JSONObject json, String URLUPDATE) {

        URLUPDATE = URLUPDATE + lumi.getIdLuminosidade();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, URLUPDATE, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getActivity(), "Configuração Luminosidade: Atualizado com sucesso !" ,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Configuração Luminosidade: Erro na atualização !" ,Toast.LENGTH_SHORT).show();
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
