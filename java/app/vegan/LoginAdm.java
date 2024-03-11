package app.vegan;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Objects;

public class LoginAdm extends AppCompatActivity {

 ImageButton imageButton8, imageButton14;
    ImageButton imageButton, imageButton2;
    TextView ForgotTextLink, textView21;
    EditText edtEmail, edtSenha;
    ProgressBar progressBar3;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_adm);
        setRequestedOrientation ( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        progressBar3 = findViewById(R.id.progressBar3);
        imageButton = findViewById(R.id.imageButton);
        imageButton2 = findViewById(R.id.imageButton2);
        ForgotTextLink = findViewById(R.id.ForgotTextLink);
        textView21 = findViewById(R.id.textView21);

        imageButton14= findViewById(R.id.imageButton14);
        imageButton14.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(LoginAdm.this, RegisterSellerActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });

        imageButton8 = findViewById(R.id.imageButton8);
        imageButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), Outros.class));
            }
        });

        fAuth = FirebaseAuth.getInstance();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edtEmail.getText().toString().trim();
                String password = edtSenha.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    edtEmail.setError(getString(R.string.umoumaiscam));
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    edtSenha.setError(getString(R.string.umoumaiscam));
                    return;
                }
                if(password.length() < 6){
                    edtSenha.setError(getString(R.string.senhapequena));
                    return;
                }
                progressBar3.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginAdm.this, R.string.entroucarr, Toast.LENGTH_SHORT).show();

                            FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                            rootRef.collection("Adm's").document(Objects.requireNonNull(fAuth.getUid())).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();

                                        if (document.exists()) {
                                            String sector = document.getString("type");

                                            if (Objects.equals(sector, "Services")){
                                                Intent intent = new Intent ( LoginAdm.this, AdmServicesActivity.class );
                                                startVibrate ( 90 );
                                                Log.d(TAG, Objects.requireNonNull(fAuth.getCurrentUser()).getUid());
                                                ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation ( getApplicationContext ( ),
                                                        R.anim.slide_in_left, R.anim.slide_out_left );
                                                ActivityCompat.startActivity ( LoginAdm.this, intent, actcompat.toBundle ( ) );
                                                progressBar3.setVisibility(View.GONE);
                                                finish();
                                            }
                                            if (Objects.equals(sector, "Diet")) {
                                                Intent intent = new Intent(LoginAdm.this, AdmHome.class);
                                                startVibrate(90);
                                                ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                                                        R.anim.slide_in_left, R.anim.slide_out_left);
                                                ActivityCompat.startActivity(LoginAdm.this, intent, actcompat.toBundle());
                                                progressBar3.setVisibility(View.GONE);
                                                finish();
                                            }
                                            if (Objects.equals(sector, "Pharmacy")) {
                                                Intent intent = new Intent(LoginAdm.this, AdmHome.class);
                                                startVibrate(90);
                                                ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                                                        R.anim.slide_in_left, R.anim.slide_out_left);
                                                ActivityCompat.startActivity(LoginAdm.this, intent, actcompat.toBundle());
                                                progressBar3.setVisibility(View.GONE);
                                                finish();
                                            }
                                            if (Objects.equals(sector, "Closet")) {
                                                Intent intent = new Intent(LoginAdm.this, AdmHome.class);
                                                startVibrate(90);
                                                ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                                                        R.anim.slide_in_left, R.anim.slide_out_left);
                                                ActivityCompat.startActivity(LoginAdm.this, intent, actcompat.toBundle());
                                                progressBar3.setVisibility(View.GONE);
                                                finish();
                                            }
                                            if (Objects.equals(sector, "Others")) {
                                                Intent intent = new Intent(LoginAdm.this, AdmHome.class);
                                                startVibrate(90);
                                                ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                                                        R.anim.slide_in_left, R.anim.slide_out_left);
                                                ActivityCompat.startActivity(LoginAdm.this, intent, actcompat.toBundle());
                                                progressBar3.setVisibility(View.GONE);
                                                finish();
                                            }
                                        }
                                    }
                                }
                            });

                        }else{
                            Toast.makeText(LoginAdm.this, "Erro!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar3.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        ForgotTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext(), R.style.MyAlertDialogTheme);
                passwordResetDialog.setTitle( Html.fromHtml(getString(R.string.esquecimi)));
                passwordResetDialog.setMessage(R.string.redfsenha);
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String  mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginAdm.this, R.string.linkredf, Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginAdm.this, R.string.errolinkredf + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                passwordResetDialog.create().show();
            }
        });

    }

    public void startVibrate ( long time ) {
        Vibrator atvib = ( Vibrator ) getSystemService ( Context.VIBRATOR_SERVICE );
        assert atvib != null;
        atvib.vibrate ( time );
    }

    public void onBackPressed ( ) {
        finish ( );
    }
}

