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

import model.Arduino;
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
        final EditText presencaField = (EditText) getView().findViewById(R.id.editPinoPresenca);

        Button btnSalvar = (Button) getView().findViewById(R.id.btnArduinoSalvar);

        SharedPreferences mSharedPreferences = this.getActivity().getSharedPreferences("ServerAdress",0);
        final String ip = mSharedPreferences.getString("servidor", null);
        final String URLCADASTRO = "http://"+ip+"/backend/arduino";
        final String URLGET = "http://"+ip+"/backend/arduino/1";
        final String URLUPDATE = "http://"+ip+"/backend/arduino/update/";

        GetConfig(URLGET);

        //seta as informações do objeto nos editText
        final Arduino arduino = Arduino.getInstancia();
        ip_arduino.setText(arduino.getIp());
        macField.setText(arduino.getMac());
        gatewayField.setText(arduino.getGateway());
        maskField.setText(arduino.getMask());
        portaField.setText(arduino.getPorta());
        temperaturaField.setText(arduino.getPinoDHT22());
        rele1Field.setText(arduino.getPinoRele1());
        rele2Field.setText(arduino.getPinoRele2());
        rele3Field.setText(arduino.getPinoRele3());
        rele4Field.setText(arduino.getPinoRele4());
        luminosidadeField.setText(arduino.getPinoLDR());
        presencaField.setText(arduino.getPinoPresenca());

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

                if(TextUtils.isEmpty(ipArduino) || TextUtils.isEmpty(mac) || TextUtils.isEmpty(gateway) || TextUtils.isEmpty(gateway) || TextUtils.isEmpty(mask) || TextUtils.isEmpty(porta) || TextUtils.isEmpty(temperatura)
                        || TextUtils.isEmpty(rele1) || TextUtils.isEmpty(rele2) || TextUtils.isEmpty(rele3) || TextUtils.isEmpty(rele4) || TextUtils.isEmpty(luminosidade) || TextUtils.isEmpty(presenca)){

                    if(TextUtils.isEmpty(ipArduino)){
                        ip_arduino.setError("Preencha o campo IP");
                    }

                    if(TextUtils.isEmpty(mac)){
                        macField.setError("Preencha o campo MAC");
                    }

                    if(TextUtils.isEmpty(gateway)){
                        gatewayField.setError("Preencha o campo Gateway");
                    }

                    if(TextUtils.isEmpty(mask)){
                        maskField.setError("Preencha o campo Máscara");
                    }

                    if(TextUtils.isEmpty(porta)){
                        portaField.setError("Preencha o campo Porta");
                    }

                    if(TextUtils.isEmpty(temperatura)){
                        temperaturaField.setError("Preencha o campo Pino Sensor de Temperatura e Umidade");
                    }

                    if(TextUtils.isEmpty(rele1)){
                        rele1Field.setError("Preencha o campo Pino do relê 1");
                    }

                    if(TextUtils.isEmpty(rele2)){
                        rele2Field.setError("Preencha o campo Pino do relê 2");
                    }

                    if(TextUtils.isEmpty(rele3)){
                        rele3Field.setError("Preencha o campo Pino do relê 3");
                    }

                    if(TextUtils.isEmpty(rele4)){
                        rele4Field.setError("Preencha o campo Pino do relê 4");
                    }

                    if(TextUtils.isEmpty(luminosidade)){
                        luminosidadeField.setError("Preencha o campo Pino do Sensor de Luminosidade");
                    }

                    if(TextUtils.isEmpty(presenca)){
                        presencaField.setError("Preencha o campo Pino do Sensor de Presença");
                    }
                }else {
                    JSONObject jsonBody = new JSONObject();
                    try {
                        jsonBody.put("ip", ipArduino);
                        arduino.setIp(ipArduino);

                        jsonBody.put("mac", mac);
                        arduino.setMac(mac);

                        jsonBody.put("gateway", gateway);
                        arduino.setGateway(gateway);

                        jsonBody.put("mask", mask);
                        arduino.setMask(mask);

                        jsonBody.put("porta", porta);
                        arduino.setPorta(porta);

                        jsonBody.put("PinoDHT", temperatura);
                        arduino.setPinoDHT22(temperatura);

                        jsonBody.put("PinoRele1", rele1);
                        arduino.setPinoRele1(rele1);
                        jsonBody.put("PinoRele2", rele2);
                        arduino.setPinoRele2(rele2);
                        jsonBody.put("PinoRele3", rele3);
                        arduino.setPinoRele3(rele3);
                        jsonBody.put("PinoRele4", rele4);
                        arduino.setPinoRele4(rele4);

                        jsonBody.put("PinoLDR", luminosidade);
                        arduino.setPinoLDR(luminosidade);

                        jsonBody.put("PinoPresenca", presenca);
                        arduino.setPinoPresenca(presenca);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //SaveConfig(jsonBody, URLCADASTRO);
                    UpdateConfig(jsonBody, URLUPDATE);
                }

            }
        });
    }

    public void SaveConfig(JSONObject json, String URLCADASTRO) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URLCADASTRO, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getActivity(), "Configuração Arduino: Configuração salva com Sucesso !" ,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Configuração Arduino: Erro ao salvar a configuração !" ,Toast.LENGTH_SHORT).show();
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
                //obtem a instancia do arduino
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
        final Arduino arduino = Arduino.getInstancia();
        URLUPDATE = URLUPDATE + arduino.getIdArduino();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, URLUPDATE, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getActivity(), "Configuração Arduino: Atualizado com sucesso !" ,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Configuração Arduino: Erro na atualização !" ,Toast.LENGTH_SHORT).show();
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
