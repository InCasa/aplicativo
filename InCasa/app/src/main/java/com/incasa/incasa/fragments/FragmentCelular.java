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

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Aplicativo;
import model.User;

public class FragmentCelular extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_celular, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText macAppField = (EditText) getView().findViewById(R.id.editAppMac);
        final EditText nameAppField = (EditText) getView().findViewById(R.id.editAppName);
        Button btnAppSalvar = (Button) getView().findViewById(R.id.btnAppSalvar);
        Button btnCloneMac = (Button) getView().findViewById(R.id.btnCloneMAC);

        SharedPreferences mSharedPreferences = this.getActivity().getSharedPreferences("ServerAdress",0);
        String ip = mSharedPreferences.getString("servidor", null);

        final String URLCADASTRO = "http://"+ip+"/backend/aplicativo";
        final String URLGET = "http://"+ip+"/backend/aplicativo/1";
        final String URLUPDATE = "http://"+ip+"/backend/aplicativo/update/";

        GetConfig(URLGET);

        //seta as informações do objeto nos editText
        Aplicativo app = Aplicativo.getInstancia();
        macAppField.setText(app.getMac());
        nameAppField.setText(app.getNome());


        btnCloneMac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                macAppField.setText(getMacAddr());
            }
        });

        btnAppSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Aplicativo app = Aplicativo.getInstancia();

                String nome = nameAppField.getText().toString();
                String mac = macAppField.getText().toString();

                if(TextUtils.isEmpty(nome) || TextUtils.isEmpty(mac)){
                    if(TextUtils.isEmpty(nome)){
                        nameAppField.setError("Preencha o campo nome");

                    }if(TextUtils.isEmpty(mac)){
                        macAppField.setError("Preencha o campo MAC");
                    }

                }else{
                    JSONObject jsonBody = new JSONObject();
                    try {
                        jsonBody.put("mac", mac);
                        app.setMac(mac);

                        jsonBody.put("nome", nome);
                        app.setNome(nome);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //SaveConfig(jsonBody, URLCADASTRO);
                    UpdateConfig(jsonBody, URLUPDATE);
                }

            }
        });

    }

    //método responsavel por pegar o MAC adress do dispositivo
    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

    public void SaveConfig(JSONObject json, String URLCADASTRO) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URLCADASTRO, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getActivity(), "Configuração Celular: Configuração salva com Sucesso !" ,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Configuração Celular: Erro ao salvar a configuração !" ,Toast.LENGTH_SHORT).show();
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

    public void GetConfig(String URLGET) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URLGET, null, new Response.Listener<JSONObject>() {
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
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Configuração Celular: Erro!" ,Toast.LENGTH_SHORT).show();
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

    public void UpdateConfig(JSONObject json, String URLUPDATE) {
        final Aplicativo app = Aplicativo.getInstancia();
        URLUPDATE = URLUPDATE + app.getIdAplicativo();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, URLUPDATE, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getActivity(), "Configuração Celular: Atualizado com sucesso !" ,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Configuração Celular: Erro na atualização !" ,Toast.LENGTH_SHORT).show();
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
