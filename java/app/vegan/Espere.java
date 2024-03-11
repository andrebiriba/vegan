package app.vegan;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class Espere extends FragmentActivity implements Serializable {

    private static final int PLAY_SERVICE_RES_REQUEST = 7001;

    private Button chegouBtn;
    private TextView trocoTv, formaPagamentoTv, delTv;
    private RecyclerView ordersRv;
    private ImageView flotBntExitP2, flotBntExitP1, close, disputaTv;
    private ImageButton chat, call;
    private RelativeLayout ordersRl;
    private ShapeableImageView brandphoto, orderPhoto;
    private TextView brandname, orderIdTv, dateTv, amountTv, statusTv, timeTv, tipoDeCompraTv, agendamentTv;
    private FirebaseAuth firebaseAuth;
    private ArrayList<ModelOrderUser> orderUserList;
    String userID, codentrega, tipoDeCompra, cpm, fidelidade, obs, username, email, telefone, cidade;
    private String shopUid, nome,foto, preco, brand, timestamp, orderby, orderto, orderid, pagamento,
    shopname, cost, status, date, time, logomarca, troco, token, brandIcon, celShop, schedule;
    FirebaseFirestore fStore;
    private ArrayList<ModelOrderedItem> orderedItemArrayList;
    private AdapterOrderedItem adapterOrderedItem;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espere);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        disputaTv = findViewById(R.id.disputaTv);
        agendamentTv = findViewById(R.id.agendamentTv);
        chegouBtn = findViewById(R.id.chegouBtn);
        tipoDeCompraTv = findViewById(R.id.tipoDeCompraTv);
        delTv = findViewById(R.id.delTv);
        orderPhoto = findViewById(R.id.imageView21);
        orderIdTv = findViewById(R.id.orderIdTv);
        dateTv = findViewById(R.id.dateTv);
        timeTv = findViewById(R.id.timeTv);
        amountTv = findViewById(R.id.amountTv);
        statusTv = findViewById(R.id.statusTv);
        formaPagamentoTv = findViewById(R.id.formaPagamentoTv);

        chat = findViewById(R.id.button18);
        call = findViewById(R.id.call);
        trocoTv = findViewById(R.id.trocoTv);
        close = findViewById(R.id.imageView66);
        brandphoto = findViewById(R.id.imageView31);
        brandname = findViewById(R.id.textView27);
        ordersRv = findViewById(R.id.recy);
        flotBntExitP2 = findViewById(R.id.flotBntExitP2);
        flotBntExitP1 = findViewById(R.id.flotBntExitP1);
        TextView fidelidadeTv = findViewById(R.id.fidelidadeTv);
        TextView cupomTv = findViewById(R.id.cupomTv);
        TextView obsTv = findViewById(R.id.obsTv);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.aguardeum);
        progressDialog.setCanceledOnTouchOutside(false);

        fStore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

        loadOrderedItems();

        Intent data = getIntent();
        nome = data.getStringExtra("title");
        codentrega = data.getStringExtra("codentrega");
         tipoDeCompra = data.getStringExtra("tipoDeCompra");
        foto = data.getStringExtra("photo");
        preco = data.getStringExtra("price");
        brand = data.getStringExtra("brandName");
        shopUid = data.getStringExtra("shopUid");
        timestamp = data.getStringExtra("timestamp");
        orderby = data.getStringExtra("orderBy");
        orderto = data.getStringExtra("orderTo");
        shopname = data.getStringExtra("shopName");
        cost = data.getStringExtra("cost");
        orderid = data.getStringExtra("orderId");
        status = data.getStringExtra("status");
        date = data.getStringExtra("data");
        time = data.getStringExtra("time");
        userID = data.getStringExtra("userID");
        troco = data.getStringExtra("troco");
        pagamento = data.getStringExtra("formadepagamento");
         cpm = data.getStringExtra("cpm");
         fidelidade = data.getStringExtra("fidelidade");
         obs = data.getStringExtra("obs");
         schedule = data.getStringExtra("schedule");
         String del = "Delivery: "+ data.getStringExtra("deliveryFee");

         delTv.setText(del);
        if(del.equals("Delivery: null")){
            delTv.setVisibility(View.GONE);
        }
        tipoDeCompraTv.setText(tipoDeCompra);

        if(!Objects.equals(schedule, "") && !Objects.equals(schedule, null)){
            agendamentTv.setVisibility(View.VISIBLE);
            String x = getString(R.string.agendamento)+" "+schedule;
            agendamentTv.setText(x);
        }
        if(agendamentTv.getText()=="Agendamento Agende o seu pedido: "){
            agendamentTv.setVisibility(View.GONE);
        }

        if(!Objects.equals(obs, "")){
        obsTv.setText(obs);}
        Log.d(TAG, "userID "+userID);

        if (Objects.equals(fidelidade, "true")){
        fidelidadeTv.setText(R.string.fidelialc);
        fidelidadeTv.setVisibility(View.VISIBLE);
        }
        if (Objects.equals(cpm, "true")){
            cupomTv.setText(R.string.cupomapli);
            cupomTv.setVisibility(View.VISIBLE);
        }
        formaPagamentoTv.setText(pagamento);

        amountTv.setText(R.string.total);
        amountTv.append(": ");
        amountTv.append(preco.replace(".",","));
        orderIdTv.setText("id: "+orderid);
        statusTv.setText(status);
        trocoTv.setText(getString(R.string.trocopara)+" "+troco);
        if(Objects.equals(troco, "00,00")){
            trocoTv.setText(getString(R.string.naohatroco));
        }

   long l = Long.parseLong(orderid);

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
   cal.setTimeInMillis(l);
        String date = DateFormat.format("dd/MM/yy", cal).toString();
        String hour = DateFormat.format("HH:mm", cal).toString();

        dateTv.setText(date);
        timeTv.setText(hour);

       loadBrandPhoto();

        disputaTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Espere.this, R.style.MyAlertDialogTheme);

                builder.setTitle("Deseja abrir uma disputa para este pedido?");
                String tempora = "Abrindo uma disputa você atestará que não recebeu o pedido comprado.";
                builder.setMessage(tempora);

                builder.setPositiveButton(getString(R.string.continuar), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        //salvar no BD
                        progressDialog.setMessage("Abrindo disputa...");
                        progressDialog.show();
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("shopName",""+shopname);
                        hashMap.put("username",""+username);
                        hashMap.put("telefone",""+telefone);
                        hashMap.put("cidade",""+cidade);
                        hashMap.put("email",""+email);
                        hashMap.put("status",""+status);
                        hashMap.put("date",""+date);
                        hashMap.put("time",""+time);
                        hashMap.put("price",""+preco);
                        hashMap.put("cost",""+cost);
                        hashMap.put("troco",""+troco);
                        hashMap.put("pagamento",""+pagamento);
                        hashMap.put("cpm",""+cpm);
                        hashMap.put("fidelidade",""+fidelidade);
                        hashMap.put("obs",""+obs);
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Disputas").child(firebaseAuth.getUid()).child(shopUid).child(orderid);
                        ref
                                .setValue(hashMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressDialog.dismiss();
                                        dialog.dismiss();
                                        Toast.makeText(Espere.this, "Disputa aberta", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        dialog.dismiss();
                                        Toast.makeText(Espere.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
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
            }
        });

       chegouBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               chegouBtn.setText(codentrega);
               changeStatusConc();
           }
       });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(userID != null) {
                    Intent intent = new Intent(v.getContext(), ChatActivity.class);
                    intent.putExtra("contatoId", shopUid);
                    intent.putExtra("userId", userID);
                    intent.putExtra("contatoName", brandname.getText().toString());
                    intent.putExtra("contatoPhoto", brandIcon);
                    v.getContext().startActivity(intent);
                }else Toast.makeText(Espere.this, R.string.entreoucadastre, Toast.LENGTH_SHORT).show();
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(userID != null){
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + celShop));
                    startActivity(intent);
                } else Toast.makeText(Espere.this, R.string.entreoucadastre, Toast.LENGTH_SHORT).show();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        flotBntExitP2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(Espere.this, Outros.class);
                startActivity(intentLoadNewActivity);
            }
        });

        flotBntExitP1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(Espere.this, Outros.class);
                startActivity(intentLoadNewActivity);
            }
        });

        close.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(Espere.this, Outros.class);
                startActivity(intentLoadNewActivity);
            }
        });

    }

    private void changeStatusConc() {
        String selectedOption = "Recebido";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("orderStatus", ""+selectedOption);
        hashMap.put("recebeu", "true");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("OrderTo").child(shopUid);
        ref.child(orderid)
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Espere.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("OrderBy").child(userID);
        ref2.child(orderid)
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Espere.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadBrandPhoto() {
        if(shopUid != null) {
            DocumentReference docReference = fStore.collection("Adm's").document(shopUid);
            docReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            brandIcon = document.getString("shopIcon");
                            String fName = document.getString("fName");
                             celShop = document.getString("cel");

                            brandname.setText(fName);
                            if (!Objects.equals(brandIcon, "")) {
                                try {
                                    Picasso.get().load(brandIcon).placeholder(R.drawable.loading).into(brandphoto);
                                } catch (Exception e) {
                                    brandphoto.setImageResource(R.drawable.error);
                                }
                            }
                        }
                    }
                }
            });
        }

        String userID;

        FirebaseUser mFirebaseUser = firebaseAuth.getCurrentUser();
        if(mFirebaseUser != null) {
            userID = mFirebaseUser.getUid(); //Do what you need to do with the id
            DocumentReference docRef = fStore.collection("Users").document(userID);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                             username = document.getString("fName");
                             email = document.getString("email");
                             telefone = document.getString("phone");
                             cidade = document.getString("city");

                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

            }
        }
    }

    private void loadOrderedItems(){
        orderedItemArrayList = new ArrayList<>();
        Intent data = getIntent();
        String orderId = data.getStringExtra("orderId");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("OrderBy").child(userID);
        ref.child(orderId).child("Items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderedItemArrayList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ModelOrderedItem modelOrderedItem = ds.getValue(ModelOrderedItem.class);
                    orderedItemArrayList.add(modelOrderedItem);
                }
                adapterOrderedItem = new AdapterOrderedItem(Espere.this, orderedItemArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Espere.this, LinearLayoutManager.VERTICAL, false);
                ordersRv.setLayoutManager(linearLayoutManager);
                ordersRv.setAdapter(adapterOrderedItem);
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
