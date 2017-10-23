package adriel145089.prototipo_tcc_aluno;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import me.drakeet.materialdialog.MaterialDialog;

public class Tela_Resultado extends AppCompatActivity {

    private TextView resultados;
    private MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela__resultado);

        Intent abrirFinalizar = getIntent();

            Bundle bundle_resposta = abrirFinalizar.getExtras();

            if (bundle_resposta != null) {

                String rspt = bundle_resposta.getString("Resposta");

                TextView resp = (TextView) findViewById(R.id.resultados);

                resp.setText(rspt);

            }

        resultados = (TextView) findViewById(R.id.resultados);
    }


    public void enviarResultado (View view){

        String result = resultados.getText().toString();

        TextView textView = (TextView)findViewById(R.id.textviewrcbservidor);


        String[] fields = {"Respostas"};
        String[] values = {result};

        EnviarResultado enviarResult = new EnviarResultado(textView, fields, values);
        enviarResult.execute();

        finish();

    }

    @Override
    protected void onPause(){

        super.onPause();
        finish();
    }

}
