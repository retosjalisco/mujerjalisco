package com.dotstudio.mujerjalisco;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

public class CuentaActivity extends AppCompatActivity {

    int posicion=0;
    ViewFlipper flipper;

    SharedPreferences data;
    public static String filename = "datos";
    int login;

    public static String idmujer;
    public static String iddependiente;

    EditText correlogintxt;
    EditText clavelogintxt;

    //registro1
    EditText nombretxt;
    EditText apellidotxt;
    EditText direcciontxt;
    EditText correotxt;
    EditText ciudadtxt;
    EditText clavetxt;
    EditText telefonotxt;
    TextView fechanaclbl;

    //socio1
    RadioButton padreradio;
    RadioButton familiarradio;
    RadioButton tutoresradio;
    EditText nombresfamiliarestxt;
    EditText ingresotxt;

    //socio2
    Spinner habitacionesspinner;

    RadioButton propiaradio;
    RadioButton rentadaradio;
    RadioButton trabajas;
    EditText lugartrabajotxt;
    Spinner dependientesspinner;

    //dependientes
    EditText nombredptxt;
    EditText apellidodtxt;
    TextView fechanac2lbl;
    RadioButton masculinoradio;

    int contextmenubandera=0;

    Bitmap photo;
    String ba1;
    byte[] ba;

    ImageButton fotoinebtn;
    ImageButton fotocomprobantebtn;
    ImageButton fotodependientebtn;

    int uploadfotos=0;

   // int cantdependientes=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);

        flipper= (ViewFlipper) findViewById(R.id.viewflipper);

        setupcontrols();

        TextView preregistrobtn= (TextView) findViewById(R.id.textView2);
        preregistrobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posicion++;
                flipper.setDisplayedChild(posicion);
            }
        });

        Button loginbtn= (Button) findViewById(R.id.button);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });

        Button registrofotosbtn= (Button) findViewById(R.id.button2);
        registrofotosbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posicion++;
                flipper.setDisplayedChild(posicion);
            }
        });

        Button socioebtn= (Button) findViewById(R.id.button3);
        socioebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posicion++;
                flipper.setDisplayedChild(posicion);
            }
        });

        Button socioe2btn= (Button) findViewById(R.id.button4);
        socioe2btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posicion++;
                flipper.setDisplayedChild(posicion);
            }
        });

        Button dependientesbtn= (Button) findViewById(R.id.button5);
        dependientesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posicion++;
                flipper.setDisplayedChild(posicion);

          //      cantdependientes= Integer.parseInt(dependientesspinner.getSelectedItem().toString());
            }
        });


        Button agregadependientesbtn= (Button) findViewById(R.id.button6);
        agregadependientesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearCuenta();
            }
        });


        Button cambiardatebtn= (Button) findViewById(R.id.button11);
        cambiardatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button cambiardatedependientebtn= (Button) findViewById(R.id.button12);
        cambiardatedependientebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        fotoinebtn= (ImageButton) findViewById(R.id.imageButton);
        registerForContextMenu(fotoinebtn);
        fotoinebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contextmenubandera=1;
                openContextMenu(v);
            }
        });


        fotocomprobantebtn= (ImageButton) findViewById(R.id.imageButton2);
        registerForContextMenu(fotocomprobantebtn);
        fotocomprobantebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contextmenubandera=2;
                openContextMenu(v);
            }
        });


        fotodependientebtn= (ImageButton) findViewById(R.id.imageButton3);
        registerForContextMenu(fotodependientebtn);
        fotodependientebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contextmenubandera=3;
                openContextMenu(v);
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setLaxStrictMode();

        data = getSharedPreferences(filename, 0);
        login = data.getInt("login", 0);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.photomenu, menu);
    }

    public void abrircamara(){
        // Revisa si tiene camara, la abre, toma la foto y la guarda en la galeria
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 100);

        } else {
            Toast.makeText(getApplicationContext(), "Camera not supported", Toast.LENGTH_LONG).show();
        }
    }

    public void abrirgaleria(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), 1);
    }

    public boolean onContextItemSelected(MenuItem item) {
        //find out which menu item was pressed
        switch (item.getItemId()) {
            case R.id.cameraitem:
                abrircamara();
                return true;
            case R.id.galeryitem:
                abrirgaleria();
                return true;
            default:
                return false;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            //Guarda la foto de la camara
            photo = (Bitmap) data.getExtras().get("data");

            switch (contextmenubandera){
                case 1:
                    fotoinebtn.setImageBitmap(photo);
                    break;
                case 2:
                    fotocomprobantebtn.setImageBitmap(photo);
                    break;
                case 3:
                    fotodependientebtn.setImageBitmap(photo);
                    break;
            }
            upload();
        }

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            //Guarda la foto de la galeria
            Uri selectedImageUri = data.getData();
            //String selectedImagePath = getPath(data.getData());
            //System.out.println("Image Path : " + selectedImagePath);
            switch (contextmenubandera){
                case 1:
                    fotoinebtn.setImageURI(selectedImageUri);
                    break;
                case 2:
                    fotocomprobantebtn.setImageURI(selectedImageUri);
                    break;
                case 3:
                    fotodependientebtn.setImageURI(selectedImageUri);
                    break;
            }
            upload();
        }
    }

    private void upload() {
        BitmapDrawable drawable = (BitmapDrawable) fotoinebtn.getDrawable();

        switch (contextmenubandera){
            case 1:
                drawable = (BitmapDrawable) fotoinebtn.getDrawable();
                break;
            case 2:
                drawable = (BitmapDrawable) fotocomprobantebtn.getDrawable();
                break;
            case 3:
                drawable = (BitmapDrawable) fotodependientebtn.getDrawable();
                break;
        }
        Bitmap bitmapOrg = drawable.getBitmap();

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 100, bao);
        ba = bao.toByteArray();
        ba1 = Base64.encodeBytes(ba);
    }



    public void setupcontrols(){

        //login
        correlogintxt= (EditText) findViewById(R.id.editText);
        clavelogintxt= (EditText) findViewById(R.id.editText2);


        //registro1
        nombretxt= (EditText) findViewById(R.id.editText3);
        apellidotxt= (EditText) findViewById(R.id.editText4);
        direcciontxt= (EditText) findViewById(R.id.editText5);
        correotxt= (EditText) findViewById(R.id.editText6);
        ciudadtxt= (EditText) findViewById(R.id.editText7);
        clavetxt= (EditText) findViewById(R.id.editText13);
        telefonotxt= (EditText) findViewById(R.id.editText14);
        fechanaclbl= (TextView) findViewById(R.id.textView4);

        //socio1
        padreradio= (RadioButton) findViewById(R.id.radioButton);
        familiarradio= (RadioButton) findViewById(R.id.radioButton2);
        tutoresradio= (RadioButton) findViewById(R.id.radioButton3);
        nombresfamiliarestxt= (EditText) findViewById(R.id.editText8);
        ingresotxt= (EditText) findViewById(R.id.editText9);


        //socio2
        habitacionesspinner= (Spinner) findViewById(R.id.spinner);
        trabajas=(RadioButton) findViewById(R.id.radioButton6);
        propiaradio= (RadioButton) findViewById(R.id.radioButton4);
        rentadaradio= (RadioButton) findViewById(R.id.radioButton5);
        lugartrabajotxt= (EditText) findViewById(R.id.editText10);
        dependientesspinner= (Spinner) findViewById(R.id.spinner2);

        //dependientes
        nombredptxt= (EditText) findViewById(R.id.editText11);
        apellidodtxt= (EditText) findViewById(R.id.editText12);
        fechanac2lbl= (TextView) findViewById(R.id.textView12);
        masculinoradio= (RadioButton) findViewById(R.id.radioButton8);

    }

    @Override
    public void onBackPressed() {
        posicion--;
        flipper.setDisplayedChild(posicion);
    }

    private  void setLaxStrictMode() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) return ;

        if(Build.VERSION.SDK_INT <  Build.VERSION_CODES.JELLY_BEAN) {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
        } else {

            new Handler().postAtFrontOfQueue(new Runnable() {
                @Override
                public void run() {
                    StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
                }
            });
        }
    }


    public boolean isNetworkOnline() {
        boolean status=false;
        try{
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState()== NetworkInfo.State.CONNECTED) {
                status= true;
            }else {
                netInfo = cm.getNetworkInfo(1);
                if(netInfo!=null && netInfo.getState()== NetworkInfo.State.CONNECTED)
                    status= true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return status;
    }

    public String obtenerJson(String urlstring, String response){
        ProgressDialog pd = new ProgressDialog(CuentaActivity.this);
        pd.setMessage("Cargando...");
        pd.show();

        DefaultHttpClient httpClient= new DefaultHttpClient();
        HttpGet request = new HttpGet(urlstring) ;
        ResponseHandler<String> responseHandler=new BasicResponseHandler();
        String responseBody="";
        JSONObject jsonResponse;
        String strParsedValue="";

        try {
            responseBody = httpClient.execute(request, responseHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            jsonResponse = new JSONObject(responseBody) ;
            strParsedValue = jsonResponse.getString(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        pd.hide();
        pd.dismiss();

        return strParsedValue;
    }

    public void iniciarSesion(){
        if(isNetworkOnline()){
            String email= correlogintxt.getText().toString();
            String clave= clavelogintxt.getText().toString();

            if((correlogintxt.getText().length()<1) || (clavelogintxt.getText().length()<2)){
                Toast.makeText(getApplicationContext(), "Ingrese todos los datos", Toast.LENGTH_LONG).show();
            }
            else{
                try{
                    String resultado= obtenerJson("http://dotstudioservices.com/promosbc/MujerJaliscoService/Service1.svc/sesion/?email="+ URLEncoder.encode(email) +"&clave="+ URLEncoder.encode(clave) +"","status");
                    if(resultado.equals("Error"))
                        Toast.makeText(getApplicationContext(), "Correo electrónico o contraseña incorrecta", Toast.LENGTH_LONG).show();
                    else{
                        data = getSharedPreferences(filename, 0);
                        SharedPreferences.Editor editor = data.edit();
                        editor.putString("email", email);
                        editor.putInt("login", 1);
                        editor.putString("idmujer",resultado);
                        editor.commit();

                        idmujer= resultado;

                        Intent mainIntent = new Intent().setClass(CuentaActivity.this, MainActivity.class);
                        mainIntent.setAction("na");
                        startActivity(mainIntent);
                        finish();
                    }
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Correo electrónico o contraseña incorrecta", Toast.LENGTH_LONG).show();
                }

            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Existe un problema con la conexion a internet", Toast.LENGTH_LONG).show();
        }
    }


    public void crearCuenta(){

        if(isNetworkOnline()){


            if((nombretxt.getText().length()<1)|| (apellidodtxt.getText().length()<1)||(correotxt.getText().length()<1)||(clavetxt.getText().length()<1)||(direcciontxt.getText().length()<1)||(ciudadtxt.getText().length()<1)){
                Toast.makeText(getApplicationContext(), "Ingrese todos los datos", Toast.LENGTH_LONG).show();
            }
            else{
                try{
                    String ecivil="0";
                    int masculino=0;

                    RadioButton solteroradio= (RadioButton) findViewById(R.id.radioButton10);
                    if(solteroradio.isChecked()) {
                        ecivil = "1";
                    }

                    String vivecon="Sola";
                    int trabaja=0;
                    int propia=0;

                    if(padreradio.isChecked()){
                        vivecon="Padres";
                    }
                    if(familiarradio.isChecked()){
                        vivecon="AlgunFamiliar";
                    }

                    if(trabajas.isChecked()){
                        trabaja=1;
                    }

                    if(propiaradio.isChecked()){
                        propia=1;
                    }

                    if(masculinoradio.isChecked()){
                        masculino=1;
                    }

                   //queda pendiente agregar si es estudiante o parentesco


                    String resultado = obtenerJson("http://dotstudioservices.com/promosbc/MujerJaliscoService/Service1.svc/registrarse/?nombre=" + URLEncoder.encode(nombretxt.getText().toString()) + "&apellido=" + URLEncoder.encode(apellidotxt.getText().toString()) + "&email=" + URLEncoder.encode(correotxt.getText().toString()) + "&clave=" + URLEncoder.encode(clavetxt.getText().toString()) + "&direccion=" + URLEncoder.encode(direcciontxt.getText().toString()) + "&ciudad=" + URLEncoder.encode(ciudadtxt.getText().toString()) + "&telefono=" + URLEncoder.encode(telefonotxt.getText().toString()) + "&fotoid=0&fotocomp=0&fechanac=" + URLEncoder.encode("12/06/1983") + "&ecivil=" + URLEncoder.encode(ecivil) + "" ,"status"); //falta arreglar foto
                    idmujer= resultado;

                    if(idmujer.equals("Error"))
                        Toast.makeText(getApplicationContext(), "Existe un problema en su registro, intente nuevamente ", Toast.LENGTH_LONG).show();
                    else{
                        if(idmujer.equals("existe")){
                            Toast.makeText(getApplicationContext(), "El usuario o correo ya existe, intente con uno diferente", Toast.LENGTH_LONG).show();
                        }
                        else{

                            //socioeconomico
                            resultado = obtenerJson("http://dotstudioservices.com/promosbc/MujerJaliscoService/Service1.svc/socioeconomico/?idmujer=" + URLEncoder.encode(idmujer) + "&vivecon=" + URLEncoder.encode(vivecon) + "&viveconnombres=" + URLEncoder.encode(nombresfamiliarestxt.getText().toString()) + "&ingreso=" + URLEncoder.encode(ingresotxt.getText().toString()) + "&casa=" + propia + "&habitaciones=" + URLEncoder.encode(habitacionesspinner.getSelectedItem().toString()) + "&trabaja=" + trabaja + "&lugartrabajo=" + URLEncoder.encode(lugartrabajotxt.getText().toString()) + "","status");
                            if(resultado.equals("Error"))
                                Toast.makeText(getApplicationContext(), "Existe un problema en su estudio socioeconomico, intente nuevamente ", Toast.LENGTH_LONG).show();
                            else {

                                //dependientes

                                resultado = obtenerJson("http://dotstudioservices.com/promosbc/MujerJaliscoService/Service1.svc/agregardependientes/?idmujer=" + URLEncoder.encode(idmujer) + "&nombre=" + URLEncoder.encode(nombredptxt.getText().toString()) + "&apellido=" + URLEncoder.encode(apellidodtxt.getText().toString()) + "&fechanac=" + URLEncoder.encode("12/06/1983") + "&masculino=" + masculino + "&estudiante=1&fotoid=0&parentesco=0" ,"status"); //falta arreglar foto
                                if(resultado.equals("Error"))
                                    Toast.makeText(getApplicationContext(), "Existe un problema en sus dependientes, intente nuevamente ", Toast.LENGTH_LONG).show();
                                else {

                                    data = getSharedPreferences(filename, 0);

                                    SharedPreferences.Editor editor = data.edit();
                                    editor.putString("email", correotxt.getText().toString());
                                    editor.putInt("login", 1);
                                    editor.putString("idmujer",idmujer);
                                    editor.commit();

                                    iddependiente=resultado;

                                    uploadfotos++;
                                    new uploadToServer().execute();

                                }

                            }

                        }

                    }
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Evite utilizar simbolos para registrarse e intente de nuevo", Toast.LENGTH_LONG).show();
                }

            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Existe un problema con la conexion a internet", Toast.LENGTH_LONG).show();
        }
    }


    public class uploadToServer extends AsyncTask<Void, Void, String> {

        private ProgressDialog pd = new ProgressDialog(CuentaActivity.this);

        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Guardando datos...");
            pd.show();
        }

        //no se estan subiendo las fotos
        @Override
        protected String doInBackground(Void... params) {
            try {
                    HttpClient httpclient = new DefaultHttpClient();
                    String URL1="";
                    String URL2="";

                switch (uploadfotos){
                        case 1:
                            URL1="http://dotstudioservices.com/promosbc/cofuServices/Service1.svc/subirimagen1";
                            URL2="http://dotstudioservices.com/promosbc/cofuServices/Service1.svc/asignarfotoid/?idmujer="+idmujer+"";
                            break ;
                        case 2:
                            URL1="http://dotstudioservices.com/promosbc/cofuServices/Service1.svc/subirimagen2";
                            URL2="http://dotstudioservices.com/promosbc/cofuServices/Service1.svc/asignarfotocomp/?idmujer="+idmujer+"";
                            break;
                        case 3:
                            URL1="http://dotstudioservices.com/promosbc/cofuServices/Service1.svc/subirimagen3";
                            URL2="http://dotstudioservices.com/promosbc/cofuServices/Service1.svc/asignarfotodependiente/?iddependiente="+iddependiente+"";
                            break;
                    }

                    HttpPost httppost = new HttpPost(URL1);
                    httppost.setEntity(new ByteArrayEntity(ba));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    String result = EntityUtils.toString(entity);
                    Log.d("Pruebas", result);
                    String resultado= obtenerJson(URL2,"status");
                    Log.d("Pruebas", resultado);
            } catch (Exception e) {
                Log.v("log_tag", "Error in http connection " + e.toString());
            }

            return "Success";
        }


        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pd.hide();
            pd.dismiss();

            Intent mainIntent = new Intent().setClass(CuentaActivity.this, MainActivity.class); //"autologin" despues de crear cuenta
            mainIntent.setAction("na");
            startActivity(mainIntent);

            uploadfotos++;
            if(uploadfotos<4) new uploadToServer().execute();

            finish();
        }
    }



}
