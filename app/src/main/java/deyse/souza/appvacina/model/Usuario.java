package deyse.souza.appvacina.model;

import android.widget.Spinner;

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


    public Usuario() {

    }

    public void salvar() {
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebaseDatabase();
        firebase.child("usuários")
                .child(this.getIdUsuario())
                .setValue(this);
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
