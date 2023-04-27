package deyse.souza.appvacina.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import deyse.souza.appvacina.model.Lembrete;

public class Lembretes extends AppCompatActivity {

    private DatabaseReference firebaseRef;
    private String idUsuarioLogado;

    private EditText editIntervalo, editdtInicio;

    Spinner editspinnerLembrete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lembretes);

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



    public void editLembreteSalvar(View view){
        String tplembrete = editspinnerLembrete.getSelectedItem().toString();
        String intervalo = editIntervalo.getText().toString();
        String dtinicio = editdtInicio.getText().toString();

        Lembrete lembrete = new Lembrete();
        lembrete.setIdUsuario(idUsuarioLogado);
        lembrete.setTplembrete(tplembrete);
        lembrete.setIntervalo(intervalo);
        lembrete.setDtinicio(dtinicio);
        lembrete.salvar();
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
                .child("lembretes")
                .child(idUsuarioLogado);
        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.getValue() !=null){
                    Lembrete lembrete = snapshot.getValue(Lembrete.class);
                    editIntervalo.setText(lembrete.getIntervalo());
                    editdtInicio.setText(lembrete.getDtinicio());


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}