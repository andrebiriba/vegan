package app.vegan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ActivityPromotionCodes extends AppCompatActivity {

    private ImageButton backBtn, addPromoBtn, filterBtn;
    private TextView filteredTv;
    private RecyclerView promoRv;
    private FirebaseAuth firebaseAuth;
    private ArrayList<ModelPromotion> promotionArrayList;
    private AdapterPromotionShop adapterPromotionShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_codes);

        backBtn = findViewById(R.id.backBtn);
        addPromoBtn = findViewById(R.id.addPromoBtn);
        filteredTv = findViewById(R.id.filteredTv);
        filterBtn = findViewById(R.id.filterBtn);
        promoRv = findViewById(R.id.promoRv);

        firebaseAuth = FirebaseAuth.getInstance();
        loadAllPromoCodes();

        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        addPromoBtn = findViewById(R.id.addPromoBtn);

        addPromoBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(ActivityPromotionCodes.this, ActivityAddPromotionCode.class);
                startActivity(intentLoadNewActivity);
            }
        });

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog();
            }
        });

    }

    private void filterDialog() {
        String[] options = {getString(R.string.todos), getString(R.string.expirados), getString(R.string.vigentes)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogTheme);
        builder.setTitle(R.string.filtrarpor)
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0){
                            filteredTv.setText(R.string.todos);
                            loadAllPromoCodes();
                        }
                      else  if(i==1){
                            filteredTv.setText(R.string.expirados);
                            loadExpiredPromoCodes();
                        }
                      else  if(i==2){
                            filteredTv.setText(R.string.vigentes);
                            loadNotExpiredPromoCodes();
                        }
                    }
                })
                .show();
    }

    public void onBackPressed ( ) {
        finish ( );
    }

    private void loadAllPromoCodes(){
        promotionArrayList = new ArrayList<>();

        Query reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getUid()).child("Promotions").limitToFirst(20);
        reference
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        promotionArrayList.clear();
                        for(DataSnapshot ds:snapshot.getChildren()){
                            ModelPromotion modelPromotion = ds.getValue(ModelPromotion.class);
                            promotionArrayList.add(modelPromotion);
                        }
                        adapterPromotionShop = new AdapterPromotionShop(ActivityPromotionCodes.this, promotionArrayList);
                        promoRv.setAdapter(adapterPromotionShop);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void loadExpiredPromoCodes(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        final String todayDate = day +"/"+month +  "/" + year;
        promotionArrayList = new ArrayList<>();

        Query reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getUid()).child("Promotions").limitToFirst(20);
        reference
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        promotionArrayList.clear();
                        for(DataSnapshot ds:snapshot.getChildren()){
                            ModelPromotion modelPromotion = ds.getValue(ModelPromotion.class);

                            String expDate = modelPromotion.getExpiredDate();
                            try{
                                SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
                                Date currentDate = sdformat.parse(todayDate);
                                Date expireDate = sdformat.parse(expDate);
                                if(expireDate.compareTo(currentDate) > 0){
                                }
                                else if(expireDate.compareTo(currentDate) < 0) {
                                }
                                else if(expireDate.compareTo(currentDate) == 0) {
                                }
                            }
                            catch(Exception e){
                            }
                            promotionArrayList.add(modelPromotion);
                        }
                        adapterPromotionShop = new AdapterPromotionShop(ActivityPromotionCodes.this, promotionArrayList);
                        promoRv.setAdapter(adapterPromotionShop);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    private void loadNotExpiredPromoCodes(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        final String todayDate = day +"/"+month +  "/" + year;

        promotionArrayList = new ArrayList<>();

        Query reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getUid()).child("Promotions").limitToFirst(20);
        reference
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        promotionArrayList.clear();
                        for(DataSnapshot ds:snapshot.getChildren()){
                            ModelPromotion modelPromotion = ds.getValue(ModelPromotion.class);

                            String expDate = modelPromotion.getExpiredDate();

                            try{
                                SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
                                Date currentDate = sdformat.parse(todayDate);
                                Date expireDate = sdformat.parse(expDate);
                                if(expireDate.compareTo(currentDate) > 0){
                                    promotionArrayList.add(modelPromotion);

                                }
                                else if(expireDate.compareTo(currentDate) < 0) {
                                }
                                else if(expireDate.compareTo(currentDate) == 0) {
                                    promotionArrayList.add(modelPromotion);
                                }
                            }
                            catch(Exception e){
                            }
                            promotionArrayList.add(modelPromotion);
                        }
                        adapterPromotionShop = new AdapterPromotionShop(ActivityPromotionCodes.this, promotionArrayList);
                        promoRv.setAdapter(adapterPromotionShop);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

}
