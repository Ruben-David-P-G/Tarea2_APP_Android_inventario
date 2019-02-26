package mx.com.ecube.inventory.inventory_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnNewProducer=findViewById(R.id.btn_new_producer);
        btnNewProducer.setOnClickListener(this);

        Button btnAllProducer=findViewById(R.id.btn_all_producers);
        btnAllProducer.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        Intent i;
    switch(view.getId()){

        case R.id.btn_new_producer:
            i= new Intent(this,ProducersFormActivity.class);
            startActivity(i);
            break;
        case R.id.btn_all_producers:
            i=new Intent(this,ProducersListActivity.class);
            startActivity(i);

            break;

        /*case R.id.btn_nuevo_material:
            i=new Intent(this,MaterialsFormActivity.class);
            startActivity(i);

            break;*/

            default: return;
    }
    }
    public void Nuevo_Material(View a){
        Intent i = new Intent(this,MaterialsFormActivity.class);
        startActivity(i);
    }
    public void listar_Material(View a){
        Intent i = new Intent(this,Materials_listActivity.class);
        startActivity(i);
    }
}
