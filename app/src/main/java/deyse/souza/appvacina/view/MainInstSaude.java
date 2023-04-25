package deyse.souza.appvacina.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import deyse.souza.appvacina.R;
import deyse.souza.appvacina.config.ConfiguracaoFirebase;

public class MainInstSaude extends AppCompatActivity {

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_inst_saude);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Vacina+");
        setSupportActionBar(toolbar);
    }

    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_instsaude,menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.menuINovaCampV:
                abrirNovaCampV();
                break;
            case R.id.menuMeuPerfilIS:
                abrirMeuPerfilIS();
                break;
            case R.id.menuNotificaoes:
                abrirNotificacoes();
                break;
            case R.id.menuSair:
                deslogarUsuario();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deslogarUsuario(){
        try{
            autenticacao.signOut();
            finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void abrirNovaCampV(){
        startActivity(new Intent(MainInstSaude.this, NovaCampV.class));
    }

    private void abrirMeuPerfilIS(){
        startActivity(new Intent(MainInstSaude.this, MeuPerfilIS.class));
    }

    private void abrirNotificacoes(){
        startActivity(new Intent(MainInstSaude.this, Notificacoes.class));
    }



}