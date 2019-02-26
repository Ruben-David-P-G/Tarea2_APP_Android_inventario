package mx.com.ecube.inventory.inventory_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MaterialsEditActivity extends AppCompatActivity  implements  View.OnClickListener, Response.Listener,Response.ErrorListener {

    private RequestQueue queue, Que;
    EditText  edite_code, edite_line, edite_description, edite_unit, edite_stock;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materials_edit);
        String valor = getIntent().getExtras().getString("id");

        this.queue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(StringRequest.Method.GET,"http://192.168.1.185:8000/api/materials/"+valor,this,this);
        this.queue.add(stringRequest);

        edite_code =(EditText)findViewById(R.id.edite_code);
        edite_line =(EditText)findViewById(R.id.edite_line);
        edite_description =(EditText)findViewById(R.id.edite_description);
        edite_unit =(EditText)findViewById(R.id.edite_unit);
        edite_stock =(EditText)findViewById(R.id.edite_stock);
        spinner =findViewById(R.id.season_spinner3);


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

            int season= result.getInt("season");
            String description= result.getString("description");
            String unit= result.getString("unit");
            String code= result.getString("code");
            String line= result.getString("line");
            String stock= result.getString("stock");

            edite_description.setText(description);
            edite_unit.setText(unit);
            edite_code.setText(code);
            edite_line.setText(line);
            edite_stock.setText(stock);
            spinner.setSelection(season);


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error en mostrar: "+ e.getMessage(), Toast.LENGTH_LONG).show();

        }


    }



    public  void  save(){
        String valor = getIntent().getExtras().getString("id");
        RequestQueue qu=Volley.newRequestQueue(this);
        CustomStringRequest stringReque = new CustomStringRequest(StringRequest.Method.PATCH,"http://192.168.1.185:8000/api/materials/"+valor,this,this,this.getParams());
        qu.add(stringReque);

    }

    private Map<String, String> getParams(){
        Map<String, String> mParams=new HashMap<>();


        mParams.put("description",edite_description.getText().toString());
        mParams.put("unit",edite_unit.getText().toString());
        mParams.put("season",spinner.getSelectedItem().toString());
        mParams.put("code",edite_code.getText().toString());
        mParams.put("line",edite_line.getText().toString());
        mParams.put("stock",edite_stock.getText().toString());
        String valor = getIntent().getExtras().getString("id");
        mParams.put("id",valor);

        return mParams;
    }


    public void guardar_edite_material(View s){
        this.save();
        finish();
    }
    public void cerrar(View s){
        finish();
    }

    public void borrar_material(View s){
        String valor = getIntent().getExtras().getString("id");
        RequestQueue quert=Volley.newRequestQueue(this);
        CustomStringRequest stringReque = new CustomStringRequest(StringRequest.Method.DELETE,"http://192.168.1.185:8000/api/materials/"+valor,this,this,this.getParams());
        quert.add(stringReque);
        finish();
    }
}
