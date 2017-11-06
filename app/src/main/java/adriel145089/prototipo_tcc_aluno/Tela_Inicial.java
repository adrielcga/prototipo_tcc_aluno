package adriel145089.prototipo_tcc_aluno;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import me.drakeet.materialdialog.MaterialDialog;

public class Tela_Inicial extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView CameraScanner;
    String url = "";
    String parametros = "";
    public static String qrcode;
    TextView textviewQrcode;

    public static final String TAG = "LOG";
    public static final int REQUEST_PERMISSIONS_CODE = 128;
    private MaterialDialog mMaterialDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela__inicial);

        textviewQrcode = (TextView) findViewById(R.id.textViewQrCode);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.i(TAG, "test");
        switch( requestCode ){
            case REQUEST_PERMISSIONS_CODE:
                for( int i = 0; i < permissions.length; i++ ){

                    if( permissions[i].equalsIgnoreCase( Manifest.permission.CAMERA )
                            && grantResults[i] == PackageManager.PERMISSION_GRANTED ){

                        CameraScanner = new ZXingScannerView(this);
                        setContentView(CameraScanner);
                        CameraScanner.setResultHandler(this);
                        CameraScanner.startCamera();
                    }
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void Scan_QRcode(View view){

        if( ContextCompat.checkSelfPermission( this, Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED ){

            if( ActivityCompat.shouldShowRequestPermissionRationale( this, Manifest.permission.CAMERA ) ){
                callDialog_Permissao( "É preciso dar permissão para acessar a CAMERA.", new String[]{Manifest.permission.CAMERA} );
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSIONS_CODE );
            }
        }
        else{

                CameraScanner = new ZXingScannerView(this);
                setContentView(CameraScanner);
                CameraScanner.setResultHandler(this);
                CameraScanner.startCamera();
            }

    }

    //caixa de dialogo perguntando se o usuario autoriza dar permissao para acessar a camera
    private void callDialog_Permissao( String message, final String[] permissions ){
        mMaterialDialog = new MaterialDialog(this)
                .setTitle("PERMISSÃO")
                .setMessage( message )
                .setPositiveButton("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ActivityCompat.requestPermissions(Tela_Inicial.this, permissions, REQUEST_PERMISSIONS_CODE);
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton("Cancelar", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });
        mMaterialDialog.show();
    }


    //
    @Override
    public void onBackPressed() {
        //caixa de dialogo perguntando se o usuario realmente quer sair

        mMaterialDialog = new MaterialDialog(this)
                .setTitle("DESEJA VOLTAR?")
                .setMessage( "Se retornar sera deslogado" )
                .setPositiveButton("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        finish();
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton("Cancelar", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });
        mMaterialDialog.show();
    }

    @Override
    protected void onPause(){

        //CameraScanner.stopCamera();
        super.onPause();
        finish();
    }




    @Override
    public void handleResult(Result result) {

        Log.w("handleResult", result.getText());

        textviewQrcode.setText(result.getText());

        CheckQrCode(textviewQrcode);

        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Status:");
        builder.setMessage(result.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();*/



    }

    public void CheckQrCode (View view){


        qrcode = textviewQrcode.getText().toString();

        //verificação da rede
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){

                url = "http://es.ft.unicamp.br/ulisses/appaluno/qrcode_adr.php"; //url do servidor

                parametros = "QRCodeProva=" +qrcode;

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

            if (result.contains("QrCode_aceito")){

                Intent abrirInstrucoes = new Intent(Tela_Inicial.this, Tela_Instrucoes.class);
                startActivity(abrirInstrucoes);
            } else {

                Toast.makeText(getApplicationContext(), "QR Code não aceito", Toast.LENGTH_LONG).show();
            }

        }
    }

}
