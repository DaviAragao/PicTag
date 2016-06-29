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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int NEW_PICTAG_REQUEST = 1;

    private TableLayout tblTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(ouvidorCamera);

        tblTags = (TableLayout) findViewById(R.id.tblTags);

        PicTagDAO tDAO = new PicTagDAO(this);
        tDAO.createTag("#davi");
        tDAO.createTag("#aragao");
        atualizarTags();
    }

    private void atualizarTags() {
        PicTagDAO tDAO = new PicTagDAO(getApplicationContext());
        List<String> tags = tDAO.getAllTags();

        //index out of bounds...
        for (int i = 0; i < tags.size(); i += 2)
            criarTag(tags.get(i), tags.get(i + 1));
    }

    private void criarTag(String tag1, String tag2) {
        LayoutInflater inflador = getLayoutInflater();

        View novaTag = inflador.inflate(R.layout.layout_tag, null);

        Button btnTag1 = (Button) novaTag.findViewById(R.id.btnTag1);
        btnTag1.setText(tag1);
        //btnTag1.setOnClickListener(ouvidorTag1);

        Button btnTag2 = (Button) novaTag.findViewById(R.id.btnTag2);
        btnTag2.setText(tag2);
       // btnTag2.setOnClickListener(ouvidorTag2);

        tblTags.addView(novaTag);
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
            try {
                if ((ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                     ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

                    Log.i("PicTag", "Solicitando permissão para escrever");
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

                    //return;
                }

                if ((ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                     ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){
                    Log.i("PicTag", "Solicitando permissão para ler");
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                }

                startActivityForResult(new Intent(MainActivity.this, SaveActivity.class), NEW_PICTAG_REQUEST);
            }
            catch (Exception e){
                Snackbar.make(tblTags, "Erro ao obter pemissão", Snackbar.LENGTH_LONG).show();
            }
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == NEW_PICTAG_REQUEST && resultCode == RESULT_OK) {
            //ATUALIZAR LISTA DE TAGS
        }
    }
}
