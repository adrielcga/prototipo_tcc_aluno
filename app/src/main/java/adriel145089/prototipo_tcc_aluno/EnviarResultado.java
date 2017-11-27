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
 * Created by Adriel on 11/08/2017.
 */

public class EnviarResultado extends AsyncTask<Void, Void, String>{



    private static final String HOST = Constantes.URL_BASE + "/resultado_adr.php";//colocar url do servidor ----------------------falta url
    TextView textView;
    private String[] fields;
    private String[] values;

    public EnviarResultado(TextView textView, String[] fields, String[] values){
        this.textView = textView;
        this.fields   = fields;
        this.values   = values;
    }

    @Override
    protected String doInBackground(Void... objects) {
        HttpURLConnection httpURLConnection = null;


        try {

               //Preparando os dados para envio via post
            String data =
                    //URLEncoder.encode("database","UTF-8")+"="+
                            //URLEncoder.encode("appaluno","UTF-8")+"&"+

                            //URLEncoder.encode("table","UTF-8")+"="+
                            //URLEncoder.encode("realizaprova","UTF-8")+"="+
                            URLEncoder.encode("QRCodeProva","UTF-8")+"="+
                            URLEncoder.encode(Tela_Inicial.qrcode,"UTF-8")+"&"+
                            URLEncoder.encode("RAAluno","UTF-8")+"="+
                            URLEncoder.encode(Tela_Login.RA,"UTF-8");

            for(int i = 0; i < fields.length; i++) {
                data +=  "&" +  URLEncoder.encode(fields[i] , "UTF-8") + "=" +
                        URLEncoder.encode(values[i], "UTF-8");
            }



               //Abrindo uma conexÃ£o com o servidor
            URL url = new URL(HOST);
            httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);


               //Enviando os dados via post
            OutputStreamWriter wr = new OutputStreamWriter(
                    httpURLConnection.getOutputStream());
            wr.write( data );
            wr.flush();


                //Lendo a resposta do servidor
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
