package br.ifsp.btv.ads.pdmde16.pictag;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

public class SaveActivity extends AppCompatActivity {

    private static final int REQUEST_FOTO = 1;

    private ImageView img;
    private MultiAutoCompleteTextView tvTags;
    private FloatingActionButton btnSave;

    private PicTagDAO dao;

    public String localFoto; //usado para armazenar o local onde se encontra a FOTO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        dao = new PicTagDAO(this);

        img = (ImageView) findViewById(R.id.imageView);
        tvTags = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoTags);
        btnSave = (FloatingActionButton) findViewById(R.id.savePicFab);

        btnSave.setOnClickListener(ouvidorSalvar);

        abrirCamera();

        List<String> tags = dao.getAllTags();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tags);
        tvTags.setAdapter(arrayAdapter);
        tvTags.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        tvTags.setThreshold(1);
    }

    public void abrirCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_FOTO);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_FOTO && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            img.setImageBitmap(photo);
            // Chame este método pra obter a URI da imagem
            Uri uri = getImageUri(this, photo);
            // Em seguida chame este método para obter o caminho do arquivo
            getRealPathFromURI(uri);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //Transforma o bitmap em jpeg
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        //Salva a imagem e retorna o caminho da imagem
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        localFoto = cursor.getString(idx);  //Armazena o local da imagem
        return localFoto;
    }

    View.OnClickListener ouvidorSalvar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if ((!localFoto.isEmpty()) && (!tvTags.getText().toString().isEmpty())){
                List<String> lstTags = Arrays.asList(tvTags.getText().toString().split("#\\w+"));
                dao.createCompletePicTag(localFoto, lstTags);
            }
        }
    };
}
