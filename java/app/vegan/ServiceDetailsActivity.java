package app.vegan;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static app.vegan.R.drawable.error;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public class ServiceDetailsActivity extends AppCompatActivity implements Serializable {
    private TextView name, category, addressOne, addressTwo, addressThree
            , serviceCert, price, bioOne, bioTwo, bioThree, suggestOne,
            suggestTwo, suggestThree, knowMore;
    private ImageView profileImg, gallery, love, chat, call, pet, vegan, acess, correct;
    private ImageButton imageButtonBack;
    private CardView floatingButton;
    private String userID, isLoved, nLikes, cel, usercoin;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        Intent data = getIntent();
        String nome = data.getStringExtra("name");
        String uid = data.getStringExtra("uid");
        String photo = data.getStringExtra("photo");
        String address1 = data.getStringExtra("address1");
        String address2 = data.getStringExtra("address2");
        String address3 = data.getStringExtra("address3");
        String categories = data.getStringExtra("categories");
        String city = data.getStringExtra("city");
        String country = data.getStringExtra("country");
        String estPrice = data.getStringExtra("estPrice");
        String firstBio = data.getStringExtra("firstBio");
        String secondBio = data.getStringExtra("secondBio");
        String serviceId = data.getStringExtra("serviceId");
        String state = data.getStringExtra("state");
        String suggest1 = data.getStringExtra("suggest1");
        String suggest2 = data.getStringExtra("suggest2");
        String suggest3 = data.getStringExtra("suggest3");
        String thirdBio = data.getStringExtra("thirdBio");
        String moreText = data.getStringExtra("moreText");
        String correctString = data.getStringExtra("correct");



        if (Objects.equals(country, "Brasil")) {
            usercoin = "R$";
        }
        if (Objects.equals(country, "Estados Unidos da América")) {
            usercoin = "US$";
        }
        if (Objects.equals(country, "Inglaterra")) {
            usercoin = "£";
        }
        if (Objects.equals(country, "Alemanha")) {
            usercoin = "€";
        }
        if (Objects.equals(country, "França")) {
            usercoin = "€";
        }
        if (Objects.equals(country, "Portugal")) {
            usercoin = "€";
        }
        if (Objects.equals(country, "Espanha")) {
            usercoin = "€";
        }


        cel = data.getStringExtra("cel");

        correct = findViewById(R.id.imageView29);
        pet = findViewById(R.id.pet);
        vegan = findViewById(R.id.vegan);
        acess = findViewById(R.id.acess);
        call = findViewById(R.id.imageView19);
        chat = findViewById(R.id.imageView22);
        knowMore = findViewById(R.id.knowMore);
        love = findViewById(R.id.imageButtonLove);
        gallery = findViewById(R.id.imageView68);
        imageButtonBack = findViewById(R.id.imageButtonBack);
        profileImg = findViewById(R.id.imageView32);
        name = findViewById(R.id.textView14);
        category = findViewById(R.id.textView65);
        addressOne = findViewById(R.id.textView);
        addressTwo = findViewById(R.id.textView11);
        addressThree = findViewById(R.id.textView12);
        serviceCert = findViewById(R.id.textView2);
        price = findViewById(R.id.textView17);
        bioOne = findViewById(R.id.textView18);
        bioTwo = findViewById(R.id.textView19);
        bioThree = findViewById(R.id.textView20);
        suggestOne = findViewById(R.id.textView22);
        suggestTwo = findViewById(R.id.textView23);
        suggestThree = findViewById(R.id.textView24);
        floatingButton = findViewById(R.id.floatingButton);

        if(Objects.equals(correctString, "true")){
            correct.setVisibility(VISIBLE);
        }

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        testUserCurrent();

        name.setText(nome);

          try {
                Picasso.get().load(photo).placeholder(R.drawable.loading).into(profileImg);
            }
            catch(Exception e){
                profileImg.setImageResource(R.drawable.error);
            }

        category.setText(categories);
        addressOne.setText(address1);
        addressTwo.setText(address2);
        addressThree.setText(address3);
        serviceCert.setText(serviceId);
        price.setText(usercoin+estPrice);
        bioOne.setText(firstBio);
        bioTwo.setText(secondBio);
        bioThree.setText(thirdBio);
        suggestOne.setText(suggest1);
        suggestTwo.setText(suggest2);
        suggestThree.setText(suggest3);
        knowMore.setText(moreText);

        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                GalleryAdd();
            }
        });

        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if ( fAuth.getCurrentUser ( ) != null ) {
                    if (Objects.equals(isLoved, "false")){
                        loved();
                    } else {
                        disloved();
                    }
                } else Toast.makeText(ServiceDetailsActivity.this, R.string.entreoucadastre, Toast.LENGTH_SHORT).show();
            }
        });

        floatingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceDetailsActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String userID;
                userID = fAuth.getCurrentUser().getUid();
                if(userID != null) {
                    Intent intent = new Intent(v.getContext(), ChatActivity.class);
                    intent.putExtra("contatoId", uid);
                    intent.putExtra("userId", userID);
                    intent.putExtra("contatoName", nome);
                    intent.putExtra("contatoPhoto", photo);
                    v.getContext().startActivity(intent);
                }else Toast.makeText(ServiceDetailsActivity.this, R.string.entreoucadastre, Toast.LENGTH_SHORT).show();
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(fAuth.getCurrentUser().getUid() != null){
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + cel));// Initiates the Intent
        startActivity(intent);
            } else Toast.makeText(ServiceDetailsActivity.this, R.string.entreoucadastre, Toast.LENGTH_SHORT).show();
               }
        });

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        Log.d(TAG, "uiduiduid " + uid);

        rootRef.collection("Adm's").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String petfriendly = document.getString("petfriendly");
                        String accessibility = document.getString("accessibility");
                        String ven = document.getString("vegan");

                        if(Objects.equals(petfriendly, "true")){
                            pet.setVisibility(VISIBLE);
                        }
                        if(Objects.equals(accessibility, "true")){
                            acess.setVisibility(VISIBLE);
                        }
                        if(Objects.equals(ven, "true")){
                            vegan.setVisibility(VISIBLE);
                        }
                    }
                }
            }
        });


    }

    private void GalleryAdd() {
        ShowGalleryDialog();
    }

    private void ShowGalleryDialog() {
        Intent data = getIntent();
        String uid = data.getStringExtra("uid");

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_gallery, null);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setView(view);
        final android.app.AlertDialog dialog = builder.create();
        dialog.show();
        ImageView close = view.findViewById(R.id.imageView66);
        ShapeableImageView photo1 = view.findViewById(R.id.gallery1);
        ShapeableImageView photo2 = view.findViewById(R.id.gallery2);
        ShapeableImageView photo3 = view.findViewById(R.id.gallery3);
        ShapeableImageView photo4 = view.findViewById(R.id.gallery4);
        ShapeableImageView photo5 = view.findViewById(R.id.gallery5);

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection("Adm's").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String ph1 = document.getString("photo1");
                        String ph2 = document.getString("photo2");
                        String ph3 = document.getString("photo3");
                        String ph4 = document.getString("photo4");
                        String ph5 = document.getString("photo5");

                        if(Objects.equals(ph1, "")){
                            photo1.setVisibility(GONE);
                        }
                        if(Objects.equals(ph2, "")){
                            photo2.setVisibility(GONE);
                        }
                        if(Objects.equals(ph3, "")){
                            photo3.setVisibility(GONE);
                        }
                        if(Objects.equals(ph4, "")){
                            photo4.setVisibility(GONE);
                        }
                        if(Objects.equals(ph5, "")){
                            photo5.setVisibility(GONE);
                        }

                        try {
                            Picasso.get().load(ph1).placeholder(R.drawable.loading).into(photo1);
                        } catch (Exception e) {
                            photo1.setImageResource(R.drawable.error);
                        }

                        try {
                            Picasso.get().load(ph2).placeholder(R.drawable.loading).into(photo2);
                        } catch (Exception e) {
                            photo2.setImageResource(R.drawable.error);
                        }

                        try {
                            Picasso.get().load(ph3).placeholder(R.drawable.loading).into(photo3);
                        } catch (Exception e) {
                            photo3.setImageResource(R.drawable.error);
                        }

                        try {
                            Picasso.get().load(ph4).placeholder(R.drawable.loading).into(photo4);
                        } catch (Exception e) {
                            photo4.setImageResource(R.drawable.error);
                        }

                        try {
                            Picasso.get().load(ph5).placeholder(R.drawable.loading).into(photo5);
                        } catch (Exception e) {
                            photo5.setImageResource(R.drawable.error);
                        }

                    }
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

    public void testUserCurrent() {
        if ( fAuth.getCurrentUser ( ) != null ) {
            floatingButton.setVisibility(GONE);
            loadPersonalLove();
        } else {
            floatingButton.setVisibility(VISIBLE);
        }
    }
    private void loadPersonalLove() {
        Intent data = getIntent();
        String uid = data.getStringExtra("uid");
        String serviceId = data.getStringExtra("serviceId");
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

        if(userID != null) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("LovedBy").child(userID).child(uid);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String loveDB = ""+snapshot.child("love").getValue();
                    if (loveDB.equals("true")) {
                        isLoved = "true";
                        try {
                            Picasso.get().load(R.drawable.heartgreen).placeholder(R.drawable.loading).into(love);
                        }
                        catch (Exception e) {
                            love.setImageDrawable(ContextCompat.getDrawable(ServiceDetailsActivity.this, error ));
                        }
                    } else {
                        isLoved = "false";
                        try {
                            Picasso.get().load(R.drawable.love).placeholder(R.drawable.loading).into(love);
                        }
                        catch (Exception e) {
                            love.setImageDrawable(ContextCompat.getDrawable(ServiceDetailsActivity.this, error ));
                        }                }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ServiceDetailsActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            isLoved = "false";
            try {
                Picasso.get().load(R.drawable.love).placeholder(R.drawable.loading).into(love);
            }
            catch (Exception e) {
                love.setImageDrawable(ContextCompat.getDrawable(ServiceDetailsActivity.this, error ));
            }
        }
    }

    private void disloved() {
        isLoved = "false";
        Intent data = getIntent();
        String uid = data.getStringExtra("uid");
        String serviceId = data.getStringExtra("serviceId");
        String nome = data.getStringExtra("name");
        String foto = data.getStringExtra("photo");
        userID = fAuth.getCurrentUser().getUid();

        if(userID != null) {
            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("LovedBy").child(userID).child(uid);
            ref.removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            try {
                                Picasso.get().load(R.drawable.love).placeholder(R.drawable.loading).into(love);
                                DocumentReference docRef = fStore.collection("Services").document(uid);
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                nLikes = document.getString("likes");
                                                if(Objects.equals(nLikes, "")) {
                                                    nLikes="0";
                                                }
                                                double cal = Double.parseDouble(nLikes);
                                                double cal1 = cal - 1;
                                                String cal2 = String.valueOf(cal1);
                                                HashMap<String, Object> hashMap = new HashMap<>();
                                                hashMap.put("productIcon",""+foto);
                                                hashMap.put("productId",""+serviceId);
                                                hashMap.put("brandName",""+nome);
                                                hashMap.put("shopUid",""+uid);
                                                hashMap.put("likes",""+cal2);
                                                docRef.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                    }
                                                });
                                                if(Objects.equals(nLikes, "1")) {
                                                    nLikes="0";
                                                    docRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                            catch (Exception e) {
                                love.setImageDrawable(ContextCompat.getDrawable(ServiceDetailsActivity.this, error ));
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ServiceDetailsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void loved() {
        isLoved = "true";
        Intent data = getIntent();
        String uid = data.getStringExtra("uid");
        String serviceId = data.getStringExtra("serviceId");
        userID = fAuth.getCurrentUser().getUid();

        Intent data2 = getIntent();
        String nome = data2.getStringExtra("name");
        String foto = data2.getStringExtra("photo");
        String uid2 = data2.getStringExtra("uid");
        String categories = data2.getStringExtra("categories");
        String serviceId2 = data2.getStringExtra("serviceId");

        if(userID != null) {
            HashMap<String, String> hashMap1 = new HashMap<>();
            hashMap1.put("love", isLoved);
            hashMap1.put("name", nome);
            hashMap1.put("categories", categories);
            hashMap1.put("foto",foto );
            hashMap1.put("id", serviceId);
            hashMap1.put("shopuid", uid);
            hashMap1.put("useruid", userID);

            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("LovedBy").child(userID).child(uid);
            ref.setValue(hashMap1)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            try {
                                Picasso.get().load(R.drawable.heartgreen).placeholder(R.drawable.loading).into(love);

                                DocumentReference docRef = fStore.collection("Services").document(uid);
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                nLikes = document.getString("likes");
                                                if(Objects.equals(nLikes, "")) {
                                                    nLikes="0";
                                                }
                                                double cal = Double.parseDouble(nLikes);
                                                double cal1 = cal + 1;
                                                String cal2 = String.valueOf(cal1);
                                                HashMap<String, Object> hashMap = new HashMap<>();
                                                hashMap.put("productIcon",""+foto);
                                                hashMap.put("productId",""+serviceId);
                                                hashMap.put("brandName",""+nome);
                                                hashMap.put("shopUid",""+uid);
                                                hashMap.put("productType","Services");
                                                hashMap.put("likes",""+cal2);
                                                DocumentReference docRef2 = fStore.collection("Services").document(serviceId);
                                                docRef2.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                    }
                                                });
                                            } else {
                                                HashMap<String, Object> hashMap = new HashMap<>();
                                                hashMap.put("productIcon",""+foto);
                                                hashMap.put("productId",""+serviceId);
                                                hashMap.put("brandName",""+nome);
                                                hashMap.put("productType","Services");
                                                hashMap.put("shopUid",""+uid);
                                                hashMap.put("likes","1");
                                                DocumentReference docRef2 = fStore.collection("Services").document(uid);
                                                docRef2.set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                    }
                                                });
                                            }
                                        }
                                    }
                                });
                            }
                            catch (Exception e) {
                                love.setImageDrawable(ContextCompat.getDrawable(ServiceDetailsActivity.this, error ));
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ServiceDetailsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public void onBackPressed ( ) {
        finish ( );
    }
}
