package deyse.souza.appvacina.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import deyse.souza.appvacina.config.ConfiguracaoFirebase;

public class Usuario {

    private String tipo;
    private String Nome;
    private String email;
    private String senha;
    private String estado;
    private String municipio;
    private String idUsuario;

    private String endereco;

    private String telefone;

    private String horario;

    private String chavetoken;

    private String status;


    public Usuario() {

    }

    public void salvar() {
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebaseDatabase();
        firebase.child("usuarios")
                .child(this.getIdUsuario())
                .setValue(this);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChavetoken() {
        return chavetoken;
    }

    public void setChavetoken(String chavetoken) {
        this.chavetoken = chavetoken;
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

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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


    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


}
