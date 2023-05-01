package deyse.souza.appvacina.view;

import static deyse.souza.appvacina.R.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

import deyse.souza.appvacina.config.ConfiguracaoFirebase;


public class Splash extends AppCompatActivity {

    int tempoDeEspera = 1000 * 3;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_splash);

        trocarTela();

        Toolbar toolbar = findViewById(id.toolbar);
        toolbar.setTitle("Vacina+");
        setSupportActionBar(toolbar);

    }

    private void trocarTela() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signOut();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (autenticacao.getCurrentUser() == null) {

                    Intent trocarDeTela = new Intent(Splash.this, Login.class);
                    startActivity(trocarDeTela);
                    finish();
                } else if (autenticacao.getCurrentUser() != null) {
                    Intent trocarDeTela = new Intent(Splash.this, MainPessoa.class);
                    startActivity(trocarDeTela);

                }

            }
        }, tempoDeEspera);
    }
}
