package com.caravanas.Database;

public class ScriptDDL {

    public static String getCreateTableExcursao(){

        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS EXCURSAO (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "Nome VARCHAR (250)         NOT NULL DEFAULT (''), " +
                    "LocalSaida VARCHAR (250)   NOT NULL DEFAULT (''), " +
                    "Data VARCHAR (20)          NOT NULL DEFAULT (''), " +
                    "Telefone VARCHAR (20)      NOT NULL DEFAULT (''));");

        return sql.toString();

    };
}
