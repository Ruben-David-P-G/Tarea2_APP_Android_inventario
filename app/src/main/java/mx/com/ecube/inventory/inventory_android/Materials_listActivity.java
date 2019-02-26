package mx.com.ecube.inventory.inventory_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Materials_listActivity extends AppCompatActivity implements  Response.Listener,Response.ErrorListener{
    public static final String TARGET_URL = "http://192.168.1.185:8000/api/materials";
    private RequestQueue queue, queue2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materials_list);

        this.queue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(StringRequest.Method.GET,TARGET_URL,this,this);
        this.queue.add(stringRequest);

    }
    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        this.queue2= Volley.newRequestQueue(this);
        StringRequest stringRequest2=new StringRequest(StringRequest.Method.GET,TARGET_URL,this,this);
        this.queue2.add(stringRequest2);
    }


    @Override
    public void onResponse(Object response) {

        try {

            JSONArray arrayJson = new JSONArray(response.toString());
            final ListView list=findViewById(R.id.MaterialsList);
            ArrayList<String> items =new ArrayList<String>();

            for (int i =0; i<arrayJson.length(); i++){
                JSONObject producer= arrayJson.getJSONObject(i);
                String name= producer.getString("id");
                int id= producer.getInt("id");

                items.add(name);
            }
            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,
                    android.R.layout.simple_expandable_list_item_1,items);
            list.setAdapter(arrayAdapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick (AdapterView < ? > adapter, View view, int position, long arg){
                    int item = position;
                    String itemval = (String) list.getItemAtPosition(position);
                    //Toast.makeText(getApplicationContext(), "selected Item Name is "+ itemval, Toast.LENGTH_LONG).show();
                    Intent b =new Intent(view.getContext(),MaterialsEditActivity.class);
                    b.putExtra("id", itemval);
                    startActivity(b);
                }
            });

            //Mostrar listview y quitar  loading
        }catch (JSONException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }


    }
}
