package deyse.souza.appvacina.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.activity.ComponentActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import java.util.Objects;

import deyse.souza.appvacina.R;
import deyse.souza.appvacina.adapter.AdapterPessoas;
import deyse.souza.appvacina.helper.DateCustom;
import deyse.souza.appvacina.helper.UsuarioFirebase;
import deyse.souza.appvacina.model.Pessoa;
import deyse.souza.appvacina.model.Usuario;
import deyse.souza.appvacina.model.Vacina;

public class AddPessoa extends AppCompatActivity {

    private EditText editNomePessoa, editDtNascimento;

    private String idUsuarioLogado, idPessoaVc;

    private List<Pessoa> pessoas = new ArrayList<>();

    private AdapterPessoas adapterPessoas;

    private Pessoa pessoaSelecionada;

    private Vacina vacina;

    private String idPessoaSel;


    private DatabaseReference firebaseRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pessoa);

        inicializarComponntes();
        idUsuarioLogado = UsuarioFirebase.getIdentificadorUsuario();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            pessoaSelecionada = (Pessoa) bundle.getSerializable("pessoa");
            idPessoaSel = pessoaSelecionada.getIdPessoaV();

        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Vacina+");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void inicializarComponntes(){
        editNomePessoa = findViewById(R.id.editNomePessoa);
        editDtNascimento = findViewById(R.id.editDtNascimento);

    }

    public void validarDadosNvPessoa(View view){

        String nome = editNomePessoa.getText().toString();
        String dtnasc = editDtNascimento.getText().toString();


        if (!nome.isEmpty()){
            if (!dtnasc.isEmpty()){

                Pessoa pessoa = new Pessoa();
                pessoa.setIdUsuario( idUsuarioLogado);
                pessoa.setNome(nome);
                pessoa.setDtnascimento(dtnasc);
                pessoa.salvar();
                finish();

            }else{
                exibirMensagem("Digite uma data de nascimento");
            }

        }else{
            exibirMensagem("Digite um nome para a pessoa");
        }


    }

    private void exibirMensagem(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    private void recuperarPessoas(){

        DatabaseReference pessoasRef = firebaseRef
                .child("pessoas")
                .child(idUsuarioLogado);

        pessoasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                pessoas.clear();

                for (DataSnapshot ds: snapshot.getChildren()){
                    pessoas.add(ds.getValue(Pessoa.class));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}