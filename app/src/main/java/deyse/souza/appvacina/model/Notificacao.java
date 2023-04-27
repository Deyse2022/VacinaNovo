package deyse.souza.appvacina.model;

import com.google.firebase.database.DatabaseReference;

import deyse.souza.appvacina.config.ConfiguracaoFirebase;

public class Notificacao {

    private String idUsuario;

    private String intervalo;

    private String dtinicio;

    private String tplembrete;

    public Notificacao() {
    }

    public void salvar() {
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebaseDatabase();
        firebase.child("notificacoes")
                .child(this.getIdUsuario())
                .setValue(this);
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(String intervalo) {
        this.intervalo = intervalo;
    }

    public String getDtinicio() {
        return dtinicio;
    }

    public void setDtinicio(String dtinicio) {
        this.dtinicio = dtinicio;
    }

    public String getTplembrete() {
        return tplembrete;
    }

    public void setTplembrete(String tplembrete) {
        this.tplembrete = tplembrete;
    }
}
