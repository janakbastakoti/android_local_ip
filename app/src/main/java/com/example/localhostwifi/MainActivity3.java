package com.example.localhostwifi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.UnknownHostException;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import java.net.Socket;

import java.net.SocketException;

import java.net.Inet4Address;
import android.os.AsyncTask;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;


public class MainActivity3 extends AppCompatActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        MyTask task = new MyTask();
//        task.execute();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
                    ServiceInfo[] serviceInfos = jmdns.list("_http._tcp.local.");
                    for (ServiceInfo serviceInfo : serviceInfos) {
                        String hostAddress = serviceInfo.getHostAddresses()[0];
                        Log.d("Host address", hostAddress);
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                  printLocalIpAddresses();
//                String ipAddress = getIpAddressFromHostname("treeleafpi.local");
//                testAddress();
//                testAddress();
//                InetAddress addr = InetAddress.getHostAddress();


//                String ipAddress = getIpAddressFromHostname("http://treeleafpi.local:8000/");
//                if (ipAddress != null) {
//                    System.out.println("IP address of example.com: " + ipAddress);
//                } else {
//                    System.out.println("Failed to get IP address of treeleafpi.local " + ipAddress);
//                }


//                InetAddress addr = InetAddress.getByName("treeleafpi.local");
//                String serverName= addr.getHostName();
////                Log.e("ServerName",serverName);
//                System.out.println("IP address of " + serverName);

//                String domainName = "treeleafpi"; // replace with your domain name
//                String ipAddress = getIpAddress(domainName);
//                System.out.println("IP address of " + domainName + " is " + ipAddress);
//                getAllIpAddress();
//                getAllIP();

            }
        });


    }

    public class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface networkInterface = networkInterfaces.nextElement();
                    if (!networkInterface.isLoopback() && networkInterface.isUp()) {
                        Log.d("Interface name", networkInterface.getName());
                        Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                        while (addresses.hasMoreElements()) {
                            InetAddress address = addresses.nextElement();
                            if (address instanceof InetAddress) {
                                try {
                                    String ip = InetAddress.getByName(address.getHostName()).getHostAddress();
                                    System.out.println("IP address  " +  ip);
                                } catch (UnknownHostException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    public static void printLocalIpAddresses() {
//        try {
//            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
//            while (networkInterfaces.hasMoreElements()) {
//                NetworkInterface networkInterface = networkInterfaces.nextElement();
//                if (!networkInterface.isLoopback() && networkInterface.isUp()) {
//                    System.out.println("Interface name: " + networkInterface.getName());
//                    Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
//                    while (addresses.hasMoreElements()) {
//                        InetAddress address = addresses.nextElement();
//                        if (address instanceof java.net.Inet4Address) {
//                            // handle IPv4 address
//                            System.out.println("IPv4 address: " + address.getHostAddress());
//                        } else if (address instanceof java.net.Inet6Address) {
//                            // handle IPv6 address
//                            System.out.println("IPv6 address: " + address.getHostAddress());
//                        }
//                    }
//                }
//            }
//        } catch (SocketException e) {
//            e.printStackTrace();
//        }
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (!networkInterface.isLoopback() && networkInterface.isUp()) {
                    System.out.println("Interface name: " + networkInterface.getName());
                    Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress address = addresses.nextElement();
                        if (address instanceof java.net.Inet4Address) {
                            String hostname = address.getHostName();
                            String ip = InetAddress.getByName(hostname).getHostAddress();
                            System.out.println("IPv4 address: " + ip);
                        } else if (address instanceof java.net.Inet6Address) {
                            System.out.println("IPv6 address: " + address.getHostAddress());
                        }
                    }
                }
            }
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }


//    public static void testAddress() throws Exception {
//        // a variable of type InetAddress to store
//        // the address of the local host
//        try {
//            InetAddress addr = InetAddress.getLocalHost();
//        } catch (UnknownHostException e) {
////            e.printStackTrace();
//        }
//
//        // Returns the IP address string in
//        // textual presentation.
////        System.out.println("Local HostAddress:  "
////                + addr.getHostAddress());
////        // Gets the host name for this IP address.
////        System.out.println("Local host name: "
////                + addr.getHostName());
//        return addr ;
//    }

//    public static String getIpAddressFromHostname(String hostname) {
//        try {
//            InetAddress[] addresses = InetAddress.getAllByName(hostname);
//            for (InetAddress address : addresses) {
//                if (address.isSiteLocalAddress()) {
//                    return address.getHostAddress();
//                }
//            }
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    private String getLocalIpAddress() {
        String ipAddress = "";
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (!address.isLinkLocalAddress() && !address.isLoopbackAddress() && address.getHostAddress().indexOf(":") == -1) {
                        ipAddress = address.getHostAddress();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ipAddress;
    }

    private static String getIpAddress(String domainName) {
        String ipAddress = "";
        try {
            InetAddress inetAddress = InetAddress.getByName(domainName);
            System.out.println(inetAddress);
            ipAddress = inetAddress.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ipAddress;
    }

    private static String getIpAddressSocket(String domainName) {
        String ipAddress = "";
        try {
            System.out.println("IP address of " + domainName);
            Socket socket = new Socket(domainName, 8000);
            InetAddress inetAddress = socket.getLocalAddress();
            System.out.println("IP address of " + inetAddress);
            ipAddress = inetAddress.getHostAddress();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ipAddress;
    }


    private static void getAllIpAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (networkInterface.isUp() && !networkInterface.isLoopback()) {
                    Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddress = inetAddresses.nextElement();
                        System.out.println(inetAddress);
                        if (inetAddress.getHostAddress().startsWith("192.168.")) {
                            System.out.println(networkInterface + networkInterface.getName() + "   test  " + inetAddress.getHostAddress());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getAllIP() {
        String subnet = "192.168.0";
        for (int i = 1; i <= 255; i++) {
            String host = subnet + "." + i;
            try {
                InetAddress address = InetAddress.getByName(host);
                if (address.isReachable(1000)) {
                    String hostname = address.getCanonicalHostName();
                    System.out.println(hostname + " " + address.getHostAddress());
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}

