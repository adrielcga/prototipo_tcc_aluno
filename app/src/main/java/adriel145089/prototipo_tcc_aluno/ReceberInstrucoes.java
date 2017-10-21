package adriel145089.prototipo_tcc_aluno;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Adriel on 18/08/2017.
 */

public class ReceberInstrucoes extends AsyncTask<Void, Void, String>{

private static final String HOST = "http://es.ft.unicamp.br/ulisses/appaluno/receber_descricao";
TextView textView;



public ReceberInstrucoes( TextView textView){

        this.textView = textView;

        }

@Override
protected String doInBackground(Void... objects) {
        HttpURLConnection httpURLConnection = null;
        try {
            /*
               Preparando os dados para envio via post
             */
        String data =
        URLEncoder.encode("table","UTF-8")+"="+
        URLEncoder.encode("Avaliacao","UTF-8") + "&"+
        URLEncoder.encode("hash","UTF-8")+"="+
        URLEncoder.encode(Tela_Inicial.qrcode,"UTF-8");




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

        textView.setText(result);
        }


}
