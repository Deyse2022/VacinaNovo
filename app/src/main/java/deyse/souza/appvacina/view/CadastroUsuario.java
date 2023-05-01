package deyse.souza.appvacina.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.messaging.FirebaseMessaging;

import deyse.souza.appvacina.R;
import deyse.souza.appvacina.config.ConfiguracaoFirebase;
import deyse.souza.appvacina.model.Usuario;

public class CadastroUsuario extends AppCompatActivity {


    private TextInputEditText campoNome, campoEmail, campoSenha;
    private SwitchCompat switchTipoUsuario;
    Spinner spinnerEstado, spinnerMunicipio;
    private FirebaseAuth autenticacao;

    private String tokenUsuario;

    private String municipioTopics;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        campoNome = findViewById(R.id.editNome);
        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        spinnerEstado = findViewById(R.id.spinnerEstado);
        spinnerMunicipio = findViewById(R.id.spinnerMunicipio);
        switchTipoUsuario = findViewById(R.id.switchTipoUsuario);

        FirebaseMessaging.getInstance().subscribeToTopic(municipioTopics);

        carregarDadosSpinner();

        recuperarToken();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Vacina+");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void validarCadastroUsuario (View view) {

        String textoNome = campoNome.getText().toString();
        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();
        String textoEstado = spinnerEstado.getSelectedItem().toString();
        String textoMunicipio = spinnerMunicipio.getSelectedItem().toString();


        if (!textoNome.isEmpty()) {
            if (!textoEmail.isEmpty()) {
                if (!textoSenha.isEmpty()) {


                    Usuario usuario = new Usuario();
                    usuario.setNome(textoNome);
                    usuario.setEmail(textoEmail);
                    usuario.setSenha(textoSenha);
                    usuario.setTipo( verificaTipoUsuario() );
                    usuario.setEstado(textoEstado);
                    usuario.setMunicipio(textoMunicipio);
                    usuario.setChavetoken(tokenUsuario);
                    usuario.setStatus(verificaStatusUsuario());


                    cadastrarUsuario(usuario);

                    municipioTopics = usuario.getMunicipio();


                } else {
                    Toast.makeText(CadastroUsuario.this,
                            "Preencha a senha!",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(CadastroUsuario.this,
                        "Preencha o email!",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(CadastroUsuario.this,
                    "Preencha o nome!",
                    Toast.LENGTH_SHORT).show();
        }



    }



    private void carregarDadosSpinner () {
        String[] estados = new String[]{
                "SC", "PR", "RS"
        };

        String[] perfis = new String[]{
                "Pessoa", "Instituição de Saúde"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, estados);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(adapter);

        ArrayAdapter<String> adapterP = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, perfis);
        adapterP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        int estado = spinnerEstado.getSelectedItemPosition();

        if (estado == 0) {

            String[] municipiosSC = new String[]{
                    "Ilhota", "Gaspar", "Blumenau"
            };
            ArrayAdapter<String> adapterSC = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, municipiosSC);
            adapterSC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerMunicipio.setAdapter(adapterSC);

        } else if (estado == 1) {
            String[] municipiosPR = new String[]{
                    "Antonina", "Bom Sucesso"
            };
            ArrayAdapter<String> adapterPR = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, municipiosPR);
            adapterPR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerMunicipio.setAdapter(adapterPR);
        } else if (estado == 2) {
            String[] municipiosRS = new String[]{
                    "Porto Alegre", "Canoas"
            };
            ArrayAdapter<String> adapterRS = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, municipiosRS);
            adapterRS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerMunicipio.setAdapter(adapterRS);
        }
    }


    public void cadastrarUsuario (final Usuario usuario) {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    String idUsuario = task.getResult().getUser().getUid();
                    usuario.setIdUsuario(idUsuario);
                    usuario.salvar();


                    if (verificaTipoUsuario() == "P") {
                        abrirTelaPrincipal();
                    } else {
                        abrirTelaPrincipalI();
                    }


                } else {

                    String excecao = "";

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        excecao = "Digite uma senha mais forte";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "Digite um e-mail válido";

                    } catch (FirebaseAuthUserCollisionException e) {
                        excecao = "Esta conta já foi cadastrada";
                    } catch (Exception e) {
                        excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroUsuario.this, excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void abrirTelaPrincipal () {
        startActivity(new Intent(this, MainPessoa.class));

    }
    public void abrirTelaPrincipalI () {
        if(verificaStatusUsuario().equals("A")){
            startActivity(new Intent(this, MainInstSaude.class));
        }
        else{
            Toast.makeText(getApplicationContext(), "Contate o Administrador para Ativar o cadastro!",
                    Toast.LENGTH_LONG).show();
        }

    }

    public String verificaTipoUsuario(){
        return switchTipoUsuario.isChecked() ? "IS" : "P" ;
    }


    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cadastro,menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void recuperarToken(){

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {

                        if ( !task.isSuccessful() ){
                            return;
                        }

                        tokenUsuario = task.getResult();

                        Log.i("Token", tokenUsuario);

                    }
                });

    }

    public String verificaStatusUsuario(){
        return switchTipoUsuario.isChecked() ? "A" : "A" ;
    }



}
