package app.vegan;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Objects;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class ActivismActivity extends AppCompatActivity {

    CardView firstcard;
    ViewGroup rootView;
    Drawable windowBackground;
    BlurView general_blur;
    ImageView share, undersigned, reach, donate, denounce, street, imageButtonBack;
    private FloatingActionButton faBplus;
    FirebaseAuth fAuth;
    private CardView floatingButton;
    private LinearLayout sharell, donatell, groupll;
    private TextView shareTv, donateTv, groupTv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activism);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        View decorView = getWindow().getDecorView();
        rootView = decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();

        shareTv = findViewById(R.id.shareTv);
        donateTv = findViewById(R.id.donateTv);
        groupTv = findViewById(R.id.groupTv);
        sharell = findViewById(R.id.sharell);
        donatell = findViewById(R.id.donatell);
        groupll = findViewById(R.id.groupll);
        floatingButton = findViewById(R.id.floatingButton);
        firstcard = findViewById(R.id.firstcard);
        general_blur = findViewById(R.id.tab_blur);
        share = findViewById(R.id.shareIv);
        undersigned = findViewById(R.id.undersignedIv);
        reach = findViewById(R.id.reachIv);
        donate = findViewById(R.id.donateIv);
        denounce = findViewById(R.id.denounceIv);
        street = findViewById(R.id.streetIv);
        imageButtonBack = findViewById(R.id.imageButtonBack);

        addBlur(general_blur);

        fAuth = FirebaseAuth.getInstance();
        testUserCurrent();

        Intent data = getIntent();
        String userId = data.getStringExtra("userId");

        imageButtonBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        share.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(ActivismActivity.this, ActivistDetails.class);
                intentLoadNewActivity.putExtra("typeActivism", "Compartilhar");
                startActivity(intentLoadNewActivity);
            }
        });

        sharell.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(ActivismActivity.this, ActivistDetails.class);
                intentLoadNewActivity.putExtra("typeActivism", "Compartilhar");
                startActivity(intentLoadNewActivity);
            }
        });

        shareTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(ActivismActivity.this, ActivistDetails.class);
                intentLoadNewActivity.putExtra("typeActivism", "Compartilhar");
                startActivity(intentLoadNewActivity);
            }
        });


        undersigned.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivismActivity.this, R.string.embreve, Toast.LENGTH_SHORT).show();
            }
        });

        donate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(ActivismActivity.this, ActivistDetails.class);
                intentLoadNewActivity.putExtra("typeActivism", "donate");
                startActivity(intentLoadNewActivity);
            }
        });

        donatell.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(ActivismActivity.this, ActivistDetails.class);
                intentLoadNewActivity.putExtra("typeActivism", "Doar");
                startActivity(intentLoadNewActivity);
            }
        });

        donateTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(ActivismActivity.this, ActivistDetails.class);
                intentLoadNewActivity.putExtra("typeActivism", "Doar");
                startActivity(intentLoadNewActivity);
            }
        });

        denounce.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivismActivity.this, R.string.embreve, Toast.LENGTH_SHORT).show();
            }
        });

        street.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(ActivismActivity.this, ActivistDetails.class);
                intentLoadNewActivity.putExtra("typeActivism", "Grupo");
                startActivity(intentLoadNewActivity);
            }
        });

        groupll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(ActivismActivity.this, ActivistDetails.class);
                intentLoadNewActivity.putExtra("typeActivism", "Grupo");
                startActivity(intentLoadNewActivity);
            }
        });

        groupTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(ActivismActivity.this, ActivistDetails.class);
                intentLoadNewActivity.putExtra("typeActivism", "Grupo");
                startActivity(intentLoadNewActivity);
            }
        });

        reach.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivismActivity.this, R.string.embreve, Toast.LENGTH_SHORT).show();
            }
        });

        floatingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivismActivity.this, LoginActivity.class);
                startActivity(intent);
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

    private void addBlur(BlurView blurView) {
        blurView.setupWith(rootView).setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new RenderScriptBlur((this)))
                .setBlurRadius(20f)
                .setBlurAutoUpdate(true)
                .setHasFixedTransformationMatrix(true);
    }

    public void onBackPressed ( ) {
        finish ( );
    }
}
