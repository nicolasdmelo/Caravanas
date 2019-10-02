package com.caravanas;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.caravanas.Database.DatabaseOpenHelper;
import com.caravanas.Dominio.Entidades.Excursao;
import com.caravanas.Dominio.Repositorio.ExcursaoRepositorio;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;

public class ActCadExcursao extends AppCompatActivity {

    private EditText editNome;
    private EditText editLocalSaida;
    private EditText editData;
    private EditText editTelefone;

    private Button criarExcursaoButton;
    private Button voltarButton;

    private ExcursaoRepositorio excursaoRepositorio;

    private SQLiteDatabase conexao;
    private DatabaseOpenHelper dbOpenHelper;
    private ConstraintLayout layoutContentActCadExcursao;

    private Excursao excursao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cad_excursao);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editNome = (EditText) findViewById(R.id.editNome);
        editLocalSaida = (EditText) findViewById(R.id.editLocalSaida);
        editData = (EditText) findViewById(R.id.editData);
        editTelefone = (EditText) findViewById(R.id.editTelefone);

        criarExcursaoButton = findViewById(R.id.criarExcursaoButton);
        voltarButton = findViewById(R.id.voltarButton);

        layoutContentActCadExcursao = (ConstraintLayout) findViewById(R.id.layoutContentActCadExcursao);

        voltarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(ActCadExcursao.this, ActMain.class);
                startActivity(it);
            }
        });

        criarExcursaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmar();


            }
        });

        criarConexao();


    }

    private void criarConexao(){

        try{

            dbOpenHelper = new DatabaseOpenHelper(this);

            conexao = dbOpenHelper.getWritableDatabase();

            Snackbar.make(layoutContentActCadExcursao, "Conexão criada com sucesso!", Snackbar.LENGTH_SHORT)
                    .setAction("Ok", null).show();

            excursaoRepositorio = new ExcursaoRepositorio(conexao);


        }catch (SQLException ex){

            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("Ok", null);
            dlg.show();

        }
    }


    private void confirmar(){

        excursao = new Excursao();

        if(validarCampos() == false){

            try{

                excursaoRepositorio.inserir(excursao);

                finish();

            }catch (SQLException ex){

                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setTitle("Erro");
                dlg.setMessage(ex.getMessage());
                dlg.setNeutralButton("Ok", null);
                dlg.show();

            }
        }
    }





    private boolean validarCampos(){

        boolean res = false;

        String nome = editNome.getText().toString();
        String localSaida= editLocalSaida.getText().toString();
        String data = editData.getText().toString();
        String telefone = editTelefone.getText().toString();

                                                                          /* Aproveitando o metodo validar para inserir os campos*/
        excursao.nome = nome;
        excursao.saidaLocal = localSaida;
        excursao.data = data;
        excursao.telefone = telefone;

        if(res = isCampoVazio(nome)){
            editNome.requestFocus();
        }
        else
            if (res = isCampoVazio(localSaida)) {
                editLocalSaida.requestFocus();
            }
        else
            if (res = !isTelefoneValido(data)) {
                editData.requestFocus();
            }
        else
            if (res = isCampoVazio(telefone)) {
                editTelefone.requestFocus();
            }

        if(res){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Aviso");
            dlg.setMessage("Há campos invalidos ou em branco!");
            dlg.setNeutralButton("Ok", null);
            dlg.show();
        }
        return res;
    }

    private boolean isCampoVazio(String valor){
        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
        return resultado;
    }

    private boolean isTelefoneValido(String tel){
        boolean resultado = (!isCampoVazio(tel) && Patterns.PHONE.matcher(tel).matches());
        return resultado;
    }


    };

