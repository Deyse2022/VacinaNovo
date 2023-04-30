package deyse.souza.appvacina.model;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

import deyse.souza.appvacina.config.ConfiguracaoFirebase;

public class CampanhaUsuario implements Serializable {

      private String id;

    private String nome;

    private String dtinicio;

    private String dtfim;

    private String dadosad;

    private String status;

    private String nomeusuario;

    private String endereco;

    private String telefone;

    private String hratend;

    private String estado;

    private String municipio;


    public CampanhaUsuario(){


    }

    public void salvar(){

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference campanhaRef = firebaseRef
                .child("campanhasusuario");

                String idCampanhaUsuario = campanhaRef.push().getKey();
                setId(idCampanhaUsuario);

                campanhaRef.child(getId()).setValue(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeusuario() {
        return nomeusuario;
    }

    public void setNomeusuario(String nomeusuario) {
        this.nomeusuario = nomeusuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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

