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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class ActMain extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView lstDados;
    private ConstraintLayout layoutContentMain;

    private SQLiteDatabase conexao;

    private DatabaseOpenHelper dbOpenHelper;

    private ExcursaoRepositorio excursaoRepositorio;

    private ExcursaoAdapter excursaoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        lstDados = (RecyclerView) findViewById(R.id.lstDados);

        layoutContentMain = (ConstraintLayout) findViewById(R.id.layoutContentMain);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(ActMain.this, ActCadExcursao.class);
                startActivity(it);
            }
        });

        criarConexao();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lstDados.setLayoutManager(linearLayoutManager);

        excursaoRepositorio = new ExcursaoRepositorio(conexao);

        List<Excursao> dados = excursaoRepositorio.buscarTodos();

        excursaoAdapter = new ExcursaoAdapter(dados);

        lstDados.setAdapter(excursaoAdapter);


    }

    private void criarConexao(){

        try{

            dbOpenHelper = new DatabaseOpenHelper(this);

            conexao = dbOpenHelper.getWritableDatabase();

            Snackbar.make(layoutContentMain, "Conexão criada com sucesso!", Snackbar.LENGTH_SHORT)
                    .setAction("Ok", null).show();


        }catch (SQLException ex){

            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("Ok", null);
            dlg.show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
