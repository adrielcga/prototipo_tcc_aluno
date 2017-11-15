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
import android.widget.EditText;
import android.widget.Toast;

public class Tela_Login extends AppCompatActivity {

    public static EditText enviarUsuario;
    EditText enviarSenha;
    String url = "";
    String parametros = "";
    public static String RA;
    //public static String Senha = enviarSenha.getText().toString();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela__login);

        enviarUsuario = (EditText) findViewById(R.id.usuario);
        enviarSenha = (EditText) findViewById(R.id.senha);
    }


    public void AbrirTelaCadastro (View view){

        Intent abrirTelaCadastro = new Intent(this, Tela_Cadastro.class);

        Bundle parametros = new Bundle();
        parametros.putString("enviarUsuario", enviarUsuario.getText().toString());
        parametros.putString("enviarSenha", enviarSenha.getText().toString());

        abrirTelaCadastro.putExtras(parametros);

        startActivity(abrirTelaCadastro);
    }

    public void Login (View view){

        //verificação da rede
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){

            RA = enviarUsuario.getText().toString();
            String senha = enviarSenha.getText().toString();

            if (RA.isEmpty() || senha.isEmpty()){

                Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_LONG).show();
            } else {

                url = Constantes.URL_BASE + "/login_adr.php"; //url do servidor

                parametros = "RAAluno=" +RA + "&SenhaAluno=" +senha;
                Log.v("LOGIN", "iniciando");

                new SolicitaDados().execute(url);
            }

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
            Log.v("LOGIN", result);
            if (result.contains("login_ok")){

                Intent abrirInicio = new Intent(Tela_Login.this, Tela_Inicial.class);
                startActivity(abrirInicio);
            } else {

                Toast.makeText(getApplicationContext(), "Usuario/senha errado", Toast.LENGTH_LONG).show();
            }

        }
    }


    }
