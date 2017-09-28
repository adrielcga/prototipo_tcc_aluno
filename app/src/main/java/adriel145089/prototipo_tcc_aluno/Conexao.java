package adriel145089.prototipo_tcc_aluno;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Adriel on 16/07/2017.
 */

public class Conexao {

    public static String postDados(String urlUsuario, String parametrosUsuario) {

        URL url;
        HttpURLConnection connection = null;

        try {
            url = new URL(urlUsuario);
            //abrir conexao
            connection = (HttpURLConnection) url.openConnection();
            //passar parametros pela conexao
            connection.setRequestMethod("POST");
            //como os dados ficarao decodificados, vou usar o form
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //tamanho de bits que sarao enviados
            connection.setRequestProperty("Content-Lenght", "" + Integer.toString(parametrosUsuario.getBytes().length));

            connection.setRequestProperty("Content-Language", "pt-BR");

            //limpar cache
            connection.setUseCaches(false);
            connection.getDoInput();
            connection.setDoOutput(true);

            //armazenar os dados que estao saindo com a conexao
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            outputStreamWriter.write(parametrosUsuario);
            outputStreamWriter.flush();

            InputStream inputStream = connection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            //pra armazenar por linhas
            String linha;
            StringBuffer resposta = new StringBuffer();

            //montar a string buffer
            while ((linha = bufferedReader.readLine()) != null){

                resposta.append(linha);
                resposta.append('\r');

            }

            bufferedReader.close();
            return resposta.toString();


        } catch (Exception erro) {

            return null;

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }


    }
}
