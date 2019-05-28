package com.example.qra;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class SpinnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);



        //массивы значений
        String [] genCatString = {"1", "2", "3"};
        String [] idString = {"10", "20", "30"};

        // адаптеры, в которые кидаются массивы значений
        ArrayAdapter<String> genCatAd = new ArrayAdapter<String>
                (getApplicationContext(), android.R.layout.simple_spinner_item, genCatString);
        ArrayAdapter<String> idAd = new ArrayAdapter<String>
                (getApplicationContext(), android.R.layout.simple_spinner_item, idString);

        // установка адаптеров
        genCatAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        idAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        //инициализация спинеров
        Spinner genCatSpn = (Spinner) findViewById(R.id.gen_cat_spn);
        genCatSpn.setAdapter(genCatAd);
        genCatSpn.setOnItemSelectedListener(onItemSelectedListener());

        Spinner idSpn = (Spinner) findViewById(R.id.id_spn);
        idSpn.setAdapter(idAd);
        idSpn.setOnItemSelectedListener(onItemSelectedListener());
    }

    // аналог OnClickListener
    AdapterView.OnItemSelectedListener onItemSelectedListener()
    {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }
}
