package deyse.souza.appvacina.model;

import com.google.firebase.database.DatabaseReference;

import deyse.souza.appvacina.config.ConfiguracaoFirebase;

public class Pessoa {

    private String idUsuario;
    private String nome;
    private String dtnasc;

    private String status;

    public Pessoa() {
    }

    public void salvar(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference empresaRef = firebaseRef.child("pessoas")
                .child(getIdUsuario())
                .push();
        empresaRef.setValue(this);
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
