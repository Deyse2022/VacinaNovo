package deyse.souza.appvacina.view;

import static deyse.souza.appvacina.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import deyse.souza.appvacina.R;
import deyse.souza.appvacina.config.ConfiguracaoFirebase;
import deyse.souza.appvacina.helper.UsuarioFirebase;
import deyse.souza.appvacina.model.Usuario;

public class Login extends AppCompatActivity {


    private TextInputEditText campoEmail, campoSenha;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_login);

        Toolbar toolbar = findViewById(id.toolbar);
        toolbar.setTitle("Vacina+");
        setSupportActionBar(toolbar);


        campoEmail = findViewById(id.editLoginEmail);
        campoSenha = findViewById(id.editLoginSenha);



    }

    public void validarLoginUsuario(View view){


        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();

        if( !textoEmail.isEmpty() ) {
            if( !textoSenha.isEmpty() ) {
                Usuario usuario = new Usuario();
                usuario.setEmail( textoEmail );
                usuario.setSenha( textoSenha );

                logarUsuario( usuario );

            }else{
                Toast.makeText(Login.this,
                        "Preencha a senha!",
                        Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(Login.this,
                    "Preencha o email!",
                    Toast.LENGTH_SHORT).show();
        }


    }

    public void logarUsuario( Usuario usuario ){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if( task.isSuccessful() ){

                    UsuarioFirebase.redirecionaUsuarioLogado(Login.this);

                }else {

                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthInvalidUserException e ) {
                        excecao = "Usuário não está cadastrado.";
                    }catch ( FirebaseAuthInvalidCredentialsException e ){
                        excecao = "E-mail e senha não correspondem a um usuário cadastrado";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário: "  + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(Login.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    public void lerTermos(View view) {

        Toast.makeText(getApplicationContext(), "Aplicativo em fase de testes!",
                Toast.LENGTH_LONG).show();
    }

    public void abrirTelaCadastro(View view){
        Intent i = new Intent(Login.this, CadastroUsuario.class);
        startActivity(i);
    }

}
