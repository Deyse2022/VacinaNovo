package deyse.souza.appvacina.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import deyse.souza.appvacina.R;
import deyse.souza.appvacina.config.ConfiguracaoFirebase;
import deyse.souza.appvacina.helper.UsuarioFirebase;
import deyse.souza.appvacina.model.Notificacao;

public class Notificacoes extends AppCompatActivity {

    private DatabaseReference firebaseRef;
    private String idUsuarioLogado;

    private EditText editIntervalo, editdtInicio;

    Spinner editspinnerLembrete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacoes);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Vacina+");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        idUsuarioLogado = UsuarioFirebase.getIdentificadorUsuario();

        inicializarComponentes();

        carregarDadosSpinner();

        recuperarDadosLembrete();


    }

    public void inicializarComponentes(){
        editIntervalo = findViewById(R.id.editIntervalo);
        editspinnerLembrete = findViewById(R.id.editspinnerLembrete);
        editdtInicio = findViewById(R.id.editdtInicio);
    }

    public void editNotificacoesSalvar(View view){
        String tplembrete = editspinnerLembrete.getSelectedItem().toString();
        String intervalo = editIntervalo.getText().toString();
        String dtinicio = editdtInicio.getText().toString();

        Notificacao notificacao = new Notificacao();
        notificacao.setIdUsuario(idUsuarioLogado);
        notificacao.setTplembrete(tplembrete);
        notificacao.setIntervalo(intervalo);
        notificacao.setDtinicio(dtinicio);
        notificacao.salvar();
        finish();

    }

    private void carregarDadosSpinner () {
        String[] estados = new String[]{
                "Diário", "Semanal", "Mensal", "Anual"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, estados);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editspinnerLembrete.setAdapter(adapter);


    }

    public void recuperarDadosLembrete(){

        DatabaseReference usuarioRef = firebaseRef
                .child("notificacoes")
                .child(idUsuarioLogado);
        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.getValue() !=null){
                    Notificacao notificacao = snapshot.getValue(Notificacao.class);
                    editIntervalo.setText(notificacao.getIntervalo());
                    editdtInicio.setText(notificacao.getDtinicio());


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}