package deyse.souza.appvacina.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import deyse.souza.appvacina.adapter.AdapterVacinas;
import deyse.souza.appvacina.config.ConfiguracaoFirebase;
import deyse.souza.appvacina.helper.DateCustom;
import deyse.souza.appvacina.helper.UsuarioFirebase;
import deyse.souza.appvacina.listener.RecyclerItemClickListener;
import deyse.souza.appvacina.model.Pessoa;
import deyse.souza.appvacina.model.Vacina;

public class ListaVacinas extends AppCompatActivity {

    private RecyclerView recyclerVacinas;
    private List<Vacina> vacinas = new ArrayList<>();

    private TextView textNomePessoaVc;

    private Pessoa pessoaSelecionada;

    private DatabaseReference firebaseRef;

    private AdapterVacinas adapterVacinas;

    private String idUsuarioLogado;

    private String idPessoaSel, datansPessoaSel;

    private Vacina vacina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_vacinas);



        idUsuarioLogado = UsuarioFirebase.getIdentificadorUsuario();

        firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();


        inicializarComponentes();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            pessoaSelecionada = (Pessoa) bundle.getSerializable("pessoa");
            textNomePessoaVc.setText (pessoaSelecionada.getNome());
            idPessoaSel = pessoaSelecionada.getIdPessoaV();
            datansPessoaSel = pessoaSelecionada.getDtnascimento();

        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Vacina+");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerVacinas.setLayoutManager(new LinearLayoutManager(this));
        adapterVacinas = new AdapterVacinas(vacinas, this);
        recyclerVacinas.setAdapter(adapterVacinas);
        recyclerVacinas.setHasFixedSize(true);

        recyclerVacinas.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerVacinas,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                informarAplicacao(position);

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


        validarVacina();

        recuperarVacinas();
    }

    private void informarAplicacao(int posicao){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Data de Aplicação");
        builder.setMessage("Digite a data de aplicação");

        EditText editAplicacao = new EditText(this);
        editAplicacao.setText(DateCustom.dataAtual());


        builder.setView(editAplicacao);

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String aplicacao = editAplicacao.getText().toString();


                Vacina vacinaselecionada = vacinas.get(posicao);
                vacinaselecionada.setDtaplicacao(aplicacao);
                vacinaselecionada.atualizarAplicacao();
                vacinaselecionada.setStatus("Em dia");
                vacinaselecionada.atualizarStatus();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_vacina,menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void inicializarComponentes(){
        textNomePessoaVc = findViewById(R.id.textNomePessoaVc);
        recyclerVacinas =findViewById(R.id.recyclerVacinas);
    }

    public void validarVacina(){

        DatabaseReference vacinaRef = firebaseRef
                .child("vacinas")
                .child(idPessoaSel)
                .child((idUsuarioLogado));

        Query vacinaPesquisa = vacinaRef.orderByChild("nome");

        vacinaPesquisa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue()==null){
                    prepararVacinas();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void recuperarVacinas(){

        DatabaseReference vacinasRef = firebaseRef
                .child("vacinas")
                .child(idPessoaSel)
                .child(idUsuarioLogado);

        vacinasRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                vacinas.clear();

                for (DataSnapshot ds: snapshot.getChildren()){
                    vacinas.add(ds.getValue(Vacina.class));



                }

                adapterVacinas.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




    public void prepararVacinas(){

        vacina = new Vacina();
        vacina.setIdUsuario(idUsuarioLogado);
        vacina.setIdPessoaV(idPessoaSel);
        vacina.setNome("BCG");
        vacina.setDtvencimento(datansPessoaSel);
        vacina.setDtaplicacao("");
        vacina.setStatus("Vencida");
        vacina.salvar();


        vacina = new Vacina();
        vacina.setIdUsuario(idUsuarioLogado);
        vacina.setIdPessoaV(idPessoaSel);
        vacina.setNome("Hepatite B");
        vacina.setDtvencimento(datansPessoaSel);
        vacina.setDtaplicacao("");
        vacina.setStatus("Vencida");
        vacina.salvar();

        vacina = new Vacina();
        vacina.setIdUsuario(idUsuarioLogado);
        vacina.setIdPessoaV(idPessoaSel);
        vacina.setNome("Poliomielite 1,2,3 - 1º Dose");
        vacina.setDtvencimento(datansPessoaSel);
        vacina.setDtaplicacao("");
        vacina.setStatus("Vencida");
        vacina.salvar();

        vacina = new Vacina();
        vacina.setIdUsuario(idUsuarioLogado);
        vacina.setIdPessoaV(idPessoaSel);
        vacina.setNome("Poliomielite 1,2,3 - 2º Dose");
        vacina.setDtvencimento("");
        vacina.setDtaplicacao("");
        vacina.setStatus("");
        vacina.salvar();

        vacina = new Vacina();
        vacina.setIdUsuario(idUsuarioLogado);
        vacina.setIdPessoaV(idPessoaSel);
        vacina.setNome("Febre Amarela");
        vacina.setDtvencimento(datansPessoaSel);
        vacina.setDtaplicacao("");
        vacina.setStatus("Vencida");
        vacina.salvar();

        vacina = new Vacina();
        vacina.setIdUsuario(idUsuarioLogado);
        vacina.setIdPessoaV(idPessoaSel);
        vacina.setNome("Febre Amarela - Reforço");
        vacina.setDtvencimento("");
        vacina.setDtaplicacao("");
        vacina.setStatus("");
        vacina.salvar();

        vacina = new Vacina();
        vacina.setIdUsuario(idUsuarioLogado);
        vacina.setIdPessoaV(idPessoaSel);
        vacina.setNome("Hepatite A");
        vacina.setDtvencimento(datansPessoaSel);
        vacina.setDtaplicacao("");
        vacina.setStatus("Vencida");
        vacina.salvar();

        vacina = new Vacina();
        vacina.setIdUsuario(idUsuarioLogado);
        vacina.setIdPessoaV(idPessoaSel);
        vacina.setNome("Gripe");
        vacina.setDtvencimento("");
        vacina.setDtaplicacao("");
        vacina.setStatus("");
        vacina.salvar();

        vacina = new Vacina();
        vacina.setIdUsuario(idUsuarioLogado);
        vacina.setIdPessoaV(idPessoaSel);
        vacina.setNome("Varicela");
        vacina.setDtvencimento(datansPessoaSel);
        vacina.setDtaplicacao("");
        vacina.setStatus("Vencida");
        vacina.salvar();



    }



}