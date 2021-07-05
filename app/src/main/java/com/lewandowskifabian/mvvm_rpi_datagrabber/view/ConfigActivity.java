package com.lewandowskifabian.mvvm_rpi_datagrabber.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.lewandowskifabian.mvvm_rpi_datagrabber.R;

public class ConfigActivity extends AppCompatActivity {

    private TextInputEditText ipInput, sampleTimeInput;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        Intent intent = getIntent();
        Bundle configB = intent.getExtras();

        ipInput = (TextInputEditText) findViewById(R.id.ipInput);
        sampleTimeInput = (TextInputEditText) findViewById(R.id.sampleTimeInput);
        saveButton = (Button) findViewById(R.id.saveButton);


        if(configB != null){
            int sampleTime = configB.getInt("sampleTime");
            String ip = configB.getString("ip");
            sampleTimeInput.setText(String.valueOf(sampleTime));
            ipInput.setText(ip);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOnClick();
            }
        });
    }

    private void saveOnClick(){
        Intent intent = new Intent();
        intent.putExtra("sampleTime", sampleTimeInput.getText().toString());
        intent.putExtra("ip", ipInput.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}