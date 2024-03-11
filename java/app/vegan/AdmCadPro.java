package app.vegan;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_MEDIA_IMAGES;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.ContentValues.TAG;

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
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
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
import com.google.firebase.auth.FirebaseUser;
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

public class AdmCadPro extends AppCompatActivity implements Serializable {

    private ImageButton backBtn;
    private ImageView productIconIv, productIcon2, productIcon3;
    private ShapeableImageView shopImg;
    private EditText titleEt,descriptionEt, quantityEt, priceEt, discountedPriceEt, discountedNoteEt,
            ingredientsEt, estoqueEt, deliveryTimeEt, qualityEt, securePolicyEt, scheduleProduction, moreInfoEt,
            adicionais1, adicionais2, adicionais3, adicionais4, adicionais5, adicionais6, adicionais7, adicionais8,
            adicionais9, adicionais10, adicionais11, adicionais12, adicionais13, adicionais14, adicionais15, nationalTimeEt,
            importedTimeEt, sizepersonalize, valor1, valor2, valor3, valor4, valor5, valor6, valor7, valor8, valor9,
            valor10, valor11,valor12, valor13, valor14, valor15;
    private TextView categoryTv, BrandTv, TypeTv, cityTv, stateTv, countryTv, adicionar, adicionar2, adicionar3;
    private SwitchCompat discountSwitch;
    private FloatingActionButton addProductBtn;
    private CheckBox checkpp, checkp, checkm, checkg, checkgg, checkggg, checkb6, checkb7, checkb8, checkb9, ch13, ch14, ch15,
             checkb10, checkb11, checkb12, checkb13, checkb14, checkRetirar, checkEntrega,
            add1, add2, add3, add4, add5, add6, add7, add8, add9, add10, add11, add12, add13, add14, add15,
            checkNacional, checkImportado, sizebox;
    private RadioButton radioOnTime, radioSchedule;
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private static final int IMAGE_PICK_CAMERA_CODE = 500;
    private String[] cameraPermissions;
    private String[] storagePermissions;
    private Uri image_uri,image_uri1,image_uri2,image_uri3;
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    private ProgressDialog progressDialog;
    String userID, photoclicked;
    private String timestamp, country, numberOfProducts;
    private boolean exceed;
    private ActivityResultLauncher<Intent> launcher;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_cad_pro);
        setRequestedOrientation ( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        sizepersonalize = findViewById(R.id.sizepersonalize);
        sizebox = findViewById(R.id.sizebox);
        shopImg = findViewById(R.id.shopImg);
        countryTv = findViewById(R.id.countryTv);
        stateTv = findViewById(R.id.stateTv);
        cityTv = findViewById(R.id.cityTv);
        adicionais1 = findViewById(R.id.adicionais1);
        adicionais2 = findViewById(R.id.adicionais2);
        adicionais3 = findViewById(R.id.adicionais3);
        adicionais4 = findViewById(R.id.adicionais4);
        adicionais5 = findViewById(R.id.adicionais5);
        adicionais6 = findViewById(R.id.adicionais6);
        adicionais7 = findViewById(R.id.adicionais7);
        adicionais8 = findViewById(R.id.adicionais8);
        adicionais9 = findViewById(R.id.adicionais9);
        adicionais10 = findViewById(R.id.adicionais10);
        adicionais11 = findViewById(R.id.adicionais11);
        adicionais12 = findViewById(R.id.adicionais12);
        adicionais13 = findViewById(R.id.adicionais13);
        adicionais14 = findViewById(R.id.adicionais14);
        adicionais15 = findViewById(R.id.adicionais15);
        valor1 = findViewById(R.id.valor1);
        valor2 = findViewById(R.id.valor2);
        valor3 = findViewById(R.id.valor3);
        valor4 = findViewById(R.id.valor4);
        valor5 = findViewById(R.id.valor5);
        valor6 = findViewById(R.id.valor6);
        valor7 = findViewById(R.id.valor7);
        valor8 = findViewById(R.id.valor8);
        valor9 = findViewById(R.id.valor9);
        valor10 = findViewById(R.id.valor10);
        valor11 = findViewById(R.id.valor11);
        valor12 = findViewById(R.id.valor12);
        valor13 = findViewById(R.id.valor13);
        valor14 = findViewById(R.id.valor14);
        valor15 = findViewById(R.id.valor15);
        moreInfoEt = findViewById(R.id.moreInfoEt);
        add1 = findViewById(R.id.add1);
        add2 = findViewById(R.id.add2);
        add3 = findViewById(R.id.add3);
        add4 = findViewById(R.id.add4);
        add5 = findViewById(R.id.add5);
        add6 = findViewById(R.id.add6);
        add7 = findViewById(R.id.add7);
        add8 = findViewById(R.id.add8);
        add9 = findViewById(R.id.add9);
        add10 = findViewById(R.id.add10);
        add11 = findViewById(R.id.add11);
        add12 = findViewById(R.id.add12);
        add13 = findViewById(R.id.add13);
        add14 = findViewById(R.id.add14);
        add15 = findViewById(R.id.add15);
        scheduleProduction = findViewById(R.id.scheduleProduction);
        checkNacional = findViewById(R.id.checkNacional);
        checkImportado = findViewById(R.id.checkImportado);
        radioOnTime = findViewById(R.id.radioOnTime);
        radioSchedule = findViewById(R.id.radioSchedule);
        checkpp = findViewById(R.id.checkb0);
        checkp = findViewById(R.id.checkb1);
        checkm = findViewById(R.id.checkb2);
        checkg = findViewById(R.id.checkb3);
        checkgg = findViewById(R.id.checkb4);
        checkggg = findViewById(R.id.checkb5);
        checkb6 = findViewById(R.id.checkb6);
        checkb7 = findViewById(R.id.checkb7);
        checkb8 = findViewById(R.id.checkb8);
        checkb9 = findViewById(R.id.checkb9);
        checkb10 = findViewById(R.id.checkb10);
        checkb11 = findViewById(R.id.checkb11);
        checkb12 = findViewById(R.id.checkb12);
        ch13 = findViewById(R.id.checb10);
        ch14 = findViewById(R.id.chekb11);
        ch15 = findViewById(R.id.chckb14);
        checkb13 = findViewById(R.id.checkb13);
        checkb14 = findViewById(R.id.checkb14);
        checkRetirar = findViewById(R.id.checkBox);
        checkEntrega = findViewById(R.id.checkBox2);
        backBtn = findViewById(R.id.backBtn);
        productIconIv = findViewById(R.id.productIconIv);
        productIcon2 = findViewById(R.id.productIcon2);
        productIcon3 = findViewById(R.id.productIcon3);
        titleEt = findViewById(R.id.titleEt);
        descriptionEt = findViewById(R.id.descriptionEt);
        quantityEt  = findViewById(R.id.quantityEt);
        priceEt  = findViewById(R.id.priceEt);
        discountedPriceEt  = findViewById(R.id.discountedPriceEt);
        discountedNoteEt  = findViewById(R.id.discountedNoteEt);
        ingredientsEt = findViewById(R.id.ingredients);
        estoqueEt = findViewById(R.id.estoqueEt);
        deliveryTimeEt  = findViewById(R.id.deliveryTimeEt);
        nationalTimeEt  = findViewById(R.id.nationalTimeEt);
        importedTimeEt  = findViewById(R.id.importedTimeEt);
        qualityEt  = findViewById(R.id.qualityEt);
        securePolicyEt  = findViewById(R.id.securePolicyEt);
        categoryTv = findViewById(R.id.categoryTv);
        TypeTv = findViewById(R.id.brandTypeTv);
        BrandTv = findViewById(R.id.brandTv);
        discountSwitch = findViewById(R.id.discountSwitch);
        addProductBtn = findViewById(R.id.addProductBtn);
        adicionar = findViewById(R.id.adicionar1);
        adicionar2 = findViewById(R.id.adicionar2);
        adicionar3 = findViewById(R.id.adicionar2);

        timestamp = "" + System.currentTimeMillis();

        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        ActivityCompat.requestPermissions(this,
                new String[]{CAMERA, READ_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);


        ActivityCompat.requestPermissions(this,
                new String[]{READ_MEDIA_IMAGES, WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);

        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        putData();

        launcher=
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),(ActivityResult result)->{
                    if(result.getResultCode()==RESULT_OK){
                        image_uri = result.getData().getData();
                        if(Objects.equals(photoclicked, "productIcon")){
                            productIconIv.setImageURI(image_uri);

                        }
                        if(Objects.equals(photoclicked, "productIcon2")){
                            productIcon2.setImageURI(image_uri);
                        }
                        if(Objects.equals(photoclicked, "productIcon3")){
                            productIcon3.setImageURI(image_uri);
                        }
                    }else if(result.getResultCode()== ImagePicker.RESULT_ERROR){
                        Toast.makeText(AdmCadPro.this, result.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        if(categoryTv.getText() == "Promoções") {
            discountSwitch.setChecked(true);
            discountedPriceEt.setVisibility(View.VISIBLE);
            discountedNoteEt.setVisibility(View.VISIBLE);
        } else{
            discountSwitch.setChecked(false);
            discountedPriceEt.setVisibility(View.GONE);
            discountedNoteEt.setVisibility(View.GONE);
        }

        discountedPriceEt.setVisibility(View.GONE);
        discountedNoteEt.setVisibility(View.GONE);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogTheme);
        progressDialog.setTitle(R.string.aguardeum);
        progressDialog.setCanceledOnTouchOutside(false);

        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        checkNacional.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    nationalTimeEt.setVisibility(View.VISIBLE);
                }
                else{
                    nationalTimeEt.setVisibility(View.GONE);
                }
            }
        });

        checkImportado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    importedTimeEt.setVisibility(View.VISIBLE);
                }
                else{
                    importedTimeEt.setVisibility(View.GONE);
                }
            }
        });

        checkEntrega.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    deliveryTimeEt.setVisibility(View.VISIBLE);
                }
                else{
                    deliveryTimeEt.setVisibility(View.GONE);
                }
            }
        });

        radioOnTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    scheduleProduction.setVisibility(View.GONE);
                }
                else{
                    scheduleProduction.setVisibility(View.VISIBLE);
                }
            }
        });

        radioSchedule.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    scheduleProduction.setVisibility(View.VISIBLE);
                }
                else{
                    scheduleProduction.setVisibility(View.GONE);
                }
            }
        });

        discountSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    discountedPriceEt.setVisibility(View.VISIBLE);
                    discountedNoteEt.setVisibility(View.VISIBLE);
                    categoryTv.setText("Promoções");
                    discountSwitch.setChecked(true);
                }
                else{
                    discountedPriceEt.setVisibility(View.GONE);
                    discountedNoteEt.setVisibility(View.GONE);
                    categoryTv.setText("Mais");
                    discountSwitch.setChecked(false);
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });

        productIconIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoclicked = "productIcon";
                //show dialog to pick image
                if (TextUtils.isEmpty(productCategory)){
                    Toast.makeText(AdmCadPro.this, R.string.categnec, Toast.LENGTH_SHORT).show();
                } else {
                    showImagePickDialog();
                    adicionar.setVisibility(View.VISIBLE);}
            }
        });

        productIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoclicked = "productIcon2";
                if (TextUtils.isEmpty(productCategory)){
                    Toast.makeText(AdmCadPro.this, R.string.categnec, Toast.LENGTH_SHORT).show();
                } else {
                    showImagePickDialog();
                    adicionar2.setVisibility(View.VISIBLE);
                }}
        });

        productIcon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoclicked = "productIcon3";
                if (TextUtils.isEmpty(productCategory)){
                    Toast.makeText(AdmCadPro.this, R.string.categnec, Toast.LENGTH_SHORT).show();
                } else {
                    showImagePickDialog();
                    adicionar3.setVisibility(View.VISIBLE);
                }
            }
        });

        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getImageSize(getApplicationContext(),image_uri)<250400) {
                    photoClick(photoclicked);
                    switch (photoclicked) {
                        case "productIcon" :
                            if(image_uri != null) {
                                try {
                                    Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(productIconIv);
                                } catch (Exception e) {
                                    Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(productIconIv);
                                }}
                            break;

                        case "productIcon2" :
                            if(image_uri != null) {
                                try {
                                    Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(productIcon2);
                                } catch (Exception e) {
                                    Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(productIcon2);
                                }}
                            break;

                        case "productIcon3":
                            if(image_uri != null){
                                try {
                                    Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(productIcon3);
                                } catch (Exception e) {
                                    Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(productIcon3);
                                }}
                    }
                    adicionar.setVisibility(View.GONE);
                }else{
                    Toast.makeText(AdmCadPro.this, "A imagem precisa ser menor que 260 KB", Toast.LENGTH_SHORT).show();
                }
            }
        });

        adicionar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                if(getImageSize(getApplicationContext(),image_uri)<250400) {
                    photoClick(photoclicked);
                    switch (photoclicked) {
                        case "productIcon":
                            if (image_uri != null) {
                                try {
                                    Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(productIconIv);
                                } catch (Exception e) {
                                    Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(productIconIv);
                                }
                            }
                            break;

                        case "productIcon2":
                            if (image_uri != null) {
                                try {
                                    Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(productIcon2);
                                } catch (Exception e) {
                                    Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(productIcon2);
                                }
                            }
                            break;

                        case "productIcon3":
                            if (image_uri != null) {
                                try {
                                    Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(productIcon3);
                                } catch (Exception e) {
                                    Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(productIcon3);
                                }
                            }
                    }
                    adicionar2.setVisibility(View.GONE);
                }else{
                    Toast.makeText(AdmCadPro.this, "A imagem precisa ser menor que 260 KB", Toast.LENGTH_SHORT).show();
                }
            }
        });

        adicionar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getImageSize(getApplicationContext(),image_uri)<250400) {
                    photoClick(photoclicked);
                    switch (photoclicked) {
                        case "productIcon" :
                            if(image_uri != null) {
                                try {
                                    Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(productIconIv);
                                } catch (Exception e) {
                                    Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(productIconIv);
                                }}
                            break;

                        case "productIcon2" :
                            if(image_uri != null) {
                                try {
                                    Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(productIcon2);
                                } catch (Exception e) {
                                    Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(productIcon2);
                                }}
                            break;

                        case "productIcon3":
                            if(image_uri != null){
                                try {
                                    Picasso.get().load(image_uri).placeholder(R.drawable.loading).into(productIcon3);
                                } catch (Exception e) {
                                    Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(productIcon3);
                                }}
                    }
                    adicionar3.setVisibility(View.GONE);
                }else{
                    Toast.makeText(AdmCadPro.this, "A imagem precisa ser menor que 260 KB", Toast.LENGTH_SHORT).show();
                }
            }
        });


        categoryTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                categoryDialog();
            }
        });

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                inputData();
                addProductBtn.setClickable(false);
            }
        });
    }


    private String productTitle, productDescription, productCategory, productBrand, sizepersonaliza, productQuantity, originalPrice, discountPrice, discountNote,
            ingredients, estoque, deliveryTime, quality, securePolicy, moreInfo, adEt1, adEt2, adEt3, adEt4, adEt5, adEt6, adEt7,
            adEt8, adEt9, adEt10, adEt11, adEt12, adEt13, adEt14, adEt15, scheduleTime, nationalTime, importedTime, val1, val2, val3, val4, val5,
            val6, val7, val8, val9, val10, val11, val12, val13, val14, val15;

    private boolean discountAvailable, checp,checpp, checm, checg, checgg, checggg, checkgluten, checksoy, cRaw, cB12, cEcofriend,
            checkorganic, checkrecycling, checkbiode, checkcanna, checkparaben, checkcotton, checknogmo, deliveryOption, onPlaceOption, onTimeOption,
            scheduleOption, nacional, importado, adic1, adic2, adic3, adic4, adic5, adic6, adic7, adic8, adic9, adic10, adic11,
            adic12, adic13, adic14, adic15, boxpersonaliza;

    private void inputData() {
        productBrand = BrandTv.getText().toString().trim();
        scheduleTime = scheduleProduction.getText().toString().trim();
        moreInfo = moreInfoEt.getText().toString().trim();
        ingredients = ingredientsEt.getText().toString().trim();
        estoque = estoqueEt.getText().toString().trim();
        deliveryTime = deliveryTimeEt.getText().toString().trim();
        nationalTime = nationalTimeEt.getText().toString().trim();
        importedTime = importedTimeEt.getText().toString().trim();
        quality = qualityEt.getText().toString().trim();
        securePolicy = securePolicyEt.getText().toString().trim();
        productTitle = titleEt.getText().toString().trim();
        productDescription = descriptionEt.getText().toString().trim();
        productCategory = categoryTv.getText().toString().trim();
        productQuantity = quantityEt.getText().toString().trim();
        originalPrice = priceEt.getText().toString().trim();
        discountAvailable = discountSwitch.isChecked();

        boxpersonaliza = sizebox.isChecked();
        if (sizebox.isChecked()){
            sizepersonaliza = sizepersonalize.getText().toString().trim();
        } else{sizepersonaliza="";}

        adic1 = add1.isChecked();
        if (add1.isChecked()){
            adEt1 = adicionais1.getText().toString().trim();
            val1 = valor1.getText().toString().trim();
        } else{adEt1="";}

        adic2 = add2.isChecked();
        if (add2.isChecked()){
            adEt2 = adicionais2.getText().toString().trim();
            val2 = valor2.getText().toString().trim();
        } else{adEt2="";}

        adic3 = add3.isChecked();
        if (add3.isChecked()){
            adEt3 = adicionais3.getText().toString().trim();
            val3 = valor3.getText().toString().trim();
        } else{adEt3="";}

        adic4 = add4.isChecked();
        if (add4.isChecked()) {
            adEt4 = adicionais4.getText().toString().trim();
            val4 = valor4.getText().toString().trim();
        } else{adEt4="";}

        adic5 = add5.isChecked();
        if (add5.isChecked()) {
            adEt5 = adicionais5.getText().toString().trim();
            val5 = valor5.getText().toString().trim();
        } else{adEt5="";}

        adic6 = add6.isChecked();
        if (add6.isChecked()) {
            adEt6 = adicionais6.getText().toString().trim();
            val6 = valor6.getText().toString().trim();
        } else{adEt6="";}

        adic7 = add7.isChecked();
        if (add7.isChecked()) {
            adEt7 = adicionais7.getText().toString().trim();
            val7 = valor7.getText().toString().trim();
        } else{adEt7="";}

        adic8 = add8.isChecked();
        if (add8.isChecked()) {
            adEt8 = adicionais8.getText().toString().trim();
            val8 = valor8.getText().toString().trim();
        } else{adEt8="";}

        adic9 = add9.isChecked();
        if (add9.isChecked()) {
            adEt9 = adicionais9.getText().toString().trim();
            val9 = valor9.getText().toString().trim();
        } else{adEt9="";}

        adic10 = add10.isChecked();
        if (add10.isChecked()) {
            adEt10 = adicionais10.getText().toString().trim();
            val10 = valor10.getText().toString().trim();
        } else{adEt10="";}

        adic11 = add11.isChecked();
        if (add11.isChecked()) {
            adEt11 = adicionais11.getText().toString().trim();
            val11 = valor11.getText().toString().trim();
        } else{adEt11="";}

        adic12 = add12.isChecked();
        if (add12.isChecked()) {
            adEt12 = adicionais12.getText().toString().trim();
            val12 = valor12.getText().toString().trim();
        } else{adEt12="";}

        adic13 = add13.isChecked();
        if (add13.isChecked()) {
            adEt13 = adicionais13.getText().toString().trim();
            val13 = valor13.getText().toString().trim();
        } else{adEt13="";}

        adic14 = add14.isChecked();
        if (add14.isChecked()) {
            adEt14 = adicionais14.getText().toString().trim();
            val14 = valor14.getText().toString().trim();
        } else{adEt14="";}

        adic15 = add15.isChecked();
        if (add15.isChecked()) {
            adEt15 = adicionais15.getText().toString().trim();
            val15 = valor15.getText().toString().trim();
        } else{adEt15="";}

        checpp = checkpp.isChecked();
        checp = checkp.isChecked();
        checm = checkm.isChecked();
        checg = checkg.isChecked();
        checgg = checkgg.isChecked();
        checggg = checkggg.isChecked();
        checkgluten = checkb6.isChecked();
        checksoy = checkb7.isChecked();
        checkorganic = checkb8.isChecked();
        checkrecycling = checkb9.isChecked();
        checkbiode = checkb10.isChecked();
        checkcanna = checkb11.isChecked();
        checkparaben = checkb12.isChecked();
        checkcotton = checkb13.isChecked();
        checknogmo = checkb14.isChecked();
        cRaw = ch13.isChecked();
        cB12 = ch14.isChecked();
        cEcofriend = ch15.isChecked();
        deliveryOption = checkEntrega.isChecked();
        onPlaceOption = checkRetirar.isChecked();
        onTimeOption = radioOnTime.isChecked();
        scheduleOption = radioSchedule.isChecked();
        nacional = checkNacional.isChecked();
        importado = checkImportado.isChecked();

        if (!deliveryOption && !onPlaceOption && !importado && !nacional){
            Toast.makeText(this, R.string.campovv, Toast.LENGTH_SHORT).show();
            return;
        }
        if (deliveryOption && Objects.equals(deliveryTime, "")){
            Toast.makeText(this, R.string.tempoestva, Toast.LENGTH_SHORT).show();
            return;
        }
        if (nacional && Objects.equals(nationalTime, "")){
            Toast.makeText(this, R.string.tempoestva, Toast.LENGTH_SHORT).show();
            return;
        }
        if (importado && Objects.equals(importedTime, "")){
            Toast.makeText(this, R.string.tempoestva, Toast.LENGTH_SHORT).show();
            return;
        }
        if (scheduleOption && Objects.equals(scheduleTime, "")){
            Toast.makeText(this, R.string.tempoestva, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(productTitle)){
            Toast.makeText(this, R.string.umoumaiscam, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(estoque)){
            Toast.makeText(this, R.string.umoumaiscam, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(productCategory)){
            Toast.makeText(this, R.string.umoumaiscam, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(originalPrice)){
            Toast.makeText(this, R.string.umoumaiscam, Toast.LENGTH_SHORT).show();
            return;
        }
        if (discountAvailable){
            discountPrice = discountedPriceEt.getText().toString().trim();
            discountNote = discountedNoteEt.getText().toString().trim();
            if (TextUtils.isEmpty(discountPrice)){
                Toast.makeText(this,R.string.umoumaiscam, Toast.LENGTH_SHORT).show();
                return;
            }
        }
        else {
            discountPrice = "0";
            discountNote = "";
        }

        if(exceed){
            return;
        }

        addProductBtn.setClickable(true);
        addProduct();
    }

    public void photoClick(String photoclicked){
        if (TextUtils.isEmpty(productCategory)){
            Toast.makeText(this, R.string.categnec, Toast.LENGTH_SHORT).show();
        } else {
            Log.d(TAG, "getImageUri "+image_uri);
            if(Objects.equals(photoclicked, "productIcon")){
                image_uri1=image_uri;
                productIcon2.setVisibility(View.VISIBLE);
            }
            if(Objects.equals(photoclicked, "productIcon2")){
                image_uri2=image_uri;
                if (Objects.equals(TypeTv.getText().toString(), "Closet") || Objects.equals(TypeTv.getText().toString(), "Others")) {
                    productIcon3.setVisibility(View.VISIBLE);
                }
            }
            if(Objects.equals(photoclicked, "productIcon3")){
                image_uri3=image_uri;
            }
        }
    }


    private void addProduct() {
        progressDialog.setMessage(getString(R.string.aguardeum)+"...");
        progressDialog.show();

        userID=firebaseAuth.getCurrentUser().getUid();

        String type = TypeTv.getText().toString().trim();
        String brand = BrandTv.getText().toString().trim();
        String count = countryTv.getText().toString().trim();
        String stat = stateTv.getText().toString().trim();
        String cit = cityTv.getText().toString().trim();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("productCountry",""+country);
        hashMap.put("productState",""+stat);
        hashMap.put("productCity",""+cit);
        hashMap.put("indisponivel","");
        hashMap.put("productId",""+timestamp);
        hashMap.put("productBrand",""+brand);
        hashMap.put("productTitle",""+productTitle);
        hashMap.put("productDescription",""+productDescription);
        hashMap.put("productCategory",""+productCategory);
        hashMap.put("productType",""+type);
        hashMap.put("productQuantity",""+productQuantity);
        hashMap.put("originalPrice",""+originalPrice);
        hashMap.put("discountPrice",""+discountPrice);
        hashMap.put("discountNote",""+discountNote);
        hashMap.put("discountAvailable",""+discountAvailable);
        hashMap.put("deliveryTime",""+deliveryTime);
        hashMap.put("nationalTime",""+nationalTime);
        hashMap.put("importedTime",""+importedTime);
        hashMap.put("moreInfo",""+moreInfo);
        hashMap.put("estoque",""+estoque);
        hashMap.put("securePolicy",""+securePolicy);
        hashMap.put("quality",""+quality);
        hashMap.put("ingredients",""+ingredients);
        hashMap.put("nationalMail",""+nacional);
        hashMap.put("internationalMail",""+importado);
        hashMap.put("onSchedule",""+scheduleOption);
        hashMap.put("scheduleProduction",""+scheduleTime);
        hashMap.put("onTime",""+onTimeOption);
        hashMap.put("deliveryOption",""+deliveryOption);
        hashMap.put("localOption",""+onPlaceOption);
        hashMap.put("glutenseal",""+checkgluten);
        hashMap.put("soyseal",""+checksoy);
        hashMap.put("organicseal",""+checkorganic);
        hashMap.put("recyclingseal",""+checkrecycling);
        hashMap.put("biodeseal",""+checkbiode);
        hashMap.put("cannaseal",""+checkcanna);
        hashMap.put("parabenseal",""+checkparaben);
        hashMap.put("cottonseal",""+checkcotton);
        hashMap.put("nogmoseal",""+checknogmo);
        hashMap.put("raw",""+cRaw);
        hashMap.put("b12",""+cB12);
        hashMap.put("ecofriendly",""+cEcofriend);
        hashMap.put("sizepp",""+checpp);
        hashMap.put("sizep",""+checp);
        hashMap.put("sizem",""+checm);
        hashMap.put("sizeg",""+checg);
        hashMap.put("sizegg",""+checgg);
        hashMap.put("sizeggg",""+checggg);
        hashMap.put("sizePersonalized",""+sizepersonaliza);
        if(!Objects.equals(adEt1, "")){
            hashMap.put("1adicional",""+adEt1);
            hashMap.put("valor1",""+val1);}
        if(!Objects.equals(adEt2, "")){
            hashMap.put("2adicional",""+adEt2);
            hashMap.put("valor2",""+val2);}
        if(!Objects.equals(adEt3, "")){
            hashMap.put("3adicional",""+adEt3);
            hashMap.put("valor3",""+val3);}
        if(!Objects.equals(adEt4, "")){
            hashMap.put("4adicional",""+adEt4);
            hashMap.put("valor4",""+val4);}
        if(!Objects.equals(adEt5, "")){
            hashMap.put("5adicional",""+adEt5);
            hashMap.put("valor5",""+val5);}
        if(!Objects.equals(adEt6, "")){
            hashMap.put("6adicional",""+adEt6);
            hashMap.put("valor6",""+val6);}
        if(!Objects.equals(adEt7, "")){
            hashMap.put("7adicional",""+adEt7);
            hashMap.put("valor7",""+val7);}
        if(!Objects.equals(adEt8, "")){
            hashMap.put("8adicional",""+adEt8);
            hashMap.put("valor8",""+val8);}
        if(!Objects.equals(adEt9, "")){
            hashMap.put("9adicional",""+adEt9);
            hashMap.put("valor9",""+val9);}
        if(!Objects.equals(adEt10, "")){
            hashMap.put("10adicional",""+adEt10);
            hashMap.put("valor10",""+val10);}
        if(!Objects.equals(adEt11, "")){
            hashMap.put("11adicional",""+adEt11);
            hashMap.put("valor11",""+val11);}
        if(!Objects.equals(adEt12, "")){
            hashMap.put("12adicional",""+adEt12);
            hashMap.put("valor12",""+val12);}
        if(!Objects.equals(adEt13, "")){
            hashMap.put("13adicional",""+adEt13);
            hashMap.put("valor13",""+val13);}
        if(!Objects.equals(adEt14, "")){
            hashMap.put("14adicional",""+adEt14);
            hashMap.put("valor14",""+val14);}
        if(!Objects.equals(adEt15, "")){
            hashMap.put("15adicional",""+adEt15);
            hashMap.put("valor15",""+val15);}

        hashMap.put("timestamp",""+timestamp);
        hashMap.put("uid",""+firebaseAuth.getUid());
        userID = firebaseAuth.getCurrentUser().getUid();
        String CollectionName = productCategory;

        if(image_uri1!=null) {

            String filePathAndName = "product_images/" + "" + timestamp;
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
            storageReference.putFile(image_uri1)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;
                            Uri downloadImageUri = uriTask.getResult();

                            String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                            hashMap.put("productIcon", downloadImageUri);
                            String CollectionName = categoryTv.getText().toString();

                            DocumentReference docRef = fStore.collection(CollectionName).document(timestamp);
                            docRef.set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                            if (checkNacional.isChecked() || checkImportado.isChecked()) {
                                                if (checkNacional.isChecked()) {
                                                    HashMap<String, Object> hashMap2 = new HashMap<>();
                                                    hashMap.put("doMail", "true");
                                                    DocumentReference admRef = fStore.collection("Adm's").document(userID);
                                                    admRef.update(hashMap2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(AdmCadPro.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                                if (checkImportado.isChecked()) {
                                                    HashMap<String, Object> hashMap2 = new HashMap<>();
                                                    hashMap.put("doInternational", "true");
                                                    DocumentReference admRef = fStore.collection("Adm's").document(userID);
                                                    admRef.update(hashMap2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(AdmCadPro.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                            }
                                            else {

                                                Double noptempo = 1.0;
                                                HashMap<String, Object> hashMap37 = new HashMap<>();
                                                Double temp = Double.parseDouble(numberOfProducts) + noptempo;
                                                hashMap37.put("numberOfProducts", "" + temp);
                                                DocumentReference nop = fStore.collection("Adm's").document(userID);
                                                nop.update(hashMap37).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(AdmCadPro.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                                HashMap<String, Object> hashMap2 = new HashMap<>();
                                                hashMap.put("doInternational", "true");
                                                hashMap.put("doMail", "true");

                                                DocumentReference admRef = fStore.collection("Adm's").document(userID);
                                                admRef.update(hashMap2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        //info add
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(AdmCadPro.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }


                                    if(image_uri2!=null) {
                                        String filePathAndName2 = "product_images/" + "2" + timestamp;
                                        StorageReference storageReference2 = FirebaseStorage.getInstance().getReference(filePathAndName2);
                                        storageReference2.putFile(image_uri2)
                                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                                        while (!uriTask.isSuccessful()) ;
                                                        Uri downloadImageUri = uriTask.getResult();

                                                        String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                                                        HashMap<String, Object> hashMap = new HashMap<>();
                                                        hashMap.put("productIcon2", downloadImageUri);
                                                        String CollectionName = productCategory;

                                                        DocumentReference docRef = fStore.collection(CollectionName).document(timestamp);
                                                        docRef.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {

                                                                if(image_uri3!=null) {


                                                                    String filePathAndName3 = "product_images/" + "3" + timestamp;
                                                                    StorageReference storageReference3 = FirebaseStorage.getInstance().getReference(filePathAndName3);
                                                                    storageReference3.putFile(image_uri3)
                                                                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                @Override
                                                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                                                                    while (!uriTask.isSuccessful()) ;
                                                                                    Uri downloadImageUri = uriTask.getResult();

                                                                                    String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                                                                                    HashMap<String, Object> hashMap = new HashMap<>();
                                                                                    hashMap.put("productIcon3", downloadImageUri);
                                                                                    String CollectionName = productCategory;

                                                                                    DocumentReference docRef = fStore.collection(CollectionName).document(timestamp);
                                                                                    docRef.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                        @Override
                                                                                        public void onSuccess(Void aVoid) {
                                                                                                progressDialog.dismiss();
                                                                                                Toast.makeText(AdmCadPro.this, "Produto Aadicionado", Toast.LENGTH_SHORT).show();
                                                                                                startVibrate(100);
                                                                                            clearData();
                                                                                        }
                                                                                    }).addOnFailureListener(new OnFailureListener() {
                                                                                        @Override
                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                            Toast.makeText(AdmCadPro.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                    });
                                                                                }
                                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    Toast.makeText(AdmCadPro.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });
                                                                }
                                                                else{
                                                                    progressDialog.dismiss();
                                                                    Toast.makeText(AdmCadPro.this, "Produto Aadicionado", Toast.LENGTH_SHORT).show();
                                                                    startVibrate(100);
                                                                    clearData();
                                                                }


                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(AdmCadPro.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });

                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(AdmCadPro.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                    else{
                                        progressDialog.dismiss();
                                        Toast.makeText(AdmCadPro.this, "Produto Aadicionado", Toast.LENGTH_SHORT).show();
                                        startVibrate(100);
                                        clearData();
                                    }





                                }

                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AdmCadPro.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AdmCadPro.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
        }




    }

    private void clearData(){
        ingredientsEt.setText("");
        estoqueEt.setText("");
        deliveryTimeEt.setText("");
        nationalTimeEt.setText("");
        importedTimeEt.setText("");
        qualityEt.setText("");
        securePolicyEt.setText("");
        scheduleProduction.setText("");
        moreInfoEt.setText("");
        adicionais1.setText("");
        adicionais2.setText("");
        adicionais3.setText("");
        adicionais4.setText("");
        adicionais5.setText("");
        adicionais6.setText("");
        adicionais7.setText("");
        adicionais8.setText("");
        adicionais9.setText("");
        adicionais10.setText("");
        adicionais11.setText("");
        adicionais12.setText("");
        adicionais13.setText("");
        adicionais14.setText("");
        adicionais15.setText("");
        titleEt.setText("");
        descriptionEt.setText("");
        categoryTv.setText("");
        quantityEt.setText("");
        priceEt.setText("");
        discountedPriceEt.setText("");
        discountedNoteEt.setText("");
        checkp.setChecked(false);
        checkm.setChecked(false);
        checkg.setChecked(false);
        checkgg.setChecked(false);
        checkggg.setChecked(false);
        checkb6.setChecked(false);
        checkb7.setChecked(false);
        checkb8.setChecked(false);
        checkb9.setChecked(false);
        checkb10.setChecked(false);
        checkb11.setChecked(false);
        checkb12.setChecked(false);
        checkb13.setChecked(false);
        checkb14.setChecked(false);
        checkRetirar.setChecked(false);
        checkEntrega.setChecked(false);
        add1.setChecked(false);
        add2.setChecked(false);
        add3.setChecked(false);
        add4.setChecked(false);
        add5.setChecked(false);
        add6.setChecked(false);
        add7.setChecked(false);
        add8.setChecked(false);
        add9.setChecked(false);
        add10.setChecked(false);
        add11.setChecked(false);
        add12.setChecked(false);
        add13.setChecked(false);
        add14.setChecked(false);
        add15.setChecked(false);
        checkNacional.setChecked(false);
        checkImportado.setChecked(false);
        productIconIv.setImageResource(R.drawable.ic_baseline_add_a_photo_24);
        productIcon2.setImageResource(R.drawable.ic_baseline_add_a_photo_24);
        productIcon3.setImageResource(R.drawable.ic_baseline_add_a_photo_24);
        image_uri = null;
    }

    private void categoryDialog(){
        String[] Diet = {
                getString(R.string.promocoes),
                getString(R.string.almoco),
                getString(R.string.lanches),
                getString(R.string.dinner),
                getString(R.string.bebidas),
                getString(R.string.sobremesas),
                getString(R.string.mercado),
                getString(R.string.mais)};

        String[] Pharmacy = {
                getString(R.string.promocoes),
                getString(R.string.saude),
                getString(R.string.higiene),
                getString(R.string.cosmeticos),
                getString(R.string.fitness),
                getString(R.string.maeebebe),
                getString(R.string.natural),
                getString(R.string.mais)};

        String[] Closet = {
                getString(R.string.promocoes),
                getString(R.string.brecho),
                getString(R.string.roupas),
                getString(R.string.acessorios),
                getString(R.string.calcados),
                getString(R.string.pecaintima),
                getString(R.string.praia),
                getString(R.string.mais)};

        String[] More = {
                getString(R.string.promocoes),
                getString(R.string.casa),
                getString(R.string.animalnaohumano),
                getString(R.string.limpeza),
                getString(R.string.ecofriendly),
                getString(R.string.jardim),
                getString(R.string.livros),
                getString(R.string.mais)};


        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme);
        String type = TypeTv.getText().toString().trim();
        if(type.equals("Diet")){
            builder.setTitle(R.string.seleccateg)
                    .setItems(Diet, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            String categor = Diet[which];

                            String[] Portugues = {
                                    "Promoções",
                                    "Almoço",
                                    "Lanches",
                                    "Jantar",
                                    "Bebidas",
                                    "Sobremesas",
                                    "Mercado",
                                    "Mais"};

                            String category = Portugues[which];

                            categoryTv.setText(categor);
                            productCategory = category;
                        }
                    })
                    .show();
        }
        if(type.equals("Pharmacy")){
            builder.setTitle(R.string.seleccateg)
                    .setItems(Pharmacy, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            String categor = Pharmacy[which];

                            String[] Portugues = {
                                    "Promoções",
                                    "Saúde",
                                    "Higiene",
                                    "Cosméticos",
                                    "Fitness",
                                    "Mãe &amp; Bebê",
                                    "Farmácia Natural",
                                    "Mais"};

                            String category = Portugues[which];

                            categoryTv.setText(categor);
                            productCategory = category;
                        }
                    })
                    .show();
        }
        if(type.equals("Closet")){
            builder.setTitle(R.string.seleccateg)
                    .setItems(Closet, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            String categor = Closet[which];

                            String[] Portugues = {
                                    "Promoções",
                                    "Brechó",
                                    "Roupas",
                                    "Acessórios",
                                    "Calçados",
                                    "Peça Íntima",
                                    "Praia",
                                    "Mais"};

                            String category = Portugues[which];

                            categoryTv.setText(categor);
                            productCategory = category;
                        }
                    })
                    .show();
        }
        if(type.equals("Others")){
            builder.setTitle(R.string.seleccateg)
                    .setItems(More, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            String categor = More[which];

                            String[] Portugues = {
                                    "Promoções",
                                    "Casa",
                                    "Animal Não-Humano",
                                    "Limpeza",
                                    "Eco Friendly",
                                    "Jardim",
                                    "Livros",
                                    "Mais"};

                            String category = Portugues[which];

                            categoryTv.setText(categor);
                            productCategory = category;
                        }
                    })
                    .show();
        }
    }


    private void showImagePickDialog() {
        ImagePicker.Companion.with(AdmCadPro.this)
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

    public void onBackPressed ( ) {
        super.onBackPressed();
        finish();
    }

    private void putData() {
        String userID;
        userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        DocumentReference docReference = fStore.collection("Adm's").document(userID);
        docReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String brandName = document.getString("fName");
                        String type = document.getString("type");
                        String city = document.getString("city");
                        String state = document.getString("state");
                        country = document.getString("country");
                        String productIcon = document.getString("shopIcon");
                        numberOfProducts = document.getString("numberOfProducts");

                        if(Objects.equals(numberOfProducts, "50")){
                            exceed = true;
                        }

                        BrandTv.setText(brandName);
                        TypeTv.setText(type);
                        cityTv.setText(city);
                        stateTv.setText(state);
                        countryTv.setText(country);

                        try {
                            Picasso.get().load(productIcon).placeholder(R.drawable.loading).into(shopImg);
                        } catch (Exception e) {
                            shopImg.setImageResource(R.drawable.error);
                        }
                    }
                }
            }
        });
    }

    public void startVibrate ( long time ) {
        Vibrator atvib = ( Vibrator ) getSystemService ( Context.VIBRATOR_SERVICE );
        assert atvib != null;
        atvib.vibrate ( time );
    }
}


