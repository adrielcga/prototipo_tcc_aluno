package adriel145089.prototipo_tcc_aluno;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Tela_Instrucoes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_instrucoes);

        TextView textView = (TextView)findViewById(R.id.instrucoesDoProf);

        textView.setText("sdf");


    ReceberInstrucoes receberInstrucoes = new ReceberInstrucoes(textView);
        receberInstrucoes.execute();

    }

    //abre a tela da prova
    public void abrirProva (View view){

        Toast.makeText(Tela_Instrucoes.this, "Boa Prova!", Toast.LENGTH_SHORT).show();

        Intent abrirProva = new Intent(this, Tela_Prova.class);
        startActivity(abrirProva);
    }
}
