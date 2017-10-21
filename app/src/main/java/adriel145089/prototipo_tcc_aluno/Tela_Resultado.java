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

                String rspt = bundle_resposta.getString("enviarUsuario");

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


        //fechar o app
        /*Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/
        finish();



    }

    @Override
    protected void onPause(){

        super.onPause();
        finish();
    }

}
