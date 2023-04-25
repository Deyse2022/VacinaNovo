package deyse.souza.appvacina.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.activity.ComponentActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import deyse.souza.appvacina.R;
import deyse.souza.appvacina.helper.UsuarioFirebase;
import deyse.souza.appvacina.model.Pessoa;
import deyse.souza.appvacina.model.Usuario;

public class AddPessoa extends AppCompatActivity {

    private EditText editNomePessoa, editDtNascimento;



    private String idUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pessoa);

        inicializarComponntes();
        idUsuarioLogado = UsuarioFirebase.getIdentificadorUsuario();

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
}