package adriel145089.prototipo_tcc_aluno;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Tela_Cadastro extends AppCompatActivity {

    private EditText ra, nome, email, senhacad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela__cadastro);

        ra = (EditText) findViewById(R.id.ra_cadastro);
        nome = (EditText) findViewById(R.id.nome_cadastro);
        email = (EditText) findViewById(R.id.email_cadastro);
        senhacad = (EditText) findViewById(R.id.senha_cadastro);

        Intent abrirTelaCadastro = getIntent();

        if (abrirTelaCadastro != null) {

            Bundle paramentros = abrirTelaCadastro.getExtras();

            if (paramentros != null) {

                String ra = paramentros.getString("enviarUsuario");
                String senha = paramentros.getString("enviarSenha");

                EditText raed = (EditText) findViewById(R.id.ra_cadastro);
                EditText senhaed = (EditText) findViewById(R.id.senha_cadastro);

                raed.setText(ra);
                senhaed.setText(senha);

            }


        }


    }

        public void Cadastrar (View view){


            String RAAluno = ra.getText().toString();
            String Nome = nome.getText().toString();
            String Email = email.getText().toString();
            String senha = senhacad.getText().toString();

            TextView textView = (TextView)findViewById(R.id.textView);

            textView.setText("sdf");

            String[] fields = {"RAAluno", "NomeAluno", "EmailAluno", "SenhaAluno"};
            String[] values = {RAAluno, Nome, Email, senha};

            CadastrarAluno cadastrarAluno = new CadastrarAluno(textView, fields, values);
            cadastrarAluno.execute();
            Log.v("Cadastro", "iniciando");


        Toast.makeText(getApplicationContext(), "Login efetuado com sucesso", Toast.LENGTH_LONG).show();

        Intent cadastro = new Intent(this, Tela_Login.class);
        startActivity(cadastro);

        finish();

    }

}
