package deyse.souza.appvacina.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import deyse.souza.appvacina.R;
import deyse.souza.appvacina.adapter.AdapterCampanhas;
import deyse.souza.appvacina.config.ConfiguracaoFirebase;
import deyse.souza.appvacina.helper.UsuarioFirebase;
import deyse.souza.appvacina.listener.RecyclerItemClickListener;
import deyse.souza.appvacina.model.Campanha;

public class MainInstSaude extends AppCompatActivity {

    private FirebaseAuth autenticacao;

    private AdapterCampanhas adapterCampanhas;

    private List<Campanha> campanhas = new ArrayList<>();

    private DatabaseReference firebaseRef;

    private RecyclerView recyclerCampanhas;

    private String idUsuarioLogado;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_inst_saude);

        inicializarComponentes();

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();

        idUsuarioLogado = UsuarioFirebase.getIdentificadorUsuario();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Vacina+");
        setSupportActionBar(toolbar);

        recyclerCampanhas.setLayoutManager(new LinearLayoutManager(this));
        recyclerCampanhas.setHasFixedSize(true);
        adapterCampanhas = new AdapterCampanhas(campanhas, this);
        recyclerCampanhas.setAdapter(adapterCampanhas);

        recyclerCampanhas.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerCampanhas,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Campanha campanhaSelecionada = campanhas.get(position);
                                Intent i = new Intent(MainInstSaude.this, EditaCampanha.class);
                                i.putExtra("campanha", campanhaSelecionada);
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

        recuperarCampanhas();

    }

    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_instsaude,menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.menuINovaCampV:
                abrirNovaCampV();
                break;
            case R.id.menuMeuPerfilIS:
                abrirMeuPerfilIS();
                break;
            case R.id.menuNotificaoes:
                abrirNotificacoes();
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

    private void abrirNovaCampV(){
        startActivity(new Intent(MainInstSaude.this, NovaCampV.class));
    }

    private void abrirMeuPerfilIS(){
        startActivity(new Intent(MainInstSaude.this, MeuPerfilIS.class));
    }

    private void abrirNotificacoes(){
        startActivity(new Intent(MainInstSaude.this, Notificacoes.class));
    }

    public void inicializarComponentes(){
        recyclerCampanhas = findViewById(R.id.recyclerCampVacinas);
    }

    private void recuperarCampanhas(){

        DatabaseReference pessoasRef = firebaseRef
                .child("campanhas")
                .child(idUsuarioLogado);

        pessoasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                campanhas.clear();

                for (DataSnapshot ds: snapshot.getChildren()){
                    campanhas.add(ds.getValue(Campanha.class));

                }

                adapterCampanhas.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}