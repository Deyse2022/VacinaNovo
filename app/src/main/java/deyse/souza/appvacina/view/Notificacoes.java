package deyse.souza.appvacina.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import deyse.souza.appvacina.R;
import deyse.souza.appvacina.api.NotificacaoService;
import deyse.souza.appvacina.config.ConfiguracaoFirebase;
import deyse.souza.appvacina.helper.DateCustom;
import deyse.souza.appvacina.helper.UsuarioFirebase;
import deyse.souza.appvacina.model.Notificaca;
import deyse.souza.appvacina.model.Notificacao;
import deyse.souza.appvacina.model.NotificacaoDados;
import deyse.souza.appvacina.model.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Notificacoes extends AppCompatActivity {

    private DatabaseReference firebaseRef;
    private String idUsuarioLogado;

    private EditText editIntervalo, editdtInicio;

    Spinner editspinnerLembrete;

    private String municipioUsuario;

    private Retrofit retrofit;
    private String baseUrl;


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



        baseUrl = "https://fcm.googleapis.com/fcm/";
        retrofit = new Retrofit.Builder()
                .baseUrl( baseUrl )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

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

    public void editNotificacoesSalvar(View view){

        enviarNotificacao();

        String tplembrete = editspinnerLembrete.getSelectedItem().toString();
        String intervalo = editIntervalo.getText().toString();
        String dtinicio = editdtInicio.getText().toString();

        Notificacao notificacao = new Notificacao();
        notificacao.setIdUsuario(idUsuarioLogado);
        notificacao.setTplembrete(tplembrete);
        notificacao.setIntervalo(intervalo);
        notificacao.setDtinicio(DateCustom.dataAtual());
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

    public void enviarNotificacao(){

        String to = "";
        to = municipioUsuario;



        Notificaca notificacao = new Notificaca("Existem Campanhas de Vacinação Ativas", "Atualize sua carteira de vacinação!");
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
                    municipioUsuario = "/topics/"+usuario.getMunicipio();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}


