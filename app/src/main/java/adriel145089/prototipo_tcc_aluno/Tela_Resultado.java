package adriel145089.prototipo_tcc_aluno;

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

        resultados = (TextView) findViewById(R.id.resultados);
    }

    /*public void voltarProva (View view){

        Intent voltaProva = new Intent(this, Tela_Prova.class);
        voltaProva.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(voltaProva);


        /*
        Intent voltaProva = new Intent();
        voltaProva.putExtra("tag","atualizar");
        setResult(Activity.RESULT_OK,voltaProva);
        finish();

    }*/


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
