package deyse.souza.appvacina.controller;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.Nullable;

import java.util.List;

import deyse.souza.appvacina.api.AppDataBase;
import deyse.souza.appvacina.datamodel.UsuarioDataModel;
import deyse.souza.appvacina.model.Usuario;

public class UsuarioController extends AppDataBase {

    private static final String TABELA = UsuarioDataModel.TABELA;
    private ContentValues dados;


    public UsuarioController(@Nullable Context context) {
        super(context);
    }

    public boolean incluir(Usuario obj) {

        dados = new ContentValues();
        dados.put(UsuarioDataModel.NOME, obj.getNome());
        dados.put(UsuarioDataModel.EMAIL, obj.getEmail());
        dados.put(UsuarioDataModel.SENHA, obj.getSenha());
        dados.put(UsuarioDataModel.ESTADO, obj.getEstado());
        dados.put(UsuarioDataModel.MUNICIPIO, obj.getMunicipio());

        return insert(TABELA, dados);
    }

    public boolean alterar(Usuario obj) {

        dados = new ContentValues();
        dados.put(UsuarioDataModel.ID, obj.getNome());
        dados.put(UsuarioDataModel.NOME, obj.getNome());
        dados.put(UsuarioDataModel.EMAIL, obj.getNome());
        dados.put(UsuarioDataModel.SENHA, obj.getNome());
        dados.put(UsuarioDataModel.TIPOPERFIL, obj.getNome());
        dados.put(UsuarioDataModel.CNES, obj.getNome());
        dados.put(UsuarioDataModel.ESTADO, obj.getNome());
        dados.put(UsuarioDataModel.MUNICIPIO, obj.getNome());

        return update(TABELA, dados);
    }

   /* public boolean deletar(Usuario obj) {

        return delete(TABELA, obj.getIdUsuario());
    }*/

    public List<Usuario> listar() {

        return list(TABELA);
    }

    public static boolean validarDadosUsuario(Usuario usuario, String email, String senha) {

        boolean retorno =  ((usuario.getEmail() == email) && (usuario.getSenha() == senha));

        return retorno;
    }
}
