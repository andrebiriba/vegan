package app.vegan;

import static app.vegan.R.drawable.error;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import java.util.HashMap;
import java.util.Map;
import de.hdodenhof.circleimageview.CircleImageView;

public class Register extends AppCompatActivity {

    public static final String TAG = "TAG";
    public static final String TAG1 = "TAG";
    EditText user_name, user_address_neighborhood, user_address_street, user_address_number, user_reference_point, user_phone, user_email , user_password;
    ImageButton registerbtn, imageButtonBack;
    CircleImageView profileIconIv;
    TextView backBtn, textview_user_country, textview_user_estate, textview_user_city,
            textview_user_cep, avatarTv, poli, termos;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID, diet;
    RadioGroup radioGroup;
    CheckBox checkTermos;
    private String paiscountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setRequestedOrientation ( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );


        poli = findViewById(R.id.poli);
        termos = findViewById(R.id.warning);
        avatarTv = findViewById(R.id.avatarTv);
        profileIconIv = findViewById(R.id.profileIconIv);
        user_name = findViewById(R.id.user_name);
        user_address_neighborhood = findViewById(R.id.user_address_neighborhood);
        user_address_street = findViewById(R.id.user_address_street);
        user_address_number = findViewById(R.id.user_address_number);
        user_reference_point = findViewById(R.id.user_reference_point);
        textview_user_country = findViewById(R.id.textview_user_country);
        textview_user_estate = findViewById(R.id.textview_user_estate);
        textview_user_city = findViewById(R.id.textview_user_city);
        textview_user_cep = findViewById(R.id.user_reference_cep);
        user_phone = findViewById(R.id.user_phone);
        user_email = findViewById(R.id.user_email);
        user_password = findViewById(R.id.user_password);
        registerbtn = findViewById(R.id.registerbtn);
        backBtn = findViewById(R.id.backBtn);
        progressBar = findViewById(R.id.progressBar);
        radioGroup = findViewById(R.id.radioCategory);
        imageButtonBack = findViewById(R.id.imageButtonBack);
        checkTermos = findViewById(R.id.checkBox3);

        avatarTv.setText("pig");

        fAuth = FirebaseAuth.getInstance();

        fStore = FirebaseFirestore.getInstance();

        profileIconIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickDialog();
            }
        });

        textview_user_country.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                categoryDialog();
            }
        });

        textview_user_estate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                categoryDialog1();
            }
        });

        textview_user_city.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                categoryDialog2();
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String email = user_email.getText().toString().trim();
                String password = user_password.getText().toString().trim();
                final String name = user_name.getText().toString();
                final String phone = user_phone.getText().toString();
                final String neighborhood = user_address_neighborhood.getText().toString();
                final String street = user_address_street.getText().toString();
                final String number = user_address_number.getText().toString();
                final String reference = user_reference_point.getText().toString();
                final String city = textview_user_city.getText().toString();
                final String state = textview_user_estate.getText().toString();
                final String country = paiscountry;
                final String cep = textview_user_cep.getText().toString().trim();
                final String avatar = avatarTv.getText().toString();

                diet = ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();

                if(!checkTermos.isChecked()){
                    checkTermos.setError(getString(R.string.ostermosnao));
                    Toast.makeText(Register.this, R.string.ostermosnao, Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(name)){
                    user_name.setError(getString(R.string.umoumaiscam));
                    Toast.makeText(Register.this, R.string.umoumaiscam, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(city)){
                    Toast.makeText(Register.this, R.string.umoumaiscam, Toast.LENGTH_SHORT).show();
                    textview_user_city.setError(getString(R.string.ostermosnao));
                    return;
                }
                if(TextUtils.isEmpty(state)){
                    Toast.makeText(Register.this, R.string.umoumaiscam, Toast.LENGTH_SHORT).show();
                    textview_user_estate.setError(getString(R.string.ostermosnao));
                    return;
                }
                if(TextUtils.isEmpty(country)){
                    Toast.makeText(Register.this, R.string.umoumaiscam, Toast.LENGTH_SHORT).show();
                    textview_user_country.setError(getString(R.string.ostermosnao));
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Register.this, R.string.umoumaiscam, Toast.LENGTH_SHORT).show();
                    user_email.setError(getString(R.string.ostermosnao));
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this, R.string.umoumaiscam, Toast.LENGTH_SHORT).show();
                    user_password.setError(getString(R.string.ostermosnao));
                    return;
                }

                if(password.length() < 6){
                    Toast.makeText(Register.this, R.string.umoumaiscam, Toast.LENGTH_SHORT).show();
                    user_password.setError(getString(R.string.ostermosnao));
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                final String timestamp = ""+System.currentTimeMillis();
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference docRef = fStore.collection("Users").document(userID);
                            Map<String,Object> edited = new HashMap<>();
                            edited.put("fName",name);
                            edited.put("email",email);
                            edited.put("number",number);
                            edited.put("neighborhood",neighborhood);
                            edited.put("avatar",avatar);
                            edited.put("timestamp",timestamp);
                            edited.put("country",country);
                            edited.put("state",state);
                            edited.put("diet",diet);
                            edited.put("city",city);
                            edited.put("token","");
                            edited.put("cep",cep);
                            edited.put("novato","true");
                            edited.put("phone",phone);
                            edited.put("reference",reference);
                            edited.put("street",street);
                            int Emoji = 0x1F49A;
                            String stringEmoji = String.valueOf(Emoji);
                            stringEmoji = (new String(Character.toChars(Emoji)));
                            final HashMap<String, Object> hashMap1 = new HashMap<>();
                            hashMap1.put("title", ""+getString(R.string.bemvinde)+stringEmoji);
                            hashMap1.put("text", ""+getString(R.string.coracaozinho));
                            final DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("ZYPUSH "+userID);
                            ref1.setValue(hashMap1)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            docRef.set(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    Toast.makeText(Register.this,R.string.sucesso, Toast.LENGTH_SHORT).show();
                                                    startVibrate ( 90 );
                                                    Intent intent = new Intent ( Register.this, Outros.class );
                                                    startVibrate ( 190 );
                                                    ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation ( getApplicationContext ( ),
                                                            R.anim.mover_esquerda, R.anim.mover_esquerda );
                                                    ActivityCompat.startActivity ( Register.this, intent, actcompat.toBundle ( ) );


                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                        }
                                    });


                        }else{
                            Toast.makeText(Register.this, "Erro!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });
            }
        });

        poli.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                builder.setTitle(getString(R.string.politicadegarantia));
                builder.setMessage(getString(R.string.longapolitica));

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        termos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                builder.setTitle(getString(R.string.termosdes));
                builder.setMessage(getString(R.string.longatermos));

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Register.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imageButtonBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Register.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showImagePickDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_profile_pic, null);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setView(view);
        final android.app.AlertDialog dialog = builder.create();
        dialog.show();
        ImageView close = view.findViewById(R.id.imageView66);
        ImageView cow = view.findViewById(R.id.imageView76);
        ImageView sheep = view.findViewById(R.id.imageView85);
        ImageView fish = view.findViewById(R.id.imageView86);
        ImageView rooster = view.findViewById(R.id.imageView87);
        ImageView pig = view.findViewById(R.id.ppig);
        ImageView goat = view.findViewById(R.id.imageView88);
        ImageView horse = view.findViewById(R.id.imageView90);
        ImageView rat = view.findViewById(R.id.imageView92);
        ImageView rabbit = view.findViewById(R.id.imageView93);
        ImageView bee = view.findViewById(R.id.imageView91);

        cow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatarTv.setText("cow");
                dialog.dismiss();
                try {
                    Picasso.get().load(R.drawable.pcow).placeholder(R.drawable.loading).into(profileIconIv);
                }
                catch (Exception e) {
                    profileIconIv.setImageDrawable(ContextCompat.getDrawable(Register.this, error ));
                }
            }
        });

        sheep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatarTv.setText("sheep");
                dialog.dismiss();
                try {
                    Picasso.get().load(R.drawable.psheep).placeholder(R.drawable.loading).into(profileIconIv);
                }
                catch (Exception e) {
                    profileIconIv.setImageDrawable(ContextCompat.getDrawable(Register.this, error ));
                }
            }
        });

        fish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatarTv.setText("fish");
                dialog.dismiss();
                try {
                    Picasso.get().load(R.drawable.pfish).placeholder(R.drawable.loading).into(profileIconIv);
                }
                catch (Exception e) {
                    profileIconIv.setImageDrawable(ContextCompat.getDrawable(Register.this, error ));
                }
            }
        });

        rooster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatarTv.setText("rooster");
                dialog.dismiss();
                try {
                    Picasso.get().load(R.drawable.pchicken).placeholder(R.drawable.loading).into(profileIconIv);
                }
                catch (Exception e) {
                    profileIconIv.setImageDrawable(ContextCompat.getDrawable(Register.this, error ));
                }
            }
        });

        pig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                try {
                    Picasso.get().load(R.drawable.ppig).placeholder(R.drawable.loading).into(profileIconIv);
                }
                catch (Exception e) {
                    profileIconIv.setImageDrawable(ContextCompat.getDrawable(Register.this, error ));
                }
            }
        });

        goat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatarTv.setText("goat");
                dialog.dismiss();
                try {
                    Picasso.get().load(R.drawable.pgoat).placeholder(R.drawable.loading).into(profileIconIv);
                }
                catch (Exception e) {
                    profileIconIv.setImageDrawable(ContextCompat.getDrawable(Register.this, error ));
                }
            }
        });

        horse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatarTv.setText("horse");
                dialog.dismiss();
                try {
                    Picasso.get().load(R.drawable.phorse).placeholder(R.drawable.loading).into(profileIconIv);
                }
                catch (Exception e) {
                    profileIconIv.setImageDrawable(ContextCompat.getDrawable(Register.this, error ));
                }
            }
        });

        rat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatarTv.setText("rat");
                dialog.dismiss();
                try {
                    Picasso.get().load(R.drawable.prat).placeholder(R.drawable.loading).into(profileIconIv);
                }
                catch (Exception e) {
                    profileIconIv.setImageDrawable(ContextCompat.getDrawable(Register.this, error ));
                }
            }
        });

        rabbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatarTv.setText("rabbit");
                dialog.dismiss();
                try {
                    Picasso.get().load(R.drawable.prabbit).placeholder(R.drawable.loading).into(profileIconIv);
                }
                catch (Exception e) {
                    profileIconIv.setImageDrawable(ContextCompat.getDrawable(Register.this, error ));
                }
            }
        });

        bee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatarTv.setText("bee");
                dialog.dismiss();
                try {
                    Picasso.get().load(R.drawable.pbee).placeholder(R.drawable.loading).into(profileIconIv);
                }
                catch (Exception e) {
                    profileIconIv.setImageDrawable(ContextCompat.getDrawable(Register.this, error ));
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void categoryDialog2() {
        //dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme);
        builder.setTitle(R.string.selcid)
                .setItems(Constants.City, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        String category3 = Constants.City[which];
                        textview_user_city.setText(category3);
                    }
                })
                .show();
    }

    private void categoryDialog1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme);
        builder.setTitle(R.string.selest)
                .setItems(Constants.States, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        String category2 = Constants.States[which];
                        textview_user_estate.setText(category2);
                    }
                })
                .show();
    }

    private void categoryDialog() {
        String[] Countries = {
                getString(R.string.brasil),
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme);
        builder.setTitle(R.string.selpai)
                .setItems(Countries, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        String category1 = Countries[which];
                        textview_user_country.setText(category1);
                        String[] Countrys = {
                                "Brasil",
                        };
                        paiscountry = Countrys[which];
                    }
                })
                .show();
    }

    public void onBackPressed ( ) {
        finish ( );
    }
    public void startVibrate ( long time ) {
        Vibrator atvib = ( Vibrator ) getSystemService ( Context.VIBRATOR_SERVICE );
        assert atvib != null;
        atvib.vibrate ( time );
    }

}
