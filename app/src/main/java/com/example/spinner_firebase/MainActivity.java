package com.example.spinner_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView text;
    Button button;
    Spinner spin;
    DatabaseReference databaseReference;
    String item;
    Member member;

    String[] qualification = {"choose", "BCA", "MCA", "BTech", "MTech", "BBA", "BA", "BCom"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.textView);
        button = findViewById(R.id.upload);
        spin = findViewById(R.id.spinner);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("value");
        spin.setOnItemSelectedListener(this);

        member = new Member();
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, qualification);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savevalue(item);
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item = spin.getSelectedItem().toString();
        text.setText(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void savevalue(String item)
    {
        if (item == "choose") {
            Toast.makeText(this, "Select qualification", Toast.LENGTH_LONG).show();
        } else {
            member.setQualification(item);
            String id = databaseReference.push().getKey();
            databaseReference.child(id).setValue(member);
            Toast.makeText(this, "value saved", Toast.LENGTH_SHORT).show();
        }
    }
}
