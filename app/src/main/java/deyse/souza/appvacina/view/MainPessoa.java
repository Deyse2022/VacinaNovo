package deyse.souza.appvacina.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
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
import deyse.souza.appvacina.model.Usuario;

public class MainPessoa extends AppCompatActivity {

    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth autenticacao;

    private AdapterPessoas adapterPessoas;

    private List<Pessoa> pessoas = new ArrayList<>();

    private DatabaseReference firebaseRef;

    private RecyclerView recyclerPessoas;

    private String idUsuarioLogado;

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
        toolbar.setTitle("Vacina+");
        setSupportActionBar(toolbar);

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
                                startActivity(new Intent(MainPessoa.this, ListaVacinas.class));

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
            case R.id.menuaddPessoa:
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
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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

}