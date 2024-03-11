package app.vegan;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ActivityOwner2 extends AppCompatActivity {

    private String uid;
    private RecyclerView productsRv;
    private ArrayList<ModelProduct> productList;
    private AdapterProductSeller adapterProductSeller;
    private String total, tota, din, est, estornoTextView, tota2, din2;
    private TextView totalTv, concluidosTv, canceladosTv, emAnaTv, emEntregaTv, emProTv, txtCartao, paraRepassarTv,
    totalDinheiro, totalPEstornar, totalJEstornado, totalDinMens, totalCardMens, totalARecCart, totalAPagDinh, statusMens, dataEstTitle, dataEst;
    private EditText totalRepa;
    private Double conc, canc, empro, saiup, emana, numPed, cartao, dinheiro, estor;
    private Button salvarRepasse;
    private ImageButton back;
    private ImageView arrowMes;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner2);

        dataEst = findViewById(R.id.dataEst);
        dataEstTitle = findViewById(R.id.dataEstTitle);
        totalDinMens = findViewById(R.id.totalDinMens);
        totalCardMens = findViewById(R.id.totalCardMens);
        totalARecCart = findViewById(R.id.totalARecCart);
        totalAPagDinh = findViewById(R.id.totalAPagDinh);
        statusMens = findViewById(R.id.statusMens);
        arrowMes = findViewById(R.id.imageView30);
        totalPEstornar = findViewById(R.id.textView70);
        totalJEstornado = findViewById(R.id.textView72);
        totalDinheiro = findViewById(R.id.textView50);
        paraRepassarTv = findViewById(R.id.textView58);
        productsRv = findViewById(R.id.productsRv);
        concluidosTv = findViewById(R.id.textView60);
        totalTv = findViewById(R.id.textView46);
        canceladosTv = findViewById(R.id.textView62);
        emAnaTv = findViewById(R.id.textView64);
        emEntregaTv = findViewById(R.id.textView66);
        emProTv = findViewById(R.id.textView68);
        txtCartao = findViewById(R.id.textView48);
        totalRepa = findViewById(R.id.textView56);
        salvarRepasse = findViewById(R.id.saveRepa);
        back = findViewById(R.id.imageButton15);

        fStore = FirebaseFirestore.getInstance();

        Intent data = getIntent();
        uid = data.getStringExtra("uid");
        Log.d(TAG, "uiduiduid " + uid);

        adapterProductSeller = new AdapterProductSeller(ActivityOwner2.this, productList);
        productsRv.setAdapter(adapterProductSeller);

        conc = 0.0;
        canc = 0.0;
        empro = 0.0;
        saiup = 0.0;
        emana = 0.0;
        cartao= 0.0;
        dinheiro=0.0;
        estor = 0.0;

        din2="0";
        tota2="0";

        din="0";
        total="0";
        tota="0";
        est="0";

        loadInfo();
        loadStatistics();
        loadAllProducts();

        arrowMes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] Diet = {
                        "02/24",
                        "03/24",
                        "04/24",
                        "05/24",
                        "06/24",
                        "07/24",
                        "08/24",
                        "09/24",
                        "10/24",
                        "11/24"};

                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityOwner2.this, R.style.MyAlertDialogTheme);
                builder.setTitle("Selecione:")
                        .setItems(Diet, new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                String categor = Diet[which];
                                dataEstTitle.setText(categor);

                            }
                        })
                        .show();
                loadStatistics();

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        salvarRepasse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DocumentReference documentReference = fStore.collection("Adm's").document(uid);
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("totalRepassado", totalRepa.getText().toString());
                documentReference.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void avoid) {
                                Toast.makeText(ActivityOwner2.this, "Salvo!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
            }
        });


    }

    private void loadInfo() {
        DocumentReference docRef = fStore.collection("Adm's").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String totalRepassado = document.getString("totalRepassado");
                        String totalEstornado = document.getString("totalEstornado");

                        totalJEstornado.setText(totalEstornado);
                        totalRepa.setText(totalRepassado);
                    }
                }
            }
        });
    }

    private void loadAllProducts() {
        productList = new ArrayList<>();

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection("Promoções")
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        productList.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelProduct modelProduct = document.toObject(ModelProduct.class);
                                productList.add(modelProduct);
                                adapterProductSeller = new AdapterProductSeller(ActivityOwner2.this, productList);
                                productsRv.setAdapter(adapterProductSeller);
                            }
                        }
                    }
                });
    }

    private void loadStatistics() {
        DatabaseReference ref4 = FirebaseDatabase.getInstance().getReference("OrderTo").child(uid);
        ref4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String orderStatus = ""+ds.child("orderStatus").getValue();
                    String price = ""+ds.child("total").getValue();
                    String formadep = ""+ds.child("formadepagamento").getValue();
                    String cpm = ""+ds.child("cpm").getValue();
                    String iddopagamento = ""+ds.child("iddopagamento").getValue();
                    String timestamp = ""+ds.child("orderId").getValue();
                    String orderTime = ""+ds.child("orderTime").getValue();
                    String date = ""+ds.child("date").getValue();
                    char mess1 = date.charAt(3);
                    char mess2 = date.charAt(4);
                    char anoo1 = date.charAt(6);
                    char anoo2 = date.charAt(7);
                    String mes1= String.valueOf(mess1);
                    String mes2=String.valueOf(mess2);
                    String ano1=String.valueOf(anoo1);
                    String ano2=String.valueOf(anoo2);


                    String ana =  getString(R.string.emanalise);
                    String emp = getString(R.string.emproducao);
                    String sai = getString(R.string.saiuparaen);
                    String con = getString(R.string.concluido);
                    String rec = "Recebido";
                    String can = getString(R.string.cancelado);
                    String pag = getString(R.string.pagonocartao);
                    String ent = "Pix na entrega";
                    String ent2 = "Cartão na entrega";
                    String ent3 = "Dinheiro";

                    //conc
                    if(orderStatus.equals(con)||orderStatus.equals(rec)) {
                        conc = conc + 1.0;

                        //total
                        String replaces = price.replace(",",".").replace("$","")
                                .replace("R","").replace(" ","").replace("null","");
                        Double temp = Double.parseDouble(replaces);
                        Double temp2 = Double.parseDouble(total);
                        temp2 = temp2 + temp;
                        total = String.valueOf(temp2);
                    }

                    //canc
                    if(orderStatus.equals(can)) {
                        canc = canc + 1.0;
                    }

                    //emana
                    if(orderStatus.equals(ana)) {
                        emana = emana + 1.0;
                        Log.d(TAG, "emana " + emana);
                    }

                    //prod
                    if(orderStatus.equals(emp)) {
                        empro = empro + 1.0;
                    }

                    //entre
                    if(orderStatus.equals(sai)) {
                        saiup = saiup + 1.0;
                    }
                    //entre
                    if(formadep.equals(pag)) {
                        if(orderStatus.equals(can)){
                            if(!formadep.equals("estornado")){
                            //contagem do total para estornar
                            String replace = price.replace(",",".").replace("$","")
                                    .replace("R","").replace(" ","").replace("null","");
                            Double tem = Double.parseDouble(replace);

                            Double tem2 = Double.parseDouble(est);
                            tem2 = tem2 + tem;

                            estornoTextView = String.valueOf(tem2);

                            //Mensagens de estorno
                            long l = Long.parseLong(timestamp);

                            Calendar cal = Calendar.getInstance(Locale.getDefault());
                            cal.setTimeInMillis(l);
                            String hour = DateFormat.format("HH:mm", cal).toString();

                            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityOwner2.this);
                            builder.setTitle(getString(R.string.atencao));
                            builder.setMessage("Valor do Estorno:"+price + "cliente:" + iddopagamento + "data: "+ date+ "hora:"+ hour);


                            builder.setPositiveButton("Estornado", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("OrderTo").child(uid).child(orderTime);
                                    Map<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("formadepagamento",  "estornado");
                                    ref2.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(ActivityOwner2.this, "Estorno Salvo!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            });

                            AlertDialog alert = builder.create();
                            alert.show();

                        }}
                        if(orderStatus.equals(con)||orderStatus.equals(rec)) {
                            String replace = price.replace(",",".").replace("$","")
                                    .replace("R","").replace(" ","").replace("null","");
                            Double tem = Double.parseDouble(replace);
                            if(cpm.equals("Desconto de Boas vindas por conta do Vegan")){
                                tem = tem*1.05;
                            }
                            Double tem2 = Double.parseDouble(tota);
                            tem2 = tem2 + tem;

                            tota = String.valueOf(tem2);

                            cartao = cartao + 1.0;

                            // mensal card
                            String mesProcurado = dataEst.getText().toString();
                            String mesDB = mes1+mes2+"/20"+ano1+ano2;
                            if(mesProcurado.equals(mesDB)) {
                                String replace2 = price.replace(",",".").replace("$","")
                                        .replace("R","").replace(" ","").replace("null","");
                                Double teM = Double.parseDouble(replace2);
                                if(cpm.equals("Desconto de Boas vindas por conta do Vegan")){
                                    teM = teM*1.05;
                                }
                                Double teM2 = Double.parseDouble(tota);
                                teM2 = teM2 + teM;
                                tota2 = String.valueOf(teM2);

                            }

                        }}

                    if(formadep.equals(ent) || formadep.equals(ent2) || formadep.equals(ent3)) {
                        if(orderStatus.equals(con)||orderStatus.equals(rec)) {
                            String replace = price.replace(",",".").replace("$","")
                                    .replace("R","").replace(" ","").replace("null","");
                            Double tem = Double.parseDouble(replace);
                            if(cpm.equals("Desconto de Boas vindas por conta do Vegan")){
                                tem = tem*1.05;
                            }
                            Double tem2 = Double.parseDouble(din);
                            tem2 = tem2 + tem;
                            din = String.valueOf(tem2);

                            dinheiro = dinheiro + 1.0;

                            //mensal din

                            String mesProcurado = dataEst.getText().toString();
                            String mesDB = mes1+mes2+"/20"+ano1+ano2;

                            if(mesProcurado.equals(mesDB)){

                                String replace3 = price.replace(",",".").replace("$","")
                                        .replace("R","").replace(" ","").replace("null","");
                                Double teMM = Double.parseDouble(replace3);
                                if(cpm.equals("Desconto de Boas vindas por conta do Vegan")){
                                    teMM = teMM*1.05;
                                }
                                Double teMM2 = Double.parseDouble(din2);
                                teMM2 = teMM2 + teMM;
                                din2 = String.valueOf(teMM2);
                            }

                        }}

                }

                //general

                //para repassar 90% cartao - 10% dinheiro
                Double tempo0 = Double.parseDouble(total.replace(",",".")); // pega o total total
                String temporaria = totalRepa.getText().toString().replace(",","."); // pega o total ja repassado
                Double temporaria2 = Double.parseDouble(temporaria); //total ja repassado

                Double calrepasse = Double.parseDouble(din)*0.1; // 10% de total dinheiro
              //  Double harepassar = temporaria2*0.9;
             //  String tempo3 = harepassar.toString();

               //
               Double calcartao0 = Double.parseDouble(din);
               Double calcartao1 = Double.parseDouble(total);
               Double calcartao2 = calcartao1-calcartao0;
                //
                Double calrepasse2 = calcartao2*0.9;
                Double calrepasse3 = calrepasse2-calrepasse-temporaria2;
                paraRepassarTv.setText(String.valueOf(calrepasse3).replace(".",",").replace("null",""));


                totalPEstornar.setText(estornoTextView);
                totalTv.setText(total.replace(".",","));
                concluidosTv.setText(String.valueOf(conc).replace(".",",").replace("null",""));
                canceladosTv.setText(String.valueOf(canc).replace(".",",").replace("null",""));
                emAnaTv.setText(String.valueOf(emana).replace(".",",").replace("null",""));
                emProTv.setText(String.valueOf(empro).replace(".",",").replace("null",""));
                emEntregaTv.setText(String.valueOf(saiup).replace(".",",").replace("null",""));
                txtCartao.setText(String.valueOf(calcartao2).replace(".",",").replace("null",""));
                totalDinheiro.setText(String.valueOf(din).replace(".",",").replace("null",""));


                //mensal

                Double calfinals0 = Double.parseDouble(din2);
                String finaldinMensal = "R$"+String.valueOf(calfinals0);
                Double calfinals1 = Double.parseDouble(tota2);
                String finalcardMensal = "R$"+String.valueOf(calfinals1);

                Double areceber = calfinals1*0.9;
                String finalareceber = "R$"+String.valueOf(areceber);
                Double apagar = calfinals0*0.1;
                String finalapagar = "R$"+String.valueOf(apagar);

                totalDinMens.setText(finaldinMensal.replace(".",",").replace("null",""));
                totalCardMens.setText(finalcardMensal.replace(".",",").replace("null",""));
                totalAPagDinh.setText(finalapagar.replace(".",",").replace("null",""));
                totalARecCart.setText(finalareceber.replace(".",",").replace("null",""));

                String variavel;
                String valorTransf;

                if(areceber>apagar){
                    variavel = "Vegan";
                    Double temptrans = areceber-apagar;
                    valorTransf = String.valueOf(temptrans);
                } else {
                    variavel = "Lojista";
                    Double temptrans = apagar-areceber;
                    valorTransf = String.valueOf(temptrans);
                }

                String statustext = "Transferência em "+ dataEst.getText().toString() + " deve/foi feita pelo " + variavel +
                        " no valor de R$"+ valorTransf;
                statusMens.setText(statustext);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ActivityOwner2.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}
