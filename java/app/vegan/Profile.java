package app.vegan;

import static app.vegan.R.drawable.error;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Profile extends AppCompatActivity {

    public static final String TAG = "TAG";
    FloatingActionButton salvar;
    private ImageButton imageButton13, imageButton3;
    LinearLayout item_add_linearlayout, itemLike, itemOrders, itemAdd, itemMobile, itemEmail, addInformationAdd, addInformationMobile,
                    addInformationEmail;
    TextView nome, personalTab, ordersTab, likesTab, addMobile, addAddress, memberDate, zerodata;
    private ImageView plusMobile, plusAddress;
    EditText bairro, endereco, numero, referencia, telefone, cidade, estado, pais, postalcode, email;
    private AdapterOrderUser adapterOrderUser;
    private AdapterLove adapterLove;
    private RecyclerView ordersRv, loveRv;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    String userID, titleP, textP;
    private ArrayList<ModelOrderUser> orderUserArrayList;
    ProgressDialog progressDialog;
    private ShapeableImageView profilePic;
    private View colunav1, colunav2;
    private CardView floatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setRequestedOrientation ( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        zerodata = findViewById(R.id.zerodata);
        colunav1 = findViewById(R.id.colunav1);
        colunav2 = findViewById(R.id.colunav2);
        loveRv = findViewById(R.id.likesRv);
        ordersRv = findViewById(R.id.ordersRv);
        memberDate = findViewById(R.id.memberDate);
        profilePic = findViewById(R.id.profilePic);
        plusMobile = findViewById(R.id.plusMobile);
        plusAddress = findViewById(R.id.plusAddress);
        addMobile = findViewById(R.id.addMobile);
        addAddress = findViewById(R.id.addAddress);
        itemLike = findViewById(R.id.itemLike);
        itemOrders = findViewById(R.id.itemOrders);
        itemAdd = findViewById(R.id.itemAdd);
        itemMobile = findViewById(R.id.itemMobile);
        itemEmail = findViewById(R.id.itemEmail);
        personalTab = findViewById(R.id.personalTab);
        likesTab = findViewById(R.id.likesTab);
        ordersTab = findViewById(R.id.ordersTab);
        item_add_linearlayout = findViewById(R.id.item_add_linearlayout);
        addInformationAdd = findViewById(R.id.addInformationAdd);
        addInformationMobile = findViewById(R.id.addInformationMobile);
        addInformationEmail = findViewById(R.id.addInformationEmail);
        imageButton13 = findViewById(R.id.imageButton13);
        imageButton3 = findViewById(R.id.imageButton3);
        cidade = findViewById(R.id.cidade);
        estado = findViewById(R.id.estado);
        pais = findViewById(R.id.pais);
        postalcode = findViewById(R.id.cep);
        bairro = findViewById(R.id.bairro);
        endereco = findViewById(R.id.endereco);
        numero = findViewById(R.id.numero);
        referencia = findViewById(R.id.referencia);
        telefone = findViewById(R.id.telefone);
        nome = findViewById(R.id.nomeuser);
        email = findViewById(R.id.emailuser);
        floatingButton = findViewById(R.id.floatingButton);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.aguardeum);
        progressDialog.setCanceledOnTouchOutside(false);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        testUserCurrent();

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickDialog();
            }
        });

        floatingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        personalTab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                personalTab.setTextColor(ContextCompat.getColor(Profile.this, R.color.white));
                ordersTab.setTextColor(ContextCompat.getColor(Profile.this, R.color.gray));
                likesTab.setTextColor(ContextCompat.getColor(Profile.this, R.color.gray));
                itemOrders.setVisibility(View.GONE);
                itemLike.setVisibility(View.GONE);
                itemAdd.setVisibility(View.VISIBLE);
                itemMobile.setVisibility(View.VISIBLE);
                itemEmail.setVisibility(View.VISIBLE);
                colunav1.setBackgroundResource(R.color.white);
                colunav2.setBackgroundResource(R.color.gray);
                zerodata.setVisibility(View.GONE);
            }
        });

        ordersTab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadOrders();
                itemOrders.setVisibility(View.VISIBLE);
                itemLike.setVisibility(View.GONE);
                itemAdd.setVisibility(View.GONE);
                itemMobile.setVisibility(View.GONE);
                itemEmail.setVisibility(View.GONE);
                personalTab.setTextColor(ContextCompat.getColor(Profile.this, R.color.gray));
                ordersTab.setTextColor(ContextCompat.getColor(Profile.this, R.color.white));
                likesTab.setTextColor(ContextCompat.getColor(Profile.this, R.color.gray));
                colunav1.setBackgroundResource(R.color.white);
                colunav2.setBackgroundResource(R.color.white);
            }
        });

        likesTab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadLoves();
                itemOrders.setVisibility(View.GONE);
                itemLike.setVisibility(View.VISIBLE);
                itemAdd.setVisibility(View.GONE);
                itemMobile.setVisibility(View.GONE);
                itemEmail.setVisibility(View.GONE);
                personalTab.setTextColor(ContextCompat.getColor(Profile.this, R.color.gray));
                ordersTab.setTextColor(ContextCompat.getColor(Profile.this, R.color.gray));
                likesTab.setTextColor(ContextCompat.getColor(Profile.this, R.color.white));
                colunav1.setBackgroundResource(R.color.gray);
                colunav2.setBackgroundResource(R.color.white);
            }
        });

        addInformationAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item_add_linearlayout.getVisibility() == View.GONE) {
                    item_add_linearlayout.setVisibility(View.VISIBLE);
                } else {
                    item_add_linearlayout.setVisibility(View.GONE);
                }
            }});

        addInformationMobile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (item_add_linearlayout.getVisibility() == View.GONE) {
                    item_add_linearlayout.setVisibility(View.VISIBLE);
                } else {
                    item_add_linearlayout.setVisibility(View.GONE);
                }
            }
        });

        addAddress.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(Profile.this,R.string.naoseesq, Toast.LENGTH_SHORT).show();
                editAdress();
            }
        });
        plusAddress.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(Profile.this,R.string.naoseesq, Toast.LENGTH_SHORT).show();
                editAdress();
            }
        });
        addInformationAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editAdress();
            }
        });

        addInformationMobile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editMobile();
            }
        });
        plusMobile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(Profile.this,R.string.naoseesq, Toast.LENGTH_SHORT).show();
                editMobile();
            }
        });
        addMobile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(Profile.this,R.string.naoseesq, Toast.LENGTH_SHORT).show();
                editMobile();
            }
        });

        ImageView sett = findViewById(R.id.sett);
        sett.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        loadMyInfo();

        imageButton13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), Outros.class));
                finish();
            }
        });

        ImageButton imageButton3 = findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                logout();
            }
        });


    }

    public void testUserCurrent() {
        if ( fAuth.getCurrentUser ( ) != null ) {
            floatingButton.setVisibility(View.GONE);
        } else {
            floatingButton.setVisibility(View.VISIBLE);
        }
    }

    private void loadOrders() {
        if ( fAuth.getCurrentUser() != null ) {
        orderUserArrayList = new ArrayList<>();

        userID = fAuth.getCurrentUser().getUid();
            Log.d(TAG, "userID "+userID);
        Query ref = FirebaseDatabase.getInstance().getReference("OrderBy").child(userID);
        ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        orderUserArrayList.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            ModelOrderUser modelOrderUser = ds.getValue(ModelOrderUser.class);
                            orderUserArrayList.add(modelOrderUser);
                        }
                        Collections.reverse(orderUserArrayList);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Profile.this, LinearLayoutManager.VERTICAL, false);
                        adapterOrderUser = new AdapterOrderUser(Profile.this, orderUserArrayList);
                        ordersRv.setLayoutManager(linearLayoutManager);
                        ordersRv.setHasFixedSize(true);
                        ordersRv.setAdapter(adapterOrderUser);

                        if (orderUserArrayList.isEmpty()) {
                            zerodata.setVisibility(View.VISIBLE);
                            itemOrders.setVisibility(View.GONE);
                            itemLike.setVisibility(View.GONE);
                            itemAdd.setVisibility(View.GONE);
                            itemMobile.setVisibility(View.GONE);
                            itemEmail.setVisibility(View.GONE);
                        } else {
                            zerodata.setVisibility(View.GONE);
                            itemOrders.setVisibility(View.VISIBLE);
                            itemLike.setVisibility(View.GONE);
                            itemAdd.setVisibility(View.GONE);
                            itemMobile.setVisibility(View.GONE);
                            itemEmail.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
        }  else Toast.makeText(Profile.this, R.string.entreoucadastre, Toast.LENGTH_SHORT).show();
    }

    private void loadLoves() {
        if ( fAuth.getCurrentUser ( ) != null ) {
            ArrayList<ModelLove> loveArrayList = new ArrayList<>();
            userID = fAuth.getCurrentUser().getUid();
            Query ref = FirebaseDatabase.getInstance().getReference("LovedBy").child(userID);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    loveArrayList.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        ModelLove modelLove = ds.getValue(ModelLove.class);
                        loveArrayList.add(modelLove);
                    }
                    Collections.reverse(loveArrayList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Profile.this, LinearLayoutManager.VERTICAL, false);
                    adapterLove = new AdapterLove(Profile.this, loveArrayList);
                    loveRv.setLayoutManager(linearLayoutManager);
                    loveRv.setAdapter(adapterLove);
                    if (adapterLove.getItemCount() == 0) {
                        zerodata.setVisibility(View.VISIBLE);
                        itemLike.setVisibility(View.GONE);
                        itemLike.setVisibility(View.GONE);
                        itemAdd.setVisibility(View.GONE);
                        itemMobile.setVisibility(View.GONE);
                        itemEmail.setVisibility(View.GONE);
                    } else {
                        zerodata.setVisibility(View.GONE);
                        itemOrders.setVisibility(View.GONE);
                        itemLike.setVisibility(View.VISIBLE);
                        itemAdd.setVisibility(View.GONE);
                        itemMobile.setVisibility(View.GONE);
                        itemEmail.setVisibility(View.GONE);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }  else Toast.makeText(Profile.this, R.string.entreoucadastre, Toast.LENGTH_SHORT).show();
    }
    
    private void editAdress() {
        if (!estado.isEnabled()) {
            //pais.setEnabled(true);
            estado.setEnabled(true);
            cidade.setEnabled(true);
            endereco.setEnabled(true);
            bairro.setEnabled(true);
            referencia.setEnabled(true);
            numero.setEnabled(true);
            postalcode.setEnabled(true);
           // pais.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_edit, 0);
            estado.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_edit, 0);
            cidade.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_edit, 0);
            endereco.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_edit, 0);
            bairro.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_edit, 0);
            referencia.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_edit, 0);
            numero.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_edit, 0);
            postalcode.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_edit, 0);
            addAddress.setText(R.string.salvar);
            plusAddress.setImageResource(R.drawable.ic_baseline_save_24_orange);
        } else {
            pais.setEnabled(false);
            estado.setEnabled(false);
            cidade.setEnabled(false);
            endereco.setEnabled(false);
            bairro.setEnabled(false);
            referencia.setEnabled(false);
            numero.setEnabled(false);
            postalcode.setEnabled(false);
            pais.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            estado.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            cidade.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            endereco.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            bairro.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            referencia.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            numero.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            postalcode.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            addAddress.setText(R.string.editar);
            plusAddress.setImageResource(R.drawable.ic_edit);
            inputData();
        }
    }

    private void editMobile() {
        if (!telefone.isEnabled()) {
            telefone.setEnabled(true);
            telefone.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_edit, 0);
            addMobile.setText(R.string.salvar);
            plusMobile.setImageResource(R.drawable.ic_baseline_save_24_orange);
        } else {
            telefone.setEnabled(false);
            telefone.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            addMobile.setText(R.string.editar);
            plusMobile.setImageResource(R.drawable.ic_edit);
            salvar.setVisibility(View.GONE);
            inputData();
        }
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
                String avatar = "cow";
                userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                DocumentReference documentReference = fStore.collection("Users").document(userID);
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("avatar", avatar);
                documentReference.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    } }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
                dialog.dismiss();
                try {
                    Picasso.get().load(R.drawable.pcow).placeholder(R.drawable.loading).into(profilePic);
                }
                catch (Exception e) {
                    profilePic.setImageDrawable(ContextCompat.getDrawable(Profile.this, error ));
                }
            }
        });

        sheep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String avatar = "sheep";
                userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                DocumentReference documentReference = fStore.collection("Users").document(userID);
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("avatar", avatar);
                documentReference.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    } }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });                dialog.dismiss();
                try {
                    Picasso.get().load(R.drawable.psheep).placeholder(R.drawable.loading).into(profilePic);
                }
                catch (Exception e) {
                    profilePic.setImageDrawable(ContextCompat.getDrawable(Profile.this, error ));
                }
            }
        });

        fish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String avatar = "fish";
                userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                DocumentReference documentReference = fStore.collection("Users").document(userID);
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("avatar", avatar);
                documentReference.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    } }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });                dialog.dismiss();
                try {
                    Picasso.get().load(R.drawable.pfish).placeholder(R.drawable.loading).into(profilePic);
                }
                catch (Exception e) {
                    profilePic.setImageDrawable(ContextCompat.getDrawable(Profile.this, error ));
                }
            }
        });

        rooster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String avatar = "rooster";
                userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                DocumentReference documentReference = fStore.collection("Users").document(userID);
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("avatar", avatar);
                documentReference.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    } }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });                dialog.dismiss();
                try {
                    Picasso.get().load(R.drawable.pchicken).placeholder(R.drawable.loading).into(profilePic);
                }
                catch (Exception e) {
                    profilePic.setImageDrawable(ContextCompat.getDrawable(Profile.this, error ));
                }
            }
        });

        pig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String avatar = "pig";
                userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                DocumentReference documentReference = fStore.collection("Users").document(userID);
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("avatar", avatar);
                documentReference.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    } }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
                dialog.dismiss();
                try {
                    Picasso.get().load(R.drawable.ppig).placeholder(R.drawable.loading).into(profilePic);
                }
                catch (Exception e) {
                    profilePic.setImageDrawable(ContextCompat.getDrawable(Profile.this, error ));
                }
            }
        });

        goat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String avatar = "goat";
                userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                DocumentReference documentReference = fStore.collection("Users").document(userID);
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("avatar", avatar);
                documentReference.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    } }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });                dialog.dismiss();
                try {
                    Picasso.get().load(R.drawable.pgoat).placeholder(R.drawable.loading).into(profilePic);
                }
                catch (Exception e) {
                    profilePic.setImageDrawable(ContextCompat.getDrawable(Profile.this, error ));
                }
            }
        });

        horse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String avatar = "horse";
                userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                DocumentReference documentReference = fStore.collection("Users").document(userID);
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("avatar", avatar);
                documentReference.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    } }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });                dialog.dismiss();
                try {
                    Picasso.get().load(R.drawable.phorse).placeholder(R.drawable.loading).into(profilePic);
                }
                catch (Exception e) {
                    profilePic.setImageDrawable(ContextCompat.getDrawable(Profile.this, error ));
                }
            }
        });

        rat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String avatar = "rat";
                userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                DocumentReference documentReference = fStore.collection("Users").document(userID);
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("avatar", avatar);
                documentReference.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    } }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });                dialog.dismiss();
                try {
                    Picasso.get().load(R.drawable.prat).placeholder(R.drawable.loading).into(profilePic);
                }
                catch (Exception e) {
                    profilePic.setImageDrawable(ContextCompat.getDrawable(Profile.this, error ));
                }
            }
        });

        rabbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String avatar = "rabbit";
                userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                DocumentReference documentReference = fStore.collection("Users").document(userID);
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("avatar", avatar);
                documentReference.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    } }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });                dialog.dismiss();
                try {
                    Picasso.get().load(R.drawable.prabbit).placeholder(R.drawable.loading).into(profilePic);
                }
                catch (Exception e) {
                    profilePic.setImageDrawable(ContextCompat.getDrawable(Profile.this, error ));
                }
            }
        });

        bee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String avatar = "bee";
                userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                DocumentReference documentReference = fStore.collection("Users").document(userID);
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("avatar", avatar);
                documentReference.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    } }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });                dialog.dismiss();
                try {
                    Picasso.get().load(R.drawable.pbee).placeholder(R.drawable.loading).into(profilePic);
                }
                catch (Exception e) {
                    profilePic.setImageDrawable(ContextCompat.getDrawable(Profile.this, error ));
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

    private void loadMyInfo() {
        String userID;

        FirebaseUser mFirebaseUser = fAuth.getCurrentUser();
        if(mFirebaseUser != null) {
            userID = mFirebaseUser.getUid(); //Do what you need to do with the id
        DocumentReference docRef = fStore.collection("Users").document(userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String name = document.getString("fName");
                        String Email = document.getString("email");
                        String phone = document.getString("phone");
                        String neighb = document.getString("neighborhood");
                        String address = document.getString("street");
                        String number = document.getString("number");
                        String referencya = document.getString("reference");
                        String estat = document.getString("state");
                        String cit = document.getString("city");
                        String countr = document.getString("country");
                        String pccep = document.getString("cep");
                        String avatar = document.getString("avatar");
                        String timestamp = document.getString("timestamp");

                        if (!Objects.equals(estat, "")){estado.setText(estat);}
                        if (!Objects.equals(cit, "")){cidade.setText(cit);}
                        if (!Objects.equals(countr, "")){pais.setText(countr);}
                        if (!Objects.equals(pccep, "")){postalcode.setText(pccep);}
                        if (!Objects.equals(neighb, "")){bairro.setText(neighb);}
                        if (!Objects.equals(number, "")){numero.setText(number);}
                        if (!Objects.equals(phone, "")){telefone.setText(phone);}
                        if (!Objects.equals(referencya, "")){referencia.setText(referencya);}
                        if (!Objects.equals(address, "")){endereco.setText(address);}
                        if (!Objects.equals(name, "")){nome.setText(name);}
                        if (!Objects.equals(Email, "")){email.setText(Email);}

                        assert timestamp != null;
                        long l = Long.parseLong(timestamp);

                            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                            cal.setTimeInMillis(l);
                            String date = DateFormat.format("MMM yyyy", cal).toString();
                            memberDate.setText(getString(R.string.membrodesde)+" "+date);
                        if (avatar == null || avatar.equals("")){
                            avatar = "pig";
                        }

                        switch (avatar) {
                            case "pig":
                                try {
                                    Picasso.get().load(R.drawable.ppig).placeholder(R.drawable.loading).into(profilePic);
                                }
                                catch (Exception e) {
                                    profilePic.setImageDrawable(ContextCompat.getDrawable(Profile.this, error ));
                                }
                                break;
                            case "cow":
                                try {
                                    Picasso.get().load(R.drawable.pcow).placeholder(R.drawable.loading).into(profilePic);
                                }
                                catch (Exception e) {
                                    profilePic.setImageDrawable(ContextCompat.getDrawable(Profile.this, error ));
                                }
                                break;
                            case "sheep":
                                try {
                                    Picasso.get().load(R.drawable.psheep).placeholder(R.drawable.loading).into(profilePic);
                                }
                                catch (Exception e) {
                                    profilePic.setImageDrawable(ContextCompat.getDrawable(Profile.this, error ));
                                }
                                break;
                            case "fish":
                                try {
                                    Picasso.get().load(R.drawable.pfish).placeholder(R.drawable.loading).into(profilePic);
                                }
                                catch (Exception e) {
                                    profilePic.setImageDrawable(ContextCompat.getDrawable(Profile.this, error ));
                                }
                                break;
                            case "goat":
                                try {
                                    Picasso.get().load(R.drawable.pgoat).placeholder(R.drawable.loading).into(profilePic);
                                }
                                catch (Exception e) {
                                    profilePic.setImageDrawable(ContextCompat.getDrawable(Profile.this, error ));
                                }
                                break;
                            case "horse":
                                try {
                                    Picasso.get().load(R.drawable.phorse).placeholder(R.drawable.loading).into(profilePic);
                                }
                                catch (Exception e) {
                                    profilePic.setImageDrawable(ContextCompat.getDrawable(Profile.this, error ));
                                }
                                break;
                            case "bee":
                                try {
                                    Picasso.get().load(R.drawable.pbee).placeholder(R.drawable.loading).into(profilePic);
                                }
                                catch (Exception e) {
                                    profilePic.setImageDrawable(ContextCompat.getDrawable(Profile.this, error ));
                                }
                                break;
                            case "rooster":
                                try {
                                    Picasso.get().load(R.drawable.pchicken).placeholder(R.drawable.loading).into(profilePic);
                                }
                                catch (Exception e) {
                                    profilePic.setImageDrawable(ContextCompat.getDrawable(Profile.this, error ));
                                }
                                break;
                            case "rat":
                                try {
                                    Picasso.get().load(R.drawable.prat).placeholder(R.drawable.loading).into(profilePic);
                                }
                                catch (Exception e) {
                                    profilePic.setImageDrawable(ContextCompat.getDrawable(Profile.this, error ));
                                }
                                break;
                            case "rabbit":
                                try {
                                    Picasso.get().load(R.drawable.prabbit).placeholder(R.drawable.loading).into(profilePic);
                                }
                                catch (Exception e) {
                                    profilePic.setImageDrawable(ContextCompat.getDrawable(Profile.this, error ));
                                }
                                break;
                        }
                    }
                }
            }
        });
        }
        if(mFirebaseUser == null){
            try {
                Picasso.get().load(R.drawable.unknown).placeholder(R.drawable.loading).into(profilePic);
            }
            catch (Exception e) {
                profilePic.setImageDrawable(ContextCompat.getDrawable(Profile.this, error ));
            }
        }
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }

    private String name, Email, phone, neighborhood, street, number, referencea, city, state, country, postalcodecep;
    private void inputData(){
        if(numero.getText().toString().isEmpty() || email.getText().toString().isEmpty()
                || telefone.getText().toString().isEmpty() ||estado.getText().toString().isEmpty()
                || cidade.getText().toString().isEmpty() || pais.getText().toString().isEmpty() ||bairro.getText().toString().isEmpty() || endereco.getText().toString().isEmpty()) {
            Toast.makeText(Profile.this, R.string.umoumaiscam, Toast.LENGTH_SHORT).show();
        }  else {
            name = nome.getText().toString().trim();
            Email = email.getText().toString().trim();
            phone = telefone.getText().toString().trim();
            neighborhood = bairro.getText().toString().trim();
            street = endereco.getText().toString().trim();
            number = numero.getText().toString().trim();
            referencea = referencia.getText().toString().trim();
            city = cidade.getText().toString().trim();
            state = estado.getText().toString().trim();
            country = pais.getText().toString().trim();
            postalcodecep = postalcode.getText().toString().trim();
            updateProfile();
        }
    }

    private void updateProfile(){
        progressDialog.setMessage(getString(R.string.atualizando));
        progressDialog.show();

        userID = fAuth.getCurrentUser().getUid();
        String Email = email.getText().toString();

                DocumentReference docRef = fStore.collection("Users").document(userID);
                Map<String,Object> edited = new HashMap<>();
                edited.put("email",Email);
                edited.put("number",number);
                edited.put("neighborhood",neighborhood);
                edited.put("country",country);
                edited.put("state",state);
                edited.put("city",city);
                edited.put("cep",postalcodecep);
                edited.put("phone",phone);
                edited.put("reference",referencea);
                edited.put("street",street);
                docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Profile.this, R.string.atualizado, Toast.LENGTH_SHORT).show();
                        startVibrate ( 90 );
                        progressDialog.dismiss();
                    }
                });

    }

    public void onBackPressed ( ) {
        finish ( );
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();//logout
        Toast.makeText(Profile.this, R.string.logout, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    public void startVibrate ( long time ) {
        Vibrator atvib = ( Vibrator ) getSystemService ( Context.VIBRATOR_SERVICE );
        assert atvib != null;
        atvib.vibrate ( time );
    }
}
