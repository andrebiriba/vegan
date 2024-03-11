package app.vegan;

import static app.vegan.R.drawable.error;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import pl.droidsonroids.gif.GifImageView;

public class Outros extends AppCompatActivity implements GestureDetector.OnGestureListener {

    public AppUpdateManager appUpdateManager;
    private static final int MY_REQUEST_CODE = 100;
    private int limit0, limit, limit2, limit3, limit4, limit5, limit6, limit7, limit8, limit9, limit10, limit11, limit12, limit13,
            limit14, limit15, limit16, limit17;
    private LinearLayoutManager layoutManager;
    private boolean isLastItemReached0, isLastItemReached, isLastItemReached2, isLastItemReached3, isLastItemReached4, isLastItemReached5,
            isLastItemReached6, isLastItemReached7, isLastItemReached8, isLastItemReached9, isLastItemReached10, isLastItemReached11, isLastItemReached12,
            isLastItemReached13, isLastItemReached14, isLastItemReached15, isLastItemReached16, isLastItemReached17;
    private boolean isScrolling0, isScrolling, isScrolling2, isScrolling3, isScrolling4, isScrolling5, isScrolling6, isScrolling7, isScrolling8,
            isScrolling9, isScrolling10, isScrolling11, isScrolling12, isScrolling13, isScrolling14, isScrolling15, isScrolling16, isScrolling17,
    notload;
    private DocumentSnapshot lastVisible0, lastVisible, lastVisible2, lastVisible3, lastVisible4, lastVisible5, lastVisible6, lastVisible7,
            lastVisible8,lastVisible9, lastVisible10, lastVisible11, lastVisible12, lastVisible13, lastVisible14, lastVisible15, lastVisible16, lastVisible17;
    private ShapeableImageView cate3, cate4, cate5, cate6, cate7, cate8;
    private ConstraintLayout clcategories;
    private BottomNavigationView bottomNavView;
    private ViewPager2 viewPager2;
    private TabLayout tablayout;
    FirebaseAuth fAuth;
    private ShapeableImageView profileimg, profileimgmini;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    private View header;
    private FirebaseAuth firebaseAuth;
    private ArrayList<ModelLocal> localList;
    private ArrayList<ModelProduct> productList, productList1, productList2, productList6, productList9, productList13,
            productList17, productList22, productList26, productLi4, productList27, productList28, productList29,
            productList30, productList31, productList32, productList33, productList34, productList35, productList36, productList37, productList38,
            productList39, productList40, productList41, productList42, productList43, productList44, productList45, productList46, productList47;
    private ArrayList<ModelProduct> serviceList2;
    private ArrayList<ModelProduct> serviceList3;
    private ArrayList<ModelProduct> serviceList4;
    private ArrayList<ModelProduct> serviceList5;
    private ArrayList<ModelProduct> serviceList6;
    private ArrayList<ModelProduct> serviceList7;
    private ArrayList<ModelProduct> serviceList8;
    private ArrayList<ModelProduct> serviceList9;
    private ArrayList<ModelProduct> serviceList10;
    private RecyclerView recyclerviewHighLights, promotionRv, lunchRv, snacksRv, dinnerRv, drinksRv, desertsRv, marketRv, moreRv, brandsRv;
    private AdapterProductBig adapterProductBig;
    private AdapterLocal adapterLocal;
    private AdapterPromotions adapterPromotions;
    private AdapterServices adapterServices;
    private AdapterServicesHigh adapterServicesHigh;
    private String shopUid, tabPosition, bottomNPosition, city, state, country, token, titleP, textP;
    private Context context;
    private TextView ctg3, ctg4, ctg5, ctg6, ctg7, ctg8;
    private CardView floatingButtonCard;
    private EditText searchTv;
    private TextView txttitle0, txt_title, txt_title1, txt_title2, txt_titleDinner, txt_titleDrinks, txt_titleDesserts, txt_titleMarket,
            txt_titleMore, embreve;
    private ImageView highlightarrow, icon_arrow_promotions, icon_arrow1, icon_arrow2, icon_arrowDinner, icon_arrowDrinks, icon_arrowDesserts,
            icon_arrowMarket, icon_arrowMore, closeAd, anuncio;
    private ImageButton cardCateg2, cardCateg3, cardCateg4, cardCateg5, cardCateg6, cardCateg7, cardCateg8, cardCateg9;
    FirebaseFirestore fStore;
    private ImageSlider carouselView;
    private String type, category, ad2, goad1, goad2, goad3, goad4, ad3, adPopUp, ad4, ad1, goadPopUp;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NestedScrollView nested;
    private SharedPreferences justOpenedApp = null;
    private static final String TAG = "Swipe Position";
    private float x1, x2, y1, y2;
    private static int MIN_DISTANCE = 200;
    private TabLayout tabLayout;
    private GifImageView load;
    static boolean hasOpened = false;
    private FirebaseAnalytics mFirebaseAnalytics;

    private ProgressBar progressDialog;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outros);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        progressDialog = findViewById(R.id.progressBar4);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        load = findViewById(R.id.load);
        tabLayout = findViewById(R.id.tabLayout);
        embreve = findViewById(R.id.embreve);
        cardCateg2 = findViewById(R.id.card2);
        cardCateg3 = findViewById(R.id.card3);
        cardCateg4 = findViewById(R.id.card4);
        cardCateg5 = findViewById(R.id.card5);
        cardCateg6 = findViewById(R.id.card6);
        cardCateg7 = findViewById(R.id.card7);
        cardCateg8 = findViewById(R.id.card8);
        cardCateg9 = findViewById(R.id.card9);
        brandsRv = findViewById(R.id.brandsRv);
        clcategories = findViewById(R.id.clcategories);
        cate3 = findViewById(R.id.imageView50);
        cate4 = findViewById(R.id.imageView56);
        cate5 = findViewById(R.id.imageView51);
        cate6 = findViewById(R.id.imageView94);
        cate7 = findViewById(R.id.imageView55);
        cate8 = findViewById(R.id.imagev);
        ctg3 = findViewById(R.id.txtcat3);
        ctg4 = findViewById(R.id.txtcat4);
        ctg5 = findViewById(R.id.txtcat5);
        ctg6 = findViewById(R.id.txtcat6);
        ctg7 = findViewById(R.id.txtcat7);
        ctg8 = findViewById(R.id.txtcat8);
        searchTv = findViewById(R.id.searchTv);
        bottomNavView = findViewById(R.id.bottomNavView);
        viewPager2 = findViewById(R.id.frag_view_pager);
        tablayout = findViewById(R.id.tab_layout);
        floatingButtonCard = findViewById(R.id.floatingButton2);
        profileimg = findViewById(R.id.profileimg);
        profileimgmini = findViewById(R.id.profileimgmini);
        txt_title = findViewById(R.id.txt_title);
        txt_title1 = findViewById(R.id.txt_title1);
        txt_title2 = findViewById(R.id.txt_title2);
        txt_titleDinner = findViewById(R.id.txt_titleDinner);
        txt_titleDrinks = findViewById(R.id.txt_titleDrinks);
        txt_titleDesserts = findViewById(R.id.txt_titleDesserts);
        txt_titleMarket = findViewById(R.id.txt_titleMarket);
        txt_titleMore = findViewById(R.id.txt_titleMore);
        icon_arrow_promotions = findViewById(R.id.icon_arrow_promotions);
        icon_arrow1 = findViewById(R.id.icon_arrow1);
        icon_arrow2 = findViewById(R.id.icon_arrow2);
        icon_arrowDinner = findViewById(R.id.icon_arrowDinner);
        icon_arrowDrinks = findViewById(R.id.icon_arrowDrinks);
        icon_arrowDesserts = findViewById(R.id.icon_arrowDesserts);
        icon_arrowMarket = findViewById(R.id.icon_arrowMarket);
        icon_arrowMore = findViewById(R.id.icon_arrowMore);
        txttitle0 = findViewById(R.id.txt_title0);
        highlightarrow = findViewById(R.id.icon_arrow_highlights);
        header = findViewById(R.id.header);
        recyclerviewHighLights = findViewById(R.id.recyclerviewHighLights);
        promotionRv  = findViewById(R.id.promotionRv);
        lunchRv = findViewById(R.id.lunchRv);
        snacksRv = findViewById(R.id.snacksRv);
        dinnerRv = findViewById(R.id.dinnerRv);
        drinksRv = findViewById(R.id.drinksRv);
        desertsRv = findViewById(R.id.desertsRv);
        marketRv = findViewById(R.id.marketRv);
        moreRv = findViewById(R.id.moreRv);
        carouselView  = findViewById(R.id.carousel_view);
        anuncio = findViewById(R.id.imagepop);
        closeAd = findViewById(R.id.close);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        nested = findViewById(R.id.nested);

        boolean statuInternet = isOnline ( this );

        if (!statuInternet) {
            alertOffline ( );
            Toast.makeText(Outros.this, R.string.falhananet, Toast.LENGTH_SHORT).show();
        } else {

            this.firebaseAuth = FirebaseAuth.getInstance();
        }


        checkForAppUpdate();
        checkForAppSparks();

        shopUid = getIntent().getStringExtra("shopUid");
        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(brandsRv.getContext(),
                R.anim.layout_fall_down);
        brandsRv.setLayoutAnimation(controller);

        //controller1 dosent work
        LayoutAnimationController controller1 = AnimationUtils.loadLayoutAnimation(promotionRv.getContext(),
                R.anim.layout_fall_down);
        promotionRv.setLayoutAnimation(controller1);


        FirebaseApp.initializeApp(Outros.this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                PlayIntegrityAppCheckProviderFactory.getInstance());


        hideArrows();
        testUserCurrent();

        if(Objects.equals(city, "") |city==null) {
            city = getString(R.string.delivery);
        }
        tabLayout.addTab(tabLayout.newTab().setText(R.string.fy));
        tabLayout.isSelected();
        tabLayout.addTab(tabLayout.newTab().setText(city));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.mai));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.impo));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.novidades));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.bran));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.cate));

        new Handler().postDelayed(
                new Runnable(){
                    @Override
                    public void run() {
                        tabLayout.getTabAt(4).select();
                        tabLayout.getTabAt(0).select();                    }
                }, 100);

        tabPosition = "0";

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tabLayout.getSelectedTabPosition() == 0){
                    tabPosition = "0";
                    if(Objects.equals(bottomNPosition, "0")){
                        dietNews();
                        clcategories.setVisibility(View.GONE);
                    } else
                    if(Objects.equals(bottomNPosition, "1")){
                        pharmacyNews();
                        clcategories.setVisibility(View.GONE);

                    } else
                    if(Objects.equals(bottomNPosition, "2")){
                        closetNews();
                        clcategories.setVisibility(View.GONE);

                    } else
                    if(Objects.equals(bottomNPosition, "3")){
                        moreNews();
                        clcategories.setVisibility(View.GONE);

                    } else
                    if(Objects.equals(bottomNPosition, "4")){
                        servicesNews();
                        clcategories.setVisibility(View.GONE);

                    }
                }else if(tabLayout.getSelectedTabPosition() == 1){
                    tabPosition = "1";
                    if(Objects.equals(bottomNPosition, "0")){
                        dietNews();
                        clcategories.setVisibility(View.GONE);

                    } else
                    if(Objects.equals(bottomNPosition, "1")){
                        pharmacyNews();
                        clcategories.setVisibility(View.GONE);

                    } else
                    if(Objects.equals(bottomNPosition, "2")){
                        closetNews();
                        clcategories.setVisibility(View.GONE);

                    } else
                    if(Objects.equals(bottomNPosition, "3")){
                        moreNews();
                        clcategories.setVisibility(View.GONE);

                    } else
                    if(Objects.equals(bottomNPosition, "4")){
                        servicesNews();
                        clcategories.setVisibility(View.GONE);

                    }
                }else if(tabLayout.getSelectedTabPosition() == 2){
                    tabPosition = "2";
                    if(Objects.equals(bottomNPosition, "0")){
                        dietMail();
                    } else
                    if(Objects.equals(bottomNPosition, "1")){
                        pharmacyMail();
                    } else
                    if(Objects.equals(bottomNPosition, "2")){
                        closetMail();
                    } else
                    if(Objects.equals(bottomNPosition, "3")){
                        moreMail();
                    } else
                    if(Objects.equals(bottomNPosition, "4")){
                        servicesMail();
                    }
                }else if(tabLayout.getSelectedTabPosition() == 3){
                    tabPosition = "3";
                    if(Objects.equals(bottomNPosition, "0")){
                        dietImported();
                    } else
                    if(Objects.equals(bottomNPosition, "1")){
                        pharmacyImported();
                    } else
                    if(Objects.equals(bottomNPosition, "2")){
                        closetImported();
                    } else
                    if(Objects.equals(bottomNPosition, "3")){
                        moreImported();
                    } else
                    if(Objects.equals(bottomNPosition, "4")){
                        servicesImported();
                    }
                }else if(tabLayout.getSelectedTabPosition() == 4){
                    tabPosition = "4";
                    if(Objects.equals(bottomNPosition, "0")){
                        dietNews();
                    } else
                    if(Objects.equals(bottomNPosition, "1")){
                        pharmacyNews();
                    } else
                    if(Objects.equals(bottomNPosition, "2")){
                        closetNews();
                    } else
                    if(Objects.equals(bottomNPosition, "3")){
                        moreNews();
                    } else
                    if(Objects.equals(bottomNPosition, "4")){
                        servicesNews();
                    }
                }else if(tabLayout.getSelectedTabPosition() == 5){
                    tabPosition = "5";
                    if(Objects.equals(bottomNPosition, "0")){
                        carouselView.setVisibility(View.GONE);
                        loadAllBrands("Diet",city, state, country);
                    } else
                    if(Objects.equals(bottomNPosition, "1")){
                        carouselView.setVisibility(View.GONE);
                        loadAllBrands("Pharmacy",city, state, country);
                    } else
                    if(Objects.equals(bottomNPosition, "2")){
                        carouselView.setVisibility(View.GONE);
                        loadAllBrands("Closet",city, state, country);
                    } else
                    if(Objects.equals(bottomNPosition, "3")){
                        carouselView.setVisibility(View.GONE);
                        loadAllBrands("Others",city, state, country);
                    } else
                    if(Objects.equals(bottomNPosition, "4")){
                        carouselView.setVisibility(View.GONE);
                        loadAllBrands("Services",city, state, country);
                    }
                }else if(tabLayout.getSelectedTabPosition() == 6){
                    tabPosition = "6";
                    if(Objects.equals(bottomNPosition, "0")){
                        embreve.setVisibility(View.GONE);
                        categoriesDiet();
                    } else
                    if(Objects.equals(bottomNPosition, "1")){
                        embreve.setVisibility(View.GONE);
                        categoriesPharmacy();
                    } else
                    if(Objects.equals(bottomNPosition, "2")){
                        embreve.setVisibility(View.GONE);
                        categoriesCloset();
                    } else
                    if(Objects.equals(bottomNPosition, "3")){
                        embreve.setVisibility(View.GONE);
                        categoriesMore();
                    } else
                    if(Objects.equals(bottomNPosition, "4")){
                        embreve.setVisibility(View.GONE);
                        categoriesServices();
                    }
                }}

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.getTabAt(0).select();

        justOpenedApp = getSharedPreferences("app.vegan", MODE_PRIVATE);

        Ads();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        NavigationView navigationView = findViewById(R.id.navigationView);

        View view = findViewById(R.id.view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationView.setVisibility(View.GONE);
            }
        });

        floatingButtonCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Outros.this, LoginActivity.class);
                startActivity(intent);
            }
        });

            View view2 = findViewById(R.id.view);

            view2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view2.setVisibility(View.GONE);
                    navigationView.setVisibility(View.GONE);
                }
            });

        ImageButton menus = findViewById(R.id.menu);

        menus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                navigationView.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
            }
        });

        View header = navigationView.getHeaderView(0);
        ShapeableImageView profileimg = header.findViewById(R.id.profileimg);
        TextView location = header.findViewById(R.id.location);
        TextView nameUser = header.findViewById(R.id.nameUser);

        header.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(Outros.this, Profile.class);
                startActivity(intentLoadNewActivity);
            }
        });

        profileimg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(Outros.this, Profile.class);
                startActivity(intentLoadNewActivity);
            }
        });

        location.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(Outros.this, Profile.class);
                startActivity(intentLoadNewActivity);
            }
        });

        nameUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(Outros.this, Profile.class);
                startActivity(intentLoadNewActivity);
            }
        });

        navigationView.setNavigationItemSelectedListener
                (new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        final int id1 = R.id.shopkeeper;
                        final int id2 = R.id.go_vegan;
                        final int id3 = R.id.who_we_are;
                        final int id4 = R.id.settings;
                        final int id5 = R.id.activist;
                        final int id8 = R.id.chat;

                        switch (menuItem.getItemId())
                        {
                            case id1:
                                Intent intent10 = new Intent(Outros.this, LoginAdm.class);
                                startActivity(intent10);
                                break;
                            case id2:
                                Intent intent1 = new Intent ( Outros.this, GoVegan.class );
                                ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation ( getApplicationContext ( ),
                                        R.anim.slide_in_left, R.anim.slide_out_left );
                                ActivityCompat.startActivity ( Outros.this, intent1, actcompat.toBundle ( ) );
                                    break;
                            case id3:
                                Intent intent2 = new Intent(Outros.this, Aboutus.class);
                                startActivity(intent2);
                                break;
                            case id4:
                                if(FirebaseAuth.getInstance().getCurrentUser() != null){
                                    Intent intent3 = new Intent(Outros.this, SettingsActivity.class);
                                    startActivity(intent3);} else {
                                    Toast.makeText(Outros.this, R.string.entreoucadastre, Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case id5:
                                Intent intent6 = new Intent(Outros.this, ActivismActivity.class);
                                startActivity(intent6);
                                break;
                            case id8:
                                if(FirebaseAuth.getInstance().getCurrentUser() != null){
                                Intent intent7 = new Intent(Outros.this, AllChatsActivity.class);
                                startActivity(intent7);} else {
                                    Toast.makeText(Outros.this, R.string.entreoucadastre, Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                        return true;
                    }
                });

        Menu menu = navigationView.getMenu();
        MenuItem user = menu.findItem(R.id.user);
        MenuItem partner = menu.findItem(R.id.partner);
        SpannableString d = new SpannableString(partner.getTitle());
        d.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, d.length(), 0);
        partner.setTitle(d);
        SpannableString s = new SpannableString(user.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s.length(), 0);
        user.setTitle(s);

            profileimgmini.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intentLoadNewActivity = new Intent(Outros.this, Profile.class);
                    startActivity(intentLoadNewActivity);
                }
            });


        cardCateg2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                category = "1";
                loadCategories(category);
            }
        });

        cardCateg3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                category = "2";
                loadCategories(category);
            }
        });

        cardCateg4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                category = "3";
                loadCategories(category);
            }
        });

        cardCateg5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                category = "4";
                loadCategories(category);
            }
        });

        cardCateg6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                category = "5";
                loadCategories(category);
            }
        });

        cardCateg7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                category = "6";
                loadCategories(category);
            }
        });

        cardCateg8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                category = "7";
                loadCategories(category);
            }
        });

        cardCateg9.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                category = "8";
                loadCategories(category);
            }
        });

        txt_titleMore.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                category = "8";
                loadCategories(category);
            }
        });

        txt_titleMarket.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                category = "7";
                loadCategories(category);
            }
        });

        txt_titleDesserts.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                category = "6";
                loadCategories(category);
            }
        });

        txt_titleDrinks.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                category = "5";
                loadCategories(category);
            }
        });

        txt_titleDinner.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                category = "4";
                loadCategories(category);
            }
        });

        txt_title2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                category = "3";
                loadCategories(category);
            }
        });

        txttitle0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
              //  category = "0";
              //  loadCategories(category);
            }
        });

        txt_title.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                category = "1";
                loadCategories(category);
            }
        });

        txt_title1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                category = "2";
                loadCategories(category);
            }
        });

        highlightarrow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            //    category = "0";
            //    loadCategories(category);
            }
        });

        icon_arrow_promotions.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                category = "1";
                loadCategories(category);
            }
        });

        icon_arrow1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                category = "2";
                loadCategories(category);
            }
        });

        icon_arrow2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                category = "3";
                loadCategories(category);
            }
        });

        icon_arrowDinner.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                category = "4";
                loadCategories(category);
            }
        });

        icon_arrowDrinks.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                category = "5";
                loadCategories(category);
            }
        });

        icon_arrowDesserts.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                category = "6";
                loadCategories(category);
            }
        });

        icon_arrowMarket.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                category = "7";
                loadCategories(category);
            }
        });

        icon_arrowMore.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                category = "8";
                loadCategories(category);
            }
        });

            searchTv.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    String se = searchTv.getText().toString();
                    Intent intentLoadNewActivity = new Intent(Outros.this, SearchActivity.class);
                    intentLoadNewActivity.putExtra("search", se);
                    startActivity(intentLoadNewActivity);
                    return true;
                }
                return false;
            }
        });


        bottomNavView.setOnItemSelectedListener(item -> {
            myClickItem(item);
            return true;
        });
        bottomNavView.getMenu().findItem(R.id.restaurants).setChecked(true);
        bottomNavView.setSelectedItemId(R.id.restaurants);
        bottomNavView.getMenu().performIdentifierAction(R.id.restaurants, 0);



        if (nested != null) {

            nested.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    if (scrollY > oldScrollY) {
                        swipeRefreshLayout.setEnabled(false);

                    }
                    if (scrollY < oldScrollY) {
                        swipeRefreshLayout.setEnabled(false);
                    }

                    if (scrollY == 0) {
                        swipeRefreshLayout.isEnabled();
                        swipeRefreshLayout.setEnabled(true);
                    }

                    if (scrollY == ( v.getMeasuredHeight() - v.getChildAt(0).getMeasuredHeight() )) {
                        swipeRefreshLayout.setEnabled(false);
                    }
                }
            });
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(tabLayout.getSelectedTabPosition() == 0){
                    tabPosition = "0";
                    icon_arrow1.setVisibility(View.VISIBLE);
                    if(Objects.equals(bottomNPosition, "0")){
                        dietNews();
                    } else
                    if(Objects.equals(bottomNPosition, "1")){
                        pharmacyNews();
                    } else
                    if(Objects.equals(bottomNPosition, "2")){
                        closetNews();
                    } else
                    if(Objects.equals(bottomNPosition, "3")){
                        moreNews();
                    } else
                    if(Objects.equals(bottomNPosition, "4")){
                        servicesNews();
                    }
                }else if(tabLayout.getSelectedTabPosition() == 1){
                    tabPosition = "1";
                    if(Objects.equals(bottomNPosition, "0")){
                        dietNews();
                    } else
                    if(Objects.equals(bottomNPosition, "1")){
                        pharmacyNews();
                    } else
                    if(Objects.equals(bottomNPosition, "2")){
                        closetNews();
                    } else
                    if(Objects.equals(bottomNPosition, "3")){
                        moreNews();
                    } else
                    if(Objects.equals(bottomNPosition, "4")){
                        servicesNews();
                    }
                }else if(tabLayout.getSelectedTabPosition() == 2){
                    tabPosition = "2";
                    if(Objects.equals(bottomNPosition, "0")){
                        dietMail();
                    } else
                    if(Objects.equals(bottomNPosition, "1")){
                        pharmacyMail();
                    } else
                    if(Objects.equals(bottomNPosition, "2")){
                        closetMail();
                    } else
                    if(Objects.equals(bottomNPosition, "3")){
                        moreMail();
                    } else
                    if(Objects.equals(bottomNPosition, "4")){
                        servicesMail();
                    }
                }else if(tabLayout.getSelectedTabPosition() == 3){
                    tabPosition = "3";
                    if(Objects.equals(bottomNPosition, "0")){
                        dietImported();
                    } else
                    if(Objects.equals(bottomNPosition, "1")){
                        pharmacyImported();
                    } else
                    if(Objects.equals(bottomNPosition, "2")){
                        closetImported();
                    } else
                    if(Objects.equals(bottomNPosition, "3")){
                        moreImported();
                    } else
                    if(Objects.equals(bottomNPosition, "4")){
                        servicesImported();
                    }
                }else if(tabLayout.getSelectedTabPosition() == 4){
                    tabPosition = "4";
                    if(Objects.equals(bottomNPosition, "0")){
                        dietNews();
                    } else
                    if(Objects.equals(bottomNPosition, "1")){
                        pharmacyNews();
                    } else
                    if(Objects.equals(bottomNPosition, "2")){
                        closetNews();
                    } else
                    if(Objects.equals(bottomNPosition, "3")){
                        moreNews();
                    } else
                    if(Objects.equals(bottomNPosition, "4")){
                        servicesNews();
                    }
                }else if(tabLayout.getSelectedTabPosition() == 5){
                    tabPosition = "5";
                    if(Objects.equals(bottomNPosition, "0")){
                        carouselView.setVisibility(View.GONE);
                        loadAllBrands("Diet",city, state, country);
                    } else
                    if(Objects.equals(bottomNPosition, "1")){
                        carouselView.setVisibility(View.GONE);
                        loadAllBrands("Pharmacy",city, state, country);
                    } else
                    if(Objects.equals(bottomNPosition, "2")){
                        carouselView.setVisibility(View.GONE);
                        loadAllBrands("Closet",city, state, country);
                    } else
                    if(Objects.equals(bottomNPosition, "3")){
                        carouselView.setVisibility(View.GONE);
                        loadAllBrands("Others",city, state, country);
                    } else
                    if(Objects.equals(bottomNPosition, "4")){
                        carouselView.setVisibility(View.GONE);
                        loadAllBrands("Services",city, state, country);
                    }
                }else if(tabLayout.getSelectedTabPosition() == 6){
                    tabPosition = "6";
                    if(Objects.equals(bottomNPosition, "0")){
                        categoriesDiet();
                    } else
                    if(Objects.equals(bottomNPosition, "1")){
                        categoriesPharmacy();
                    } else
                    if(Objects.equals(bottomNPosition, "2")){
                        categoriesCloset();
                    } else
                    if(Objects.equals(bottomNPosition, "3")){
                        categoriesMore();
                    } else
                    if(Objects.equals(bottomNPosition, "4")){
                        categoriesServices();
                    }
                }
            swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void checkForAppSparks() {
        appUpdateManager = AppUpdateManagerFactory.create(this);

        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo,
                            AppUpdateType.FLEXIBLE,
                            this,
                            MY_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        });
        appUpdateManager.registerListener(listener);
    }

    private void checkForAppUpdate() {
        appUpdateManager = AppUpdateManagerFactory.create(this);

        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo,
                            AppUpdateType.IMMEDIATE,
                            this,
                            MY_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Log.w("Outros", "Update error code: "+ resultCode);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        appUpdateManager.unregisterListener(listener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(appUpdateInfo -> {
                    if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                        popupSnackbarForCompleteUpdate();
                    }
                });
    }

    InstallStateUpdatedListener listener = state -> {
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            popupSnackbarForCompleteUpdate();
        }
    };

    private void popupSnackbarForCompleteUpdate() {
        Snackbar snackbar =
                Snackbar.make(
                        findViewById(android.R.id.content),
                        R.string.updtatefinish,
                        Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.install, view -> appUpdateManager.completeUpdate());
        snackbar.setActionTextColor(
                getResources().getColor(R.color.green));
        snackbar.show();
    }

    private void  loadCategories(String category) {

        switch (bottomNPosition) {
            case "0" :
                type = "Diet";
                break;
            case "1":
                type = "Pharmacy";
                break;
            case "2":
                type = "Closet";
                break;
            case "3":
                type = "Others";
                break;
            case "4":
                type = "Services";
                break;
        }

        switch (type) {
            case "Diet" :
                switch (category) {
                    case "0" :
                        category = "Destaques";
                        break;
                    case "1":
                        category = "Promoções";
                        break;
                    case "2":
                        category = "Almoço";
                        break;
                    case "3":
                        category = "Lanche";
                        break;
                    case "4":
                        category = "Jantar";
                        break;
                    case "5":
                        category = "Bebidas";
                        break;
                    case "6":
                        category = "Sobremesas";
                        break;
                    case "7":
                        category = "Mercado";
                        break;
                    case "8":
                        category = "Mais";
                        break;
                }

                break;
            case "Pharmacy":
                switch (category) {
                    case "0" :
                        category = "Destaques";
                        break;
                    case "1":
                        category = "Promoções";
                        break;
                    case "2":
                        category = "Saúde";
                        break;
                    case "3":
                        category = "Higiene";
                        break;
                    case "4":
                        category = "Cosméticos";
                        break;
                    case "5":
                        category = "Fitness";
                        break;
                    case "6":
                        category = "Mãe & Bebê";
                        break;
                    case "7":
                        category = "Bem-Estar";
                        break;
                    case "8":
                        category = "Mais";
                        break;
                }
                break;
            case "Closet":
                switch (category) {
                    case "0" :
                        category = "Destaques";
                        break;
                    case "1":
                        category = "Promoções";
                        break;
                    case "2":
                        category = "Brechó";
                        break;
                    case "3":
                        category = "Roupas";
                        break;
                    case "4":
                        category = "Acessórios";
                        break;
                    case "5":
                        category = "Calçados";
                        break;
                    case "6":
                        category = "Intíma";
                        break;
                    case "7":
                        category = "Praia";
                        break;
                    case "8":
                        category = "Mais";
                        break;
                }                break;
            case "Others":
                switch (category) {
                    case "0" :
                        category = "Destaques";
                        break;
                    case "1":
                        category = "Promoções";
                        break;
                    case "2":
                        category = "Casa";
                        break;
                    case "3":
                        category = "Animal Não-Humano";
                        break;
                    case "4":
                        category = "Limpeza";
                        break;
                    case "5":
                        category = "Eco Friendly";
                        break;
                    case "6":
                        category = "Jardim";
                        break;
                    case "7":
                        category = "Livros";
                        break;
                    case "8":
                        category = "Mais";
                        break;
                }                break;
            case "Services":
                switch (category) {
                    case "0" :
                        category = "Destaques";
                        break;
                    case "1":
                        category = "Promoções";
                        break;
                    case "2":
                        category = "Nutricionistas";
                        break;
                    case "3":
                        category = "Médicos";
                        break;
                    case "4":
                        category = "Cabeleireiros & Barbeiros";
                        break;
                    case "5":
                        category = "Tatuadores";
                        break;
                    case "6":
                        category = "Lazer";
                        break;
                    case "7":
                        category = "Alimentícios";
                        break;
                    case "8":
                        category = "Mais";
                        break;
                }
                break;
        }
        Intent intent = new Intent(this, CategoryDetails.class);
        intent.putExtra("category", category);
        intent.putExtra("type", type);
        intent.putExtra("tabposition",tabPosition);
        startActivity(intent);

    }

    private void alertOffline ( ) {
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

    private void Ads() {

            DocumentReference docRef = fStore.collection("anuncio").document("IQCjYIhh0b3CrJRBQLwV");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            ad1 = document.getString("ad1");
                            ad2 = document.getString("ad2");
                            ad3 = document.getString("ad3");
                            ad4 = document.getString("ad4");
                            adPopUp = document.getString("adPopUp");
                            goad1 = document.getString("goad1");
                            goad2 = document.getString("goad2");
                            goad3 = document.getString("goad3");
                            goad4 = document.getString("goad4");
                            goadPopUp = document.getString("goadPopUp");

                            if (justOpenedApp.getBoolean("firstrun", true)) {
                                anuncio.setVisibility(View.VISIBLE);
                                closeAd.setVisibility(View.VISIBLE);
                                try {
                                    Picasso.get().load(adPopUp).placeholder(R.drawable.loading).into(anuncio);
                                } catch (Exception e) {
                                    anuncio.setImageDrawable(ContextCompat.getDrawable(Outros.this, R.drawable.error));
                                }

                                if (Objects.equals(goadPopUp, "") | Objects.equals(goadPopUp, "0") | Objects.equals(goadPopUp, "1") | Objects.equals(goadPopUp, "2")) {
                                    switch (goadPopUp) {
                                        case "":
                                            closeAd.setVisibility(View.GONE);
                                            anuncio.setOnClickListener(new View.OnClickListener() {
                                                public void onClick(View view) {
                                                    anuncio.setVisibility(View.GONE);
                                                    closeAd.setVisibility(View.GONE);
                                                }
                                            });

                                            closeAd.setOnClickListener(new View.OnClickListener() {
                                                public void onClick(View view) {
                                                    anuncio.setVisibility(View.GONE);
                                                    closeAd.setVisibility(View.GONE);
                                                }
                                            });
                                            break;

                                        case "0":
                                            closeAd.setVisibility(View.VISIBLE);
                                            anuncio.setOnClickListener(new View.OnClickListener() {
                                                public void onClick(View view) {
                                                    Intent intent1 = new Intent(Outros.this, GoVegan.class);
                                                    ActivityOptionsCompat actcompat = ActivityOptionsCompat.makeCustomAnimation(Outros.this,
                                                            R.anim.slide_in_left, R.anim.slide_out_left);
                                                    ActivityCompat.startActivity(Outros.this, intent1, actcompat.toBundle());
                                                }
                                            });

                                            closeAd.setOnClickListener(new View.OnClickListener() {
                                                public void onClick(View view) {
                                                    anuncio.setVisibility(View.GONE);
                                                    closeAd.setVisibility(View.GONE);
                                                }
                                            });
                                            break;

                                        case "1":
                                            closeAd.setVisibility(View.VISIBLE);
                                            anuncio.setOnClickListener(new View.OnClickListener() {
                                                public void onClick(View view) {
                                                    Intent intentLoadNewActivity = new Intent(Outros.this, ActivismActivity.class);
                                                    startActivity(intentLoadNewActivity);
                                                }
                                            });

                                            closeAd.setOnClickListener(new View.OnClickListener() {
                                                public void onClick(View view) {
                                                    anuncio.setVisibility(View.GONE);
                                                    closeAd.setVisibility(View.GONE);
                                                }
                                            });
                                            break;

                                        case "2":
                                            closeAd.setVisibility(View.VISIBLE);
                                            anuncio.setOnClickListener(new View.OnClickListener() {
                                                public void onClick(View view) {
                                                    Intent intentLoadNewActivity = new Intent(Outros.this, Aboutus.class);
                                                    startActivity(intentLoadNewActivity);
                                                }
                                            });

                                            closeAd.setOnClickListener(new View.OnClickListener() {
                                                public void onClick(View view) {
                                                    anuncio.setVisibility(View.GONE);
                                                    closeAd.setVisibility(View.GONE);
                                                }
                                            });
                                            break;
                                    }
                                } else {
                                    anuncio.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View view) {
                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(goadPopUp));
                                            startActivity(browserIntent);
                                        }
                                    });

                                    closeAd.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View view) {
                                            anuncio.setVisibility(View.GONE);
                                            closeAd.setVisibility(View.GONE);
                                        }
                                    });
                                }
                                justOpenedApp.edit().putBoolean("firstrun", false).commit();
                            }

                            ArrayList<SlideModel> slideModels = new ArrayList<>();
                            slideModels.add(new SlideModel(ad1, ScaleTypes.FIT));
                            slideModels.add(new SlideModel(ad2, ScaleTypes.FIT));
                            slideModels.add(new SlideModel(ad3, ScaleTypes.FIT));
                            slideModels.add(new SlideModel(ad4, ScaleTypes.FIT));

                            carouselView.setImageList(slideModels, ScaleTypes.FIT);


                            carouselView.setItemClickListener(new ItemClickListener() {
                                public void doubleClick(int i) {
                                    if (i == 0) {
                                        if (Objects.equals(goad1, "0")) {

                                        } else {
                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(goad1));
                                            startActivity(browserIntent);
                                        }

                                    }
                                    if (i == 1) {
                                        if (Objects.equals(goad2, "0")) {

                                        } else {
                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(goad2));
                                            startActivity(browserIntent);
                                        }
                                    }
                                    if (i == 2) {
                                        if (Objects.equals(goad3, "0")) {

                                        } else {
                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(goad3));
                                            startActivity(browserIntent);
                                        }
                                    }
                                    if (i == 3) {
                                        if (Objects.equals(goad4, "0")) {

                                        } else {
                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(goad4));
                                            startActivity(browserIntent);
                                        }
                                    }
                                }

                                @Override
                                public void onItemSelected(int i) {
                                    if (i == 0) {
                                        if (Objects.equals(goad1, "0")) {

                                        } else {
                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(goad1));
                                            startActivity(browserIntent);
                                        }

                                    }
                                    if (i == 1) {
                                        if (Objects.equals(goad2, "0")) {

                                        } else {
                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(goad2));
                                            startActivity(browserIntent);
                                        }
                                    }
                                    if (i == 2) {
                                        if (Objects.equals(goad3, "0")) {

                                        } else {
                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(goad3));
                                            startActivity(browserIntent);
                                        }
                                    }
                                    if (i == 3) {
                                        if (Objects.equals(goad4, "0")) {

                                        } else {
                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(goad4));
                                            startActivity(browserIntent);
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            });
    }

    private void dietImported() {
        hideArrows();
        embreve.setVisibility(View.VISIBLE);
        txt_title1.setText(R.string.almoco);
        txt_title2.setText(R.string.lanches);
        txt_titleDinner.setText(R.string.dinner);
        txt_titleDrinks.setText(R.string.bebidas);
        txt_titleDesserts.setText(R.string.sobremesas);
        txt_titleMarket.setText(R.string.mercado);
        clcategories.setVisibility(View.GONE);
        brandsRv.setVisibility(View.GONE);
        recyclerviewHighLights.setVisibility(View.VISIBLE);
        promotionRv.setVisibility(View.VISIBLE);
        lunchRv.setVisibility(View.VISIBLE);
        snacksRv.setVisibility(View.VISIBLE);
        dinnerRv.setVisibility(View.VISIBLE);
        drinksRv.setVisibility(View.VISIBLE);
        desertsRv.setVisibility(View.VISIBLE);
        marketRv.setVisibility(View.VISIBLE);
        moreRv.setVisibility(View.VISIBLE);
        String filter1 ="Diet";
        String selected = "Promoções";
        loadAllHighlightsImported(filter1, country);
        loadAllImported(productList37, promotionRv, selected, filter1, country);
        String selected2 = "Almoço";
        loadAllImported(productList38,lunchRv, selected2, filter1, country);
        String selected3 = "Lanche";
        loadAllImported(productList39, snacksRv, selected3, filter1, country);
        String selected4 = "Jantar";
        loadAllImported(productList40, dinnerRv, selected4, filter1, country);
        String selected5 = "Bebidas";
        loadAllImported(productList42, drinksRv, selected5, filter1, country);
        String selected6 = "Sobremesas";
        loadAllImported(productList45, desertsRv, selected6, filter1, country);
        String selected7 = "Mercado";
        loadAllImported(productList46, marketRv, selected7, filter1, country);
        String selected8 = "Mais";
        loadAllImported(productList47, moreRv, selected8, filter1, country);
    }

    private void pharmacyImported(){
        hideArrows();
        embreve.setVisibility(View.VISIBLE);
        txt_title1.setText(R.string.saude);
        txt_title2.setText(R.string.higiene);
        txt_titleDinner.setText(R.string.cosmeticos);
        txt_titleDrinks.setText(R.string.fitness);
        txt_titleDesserts.setText(R.string.maeebebe);
        txt_titleMarket.setText(R.string.natural);
        clcategories.setVisibility(View.GONE);
        brandsRv.setVisibility(View.GONE);
        recyclerviewHighLights.setVisibility(View.VISIBLE);
        promotionRv.setVisibility(View.VISIBLE);
        lunchRv.setVisibility(View.VISIBLE);
        snacksRv.setVisibility(View.VISIBLE);
        dinnerRv.setVisibility(View.VISIBLE);
        drinksRv.setVisibility(View.VISIBLE);
        desertsRv.setVisibility(View.VISIBLE);
        marketRv.setVisibility(View.VISIBLE);
        moreRv.setVisibility(View.VISIBLE);
        String filter2 = "Pharmacy";
        String selected = "Promoções";
        loadAllHighlightsImported(filter2, country);
        loadAllImported(productList37,promotionRv, selected, filter2, country);
        String selected9 = "Saúde";
        loadAllImported(productList38,lunchRv, selected9, filter2, country);
        String selected10 = "Higiene";
        loadAllImported(productList39,snacksRv, selected10, filter2, country);
        String selected11 = "Cosméticos";
        loadAllImported(productList40,dinnerRv, selected11, filter2, country);
        String selected12 = "Fitness";
        loadAllImported(productList42,drinksRv, selected12, filter2, country);
        String selected13 = "Mãe & Bebê";
        loadAllImported(productList45,desertsRv, selected13, filter2, country);
        String selected14 = "Bem estar";
        loadAllImported(productList46,marketRv, selected14, filter2, country);
        String selected8 = "Mais";
        loadAllImported(productList47,moreRv, selected8, filter2, country);
    }

    private void closetImported(){
        hideArrows();
        embreve.setVisibility(View.VISIBLE);
        txt_title1.setText(R.string.brecho);
        txt_title2.setText(R.string.roupas);
        txt_titleDinner.setText(R.string.acessorios);
        txt_titleDrinks.setText(R.string.calcados);
        txt_titleDesserts.setText(R.string.pecaintima);
        txt_titleMarket.setText(R.string.praia);
        clcategories.setVisibility(View.GONE);
        brandsRv.setVisibility(View.GONE);
        recyclerviewHighLights.setVisibility(View.VISIBLE);
        promotionRv.setVisibility(View.VISIBLE);
        lunchRv.setVisibility(View.VISIBLE);
        snacksRv.setVisibility(View.VISIBLE);
        dinnerRv.setVisibility(View.VISIBLE);
        drinksRv.setVisibility(View.VISIBLE);
        desertsRv.setVisibility(View.VISIBLE);
        marketRv.setVisibility(View.VISIBLE);
        moreRv.setVisibility(View.VISIBLE);
        String selected15 = "Brechó";
        String filter3 = "Closet";
        String promotions = "Promoções";
        loadAllHighlightsImported(filter3, country);
        loadAllImported(productList37,promotionRv, promotions, filter3, country);
        loadAllImported(productList38,lunchRv, selected15, filter3, country);
        String selected16 = "Roupas";
        loadAllImported(productList39,snacksRv, selected16, filter3, country);
        String selected17 = "Acessórios";
        loadAllImported(productList40,dinnerRv, selected17, filter3, country);
        String selected18 = "Calçados";
        loadAllImported(productList42,drinksRv, selected18, filter3, country);
        String selected19 = "Peça Intíma";
        loadAllImported(productList45,desertsRv, selected19, filter3, country);
        String selected20 = "Praia";
        loadAllImported(productList46,marketRv, selected20, filter3, country);
        String selected27 = "Mais";
        loadAllImported(productList47,moreRv, selected27, filter3, country);
    }

    private void moreImported(){
        hideArrows();
        embreve.setVisibility(View.VISIBLE);
        txt_title1.setText(R.string.casa);
        txt_title2.setText(R.string.animalnaohumano);
        txt_titleDinner.setText(R.string.limpeza);
        txt_titleDrinks.setText(R.string.ecofriendly);
        txt_titleDesserts.setText(R.string.jardim);
        txt_titleMarket.setText(R.string.livros);
        clcategories.setVisibility(View.GONE);
        brandsRv.setVisibility(View.GONE);
        recyclerviewHighLights.setVisibility(View.VISIBLE);
        promotionRv.setVisibility(View.VISIBLE);
        lunchRv.setVisibility(View.VISIBLE);
        snacksRv.setVisibility(View.VISIBLE);
        dinnerRv.setVisibility(View.VISIBLE);
        drinksRv.setVisibility(View.VISIBLE);
        desertsRv.setVisibility(View.VISIBLE);
        marketRv.setVisibility(View.VISIBLE);
        moreRv.setVisibility(View.VISIBLE);
        String selected21 = "Casa";
        String filter4 = "Others";
        String promotion = "Promoções";
        loadAllHighlightsImported(filter4, country);
        loadAllImported(productList37,promotionRv, promotion, filter4, country);
        loadAllImported(productList38,lunchRv, selected21, filter4, country);
        String selected22 = "Animal Não-Humano";
        loadAllImported(productList39,snacksRv, selected22, filter4, country);
        String selected23 = "Limpeza";
        loadAllImported(productList40,dinnerRv, selected23, filter4, country);
        String selected24 = "Eco Friendly";
        loadAllImported(productList42,drinksRv, selected24, filter4, country);
        String selected25 = "Jardim";
        loadAllImported(productList45,desertsRv, selected25, filter4, country);
        String selected26 = "Livros";
        loadAllImported(productList46,marketRv, selected26, filter4, country);
        String selected28 = "Mais";
        loadAllImported(productList47,moreRv, selected28, filter4, country);
    }

    private void servicesImported(){
        hideArrows();
        embreve.setVisibility(View.VISIBLE);
        txt_title1.setVisibility(View.GONE);
        txt_title2.setVisibility(View.GONE);
        txt_titleDinner.setVisibility(View.GONE);
        txt_titleDrinks.setVisibility(View.GONE);
        txt_titleDesserts.setVisibility(View.GONE);
        txt_titleMarket.setVisibility(View.GONE);
        clcategories.setVisibility(View.GONE);
        brandsRv.setVisibility(View.GONE);
        recyclerviewHighLights.setVisibility(View.GONE);
        promotionRv.setVisibility(View.GONE);
        lunchRv.setVisibility(View.GONE);
        snacksRv.setVisibility(View.GONE);
        dinnerRv.setVisibility(View.GONE);
        drinksRv.setVisibility(View.GONE);
        desertsRv.setVisibility(View.GONE);
        marketRv.setVisibility(View.GONE);
        moreRv.setVisibility(View.GONE);
        embreve.setVisibility(View.VISIBLE);
    }

    private void dietMail() {
        txt_title1.setText(R.string.almoco);
        txt_title2.setText(R.string.lanches);
        txt_titleDinner.setText(R.string.dinner);
        txt_titleDrinks.setText(R.string.bebidas);
        txt_titleDesserts.setText(R.string.sobremesas);
        txt_titleMarket.setText(R.string.mercado);
        clcategories.setVisibility(View.GONE);
        brandsRv.setVisibility(View.GONE);
        recyclerviewHighLights.setVisibility(View.VISIBLE);
        promotionRv.setVisibility(View.VISIBLE);
        lunchRv.setVisibility(View.VISIBLE);
        snacksRv.setVisibility(View.VISIBLE);
        dinnerRv.setVisibility(View.VISIBLE);
        drinksRv.setVisibility(View.VISIBLE);
        desertsRv.setVisibility(View.VISIBLE);
        marketRv.setVisibility(View.VISIBLE);
        moreRv.setVisibility(View.VISIBLE);
        String filter1 ="Diet";
        String selected = "Promoções";
        loadAllHighlightsNational(productList37, filter1, city, country);
        loadAllNationalMail(productList28, promotionRv,selected, filter1, city, country);
        String selected2 = "Almoço";
        loadAllNationalMail(productList29, lunchRv, selected2, filter1, city, country);
        String selected3 = "Lanche";
        loadAllNationalMail(productList30, snacksRv, selected3, filter1, city, country);
        String selected4 = "Jantar";
        loadAllNationalMail(productList31, dinnerRv, selected4, filter1, city, country);
        String selected5 = "Bebidas";
        loadAllNationalMail(productList32, drinksRv, selected5, filter1, city, country);
        String selected6 = "Sobremesas";
        loadAllNationalMail(productList33, desertsRv, selected6, filter1, city, country);
        String selected7 = "Mercado";
        loadAllNationalMail(productList35, marketRv, selected7, filter1, city, country);
        String selected8 = "Mais";
        loadAllNationalMail(productList36, moreRv, selected8, filter1, city, country);
    }

    private void pharmacyMail(){
        txt_title1.setText(R.string.saude);
        txt_title2.setText(R.string.higiene);
        txt_titleDinner.setText(R.string.cosmeticos);
        txt_titleDrinks.setText(R.string.fitness);
        txt_titleDesserts.setText(R.string.maeebebe);
        txt_titleMarket.setText(R.string.natural);
        clcategories.setVisibility(View.GONE);
        brandsRv.setVisibility(View.GONE);
        recyclerviewHighLights.setVisibility(View.VISIBLE);
        promotionRv.setVisibility(View.VISIBLE);
        lunchRv.setVisibility(View.VISIBLE);
        snacksRv.setVisibility(View.VISIBLE);
        dinnerRv.setVisibility(View.VISIBLE);
        drinksRv.setVisibility(View.VISIBLE);
        desertsRv.setVisibility(View.VISIBLE);
        marketRv.setVisibility(View.VISIBLE);
        moreRv.setVisibility(View.VISIBLE);
        String filter2 = "Pharmacy";
        String selected = "Promoções";
        loadAllHighlightsNational(productList37, filter2, city, country);
        loadAllNationalMail(productList28, promotionRv, selected, filter2, city, country);
        String selected9 = "Saúde";
        loadAllNationalMail(productList29, lunchRv, selected9, filter2, city, country);
        String selected10 = "Higiene";
        loadAllNationalMail(productList30, snacksRv, selected10, filter2, city, country);
        String selected11 = "Cosméticos";
        loadAllNationalMail(productList31, dinnerRv, selected11, filter2, city, country);
        String selected12 = "Fitness";
        loadAllNationalMail(productList32, drinksRv, selected12, filter2, city, country);
        String selected13 = "Mãe & Bebê";
        loadAllNationalMail(productList33, desertsRv, selected13, filter2, city, country);
        String selected14 = "Bem estar";
        loadAllNationalMail(productList35, marketRv, selected14, filter2, city, country);
        String selected8 = "Mais";
        loadAllNationalMail(productList36, moreRv, selected8, filter2, city, country);
    }

    private void closetMail(){
        txt_title1.setText(R.string.brecho);
        txt_title2.setText(R.string.roupas);
        txt_titleDinner.setText(R.string.acessorios);
        txt_titleDrinks.setText(R.string.calcados);
        txt_titleDesserts.setText(R.string.pecaintima);
        txt_titleMarket.setText(R.string.praia);
        clcategories.setVisibility(View.GONE);
        brandsRv.setVisibility(View.GONE);
        recyclerviewHighLights.setVisibility(View.VISIBLE);
        promotionRv.setVisibility(View.VISIBLE);
        lunchRv.setVisibility(View.VISIBLE);
        snacksRv.setVisibility(View.VISIBLE);
        dinnerRv.setVisibility(View.VISIBLE);
        drinksRv.setVisibility(View.VISIBLE);
        desertsRv.setVisibility(View.VISIBLE);
        marketRv.setVisibility(View.VISIBLE);
        moreRv.setVisibility(View.VISIBLE);
        String selected15 = "Brechó";
        String filter3 = "Closet";
        String promotions = "Promoções";
        loadAllHighlightsNational(productList37, filter3, city, country);
        loadAllNationalMail(productList28, promotionRv, promotions, filter3, city, country);
        loadAllNationalMail(productList29, lunchRv, selected15, filter3, city, country);
        String selected16 = "Roupas";
        loadAllNationalMail(productList30, snacksRv, selected16, filter3, city, country);
        String selected17 = "Acessórios";
        loadAllNationalMail(productList31, dinnerRv, selected17, filter3, city, country);
        String selected18 = "Calçados";
        loadAllNationalMail(productList32, drinksRv, selected18, filter3, city, country);
        String selected19 = "Peça Intíma";
        loadAllNationalMail(productList33, desertsRv, selected19, filter3, city, country);
        String selected20 = "Praia";
        loadAllNationalMail(productList35, marketRv, selected20, filter3, city, country);
        String selected27 = "Mais";
        loadAllNationalMail(productList36, moreRv, selected27, filter3, city, country);
    }

    private void moreMail(){
        txt_title1.setText(R.string.casa);
        txt_title2.setText(R.string.animalnaohumano);
        txt_titleDinner.setText(R.string.limpeza);
        txt_titleDrinks.setText(R.string.ecofriendly);
        txt_titleDesserts.setText(R.string.jardim);
        txt_titleMarket.setText(R.string.livros);
        clcategories.setVisibility(View.GONE);
        brandsRv.setVisibility(View.GONE);
        recyclerviewHighLights.setVisibility(View.VISIBLE);
        promotionRv.setVisibility(View.VISIBLE);
        lunchRv.setVisibility(View.VISIBLE);
        snacksRv.setVisibility(View.VISIBLE);
        dinnerRv.setVisibility(View.VISIBLE);
        drinksRv.setVisibility(View.VISIBLE);
        desertsRv.setVisibility(View.VISIBLE);
        marketRv.setVisibility(View.VISIBLE);
        moreRv.setVisibility(View.VISIBLE);
        String selected21 = "Casa";
        String filter4 = "Others";
        String promotion = "Promoções";
        loadAllHighlightsNational(productList37, filter4, city, country);
        loadAllNationalMail(productList28, promotionRv, promotion, filter4, city, country);
        loadAllNationalMail(productList29, lunchRv, selected21, filter4, city, country);
        String selected22 = "Animal Não-Humano";
        loadAllNationalMail(productList30, snacksRv, selected22, filter4, city, country);
        String selected23 = "Limpeza";
        loadAllNationalMail(productList31, dinnerRv, selected23, filter4, city, country);
        String selected24 = "Eco Friendly";
        loadAllNationalMail(productList32, drinksRv, selected24, filter4, city, country);
        String selected25 = "Jardim";
        loadAllNationalMail(productList33, desertsRv, selected25, filter4, city, country);
        String selected26 = "Livros";
        loadAllNationalMail(productList35, marketRv, selected26, filter4, city, country);
        String selected28 = "Mais";
        loadAllNationalMail(productList36, moreRv, selected28, filter4, city, country);
    }

    private void servicesMail(){
        hideArrows();
        txt_title1.setVisibility(View.GONE);
        txt_title2.setVisibility(View.GONE);
        txt_titleDinner.setVisibility(View.GONE);
        txt_titleDrinks.setVisibility(View.GONE);
        txt_titleDesserts.setVisibility(View.GONE);
        txt_titleMarket.setVisibility(View.GONE);
        clcategories.setVisibility(View.GONE);
        brandsRv.setVisibility(View.GONE);
        recyclerviewHighLights.setVisibility(View.GONE);
        promotionRv.setVisibility(View.GONE);
        lunchRv.setVisibility(View.GONE);
        snacksRv.setVisibility(View.GONE);
        dinnerRv.setVisibility(View.GONE);
        drinksRv.setVisibility(View.GONE);
        desertsRv.setVisibility(View.GONE);
        marketRv.setVisibility(View.GONE);
        moreRv.setVisibility(View.GONE);
        embreve.setVisibility(View.VISIBLE);
    }

    private void dietNews() {
        embreve.setVisibility(View.GONE);
        brandsRv.setVisibility(View.GONE);
        clcategories.setVisibility(View.GONE);
        txt_title1.setText(R.string.almoco);
        txt_title2.setText(R.string.lanches);
        txt_titleDinner.setText(R.string.dinner);
        txt_titleDrinks.setText(R.string.bebidas);
        txt_titleDesserts.setText(R.string.sobremesas);
        txt_titleMarket.setText(R.string.mercado);
        carouselView.setVisibility(View.VISIBLE);
        recyclerviewHighLights.setVisibility(View.VISIBLE);
        promotionRv.setVisibility(View.VISIBLE);
        lunchRv.setVisibility(View.VISIBLE);
        snacksRv.setVisibility(View.VISIBLE);
        dinnerRv.setVisibility(View.VISIBLE);
        drinksRv.setVisibility(View.VISIBLE);
        desertsRv.setVisibility(View.VISIBLE);
        marketRv.setVisibility(View.VISIBLE);
        moreRv.setVisibility(View.VISIBLE);
        String filter1 ="Diet";
        String selected = "Promoções";
        loadAllHighlights(filter1);
        loadAllPromotions(selected, filter1);
        String selected2 = "Almoço";
        loadAllLunchs(selected2, filter1);
        String selected3 = "Lanche";
        loadAllSnacks(selected3, filter1);
        String selected4 = "Jantar";
        loadAllDinners(selected4, filter1);
        String selected5 = "Bebidas";
        loadAllDrinks(selected5, filter1);
        String selected6 = "Sobremesas";
        loadAllDeserts(selected6, filter1);
        String selected7 = "Mercado";
        loadAllMarket(selected7, filter1);
        String selected8 = "Mais";
        loadAllMore(selected8, filter1);
    }

    private void pharmacyNews(){
        embreve.setVisibility(View.GONE);
        brandsRv.setVisibility(View.GONE);
        clcategories.setVisibility(View.GONE);
        txt_title1.setText(R.string.saude);
        txt_title2.setText(R.string.higiene);
        txt_titleDinner.setText(R.string.cosmeticos);
        txt_titleDrinks.setText(R.string.fitness);
        txt_titleDesserts.setText(R.string.maeebebe);
        txt_titleMarket.setText(R.string.natural);
        carouselView.setVisibility(View.VISIBLE);
        recyclerviewHighLights.setVisibility(View.VISIBLE);
        promotionRv.setVisibility(View.VISIBLE);
        lunchRv.setVisibility(View.VISIBLE);
        snacksRv.setVisibility(View.VISIBLE);
        dinnerRv.setVisibility(View.VISIBLE);
        drinksRv.setVisibility(View.VISIBLE);
        desertsRv.setVisibility(View.VISIBLE);
        marketRv.setVisibility(View.VISIBLE);
        moreRv.setVisibility(View.VISIBLE);
        String filter2 = "Pharmacy";
        String selected = "Promoções";
        loadAllHighlights(filter2);
        loadAllPromotions(selected, filter2);
        String selected9 = "Saúde";
        loadAllLunchs(selected9, filter2);
        String selected10 = "Higiene";
        loadAllSnacks(selected10, filter2);
        String selected11 = "Cosméticos";
        loadAllDinners(selected11, filter2);
        String selected12 = "Fitness";
        loadAllDrinks(selected12, filter2);
        String selected13 = "Mãe & Bebê";
        loadAllDeserts(selected13, filter2);
        String selected14 = "Bem estar";
        loadAllMarket(selected14, filter2);
        String selected8 = "Mais";
        loadAllMore(selected8, filter2);
    }

    private void closetNews(){
        txt_title1.setText(R.string.brecho);
        txt_title2.setText(R.string.roupas);
        txt_titleDinner.setText(R.string.acessorios);
        txt_titleDrinks.setText(R.string.calcados);
        txt_titleDesserts.setText(R.string.pecaintima);
        txt_titleMarket.setText(R.string.praia);
        brandsRv.setVisibility(View.GONE);
        clcategories.setVisibility(View.GONE);
        embreve.setVisibility(View.GONE);
        carouselView.setVisibility(View.VISIBLE);
        recyclerviewHighLights.setVisibility(View.VISIBLE);
        promotionRv.setVisibility(View.VISIBLE);
        lunchRv.setVisibility(View.VISIBLE);
        snacksRv.setVisibility(View.VISIBLE);
        dinnerRv.setVisibility(View.VISIBLE);
        drinksRv.setVisibility(View.VISIBLE);
        desertsRv.setVisibility(View.VISIBLE);
        marketRv.setVisibility(View.VISIBLE);
        moreRv.setVisibility(View.VISIBLE);
        String selected15 = "Brechó";
        String filter3 = "Closet";
        String promotions = "Promoções";
        loadAllHighlights(filter3);
        loadAllPromotions(promotions, filter3);
        loadAllLunchs(selected15, filter3);
        String selected16 = "Roupas";
        loadAllSnacks(selected16, filter3);
        String selected17 = "Acessórios";
        loadAllDinners(selected17, filter3);
        String selected18 = "Calçados";
        loadAllDrinks(selected18, filter3);
        String selected19 = "Peça Intíma";
        loadAllDeserts(selected19, filter3);
        String selected20 = "Praia";
        loadAllMarket(selected20, filter3);
        String selected27 = "Mais";
        loadAllMore(selected27, filter3);
    }

    private void moreNews(){
        txt_title1.setText(R.string.casa);
        txt_title2.setText(R.string.animalnaohumano);
        txt_titleDinner.setText(R.string.limpeza);
        txt_titleDrinks.setText(R.string.ecofriendly);
        txt_titleDesserts.setText(R.string.jardim);
        txt_titleMarket.setText(R.string.livros);
        brandsRv.setVisibility(View.GONE);
        clcategories.setVisibility(View.GONE);
        embreve.setVisibility(View.GONE);
        carouselView.setVisibility(View.VISIBLE);
        recyclerviewHighLights.setVisibility(View.VISIBLE);
        promotionRv.setVisibility(View.VISIBLE);
        lunchRv.setVisibility(View.VISIBLE);
        snacksRv.setVisibility(View.VISIBLE);
        dinnerRv.setVisibility(View.VISIBLE);
        drinksRv.setVisibility(View.VISIBLE);
        desertsRv.setVisibility(View.VISIBLE);
        marketRv.setVisibility(View.VISIBLE);
        moreRv.setVisibility(View.VISIBLE);
        String selected21 = "Casa";
        String filter4 = "Others";
        String promotion = "Promoções";
        loadAllHighlights(filter4);
        loadAllPromotions(promotion, filter4);
        loadAllLunchs(selected21, filter4);
        String selected22 = "Animal Não-Humano";
        loadAllSnacks(selected22, filter4);
        String selected23 = "Limpeza";
        loadAllDinners(selected23, filter4);
        String selected24 = "Eco Friendly";
        loadAllDrinks(selected24, filter4);
        String selected25 = "Jardim";
        loadAllDeserts(selected25, filter4);
        String selected26 = "Livros";
        loadAllMarket(selected26, filter4);
        String selected28 = "Mais";
        loadAllMore(selected28, filter4);
    }

    private void servicesNews(){
        txt_title1.setText(R.string.dietaenutri);
        txt_title2.setText(R.string.saudeebemestar);
        txt_titleDinner.setText(R.string.estetica);
        txt_titleDrinks.setText(R.string.tatuagem);
        txt_titleDesserts.setText("Lazer & Hospedagens");
        txt_titleMarket.setText(R.string.culinaria);
        brandsRv.setVisibility(View.GONE);
        clcategories.setVisibility(View.GONE);
        embreve.setVisibility(View.GONE);
        carouselView.setVisibility(View.VISIBLE);
        recyclerviewHighLights.setVisibility(View.VISIBLE);
        promotionRv.setVisibility(View.VISIBLE);
        lunchRv.setVisibility(View.VISIBLE);
        snacksRv.setVisibility(View.VISIBLE);
        dinnerRv.setVisibility(View.VISIBLE);
        drinksRv.setVisibility(View.VISIBLE);
        desertsRv.setVisibility(View.VISIBLE);
        marketRv.setVisibility(View.VISIBLE);
        moreRv.setVisibility(View.VISIBLE);
        loadAllHighlights("Services");
        loadAllPromotionsServices("Promoções");
        loadAllNutri("Nutricionistas");
        loadAllMedicos("Médicos");
        loadAllBarbers("Cabeleireiros & Barbeiros");
        loadAllTatuadores("Tatuadores");
        loadAllHospedagens("Lazer");
        loadAllAlimenticios("Alimentícios");
        loadAllMoreServices("Mais");
    }

    private void categoriesServices() {
        brandsRv.setVisibility(View.GONE);
        embreve.setVisibility(View.GONE);
        clcategories.setVisibility(View.GONE);
        recyclerviewHighLights.setVisibility(View.GONE);
        promotionRv.setVisibility(View.GONE);
        lunchRv.setVisibility(View.GONE);
        snacksRv.setVisibility(View.GONE);
        dinnerRv.setVisibility(View.GONE);
        drinksRv.setVisibility(View.GONE);
        desertsRv.setVisibility(View.GONE);
        marketRv.setVisibility(View.GONE);
        moreRv.setVisibility(View.GONE);
        hideArrows();
        embreve.setVisibility(View.GONE);
        cate3.setImageResource(R.drawable.nutricateg);
        cate4.setImageResource(R.drawable.medicateg);
        cate5.setImageResource(R.drawable.barbercateg);
        cate6.setImageResource(R.drawable.tattcateg);
        cate7.setImageResource(R.drawable.hospecateg);
        cate8.setImageResource(R.drawable.alimecateg);
        ctg3.setText(R.string.dietaenutri);
        ctg4.setText(R.string.saudeebemestar);
        ctg5.setText(R.string.estetica);
        ctg6.setText(R.string.tatuagem);
        ctg7.setText(R.string.turismo);
        ctg8.setText(R.string.culinaria);
        clcategories.setVisibility(View.VISIBLE);
    }

    private void categoriesMore() {
        brandsRv.setVisibility(View.GONE);
        clcategories.setVisibility(View.GONE);
        embreve.setVisibility(View.GONE);
        recyclerviewHighLights.setVisibility(View.GONE);
        promotionRv.setVisibility(View.GONE);
        lunchRv.setVisibility(View.GONE);
        snacksRv.setVisibility(View.GONE);
        dinnerRv.setVisibility(View.GONE);
        drinksRv.setVisibility(View.GONE);
        desertsRv.setVisibility(View.GONE);
        marketRv.setVisibility(View.GONE);
        moreRv.setVisibility(View.GONE);
        hideArrows();
        embreve.setVisibility(View.GONE);
        cate3.setImageResource(R.drawable.casasateg);
        cate4.setImageResource(R.drawable.petcateg);
        cate5.setImageResource(R.drawable.uticateg);
        cate6.setImageResource(R.drawable.reuticateg);
        cate7.setImageResource(R.drawable.jardimcateg);
        cate8.setImageResource(R.drawable.livroscateg);
        ctg3.setText(R.string.casa);
        ctg4.setText(R.string.animalnaohumano);
        ctg5.setText(R.string.limpeza);
        ctg6.setText(R.string.ecofriendly);
        ctg7.setText(R.string.jardim);
        ctg8.setText(R.string.livros);
        clcategories.setVisibility(View.VISIBLE);
    }

    private void categoriesCloset() {
        brandsRv.setVisibility(View.GONE);
        embreve.setVisibility(View.GONE);
        clcategories.setVisibility(View.GONE);
        recyclerviewHighLights.setVisibility(View.GONE);
        promotionRv.setVisibility(View.GONE);
        lunchRv.setVisibility(View.GONE);
        snacksRv.setVisibility(View.GONE);
        dinnerRv.setVisibility(View.GONE);
        drinksRv.setVisibility(View.GONE);
        desertsRv.setVisibility(View.GONE);
        marketRv.setVisibility(View.GONE);
        moreRv.setVisibility(View.GONE);
        hideArrows();
        embreve.setVisibility(View.GONE);
        cate3.setImageResource(R.drawable.brechocateg);
        cate4.setImageResource(R.drawable.roupascateg);
        cate5.setImageResource(R.drawable.acessocateg);
        cate6.setImageResource(R.drawable.calcateg);
        cate7.setImageResource(R.drawable.pecateg);
        cate8.setImageResource(R.drawable.praiacateg);
        ctg3.setText(R.string.brecho);
        ctg4.setText(R.string.roupas);
        ctg5.setText(R.string.acessorios);
        ctg6.setText(R.string.calcados);
        ctg7.setText(R.string.pecaintima);
        ctg8.setText(R.string.praia);
        clcategories.setVisibility(View.VISIBLE);
    }

    private void categoriesPharmacy() {
        brandsRv.setVisibility(View.GONE);
        embreve.setVisibility(View.GONE);
        clcategories.setVisibility(View.GONE);
        recyclerviewHighLights.setVisibility(View.GONE);
        promotionRv.setVisibility(View.GONE);
        lunchRv.setVisibility(View.GONE);
        snacksRv.setVisibility(View.GONE);
        dinnerRv.setVisibility(View.GONE);
        drinksRv.setVisibility(View.GONE);
        desertsRv.setVisibility(View.GONE);
        marketRv.setVisibility(View.GONE);
        moreRv.setVisibility(View.GONE);
        hideArrows();
        embreve.setVisibility(View.GONE);
        cate3.setImageResource(R.drawable.healthcateg);
        cate4.setImageResource(R.drawable.hygiencateg);
        cate5.setImageResource(R.drawable.cosmeticcateg);
        cate6.setImageResource(R.drawable.fitcateg);
        cate7.setImageResource(R.drawable.mothercateg);
        cate8.setImageResource(R.drawable.bemcateg);
        ctg3.setText(R.string.saude);
        ctg4.setText(R.string.higiene);
        ctg5.setText(R.string.cosmeticos);
        ctg6.setText(R.string.fitness);
        ctg7.setText(R.string.maeebebe);
        ctg8.setText(R.string.natural);
        clcategories.setVisibility(View.VISIBLE);
    }

    private void categoriesDiet() {
        brandsRv.setVisibility(View.GONE);
        embreve.setVisibility(View.GONE);
        clcategories.setVisibility(View.GONE);
        recyclerviewHighLights.setVisibility(View.GONE);
        promotionRv.setVisibility(View.GONE);
        lunchRv.setVisibility(View.GONE);
        snacksRv.setVisibility(View.GONE);
        dinnerRv.setVisibility(View.GONE);
        drinksRv.setVisibility(View.GONE);
        desertsRv.setVisibility(View.GONE);
        marketRv.setVisibility(View.GONE);
        moreRv.setVisibility(View.GONE);
        hideArrows();
        embreve.setVisibility(View.GONE);
        clcategories.setVisibility(View.VISIBLE);
        cate3.setImageResource(R.drawable.lunchcateg);
        cate4.setImageResource(R.drawable.snackcateg);
        cate5.setImageResource(R.drawable.dinnercateg);
        cate6.setImageResource(R.drawable.drinkscateg);
        cate7.setImageResource(R.drawable.dessertcateg);
        cate8.setImageResource(R.drawable.marketcateg);
        ctg3.setText(R.string.almoco);
        ctg4.setText(R.string.lanches);
        ctg5.setText(R.string.dinner);
        ctg6.setText(R.string.bebidas);
        ctg7.setText(R.string.sobremesas);
        ctg8.setText(R.string.mercado);
    }

    private void hideArrows(){
        carouselView.setVisibility(View.GONE);
        txttitle0.setVisibility(View.GONE);
        txt_title.setVisibility(View.GONE);
        txt_title1.setVisibility(View.GONE);
        txt_title2.setVisibility(View.GONE);
        txt_titleDinner.setVisibility(View.GONE);
        txt_titleDrinks.setVisibility(View.GONE);
        txt_titleDesserts.setVisibility(View.GONE);
        txt_titleMarket.setVisibility(View.GONE);
        txt_titleMore.setVisibility(View.GONE);
        highlightarrow.setVisibility(View.GONE);
        icon_arrow1.setVisibility(View.GONE);
        icon_arrow2.setVisibility(View.GONE);
        icon_arrow_promotions.setVisibility(View.GONE);
        icon_arrowDinner.setVisibility(View.GONE);
        icon_arrowDrinks.setVisibility(View.GONE);
        icon_arrowDesserts.setVisibility(View.GONE);
        icon_arrowMarket.setVisibility(View.GONE);
        icon_arrowMore.setVisibility(View.GONE);
    }

    private void showArrows(){
        embreve.setVisibility(View.GONE);
        recyclerviewHighLights.setVisibility(View.VISIBLE);
        promotionRv.setVisibility(View.VISIBLE);
        lunchRv.setVisibility(View.VISIBLE);
        snacksRv.setVisibility(View.VISIBLE);
        dinnerRv.setVisibility(View.VISIBLE);
        drinksRv.setVisibility(View.VISIBLE);
        desertsRv.setVisibility(View.VISIBLE);
        marketRv.setVisibility(View.VISIBLE);
        moreRv.setVisibility(View.VISIBLE);
        embreve.setVisibility(View.GONE);
        carouselView.setVisibility(View.VISIBLE);
        clcategories.setVisibility(View.GONE);
        brandsRv.setVisibility(View.GONE);
        txttitle0.setVisibility(View.VISIBLE);
        txt_title.setVisibility(View.VISIBLE);
        txt_title1.setVisibility(View.VISIBLE);
        txt_title2.setVisibility(View.VISIBLE);
        txt_titleDinner.setVisibility(View.VISIBLE);
        txt_titleDrinks.setVisibility(View.VISIBLE);
        txt_titleDesserts.setVisibility(View.VISIBLE);
        txt_titleMarket.setVisibility(View.VISIBLE);
        txt_titleMore.setVisibility(View.VISIBLE);
        highlightarrow.setVisibility(View.VISIBLE);
        icon_arrow1.setVisibility(View.VISIBLE);
        icon_arrow2.setVisibility(View.VISIBLE);
        icon_arrow_promotions.setVisibility(View.VISIBLE);
        icon_arrowDinner.setVisibility(View.VISIBLE);
        icon_arrowDrinks.setVisibility(View.VISIBLE);
        icon_arrowDesserts.setVisibility(View.VISIBLE);
        icon_arrowMarket.setVisibility(View.VISIBLE);
        icon_arrowMore.setVisibility(View.VISIBLE);
    }

    private void myClickItem(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.restaurants:
                bottomNPosition = "0";
                if (Objects.equals(tabPosition, "0")) {
                    hideArrows();
                    txt_title1.setText(R.string.almoco);
                    txt_title2.setText(R.string.lanches);
                    txt_titleDinner.setText(R.string.dinner);
                    txt_titleDrinks.setText(R.string.bebidas);
                    txt_titleDesserts.setText(R.string.sobremesas);
                    txt_titleMarket.setText(R.string.mercado);
                    dietNews();
                }
                if (Objects.equals(tabPosition, "1")) {
                    hideArrows();
                    txt_title1.setText(R.string.almoco);
                    txt_title2.setText(R.string.lanches);
                    txt_titleDinner.setText(R.string.dinner);
                    txt_titleDrinks.setText(R.string.bebidas);
                    txt_titleDesserts.setText(R.string.sobremesas);
                    txt_titleMarket.setText(R.string.mercado);
                    recyclerviewHighLights.setVisibility(View.VISIBLE);
                    promotionRv.setVisibility(View.VISIBLE);
                    lunchRv.setVisibility(View.VISIBLE);
                    snacksRv.setVisibility(View.VISIBLE);
                    dinnerRv.setVisibility(View.VISIBLE);
                    drinksRv.setVisibility(View.VISIBLE);
                    desertsRv.setVisibility(View.VISIBLE);
                    marketRv.setVisibility(View.VISIBLE);
                    moreRv.setVisibility(View.VISIBLE);
                    dietNews();
                }
                if (Objects.equals(tabPosition, "2")) {
                    dietMail();
                }
                if (Objects.equals(tabPosition, "3")) {
                    hideArrows();
                    dietImported();
                }
                if (Objects.equals(tabPosition, "4")) {
                    hideArrows();
                    txt_title1.setText(R.string.almoco);
                    txt_title2.setText(R.string.lanches);
                    txt_titleDinner.setText(R.string.dinner);
                    txt_titleDrinks.setText(R.string.bebidas);
                    txt_titleDesserts.setText(R.string.sobremesas);
                    txt_titleMarket.setText(R.string.mercado);
                    recyclerviewHighLights.setVisibility(View.VISIBLE);
                    promotionRv.setVisibility(View.VISIBLE);
                    lunchRv.setVisibility(View.VISIBLE);
                    snacksRv.setVisibility(View.VISIBLE);
                    dinnerRv.setVisibility(View.VISIBLE);
                    drinksRv.setVisibility(View.VISIBLE);
                    desertsRv.setVisibility(View.VISIBLE);
                    marketRv.setVisibility(View.VISIBLE);
                    moreRv.setVisibility(View.VISIBLE);
                    dietNews();
                }
                if (Objects.equals(tabPosition, "5")) {
                    hideArrows();
                    carouselView.setVisibility(View.GONE);
                    loadAllBrands("Diet", city, state, country);
                }
                if (Objects.equals(tabPosition, "6")) {
                    hideArrows();
                    categoriesDiet();
                }
                break;
            case R.id.pharmacys:
                bottomNPosition = "1";
                if (Objects.equals(tabPosition, "0")) {
                    hideArrows();
                    pharmacyNews();
                }
                    if (Objects.equals(tabPosition, "1")) {
                        hideArrows();
                    pharmacyNews();
                }
                if (Objects.equals(tabPosition, "2")) {
                    pharmacyMail();               }
                if (Objects.equals(tabPosition, "3")) {
                    hideArrows();
                    pharmacyImported();
                }
                if (Objects.equals(tabPosition, "4")) {
                    hideArrows();
                    pharmacyNews();
                }
                if (Objects.equals(tabPosition, "5")) {
                    hideArrows();
                    carouselView.setVisibility(View.GONE);
                    loadAllBrands("Pharmacy",city, state, country);
                }
                    if (Objects.equals(tabPosition, "6")) {
                        hideArrows();
                    categoriesPharmacy();
                }
                break;
            case R.id.closet:
                bottomNPosition = "2";
                if (Objects.equals(tabPosition, "0")) {
                    hideArrows();
                    closetNews();
                }
                if (Objects.equals(tabPosition, "1")) {
                    hideArrows();
                    closetNews();
                }
                if (Objects.equals(tabPosition, "2")) {
                    closetMail();              }
                if (Objects.equals(tabPosition, "3")) {
                    hideArrows();
                    closetImported();               }
                if (Objects.equals(tabPosition, "4")) {
                    hideArrows();
                    closetNews();
                }
                if (Objects.equals(tabPosition, "5")) {
                    hideArrows();
                    carouselView.setVisibility(View.GONE);
                    loadAllBrands("Closet",city, state, country);
                }
                if (Objects.equals(tabPosition, "6")) {
                    hideArrows();
                    categoriesCloset();
                }
                break;
            case R.id.more:
                bottomNPosition = "3";
                if (Objects.equals(tabPosition, "0")) {
                    hideArrows();
                    moreNews();
                }
                if (Objects.equals(tabPosition, "1")) {
                    hideArrows();
                    moreNews();
                }
                if (Objects.equals(tabPosition, "2")) {
                    hideArrows();
                    moreMail();             }
                if (Objects.equals(tabPosition, "3")) {
                    hideArrows();
                    moreImported();             }
                if (Objects.equals(tabPosition, "4")) {
                    hideArrows();
                    moreNews();
                }
                if (Objects.equals(tabPosition, "5")) {
                    hideArrows();
                    carouselView.setVisibility(View.GONE);
                    loadAllBrands("Others",city, state, country);
                }
                if (Objects.equals(tabPosition, "6")) {
                    hideArrows();
                    categoriesMore();
                }
                break;
            case R.id.services:
                bottomNPosition = "4";
                if (Objects.equals(tabPosition, "0")) {
                    servicesNews();
                }
                if (Objects.equals(tabPosition, "1")) {
                    servicesNews();
                }
                if (Objects.equals(tabPosition, "2")) {
                    servicesMail();
                }
                if (Objects.equals(tabPosition, "3")) {
                    hideArrows();
                    servicesImported();            }
                if (Objects.equals(tabPosition, "4")) {
                    servicesNews();
                }
                if (Objects.equals(tabPosition, "5")) {
                    hideArrows();
                    carouselView.setVisibility(View.GONE);
                    loadAllBrands("Services",city, state, country);
                }
                if (Objects.equals(tabPosition, "6")) {
                    hideArrows();
                    categoriesServices();
                }
                break;
        }
    }

    private void loadPersonalInfo() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        String token = task.getResult();
                        System.out.println("TOKEN "+ token);
                    }
                });

        fAuth = FirebaseAuth.getInstance();
        String userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String name = document.getString("fName");
                            String avatar = document.getString("avatar");
                            String estat = document.getString("state");
                            String cit = document.getString("city");
                            String countr = document.getString("country");

                            if(name==""){
                                logout();
                            }
                            if(cit==""){
                                logout();
                            }
                            if(cit=="null"){
                                logout();
                            }
                            if(name=="null"){
                                logout();
                            }
                            if (name==null){
                                logout();
                            }
                            if(cit==null){
                                logout();
                            }

                            floatingButtonCard.setVisibility(View.GONE);

                            if (Objects.equals(avatar, "")) {
                                floatingButtonCard.setVisibility(View.VISIBLE);
                                avatar = "error";
                            }
                                city = cit;
                                state = estat;
                                country = countr;
                                NavigationView navigationView = findViewById(R.id.navigationView);
                                View header = navigationView.getHeaderView(0);
                                ShapeableImageView profileimg = header.findViewById(R.id.profileimg);
                                TextView location = header.findViewById(R.id.location);
                                TextView nameUser = header.findViewById(R.id.nameUser);
                                nameUser.setText(name);
                                location.setText(country + " - " + state + ", " + city);


                            if (avatar == null){
                                avatar = "error";
                            }
                                switch (avatar) {
                                    case "pig":
                                        try {
                                            Picasso.get().load(R.drawable.ppig).placeholder(R.drawable.loading).into(profileimgmini);
                                        } catch (Exception e) {
                                            profileimgmini.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
                                        }
                                        try {
                                            Picasso.get().load(R.drawable.ppig).placeholder(R.drawable.loading).into(profileimg);
                                        } catch (Exception e) {
                                            profileimg.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
                                        }
                                        break;
                                    case "cow":
                                        try {
                                            Picasso.get().load(R.drawable.pcow).placeholder(R.drawable.loading).into(profileimgmini);
                                        } catch (Exception e) {
                                            profileimgmini.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
                                        }
                                        try {
                                            Picasso.get().load(R.drawable.pcow).placeholder(R.drawable.loading).into(profileimg);
                                        } catch (Exception e) {
                                            profileimg.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
                                        }
                                        break;
                                    case "sheep":
                                        try {
                                            Picasso.get().load(R.drawable.psheep).placeholder(R.drawable.loading).into(profileimgmini);
                                        } catch (Exception e) {
                                            profileimgmini.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
                                        }
                                        try {
                                            Picasso.get().load(R.drawable.psheep).placeholder(R.drawable.loading).into(profileimg);
                                        } catch (Exception e) {
                                            profileimg.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
                                        }
                                        break;
                                    case "fish":
                                        try {
                                            Picasso.get().load(R.drawable.pfish).placeholder(R.drawable.loading).into(profileimgmini);
                                        } catch (Exception e) {
                                            profileimgmini.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
                                        }
                                        try {
                                            Picasso.get().load(R.drawable.pfish).placeholder(R.drawable.loading).into(profileimg);
                                        } catch (Exception e) {
                                            profileimg.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
                                        }
                                        break;
                                    case "goat":
                                        try {
                                            Picasso.get().load(R.drawable.pgoat).placeholder(R.drawable.loading).into(profileimgmini);
                                        } catch (Exception e) {
                                            profileimgmini.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
                                        }
                                        try {
                                            Picasso.get().load(R.drawable.pgoat).placeholder(R.drawable.loading).into(profileimg);
                                        } catch (Exception e) {
                                            profileimg.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
                                        }
                                        break;
                                    case "horse":
                                        try {
                                            Picasso.get().load(R.drawable.phorse).placeholder(R.drawable.loading).into(profileimgmini);
                                        } catch (Exception e) {
                                            profileimgmini.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
                                        }
                                        try {
                                            Picasso.get().load(R.drawable.phorse).placeholder(R.drawable.loading).into(profileimg);
                                        } catch (Exception e) {
                                            profileimg.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
                                        }
                                        break;
                                    case "bee":
                                        try {
                                            Picasso.get().load(R.drawable.pbee).placeholder(R.drawable.loading).into(profileimgmini);
                                        } catch (Exception e) {
                                            profileimgmini.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
                                        }
                                        try {
                                            Picasso.get().load(R.drawable.pbee).placeholder(R.drawable.loading).into(profileimg);
                                        } catch (Exception e) {
                                            profileimg.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
                                        }
                                        break;
                                    case "rooster":
                                        try {
                                            Picasso.get().load(R.drawable.pchicken).placeholder(R.drawable.loading).into(profileimgmini);
                                        } catch (Exception e) {
                                            profileimgmini.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
                                        }
                                        try {
                                            Picasso.get().load(R.drawable.pchicken).placeholder(R.drawable.loading).into(profileimg);
                                        } catch (Exception e) {
                                            profileimg.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
                                        }
                                        break;
                                    case "rat":
                                        try {
                                            Picasso.get().load(R.drawable.prat).placeholder(R.drawable.loading).into(profileimgmini);
                                        } catch (Exception e) {
                                            profileimgmini.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
                                        }
                                        try {
                                            Picasso.get().load(R.drawable.prat).placeholder(R.drawable.loading).into(profileimg);
                                        } catch (Exception e) {
                                            profileimg.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
                                        }
                                        break;
                                    case "rabbit":
                                        try {
                                            Picasso.get().load(R.drawable.prabbit).placeholder(R.drawable.loading).into(profileimgmini);
                                        } catch (Exception e) {
                                            profileimgmini.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
                                        }
                                        try {
                                            Picasso.get().load(R.drawable.prabbit).placeholder(R.drawable.loading).into(profileimg);
                                        } catch (Exception e) {
                                            profileimg.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
                                        }
                                        break;
                                    case "unknown":
                                        try {
                                            Picasso.get().load(R.drawable.unknown).placeholder(R.drawable.loading).into(profileimgmini);
                                        } catch (Exception e) {
                                            profileimgmini.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
                                        }
                                        try {
                                            Picasso.get().load(R.drawable.unknown).placeholder(R.drawable.loading).into(profileimg);
                                        } catch (Exception e) {
                                            profileimg.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
                                        }
                                        break;
                                    case "error":
                                        try {
                                            Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(profileimgmini);
                                        } catch (Exception e) {
                                            profileimgmini.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
                                        }
                                        try {
                                            Picasso.get().load(R.drawable.error).placeholder(R.drawable.loading).into(profileimg);
                                        } catch (Exception e) {
                                            profileimg.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
                                        }
                                        break;
                                }
                        }
                    }
                }
            });

    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();//logout
        Toast.makeText(Outros.this, R.string.logout, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.aboutustxt);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("lemubitA", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void testUserCurrent() {
        if ( FirebaseAuth.getInstance().getCurrentUser() != null ) {

            FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
            DocumentReference docIdRef = rootRef.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            getPlaceuser();
                            loadPersonalInfo();
                            getToken();
                        } else {
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(getApplicationContext(), Outros.class));
                            finish();
                        }
                    } else {
                        Log.d(TAG, "Failed with: ", task.getException());
                    }
                }
            });

            if(!hasOpened){
                createNotificationChannel();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ZYPUSH "+firebaseAuth.getCurrentUser().getUid());
                ref
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                titleP = ""+snapshot.child("title").getValue();
                                textP = ""+snapshot.child("text").getValue();

                                int Emoji = 0x1F49A;

                                String stringEmoji = String.valueOf(Emoji);

                                stringEmoji = (new String(Character.toChars(Emoji)));

                                PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                                        new Intent(getApplicationContext(), Outros.class), PendingIntent.FLAG_IMMUTABLE);

                                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "lemubitA")
                                        .setSmallIcon(R.drawable.veganosbrand)
                                        .setContentTitle(titleP + stringEmoji )
                                        .setContentIntent(contentIntent)
                                        .setContentText(textP)
                                        .setPriority(NotificationCompat.PRIORITY_HIGH);

                                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

                                int mNotificationId = 001;

                                NotificationManager mNotificationManager =
                                        (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                                mNotificationManager.notify(mNotificationId, builder.build());




                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                hasOpened = true;
            }

        } else {
            NavigationView navigationView = findViewById(R.id.navigationView);
            View header = navigationView.getHeaderView(0);
            TextView location = header.findViewById(R.id.location);
            TextView nameUser = header.findViewById(R.id.nameUser);
            ShapeableImageView profileimg = header.findViewById(R.id.profileimg);
            try {
                Picasso.get().load(R.drawable.unknown).placeholder(R.drawable.loading).into(profileimgmini);
            } catch (Exception e) {
                profileimgmini.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
            }
            try {
                Picasso.get().load(R.drawable.unknown).placeholder(R.drawable.loading).into(profileimg);
            } catch (Exception e) {
                profileimg.setImageDrawable(ContextCompat.getDrawable(Outros.this, error));
            }
            floatingButtonCard.setVisibility(View.VISIBLE);
            city = "Aracaju";
            state = "Sergipe";
            country = "Brasil";

            nameUser.setText(R.string.usuarioanonimo);
            location.setText(R.string.navpriv);
        }
        if ( city == null || city == "") {
            city = "Aracaju";
            state = "Sergipe";
            country = "Brasil";
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

                                }
                            });


                            return;
                        }

                    }
                });
    }

    public void loadAllBrands(final String filterBn, final String cida, final String estad, final String pai) {
        embreve.setVisibility(View.GONE);
        recyclerviewHighLights.setVisibility(View.GONE);
        promotionRv.setVisibility(View.GONE);
        lunchRv.setVisibility(View.GONE);
        snacksRv.setVisibility(View.GONE);
        dinnerRv.setVisibility(View.GONE);
        drinksRv.setVisibility(View.GONE);
        desertsRv.setVisibility(View.GONE);
        marketRv.setVisibility(View.GONE);
        moreRv.setVisibility(View.GONE);
        hideArrows();
        clcategories.setVisibility(View.GONE);
        brandsRv.setVisibility(View.VISIBLE);
        localList = new ArrayList<>();
        localList.clear();
        isLastItemReached17 = false;

        limit17 = 8;

        FirebaseFirestore nearby = FirebaseFirestore.getInstance();
        nearby.collection("Adm's")
                .whereEqualTo("type", filterBn)
                .whereEqualTo("country", pai)
                .whereEqualTo("city", cida)
                .whereEqualTo("state", estad)
                .limit(limit17)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelLocal modelLocal0 = document.toObject(ModelLocal.class);
                                localList.add(modelLocal0);
                                if(localList.isEmpty()){
                                    brandsRv.setVisibility(View.GONE);
                                    return;
                                }
                            }
                            adapterLocal = new AdapterLocal(Outros.this, localList);
                            brandsRv.setAdapter(adapterLocal);

                            brandsRv.getAdapter().notifyDataSetChanged();
                            brandsRv.scheduleLayoutAnimation();
                            if(task.getResult().size()!=0) {
                            lastVisible17 = task.getResult().getDocuments()
                                    .get(task.getResult().size() -1);
                            }
                            RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);

                                    if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                        isScrolling17 = true;
                                    }
                                }

                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);

                                    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                                    int visibleItemCount = layoutManager.getChildCount();
                                    int totalItemCount = layoutManager.getItemCount();

                                    while (isScrolling17 && (firstVisibleItem + visibleItemCount == totalItemCount) && !isLastItemReached17) {

                                        isScrolling17 = false;

                                        limit17 += limit17+5;

                                        Query nextQuery =  FirebaseFirestore.getInstance().collection("Adm's")
                                                .whereEqualTo("type", filterBn)
                                                .whereEqualTo("country", pai)
                                                .whereEqualTo("city", cida)
                                                .whereEqualTo("state", estad)
                                                .limit(limit17);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelLocal modelLocal = document.toObject(ModelLocal.class);
                                                    localList.add(modelLocal);
                                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.VERTICAL, false);
                                                    layoutManager.setStackFromEnd(false);
                                                    brandsRv.setLayoutManager(layoutManager);

                                                }

                                                adapterLocal.notifyDataSetChanged();

                                                if(task.getResult().size() < 150) { //limit
                                                    isLastItemReached17 = true;
                                                }
                                            }
                                        });
                                    }
                                }
                            };
                            brandsRv.addOnScrollListener(onScrollListener);
                        }
                    }
                });
    }


    public void getPlaceuser() {
       fAuth = FirebaseAuth.getInstance();
      String userId = fAuth.getCurrentUser().getUid();
        DocumentReference docRef = fStore.collection("Users").document(userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String estat = document.getString("state");
                        String cit = document.getString("city");
                        String countr = document.getString("country");

                        city = cit;
                        state = estat;
                        country = countr;
                        Log.d(TAG, city + state + country);
                    }
                }
            }
        });
    }

       public void loadAllHighlights(final String filter97) {
           productList = new ArrayList<>();
           isLastItemReached0 = false;
           limit0 = 15;
           FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
           rootRef.collection(filter97)
                   .orderBy("likes", Query.Direction.DESCENDING)
                   .limit(limit0)
                   .get()
                   .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                       @Override
                       public void onComplete(@NonNull Task<QuerySnapshot> task) {
                           productList.clear();
                           if (task.isSuccessful()) {
                               for (QueryDocumentSnapshot document : task.getResult()) {
                                   ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                   productList.add(modelProduct);
                                   if(productList.isEmpty()){
                                       txttitle0.setVisibility(View.GONE);
                                       highlightarrow.setVisibility(View.GONE);
                                       recyclerviewHighLights.setVisibility(View.GONE);
                                   }
                                   else{
                                       txttitle0.setVisibility(View.VISIBLE);
                                       highlightarrow.setVisibility(View.VISIBLE);
                                       recyclerviewHighLights.setVisibility(View.VISIBLE);
                                   }
                                   if(!Objects.equals(tabPosition, "4")) {
                                       Collections.shuffle(productList);
                                   }
                                   adapterProductBig = new AdapterProductBig(Outros.this, productList);
                                   recyclerviewHighLights.setAdapter(adapterProductBig);
                                       LinearLayoutManager layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                       layoutManager.setStackFromEnd(false);
                                       recyclerviewHighLights.setLayoutManager(layoutManager);
                               }
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
                                                       productList.add(modelProduct);
                                                       layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                                       layoutManager.setStackFromEnd(false);
                                                       recyclerviewHighLights.setLayoutManager(layoutManager);
                                                   }
                                                   adapterProductBig.notifyDataSetChanged();

                                                   if(task.getResult().size() < 150) { //limit
                                                       isLastItemReached0 = true;
                                                   }
                                               }
                                           });
                                       }
                                   }
                               };
                               recyclerviewHighLights.addOnScrollListener(onScrollListener);
                           }
                           if(productList.isEmpty()){
                               recyclerviewHighLights.setVisibility(View.GONE);
                               txttitle0.setVisibility(View.GONE);
                               highlightarrow.setVisibility(View.GONE);
                           }
                       }
                   });
       }

    public void loadAllHighlightsImported(final String filter97, final String userCountry) {
        productList44 = new ArrayList<>();

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection("Product's")
                .whereEqualTo("productType", filter97)
                .whereNotEqualTo("userCountry", userCountry)
                .whereEqualTo("internationalMail", "true")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList44.add(modelProduct);
                                if(tabPosition != "4") {Collections.shuffle(productList44);}
                                adapterProductBig = new AdapterProductBig(Outros.this, productList44);
                                recyclerviewHighLights.setAdapter(adapterProductBig);
                            }
                        }
                        if(productList44.isEmpty()){
                        }
                    }
                });
    }

    public void loadAllHighlightsNational(ArrayList pl0, final String filter97, final String cidad, final String userCountry) {
        pl0 = new ArrayList<>();
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        ArrayList finalPl = pl0;
        rootRef.collection("Product's")
                .whereEqualTo("productType", filter97)
                .whereEqualTo("productCountry", userCountry)
                .whereEqualTo("nationalMail", "true")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                finalPl.add(modelProduct);
                                if(!Objects.equals(tabPosition, "4")) {Collections.shuffle(finalPl);}
                                adapterProductBig = new AdapterProductBig(Outros.this, finalPl);
                                recyclerviewHighLights.setAdapter(adapterProductBig);
                            }
                        }
                    }
                });
    }

    public void loadAllPromotions(final String selected1, final String filter1) {
        productList1 = new ArrayList<>();
        isLastItemReached = false;

        limit = 9;

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection(selected1)
                .whereEqualTo("productCity", city)
                .whereEqualTo("productType", filter1)
                .whereEqualTo("productCategory",selected1)
                .limit(limit)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        productList1.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList1.add(modelProduct);
                                if(productList1.isEmpty()){
                                    txt_title.setVisibility(View.GONE);
                                    icon_arrow_promotions.setVisibility(View.GONE);
                                    promotionRv.setVisibility(View.GONE);
                                    load.setVisibility(View.GONE);
                                    progressDialog.setVisibility(View.GONE);}
                                else{
                                    txt_title.setVisibility(View.VISIBLE);
                                    icon_arrow_promotions.setVisibility(View.VISIBLE);
                                    promotionRv.setVisibility(View.VISIBLE);
                                    load.setVisibility(View.GONE);
                                    progressDialog.setVisibility(View.GONE);
                                }
                            }
                            if(!Objects.equals(tabPosition, "4") & !Objects.equals(tabPosition, "1")) {
                                Collections.shuffle(productList1);
                            }
                            if(Objects.equals(tabPosition, "4")) {
                                layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, true);
                                layoutManager.setStackFromEnd(true);
                            }
                            else {
                                layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                layoutManager.setStackFromEnd(false);
                            }
                            promotionRv.setLayoutManager(layoutManager);
                            adapterPromotions = new AdapterPromotions(Outros.this, productList1);
                            promotionRv.setAdapter(adapterPromotions);
                            load.setVisibility(View.GONE);
                            progressDialog.setVisibility(View.GONE);

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

                                        Query nextQuery = FirebaseFirestore.getInstance().collection(selected1).whereEqualTo("productType", filter1)
                                                .whereEqualTo("productCategory",selected1)
                                             //   .startAfter(lastVisible)
                                                .limit(limit);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    productList1.add(modelProduct);
                                                        layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                                        layoutManager.setStackFromEnd(false);
                                                        promotionRv.setLayoutManager(layoutManager);
                                                }
                                                adapterPromotions.notifyDataSetChanged();

                                                if(task.getResult().size() < 150) { //limit
                                                    isLastItemReached = true;
                                                }
                                            }
                                        });
                                    }
                                }
                            };
                            promotionRv.addOnScrollListener(onScrollListener);

                        }
                        if(productList1.isEmpty()){
                            promotionRv.setVisibility(View.GONE);
                            txt_title.setVisibility(View.GONE);
                            icon_arrow_promotions.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void loadAllPromotionsServices(final String selected1) {
        serviceList10 = new ArrayList<ModelProduct>();
        isLastItemReached9 = false;

        limit9 = 9;

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection("Adm's")
                .whereEqualTo("categories", selected1)
                .whereEqualTo("country", country)
                .whereEqualTo("state", state)
                .whereEqualTo("city", city)
                .limit(limit9)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                serviceList10.add(modelProduct);
                                if(serviceList10.isEmpty()){
                                    txt_title.setVisibility(View.GONE);
                                    icon_arrow_promotions.setVisibility(View.GONE);
                                    promotionRv.setVisibility(View.GONE);
                                }
                                else{
                                    txt_title.setVisibility(View.VISIBLE);
                                    icon_arrow_promotions.setVisibility(View.VISIBLE);
                                    promotionRv.setVisibility(View.VISIBLE);
                                    load.setVisibility(View.GONE);
                                }
                                if(!Objects.equals(tabPosition, "4")) {Collections.shuffle(serviceList10);}
                                if(Objects.equals(tabPosition, "4")) {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, true);
                                    layoutManager.setStackFromEnd(true);
                                    promotionRv.setLayoutManager(layoutManager);
                                }
                                else {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                    layoutManager.setStackFromEnd(false);
                                    promotionRv.setLayoutManager(layoutManager);
                                }

                            }
                            adapterServices = new AdapterServices(Outros.this, serviceList10);
                            promotionRv.setAdapter(adapterServices);

                            if(task.getResult().size()!=0) {
                                lastVisible9 = task.getResult().getDocuments()
                                    .get(task.getResult().size() -1);}

                            RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);

                                    if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                        isScrolling9 = true;
                                    }
                                }

                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);

                                    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                                    int visibleItemCount = layoutManager.getChildCount();
                                    int totalItemCount = layoutManager.getItemCount();

                                    while (isScrolling9 && (firstVisibleItem + visibleItemCount == totalItemCount) && !isLastItemReached9) {

                                        isScrolling9 = false;

                                        limit9 += limit9+3;

                                        Query nextQuery =  FirebaseFirestore.getInstance().collection("Adm's")
                                                .whereEqualTo("categories", selected1)
                                                .whereEqualTo("country", country)
                                                .whereEqualTo("state", state)
                                                .whereEqualTo("city", city)
                                              //  .startAfter(lastVisible9)
                                                .limit(limit9);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    serviceList10.add(modelProduct);
                                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                                    layoutManager.setStackFromEnd(false);
                                                    promotionRv.setLayoutManager(layoutManager);

                                                }

                                                adapterServices.notifyDataSetChanged();

                                                if(task.getResult().size() < 150) { //limit
                                                    isLastItemReached9 = true;
                                                }
                                            }
                                        });
                                    }
                                }
                            };
                            promotionRv.addOnScrollListener(onScrollListener);
                        }
                        if(serviceList10.isEmpty()){
                            promotionRv.setVisibility(View.GONE);
                            txt_title.setVisibility(View.GONE);
                            icon_arrow_promotions.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void loadAllLunchs(final String selected2, final String filter2) {
        productList2 = new ArrayList<>();
        isLastItemReached2 = false;

        limit2 = 9;

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection(selected2)
                .whereEqualTo("productType", filter2)
                .whereEqualTo("productCategory",selected2)
                .whereEqualTo("productCity", city)
                .limit(limit2)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        productList2.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList2.add(modelProduct);
                                if(productList2.isEmpty()){
                                    txt_title1.setVisibility(View.GONE);
                                    icon_arrow1.setVisibility(View.GONE);
                                    lunchRv.setVisibility(View.GONE);
                                    load.setVisibility(View.GONE);
                                }
                                else{
                                    txt_title1.setVisibility(View.VISIBLE);
                                    icon_arrow1.setVisibility(View.VISIBLE);
                                    lunchRv.setVisibility(View.VISIBLE);
                                    load.setVisibility(View.GONE);
                                }
                                if(!Objects.equals(tabPosition, "4")) {
                                    Collections.shuffle(productList2);}
                                if(Objects.equals(tabPosition, "4")) {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, true);
                                    layoutManager.setStackFromEnd(true);
                                    lunchRv.setLayoutManager(layoutManager);
                                }
                                else {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                    layoutManager.setStackFromEnd(false);
                                    lunchRv.setLayoutManager(layoutManager);
                                }
                            }
                            adapterPromotions = new AdapterPromotions(Outros.this, productList2);
                            lunchRv.setAdapter(adapterPromotions);
                            if(task.getResult().size()!=0) {
                            lastVisible2 = task.getResult().getDocuments().get(task.getResult().size() -1);}
                            if (adapterPromotions.getItemCount() == 0) {
                                lunchRv.setVisibility(View.GONE);
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

                                    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                                    int visibleItemCount = layoutManager.getChildCount();
                                    int totalItemCount = layoutManager.getItemCount();

                                    while (isScrolling2 && (firstVisibleItem + visibleItemCount == totalItemCount) && !isLastItemReached2) {

                                        isScrolling2 = false;

                                        limit2 += limit2+3;

                                        Query nextQuery = FirebaseFirestore.getInstance().collection(selected2)
                                                .whereEqualTo("productType", filter2)
                                                .whereEqualTo("productCategory",selected2)
//                                                .startAfter(2)
                                                .limit(limit2);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    productList2.add(modelProduct);
                                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                                    layoutManager.setStackFromEnd(false);
                                                    lunchRv.setLayoutManager(layoutManager);

                                                }

                                                adapterPromotions.notifyDataSetChanged();

                                                if(task.getResult().size() < 150) { //limit
                                                    isLastItemReached2 = true;
                                                }
                                            }
                                        });
                                    }

                                }
                            };
                            lunchRv.addOnScrollListener(onScrollListener);

                        }
                        if(productList2.isEmpty()){
                            lunchRv.setVisibility(View.GONE);
                            txt_title1.setVisibility(View.GONE);
                            icon_arrow1.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void loadAllNutri(final String selected2) {
        serviceList2 = new ArrayList<ModelProduct>();
        isLastItemReached10 = false;

        limit10 = 9;

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection("Adm's")
                .whereEqualTo("categories", selected2)
                .whereEqualTo("country", country)
                .whereEqualTo("state", state)
                .whereEqualTo("city", city)
                .limit(limit10)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                serviceList2.add(modelProduct);
                                if(serviceList2.isEmpty()){
                                    txt_title1.setVisibility(View.GONE);
                                    icon_arrow1.setVisibility(View.GONE);
                                    lunchRv.setVisibility(View.GONE);
                                }
                                else{
                                    txt_title1.setVisibility(View.VISIBLE);
                                    icon_arrow1.setVisibility(View.VISIBLE);
                                    lunchRv.setVisibility(View.VISIBLE);
                                    load.setVisibility(View.GONE);
                                }
                                if(!Objects.equals(tabPosition, "4")) {Collections.shuffle(serviceList2);}
                                if(Objects.equals(tabPosition, "4")) {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, true);
                                    layoutManager.setStackFromEnd(true);
                                    lunchRv.setLayoutManager(layoutManager);
                                }
                                else {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                    layoutManager.setStackFromEnd(false);
                                    lunchRv.setLayoutManager(layoutManager);
                                }

                            }
                            adapterServices = new AdapterServices(Outros.this, serviceList2);
                            lunchRv.setAdapter(adapterServices);
                            if(task.getResult().size()!=0) {
                                lastVisible10 = task.getResult().getDocuments()
                                    .get(task.getResult().size() -1);}

                            RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);

                                    if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                        isScrolling10 = true;
                                    }
                                }

                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);

                                    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                                    int visibleItemCount = layoutManager.getChildCount();
                                    int totalItemCount = layoutManager.getItemCount();

                                    while (isScrolling10 && (firstVisibleItem + visibleItemCount == totalItemCount) && !isLastItemReached10) {

                                        isScrolling10 = false;

                                        limit10 += limit10+3;

                                        Query nextQuery =  FirebaseFirestore.getInstance().collection("Adm's")
                                                .whereEqualTo("categories", selected2)
                                                .whereEqualTo("country", country)
                                                .whereEqualTo("state", state)
                                                .whereEqualTo("city", city)
                                               // .startAfter(lastVisible10)
                                                .limit(limit10);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    serviceList2.add(modelProduct);
                                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                                    layoutManager.setStackFromEnd(false);
                                                    lunchRv.setLayoutManager(layoutManager);

                                                }

                                                adapterServices.notifyDataSetChanged();

                                                if(task.getResult().size() < 150) { //limit
                                                    isLastItemReached10 = true;
                                                }
                                            }
                                        });
                                    }
                                }
                            };
                            lunchRv.addOnScrollListener(onScrollListener);
                        }
                        if(serviceList2.isEmpty()){
                            lunchRv.setVisibility(View.GONE);
                            txt_title1.setVisibility(View.GONE);
                            icon_arrow1.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void loadAllSnacks(final String selected3, final String filter1) {
        productList6 = new ArrayList<>();

        isLastItemReached3 = false;

        limit3 = 9;

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection(selected3)
                .whereEqualTo("productCity", city)
                .whereEqualTo("productType", filter1)
                .whereEqualTo("productCategory",selected3)
                .limit(limit3)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        productList6.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList6.add(modelProduct);
                                if(productList6.isEmpty()){
                                    txt_title2.setVisibility(View.GONE);
                                    icon_arrow2.setVisibility(View.GONE);
                                    snacksRv.setVisibility(View.GONE);
                                    load.setVisibility(View.GONE);
                                }
                                else{
                                    txt_title2.setVisibility(View.VISIBLE);
                                    icon_arrow2.setVisibility(View.VISIBLE);
                                    snacksRv.setVisibility(View.VISIBLE);
                                    load.setVisibility(View.GONE);
                                }
                                if(!Objects.equals(tabPosition, "4")) {Collections.shuffle(productList6);}
                                adapterPromotions = new AdapterPromotions(Outros.this, productList6);
                                snacksRv.setAdapter(adapterPromotions);
                                if(Objects.equals(tabPosition, "4")) {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, true);
                                    layoutManager.setStackFromEnd(true);
                                    snacksRv.setLayoutManager(layoutManager);
                                }
                                else {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                    layoutManager.setStackFromEnd(false);
                                    snacksRv.setLayoutManager(layoutManager);
                                }
                                if (adapterPromotions.getItemCount() == 0) {
                                    snacksRv.setVisibility(View.GONE);
                                }
                            }
                            if(task.getResult().size()!=0) {
                                lastVisible3 = task.getResult().getDocuments()
                                    .get(task.getResult().size() -1);}

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

                                        limit3 += limit3+3;

                                        Query nextQuery = FirebaseFirestore.getInstance().collection(selected3).whereEqualTo("productType", filter1)
                                                .whereEqualTo("productCategory",selected3)
                                             //   .startAfter(lastVisible3)
                                                .limit(limit3);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    productList6.add(modelProduct);
                                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                                    layoutManager.setStackFromEnd(false);
                                                    snacksRv.setLayoutManager(layoutManager);

                                                }

                                                adapterPromotions.notifyDataSetChanged();

                                                if(task.getResult().size() < 150) { //limit
                                                    isLastItemReached3 = true;
                                                }
                                            }
                                        });
                                    }

                                }
                            };
                            snacksRv.addOnScrollListener(onScrollListener);
                        }
                        if(productList6.isEmpty()){
                            snacksRv.setVisibility(View.GONE);
                            txt_title2.setVisibility(View.GONE);
                            icon_arrow2.setVisibility(View.GONE);
                        }
                    }
                });
    }

   public void loadAllMedicos(final String selected2) {
        serviceList3 = new ArrayList<ModelProduct>();
       isLastItemReached11 = false;

       limit11 = 9;

       FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
       rootRef.collection("Adm's")
               .whereEqualTo("categories", selected2)
               .whereEqualTo("country", country)
               .whereEqualTo("state", state)
               .whereEqualTo("city", city)
               .limit(limit11)
               .get()
               .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       if (task.isSuccessful()) {
                           for (QueryDocumentSnapshot document : task.getResult()) {
                               ModelProduct modelProduct = document.toObject(ModelProduct.class);
                               serviceList3.add(modelProduct);
                               if(serviceList3.isEmpty()){
                                   txt_title2.setVisibility(View.GONE);
                                   icon_arrow2.setVisibility(View.GONE);
                                   snacksRv.setVisibility(View.GONE);
                               }
                               else{
                                   txt_title2.setVisibility(View.VISIBLE);
                                   icon_arrow2.setVisibility(View.VISIBLE);
                                   snacksRv.setVisibility(View.VISIBLE);
                                   load.setVisibility(View.GONE);
                               }
                               if(!Objects.equals(tabPosition, "4")) {Collections.shuffle(serviceList3);}
                               if(Objects.equals(tabPosition, "4")) {
                                   layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, true);
                                   layoutManager.setStackFromEnd(true);
                                   snacksRv.setLayoutManager(layoutManager);
                               }
                               else {
                                   layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                   layoutManager.setStackFromEnd(false);
                                   snacksRv.setLayoutManager(layoutManager);
                               }
                           }
                           adapterServices = new AdapterServices(Outros.this, serviceList3);
                           snacksRv.setAdapter(adapterServices);
                           if(task.getResult().size()!=0) {
                               lastVisible11 = task.getResult().getDocuments()
                                   .get(task.getResult().size() -1);}

                           RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
                               @Override
                               public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                   super.onScrollStateChanged(recyclerView, newState);

                                   if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                       isScrolling11 = true;
                                   }
                               }

                               @Override
                               public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                   super.onScrolled(recyclerView, dx, dy);

                                   int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                                   int visibleItemCount = layoutManager.getChildCount();
                                   int totalItemCount = layoutManager.getItemCount();

                                   while (isScrolling11 && (firstVisibleItem + visibleItemCount == totalItemCount) && !isLastItemReached11) {

                                       isScrolling11 = false;

                                       limit11 += limit11+3;

                                       Query nextQuery =  FirebaseFirestore.getInstance().collection("Adm's")
                                               .whereEqualTo("categories", selected2)
                                               .whereEqualTo("country", country)
                                               .whereEqualTo("state", state)
                                               .whereEqualTo("city", city)
                                               //.startAfter(lastVisible11)
                                               .limit(limit11);
                                       nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               for (QueryDocumentSnapshot document : task.getResult()) {
                                                   ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                   serviceList3.add(modelProduct);
                                                   layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                                   layoutManager.setStackFromEnd(false);
                                                   snacksRv.setLayoutManager(layoutManager);

                                               }

                                               adapterServices.notifyDataSetChanged();

                                               if(task.getResult().size() < 150) { //limit
                                                   isLastItemReached11 = true;
                                               }
                                           }
                                       });
                                   }
                               }
                           };
                           snacksRv.addOnScrollListener(onScrollListener);
                       }
                       if(serviceList3.isEmpty()){
                           snacksRv.setVisibility(View.GONE);
                           txt_title2.setVisibility(View.GONE);
                           icon_arrow2.setVisibility(View.GONE);
                       }
                   }
               });
   }

    public void loadAllDinners(final String selected4, final String filter1) {
        productList9 = new ArrayList<>();
        isLastItemReached4 = false;

        limit4 = 9;

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection(selected4)
                .whereEqualTo("productCity", city)
                .whereEqualTo("productType", filter1)
                .whereEqualTo("productCategory",selected4)
                .limit(limit4)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        productList9.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList9.add(modelProduct);
                                if(productList9.isEmpty()){
                                    txt_titleDinner.setVisibility(View.GONE);
                                    icon_arrowDinner.setVisibility(View.GONE);
                                    dinnerRv.setVisibility(View.GONE);
                                    load.setVisibility(View.GONE);
                                }
                                else{
                                    txt_titleDinner.setVisibility(View.VISIBLE);
                                    icon_arrowDinner.setVisibility(View.VISIBLE);
                                    dinnerRv.setVisibility(View.VISIBLE);
                                    load.setVisibility(View.GONE);
                                }
                                if(!Objects.equals(tabPosition, "4")) {Collections.shuffle(productList9);}

                                if(Objects.equals(tabPosition, "4")) {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, true);
                                    layoutManager.setStackFromEnd(true);
                                    dinnerRv.setLayoutManager(layoutManager);
                                }
                                else {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                    layoutManager.setStackFromEnd(false);
                                    dinnerRv.setLayoutManager(layoutManager);
                                }
                            }
                            adapterPromotions = new AdapterPromotions(Outros.this, productList9);
                            dinnerRv.setAdapter(adapterPromotions);
                            if(task.getResult().size()!=0) {
                                lastVisible4 = task.getResult().getDocuments()
                                        .get(task.getResult().size() - 1);
                            }
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

                                        limit4 += limit4+3;

                                        Query nextQuery = FirebaseFirestore.getInstance().collection(selected4).whereEqualTo("productType", filter1)
                                                .whereEqualTo("productCategory",selected4)
                                             //   .startAfter(lastVisible4)
                                                .limit(limit4);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    productList9.add(modelProduct);
                                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                                    layoutManager.setStackFromEnd(false);
                                                    dinnerRv.setLayoutManager(layoutManager);

                                                }

                                                adapterPromotions.notifyDataSetChanged();

                                                if(task.getResult().size() < 150) { //limit
                                                    isLastItemReached4 = true;
                                                }
                                            }
                                        });
                                    }

                                }
                            };
                            dinnerRv.addOnScrollListener(onScrollListener);
                        }
                        if(productList9.isEmpty()){
                            dinnerRv.setVisibility(View.GONE);
                            txt_titleDinner.setVisibility(View.GONE);
                            icon_arrowDinner.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void loadAllBarbers(final String selected2) {
        serviceList4 = new ArrayList<ModelProduct>();
        isLastItemReached12 = false;

        limit12 = 9;

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection("Adm's")
                .whereEqualTo("categories", selected2)
                .whereEqualTo("country", country)
                .whereEqualTo("state", state)
                .whereEqualTo("city", city)
                .limit(limit12)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                serviceList4.add(modelProduct);
                                if(serviceList4.isEmpty()){
                                    txt_titleDinner.setVisibility(View.GONE);
                                    icon_arrowDinner.setVisibility(View.GONE);
                                    dinnerRv.setVisibility(View.GONE);
                                }
                                else{
                                    txt_titleDinner.setVisibility(View.VISIBLE);
                                    icon_arrowDinner.setVisibility(View.VISIBLE);
                                    dinnerRv.setVisibility(View.VISIBLE);
                                    load.setVisibility(View.GONE);
                                }
                                if(!Objects.equals(tabPosition, "4")) {Collections.shuffle(serviceList4);}

                                if(Objects.equals(tabPosition, "4")) {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, true);
                                    layoutManager.setStackFromEnd(true);
                                    dinnerRv.setLayoutManager(layoutManager);
                                }
                                else {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                    layoutManager.setStackFromEnd(false);
                                    dinnerRv.setLayoutManager(layoutManager);
                                }
                            }
                            adapterServices = new AdapterServices(Outros.this, serviceList4);
                            dinnerRv.setAdapter(adapterServices);

                            if(task.getResult().size()!=0) {
                                lastVisible12 = task.getResult().getDocuments()
                                        .get(task.getResult().size() - 1);
                            }
                            RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);

                                    if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                        isScrolling12 = true;
                                    }
                                }

                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);

                                    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                                    int visibleItemCount = layoutManager.getChildCount();
                                    int totalItemCount = layoutManager.getItemCount();

                                    while (isScrolling12 && (firstVisibleItem + visibleItemCount == totalItemCount) && !isLastItemReached12) {

                                        isScrolling12 = false;

                                        limit12 += limit12+3;

                                        Query nextQuery =  FirebaseFirestore.getInstance().collection("Adm's")
                                                .whereEqualTo("categories", selected2)
                                                .whereEqualTo("country", country)
                                                .whereEqualTo("state", state)
                                                .whereEqualTo("city", city)
                                             //   .startAfter(lastVisible12)
                                                .limit(limit12);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    serviceList4.add(modelProduct);
                                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                                    layoutManager.setStackFromEnd(false);
                                                    dinnerRv.setLayoutManager(layoutManager);

                                                }

                                                adapterServices.notifyDataSetChanged();

                                                if(task.getResult().size() < 150) { //limit
                                                    isLastItemReached12 = true;
                                                }
                                            }
                                        });
                                    }
                                }
                            };
                            dinnerRv.addOnScrollListener(onScrollListener);
                        }
                        if(serviceList4.isEmpty()){
                            dinnerRv.setVisibility(View.GONE);
                            txt_titleDinner.setVisibility(View.GONE);
                            icon_arrowDinner.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void loadAllDrinks(final String selected5, final String filter1) {
        productList13 = new ArrayList<>();
        isLastItemReached5 = false;

        limit5 = 9;

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection(selected5)
                .whereEqualTo("productCity", city)
                .whereEqualTo("productType", filter1)
                .whereEqualTo("productCategory",selected5)
                .limit(limit5)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        productList13.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList13.add(modelProduct);
                                if(productList13.isEmpty()){
                                    txt_titleDrinks.setVisibility(View.GONE);
                                    icon_arrowDrinks.setVisibility(View.GONE);
                                    drinksRv.setVisibility(View.GONE);
                                    load.setVisibility(View.GONE);
                                }
                                else{
                                    txt_titleDrinks.setVisibility(View.VISIBLE);
                                    icon_arrowDrinks.setVisibility(View.VISIBLE);
                                    drinksRv.setVisibility(View.VISIBLE);
                                    load.setVisibility(View.GONE);
                                }
                                if(!Objects.equals(tabPosition, "4")) {Collections.shuffle(productList13);}
                                if(Objects.equals(tabPosition, "4")) {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, true);
                                    layoutManager.setStackFromEnd(true);
                                    drinksRv.setLayoutManager(layoutManager);
                                }
                                else {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                    layoutManager.setStackFromEnd(false);
                                    drinksRv.setLayoutManager(layoutManager);
                                }

                            }
                            adapterPromotions = new AdapterPromotions(Outros.this, productList13);
                            drinksRv.setAdapter(adapterPromotions);

                            if(task.getResult().size()!=0) {
                                lastVisible5 = task.getResult().getDocuments()
                                        .get(task.getResult().size() - 1);
                            }
                            RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);

                                    if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                        isScrolling5 = true;
                                    }
                                }

                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);

                                    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                                    int visibleItemCount = layoutManager.getChildCount();
                                    int totalItemCount = layoutManager.getItemCount();

                                    while (isScrolling5 && (firstVisibleItem + visibleItemCount == totalItemCount) && !isLastItemReached5) {

                                        isScrolling5 = false;

                                        limit5 += limit5+3;

                                        Query nextQuery = FirebaseFirestore.getInstance().collection(selected5).whereEqualTo("productType", filter1)
                                                .whereEqualTo("productCategory",selected5)
                                              //  .startAfter(lastVisible5)
                                                .limit(limit5);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    productList13.add(modelProduct);
                                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                                    layoutManager.setStackFromEnd(false);
                                                    drinksRv.setLayoutManager(layoutManager);

                                                }

                                                adapterPromotions.notifyDataSetChanged();

                                                if(task.getResult().size() < 150) { //limit
                                                    isLastItemReached5 = true;
                                                }
                                            }
                                        });
                                    }

                                }
                            };
                            drinksRv.addOnScrollListener(onScrollListener);
                        }
                        if(productList13.isEmpty()){
                            drinksRv.setVisibility(View.GONE);
                            txt_titleDrinks.setVisibility(View.GONE);
                            icon_arrowDrinks.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void loadAllTatuadores(final String selected2) {
        serviceList5 = new ArrayList<ModelProduct>();
        isLastItemReached13 = false;

        limit13 = 9;

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection("Adm's")
                .whereEqualTo("categories", selected2)
                .whereEqualTo("country", country)
                .whereEqualTo("state", state)
                .whereEqualTo("city", city)
                .limit(limit13)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                serviceList5.add(modelProduct);
                                if(serviceList5.isEmpty()){
                                    txt_titleDrinks.setVisibility(View.GONE);
                                    icon_arrowDrinks.setVisibility(View.GONE);
                                    drinksRv.setVisibility(View.GONE);
                                }
                                else{
                                    txt_titleDrinks.setVisibility(View.VISIBLE);
                                    icon_arrowDrinks.setVisibility(View.VISIBLE);
                                    drinksRv.setVisibility(View.VISIBLE);
                                    load.setVisibility(View.GONE);
                                }
                                if(!Objects.equals(tabPosition, "4")) {Collections.shuffle(serviceList5);}
                                if(Objects.equals(tabPosition, "4")) {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, true);
                                    layoutManager.setStackFromEnd(true);
                                    drinksRv.setLayoutManager(layoutManager);
                                }
                                else {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                    layoutManager.setStackFromEnd(false);
                                    drinksRv.setLayoutManager(layoutManager);
                                }

                            }
                            adapterServices = new AdapterServices(Outros.this, serviceList5);
                            drinksRv.setAdapter(adapterServices);

                            if(task.getResult().size()!=0) {
                                lastVisible13 = task.getResult().getDocuments()
                                    .get(task.getResult().size() -1);}

                            RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);

                                    if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                        isScrolling13 = true;
                                    }
                                }

                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);

                                    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                                    int visibleItemCount = layoutManager.getChildCount();
                                    int totalItemCount = layoutManager.getItemCount();

                                    while (isScrolling13 && (firstVisibleItem + visibleItemCount == totalItemCount) && !isLastItemReached13) {

                                        isScrolling13 = false;

                                        limit13 += limit13+3;

                                        Query nextQuery =  FirebaseFirestore.getInstance().collection("Adm's")
                                                .whereEqualTo("categories", selected2)
                                                .whereEqualTo("country", country)
                                                .whereEqualTo("state", state)
                                                .whereEqualTo("city", city)
                                            //    .startAfter(lastVisible13)
                                                .limit(limit13);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    serviceList5.add(modelProduct);
                                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                                    layoutManager.setStackFromEnd(false);
                                                    drinksRv.setLayoutManager(layoutManager);
                                                }

                                                adapterServices.notifyDataSetChanged();

                                                if(task.getResult().size() < 150) { //limit
                                                    isLastItemReached13 = true;
                                                }
                                            }
                                        });
                                    }
                                }
                            };
                            drinksRv.addOnScrollListener(onScrollListener);
                        }
                        if(serviceList5.isEmpty()){
                            drinksRv.setVisibility(View.GONE);
                            txt_titleDrinks.setVisibility(View.GONE);
                            icon_arrowDrinks.setVisibility(View.GONE);
                        } else {
                            drinksRv.setVisibility(View.VISIBLE);
                            txt_titleDrinks.setVisibility(View.VISIBLE);
                            icon_arrowDrinks.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    public void loadAllDeserts(final String selected6, final String filter1) {
        productList17 = new ArrayList<>();
        isLastItemReached6 = false;

        limit6 = 9;

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection(selected6)
                .whereEqualTo("productCity", city)
                .whereEqualTo("productType", filter1)
                .whereEqualTo("productCategory",selected6)
                .limit(limit6)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        productList17.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList17.add(modelProduct);
                                if(productList17.isEmpty()){
                                    desertsRv.setVisibility(View.GONE);
                                    txt_titleDesserts.setVisibility(View.GONE);
                                    icon_arrowDesserts.setVisibility(View.GONE);
                                    load.setVisibility(View.GONE);
                                }
                                else{
                                    txt_titleDesserts.setVisibility(View.VISIBLE);
                                    icon_arrowDesserts.setVisibility(View.VISIBLE);
                                    desertsRv.setVisibility(View.VISIBLE);
                                    load.setVisibility(View.GONE);
                                }
                                if(!Objects.equals(tabPosition, "4")) {Collections.shuffle(productList17);}
                                if(Objects.equals(tabPosition, "4")) {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, true);
                                    layoutManager.setStackFromEnd(true);
                                    desertsRv.setLayoutManager(layoutManager);
                                }
                                else {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                    layoutManager.setStackFromEnd(false);
                                    desertsRv.setLayoutManager(layoutManager);
                                }

                            }
                            adapterPromotions = new AdapterPromotions(Outros.this, productList17);
                            desertsRv.setAdapter(adapterPromotions);

                            if(task.getResult().size()!=0) {
                                lastVisible6 = task.getResult().getDocuments()
                                        .get(task.getResult().size() - 1);
                            }
                            RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);

                                    if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                        isScrolling6 = true;
                                    }
                                }

                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);

                                    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                                    int visibleItemCount = layoutManager.getChildCount();
                                    int totalItemCount = layoutManager.getItemCount();

                                    while (isScrolling6 && (firstVisibleItem + visibleItemCount == totalItemCount) && !isLastItemReached6) {

                                        isScrolling6 = false;

                                        limit6 += limit6+3;

                                        Query nextQuery = FirebaseFirestore.getInstance().collection(selected6).whereEqualTo("productType", filter1)
                                                .whereEqualTo("productCategory",selected6)
                                              //  .startAfter(lastVisible6)
                                                .limit(limit6);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    productList17.add(modelProduct);
                                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                                    layoutManager.setStackFromEnd(false);
                                                    desertsRv.setLayoutManager(layoutManager);

                                                }

                                                adapterPromotions.notifyDataSetChanged();

                                                if(task.getResult().size() < 150) { //limit
                                                    isLastItemReached6 = true;
                                                }
                                            }
                                        });
                                    }

                                }
                            };
                            desertsRv.addOnScrollListener(onScrollListener);
                        }
                        if(productList17.isEmpty()){
                            desertsRv.setVisibility(View.GONE);
                            txt_titleDesserts.setVisibility(View.GONE);
                            icon_arrowDesserts.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void loadAllHospedagens(final String selected2) {
        serviceList6 = new ArrayList<ModelProduct>();
        isLastItemReached14 = false;

        limit14 = 9;

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection("Adm's")
                .whereEqualTo("categories", selected2)
                .whereEqualTo("country", country)
                .whereEqualTo("type", "Services")
                .whereEqualTo("state", state)
                .whereEqualTo("city", city)
                .limit(limit14)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                serviceList6.add(modelProduct);
                                if(serviceList6.isEmpty()){
                                    desertsRv.setVisibility(View.GONE);
                                    txt_titleDesserts.setVisibility(View.GONE);
                                    icon_arrowDesserts.setVisibility(View.GONE);
                                }
                                else{
                                    txt_titleDesserts.setVisibility(View.VISIBLE);
                                    icon_arrowDesserts.setVisibility(View.VISIBLE);
                                    desertsRv.setVisibility(View.VISIBLE);
                                    load.setVisibility(View.GONE);
                                }
                                if(!Objects.equals(tabPosition, "4")) {Collections.shuffle(serviceList6);}

                                if(Objects.equals(tabPosition, "4")) {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, true);
                                    layoutManager.setStackFromEnd(true);
                                    desertsRv.setLayoutManager(layoutManager);
                                }
                                else {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                    layoutManager.setStackFromEnd(false);
                                    desertsRv.setLayoutManager(layoutManager);
                                }

                            }
                            adapterServices = new AdapterServices(Outros.this, serviceList6);
                            desertsRv.setAdapter(adapterServices);

                            if(task.getResult().size()!=0) {
                                lastVisible14 = task.getResult().getDocuments()
                                        .get(task.getResult().size() - 1);
                            }
                            RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);

                                    if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                        isScrolling14 = true;
                                    }
                                }

                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);

                                    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                                    int visibleItemCount = layoutManager.getChildCount();
                                    int totalItemCount = layoutManager.getItemCount();

                                    while (isScrolling14 && (firstVisibleItem + visibleItemCount == totalItemCount) && !isLastItemReached14) {

                                        isScrolling14 = false;

                                        limit14 += limit14+3;

                                        Query nextQuery =  FirebaseFirestore.getInstance().collection("Adm's")
                                                .whereEqualTo("categories", selected2)
                                                .whereEqualTo("country", country)
                                                .whereEqualTo("state", state)
                                                .whereEqualTo("city", city)
                                            //    .startAfter(lastVisible14)
                                                .limit(limit14);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    serviceList6.add(modelProduct);
                                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                                    layoutManager.setStackFromEnd(false);
                                                    desertsRv.setLayoutManager(layoutManager);

                                                }

                                                adapterServices.notifyDataSetChanged();

                                                if(task.getResult().size() < 150) { //limit
                                                    isLastItemReached14 = true;
                                                }
                                            }
                                        });
                                    }
                                }
                            };
                            desertsRv.addOnScrollListener(onScrollListener);
                        }
                        if(serviceList6.isEmpty()){
                            desertsRv.setVisibility(View.GONE);
                            txt_titleDesserts.setVisibility(View.GONE);
                            icon_arrowDesserts.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void loadAllMarket(final String selected7, final String filter1) {
        productList22 = new ArrayList<>();
        isLastItemReached7 = false;

        limit7 = 9;

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection(selected7)
                .whereEqualTo("productCity", city)
                .whereEqualTo("productType", filter1)
                .whereEqualTo("productCategory",selected7)
                .limit(limit7)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        productList22.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList22.add(modelProduct);if(!Objects.equals(tabPosition, "4")) {Collections.shuffle(productList22);}
                                if(productList22.isEmpty()){
                                    marketRv.setVisibility(View.GONE);
                                    txt_titleMarket.setVisibility(View.GONE);
                                    icon_arrowMarket.setVisibility(View.GONE);
                                    load.setVisibility(View.GONE);
                                }
                                else{
                                    txt_titleMarket.setVisibility(View.VISIBLE);
                                    icon_arrowMarket.setVisibility(View.VISIBLE);
                                    marketRv.setVisibility(View.VISIBLE);
                                    load.setVisibility(View.GONE);
                                }
                                if(Objects.equals(tabPosition, "4")) {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, true);
                                    layoutManager.setStackFromEnd(true);
                                    marketRv.setLayoutManager(layoutManager);
                                }
                                else {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                    layoutManager.setStackFromEnd(false);
                                    marketRv.setLayoutManager(layoutManager);
                                }
                            }
                            adapterPromotions = new AdapterPromotions(Outros.this, productList22);
                            marketRv.setAdapter(adapterPromotions);

                            if(task.getResult().size()!=0) {
                                lastVisible7 = task.getResult().getDocuments()
                                    .get(task.getResult().size() -1);}

                            RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);

                                    if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                        isScrolling7 = true;
                                    }
                                }

                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);

                                    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                                    int visibleItemCount = layoutManager.getChildCount();
                                    int totalItemCount = layoutManager.getItemCount();

                                    while (isScrolling7 && (firstVisibleItem + visibleItemCount == totalItemCount) && !isLastItemReached7) {

                                        isScrolling7 = false;

                                        limit7 += limit7+3;

                                        Query nextQuery = FirebaseFirestore.getInstance().collection(selected7).whereEqualTo("productType", filter1)
                                                .whereEqualTo("productCategory",selected7)
                                               // .startAfter(lastVisible7)
                                                .limit(limit7);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    productList22.add(modelProduct);
                                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                                    layoutManager.setStackFromEnd(false);
                                                    marketRv.setLayoutManager(layoutManager);

                                                }

                                                adapterPromotions.notifyDataSetChanged();

                                                if(task.getResult().size() < 150) { //limit
                                                    isLastItemReached7 = true;
                                                }
                                            }
                                        });
                                    }

                                }
                            };
                            marketRv.addOnScrollListener(onScrollListener);
                        }
                        if(productList22.isEmpty()){
                            txt_titleMarket.setVisibility(View.GONE);
                            marketRv.setVisibility(View.GONE);
                            icon_arrowMarket.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void loadAllAlimenticios(final String selected2) {
        serviceList7 = new ArrayList<ModelProduct>();
        isLastItemReached15 = false;

        limit15 = 9;

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection("Adm's")
                .whereEqualTo("categories", selected2)
                .whereEqualTo("country", country)
                .whereEqualTo("state", state)
                .whereEqualTo("city", city)
                .limit(limit15)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                serviceList7.add(modelProduct);
                                if(serviceList7.isEmpty()){
                                    marketRv.setVisibility(View.GONE);
                                    txt_titleMarket.setVisibility(View.GONE);
                                    icon_arrowMarket.setVisibility(View.GONE);
                                }
                                else{
                                    txt_titleMarket.setVisibility(View.VISIBLE);
                                    icon_arrowMarket.setVisibility(View.VISIBLE);
                                    marketRv.setVisibility(View.VISIBLE);
                                    load.setVisibility(View.GONE);
                                }
                                if(!Objects.equals(tabPosition, "4")) {Collections.shuffle(serviceList7);}
                                if(Objects.equals(tabPosition, "4")) {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, true);
                                    layoutManager.setStackFromEnd(true);
                                    marketRv.setLayoutManager(layoutManager);
                                }
                                else {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                    layoutManager.setStackFromEnd(false);
                                    marketRv.setLayoutManager(layoutManager);
                                }
                            }
                            adapterServices = new AdapterServices(Outros.this, serviceList7);
                            marketRv.setAdapter(adapterServices);

                            if(task.getResult().size()!=0) {
                                lastVisible15 = task.getResult().getDocuments()
                                        .get(task.getResult().size() -1);}

                            RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);

                                    if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                        isScrolling15 = true;
                                    }
                                }

                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);

                                    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                                    int visibleItemCount = layoutManager.getChildCount();
                                    int totalItemCount = layoutManager.getItemCount();

                                    while (isScrolling15 && (firstVisibleItem + visibleItemCount == totalItemCount) && !isLastItemReached15) {

                                        isScrolling15 = false;

                                        limit15 += limit15+3;

                                        Query nextQuery =  FirebaseFirestore.getInstance().collection("Adm's")
                                                .whereEqualTo("categories", selected2)
                                                .whereEqualTo("country", country)
                                                .whereEqualTo("state", state)
                                                .whereEqualTo("city", city)
                                            //    .startAfter(lastVisible15)
                                                .limit(limit15);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    serviceList7.add(modelProduct);
                                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                                    layoutManager.setStackFromEnd(false);
                                                    marketRv.setLayoutManager(layoutManager);

                                                }

                                                adapterServices.notifyDataSetChanged();

                                                if(task.getResult().size() < 150) { //limit
                                                    isLastItemReached15 = true;
                                                }
                                            }
                                        });
                                    }
                                }
                            };
                            marketRv.addOnScrollListener(onScrollListener);
                        }
                        if(serviceList7.isEmpty()){
                            txt_titleMarket.setVisibility(View.GONE);
                            marketRv.setVisibility(View.GONE);
                            icon_arrowMarket.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void loadAllMore(final String selected8, final String filter1) {
        productList26 = new ArrayList<>();
        isLastItemReached8 = false;

        limit8 = 9;

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection(selected8)
                .whereEqualTo("productCity", city)
                .whereEqualTo("productType", filter1)
                .whereEqualTo("productCategory",selected8)
                .limit(limit8)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        productList26.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList26.add(modelProduct);
                                if(productList26.isEmpty()){
                                    moreRv.setVisibility(View.GONE);
                                    txt_titleMore.setVisibility(View.GONE);
                                    icon_arrowMore.setVisibility(View.GONE);
                                }
                                else{
                                    txt_titleMore.setVisibility(View.VISIBLE);
                                    icon_arrowMore.setVisibility(View.VISIBLE);
                                    moreRv.setVisibility(View.VISIBLE);
                                    load.setVisibility(View.GONE);
                                }
                                if(!Objects.equals(tabPosition, "4")) {Collections.shuffle(productList26);}

                                if(Objects.equals(tabPosition, "4")) {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, true);
                                    layoutManager.setStackFromEnd(true);
                                    moreRv.setLayoutManager(layoutManager);
                                }
                                else {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                    layoutManager.setStackFromEnd(false);
                                    moreRv.setLayoutManager(layoutManager);
                                }
                            }
                            adapterPromotions = new AdapterPromotions(Outros.this, productList26);
                            moreRv.setAdapter(adapterPromotions);

                            if(task.getResult().size()!=0) {
                                lastVisible8 = task.getResult().getDocuments()
                                    .get(task.getResult().size() -1);}

                            RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);

                                    if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                        isScrolling8 = true;
                                    }
                                }

                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);

                                    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                                    int visibleItemCount = layoutManager.getChildCount();
                                    int totalItemCount = layoutManager.getItemCount();

                                    while (isScrolling8 && (firstVisibleItem + visibleItemCount == totalItemCount) && !isLastItemReached8) {

                                        isScrolling8 = false;

                                        limit8 += limit8+3;

                                        Query nextQuery = FirebaseFirestore.getInstance().collection(selected8).whereEqualTo("productType", filter1)
                                                .whereEqualTo("productCategory",selected8)
                                             //   .startAfter(lastVisible8)
                                                .limit(limit8);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    productList26.add(modelProduct);
                                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                                    layoutManager.setStackFromEnd(false);
                                                    moreRv.setLayoutManager(layoutManager);

                                                }

                                                adapterPromotions.notifyDataSetChanged();

                                                if(task.getResult().size() < 150) { //limit
                                                    isLastItemReached8 = true;
                                                }
                                            }
                                        });
                                    }
                                }
                            };
                            moreRv.addOnScrollListener(onScrollListener);
                        }
                        if(productList26.isEmpty()){
                            txt_titleMore.setVisibility(View.GONE);
                            moreRv.setVisibility(View.GONE);
                            icon_arrowMore.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void loadAllMoreServices(final String selected1) {
        serviceList8 = new ArrayList<ModelProduct>();
        isLastItemReached16 = false;

        limit16 = 9;

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection("Adm's")
                .whereEqualTo("categories", selected1)
                .whereEqualTo("country", country)
                .whereEqualTo("state", state)
                .whereEqualTo("city", city)
                .limit(limit16)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                serviceList8.add(modelProduct);
                                if(serviceList8.isEmpty()){
                                    moreRv.setVisibility(View.GONE);
                                    txt_titleMore.setVisibility(View.GONE);
                                    icon_arrowMore.setVisibility(View.GONE);
                                }
                                else{
                                    txt_titleMore.setVisibility(View.VISIBLE);
                                    icon_arrowMore.setVisibility(View.VISIBLE);
                                    moreRv.setVisibility(View.VISIBLE);
                                    load.setVisibility(View.GONE);
                                }
                                if(!Objects.equals(tabPosition, "4")) {Collections.shuffle(serviceList8);}
                                if(Objects.equals(tabPosition, "4")) {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, true);
                                    layoutManager.setStackFromEnd(true);
                                    moreRv.setLayoutManager(layoutManager);
                                }
                                else {
                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                    layoutManager.setStackFromEnd(false);
                                    moreRv.setLayoutManager(layoutManager);
                                }
                            }
                            adapterServices = new AdapterServices(Outros.this, serviceList8);
                            moreRv.setAdapter(adapterServices);

                            if(task.getResult().size()!=0) {
                                lastVisible16 = task.getResult().getDocuments()
                                        .get(task.getResult().size() - 1);
                            }
                            RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);

                                    if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                        isScrolling16 = true;
                                    }
                                }

                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);

                                    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                                    int visibleItemCount = layoutManager.getChildCount();
                                    int totalItemCount = layoutManager.getItemCount();

                                    while (isScrolling16 && (firstVisibleItem + visibleItemCount == totalItemCount) && !isLastItemReached16) {

                                        isScrolling16 = false;

                                        limit16 += limit16+3;

                                        Query nextQuery =  FirebaseFirestore.getInstance().collection("Adm's")
                                                .whereEqualTo("categories", selected1)
                                                .whereEqualTo("country", country)
                                                .whereEqualTo("state", state)
                                                .whereEqualTo("city", city)
                                               // .startAfter(lastVisible16)
                                                .limit(limit16);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    serviceList8.add(modelProduct);
                                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                                    layoutManager.setStackFromEnd(false);
                                                    moreRv.setLayoutManager(layoutManager);

                                                }

                                                adapterServices.notifyDataSetChanged();

                                                if(task.getResult().size() < 150) { //limit
                                                    isLastItemReached16 = true;
                                                }
                                            }
                                        });
                                    }
                                }
                            };
                            moreRv.addOnScrollListener(onScrollListener);
                        }
                        if(serviceList8.isEmpty()){
                            txt_titleMore.setVisibility(View.GONE);
                            moreRv.setVisibility(View.GONE);
                            icon_arrowMore.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void loadAllNationalMail(ArrayList pL, final RecyclerView recyV,final String selc, final String filter1, final String cidad, final String userCountry) {
        pL = new ArrayList<>();

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        ArrayList finalPL = pL;
        rootRef.collection(selc)
                .whereEqualTo("productCategory",selc)
                .whereEqualTo("productType", filter1)
                .whereEqualTo("productCountry", userCountry)
                .whereEqualTo("nationalMail", "true")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                finalPL.add(modelProduct);
                            }
                            adapterPromotions = new AdapterPromotions(Outros.this, finalPL);
                            recyV.setAdapter(adapterPromotions);
                            if(task.getResult().size()!=0) {
                                lastVisible16 = task.getResult().getDocuments()
                                        .get(task.getResult().size() - 1);
                            }
                            RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);

                                    if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                                        isScrolling16 = true;
                                    }
                                }

                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);

                                    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                                    int visibleItemCount = layoutManager.getChildCount();
                                    int totalItemCount = layoutManager.getItemCount();

                                    while (isScrolling16 && (firstVisibleItem + visibleItemCount == totalItemCount) && !isLastItemReached16) {

                                        isScrolling16 = false;

                                        limit16 += limit16+3;

                                        Query nextQuery =  FirebaseFirestore.getInstance().collection(selc)
                                                .whereEqualTo("productCategory",selc)
                                                .whereEqualTo("productType", filter1)
                                                .whereEqualTo("productCountry", userCountry)
                                                .whereEqualTo("nationalMail", "true")
                                              //  .startAfter(lastVisible16)
                                                .limit(limit16);
                                        nextQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                                    finalPL.add(modelProduct);
                                                    layoutManager = new LinearLayoutManager(Outros.this, LinearLayoutManager.HORIZONTAL, false);
                                                    layoutManager.setStackFromEnd(false);
                                                    recyV.setLayoutManager(layoutManager);

                                                }

                                                adapterPromotions.notifyDataSetChanged();

                                                if(task.getResult().size() < 150) { //limit
                                                    isLastItemReached16 = true;
                                                }
                                            }
                                        });
                                    }
                                }
                            };
                            recyV.addOnScrollListener(onScrollListener);
                        }
                        if(finalPL.isEmpty()){
                            switch (selc) {
                                case "Promoções" :
                                    txt_title.setVisibility(View.GONE);
                                    promotionRv.setVisibility(View.GONE);
                                    icon_arrow_promotions.setVisibility(View.GONE);
                                    break;
                                case "Almoço":
                                case "Saúde":
                                case "Brechó":
                                case "Casa":
                                    txt_title1.setVisibility(View.GONE);
                                    lunchRv.setVisibility(View.GONE);
                                    icon_arrow1.setVisibility(View.GONE);
                                    break;
                                case "Lanche":
                                case "Higiene":
                                case "Roupas":
                                case "Animal Não-Humano":
                                    txt_title2.setVisibility(View.GONE);
                                    snacksRv.setVisibility(View.GONE);
                                    icon_arrow2.setVisibility(View.GONE);
                                    break;
                                case "Jantar":
                                case "Cosméticos":
                                case "Acessórios":
                                case "Limpeza":
                                    txt_titleDinner.setVisibility(View.GONE);
                                    dinnerRv.setVisibility(View.GONE);
                                    icon_arrowDinner.setVisibility(View.GONE);
                                    break;
                                case "Bebidas":
                                case "Fitness":
                                case "Calçados":
                                case "Eco Friendly":
                                    txt_titleDrinks.setVisibility(View.GONE);
                                    drinksRv.setVisibility(View.GONE);
                                    icon_arrowDrinks.setVisibility(View.GONE);
                                    break;
                                case "Sobremesas":
                                case "Mãe & Bebê":
                                case "Peça Intíma":
                                case "Jardim":
                                    txt_titleDesserts.setVisibility(View.GONE);
                                    desertsRv.setVisibility(View.GONE);
                                    icon_arrowDesserts.setVisibility(View.GONE);
                                    break;
                                case "Mercado":
                                case "Bem estar":
                                case "Praia":
                                case "Livros":
                                    txt_titleMarket.setVisibility(View.GONE);
                                    marketRv.setVisibility(View.GONE);
                                    icon_arrowMarket.setVisibility(View.GONE);
                                    break;
                                case "Mais":
                                    txt_titleMore.setVisibility(View.GONE);
                                    moreRv.setVisibility(View.GONE);
                                    icon_arrowMore.setVisibility(View.GONE);
                                    break;
                            }

                        }
                    }
                });
    }

    public void loadAllImported(ArrayList pl1, final RecyclerView recyclerV, final String selected8, final String filter1, final String userCountry) {
        pl1 = new ArrayList<>();

        //get all products
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        ArrayList finalPl = pl1;
        rootRef.collection(selected8)
                .whereEqualTo("productType", filter1)
                .whereEqualTo("productCategory",selected8)
                .whereEqualTo("internationalMail", "true")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                finalPl.add(modelProduct);

                                adapterPromotions = new AdapterPromotions(Outros.this, finalPl);
                                recyclerV.setAdapter(adapterPromotions);
                            }
                        }
                    }
                });
    }

    public void onBackPressed ( ) {
        finish ( );
    }

    @Override
    public boolean onDown(@NonNull MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}
