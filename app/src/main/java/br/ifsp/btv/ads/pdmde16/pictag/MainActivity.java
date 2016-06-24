package br.ifsp.btv.ads.pdmde16.pictag;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TableLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TableLayout tblTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TagDAO tDAO = new TagDAO(getApplicationContext());
        tDAO.createTag("#davi");
        tDAO.createTag("#aragao");
        atualizarTags();
    }

    private void atualizarTags() {
        TagDAO tDAO = new TagDAO(getApplicationContext());
        List<String> tags = tDAO.getAll();

        for (int i = 0; i < tags.size(); i += 2)
            criarTag(tags.get(i), tags.get(i + 1));
    }

    private void criarTag(String tag1, String tag2) {
        LayoutInflater inflador = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        View novaTag = inflador.inflate(R.layout.layout_tag, null);

        Button btnTag1 = (Button) novaTag.findViewById(R.id.btnTag1);
        btnTag1.setText(tag1);
        //btnTag1.setOnClickListener(ouvidorTag1);

        Button btnTag2 = (Button) novaTag.findViewById(R.id.btnTag2);
        btnTag2.setText(tag2);
       // btnTag2.setOnClickListener(ouvidorTag2);
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
}
