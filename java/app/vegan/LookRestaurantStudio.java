package app.vegan;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_MEDIA_IMAGES;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.os.Vibrator;
import android.widget.CompoundButton.OnCheckedChangeListener;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class LookRestaurantStudio extends AppCompatActivity {

    private ImageView photo1, photo2, photo3, photo4, photo5, photoCover;
    private EditText edit_description, daysEt, timetableEt, addressEt, deliveryFeeEt, timeDeliveryEt, celEt, contaEt,
    segOpen, terOpen, quaOpen, quiOpen, sexOpen, sabOpen, domOpen, segClose, terClose,
    quaClose, quiClose,sexClose,sabClose,domClose,importedFeeEt, nationalFeeEt, deliveryFixoEt, edit_cep;
    private FloatingActionButton saveProfileInfo;
    private SwitchCompat petSealSwitch, accessSwitch, vegSwitch;
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private static final int IMAGE_PICK_CAMERA_CODE = 500;
    private String[] cameraPermissions;
    private String[] storagePermissions;
    private Uri image_uri, image_uri1, image_uri2, image_uri3, image_uri4, image_uri5, image_uri6;
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    private String photoclicked;
    private ImageButton backBtn;
    private ProgressDialog progressDialog;
    private Boolean imageOk;
    private TextView adicionar, adicionar1, adicionar2, adicionar3, adicionar4, adicionar5;
    private CheckBox segunda, terca, quarta, quinta, sexta, sabado, domingo, dinheiroEnt, pixEnt, cartEnt;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_restaurant_studio);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        edit_cep = findViewById(R.id.edit_cep);
        dinheiroEnt = findViewById(R.id.dinheiroEnt);
        pixEnt = findViewById(R.id.pixEnt);
        cartEnt = findViewById(R.id.cartEnt);
        deliveryFixoEt = findViewById(R.id.deliveryFixoEt);
        nationalFeeEt = findViewById(R.id.nationalFeeEt);
        importedFeeEt = findViewById(R.id.importedFeeEt);
        segunda = findViewById(R.id.segunda);
        terca = findViewById(R.id.terca);
        quarta = findViewById(R.id.quarta);
        quinta = findViewById(R.id.quinta);
        sexta = findViewById(R.id.sexta);
        sabado = findViewById(R.id.sabado);
        domingo = findViewById(R.id.domingo);
        segOpen = findViewById(R.id.segOpen);
        terOpen = findViewById(R.id.terOpen);
        quaOpen = findViewById(R.id.quaOpen);
        quiOpen = findViewById(R.id.quiOpen);
        sexOpen = findViewById(R.id.sexOpen);
        sabOpen = findViewById(R.id.sabOpen);
        domOpen = findViewById(R.id.domOpen);
        segClose = findViewById(R.id.segClose);
        terClose = findViewById(R.id.terClose);
        quaClose = findViewById(R.id.quaClose);
        quiClose = findViewById(R.id.quiClose);
        sexClose = findViewById(R.id.sexClose);
        sabClose = findViewById(R.id.sabClose);
        domClose = findViewById(R.id.domClose);
        contaEt = findViewById(R.id.contaEt);
        celEt = findViewById(R.id.celEt);
        deliveryFeeEt = findViewById(R.id.deliveryFeeEt);
        timeDeliveryEt = findViewById(R.id.timeDeliveryEt);
        backBtn = findViewById(R.id.backBtn);
        adicionar = findViewById(R.id.adicionar);
        adicionar1 = findViewById(R.id.adicionar2);
        adicionar2 = findViewById(R.id.adicionar1);
        adicionar3 = findViewById(R.id.adicionar4);
        adicionar4 = findViewById(R.id.adicionar3);
        adicionar5 = findViewById(R.id.adicionar5);
        photo5 = findViewById(R.id.imageVw);
        addressEt = findViewById(R.id.edit_address);
        accessSwitch = findViewById(R.id.accessSwitch);
        petSealSwitch = findViewById(R.id.petSwitch);
        vegSwitch = findViewById(R.id.vegSwitch);
        saveProfileInfo = findViewById(R.id.saveProfile);
        edit_description = findViewById(R.id.edit_description);
        daysEt = findViewById(R.id.daysEt);
        timetableEt = findViewById(R.id.timetableEt);
        photoCover = findViewById(R.id.imageView75);
        photo1 = findViewById(R.id.imageViw75);
        photo2 = findViewById(R.id.imageVi75);
        photo3 = findViewById(R.id.imageVw3);
        photo4 = findViewById(R.id.imageVw75);
        photo5 = findViewById(R.id.imageVw);

        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        ActivityCompat.requestPermissions(this,
                new String[]{CAMERA, READ_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);


        ActivityCompat.requestPermissions(this,
                new String[]{READ_MEDIA_IMAGES, WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);

        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        imageOk = false;

        loadInformation();

        launcher=
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),(ActivityResult result)->{
                    if(result.getResultCode()==RESULT_OK){
                        image_uri = result.getData().getData();
                        if(Objects.equals(photoclicked, "photo")){
                            photoCover.setImageURI(image_uri);
                        }
                        if(Objects.equals(photoclicked, "photo1")){
                            photo1.setImageURI(image_uri);
                        }
                        if(Objects.equals(photoclicked, "photo2")){
                            photo2.setImageURI(image_uri);
                        }
                        if(Objects.equals(photoclicked, "photo3")){
                            photo3.setImageURI(image_uri);
                        }
                        if(Objects.equals(photoclicked, "photo4")){
                            photo4.setImageURI(image_uri);
                        }
                        if(Objects.equals(photoclicked, "photo5")){
                            photo5.setImageURI(image_uri);
                        }
                    }else if(result.getResultCode()== ImagePicker.RESULT_ERROR){
                        Toast.makeText(LookRestaurantStudio.this, result.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.aguardeum);
        progressDialog.setCanceledOnTouchOutside(false);

        segunda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                segOpen.setVisibility(View.VISIBLE);
                segClose.setVisibility(View.VISIBLE);
                }
                else{
                    segOpen.setVisibility(View.GONE);
                    segClose.setVisibility(View.GONE);
                }
            }
        });

        terca.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    terOpen.setVisibility(View.VISIBLE);
                    terClose.setVisibility(View.VISIBLE);
                }
                else{
                    terOpen.setVisibility(View.GONE);
                    terClose.setVisibility(View.GONE);
                }
            }
        });

        quarta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    quaOpen.setVisibility(View.VISIBLE);
                    quaClose.setVisibility(View.VISIBLE);
                }
                else{
                    quaOpen.setVisibility(View.GONE);
                    quaClose.setVisibility(View.GONE);
                }
            }
        });

        quinta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    quiOpen.setVisibility(View.VISIBLE);
                    quiClose.setVisibility(View.VISIBLE);
                }
                else{
                    quiOpen.setVisibility(View.GONE);
                    quiClose.setVisibility(View.GONE);
                }
            }
        });

        sexta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sexOpen.setVisibility(View.VISIBLE);
                    sexClose.setVisibility(View.VISIBLE);
                }
                else{
                    sexOpen.setVisibility(View.GONE);
                    sexClose.setVisibility(View.GONE);
                }
            }
        });

        sabado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sabOpen.setVisibility(View.VISIBLE);
                    sabClose.setVisibility(View.VISIBLE);
                }
                else{
                    sabOpen.setVisibility(View.GONE);
                    sabClose.setVisibility(View.GONE);
                }
            }
        });

        domingo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    domOpen.setVisibility(View.VISIBLE);
                    domClose.setVisibility(View.VISIBLE);
                }
                else{
                    domOpen.setVisibility(View.GONE);
                    domClose.setVisibility(View.GONE);
                }
            }
        });


        vegSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                vegSwitch.setChecked(isChecked);
            }
        });

        accessSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                accessSwitch.setChecked(isChecked);
            }
        });

        petSealSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                petSealSwitch.setChecked(isChecked);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        saveProfileInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInfo();
            }
        });

        photoCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickDialog();
                photoclicked = "photo";
                adicionar.setVisibility(View.VISIBLE);
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
                showImagePickDialog();//
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

        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getImageSize(getApplicationContext(),image_uri)<550400) {
                    photoClick(photoclicked);
                switch (photoclicked) {
                    case "photo" :
                        if(image_uri != null) {
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photoCover);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photoCover);
                            }}
                        break;

                    case "photo1" :
                        if(image_uri != null) {
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo1);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo1);
                            }}
                        break;

                    case "photo2":
                        if(image_uri != null){
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo2);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo2);
                            }}
                        break;
                    case "photo3":
                        if(image_uri != null){
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo3);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo3);
                            }}
                        break;
                    case "photo4":
                        if(image_uri != null){
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo4);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo4);
                            }}
                        break;
                    case "photo5":
                        if(image_uri != null){
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo5);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo5);
                            }}
                        break;
                }
                adicionar1.setVisibility(View.GONE);
                }else{
                    Toast.makeText(LookRestaurantStudio.this, R.string.bigimage, Toast.LENGTH_SHORT).show();
                }
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
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo1);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo1);
                            }}
                        break;

                    case "photo2":
                        if(image_uri != null){
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo2);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo2);
                            }}
                        break;
                    case "photo3":
                        if(image_uri != null){
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo3);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo3);
                            }}
                        break;
                    case "photo4":
                        if(image_uri != null){
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo4);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo4);
                            }}
                        break;
                    case "photo5":
                        if(image_uri != null){
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo5);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo5);
                            }}
                        break;
                }
                adicionar1.setVisibility(View.GONE);
            }else{
                Toast.makeText(LookRestaurantStudio.this, R.string.bigimage, Toast.LENGTH_SHORT).show();
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
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo1);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo1);
                            }}
                        break;

                    case "photo2":
                        if(image_uri != null){
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo2);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo2);
                            }}
                        break;
                    case "photo3":
                        if(image_uri != null){
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo3);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo3);
                            }}
                        break;
                    case "photo4":
                        if(image_uri != null){
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo4);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo4);
                            }}
                        break;
                    case "photo5":
                        if(image_uri != null){
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo5);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo5);
                            }}
                        break;
                }
                adicionar2.setVisibility(View.GONE);
                }else{
                    Toast.makeText(LookRestaurantStudio.this, R.string.bigimage, Toast.LENGTH_SHORT).show();
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
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo1);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo1);
                            }}
                        break;

                    case "photo2":
                        if(image_uri != null){
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo2);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo2);
                            }}
                        break;
                    case "photo3":
                        if(image_uri != null){
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo3);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo3);
                            }}
                        break;
                    case "photo4":
                        if(image_uri != null){
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo4);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo4);
                            }}
                        break;
                    case "photo5":
                        if(image_uri != null){
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo5);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo5);
                            }}
                        break;
                }
                adicionar3.setVisibility(View.GONE);
                }else{
                    Toast.makeText(LookRestaurantStudio.this, R.string.bigimage, Toast.LENGTH_SHORT).show();
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
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo1);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo1);
                            }}
                        break;

                    case "photo2":
                        if(image_uri != null){
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo2);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo2);
                            }}
                        break;
                    case "photo3":
                        if(image_uri != null){
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo3);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo3);
                            }}
                        break;
                    case "photo4":
                        if(image_uri != null){
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo4);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo4);
                            }}
                        break;
                    case "photo5":
                        if(image_uri != null){
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo5);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo5);
                            }}
                        break;
                }
                adicionar4.setVisibility(View.GONE);
                }else{
                    Toast.makeText(LookRestaurantStudio.this, R.string.bigimage, Toast.LENGTH_SHORT).show();
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
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo1);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo1);
                            }}
                        break;

                    case "photo2":
                        if(image_uri != null){
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo2);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo2);
                            }}
                        break;
                    case "photo3":
                        if(image_uri != null){
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo3);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo3);
                            }}
                        break;
                    case "photo4":
                        if(image_uri != null){
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo4);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo4);
                            }}
                        break;
                    case "photo5":
                        if(image_uri != null){
                            try {
                                Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(photo5);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(photo5);
                            }}
                        break;
                }
                adicionar5.setVisibility(View.GONE);
                }else{
                    Toast.makeText(LookRestaurantStudio.this, R.string.bigimage, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void photoClick(String photoclicked){
            Log.d(TAG, "getImageUri "+image_uri);
            if(Objects.equals(photoclicked, "photo")){
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


    private void loadInformation() {
        Intent data = getIntent();
       String uuid = firebaseAuth.getCurrentUser().getUid();

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection("Adm's").document(uuid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String neigh = document.getString("neighborhood");
                        String address = document.getString("street");
                        String number = document.getString("number");
                        String description = document.getString("description");
                        String timetable = document.getString("timetable");
                        String addressLook = document.getString("addressProfile");
                        String petfriendly = document.getString("petfriendly");
                        String accessibility = document.getString("accessibility");
                        String vegan = document.getString("vegan");
                        String daytable = document.getString("daytable");
                        String fee = document.getString("deliveryFee");
                        String delFixo = document.getString("deliveryFixo");
                        String cepLoad = document.getString("cep");

                        String nationalFee = document.getString("nationalFee");
                        String importedFee = document.getString("importedFee");
                        String cel = document.getString("cel");
                        String contaBancaria = document.getString("contaBancaria");
                        String timeDelivery = document.getString("timeDelivery");
                        String ph = document.getString("photo");
                        String ph1 = document.getString("photo1");
                        String ph2 = document.getString("photo2");
                        String ph3 = document.getString("photo3");
                        String ph4 = document.getString("photo4");
                        String ph5 = document.getString("photo5");

                        String segundou = document.getString("segunda");
                        String tercou = document.getString("terca");
                        String quartou = document.getString("quarta");
                        String quintou = document.getString("quinta");
                        String sextou = document.getString("sexta");
                        String sabadou = document.getString("sabado");
                        String domingou = document.getString("domingo");

                        String segabrio = document.getString("segundaAbriu");
                        String terabrio = document.getString("tercaAbriu");
                        String quaabrio = document.getString("quartaAbriu");
                        String quiabrio = document.getString("quintaAbriu");
                        String sexabrio = document.getString("sextaAbriu");
                        String sababrio = document.getString("sabadoAbriu");
                        String domabrio = document.getString("domingoAbriu");

                        String segfecho = document.getString("segundaFechou");
                        String terfecho = document.getString("tercaFechou");
                        String quafecho = document.getString("quartaFechou");
                        String quifecho = document.getString("quintaFechou");
                        String sexfecho = document.getString("sextaFechou");
                        String sabfecho = document.getString("sabadoFechou");
                        String domfecho = document.getString("domingoFechou");

                        String dinheiro = document.getString("dinheiroEnt");
                        String pix = document.getString("pixEnt");
                        String cartao = document.getString("cartaoEnt");

                        if(Objects.equals(dinheiro, "true")){
                            dinheiroEnt.setChecked(true);
                        }
                        if(Objects.equals(pix, "true")){
                            pixEnt.setChecked(true);
                        }
                        if(Objects.equals(cartao, "true")){
                            cartEnt.setChecked(true);
                        }

                        if (Objects.equals(segundou, "true")){
                            segunda.setChecked(true);
                            segOpen.setVisibility(View.VISIBLE);
                            segClose.setVisibility(View.VISIBLE);
                            segOpen.setText(segabrio);
                            segClose.setText(segfecho);
                        }
                        if (Objects.equals(tercou, "true")){
                            terca.setChecked(true);
                            terOpen.setVisibility(View.VISIBLE);
                            terClose.setVisibility(View.VISIBLE);
                            terOpen.setText(terabrio);
                            terClose.setText(terfecho);
                        } if (Objects.equals(quartou, "true")){
                            quarta.setChecked(true);
                            quaOpen.setVisibility(View.VISIBLE);
                            quaClose.setVisibility(View.VISIBLE);
                            quaOpen.setText(quaabrio);
                            quaClose.setText(quafecho);
                        } if (Objects.equals(quintou, "true")){
                            quinta.setChecked(true);
                            quiOpen.setVisibility(View.VISIBLE);
                            quiClose.setVisibility(View.VISIBLE);
                            quiOpen.setText(quiabrio);
                            quiClose.setText(quifecho);
                        } if (Objects.equals(sextou, "true")){
                            sexta.setChecked(true);
                            sexOpen.setVisibility(View.VISIBLE);
                            sexClose.setVisibility(View.VISIBLE);
                            sexOpen.setText(sexabrio);
                            sexClose.setText(sexfecho);
                        } if (Objects.equals(sabadou, "true")){
                            sabado.setChecked(true);
                            sabOpen.setVisibility(View.VISIBLE);
                            sabClose.setVisibility(View.VISIBLE);
                            sabOpen.setText(sababrio);
                            sabClose.setText(sabfecho);
                        } if (Objects.equals(domingou, "true")){
                            domingo.setChecked(true);
                            domOpen.setVisibility(View.VISIBLE);
                            domClose.setVisibility(View.VISIBLE);
                            domOpen.setText(domabrio);
                            domClose.setText(domfecho);
                        }

                        edit_cep.setText(cepLoad);
                        deliveryFixoEt.setText(delFixo);
                        nationalFeeEt.setText(nationalFee);
                        importedFeeEt.setText(importedFee);
                        edit_description.setText(description);
                        timetableEt.setText(timetable);
                        daysEt.setText(daytable);
                        deliveryFeeEt.setText(fee);
                        celEt.setText(cel);
                        timeDeliveryEt.setText(timeDelivery);
                        contaEt.setText(contaBancaria);

                        if(Objects.equals(addressLook, "")) {
                            addressEt.setText(neigh + ", " + address + ", " + number);
                        } else {
                            addressEt.setText(addressLook);
                        }

                        if(petfriendly.equals("true")){
                        petSealSwitch.setChecked(true);}
                        if(accessibility.equals("true")){
                        accessSwitch.setChecked(true);}
                        if(vegan.equals("true")){
                        vegSwitch.setChecked(true);}

                        try {
                            Picasso.get().load(ph).placeholder(R.drawable.loading).into(photoCover);
                        } catch (Exception e) {
                            photoCover.setImageResource(R.drawable.error);
                        }
                        if(ph.equals("")){
                            try {
                                Picasso.get().load(R.drawable.ic_baseline_add_a_photo_24).placeholder(R.drawable.ic_baseline_add_a_photo_24).into(photoCover);
                            } catch (Exception e) {
                                photoCover.setImageResource(R.drawable.error);
                            }
                        }

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

                    }
                }
            }
        });
    }

    private void saveInfo() {
        progressDialog.setMessage(getString(R.string.aguardeum));
        progressDialog.show();
        Boolean dinheiroMet = dinheiroEnt.isChecked();
        Boolean pixMet = pixEnt.isChecked();
        Boolean cartaoMet = cartEnt.isChecked();

        Boolean segu  = segunda.isChecked();
        Boolean terc  = terca.isChecked();
        Boolean quar  = quarta.isChecked();
        Boolean quin  = quinta.isChecked();
        Boolean sext  = sexta.isChecked();
        Boolean saba  = sabado.isChecked();
        Boolean domi  = domingo.isChecked();

        String segun = "";
        String tercc = "";
        String quart = "";
        String quint = "";
        String sexxt = "";
        String sabad = "";
        String domin = "";

        String dinEn = "";
        String pixEn = "";
        String cartaoEn = "";

        if(cartaoMet){
            cartaoEn="true";
        }

        if(pixMet){
            pixEn="true";
        }

        if(dinheiroMet){
            dinEn="true";
        }

        if(segu){
            segun = "true";
        }
        if(terc){
            tercc = "true";
        }
        if(quar){
            quart = "true";
        }
        if(quin){
            quint = "true";
        }
        if(sext){
            sexxt = "true";
        }
        if(saba){
            sabad = "true";
        }
        if(domi){
            domin = "true";
        }

        String description = edit_description.getText().toString().trim();
        String timetable = timetableEt.getText().toString().trim();
        String daytable = daysEt.getText().toString().trim();
        Boolean petfriendly = petSealSwitch.isChecked();
        String petf = "";
        if(petfriendly){
             petf = "true";
        }
        Boolean vegan = vegSwitch.isChecked();
        String veg = "";
        if(vegan){
            veg = "true";
        }
        Boolean accessibility = accessSwitch.isChecked();
        String acc = "";
        if(accessibility){
            acc = "true";
        }
        String addressProfile = addressEt.getText().toString().trim();
        String deliveryfee = deliveryFeeEt.getText().toString();
        String timeDelivery = timeDeliveryEt.getText().toString();
        String cel = celEt.getText().toString();
        String conta = contaEt.getText().toString();

        String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        photoCover.setImageURI(image_uri);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("nationalFee", nationalFeeEt.getText().toString());
        hashMap.put("importedFee", importedFeeEt.getText().toString());
        hashMap.put("dinheiroEnt", dinEn);
        hashMap.put("cep", edit_cep.getText().toString());
        hashMap.put("pixEnt", pixEn);
        hashMap.put("cartaoEnt", cartaoEn);
        hashMap.put("segunda", segun);
        hashMap.put("terca", tercc);
        hashMap.put("quarta", quart);
        hashMap.put("quinta", quint);
        hashMap.put("sexta", sexxt);
        hashMap.put("sabado", sabad);
        hashMap.put("domingo", domin);
        hashMap.put("segundaAbriu", segOpen.getText().toString());
        hashMap.put("tercaAbriu", terOpen.getText().toString());
        hashMap.put("quartaAbriu", quaOpen.getText().toString());
        hashMap.put("quintaAbriu", quiOpen.getText().toString());
        hashMap.put("sextaAbriu", sexOpen.getText().toString());
        hashMap.put("sabadoAbriu", sabOpen.getText().toString());
        hashMap.put("domingoAbriu", domOpen.getText().toString());
        hashMap.put("segundaFechou", segClose.getText().toString());
        hashMap.put("tercaFechou", terClose.getText().toString());
        hashMap.put("quartaFechou", quaClose.getText().toString());
        hashMap.put("quintaFechou", quiClose.getText().toString());
        hashMap.put("sextaFechou", sexClose.getText().toString());
        hashMap.put("sabadoFechou", sabClose.getText().toString());
        hashMap.put("domingoFechou", domClose.getText().toString());
        hashMap.put("timetable", timetable);
        hashMap.put("deliveryFee", deliveryfee);
        hashMap.put("deliveryFixo", deliveryFixoEt.getText().toString());
        hashMap.put("timeDelivery", timeDelivery);
        hashMap.put("daytable", daytable);
        hashMap.put("description", description);
        hashMap.put("vegan", veg);
        hashMap.put("accessibility", acc);
        hashMap.put("petfriendly", petf);
        hashMap.put("addressProfile", addressProfile);
        hashMap.put("contaBancaria", conta);
        hashMap.put("cel", cel);
        if(image_uri1!=null){
            final String timestamp = "" + System.currentTimeMillis();
            String filePathAndName3 = photoclicked + "/" + "" + timestamp;
            StorageReference storageReference3 = FirebaseStorage.getInstance().getReference(filePathAndName3);
            storageReference3.putFile(image_uri1)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;
                            Uri downloadImageUri = uriTask.getResult();

                            String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                            DocumentReference documentReference = fStore.collection("Adm's").document(userID);
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("photo", downloadImageUri);
                            documentReference.update(hashMap).
                                    addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void avoid) {
                                            Toast.makeText(LookRestaurantStudio.this, R.string.fotoad, Toast.LENGTH_SHORT).show();

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

                                                                String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                                                                DocumentReference documentReference = fStore.collection("Adm's").document(userID);
                                                                HashMap<String, Object> hashMap = new HashMap<>();
                                                                hashMap.put("photo1", downloadImageUri);
                                                                documentReference.update(hashMap).
                                                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void avoid) {
                                                                                Toast.makeText(LookRestaurantStudio.this, R.string.fotoad, Toast.LENGTH_SHORT).show();


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

                                                                                                    String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                                                                                                    DocumentReference documentReference = fStore.collection("Adm's").document(userID);
                                                                                                    HashMap<String, Object> hashMap = new HashMap<>();
                                                                                                    hashMap.put("photo2", downloadImageUri);
                                                                                                    documentReference.update(hashMap).
                                                                                                            addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                @Override
                                                                                                                public void onSuccess(Void avoid) {
                                                                                                                    Toast.makeText(LookRestaurantStudio.this, R.string.fotoad, Toast.LENGTH_SHORT).show();

                                                                                                                    if(image_uri4!=null){
                                                                                                                        final String timestamp = "" + System.currentTimeMillis();
                                                                                                                        String filePathAndName3 = photoclicked + "/" + "" + timestamp;
                                                                                                                        StorageReference storageReference3 = FirebaseStorage.getInstance().getReference(filePathAndName3);
                                                                                                                        storageReference3.putFile(image_uri4)
                                                                                                                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                                                                    @Override
                                                                                                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                                                                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                                                                                                                        while (!uriTask.isSuccessful()) ;
                                                                                                                                        Uri downloadImageUri = uriTask.getResult();

                                                                                                                                        String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                                                                                                                                        DocumentReference documentReference = fStore.collection("Adm's").document(userID);
                                                                                                                                        HashMap<String, Object> hashMap = new HashMap<>();
                                                                                                                                        hashMap.put("photo3", downloadImageUri);
                                                                                                                                        documentReference.update(hashMap).
                                                                                                                                                addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                                                    @Override
                                                                                                                                                    public void onSuccess(Void avoid) {
                                                                                                                                                        Toast.makeText(LookRestaurantStudio.this, R.string.fotoad, Toast.LENGTH_SHORT).show();


                                                                                                                                                        if(image_uri5!=null){
                                                                                                                                                            final String timestamp = "" + System.currentTimeMillis();
                                                                                                                                                            String filePathAndName3 = photoclicked + "/" + "" + timestamp;
                                                                                                                                                            StorageReference storageReference3 = FirebaseStorage.getInstance().getReference(filePathAndName3);
                                                                                                                                                            storageReference3.putFile(image_uri5)
                                                                                                                                                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                                                                                                        @Override
                                                                                                                                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                                                                                                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                                                                                                                                                            while (!uriTask.isSuccessful()) ;
                                                                                                                                                                            Uri downloadImageUri = uriTask.getResult();

                                                                                                                                                                            String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                                                                                                                                                                            DocumentReference documentReference = fStore.collection("Adm's").document(userID);
                                                                                                                                                                            HashMap<String, Object> hashMap = new HashMap<>();
                                                                                                                                                                            hashMap.put("photo4", downloadImageUri);
                                                                                                                                                                            documentReference.update(hashMap).
                                                                                                                                                                                    addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                                                                                        @Override
                                                                                                                                                                                        public void onSuccess(Void avoid) {
                                                                                                                                                                                            Toast.makeText(LookRestaurantStudio.this, R.string.fotoad, Toast.LENGTH_SHORT).show();

                                                                                                                                                                                            if(image_uri6!=null){
                                                                                                                                                                                                final String timestamp = "" + System.currentTimeMillis();
                                                                                                                                                                                                String filePathAndName3 = photoclicked + "/" + "" + timestamp;
                                                                                                                                                                                                StorageReference storageReference3 = FirebaseStorage.getInstance().getReference(filePathAndName3);
                                                                                                                                                                                                storageReference3.putFile(image_uri6)
                                                                                                                                                                                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                                                                                                                                            @Override
                                                                                                                                                                                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                                                                                                                                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                                                                                                                                                                                                while (!uriTask.isSuccessful()) ;
                                                                                                                                                                                                                Uri downloadImageUri = uriTask.getResult();

                                                                                                                                                                                                                String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                                                                                                                                                                                                                DocumentReference documentReference = fStore.collection("Adm's").document(userID);
                                                                                                                                                                                                                HashMap<String, Object> hashMap = new HashMap<>();
                                                                                                                                                                                                                hashMap.put("photo5", downloadImageUri);
                                                                                                                                                                                                                documentReference.update(hashMap).
                                                                                                                                                                                                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                                                                                                                            @Override
                                                                                                                                                                                                                            public void onSuccess(Void avoid) {
                                                                                                                                                                                                                                Toast.makeText(LookRestaurantStudio.this, "Informações Atualizadas", Toast.LENGTH_SHORT).show();
                                                                                                                                                                                                                                progressDialog.dismiss();
                                                                                                                                                                                                                                startVibrate(100);
                                                                                                                                                                                                                            }
                                                                                                                                                                                                                        })
                                                                                                                                                                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                                                                                                                                                                            @Override
                                                                                                                                                                                                                            public void onFailure(@NonNull Exception e) {
                                                                                                                                                                                                                                Toast.makeText(LookRestaurantStudio.this, "erro " + e, Toast.LENGTH_SHORT).show();
                                                                                                                                                                                                                            }
                                                                                                                                                                                                                        });
                                                                                                                                                                                                            }
                                                                                                                                                                                                        }).addOnFailureListener(new OnFailureListener() {
                                                                                                                                                                                                            @Override
                                                                                                                                                                                                            public void onFailure(@NonNull Exception e) {
                                                                                                                                                                                                                Toast.makeText(LookRestaurantStudio.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                                                                                                                                            }
                                                                                                                                                                                                        });        }


                                                                                                                                                                                        }
                                                                                                                                                                                    })
                                                                                                                                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                                                                                                                                        @Override
                                                                                                                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                                                                                                                            Toast.makeText(LookRestaurantStudio.this, "erro " + e, Toast.LENGTH_SHORT).show();
                                                                                                                                                                                        }
                                                                                                                                                                                    });
                                                                                                                                                                        }
                                                                                                                                                                    }).addOnFailureListener(new OnFailureListener() {
                                                                                                                                                                        @Override
                                                                                                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                                                                                                            Toast.makeText(LookRestaurantStudio.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                                                                                                        }
                                                                                                                                                                    });        }

                                                                                                                                                        else {
                                                                                                                                                            Toast.makeText(LookRestaurantStudio.this, "Informações Atualizadas", Toast.LENGTH_SHORT).show();
                                                                                                                                                            progressDialog.dismiss();
                                                                                                                                                            startVibrate(100);
                                                                                                                                                        }

                                                                                                                                                    }
                                                                                                                                                })
                                                                                                                                                .addOnFailureListener(new OnFailureListener() {
                                                                                                                                                    @Override
                                                                                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                                                                                        Toast.makeText(LookRestaurantStudio.this, "erro " + e, Toast.LENGTH_SHORT).show();
                                                                                                                                                    }
                                                                                                                                                });
                                                                                                                                    }
                                                                                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                                                                                    @Override
                                                                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                                                                        Toast.makeText(LookRestaurantStudio.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                                                                    }
                                                                                                                                });        }

                                                                                                                    else {
                                                                                                                        Toast.makeText(LookRestaurantStudio.this, "Informações Atualizadas", Toast.LENGTH_SHORT).show();
                                                                                                                        progressDialog.dismiss();
                                                                                                                        startVibrate(100);
                                                                                                                    }


                                                                                                                }
                                                                                                            })
                                                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                                                @Override
                                                                                                                public void onFailure(@NonNull Exception e) {
                                                                                                                    Toast.makeText(LookRestaurantStudio.this, "erro " + e, Toast.LENGTH_SHORT).show();
                                                                                                                }
                                                                                                            });
                                                                                                }
                                                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                                                @Override
                                                                                                public void onFailure(@NonNull Exception e) {
                                                                                                    Toast.makeText(LookRestaurantStudio.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            });        }
                                                                                else {
                                                                                    Toast.makeText(LookRestaurantStudio.this, "Informações Atualizadas", Toast.LENGTH_SHORT).show();
                                                                                    progressDialog.dismiss();
                                                                                    startVibrate(100);
                                                                                }

                                                                            }
                                                                        })
                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                Toast.makeText(LookRestaurantStudio.this, "erro " + e, Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(LookRestaurantStudio.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });        }
                                            else {
                                                Toast.makeText(LookRestaurantStudio.this, "Informações Atualizadas", Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                                startVibrate(100);
                                            }



                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(LookRestaurantStudio.this, "erro " + e, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LookRestaurantStudio.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        DocumentReference reference = fStore.collection("Adm's").document(userID);
        reference.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if(image_uri1==null) {
                    Toast.makeText(LookRestaurantStudio.this, "Informações Atualizadas", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    startVibrate(100);
                }
            } }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LookRestaurantStudio.this, "erro " + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void startVibrate ( long time ) {
        Vibrator atvib = ( Vibrator ) getSystemService ( Context.VIBRATOR_SERVICE );
        assert atvib != null;
        atvib.vibrate ( time );
    }

    void showImagePickDialog() {
        ImagePicker.Companion.with(LookRestaurantStudio.this)
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

    private void pickFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
        progressDialog.setMessage(getString(R.string.aguardeum));
        progressDialog.show();
    }

    private void pickFromCamera(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE,"Temp_Image_Title");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Temp_Image_Description");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent intent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
        progressDialog.setMessage(getString(R.string.aguardeum));
        progressDialog.show();
    }

    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted){
                        pickFromCamera();
                    }
                    else {
                        Toast.makeText(this, R.string.permisscame, Toast.LENGTH_SHORT).show();
                    }

                }
            }
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted){
                        pickFromGallery();
                    }else{
                        Toast.makeText(this, R.string.permissarm, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if(resultCode == RESULT_OK){
            progressDialog.dismiss();

            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                image_uri = data.getData();
                if(Objects.equals(photoclicked, "photo")){
                    photoCover.setImageURI(image_uri);
                }
                if(Objects.equals(photoclicked, "photo1")){
                    photo1.setImageURI(image_uri);
                }
                if(Objects.equals(photoclicked, "photo2")){
                    photo2.setImageURI(image_uri);
                }
                if(Objects.equals(photoclicked, "photo3")){
                    photo3.setImageURI(image_uri);
                }
                if(Objects.equals(photoclicked, "photo4")){
                    photo4.setImageURI(image_uri);
                }
                if(Objects.equals(photoclicked, "photo5")){
                    photo5.setImageURI(image_uri);
                }
            }
            else if(requestCode == IMAGE_PICK_CAMERA_CODE){
                if(Objects.equals(photoclicked, "photo")){
                    photoCover.setImageURI(image_uri);
                }
                if(Objects.equals(photoclicked, "photo1")){
                    photo1.setImageURI(image_uri);
                }
                if(Objects.equals(photoclicked, "photo2")){
                    photo2.setImageURI(image_uri);
                }
                if(Objects.equals(photoclicked, "photo3")){
                    photo3.setImageURI(image_uri);
                }
                if(Objects.equals(photoclicked, "photo4")){
                    photo4.setImageURI(image_uri);
                }
                if(Objects.equals(photoclicked, "photo5")){
                    photo5.setImageURI(image_uri);
                }
            }
        }
        progressDialog.dismiss();
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setPhoto1(){
        photo1.setImageURI(image_uri);
        final String timestamp = "" + System.currentTimeMillis();
        String filePathAndName = "photo1/" + "" + timestamp;
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
        storageReference.putFile(image_uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        Uri downloadImageUri = uriTask.getResult();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LookRestaurantStudio.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        DocumentReference documentReference = fStore.collection("Adm's").document(userID);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("photo1", image_uri);
        documentReference.update(hashMap).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void avoid){
                        Toast.makeText(LookRestaurantStudio.this, R.string.fotoad, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LookRestaurantStudio.this, "erro " + e, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setPhoto2(){
        photo2.setImageURI(image_uri);
        final String timestamp = "" + System.currentTimeMillis();
        String filePathAndName = "photo2/" + "" + timestamp;
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
        storageReference.putFile(image_uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        Uri downloadImageUri = uriTask.getResult();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LookRestaurantStudio.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        DocumentReference documentReference = fStore.collection("Adm's").document(userID);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("photo2", image_uri);
        documentReference.update(hashMap).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void avoid){
                        Toast.makeText(LookRestaurantStudio.this, R.string.fotoad, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LookRestaurantStudio.this, "erro " + e, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setPhoto3(){
        photo3.setImageURI(image_uri);
        final String timestamp = "" + System.currentTimeMillis();
        String filePathAndName = "photo3/" + "" + timestamp;
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
        storageReference.putFile(image_uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        Uri downloadImageUri = uriTask.getResult();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LookRestaurantStudio.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        DocumentReference documentReference = fStore.collection("Adm's").document(userID);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("photo3", image_uri);
        documentReference.update(hashMap).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void avoid){
                        Toast.makeText(LookRestaurantStudio.this, R.string.fotoad, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LookRestaurantStudio.this, "erro " + e, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setPhoto4(){
        photo4.setImageURI(image_uri);
        final String timestamp = "" + System.currentTimeMillis();
        String filePathAndName = "photo4/" + "" + timestamp;
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
        storageReference.putFile(image_uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        Uri downloadImageUri = uriTask.getResult();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LookRestaurantStudio.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        DocumentReference documentReference = fStore.collection("Adm's").document(userID);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("photo4", image_uri);
        documentReference.update(hashMap).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void avoid){
                        Toast.makeText(LookRestaurantStudio.this, R.string.fotoad, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LookRestaurantStudio.this, "erro " + e, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setPhoto5(){
        photo5.setImageURI(image_uri);
        final String timestamp = "" + System.currentTimeMillis();
        String filePathAndName = "photo5/" + "" + timestamp;
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
        storageReference.putFile(image_uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        Uri downloadImageUri = uriTask.getResult();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LookRestaurantStudio.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        DocumentReference documentReference = fStore.collection("Adm's").document(userID);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("photo5", image_uri);
        documentReference.update(hashMap).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void avoid){
                        Toast.makeText(LookRestaurantStudio.this, R.string.fotoad, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LookRestaurantStudio.this, "erro " + e, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setPhotoCover(){
       String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        photoCover.setImageURI(image_uri);
        final String timestamp = "" + System.currentTimeMillis();
        String filePathAndName = "photoCover/" + "" + timestamp;
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
        storageReference.putFile(image_uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        Uri downloadImageUri = uriTask.getResult();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LookRestaurantStudio.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        DocumentReference documentReference = fStore.collection("Adm's").document(userID);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("photoCover", image_uri);
        documentReference.update(hashMap).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void avoid){
                        Toast.makeText(LookRestaurantStudio.this, R.string.fotoad, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LookRestaurantStudio.this, "erro " + e, Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
