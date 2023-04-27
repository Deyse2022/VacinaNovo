package deyse.souza.appvacina.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import deyse.souza.appvacina.R;
import deyse.souza.appvacina.adapter.AdapterCampanhas;
import deyse.souza.appvacina.helper.UsuarioFirebase;
import deyse.souza.appvacina.model.Campanha;

public class NovaCampV extends AppCompatActivity {

    private EditText editNomeCampanha, editDtinicioC, editDtFim, editInfoad;

    private String idUsuarioLogado, idCampanha;

    private List<Campanha> campanhas = new ArrayList<>();

    private SwitchCompat switchStatus;

    private AdapterCampanhas adapterCampanhas;

    private DatabaseReference firebaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_camp_v);

        inicializarComponntes();
        idUsuarioLogado = UsuarioFirebase.getIdentificadorUsuario();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Vacina+");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void inicializarComponntes(){
        editNomeCampanha = findViewById(R.id.editNomeCampanha);
        editDtinicioC= findViewById(R.id.editDtinicioC);
        editDtFim = findViewById(R.id.editDtFim);
        editInfoad = findViewById(R.id.editInfoad);
        switchStatus = findViewById(R.id.switchStatus);

    }

    public void validarDadosNvCampanha(View view){

        String nome = editNomeCampanha.getText().toString();
        String dtinicio = editDtinicioC.getText().toString();
        String dtfim = editDtFim.getText().toString();
        String infoad = editInfoad.getText().toString();


        if (!nome.isEmpty()){
            if (!dtinicio.isEmpty()){
                if(!dtfim.isEmpty()){


                Campanha campanha = new Campanha();
                campanha.setIdUsuario( idUsuarioLogado);
                campanha.setNome(nome);
                campanha.setDtinicio(dtinicio);
                campanha.setDtfim(dtfim);
                campanha.setDadosad(infoad);
                campanha.setStatus(verificaStatusCampanha());
                campanha.salvar();
                finish();

            }else{
                exibirMensagem("Digite uma data fim");
            }

            }else{
                exibirMensagem("Digite uma data de inicio");
            }

        }else{
            exibirMensagem("Digite um nome para a campanha");
        }


    }

    private void exibirMensagem(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    /*private void recuperarCampanhas() {

        DatabaseReference campanhasRef = firebaseRef
                .child("campanhas")
                .child(idUsuarioLogado);

        campanhasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                campanhas.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    campanhas.add(ds.getValue(Campanha.class));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/

    public String verificaStatusCampanha(){
        return switchStatus.isChecked() ? "Ativa" : "Inativa" ;
    }

}