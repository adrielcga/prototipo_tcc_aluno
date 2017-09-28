package adriel145089.prototipo_tcc_aluno;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

/**
 * Created by Adriel on 02/09/2017.
 */

public class ContaRegressivaProva extends CountDownTimer {

    private TextView contador;
    private Context context;
    private long timeInFuture;
    private long intervalo;

    //construtor
    //timeInFuture é o periodo de contagem em referencia ao tempo atual
    //intervalo de tempo
    public ContaRegressivaProva(Context context, TextView contador, long timeInFuture, long intervalo){

        super(timeInFuture, intervalo);
        this.contador = contador;
        this.context = context;
    }

    //tempo de intervalo da contagem regressiva
    //millisUntilFinished quanto falta pra acabar a contagem regressiva
    @Override
    public void onTick(long millisUntilFinished) {

        timeInFuture = millisUntilFinished;
        contador.setText(getCorrectTimer(true, millisUntilFinished) + ":" + getCorrectTimer(false, millisUntilFinished));
    }

    //chama quando acaba a contagem regressiva
    @Override
    public void onFinish() {

        timeInFuture -=1000;
        contador.setText(getCorrectTimer(true, timeInFuture) + ":" + getCorrectTimer(false, timeInFuture));

        Toast.makeText(context, "ACABOU O TEMPO!", Toast.LENGTH_SHORT).show();

        Intent abrirtela = new Intent(context, Tela_Resultado.class);
        context.startActivity(abrirtela);
    }

    private String getCorrectTimer(boolean isMinute, long millisUntilFinished){

        String aux;
        int contantecalendar = isMinute ? Calendar.MINUTE : Calendar.SECOND;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisUntilFinished);

        aux = calendar.get(contantecalendar) < 10 ? "0" + calendar.get(contantecalendar) : "" + calendar.get(contantecalendar);
        return (aux);
    }
}
