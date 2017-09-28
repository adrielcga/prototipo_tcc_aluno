package adriel145089.prototipo_tcc_aluno;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


public class Tela_Prova extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String OPCAO_ATUAL = "opcaoAtual";
    Toolbar mToolbar;
    Spinner mSpinner;
    private ContaRegressivaProva contadorRegre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela__prova);

        //criando uma toolbar para as questoes
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.questoes)
        );

        mSpinner = new Spinner(this);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mToolbar.addView(mSpinner);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        //---------------------------------
        //receber questoes


        String[] fields = {"Questao",   "Alternativa1", "Alternativa2", "Alternativa3", "Alternativa4", "Alternativa5", "Alternativa6"};
        String[] values = {};


        fields = new String[0];
        values = new String[0];
        ListView listView = (ListView) findViewById(R.id.receberquestao);
        ReceberQuestoes receberQuestoes = new ReceberQuestoes(this, listView, fields, values);
        receberQuestoes.execute();

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
    private void exibirItem(int position) {


        //pilha de fragmentos
        FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.Fragment f = fm.findFragmentByTag("tag");
        FragmentTransaction ft = fm.beginTransaction();
        if (f != null) {
            ft.replace(R.id.frame, f, "tag");
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        }
        ft.commit();
    }

    @Override
    public void onResume(){

        super.onResume();

        TextView contador = (TextView) findViewById(R.id.contadorRegressivo);
        contadorRegre = new ContaRegressivaProva(this, contador, 2*60*60*1000 /*tempoDaContagem*/, 1000); //o 1000 é pra contar de 1 em 1 segundo, é 1000 pq é em milisegundos
        contadorRegre.start();
    }

    //abrir a tela de finalizar, onde tem o resumo das questoes

    public void abrirFinalizar(View view){


        Intent abrirtela = new Intent(Tela_Prova.this, Tela_Resultado.class);
        abrirtela.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(abrirtela);

    }

    public void ReceberQuestoes(View view){


    }



}

