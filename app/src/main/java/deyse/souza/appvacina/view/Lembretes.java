package deyse.souza.appvacina.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import deyse.souza.appvacina.R;
import deyse.souza.appvacina.api.NotificacaoService;
import deyse.souza.appvacina.config.ConfiguracaoFirebase;
import deyse.souza.appvacina.helper.UsuarioFirebase;
import deyse.souza.appvacina.model.Lembrete;
import deyse.souza.appvacina.model.Notificaca;
import deyse.souza.appvacina.model.NotificacaoDados;
import deyse.souza.appvacina.model.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Lembretes extends AppCompatActivity {

    private DatabaseReference firebaseRef;
    private String idUsuarioLogado;

    private EditText editIntervalo, editdtInicio;

    Spinner editspinnerLembrete;

    private Retrofit retrofit;
    private String baseUrl;

    private String tokenUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lembretes);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Vacina+");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        baseUrl = "https://fcm.googleapis.com/fcm/";
        retrofit = new Retrofit.Builder()
                .baseUrl( baseUrl )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        idUsuarioLogado = UsuarioFirebase.getIdentificadorUsuario();

        inicializarComponentes();

        carregarDadosSpinner();

        recuperarDadosLembrete();

        recuperarDadosUsuario();


    }

    public void inicializarComponentes(){
        editIntervalo = findViewById(R.id.editIntervalo);
        editspinnerLembrete = findViewById(R.id.editspinnerLembrete);
        editdtInicio = findViewById(R.id.editdtInicio);
    }



    public void editLembreteSalvar(View view){

        enviarNotificacao();

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

    public void enviarNotificacao(){

        String to = "";
        to = tokenUsuario;

        /*to = "/topics/"+tokenUsuario;*/


        Notificaca notificacao = new Notificaca("Existem vacinas Pendentes", "Atualize sua carteira de vacinação!");
        NotificacaoDados notificacaoDados = new NotificacaoDados(to, notificacao );

        NotificacaoService service = retrofit.create(NotificacaoService.class);
        Call<NotificacaoDados> call = service.salvarNotificacao( notificacaoDados );

        call.enqueue(new Callback<NotificacaoDados>() {
            @Override
            public void onResponse(Call<NotificacaoDados> call, Response<NotificacaoDados> response) {

                if( response.isSuccessful() ){

                    Toast.makeText(getApplicationContext(),
                            "codigo: " + response.code(),
                            Toast.LENGTH_LONG );

                }
            }

            @Override
            public void onFailure(Call<NotificacaoDados> call, Throwable t) {

            }
        });

    }

    public void recuperarDadosUsuario(){

        DatabaseReference usuarioRef = firebaseRef
                .child("usuarios")
                .child(idUsuarioLogado);
        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.getValue() !=null){
                    Usuario usuario = snapshot.getValue(Usuario.class);
                    tokenUsuario = usuario.getChavetoken();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}