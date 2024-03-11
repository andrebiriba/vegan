package app.vegan;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class Order extends AppCompatActivity implements Observer{
    private Product product;
    private RadioButton creditCardR, cashR, pix, cartao, dinheiro;
    private LinearLayout lllojista;
    private ShapeableImageView logobrand;
    private ImageButton imageButtonBack;
    private Double totalIncome, feetotal;
    private ImageView datePickIb;
    private Button btnFinishOrder;
    public TextView totalTv, TotalTv, datePickTv, cidade, estado, pais, itemTitleTv, trocoTv, cupomTv, itemPriceEachTv,
    lojaname, feeTv, bemvinde;
    private ImageView itemphoto, googlepay;
    public EditText edtStreetDelivery, edtNumberDelivery, edtNeighborhoodDelivery, editObservation,
            bairro, endereco, numero, referencia, postalcode;
    public String deliveryFee, afd, sad;
    private FirebaseAuth firebaseAuth;
    private String shopUid, nome, foto, preco, brand, troco, pontos, cpm, api26orhigher;
    private Integer api;
    private String tipoDeCompra, sume, nomeuser, cupomaplicado, latUser, longUser, latShop, longShop;
    RadioButton radioBxRetirar, radioBxEntrega, radioBxCorreio, radioBxImported;
    FirebaseFirestore fStore;
    String userID, productType, descriptionProduct, productCountry, finalcoin, cambio, usercoin, scheduleProduction;
    FirebaseAuth fAuth;
    private String adc1, adc2, adc3, adc4, adc5, adc6, adc7, adc8, adc9, adc10, adc11, adc12, adc13, adc14, adc15, formadepag;
    private String va1, va2, va3, va4, va5, va6, va7, va8, va9, va10, va11, va12, va13, va14, va15, size, premiumisok, admcity,
            countr, estat, cit, codentrega, schedule, nationalFee, importedFee, deliveryFixo, fee;
    private TextView addmoreitens, timeopening;
    private ProgressDialog progressDialog;
    private ArrayList<ModelCartItem> cartItemList;
    private AdapterCartItem adapterCartItem;
    private RecyclerView cartItemsRv;
    private LinearLayout naEntrega, datePickLl;
    private boolean temfiel, ganhoufiel;
    PaymentSheet paymentSheet;
    String paymentIntentClientSecret, amount, quantity, timestamp, name, shoptoken, apagaraoadm,
    timetable, daytable, iddopagamento, localOption;
    PaymentSheet.CustomerConfiguration customerConfig;

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            assert connectivityManager != null;
            network = connectivityManager.getActiveNetwork();
        } else
            return true;
        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
        return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setRequestedOrientation ( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        datePickLl = findViewById(R.id.datePickLl);
        bemvinde = findViewById(R.id.textView30);
        dinheiro = findViewById(R.id.dinheiro);
        cartao = findViewById(R.id.cartao);
        pix = findViewById(R.id.pix);
        naEntrega = findViewById(R.id.naEntrega);
        timeopening = findViewById(R.id.timeopening);
        feeTv = findViewById(R.id.feeTv);
        cidade = findViewById(R.id.textview_user_city);
        estado = findViewById(R.id.textview_user_estate);
        pais = findViewById(R.id.textview_user_country);
        postalcode = findViewById(R.id.edtPostalCodeDelivery);
        bairro = findViewById(R.id.edtNeighborhoodDelivery);
        endereco = findViewById(R.id.edtStreetDelivery);
        numero = findViewById(R.id.edtNumberDelivery);
        referencia = findViewById(R.id.edtcomplementDelivery);
        cartItemsRv = findViewById(R.id.cartItemsRv);
        lojaname = findViewById(R.id.lojaname);
        logobrand = findViewById(R.id.logobrand);
        itemPriceEachTv = findViewById(R.id.itemPriceEachTv);
        cupomTv = findViewById(R.id.cupomTv);
        lllojista = findViewById(R.id.lllojista);
        addmoreitens = findViewById(R.id.addmoreitens);
        trocoTv = findViewById(R.id.trocoTv);
        itemphoto = findViewById(R.id.imageView20);
        itemTitleTv = findViewById(R.id.itemTitleTv);
        totalTv = findViewById(R.id.totalTv);
        TotalTv = findViewById(R.id.TotalTv);
        datePickTv = findViewById(R.id.datePickTv);
        RadioGroup rg_1 = findViewById(R.id.rg_1);
        RadioGroup rg_2 = findViewById(R.id.rg_2);
        radioBxRetirar = findViewById(R.id.radioBxRetirar);
        radioBxEntrega = findViewById(R.id.radioBxEntrega);
        radioBxCorreio = findViewById(R.id.radioBxCorreio);
        radioBxImported = findViewById(R.id.radioBxImported);
        creditCardR = findViewById(R.id.creditCardR);
        cashR = findViewById(R.id.cashR);
        btnFinishOrder = findViewById(R.id.btnFinishOrder);
        googlepay = findViewById(R.id.googlepay);
        creditCardR = findViewById(R.id.creditCardR);
        cashR = findViewById(R.id.cashR);
        edtStreetDelivery = findViewById(R.id.edtStreetDelivery);
        edtNumberDelivery = findViewById(R.id.edtNumberDelivery);
        edtNeighborhoodDelivery = findViewById(R.id.edtNeighborhoodDelivery);
        editObservation = findViewById(R.id.editObservation);

        schedule = "";

        boolean statuInternet = isOnline ( this );

        if (!statuInternet) {
            alertOffline ( );
            Toast.makeText(Order.this, R.string.falhananet, Toast.LENGTH_SHORT).show();
        } else {

            this.firebaseAuth = FirebaseAuth.getInstance();
        }

         timestamp = ""+System.currentTimeMillis();
        progressDialog = new ProgressDialog(this,  R.style.MyAlertDialogTheme);
        progressDialog.setTitle(R.string.aguardeum);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(getString(R.string.aguardeum));
        progressDialog.show();

        Intent data = getIntent();
        localOption = data.getStringExtra("localOption");
        va15 = data.getStringExtra("va15");
        va14 = data.getStringExtra("va14");
        va13 = data.getStringExtra("va13");
        va12 = data.getStringExtra("va12");
        va11 = data.getStringExtra("va11");
        va10 = data.getStringExtra("va10");
        va9 = data.getStringExtra("va9");
        va8 = data.getStringExtra("va8");
        va7 = data.getStringExtra("va7");
        va6 = data.getStringExtra("va6");
        va5 = data.getStringExtra("va5");
        va4 = data.getStringExtra("va4");
        va3 = data.getStringExtra("va3");
        va2 = data.getStringExtra("va2");
        va1 = data.getStringExtra("va1");
        adc15 = data.getStringExtra("adc15");
        adc14 = data.getStringExtra("adc14");
        adc13 = data.getStringExtra("adc13");
        adc12 = data.getStringExtra("adc12");
        adc11 = data.getStringExtra("adc11");
        adc10 = data.getStringExtra("adc10");
        adc9 = data.getStringExtra("adc9");
        adc8 = data.getStringExtra("adc8");
        adc7 = data.getStringExtra("adc7");
        adc6 = data.getStringExtra("adc6");
        adc5 = data.getStringExtra("adc5");
        adc4 = data.getStringExtra("adc4");
        adc3 = data.getStringExtra("adc3");
        adc2 = data.getStringExtra("adc2");
        adc1 = data.getStringExtra("adc1");
        size = data.getStringExtra("size");

        api = Integer.valueOf(android.os.Build.VERSION.SDK);

        feetotal=0.0;
        if(size!=null){

        }
        Log.d(TAG, "emanana " + localOption);
        if (Objects.equals(localOption, "true")){
            radioBxRetirar.setVisibility(VISIBLE);
        }

        nome = data.getStringExtra("title");
        foto = data.getStringExtra("photo");
        preco = data.getStringExtra("price");
        brand = data.getStringExtra("brandName");
        shopUid = data.getStringExtra("shopUid");
        productType = data.getStringExtra("type");
        descriptionProduct = data.getStringExtra("descriptionProduct");
        productCountry = data.getStringExtra("productCountry");
        scheduleProduction = data.getStringExtra("scheduleProduction");

        if(api>=26){
            api26orhigher="true";
            timeopening.setVisibility(GONE);
            apihigh();

        } else {

        }

        if(!Objects.equals(scheduleProduction, "")){
            datePickLl.setVisibility(VISIBLE);
            String xsf = scheduleProduction+" dias para a produção. \n"+getString(R.string.agendeoped);
            datePickTv.setText(xsf);
            timeopening.setVisibility(VISIBLE);
        }

        finalcoin = "";
        if(Objects.equals(productCountry, "Brasil")){
            finalcoin = "R$";
        }
        if(Objects.equals(productCountry,"Estados Unidos da América")){
            finalcoin = "US$";
        }
        if(Objects.equals(productCountry, "Inglaterra")){
            finalcoin = "£";
        }
        if(Objects.equals(productCountry, "Alemanha")){
            finalcoin = "€";
        }
        if(Objects.equals(productCountry, "França")){
            finalcoin = "€";
        }
        if(Objects.equals(productCountry, getResources().getString(R.string.canada))){
            finalcoin = "C$";
        }
        if(Objects.equals(productCountry,"Portugal")){
            finalcoin = "€";
        }
        if(Objects.equals(productCountry, "Espanha")){
            finalcoin = "€";
        }
        if(Objects.equals(productCountry, getResources().getString(R.string.mexico))){
            finalcoin = "MEX$";
        }
        if(Objects.equals(productCountry, getResources().getString(R.string.argentina))){
            finalcoin = "ARS$";
        }
        if(Objects.equals(productCountry, getResources().getString(R.string.escocia))){
            finalcoin = "£";
        }

        Log.d(TAG, "finalcoin "+finalcoin);

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getUid();
        Log.d(TAG, "userID "+userID);
        fAuth = FirebaseAuth.getInstance();

        fStore = FirebaseFirestore.getInstance();
        deliveryFee = "0";
        fee="0";
        troco = "00,00";

        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);

        loadPersonalInfo();

        nome = data.getStringExtra("title");
        foto = data.getStringExtra("photo");
        preco = data.getStringExtra("price");
        brand = data.getStringExtra("brand");
        shopUid = data.getStringExtra("shopUid");
        productType = data.getStringExtra("type");



        addmoreitens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(Order.this, MoreItens.class);
                intentLoadNewActivity.putExtra("uid", shopUid);
                intentLoadNewActivity.putExtra("type", productType);
                intentLoadNewActivity.putExtra("brandName", brand);
                startActivity(intentLoadNewActivity);
            }
        });

        lllojista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(Order.this, RestaurantDetailsActivity.class);
                intentLoadNewActivity.putExtra("uid", shopUid);
                intentLoadNewActivity.putExtra("type", productType);
                intentLoadNewActivity.putExtra("moreItens", true);
                startActivity(intentLoadNewActivity);
            }
        });

        lojaname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(Order.this, RestaurantDetailsActivity.class);
                intentLoadNewActivity.putExtra("uid", shopUid);
                intentLoadNewActivity.putExtra("type", productType);
                intentLoadNewActivity.putExtra("moreItens", true);
                startActivity(intentLoadNewActivity);
            }
        });

        logobrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(Order.this, RestaurantDetailsActivity.class);
                intentLoadNewActivity.putExtra("uid", shopUid);
                intentLoadNewActivity.putExtra("type", productType);
                intentLoadNewActivity.putExtra("moreItens", true);
                startActivity(intentLoadNewActivity);
            }
        });

        datePickTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickDialog();
            }
        });

        datePickIb = findViewById(R.id.datePickIb);


        datePickIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                datePickDialog();
            }
        });

        imageButtonBack = findViewById(R.id.imageButtonBack);

        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });

        radioBxImported.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rg_1.clearCheck();
                String asd = usercoin+importedFee;
                feeTv.setText(asd);

                Double ocambio = Double.parseDouble(cambio.replace(",",".").replace(finalcoin,"").replace("null",""));
                Double cambiado = Double.parseDouble(importedFee)*ocambio;

                String change = String.valueOf(cambiado);
                TotalTv.setText(usercoin+change);

                radioBxImported.setTextColor(Order.this.getResources().getColor(R.color.colorPrimaryDark));
                radioBxImported.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.colorPrimaryDark)));
                radioBxCorreio.setTextColor(Order.this.getResources().getColor(R.color.black));
                radioBxCorreio.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                radioBxEntrega.setTextColor(Order.this.getResources().getColor(R.color.black));
                radioBxEntrega.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                radioBxRetirar.setTextColor(Order.this.getResources().getColor(R.color.black));
                radioBxRetirar.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
            }
        });
        radioBxCorreio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rg_1.clearCheck();
                String asd = usercoin+importedFee;
                feeTv.setText(asd);
                loadPersonalInfo();
                Double tri = Double.parseDouble(totalTv.getText().toString().replace(",",".").replace(finalcoin,"").replace("null",""));
                Double qya = Double.parseDouble(importedFee)+tri;

                String change = String.valueOf(qya);
                TotalTv.setText(usercoin+change);
                radioBxImported.setTextColor(Order.this.getResources().getColor(R.color.black));
                radioBxImported.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                radioBxCorreio.setTextColor(Order.this.getResources().getColor(R.color.colorPrimaryDark));
                radioBxCorreio.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.colorPrimaryDark)));
                radioBxEntrega.setTextColor(Order.this.getResources().getColor(R.color.black));
                radioBxEntrega.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                radioBxRetirar.setTextColor(Order.this.getResources().getColor(R.color.black));
                radioBxRetirar.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
            }
        }); radioBxEntrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rg_2.clearCheck();
                feeTv.setVisibility(VISIBLE);
                loadPersonalInfo();
                radioBxImported.setTextColor(Order.this.getResources().getColor(R.color.black));
                radioBxImported.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                radioBxCorreio.setTextColor(Order.this.getResources().getColor(R.color.black));
                radioBxCorreio.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                radioBxEntrega.setTextColor(Order.this.getResources().getColor(R.color.colorPrimaryDark));
                radioBxEntrega.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.colorPrimaryDark)));
                radioBxRetirar.setTextColor(Order.this.getResources().getColor(R.color.black));
                radioBxRetirar.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
            }
        }); radioBxRetirar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feeTv.setVisibility(GONE);
                rg_2.clearCheck();
                TotalTv.setText(totalTv.getText());
                radioBxImported.setTextColor(Order.this.getResources().getColor(R.color.black));
                radioBxImported.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                radioBxCorreio.setTextColor(Order.this.getResources().getColor(R.color.black));
                radioBxCorreio.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                radioBxEntrega.setTextColor(Order.this.getResources().getColor(R.color.black));
                radioBxEntrega.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                radioBxRetirar.setTextColor(Order.this.getResources().getColor(R.color.colorPrimaryDark));
                radioBxRetirar.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.colorPrimaryDark)));
            }
        });
        cashR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formadepag = "Na Entrega";
                cashR.setTextColor(Order.this.getResources().getColor(R.color.colorPrimaryDark));
                cashR.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.colorPrimaryDark)));
                creditCardR.setTextColor(Order.this.getResources().getColor(R.color.black));
                creditCardR.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                googlepay.setVisibility(GONE);
                btnFinishOrder.setVisibility(VISIBLE);
                naEntrega.setVisibility(VISIBLE);
                trocoTv.setVisibility(VISIBLE);
                //pix cartao dinheiro
            }
        });



        creditCardR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formadepag = "Pago no Aplicativo";
                creditCardR.setTextColor(Order.this.getResources().getColor(R.color.colorPrimaryDark));
                creditCardR.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.colorPrimaryDark)));
                cashR.setTextColor(Order.this.getResources().getColor(R.color.black));
                cashR.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                googlepay.setVisibility(VISIBLE);
                btnFinishOrder.setVisibility(GONE);
                trocoTv.setVisibility(GONE);
                naEntrega.setVisibility(GONE);
            }
        });

        pix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formadepag = "Pix na entrega";
                trocoTv.setVisibility(GONE);
                pix.setTextColor(Order.this.getResources().getColor(R.color.colorPrimaryDark));
                pix.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.colorPrimaryDark)));
                cartao.setTextColor(Order.this.getResources().getColor(R.color.black));
                cartao.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                dinheiro.setTextColor(Order.this.getResources().getColor(R.color.black));
                dinheiro.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
            }
        });

        cartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formadepag = "Cartão na entrega";
                trocoTv.setVisibility(GONE);
                cartao.setTextColor(Order.this.getResources().getColor(R.color.colorPrimaryDark));
                cartao.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.colorPrimaryDark)));
                pix.setTextColor(Order.this.getResources().getColor(R.color.black));
                pix.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                dinheiro.setTextColor(Order.this.getResources().getColor(R.color.black));
                dinheiro.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
            }
        });

        dinheiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formadepag = "Dinheiro";
                dinheiro.setTextColor(Order.this.getResources().getColor(R.color.colorPrimaryDark));
                dinheiro.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.colorPrimaryDark)));
                cartao.setTextColor(Order.this.getResources().getColor(R.color.black));
                cartao.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                pix.setTextColor(Order.this.getResources().getColor(R.color.black));
                pix.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                trocoTv.setVisibility(VISIBLE);
                trocoTv.setEnabled(true);
                trocoTv.setText(getString(R.string.precisatroco));
            }
        });

        DocumentReference docRef = fStore.collection("Users").document(fAuth.getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String countryuser = document.getString("country");

                        if (Objects.equals(countryuser, "Brasil")) {
                            usercoin = "R$";
                        }
                        if (Objects.equals(countryuser, "Estados Unidos da América")) {
                            usercoin = "US$";
                        }
                        if (Objects.equals(countryuser, "Inglaterra")) {
                            usercoin = "£";
                        }
                        if (Objects.equals(countryuser, "Alemanha")) {
                            usercoin = "€";
                        }
                        if (Objects.equals(countryuser, "França")) {
                            usercoin = "€";
                        }
                        if (Objects.equals(countryuser, "Portugal")) {
                            usercoin = "€";
                        }
                        if (Objects.equals(countryuser, "Espanha")) {
                            usercoin = "€";
                        }

                    }
                }
            } });

        if(radioBxImported.isChecked()){
                            if (Objects.equals(usercoin, "R$")){

                                DocumentReference docRef2 = fStore.collection("Cambio").document(productCountry);
                                docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                cambio = document.getString("valor");
                                                Double ocambio = Double.parseDouble(cambio.replace(",",".").replace(finalcoin,"").replace("null",""));
                                                Double cambiado = feetotal*ocambio;

                                                String change = String.valueOf(cambiado);
                                                TotalTv.setText(usercoin+change.replace("null", "").replace(".", ","));
                                            }
                                        }
                                    }
                                });
                            }
                            if (Objects.equals(usercoin, "US$")){

                                DocumentReference docRef2 = fStore.collection("Cambio2").document(productCountry);
                                docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                cambio = document.getString("valor");
                                                Double ocambio = Double.parseDouble(cambio.replace(",",".").replace(finalcoin,"").replace("null",""));
                                                Double cambiado = feetotal*ocambio;

                                                String change = String.valueOf(cambiado);
                                                TotalTv.setText(usercoin+change.replace("null", "").replace(".", ","));
                                            }
                                        }
                                    }
                                });
                            }
                            if (Objects.equals(usercoin, "£")){

                                DocumentReference docRef2 = fStore.collection("Cambio3").document(productCountry);
                                docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                cambio = document.getString("valor");
                                                Double ocambio = Double.parseDouble(cambio.replace(",",".").replace(finalcoin,"").replace("null",""));
                                                Double cambiado = feetotal*ocambio;

                                                String change = String.valueOf(cambiado);
                                                TotalTv.setText(usercoin+change.replace("null", "").replace(".", ","));
                                            }
                                        }
                                    }
                                });
                            }
                            if (Objects.equals(usercoin, "€")){

                                DocumentReference docRef2 = fStore.collection("Cambio4").document(productCountry);
                                docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                cambio = document.getString("valor");
                                                Double ocambio = Double.parseDouble(cambio.replace(",",".").replace(finalcoin,"").replace("null",""));
                                                Double cambiado = feetotal*ocambio;

                                                String change = String.valueOf(cambiado);
                                                TotalTv.setText(usercoin+change.replace("null", "").replace(".", ","));
                                            }
                                        }
                                    }
                                });
                            }

        }else{
            TotalTv.setText(finalcoin+String.valueOf(feetotal).replace("null", "").replace(".", ","));
        }

        trocoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText trocoEdit = new EditText(v.getContext());
                AlertDialog.Builder trocoDialog = new AlertDialog.Builder(v.getContext(), R.style.MyAlertDialogTheme);
                trocoDialog.setTitle(R.string.trocopara);
                trocoDialog.setMessage(R.string.digitetroc);
                trocoDialog.setView(trocoEdit);

                trocoDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        troco = trocoEdit.getText().toString();
                        trocoTv.setText(getString(R.string.trocopara)+" "+ troco+getString(R.string.clialter));
                    }
                });
                trocoDialog.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                trocoDialog.create().show();
            }
        });


        String finalCoin = finalcoin;
        cupomTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText codeCupom = new EditText(v.getContext());
                AlertDialog.Builder cupomDialog = new AlertDialog.Builder(v.getContext(), R.style.MyAlertDialogTheme);
                cupomDialog.setTitle(getString(R.string.cupom));
                cupomDialog.setMessage(R.string.ccaqui);
                cupomDialog.setView(codeCupom);

                cupomDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String code = codeCupom.getText().toString();

                        Query reference = FirebaseDatabase.getInstance().getReference("Users").child(shopUid).child("Promotions").child(code);
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String promoCode = dataSnapshot.child("promoCode").getValue().toString();
                                String promoPrice = dataSnapshot.child("promoPrice").getValue().toString();
                                String mimnimumOrderPrice = dataSnapshot.child("mimnimumOrderPrice").getValue().toString();
                                String expiredDate = dataSnapshot.child("expiredDate").getValue().toString();
                                String expiredTimestamp = dataSnapshot.child("expiredTimestamp").getValue().toString();

                                final String timestampnow = ""+System.currentTimeMillis();
                                long now = Long.parseLong(timestampnow);
                                long exp = Long.parseLong(expiredTimestamp);

                                Date mytime = null;


                                String total = totalTv.getText().toString().replace(",",".").replace(finalcoin,"").replace("null", "");
                                String discount = promoPrice.replace(",", ".").replace(finalcoin, "").replace("null", "");
                                String minim = mimnimumOrderPrice.replace(",", ".").replace(finalcoin, "").replace("null", "");

                                double tot = Double.parseDouble(total);
                                double disc = Double.parseDouble(discount);
                                double min = Double.parseDouble(minim);

                                    if(tot >= min && !Objects.equals(cupomaplicado, "true")) {
                                        if (now <= exp) {

                                            double cal = tot - disc;
                                            String fina = String.valueOf(cal);
                                            TotalTv.setText(fina.replace(".", ",").replace("null", ""));
                                            totalTv.setText(fina.replace(".", ",").replace("null", ""));
                                            cupomaplicado = "true";
                                            Toast.makeText(Order.this, R.string.cupapli, Toast.LENGTH_SHORT).show();
                                            cupomTv.setText(code+" "+getString(R.string.cupapli));
                                            cpm = "true";
                                            cupomTv.setEnabled(false);
                                        } else {
                                                  Toast.makeText(Order.this, R.string.cupexp, Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        Toast.makeText(Order.this, R.string.valormini, Toast.LENGTH_SHORT).show();
                                    }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                throw databaseError.toException();
                            }
                        });

                    }
                });
                cupomDialog.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                cupomDialog.create().show();
            }
        });


        googlepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Objects.equals(scheduleProduction, "")) {
                    schedule = datePickTv.getText().toString();
                }
                String x = TotalTv.getText().toString().replace(".","").replace(",","").replace(finalcoin,"").replace("null","").replace("US$","")
                                .replace("£","").replace(usercoin,"").replace("R$","");
                        Double y = Double.parseDouble(x);
                        Double z = 0.0;
                        if(radioBxCorreio.isChecked()){
                             z = y;
                        } else {
                            z=y/10;
                        }
                        amount = String.valueOf(z).replace(".","").replace(",","").replace(finalcoin,"").replace("£","").replace("US$","").replace("null","");
                        Log.d(TAG, "amount"+amount);
                        getDetails();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void apihigh() {
        // timezone I'm working on (use JVM default, or a specific one, like ZoneId.of("America/New_York")
        ZoneId zone = ZoneId.systemDefault();
// today
        LocalDate today = LocalDate.now(zone);
//get user week day
        Calendar sCalendar = Calendar.getInstance();
        String dayLongName = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
//get user local time
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

//get shop time
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection("Adm's").document(shopUid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
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

                        String horaab = "";
                        String horafe = "";

                        if(Objects.equals(dayLongName, "segunda-feira")){
                            if(Objects.equals(segundou, "true")) {
                                horaab = segabrio;
                                horafe = segfecho;
                            } else {
                                fechadoDialog();
                                return;
                            }
                        }

                        if(Objects.equals(dayLongName, "terça-feira")){
                            if(Objects.equals(tercou, "true")) {
                                horaab = terabrio;
                                horafe = terfecho;
                            } else {
                                fechadoDialog();
                                return;
                            }
                        }

                        if(Objects.equals(dayLongName, "quarta-feira")){
                            if(Objects.equals(quartou, "true")) {
                                horaab = quaabrio;
                                horafe = quafecho;
                            } else {
                                fechadoDialog();
                                return;
                            }
                        }

                        if(Objects.equals(dayLongName, "quinta-feira")){
                            if(Objects.equals(quintou, "true")) {
                                horaab = quiabrio;
                                horafe = quifecho;
                            } else {
                                fechadoDialog();
                                return;
                            }
                        }

                        if(Objects.equals(dayLongName, "sexta-feira")){
                            if(Objects.equals(sextou, "true")) {
                                horaab = sexabrio;
                                horafe = sexfecho;
                            } else {
                                fechadoDialog();
                                return;
                            }
                        }

                        if(Objects.equals(dayLongName, "sábado")){
                            if(Objects.equals(sabadou, "true")) {
                                horaab = sababrio;
                                horafe = sabfecho;
                            } else {
                                fechadoDialog();
                                return;
                            }
                        }

                        if(Objects.equals(dayLongName, "domingo")){
                            if(Objects.equals(domingou, "true")) {
                                horaab = domabrio;
                                horafe = domfecho;
                            } else {
                                fechadoDialog();
                                return;
                            }
                        }

                        if(!Objects.equals(horaab, "") || !Objects.equals(horafe, "")) {

                            LocalTime openShop = LocalTime.parse(horaab); // or LocalTime.parse("16:00") if you have a String
                            LocalTime closeShop = LocalTime.parse(horafe); // or LocalTime.parse("02:00") if you have a String


                            ZonedDateTime rest1Start = today.atTime(openShop).atZone(zone);
                            ZonedDateTime rest1End = today.atTime(closeShop).atZone(zone);


                            ZonedDateTime zdt = today.atTime(LocalTime.parse(currentTime)).atZone(zone);

                            if (rest1Start.isAfter(zdt) || rest1End.isBefore(zdt)) {
                                // restaurant 1 is closed
                                AlertDialog.Builder builder = new AlertDialog.Builder(Order.this, R.style.MyAlertDialogTheme);
                                builder.setIcon(R.drawable.ic_baseline_error);
                                builder.setTitle("Este estabelecimento está fechado");
                                builder.setMessage("Confira o horário e tente novamente mais tarde.");
                                builder.setCancelable(false);

                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        onBackPressed();
                                    }
                                });
                                builder.create();
                                builder.show();
                            } else {
                                // restaurant 1 is open

                            }
                        } else{
                            fechadoDialog();
                            return;
                        }

                    }}}});




    }

    private void fechadoDialog() {
        // restaurant 1 is closed
        AlertDialog.Builder builder = new AlertDialog.Builder ( Order.this, R.style.MyAlertDialogTheme);
        builder.setIcon (R.drawable.ic_baseline_error);
        builder.setTitle ("Este estabelecimento está fechado");
        builder.setMessage ("Confira o horário e tente novamente mais tarde.");
        builder.setCancelable(false);
        builder.setPositiveButton ( "Ok", new DialogInterface.OnClickListener ( ) {
            @Override
            public void onClick ( DialogInterface dialog, int which ) {
                onBackPressed();
            }
        } );
        builder.create ( );
        builder.show ( );
    }

    private void alertOffline() {
        AlertDialog.Builder builder = new AlertDialog.Builder ( this, R.style.MyAlertDialogTheme);
        builder.setIcon ( R.drawable.ic_baseline_wifi_off_24_green );
        builder.setTitle (R.string.vocenaopossuint);
        builder.setMessage (R.string.verifiqint);

        builder.setPositiveButton ( "Ok", new DialogInterface.OnClickListener ( ) {
            @Override
            public void onClick ( DialogInterface dialog, int which ) {
                finish ( );
            }
        } );
        builder.create ( );
        builder.show ( );
    }

    void getDetails(){

        if(Objects.equals(usercoin, "R$")){

            Fuel.INSTANCE.post(""+amount
                    , null).responseString(new Handler<String>() {
                @Override
                public void success(String s) {
                    try{
                        JSONObject result = new JSONObject(s);
                        customerConfig = new PaymentSheet.CustomerConfiguration(
                                result.getString("customer"),
                                result.getString("ephemeralKey")
                        );
                        iddopagamento = result.getString("customer");
                        paymentIntentClientSecret = result.getString("paymentIntent");
                        PaymentConfiguration.init(getApplicationContext(), result.getString("publishableKey"));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showStripePaymentSheet();
                            }
                        });

                    } catch (JSONException e){
                        Toast.makeText(Order.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(@NonNull FuelError fuelError) {
                }
            });}

        if(Objects.equals(usercoin, "€")){

            Fuel.INSTANCE.post(""+amount
                    , null).responseString(new Handler<String>() {
                @Override
                public void success(String s) {
                    try{
                        JSONObject result = new JSONObject(s);
                        customerConfig = new PaymentSheet.CustomerConfiguration(
                                result.getString("customer"),
                                result.getString("ephemeralKey")
                        );
                        iddopagamento = result.getString("customer");
                        paymentIntentClientSecret = result.getString("paymentIntent");
                        PaymentConfiguration.init(getApplicationContext(), result.getString("publishableKey"));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showStripePaymentSheet();
                            }
                        });

                    } catch (JSONException e){
                        Toast.makeText(Order.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(@NonNull FuelError fuelError) {
                }
            });}

        if(Objects.equals(usercoin, "US$")){

            Fuel.INSTANCE.post(""+amount
                    , null).responseString(new Handler<String>() {
                @Override
                public void success(String s) {
                    try{
                        JSONObject result = new JSONObject(s);
                        customerConfig = new PaymentSheet.CustomerConfiguration(
                                result.getString("customer"),
                                result.getString("ephemeralKey")
                        );
                        iddopagamento = result.getString("customer");
                        paymentIntentClientSecret = result.getString("paymentIntent");
                        PaymentConfiguration.init(getApplicationContext(), result.getString("publishableKey"));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showStripePaymentSheet();
                            }
                        });

                    } catch (JSONException e){
                        Toast.makeText(Order.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(@NonNull FuelError fuelError) {
                }
            });}

        if(Objects.equals(usercoin, "£")){

            Fuel.INSTANCE.post(""+amount
                    , null).responseString(new Handler<String>() {
                @Override
                public void success(String s) {
                    try{
                        JSONObject result = new JSONObject(s);
                        customerConfig = new PaymentSheet.CustomerConfiguration(
                                result.getString("customer"),
                                result.getString("ephemeralKey")
                        );
                        iddopagamento = result.getString("customer");
                        paymentIntentClientSecret = result.getString("paymentIntent");
                        PaymentConfiguration.init(getApplicationContext(), result.getString("publishableKey"));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showStripePaymentSheet();
                            }
                        });

                    } catch (JSONException e){
                        Toast.makeText(Order.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(@NonNull FuelError fuelError) {
                }
            });}

    }

    void showStripePaymentSheet(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        final PaymentSheet.Configuration configuration = new PaymentSheet.Configuration.Builder("Vegan")
                .customer(customerConfig)
                .allowsDelayedPaymentMethods(false)// como verdadeiro permite formas de pagamento de notificacao assincrona
                .build();
        paymentSheet.presentWithPaymentIntent(
                paymentIntentClientSecret,
                configuration
        );
    }

    void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult){

        if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Toast.makeText(Order.this, R.string.cancelado, Toast.LENGTH_SHORT).show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Toast.makeText(Order.this, ((PaymentSheetResult.Failed) paymentSheetResult).getError().toString(), Toast.LENGTH_SHORT).show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Completed) {

            Double cal = Double.parseDouble(amount.replace(",",".").replace(finalcoin,"").replace("null",""));
            Double cal2 = Double.parseDouble(apagaraoadm.replace(",",".").replace(finalcoin,"").replace("null",""));
            Double tot = cal + cal2;
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("apagaraoadm",""+tot);
            DocumentReference documentReference = fStore.collection("Adm's").document(shopUid);
            documentReference.update(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void avoid){

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

            String cost = TotalTv.getText().toString().trim().replace(".",",");
            final HashMap<String, String> hashMap1 = new HashMap<>();
            hashMap1.put("orderId", ""+timestamp);
            hashMap1.put("tipoDeCompra", ""+tipoDeCompra);
            hashMap1.put("orderTime", ""+timestamp);
            hashMap1.put("orderStatus", getString(R.string.emanalise));
            hashMap1.put("orderCost", ""+cost);
            hashMap1.put("orderBy", ""+firebaseAuth.getUid());
            hashMap1.put("orderTo",""+shopUid);
            hashMap1.put("cpm",""+cpm);
            hashMap1.put("codentrega",""+codentrega);
            hashMap1.put("schedule",schedule);
            hashMap1.put("recebeu","");
            hashMap1.put("iddopagamento", ""+iddopagamento);
            hashMap1.put("obs",""+editObservation.getText().toString());


            final DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Users").child(shopUid).child("Orders");
            ref1.child(timestamp).setValue(hashMap1)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            for(int i=0; i<cartItemList.size(); i++) {
                                String pId = cartItemList.get(i).getpId();
                                String id = cartItemList.get(i).getId();
                                String cost = cartItemList.get(i).getCost();
                                String name = cartItemList.get(i).getName();
                                String price = cartItemList.get(i).getPrice();
                                quantity = cartItemList.get(i).getQuantity();
                                String size  = cartItemList.get(i).getSize();
                                String adc1  = cartItemList.get(i).getAdc1();
                                String adc2  = cartItemList.get(i).getAdc2();
                                String adc3  = cartItemList.get(i).getAdc3();
                                String adc4  = cartItemList.get(i).getAdc4();
                                String adc5  = cartItemList.get(i).getAdc5();
                                String adc6  = cartItemList.get(i).getAdc6();
                                String adc7  = cartItemList.get(i).getAdc7();
                                String adc8  = cartItemList.get(i).getAdc8();
                                String adc9  = cartItemList.get(i).getAdc9();
                                String adc10 = cartItemList.get(i).getAdc10();
                                String adc11 = cartItemList.get(i).getAdc11();
                                String adc12 = cartItemList.get(i).getAdc12();
                                String adc13 = cartItemList.get(i).getAdc13();
                                String adc14 = cartItemList.get(i).getAdc14();
                                String adc15 = cartItemList.get(i).getAdc15();
                                String va1  = cartItemList.get(i).getVa1();
                                String va2  = cartItemList.get(i).getVa2();
                                String va3  = cartItemList.get(i).getVa3();
                                String va4  = cartItemList.get(i).getVa4();
                                String va5  = cartItemList.get(i).getVa5();
                                String va6  = cartItemList.get(i).getVa6();
                                String va7  = cartItemList.get(i).getVa7();
                                String va8  = cartItemList.get(i).getVa8();
                                String va9  = cartItemList.get(i).getVa9();
                                String va10 = cartItemList.get(i).getVa10();
                                String va11 = cartItemList.get(i).getVa11();
                                String va12 = cartItemList.get(i).getVa12();
                                String va13 = cartItemList.get(i).getVa13();
                                String va14 = cartItemList.get(i).getVa14();
                                String va15 = cartItemList.get(i).getVa15();
                                HashMap<String, String> hashMap1 = new HashMap<>();
                                hashMap1.put("pId", pId);
                                hashMap1.put("name", name);
                                hashMap1.put("cost", cost);
                                hashMap1.put("price", price);
                                hashMap1.put("quantity", quantity);
                                hashMap1.put("size", size);
                                hashMap1.put("schedule",schedule);
                                hashMap1.put("va1", va1);
                                hashMap1.put("va2", va2);
                                hashMap1.put("va3", va3);
                                hashMap1.put("va4", va4);
                                hashMap1.put("va5", va5);
                                hashMap1.put("va6", va6);
                                hashMap1.put("va7", va7);
                                hashMap1.put("va8", va8);
                                hashMap1.put("va9", va9);
                                hashMap1.put("va10", va10);
                                hashMap1.put("va11", va11);
                                hashMap1.put("va12", va12);
                                hashMap1.put("va13", va13);
                                hashMap1.put("va14", va14);
                                hashMap1.put("va15", va15);
                                hashMap1.put("adc1", adc1);
                                hashMap1.put("adc2", adc2);
                                hashMap1.put("adc3", adc3);
                                hashMap1.put("adc4", adc4);
                                hashMap1.put("adc5", adc5);
                                hashMap1.put("adc6", adc6);
                                hashMap1.put("adc7", adc7);
                                hashMap1.put("adc8", adc8);
                                hashMap1.put("adc9", adc9);
                                hashMap1.put("adc10", adc10);
                                hashMap1.put("adc11", adc11);
                                hashMap1.put("adc12", adc12);
                                hashMap1.put("adc13", adc13);
                                hashMap1.put("adc14", adc14);
                                hashMap1.put("adc15", adc15);
                                ref1.child(timestamp).child("Items").child(id).setValue(hashMap1);
                            }
                            progressDialog.dismiss();
                            Toast.makeText(Order.this, R.string.preencha, Toast.LENGTH_SHORT).show();
                            // prepareNotificationMessage(timestamp);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //failed placing order
                            progressDialog.dismiss();
                            Toast.makeText(Order.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


            long l = Long.parseLong(timestamp);

            Calendar cal3 = Calendar.getInstance(Locale.ENGLISH);
            cal3.setTimeInMillis(l);
            String date = DateFormat.format("dd/MM/yy", cal3).toString();
            String hour = DateFormat.format("HH:mm", cal3).toString();


            final HashMap<String, String> hashMap5 = new HashMap<>();
            hashMap5.put("orderId", ""+timestamp);
            hashMap5.put("date", ""+date);
            hashMap5.put("hour", ""+hour);
            hashMap5.put("tipoDeCompra", ""+tipoDeCompra);
            hashMap5.put("orderTime", ""+timestamp);
            hashMap5.put("orderStatus", getString(R.string.emanalise));
            hashMap5.put("orderBy", ""+firebaseAuth.getUid());
            hashMap5.put("orderTo",""+shopUid);
            hashMap5.put("cliente", ""+nomeuser);
            hashMap5.put("total", ""+TotalTv.getText().toString());
            hashMap5.put("formadepagamento", getString(R.string.pagonocartao));
            hashMap5.put("iddopagamento", ""+iddopagamento);
            hashMap5.put("schedule",schedule);
            hashMap5.put("troco", ""+troco);
            hashMap5.put("codentrega",""+codentrega);
            hashMap5.put("cpm", ""+cpm);
            hashMap5.put("obs",""+editObservation.getText().toString());
            hashMap5.put("recebeu","");
            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("OrderTo").child(shopUid).child(timestamp);
            ref.setValue(hashMap5)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            for(int i=0; i<cartItemList.size(); i++) {
                                String pId = cartItemList.get(i).getpId();
                                String id = cartItemList.get(i).getId();
                                String cost = cartItemList.get(i).getCost();
                                String name = cartItemList.get(i).getName();
                                String price = cartItemList.get(i).getPrice();
                                quantity = cartItemList.get(i).getQuantity();

                                HashMap<String, String> hashMap1 = new HashMap<>();
                                hashMap1.put("pId", pId);
                                hashMap1.put("name", name);
                                hashMap1.put("cost", cost);
                                hashMap1.put("price", price);
                                hashMap1.put("schedule",schedule);
                                hashMap1.put("troco", ""+troco);
                                hashMap1.put("quantity", quantity);
                                ref.child("Items").child(id).setValue(hashMap1);

                                DocumentReference docRef0 = fStore.collection("Points "+shopUid).document("Fidelidade");
                                docRef0.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                String premio = document.getString("premio");

                                                if (Objects.equals(premio, name)) {
                                                    temfiel = true;

                                                    DocumentReference docRef = fStore.collection("Fidelidade "+userID).document(shopUid);
                                                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                DocumentSnapshot document = task.getResult();
                                                                if (document.exists()) {
                                                                    pontos = document.getString("pontos");
                                                                    if (pontos == null || pontos.equals("")) {
                                                                        DocumentReference docRef7 = fStore.collection("Fidelidade " + userID).document(shopUid);
                                                                        Map<String, Object> edited = new HashMap<>();
                                                                        edited.put("pontos", "0");
                                                                        docRef7.set(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid) {
                                                                            }
                                                                        });
                                                                        pontos = "0";
                                                                    } else {
                                                                        int calpontos = Integer.parseInt(pontos);
                                                                        int calquan = Integer.parseInt(quantity);
                                                                        if (calpontos + calquan >= 10) {
                                                                            ganhoufiel = true;
                                                                            HashMap<String, String> hashMap1 = new HashMap<>();
                                                                            hashMap1.put("pId", pId);
                                                                            hashMap1.put("name", name);
                                                                            hashMap1.put("cost", cost);
                                                                            hashMap1.put("price", price);
                                                                            hashMap1.put("troco", ""+troco);
                                                                            hashMap1.put("schedule",schedule);
                                                                            quantity = Integer.toString(calquan + 1);
                                                                            hashMap1.put("quantity", quantity);
                                                                            ref.child("Items").child(id).setValue(hashMap1);
                                                                            Toast.makeText(Order.this,R.string.parabenfree , Toast.LENGTH_LONG).show();
                                                                        } else {
                                                                            DocumentReference docRef2 = fStore.collection("Fidelidade " + userID).document(shopUid);
                                                                            Map<String, Object> edited = new HashMap<>();
                                                                            int mypointsnow = calpontos + calquan;
                                                                            String pontosstr = Integer.toString(mypointsnow);
                                                                            edited.put("pontos", pontosstr);
                                                                            docRef2.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {
                                                                                }
                                                                            });
                                                                        }}} else {
                                                                    DocumentReference docRef7 = fStore.collection("Fidelidade " + userID).document(shopUid);
                                                                    Map<String, Object> edited = new HashMap<>();
                                                                    edited.put("pontos", "0");
                                                                    docRef7.set(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {
                                                                        }
                                                                    });
                                                                    pontos = "0";
                                                                    int calpontos = Integer.parseInt(pontos);
                                                                    int calquan = Integer.parseInt(quantity);
                                                                    if (calpontos + calquan >= 10) {
                                                                        ganhoufiel = true;
                                                                        HashMap<String, String> hashMap1 = new HashMap<>();
                                                                        hashMap1.put("pId", pId);
                                                                        hashMap1.put("name", name);
                                                                        hashMap1.put("cost", cost);
                                                                        hashMap1.put("price", price);
                                                                        hashMap1.put("troco", ""+troco);
                                                                        hashMap1.put("schedule",schedule);
                                                                        quantity = Integer.toString(calquan + 1);
                                                                        hashMap1.put("quantity", Integer.toString(calquan + 1));
                                                                        ref.child("Items").child(id).setValue(hashMap1);
                                                                        DocumentReference docRef8 = fStore.collection("Fidelidade " + userID).document(shopUid);
                                                                        Map<String, Object> edited8 = new HashMap<>();
                                                                        edited8.put("pontos", "0");
                                                                        docRef8.set(edited8).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid) {
                                                                            }
                                                                        });
                                                                    } else {
                                                                        DocumentReference docRef2 = fStore.collection("Fidelidade " + userID).document(shopUid);
                                                                        Map<String, Object> edited2 = new HashMap<>();
                                                                        int mypointsnow = calpontos + calquan;
                                                                        String pontosstr = Integer.toString(mypointsnow);
                                                                        edited2.put("pontos", pontosstr);
                                                                        docRef2.update(edited2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid) {
                                                                            }
                                                                        });
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                            final HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("orderId", ""+timestamp);
                            hashMap.put("tipoDeCompra", ""+tipoDeCompra);
                            hashMap.put("orderTime", ""+timestamp);
                            hashMap.put("orderStatus", getString(R.string.emanalise));
                            hashMap.put("orderBy", ""+firebaseAuth.getUid());
                            hashMap.put("orderTo",""+shopUid);
                            hashMap.put("cliente", ""+nomeuser);
                            hashMap.put("deliveryFee", ""+feeTv.getText().toString());
                            hashMap.put("total", ""+TotalTv.getText().toString());
                            hashMap.put("troco", ""+troco);
                            hashMap.put("schedule",schedule);
                            hashMap.put("formadepagamento", getString(R.string.pagonocartao));
                            hashMap.put("iddopagamento", ""+iddopagamento);
                            hashMap.put("codentrega",""+codentrega);
                            hashMap.put("recebeu","");
                            if(ganhoufiel){
                                hashMap.put("fidelidade", "true");}
                            hashMap.put("cpm", cpm);
                            hashMap.put("obs",""+editObservation.getText().toString());

                            final DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("OrderBy").child(userID).child(timestamp);
                            ref2.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            for(int i=0; i<cartItemList.size(); i++) {
                                                String pId = cartItemList.get(i).getpId();
                                                String id = cartItemList.get(i).getId();
                                                String cost = cartItemList.get(i).getCost();
                                                String name = cartItemList.get(i).getName();
                                                String price = cartItemList.get(i).getPrice();

                                                HashMap<String, String> hashMap1 = new HashMap<>();
                                                hashMap1.put("pId", pId);
                                                hashMap1.put("name", name);
                                                hashMap1.put("cost", cost);
                                                hashMap1.put("price", price);
                                                hashMap1.put("troco", ""+troco);
                                                hashMap1.put("quantity", quantity);
                                                hashMap1.put("schedule",schedule);
                                                ref2.child("Items").child(id).setValue(hashMap1);

                                            }
                                            progressDialog.dismiss();

                                            Intent intent = new Intent(Order.this, Espere.class);
                                            intent.putExtra("vemdocarrinho", "sim");
                                            intent.putExtra("userID", userID);
                                            Log.d(TAG, "userID "+userID);
                                            intent.putExtra("title", name);
                                            intent.putExtra("status", getString(R.string.emanalise));
                                            intent.putExtra("shopUid", shopUid);
                                            intent.putExtra("photo", foto);
                                            intent.putExtra("tipoDeCompra", tipoDeCompra);
                                            intent.putExtra("price", TotalTv.getText().toString());
                                            intent.putExtra("troco", troco);
                                            intent.putExtra("brandName", brand);
                                            intent.putExtra("schedule",schedule);
                                            intent.putExtra("orderId", timestamp);
                                            intent.putExtra("cpm", cpm);
                                            intent.putExtra("deliveryFee", feeTv.getText().toString());
                                            intent.putExtra("codentrega", codentrega);
                                            intent.putExtra("obs", editObservation.getText().toString());
                                            if(ganhoufiel){
                                                intent.putExtra("fidelidade", "true");
                                            }
                                            intent.putExtra("pagamento", getString(R.string.pagonocartao));

                                            progressDialog.dismiss();
                                            Toast.makeText(Order.this, R.string.pedreal, Toast.LENGTH_SHORT).show();
                                            finish();
                                            sendPush();
                                            startActivity(intent);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(Order.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                            progressDialog.dismiss();
                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Order.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


        }
    }


    private void loadPersonalInfo() {
       if(firebaseAuth.getUid() != null) {
           DocumentReference docRef = fStore.collection("Users").document(fAuth.getCurrentUser().getUid());
           docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                  @Override
                                                  public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                      if (task.isSuccessful()) {
                                                          DocumentSnapshot document = task.getResult();
                                                          if (document.exists()) {
                                                              String novato = document.getString("novato");

                                                              if (Objects.equals(novato, "true")) {
                                                                  Toast.makeText(Order.this, "Apartir de 50 reais, pagando no app, receba seu desconto de boas vindas no final!", Toast.LENGTH_LONG).show();

                                                              }
                                                          }
                                                      }
                                                  }
                                              });


        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
            rootRef.collection("Users").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            name = document.getString("fName");
                            String neighb = document.getString("neighborhood");
                            String address = document.getString("street");
                            String number = document.getString("number");
                            String reference = document.getString("reference");
                             estat = document.getString("state");
                             cit = document.getString("city");
                            countr = document.getString("country");
                            String pccep = document.getString("cep");

                            nomeuser = name;
                            estado.setText(estat);
                            cidade.setText(cit);
                            pais.setText(countr);
                            postalcode.setText(pccep);
                            bairro.setText(neighb);
                            numero.setText(number);
                            referencia.setText(reference);
                            endereco.setText(address);

                            lojaname.setText(brand);

                            DocumentReference docReference = fStore.collection("Adm's").document(shopUid);
                            docReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            String productIcon = document.getString("shopIcon");
                                            String fName = document.getString("fName");
                                            String admcountry = document.getString("country");
                                            String admstate = document.getString("state");
                                            admcity = document.getString("city");
                                            String shopCep = document.getString("cep");
                                            String dinheiroEnt = document.getString("dinheiroEnt");
                                            String pixEnt = document.getString("pixEnt");
                                            String cartaoEnt = document.getString("cartaoEnt");
                                            fee = document.getString("deliveryFee");
                                            nationalFee = document.getString("nationalFee");
                                            importedFee = document.getString("importedFee");
                                            timetable = document.getString("timetable");
                                            daytable = document.getString("daytable");
                                            apagaraoadm = document.getString("apagaraoadm");
                                            premiumisok = document.getString("premium");
                                            deliveryFixo = document.getString("deliveryFixo");

                                            if(!Objects.equals(dinheiroEnt, "true")){
                                                dinheiro.setVisibility(GONE);
                                                creditCardR.isChecked();
                                                formadepag = "Pago no Aplicativo";
                                                creditCardR.setTextColor(Order.this.getResources().getColor(R.color.colorPrimaryDark));
                                                creditCardR.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.colorPrimaryDark)));
                                                cashR.setTextColor(Order.this.getResources().getColor(R.color.black));
                                                cashR.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                                                googlepay.setVisibility(VISIBLE);
                                                btnFinishOrder.setVisibility(GONE);
                                                trocoTv.setVisibility(GONE);
                                                naEntrega.setVisibility(GONE);
                                            }
                                            if(!Objects.equals(pixEnt, "true")){
                                                pix.setVisibility(GONE);
                                                creditCardR.isChecked();
                                                formadepag = "Pago no Aplicativo";
                                                creditCardR.setTextColor(Order.this.getResources().getColor(R.color.colorPrimaryDark));
                                                creditCardR.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.colorPrimaryDark)));
                                                cashR.setTextColor(Order.this.getResources().getColor(R.color.black));
                                                cashR.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                                                googlepay.setVisibility(VISIBLE);
                                                btnFinishOrder.setVisibility(GONE);
                                                trocoTv.setVisibility(GONE);
                                                naEntrega.setVisibility(GONE);
                                            }
                                            if(!Objects.equals(cartaoEnt, "true")){
                                                cartao.setVisibility(GONE);
                                                creditCardR.isChecked();
                                                formadepag = "Pago no Aplicativo";
                                                creditCardR.setTextColor(Order.this.getResources().getColor(R.color.colorPrimaryDark));
                                                creditCardR.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.colorPrimaryDark)));
                                                cashR.setTextColor(Order.this.getResources().getColor(R.color.black));
                                                cashR.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                                                googlepay.setVisibility(VISIBLE);
                                                btnFinishOrder.setVisibility(GONE);
                                                trocoTv.setVisibility(GONE);
                                                naEntrega.setVisibility(GONE);
                                            }
                                            if(!Objects.equals(dinheiroEnt, "true") && !Objects.equals(pixEnt, "true")
                                            && !Objects.equals(cartaoEnt, "true")) {
                                                cashR.setVisibility(GONE);
                                                creditCardR.isChecked();
                                                formadepag = "Pago no Aplicativo";
                                                creditCardR.setTextColor(Order.this.getResources().getColor(R.color.colorPrimaryDark));
                                                creditCardR.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.colorPrimaryDark)));
                                                cashR.setTextColor(Order.this.getResources().getColor(R.color.black));
                                                cashR.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                                                googlepay.setVisibility(VISIBLE);
                                                btnFinishOrder.setVisibility(GONE);
                                                trocoTv.setVisibility(GONE);
                                                naEntrega.setVisibility(GONE);
                                            }


                                            if (radioBxImported.isChecked()) {
                                                fee = importedFee;
                                            }
                                            if (radioBxCorreio.isChecked()) {
                                                fee = nationalFee;
                                            }

                                            if (!radioBxCorreio.isChecked() || !radioBxImported.isChecked()) {

                                                if(!Objects.equals(admcity, cit)) {
                                                } else {
                                                    //calculate deliver fee
                                                    final Geocoder geocoder = new Geocoder(Order.this);
                                                    final String zip = pccep;
                                                    try {
                                                        List<Address> addresses = geocoder.getFromLocationName(zip, 1);
                                                        if (addresses != null && !addresses.isEmpty()) {
                                                            Address address = addresses.get(0);
                                                            // Use the address as needed
                                                            //       String message = String.format("Latitude: %f, Longitude: %f",
                                                            //             address.getLatitude(), address.getLongitude());
                                                            //   Toast.makeText(Order.this, message, Toast.LENGTH_LONG).show();
                                                            latUser = String.format("%f",
                                                                    address.getLatitude());
                                                            longUser = String.format("%f",
                                                                    address.getLongitude());
                                                            Log.d(TAG, "latUser " + latUser);
                                                            Log.d(TAG, "longUser " + longUser);
                                                        } else {
                                                            // Display appropriate message when Geocoder services are not available
                                                            Toast.makeText(Order.this, "Unable to geocode zipcode", Toast.LENGTH_LONG).show();
                                                        }
                                                    } catch (IOException e) {
                                                        // handle exception
                                                    }

                                                    final Geocoder geocode = new Geocoder(Order.this);
                                                    final String zipe = shopCep;
                                                    try {
                                                        List<Address> addresses = geocode.getFromLocationName(zipe, 1);
                                                        if (addresses != null && !addresses.isEmpty()) {
                                                            Address address = addresses.get(0);
                                                            // Use the address as needed
                                                            //       String message = String.format("Latitude: %f, Longitude: %f",
                                                            //             address.getLatitude(), address.getLongitude());
                                                            //   Toast.makeText(Order.this, message, Toast.LENGTH_LONG).show();
                                                            latShop = String.format("%f",
                                                                    address.getLatitude());
                                                            longShop = String.format("%f",
                                                                    address.getLongitude());
                                                            Log.d(TAG, "longShop " + longShop);
                                                            Log.d(TAG, "latShop " + latShop);

                                                        } else {
                                                            // Display appropriate message when Geocoder services are not available
                                                            Toast.makeText(Order.this, "Unable to geocode zipcode", Toast.LENGTH_LONG).show();
                                                        }
                                                    } catch (IOException e) {
                                                        // handle exception
                                                    }

                                                    Location locationA = new Location("point A");

                                                    locationA.setLatitude(Double.parseDouble(latUser.replace(",", ".")));
                                                    locationA.setLongitude(Double.parseDouble(longUser.replace(",", ".")));

                                                    Location locationB = new Location("point B");

                                                    locationB.setLatitude(Double.parseDouble(latShop.replace(",", ".")));
                                                    locationB.setLongitude(Double.parseDouble(longShop.replace(",", ".")));

                                                    float distance = locationA.distanceTo(locationB);

                                                    Log.d(TAG, "distance in meters " + distance);

                                                    Double temporal1 = Double.parseDouble(String.valueOf(distance)) * 0.001;

                                                    Log.d(TAG, "distance in km " + temporal1);

                                                    Double temporal2 = temporal1 * 0.50;
                                                    Double temporal3 = temporal2 + Double.parseDouble(deliveryFixo);

                                                    Log.d(TAG, "delivery fee " + temporal2);

                                                    DecimalFormat numberFormat = new DecimalFormat("#.00");

                                                    String temporal4 = numberFormat.format(temporal3);

                                                    deliveryFee = temporal4;
                                                }
                                        }
                                            String now = daytable + " " + timetable;
                                            timeopening.setText(now);

                                            if(Objects.equals(admcity, cit)){
                                                tipoDeCompra = "Compra Local Delivery";
                                                radioBxEntrega.setVisibility(VISIBLE);
                                                radioBxCorreio.setVisibility(GONE);
                                                radioBxImported.setVisibility(GONE);
                                                radioBxEntrega.setChecked(true);
                                                radioBxImported.setChecked(false);
                                                radioBxEntrega.setTextColor(Order.this.getResources().getColor(R.color.colorPrimaryDark));
                                                radioBxEntrega.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.colorPrimaryDark)));
                                                radioBxCorreio.setTextColor(Order.this.getResources().getColor(R.color.black));
                                                radioBxCorreio.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                                                radioBxImported.setTextColor(Order.this.getResources().getColor(R.color.black));
                                                radioBxImported.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                                                radioBxRetirar.setTextColor(Order.this.getResources().getColor(R.color.black));
                                                radioBxRetirar.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                                            }

                                            if(!Objects.equals(admcity, cit)){
                                                tipoDeCompra = "Compra Nacional";
                                                radioBxRetirar.setVisibility(GONE);
                                                radioBxEntrega.setVisibility(GONE);
                                                radioBxCorreio.setVisibility(VISIBLE);
                                                radioBxImported.setVisibility(GONE);
                                                radioBxCorreio.setChecked(true);
                                                radioBxEntrega.setChecked(false);
                                                radioBxCorreio.setTextColor(Order.this.getResources().getColor(R.color.colorPrimaryDark));
                                                radioBxCorreio.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.colorPrimaryDark)));
                                                radioBxImported.setTextColor(Order.this.getResources().getColor(R.color.black));
                                                radioBxImported.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                                                radioBxEntrega.setTextColor(Order.this.getResources().getColor(R.color.black));
                                                radioBxEntrega.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                                                radioBxRetirar.setTextColor(Order.this.getResources().getColor(R.color.black));
                                                radioBxRetirar.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));

                                                cashR.setVisibility(GONE);
                                                creditCardR.isChecked();
                                                formadepag = "Pago no Aplicativo";
                                                creditCardR.setTextColor(Order.this.getResources().getColor(R.color.colorPrimaryDark));
                                                creditCardR.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.colorPrimaryDark)));
                                                cashR.setTextColor(Order.this.getResources().getColor(R.color.black));
                                                cashR.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                                                googlepay.setVisibility(VISIBLE);
                                                btnFinishOrder.setVisibility(GONE);
                                                trocoTv.setVisibility(GONE);
                                                naEntrega.setVisibility(GONE);

                                                String tempfee = "R$"+nationalFee;
                                                feeTv.setText(tempfee);

                                            }

                                            if(!Objects.equals(admcountry, countr)){
                                                tipoDeCompra = "Compra Internacional";
                                                radioBxRetirar.setVisibility(GONE);
                                                radioBxEntrega.setVisibility(GONE);
                                                radioBxCorreio.setVisibility(GONE);
                                                radioBxImported.setVisibility(VISIBLE);
                                                radioBxImported.setChecked(true);
                                                radioBxEntrega.setChecked(false);
                                                radioBxImported.setTextColor(Order.this.getResources().getColor(R.color.colorPrimaryDark));
                                                radioBxImported.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.colorPrimaryDark)));
                                                radioBxCorreio.setTextColor(Order.this.getResources().getColor(R.color.black));
                                                radioBxCorreio.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                                                radioBxEntrega.setTextColor(Order.this.getResources().getColor(R.color.black));
                                                radioBxEntrega.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                                                radioBxRetirar.setTextColor(Order.this.getResources().getColor(R.color.black));
                                                radioBxRetirar.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));

                                                cashR.setVisibility(GONE);
                                                creditCardR.isChecked();
                                                formadepag = "Pago no Aplicativo";
                                                creditCardR.setTextColor(Order.this.getResources().getColor(R.color.colorPrimaryDark));
                                                creditCardR.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.colorPrimaryDark)));
                                                cashR.setTextColor(Order.this.getResources().getColor(R.color.black));
                                                cashR.setButtonTintList(ColorStateList.valueOf(Order.this.getResources().getColor(R.color.black)));
                                                googlepay.setVisibility(VISIBLE);
                                                btnFinishOrder.setVisibility(GONE);
                                                trocoTv.setVisibility(GONE);
                                                naEntrega.setVisibility(GONE);
                                            }

                                            if(Objects.equals(premiumisok, "premiumfalse")){
                                                btnFinishOrder.setEnabled(false);
                                                googlepay.setEnabled(false);
                                                addmoreitens.setText(getString(R.string.lojaindisponivel));
                                            }


                                            if(Objects.equals(admcity, cit)) {
                                                Double xs = Double.parseDouble(deliveryFee.replace(",", ".").replace(" ", ""));
                                                String sx = String.valueOf(xs);
                                                feeTv.setText(finalcoin + sx);
                                            }

                                            passarCartInfo();
                                            lojaname.setText(fName);
                                            if (!Objects.equals(productIcon, "")) {
                                                try {
                                                    Picasso.get().load(productIcon).placeholder(R.drawable.loading).into(logobrand);
                                                } catch (Exception e) {
                                                    logobrand.setImageResource(R.drawable.error);
                                                }
                                            }
                                        }
                                    }
                                }
                            });

                        }
                    }
                }
            });
       }


        }




    public void passarCartInfo() {
        cartItemList = new ArrayList<>();
        totalIncome = 0.00;
        sume = finalcoin;
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection("Cart "+userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        cartItemList.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelCartItem modelCartItem = document.toObject(ModelCartItem.class);
                                String value = Objects.requireNonNull(document.getString("totalvalue")).replace(",",".").replace(finalcoin,"");

                                totalIncome = Double.parseDouble(value) + totalIncome;
                                cartItemList.add(modelCartItem);
                            }
                            String xxx = String.valueOf(totalIncome).replace(",",".").replace(finalcoin,"").replace("null","");
                            sume = xxx;
                            if(Objects.equals(admcity, cit)){
                            Double temp = Double.parseDouble(feeTv.getText().toString().replace(",",".").replace(finalcoin,"").replace("null",""));
                            Double delfixo = Double.parseDouble(deliveryFixo);
                            Double plusDel = totalIncome + temp;
                            xxx = String.valueOf(totalIncome).replace(",",".").replace(finalcoin,"").replace("null","");
                            sume = xxx;
                            feetotal = plusDel; }
                            adapterCartItem = new AdapterCartItem(Order.this, cartItemList);
                            cartItemsRv.setAdapter(adapterCartItem);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(Order.this, LinearLayoutManager.VERTICAL, false);
                            layoutManager.setStackFromEnd(false);
                            cartItemsRv.setLayoutManager(layoutManager);
                        }
                        String temporara="";
                        if(Objects.equals(admcity, cit)){

                             temporara = String.valueOf(feetotal);}
                        else{
                             temporara = String.valueOf(nationalFee);}

                        totalTv.setText(finalcoin+sume.replace("null", "").replace(".", ","));
                        Double finalpricetotal0 = Double.parseDouble(totalTv.getText().toString().replace("null", "").replace(",", ".").replace("R", "").replace("$", ""));
                        Double finalpricetotal1 = Double.parseDouble(feeTv.getText().toString().replace("null", "").replace(",", ".").replace("R", "").replace("$", ""));
                        Double finalpricetotal2 = finalpricetotal0 + finalpricetotal1;
                        String finalpricetotal3 = "R$"+String.valueOf(finalpricetotal2).replace("null", "").replace(".", ",");

                        TotalTv.setText(finalpricetotal3);
                        progressDialog.dismiss();
                        if(radioBxImported.isChecked()){
                            DocumentReference docRef = fStore.collection("Users").document(fAuth.getCurrentUser().getUid());
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            String countryuser = document.getString("country");

                                            if (Objects.equals(countryuser, "Brasil")) {
                                                usercoin = "R$";
                                            }
                                            if (Objects.equals(countryuser, "Estados Unidos da América")) {
                                                usercoin = "US$";
                                            }
                                            if (Objects.equals(countryuser, "Inglaterra")) {
                                                usercoin = "£";
                                            }
                                            if (Objects.equals(countryuser, "Alemanha")) {
                                                usercoin = "€";
                                            }
                                            if (Objects.equals(countryuser, "França")) {
                                                usercoin = "€";
                                            }
                                            if (Objects.equals(countryuser, "Portugal")) {
                                                usercoin = "€";
                                            }
                                            if (Objects.equals(countryuser, "Espanha")) {
                                                usercoin = "€";
                                            }

                                            if (Objects.equals(usercoin, "R$")){

                                                DocumentReference docRef = fStore.collection("Cambio").document(productCountry);
                                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot document = task.getResult();
                                                        if (document.exists()) {
                                                            cambio = document.getString("valor");
                                                            Double ocambio = Double.parseDouble(cambio.replace(",",".").replace(finalcoin,"").replace("null",""));
                                                            Double cambiado = feetotal*ocambio;

                                                            String change = String.valueOf(cambiado);
                                                            TotalTv.setText(usercoin+change.replace("null", "").replace(".", ","));
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                            if (Objects.equals(usercoin, "US$")){

                                                DocumentReference docRef = fStore.collection("Cambio2").document(productCountry);
                                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot document = task.getResult();
                                                            if (document.exists()) {
                                                                cambio = document.getString("valor");
                                                                Double ocambio = Double.parseDouble(cambio.replace(",",".").replace(finalcoin,"").replace("null",""));
                                                                Double cambiado = feetotal*ocambio;

                                                                String change = String.valueOf(cambiado);
                                                                TotalTv.setText(usercoin+change.replace("null", "").replace(".", ","));
                                                            }
                                                        }
                                                    }
                                                });
                                            }
                                            if (Objects.equals(usercoin, "£")){

                                                DocumentReference docRef = fStore.collection("Cambio3").document(productCountry);
                                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot document = task.getResult();
                                                            if (document.exists()) {
                                                                cambio = document.getString("valor");
                                                                Double ocambio = Double.parseDouble(cambio.replace(",",".").replace(finalcoin,"").replace("null",""));
                                                                Double cambiado = feetotal*ocambio;

                                                                String change = String.valueOf(cambiado);
                                                                TotalTv.setText(usercoin+change.replace("null", "").replace(".", ","));
                                                            }
                                                        }
                                                    }
                                                });
                                            }
                                            if (Objects.equals(usercoin, "€")){

                                                DocumentReference docRef = fStore.collection("Cambio4").document(productCountry);
                                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot document = task.getResult();
                                                            if (document.exists()) {
                                                                cambio = document.getString("valor");
                                                                Double ocambio = Double.parseDouble(cambio.replace(",",".").replace(finalcoin,"").replace("null",""));
                                                                Double cambiado = feetotal*ocambio;

                                                                String change = String.valueOf(cambiado);
                                                                TotalTv.setText(usercoin+change.replace("null", "").replace(".", ","));
                                                            }
                                                        }
                                                    }
                                                });
                                            }



                                        }
                                    }
                                }
                            });

                        }
                        Log.d(TAG, "finalcoin "+finalcoin);

                    }
                });

    }

    private void datePickDialog() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH) +1;
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                DecimalFormat mFormat = new DecimalFormat("00");
                String pDay = mFormat.format(dayOfMonth);
                String pMonth = mFormat.format(monthOfYear);
                String pYear = ""+year;
                String pDate = pDay + "/"+pMonth+"/"+pYear;
                datePickTv.setText(pDate);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
    }

    public void buy(View view){
        if(radioBxCorreio.isChecked()){
            String tempfee = "R$"+nationalFee;
            feeTv.setText(tempfee);
        }

        if (radioBxRetirar.isChecked()){
            tipoDeCompra = "Compra Retirada No Local";
        }
        if (radioBxEntrega.isChecked()){
            tipoDeCompra = "Compra Local Delivery";
        }

        final int min = 1000;
        final int max = 9999;
        final int random = new Random().nextInt((max - min) + 1) + min;

        codentrega = String.valueOf(random);

        if (creditCardR.isChecked()) {
            DocumentReference docRef = fStore.collection("Users").document(fAuth.getCurrentUser().getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String novato = document.getString("novato");

                            if (Objects.equals(novato, "true")) {
                                String tot = totalTv.getText().toString().replace("R", "")
                                        .replace("$", "").replace(",", ".")
                                        .replace(" ", "");
                                Double nov = Double.parseDouble(tot);
                                if (nov >= 50) {
                                    cpm = "Desconto de Boas vindas por conta do Vegan";
                                    Toast.makeText(Order.this,"Desconto de 5% de Boas Vindas!", Toast.LENGTH_SHORT).show();
                                    Double nova = nov - nov*0.05;
                                    totalTv.setText(nova.toString());

                                    if (cartItemList.size() == 0) {
                                        Toast.makeText(Order.this, R.string.seucarr, Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    if(!Objects.equals(api26orhigher, "true")) {


                                        AlertDialog.Builder builder = new AlertDialog.Builder(Order.this, R.style.MyAlertDialogTheme);

                                        builder.setTitle(getString(R.string.vctemctz));
                                        String tempora = getString(R.string.ohorariode) + " " + daytable + " " + timetable;
                                        builder.setMessage(tempora);

                                        builder.setPositiveButton(getString(R.string.continuar), new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int which) {
                                                String x = TotalTv.getText().toString().replace(".", "").replace(",", "").replace(finalcoin, "").replace(usercoin, "").replace("null", "").replace("US$", "").replace("£", "");
                                                Double y = Double.parseDouble(x);
                                                amount = String.valueOf(y).replace(".", "").replace(",", "").replace(finalcoin, "").replace(usercoin, "").replace("£", "").replace("US$", "").replace("null", "");
                                                getDetails();
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

                                    } else {
                                        String x = TotalTv.getText().toString().replace(".", "").replace(",", "").replace(finalcoin, "").replace(usercoin, "").replace("null", "").replace("US$", "").replace("£", "");
                                        Double y = Double.parseDouble(x);
                                        amount = String.valueOf(y).replace(".", "").replace(",", "").replace(finalcoin, "").replace(usercoin, "").replace("£", "").replace("US$", "").replace("null", "");
                                        getDetails();
                                    }


                                    DocumentReference documentReference = fStore.collection("Users").document(fAuth.getCurrentUser().getUid());
                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("novato", "false");
                                    documentReference.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void avoid) {
                                                    Toast.makeText(Order.this, "Salvo!", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                }
                                            });

                                } else {

                                    if (cartItemList.size() == 0) {
                                        Toast.makeText(Order.this, R.string.seucarr, Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    if(!Objects.equals(api26orhigher, "true")) {

                                        AlertDialog.Builder builder = new AlertDialog.Builder(Order.this, R.style.MyAlertDialogTheme);

                                        builder.setTitle(getString(R.string.vctemctz));
                                        String tempora = getString(R.string.ohorariode) + " " + daytable + " " + timetable;
                                        builder.setMessage(tempora);

                                        builder.setPositiveButton(getString(R.string.continuar), new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int which) {
                                                String x = TotalTv.getText().toString().replace(".", "").replace(",", "").replace(finalcoin, "").replace(usercoin, "").replace("null", "").replace("US$", "").replace("£", "");
                                                Double y = Double.parseDouble(x);
                                                amount = String.valueOf(y).replace(".", "").replace(",", "").replace(finalcoin, "").replace(usercoin, "").replace("£", "").replace("US$", "").replace("null", "");
                                                getDetails();
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
                                    } else {
                                        String x = TotalTv.getText().toString().replace(".", "").replace(",", "").replace(finalcoin, "").replace(usercoin, "").replace("null", "").replace("US$", "").replace("£", "");
                                        Double y = Double.parseDouble(x);
                                        amount = String.valueOf(y).replace(".", "").replace(",", "").replace(finalcoin, "").replace(usercoin, "").replace("£", "").replace("US$", "").replace("null", "");
                                        getDetails();
                                    }

                                    DocumentReference documentReference = fStore.collection("Users").document(fAuth.getCurrentUser().getUid());
                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("novato", "false");
                                    documentReference.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void avoid) {
                                                    Toast.makeText(Order.this, "Salvo!", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                }
                                            });

                                }
                            }

                        }
                    }
                }
            });
        }

        if (cashR.isChecked()) {
            if(!Objects.equals(api26orhigher, "true")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Order.this, R.style.MyAlertDialogTheme);

                builder.setTitle(getString(R.string.vctemctz));
                String tempora = getString(R.string.ohorariode) + " " + daytable + " " + timetable;
                builder.setMessage(tempora);

                builder.setPositiveButton(getString(R.string.continuar), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        DocumentReference documentReference = fStore.collection("Users").document(fAuth.getCurrentUser().getUid());
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("novato", "false");
                        documentReference.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void avoid) {
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                    }
                                });
                        submitOrder();
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
            } else {
                DocumentReference documentReference = fStore.collection("Users").document(fAuth.getCurrentUser().getUid());
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("novato", "false");
                documentReference.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void avoid) {
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                submitOrder();
            }
        }
    }


    private void sendPush() {
        DocumentReference docRef = fStore.collection("Adm's").document(shopUid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("documentexists "+ shoptoken);
                        shoptoken = document.getString("token");
                         sad = getApplicationContext().getString(R.string.novopedido);
                         afd = getApplicationContext().getString(R.string.aguardaana);


                        FCMSend.pushNotification(
                                Order.this,
                                shoptoken,
                                sad,
                                afd
                        );
                    }
                }
            }
        });

        //foreground
        String timestampush = ""+System.currentTimeMillis();

        long l = Long.parseLong(timestampush);

        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(l);
        String date = DateFormat.format("dd/MM/yy", cal).toString();
        String hour = DateFormat.format("HH:mm", cal).toString();

        sad = getApplicationContext().getString(R.string.suaultimano);
        afd = getApplicationContext().getString(R.string.oseuultimop)+" "+hour + " " + date + " " + getString(R.string.nahoralocal);

        final HashMap<String, Object> hashMap1 = new HashMap<>();
        hashMap1.put("title", ""+sad);
        hashMap1.put("text", ""+afd);

        final DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("ZYPUSH "+shopUid);
        ref1.setValue(hashMap1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });


    }

    private void submitOrder() {
        progressDialog.setMessage(getString(R.string.realizpedio));
        progressDialog.show();
        final String timestamp = ""+System.currentTimeMillis();
       // String upToNCharacters = String.format("%.4s", timestamp);   gerar 4 code codigo entreg
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        if(!Objects.equals(scheduleProduction, "")) {
            schedule = datePickTv.getText().toString();
        }

        String cost = TotalTv.getText().toString().trim().replace(".",",");
        final HashMap<String, String> hashMap1 = new HashMap<>();
        hashMap1.put("orderId", ""+timestamp);
        hashMap1.put("tipoDeCompra", ""+tipoDeCompra);
        hashMap1.put("orderTime", ""+timestamp);
        hashMap1.put("orderStatus", "Em Progresso");
        hashMap1.put("orderCost", ""+cost);
        hashMap1.put("orderBy", ""+firebaseAuth.getUid());
        hashMap1.put("orderTo",""+shopUid);
        hashMap1.put("cpm",""+cpm);
        hashMap1.put("recebeu","");
        hashMap1.put("schedule",schedule);
        hashMap1.put("codentrega",""+codentrega);
        Log.d(TAG, "colore1"+shopUid);
        Log.d(TAG, "colore2"+timestamp);

        long l = Long.parseLong(timestamp);

        Calendar cal3 = Calendar.getInstance(Locale.ENGLISH);
        cal3.setTimeInMillis(l);
        String date = DateFormat.format("dd/MM/yy", cal3).toString();
        String hour = DateFormat.format("HH:mm", cal3).toString();


        final HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("orderId", ""+timestamp);
        hashMap.put("date", ""+date);
        hashMap.put("hour", ""+hour);
        hashMap.put("tipoDeCompra", ""+tipoDeCompra);
        hashMap.put("cpm", ""+cpm);
        hashMap.put("orderTime", ""+timestamp);
        hashMap.put("orderStatus", "Em Análise");
        hashMap.put("orderBy", ""+firebaseAuth.getUid());
        hashMap.put("orderTo",""+shopUid);
        hashMap.put("recebeu","");
        hashMap.put("schedule",schedule);
        hashMap.put("obs",""+editObservation.getText().toString());
        hashMap.put("cliente", ""+nomeuser);
        hashMap.put("total", ""+TotalTv.getText().toString());
        hashMap.put("formadepagamento", formadepag);
        hashMap.put("codentrega",""+codentrega);
        hashMap.put("troco", ""+troco);
        Log.d(TAG, "apos criar hashmap");

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("OrderTo").child(shopUid).child(timestamp);
        ref.setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        for(int i=0; i<cartItemList.size(); i++) {
                            Log.d(TAG, "dentro do for");
                            String pId = cartItemList.get(i).getpId();
                            String id = cartItemList.get(i).getId();
                            String cost = cartItemList.get(i).getCost();
                            String name = cartItemList.get(i).getName();
                            String price = cartItemList.get(i).getPrice();
                           String size  = cartItemList.get(i).getSize();
                           String adc1  = cartItemList.get(i).getAdc1();
                           String adc2  = cartItemList.get(i).getAdc2();
                           String adc3  = cartItemList.get(i).getAdc3();
                           String adc4  = cartItemList.get(i).getAdc4();
                           String adc5  = cartItemList.get(i).getAdc5();
                           String adc6  = cartItemList.get(i).getAdc6();
                           String adc7  = cartItemList.get(i).getAdc7();
                           String adc8  = cartItemList.get(i).getAdc8();
                           String adc9  = cartItemList.get(i).getAdc9();
                           String adc10 = cartItemList.get(i).getAdc10();
                           String adc11 = cartItemList.get(i).getAdc11();
                           String adc12 = cartItemList.get(i).getAdc12();
                           String adc13 = cartItemList.get(i).getAdc13();
                           String adc14 = cartItemList.get(i).getAdc14();
                            String adc15 = cartItemList.get(i).getAdc15();
                            String va1  = cartItemList.get(i).getVa1();
                             String va2  = cartItemList.get(i).getVa2();
                             String va3  = cartItemList.get(i).getVa3();
                             String va4  = cartItemList.get(i).getVa4();
                             String va5  = cartItemList.get(i).getVa5();
                             String va6  = cartItemList.get(i).getVa6();
                             String va7  = cartItemList.get(i).getVa7();
                             String va8  = cartItemList.get(i).getVa8();
                             String va9  = cartItemList.get(i).getVa9();
                             String va10 = cartItemList.get(i).getVa10();
                             String va11 = cartItemList.get(i).getVa11();
                             String va12 = cartItemList.get(i).getVa12();
                             String va13 = cartItemList.get(i).getVa13();
                             String va14 = cartItemList.get(i).getVa14();
                            String va15 = cartItemList.get(i).getVa15();
                            quantity = cartItemList.get(i).getQuantity();

                            HashMap<String, String> hashMap1 = new HashMap<>();
                            hashMap1.put("pId", pId);
                            hashMap1.put("name", name);
                            hashMap1.put("cost", cost);
                            hashMap1.put("price", price);
                            hashMap1.put("troco", ""+troco);
                            hashMap1.put("quantity", ""+quantity);
                            hashMap1.put("size", size);
                            hashMap1.put("va1", va1);
                            hashMap1.put("va2", va2);
                            hashMap1.put("va3", va3);
                            hashMap1.put("va4", va4);
                            hashMap1.put("va5", va5);
                            hashMap1.put("va6", va6);
                            hashMap1.put("va7", va7);
                            hashMap1.put("va8", va8);
                            hashMap1.put("va9", va9);
                            hashMap1.put("va10", va10);
                            hashMap1.put("va11", va11);
                            hashMap1.put("va12", va12);
                            hashMap1.put("va13", va13);
                            hashMap1.put("va14", va14);
                            hashMap1.put("va15", va15);
                            hashMap1.put("adc1", adc1);
                            hashMap1.put("adc2", adc2);
                            hashMap1.put("adc3", adc3);
                            hashMap1.put("adc4", adc4);
                            hashMap1.put("adc5", adc5);
                            hashMap1.put("adc6", adc6);
                            hashMap1.put("adc7", adc7);
                            hashMap1.put("adc8", adc8);
                            hashMap1.put("adc9", adc9);
                            hashMap1.put("adc10", adc10);
                            hashMap1.put("adc11", adc11);
                            hashMap1.put("adc12", adc12);
                            hashMap1.put("adc13", adc13);
                            hashMap1.put("adc14", adc14);
                            hashMap1.put("adc15", adc15);
                            hashMap1.put("schedule",schedule);
                            ref.child("Items").child(id).setValue(hashMap1);

                            DocumentReference docRef0 = fStore.collection("Points "+shopUid).document("Fidelidade");
                            docRef0.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            String premio = document.getString("premio");

                                            if (Objects.equals(premio, name)) {
                                                temfiel = true;

                                                DocumentReference docRef = fStore.collection("Fidelidade "+userID).document(shopUid);
                                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot document = task.getResult();
                                                            if (document.exists()) {
                                                                 pontos = document.getString("pontos");
                                                                if (pontos == null || pontos.equals("")) {
                                                                    DocumentReference docRef7 = fStore.collection("Fidelidade " + userID).document(shopUid);
                                                                    Map<String, Object> edited = new HashMap<>();
                                                                    edited.put("pontos", "0");
                                                                    docRef7.set(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {
                                                                        }
                                                                    });
                                                                    pontos = "0";
                                                                } else {
                                                                    int calpontos = Integer.parseInt(pontos);
                                                                    int calquan = Integer.parseInt(quantity);
                                                                    if (calpontos + calquan >= 10) {
                                                                        ganhoufiel = true;
                                                                        HashMap<String, String> hashMap1 = new HashMap<>();
                                                                        quantity = Integer.toString(calquan + 1);
                                                                        hashMap1.put("quantity", quantity);
                                                                        ref.child("Items").child(id).setValue(hashMap1);
                                                                        Toast.makeText(Order.this, R.string.parabensfid, Toast.LENGTH_LONG).show();
                                                                        DocumentReference docRef8 = fStore.collection("Fidelidade " + userID).document(shopUid);
                                                                        Map<String, Object> edited8 = new HashMap<>();
                                                                        edited8.put("pontos", "0");
                                                                        docRef8.set(edited8).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid) {
                                                                            }
                                                                        });
                                                                    } else {
                                                                        DocumentReference docRef2 = fStore.collection("Fidelidade " + userID).document(shopUid);
                                                                        Map<String, Object> edited = new HashMap<>();
                                                                        int mypointsnow = calpontos + calquan;
                                                                        String pontosstr = Integer.toString(mypointsnow);
                                                                        edited.put("pontos", pontosstr);
                                                                        docRef2.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid) {
                                                                            }
                                                                        });
                                                            }}} else {
                                                                DocumentReference docRef7 = fStore.collection("Fidelidade " + userID).document(shopUid);
                                                                Map<String, Object> edited = new HashMap<>();
                                                                edited.put("pontos", "0");
                                                                docRef7.set(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                    }
                                                                });
                                                                pontos = "0";
                                                                int calpontos = Integer.parseInt(pontos);
                                                                int calquan = Integer.parseInt(quantity);
                                                                if (calpontos + calquan >= 10) {
                                                                    ganhoufiel = true;
                                                                    HashMap<String, String> hashMap1 = new HashMap<>();
                                                                    quantity = Integer.toString(calquan + 1);
                                                                    hashMap1.put("quantity", Integer.toString(calquan + 1));
                                                                    ref.child("Items").child(id).setValue(hashMap1);
                                                                    Toast.makeText(Order.this, R.string.parabensfid, Toast.LENGTH_LONG).show();
                                                                    DocumentReference docRef8 = fStore.collection("Fidelidade " + userID).document(shopUid);
                                                                    Map<String, Object> edited8 = new HashMap<>();
                                                                    edited8.put("pontos", "0");
                                                                    docRef8.set(edited8).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {
                                                                        }
                                                                    });
                                                                } else {
                                                                    DocumentReference docRef2 = fStore.collection("Fidelidade " + userID).document(shopUid);
                                                                    Map<String, Object> edited2 = new HashMap<>();
                                                                    int mypointsnow = calpontos + calquan;
                                                                    String pontosstr = Integer.toString(mypointsnow);
                                                                    edited2.put("pontos", pontosstr);
                                                                    docRef2.update(edited2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {
                                                                        }
                                                                    });


                                                                }
                                                            }
                                                        }
                                                    }

                                                });



                                            }



                                        }
                                    }
                                }
                            });

                        }

                        Log.d(TAG, "apos o for");

                        final HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("orderId", ""+timestamp);
                        hashMap.put("tipoDeCompra", ""+tipoDeCompra);
                        hashMap.put("orderTime", ""+timestamp);
                        hashMap.put("orderStatus", "Em Análise");
                        hashMap.put("orderBy", ""+firebaseAuth.getUid());
                        hashMap.put("orderTo",""+shopUid);
                        hashMap.put("cliente", ""+nomeuser);
                        hashMap.put("total", ""+TotalTv.getText().toString());
                        hashMap.put("troco", ""+troco);
                        hashMap.put("recebeu","");
                        hashMap.put("schedule",schedule);
                        hashMap.put("codentrega",""+codentrega);
                        if(ganhoufiel==true){
                        hashMap.put("fidelidade", "true");}
                        hashMap.put("cpm", cpm);
                        hashMap.put("formadepagamento", formadepag);
                        hashMap.put("obs",""+editObservation.getText().toString());


                        Log.d(TAG, "apos criar hashmap2");

                        final DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("OrderBy").child(userID).child(timestamp);
                        ref2.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        for(int i=0; i<cartItemList.size(); i++) {
                                            Log.d(TAG, "dentro 2 for");
                                            String pId = cartItemList.get(i).getpId();
                                            String id = cartItemList.get(i).getId();
                                            String cost = cartItemList.get(i).getCost();
                                            String name = cartItemList.get(i).getName();
                                            String price = cartItemList.get(i).getPrice();
                                            String size  = cartItemList.get(i).getSize();
                                            String adc1  = cartItemList.get(i).getAdc1();
                                            String adc2  = cartItemList.get(i).getAdc2();
                                            String adc3  = cartItemList.get(i).getAdc3();
                                            String adc4  = cartItemList.get(i).getAdc4();
                                            String adc5  = cartItemList.get(i).getAdc5();
                                            String adc6  = cartItemList.get(i).getAdc6();
                                            String adc7  = cartItemList.get(i).getAdc7();
                                            String adc8  = cartItemList.get(i).getAdc8();
                                            String adc9  = cartItemList.get(i).getAdc9();
                                            String adc10 = cartItemList.get(i).getAdc10();
                                            String adc11 = cartItemList.get(i).getAdc11();
                                            String adc12 = cartItemList.get(i).getAdc12();
                                            String adc13 = cartItemList.get(i).getAdc13();
                                            String adc14 = cartItemList.get(i).getAdc14();
                                            String adc15 = cartItemList.get(i).getAdc15();
                                            String va1  = cartItemList.get(i).getVa1();
                                            String va2  = cartItemList.get(i).getVa2();
                                            String va3  = cartItemList.get(i).getVa3();
                                            String va4  = cartItemList.get(i).getVa4();
                                            String va5  = cartItemList.get(i).getVa5();
                                            String va6  = cartItemList.get(i).getVa6();
                                            String va7  = cartItemList.get(i).getVa7();
                                            String va8  = cartItemList.get(i).getVa8();
                                            String va9  = cartItemList.get(i).getVa9();
                                            String va10 = cartItemList.get(i).getVa10();
                                            String va11 = cartItemList.get(i).getVa11();
                                            String va12 = cartItemList.get(i).getVa12();
                                            String va13 = cartItemList.get(i).getVa13();
                                            String va14 = cartItemList.get(i).getVa14();
                                            String va15 = cartItemList.get(i).getVa15();
                                            quantity = cartItemList.get(i).getQuantity();

                                            HashMap<String, String> hashMap1 = new HashMap<>();
                                            hashMap1.put("pId", pId);
                                            hashMap1.put("name", name);
                                            hashMap1.put("cost", cost);
                                            hashMap1.put("price", price);
                                            hashMap1.put("troco", ""+troco);
                                            hashMap1.put("schedule",schedule);
                                            hashMap1.put("size", size);
                                            hashMap1.put("va1", va1);
                                            hashMap1.put("va2", va2);
                                            hashMap1.put("va3", va3);
                                            hashMap1.put("va4", va4);
                                            hashMap1.put("va5", va5);
                                            hashMap1.put("va6", va6);
                                            hashMap1.put("va7", va7);
                                            hashMap1.put("va8", va8);
                                            hashMap1.put("va9", va9);
                                            hashMap1.put("va10", va10);
                                            hashMap1.put("va11", va11);
                                            hashMap1.put("va12", va12);
                                            hashMap1.put("va13", va13);
                                            hashMap1.put("va14", va14);
                                            hashMap1.put("va15", va15);
                                            hashMap1.put("adc1", adc1);
                                            hashMap1.put("adc2", adc2);
                                            hashMap1.put("adc3", adc3);
                                            hashMap1.put("adc4", adc4);
                                            hashMap1.put("adc5", adc5);
                                            hashMap1.put("adc6", adc6);
                                            hashMap1.put("adc7", adc7);
                                            hashMap1.put("adc8", adc8);
                                            hashMap1.put("adc9", adc9);
                                            hashMap1.put("adc10", adc10);
                                            hashMap1.put("adc11", adc11);
                                            hashMap1.put("adc12", adc12);
                                            hashMap1.put("adc13", adc13);
                                            hashMap1.put("adc14", adc14);
                                            hashMap1.put("adc15", adc15);
                                            hashMap1.put("pId", pId);
                                            hashMap1.put("name", name);
                                            hashMap1.put("cost", cost);
                                            hashMap1.put("price", price);
                                            hashMap1.put("troco", ""+troco);
                                            hashMap1.put("quantity", quantity);
                                            ref2.child("Items").child(id).setValue(hashMap1);

                                        }
                                        progressDialog.dismiss();
                                        Log.d(TAG, "depois 2 for");

                                        Intent intent = new Intent(Order.this, Espere.class);
                                        intent.putExtra("vemdocarrinho", "sim");
                                        intent.putExtra("userID", userID);
                                        Log.d(TAG, "userID "+userID);
                                        intent.putExtra("title", name);
                                        intent.putExtra("status", getString(R.string.emanalise));
                                        intent.putExtra("shopUid", shopUid);
                                        intent.putExtra("photo", foto);
                                        intent.putExtra("schedule",schedule);
                                        intent.putExtra("tipoDeCompra", tipoDeCompra);
                                        if(ganhoufiel){
                                            hashMap.put("fidelidade", "true");}
                                        intent.putExtra("price", TotalTv.getText().toString());
                                        intent.putExtra("troco", troco);
                                        intent.putExtra("brandName", brand);
                                        intent.putExtra("orderId", timestamp);
                                        intent.putExtra("deliveryFee", feeTv.getText().toString());
                                        intent.putExtra("cpm", cpm);
                                        intent.putExtra("codentrega", codentrega);
                                        intent.putExtra("obs", editObservation.getText().toString());
                                        intent.putExtra("pagamento", formadepag);
                                        Log.d(TAG, "pósloop");

                                        progressDialog.dismiss();
                                        Toast.makeText(Order.this, R.string.pedreal, Toast.LENGTH_SHORT).show();
                                        finish();
                                        sendPush();
                                        startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(Order.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                        progressDialog.dismiss();
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Order.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }


    public void onBackPressed ( ) {
        finish ( );
    }

    public void startVibrate ( long time ) {
        Vibrator atvib = ( Vibrator ) getSystemService ( Context.VIBRATOR_SERVICE );
        assert atvib != null;
        atvib.vibrate ( time );
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
