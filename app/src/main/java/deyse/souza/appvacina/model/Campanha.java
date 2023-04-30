package deyse.souza.appvacina.model;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

import deyse.souza.appvacina.config.ConfiguracaoFirebase;

public class Campanha implements Serializable {

    private String idUsuario;

    private String idCampanha;

    private String nome;

    private String dtinicio;

    private String dtfim;

    private String dadosad;

    private String status;

    private String endereço;

    private String telefone;

    private String hratend;

    public Campanha() {

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference pessoaRef = firebaseRef
                .child("campanhas");
        setIdCampanha( pessoaRef.push().getKey() );

    }

    public void salvar(){

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference pessoaRef = firebaseRef
                .child("campanhas")
                .child(getIdUsuario())
                .child(getIdCampanha());
        pessoaRef.setValue(this);
    }

    public String getEndereço() {
        return endereço;
    }

    public void setEndereço(String endereço) {
        this.endereço = endereço;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getHratend() {
        return hratend;
    }

    public void setHratend(String hratend) {
        this.hratend = hratend;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdCampanha() {
        return idCampanha;
    }

    public void setIdCampanha(String idCampanha) {
        this.idCampanha = idCampanha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDtinicio() {
        return dtinicio;
    }

    public void setDtinicio(String dtinicio) {
        this.dtinicio = dtinicio;
    }

    public String getDtfim() {
        return dtfim;
    }

    public void setDtfim(String dtfim) {
        this.dtfim = dtfim;
    }

    public String getDadosad() {
        return dadosad;
    }

    public void setDadosad(String dadosad) {
        this.dadosad = dadosad;
    }
}
