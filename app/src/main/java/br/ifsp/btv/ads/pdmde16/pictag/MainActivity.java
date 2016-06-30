package br.ifsp.btv.ads.pdmde16.pictag;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int NEW_PICTAG_REQUEST = 1;

    private TableLayout tblTags;
    private PicTagDAO dao;
    private List<String> lstTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(ouvidorCamera);

        tblTags = (TableLayout) findViewById(R.id.tblTags);

        lstTags = new ArrayList<>();
        dao = new PicTagDAO(this);
        //dao.createTag("#davi");
        //dao.createTag("#aragao");
        //atualizarTags();
        criarTodasTags();
    }

    private void criarTodasTags(){
        List<String> todasTags = dao.getAllTags();

        //Este método já
        atualizarTags(todasTags);
    }

    //Método responsável por receber uma lista de tags
    //e adiciona um botao para esta lista de tags se necessario
    private void atualizarTags(List<String> tagsNovas) {
        List<String> tagsAdicionadas = new ArrayList<>();

        //Faz um laço nas tags recebidas
        for (String tag: tagsNovas){
            //Verifica se a tag nova deve ser adicionada e se esta tag já não será adicionada
            if ((lstTags.indexOf(tag) == -1) && (tagsAdicionadas.indexOf(tag) == -1)){
                tagsAdicionadas.add(tag);
            }
        }

        lstTags.addAll(tagsAdicionadas);

        for (int i = 0; i < tagsAdicionadas.size(); i += 2) {
            //Se a posicao i+1 = size entao a posicao i+1 nao existe na lista
            if (tagsAdicionadas.size() == i+1)
                criarTags(tagsAdicionadas.get(i), null);
            else
                criarTags(tagsAdicionadas.get(i), tagsAdicionadas.get(i + 1));
        }
    }

    private void criarTags(String tag1, String tag2) {
        LayoutInflater inflador = getLayoutInflater();

        //Se a última linha está com o segundo botão invisível
        if ((tblTags.getChildCount() > 0) && (tblTags.getChildAt(tblTags.getChildCount()-1).findViewById(R.id.btnTag2).getVisibility() == View.GONE)) {
            View tableRowAnt = tblTags.getChildAt(tblTags.getChildCount()-1);

            Button btnTag2Ant = (Button) tableRowAnt.findViewById(R.id.btnTag2);
            btnTag2Ant.setText(tag1);
            btnTag2Ant.setVisibility(View.VISIBLE);
            btnTag2Ant.setOnClickListener(ouvidorBtnTag);

            tag1 = tag2;
            tag2 = null;
        }

        if (tag1 != null) {
            View novaTag = inflador.inflate(R.layout.layout_row_tag, null);

            Button btnTag1 = (Button) novaTag.findViewById(R.id.btnTag1);
            btnTag1.setText(tag1);
            btnTag1.setVisibility(View.VISIBLE);
            btnTag1.setOnClickListener(ouvidorBtnTag);

            Button btnTag2 = (Button) novaTag.findViewById(R.id.btnTag2);

            if (tag2 != null) {
                btnTag2.setText(tag2);
                btnTag2.setVisibility(View.VISIBLE);
                btnTag2.setOnClickListener(ouvidorBtnTag);
            }
            else
                btnTag2.setVisibility(View.GONE);

            tblTags.addView(novaTag);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener ouvidorCamera = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tirarNovasFotos();
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == NEW_PICTAG_REQUEST && resultCode == RESULT_OK) {
            List<String> tagsFotoNova = data.getExtras().getStringArrayList("TAGS");

            atualizarTags(tagsFotoNova);
        }
    }

    private void tirarNovasFotos(){
        if (havePermission())
            startActivityForResult(new Intent(MainActivity.this, SaveActivity.class), NEW_PICTAG_REQUEST);
    }

    private boolean havePermission(){
        try {
            if ((ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

            if ((ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            return true;
        }
        catch (Exception e){
            Snackbar.make(tblTags, "Erro ao obter pemissão", Snackbar.LENGTH_LONG).show();
            return false;
        }
    }

    View.OnClickListener ouvidorBtnTag = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button btn = (Button) v;
            String tag = (String) btn.getText();

            Intent intent = new Intent(MainActivity.this, FotosActivity.class);
            intent.putExtra("TAG_DEFAULT", tag);

            startActivity(intent);
        }
    };
}
