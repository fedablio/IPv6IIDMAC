package br.com.fedablio.ima;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import br.com.fedablio.model.Ipv6;

public class MainActivity extends Activity {

    private Ipv6 ipv6 = new Ipv6();
    private Spinner spBits;
    private EditText etMacAddress;
    private EditText etPrefixoIpv6;
    private Button btIpv6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spBits = (Spinner) findViewById(R.id.spinnerBits);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.array01, R.layout.spinner_layout);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBits.setAdapter(adapter);
        etMacAddress = (EditText) findViewById(R.id.editTextMacAddress);
        etPrefixoIpv6 = (EditText) findViewById(R.id.editTextPrefixoIpv6);
        btIpv6 = (Button) findViewById(R.id.buttonIpv6);
    }

    public void converter(View view) {
        ipv6.setBits(spBits.getSelectedItem().toString());
        ipv6.setMac(etMacAddress.getText().toString());
        ipv6.setPrefixo(etPrefixoIpv6.getText().toString());
        if (ipv6.getBits().equals("Bits (Interface)") || ipv6.getMac().equals("") || ipv6.getPrefixo().equals("")) {
            Toast.makeText(this, "Bits (Interface), MAC Address e Prefixo IPv6 devem ser preenchidos.", Toast.LENGTH_SHORT).show();
        } else {
            String macaddress01 = ipv6.getMac();
            String macaddress02 = macaddress01.replace(":", "");
            String macaddress03 = macaddress02.replace("-", "");
            String macaddress04 = macaddress03.replace(".", "");
            if (ipv6.getBits().equals("48")) {
                if (macaddress04.length() != 12) {
                    Toast.makeText(this, "Mac Address de 48 bits devem conter 12 letras/números.", Toast.LENGTH_SHORT).show();
                } else {
                    String primeiraParte = macaddress04.substring(0, 1);
                    String segundaParte = macaddress04.substring(1, 2);
                    String terceiraParte = macaddress04.substring(2, 6);
                    String quartaParte = macaddress04.substring(6, 12);
                    String binarios = hexadecimalBinary(primeiraParte, segundaParte);
                    String p1 = binarios.substring(0, 6);
                    String p2 = binarios.substring(6, 7);
                    String p3 = binarios.substring(7, 8);
                    switch (p2) {
                        case "0":
                            p2 = "1";
                            break;
                        default:
                            p2 = "0";
                    }
                    String alterado = p1 + p2 + p3;
                    String hd01 = alterado.substring(0, 4);
                    String hd02 = alterado.substring(4, 8);
                    String montado = binaryHexadecimal(hd01, hd02);
                    String mac64 = montado + terceiraParte + "FFFE" + quartaParte;
                    String q1 = mac64.substring(0, 4);
                    String q2 = mac64.substring(4, 8);
                    String q3 = mac64.substring(8, 12);
                    String q4 = mac64.substring(12, 16);
                    String mc01 = q1 + ":" + q2 + ":" + q3 + ":" + q4;
                    ipv6.setResultado(ipv6.getPrefixo().toUpperCase() + ":" + mc01.toUpperCase());
                    btIpv6.setText(ipv6.getResultado());
                }
            } else {
                if (macaddress04.length() != 16) {
                    Toast.makeText(this, "Mac Address de 64 bits devem conter 16 letras/números.", Toast.LENGTH_SHORT).show();
                } else {
                    String q1 = macaddress04.substring(0, 4);
                    String q2 = macaddress04.substring(4, 8);
                    String q3 = macaddress04.substring(8, 12);
                    String q4 = macaddress04.substring(12, 16);
                    String mc01 = q1 + ":" + q2 + ":" + q3 + ":" + q4;
                    ipv6.setResultado(ipv6.getPrefixo().toUpperCase() + ":" + mc01.toUpperCase());
                    btIpv6.setText(ipv6.getResultado());
                }
            }
        }
    }

    public void limpar(View view){
        spBits.setSelection(0);
        etMacAddress.setText("");
        etPrefixoIpv6.setText("");
        btIpv6.setText("-");
        ipv6.setBits(null);
        ipv6.setMac(null);
        ipv6.setPrefixo(null);
        ipv6.setResultado(null);
        etMacAddress.requestFocus();
    }

    public void copiar(View view){
        if(ipv6.getResultado() != null){
            ClipboardManager Copiar = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            Copiar.setText(ipv6.getResultado());
            Toast.makeText(MainActivity.this, "IPv6 copiado.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "O IPv6 deve ser gerado.", Toast.LENGTH_SHORT).show();
        }
    }

    public String hexadecimalBinary(String hexadecimal1, String hexadecimal2) {
        String resultado = "";
        String var1 = "";
        String var2 = "";
        switch (hexadecimal1.toUpperCase()) {
            case "A":
                var1 = "1010";
                break;
            case "B":
                var1 = "1011";
                break;
            case "C":
                var1 = "1100";
                break;
            case "D":
                var1 = "1101";
                break;
            case "E":
                var1 = "1110";
                break;
            case "F":
                var1 = "1111";
                break;
            case "0":
                var1 = "0000";
                break;
            case "1":
                var1 = "0001";
                break;
            case "2":
                var1 = "0010";
                break;
            case "3":
                var1 = "0011";
                break;
            case "4":
                var1 = "0100";
                break;
            case "5":
                var1 = "0101";
                break;
            case "6":
                var1 = "0110";
                break;
            case "7":
                var1 = "0111";
                break;
            case "8":
                var1 = "1000";
                break;
            default:
                var1 = "1001";
        }
        switch (hexadecimal2.toUpperCase()) {
            case "A":
                var2 = "1010";
                break;
            case "B":
                var2 = "1011";
                break;
            case "C":
                var2 = "1100";
                break;
            case "D":
                var2 = "1101";
                break;
            case "E":
                var2 = "1110";
                break;
            case "F":
                var2 = "1111";
                break;
            case "0":
                var2 = "0000";
                break;
            case "1":
                var2 = "0001";
                break;
            case "2":
                var2 = "0010";
                break;
            case "3":
                var2 = "0011";
                break;
            case "4":
                var2 = "0100";
                break;
            case "5":
                var2 = "0101";
                break;
            case "6":
                var2 = "0110";
                break;
            case "7":
                var2 = "0111";
                break;
            case "8":
                var2 = "1000";
                break;
            default:
                var2 = "1001";
        }
        return resultado = var1 + var2;
    }

    public String binaryHexadecimal(String binary01, String binary02) {
        String resultado = "";
        String var1 = "";
        String var2 = "";
        switch (binary01.toUpperCase()) {
            case "1010":
                var1 = "A";
                break;
            case "1011":
                var1 = "B";
                break;
            case "1100":
                var1 = "C";
                break;
            case "1101":
                var1 = "D";
                break;
            case "1110":
                var1 = "E";
                break;
            case "1111":
                var1 = "F";
                break;
            case "0000":
                var1 = "0";
                break;
            case "0001":
                var1 = "1";
                break;
            case "0010":
                var1 = "2";
                break;
            case "0011":
                var1 = "3";
                break;
            case "0100":
                var1 = "4";
                break;
            case "0101":
                var1 = "5";
                break;
            case "0110":
                var1 = "6";
                break;
            case "0111":
                var1 = "7";
                break;
            case "1000":
                var1 = "8";
                break;
            default:
                var1 = "9";
        }
        switch (binary02.toUpperCase()) {
            case "1010":
                var2 = "A";
                break;
            case "1011":
                var2 = "B";
                break;
            case "1100":
                var2 = "C";
                break;
            case "1101":
                var2 = "D";
                break;
            case "1110":
                var2 = "E";
                break;
            case "1111":
                var2 = "F";
                break;
            case "0000":
                var2 = "0";
                break;
            case "0001":
                var2 = "1";
                break;
            case "0010":
                var2 = "2";
                break;
            case "0011":
                var2 = "3";
                break;
            case "0100":
                var2 = "4";
                break;
            case "0101":
                var2 = "5";
                break;
            case "0110":
                var2 = "6";
                break;
            case "0111":
                var2 = "7";
                break;
            case "1000":
                var2 = "8";
                break;
            default:
                var2 = "9";
        }
        return resultado = var1 + var2;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.mnEndereco){
            Intent intent = new Intent(MainActivity.this, br.com.fedablio.ima.EnderecoActivity.class);
            startActivity(intent);
            finish();
        }
        if(id == R.id.mnSobre){
            Intent intent = new Intent(MainActivity.this, SobreActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}