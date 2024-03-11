package app.vegan;

import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;


public class MoreItens extends AppCompatActivity implements Serializable {

    private TextView brandName, title1, title2, title3, title4, title5, title6,
            promoTv, title7, filter;
    private ImageButton imageButtonBack;
    private ShapeableImageView circleImageView;
    private ImageView xfilter, okfilter;

    private ArrayList<ModelProduct> productList, productList1, productList2, productList3, productList4,
            productList5, productList6, productList7;
    private RecyclerView rvdinnerdiet, rvpromotioinsdiet, rvlunchdiet, rvdessertdiet, rvdrinksdiet,
            rvmarketdiet, rvMorediet, rvsnackdiet, rv_search;
    private AdapterCategoriesMoreItens adapterCategories;
    private String classe, selected2, selected3, selected4, selected5, selected6, selected7,selected8, selected1;
    private FirebaseAuth firebaseAuth;
    private ArrayList<ModelProduct> productList54;

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
        setContentView(R.layout.activity_more_itens);

        okfilter = findViewById(R.id.imageView58);
        xfilter = findViewById(R.id.imageView59);
        filter = findViewById(R.id.textView9);
        title7 = findViewById(R.id.categoryTitle7);
        promoTv = findViewById(R.id.textViw8);
        title1 = findViewById(R.id.categoryTitle2);
        title2 = findViewById(R.id.categoryTitle3);
        title3 = findViewById(R.id.categoryTitlex);
        title4 = findViewById(R.id.categoryTitle4);
        title5 = findViewById(R.id.categoryTitle5);
        title6 = findViewById(R.id.categoryTitle6);
        circleImageView = findViewById(R.id.imageButton17);
        brandName = findViewById(R.id.textView8);
        imageButtonBack = findViewById(R.id.imageButtonBack);
        rvdinnerdiet = findViewById(R.id.rvdinnerdiet);
        rvpromotioinsdiet = findViewById(R.id.rvpromotioinsdiet);
        rvdessertdiet = findViewById(R.id.rvdessertdiet);
        rvlunchdiet = findViewById(R.id.rvlunchdiet);
        rvdrinksdiet = findViewById(R.id.rvdrinksdiet);
        rvmarketdiet = findViewById(R.id.rvmarketdiet);
        rvMorediet = findViewById(R.id.rvMorediet);
        rvsnackdiet = findViewById(R.id.rvsnackdiet);
        rv_search = findViewById(R.id.rv_search);

        Intent data = getIntent();
        String brand = data.getStringExtra("brandName");
        classe = data.getStringExtra("type");
        setArrows();
        loadPhoto();
        firebaseAuth = FirebaseAuth.getInstance();

        boolean statuInternet = isOnline ( this );

        if (!statuInternet) {
            alertOffline ( );
            Toast.makeText(MoreItens.this, R.string.falhananet, Toast.LENGTH_SHORT).show();
        } else {

            this.firebaseAuth = FirebaseAuth.getInstance();
        }


        brandName.setText(brand);

         selected8 = "Mais";
         selected1 = "Promoções";

        if(Objects.equals(classe, "Diet")){
        String selected2 = "Almoço";
        String selected3 = "Jantar";
        String selected4 = "Lanche";
        String selected5 = "Bebidas";
        String selected6 = "Sobremesas";
        String selected7 = "Mercado";
            loadAllLunchs(selected2);
            loadAllDinners(selected3);
            loadAllSnacks(selected4);
            loadAllDrinks(selected5);
            loadAllDesserts(selected6);
            loadAllMarkets(selected7);
        }

        if(Objects.equals(classe, "Pharmacy")){
             selected2 = "Saúde";
             selected3 = "Higiene";
             selected4 = "Cosméticos";
             selected5 = "Fitness";
             selected6 = "Mãe & Bebê";
             selected7 = "Bem estar";
            loadAllLunchs(selected2);
            loadAllDinners(selected3);
            loadAllSnacks(selected4);
            loadAllDrinks(selected5);
            loadAllDesserts(selected6);
            loadAllMarkets(selected7);
        }

        if(Objects.equals(classe, "Closet")){
             selected2 = "Brechó";
             selected3 = "Roupas";
             selected4 = "Acessórios";
             selected5 = "Calçados";
             selected6 = "Peça Intíma";
             selected7 = "Praia";
            loadAllLunchs(selected2);
            loadAllDinners(selected3);
            loadAllSnacks(selected4);
            loadAllDrinks(selected5);
            loadAllDesserts(selected6);
            loadAllMarkets(selected7);
        }

        if(Objects.equals(classe, "Others")){
             selected2 = "Casa";
             selected3 = "Animal Não-Humano";
             selected4 = "Limpeza";
             selected5 = "Eco Friendly";
             selected6 = "Jardim";
             selected7 = "Livros";
            loadAllLunchs(selected2);
            loadAllDinners(selected3);
            loadAllSnacks(selected4);
            loadAllDrinks(selected5);
            loadAllDesserts(selected6);
            loadAllMarkets(selected7);
        }

        loadAllPromotions(selected1);

        loadAllMore(selected8);

        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String type=classe;

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


                AlertDialog.Builder builder = new AlertDialog.Builder(MoreItens.this, R.style.MyAlertDialogTheme);
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
                filter.setText(R.string.filtrarpor);
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
    private void hide() {
        promoTv.setVisibility(View.GONE);
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
                                adapterCategories = new AdapterCategoriesMoreItens(MoreItens.this, productList54);
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


    private void setArrows() {
        promoTv.setText(R.string.promocoes);
        title1.setVisibility(View.VISIBLE);
        title2.setVisibility(View.VISIBLE);
        title3.setVisibility(View.VISIBLE);
        title4.setVisibility(View.VISIBLE);
        title5.setVisibility(View.VISIBLE);
        title6.setVisibility(View.VISIBLE);

        rvdinnerdiet.setVisibility(View.VISIBLE);
        rvpromotioinsdiet.setVisibility(View.VISIBLE);
        rvlunchdiet.setVisibility(View.VISIBLE);
        rvdessertdiet.setVisibility(View.VISIBLE);
        rvdrinksdiet.setVisibility(View.VISIBLE);
        rvmarketdiet.setVisibility(View.VISIBLE);
        rvMorediet.setVisibility(View.VISIBLE);
        rvsnackdiet.setVisibility(View.VISIBLE);
        rv_search.setVisibility(View.GONE);

        switch (classe) {
            case "Diet":
                title1.setText(R.string.almoco);
                title2.setText(R.string.dinner);
                title3.setText(R.string.lanches);
                title4.setText(R.string.sobremesas);
                title5.setText(R.string.bebidas);
                title6.setText(R.string.mercado);
                break;
            case "Pharmacy":
                title1.setText(R.string.saude);
                title2.setText(R.string.higiene);
                title3.setText(R.string.cosmeticos);
                title4.setText(R.string.maeebebe);
                title5.setText(R.string.fitness);
                title6.setText(R.string.natural);
                break;
            case "Closet":
                title1.setText(R.string.brecho);
                title2.setText(R.string.roupas);
                title3.setText(R.string.acessorios);
                title4.setText(R.string.pecaintima);
                title5.setText(R.string.calcados);
                title6.setText(R.string.praia);
                break;
            case "Others":
                title1.setText(R.string.casa);
                title2.setText(R.string.animalnaohumano);
                title3.setText(R.string.limpeza);
                title4.setText(R.string.ecofriendly);
                title5.setText(R.string.jardim);
                title6.setText(R.string.livros);
                break;
        }

    }

    private void loadPhoto() {
        Intent data = getIntent();
        String uid = data.getStringExtra("uid");
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection("Adm's").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String brandIcon = document.getString("shopIcon");

                        try {
                            Picasso.get().load(brandIcon).placeholder(R.drawable.loading).into(circleImageView);
                        }
                        catch (Exception e) {
                            if (circleImageView == null)
                                return;
                            else
                                circleImageView.setImageResource(R.drawable.error);
                        }

                    }
                }
            }
        });

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
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList1.add(modelProduct);
                                adapterCategories = new AdapterCategoriesMoreItens(MoreItens.this, productList1);
                                rvpromotioinsdiet.setAdapter(adapterCategories);
                            }
                        }
                        if(productList1.isEmpty()){
                            rvpromotioinsdiet.setVisibility(View.GONE);
                            promoTv.setVisibility(View.GONE);
                        }

                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList2.add(modelProduct);
                                adapterCategories = new AdapterCategoriesMoreItens(MoreItens.this, productList2);
                                rvlunchdiet.setAdapter(adapterCategories);
                            }
                        }
                        if(productList2.isEmpty()){
                            rvlunchdiet.setVisibility(View.GONE);
                            title1.setVisibility(View.GONE);
                        }
                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList.add(modelProduct);
                                adapterCategories = new AdapterCategoriesMoreItens(MoreItens.this, productList);
                                rvdinnerdiet.setAdapter(adapterCategories);
                            }
                        }
                        if(productList.isEmpty()){
                            rvdinnerdiet.setVisibility(View.GONE);
                            title2.setVisibility(View.GONE);
                        }
                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList3.add(modelProduct);
                                adapterCategories = new AdapterCategoriesMoreItens(MoreItens.this, productList3);
                                rvsnackdiet.setAdapter(adapterCategories);
                            }
                        }
                        if(productList3.isEmpty()){
                            rvsnackdiet.setVisibility(View.GONE);
                            title3.setVisibility(View.GONE);
                        }
                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList4.add(modelProduct);
                                adapterCategories = new AdapterCategoriesMoreItens(MoreItens.this, productList4);
                                rvdrinksdiet.setAdapter(adapterCategories);
                            }
                        }
                        if(productList4.isEmpty()){
                            rvdrinksdiet.setVisibility(View.GONE);
                            title5.setVisibility(View.GONE);
                        }
                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList5.add(modelProduct);
                                adapterCategories = new AdapterCategoriesMoreItens(MoreItens.this, productList5);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MoreItens.this, LinearLayoutManager.VERTICAL, true);
                                rvdessertdiet.setLayoutManager(linearLayoutManager);
                                rvdessertdiet.setHasFixedSize(true);
                                rvdessertdiet.setAdapter(adapterCategories);
                            }
                        }
                        if(productList5.isEmpty()){
                            rvdessertdiet.setVisibility(View.GONE);
                            title4.setVisibility(View.GONE);
                        }
                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList6.add(modelProduct);
                                adapterCategories = new AdapterCategoriesMoreItens(MoreItens.this, productList6);
                                rvmarketdiet.setAdapter(adapterCategories);
                            }
                        }
                        if(productList6.isEmpty()){
                            rvmarketdiet.setVisibility(View.GONE);
                            title6.setVisibility(View.GONE);
                        }
                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList7.add(modelProduct);
                                adapterCategories = new AdapterCategoriesMoreItens(MoreItens.this, productList7);
                                rvMorediet.setAdapter(adapterCategories);
                            }
                        }
                        if(productList7.isEmpty()){
                            rvMorediet.setVisibility(View.GONE);
                            title7.setVisibility(View.GONE);
                        }
                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


}
