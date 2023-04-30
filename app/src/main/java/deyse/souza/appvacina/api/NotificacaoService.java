package deyse.souza.appvacina.api;

import deyse.souza.appvacina.model.NotificacaoDados;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NotificacaoService {

    @Headers({
            "Authorization:key=AAAALXNtCpQ:APA91bEZg1sKqeOSktjzIJQ3o54DCJSr8Sqp8L2RsqMPPkEbWgcdupHRmYkyhiUNICaPH52_nJTp2dsh0dQvz-4_nmdec3RccInuJcWfy9fSTgumcHQR6iy4oRwkm6Bj0XzA6Ne9SAcz",
            "Content-Type:application/json"
    })

    @POST("send")
    Call<NotificacaoDados> salvarNotificacao(@Body NotificacaoDados notificacaoDados);

}
