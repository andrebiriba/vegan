package app.vegan;

import static android.content.ContentValues.TAG;

import static app.vegan.R.drawable.error;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class RestaurantDetailsActivity extends AppCompatActivity {

    private TextView brandName, addressTv, descriptionTv, timetableTv, daytableTv, title1, title2, title3, title4, title5, title6, buscador,
    promoTv, title7, timeD;
    private ImageButton imageButtonBack, love, chat, pointsIb, callingIb;
    private ShapeableImageView circleImageView;
    private ImageView coverIv, petSeal, veganSeal, accessSeal,xfilter, okfilter, correctIv;
    private ShapeableImageView img1, img2, img3, img4, img5, img6;
    private FirebaseAuth firebaseAuth;
    private ArrayList<ModelProduct> photoList, productList, productList1, productList2, productList3, productList4, productList54,
            productList5, productList6, productList7;
    private RecyclerView rvdinnerdiet, rvpromotioinsdiet, rvlunchdiet, rvdessertdiet, rvdrinksdiet,
            rvmarketdiet, rvMorediet, rvsnackdiet, rv_search;
    private HorizontalScrollView recyclerView;
    private AdapterCategories adapterCategories,adapterCategories2;
    private String shopUid, photoBrand, classe, isLoved, userID, brandIcon, description, cel, selected2, selected3, selected4, selected5, selected6, selected7
            , brand, type, selected1, selected8;
    private CardView floatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        timeD = findViewById(R.id.textView4);
        callingIb = findViewById(R.id.callingIb);
        correctIv = findViewById(R.id.correctIv);
        okfilter = findViewById(R.id.imageView58);
        promoTv = findViewById(R.id.textView8);
        xfilter = findViewById(R.id.imageView59);
        buscador = findViewById(R.id.textView9);
        title1 = findViewById(R.id.categoryTitle2);
        title2 = findViewById(R.id.categoryTitle3);
        title3 = findViewById(R.id.categoryTitlex);
        title4 = findViewById(R.id.categoryTitle4);
        title5 = findViewById(R.id.categoryTitle5);
        title6 = findViewById(R.id.categoryTitle6);
        title7 = findViewById(R.id.categoryTitle7);
        img1 = findViewById(R.id.gallery1);
        img2 = findViewById(R.id.gallery2);
        img3 = findViewById(R.id.gallery3);
        img4 = findViewById(R.id.gallery4);
        img5 = findViewById(R.id.gallery5);
        circleImageView = findViewById(R.id.circleImageView);
        chat = findViewById(R.id.imageButton17);
        love = findViewById(R.id.imageButtonLove);
        pointsIb = findViewById(R.id.pointsIb);
        petSeal = findViewById(R.id.imageView999);
        accessSeal = findViewById(R.id.imageView9);
        veganSeal = findViewById(R.id.imageView99);
        descriptionTv = findViewById(R.id.textView3);
        timetableTv = findViewById(R.id.textView6);
        daytableTv = findViewById(R.id.textVi6);
        addressTv = findViewById(R.id.imageView19);
        coverIv = findViewById(R.id.coverIv);
        brandName = findViewById(R.id.textView2);
        imageButtonBack = findViewById(R.id.imageButtonBack);
        recyclerView = findViewById(R.id.recyclerView);
        rvdinnerdiet = findViewById(R.id.rvdinnerdiet);
        rvpromotioinsdiet = findViewById(R.id.rvpromotioinsdiet);
        rvdessertdiet = findViewById(R.id.rvdessertdiet);
        rvlunchdiet = findViewById(R.id.rvlunchdiet);
        rvdrinksdiet = findViewById(R.id.rvdrinksdiet);
        rvmarketdiet = findViewById(R.id.rvmarketdiet);
        rvMorediet = findViewById(R.id.rvMorediet);
        rvsnackdiet = findViewById(R.id.rvsnackdiet);
        rv_search = findViewById(R.id.rv_search);
        floatingButton = findViewById(R.id.floatingButton);

        Intent data = getIntent();
        String uid = data.getStringExtra("uid");

        loadData();

        shopUid = getIntent().getStringExtra("uid");
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!= null) {
            userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        }
        testUserCurrent();


        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });

        floatingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantDetailsActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if ( firebaseAuth.getCurrentUser ( ) != null ) {
                    if (Objects.equals(isLoved, "false")){
                        loved();
                    } else {
                        disloved();
                    }
                } else Toast.makeText(RestaurantDetailsActivity.this, R.string.entreoucadastre, Toast.LENGTH_SHORT).show();

            }
        });

        pointsIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(userID != null) {
                        Intent intent = new Intent(v.getContext(), Points.class);
                        intent.putExtra("shopUid", shopUid);
                        intent.putExtra("shopName", brand);
                        v.getContext().startActivity(intent);
                }
            }
        });

        callingIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + cel));// Initiates the Intent
                    startActivity(intent);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String userID;
                userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                                intent.putExtra("contatoId", uid);
                                intent.putExtra("userId", userID);
                                intent.putExtra("contatoName", brand);
                                intent.putExtra("contatoPhoto", photoBrand);
                                v.getContext().startActivity(intent);
            }
        });

        buscador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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


                AlertDialog.Builder builder = new AlertDialog.Builder(RestaurantDetailsActivity.this, R.style.MyAlertDialogTheme);
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

                                    String select = Portugues[which];
                                    if (select.equals("")){
                                    }
                                    else {
                                        xfilter.setVisibility(View.VISIBLE);
                                        loadFilteredProducts(select);
                                        okfilter.setVisibility(View.VISIBLE);
                                    }
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

                                    String select = Portugues[which];
                                    if (select.equals("")){
                                    }
                                    else {
                                        xfilter.setVisibility(View.VISIBLE);
                                        loadFilteredProducts(select);
                                        okfilter.setVisibility(View.VISIBLE);
                                    }
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

                                    String select = Portugues[which];
                                    if (select.equals("")){
                                    }
                                    else {
                                        xfilter.setVisibility(View.VISIBLE);
                                        loadFilteredProducts(select);
                                        okfilter.setVisibility(View.VISIBLE);
                                    }
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

                                    String select = Portugues[which];
                                    if (select.equals("")){
                                    }
                                    else {
                                        xfilter.setVisibility(View.VISIBLE);
                                        loadFilteredProducts(select);
                                        okfilter.setVisibility(View.VISIBLE);
                                    }
                                }
                            })
                            .show();
                }
            }
        });

        xfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                buscador.setText(R.string.filtrarpor);
                xfilter.setVisibility(View.GONE);
                okfilter.setVisibility(View.GONE);

                setArrows();

                switch (classe) {
                    case "Diet":
                        selected2 = "Almoço";
                        selected3 = "Jantar";
                        selected4 = "Lanche";
                        selected5 = "Bebidas";
                        selected6 = "Sobremesas";
                        selected7 = "Mercado";
                        break;
                    case "Pharmacy":
                        selected2 = "Saúde";
                        selected3 = "Higiene";
                        selected4 = "Cosméticos";
                        selected5 = "Fitness";
                        selected6 = "Bem estar";
                        selected7 = "Mãe & Bebê";
                        break;
                    case "Closet":
                        selected2 = "Brechó";
                        selected3 = "Roupas";
                        selected4 = "Acessórios";
                        selected5 = "Calçados";
                        selected6 = "Praia";
                        selected7 = "Peça Íntima";
                        break;
                    case "Others":
                        selected2 = "Casa";
                        selected3 = "Animal Não-Humano";
                        selected4 = "Limpeza";
                        selected5 = "Eco Friendly";
                        selected6 = "Livros";
                        selected7 = "Jardim";
                        break;
                }

                loadAllPromotions(selected1);

                loadAllLunchs(selected2);

                loadAllDinners(selected3);

                loadAllSnacks(selected4);

                loadAllDrinks(selected5);

                loadAllDesserts(selected6);

                loadAllMarkets(selected7);

                loadAllMore(selected8);

            }
        });

    }

    private void loadFilteredProducts(String selected1) {
        hide();
        Intent data = getIntent();
        String brand = data.getStringExtra("brandName");
        String uid = data.getStringExtra("uid");
        String type = data.getStringExtra("type");

        productList54 = new ArrayList<>();
        String fAuth = firebaseAuth.getUid();
        String filter1 = type;

        FirebaseFirestore reef = FirebaseFirestore.getInstance();
        reef.collection(selected1)
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        productList54.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList54.add(modelProduct);
                                adapterCategories = new AdapterCategories(RestaurantDetailsActivity.this, productList54);
                                rv_search.setAdapter(adapterCategories);
                            }
                            if (adapterCategories.getItemCount() == 0) {
                                rv_search.setVisibility(View.GONE);
                            }
                        } else {
                         }
                    }
                });

    }

    private void hide() {
        promoTv.setText(R.string.resultadosdab);
        title1.setVisibility(View.GONE);
        title2.setVisibility(View.GONE);
        title3.setVisibility(View.GONE);
        title4.setVisibility(View.GONE);
        title5.setVisibility(View.GONE);
        title6.setVisibility(View.GONE);
        title7.setVisibility(View.GONE);
        rvdinnerdiet.setVisibility(View.GONE);
        rvpromotioinsdiet.setVisibility(View.GONE);
        rvlunchdiet.setVisibility(View.GONE);
        rvdessertdiet.setVisibility(View.GONE);
        rvdrinksdiet.setVisibility(View.GONE);
        rvmarketdiet.setVisibility(View.GONE);
        rvMorediet.setVisibility(View.GONE);
        rvsnackdiet.setVisibility(View.GONE);
        rv_search.setVisibility(View.VISIBLE);
    }

    private void setArrows() {
        promoTv.setText(R.string.promocoes);
        title1.setVisibility(View.VISIBLE);
        title2.setVisibility(View.VISIBLE);
        title3.setVisibility(View.VISIBLE);
        title4.setVisibility(View.VISIBLE);
        title5.setVisibility(View.VISIBLE);
        title6.setVisibility(View.VISIBLE);
        rv_search.setVisibility(View.GONE);
        rvdinnerdiet.setVisibility(View.VISIBLE);
        rvpromotioinsdiet.setVisibility(View.VISIBLE);
        rvlunchdiet.setVisibility(View.VISIBLE);
        rvdessertdiet.setVisibility(View.VISIBLE);
        rvdrinksdiet.setVisibility(View.VISIBLE);
        rvmarketdiet.setVisibility(View.VISIBLE);
        rvMorediet.setVisibility(View.VISIBLE);
        rvsnackdiet.setVisibility(View.VISIBLE);

        switch (classe) {
            case "Diet":
                title1.setText(getResources().getString(R.string.almoco));
                title2.setText(getResources().getString(R.string.dinner));
                title3.setText(getResources().getString(R.string.lanches));
                title4.setText(getResources().getString(R.string.sobremesas));
                title5.setText(getResources().getString(R.string.bebidas));
                title6.setText(getResources().getString(R.string.mercado));
                break;
            case "Pharmacy":
                title1.setText(R.string.saude);
                title2.setText(R.string.higiene);
                title3.setText(R.string.cosmeticos);
                title4.setText(R.string.natural);
                title5.setText(R.string.fitness);
                title6.setText(R.string.maeebebe);
                break;
            case "Closet":
                title1.setText(R.string.brecho);
                title2.setText(R.string.roupas);
                title3.setText(R.string.acessorios);
                title4.setText(R.string.praia);
                title5.setText(R.string.calcados);
                title6.setText(R.string.pecaintima);
                break;
            case "Others":
                title1.setText(R.string.casa);
                title2.setText(R.string.animalnaohumano);
                title3.setText(R.string.limpeza);
                title4.setText(R.string.livros);
                title5.setText(R.string.ecofriendly);
                title6.setText(R.string.jardim);
                break;
        }

    }

    private void loadData() {
        Intent data = getIntent();
        String shopUid = data.getStringExtra("uid");
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection("Adm's").document(shopUid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String cover = document.getString("photo");
                        String neigh = document.getString("neighborhood");
                        String address = document.getString("street");
                        String addressProfile = document.getString("addressProfile");
                        String number = document.getString("number");
                        String timeDelivery = document.getString("timeDelivery");
                        description = document.getString("description");
                        String timetable = document.getString("timetable");
                        String daytable = document.getString("daytable");
                        String petfriendly = document.getString("petfriendly");
                        String accessibility = document.getString("accessibility");
                        String vegan = document.getString("vegan");
                        String possuiFidelidade = document.getString("possuiFidelidade");
                        brandIcon = document.getString("shopIcon");
                        String photo1 = document.getString("photo1");
                        String photo2 = document.getString("photo2");
                        String photo3 = document.getString("photo3");
                        String photo4 = document.getString("photo4");
                        String photo5 = document.getString("photo5");
                        String correct = document.getString("correct");
                        String fName = document.getString("fName");
                        type = document.getString("type");

                        brand = fName;
                        classe = type;
                        setArrows();
                        brandName.setText(fName);

                        selected1 = "Promoções";
                        loadAllPromotions(selected1);
                        selected8 = "Mais";
                        loadAllMore(selected8);

                        switch (classe) {
                            case "Diet":
                                selected2 = "Almoço";
                                selected3 = "Jantar";
                                selected4 = "Lanche";
                                selected5 = "Bebidas";
                                selected6 = "Sobremesas";
                                selected7 = "Mercado";
                                break;
                            case "Pharmacy":
                                selected2 = "Saúde";
                                selected3 = "Higiene";
                                selected4 = "Cosméticos";
                                selected5 = "Fitness";
                                selected6 = "Bem estar";
                                selected7 = "Mãe & Bebê";
                                break;
                            case "Closet":
                                selected2 = "Brechó";
                                selected3 = "Roupas";
                                selected4 = "Acessórios";
                                selected5 = "Calçados";
                                selected6 = "Praia";
                                selected7 = "Peça Intíma";
                                break;
                            case "Others":
                                selected2 = "Casa";
                                selected3 = "Animal Não-Humano";
                                selected4 = "Limpeza";
                                selected5 = "Eco Friendly";
                                selected6 = "Livros";
                                selected7 = "Jardim";
                                break;
                        }


                        loadAllLunchs(selected2);

                        loadAllDinners(selected3);

                        loadAllSnacks(selected4);

                        loadAllDrinks(selected5);

                        loadAllDesserts(selected6);

                        loadAllMarkets(selected7);


                        cel = document.getString("cel");
                                if(Objects.equals(correct, "true")){
                        correctIv.setVisibility(View.VISIBLE);}

                        photoBrand = brandIcon;
                        descriptionTv.setText(description);
                        timeD.setText(timeDelivery);
                        if(Objects.equals(timeDelivery, "")){
                            ImageView imageView103 = findViewById(R.id.imageView103);
                            imageView103.setVisibility(View.GONE);
                        }

                        if(Objects.equals(addressProfile, "")) {
                            addressTv.setText(neigh + ", " + address + ", " + number);
                        } else {
                            addressTv.setText(addressProfile);
                        }
                        timetableTv.setText(timetable);
                        daytableTv.setText(daytable);

                        if (Objects.equals(possuiFidelidade, "true")) {
                            Picasso.get().load(R.drawable.giftbox_white).placeholder(R.drawable.loading).into(pointsIb);
                        }
                        if (Objects.equals(vegan, "true")) {
                                veganSeal.setVisibility(View.VISIBLE);
                            }

                        if (Objects.equals(accessibility, "true")) {
                                accessSeal.setVisibility(View.VISIBLE);
                            }

                        if (Objects.equals(petfriendly, "true")) {
                                petSeal.setVisibility(View.VISIBLE);
                            }

                        try {
                            Picasso.get().load(cover).placeholder(R.drawable.loading).into(coverIv);
                        }
                        catch (Exception e) {
                            if (coverIv == null)
                                return;
                            else
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(coverIv);
                        }


                        try {
                            Picasso.get().load(brandIcon).placeholder(R.drawable.loading).into(circleImageView);
                        }
                        catch (Exception e) {
                            if (circleImageView == null)
                                return;
                            else
                                circleImageView.setImageResource(R.drawable.error);
                        }

                        try {
                            Picasso.get().load(photo1).placeholder(R.drawable.loading).into(img1);
                        }
                        catch (Exception e) {
                            if (img1 == null)
                                return;
                            else
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(img1);
                        }

                        try {
                            Picasso.get().load(photo2).placeholder(R.drawable.loading).into(img2);
                        }
                        catch (Exception e) {
                            if (img2 == null)
                                return;
                            else
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(img2);
                        }

                        try {
                            Picasso.get().load(photo3).placeholder(R.drawable.loading).into(img3);
                        }
                        catch (Exception e) {
                            if (img3 == null)
                                return;
                            else
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(img3);
                        }

                        try {
                            Picasso.get().load(photo4).placeholder(R.drawable.loading).into(img4);
                        }
                        catch (Exception e) {
                            if (img4 == null)
                                return;
                            else
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(img4);
                        }

                        try {
                            Picasso.get().load(photo5).placeholder(R.drawable.loading).into(img5);
                        }
                        catch (Exception e) {
                            if (img5 == null)
                                return;
                            else
                                Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(img5);
                        }

                    }
                }
            }
        });
    }

    public void testUserCurrent() {
        if ( userID != null ) {
            floatingButton.setVisibility(View.GONE);
            loadPersonalLove();
        } else {
            floatingButton.setVisibility(View.VISIBLE);
        }
    }

    private void loadPersonalLove() {
        userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        if(userID != null) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("LovedBy").child(userID).child(shopUid);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String loveDB = ""+snapshot.child("love").getValue();
                    if (loveDB.equals("true")) {
                        isLoved = "true";
                        try {
                            Picasso.get().load(R.drawable.heartgreen).placeholder((R.drawable.loading)).into(love);
                        }
                        catch (Exception e) {
                            love.setImageDrawable(ContextCompat.getDrawable(RestaurantDetailsActivity.this, error ));
                        }
                    } else {
                        isLoved = "false";
                        try {
                            Picasso.get().load(R.drawable.love).placeholder((R.drawable.loading)).into(love);
                        }
                        catch (Exception e) {
                            love.setImageDrawable(ContextCompat.getDrawable(RestaurantDetailsActivity.this, error ));
                        }                }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(RestaurantDetailsActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            isLoved = "false";
            try {
                Picasso.get().load(R.drawable.love).placeholder((R.drawable.loading)).into(love);
            }
            catch (Exception e) {
                love.setImageDrawable(ContextCompat.getDrawable(RestaurantDetailsActivity.this, error ));
            }
        }
    }

    private void disloved() {
        isLoved = "false";
        userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

        if(userID != null) {
            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("LovedBy").child(userID).child(shopUid);
            ref.removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            try {
                                Picasso.get().load(R.drawable.love).placeholder((R.drawable.loading)).into(love);
                            }
                            catch (Exception e) {
                                love.setImageDrawable(ContextCompat.getDrawable(RestaurantDetailsActivity.this, error ));
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RestaurantDetailsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void loved() {
        isLoved = "true";
        userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();


        if(userID != null) {
            HashMap<String, String> hashMap1 = new HashMap<>();
            hashMap1.put("love", isLoved);
            hashMap1.put("name", brandName.getText().toString());
            hashMap1.put("brand", description);
            hashMap1.put("foto",brandIcon );
            hashMap1.put("id", shopUid);
            hashMap1.put("shopuid", shopUid);
            hashMap1.put("useruid", userID);
            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("LovedBy").child(userID).child(shopUid);
            ref.setValue(hashMap1)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            try {
                                Picasso.get().load(R.drawable.heartgreen).placeholder((R.drawable.loading)).into(love);
                            }
                            catch (Exception e) {
                                love.setImageDrawable(ContextCompat.getDrawable(RestaurantDetailsActivity.this, error ));
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RestaurantDetailsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public void loadAllPromotions(final String selected1) {
        productList1 = new ArrayList<>();
        Intent data = getIntent();
        String uid = data.getStringExtra("uid");

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection(selected1)
                .whereEqualTo("uid", uid)
                .whereEqualTo("productCategory",selected1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList1.add(modelProduct);
                                adapterCategories = new AdapterCategories(RestaurantDetailsActivity.this, productList1);
                                rvpromotioinsdiet.setAdapter(adapterCategories);
                            }
                        }
                        if(productList1.isEmpty()){
                            rvpromotioinsdiet.setVisibility(View.GONE);
                            promoTv.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void loadAllLunchs(final String selected2) {
        productList2 = new ArrayList<>();
        Intent data = getIntent();
        String uid = data.getStringExtra("uid");
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection(selected2)
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList2.add(modelProduct);
                                adapterCategories = new AdapterCategories(RestaurantDetailsActivity.this, productList2);
                                rvlunchdiet.setAdapter(adapterCategories);
                            }
                        }
                        if(productList2.isEmpty()){
                            rvlunchdiet.setVisibility(View.GONE);
                            title1.setVisibility(View.GONE);
                        }
                    }
                });

    }

    public void loadAllDinners(final String selected3) {
        productList = new ArrayList<>();
        Intent data = getIntent();
        String uid = data.getStringExtra("uid");
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection(selected3)
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList.add(modelProduct);
                                adapterCategories = new AdapterCategories(RestaurantDetailsActivity.this, productList);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RestaurantDetailsActivity.this, LinearLayoutManager.VERTICAL, false);
                                rvdinnerdiet.setLayoutManager(linearLayoutManager);
                                rvdinnerdiet.setAdapter(adapterCategories);
                            }

                        }
                        if(productList.isEmpty()){
                            rvdinnerdiet.setVisibility(View.GONE);
                            title2.setVisibility(View.GONE);
                        }

                    }
                });
    }

    public void loadAllSnacks(final String selected4) {
        productList3 = new ArrayList<>();
        Intent data = getIntent();
        String uid = data.getStringExtra("uid");
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection(selected4)
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList3.add(modelProduct);
                                adapterCategories = new AdapterCategories(RestaurantDetailsActivity.this, productList3);
                                rvsnackdiet.setAdapter(adapterCategories);
                            }
                        }
                        if(productList3.isEmpty()){
                            rvsnackdiet.setVisibility(View.GONE);
                            title3.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void loadAllDrinks(final String selected5) {
        productList4 = new ArrayList<>();

        Intent data = getIntent();
        String uid = data.getStringExtra("uid");
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection(selected5)
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList4.add(modelProduct);
                                adapterCategories = new AdapterCategories(RestaurantDetailsActivity.this, productList4);
                                rvdrinksdiet.setAdapter(adapterCategories);
                            }
                        }
                        if(productList4.isEmpty()){
                            rvdrinksdiet.setVisibility(View.GONE);
                            title5.setVisibility(View.GONE);
                        }
                    }
                });

    }

    public void loadAllDesserts(final String selected6) {
        productList5 = new ArrayList<>();

        Intent data = getIntent();
        String uid = data.getStringExtra("uid");
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection(selected6)
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList5.add(modelProduct);
                                adapterCategories = new AdapterCategories(RestaurantDetailsActivity.this, productList5);
                                rvdessertdiet.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RestaurantDetailsActivity.this, LinearLayoutManager.VERTICAL, true);
                                rvdessertdiet.setLayoutManager(linearLayoutManager);
                                rvdessertdiet.setAdapter(adapterCategories);
                            }
                        }
                        if(productList5.isEmpty()){
                            rvdessertdiet.setVisibility(View.GONE);
                            title4.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void loadAllMarkets(final String selected7) {
        productList6 = new ArrayList<>();

        Intent data = getIntent();
        String uid = data.getStringExtra("uid");
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection(selected7)
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList6.add(modelProduct);
                                adapterCategories = new AdapterCategories(RestaurantDetailsActivity.this, productList6);
                                rvmarketdiet.setAdapter(adapterCategories);
                            }

                        if(productList6.isEmpty()){
                            rvmarketdiet.setVisibility(View.GONE);
                            title6.setVisibility(View.GONE);
                        }
                    }
                    }
                });
    }

    public void loadAllMore(final String selected8) {
        productList7 = new ArrayList<>();

        Intent data = getIntent();
        String uid = data.getStringExtra("uid");
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection(selected8)
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList7.add(modelProduct);
                                adapterCategories = new AdapterCategories(RestaurantDetailsActivity.this, productList7);
                                rvMorediet.setAdapter(adapterCategories);
                            }
                        }
                        if(productList7.isEmpty()){
                            rvMorediet.setVisibility(View.GONE);
                            title7.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void onBackPressed ( ) {
        finish ( );
    }
}
