package br.com.fedablio.ima;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.Formatter;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class EnderecoActivity extends Activity {

    private TextView tvIp01;
    private TextView tvIp02;
    private TextView tvIp03;
    private TextView tvIp04;
    private TextView tvIp05;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endereco);
        tvIp01 = (TextView) findViewById(R.id.textViewIp01);
        tvIp02 = (TextView) findViewById(R.id.textViewIp02);
        tvIp03 = (TextView) findViewById(R.id.textViewIp03);
        tvIp04 = (TextView) findViewById(R.id.textViewIp04);
        tvIp05 = (TextView) findViewById(R.id.textViewIp05);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        tvIp01.setText(linkLocal());
        tvIp02.setText(macAddress());
        tvIp03.setText(ipv4());
        tvIp04.setText(ipv6());
        tvIp05.setText(ipv4L());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_endereco, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.mnPrincipal){
            Intent intent = new Intent(EnderecoActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public String linkLocal() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        boolean isIPv4 = sAddr.indexOf(':')<0;
                        if (false) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%');
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception erro) {
            return "-";
        }
        return "-";
    }

    public String macAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    String hex = Integer.toHexString(b & 0xFF);
                    if (hex.length() == 1)
                        hex = "0".concat(hex);
                    res1.append(hex.concat(":"));
                }
                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception erro) {
            return "-";
        }
        return "-";
    }

    public String ipv4() {
        String ip = "";
        try {
            URL url = new URL("https://ipv4.wtfismyip.com/text");
            InputStreamReader isr = new InputStreamReader(url.openStream());
            BufferedReader br = new BufferedReader(isr);
            return ip = br.readLine();
        } catch(Exception erro){
            return "-";
        }
    }

    public String ipv6() {
        String ip = "";
        try {
            URL url = new URL("https://ipv6.wtfismyip.com/text");
            InputStreamReader isr = new InputStreamReader(url.openStream());
            BufferedReader br = new BufferedReader(isr);
            return ip = br.readLine();
        } catch(Exception erro){
            return "-";
        }
    }

    public String ipv4L() {
        String ip = "";
        try {
            for (Enumeration enumeration = NetworkInterface.getNetworkInterfaces(); enumeration.hasMoreElements();){
                NetworkInterface nInterface = (NetworkInterface) enumeration.nextElement();

                for (Enumeration IPenumeration = nInterface.getInetAddresses(); IPenumeration.hasMoreElements();){
                    InetAddress netAdress = (InetAddress) IPenumeration.nextElement();
                    if (!netAdress.isLoopbackAddress()  &&  netAdress instanceof Inet4Address){
                        return ip = netAdress.getHostAddress();
                    }
                }
            }
        }  catch(Exception erro){
            return "-";
        }
        return "-";
    }

}