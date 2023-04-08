package deyse.souza.appvacina.api;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import deyse.souza.appvacina.datamodel.UsuarioDataModel;
import deyse.souza.appvacina.model.Usuario;

public class AppDataBase extends SQLiteOpenHelper {

    private static final String DB_NAME ="vacinaDB.sqlite";
    private static final int DB_VERSION = 1;

    Cursor cursor;

    SQLiteDatabase db;

    public AppDataBase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        try{

            db.execSQL(UsuarioDataModel.gerarTabela());

            Log.i(AppUtil.LOG_APP,"TB Usuario" + UsuarioDataModel.gerarTabela());

        }catch (SQLiteException e){

            Log.e(AppUtil.LOG_APP,"Erro TB Usuario" +e.getMessage());

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insert(String tabela, ContentValues dados){

        boolean sucesso = true;

        try{

            Log.i(AppUtil.LOG_APP,tabela+"Insert executado com sucesso ");

            sucesso= db.insert(tabela, null, dados)>0;

        }catch(SQLiteException e){

            Log.e(AppUtil.LOG_APP,tabela+"Falhou ao executar o insert(): "+e.getMessage());
        }

        return sucesso;
    }

    public boolean delete(String tabela, int id){

        boolean sucesso = true;

        try{

            Log.i(AppUtil.LOG_APP,tabela+"Delete executado com sucesso ");

            sucesso= db.delete(tabela, "id=?", new String[]{Integer.toString(id)})>0;

        }catch(SQLiteException e){

            Log.e(AppUtil.LOG_APP,tabela+"Falhou ao executar o delete(): "+e.getMessage());
        }

        return sucesso;
    }

    public boolean update(String tabela, ContentValues dados){

        boolean sucesso = true;

        try{

            int id = dados.getAsInteger("id");

            Log.i(AppUtil.LOG_APP,tabela+"Update executado com sucesso ");

            sucesso= db.update(tabela,dados, "id=?", new String[]{Integer.toString(id)})>0;

        }catch(SQLiteException e){

            Log.e(AppUtil.LOG_APP,tabela+"Falhou ao executar o update(): "+e.getMessage());
        }

        return sucesso;
    }

    @SuppressLint("Range")
    public List<Usuario> list(String tabela){

        List<Usuario> list = new ArrayList<>();

        Usuario usuario;

        String sql = "SELECT * FROM"+tabela;

        try {

            cursor = db.rawQuery(sql, null);

            if (cursor.moveToFirst()) {
                do {

                    usuario = new Usuario();


                    usuario.setNome(cursor.getString(cursor.getColumnIndex(UsuarioDataModel.NOME)));
                    usuario.setEmail(cursor.getString(cursor.getColumnIndex(UsuarioDataModel.EMAIL)));
                    usuario.setEstado(cursor.getString(cursor.getColumnIndex(UsuarioDataModel.ESTADO)));
                    usuario.setMunicipio(cursor.getString(cursor.getColumnIndex(UsuarioDataModel.MUNICIPIO)));
                    //  usuario.setMunicipio(cursor.getInt(cursor.getColumnIndex(UsuarioDataModel.MUNICIPIO))==1);


                    list.add(usuario);

                } while (cursor.moveToNext());

                Log.i(AppUtil.LOG_APP,tabela+"Lista gerada com sucesso");

            }

        }catch(SQLiteException e){
            Log.e(AppUtil.LOG_APP,"Erro ao listar os dados"+tabela);
            Log.e(AppUtil.LOG_APP,"Erro "+e.getMessage());
        }

        return list;
    }
}
