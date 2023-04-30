package deyse.souza.appvacina.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import deyse.souza.appvacina.R;
import deyse.souza.appvacina.adapter.AdapterPessoas;
import deyse.souza.appvacina.config.ConfiguracaoFirebase;
import deyse.souza.appvacina.helper.UsuarioFirebase;
import deyse.souza.appvacina.listener.RecyclerItemClickListener;
import deyse.souza.appvacina.model.Pessoa;
import deyse.souza.appvacina.model.Vacina;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPessoa extends AppCompatActivity {

    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth autenticacao;

    private AdapterPessoas adapterPessoas;

    private List<Pessoa> pessoas = new ArrayList<>();

    private DatabaseReference firebaseRef;

    private RecyclerView recyclerPessoas;

    private Vacina vacina;

    private String idPessoaSel;

    private Pessoa pessoaSelecionada;

    private String idUsuarioLogado;

    private Retrofit retrofit;
    private String baseUrl;

    private String tokenUsuario;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();

        firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();

        idUsuarioLogado = UsuarioFirebase.getIdentificadorUsuario();

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Carteiras de Vacinação");
        setSupportActionBar(toolbar);

        baseUrl = "https://fcm.googleapis.com/fcm/";
        retrofit = new Retrofit.Builder()
                .baseUrl( baseUrl )
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        recyclerPessoas.setLayoutManager(new LinearLayoutManager(this));
        recyclerPessoas.setHasFixedSize(true);
        adapterPessoas = new AdapterPessoas(pessoas, this);
        recyclerPessoas.setAdapter(adapterPessoas);

        recyclerPessoas.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerPessoas,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Pessoa pessoaSelecionada = pessoas.get(position);
                                Intent i = new Intent(MainPessoa.this, ListaVacinas.class);
                                i.putExtra("pessoa", pessoaSelecionada);
                                startActivity(i);


                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        recuperarPessoas();

        DatabaseReference usuarios = referencia.child( "usuarios" );

        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("FIREBASE", dataSnapshot.getValue().toString() );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_pessoa,menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.menuadeletePessoa:
                abrirAddPessoa();
                break;
            case R.id.menuMeuPerfil:
                abrirMeuPerfil();
                break;
            case R.id.menuLembretes:
                abrirLembretes();
                break;
            case R.id.menuCampVacinacao:
                abrirCampVacinacao();
                break;
            case R.id.menuSair:
                deslogarUsuario();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deslogarUsuario(){
        try{
            autenticacao.signOut();
            finish();
        }catch (Exception e){
           e.printStackTrace();
        }
    }

    private void abrirAddPessoa(){
        startActivity(new Intent(MainPessoa.this, AddPessoa.class));
    }
    private void abrirMeuPerfil(){
        startActivity(new Intent(MainPessoa.this, MeuPerfilP.class));
    }

    private void abrirLembretes(){
        startActivity(new Intent(MainPessoa.this, Lembretes.class));
    }

    private void abrirCampVacinacao(){
        startActivity(new Intent(MainPessoa.this, CampVacinacao.class));
    }

    public void inicializarComponentes(){
        recyclerPessoas = findViewById(R.id.recyclerPessoas);
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

                adapterPessoas.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void deletePessoa(View view){
        Toast.makeText(getApplicationContext(), "Deletar pessoa",
                Toast.LENGTH_LONG).show();
    }

    // Declare the launcher at the top of your Activity/Fragment:
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });

    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }




}