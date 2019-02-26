package mx.com.ecube.inventory.inventory_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MaterialsFormActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener,Response.ErrorListener {
    public static final String TARGET_URL = "http://192.168.1.185:8000/api/materials";
    private RequestQueue Queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materials_form);

        Button btnsave=findViewById(R.id.btn_save_material);
        btnsave.setOnClickListener(this);

        Button btncancel=findViewById(R.id.btn_cancel_material);
        btncancel.setOnClickListener(this);

        Spinner spinner =findViewById(R.id.season_spinner2);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.seasons_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        this.Queue= Volley.newRequestQueue(this);
    }
    public void onClick(View view){
        Intent i;
        switch(view.getId()){

            case R.id.btn_cancel_material:
                finish();
                break;
            case R.id.btn_save_material:
                this.save();
                break;
            default: return;
        }
    }



    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,"Error: "+error.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(Object response) {
        Toast.makeText(this,response.toString(),Toast.LENGTH_LONG).show();
    }



    public  void  save(){
        RequestQueue q=Volley.newRequestQueue(this);
        CustomStringRequest stringRequest = new CustomStringRequest(StringRequest.Method.POST,TARGET_URL,this,this,this.getParams());
        q.add(stringRequest);
    }

    private Map<String, String> getParams(){
        Map<String, String> Parametros=new HashMap<>();


        Spinner seasonSpiner=findViewById(R.id.season_spinner2);
        Parametros.put("season",seasonSpiner.getSelectedItem().toString());

        EditText edit_code =findViewById(R.id.edit_code);
        Parametros.put("code",edit_code.getText().toString());

        EditText edit_line =findViewById(R.id.edit_line);
        Parametros.put("line",edit_line.getText().toString());

        EditText edit_description =findViewById(R.id.edit_description);
        Parametros.put("description",edit_description.getText().toString());

        EditText edit_unit =findViewById(R.id.edit_unit);
        Parametros.put("unit",edit_unit.getText().toString());

        EditText edit_stock =findViewById(R.id.edit_stock);
        Parametros.put("stock",edit_stock.getText().toString());

        return Parametros;
    }
}
