package deyse.souza.appvacina.model;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

import deyse.souza.appvacina.config.ConfiguracaoFirebase;

public class Pessoa implements Serializable {

    private String idUsuario;

    private String idPessoaV;
    private String nome;
    private String dtnasc;

    private String status;


    public Pessoa() {

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference pessoaRef = firebaseRef
                .child("pessoas");
        setIdPessoaV( pessoaRef.push().getKey() );

    }

    public void salvar(){

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference pessoaRef = firebaseRef
                .child("pessoas")
                .child(getIdUsuario())
                .child(getIdPessoaV());
        pessoaRef.setValue(this);
    }


    public  String getIdPessoaV() {
        return idPessoaV;
    }

    public void setIdPessoaV(String idPessoaV) {
        this.idPessoaV = idPessoaV;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDtnascimento() {
        return dtnasc;
    }

    public void setDtnascimento(String dtnascimento) {
        this.dtnasc = dtnascimento;
    }
}
