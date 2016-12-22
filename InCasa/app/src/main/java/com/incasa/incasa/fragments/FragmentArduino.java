package com.incasa.incasa.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.incasa.incasa.CadastroActivity;
import com.incasa.incasa.LoginActivity;
import com.incasa.incasa.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import model.User;

public class FragmentArduino extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_arduino, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText ip_arduino = (EditText) getView().findViewById(R.id.editIP);
        final EditText macField = (EditText) getView().findViewById(R.id.editMac);
        final EditText gatewayField = (EditText) getView().findViewById(R.id.editGateway);
        final EditText maskField = (EditText) getView().findViewById(R.id.editMask);
        final EditText portaField = (EditText) getView().findViewById(R.id.editPorta);
        final EditText temperaturaField = (EditText) getView().findViewById(R.id.editPinoTemperatura);
        final EditText rele1Field = (EditText) getView().findViewById(R.id.editPinoRele1);
        final EditText rele2Field = (EditText) getView().findViewById(R.id.editPinoRele2);
        final EditText rele3Field = (EditText) getView().findViewById(R.id.editPinoRele3);
        final EditText rele4Field = (EditText) getView().findViewById(R.id.editPinoRele4);
        final EditText luminosidadeField = (EditText) getView().findViewById(R.id.editPinoLuminosidade);
        final EditText presencaField = (EditText) getView().findViewById(R.id.editPinoLuminosidade);

        Switch editar  = (Switch) getView().findViewById(R.id.editSwitch);

        Button btnSalvar = (Button) getView().findViewById(R.id.btnArduinoSalvar);

        SharedPreferences mSharedPreferences = this.getActivity().getSharedPreferences("ServerAdress",0);
        String ip = mSharedPreferences.getString("servidor", null);
        final String URLCADASTRO = "http://"+ip+"/backend/arduino";

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ipArduino = ip_arduino.getText().toString();
                String mac = macField.getText().toString();
                String gateway = gatewayField.getText().toString();
                String mask = maskField.getText().toString();
                String porta = portaField.getText().toString();
                String temperatura = temperaturaField.getText().toString();
                String rele1 = rele1Field.getText().toString();
                String rele2 = rele2Field.getText().toString();
                String rele3 = rele3Field.getText().toString();
                String rele4 = rele4Field.getText().toString();
                String luminosidade = luminosidadeField.getText().toString();
                String presenca = presencaField.getText().toString();

                //necessario criar logica para diferenciar entre atualização das informações e criação de uma nova,
                // um metodo de update para a rota de updade do banco de dados

                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("ip", ipArduino);
                    jsonBody.put("mac", mac);
                    jsonBody.put("gateway", gateway);
                    jsonBody.put("mask", mask);
                    jsonBody.put("porta", porta);
                    jsonBody.put("PinoDHT", temperatura);
                    jsonBody.put("PinoRele1", rele1);
                    jsonBody.put("PinoRele2", rele2);
                    jsonBody.put("PinoRele3", rele3);
                    jsonBody.put("PinoRele4", rele4);
                    jsonBody.put("PinoLDR", luminosidade);
                    jsonBody.put("PinoPresenca", presenca);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                SaveConfig(jsonBody, URLCADASTRO);

            }
        });
    }

    public void SaveConfig(JSONObject json, String URLCADASTRO) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URLCADASTRO, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getActivity(), "Configuração Arduino: Sucesso!" ,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Configuração Arduino: Erro!" ,Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                // add headers <key,value>
                User user = User.getInstancia();
                String auth = new String(Base64.encode((user.getLogin() + ":" + user.getSenha()).getBytes(), Base64.DEFAULT));

                headers.put("Authorization ", " Basic " + auth);
                Log.d("Application started", String.valueOf(headers));
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
