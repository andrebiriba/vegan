package app.vegan;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.drjacky.imagepicker.ImagePicker;
import com.github.drjacky.imagepicker.constant.ImageProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class AdmServicesActivity extends AppCompatActivity implements Serializable {

    public static final String TAG = "TAG";
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private static final int IMAGE_PICK_CAMERA_CODE = 500;
    private String[] cameraPermissions;
    private String[] storagePermissions;
    private Uri image_uri, image_uri1, image_uri2, image_uri3, image_uri4, image_uri5, image_uri6;
    private CheckBox vegn, acessib, petf;
    private FloatingActionButton save;
    private ImageButton imageButtonBack;
    private ImageView camera, gallery, chat;
    private TextView categoryTv, cityTv, stateTv, countryTv, titleMore, adicionar0;
    private EditText address1, address2, address3, nameP, id, price, bio1, bio2, bio3, serv1, serv2, serv3, textMore, telefEt;
    private ProgressDialog progressDialog, progressDialog2, progressDialog3, progressDialog4, progressDialog5, progressDialog6;
    FirebaseAuth fAuth;
    private Boolean imageOk;
    FirebaseFirestore fStore;
    String userID, photoclicked, vega, aces, pet, catego;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_services);

        telefEt = findViewById(R.id.telefEt);
        adicionar0 = findViewById(R.id.adicionar0);
        vegn = findViewById(R.id.checkBox4);
        acessib = findViewById(R.id.checkBox5);
        petf = findViewById(R.id.checkBox6);
        chat = findViewById(R.id.imageView20);
        titleMore = findViewById(R.id.moreTitle);
        textMore = findViewById(R.id.edbigtxt);
        cityTv = findViewById(R.id.cityTv);
        stateTv = findViewById(R.id.stateTv);
        countryTv = findViewById(R.id.countryTv);
        categoryTv = findViewById(R.id.textView65);
        save = findViewById(R.id.saveBtn);
        camera = findViewById(R.id.imageView42);
        gallery = findViewById(R.id.imageView68);
        address1 = findViewById(R.id.textView);
        address2 = findViewById(R.id.textView11);
        address3 = findViewById(R.id.textView12);
        nameP = findViewById(R.id.textView14);
        id = findViewById(R.id.textView2);
        price = findViewById(R.id.textView17);
        bio1 = findViewById(R.id.textView18);
        bio2 = findViewById(R.id.textView19);
        bio3 = findViewById(R.id.textView20);
        serv1 = findViewById(R.id.textView22);
        serv2 = findViewById(R.id.textView23);
        serv3 = findViewById(R.id.textView24);
        imageButtonBack = findViewById(R.id.imageButtonBack);

        imageOk = false;

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.aguardeum);
        progressDialog.setCanceledOnTouchOutside(false);

        progressDialog2 = new ProgressDialog(this);
        progressDialog2.setTitle(R.string.aguardeum);
        progressDialog2.setCanceledOnTouchOutside(false);

        progressDialog3 = new ProgressDialog(this);
        progressDialog3.setTitle(R.string.aguardeum);
        progressDialog3.setCanceledOnTouchOutside(false);

        progressDialog4 = new ProgressDialog(this);
        progressDialog4.setTitle(R.string.aguardeum);
        progressDialog4.setCanceledOnTouchOutside(false);

        progressDialog5 = new ProgressDialog(this);
        progressDialog5.setTitle(R.string.aguardeum);
        progressDialog5.setCanceledOnTouchOutside(false);

        progressDialog6 = new ProgressDialog(this);
        progressDialog6.setTitle(R.string.aguardeum);
        progressDialog6.setCanceledOnTouchOutside(false);
        
        loadContent();

        launcher=
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),(ActivityResult result)->{
                    if(result.getResultCode()==RESULT_OK){
                        image_uri = result.getData().getData();
                        if(Objects.equals(photoclicked, "photoProfile")){
                            image_uri1=image_uri;
                        }
                        if(Objects.equals(photoclicked, "photo1")){
                            image_uri2=image_uri;
                        }
                        if(Objects.equals(photoclicked, "photo2")){
                            image_uri3=image_uri;
                        }
                        if(Objects.equals(photoclicked, "photo3")){
                            image_uri4=image_uri;
                        }
                        if(Objects.equals(photoclicked, "photo4")){
                            image_uri5=image_uri;
                        }
                        if(Objects.equals(photoclicked, "photo5")){
                            image_uri6=image_uri;
                        }

                    }else if(result.getResultCode()== ImagePicker.RESULT_ERROR){
                        Toast.makeText(AdmServicesActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), LoginAdm.class));
                finish();
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intentLoadNewActivity = new Intent(AdmServicesActivity.this, AllChatsActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Save();
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                photoclicked = "photoProfile";
                ProfilePicture();
                adicionar0.setVisibility(View.VISIBLE);
            }
        });

        adicionar0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getImageSize(getApplicationContext(),image_uri)<550400) {
                    if(Objects.equals(photoclicked, "photoProfile")){
                        image_uri1=image_uri;
                    }
                    if(Objects.equals(photoclicked, "photo1")){
                        image_uri2=image_uri;
                    }
                    if(Objects.equals(photoclicked, "photo2")){
                        image_uri3=image_uri;
                    }
                    if(Objects.equals(photoclicked, "photo3")){
                        image_uri4=image_uri;
                    }
                    if(Objects.equals(photoclicked, "photo4")){
                        image_uri5=image_uri;
                    }
                    if(Objects.equals(photoclicked, "photo5")){
                        image_uri6=image_uri;
                    }
                    photoClick(photoclicked);
                    switch (photoclicked) {
                        case "photoProfile":
                            if (image_uri != null) {
                                try {
                                    Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(camera);
                                } catch (Exception e) {
                                    Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(camera);
                                }
                            }
                            break;
                    }
                    adicionar0.setVisibility(View.GONE);
                }else{
                    Toast.makeText(AdmServicesActivity.this, R.string.bigimage, Toast.LENGTH_SHORT).show();
                }
            }
        });


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                GalleryAdd();
            }
        });

        categoryTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                categoryDialog();
            }
        });
    }

    private void loadContent() {
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection("Adm's").document(Objects.requireNonNull(fAuth.getUid())).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String profilephoto = document.getString("profilePhoto");
                        String type = document.getString("type");
                        categoryTv.setText(type);
                        String city = document.getString("city");
                        cityTv.setText(city);
                        String state = document.getString("state");
                        stateTv.setText(state);
                        String country = document.getString("country");
                        countryTv.setText(country);
                        String name = document.getString("serviceTitle");
                        nameP.setText(name);
                        String priceEst = document.getString("estPrice");
                        price.setText(priceEst);
                        String bioFirst = document.getString("firstBio");
                        bio1.setText(bioFirst);
                        String bioSec = document.getString("secondBio");
                        bio2.setText(bioSec);
                        String biothird = document.getString("thirdBio");
                        bio3.setText(biothird);
                        String catig = document.getString("categories");
                        String cel = document.getString("cel");

                        catego = catig;

                        if(!Objects.equals(cel, "")){
                        telefEt.setText(cel);}

                        if(!Objects.equals(catig, "")){
                        categoryTv.setText(catig);}
                        else{
                            categoryTv.setText(R.string.cate);
                        }
                        String endre1 = document.getString("address1");
                        address1.setText(endre1);
                        String endre2 = document.getString("address2");
                        address2.setText(endre2);
                        String endre3 = document.getString("address3");
                        address3.setText(endre3);
                        String reco1 = document.getString("suggest1");
                        serv1.setText(reco1);
                        String reco2 = document.getString("suggest2");
                        serv2.setText(reco2);
                        String reco3 = document.getString("suggest3");
                        serv3.setText(reco3);
                        String idsubt = document.getString("serviceId");
                        id.setText(idsubt);
                        String moreText = document.getString("moreText");
                        textMore.setText(moreText);

                        try {
                            Picasso.get().load(profilephoto).placeholder(R.drawable.loading).into(camera);
                        }
                        catch(Exception e){
                            camera.setImageResource(R.drawable.error);
                        }
                    }
                }
            }
        });

    }

    private void GalleryAdd() {
            ShowGalleryDialog();
    }
    private void ProfilePicture() {
        showImagePickDialog();
    }

    private void Save() {
        String city = cityTv.getText().toString().trim();
        String state = stateTv.getText().toString().trim();
        String country = countryTv.getText().toString().trim();
        String cati = catego;
        String nome = nameP.getText().toString().trim();
        String end1 = address1.getText().toString().trim();
        String end2 = address2.getText().toString().trim();
        String end3 = address3.getText().toString().trim();
        String iden = id.getText().toString().trim();
        String cost = price.getText().toString().trim();
        String biot = bio1.getText().toString().trim();
        String biom = bio2.getText().toString().trim();
        String biob = bio3.getText().toString().trim();
        String ser1 = serv1.getText().toString().trim();
        String ser2 = serv2.getText().toString().trim();
        String ser3 = serv3.getText().toString().trim();
        String moreText = textMore.getText().toString().trim();

        if (vegn.isChecked()){
             vega = "true";
        } else{
             vega = "false";
        }

        if (acessib.isChecked()){
             aces = "true";
        } else{
             aces = "false";
        }

        if (petf.isChecked()){
             pet = "true";
        } else{
             pet = "false";
        }

        if(TextUtils.isEmpty(cati)){
            categoryTv.setError(getString(R.string.categnec));
            return;
        }
        if(TextUtils.isEmpty(nome)){
            nameP.setError(getString(R.string.umoumaiscam));
            return;
        }  if(TextUtils.isEmpty(end1)){
            address1.setError(getString(R.string.umoumaiscam));
            return;
        }  if(TextUtils.isEmpty(end2)){
            address2.setError(getString(R.string.umoumaiscam));
            return;
        }  if(TextUtils.isEmpty(end3)){
            address3.setError(getString(R.string.umoumaiscam));
            return;
        }  if(TextUtils.isEmpty(iden)){
            id.setError(getString(R.string.umoumaiscam));
            return;
        }  if(TextUtils.isEmpty(cost)){
            price.setError(getString(R.string.umoumaiscam));
            return;
        }  if(TextUtils.isEmpty(biot)){
            bio1.setError(getString(R.string.umoumaiscam));
            return;
        }  if(TextUtils.isEmpty(biom)){
            bio2.setError(getString(R.string.umoumaiscam));
            return;
        }  if(TextUtils.isEmpty(biob)){
            bio3.setError(getString(R.string.umoumaiscam));
            return;
        }  if(TextUtils.isEmpty(ser1)){
            serv1.setError(getString(R.string.umoumaiscam));
            return;
        }  if(TextUtils.isEmpty(ser2)){
            serv2.setError(getString(R.string.umoumaiscam));
            return;
        }  if(TextUtils.isEmpty(ser3)){
            serv3.setError(getString(R.string.umoumaiscam));
            return;
        }

        progressDialog.setMessage(getString(R.string.aguardeum));
        progressDialog.show();

        final String timestamp = ""+System.currentTimeMillis();
        String filePathAndName = "product_images/" + "" + timestamp;

        if(image_uri1!= null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
            storageReference.putFile(image_uri1)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;
                            Uri downloadImageUri = uriTask.getResult();

                            if (uriTask.isSuccessful()) {
                                userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                                DocumentReference documentReference = fStore.collection("Adm's").document(userID);
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("categories", "" + cati);
                                hashMap.put("city", "" + city);
                                hashMap.put("state", "" + state);
                                hashMap.put("country", "" + country);
                                hashMap.put("timeId", "" + timestamp);
                                hashMap.put("petfriendly", "" + pet);
                                hashMap.put("vegan", "" + vega);
                                hashMap.put("accessibility", "" + aces);
                                hashMap.put("serviceTitle", "" + nome);
                                hashMap.put("address1", "" + end1);
                                hashMap.put("address2", "" + end2);
                                hashMap.put("address3", "" + end3);
                                hashMap.put("profilePhoto", "" + downloadImageUri);
                                hashMap.put("serviceId", "" + iden);
                                hashMap.put("estPrice", "" + cost);
                                hashMap.put("firstBio", "" + biot);
                                hashMap.put("secondBio", "" + biom);
                                hashMap.put("thirdBio", "" + biob);
                                hashMap.put("cel", "" + telefEt.getText().toString());
                                hashMap.put("suggest1", "" + ser1);
                                hashMap.put("suggest2", "" + ser2);
                                hashMap.put("suggest3", "" + ser3);
                                hashMap.put("type", "Services");
                                hashMap.put("moreText", "" + moreText);
                                hashMap.put("uid", "" + fAuth.getUid());
                                documentReference.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressDialog.dismiss();
                                        Toast.makeText(AdmServicesActivity.this, R.string.contad, Toast.LENGTH_SHORT).show();
                                        Intent intent3 = new Intent(AdmServicesActivity.this, AdmServicesActivity.class);
                                        startActivity(intent3);
                                        startVibrate(100);
                                        finish();

                                        if(image_uri2!=null){
                                            final String timestamp = ""+System.currentTimeMillis();
                                            String filePathAndName3 = photoclicked + "/" + "" + timestamp;
                                            StorageReference storageReference3 = FirebaseStorage.getInstance().getReference(filePathAndName3);
                                            storageReference3.putFile(image_uri2)
                                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                                            while (!uriTask.isSuccessful()) ;
                                                            Uri downloadImageUri = uriTask.getResult();

                                                            String userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                                                            DocumentReference documentReference = fStore.collection("Adm's").document(userID);
                                                            HashMap<String, Object> hashMap = new HashMap<>();
                                                            hashMap.put("photo1", downloadImageUri);
                                                            documentReference.update(hashMap).
                                                                    addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void avoid) {
                                                                            if(image_uri3!=null){
                                                                                final String timestamp = ""+System.currentTimeMillis();
                                                                                String filePathAndName3 = photoclicked + "/" + "" + timestamp;
                                                                                StorageReference storageReference3 = FirebaseStorage.getInstance().getReference(filePathAndName3);
                                                                                storageReference3.putFile(image_uri3)
                                                                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                            @Override
                                                                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                                                                                while (!uriTask.isSuccessful()) ;
                                                                                                Uri downloadImageUri = uriTask.getResult();

                                                                                                String userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                                                                                                DocumentReference documentReference = fStore.collection("Adm's").document(userID);
                                                                                                HashMap<String, Object> hashMap = new HashMap<>();
                                                                                                hashMap.put("photo2", downloadImageUri);
                                                                                                documentReference.update(hashMap).
                                                                                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                            @Override
                                                                                                            public void onSuccess(Void avoid) {
                                                                                                                if(image_uri4!=null){
                                                                                                                    final String timestamp = ""+System.currentTimeMillis();
                                                                                                                    String filePathAndName3 = photoclicked + "/" + "" + timestamp;
                                                                                                                    StorageReference storageReference3 = FirebaseStorage.getInstance().getReference(filePathAndName3);
                                                                                                                    storageReference3.putFile(image_uri4)
                                                                                                                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                                                                @Override
                                                                                                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                                                                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                                                                                                                    while (!uriTask.isSuccessful()) ;
                                                                                                                                    Uri downloadImageUri = uriTask.getResult();

                                                                                                                                    String userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                                                                                                                                    DocumentReference documentReference = fStore.collection("Adm's").document(userID);
                                                                                                                                    HashMap<String, Object> hashMap = new HashMap<>();
                                                                                                                                    hashMap.put("photo3", downloadImageUri);
                                                                                                                                    documentReference.update(hashMap).
                                                                                                                                            addOnSuccessListener(new OnSuccessListener<Void>() {

                                                                                                                                                @Override
                                                                                                                                                public void onSuccess(Void avoid) {
                                                                                                                                                    if(image_uri5!=null){
                                                                                                                                                        final String timestamp = ""+System.currentTimeMillis();
                                                                                                                                                        String filePathAndName3 = photoclicked + "/" + "" + timestamp;
                                                                                                                                                        StorageReference storageReference3 = FirebaseStorage.getInstance().getReference(filePathAndName3);
                                                                                                                                                        storageReference3.putFile(image_uri5)
                                                                                                                                                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                                                                                                    @Override
                                                                                                                                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                                                                                                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                                                                                                                                                        while (!uriTask.isSuccessful()) ;
                                                                                                                                                                        Uri downloadImageUri = uriTask.getResult();

                                                                                                                                                                        String userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                                                                                                                                                                        DocumentReference documentReference = fStore.collection("Adm's").document(userID);
                                                                                                                                                                        HashMap<String, Object> hashMap = new HashMap<>();
                                                                                                                                                                        hashMap.put("photo4", downloadImageUri);
                                                                                                                                                                        documentReference.update(hashMap).
                                                                                                                                                                                addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                                                                                    @Override
                                                                                                                                                                                    public void onSuccess(Void avoid) {
                                                                                                                                                                                        if(image_uri6!=null){
                                                                                                                                                                                            final String timestamp = ""+System.currentTimeMillis();
                                                                                                                                                                                            String filePathAndName3 = photoclicked + "/" + "" + timestamp;
                                                                                                                                                                                            StorageReference storageReference3 = FirebaseStorage.getInstance().getReference(filePathAndName3);
                                                                                                                                                                                            storageReference3.putFile(image_uri6)
                                                                                                                                                                                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                                                                                                                                        @Override
                                                                                                                                                                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                                                                                                                                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                                                                                                                                                                                            while (!uriTask.isSuccessful()) ;
                                                                                                                                                                                                            Uri downloadImageUri = uriTask.getResult();

                                                                                                                                                                                                            String userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                                                                                                                                                                                                            DocumentReference documentReference = fStore.collection("Adm's").document(userID);
                                                                                                                                                                                                            HashMap<String, Object> hashMap = new HashMap<>();
                                                                                                                                                                                                            hashMap.put("photo5", downloadImageUri);
                                                                                                                                                                                                            documentReference.update(hashMap).
                                                                                                                                                                                                                    addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                                                                                                                        @Override
                                                                                                                                                                                                                        public void onSuccess(Void avoid) {
                                                                                                                                                                                                                                progressDialog.dismiss();
                                                                                                                                                                                                                                Toast.makeText(AdmServicesActivity.this, R.string.contad, Toast.LENGTH_SHORT).show();
                                                                                                                                                                                                                                startVibrate(100);
                                                                                                                                                                                                                        }
                                                                                                                                                                                                                    })
                                                                                                                                                                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                                                                                                                                                                        @Override
                                                                                                                                                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                                                                                                                                                            progressDialog6.dismiss();
                                                                                                                                                                                                                            Toast.makeText(AdmServicesActivity.this, "erro " + e, Toast.LENGTH_SHORT).show();
                                                                                                                                                                                                                        }
                                                                                                                                                                                                                    });
                                                                                                                                                                                                        }
                                                                                                                                                                                                    }).addOnFailureListener(new OnFailureListener() {
                                                                                                                                                                                                        @Override
                                                                                                                                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                                                                                                                                            Toast.makeText(AdmServicesActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                                                                                                                                        }
                                                                                                                                                                                                    });        }
                                                                                                                                                                                        else {
                                                                                                                                                                                            progressDialog.dismiss();
                                                                                                                                                                                            Toast.makeText(AdmServicesActivity.this, R.string.contad, Toast.LENGTH_SHORT).show();
                                                                                                                                                                                            startVibrate(100);
                                                                                                                                                                                        }
                                                                                                                                                                                    }
                                                                                                                                                                                })
                                                                                                                                                                                .addOnFailureListener(new OnFailureListener() {
                                                                                                                                                                                    @Override
                                                                                                                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                                                                                                                        Toast.makeText(AdmServicesActivity.this, "erro " + e, Toast.LENGTH_SHORT).show();
                                                                                                                                                                                    }
                                                                                                                                                                                });
                                                                                                                                                                    }
                                                                                                                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                                                                                                                    @Override
                                                                                                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                                                                                                        Toast.makeText(AdmServicesActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                                                                                                    }
                                                                                                                                                                });        }
                                                                                                                                                    else {
                                                                                                                                                        progressDialog.dismiss();
                                                                                                                                                        Toast.makeText(AdmServicesActivity.this, R.string.contad, Toast.LENGTH_SHORT).show();
                                                                                                                                                        startVibrate(100);
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            })
                                                                                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                                                                                @Override
                                                                                                                                                public void onFailure(@NonNull Exception e) {
                                                                                                                                                    Toast.makeText(AdmServicesActivity.this, "erro " + e, Toast.LENGTH_SHORT).show();
                                                                                                                                                }
                                                                                                                                            });
                                                                                                                                }
                                                                                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                                                                                @Override
                                                                                                                                public void onFailure(@NonNull Exception e) {
                                                                                                                                    Toast.makeText(AdmServicesActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                                                                }
                                                                                                                            });        }
                                                                                                                else {
                                                                                                                    progressDialog.dismiss();
                                                                                                                    Toast.makeText(AdmServicesActivity.this, R.string.contad, Toast.LENGTH_SHORT).show();
                                                                                                                    startVibrate(100);
                                                                                                                }
                                                                                                            }
                                                                                                        })
                                                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                                                            @Override
                                                                                                            public void onFailure(@NonNull Exception e) {
                                                                                                                Toast.makeText(AdmServicesActivity.this, "erro " + e, Toast.LENGTH_SHORT).show();
                                                                                                            }
                                                                                                        });
                                                                                            }
                                                                                        }).addOnFailureListener(new OnFailureListener() {
                                                                                            @Override
                                                                                            public void onFailure(@NonNull Exception e) {
                                                                                                Toast.makeText(AdmServicesActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        });        }
                                                                            else {
                                                                                progressDialog.dismiss();
                                                                                Toast.makeText(AdmServicesActivity.this, R.string.contad, Toast.LENGTH_SHORT).show();
                                                                                startVibrate(100);
                                                                            }

                                                                        }
                                                                    })
                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {

                                                                            Toast.makeText(AdmServicesActivity.this, "erro " + e, Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {

                                                            Toast.makeText(AdmServicesActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });        }

                                        else {
                                            progressDialog.dismiss();
                                            Toast.makeText(AdmServicesActivity.this, R.string.contad, Toast.LENGTH_SHORT).show();
                                            startVibrate(100);
                                        }


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e);
                                    }
                                });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // failed uploading image
                            Toast.makeText(AdmServicesActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
            DocumentReference documentReference = fStore.collection("Adm's").document(userID);
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("categories", "" + cati);
            hashMap.put("city", "" + city);
            hashMap.put("state", "" + state);
            hashMap.put("country", "" + country);
            hashMap.put("timeId", "" + timestamp);
            hashMap.put("serviceTitle", "" + nome);
            hashMap.put("address1", "" + end1);
            hashMap.put("address2", "" + end2);
            hashMap.put("address3", "" + end3);
            hashMap.put("serviceId", "" + iden);
            hashMap.put("estPrice", "" + cost);
            hashMap.put("firstBio", "" + biot);
            hashMap.put("secondBio", "" + biom);
            hashMap.put("thirdBio", "" + biob);
            hashMap.put("suggest1", "" + ser1);
            hashMap.put("suggest2", "" + ser2);
            hashMap.put("suggest3", "" + ser3);
            hashMap.put("type", "Services");
            hashMap.put("moreText", "" + moreText);
            hashMap.put("uid", "" + fAuth.getUid());
            documentReference.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    if(image_uri2!=null){
                        final String timestamp = "" + System.currentTimeMillis();
                        String filePathAndName3 = photoclicked + "/" + "" + timestamp;
                        StorageReference storageReference3 = FirebaseStorage.getInstance().getReference(filePathAndName3);
                        storageReference3.putFile(image_uri2)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                        while (!uriTask.isSuccessful()) ;
                                        Uri downloadImageUri = uriTask.getResult();

                                        String userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                                        DocumentReference documentReference = fStore.collection("Adm's").document(userID);
                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("photo1", downloadImageUri);
                                        documentReference.update(hashMap).
                                                addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void avoid) {
                                                        if(image_uri3!=null){
                                                            final String timestamp = "" + System.currentTimeMillis();
                                                            String filePathAndName3 = photoclicked + "/" + "" + timestamp;
                                                            StorageReference storageReference3 = FirebaseStorage.getInstance().getReference(filePathAndName3);
                                                            storageReference3.putFile(image_uri3)
                                                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                        @Override
                                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                                                            while (!uriTask.isSuccessful()) ;
                                                                            Uri downloadImageUri = uriTask.getResult();

                                                                            String userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                                                                            DocumentReference documentReference = fStore.collection("Adm's").document(userID);
                                                                            HashMap<String, Object> hashMap = new HashMap<>();
                                                                            hashMap.put("photo2", downloadImageUri);
                                                                            documentReference.update(hashMap).
                                                                                    addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                        @Override
                                                                                        public void onSuccess(Void avoid) {
                                                                                            if(image_uri4!=null){
                                                                                                final String timestamp = ""+System.currentTimeMillis();
                                                                                                String filePathAndName3 = photoclicked + "/" + "" + timestamp;
                                                                                                StorageReference storageReference3 = FirebaseStorage.getInstance().getReference(filePathAndName3);
                                                                                                storageReference3.putFile(image_uri4)
                                                                                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                                            @Override
                                                                                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                                                                                                while (!uriTask.isSuccessful()) ;
                                                                                                                Uri downloadImageUri = uriTask.getResult();

                                                                                                                String userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                                                                                                                DocumentReference documentReference = fStore.collection("Adm's").document(userID);
                                                                                                                HashMap<String, Object> hashMap = new HashMap<>();
                                                                                                                hashMap.put("photo3", downloadImageUri);
                                                                                                                documentReference.update(hashMap).
                                                                                                                        addOnSuccessListener(new OnSuccessListener<Void>() {

                                                                                                                            @Override
                                                                                                                            public void onSuccess(Void avoid) {
                                                                                                                                if(image_uri5!=null){
                                                                                                                                    final String timestamp = ""+System.currentTimeMillis();
                                                                                                                                    String filePathAndName3 = photoclicked + "/" + "" + timestamp;
                                                                                                                                    StorageReference storageReference3 = FirebaseStorage.getInstance().getReference(filePathAndName3);
                                                                                                                                    storageReference3.putFile(image_uri5)
                                                                                                                                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                                                                                @Override
                                                                                                                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                                                                                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                                                                                                                                    while (!uriTask.isSuccessful()) ;
                                                                                                                                                    Uri downloadImageUri = uriTask.getResult();

                                                                                                                                                    String userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                                                                                                                                                    DocumentReference documentReference = fStore.collection("Adm's").document(userID);
                                                                                                                                                    HashMap<String, Object> hashMap = new HashMap<>();
                                                                                                                                                    hashMap.put("photo4", downloadImageUri);
                                                                                                                                                    documentReference.update(hashMap).
                                                                                                                                                            addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                                                                @Override
                                                                                                                                                                public void onSuccess(Void avoid) {
                                                                                                                                                                    if(image_uri6!=null){
                                                                                                                                                                        final String timestamp = ""+System.currentTimeMillis();
                                                                                                                                                                        String filePathAndName3 = photoclicked + "/" + "" + timestamp;
                                                                                                                                                                        StorageReference storageReference3 = FirebaseStorage.getInstance().getReference(filePathAndName3);
                                                                                                                                                                        storageReference3.putFile(image_uri6)
                                                                                                                                                                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                                                                                                                    @Override
                                                                                                                                                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                                                                                                                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                                                                                                                                                                        while (!uriTask.isSuccessful()) ;
                                                                                                                                                                                        Uri downloadImageUri = uriTask.getResult();

                                                                                                                                                                                        String userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                                                                                                                                                                                        DocumentReference documentReference = fStore.collection("Adm's").document(userID);
                                                                                                                                                                                        HashMap<String, Object> hashMap = new HashMap<>();
                                                                                                                                                                                        hashMap.put("photo5", downloadImageUri);
                                                                                                                                                                                        documentReference.update(hashMap).
                                                                                                                                                                                                addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                                                                                                    @Override
                                                                                                                                                                                                    public void onSuccess(Void avoid) {
                                                                                                                                                                                                        progressDialog.dismiss();
                                                                                                                                                                                                        Toast.makeText(AdmServicesActivity.this, R.string.contad, Toast.LENGTH_SHORT).show();
                                                                                                                                                                                                        startVibrate(100);
                                                                                                                                                                                                    }
                                                                                                                                                                                                })
                                                                                                                                                                                                .addOnFailureListener(new OnFailureListener() {
                                                                                                                                                                                                    @Override
                                                                                                                                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                                                                                                                                        progressDialog6.dismiss();
                                                                                                                                                                                                        Toast.makeText(AdmServicesActivity.this, "erro " + e, Toast.LENGTH_SHORT).show();
                                                                                                                                                                                                    }
                                                                                                                                                                                                });
                                                                                                                                                                                    }
                                                                                                                                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                                                                                                                                    @Override
                                                                                                                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                                                                                                                        Toast.makeText(AdmServicesActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                                                                                                                    }
                                                                                                                                                                                });        }
                                                                                                                                                                    else {
                                                                                                                                                                        progressDialog.dismiss();
                                                                                                                                                                        Toast.makeText(AdmServicesActivity.this, R.string.contad, Toast.LENGTH_SHORT).show();
                                                                                                                                                                        startVibrate(100);
                                                                                                                                                                    }
                                                                                                                                                                }
                                                                                                                                                            })
                                                                                                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                                                                                                @Override
                                                                                                                                                                public void onFailure(@NonNull Exception e) {
                                                                                                                                                                    Toast.makeText(AdmServicesActivity.this, "erro " + e, Toast.LENGTH_SHORT).show();
                                                                                                                                                                }
                                                                                                                                                            });
                                                                                                                                                }
                                                                                                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                                                                                                @Override
                                                                                                                                                public void onFailure(@NonNull Exception e) {
                                                                                                                                                    Toast.makeText(AdmServicesActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                                                                                }
                                                                                                                                            });        }
                                                                                                                                else {
                                                                                                                                    progressDialog.dismiss();
                                                                                                                                    Toast.makeText(AdmServicesActivity.this, R.string.contad, Toast.LENGTH_SHORT).show();
                                                                                                                                    startVibrate(100);
                                                                                                                                }
                                                                                                                            }
                                                                                                                        })
                                                                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                                                                            @Override
                                                                                                                            public void onFailure(@NonNull Exception e) {
                                                                                                                                Toast.makeText(AdmServicesActivity.this, "erro " + e, Toast.LENGTH_SHORT).show();
                                                                                                                            }
                                                                                                                        });
                                                                                                            }
                                                                                                        }).addOnFailureListener(new OnFailureListener() {
                                                                                                            @Override
                                                                                                            public void onFailure(@NonNull Exception e) {
                                                                                                                Toast.makeText(AdmServicesActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                                            }
                                                                                                        });        }
                                                                                            else {
                                                                                                progressDialog.dismiss();
                                                                                                Toast.makeText(AdmServicesActivity.this, R.string.contad, Toast.LENGTH_SHORT).show();
                                                                                                startVibrate(100);
                                                                                            }
                                                                                        }
                                                                                    })
                                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                                        @Override
                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                            Toast.makeText(AdmServicesActivity.this, "erro " + e, Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                    });
                                                                        }
                                                                    }).addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            Toast.makeText(AdmServicesActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });        }
                                                        else {
                                                            progressDialog.dismiss();
                                                            Toast.makeText(AdmServicesActivity.this, R.string.contad, Toast.LENGTH_SHORT).show();
                                                            startVibrate(100);
                                                        }

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                        Toast.makeText(AdmServicesActivity.this, "erro " + e, Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(AdmServicesActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });        }

                    else {
                        progressDialog.dismiss();
                        Toast.makeText(AdmServicesActivity.this, R.string.contad, Toast.LENGTH_SHORT).show();
                        startVibrate(100);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Log.d(TAG, "onFailure: " + e);
                }
            });

        }

    }

    public void startVibrate ( long time ) {
        Vibrator atvib = ( Vibrator ) getSystemService ( Context.VIBRATOR_SERVICE );
        assert atvib != null;
        atvib.vibrate ( time );
    }

    public void onBackPressed ( ) {
        super.onBackPressed();
        finish();
    }

    private void ShowGalleryDialog() {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_gallery, null);
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this,  R.style.MyAlertDialogTheme);
            builder.setView(view);
            final android.app.AlertDialog dialog = builder.create();
            dialog.show();
            ImageView close = view.findViewById(R.id.imageView66);
            ShapeableImageView photo1 = view.findViewById(R.id.gallery1);
            ShapeableImageView photo2 = view.findViewById(R.id.gallery2);
            ShapeableImageView photo3 = view.findViewById(R.id.gallery3);
            ShapeableImageView photo4 = view.findViewById(R.id.gallery4);
            ShapeableImageView photo5 = view.findViewById(R.id.gallery5);
        TextView adicionar1 = view.findViewById(R.id.adicionar1);
        TextView adicionar2 = view.findViewById(R.id.adicionar2);
        TextView adicionar3 = view.findViewById(R.id.adicionar3);
        TextView adicionar4 = view.findViewById(R.id.adicionar4);
        TextView adicionar5 = view.findViewById(R.id.adicionar5);
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection("Adm's").document(Objects.requireNonNull(fAuth.getUid())).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
                        try {
                            Picasso.get().load(ph1).placeholder(R.drawable.loading).into(photo1);
                        } catch (Exception e) {
                            photo1.setImageResource(R.drawable.error);
                        }
                       if(ph1.equals("")){
                           try {
                               Picasso.get().load(R.drawable.ic_baseline_add_a_photo_24).placeholder(R.drawable.ic_baseline_add_a_photo_24).into(photo1);
                           } catch (Exception e) {
                               photo1.setImageResource(R.drawable.error);
                           }
                       }
                        try {
                            Picasso.get().load(ph2).placeholder(R.drawable.loading).into(photo2);
                        } catch (Exception e) {
                            photo2.setImageResource(R.drawable.error);
                        }
                        if(ph2.equals("")){
                            try {
                                Picasso.get().load(R.drawable.ic_baseline_add_a_photo_24).placeholder(R.drawable.ic_baseline_add_a_photo_24).into(photo2);
                            } catch (Exception e) {
                                photo2.setImageResource(R.drawable.error);
                            }
                        }

                        try {
                            Picasso.get().load(ph3).placeholder(R.drawable.loading).into(photo3);
                        } catch (Exception e) {
                            photo3.setImageResource(R.drawable.error);
                        }
                        if(ph3.equals("")){
                            try {
                                Picasso.get().load(R.drawable.ic_baseline_add_a_photo_24).placeholder(R.drawable.ic_baseline_add_a_photo_24).into(photo3);
                            } catch (Exception e) {
                                photo3.setImageResource(R.drawable.error);
                            }
                        }

                        try {
                            Picasso.get().load(ph4).placeholder(R.drawable.loading).into(photo4);
                        } catch (Exception e) {
                            photo4.setImageResource(R.drawable.error);
                        }

                        if(ph4.equals("")){
                            try {
                                Picasso.get().load(R.drawable.ic_baseline_add_a_photo_24).placeholder(R.drawable.ic_baseline_add_a_photo_24).into(photo4);
                            } catch (Exception e) {
                                photo4.setImageResource(R.drawable.error);
                            }
                        }

                        try {
                            Picasso.get().load(ph5).placeholder(R.drawable.loading).into(photo5);
                        } catch (Exception e) {
                            photo5.setImageResource(R.drawable.error);
                        }

                        if(ph5.equals("")){
                            try {
                                Picasso.get().load(R.drawable.ic_baseline_add_a_photo_24).placeholder(R.drawable.ic_baseline_add_a_photo_24).into(photo5);
                            } catch (Exception e) {
                                photo5.setImageResource(R.drawable.error);
                            }
                        }

                    }}}});

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

        adicionar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getImageSize(getApplicationContext(),image_uri)<550400) {
                    photoClick(photoclicked);
                switch (photoclicked) {
                    case "photo1" :
                        if(image_uri != null) {
                            if(Objects.equals(photoclicked, "photoProfile")){
                                image_uri1=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo1")){
                                image_uri2=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo2")){
                                image_uri3=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo3")){
                                image_uri4=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo4")){
                                image_uri5=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo5")){
                                image_uri6=image_uri;
                            }
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo1);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo1);
                            }}
                        break;

                    case "photo2":
                        if(image_uri != null){
                            if(Objects.equals(photoclicked, "photoProfile")){
                                image_uri1=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo1")){
                                image_uri2=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo2")){
                                image_uri3=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo3")){
                                image_uri4=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo4")){
                                image_uri5=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo5")){
                                image_uri6=image_uri;
                            }
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo2);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo2);
                            }}
                        break;
                    case "photo3":
                        if(image_uri != null){
                            if(Objects.equals(photoclicked, "photoProfile")){
                                image_uri1=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo1")){
                                image_uri2=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo2")){
                                image_uri3=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo3")){
                                image_uri4=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo4")){
                                image_uri5=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo5")){
                                image_uri6=image_uri;
                            }
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo3);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo3);
                            }}
                        break;
                    case "photo4":
                        if(image_uri != null){
                            if(Objects.equals(photoclicked, "photoProfile")){
                                image_uri1=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo1")){
                                image_uri2=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo2")){
                                image_uri3=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo3")){
                                image_uri4=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo4")){
                                image_uri5=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo5")){
                                image_uri6=image_uri;
                            }
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo4);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo4);
                            }}
                        break;
                    case "photo5":
                        if(image_uri != null){
                            if(Objects.equals(photoclicked, "photoProfile")){
                                image_uri1=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo1")){
                                image_uri2=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo2")){
                                image_uri3=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo3")){
                                image_uri4=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo4")){
                                image_uri5=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo5")){
                                image_uri6=image_uri;
                            }
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo5);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo5);
                            }}
                        break;
                }
                adicionar1.setVisibility(View.GONE);
            }else{
                Toast.makeText(AdmServicesActivity.this, R.string.bigimage, Toast.LENGTH_SHORT).show();
            }
            }
        });

        adicionar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getImageSize(getApplicationContext(),image_uri)<550400) {
                    photoClick(photoclicked);
                switch (photoclicked) {
                    case "photo1" :
                        if(image_uri != null) {
                            if(Objects.equals(photoclicked, "photoProfile")){
                                image_uri1=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo1")){
                                image_uri2=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo2")){
                                image_uri3=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo3")){
                                image_uri4=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo4")){
                                image_uri5=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo5")){
                                image_uri6=image_uri;
                            }
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo1);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo1);
                            }}
                        break;

                    case "photo2":
                        if(image_uri != null){
                            if(Objects.equals(photoclicked, "photoProfile")){
                                image_uri1=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo1")){
                                image_uri2=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo2")){
                                image_uri3=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo3")){
                                image_uri4=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo4")){
                                image_uri5=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo5")){
                                image_uri6=image_uri;
                            }
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo2);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo2);
                            }}
                        break;
                    case "photo3":
                        if(image_uri != null){
                            if(Objects.equals(photoclicked, "photoProfile")){
                                image_uri1=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo1")){
                                image_uri2=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo2")){
                                image_uri3=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo3")){
                                image_uri4=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo4")){
                                image_uri5=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo5")){
                                image_uri6=image_uri;
                            }
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo3);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo3);
                            }}
                        break;
                    case "photo4":
                        if(image_uri != null){
                            if(Objects.equals(photoclicked, "photoProfile")){
                                image_uri1=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo1")){
                                image_uri2=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo2")){
                                image_uri3=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo3")){
                                image_uri4=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo4")){
                                image_uri5=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo5")){
                                image_uri6=image_uri;
                            }
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo4);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo4);
                            }}
                        break;
                    case "photo5":
                        if(image_uri != null){
                            if(Objects.equals(photoclicked, "photoProfile")){
                                image_uri1=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo1")){
                                image_uri2=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo2")){
                                image_uri3=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo3")){
                                image_uri4=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo4")){
                                image_uri5=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo5")){
                                image_uri6=image_uri;
                            }
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo5);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo5);
                            }}
                        break;
                }
                adicionar2.setVisibility(View.GONE);
            }else{
                Toast.makeText(AdmServicesActivity.this, R.string.bigimage, Toast.LENGTH_SHORT).show();
            }
            }
        });

        adicionar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getImageSize(getApplicationContext(),image_uri)<550400) {
                    photoClick(photoclicked);
                switch (photoclicked) {
                    case "photo1" :
                        if(image_uri != null) {
                            if(Objects.equals(photoclicked, "photoProfile")){
                                image_uri1=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo1")){
                                image_uri2=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo2")){
                                image_uri3=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo3")){
                                image_uri4=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo4")){
                                image_uri5=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo5")){
                                image_uri6=image_uri;
                            }
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo1);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo1);
                            }}
                        break;

                    case "photo2":
                        if(image_uri != null){
                            if(Objects.equals(photoclicked, "photoProfile")){
                                image_uri1=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo1")){
                                image_uri2=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo2")){
                                image_uri3=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo3")){
                                image_uri4=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo4")){
                                image_uri5=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo5")){
                                image_uri6=image_uri;
                            }
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo2);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo2);
                            }}
                        break;
                    case "photo3":
                        if(image_uri != null){
                            if(Objects.equals(photoclicked, "photoProfile")){
                                image_uri1=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo1")){
                                image_uri2=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo2")){
                                image_uri3=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo3")){
                                image_uri4=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo4")){
                                image_uri5=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo5")){
                                image_uri6=image_uri;
                            }
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo3);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo3);
                            }}
                        break;
                    case "photo4":
                        if(image_uri != null){
                            if(Objects.equals(photoclicked, "photoProfile")){
                                image_uri1=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo1")){
                                image_uri2=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo2")){
                                image_uri3=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo3")){
                                image_uri4=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo4")){
                                image_uri5=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo5")){
                                image_uri6=image_uri;
                            }
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo4);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo4);
                            }}
                        break;
                    case "photo5":
                        if(image_uri != null){
                            if(Objects.equals(photoclicked, "photoProfile")){
                                image_uri1=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo1")){
                                image_uri2=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo2")){
                                image_uri3=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo3")){
                                image_uri4=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo4")){
                                image_uri5=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo5")){
                                image_uri6=image_uri;
                            }
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo5);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo5);
                            }}
                        break;
                }
                adicionar3.setVisibility(View.GONE);
                }else{
                    Toast.makeText(AdmServicesActivity.this, R.string.bigimage, Toast.LENGTH_SHORT).show();
                }
            }
        });

        adicionar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getImageSize(getApplicationContext(),image_uri)<550400) {
                    photoClick(photoclicked);
                switch (photoclicked) {
                    case "photo1" :
                        if(image_uri != null) {
                            if(Objects.equals(photoclicked, "photoProfile")){
                                image_uri1=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo1")){
                                image_uri2=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo2")){
                                image_uri3=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo3")){
                                image_uri4=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo4")){
                                image_uri5=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo5")){
                                image_uri6=image_uri;
                            }
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo1);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo1);
                            }}
                        break;

                    case "photo2":
                        if(image_uri != null){
                            if(Objects.equals(photoclicked, "photoProfile")){
                                image_uri1=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo1")){
                                image_uri2=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo2")){
                                image_uri3=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo3")){
                                image_uri4=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo4")){
                                image_uri5=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo5")){
                                image_uri6=image_uri;
                            }
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo2);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo2);
                            }}
                        break;
                    case "photo3":
                        if(image_uri != null){
                            if(Objects.equals(photoclicked, "photoProfile")){
                                image_uri1=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo1")){
                                image_uri2=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo2")){
                                image_uri3=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo3")){
                                image_uri4=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo4")){
                                image_uri5=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo5")){
                                image_uri6=image_uri;
                            }
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo3);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo3);
                            }}
                        break;
                    case "photo4":
                        if(image_uri != null){
                            if(Objects.equals(photoclicked, "photoProfile")){
                                image_uri1=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo1")){
                                image_uri2=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo2")){
                                image_uri3=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo3")){
                                image_uri4=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo4")){
                                image_uri5=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo5")){
                                image_uri6=image_uri;
                            }
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo4);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo4);
                            }}
                        break;
                    case "photo5":
                        if(image_uri != null){
                            if(Objects.equals(photoclicked, "photoProfile")){
                                image_uri1=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo1")){
                                image_uri2=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo2")){
                                image_uri3=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo3")){
                                image_uri4=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo4")){
                                image_uri5=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo5")){
                                image_uri6=image_uri;
                            }
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo5);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo5);
                            }}
                        break;
                }
                adicionar4.setVisibility(View.GONE);
                }else{
                    Toast.makeText(AdmServicesActivity.this, R.string.bigimage, Toast.LENGTH_SHORT).show();
                }
            }
        });

        adicionar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getImageSize(getApplicationContext(),image_uri)<550400) {
                    photoClick(photoclicked);
                switch (photoclicked) {
                    case "photo1" :
                        if(image_uri != null) {
                            if(Objects.equals(photoclicked, "photoProfile")){
                                image_uri1=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo1")){
                                image_uri2=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo2")){
                                image_uri3=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo3")){
                                image_uri4=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo4")){
                                image_uri5=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo5")){
                                image_uri6=image_uri;
                            }
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo1);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo1);
                            }}
                        break;

                    case "photo2":
                        if(image_uri != null){
                            if(Objects.equals(photoclicked, "photoProfile")){
                                image_uri1=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo1")){
                                image_uri2=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo2")){
                                image_uri3=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo3")){
                                image_uri4=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo4")){
                                image_uri5=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo5")){
                                image_uri6=image_uri;
                            }
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo2);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo2);
                            }}
                        break;
                    case "photo3":
                        if(image_uri != null){
                            if(Objects.equals(photoclicked, "photoProfile")){
                                image_uri1=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo1")){
                                image_uri2=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo2")){
                                image_uri3=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo3")){
                                image_uri4=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo4")){
                                image_uri5=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo5")){
                                image_uri6=image_uri;
                            }
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo3);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo3);
                            }}
                        break;
                    case "photo4":
                        if(image_uri != null){
                            if(Objects.equals(photoclicked, "photoProfile")){
                                image_uri1=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo1")){
                                image_uri2=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo2")){
                                image_uri3=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo3")){
                                image_uri4=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo4")){
                                image_uri5=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo5")){
                                image_uri6=image_uri;
                            }
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo4);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo4);
                            }}
                        break;
                    case "photo5":
                        if(image_uri != null){
                            if(Objects.equals(photoclicked, "photoProfile")){
                                image_uri1=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo1")){
                                image_uri2=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo2")){
                                image_uri3=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo3")){
                                image_uri4=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo4")){
                                image_uri5=image_uri;
                            }
                            if(Objects.equals(photoclicked, "photo5")){
                                image_uri6=image_uri;
                            }
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo5);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo5);
                            }}
                        break;
                }
                adicionar5.setVisibility(View.GONE);
                }else{
                    Toast.makeText(AdmServicesActivity.this, R.string.bigimage, Toast.LENGTH_SHORT).show();
                }
            }
        });


        photo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickDialog();
                photoclicked = "photo1";
                adicionar1.setVisibility(View.VISIBLE);
            }
        });

        photo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickDialog();
                photoclicked = "photo2";
                adicionar2.setVisibility(View.VISIBLE);
            }
        });

        photo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickDialog();
                    photoclicked = "photo3";
                adicionar3.setVisibility(View.VISIBLE);
            }
        });

        photo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickDialog();
                    photoclicked = "photo4";
                adicionar4.setVisibility(View.VISIBLE);
            }
        });

        photo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickDialog();
                photoclicked = "photo5";
                adicionar5.setVisibility(View.VISIBLE);
            }
        });
    }

    public static float getImageSize(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
            cursor.moveToFirst();
            float imageSize = cursor.getLong(sizeIndex);
            cursor.close();
            return imageSize; // returns size in bytes
        }
        return 0;
    }

    public void  photoClick(String photoclicked){
        Log.d(TAG, "getImageUri "+image_uri);
        if(Objects.equals(photoclicked, "photoProfile")){
            image_uri1=image_uri;
        }
        if(Objects.equals(photoclicked, "photo1")){
            image_uri2=image_uri;
        }
        if(Objects.equals(photoclicked, "photo2")){
            image_uri3=image_uri;
        }
        if(Objects.equals(photoclicked, "photo3")){
            image_uri4=image_uri;
        }
        if(Objects.equals(photoclicked, "photo4")){
            image_uri5=image_uri;
        }
        if(Objects.equals(photoclicked, "photo5")){
            image_uri6=image_uri;
        }
    }

    private void showImagePickDialog() {
        ImagePicker.Companion.with(AdmServicesActivity.this)
                .crop()
                .cropOval()
                .maxResultSize(512,512,true)
                .provider(ImageProvider.BOTH) //Or bothCameraGallery()
                .createIntentFromDialog((Function1)(new Function1(){
                    public Object invoke(Object var1){
                        this.invoke((Intent)var1);
                        return Unit.INSTANCE;
                    }


                    public final void invoke(@NotNull Intent it){
                        Intrinsics.checkNotNullParameter(it,"it");
                        launcher.launch(it);
                    }

                }));

    }

    String[] serviceCategories = {
            "Promoções",
            "Nutricionistas",
            "Médicos",
            "Cabeleireiros & Barbeiros",
            "Tatuadores",
            "Lazer & Hospedagens",
            "Alimentícios",
            "Mais"
    };

    String[] servicePort = {
            "Promoções",
            "Nutricionistas",
            "Médicos",
            "Cabeleireiros & Barbeiros",
            "Tatuadores",
            "Lazer",
            "Alimentícios",
            "Mais"
    };

    private void categoryDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme);
            builder.setTitle(R.string.categoria)
                    .setItems(serviceCategories, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            String category = serviceCategories[which];
                            categoryTv.setText(category);
                            catego = servicePort[which];
                        }
                    })
                    .show();
        }
}
