package deyse.souza.appvacina.helper;

import android.app.Activity;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import deyse.souza.appvacina.config.ConfiguracaoFirebase;
import deyse.souza.appvacina.model.Usuario;
import deyse.souza.appvacina.view.MainInstSaude;
import deyse.souza.appvacina.view.MainPessoa;

public class UsuarioFirebase {

    public static FirebaseUser getUsuarioAtual(){
        FirebaseAuth usuario = ConfiguracaoFirebase.getFirebaseAutenticacao();
        return usuario.getCurrentUser();
    }

    public static String getIdentificadorUsuario(){

        return getUsuarioAtual().getUid();
    }


    public static void redirecionaUsuarioLogado(final Activity activity){

        FirebaseUser user = getUsuarioAtual();
        if(user != null ){
            DatabaseReference usuariosRef = ConfiguracaoFirebase.getFirebaseDatabase()
                    .child("usuarios")
                    .child( getIdentificadorUsuario() );
            usuariosRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    Usuario usuario = dataSnapshot.getValue( Usuario.class );

                    String tipoUsuario = usuario.getTipo();

                    if( tipoUsuario.equals("IS") ){
                        Intent i = new Intent(activity, MainInstSaude.class);
                        activity.startActivity(i);
                    }else {
                        Intent i = new Intent(activity, MainPessoa.class);
                        activity.startActivity(i);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }



}
