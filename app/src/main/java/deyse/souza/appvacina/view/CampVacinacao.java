package deyse.souza.appvacina.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import deyse.souza.appvacina.R;
import deyse.souza.appvacina.adapter.AdapterCampanhasUsuario;
import deyse.souza.appvacina.config.ConfiguracaoFirebase;
import deyse.souza.appvacina.helper.UsuarioFirebase;
import deyse.souza.appvacina.model.CampanhaUsuario;
import deyse.souza.appvacina.model.Usuario;

public class CampVacinacao extends AppCompatActivity {

    private RecyclerView recyclerCampUsuario;

    private TextView textAgCamp;

    private DatabaseReference firebaseRef;

    private String idUsuarioLogado, statusCampanha, municipioUsuario, idUsuario;

    private List<CampanhaUsuario> campanhaUsuarios = new ArrayList<>();

    private AdapterCampanhasUsuario adapterCampanhasUsuario;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp_vacinacao);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Campanhas de Vacinação");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        inicialicarComponentes();

        adapterCampanhasUsuario = new AdapterCampanhasUsuario(campanhaUsuarios, getApplicationContext() );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerCampUsuario.setLayoutManager(layoutManager);
        recyclerCampUsuario.setHasFixedSize(true);
        recyclerCampUsuario.setAdapter(adapterCampanhasUsuario);


        firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        idUsuarioLogado = UsuarioFirebase.getIdentificadorUsuario();

        recuperarCampanhasUsuario();


    }



    private void inicialicarComponentes() {

        recyclerCampUsuario = findViewById(R.id.recyclerCampUsuario);
        textAgCamp = findViewById(R.id.textAgCamp);

    }

    private void recuperarCampanhasUsuario() {

        DatabaseReference campanhas = firebaseRef.child("campanhasusuario");

       Query campanhaPesquisa = campanhas.orderByChild("status").equalTo("Ativa");

        campanhaPesquisa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                    if (snapshot.getChildrenCount() > 0) {
                        textAgCamp.setVisibility(View.GONE);
                        recyclerCampUsuario.setVisibility(View.VISIBLE);
                    } else {
                        textAgCamp.setVisibility(View.VISIBLE);
                        recyclerCampUsuario.setVisibility(View.GONE);
                    }


                    for (DataSnapshot ds : snapshot.getChildren()) {

                        CampanhaUsuario campanhaUsuario = ds.getValue(CampanhaUsuario.class);
                        campanhaUsuarios.add(campanhaUsuario);



                }

                    adapterCampanhasUsuario.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}