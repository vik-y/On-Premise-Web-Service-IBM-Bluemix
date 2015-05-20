package vikasyadav.iiitb.com.ibm_onpremiseweb;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;


public class TableActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        TextView display = (TextView)findViewById(R.id.display);

        String intentdata = getIntent().getStringExtra("EXTRA_JSON").toString();
        intentdata = intentdata.replace("\\", "\\\\ ");
        display.setText(intentdata);

        /*JsonParser parser = new JsonParser();
        Object obj = parser.parse(intentdata);
        JSONArray array = (JSONArray)obj;


        try{
            display.setText(array.get(1).toString());
        }
        catch(JSONException e){
            display.setText("sucks");
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_table, menu);
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
