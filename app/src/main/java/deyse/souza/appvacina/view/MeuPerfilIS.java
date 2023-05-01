package deyse.souza.appvacina.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import deyse.souza.appvacina.R;
import deyse.souza.appvacina.config.ConfiguracaoFirebase;
import deyse.souza.appvacina.helper.UsuarioFirebase;
import deyse.souza.appvacina.model.Usuario;

public class MeuPerfilIS extends AppCompatActivity {

    private DatabaseReference firebaseRef;
    private String idUsuarioLogado;

    private EditText editNomeUsuario, editEmailUsuario,edittipo, editEndereco, editTelefone, editHorario ;

    Spinner editspinnerEstado, editspinnerMunicipio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meu_perfil_is);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Vacina+");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        idUsuarioLogado = UsuarioFirebase.getIdentificadorUsuario();

        inicializarComponentes();

        recuperarDadosUsuario();

        carregarDadosSpinner();

    }

    public void inicializarComponentes(){
        editNomeUsuario = findViewById(R.id.editNomeUsuario);
        editEmailUsuario = findViewById(R.id.editEmailUsuario);
        editspinnerEstado = findViewById(R.id.editspinnerEstado);
        editspinnerMunicipio = findViewById(R.id.editspinnerMunicipio);
        edittipo = findViewById(R.id.edittipo);
        editEndereco = findViewById(R.id.editEndereco);
        editTelefone = findViewById(R.id.editTelefone);
        editHorario = findViewById(R.id.editHorario);
    }

    public void recuperarDadosUsuario(){

        DatabaseReference instsaudeRef = firebaseRef
                .child("usuarios")
                .child(idUsuarioLogado);
        instsaudeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.getValue() !=null){
                    Usuario usuario = snapshot.getValue(Usuario.class);
                    editNomeUsuario.setText(usuario.getNome());
                    editEmailUsuario.setText(usuario.getEmail());
                    edittipo.setText(usuario.getTipo());
                    editEndereco.setText(usuario.getEndereco());
                    editTelefone.setText(usuario.getTelefone());
                    editHorario.setText(usuario.getHorario());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




    public void editUsuarioSalvar(View view) {

        String nome = editNomeUsuario.getText().toString();
        String email = editEmailUsuario.getText().toString();
        String estado = editspinnerEstado.getSelectedItem().toString();
        String municipio = editspinnerMunicipio.getSelectedItem().toString();
        String tipo = edittipo.getText().toString();
        String endereco = editEndereco.getText().toString();
        String telefone = editTelefone.getText().toString();
        String horario = editHorario.getText().toString();


        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuarioLogado);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setEstado(estado);
        usuario.setMunicipio(municipio);
        usuario.setTipo(tipo);
        usuario.setEndereco(endereco);
        usuario.setTelefone(telefone);
        usuario.setHorario(horario);
        usuario.setStatus("A");
        usuario.salvar();
        finish();
    }

    private void carregarDadosSpinner () {
        String[] estados = new String[]{
                "SC", "PR", "RS"
        };


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, estados);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editspinnerEstado.setAdapter(adapter);



        editspinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                solicitaMunicipios();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void solicitaMunicipios () {

        int estado = editspinnerEstado.getSelectedItemPosition();

        if (estado == 0) {

            String[] municipiosSC = new String[]{
                    "Ilhota", "Gaspar", "Blumenau"
            };
            ArrayAdapter<String> adapterSC = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, municipiosSC);
            adapterSC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editspinnerMunicipio.setAdapter(adapterSC);

        } else if (estado == 1) {
            String[] municipiosPR = new String[]{
                    "Antonina", "Bom Sucesso"
            };
            ArrayAdapter<String> adapterPR = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, municipiosPR);
            adapterPR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editspinnerMunicipio.setAdapter(adapterPR);
        } else if (estado == 2) {
            String[] municipiosRS = new String[]{
                    "Porto Alegre", "Canoas"
            };
            ArrayAdapter<String> adapterRS = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, municipiosRS);
            adapterRS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            editspinnerMunicipio.setAdapter(adapterRS);
        }
    }
}