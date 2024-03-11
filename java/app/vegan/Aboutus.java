package app.vegan;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import cn.carbs.android.library.BuildConfig;

public class Aboutus extends AppCompatActivity {

    private CardView floatingButton;
    FirebaseAuth fAuth;
    private ImageView inviteIv, insta, youtube, sendIv;
    private TextView inviteTv, poli, termos;
    private LinearLayout inviteLl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        setRequestedOrientation ( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        poli = findViewById(R.id.poli);
        termos = findViewById(R.id.warning);
        sendIv = findViewById(R.id.send);
        ImageView fButton = findViewById(R.id.flotBntExit2);
        ImageView garbage = findViewById(R.id.clean);
        EditText box = findViewById(R.id.boxdialogue);
        floatingButton = findViewById(R.id.floatingButton);
        inviteTv = findViewById(R.id.textView73);
        inviteIv = findViewById(R.id.imageView102);
        inviteLl = findViewById(R.id.llinvite);
        insta = findViewById(R.id.insta);
        youtube = findViewById(R.id.youtube);

        fAuth = FirebaseAuth.getInstance();
        testUserCurrent();

        poli.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(Aboutus.this);
                builder.setTitle(getString(R.string.politicadegarantia));
                builder.setMessage(getString(R.string.longapolitica));

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        termos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(Aboutus.this);
                builder.setTitle(getString(R.string.termosdes));
                builder.setMessage(getString(R.string.longatermos));

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        fButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        garbage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                box.setText("");
            }
        });

        sendIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:veganosapp@gmail.com")));
            }
        });

        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/veganappbr/"));
                startActivity(browserIntent);
            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UC78kRWYBacW2f6zLWsQSBFw"));
                startActivity(browserIntent);
            }
        });

        inviteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                sharedInvitation();
            }
        });

        inviteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedInvitation();

            }
        });

        inviteLl.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                sharedInvitation();
            }
        });

    }
    public void testUserCurrent() {
        if ( fAuth.getCurrentUser ( ) != null ) {
            floatingButton.setVisibility(GONE);
        } else {
            floatingButton.setVisibility(VISIBLE);
        }
    }

    public void onBackPressed ( ) {
        finish ( );
    }

    private void sharedInvitation(){
        String escolha = getString(R.string.escolhaum);
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Vegan app");
            String shareMessage= this.getString(R.string.shareapp);
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, escolha));
        } catch(Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
