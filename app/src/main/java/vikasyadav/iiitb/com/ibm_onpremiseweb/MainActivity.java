package vikasyadav.iiitb.com.ibm_onpremiseweb;

import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    EditText rtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button rbutton = (Button)findViewById(R.id.button);
        Button adduser = (Button)findViewById(R.id.adduser);
        final EditText username, fullname, age_person, gender, location, email;
        username = (EditText)findViewById(R.id.username);
        fullname = (EditText)findViewById(R.id.fullname);
        email = (EditText)findViewById(R.id.email);
        age_person = (EditText)findViewById(R.id.age);
        gender = (EditText)findViewById(R.id.gender);
        location = (EditText)findViewById(R.id.location);

        rtext = (EditText)findViewById(R.id.output);

        //1 - type, GET or POST
        //2 - key value pairs
        rbutton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                CallServer httpget = new CallServer();
                //http
                httpget.execute(
                        new BasicNameValuePair("url", "http://middlewareapp.mybluemix.net/NewFile.jsp?api=get_users"),
                        new BasicNameValuePair("type", "GET")
                );

            }
        });

        adduser.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                CallServer httpget = new CallServer();
                //http
                httpget.execute(
                        new BasicNameValuePair("url", "http://middlewareapp.mybluemix.net/NewFile.jsp?api=add_user"),
                        new BasicNameValuePair("type", "POST"),
                        new BasicNameValuePair("age", age_person.getText().toString()),
                        new BasicNameValuePair("fullname", fullname.getText().toString()),
                        new BasicNameValuePair("username", username.getText().toString()),
                        new BasicNameValuePair("email", email.getText().toString()),
                        new BasicNameValuePair("gender", gender.getText().toString()),
                        new BasicNameValuePair("location", fullname.getText().toString())
                );
            }
        });
    }

    private class CallServer extends AsyncTask<NameValuePair, Integer, String> {
        @Override
        protected String doInBackground(NameValuePair... urls) {
            //String resp = "";
            String requestUrl = urls[0].getValue();
            List<NameValuePair> passlist = new ArrayList<NameValuePair>() ;



            JParser jparser = new JParser();

            int i=1;
            for(NameValuePair nvp:urls){
                if(i>2){
                    passlist.add(new BasicNameValuePair(nvp.getName(), nvp.getValue()));
                }
                i+=1;
            }

            String resp = jparser.makeHttpRequest(requestUrl, urls[1].getValue(), passlist);
            if(resp!=null) Log.v("JSONTESt", resp.toString());


            // TODO Auto-generated method stub


            if (resp!=null) return resp.toString();
            else return "null statement received";

        }

        @Override
        protected void onPostExecute(String result) {
            rtext.setText(result);
            String passdata = rtext.getText().toString();

            if(passdata.length()>15){
                Intent intent = new Intent(getBaseContext(), TableActivity.class);
                intent.putExtra("EXTRA_JSON", passdata);
                startActivity(intent);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
