package br.ifsp.btv.ads.pdmde16.pictag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FotosActivity extends AppCompatActivity {

    private String tagDefault;
    private PicTagDAO dao;
    private

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);

        tagDefault = getIntent().getExtras().getString("TAG_DEFAULT");
        dao = new PicTagDAO(this);


        try {
            Map<String, List<String>> map = new LinkedHashMap<>();

            //map.entrySet().iterator().

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregaImagensTags(){

    }
}
