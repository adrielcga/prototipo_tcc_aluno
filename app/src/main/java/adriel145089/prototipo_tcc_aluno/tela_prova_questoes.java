package adriel145089.prototipo_tcc_aluno;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class tela_prova_questoes extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup conteudo, Bundle saveInstanceState){

        View view = inflater.inflate(R.layout.fragment_tela_prova_questoes, null);

        ReceberQuestoes(view);

        return(view);


    }


    public void ReceberQuestoes(View view){






        String[] fields = {"Questao",   "Alternativa1", "Alternativa2", "Alternativa3", "Alternativa4", "Alternativa5", "Alternativa6"};
        String[] values = {"", "", "", "", "", "", ""};


        fields = new String[0];
        values = new String[0];
        ListView listView = (ListView) view.findViewById(R.id.Listaquestoes);
        ReceberQuestoes receberQuestoes = new ReceberQuestoes(getActivity(), listView, fields,values);
        receberQuestoes.execute();




    }



}
