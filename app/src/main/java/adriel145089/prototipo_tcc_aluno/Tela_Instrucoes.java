package adriel145089.prototipo_tcc_aluno;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Tela_Instrucoes extends AppCompatActivity {

    String url = "";
    String parametros = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_instrucoes);

        TextView textView = (TextView)findViewById(R.id.instrucoesDoProf);

        textView.setText("Aguardando Servidor...");


    ReceberInstrucoes receberInstrucoes = new ReceberInstrucoes(textView);

        receberInstrucoes.execute();

    }

    //abre a tela da prova
    public void abrirProva (View view){

        //verificação da rede
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){


            Toast.makeText(Tela_Instrucoes.this, "Boa Prova!", Toast.LENGTH_SHORT).show();

            Intent abrirProva = new Intent(Tela_Instrucoes.this, Tela_Prova.class);

            startActivity(abrirProva);

            //String Hash = Tela_Inicial.qrcode;

            //parametros = "QRCodeProva=" + Hash;

            //url = "http://es.ft.unicamp.br/ulisses/appaluno/libera_prova_adr.php"; //url do servidor

            //new LiberarProva().execute(url);
        }
    }

    private class LiberarProva extends AsyncTask<String, Void, String> {

        protected String doInBackground (String... urls){


            return Conexao.postDados(urls[0], parametros);

        }

        protected void onPostExecute(String result){

            //enviarUsuario.setText(result);
            if (result.contains("1")){

                Toast.makeText(Tela_Instrucoes.this, "Boa Prova!", Toast.LENGTH_SHORT).show();

                Intent abrirProva = new Intent(Tela_Instrucoes.this, Tela_Prova.class);
                startActivity(abrirProva);
            } else {

                Toast.makeText(getApplicationContext(), "Aguarde o inicio da prova", Toast.LENGTH_LONG).show();
            }

        }
    }


    @Override
    protected void onPause(){

        super.onPause();

        finish();
    }
}
