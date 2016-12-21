package com.incasa.incasa.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.incasa.incasa.R;

import org.json.JSONObject;

public class FragmentServidor extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_servidor, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText ip_server = (EditText) getView().findViewById(R.id.txvServer);
        Button btnAlterar = (Button) getView().findViewById(R.id.btnAlterar);

        final SharedPreferences mSharedPreferences = this.getActivity().getSharedPreferences("ServerAdress",0);

        if(mSharedPreferences.getString("servidor", null) != " "){
            ip_server.setText(mSharedPreferences.getString("servidor", null));
        }

        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = ip_server.getText().toString();

                testeServer(ip);

                if(ip != mSharedPreferences.getString("servidor", null)){
                    SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                    mEditor.putString("servidor", ip);
                    mEditor.apply();
                }

            }
        });

    }

    public void testeServer(String ipServer) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "http://"+ipServer+"/backend/teste", null, new Response.Listener<JSONObject>() {
            //Em caso de sucesso
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getActivity(), "Servidor: OK" ,Toast.LENGTH_SHORT).show();
            }

        }, new Response.ErrorListener() {
            //Em caso de erro
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Erro no servidor" ,Toast.LENGTH_SHORT).show();
            }
        });

        //fila de requisições
        RequestQueue fila = Volley.newRequestQueue(getActivity().getApplicationContext());

        //Adiciona a requisição á fila de requisições
        fila.add(req);
    }
}
