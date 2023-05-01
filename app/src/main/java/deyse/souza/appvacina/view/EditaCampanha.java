package deyse.souza.appvacina.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import deyse.souza.appvacina.R;
import deyse.souza.appvacina.config.ConfiguracaoFirebase;
import deyse.souza.appvacina.helper.UsuarioFirebase;
import deyse.souza.appvacina.model.Campanha;

public class EditaCampanha extends AppCompatActivity {

    private DatabaseReference firebaseRef;
    private String idUsuarioLogado;

    private EditText editNomeCampanha, editDtinicioC, editDtFim, editInfoad;

    private SwitchCompat switchStatus;

    private Campanha campanhaSelecionada;

    private String idCampanhaSel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_campanha);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Vacina+");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        idUsuarioLogado = UsuarioFirebase.getIdentificadorUsuario();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            campanhaSelecionada = (Campanha) bundle.getSerializable("campanha");
            idCampanhaSel = campanhaSelecionada.getIdCampanha();

        }

        inicializarComponentes();

        recuperarCampanhas();

        /*recuperarCampanhasUsuario();*/


    }

    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_campanha,menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void inicializarComponentes(){
        editNomeCampanha = findViewById(R.id.editNomeCampanha);
        editDtinicioC= findViewById(R.id.editDtinicioC);
        editDtFim = findViewById(R.id.editDtFim);
        editInfoad = findViewById(R.id.editInfoad);
        switchStatus = findViewById(R.id.switchStatus);

    }


    private void recuperarCampanhas() {

        DatabaseReference campanhasRef = firebaseRef
                .child("campanhas")
                .child(idUsuarioLogado)
                .child(idCampanhaSel);


        campanhasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if(snapshot.getValue() !=null){
                    Campanha campanha = snapshot.getValue(Campanha.class);
                    editNomeCampanha.setText(campanha.getNome());
                    editDtinicioC.setText(campanha.getDtinicio());
                    editDtFim.setText(campanha.getDtfim());
                    editInfoad.setText(campanha.getDadosad());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void editCampanhaSalvar(View view) {

        String nome = editNomeCampanha.getText().toString();
        String dtinicio = editDtinicioC.getText().toString();
        String dtfim = editDtFim.getText().toString();
        String infoad = editInfoad.getText().toString();


        Campanha campanha = new Campanha();
        campanha.setIdUsuario( idUsuarioLogado);
        campanha.setIdCampanha(idCampanhaSel);
        campanha.setNome(nome);
        campanha.setDtinicio(dtinicio);
        campanha.setDtfim(dtfim);
        campanha.setDadosad(infoad);
        campanha.setStatus(verificaStatusCampanha());
        campanha.salvar();

        /*CampanhaUsuario campanhaUsuario = new CampanhaUsuario();
        campanhaUsuario.setNome(nome);
        campanhaUsuario.setDtinicio(dtinicio);
        campanhaUsuario.setDtfim(dtfim);
        campanhaUsuario.setDadosad(infoad);
        campanhaUsuario.setStatus(verificaStatusCampanha());
        campanhaUsuario.salvar();*/

        finish();
    }

    public String verificaStatusCampanha(){
        return switchStatus.isChecked() ? "Ativa" : "Inativa" ;
    }

    /*private void recuperarCampanhasUsuario() {

        DatabaseReference campanhasRef = firebaseRef
                .child("campanhasusuario");


        campanhasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if(snapshot.getValue() !=null){
                    CampanhaUsuario campanhaUsuario= snapshot.getValue(CampanhaUsuario.class);
                    editNomeCampanha.setText(campanhaUsuario.getNome());
                    editDtinicioC.setText(campanhaUsuario.getDtinicio());
                    editDtFim.setText(campanhaUsuario.getDtfim());
                    editInfoad.setText(campanhaUsuario.getDadosad());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/


}