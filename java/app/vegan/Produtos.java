package app.vegan;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class Produtos extends AppCompatActivity {

    private TextView tabProductsTv, tabOrdersTv, filteredProductsTv;
    private EditText searchProductEt;
    private ImageView filterProductBtn;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private RelativeLayout productsRl;
    private RecyclerView productsRv;
    private ImageButton imageButton6;

    private ArrayList<ModelProduct> productList;
    private AdapterProductSeller adapterProductSeller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);
        setRequestedOrientation ( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        filteredProductsTv = findViewById(R.id.filteredProductsTv);
        searchProductEt = findViewById(R.id.searchProductEt);
        filterProductBtn = findViewById(R.id.filterProductBtn);
        productsRl = findViewById(R.id.productsRl);
        productsRv = findViewById(R.id.productsRv);
        adapterProductSeller = new AdapterProductSeller(Produtos.this, productList);
        productsRv.setAdapter(adapterProductSeller);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Aguarde");
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth = FirebaseAuth.getInstance();

        loadAllProducts();

        productsRl.setVisibility(View.VISIBLE);

        searchProductEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    adapterProductSeller.getFilter().filter(s);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imageButton6 = findViewById(R.id.imageButton6);

        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });

        String[] MemberList = {
                getString(R.string.promocoes),
                getString(R.string.almoco),
                getString(R.string.lanches),
                getString(R.string.dinner),
                getString(R.string.bebidas),
                getString(R.string.sobremesas),
                getString(R.string.mercado),
                getString(R.string.saude),
                getString(R.string.higiene),
                getString(R.string.cosmeticos),
                getString(R.string.fitness),
                getString(R.string.maeebebe),
                getString(R.string.natural),
                getString(R.string.brecho),
                getString(R.string.roupas),
                getString(R.string.acessorios),
                getString(R.string.calcados),
                getString(R.string.pecaintima),
                getString(R.string.praia),
                getString(R.string.casa),
                getString(R.string.animalnaohumano),
                getString(R.string.limpeza),
                getString(R.string.ecofriendly),
                getString(R.string.jardim),
                getString(R.string.livros),
                getString(R.string.dietaenutri),
                getString(R.string.saudeebemestar),
                getString(R.string.estetica),
                getString(R.string.tatuagem),
                getString(R.string.turismo),
                getString(R.string.culinaria),
                getString(R.string.mais)};

        filterProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Produtos.this, R.style.MyAlertDialogTheme);
                builder.setTitle(R.string.esccateg)
                        .setItems(MemberList, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String selecte = MemberList[which];

                                String[] Portugues = {
                                        "Promoções",
                                        "Almoço",
                                        "Lanche",
                                        "Jantar",
                                        "Bebidas",
                                        "Sobremesas",
                                        "Mercado",
                                        "Saúde",
                                        "Higiene",
                                        "Cosméticos",
                                        "Fitness",
                                        "Mãe & Bebê",
                                        "Bem estar",
                                        "Brechó",
                                        "Roupas",
                                        "Acessórios",
                                        "Calçados",
                                        "Peça Intíma",
                                        "Praia",
                                        "Casa",
                                        "Animal Não-Humano",
                                        "Limpeza",
                                        "Eco Friendly",
                                        "Jardim",
                                        "Livros",
                                        "Nutricionistas",
                                        "Médicos",
                                        "Cabeleireiros & Barbeiros",
                                        "Tatuadores",
                                        "Lazer",
                                        "Alimentícios",
                                        "Mais"};

                                String selected = Portugues[which];


                                filteredProductsTv.setText(selecte);
                                if (selected.equals(" ")){
                                    loadAllProducts();
                                }
                                else {
                                    loadFilteredProducts(selected);
                                }
                            }
                        })
                .show();
            }
        });
    }

    private void loadFilteredProducts(final String selected) {
        productList = new ArrayList<>();
        String fAuth = firebaseAuth.getUid();

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection(selected)
                .whereEqualTo("uid", fAuth)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        productList.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList.add(modelProduct);
                                adapterProductSeller = new AdapterProductSeller(Produtos.this, productList);
                                productsRv.setAdapter(adapterProductSeller);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void loadAllProducts() {
        productList = new ArrayList<>();
        String fAuth = firebaseAuth.getUid();

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection("Promoções")
                .whereEqualTo("uid", fAuth)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        productList.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList.add(modelProduct);
                                adapterProductSeller = new AdapterProductSeller(Produtos.this, productList);
                                productsRv.setAdapter(adapterProductSeller);
                            }
                        }
                    }
                });

    }

    public void onBackPressed ( ) {
        finish ( );
    }

}
