package app.vegan;

import static android.content.ContentValues.TAG;
import static app.vegan.R.drawable.error;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import java.util.Objects;

public class Points extends AppCompatActivity {

    private TextView user_points_name, txtPonts, txtPonts2, premioTv, usernameTv;
    private ImageView imgVw1,imgVw2,imgVw3,imgVw4,imgVw5,imgVw6,imgVw7,imgVw8,
            imgVw9,imgVw10,imgVw11,imgVw12,imgVw13,imgVw14,imgVw15,imgVw16,imgVw17,
            imgVw18,imgVw19,imgVw20,imgVw21,imgVw22,imgVw23,imgVw24,imgVw25;
    private TextView btnRescuePont;
    private ImageButton flotBntHomePont, flotBntOrderPont;
    private ImageView imageButton12;
    private FirebaseAuth firebaseAuth;
    private Integer points;
    private String shopUid, pontos;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);

        setRequestedOrientation ( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
        usernameTv = findViewById(R.id.usernameTv);
        premioTv = findViewById(R.id.premioTv);
        txtPonts2 = findViewById(R.id.txtPonts2);
        user_points_name = findViewById(R.id.user_points_name);
        txtPonts = findViewById(R.id.txtPonts);
        imageButton12 = findViewById(R.id.imageButton12);
        imgVw11 = findViewById(R.id.imgVw11);
        imgVw12 = findViewById(R.id.imgVw12);
        imgVw13 = findViewById(R.id.imgVw13);
        imgVw14 = findViewById(R.id.imgVw14);
        imgVw15 = findViewById(R.id.imgVw15);
        imgVw16 = findViewById(R.id.imgVw16);
        imgVw17 = findViewById(R.id.imgVw17);
        imgVw18 = findViewById(R.id.imgVw18);
        imgVw19 = findViewById(R.id.imgVw19);
        imgVw20 = findViewById(R.id.imgVw20);
        btnRescuePont = findViewById(R.id.btnRescuePont);
        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        Intent data = getIntent();
        String brand = data.getStringExtra("shopName");
        shopUid = data.getStringExtra("shopUid");

        txtPonts.setText(brand);

        loadInfo();

        imageButton12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });


    }

    private void loadInfo() {
        DocumentReference docRef = fStore.collection("Points " + shopUid).document("Fidelidade");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String expira = document.getString("expira");
                        String premio = document.getString("premio");
                        String regras = document.getString("regras");

                        premioTv.setText(premio);
                        user_points_name.setText(expira);
                        txtPonts2.setText(regras);

                        String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                        DocumentReference docRef3 = fStore.collection("Fidelidade " + userID).document(shopUid);
                        docRef3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "points task "+pontos);
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        pontos = document.getString("pontos");
                                        Log.d(TAG, "points document "+pontos);

                                        switch (Objects.requireNonNull(pontos)){
                                            case "1":
                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw11);
                                                }
                                                catch (Exception e) {
                                                    imgVw11.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }
                                                imgVw11.setVisibility(View.VISIBLE);

                                                break;

                                            case "2":
                                                Log.d(TAG, "points switch "+pontos);
                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw11);
                                                }
                                                catch (Exception e) {
                                                    imgVw11.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw12);
                                                }
                                                catch (Exception e) {
                                                    imgVw12.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }
                                                imgVw11.setVisibility(View.VISIBLE);
                                                imgVw12.setVisibility(View.VISIBLE);

                                                break;

                                            case "3":
                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw11);
                                                }
                                                catch (Exception e) {
                                                    imgVw11.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw12);
                                                }
                                                catch (Exception e) {
                                                    imgVw12.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw13);
                                                }
                                                catch (Exception e) {
                                                    imgVw13.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }
                                                imgVw11.setVisibility(View.VISIBLE);
                                                imgVw12.setVisibility(View.VISIBLE);
                                                imgVw13.setVisibility(View.VISIBLE);

                                                break;

                                            case "4":
                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw11);
                                                }
                                                catch (Exception e) {
                                                    imgVw11.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw12);
                                                }
                                                catch (Exception e) {
                                                    imgVw12.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw13);
                                                }
                                                catch (Exception e) {
                                                    imgVw13.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw14);
                                                }
                                                catch (Exception e) {
                                                    imgVw14.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                imgVw11.setVisibility(View.VISIBLE);
                                                imgVw12.setVisibility(View.VISIBLE);
                                                imgVw13.setVisibility(View.VISIBLE);
                                                imgVw14.setVisibility(View.VISIBLE);

                                                break;

                                            case "5":
                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw11);
                                                }
                                                catch (Exception e) {
                                                    imgVw11.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw12);
                                                }
                                                catch (Exception e) {
                                                    imgVw12.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw13);
                                                }
                                                catch (Exception e) {
                                                    imgVw13.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw14);
                                                }
                                                catch (Exception e) {
                                                    imgVw14.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw15);
                                                }
                                                catch (Exception e) {
                                                    imgVw15.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }
                                                imgVw11.setVisibility(View.VISIBLE);
                                                imgVw12.setVisibility(View.VISIBLE);
                                                imgVw13.setVisibility(View.VISIBLE);
                                                imgVw14.setVisibility(View.VISIBLE);
                                                imgVw15.setVisibility(View.VISIBLE);

                                                break;

                                            case "6":
                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw11);
                                                }
                                                catch (Exception e) {
                                                    imgVw11.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw12);
                                                }
                                                catch (Exception e) {
                                                    imgVw12.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw13);
                                                }
                                                catch (Exception e) {
                                                    imgVw13.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw14);
                                                }
                                                catch (Exception e) {
                                                    imgVw14.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw15);
                                                }
                                                catch (Exception e) {
                                                    imgVw15.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw16);
                                                }
                                                catch (Exception e) {
                                                    imgVw16.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }
                                                imgVw11.setVisibility(View.VISIBLE);
                                                imgVw12.setVisibility(View.VISIBLE);
                                                imgVw13.setVisibility(View.VISIBLE);
                                                imgVw14.setVisibility(View.VISIBLE);
                                                imgVw15.setVisibility(View.VISIBLE);
                                                imgVw16.setVisibility(View.VISIBLE);

                                                break;

                                            case "7":
                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw11);
                                                }
                                                catch (Exception e) {
                                                    imgVw11.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw12);
                                                }
                                                catch (Exception e) {
                                                    imgVw12.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw13);
                                                }
                                                catch (Exception e) {
                                                    imgVw13.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw14);
                                                }
                                                catch (Exception e) {
                                                    imgVw14.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw15);
                                                }
                                                catch (Exception e) {
                                                    imgVw15.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder(R.drawable.loading).into(imgVw16);
                                                }
                                                catch (Exception e) {
                                                    imgVw16.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder((R.drawable.loading)).into(imgVw17);
                                                }
                                                catch (Exception e) {
                                                    imgVw17.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }
                                                imgVw11.setVisibility(View.VISIBLE);
                                                imgVw12.setVisibility(View.VISIBLE);
                                                imgVw13.setVisibility(View.VISIBLE);
                                                imgVw14.setVisibility(View.VISIBLE);
                                                imgVw15.setVisibility(View.VISIBLE);
                                                imgVw16.setVisibility(View.VISIBLE);
                                                imgVw17.setVisibility(View.VISIBLE);

                                                break;

                                            case"8":
                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder((R.drawable.loading)).into(imgVw11);
                                                }
                                                catch (Exception e) {
                                                    imgVw11.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder((R.drawable.loading)).into(imgVw12);
                                                }
                                                catch (Exception e) {
                                                    imgVw12.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder((R.drawable.loading)).into(imgVw13);
                                                }
                                                catch (Exception e) {
                                                    imgVw13.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder((R.drawable.loading)).into(imgVw14);
                                                }
                                                catch (Exception e) {
                                                    imgVw14.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder((R.drawable.loading)).into(imgVw15);
                                                }
                                                catch (Exception e) {
                                                    imgVw15.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder((R.drawable.loading)).into(imgVw16);
                                                }
                                                catch (Exception e) {
                                                    imgVw16.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder((R.drawable.loading)).into(imgVw17);
                                                }
                                                catch (Exception e) {
                                                    imgVw17.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder((R.drawable.loading)).into(imgVw18);
                                                }
                                                catch (Exception e) {
                                                    imgVw18.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }
                                                imgVw11.setVisibility(View.VISIBLE);
                                                imgVw12.setVisibility(View.VISIBLE);
                                                imgVw13.setVisibility(View.VISIBLE);
                                                imgVw14.setVisibility(View.VISIBLE);
                                                imgVw15.setVisibility(View.VISIBLE);
                                                imgVw16.setVisibility(View.VISIBLE);
                                                imgVw17.setVisibility(View.VISIBLE);
                                                imgVw18.setVisibility(View.VISIBLE);

                                                break;

                                            case "9":
                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder((R.drawable.loading)).into(imgVw11);
                                                }
                                                catch (Exception e) {
                                                    imgVw11.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder((R.drawable.loading)).into(imgVw12);
                                                }
                                                catch (Exception e) {
                                                    imgVw12.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder((R.drawable.loading)).into(imgVw13);
                                                }
                                                catch (Exception e) {
                                                    imgVw13.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder((R.drawable.loading)).into(imgVw14);
                                                }
                                                catch (Exception e) {
                                                    imgVw14.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder((R.drawable.loading)).into(imgVw15);
                                                }
                                                catch (Exception e) {
                                                    imgVw15.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder((R.drawable.loading)).into(imgVw16);
                                                }
                                                catch (Exception e) {
                                                    imgVw16.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder((R.drawable.loading)).into(imgVw17);
                                                }
                                                catch (Exception e) {
                                                    imgVw17.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder((R.drawable.loading)).into(imgVw18);
                                                }
                                                catch (Exception e) {
                                                    imgVw18.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                try {
                                                    Picasso.get().load(R.drawable.veganoslogoicon).placeholder((R.drawable.loading)).into(imgVw19);
                                                }
                                                catch (Exception e) {
                                                    imgVw19.setImageDrawable(ContextCompat.getDrawable(Points.this, error ));
                                                }

                                                imgVw11.setVisibility(View.VISIBLE);
                                                imgVw12.setVisibility(View.VISIBLE);
                                                imgVw13.setVisibility(View.VISIBLE);
                                                imgVw14.setVisibility(View.VISIBLE);
                                                imgVw15.setVisibility(View.VISIBLE);
                                                imgVw16.setVisibility(View.VISIBLE);
                                                imgVw17.setVisibility(View.VISIBLE);
                                                imgVw18.setVisibility(View.VISIBLE);
                                                imgVw19.setVisibility(View.VISIBLE);

                                                break;

                                        }

                                        points = Integer.parseInt(pontos);
                                    }
                                }
                            }
                        });


                    }
                }
            }
        });

        String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
            DocumentReference docRef2 = fStore.collection("Users").document(userID);
            docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String name = document.getString("fName");
                            usernameTv.setText(name);
                        }
                    }
                }
            });


    }

    @Override
    public void onBackPressed ( ) {
        finish ( );
    }

}
