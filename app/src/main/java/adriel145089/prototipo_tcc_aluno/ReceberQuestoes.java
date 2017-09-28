package adriel145089.prototipo_tcc_aluno;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


/**
 * Created by Adriel on 17/07/2017.
 */

public class ReceberQuestoes extends AsyncTask <Void, Void, String> {

    private static final String HOST = "http://es.ft.unicamp.br/ulisses/appaluno/get_questoes.php";
    private Context context;
    private  ListView listView;
    private  String[] fields;
    private  String[] values;

    public ReceberQuestoes(Context context, ListView listView, String[] fields, String[] values){
        this.context  = context;
        this.listView = listView;
        this.fields   = fields;
        this.values   = values;
    }

    @Override
    protected String doInBackground(Void... objects) {
        HttpURLConnection httpURLConnection = null;
        try {
            /*
               Preparando os dados para envio via post
             */
            String data =
                    //URLEncoder.encode("appaluno","UTF-8")+"="+
                            //URLEncoder.encode("appaluno","UTF-8")+"&"+
                            //URLEncoder.encode("table","UTF-8")+"="+
                            //URLEncoder.encode("questao","UTF-8")+"&"+
                            URLEncoder.encode("IdAvaliacao","UTF-8")+"="+
                            URLEncoder.encode("555","UTF-8")
            ;


            for(int i = 0; i < fields.length; i++) {
                data +=  "&" +  URLEncoder.encode(fields[i] , "UTF-8") + "=" +
                        URLEncoder.encode(values[i], "UTF-8");
            }


            /*
               Abrindo uma conexÃ£o com o servidor
             */
            URL url = new URL(HOST);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            /*
               Enviando os dados via post
             */
            OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());
            wr.write( data );
            wr.flush();

            /*
                Lendo a resposta do servidor
             */
            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(httpURLConnection.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            return sb.toString();

        }

        catch (IOException exception){
            exception.printStackTrace();
            return "Exception: " + exception.getMessage();
        }

        finally {
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
            }
        }
    }


    @Override
    protected  void onPostExecute(String result){

           //Convertendo JSONArray para ArrayList


        ArrayList<String> questao = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                questao.add(jsonObject.getString("Questao"));
            }
        } catch (JSONException exception){
            exception.printStackTrace();
        }


          //Adicionando dados na ListView


       ArrayAdapter<String> adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_checked, questao);
       listView.setAdapter(adapter);



    }
}
