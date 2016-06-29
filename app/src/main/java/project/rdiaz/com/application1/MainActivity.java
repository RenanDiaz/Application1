package project.rdiaz.com.application1;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    Button guardar;
    private RestOperation restOperation = null;
    EditText latitud, longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        guardar = (Button)findViewById(R.id.guardar_datos);
        guardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                latitud = (EditText)findViewById(R.id.latitud);
                longitud = (EditText)findViewById(R.id.longitud);
                String latitud_post= latitud.getText().toString();
                String longitud_post= longitud.getText().toString();
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Post...", Toast.LENGTH_SHORT);

                toast1.show();

                restOperation = new RestOperation();
                restOperation.execute("http://190.141.120.200:8080/Rutas/ubicacion/guardar/123456?latitud="+latitud_post+"&longitud="+longitud_post);
            }
        });


    }

    private class RestOperation extends AsyncTask<String, Void, Void> {

        final HttpClient httpClient = new DefaultHttpClient();
        String content, error, data;
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        TextView serverDataRecived = (TextView) findViewById(R.id.home);
        JSONObject jsonResponse;
        Boolean islogin_message= false;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            try {
                data += "&" + URLEncoder.encode("data", "UTF-8") + "-" ;//+ userinput.getText();
            }catch (UnsupportedEncodingException e) {
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Nombre de usuario/contraseña inválidos.", Toast.LENGTH_SHORT);

                toast1.show();
                e.printStackTrace();
            }

        }

        @Override
        protected Void  doInBackground(String... params){
            BufferedReader br = null;
            URL url;


            try {
                url = new URL(params[0]);
                URLConnection connection =  url.openConnection();
                connection.setDoOutput(true);
                br =  new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String line =  null;
                while ((line = br.readLine()) != null){
                    sb.append(line);
                    sb.append(System.getProperty("line.separator"));
                    content =  sb.toString();

                }

            } catch (MalformedURLException e ){
                error = e.getMessage();
                e.printStackTrace();
            } catch (IOException e) {
                error = e.getMessage();
                e.printStackTrace();
            } finally {
                /*try {
                    br.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
                */
                if ((br != null)) {
                    try {
                        br.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            if (error!=null){
                Log.i("Error", error);
                if(islogin_message == false){
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "error", Toast.LENGTH_LONG);

                    toast1.show();
                }

            }
            /*else {
                try {
                    jsonResponse =  new JSONObject(content);
                    //System.out.println("valor del json.. " + jsonResponse);
                    //String isLoginResponse = jsonResponse.getString("IsLogin");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
             */
        }
    }

}
