package app.vegan;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class ActivistDetails extends AppCompatActivity {

    private TextView title, linksvb, linkmfa, linkAnimalSente, linkAmorQueSalva, linkAra, linkSurge, linkVr, linkVoz;
    private CardView floatingButton;
    private ImageView imageButtonBack, share, share2, share3, share4, share5, share7, share8, share9;
    private String foto;
    private LinearLayout llayout,llayout2, llayout3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activist_details);

        linkSurge = findViewById(R.id.linkOra);
        linkVr = findViewById(R.id.linksAra);
        linkVoz = findViewById(R.id.linksAsara);
        linkAnimalSente = findViewById(R.id.linkAnimalSente);
        linkAmorQueSalva = findViewById(R.id.linkAmorQueSalva);
        linkAra = findViewById(R.id.linkAra);
        linksvb = findViewById(R.id.linksvb);
        linkmfa = findViewById(R.id.linkmfa);
        llayout = findViewById(R.id.llayout);
        llayout2 = findViewById(R.id.llayout2);
        llayout3 = findViewById(R.id.llayout3);
        share = findViewById(R.id.share);
        share2 = findViewById(R.id.share2);
        share3 = findViewById(R.id.share3);
        share4 = findViewById(R.id.share4);
        share5 = findViewById(R.id.share5);
        share7 = findViewById(R.id.share7);
        share8 = findViewById(R.id.share8);
        share9 = findViewById(R.id.share9);

        title = findViewById(R.id.textView66);
        floatingButton = findViewById(R.id.floatingButton);
        imageButtonBack = findViewById(R.id.imageView40);
        testUserCurrent();

        Intent data = getIntent();
        String typeActivism = data.getStringExtra("typeActivism");

        if (Objects.equals(typeActivism, "Compartilhar")){
            llayout2.setVisibility(GONE);
            llayout3.setVisibility(GONE);
            llayout.setVisibility(VISIBLE);
            title.setText(R.string.share);
        }
        if (Objects.equals(typeActivism, "undersigned")){
            title.setText(R.string.undersigned);
        }
        if (Objects.equals(typeActivism, "reach")){
            title.setText(R.string.reach);
        }
        if (Objects.equals(typeActivism, "donate")){
            title.setText(R.string.donate);
            llayout2.setVisibility(GONE);
            llayout3.setVisibility(VISIBLE);
            llayout.setVisibility(GONE);
        }
        if (Objects.equals(typeActivism, "denounce")){
            title.setText(R.string.denounce);
        }
        if (Objects.equals(typeActivism, "Grupo")){
            title.setText(R.string.group);
            loadAllStreet();
        }
        floatingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivistDetails.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        imageButtonBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        share.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                foto = "1";
                checarPermissao();
            }
        });

        share2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                foto = "2";
                checarPermissao();
            }
        });

        share3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                foto = "3";
                checarPermissao();
            }
        });

        share4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                foto = "4";
                checarPermissao();            }
        });

        share5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                foto = "5";
                checarPermissao();
            }
        });

        share7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                foto = "7";
                checarPermissao();
            }
        });

        share8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                foto = "8";
                checarPermissao();
            }
        });

        share9.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                foto = "9";
                checarPermissao();
            }
        });

        linkmfa.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mercyforanimals.org.br/junte-se-a-nos/"));
                startActivity(browserIntent);
            }
        });

        linksvb.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.svb.org.br/junte-se/voluntarios"));
                startActivity(browserIntent);            }
        });

        linkAra.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://linkr.bio/bufalas_de_brotas_oficial"));
                startActivity(browserIntent);
            }
        });

        linkAnimalSente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.santuarioanimalsente.com"));
                startActivity(browserIntent);
            }
        });

        linkAmorQueSalva.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://santuarioamorquesalva.org"));
                startActivity(browserIntent);            }
        });

        linkSurge.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.surgesanctuary.org"));
                startActivity(browserIntent);            }
        });

        linkVr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://svr.org.br"));
                startActivity(browserIntent);            }
        });

        linkVoz.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.santuariovozanimal.com.br"));
                startActivity(browserIntent);            }
        });

    }

    private static final int SOLICITAR_PERMISSAO = 1;
    private void checarPermissao(){

        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, SOLICITAR_PERMISSAO);
        } else {
            sharedImage();
        }
    }

    private void sharedImage(){
        if (Objects.equals(foto, "1")) {
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.repo);
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), b, getString(R.string.activist), null);
            Uri imageUri =  Uri.parse(path);
            share.putExtra(Intent.EXTRA_STREAM, imageUri);
            startActivity(Intent.createChooser(share, getString(R.string.selecione_o_tipo)));
        }
        if (Objects.equals(foto, "2")) {
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.repo7);
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), b, getString(R.string.activist), null);
            Uri imageUri =  Uri.parse(path);
            share.putExtra(Intent.EXTRA_STREAM, imageUri);
            startActivity(Intent.createChooser(share, getString(R.string.selecione_o_tipo)));
        }
        if (Objects.equals(foto, "3")) {
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.repo2);
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), b, getString(R.string.activist), null);
            Uri imageUri =  Uri.parse(path);
            share.putExtra(Intent.EXTRA_STREAM, imageUri);
            startActivity(Intent.createChooser(share, getString(R.string.selecione_o_tipo)));
        }
        if (Objects.equals(foto, "4")) {
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.repo4);
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), b, getString(R.string.activist), null);
            Uri imageUri =  Uri.parse(path);
            share.putExtra(Intent.EXTRA_STREAM, imageUri);
            startActivity(Intent.createChooser(share, getString(R.string.selecione_o_tipo)));
        }
        if (Objects.equals(foto, "5")) {
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.repo3);
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), b, getString(R.string.activist), null);
            Uri imageUri =  Uri.parse(path);
            share.putExtra(Intent.EXTRA_STREAM, imageUri);
            startActivity(Intent.createChooser(share, getString(R.string.selecione_o_tipo)));
        }

        if (Objects.equals(foto, "7")) {
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.repo5);
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), b, getString(R.string.activist), null);
            Uri imageUri =  Uri.parse(path);
            share.putExtra(Intent.EXTRA_STREAM, imageUri);
            startActivity(Intent.createChooser(share, getString(R.string.selecione_o_tipo)));
        }
        if (Objects.equals(foto, "8")) {
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.repo6);
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), b, getString(R.string.activist), null);
            Uri imageUri =  Uri.parse(path);
            share.putExtra(Intent.EXTRA_STREAM, imageUri);
            startActivity(Intent.createChooser(share, getString(R.string.selecione_o_tipo)));
        }
        if (Objects.equals(foto, "9")) {
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.repost);
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), b, getString(R.string.activist), null);
            Uri imageUri =  Uri.parse(path);
            share.putExtra(Intent.EXTRA_STREAM, imageUri);
            startActivity(Intent.createChooser(share, getString(R.string.selecione_o_tipo)));
        }

    }

    public void testUserCurrent() {
        floatingButton.setVisibility(VISIBLE);
    }

    private void loadAllStreet() {
        llayout3.setVisibility(GONE);
        llayout.setVisibility(GONE);
        llayout2.setVisibility(VISIBLE);
    }

    public void onBackPressed ( ) {
        finish ( );
    }
}
