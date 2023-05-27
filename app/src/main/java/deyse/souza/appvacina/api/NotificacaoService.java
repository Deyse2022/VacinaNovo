package deyse.souza.appvacina.api;

import deyse.souza.appvacina.model.NotificacaoDados;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NotificacaoService {

    @Headers({
            "Authorization:key=",
            "Content-Type:application/json"
    })
//a chave do servidor deve ser solicitada ao desenvolvedor do sistema

    @POST("send")
    Call<NotificacaoDados> salvarNotificacao(@Body NotificacaoDados notificacaoDados);

}
