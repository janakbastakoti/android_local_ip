package com.example.localhostwifi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class IPAddressFinder {
    public static String getIPAddress(String hostname) throws IOException {
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
    }
}
