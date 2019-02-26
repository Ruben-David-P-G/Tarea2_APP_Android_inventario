package mx.com.ecube.inventory.inventory_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProducersEditActivity extends AppCompatActivity  implements  View.OnClickListener, Response.Listener,Response.ErrorListener{

    private RequestQueue queue, Que;
    EditText  edit_contra, edit_name;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producers_edit);

        String valor = getIntent().getExtras().getString("id");

        this.queue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(StringRequest.Method.GET,"http://192.168.1.185:8000/api/producers/"+valor,this,this);
        this.queue.add(stringRequest);

        edit_contra =(EditText)findViewById(R.id.edit_contra);
        edit_name =(EditText)findViewById(R.id.edit_name);
        spinner =findViewById(R.id.season_spinner2);


        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.seasons_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        this.Que= Volley.newRequestQueue(this);
    }

    public void onClick(View view){
        Intent i;
        switch(view.getId()){

            case R.id.btn_edit_cancel:
                finish();
                break;
            case R.id.btn_editar:
                this.save();
                finish();
                break;
            default: return;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }



    @Override
    public void onResponse(Object response) {
        try {
            JSONObject result = new JSONObject(response.toString());



            String name= result.getString("name");
            String contract= result.getString("contract");
            int season= result.getInt("season");
            int id= result.getInt("id");

            edit_contra.setText(contract);
            edit_name.setText(name);
            spinner.setSelection(season);

            //Toast.makeText(getApplicationContext(), "selected Item Name is "+ name, Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();

        }


    }



    public  void  save(){
        String valor = getIntent().getExtras().getString("id");
        RequestQueue qu=Volley.newRequestQueue(this);
        CustomStringRequest stringReque = new CustomStringRequest(StringRequest.Method.PATCH,"http://192.168.1.185:8000/api/producers/"+valor,this,this,this.getParams());
        qu.add(stringReque);

    }

    private Map<String, String> getParams(){
        Map<String, String> mParams=new HashMap<>();

        EditText txtContract =findViewById(R.id.edit_contra);
        mParams.put("contract",txtContract.getText().toString());

        EditText txtName =findViewById(R.id.edit_name);
        mParams.put("name",txtName.getText().toString());

        Spinner seasonSpiner=findViewById(R.id.season_spinner2);
        mParams.put("season",seasonSpiner.getSelectedItem().toString());

        String valor = getIntent().getExtras().getString("id");
        mParams.put("id",valor);

        return mParams;
    }
    public void guardar_edit(View s){
        this.save();
        finish();
    }
    public void cerrar(View s){
        finish();
    }

    public void borrar_producto(View s){
        String valor = getIntent().getExtras().getString("id");
        RequestQueue quert=Volley.newRequestQueue(this);
        CustomStringRequest stringReque = new CustomStringRequest(StringRequest.Method.DELETE,"http://192.168.1.185:8000/api/producers/"+valor,this,this,this.getParams());
        quert.add(stringReque);
        finish();
    }


}
