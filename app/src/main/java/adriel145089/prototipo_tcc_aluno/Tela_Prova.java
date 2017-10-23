package adriel145089.prototipo_tcc_aluno;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.drakeet.materialdialog.MaterialDialog;


public class Tela_Prova extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String OPCAO_ATUAL = "opcaoAtual";
    Toolbar mToolbar;
    Spinner mSpinner;
    private ContaRegressivaProva contadorRegre;
    private JSONArray   questoes;
    private boolean[][] respostas;

    private int       questao;
    private ListView listView;
    private TextView enunciado;
    private ArrayAdapter  arrayAdapter;
    ArrayList<String> alternativas;
    String str = "";

    private MaterialDialog mMaterialDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela__prova);

        questao = -1;

        alternativas = new ArrayList<>();
        listView = (ListView)findViewById(R.id.listView);
        enunciado = (TextView)findViewById(R.id.enunciado);
        arrayAdapter=new ArrayAdapter<String>(this,
                R.layout.rowlayout, R.id.checkedTextView,
                alternativas);

        listView.setAdapter(arrayAdapter);



        //click resposta
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listview,
                                    View view,
                                    int position,
                                    long id) {

                if(Tela_Prova.this.respostas[questao][position]){
                    Log.v("asdfasdf", questao + ","+ position);
                    Tela_Prova.this.respostas[questao][position] = false;
                } else {
                    Log.v("asdfasdf", questao + ","+ position);
                    Tela_Prova.this.respostas[questao][position] = true;

                }

            }
        };
        listView.setOnItemClickListener(itemClickListener);

        mSpinner = new Spinner(this);
        mSpinner.setOnItemSelectedListener(this);



        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mToolbar.addView(mSpinner);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);




        //---------------------------------
        //receber questoes
        String[] fields = {};
        String[] values = {};
        fields = new String[0];
        values = new String[0];
        ReceberQuestoes receberQuestoes = new ReceberQuestoes(this, fields, values);
        receberQuestoes.execute();
    }

    public void ReceberQuestoes(JSONArray jsonArray){
        this.questoes = jsonArray;
        this.respostas = new boolean[this.questoes.length()][9];
        ArrayList<String> titulos = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                titulos.add(jsonObject.getString("TituloQuestao"));
            }
        }  catch (JSONException exception){
            exception.printStackTrace();
        }


        //criando na toolbar lsita de questoes questoes
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                titulos
        );
        mSpinner.setAdapter(adapter);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(OPCAO_ATUAL)) {
            mSpinner.setSelection(savedInstanceState.getInt(OPCAO_ATUAL));
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(OPCAO_ATUAL, mSpinner.getSelectedItemPosition());
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view,
                               int position, long l) {
        exibirItem(position);
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private void exibirArray(){
        for (int i = 0; i < respostas.length; i++){
            //String str = "";
            for (int j = 0; j < 9; j++){
                str += respostas[i][j]+",";
            }
            Log.v("Resposta", str);
        }
    }

    private void exibirItem(int position) {
        exibirArray();
        JSONObject jsonObject;
        questao = position;
        try {
            jsonObject = questoes.getJSONObject(position);
            enunciado.setText(jsonObject.getString("EnunQuestao"));
            alternativas.clear();


            String[] enunFiels = {
                    "EnunAltA",
                    "EnunAltB",
                    "EnunAltC",
                    "EnunAltD",
                    "EnunAltE",
                    "EnunAltF",
                    "EnunAltG",
                    "EnunAltH",
                    "EnunAltI"
            };

            for (int i = 0; i < enunFiels.length; i++){
                String x = jsonObject.getString(enunFiels[i]);
                if (!x.equals("null")){
                    alternativas.add(x);
                }
            }
            arrayAdapter.notifyDataSetChanged();


        }  catch (JSONException exception){
            exception.printStackTrace();
        }

    }

    @Override
    public void onResume(){

        super.onResume();

        TextView contador = (TextView) findViewById(R.id.contadorRegressivo);
        contadorRegre = new ContaRegressivaProva(this, contador, 60*60*1000 /*tempoDaContagem*/, 1000); //o 1000 é pra contar de 1 em 1 segundo, é 1000 pq é em milisegundos
        contadorRegre.start();
    }

    //abrir a tela de finalizar, onde tem o resumo das questoes
    public void abrirFinalizar(View view){

        Intent abrirtela = new Intent(Tela_Prova.this, Tela_Resultado.class);
        abrirtela.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Bundle bundle_resposta = new Bundle();
        bundle_resposta.putString("Resposta", str);
        abrirtela.putExtras(bundle_resposta);
        startActivity(abrirtela);

    }

    @Override
    public void onBackPressed() {
        //caixa de dialogo perguntando se o usuario realmente quer sair

        mMaterialDialog = new MaterialDialog(this)
                .setTitle("DESEJA REALMENTE SAIR?")
                .setMessage( "Se sair, retornará a tela de login" )
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

        super.onPause();
        finish();
    }


}

