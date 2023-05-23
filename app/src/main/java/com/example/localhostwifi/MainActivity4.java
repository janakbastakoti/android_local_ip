package com.example.localhostwifi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.net.UnknownHostException;
import java.net.InetAddress;


import java.io.IOException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainActivity4 extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);


        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
//                    String ipAddress = IPAddressFinder.getIPAddress("bikashsaud");
//                    System.out.println("Ip address::: " +  ipAddress + " find ip address ");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                String hostname = "bikashsaud";
                new GetIPAddressTask().execute(hostname);



            }
        });
    }

    private class GetIPAddressTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... hostnames) {
            String hostname = hostnames[0];
            String ipAddress = null;
            try {
                Process process = Runtime.getRuntime().exec("ping -c 1 " + hostname);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("from")) {
                        String[] words = line.split(" ");
                        ipAddress = words[3].substring(0, words[3].length() - 1);
                        break;
                    }
                }

                if (ipAddress == null) {
                    ipAddress = InetAddress.getByName(hostname).getHostAddress();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return ipAddress;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Log.d("IP address", result);
            } else {
                Log.d("IP address", "Result is null");
            }
        }
    }






}