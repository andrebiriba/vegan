package app.vegan;

import static android.content.ContentValues.TAG;
import static app.vegan.R.drawable.error;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class OrderDetailsSellerActivity extends AppCompatActivity {

    private ImageButton backBtn, editBtn, shareBtn;
    private TextView orderIdTv, dateTv, orderStatusTv, emailTv, phoneTv, amountTv, addressTv,
            totalItemsTv, brandTv, username, timeTv, trocoTv, formadepagamentoTv, obsTv, cpmTv,
            tipoDeCompraTv, codentregaTv, agendamentoTv;
    private RecyclerView itemsRv;
    String userID, codentrega;
    private ShapeableImageView shopFoto, avataruser;
    private String orderId, orderBy, finalcoin, cpm;
    private String shopUid, country, state, city, neighborhood, street, number, reference, cep, afd,
    itemPId, itemName, itemCost, itemQuantity,path, clientID, clientToken, fName, token, phone,
    troco, formadepagamento, cost;
    private String pId, name, price, costt, quantity, size, va1, va2, va3, va4, va5, va6, va7, va8, va9,va10,
    va11, va12, va13, va14, va15, adc1, adc2, adc3, adc4, adc5, adc6, adc7, adc8, adc9, adc10, adc11, adc12, adc13,
    adc14, adc15;
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    private ArrayList<ModelOrderedItem> orderedItemArrayList;
    private AdapterOrderedItem adapterOrderedItem;
    final static int REQUEST_CODE = 1232;
    private int i;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oders_details_seller);

        agendamentoTv = findViewById(R.id.agendamentoTv);
        codentregaTv = findViewById(R.id.codentregaTv);
        tipoDeCompraTv = findViewById(R.id.tipoDeCompraTv);
        cpmTv = findViewById(R.id.cupomTv);
        obsTv = findViewById(R.id.obsTv);
        formadepagamentoTv = findViewById(R.id.formaPagamentoTv);
        username = findViewById(R.id.username);
        avataruser = findViewById(R.id.avataruser);
        backBtn = findViewById(R.id.backBtn);
        editBtn = findViewById(R.id.editBtn);
        shareBtn = findViewById(R.id.shareBtn);
        orderIdTv = findViewById(R.id.orderIdTv);
        dateTv = findViewById(R.id.dateTv);
        timeTv = findViewById(R.id.timeTv);
        orderStatusTv = findViewById(R.id.orderStatusTv);
        emailTv = findViewById(R.id.emailTv);
        phoneTv = findViewById(R.id.phoneTv);
        totalItemsTv = findViewById(R.id.totalItemsTv);
        amountTv = findViewById(R.id.amountTv);
        addressTv = findViewById(R.id.addressTv);
        itemsRv = findViewById(R.id.itemsRv);
        brandTv = findViewById(R.id.brandTv);
        shopFoto = findViewById(R.id.shopFoto);
        trocoTv = findViewById(R.id.trocoTv);

        askPermissions();

        fStore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        Intent data = getIntent();
         orderId = data.getStringExtra("orderId");
        orderBy = data.getStringExtra("orderBy");

        DocumentReference docRef = fStore.collection("Adm's").document(firebaseAuth.getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                       String admcountry = document.getString("country");

                        if (Objects.equals(admcountry, "Brasil")) {
                            finalcoin = "R$";
                        }
                        if (Objects.equals(admcountry, "Estados Unidos da América")) {
                            finalcoin = "US$";
                        }
                        if (Objects.equals(admcountry, "Inglaterra")) {
                            finalcoin = "£";
                        }
                        if (Objects.equals(admcountry, "Alemanha")) {
                            finalcoin = "€";
                        }
                        if (Objects.equals(admcountry, "França")) {
                            finalcoin = "€";
                        }
                        if (Objects.equals(admcountry, "Portugal")) {
                            finalcoin = "€";
                        }
                        if (Objects.equals(admcountry, "Espanha")) {
                            finalcoin = "€";
                        }

                        getToken();
                        loadOrderDetails();
                        loadOrderedItems();

                    }
                }
            }
        });

        itemsRv.setAdapter(adapterOrderedItem);



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareOrderInfo();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOrderStatusDialog();
            }
        });
    }

    private void shareOrderInfo(){
        /*
        String escolha = getString(R.string.escolhaum);
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Vegan app");
            String shareMessage = orderId +"\n"+ fName+", \n"+ phone+", \n"+country + ", "+ state + ", "+ city + ". \n"+ neighborhood + ", "+ street+ ", "+ number+
                    ", "+ reference+ ", " +cep + "\n" + troco + ", " + formadepagamento + ", \n" + "TOTAL: " + cost + "\n"+ "(" + totalItemsTv.getText().toString() + ")";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, escolha));
        } catch(Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        String title = "Pedido"+orderId;
        String description = orderId +"\n"+ fName+", \n"+ phone+", \n"+country + ", "+ state + ", "+ city + ". \n"+ neighborhood + ", "+ street+ ", "+ number+
                ", "+ reference+ ", " +cep + "\n" + troco + ", " + formadepagamento + ", \n" + "TOTAL: " + cost + "\n"+ "(" + totalItemsTv.getText().toString() + ")";

        // create a new document
        PdfDocument document = new PdfDocument();
        // crate a page description

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawText(title, 20, 40, paint);
        canvas.drawText(description, 20, 60, paint);
        // canvas.drawText(description,1,20,20.0f,30.0f,paint);
        //canvas.drawt
        // finish the page
        document.finishPage(page);
// draw text on the graphics object of the page

        // write the document content
        String directory_path = OrderDetailsSellerActivity.this.getExternalFilesDir(null).toString();
        File file = new File(directory_path);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String targetPdf = directory_path+title+".pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("main", "error "+e.toString());
            Toast.makeText(this, "Something wrong: " + e.toString(),
                    Toast.LENGTH_LONG).show();
        }
        // close the document
        document.close();



             pId = dataSnapshot.child("pId").getValue().toString();
                         name = dataSnapshot.child("name").getValue().toString();
                         price = dataSnapshot.child("price").getValue().toString();
                         costt = dataSnapshot.child("cost").getValue().toString();
                         quantity = dataSnapshot.child("quantity").getValue().toString();
                         size = dataSnapshot.child("size").getValue().toString();
                         va1 = dataSnapshot.child("va1").getValue().toString();
                         va2 = dataSnapshot.child("va2").getValue().toString();
                         va3 = dataSnapshot.child("va3").getValue().toString();
                         va4 = dataSnapshot.child("va4").getValue().toString();
                         va5 = dataSnapshot.child("va5").getValue().toString();
                         va6 = dataSnapshot.child("va6").getValue().toString();
                         va7 = dataSnapshot.child("va7").getValue().toString();
                         va8 = dataSnapshot.child("va8").getValue().toString();
                         va9 = dataSnapshot.child("va9").getValue().toString();
                         va10 = dataSnapshot.child("va10").getValue().toString();
                         va11 = dataSnapshot.child("va11").getValue().toString();
                         va12 = dataSnapshot.child("va12").getValue().toString();
                         va13 = dataSnapshot.child("va13").getValue().toString();
                         va14 = dataSnapshot.child("va14").getValue().toString();
                         va15 = dataSnapshot.child("va15").getValue().toString();
                         adc1 = dataSnapshot.child("adc1").getValue().toString();
                         adc2 = dataSnapshot.child("adc2").getValue().toString();
                         adc3 = dataSnapshot.child("adc3").getValue().toString();
                         adc4 = dataSnapshot.child("adc4").getValue().toString();
                         adc5 = dataSnapshot.child("adc5").getValue().toString();
                         adc6 = dataSnapshot.child("adc6").getValue().toString();
                         adc7 = dataSnapshot.child("adc7").getValue().toString();
                         adc8 = dataSnapshot.child("adc8").getValue().toString();
                         adc9 = dataSnapshot.child("adc9").getValue().toString();
                        adc10 = dataSnapshot.child("adc10").getValue().toString();
                         adc11 = dataSnapshot.child("adc11").getValue().toString();
                        adc12 = dataSnapshot.child("adc12").getValue().toString();
                        adc13 = dataSnapshot.child("adc13").getValue().toString();
                        adc14 = dataSnapshot.child("adc14").getValue().toString();
                        adc15 = dataSnapshot.child("adc15").getValue().toString();
         */

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1080, 999999, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(24);

        String text = "Id: "+orderId;
        String text2 = "Nome: "+fName;
        String text3 ="Telefone: "+phone ;
        String text4 = "País: "+country;
        String text5 = "Estado: "+state;
        String text6 = "Cidade: "+city;
        String text7 = "Bairro: "+neighborhood;
        String text8 = "Rua: "+street;
        String text9 = "Número: "+number;
        String text10 = "Complemento: "+reference;
        String text11 = "CEP: "+cep;
        String text12 = "Forma de pagamento: "+formadepagamento;
        String text13 = "Total: "+cost;
        String text14 = "Troco: "+troco;
        String text15 = "Diferentes Artigos: (" + totalItemsTv.getText().toString() + ")";
        float x = 100;

        canvas.drawText("VEGAN APP    -    "+ brandTv.getText().toString(), x,100, paint);
        canvas.drawText(dateTv.getText().toString(), x,150, paint);
        canvas.drawText(timeTv.getText().toString(), x,200, paint);
        canvas.drawText(text, x,250, paint);
        canvas.drawText(text2, x,300, paint);
        canvas.drawText(text3, x,350, paint);
        canvas.drawText(text4, x,400, paint);
        canvas.drawText(text5, x,450, paint);
        canvas.drawText(text6, x,500, paint);
        canvas.drawText(text7, x,550, paint);
        canvas.drawText(text8, x,600, paint);
        canvas.drawText(text9, x,650, paint);
        canvas.drawText(text10, x,700, paint);
        canvas.drawText(text11, x,750, paint);
        canvas.drawText(text12, x,800, paint);
        canvas.drawText(text13, x,850, paint);
        canvas.drawText(text14, x,900, paint);
        canvas.drawText(text15, x,950, paint);
        canvas.drawText(tipoDeCompraTv.getText().toString(), x,1000, paint);
        canvas.drawText(agendamentoTv.getText().toString(), x,1050, paint);
        canvas.drawText(cpmTv.getText().toString(), x,1100, paint);
        canvas.drawText(obsTv.getText().toString(), x,1150, paint);
        canvas.drawText("Código de Entrega: "+codentregaTv.getText().toString(), x,1200, paint);

        i=0;
        userID = firebaseAuth.getCurrentUser().getUid();
        Intent data = getIntent();
        String orderId = data.getStringExtra("orderId");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("OrderTo").child(userID);
        ref.child(orderId).child("Items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    i=i+1800;
                    name = ds.child("name").getValue(String.class) + " ";
                    price = ds.child("price").getValue(String.class) + " ";
                    costt = ds.child("cost").getValue(String.class) + " ";
                    quantity = ds.child("quantity").getValue(String.class) + " ";
                    size = ds.child("size").getValue(String.class) + " ";
                    va1 = ds.child("va1").getValue(String.class) + " ";
                    va2 = ds.child("va2").getValue(String.class) + " ";
                    va3 = ds.child("va3").getValue(String.class) + " ";
                    va4 = ds.child("va4").getValue(String.class) + " ";
                    va5 = ds.child("va5").getValue(String.class) + " ";
                    va6 = ds.child("va6").getValue(String.class) + " ";
                    va7 = ds.child("va7").getValue(String.class) + " ";
                    va8 = ds.child("va8").getValue(String.class) + " ";
                    va9 = ds.child("va9").getValue(String.class) + " ";
                    va10 = ds.child("va10").getValue(String.class) + " ";
                    va11 = ds.child("va11").getValue(String.class) + " ";
                    va12 = ds.child("va12").getValue(String.class) + " ";
                    va13 = ds.child("va13").getValue(String.class) + " ";
                    va14 = ds.child("va14").getValue(String.class) + " ";
                    va15 = ds.child("va15").getValue(String.class) + " ";
                    adc1 = ds.child("adc1").getValue(String.class) + " ";
                    adc2 = ds.child("adc2").getValue(String.class) + " ";
                    adc3 = ds.child("adc3").getValue(String.class) + " ";
                    adc4 = ds.child("adc4").getValue(String.class) + " ";
                    adc5 = ds.child("adc5").getValue(String.class) + " ";
                    adc6 = ds.child("adc6").getValue(String.class) + " ";
                    adc7 = ds.child("adc7").getValue(String.class) + " ";
                    adc8 = ds.child("adc8").getValue(String.class) + " ";
                    adc9 = ds.child("adc9").getValue(String.class) + " ";
                    adc10 = ds.child("adc10").getValue(String.class) + " ";
                    adc11 = ds.child("adc11").getValue(String.class) + " ";
                    adc12 = ds.child("adc12").getValue(String.class) + " ";
                    adc13 = ds.child("adc13").getValue(String.class) + " ";
                    adc14 = ds.child("adc14").getValue(String.class) + " ";
                    adc15 = ds.child("adc15").getValue(String.class) + " ";

                    canvas.drawText("_____________________________________", 100,i+25, paint);
                    canvas.drawText("Título: "+name, 100,i+50, paint);
                    canvas.drawText("Preço: "+price, 100,i+100, paint);
                    canvas.drawText("Custo: "+costt, 100,i+150, paint);
                    canvas.drawText("Quantidade: "+quantity, 100,i+200, paint);
                    canvas.drawText("Tamanho: "+size, 100,i+250, paint);
                    canvas.drawText("Adicional1: "+adc1, 100,i+300, paint);
                    canvas.drawText("Valor1: "+va1, 100,i+350, paint);
                    canvas.drawText("Adicional2: "+adc2, 100,i+400, paint);
                    canvas.drawText("Valor2: "+va2, 100,i+450, paint);
                    canvas.drawText("Adicional3: "+adc3, 100,i+500, paint);
                    canvas.drawText("Valor3: "+va3, 100,i+550, paint);
                    canvas.drawText("Adicional4: "+adc4, 100,i+600, paint);
                    canvas.drawText("Valor4: "+va4, 100,i+650, paint);
                    canvas.drawText("Adicional5: "+adc5, 100,i+700, paint);
                    canvas.drawText("Valor5: "+va5, 100,i+750, paint);
                    canvas.drawText("Adicional6: "+adc6, 100,i+800, paint);
                    canvas.drawText("Valor6: "+va6, 100,i+850, paint);
                    canvas.drawText("Adicional7: "+adc7, 100,i+900, paint);
                    canvas.drawText("Valor7: "+va7, 100,i+950, paint);
                    canvas.drawText("Adicional8: "+adc8, 100,i+1000, paint);
                    canvas.drawText("Valor8: "+va8, 100,i+1050, paint);
                    canvas.drawText("Adicional9: "+adc9, 100,i+1100, paint);
                    canvas.drawText("Valor9: "+va9, 100,i+1150, paint);
                    canvas.drawText("Adicional10: "+adc10, 100,i+1200, paint);
                    canvas.drawText("Valor10: "+va10, 100,i+1250, paint);
                    canvas.drawText("Adicional11: "+adc11, 100,i+1300, paint);
                    canvas.drawText("Valor11: "+va11, 100,i+1350, paint);
                    canvas.drawText("Adicional12: "+adc12, 100,i+1400, paint);
                    canvas.drawText("Valor12: "+va12, 100,i+1450, paint);
                    canvas.drawText("Adicional13: "+adc13, 100,i+1500, paint);
                    canvas.drawText("Valor13: "+va13, 100,i+1550, paint);
                    canvas.drawText("Adicional14: "+adc14, 100,i+1600, paint);
                    canvas.drawText("Valor14: "+va14, 100,i+1650, paint);
                    canvas.drawText("Adicional15: "+adc15, 100,i+1700, paint);
                    canvas.drawText("Valor15: "+va15, 100,i+1750, paint);

                }

                document.finishPage(page);

                File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                String fileName = "Pedido"+orderId+".pdf";
                File file = new File(downloadsDir, fileName);
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    document.writeTo(fos);
                    document.close();
                    fos.close();
                    Toast.makeText(getApplicationContext(),"Aguarde um Instante", Toast.LENGTH_SHORT).show();
                    final Handler handler2 = new Handler();
                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                            //    Uri uri = Uri.parse("/Download/" + "Pedido" + orderId + ".pdf");
                            //    Intent share = new Intent(Intent.ACTION_SEND);
                            //    share.setType("application/pdf");
                            //    share.putExtra(Intent.EXTRA_STREAM, uri);
                            //    startActivity(Intent.createChooser(share, getString(R.string.selecione_o_tipo)));
                                DownloadManager downloadManager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                                downloadManager.addCompletedDownload(file.getName(), file.getName(), true,
                                        "application", file.getPath(), file.length(), true);
                                startActivity(new Intent(downloadManager.ACTION_VIEW_DOWNLOADS));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, 3000 );

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void askPermissions(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
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


    private void editOrderStatusDialog() {
        final String[] options = {getString(R.string.emanalise), getString(R.string.emproducao),
                getString(R.string.saiuparaen), getString(R.string.concluido), getString(R.string.cancelado)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme);
        builder.setTitle(getString(R.string.statusdoped))
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String selectedOption = options[i];

                        if(Objects.equals(selectedOption, getString(R.string.cancelado))){
                            if(formadepagamentoTv.getText().toString().equals(getString(R.string.pagonocartao))){
                                AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailsSellerActivity.this);
                                builder.setTitle(getString(R.string.atencao));
                                builder.setMessage("Este pedido será estornado! Pedidos pagos no app Vegan são cobrados automaticamente, então quando cancelados são enviadas solicitações de estorno automaticamente, se você cancelou por engano, altere novamente o status do pedido e entre em contato com o time Vegan para tentarmos cancelar o estorno. Obrigado pela atenção.");

                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        }



                        editOrderStatus(selectedOption);
                        sendPush();
                    }
                })
                .show();
    }

    private void editOrderStatus(final String selectedOption) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("orderStatus", ""+selectedOption);
        Intent data = getIntent();
        String orderId = data.getStringExtra("orderId");
        String orderBy = data.getStringExtra("orderBy");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("OrderTo").child(userID);
        ref.child(orderId)
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        String message = getString(R.string.estepedidoesta)+" "+selectedOption;
                        Toast.makeText(OrderDetailsSellerActivity.this, message , Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(OrderDetailsSellerActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("OrderBy").child(orderBy);
        ref2.child(orderId)
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        sendPush();
                        orderStatusTv.setText(selectedOption);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(OrderDetailsSellerActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendPush() {
        if(Objects.equals(orderStatusTv.getText().toString(), getString(R.string.cancelado))&&formadepagamentoTv.getText().toString().equals(getString(R.string.pagonocartao))){

                DocumentReference docRef = fStore.collection("Users").document(orderBy);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                clientToken = document.getString("token");
                                FCMSend.pushNotification(
                                        OrderDetailsSellerActivity.this,
                                        clientToken,
                                        getString(R.string.ostatusdoseuped),
                                        getString(R.string.alterousta)+" "+orderStatusTv.getText().toString()+" "+getString(R.string.comoseucancelado)
                                );
                            }
                        }
                    }
                });

                //FOREGROUND
                String timestampush = ""+System.currentTimeMillis();

                long l = Long.parseLong(timestampush);

                Calendar cal = Calendar.getInstance(Locale.getDefault());
                cal.setTimeInMillis(l);
                String date = DateFormat.format("dd/MM/yy", cal).toString();
                String hour = DateFormat.format("HH:mm", cal).toString();

                int Emoji = 0x1F49A;

                String stringEmoji = String.valueOf(Emoji);

                stringEmoji = (new String(Character.toChars(Emoji)));

                final HashMap<String, Object> hashMap1 = new HashMap<>();
                hashMap1.put("title", getString(R.string.suaultimano));
                hashMap1.put("text", getString(R.string.ostatusdoseuped)+stringEmoji+" "+hour+" "+date+" "+orderStatusTv.getText().toString()+" "+getString(R.string.comoseucancelado));

                final DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("ZYPUSH "+clientID);
                ref1.setValue(hashMap1)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });



        } else {

            DocumentReference docRef = fStore.collection("Users").document(orderBy);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            clientToken = document.getString("token");
                            FCMSend.pushNotification(
                                    OrderDetailsSellerActivity.this,
                                    clientToken,
                                    getString(R.string.ostatusdoseuped),
                                    getString(R.string.alterousta) + " " + orderStatusTv.getText().toString()
                            );
                        }
                    }
                }
            });

            //FOREGROUND
            String timestampush = "" + System.currentTimeMillis();

            long l = Long.parseLong(timestampush);

            Calendar cal = Calendar.getInstance(Locale.getDefault());
            cal.setTimeInMillis(l);
            String date = DateFormat.format("dd/MM/yy", cal).toString();
            String hour = DateFormat.format("HH:mm", cal).toString();

            int Emoji = 0x1F49A;

            String stringEmoji = String.valueOf(Emoji);

            stringEmoji = (new String(Character.toChars(Emoji)));

            final HashMap<String, Object> hashMap1 = new HashMap<>();
            hashMap1.put("title", getString(R.string.suaultimano));
            hashMap1.put("text", getString(R.string.ostatusdoseuped) + stringEmoji + " " + hour + " " + date + " " + orderStatusTv.getText().toString());

            final DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("ZYPUSH " + clientID);
            ref1.setValue(hashMap1)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
        }
    }

    private void loadOrderDetails() {
        Intent data = getIntent();
        orderId = data.getStringExtra("orderId");
        String orderBy = data.getStringExtra("orderBy");
         cost = data.getStringExtra("cost");
        String status = data.getStringExtra("status");
         troco = data.getStringExtra("troco");
         formadepagamento = data.getStringExtra("formadepagamento");
        cpm = data.getStringExtra("cpm");
        String tipoDCompra = data.getStringExtra("tipoDeCompra");
        codentrega = data.getStringExtra("codentrega");
        String schedule = data.getStringExtra("schedule");
        String obss = data.getStringExtra("obs");

        obsTv.setText(obss);
        agendamentoTv.setText(schedule);
        if(Objects.equals(schedule, "Agende o seu pedido:")){
            agendamentoTv.setText("");
        }
        codentregaTv.setText(codentrega);
        tipoDeCompraTv.setText(tipoDCompra);

        cpmTv.setText(cpm);
        if(Objects.equals(cpm, "null")){
            cpmTv.setText("");
        }

        long l = Long.parseLong(orderId);

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(l);
        String date = DateFormat.format("dd/MM/yy", cal).toString();
        String time = DateFormat.format("HH:mm", cal).toString();

        if (Objects.equals(troco, "00,00")){
            trocoTv.setText(getString(R.string.naohavera));
        } else {
            trocoTv.setText(troco);
        }

        formadepagamentoTv.setText(formadepagamento);
        dateTv.setText(date);
        timeTv.setText(time);
        orderIdTv.setText(orderId);
        orderStatusTv.setText(status);
        String arrange = cost.replace(finalcoin,"");
        amountTv.setText(finalcoin +arrange.replace(".",","));

        String ana =  getString(R.string.emanalise);
        String emp = getString(R.string.emproducao);
    String sai = getString(R.string.saiuparaen);
       String con = getString(R.string.concluido);
   String can = getString(R.string.cancelado);

        if(Objects.equals(status, ana)) {
                orderStatusTv.setTextColor(getResources().getColor(R.color.blue));
        }
        if(Objects.equals(status, emp)) {
            orderStatusTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
        if(Objects.equals(status, sai)) {
            orderStatusTv.setTextColor(getResources().getColor(R.color.yellow));
        }
        if(Objects.equals(status, con)) {
            orderStatusTv.setTextColor(getResources().getColor(R.color.green));
        }

        if(Objects.equals(status, "Recebido")) {
            orderStatusTv.setTextColor(getResources().getColor(R.color.green));
        }

        if(Objects.equals(status, can)) {
            orderStatusTv.setTextColor(getResources().getColor(R.color.red));
        }

        String userID;
        userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection("Adm's").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String brandIcon = document.getString("shopIcon");
                        String brandName = document.getString("fName");

                        brandTv.setText(brandName);
                        try {
                            Picasso.get().load(brandIcon).placeholder(R.drawable.loading).into(shopFoto);
                        } catch (Exception e) {
                            shopFoto.setImageResource(R.drawable.error);
                        }
                    }
                }
            }
        });
        FirebaseFirestore root = FirebaseFirestore.getInstance();
        root.collection("Users").document(orderBy).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        clientID = document.getString("userID");
                        String avatar = document.getString("avatar");
                        fName = document.getString("fName");
                        String email = document.getString("email");
                        phone = document.getString("phone");
                        country = document.getString("country");
                        state = document.getString("state");
                        city = document.getString("city");
                        neighborhood = document.getString("neighborhood");
                        street = document.getString("street");
                        number = document.getString("number");
                        reference = document.getString("reference");
                        cep = document.getString("cep");

                        username.setText(fName);
                        emailTv.setText(email);
                        phoneTv.setText(phone);
                        String fulladress = country+", "+state+", "+city+", "+neighborhood+", "+street+", "+number+", "+reference+", "+cep;
                        addressTv.setText(fulladress);
                        try {
                            Picasso.get().load(avatar).placeholder(R.drawable.loading).into(avataruser);
                        } catch (Exception e) {
                            avataruser.setImageResource(R.drawable.error);
                        }
                        switch (Objects.requireNonNull(avatar)) {
                            case "pig":
                                try {
                                    Picasso.get().load(R.drawable.ppig).placeholder(R.drawable.loading).into(avataruser);
                                }
                                catch (Exception e) {
                                    avataruser.setImageDrawable(ContextCompat.getDrawable(OrderDetailsSellerActivity.this, error ));
                                }
                                break;
                            case "cow":
                                try {
                                    Picasso.get().load(R.drawable.pcow).placeholder(R.drawable.loading).into(avataruser);
                                }
                                catch (Exception e) {
                                    avataruser.setImageDrawable(ContextCompat.getDrawable(OrderDetailsSellerActivity.this, error ));
                                }
                                break;
                            case "sheep":
                                try {
                                    Picasso.get().load(R.drawable.psheep).placeholder(R.drawable.loading).into(avataruser);
                                }
                                catch (Exception e) {
                                    avataruser.setImageDrawable(ContextCompat.getDrawable(OrderDetailsSellerActivity.this, error ));
                                }
                                break;
                            case "fish":
                                try {
                                    Picasso.get().load(R.drawable.pfish).placeholder(R.drawable.loading).into(avataruser);
                                }
                                catch (Exception e) {
                                    avataruser.setImageDrawable(ContextCompat.getDrawable(OrderDetailsSellerActivity.this, error ));
                                }
                                break;
                            case "goat":
                                try {
                                    Picasso.get().load(R.drawable.pgoat).placeholder(R.drawable.loading).into(avataruser);
                                }
                                catch (Exception e) {
                                    avataruser.setImageDrawable(ContextCompat.getDrawable(OrderDetailsSellerActivity.this, error ));
                                }
                                break;
                            case "horse":
                                try {
                                    Picasso.get().load(R.drawable.phorse).placeholder(R.drawable.loading).into(avataruser);
                                }
                                catch (Exception e) {
                                    avataruser.setImageDrawable(ContextCompat.getDrawable(OrderDetailsSellerActivity.this, error ));
                                }
                                break;
                            case "bee":
                                try {
                                    Picasso.get().load(R.drawable.pbee).placeholder(R.drawable.loading).into(avataruser);
                                }
                                catch (Exception e) {
                                    avataruser.setImageDrawable(ContextCompat.getDrawable(OrderDetailsSellerActivity.this, error ));
                                }
                                break;
                            case "rooster":
                                try {
                                    Picasso.get().load(R.drawable.pchicken).placeholder(R.drawable.loading).into(avataruser);
                                }
                                catch (Exception e) {
                                    avataruser.setImageDrawable(ContextCompat.getDrawable(OrderDetailsSellerActivity.this, error ));
                                }
                                break;
                            case "rat":
                                try {
                                    Picasso.get().load(R.drawable.prat).placeholder(R.drawable.loading).into(avataruser);
                                }
                                catch (Exception e) {
                                    avataruser.setImageDrawable(ContextCompat.getDrawable(OrderDetailsSellerActivity.this, error ));
                                }
                                break;
                            case "rabbit":
                                try {
                                    Picasso.get().load(R.drawable.prabbit).placeholder(R.drawable.loading).into(avataruser);
                                }
                                catch (Exception e) {
                                    avataruser.setImageDrawable(ContextCompat.getDrawable(OrderDetailsSellerActivity.this, error ));
                                }
                                break;
                        }




                    }
                }
            }
        });
    }


    private void loadOrderedItems(){
        orderedItemArrayList = new ArrayList<>();
        userID = firebaseAuth.getCurrentUser().getUid();
        Intent data = getIntent();
        String orderId = data.getStringExtra("orderId");
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("OrderTo").child(userID);
            ref.child(orderId).child("Items").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    orderedItemArrayList.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        ModelOrderedItem modelOrderedItem = ds.getValue(ModelOrderedItem.class);
                        orderedItemArrayList.add(modelOrderedItem);
                    }
                    adapterOrderedItem = new AdapterOrderedItem(OrderDetailsSellerActivity.this, orderedItemArrayList);
                    itemsRv.setAdapter(adapterOrderedItem);

                    int itens = Objects.requireNonNull(itemsRv.getAdapter()).getItemCount();
                    totalItemsTv.setText(String.valueOf(itens));
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
