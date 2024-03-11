package app.vegan;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {

    private int limit, limit2, limit3, limit4;
    private LinearLayoutManager layoutManager;
    private GridLayoutManager layoutManager2;
    private boolean isLastItemReached, isLastItemReached2,isLastItemReached3, isLastItemReached4;
    private boolean isScrolling, isScrolling2, isScrolling3, isScrolling4;
    private DocumentSnapshot lastVisible, lastVisible2, lastVisible3, lastVisible4;
    private FirebaseAuth firebaseAuth;
    private ImageButton imageButtonBack, imageGridButton, imageListButton;
    RecyclerView rv_search_grid, rv_search_list;
    private ImageView clickProductListener,xsearch, filterIv, checkfilter;
    private AdapterGridSearch adapterSearchGrid, adapterSearchGrids;
    private AdapterListSearch adapterListSearch;
    private AdapterServicesList adapterServicesList;
    private AdapterServicesGrid adapterServicesGrid;
    private EditText searchEt;
    private AdapterListSearch adapterProductSeller;
    private CardView floatingButton;
    private ArrayList<ModelProduct> productList, productList0, productList1, productList2;
    private TextView buscaTv;
    private String textP, titleP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setRequestedOrientation ( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        buscaTv = findViewById(R.id.textView44);
        floatingButton = findViewById(R.id.floatingButton);
        checkfilter = findViewById(R.id.imageView58);
        filterIv = findViewById(R.id.imageView3);
        xsearch = findViewById(R.id.imageView59);
        searchEt = findViewById(R.id.editText45);
        clickProductListener = findViewById(R.id.imageView60);
        rv_search_list = findViewById(R.id.rv_search_list);
        rv_search_grid = findViewById(R.id.rv_search_grid);
        imageButtonBack = findViewById(R.id.imageButtonBack);
        imageGridButton = findViewById(R.id.imageViewGridButton);
        imageListButton = findViewById(R.id.imageViewListButton);
        adapterProductSeller = new AdapterListSearch(SearchActivity.this, productList);

        firebaseAuth = FirebaseAuth.getInstance();
        testUserCurrent();

        String selected1 ="Promoções";
        loadSearchGrid(selected1);
        loadSearchList(selected1);
        String a = getResources().getString(R.string.filtradopor);
        buscaTv.setText(a);

        Intent data = getIntent();
        String se = data.getStringExtra("search");
        if(!Objects.equals(se, "") && !Objects.equals(se, null)) {
            searchEt.setText(se);
            buscaTv.setText(getString(R.string.filtradop) + " " + selected1);

        }
        xsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                searchEt.setText("");
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
                "Lazer",
                getString(R.string.culinaria),
                getString(R.string.mais)};

        filterIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this, R.style.MyAlertDialogTheme);
                builder.setTitle(getString(R.string.cate))
                        .setItems(MemberList, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //get selected item
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

                                if (selected.equals("")){
                                    //load all
                                    loadSearchList(selected);
                                }
                                else {
                                    checkfilter.setVisibility(View.VISIBLE);
                                    Log.d(TAG, " => " + selected);
                                    Log.d(TAG, " => " + selecte);
                                    if(selecte.equals(getString(R.string.dietaenutri)) ||
                                            selecte.equals(getString(R.string.saudeebemestar)) ||
                                            selecte.equals(getString(R.string.estetica)) ||
                                            selecte.equals(getString(R.string.tatuagem)) ||
                                            selecte.equals("Lazer") ||
                                            selecte.equals(getString(R.string.culinaria))
                                    ){
                                        loadFilteredServices(selected);
                                    } else {
                                        loadFilteredProducts(selected);
                                    }
                                    buscaTv.setText(getString(R.string.filtradop)+" "+selecte);
                                }
                            }
                        })
                        .show();
            }
        });

        checkfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this, R.style.MyAlertDialogTheme);
                builder.setTitle(getString(R.string.cate))
                        .setItems(MemberList, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //get selected item
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
                                if (selected.equals("")){
                                    //load all
                                    loadSearchList(selected);
                                }
                                else {
                                    checkfilter.setVisibility(View.VISIBLE);
                                    Log.d(TAG, " => " + selected);
                                    Log.d(TAG, " => " + selecte);
                                    if(selecte.equals(getString(R.string.dietaenutri)) ||
                                            selecte.equals(getString(R.string.saudeebemestar)) ||
                                            selecte.equals(getString(R.string.estetica)) ||
                                            selecte.equals(getString(R.string.tatuagem)) ||
                                            selecte.equals(getString(R.string.turismo)) ||
                                            selecte.equals(getString(R.string.culinaria))
                                    ){
                                        loadFilteredServices(selected);
                                    } else {
                                        loadFilteredProducts(selected);
                                    }
                                    buscaTv.setText(getString(R.string.filtradop)+" "+selecte);

                                }
                            }
                        })
                        .show();
            }
        });

        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });

        imageGridButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                rv_search_grid.setVisibility(View.VISIBLE);
                rv_search_list.setVisibility(View.GONE);
                imageGridButton.setImageResource(R.drawable.ic_baseline_grid_view_24_green);
                imageListButton.setImageResource(R.drawable.ic_baseline_view_list_24);
            }
        });

        imageListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                rv_search_list.setVisibility(View.VISIBLE);
                rv_search_grid.setVisibility(View.GONE);
                imageListButton.setImageResource(R.drawable.ic_baseline_view_list_24_green);
                imageGridButton.setImageResource(R.drawable.ic_baseline_grid_view_24);
            }
        });

        floatingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    adapterProductSeller.getFilter().filter(s);
                    adapterListSearch.getFilter().filter(s);
                    adapterSearchGrid.getFilter().filter(s);
                    adapterSearchGrids.getFilter().filter(s);
                    adapterServicesGrid.getFilter().filter(s);
                    adapterServicesList.getFilter().filter(s);

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }


    public void testUserCurrent() {
        if ( firebaseAuth.getCurrentUser ( ) != null ) {
            floatingButton.setVisibility(GONE);
        } else {
            floatingButton.setVisibility(VISIBLE);
        }
    }

    public void  loadSearchList(final String selected1) {
        productList1 = new ArrayList<>();
        isLastItemReached = false;

        limit = 20;

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection("Promoções")
                .limit(limit)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        productList1.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList1.add(modelProduct);
                            }
                            adapterListSearch = new AdapterListSearch(SearchActivity.this, productList1);
                            rv_search_list.setAdapter(adapterListSearch);
                            layoutManager = new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false);
                            layoutManager.setStackFromEnd(false);
                            rv_search_list.setLayoutManager(layoutManager);

                            if(task.getResult().size()!=0) {
                                lastVisible = task.getResult().getDocuments()
                                        .get(task.getResult().size() - 1);
                            }

                            RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);

                                    if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                        isScrolling = true;
                                    }
                                }

                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);

                                    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                                    int visibleItemCount = layoutManager.getChildCount();
                                    int totalItemCount = layoutManager.getItemCount();

                                    while (isScrolling && (firstVisibleItem + visibleItemCount == totalItemCount) && !isLastItemReached) {

                                        isScrolling = false;
                                        limit += limit+6;

                                        Query nextQuery = FirebaseFirestore.getInstance().collection("Promoções")
                                                .startAfter(lastVisible)
                                                .limit(limit);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    productList1.add(modelProduct);
                                                    layoutManager = new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false);
                                                    layoutManager.setStackFromEnd(false);
                                                    rv_search_list.setLayoutManager(layoutManager);
                                                }

                                                adapterListSearch.notifyDataSetChanged();

                                                if(task.getResult().size() < 150) { //limit
                                                    isLastItemReached = true;
                                                }
                                            }
                                        });
                                    }

                                }
                            };
                            rv_search_list.addOnScrollListener(onScrollListener);

                        }
                    }
                });
    }


    public void loadSearchGrid(final String selected1) {
        productList2 = new ArrayList<>();
        isLastItemReached2 = false;

        limit2 = 35;

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection(selected1).limit(limit2)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        productList2.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList2.add(modelProduct);
                            }
                            adapterSearchGrid = new AdapterGridSearch(SearchActivity.this, productList2);
                            rv_search_grid.setAdapter(adapterSearchGrid);
                            layoutManager2 = new GridLayoutManager(SearchActivity.this, 3);
                            layoutManager2.setStackFromEnd(false);
                            rv_search_grid.setLayoutManager(layoutManager2);

                            if(task.getResult().size()!=0) {
                                lastVisible2 = task.getResult().getDocuments()
                                        .get(task.getResult().size() - 1);
                            }
                            RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);

                                    if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                        isScrolling2 = true;
                                    }
                                }

                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);

                                    int firstVisibleItem = layoutManager2.findFirstVisibleItemPosition();
                                    int visibleItemCount = layoutManager2.getChildCount();
                                    int totalItemCount = layoutManager2.getItemCount();

                                    while (isScrolling2 && (firstVisibleItem + visibleItemCount == totalItemCount) && !isLastItemReached2) {

                                        isScrolling2 = false;

                                        limit2 += limit2+15;

                                        Query nextQuery = FirebaseFirestore.getInstance().collection("Promoções")
                                                .startAfter(lastVisible2)
                                                .limit(limit2);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    productList2.add(modelProduct);
                                                    layoutManager2 = new GridLayoutManager(SearchActivity.this, 3);
                                                    layoutManager2.setStackFromEnd(false);
                                                    rv_search_grid.setLayoutManager(layoutManager2);
                                                }

                                                adapterSearchGrid.notifyDataSetChanged();

                                                if(task.getResult().size() < 300) { //limit
                                                    isLastItemReached2 = true;
                                                }
                                            }
                                        });
                                    }

                                }
                            };
                            rv_search_grid.addOnScrollListener(onScrollListener);

                        }
                    }
                });
    }

    private void loadFilteredProducts(String selected1) {
        productList = new ArrayList<>();
        isLastItemReached3 = false;

        limit3 = 25;

        //get all products
        FirebaseFirestore reef = FirebaseFirestore.getInstance();
        reef.collection(selected1)
                .limit(limit3)
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
                            }
                            adapterProductSeller = new AdapterListSearch(SearchActivity.this, productList);
                            rv_search_list.setAdapter(adapterProductSeller);
                            adapterSearchGrids = new AdapterGridSearch(SearchActivity.this, productList);
                            rv_search_grid.setAdapter(adapterSearchGrids);
                            layoutManager2 = new GridLayoutManager(SearchActivity.this, 3);
                            layoutManager2.setStackFromEnd(false);
                            rv_search_grid.setLayoutManager(layoutManager2);
                            layoutManager = new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false);
                            layoutManager.setStackFromEnd(false);
                            rv_search_list.setLayoutManager(layoutManager);
                            if(task.getResult().size()!=0) {
                                lastVisible3 = task.getResult().getDocuments()
                                        .get(task.getResult().size() - 1);
                            }
                            RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);

                                    if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                        isScrolling3 = true;
                                    }
                                }

                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);

                                    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                                    int visibleItemCount = layoutManager.getChildCount();
                                    int totalItemCount = layoutManager.getItemCount();

                                    while (isScrolling3 && (firstVisibleItem + visibleItemCount == totalItemCount) && !isLastItemReached3) {

                                        isScrolling3 = false;

                                        limit3 += limit3+15;

                                        Query nextQuery = FirebaseFirestore.getInstance().collection(selected1)
                                                .startAfter(lastVisible3)
                                                .limit(limit3);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    productList1.add(modelProduct);
                                                    layoutManager2 = new GridLayoutManager(SearchActivity.this, 3);
                                                    layoutManager2.setStackFromEnd(false);
                                                    rv_search_grid.setLayoutManager(layoutManager2);
                                                    layoutManager = new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false);
                                                    layoutManager.setStackFromEnd(false);
                                                    rv_search_list.setLayoutManager(layoutManager);
                                                }

                                                adapterProductSeller.notifyDataSetChanged();
                                                adapterSearchGrids.notifyDataSetChanged();

                                                if(task.getResult().size() < 500) { //limit
                                                    isLastItemReached3 = true;
                                                }
                                            }
                                        });
                                    }

                                }
                            };
                            rv_search_list.addOnScrollListener(onScrollListener);
                            rv_search_grid.addOnScrollListener(onScrollListener);
                        }
                    }
                });
    }

    private void loadFilteredServices(String selected1) {
        productList0 = new ArrayList<>();
        isLastItemReached4 = false;

        limit4 = 25;

        FirebaseFirestore reef = FirebaseFirestore.getInstance();
        reef.collection("Adm's")
                .whereEqualTo("categories",selected1)
                .limit(limit4)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        productList0.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList0.add(modelProduct);
                            }
                            adapterServicesList = new AdapterServicesList(SearchActivity.this, productList0);
                            rv_search_list.setAdapter(adapterServicesList);
                            adapterServicesGrid = new AdapterServicesGrid(SearchActivity.this, productList0);
                            rv_search_grid.setAdapter(adapterServicesGrid);
                            layoutManager2 = new GridLayoutManager(SearchActivity.this, 3);
                            layoutManager2.setStackFromEnd(false);
                            rv_search_grid.setLayoutManager(layoutManager2);
                            layoutManager = new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false);
                            layoutManager.setStackFromEnd(false);
                            rv_search_list.setLayoutManager(layoutManager);
                            if(task.getResult().size()!=0) {
                                lastVisible4 = task.getResult().getDocuments()
                                    .get(task.getResult().size() -1);}

                            RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);

                                    if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                        isScrolling4 = true;
                                    }
                                }

                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);

                                    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                                    int visibleItemCount = layoutManager.getChildCount();
                                    int totalItemCount = layoutManager.getItemCount();

                                    while (isScrolling4 && (firstVisibleItem + visibleItemCount == totalItemCount) && !isLastItemReached4) {

                                        isScrolling4 = false;

                                        limit4 += limit4+15;

                                        Query nextQuery = FirebaseFirestore.getInstance().collection("Adm's")
                                                .whereEqualTo("categories",selected1)
                                                .startAfter(lastVisible4)
                                                .limit(limit4);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    productList0.add(modelProduct);
                                                    layoutManager2 = new GridLayoutManager(SearchActivity.this, 3);
                                                    layoutManager2.setStackFromEnd(false);
                                                    rv_search_grid.setLayoutManager(layoutManager2);
                                                    layoutManager = new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false);
                                                    layoutManager.setStackFromEnd(false);
                                                    rv_search_list.setLayoutManager(layoutManager);
                                                }

                                                adapterServicesList.notifyDataSetChanged();
                                                adapterServicesGrid.notifyDataSetChanged();

                                                if(task.getResult().size() < 500) { //limit
                                                    isLastItemReached4 = true;
                                                }
                                            }
                                        });
                                    }

                                }
                            };
                            rv_search_list.addOnScrollListener(onScrollListener);
                            rv_search_grid.addOnScrollListener(onScrollListener);
                        }
                    }
                });
    }

    public void onBackPressed ( ) {
        finish ( );
    }

}
