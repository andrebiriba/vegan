package app.vegan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import java.util.Objects;


public class SettingsActivity extends AppCompatActivity {

    SwitchCompat fcmSwitch;
    TextView notificationStatusTv, sairTv, sobrenosTv, excluirMinhaContaTv, poli, termos;
    private ImageButton backBtn;
    private static String enabledMessage = "";
    private static String disabledMessage = "";
    private boolean isChecked = false;
    private FirebaseAuth firebaseAuth;
    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;
    private CardView floatingButton;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        poli = findViewById(R.id.poli);
        termos = findViewById(R.id.warning);
        sairTv = findViewById(R.id.sairTv);
        sobrenosTv = findViewById(R.id.sobrenosTv);
        excluirMinhaContaTv = findViewById(R.id.excluirMinhaContaTv);
        fcmSwitch = findViewById(R.id.fcmSwitch);
        notificationStatusTv = findViewById(R.id.notificationStatusTv);
        backBtn = findViewById(R.id.backBtn);
        floatingButton = findViewById(R.id.floatingButton);

        disabledMessage = getString(R.string.notifbloq);
        enabledMessage = getString(R.string.notifper);


        fStore=FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        testUserCurrent();

        sp = getSharedPreferences("SETTINGS_SP", MODE_PRIVATE);
        isChecked = sp.getBoolean("FCM_ENABLED", false);
        fcmSwitch.setChecked(isChecked);
        if (isChecked) {
            notificationStatusTv.setText(enabledMessage);
        }
        else {
            notificationStatusTv.setText(disabledMessage);
        }

        poli.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle(getString(R.string.politicadegarantia));
                builder.setMessage(getString(R.string.longapolitica));

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        termos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle(getString(R.string.termosdes));
                builder.setMessage(getString(R.string.longatermos));

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        excluirMinhaContaTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this, R.style.MyAlertDialogTheme);

                builder.setTitle(getString(R.string.vctemctz));
                String tempora = getString(R.string.desejaexc);
                builder.setMessage(tempora);

                builder.setPositiveButton(getString(R.string.continuar), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Intent data = getIntent();
                        String adm = data.getStringExtra("adm");
                        String userid=firebaseAuth.getCurrentUser().getUid();

                        if(Objects.equals(adm, "true")){

                            FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

                            rootRef.collection("Promoções")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Promoções").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });

                            rootRef.collection("Mais")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Mais").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });
                            rootRef.collection("Limpeza")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Limpeza").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });
                            rootRef.collection("Sobremesas")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Sobremesas").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });

                            rootRef.collection("Saúde")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Saúde").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });
                            rootRef.collection("Roupas")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Roupas").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });
                            rootRef.collection("Praia")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Praia").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });
                            rootRef.collection("Peça Íntima")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Peça Íntima").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });
                            rootRef.collection("Animal Não-Humano")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Animal Não-Humano").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });
                            rootRef.collection("Mãe & Bebê")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Mãe & Bebê").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });
                            rootRef.collection("Mercado")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Mercado").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });
                            rootRef.collection("Livros")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Livros").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });
                            rootRef.collection("Lanche")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Lanche").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });
                            rootRef.collection("Jardim")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Jardim").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });
                            rootRef.collection("Jantar")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Jantar").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });
                            rootRef.collection("Higiene")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Higiene").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });
                            rootRef.collection("Fitness")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Fitness").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });
                            rootRef.collection("Farmácia Natural")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Farmácia Natural").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });
                            rootRef.collection("Cosméticos")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Cosméticos").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });
                            rootRef.collection("Casa")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Casa").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });
                            rootRef.collection("Eco Friendly")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Eco Friendly").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });
                            rootRef.collection("Calçados")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Calçados").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });
                            rootRef.collection("Brechó")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Brechó").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });
                            rootRef.collection("Bem estar")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Bem estar").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });
                            rootRef.collection("Bebidas")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Bebidas").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });
                            rootRef.collection("Almoço")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Almoço").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });
                            rootRef.collection("Acessórios")
                                    .whereEqualTo("uid", userid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if (document.exists()) {
                                                        String productId = document.getString("productId");

                                                        DocumentReference docRef = fStore.collection("Acessórios").document(productId);
                                                        docRef.delete();
                                                    }
                                                }
                                            }
                                        }
                                    });

                            DocumentReference docRef = fStore.collection("Adm's").document(userid);
                            docRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    firebaseAuth.getCurrentUser().delete()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(SettingsActivity.this, R.string.deletado, Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(SettingsActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });                        }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Uh-oh, an error occurred!
                                }
                            });




                        } else {
                            FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                            rootRef.collection("Users").document(userid)
                                    .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            firebaseAuth.getCurrentUser().delete()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(SettingsActivity.this, R.string.deletado, Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(SettingsActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });

                                        }
                                    });
                        }
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        sairTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        sobrenosTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, Aboutus.class);
                startActivity(intent);
                finish();
            }
        });

        floatingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fcmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    //subscribeToTopic();
                }
                else{
                    //unSubscribeToTopic();
                }
            }
        });

    }

    public void testUserCurrent() {
        if ( firebaseAuth.getCurrentUser ( ) != null ) {
            floatingButton.setVisibility(View.GONE);
        } else {
            floatingButton.setVisibility(View.VISIBLE);
        }
    }

    private void subscribeToTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.FCM_TOPIC)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        spEditor = sp.edit();
                        spEditor.putBoolean("FCM_ENABLED", true);
                        spEditor.apply();

                        Toast.makeText(SettingsActivity.this, enabledMessage, Toast.LENGTH_SHORT).show();
                        notificationStatusTv.setText(enabledMessage);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SettingsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void unSubscribeToTopic(){
        FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.FCM_KEY)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        spEditor = sp.edit();
                        spEditor.putBoolean("FCM_ENABLED", false);
                        spEditor.apply();
                        Toast.makeText(SettingsActivity.this, disabledMessage, Toast.LENGTH_SHORT).show();
                        notificationStatusTv.setText(disabledMessage);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed unsubscribing
                        Toast.makeText(SettingsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void onBackPressed ( ) {
        super.onBackPressed();
        finish();
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();//logout
        Toast.makeText(SettingsActivity.this, R.string.logout, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}
