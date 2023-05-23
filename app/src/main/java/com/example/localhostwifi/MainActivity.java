package com.example.localhostwifi;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
//import org.xbill.DNS.dnssec.R;
import java.util.concurrent.CountDownLatch;

//import org.kohsuke.nmap4j.Nmap4j;
//import org.kohsuke.nmap4j.core.NmapExecutionException;
//import org.kohsuke.nmap4j.core.NmapInitializationException;
//import org.kohsuke.nmap4j.data.NMapRun;


public class MainActivity extends AppCompatActivity {

    private EditText hostnameInput;
    private Button resolveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resolveButton = findViewById(R.id.button_find_ip);


        resolveButton.setOnClickListener(view -> {
//            new GetIpAddressTask().execute("treeleafpi.local");
//            String hostname = "google";
////            new LookupTask().execute(hostname);
//            new MDnsAsyncTask().execute();

//            String ipAddress = "192.168.1.72";
//            new MdnsAsyncTask3().execute(ipAddress);
//            HostnameAsyncTask task = new MdnsAsyncTask();
//            task.execute(ipAddress);

        });

    }

    public class GetIpFromHostnameTask extends AsyncTask<String, Void, String> {

        private static final String TAG = "GetIpFromHostnameTask";

        @Override
        protected String doInBackground(String... params) {
            String hostname = params[0];

            try {
                Lookup lookup = new Lookup(hostname, Type.A);
                Record[] records = lookup.run();

                if (records != null) {
                    for (Record record : records) {
                        String ipAddress = record.rdataToString();
                        System.out.println("IP Address: " + ipAddress);
                    }
                } else {
                    System.out.println("No DNS records found for " + hostname);
                }
            } catch (TextParseException e) {
                System.out.println("Invalid hostname: " + e.getMessage());
            }
            return null;

        }


    }

    public class GetIpFromHostnameTask1 extends AsyncTask<String, Void, String> {

        private static final String TAG = "LookupTask";

        @Override
        protected String doInBackground(String... params) {
            String hostname = params[0];
            try {
                Lookup lookup = new Lookup(hostname, Type.A);
                Record[] records = lookup.run();
                if (records != null) {
                    for (Record record : records) {
                        String ipAddress = record.rdataToString();
                        Log.d(TAG, "IP Address: " + ipAddress);
                        return ipAddress;
                    }
                } else {
                    Log.d(TAG, "No DNS records found for " + hostname);
                    return null;
                }
            } catch (TextParseException e) {
                Log.e(TAG, "Invalid hostname: " + e.getMessage());
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                // The result string contains the IP address of the hostname
                Log.d("IP Address", result);
            } else {
                Log.e(TAG, "Unable to resolve IP address for hostname");
            }
        }

    }

    private class GetIpAddressTask extends AsyncTask<String, Void, List<String>> {

        @Override
        protected List<String> doInBackground(String... params) {
            List<String> ipAddresses = new ArrayList<>();
            String url = params[0];
            try {
                InetAddress[] addresses = InetAddress.getAllByName(url);
                for (InetAddress address : addresses) {
                    String ipAddress = address.getHostAddress();
                    ipAddresses.add(ipAddress);
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            return ipAddresses;
        }

        @Override
        protected void onPostExecute(List<String> ipAddresses) {
            if (ipAddresses.isEmpty()) {
                 System.out.println("No IP address found for this domain name");
//                mTextViewResult.setText("No IP address found for this domain name");
            } else {
                StringBuilder sb = new StringBuilder();
                for (String ipAddress : ipAddresses) {
                    sb.append(ipAddress).append("\n");
                }
//                mTextViewResult.setText(sb.toString());
                System.out.println(sb);
            }
        }
    }

    public class MDnsAsyncTask extends AsyncTask<Void, Void, List<String>> {
        private Context mContext;


        @Override
        protected List<String> doInBackground(Void... params) {
            List<String> ipList = new ArrayList<>();

            try {
                InetAddress[] inetAddresses = InetAddress.getAllByName("http, .local");
                for (InetAddress address : inetAddresses) {
                    ipList.add(address.getHostAddress());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return ipList;
        }

        @Override
        protected void onPostExecute(List<String> ipList) {
            if (ipList.isEmpty()) {
                System.out.println("No IP address found for this domain name");
//                mTextViewResult.setText("No IP address found for this domain name");
            } else {
//                StringBuilder sb = new StringBuilder();
//                for (String ipAddress : ipList) {
//                    sb.append(ipAddress).append("\n");
//                }
//                mTextViewResult.setText(sb.toString());
                System.out.println(ipList);
            }
        }

//        public interface OnTaskCompleted {
//            void onTaskCompleted(List<String> ipList);
//        }


    }

    public class HostnameAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String ipAddress = params[0];

            try {
                InetAddress inetAddress = InetAddress.getByName(ipAddress);
                System.out.println(inetAddress + "test");
                String hostname = inetAddress.getHostName();
                System.out.println(hostname + "host name is ");
                return hostname;
            } catch (UnknownHostException e) {
                e.printStackTrace();
                return "Unknown Host";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println(result);
        }


    }

    public class MdnsAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String ipAddress = params[0];
            String serviceType = "_http._tcp.local."; // replace with the mDNS service type you're looking for

            try {
                JmDNS jmdns = JmDNS.create();
                ServiceInfo[] services = jmdns.list(serviceType);
                System.out.println(services);
                for (ServiceInfo info : services) {
                    if (ipAddress.equals(info.getInetAddresses()[0].getHostAddress())) {
                        return info.getServer();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "Unknown";
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println(result);
            // handle the result here
//            Toast.makeText(context, "Hostname: " + result, Toast.LENGTH_SHORT).show();
        }
    }

    public class MdnsAsyncTask2 extends AsyncTask<String, Void, ServiceInfo[]> {

        @Override
        protected ServiceInfo[] doInBackground(String... params) {
            try {
                InetAddress address = InetAddress.getByName(params[0]); // Get the mDNS server's IP address from the first parameter
                System.out.println(address);
                JmDNS jmdns = JmDNS.create(address);
                System.out.println(jmdns);
                String serviceType = params[1]; // Get the service type from the second parameter
                ServiceInfo[] services = jmdns.list(serviceType);

                jmdns.close(); // Remember to close the JmDNS instance when you are done

                return services;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ServiceInfo[] services) {
            System.out.println(services);
            if (services != null) {
                for (ServiceInfo info : services) {
                    System.out.println(info.getName()); // Print the name of each available service
                }
            } else {
                System.out.println("Failed to get services from mDNS server");
            }
        }
    }

    public class MdnsAsyncTask3 extends AsyncTask<String, Void, ServiceInfo> {
        private Exception exception;

        protected ServiceInfo doInBackground(String... params) {
            String ip = params[0];
            ServiceInfo serviceInfo = null;
            try {
                JmDNS jmdns = JmDNS.create();
                System.out.println(jmdns);
                serviceInfo = jmdns.getServiceInfo(".local.", ip);
                jmdns.close();
            } catch (IOException e) {
                this.exception = e;
            }
            return serviceInfo;
        }

        protected void onPostExecute(ServiceInfo serviceInfo) {
            System.out.println(serviceInfo);
            if (this.exception != null) {
                // Handle the exception
            } else if (serviceInfo != null) {
                // The server details were found
                String hostname = serviceInfo.getHostAddress();
                int port = serviceInfo.getPort();
                String serverName = serviceInfo.getServer();
                // Do something with the server details
            } else {
                // The server details were not found
            }
        }
    }


}



