package br.ifsp.btv.ads.pdmde16.pictag;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class SaveActivity extends AppCompatActivity {

    private static final int REQUEST_FOTO = 1;

    private ImageView img;
    private Bitmap bitmap;
    public String localFoto; //usado para armazenar o local onde se encontra a FOTO
    public Boolean mudarFoto; //chavear imagem antiga e foto

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        img = (ImageView) findViewById(R.id.imageView);

        abrirCamera();
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

            localFoto = uri.getPath();
            // Em seguida chame este método para obter o caminho do arquivo
            //File file = new File(getRealPathFromURI(uri));
            mudarFoto = true; //controle de imagens
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

    //private void

}
