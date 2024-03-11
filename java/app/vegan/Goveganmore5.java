package app.vegan;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.squareup.picasso.Picasso;

public class Goveganmore5 extends YouTubeBaseActivity {

    TextView helpTv;
    private ImageView beeshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goveganmore5);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ImageButton midiabutton = findViewById(R.id.recomenBtn2);
        helpTv = findViewById(R.id.help);
        beeshow = findViewById(R.id.imageView45);

        WebView webView = findViewById(R.id.video1);
        String video = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/1YnJqoPmR8s\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        webView.loadData(video, "text/html","utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());

        beeshow.setAnimation(AnimationUtils.loadAnimation(Goveganmore5.this, R.anim.slide_slow));


        final Handler handler3 = new Handler();
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                beeshow.clearAnimation();
                beeshow.setVisibility(View.GONE);
            }
        }, 6000 );


        helpTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showHelpDialog();
            }
        });

        midiabutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
                Intent intent1 = new Intent ( Goveganmore5.this, govegandoc.class );
                ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation ( getApplicationContext ( ),
                        R.anim.slide_in_left, R.anim.slide_out_left );
                ActivityCompat.startActivity ( Goveganmore5.this, intent1, actcompat.toBundle ( ) );
            }
        });

        ImageView sparkles = findViewById(R.id.sparkles);

        sparkles.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
                Intent intent1 = new Intent ( Goveganmore5.this, govegandoc.class );
                ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation ( getApplicationContext ( ),
                        R.anim.slide_in_left, R.anim.slide_out_left );
                ActivityCompat.startActivity ( Goveganmore5.this, intent1, actcompat.toBundle ( ) );
            }
        });

        TextView textbutton = findViewById(R.id.textView63);

        textbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
                Intent intent1 = new Intent ( Goveganmore5.this, govegandoc.class );
                ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation ( getApplicationContext ( ),
                        R.anim.slide_in_left, R.anim.slide_out_left );
                ActivityCompat.startActivity ( Goveganmore5.this, intent1, actcompat.toBundle ( ) );
            }
        });


        ImageButton backButton = findViewById(R.id.imageBack);

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent ( Goveganmore5.this, Goveganmore4.class );
                ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation ( getApplicationContext ( ),
                        R.anim.slide_in_right, R.anim.slide_out_right );
                ActivityCompat.startActivity ( Goveganmore5.this, intent1, actcompat.toBundle ( ) );
                finish();
            }
        });

    }

    private void showHelpDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.docbig, null);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setView(view);
        final android.app.AlertDialog dialog = builder.create();
        dialog.show();
        ImageView docimage = view.findViewById(R.id.docimage);
        TextView legendyt = view.findViewById(R.id.legendyt);
        ImageView tap = view.findViewById(R.id.imageView98);
        ImageView tap2 = view.findViewById(R.id.imageVie9);

        tap2.setVisibility(View.GONE);
        tap.setVisibility(View.GONE);
        legendyt.setVisibility(View.VISIBLE);

        try {
            Picasso.get().load(R.drawable.legendas).placeholder(R.drawable.loading).into(docimage);
        } catch (Exception e) {
            docimage.setImageDrawable(ContextCompat.getDrawable(Goveganmore5.this, R.drawable.error));
        }

        docimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}
