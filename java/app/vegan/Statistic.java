package app.vegan;

import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Statistic extends AppCompatActivity {

    public FirebaseAuth firebaseAuth;
    private String uid, total, tota, tota2, din, din2;
    private TextView totalTv, concluidosTv, canceladosTv, emAnaTv, emEntregaTv, emProTv, txtCartao, textdinheiro,
            totalDinMens, totalCardMens, totalARecCart, totalAPagDinh, statusMens, dataEstTitle, dataEst;
    private Double conc, canc,dinheiro, empro, saiup, emana, numPed, cartao;
    private ImageView load, arrowMes;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic2);

        dataEst = findViewById(R.id.dataEst);
        dataEstTitle = findViewById(R.id.dataEstTitle);
        totalDinMens = findViewById(R.id.totalDinMens);
        totalCardMens = findViewById(R.id.totalCardMens);
        totalARecCart = findViewById(R.id.totalARecCart);
        totalAPagDinh = findViewById(R.id.totalAPagDinh);
        statusMens = findViewById(R.id.statusMens);
        arrowMes = findViewById(R.id.imageView30);
        back = findViewById(R.id.backBtnCategory);
        load = findViewById(R.id.imageView7);
        concluidosTv = findViewById(R.id.txtConcluidos);
        totalTv = findViewById(R.id.txtTotalVendas);
        canceladosTv = findViewById(R.id.txtCancelados);
        emAnaTv = findViewById(R.id.txtEmAnalise);
        emEntregaTv = findViewById(R.id.txtSaiuEntrega);
        emProTv = findViewById(R.id.txtEmProd);
        txtCartao = findViewById(R.id.txtCartao);
        textdinheiro = findViewById(R.id.textdinheiro);

        conc = 0.0;
        canc = 0.0;
        empro = 0.0;
        saiup = 0.0;
        emana = 0.0;
        cartao= 0.0;
        dinheiro=0.0;

        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
        din="0";
        din2="0";
        total="0";
        tota="0";
        tota2="0";
        loadStatistics();

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

                AlertDialog.Builder builder = new AlertDialog.Builder(Statistic.this, R.style.MyAlertDialogTheme);
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

                    //din
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

                Double calcartao0 = Double.parseDouble(din);
                Double calcartao1 = Double.parseDouble(total);
                Double calcartao2 = calcartao1-calcartao0;
                String finalcar = "R$"+String.valueOf(calcartao2);
                String finaldin = "R$"+String.valueOf(calcartao0);

                totalTv.setText("R$"+total.replace(".",","));
                concluidosTv.setText(String.valueOf(conc).replace(".",",").replace("null",""));
                canceladosTv.setText(String.valueOf(canc).replace(".",",").replace("null",""));
                emAnaTv.setText(String.valueOf(emana).replace(".",",").replace("null",""));
                emProTv.setText(String.valueOf(empro).replace(".",",").replace("null",""));
                emEntregaTv.setText(String.valueOf(saiup).replace(".",",").replace("null",""));
                txtCartao.setText(finalcar.replace(".",",").replace("null",""));
                textdinheiro.setText(finaldin.replace(".",",").replace("null",""));

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

                load.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Statistic.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
