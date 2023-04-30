package deyse.souza.appvacina.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import deyse.souza.appvacina.R;
import deyse.souza.appvacina.model.Usuario;
import deyse.souza.appvacina.view.MainPessoa;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        if(message.getNotification() != null){



            String titulo = message.getNotification().getTitle();
            String corpo = message.getNotification().getBody();

            enviarNotificacao(titulo,corpo);

        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

    }

    private void enviarNotificacao(String titulo, String corpo){

        String canal = getString(R.string.default_notification_channel_id);
        Uri uriSom = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(this, MainPessoa.class);
        PendingIntent pendingIntent = PendingIntent.getActivity( this, 0,intent, PendingIntent.FLAG_IMMUTABLE );

        NotificationCompat.Builder message = new NotificationCompat.Builder(this,canal )
                .setContentTitle(titulo)
                .setContentText(corpo)
                .setSmallIcon(R.drawable.add_campvacina_24)
                .setSound(uriSom)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE );

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(canal, "canal", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, message.build() );

    }
}
