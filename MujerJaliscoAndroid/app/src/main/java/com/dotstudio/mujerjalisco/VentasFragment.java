package com.dotstudio.mujerjalisco;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class VentasFragment extends Fragment {

    ListView lista;
    View rootview;
    JSONArray jsonArray;


    public VentasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview= inflater.inflate(R.layout.fragment_ventas, container, false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setLaxStrictMode();

        lista = (ListView) rootview.findViewById(R.id.listView3);

        if(isNetworkOnline()){
            new downloadData().execute();
        }
        else{
            Toast.makeText(getActivity().getApplicationContext(), "Conexion a internet inexistente, intente de nuevo", Toast.LENGTH_LONG).show();
        }

        return rootview;
    }

    @SuppressLint("NewApi")
    private void setLaxStrictMode() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) return;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
        }
        else{
            new Handler().postAtFrontOfQueue(new Runnable() {
                @Override
                public void run() {
                    StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
                }
            });
        }
    }

    public boolean isNetworkOnline() {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;
    }

    public class downloadData extends AsyncTask<Void, Void, String> {
        ArrayList<ItemGet> details;
        private ProgressDialog pd = new ProgressDialog(getActivity());

        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Descargando información...");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                details = getListData();
            } catch (Exception e) {
                Log.v("log_tag", "Error in http connection " + e.toString());
            }
            return "Success";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pd.hide();
            pd.dismiss();
            lista.setAdapter(new CustomListAdapterVentas(getActivity(), details));

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                    Object o = lista.getItemAtPosition(position);
                    ItemGet newsData = (ItemGet) o;

                }
            });

        }
    }

    private ArrayList<ItemGet> getListData(){
        ArrayList<ItemGet> results = new ArrayList<>();

        ItemGet newsData = new ItemGet();

        jsonArray= obtenerJson("http://dotstudioservices.com/promosbc/mujerjaliscoservice/Service1.svc/obtenerventas", "listaproductos");
        for(int i=0; i<jsonArray.length(); i++){
            try {
                newsData = new ItemGet();
                newsData.setId(jsonArray.getJSONObject(i).getInt("idproducto"));
                newsData.setFecha("Precio unitario: $" + jsonArray.getJSONObject(i).getString("precio"));
                newsData.setSubtitulo(""+ jsonArray.getJSONObject(i).getString("nombremujer")+ " "+jsonArray.getJSONObject(i).getString("apellidomujer"));
                // newsData.setCantidad(2);
                newsData.setImagen(jsonArray.getJSONObject(i).getString("imagen"));
                newsData.setTitulo(jsonArray.getJSONObject(i).getString("nombre")); //que envie el nombre correcto

            } catch (JSONException e) {
                e.printStackTrace();
            }
            results.add(newsData);
        }
        return results;
    }

    public JSONArray obtenerJson(String urlstring, String response) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet(urlstring);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = "";
        JSONObject jsonResponse;
        JSONArray jsonArray = null;
        try {
            responseBody = httpClient.execute(request, responseHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            jsonResponse = new JSONObject(responseBody);
            jsonArray = jsonResponse.getJSONArray(response);
        } catch (JSONException e) {
        }
        httpClient.getConnectionManager().shutdown();
        return jsonArray;
    }


}
