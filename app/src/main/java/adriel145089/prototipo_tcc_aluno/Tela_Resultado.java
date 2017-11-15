package adriel145089.prototipo_tcc_aluno;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import me.drakeet.materialdialog.MaterialDialog;

public class Tela_Resultado extends AppCompatActivity {

    private TextView resultados;
    private MaterialDialog mMaterialDialog;
    private String resultadosStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela__resultado);

        Intent abrirFinalizar = getIntent();

            Bundle bundle_resposta = abrirFinalizar.getExtras();

            if (bundle_resposta != null) {

                String rspt = bundle_resposta.getString("Resposta");
                boolean[][] respostas = (boolean[][]) bundle_resposta.getSerializable("Resposta_Array");

                TextView resp = (TextView) findViewById(R.id.resultados);

                StringBuilder stringBuilder = new StringBuilder();
                for (int i=0; i < respostas.length; i++) {
                    ArrayList<Character> selecionadas = new ArrayList<>(respostas.length);
                    for (int j=0; j < respostas[i].length; j++) {
                        if (respostas[i][j]) {
                            selecionadas.add((char) ('A' + j));
                        }
                    }
                    stringBuilder.append(i+1);
                    stringBuilder.append(") ");
                    stringBuilder.append(TextUtils.join(", ", selecionadas));
                    stringBuilder.append("\n\n");
                }

                resp.setText(stringBuilder.toString());
                resultadosStr = rspt;

            }

        resultados = (TextView) findViewById(R.id.resultados);
    }

    public void enviarResultado (View view){

        String result = resultadosStr;

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
