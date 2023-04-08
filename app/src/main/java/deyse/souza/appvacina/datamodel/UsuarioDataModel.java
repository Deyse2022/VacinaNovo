package deyse.souza.appvacina.datamodel;

import java.util.Queue;

public class UsuarioDataModel {

    public static final String TABELA = "usuario";
    public static final String ID = "id";
    public static final String NOME = "nome";
    public static final String EMAIL = "email";
    public static final String SENHA = "senha";
    public static final String TIPOPERFIL = "tipoperfil";
    public static final String CNES = "cnes";
    public static final String ESTADO = "estado";
    public static final String MUNICIPIO = "municipio";
    private static final String DATA_INC = "datainc";
    private static final String DATA_ALT = "dataalt";

    private static String query;

    public static String gerarTabela(){

        query = "CREATE TABLE "+TABELA+" ( ";
        query += ID+" INTEGER PRIMARY KEY AUTOINCREMENT, ";
        query += NOME+" TEXT, ";
        query += EMAIL+" TEXT, ";
        query += SENHA+" TEXT, ";
        query += TIPOPERFIL+" TEXT, ";
        query += CNES+" TEXT, ";
        query += ESTADO+" TEXT, ";
        query += MUNICIPIO+" INTEGER, ";
        query += DATA_INC+" DATETIME DEFAULT CURRENT_TIMESTAMP, ";
        query += DATA_ALT+" DATETIME DEFAULT CURRENT_TIMESTAMP ";

        query += ")";

        return query;
    }

}
