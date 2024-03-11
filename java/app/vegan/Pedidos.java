package app.vegan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Pedidos extends AppCompatActivity {

    private static final String TAG = "Log";
    private TextView filteredOrdersTv;
    private ImageView filterOrderBtn, load;
    private ImageButton imageButton6;
    private RecyclerView ordersRv;
    String userID, token;

    private ArrayList<ModelOrderShop> orderShopArrayList;
    private AdapterOrderShop adapterOrderShop;
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        setRequestedOrientation ( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        load = findViewById(R.id.load);
        imageButton6 = findViewById(R.id.imageButton6);
        filteredOrdersTv = findViewById(R.id.filteredOrdersTv);
        filterOrderBtn = findViewById(R.id.filterOrderBtn);
        ordersRv = findViewById(R.id.ordersRv);

        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        getToken();
        loadAllOrders();

        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });

        filterOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] options = {getString(R.string.todos), getString(R.string.emanalise), getString(R.string.emproducao), getString(R.string.saiuparaen),
                        getString(R.string.concluido), "Recebidos", getString(R.string.cancelado),};
                AlertDialog.Builder builder = new AlertDialog.Builder(Pedidos.this, R.style.MyAlertDialogTheme);
                builder.setTitle(getString(R.string.filtrarpor))
                        .setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==0){
                                    filteredOrdersTv.setText(R.string.todos);
                                    adapterOrderShop.getFilter().filter("");
                                }
                                else {
                                    String optionClicked = options[which];
                                    filteredOrdersTv.setText(getString(R.string.mostrandopedidos)+" "+optionClicked);
                                    adapterOrderShop.getFilter().filter(optionClicked);
                                }
                            }
                        })
                        .show();
            }
        });


    }

    private void getToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if(task.isSuccessful()){
                            String token= task.getResult();
                            String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                            System.out.println("TOKEN "+ token);
                            DocumentReference docRef = fStore.collection("Adm's").document(userID);
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


    private void loadAllOrders() {
        orderShopArrayList = new ArrayList<>();
        userID = firebaseAuth.getCurrentUser().getUid();

        Query ref = FirebaseDatabase.getInstance().getReference("OrderTo").child(userID);
        ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        orderShopArrayList.clear();
                        for (DataSnapshot ds: dataSnapshot.getChildren()) {
                            ModelOrderShop modelOrderShop = ds.getValue(ModelOrderShop.class);
                            orderShopArrayList.add(modelOrderShop);
                        }
                        Collections.reverse(orderShopArrayList);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Pedidos.this, LinearLayoutManager.VERTICAL, false);
                        adapterOrderShop = new AdapterOrderShop(Pedidos.this, orderShopArrayList);
                        ordersRv.setLayoutManager(linearLayoutManager);
                        ordersRv.setAdapter(adapterOrderShop);
                        load.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void onBackPressed ( ) {
        finish ( );
    }


}
