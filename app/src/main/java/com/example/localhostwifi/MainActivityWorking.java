package com.example.localhostwifi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainActivityWorking extends AppCompatActivity {

    private EditText inputHostname;
    private Button buttonFindIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_working);

        inputHostname = findViewById(R.id.input_hostname);
        buttonFindIp = findViewById(R.id.button_find_ip);

        buttonFindIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hostname = inputHostname.getText().toString();
                new GetIPAddressTask().execute(hostname);
            }
        });
    }

    private class GetIPAddressTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String hostname = params[0];
            try {
                Process process = Runtime.getRuntime().exec("ping -c 1 " + hostname);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("from")) {
                        String[] words = line.split(" ");
                        return words[3].substring(0, words[3].length() - 1);
                    }
                }

                return InetAddress.getByName(hostname).getHostAddress();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                Log.d("IP address", result);
            } else {
                Log.d("IP address", "Error finding IP address");
            }
        }
    }
}
