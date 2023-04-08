import android.content.Intent;

import java.util.logging.Handler;

public class SplashActivity {

    int tempoDeEspera = 1000 * 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        trocarTela();

    }

    private void trocarTela() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent trocarDeTela = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(trocarDeTela);
                finish();


            }
        }, tempoDeEspera);



    }


}
