package app.vegan;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import static app.vegan.R.drawable.error;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DietDetailsActivity extends AppCompatActivity implements Serializable {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private TextView titleDietDetails, brandTv, priceDietDetail, discountedNoteTv, categoryTv, quantityDietDetailsTv,
            descriptionDietDetailsTv, mostrarmais, mostrarmenos, ingredientes, adicionais1, valor1, valor2,
            valor3, valor4, valor5, valor6, valor7, valor8, valor9, valor10, valor11, valor12, valor13, valor14,
            valor15, adicionais2, adicionais3, adicionais4, adicionais5, adicionais6, adicionais10, adicionais11,
            adicionais12, adicionais7, adicionais8, adicionais9, adicionais14, adicionais15, adicionais13, qualidade,
            garantia, saibamais, tempoentrega, tempoproducao, numeral1, numeral2, numeral3, numeral4, numeral5, numeral6,
            numeral7, numeral8, numeral9, numeral10, numeral11, numeral12, numeral13, numeral14, numeral15, ingredientesTv,
            tamanhosTv, adicionaisTv, qualidadeTv, garantiaTv, saibamaisTv, tempoentregaTv, tempoproducaoTv, tamanho;
    private ShapeableImageView brandPhoto;
    private ImageSlider circleImageProduct;
    private LinearLayout layoutmais;
    private ImageView selo1, selo2, selo3, selo4, love, imageButtonBack, fire, mailTypeImage;
    private Button orderButton;
    private int quantity = 1;
    private Double finalPrice = 1.00;
    private String type, userID, isLoved, novirgula, seloa, selob, seloc, selod, selos, shopUid, nome, foto, discountNote,
            adicional1, adicional2, nLikes,sizepp, sizep, sizem, sizeg, sizegg, sizeggg, categoryProduct, productId,
            adicional3, adicional4, adicional5, adicional6, adicional7, adicional8, adicional9, adicional10, adicional11,
            adicional12, adicional13, adicional14, adicional15, alor1, alor2, alor3, alor4, alor5, alor6, alor7, alor8,
            alor9, alor10, alor11, alor12, alor13, alor14, alor15, token, productCountry, size, taman, localOption, scheduleProduction, nationalMail;

    private String adc1, adc2, adc3, adc4, adc5, adc6, adc7, adc8, adc9, adc10, adc11, adc12, adc13, adc14, adc15;
    private String va1, va2, va3, va4, va5, va6, va7, va8, va9, va10, va11, va12, va13, va14, va15;
    private Double v1, v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15;
    private CardView floatingButton;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_details);

        v1=0.0;
        v2=0.0;
        v3=0.0;
        v4=0.0;
        v5=0.0;
        v6=0.0;
        v7=0.0;
        v8=0.0;
        v9=0.0;
        v10=0.0;
        v11=0.0;
        v12=0.0;
        v13=0.0;
        v14=0.0;
        v15=0.0;

        Intent data = getIntent();
        nome = data.getStringExtra("title");
        localOption = data.getStringExtra("localOption");
        foto = data.getStringExtra("photo");
        String foto2 = data.getStringExtra("photo2");
        String discountPrice = data.getStringExtra("discountPrice");
        discountNote = data.getStringExtra("discountNote");
        String priceOriginal = data.getStringExtra("priceOriginal");
        String quantityProduct = data.getStringExtra("quantityProduct");
        String descriptionProduct = data.getStringExtra("descriptionProduct");
         categoryProduct = data.getStringExtra("categoryProduct");
         String indisponivel = data.getStringExtra("indisponivel");
        String brandName = data.getStringExtra("brandName");
        String soyseal = data.getStringExtra("soyseal");
        String parabenseal = data.getStringExtra("parabenseal");
        String cottonseal = data.getStringExtra("cottonseal");
        String cannaseal = data.getStringExtra("cannaseal");
        String glutenseal = data.getStringExtra("glutenseal");
        String organicseal = data.getStringExtra("organicseal");
        String biodeseal = data.getStringExtra("biodeseal");
        String nogmoseal = data.getStringExtra("nogmoseal");
        String recyseak = data.getStringExtra("recyseal");
        String rawseal = data.getStringExtra("rawseal");
        String b12seal = data.getStringExtra("b12seal");
        String ecoseal = data.getStringExtra("ecoseal");
        shopUid = data.getStringExtra("shopUid");
        String securePolicy = data.getStringExtra("securePolicy");
        scheduleProduction = data.getStringExtra("scheduleProduction");
        String quality = data.getStringExtra("quality");
        String nationalTime = data.getStringExtra("nationalTime");
        nationalMail = data.getStringExtra("nationalMail");
        String moreInfo = data.getStringExtra("moreInfo");
        String importedTime = data.getStringExtra("importedTime");
        String deliveryTime = data.getStringExtra("deliveryTime");
        String LocalOption = data.getStringExtra("LocalOption");
        productCountry = data.getStringExtra("productCountry");
        String sizePersonalized = data.getStringExtra("sizePersonalized");
        productId = data.getStringExtra("productId");
        adicional1 = data.getStringExtra("adicional1");
        adicional2 = data.getStringExtra("adicional2");
        adicional3 = data.getStringExtra("adicional3");
        adicional4 = data.getStringExtra("adicional4");
        adicional5 = data.getStringExtra("adicional5");
        adicional6 = data.getStringExtra("adicional6");
        adicional7 = data.getStringExtra("adicional7");
        adicional8 = data.getStringExtra("adicional8");
        adicional9 = data.getStringExtra("adicional9");
        adicional10 = data.getStringExtra("adicional10");
        adicional11 = data.getStringExtra("adicional11");
        adicional12 = data.getStringExtra("adicional12");
        adicional13 = data.getStringExtra("adicional13");
        adicional14 = data.getStringExtra("adicional14");
        adicional15 = data.getStringExtra("adicional15");
        sizepp = data.getStringExtra("sizep");
        sizep = data.getStringExtra("sizep");
        sizem = data.getStringExtra("sizem");
        sizeg = data.getStringExtra("sizeg");
        sizegg = data.getStringExtra("sizegg");
        sizeggg = data.getStringExtra("sizeggg");
        alor1 = data.getStringExtra("valor1");
        alor2 = data.getStringExtra("valor2");
        alor3 = data.getStringExtra("valor3");
        alor4 = data.getStringExtra("valor4");
        alor5 = data.getStringExtra("valor5");
        alor6 = data.getStringExtra("valor6");
        alor7 = data.getStringExtra("valor7");
        alor8 = data.getStringExtra("valor8");
        alor9 = data.getStringExtra("valor9");
        alor10 = data.getStringExtra("valor10");
        alor11 = data.getStringExtra("valor11");
        alor12 = data.getStringExtra("valor12");
        alor13 = data.getStringExtra("valor13");
        alor14 = data.getStringExtra("valor14");
        alor15 = data.getStringExtra("valor15");
        String ingredients = data.getStringExtra("ingredients");

        tamanho = findViewById(R.id.compdsico);
        fire = findViewById(R.id.fire);
        ingredientesTv = findViewById(R.id.textw59);
        tamanhosTv = findViewById(R.id.tedxw59);
        adicionaisTv = findViewById(R.id.tew59);
        qualidadeTv = findViewById(R.id.texw59);
        garantiaTv = findViewById(R.id.txw59);
        saibamaisTv = findViewById(R.id.tw59);
        tempoentregaTv = findViewById(R.id.tws59);
        tempoproducaoTv = findViewById(R.id.twsd59);
        numeral1 = findViewById(R.id.add1);
        numeral2 = findViewById(R.id.add2);
        numeral3 = findViewById(R.id.add3);
        numeral4 = findViewById(R.id.add4);
        numeral5 = findViewById(R.id.add5);
        numeral6 = findViewById(R.id.add6);
        numeral7 = findViewById(R.id.add7);
        numeral8 = findViewById(R.id.add8);
        numeral9 = findViewById(R.id.add9);
        numeral10 = findViewById(R.id.add10);
        numeral11 = findViewById(R.id.add11);
        numeral12 = findViewById(R.id.add12);
        numeral13 = findViewById(R.id.add13);
        numeral14 = findViewById(R.id.add14);
        numeral15 = findViewById(R.id.add15);
        ingredientes = findViewById(R.id.composico);
        adicionais1 = findViewById(R.id.adicionais1);
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
        adicionais2 = findViewById(R.id.adicionais2);
        adicionais3 = findViewById(R.id.adicionais3);
        adicionais4 = findViewById(R.id.adicionais4);
        adicionais5 = findViewById(R.id.adicionais5);
        adicionais6 = findViewById(R.id.adicionais6);
        adicionais10 = findViewById(R.id.adicionais10);
        adicionais11 = findViewById(R.id.adicionais11);
        adicionais12 = findViewById(R.id.adicionais12);
        adicionais7 = findViewById(R.id.adicionais7);
        adicionais8 = findViewById(R.id.adicionais8);
        adicionais9 = findViewById(R.id.adicionais9);
        adicionais14 = findViewById(R.id.adicionais14);
        adicionais15 = findViewById(R.id.adicionais15);
        adicionais13 = findViewById(R.id.adicionais13);
        qualidade = findViewById(R.id.compsico);
        garantia = findViewById(R.id.compsio);
        saibamais = findViewById(R.id.compio);
        tempoentrega = findViewById(R.id.cosmpio);
        mailTypeImage = findViewById(R.id.nal);
        tempoproducao = findViewById(R.id.cosmdpio);
        layoutmais = findViewById(R.id.layoutmore);
        mostrarmais = findViewById(R.id.mostrarmais);
        mostrarmenos = findViewById(R.id.mostrarmenos);
        brandPhoto = findViewById(R.id.brandPhoto);
        imageButtonBack = findViewById(R.id.imageView16);
        love = findViewById(R.id.imageView74);
        orderButton = findViewById(R.id.placeOrderButton);
        titleDietDetails = findViewById(R.id.titleDietDetails);
        brandTv = findViewById(R.id.brandTv);
        priceDietDetail = findViewById(R.id.priceDietDetail);
        discountedNoteTv = findViewById(R.id.discountedNoteTv);
        categoryTv = findViewById(R.id.deliveryDietDetailsTv);
        quantityDietDetailsTv = findViewById(R.id.quantityDietDetailsTv);
        descriptionDietDetailsTv = findViewById(R.id.descriptionDietDetailsTv);
        circleImageProduct = findViewById(R.id.circleImageProduct);
        floatingButton = findViewById(R.id.floatingButton);
        selo1 = findViewById(R.id.selo1);
        selo2 = findViewById(R.id.selo2);
        selo3 = findViewById(R.id.selo3);
        selo4 = findViewById(R.id.selo4);

        if(Objects.equals(indisponivel, "true")){
            orderButton.setEnabled(false);
            Toast.makeText(DietDetailsActivity.this, "Item indisponível temporariamente", Toast.LENGTH_SHORT).show();
        }

        if(Objects.equals(nationalMail, "true")){
            mailTypeImage.setVisibility(VISIBLE);
        }

        selo1.setVisibility(GONE);
        selo2.setVisibility(GONE);
        selo3.setVisibility(GONE);
        selo4.setVisibility(GONE);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        testUserCurrent();

        boolean statuInternet = isOnline ( this );

        if (!statuInternet) {
            alertOffline ( );
            Toast.makeText(DietDetailsActivity.this, R.string.falhananet, Toast.LENGTH_SHORT).show();
        } else {

            this.fAuth = FirebaseAuth.getInstance();
        }


        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(foto, ScaleTypes.CENTER_CROP));
        if(foto2 != null && !foto2.equals("")) {
            slideModels.add(new SlideModel(foto2, ScaleTypes.CENTER_CROP));
        } else
        {
            slideModels.add(new SlideModel(foto, ScaleTypes.CENTER_CROP));
        }
        circleImageProduct.setImageList(slideModels, ScaleTypes.CENTER_CROP);

        circleImageProduct.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int position) {
                if(position == 0){
                    androidx.appcompat.app.AlertDialog.Builder alertViewPicture = new androidx.appcompat.app.AlertDialog.Builder(DietDetailsActivity.this, R.style.MyAlertDialogTheme);
                    LayoutInflater factory = LayoutInflater.from(DietDetailsActivity.this);
                    final View view = factory.inflate(R.layout.dialog_image, null);
                    final ImageView dialogImageView = (ImageView) view.findViewById(R.id.imageViewTest);

                    try {
                        Picasso.get().load(foto).placeholder(R.drawable.loading).into(dialogImageView);
                    }
                    catch (Exception e) {
                        dialogImageView.setImageDrawable(ContextCompat.getDrawable(DietDetailsActivity.this, R.drawable.error ));
                    }

                    alertViewPicture.setView(view);
                    alertViewPicture.setNegativeButton(getResources().getString(R.string.fechar), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dlg, int sumthin) {
                        }
                    });

                    alertViewPicture.show();
                }
                if(position == 1){
                    androidx.appcompat.app.AlertDialog.Builder alertViewPicture = new androidx.appcompat.app.AlertDialog.Builder(DietDetailsActivity.this, R.style.MyAlertDialogTheme);
                    LayoutInflater factory = LayoutInflater.from(DietDetailsActivity.this);
                    final View view = factory.inflate(R.layout.dialog_image, null);
                    final ImageView dialogImageView = (ImageView) view.findViewById(R.id.imageViewTest);

                    try {
                        Picasso.get().load(foto2).placeholder(R.drawable.loading).into(dialogImageView);
                    }
                    catch (Exception e) {
                        dialogImageView.setImageDrawable(ContextCompat.getDrawable(DietDetailsActivity.this, R.drawable.error ));
                    }

                    alertViewPicture.setView(view);
                    alertViewPicture.setNegativeButton(getResources().getString(R.string.fechar), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dlg, int sumthin) {
                        }
                    });

                    alertViewPicture.show();
                }
            }
        });

        if(Objects.equals(soyseal, "true")){
            if(selo1.getVisibility() == GONE){
            selo1.setVisibility(VISIBLE);
            selo1.setImageResource(R.drawable.soyfree);
            seloa = "soja";
            } else if(selo2.getVisibility() == GONE){
            selo2.setVisibility(VISIBLE);
            selo2.setImageResource(R.drawable.soyfree);
                selob = "soja";
            } else if(selo3.getVisibility() == GONE){
            selo3.setVisibility(VISIBLE);
            selo3.setImageResource(R.drawable.soyfree);
                seloc = "soja";
            } else if(selo4.getVisibility() == GONE){
                 selo4.setVisibility(VISIBLE);
                 selo4.setImageResource(R.drawable.soyfree);
                selod = "soja";
            }
        }

        if(Objects.equals(parabenseal, "true")) {
            if (selo1.getVisibility() == GONE) {
                selo1.setVisibility(VISIBLE);
                selo1.setImageResource(R.drawable.parabenfree);
                seloa = "parabeno";
            }
            else if (selo2.getVisibility() == GONE) {
                selo2.setVisibility(VISIBLE);
                selo2.setImageResource(R.drawable.parabenfree);
                selob = "parabeno";
            }
            else if (selo3.getVisibility() == GONE) {
                selo3.setVisibility(VISIBLE);
                selo3.setImageResource(R.drawable.parabenfree);
                seloc = "parabeno";
            }
            else if (selo4.getVisibility() == GONE) {
                selo4.setVisibility(VISIBLE);
                selo4.setImageResource(R.drawable.parabenfree);
                selod = "parabeno";
            }
        }

        if(Objects.equals(cottonseal, "true")){
            if(selo1.getVisibility() == GONE){
                selo1.setVisibility(VISIBLE);selo1.setImageResource(R.drawable.cotton);
                seloa = "algodao";
            } else
                if(selo2.getVisibility() == GONE){
                    selo2.setVisibility(VISIBLE);selo2.setImageResource(R.drawable.cotton);
                    selob = "algodao";
                } else
                    if(selo3.getVisibility() == GONE){
                        selo3.setVisibility(VISIBLE);selo3.setImageResource(R.drawable.cotton);
                        seloc = "algodao";
                    } else
                        if(selo4.getVisibility() == GONE){
                            selo4.setVisibility(VISIBLE);selo4.setImageResource(R.drawable.cotton);
                            selod = "algodao";
                        }
        }

        if(Objects.equals(cannaseal, "true")) {
            if (selo1.getVisibility() == GONE) {
                selo1.setVisibility(VISIBLE);
                selo1.setImageResource(R.drawable.cannabis);
                seloa = "maconha";
            } else if (selo2.getVisibility() == GONE) {
                selo2.setVisibility(VISIBLE);
                selo2.setImageResource(R.drawable.cannabis);
                selob = "maconha";
            } else if (selo3.getVisibility() == GONE) {
                selo3.setVisibility(VISIBLE);
                selo3.setImageResource(R.drawable.cannabis);
                seloc = "maconha";
            } else if (selo4.getVisibility() == GONE) {
                selo4.setVisibility(VISIBLE);
                selo4.setImageResource(R.drawable.cannabis);
                selod = "maconha";
            }
        }

        if(Objects.equals(glutenseal, "true")) {
            if (selo1.getVisibility() == GONE) {
                selo1.setVisibility(VISIBLE);
                selo1.setImageResource(R.drawable.glutenfree);
                seloa = "gluten";
            } else if (selo2.getVisibility() == GONE) {
                selo2.setVisibility(VISIBLE);
                selo2.setImageResource(R.drawable.glutenfree);
                selob = "gluten";
            } else if (selo3.getVisibility() == GONE) {
                selo3.setVisibility(VISIBLE);
                selo3.setImageResource(R.drawable.glutenfree);
                seloc = "gluten";
            } else if (selo4.getVisibility() == GONE) {
                selo4.setVisibility(VISIBLE);
                selo4.setImageResource(R.drawable.glutenfree);
                selod = "gluten";
            }
        }

        if(Objects.equals(rawseal, "true")) {
            if (selo1.getVisibility() == GONE) {
                selo1.setVisibility(VISIBLE);
                selo1.setImageResource(R.drawable.rawfood);
                seloa = "cru";
            } else if (selo2.getVisibility() == GONE) {
                selo2.setVisibility(VISIBLE);
                selo2.setImageResource(R.drawable.rawfood);
                selob = "cru";
            } else if (selo3.getVisibility() == GONE) {
                selo3.setVisibility(VISIBLE);
                selo3.setImageResource(R.drawable.rawfood);
                seloc = "cru";
            } else if (selo4.getVisibility() == GONE) {
                selo4.setVisibility(VISIBLE);
                selo4.setImageResource(R.drawable.rawfood);
                selod = "cru";
            }
        }

        if(Objects.equals(b12seal, "true")) {
            if (selo1.getVisibility() == GONE) {
                selo1.setVisibility(VISIBLE);
                selo1.setImageResource(R.drawable.b12);
                seloa = "b12";
            } else if (selo2.getVisibility() == GONE) {
                selo2.setVisibility(VISIBLE);
                selo2.setImageResource(R.drawable.b12);
                selob = "b12";
            } else if (selo3.getVisibility() == GONE) {
                selo3.setVisibility(VISIBLE);
                selo3.setImageResource(R.drawable.b12);
                seloc = "b12";
            } else if (selo4.getVisibility() == GONE) {
                selo4.setVisibility(VISIBLE);
                selo4.setImageResource(R.drawable.b12);
                selod = "b12";
            }
        }

        if(Objects.equals(ecoseal, "true")) {
            if (selo1.getVisibility() == GONE) {
                selo1.setVisibility(VISIBLE);
                selo1.setImageResource(R.drawable.eco);
                seloa = "eco";
            } else if (selo2.getVisibility() == GONE) {
                selo2.setVisibility(VISIBLE);
                selo2.setImageResource(R.drawable.eco);
                selob = "eco";
            } else if (selo3.getVisibility() == GONE) {
                selo3.setVisibility(VISIBLE);
                selo3.setImageResource(R.drawable.eco);
                seloc = "eco";
            } else if (selo4.getVisibility() == GONE) {
                selo4.setVisibility(VISIBLE);
                selo4.setImageResource(R.drawable.eco);
                selod = "eco";
            }
        }

        if(Objects.equals(recyseak, "true")) {
            if (selo1.getVisibility() == GONE) {
                selo1.setVisibility(VISIBLE);
                selo1.setImageResource(R.drawable.recycling);
                seloa = "rec";
            } else if (selo2.getVisibility() == GONE) {
                selo2.setVisibility(VISIBLE);
                selo2.setImageResource(R.drawable.recycling);
                selob = "rec";
            } else if (selo3.getVisibility() == GONE) {
                selo3.setVisibility(VISIBLE);
                selo3.setImageResource(R.drawable.recycling);
                seloc = "rec";
            } else if (selo4.getVisibility() == GONE) {
                selo4.setVisibility(VISIBLE);
                selo4.setImageResource(R.drawable.recycling);
                selod = "rec";
            }
        }

        if(Objects.equals(organicseal, "true")) {
            if (selo1.getVisibility() == GONE) {
                selo1.setVisibility(VISIBLE);
                selo1.setImageResource(R.drawable.organic);
                seloa = "org";
            } else if (selo2.getVisibility() == GONE) {
                selo2.setVisibility(VISIBLE);
                selo2.setImageResource(R.drawable.organic);
                selob = "org";
            } else if (selo3.getVisibility() == GONE) {
                selo3.setVisibility(VISIBLE);
                selo3.setImageResource(R.drawable.organic);
                seloc = "org";
            } else if (selo4.getVisibility() == GONE) {
                selo4.setVisibility(VISIBLE);
                selo4.setImageResource(R.drawable.organic);
                selod = "org";

            }
        }

        if(Objects.equals(biodeseal, "true")) {
            if (selo1.getVisibility() == GONE) {
                selo1.setVisibility(VISIBLE);
                selo1.setImageResource(R.drawable.biodegradable);
                seloa = "bio";
            } else if (selo2.getVisibility() == GONE) {
                selo2.setVisibility(VISIBLE);
                selo2.setImageResource(R.drawable.biodegradable);
                selob = "bio";
            } else if (selo3.getVisibility() == GONE) {
                selo3.setVisibility(VISIBLE);
                selo3.setImageResource(R.drawable.biodegradable);
                seloc = "bio";
            } else if (selo4.getVisibility() == GONE) {
                selo3.setVisibility(GONE);
                selo4.setImageResource(R.drawable.biodegradable);
                selod = "bio";
            }
        }

        if(Objects.equals(nogmoseal, "true")) {
            if (selo1.getVisibility() == GONE) {
                selo1.setVisibility(VISIBLE);
                selo1.setImageResource(R.drawable.transgenic);
                seloa = "gmo";
            } else if (selo2.getVisibility() == GONE) {
                selo2.setVisibility(VISIBLE);
                selo2.setImageResource(R.drawable.transgenic);
                selob = "gmo";
            } else if (selo3.getVisibility() == GONE) {
                selo3.setVisibility(VISIBLE);
                selo3.setImageResource(R.drawable.transgenic);
                seloc = "gmo";
            } else if (selo4.getVisibility() == GONE) {
                selo4.setVisibility(VISIBLE);
                selo4.setImageResource(R.drawable.transgenic);
                selod = "gmo";
            }
        }

        selo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                selos=seloa;
                toastselo(selos);
            }
        });

        selo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                selos=selob;
                toastselo(selos);
            }
        });

        selo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                selos=seloc;
                toastselo(selos);
            }
        });

        selo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                selos=selod;
                toastselo(selos);
            }
        });

        mailTypeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DietDetailsActivity.this, R.string.enviparato, Toast.LENGTH_SHORT);
            }
        });

        if(Objects.equals(productCountry, "Brasil")){
            priceDietDetail.setText("R$");
        }
        if(Objects.equals(productCountry, "Estados Unidos da América")){
            priceDietDetail.setText("US$");
        }
        if(Objects.equals(productCountry, "Inglaterra")){
            priceDietDetail.setText("£");
        }
        if(Objects.equals(productCountry, "Alemanha")){
            priceDietDetail.setText("€");
        }
        if(Objects.equals(productCountry, "França")){
            priceDietDetail.setText("€");
        }
        if(Objects.equals(productCountry, getResources().getString(R.string.canada))){
            priceDietDetail.setText("C$");
        }
        if(Objects.equals(productCountry, "Portugal")){
            priceDietDetail.setText("€");
        }
        if(Objects.equals(productCountry, "Espanha")){
            priceDietDetail.setText("€");
        }
        if(Objects.equals(productCountry, getResources().getString(R.string.mexico))){
            priceDietDetail.setText("MEX$");
        }
        if(Objects.equals(productCountry, getResources().getString(R.string.argentina))){
            priceDietDetail.setText("ARS$");
        }
        if(Objects.equals(productCountry, getResources().getString(R.string.escocia))){
            priceDietDetail.setText("£");
        }

        brandTv.setText(brandName);

            if(!Objects.equals(discountNote, "")){
            discountedNoteTv.setVisibility(VISIBLE);
                fire.setVisibility(VISIBLE);
            discountedNoteTv.setText(discountNote+"% OFF");
            priceDietDetail.append(discountPrice.replace(".",","));
        } else {
                priceDietDetail.append(priceOriginal.replace(".",","));
            }
        quantityDietDetailsTv.setText(quantityProduct);
        descriptionDietDetailsTv.setText(descriptionProduct);
        categoryTv.setText(categoryProduct);
        titleDietDetails.setText(nome);
        titleDietDetails.setTextColor(getResources().getColor(R.color.white));

        if(ingredients == null || ingredients.equals("")){
            ingredientes.setVisibility(GONE);
            ingredientesTv.setVisibility(GONE);
        } else {
            ingredientes.setText(ingredients);
        }

        if(securePolicy == null || securePolicy.equals("")){
            garantia.setVisibility(GONE);
            garantiaTv.setVisibility(GONE);
        } else {
            garantia.setText(securePolicy);
        }

        if(scheduleProduction == null || scheduleProduction.equals("")){
            tempoproducao.setVisibility(GONE);
            tempoproducaoTv.setVisibility(GONE);
        } else {
            tempoproducao.setText(scheduleProduction);
        }

        if(quality == null || quality.equals("")){
            qualidade.setVisibility(GONE);
            qualidadeTv.setVisibility(GONE);
        } else {
            qualidade.setText(quality);
        }

        if(moreInfo == null || moreInfo.equals("")){
            saibamais.setVisibility(GONE);
            saibamaisTv.setVisibility(GONE);
        } else {
            saibamais.setText(moreInfo);
        }

        if(importedTime != null && !importedTime.equals("")){
            tempoentrega.setText(importedTime);
        }

        if(nationalTime != null && !nationalTime.equals("")){
            tempoentrega.setText(nationalTime);
        }

        if(deliveryTime != null && !deliveryTime.equals("")){
            tempoentrega.setText(deliveryTime);
        }

        if(LocalOption != null && !LocalOption.equals("")){
            tempoentrega.setText(getString(R.string.disponivelapen));
        }

        if(adicional1 != null && !adicional1.equals("") && !adicional1.equals("false")){
            adicionais1.setText(adicional1);
            if(alor1!=null&& !alor1.equals("") && !alor1.equals("false")){
                valor1.setText(alor1);
            }
        } else {adicionais1.setVisibility(GONE); valor1.setVisibility(GONE); adicionaisTv.setVisibility(GONE); numeral1.setVisibility(GONE);}
        if(adicional2 != null && !adicional2.equals("") && !adicional2.equals("false")){
            adicionais2.setText(adicional2);
            if(alor2!=null&& !alor2.equals("") && !alor2.equals("false")){
                valor2.setText(alor2);
            }
        } else {adicionais2.setVisibility(GONE);  valor2.setVisibility(GONE); numeral2.setVisibility(GONE);}
        if(adicional3 != null && !adicional3.equals("") && !adicional3.equals("false")){
            adicionais3.setText(adicional3);
            if(alor3!=null&& !alor3.equals("") && !alor3.equals("false")){
                valor3.setText(alor3);
            }
        } else {adicionais3.setVisibility(GONE); valor3.setVisibility(GONE); numeral3.setVisibility(GONE);}
        if(adicional4 != null && !adicional4.equals("") && !adicional4.equals("false")){
            adicionais4.setText(adicional4);
            if(alor4!=null&& !alor4.equals("") && !alor4.equals("false")){
                valor4.setText(alor4);
            }
        } else {adicionais4.setVisibility(GONE); valor4.setVisibility(GONE);numeral4.setVisibility(GONE);}
        if(adicional5 != null && !adicional5.equals("") && !adicional5.equals("false")){
            adicionais5.setText(adicional5);
            if(alor5!=null&& !alor5.equals("") && !alor5.equals("false")){
                valor5.setText(alor5);
            }
        } else {adicionais5.setVisibility(GONE);valor5.setVisibility(GONE);numeral5.setVisibility(GONE);}
        if(adicional6 != null && !adicional6.equals("") && !adicional6.equals("false")){
            adicionais6.setText(adicional6);
            if(alor6!=null&& !alor6.equals("") && !alor6.equals("false")){
                valor6.setText(alor6);
            }
        } else {adicionais6.setVisibility(GONE);valor6.setVisibility(GONE);numeral6.setVisibility(GONE);}
        if(adicional7 != null && !adicional7.equals("") && !adicional7.equals("false")){
            adicionais7.setText(adicional7);
            if(alor7!=null&& !alor7.equals("") && !alor7.equals("false")){
                valor7.setText(alor7);
            }
        } else {adicionais7.setVisibility(GONE);valor7.setVisibility(GONE);numeral7.setVisibility(GONE);}
        if(adicional8 != null && !adicional8.equals("") && !adicional8.equals("false")){
            adicionais8.setText(adicional8);
            if(alor8!=null&& !alor8.equals("") && !alor8.equals("false")){
                valor8.setText(alor8);
            }
        } else {adicionais8.setVisibility(GONE);valor8.setVisibility(GONE);numeral8.setVisibility(GONE);}
        if(adicional9 != null && !adicional9.equals("") && !adicional9.equals("false")){
            adicionais9.setText(adicional9);
            if(alor9!=null&& !alor9.equals("") && !alor9.equals("false")){
                valor9.setText(alor9);
            }
        } else {adicionais9.setVisibility(GONE);valor9.setVisibility(GONE);numeral9.setVisibility(GONE);}
        if(adicional10 != null && !adicional10.equals("") && !adicional10.equals("false")){
            adicionais10.setText(adicional10);
            if(alor10!=null&& !alor10.equals("") && !alor10.equals("false")){
                valor10.setText(alor10);
            }
        } else {adicionais10.setVisibility(GONE);valor10.setVisibility(GONE);numeral10.setVisibility(GONE);}
        if(adicional11 != null && !adicional11.equals("") && !adicional11.equals("false")){
            adicionais11.setText(adicional11);
            if(alor11!=null&& !alor11.equals("") && !alor11.equals("false")){
                valor11.setText(alor11);
            }
        } else {adicionais11.setVisibility(GONE);valor11.setVisibility(GONE);numeral11.setVisibility(GONE);}
        if(adicional12 != null && !adicional12.equals("") && !adicional12.equals("false")){
            adicionais12.setText(adicional12);
            if(alor12!=null&& !alor12.equals("") && !alor12.equals("false")){
                valor12.setText(alor12);
            }
        } else {adicionais12.setVisibility(GONE);valor12.setVisibility(GONE);numeral12.setVisibility(GONE);}
        if(adicional13 != null && !adicional13.equals("") && !adicional13.equals("false")){
            adicionais13.setText(adicional13);
            if(alor13!=null&& !alor13.equals("") && !alor13.equals("false")){
                valor13.setText(alor13);
            }
        } else {adicionais13.setVisibility(GONE);valor13.setVisibility(GONE);numeral13.setVisibility(GONE);}
        if(adicional14 != null && !adicional14.equals("") && !adicional14.equals("false")){
            adicionais14.setText(adicional14);
            if(alor14!=null&& !alor14.equals("") && !alor14.equals("false")){
                valor14.setText(alor14);
            }
        } else {adicionais14.setVisibility(GONE);valor14.setVisibility(GONE);numeral14.setVisibility(GONE);}
        if(adicional15 != null && !adicional15.equals("") && !adicional15.equals("false")){
            adicionais15.setText(adicional15);
            if(alor15!=null&& !alor15.equals("") && !alor15.equals("false")){
                valor15.setText(alor15);
            }
        } else {adicionais15.setVisibility(GONE);valor15.setVisibility(GONE);numeral15.setVisibility(GONE);}

        mostrarmais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taman="";
                if (sizep.equals("true")) {
                    taman = taman+ " P ";
                }

                if (sizem.equals("true")) {
                    taman =taman+ " M ";
                }

                if (sizeg.equals("true")) {
                    taman =taman+ " G ";
                }

                if (sizegg.equals("true")) {
                    taman = taman+" GG ";
                }

                if (sizeggg.equals("true")) {
                    taman = taman+" GGG ";
                }

                if (sizePersonalized.equals("true")) {
                    taman = sizePersonalized;
                }

                if (!taman.equals("")) {
                    tamanho.setText(taman);
                } else {
                    tamanho.setVisibility(GONE);
                    tamanhosTv.setVisibility(GONE);
                }

                layoutmais.setVisibility(VISIBLE);
                descriptionDietDetailsTv.setMaxLines(10);
                mostrarmais.setVisibility(View.INVISIBLE);
            }
        });

        mostrarmenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutmais.setVisibility(GONE);
                descriptionDietDetailsTv.setMaxLines(4);
                mostrarmais.setVisibility(VISIBLE);
            }
        });

        imageButtonBack = findViewById(R.id.imageView16);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });

        orderButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if ( fAuth.getCurrentUser ( ) != null ) {
                    DocumentReference docRef = fStore.collection(categoryProduct).document(productId);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                     sizeggg = document.getString("sizeggg");
                                     sizegg = document.getString("sizegg");
                                     sizeg = document.getString("sizeg");
                                     sizem = document.getString("sizem");
                                     sizep = document.getString("sizep");
                                    sizepp = document.getString("sizepp");
                                    String personalite = document.getString("sizePersonalized");

                                     if(!Objects.equals(sizeg, "true") && !Objects.equals(sizegg, "true")
                                             && !Objects.equals(sizeggg, "true") && !Objects.equals(sizem, "true")
                                             && !Objects.equals(sizep, "true") && !Objects.equals(sizepp, "true")&& !Objects.equals(personalite, "true")){
                                         testAditionals();
                                     }
                                     else {
                                         showSizeDialog();
                                     }

                                }
                            }
                        }
                    });


                } else Toast.makeText(DietDetailsActivity.this, R.string.entreoucadastre, Toast.LENGTH_SHORT).show();
            }
        });

        floatingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DietDetailsActivity.this, LoginActivity.class);
                startActivity(intent);
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
            } else Toast.makeText(DietDetailsActivity.this, R.string.entreoucadastre, Toast.LENGTH_SHORT).show();

            }
        });

        DocumentReference docRef = fStore.collection("Adm's").document(shopUid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String shopIcon = document.getString("shopIcon");
                        type = document.getString("type");


                        try {
                            Picasso.get().load(shopIcon).placeholder(R.drawable.loading).into(brandPhoto);
                        }
                        catch (Exception e) {
                            brandPhoto.setImageDrawable(ContextCompat.getDrawable(DietDetailsActivity.this, R.drawable.error ));
                        }
                    }
                }
            }
        });


        brandTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DietDetailsActivity.this, RestaurantDetailsActivity.class);
                intent.putExtra("brandName", brandName);
                intent.putExtra("uid", shopUid);
                intent.putExtra("shopUid", shopUid);
                intent.putExtra("type", type);
                v.getContext().startActivity(intent);
            }
        });

        brandPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DietDetailsActivity.this, RestaurantDetailsActivity.class);
                intent.putExtra("brandName", brandName);
                intent.putExtra("uid", shopUid);
                intent.putExtra("shopUid", shopUid);
                intent.putExtra("type", type);
                v.getContext().startActivity(intent);
            }
        });

    }

    private void testAditionals() {
                if ( fAuth.getCurrentUser ( ) != null ) {
                    DocumentReference docRef = fStore.collection(categoryProduct).document(productId);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    String adicional001 = document.getString("adicional001");

                                    if(Objects.equals(adicional001, "false") || Objects.equals(adicional001, "")
                                            || adicional001 == null){
                                        showQuantityDialog();
                                    }
                                    else {
                                        showAditionalsDialog();
                                    }

                                }
                            }
                        }
                    });


                } else Toast.makeText(DietDetailsActivity.this, R.string.entreoucadastre, Toast.LENGTH_SHORT).show();
            }


    private void toastselo(String selos) {
        switch (selos) {
            case "soja":
                Toast.makeText(DietDetailsActivity.this, R.string.nosoja, Toast.LENGTH_SHORT).show();
                break;
            case "gmo":
                Toast.makeText(DietDetailsActivity.this, R.string.trans, Toast.LENGTH_SHORT).show();
                break;
            case "bio":
                Toast.makeText(DietDetailsActivity.this, R.string.biode, Toast.LENGTH_SHORT).show();
                break;
            case "org":
                Toast.makeText(DietDetailsActivity.this, R.string.organ, Toast.LENGTH_SHORT).show();
                break;
            case "rec":
                Toast.makeText(DietDetailsActivity.this, R.string.recic, Toast.LENGTH_SHORT).show();
                break;
            case "eco":
                Toast.makeText(DietDetailsActivity.this, R.string.ecof, Toast.LENGTH_SHORT).show();
                break;
            case "b12":
                Toast.makeText(DietDetailsActivity.this, R.string.rb12, Toast.LENGTH_SHORT).show();
                break;
            case "cru":
                Toast.makeText(DietDetailsActivity.this, R.string.raw, Toast.LENGTH_SHORT).show();
                break;
            case "maconha":
                Toast.makeText(DietDetailsActivity.this, R.string.cannabis, Toast.LENGTH_SHORT).show();
                break;
            case "algodao":
                Toast.makeText(DietDetailsActivity.this, R.string.algodao, Toast.LENGTH_SHORT).show();
                break;
            case "parabeno":
                Toast.makeText(DietDetailsActivity.this, R.string.parabenfree, Toast.LENGTH_SHORT).show();
                break;
            case "gluten":
                Toast.makeText(DietDetailsActivity.this, R.string.nogluten, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void testUserCurrent() {
        if ( fAuth.getCurrentUser ( ) != null ) {
            floatingButton.setVisibility(GONE);
            loadPersonalLove();
            getToken();
        } else {
            floatingButton.setVisibility(VISIBLE);
        }
    }

    private void getToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if(task.isSuccessful()){
                            token= task.getResult();
                            System.out.println("TOKEN "+ token);
                            String userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                            DocumentReference docRef = fStore.collection("Users").document(userID);
                            Map<String,Object> edited = new HashMap<>();
                            edited.put("token",token);
                            docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(DietDetailsActivity.this, R.string.possibletokene,
                                            Toast.LENGTH_LONG).show();
                                }
                            });


                            return;
                        }

                    }
                });
    }


    private void loadPersonalLove() {
        Intent data = getIntent();
        String shopUid = data.getStringExtra("shopUid");
        String productId = data.getStringExtra("productId");
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        if(userID != null) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("LovedBy").child(userID).child(productId);
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
                        love.setImageDrawable(ContextCompat.getDrawable(DietDetailsActivity.this, error ));
                    }
                } else {
                    isLoved = "false";
                    try {
                        Picasso.get().load(R.drawable.love).placeholder(R.drawable.loading).into(love);
                    }
                    catch (Exception e) {
                        love.setImageDrawable(ContextCompat.getDrawable(DietDetailsActivity.this, error ));
                    }                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DietDetailsActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        } else {
            isLoved = "false";
            try {
                Picasso.get().load(R.drawable.love).placeholder(R.drawable.loading).into(love);
            }
            catch (Exception e) {
                love.setImageDrawable(ContextCompat.getDrawable(DietDetailsActivity.this, error ));
            }
        }
    }

    private void disloved() {
        isLoved = "false";
        Intent data = getIntent();
        String shopUid = data.getStringExtra("shopUid");
        String productId = data.getStringExtra("productId");
        String brandName = data.getStringExtra("brandName");

        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

        if(userID != null) {
            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("LovedBy").child(userID).child(productId);
            ref.removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            try {
                                Picasso.get().load(R.drawable.love).placeholder(R.drawable.loading).into(love);

                            DocumentReference docRef = fStore.collection("Diet").document(productId);
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            nLikes = document.getString("likes");
                                            double cal = Double.parseDouble(nLikes);
                                            double cal1 = cal - 1;
                                            String cal2 = String.valueOf(cal1);
                                            HashMap<String, Object> hashMap = new HashMap<>();
                                            hashMap.put("productIcon",""+foto);
                                            hashMap.put("productId",""+productId);
                                            hashMap.put("brandName",""+brandName);
                                            hashMap.put("shopUid",""+shopUid);
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
                            love.setImageDrawable(ContextCompat.getDrawable(DietDetailsActivity.this, error ));
                        }
                    }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DietDetailsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}


    private void alertOffline ( ) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder ( this, R.style.MyAlertDialogTheme);
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

    private void loved() {
        isLoved = "true";
        Intent data = getIntent();
        String shopUid = data.getStringExtra("shopUid");
        String productId = data.getStringExtra("productId");
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

        Intent data2 = getIntent();
        String nome = data2.getStringExtra("title");
        String foto = data2.getStringExtra("photo");
        String lojauid = data2.getStringExtra("shopUid");
        String brandName = data2.getStringExtra("brandName");
        String Id = data2.getStringExtra("productId");

        if(userID != null) {
        HashMap<String, String> hashMap1 = new HashMap<>();
        hashMap1.put("love", isLoved);
            hashMap1.put("name", nome);
            hashMap1.put("brand", brandName);
            hashMap1.put("foto",foto );
            hashMap1.put("id", Id);
            hashMap1.put("shopuid", lojauid);
            hashMap1.put("useruid", userID);
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("LovedBy").child(userID).child(productId);
        ref.setValue(hashMap1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        try {
                            Picasso.get().load(R.drawable.heartgreen).placeholder(R.drawable.loading).into(love);

                            DocumentReference docRef = fStore.collection("Diet").document(productId);
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
                                            hashMap.put("productId",""+productId);
                                            hashMap.put("brandName",""+brandName);
                                            hashMap.put("productType", type);
                                            hashMap.put("uid", lojauid);
                                            hashMap.put("shopUid",""+shopUid);
                                            hashMap.put("likes",""+cal2);
                                            DocumentReference docRef2 = fStore.collection("Diet").document(productId);
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
                                            hashMap.put("productId",""+productId);
                                            hashMap.put("brandName",""+brandName);
                                            hashMap.put("shopUid",""+shopUid);
                                            hashMap.put("productType", type);
                                            hashMap.put("uid", lojauid);
                                            hashMap.put("likes","1");
                                            DocumentReference docRef2 = fStore.collection("Diet").document(productId);
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
                            love.setImageDrawable(ContextCompat.getDrawable(DietDetailsActivity.this, error ));
                        }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DietDetailsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }

    private void showQuantityDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_quantity, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        Button continueBtn = view.findViewById(R.id.continueBtn);
        ShapeableImageView productIv = view.findViewById(R.id.productIv);
        TextView titleTv = view.findViewById(R.id.titleTv);
        TextView discountedNoteTv = view.findViewById(R.id.discountedNoteTv);
        TextView originalPriceTv = view.findViewById(R.id.originalPriceTv);
        TextView priceDiscountedTv = view.findViewById(R.id.priceDiscountedTv);
        TextView finalPriceTv = view.findViewById(R.id.finalPriceTv);
        TextView quantityTv = view.findViewById(R.id.quantityTv);
        ImageButton incrementBtn = view.findViewById(R.id.incrementBtn);
        ImageButton decrementBtn = view.findViewById(R.id.decrementBtn);

        Intent data = getIntent();
        String nome = data.getStringExtra("title");
        String foto = data.getStringExtra("photo");
        String discountNote = data.getStringExtra("discountNote");
        String discountPrice = data.getStringExtra("discountPrice");
        String priceOriginal = data.getStringExtra("priceOriginal");
        String productCountry = data.getStringExtra("productCountry");

        String finalcoin = "";
        if(Objects.equals(productCountry, "Brasil")){
            originalPriceTv.setText("R$");
            priceDiscountedTv.setText("R$");
            finalcoin = "R$";
        }
        if(Objects.equals(productCountry, "Estados Unidos da América")){
            originalPriceTv.setText("US$");
            priceDiscountedTv.setText("US$");
            finalcoin = "US$";
        }
        if(Objects.equals(productCountry, "Inglaterra")){
            originalPriceTv.setText("£");
            priceDiscountedTv.setText("£");
            finalcoin = "£";
        }
        if(Objects.equals(productCountry, "Alemanha")){
            originalPriceTv.setText("€");
            priceDiscountedTv.setText("€");
            finalcoin = "€";
        }
        if(Objects.equals(productCountry, "França")){
            originalPriceTv.setText("€");
            priceDiscountedTv.setText("€");
            finalcoin = "€";
        }
        if(Objects.equals(productCountry, getResources().getString(R.string.canada))){
            originalPriceTv.setText("C$");
            priceDiscountedTv.setText("C$");
            finalcoin = "C$";
        }
        if(Objects.equals(productCountry, "Portugal")){
            originalPriceTv.setText("€");
            priceDiscountedTv.setText("€");
            finalcoin = "€";
        }
        if(Objects.equals(productCountry, "Espanha")){
            originalPriceTv.setText("€");
            priceDiscountedTv.setText("€");
            finalcoin = "€";
        }
        if(Objects.equals(productCountry, getResources().getString(R.string.mexico))){
            originalPriceTv.setText("MEX$");
            priceDiscountedTv.setText("MEX$");
            finalcoin = "MEX$";
        }
        if(Objects.equals(productCountry, getResources().getString(R.string.argentina))){
            originalPriceTv.setText("ARS$");
            priceDiscountedTv.setText("ARS$");
            finalcoin = "ARS$";
        }
        if(Objects.equals(productCountry, getResources().getString(R.string.escocia))){
            originalPriceTv.setText("£");
            priceDiscountedTv.setText("£");
            finalcoin = "£";
        }

        titleTv.setText(nome);

        if(!Objects.equals(discountNote, "")){
            originalPriceTv.setText(originalPriceTv.getText()+priceOriginal.replace(".",","));
            priceDiscountedTv.setText(priceDiscountedTv.getText()+discountPrice.replace(".",","));
            discountedNoteTv.setVisibility(View.VISIBLE);
            discountedNoteTv.setText(discountNote+"% OFF");
            originalPriceTv.setPaintFlags(originalPriceTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        } else {
            priceDiscountedTv.setText(priceDiscountedTv.getText()+priceOriginal.replace(".",","));
            originalPriceTv.setVisibility(View.GONE);
            priceDiscountedTv.setTextSize(24);
        }

        try {
            Picasso.get().load(foto).placeholder(R.drawable.loading).into(productIv);
        }
        catch(Exception e){
            productIv.setImageResource(R.drawable.error);
        }

        if(adc1!=null) {
            v1 = Double.parseDouble(va1.replace(",", ".").replace("(", "").replace(")", ""));
        }
        if(adc2!=null){
            v2 = Double.parseDouble(va2.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc3!=null){
            v3 = Double.parseDouble(va3.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc4!=null){
            v4 = Double.parseDouble(va4.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc5!=null){
            v5 = Double.parseDouble(va5.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc6!=null){
            v6 = Double.parseDouble(va6.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc7!=null){
            v7 = Double.parseDouble(va7.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc8!=null){
            v8 = Double.parseDouble(va8.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc9!=null){
            v9 = Double.parseDouble(va9.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc10!=null){
            v10 = Double.parseDouble(va10.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc11!=null){
            v11 = Double.parseDouble(va11.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc12!=null){
            v12 = Double.parseDouble(va12.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc13!=null){
            v13 = Double.parseDouble(va13.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc14!=null){
            v14 = Double.parseDouble(va14.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc15!=null){
            v15 = Double.parseDouble(va15.replace(",",".").replace("(","").replace(")",""));
        }


        if(!Objects.equals(discountNote, "")){
            Double totaladicionais = v1+v2+v3+v4+v5+v6+v7+v8+v9+v10+v11+v12+v13+v14+v15;
            Double calculo = Double.parseDouble(discountPrice.replace(",",".").replace("R$",""));
            Double temp = totaladicionais+calculo;
            String result = String.valueOf(temp).replace(".",",");

            finalPriceTv.setText(finalcoin+result);
            novirgula =finalcoin+result.replace(",",".").replace(finalcoin,"");
        }
        else {
            Double totaladicionais = v1+v2+v3+v4+v5+v6+v7+v8+v9+v10+v11+v12+v13+v14+v15;
            Double calculo = Double.parseDouble(priceOriginal.replace(",",".").replace("R$",""));
            Double temp = totaladicionais+calculo;
            String result = String.valueOf(temp).replace(".",",");

            finalPriceTv.setText(finalcoin+result);
            novirgula =finalcoin+result.replace(",",".").replace(finalcoin,"");

        }

        finalPrice = Double.parseDouble(finalPriceTv.getText().toString().replace(",",".").replace(finalcoin,""));

        String finalCoin = finalcoin;
        incrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                finalPrice = finalPrice + Double.parseDouble(novirgula.replace("R$",""));
                finalPriceTv.setText(finalCoin +String.valueOf(finalPrice).replace(".",","));
                quantityTv.setText(String.valueOf(quantity));
            }
        });
        decrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity>1){
                    quantity--;
                    finalPrice = finalPrice - Double.parseDouble(novirgula.replace("R$",""));
                    finalPriceTv.setText(finalCoin+String.valueOf(finalPrice).replace(".",","));
                    quantityTv.setText(""+quantity);
                }
            }
        });

        String finalCoin1 = finalcoin;
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = getIntent();
                String nome = data.getStringExtra("title");
                String foto = data.getStringExtra("photo");
                String price = priceDietDetail.getText().toString();
                String priceOriginal = data.getStringExtra("priceOriginal");
                String quantityProduct = data.getStringExtra("quantityProduct");
                String descriptionProduct = data.getStringExtra("descriptionProduct");
                String shopUid = data.getStringExtra("shopUid");
                String productType = data.getStringExtra("type");
                String brandName = brandTv.getText().toString();
                String productId = data.getStringExtra("productId");
                String productCountry = data.getStringExtra("productCountry");


                FirebaseFirestore.getInstance().collection("Cart "+userID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String temp = document.getId();
                                FirebaseFirestore.getInstance().collection("Cart "+userID)
                                        .document(temp).delete();
                            }
                            DocumentReference docRef = fStore.collection("Cart "+userID).document(productId);
                            Map<String,Object> cart = new HashMap<>();
                            cart.put("uid",userID);
                            cart.put("id",productId);
                            if(size!=null){
                                cart.put("size",size);}
                            if(adc1!=null){
                                v1 = Double.parseDouble(va1.replace(",",".").replace("(","").replace(")",""));
                                cart.put("adc1",adc1);
                                cart.put("va1",va1);}
                            if(adc2!=null){
                                v2 = Double.parseDouble(va2.replace(",",".").replace("(","").replace(")",""));
                                cart.put("adc2",adc2);
                                cart.put("va2",va2);}
                            if(adc3!=null){
                                v3 = Double.parseDouble(va3.replace(",",".").replace("(","").replace(")",""));
                                cart.put("adc3",adc3);
                                cart.put("va3",va3);}
                            if(adc4!=null){
                                v4 = Double.parseDouble(va4.replace(",",".").replace("(","").replace(")",""));
                                cart.put("adc4",adc4);
                                cart.put("va4",va4);}
                            if(adc5!=null){
                                v5 = Double.parseDouble(va5.replace(",",".").replace("(","").replace(")",""));
                                cart.put("adc5",adc5);
                                cart.put("va5",va5);}
                            if(adc6!=null){
                                v6 = Double.parseDouble(va6.replace(",",".").replace("(","").replace(")",""));
                                cart.put("adc6",adc6);
                                cart.put("va6",va6);}
                            if(adc7!=null){
                                v7 = Double.parseDouble(va7.replace(",",".").replace("(","").replace(")",""));
                                cart.put("adc7",adc7);
                                cart.put("va7",va7);}
                            if(adc8!=null){
                                v8 = Double.parseDouble(va8.replace(",",".").replace("(","").replace(")",""));
                                cart.put("adc8",adc8);
                                cart.put("va8",va8);}
                            if(adc9!=null){
                                v9 = Double.parseDouble(va9.replace(",",".").replace("(","").replace(")",""));
                                cart.put("adc9",adc9);
                                cart.put("va9",va9);}
                            if(adc10!=null){
                                v10 = Double.parseDouble(va10.replace(",",".").replace("(","").replace(")",""));
                                cart.put("adc10",adc10);
                                cart.put("va10",va10);}
                            if(adc11!=null){
                                v11 = Double.parseDouble(va11.replace(",",".").replace("(","").replace(")",""));
                                cart.put("adc11",adc11);
                                cart.put("va11",va11);}
                            if(adc12!=null){
                                v12 = Double.parseDouble(va12.replace(",",".").replace("(","").replace(")",""));
                                cart.put("adc12",adc12);
                                cart.put("va12",va12);}
                            if(adc13!=null){
                                v13 = Double.parseDouble(va13.replace(",",".").replace("(","").replace(")",""));
                                cart.put("adc13",adc13);
                                cart.put("va13",va13);}
                            if(adc14!=null){
                                v14 = Double.parseDouble(va14.replace(",",".").replace("(","").replace(")",""));
                                cart.put("adc14",adc14);
                                cart.put("va14",va14);}
                            if(adc15!=null){
                                v15 = Double.parseDouble(va15.replace(",",".").replace("(","").replace(")",""));
                                cart.put("adc15",adc15);
                                cart.put("va15",va15);}

                            Double totaladicionais = v1+v2+v3+v4+v5+v6+v7+v8+v9+v10+v11+v12+v13+v14+v15;

                            cart.put("name",nome);
                            cart.put("shopUid",shopUid);
                            cart.put("photo",foto);
                            cart.put("productCountry",productCountry);
                            cart.put("priceOriginal",priceOriginal);
                            cart.put("price",price);
                            cart.put("brand",brandName);
                            cart.put("descriptionProduct",descriptionProduct);
                            String qua = Integer.toString(quantity);
                            cart.put("quantity",qua);
                            Double calculo = Double.parseDouble(price.replace(",",".").replace("R$","").replace("$","").replace(finalCoin1, "").replace(finalCoin, "").replace("US", ""));

                            Double foi = calculo+totaladicionais;
                            Double maiscalculo = quantity*foi;

                            cart.put("totalvalue",String.valueOf(maiscalculo));
                            cart.put("type",productType);
                            docRef.set(cart).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent = new Intent(DietDetailsActivity.this, Order.class);
                                    intent.putExtra("title", nome);
                                    intent.putExtra("localOption", localOption);
                                    intent.putExtra("shopUid", shopUid);
                                    intent.putExtra("photo", foto);
                                    intent.putExtra("productCountry", productCountry);
                                    intent.putExtra("priceOriginal", priceOriginal);
                                    intent.putExtra("price", price);
                                    intent.putExtra("brand", brandName);
                                    intent.putExtra("descriptionProduct", descriptionProduct);
                                    intent.putExtra("scheduleProduction", scheduleProduction);
                                    intent.putExtra("quantity", qua);
                                    intent.putExtra("type", productType);
                                    v.getContext().startActivity(intent);
                                    dialog.dismiss();
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(DietDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            Log.d(TAG, list.toString());
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
            }
        });
    }

    private void showAditionalsDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_adicional, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        Button continueBtn = view.findViewById(R.id.continueBtn);
        ShapeableImageView productIv = view.findViewById(R.id.productIv);
        TextView titleTv = view.findViewById(R.id.titleTv);
        TextView discountedNoteTv = view.findViewById(R.id.discountedNoteTv);
        CheckBox ad1 = view.findViewById(R.id.checkbox);
        CheckBox ad2 = view.findViewById(R.id.checkbox2);
        CheckBox ad3 = view.findViewById(R.id.checkbox3);
        CheckBox ad4 = view.findViewById(R.id.checkbox4);
        CheckBox ad5 = view.findViewById(R.id.checkbox5);
        CheckBox ad6 = view.findViewById(R.id.checkbox6);
        CheckBox ad7 = view.findViewById(R.id.checkbox7);
        CheckBox ad8 = view.findViewById(R.id.checkbox8);
        CheckBox ad9 = view.findViewById(R.id.checkbox9);
        CheckBox ad10 = view.findViewById(R.id.checkbox10);
        CheckBox ad11 = view.findViewById(R.id.checkbox11);
        CheckBox ad12 = view.findViewById(R.id.checkbox12);
        CheckBox ad13 = view.findViewById(R.id.checkbox13);
        CheckBox ad14 = view.findViewById(R.id.checkbox14);
        CheckBox ad15 = view.findViewById(R.id.checkbox15);
        TextView val1 = view.findViewById(R.id.va1);
        TextView val2 = view.findViewById(R.id.va2);
        TextView val3 = view.findViewById(R.id.va3);
        TextView val4 = view.findViewById(R.id.va4);
        TextView val5 = view.findViewById(R.id.va5);
        TextView val6 = view.findViewById(R.id.va6);
        TextView val7 = view.findViewById(R.id.va7);
        TextView val8 = view.findViewById(R.id.va8);
        TextView val9 = view.findViewById(R.id.va9);
        TextView val10 = view.findViewById(R.id.va10);
        TextView val11 = view.findViewById(R.id.va11);
        TextView val12 = view.findViewById(R.id.va12);
        TextView val13 = view.findViewById(R.id.va13);
        TextView val14 = view.findViewById(R.id.va14);
        TextView val15 = view.findViewById(R.id.va15);

        Intent data = getIntent();
        String timestamp = data.getStringExtra("timestamp");
        String category = data.getStringExtra("categoryProduct");

        titleTv.setText(nome);
        if(!Objects.equals(discountNote, "")){
            discountedNoteTv.setText(discountNote+"% OFF");
        discountedNoteTv.setVisibility(VISIBLE);}

        try {
            Picasso.get().load(foto).placeholder(R.drawable.loading).into(productIv);
        }
        catch(Exception e){
            productIv.setImageResource(R.drawable.error);
        }
        if(!Objects.equals(adicional1, null)){
                            ad1.setVisibility(VISIBLE);
                            val1.setVisibility(VISIBLE);
                            ad1.setText(adicional1);
                            val1.setText("("+alor1+")");
                        }
        if(!Objects.equals(adicional2, null)){
                            ad2.setVisibility(VISIBLE);
                            val2.setVisibility(VISIBLE);
                            ad2.setText(adicional2);
                            val2.setText("("+alor2+")");
                        }
        if(!Objects.equals(adicional3, null)){
                            ad3.setVisibility(VISIBLE);
                            val3.setVisibility(VISIBLE);
                            ad3.setText(adicional3);
                            val3.setText("("+alor3+")");
                        }
                        if(!Objects.equals(adicional4, null)){
                            ad4.setVisibility(VISIBLE);
                            val4.setVisibility(VISIBLE);
                            ad4.setText(adicional4);
                            val4.setText("("+alor4+")");
                        }
                        if(!Objects.equals(adicional5, null)){
                            ad5.setVisibility(VISIBLE);
                            val5.setVisibility(VISIBLE);
                            ad5.setText(adicional5);
                            val5.setText("("+alor5+")");
                        }
                        if(!Objects.equals(adicional6, null)){
                            ad6.setVisibility(VISIBLE);
                            val6.setVisibility(VISIBLE);
                            ad6.setText(adicional6);
                            val6.setText("("+alor6+")");
                        }
                        if(!Objects.equals(adicional7, null)){
                            ad7.setVisibility(VISIBLE);
                            val7.setVisibility(VISIBLE);
                            ad7.setText(adicional7);
                            val7.setText("("+alor7+")");
                        }
                        if(!Objects.equals(adicional8, null)){
                            ad8.setVisibility(VISIBLE);
                            val8.setVisibility(VISIBLE);
                            ad8.setText(adicional8);
                            val8.setText("("+alor8+")");
                        }
                        if(!Objects.equals(adicional9, null)){
                            ad9.setVisibility(VISIBLE);
                            val9.setVisibility(VISIBLE);
                            ad9.setText(adicional9);
                            val9.setText("("+alor9+")");
                        }
                        if(!Objects.equals(adicional10, null)){
                            ad10.setVisibility(VISIBLE);
                            val10.setVisibility(VISIBLE);
                            ad10.setText(adicional10);
                            val10.setText("("+alor10+")");
                        }
                        if(!Objects.equals(adicional11, null)){
                            ad11.setVisibility(VISIBLE);
                            val11.setVisibility(VISIBLE);
                            ad11.setText(adicional11);
                            val11.setText("("+alor11+")");
                        }
                        if(!Objects.equals(adicional12, null)){
                            ad12.setVisibility(VISIBLE);
                            val12.setVisibility(VISIBLE);
                            ad12.setText(adicional12);
                            val12.setText("("+alor12+")");
                        }
                        if(!Objects.equals(adicional13, null)){
                            ad13.setVisibility(VISIBLE);
                            val13.setVisibility(VISIBLE);
                            ad13.setText(adicional13);
                            val13.setText("("+alor13+")");
                        }
                        if(!Objects.equals(adicional14, null)){
                            ad14.setVisibility(VISIBLE);
                            val14.setVisibility(VISIBLE);
                            ad14.setText(adicional14);
                            val14.setText("("+alor14+")");
                        }
                        if(!Objects.equals(adicional15, null)){
                            ad15.setVisibility(VISIBLE);
                            val15.setVisibility(VISIBLE);
                            ad15.setText(adicional15);
                            val15.setText("("+alor15+")");
                        }

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ad1.isChecked()){
                    adc1 = ad1.getText().toString();
                    va1= val1.getText().toString();
                }
                if(ad2.isChecked()){
                    adc2 = ad2.getText().toString();
                    va2= val2.getText().toString();
                }
                if(ad3.isChecked()){
                    adc3 = ad3.getText().toString();
                    va3= val3.getText().toString();
                }
                if(ad4.isChecked()){
                    adc4 = ad4.getText().toString();
                    va4= val4.getText().toString();
                }
                if(ad5.isChecked()){
                    adc5 = ad5.getText().toString();
                    va5= val5.getText().toString();
                }
                if(ad6.isChecked()){
                    adc6 = ad6.getText().toString();
                    va6= val6.getText().toString();
                }
                if(ad7.isChecked()){
                    adc7 = ad7.getText().toString();
                    va7= val7.getText().toString();
                }
                if(ad8.isChecked()){
                    adc8 = ad8.getText().toString();
                    va8= val8.getText().toString();
                }
                if(ad9.isChecked()){
                    adc9 = ad9.getText().toString();
                    va9= val9.getText().toString();
                }
                if(ad10.isChecked()){
                    adc10 = ad10.getText().toString();
                    va10= val10.getText().toString();
                }
                if(ad11.isChecked()){
                    adc11 = ad11.getText().toString();
                    va11= val11.getText().toString();
                }
                if(ad12.isChecked()){
                    adc12 = ad12.getText().toString();
                    va12= val12.getText().toString();
                }
                if(ad13.isChecked()){
                    adc13 = ad13.getText().toString();
                    va13= val13.getText().toString();
                }
                if(ad14.isChecked()){
                    adc14 = ad14.getText().toString();
                    va14= val14.getText().toString();
                }
                if(ad15.isChecked()){
                    adc15 = ad15.getText().toString();
                    va15= val15.getText().toString();
                }

                showQuantityDialog();
                dialog.dismiss();
            }
        });
    }

    private void showSizeDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_size, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        ShapeableImageView productIv = view.findViewById(R.id.productIv);
        TextView titleTv = view.findViewById(R.id.titleTv);
        TextView discountedNoteTv = view.findViewById(R.id.discountedNoteTv);
        Button continueBtn = view.findViewById(R.id.continueBtn);
        ImageView incrementBtn = view.findViewById(R.id.incrementBtn);
        ImageView decrementBtn = view.findViewById(R.id.decrementBtn);
        TextView quantityTv = view.findViewById(R.id.quantityTv);

        Intent data = getIntent();
        String nome = data.getStringExtra("title");
        String foto = data.getStringExtra("photo");
        String discountNote = data.getStringExtra("discountNote");

        titleTv.setText(nome);
        if(!Objects.equals(discountNote, "")){
            discountedNoteTv.setText(discountNote+"% OFF");
        discountedNoteTv.setVisibility(VISIBLE);}

        try {
            Picasso.get().load(foto).placeholder(R.drawable.loading).into(productIv);
        }
        catch(Exception e){
            productIv.setImageResource(R.drawable.error);
        }

        DocumentReference docRef = fStore.collection(categoryProduct).document(productId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        sizepp = document.getString("sizepp");
                        sizep = document.getString("sizep");
                        sizem = document.getString("sizem");
                        sizeg = document.getString("sizeg");
                        sizegg = document.getString("sizegg");
                        sizeggg = document.getString("sizeggg");
                        String sizePersonalized = document.getString("sizePersonalized");

                        if(Objects.equals(sizeggg, "true")){
                            quantityTv.setText("GGG");}
                        if(Objects.equals(sizegg, "true")){
                            quantityTv.setText("GG");}
                        if(Objects.equals(sizeg, "true")){
                            quantityTv.setText("G");}
                        if(Objects.equals(sizem, "true")){
                            quantityTv.setText("M");}
                        if(Objects.equals(sizep, "true")){
                            quantityTv.setText("P");}
                        if(Objects.equals(sizepp, "true")){
                            quantityTv.setText("PP");}

                        if(Objects.equals(sizePersonalized, "true")){
                            incrementBtn.setVisibility(GONE);
                            decrementBtn.setVisibility(GONE);
                            titleTv.setText("Digite o tamanho desejado em Observações");
                            quantityTv.setText(sizePersonalized);}
                    }
                }
            }
        });


        incrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (quantityTv.getText().toString()) {


                    case "PP":

                        if (Objects.equals(sizeggg, "true")) {
                            quantityTv.setText("GGG");
                        }
                        if (Objects.equals(sizegg, "true")) {
                            quantityTv.setText("GG");
                        }
                        if (Objects.equals(sizeg, "true")) {
                            quantityTv.setText("G");
                        }
                        if (Objects.equals(sizem, "true")) {
                            quantityTv.setText("M");
                        }
                        if (Objects.equals(sizep, "true")) {
                            quantityTv.setText("P");
                        }
                        break;

                    case "P":

                        if (Objects.equals(sizeggg, "true")) {
                            quantityTv.setText("GGG");
                        }
                        if (Objects.equals(sizegg, "true")) {
                            quantityTv.setText("GG");
                        }
                        if (Objects.equals(sizeg, "true")) {
                            quantityTv.setText("G");
                        }
                        if (Objects.equals(sizem, "true")) {
                            quantityTv.setText("M");
                        }
                        break;


                    case "M":
                        if (Objects.equals(sizeggg, "true")) {
                            quantityTv.setText("GGG");
                        }
                        if (Objects.equals(sizegg, "true")) {
                            quantityTv.setText("GG");
                        }
                        if (Objects.equals(sizeg, "true")) {
                            quantityTv.setText("G");
                        }
                        break;


                    case "G":
                        if (Objects.equals(sizeggg, "true")) {
                            quantityTv.setText("GGG");
                        }
                        if (Objects.equals(sizegg, "true")) {
                            quantityTv.setText("GG");
                        }
                        break;

                    case "GG":
                        if (Objects.equals(sizeggg, "true")) {
                            quantityTv.setText("GGG");
                        }
                        break;
                }
            }});
        decrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (quantityTv.getText().toString()) {
                    case "GGG":

                        if (Objects.equals(sizepp, "true")) {
                            quantityTv.setText("PP");
                        }
                         if (Objects.equals(sizep, "true")) {
                            quantityTv.setText("P");
                        }
                        if (Objects.equals(sizem, "true")) {
                            quantityTv.setText("M");
                        }
                        if (Objects.equals(sizeg, "true")) {
                            quantityTv.setText("G");
                        }
                        if (Objects.equals(sizegg, "true")) {
                            quantityTv.setText("GG");
                        }
                    break;


                    case "GG":
                        if (Objects.equals(sizepp, "true")) {
                            quantityTv.setText("PP");
                        }
                        if (Objects.equals(sizep, "true")) {
                            quantityTv.setText("P");
                        }
                        if (Objects.equals(sizem, "true")) {
                            quantityTv.setText("M");
                        }
                        if (Objects.equals(sizeg, "true")) {
                            quantityTv.setText("G");
                        }
                   break;

                    case "G":
                        if (Objects.equals(sizepp, "true")) {
                            quantityTv.setText("PP");
                        }
                        if (Objects.equals(sizep, "true")) {
                            quantityTv.setText("P");
                        }
                        if (Objects.equals(sizem, "true")) {
                            quantityTv.setText("M");
                        }
                    break;

                    case "M":
                        if (Objects.equals(sizepp, "true")) {
                            quantityTv.setText("PP");
                        }
                        if (Objects.equals(sizep, "true")) {
                            quantityTv.setText("P");
                        }
                    break;
                    case "P":
                        if (Objects.equals(sizepp, "true")) {
                            quantityTv.setText("PP");
                        }
                        break;
                }


            }});


        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                size=quantityTv.getText().toString();
                testAditionals();
                dialog.dismiss();
            }
        });
    }

    public void onBackPressed ( ) {
        finish ( );
    }

}
