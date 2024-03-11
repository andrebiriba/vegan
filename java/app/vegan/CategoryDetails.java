package app.vegan;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class CategoryDetails extends AppCompatActivity implements Serializable {

    private int limit0, limit, limit2;
    private LinearLayoutManager layoutManager;
    private GridLayoutManager layoutManager2;
    private boolean isLastItemReached0, isLastItemReached, isLastItemReached2;
    private boolean isScrolling0, isScrolling, isScrolling2;
    private DocumentSnapshot lastVisible0, lastVisible, lastVisible2;
    private FirebaseAuth firebaseAuth;
    private ImageButton imageButtonBack, imageGridButton, imageListButton;
    RecyclerView rv_category_grid, rv_category_list;
    private ArrayList<ModelProduct> productList1;
    private AdapterGridSearch adapterSearchGrid;
    private AdapterCategories adapterCategories;
    private AdapterServicesList adapterServiceslist;
    private AdapterServicesGrid adapterServicesGrid;
    private TextView title, subtitle;
    private CardView floatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

        title = findViewById(R.id.textView46);
        subtitle = findViewById(R.id.subtitle);
        rv_category_list = findViewById(R.id.rvcatdetails);
        rv_category_grid = findViewById(R.id.rvcatdetailsgrid);
        imageButtonBack = findViewById(R.id.backBtnCategory);
        imageGridButton = findViewById(R.id.imageViewGridButton2);
        imageListButton = findViewById(R.id.imageViewListButton2);
        floatingButton = findViewById(R.id.floatingButton);

        Intent data = getIntent();
        String type = data.getStringExtra("type");
        String category = data.getStringExtra("category");

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
                getString(R.string.estetica),
                getString(R.string.dietaenutri),
                getString(R.string.saudeebemestar),
                getString(R.string.cabeleireiras),
                getString(R.string.tatuagem),
                getString(R.string.turismo),
                getString(R.string.culinaria),
                getString(R.string.mais)};

        String[] Portugues = {
                "Promoções",
                "Almoço",
                "Lanches",
                "Jantar",
                "Bebidas",
                "Sobremesas",
                "Mercado",
                "Saúde",
                "Higiene",
                "Cosméticos",
                "Fitness",
                "Mãe &amp; Bebê",
                "Farmácia Natural",
                "Brechó",
                "Roupas",
                "Acessórios",
                "Calçados",
                "Peça Íntima",
                "Praia",
                "Casa",
                "Animal Não-Humano",
                "Limpeza",
                "Eco Friendly",
                "Jardim",
                "Livros",
                "Estética",
                "Dieta &amp; Nutrição",
                "Saúde &amp; Bem-Estar",
                "Cabeleireiras &amp; Barbeiros",
                "Tatuagem",
                "Turismo &amp; Lazer",
                "Culinária",
                "Mais"};

        if(Arrays.asList(Portugues).contains(category)){
           int position = Arrays.asList(Portugues).indexOf(category);
            String selected = MemberList[position];
            title.setText(selected);
        }

        String[] MemberL = {
                getString(R.string.diet),
                getString(R.string.pharmacy),
                getString(R.string.closet),
                getString(R.string.others),
                getString(R.string.services)};

        String[] Ingles = {
                "Diet",
                "Pharmacy",
                "Closet",
                "Others",
                "Services"};

        if(Arrays.asList(Ingles).contains(type)){
            int position = Arrays.asList(Ingles).indexOf(type);
            String selected = MemberL[position];
            subtitle.setText(selected);
        }

        firebaseAuth = FirebaseAuth.getInstance();
        testUserCurrent();

        if(Objects.equals(category, "Destaques")){
            loadDestaquesList(type);
            loadDestaquesGrid(type);
        } else {
            loadCategoryList(category, type);
            loadCategoryGrid(category, type);
        }
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });

        floatingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryDetails.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        imageGridButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                rv_category_grid.setVisibility(View.VISIBLE);
                rv_category_list.setVisibility(View.GONE);
                imageGridButton.setImageResource(R.drawable.ic_baseline_grid_view_24_green);
                imageListButton.setImageResource(R.drawable.ic_baseline_view_list_24);
            }
        });

        imageListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                rv_category_list.setVisibility(View.VISIBLE);
                rv_category_grid.setVisibility(View.GONE);
                imageListButton.setImageResource(R.drawable.ic_baseline_view_list_24_green);
                imageGridButton.setImageResource(R.drawable.ic_baseline_grid_view_24);
            }
        });
    }

    public void loadDestaquesList(final String filter97) {
        productList1 = new ArrayList<>();
        isLastItemReached0 = false;
        limit0 = 12;
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection(filter97)
                .orderBy("likes", Query.Direction.DESCENDING)
                .limit(limit0)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        productList1.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList1.add(modelProduct);

                                adapterServiceslist = new AdapterServicesList(CategoryDetails.this, productList1);
                                rv_category_list.setAdapter(adapterServiceslist);
                            }
                            layoutManager = new LinearLayoutManager(CategoryDetails.this, LinearLayoutManager.VERTICAL, true);
                            layoutManager.setStackFromEnd(true);
                            rv_category_list.setLayoutManager(layoutManager);
                            if(task.getResult().size()!=0) {
                                lastVisible0 = task.getResult().getDocuments()
                                        .get(task.getResult().size() - 1);
                            }
                            RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);

                                    if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                        isScrolling0 = true;
                                    }
                                }

                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);

                                    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                                    int visibleItemCount = layoutManager.getChildCount();
                                    int totalItemCount = layoutManager.getItemCount();

                                    while (isScrolling0 && (firstVisibleItem + visibleItemCount == totalItemCount) && !isLastItemReached0) {

                                        isScrolling0 = false;
                                        limit0 += limit0+6;

                                        Query nextQuery = FirebaseFirestore.getInstance().collection(filter97)
                                                .orderBy("likes", Query.Direction.DESCENDING)
                                                .startAfter(lastVisible0)
                                                .limit(limit0);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    productList1.add(modelProduct);
                                                    layoutManager = new LinearLayoutManager(CategoryDetails.this, LinearLayoutManager.VERTICAL, false);
                                                    layoutManager.setStackFromEnd(false);
                                                    rv_category_list.setLayoutManager(layoutManager);
                                                }

                                                adapterServiceslist.notifyDataSetChanged();

                                                if(task.getResult().size() < 150) { //limit
                                                    isLastItemReached0 = true;
                                                }
                                            }
                                        });
                                    }
                                }
                            };
                            rv_category_list.addOnScrollListener(onScrollListener);
                        }
                    }
                });
    }


    public void loadDestaquesGrid(final String filter97) {
        productList1 = new ArrayList<>();
        isLastItemReached2 = false;
        limit2 = 32;
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection(filter97)
                .orderBy("likes", Query.Direction.DESCENDING)
                .limit(limit2)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        productList1.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList1.add(modelProduct);

                                adapterServicesGrid = new AdapterServicesGrid(CategoryDetails.this, productList1);
                                rv_category_grid.setAdapter(adapterServicesGrid);
                            }
                            layoutManager2 = new GridLayoutManager(CategoryDetails.this, 3);
                            rv_category_grid.setLayoutManager(layoutManager2);

                            lastVisible2 = task.getResult().getDocuments()
                                    .get(task.getResult().size() -1);

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
                                        limit2 += limit2+6;

                                        Query nextQuery = FirebaseFirestore.getInstance().collection(filter97)
                                                .orderBy("likes", Query.Direction.DESCENDING)
                                                .startAfter(lastVisible2)
                                                .limit(limit2);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    productList1.add(modelProduct);
                                                    layoutManager2 = new GridLayoutManager(CategoryDetails.this, 3);
                                                    layoutManager2.setStackFromEnd(false);
                                                    rv_category_grid.setLayoutManager(layoutManager2);
                                                }

                                                adapterServicesGrid.notifyDataSetChanged();

                                                if(task.getResult().size() < 300) { //limit
                                                    isLastItemReached2 = true;
                                                }
                                            }
                                        });
                                    }
                                }
                            };
                            rv_category_grid.addOnScrollListener(onScrollListener);
                        }
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

    public void loadCategoryList(final String selected1, final String filter) {
        productList1 = new ArrayList<>();
        isLastItemReached = false;

        limit = 12;
        if(Objects.equals(filter, "Services")){

            FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
            rootRef.collection("Adm's")
                    .whereEqualTo("categories", selected1)
                    .limit(limit)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                    productList1.add(modelProduct);
                                    adapterServiceslist = new AdapterServicesList(CategoryDetails.this, productList1);
                                    rv_category_list.setAdapter(adapterServiceslist);
                                }
                                layoutManager = new LinearLayoutManager(CategoryDetails.this, LinearLayoutManager.VERTICAL, true);
                                layoutManager.setStackFromEnd(true);
                                rv_category_list.setLayoutManager(layoutManager);

                                if(task.getResult().size()!=0) {
                                    lastVisible = task.getResult().getDocuments()
                                            .get(task.getResult().size() - 1);
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
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

                                        limit += limit+5;

                                        Query nextQuery = FirebaseFirestore.getInstance().collection(selected1)
                                                .whereEqualTo("productCategory",selected1)
                                                .startAfter(lastVisible)
                                                .limit(limit);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    productList1.add(modelProduct);
                                                    layoutManager = new LinearLayoutManager(CategoryDetails.this, LinearLayoutManager.VERTICAL, false);
                                                    layoutManager.setStackFromEnd(false);
                                                    rv_category_list.setLayoutManager(layoutManager);
                                                }

                                                adapterServiceslist.notifyDataSetChanged();

                                                if(task.getResult().size() < 300) { //limit
                                                    isLastItemReached = true;
                                                }
                                            }
                                        });
                                    }
                                }
                            };
                            rv_category_list.addOnScrollListener(onScrollListener);
                        }
                    });

        } else{

            FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
            rootRef.collection(selected1)
                    .whereEqualTo("productType", filter)
                    .whereEqualTo("productCategory",selected1)
                    .limit(limit)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                    productList1.add(modelProduct);
                                    adapterCategories = new AdapterCategories(CategoryDetails.this, productList1);
                                    rv_category_list.setAdapter(adapterCategories);
                                }
                                layoutManager = new LinearLayoutManager(CategoryDetails.this, LinearLayoutManager.VERTICAL, true);
                                layoutManager.setStackFromEnd(true);
                                rv_category_list.setLayoutManager(layoutManager);
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
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

                                        limit += limit+3;

                                        Query nextQuery = FirebaseFirestore.getInstance().collection(selected1).whereEqualTo("productType", filter)
                                                .whereEqualTo("productCategory",selected1)
                                                .startAfter(lastVisible)
                                                .limit(limit);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    productList1.add(modelProduct);
                                                    layoutManager = new LinearLayoutManager(CategoryDetails.this, LinearLayoutManager.VERTICAL, false);
                                                    layoutManager.setStackFromEnd(false);
                                                    rv_category_list.setLayoutManager(layoutManager);
                                                }

                                                adapterCategories.notifyDataSetChanged();

                                                if(task.getResult().size() < 300) { //limit
                                                    isLastItemReached = true;
                                                }
                                            }
                                        });
                                    }
                                }
                            };
                            rv_category_list.addOnScrollListener(onScrollListener);
                        }
                    });
        }
    }

    public void loadCategoryGrid(final String selected1, final String filter) {
        productList1 = new ArrayList<>();
        isLastItemReached2 = false;

        limit2 = 32;
        if(Objects.equals(filter, "Services")){

            FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
            rootRef.collection("Adm's")
                    .whereEqualTo("categories", selected1)
                    .limit(limit2)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                    productList1.add(modelProduct);
                                    adapterServicesGrid = new AdapterServicesGrid(CategoryDetails.this, productList1);
                                    rv_category_grid.setAdapter(adapterServicesGrid);
                                }
                                layoutManager2 = new GridLayoutManager(CategoryDetails.this, 3);
                                rv_category_grid.setLayoutManager(layoutManager2);

                                if(task.getResult().size()!=0) {
                                    lastVisible2 = task.getResult().getDocuments()
                                            .get(task.getResult().size() - 1);
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
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

                                        limit2 += limit2+5;

                                        Query nextQuery = FirebaseFirestore.getInstance().collection(selected1)
                                                .whereEqualTo("productCategory",selected1)
                                                .startAfter(lastVisible2)
                                                .limit(limit2);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    productList1.add(modelProduct);
                                                    layoutManager2 = new GridLayoutManager(CategoryDetails.this, 3);
                                                    layoutManager2.setStackFromEnd(false);
                                                    rv_category_grid.setLayoutManager(layoutManager2);
                                                }

                                                adapterServicesGrid.notifyDataSetChanged();

                                                if(task.getResult().size() < 300) { //limit
                                                    isLastItemReached2 = true;
                                                }
                                            }
                                        });
                                    }
                                }
                            };
                            rv_category_grid.addOnScrollListener(onScrollListener);
                        }
                    });

        } else{

            FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
            rootRef.collection(selected1)
                    .whereEqualTo("productType", filter)
                    .whereEqualTo("productCategory",selected1)
                    .limit(limit2)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                    productList1.add(modelProduct);
                                    adapterSearchGrid = new AdapterGridSearch(CategoryDetails.this, productList1);
                                    rv_category_grid.setAdapter(adapterSearchGrid);
                                }
                                layoutManager2 = new GridLayoutManager(CategoryDetails.this, 3);
                                rv_category_grid.setLayoutManager(layoutManager2);
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
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

                                        limit2 += limit2+3;

                                        Query nextQuery = FirebaseFirestore.getInstance().collection(selected1).whereEqualTo("productType", filter)
                                                .whereEqualTo("productCategory",selected1)
                                                .startAfter(lastVisible2)
                                                .limit(limit2);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    productList1.add(modelProduct);
                                                    layoutManager2 = new GridLayoutManager(CategoryDetails.this, 3);
                                                    rv_category_grid.setLayoutManager(layoutManager2);
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
                            rv_category_grid.addOnScrollListener(onScrollListener);
                        }
                    });
        }
    }

        public void onBackPressed ( ) {
        finish ( );
    }
}
