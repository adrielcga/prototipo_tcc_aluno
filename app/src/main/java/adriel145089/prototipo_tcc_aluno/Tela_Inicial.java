package adriel145089.prototipo_tcc_aluno;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Tela_Inicial extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView CameraScanner;
    String url = "";
    String parametros = "";
    String qrcode;
    TextView textviewQrcode;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela__inicial);

        textviewQrcode = (TextView) findViewById(R.id.textViewQrCode);

    }


    public void Scan_QRcode(View view){

                CameraScanner = new ZXingScannerView(this);
                setContentView(CameraScanner);
                CameraScanner.setResultHandler(this);
                CameraScanner.startCamera();

    }


    /*@Override
    protected void onPause(){

        super.onPause();
        CameraScanner.stopCamera();

    }*/

    @Override
    public void handleResult(Result result) {

        Log.w("handleResult", result.getText());

        textviewQrcode.setText(result.getText().toString());

        CheckQrCode(textviewQrcode);

        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Status:");
        builder.setMessage(result.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();*/



    }

    public void CheckQrCode (View view){


        String qrcode = textviewQrcode.getText().toString();
        //verificação da rede
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){

                url = "http://es.ft.unicamp.br/ulisses/appaluno/qrcode_adr.php"; //url do servidor

                parametros = "QrCode=" +qrcode;

                new SolicitaDados().execute(url);

        } else {

            Toast.makeText(getApplicationContext(), "Sem conexão com o servidor", Toast.LENGTH_LONG).show();
        }

    }

    private class SolicitaDados extends AsyncTask<String, Void, String> {

        protected String doInBackground (String... urls){


            return Conexao.postDados(urls[0], parametros);

        }

        protected void onPostExecute(String result){

            //enviarUsuario.setText(result);
            if (result.contains("QrCode_aceito")){

                Intent abrirInstrucoes = new Intent(Tela_Inicial.this, Tela_Instrucoes.class);

                startActivity(abrirInstrucoes);
            } else {

                Toast.makeText(getApplicationContext(), "QR Code não aceito", Toast.LENGTH_LONG).show();
            }

        }
    }
}
