package deyse.souza.appvacina.model;

public class NotificacaoDados {

    private String to;
    private Notificaca notification;

    public NotificacaoDados(String to, Notificaca notification) {
        this.to = to;
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Notificaca getNotification() {
        return notification;
    }

    public void setNotification(Notificaca notification) {
        this.notification = notification;
    }
}
