
package com.susmitasen.weatherreport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Cityfind extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cityfind);
        final EditText editText=findViewById(R.id.searchcity);
        ImageView backbutton=findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String newcity=editText.getText().toString();
                Intent intent=new Intent(Cityfind.this,MainActivity.class);
                intent.putExtra("City",newcity);
                startActivity(intent);
                return false;
            }
        });
    }
}