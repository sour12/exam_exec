package com.exam_exec;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ret = "";
                ret = runCommand(editText.getText().toString());
                textView.setText(ret);
            }
        });
    }

    String runCommand(String cmd) {
        String result = "";
        String[] command = {"/system/bin/sh", "-c", cmd};
        try {
            /* command execution */
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(command);

            /* parsing return string */
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String line;
            while ((line = stdInput.readLine()) != null) {
                result += (line + "\n");
            }
            while ((line = stdError.readLine()) != null) {
                result += (line + "\n");
            }
        } catch (IOException e) { e.printStackTrace(); }
        return result;
    }
}