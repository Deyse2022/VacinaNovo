package deyse.souza.appvacina.model;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import deyse.souza.appvacina.config.ConfiguracaoFirebase;

public class Vacina {

    private String IdVacina;

    private String idUsuario;

     private String idPessoaV;
    private String nome;
    private String dtvencimento;
    private String dtaplicacao;
    private String status;


    public Vacina() {
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference vacinaRef = firebaseRef
                .child("vacinas");
        setIdVacina( vacinaRef.push().getKey() );

    }

    public void salvar(){

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference pessoaRef = firebaseRef
                .child("vacinas")
                .child(getIdPessoaV())
                .child(getIdUsuario())
                .child(getIdVacina());
        pessoaRef.setValue(this);

    }

    public void atualizarStatus(){

        HashMap<String, Object> status = new HashMap<>();
        status.put("status", getStatus() );

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference vacinaRef = firebaseRef
                .child("vacinas")
                .child(getIdPessoaV())
                .child(getIdUsuario())
                .child(getIdVacina());
        vacinaRef.updateChildren( status );

    }

    public void atualizarAplicacao(){

        HashMap<String, Object> dtaplicacao = new HashMap<>();
        dtaplicacao.put("dtaplicacao", getDtaplicacao() );

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference vacinaRef = firebaseRef
                .child("vacinas")
                .child(getIdPessoaV())
                .child(getIdUsuario())
                .child(getIdVacina());
        vacinaRef.updateChildren( dtaplicacao );

    }



    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdVacina() {
        return IdVacina;
    }

    public void setIdVacina(String idVacina) {
        IdVacina = idVacina;
    }

    public String getIdPessoaV() {
        return idPessoaV;
    }

    public void setIdPessoaV(String idPessoaV) {
        this.idPessoaV = idPessoaV;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDtvencimento() {
        return dtvencimento;
    }

    public void setDtvencimento(String dtvencimento) {
        this.dtvencimento = dtvencimento;
    }

    public String getDtaplicacao() {
        return dtaplicacao;
    }

    public void setDtaplicacao(String dtaplicacao) {
        this.dtaplicacao = dtaplicacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
