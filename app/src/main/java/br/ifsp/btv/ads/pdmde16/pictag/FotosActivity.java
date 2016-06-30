package br.ifsp.btv.ads.pdmde16.pictag;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Map;

public class FotosActivity extends AppCompatActivity {

    private String tagDefault;
    private PicTagDAO dao;
    private Map<String, String> allPicTags;
    private TableLayout tblPicTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);

        tagDefault = getIntent().getExtras().getString("TAG_DEFAULT");
        dao = new PicTagDAO(this);

        tblPicTags = (TableLayout) findViewById(R.id.tblImageTag);
    }

    private void carregaImagensTags(){
        allPicTags = dao.getPicTagsByTagName(tagDefault);

        //Para cada par de imagem e string com tags
        for (Map.Entry<String, String> picTag: allPicTags.entrySet()){
            inflarPicTag(picTag.getKey(), picTag.getValue());
        }
    }

    //Parametro pic contem o caminho da foto
    //Parametro tags contem um string com todas as tags
    private void inflarPicTag(String pic, String tags){
        LayoutInflater inflador = getLayoutInflater();

        //Infla um layout contendo o imageview para exibir a foto
        // e um text view para exibir as tags
        TableRow rowNova = (TableRow) inflador.inflate(R.layout.layout_tag, null);

        //Encontra o imageview no layout inflado
        ImageView imgPic = (ImageView) rowNova.findViewById(R.id.imgPic);
        //Imagem redimensionada proporcionamente ao centro
        imgPic.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        //Carrega a foto a partir do caminho da foto(paramentro pic)
        imgPic.setImageBitmap(BitmapFactory.decodeFile(pic));

        //Encontra o text view dentro do layout inflado
        TextView lblTags = (TextView) rowNova.findViewById(R.id.lblTags);
        //Seta o texto com as tags no text view
        lblTags.setText(tags);

        //Adiciona o layout inflado em tela
        tblPicTags.addView(rowNova);
    }
}
