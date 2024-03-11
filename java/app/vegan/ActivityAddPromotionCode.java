package app.vegan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class ActivityAddPromotionCode extends AppCompatActivity {

    private ImageButton backBtn;
    private EditText promoCodeEt, promoDescriptionEt, promoPriceEt, minimumOrderPriceEt;
    private TextView expireDateTv, titleTv;
    private Button addBtn;
    private FirebaseAuth firebaseAuth;
   private ProgressDialog progressDialog;
   private String promoCode;
   private long timestampex;
   private boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_promotion_code);

        backBtn = findViewById(R.id.backBtn);
        titleTv = findViewById(R.id.titleTv);
        promoCodeEt = findViewById(R.id.promoCodeEt);
        promoDescriptionEt = findViewById(R.id.promoDescriptionEt);
        promoPriceEt = findViewById(R.id.promoPriceEt);
        minimumOrderPriceEt = findViewById(R.id.minimumOrderPriceEt);
        expireDateTv = findViewById(R.id.expireDateTv);
        promoCodeEt = findViewById(R.id.promoCodeEt);
        addBtn = findViewById(R.id.addBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.aguardeum);
        progressDialog.setCanceledOnTouchOutside(false);

        Intent intent = getIntent();
        if(intent.getStringExtra("promoId") !=null){
            promoCode = intent.getStringExtra("promoCode");
            titleTv.setText("Editar");
            isUpdating = true;
            loadPromoInfo();
        }
        else {
            titleTv.setText(R.string.adicionarcupo);
            isUpdating = false;
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        expireDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickDialog();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();
            }
        });
    }

    private void loadPromoInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getUid()).child("Promotions").child(promoCode);
        ref
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String description = ""+snapshot.child("description").getValue();
                        String promoCode = ""+snapshot.child("promoCode").getValue();
                        String promoPrice = ""+snapshot.child("promoPrice").getValue();
                        String minimumOrderPrice = ""+snapshot.child("minimumOrderPrice").getValue();
                        String expireDate = ""+snapshot.child("ExpireDate").getValue();
                        promoCodeEt.setText(promoCode);
                        promoDescriptionEt.setText(description);
                        promoPriceEt.setText(promoPrice);
                        minimumOrderPriceEt.setText(minimumOrderPrice);
                        expireDateTv.setText(expireDate);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    private void datePickDialog() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        c.getTimeInMillis();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.MyAlertDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                DecimalFormat mFormat = new DecimalFormat("00");
                String pDay = mFormat.format(dayOfMonth);
                String pMonth = mFormat.format(monthOfYear+1);
                String pYear = ""+year;
                String pDate = pDay + "/"+pMonth+"/"+pYear;
                expireDateTv.setText(pDate);
                Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                timestampex = calendar.getTimeInMillis();
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
    }
    private String description, promoPrice, minimumOrderPrice, expireDate;
    private void inputData(){
        promoCode = promoCodeEt.getText().toString().trim();
        description = promoDescriptionEt.getText().toString().trim();
        promoPrice = promoPriceEt.getText().toString().trim();
        minimumOrderPrice = minimumOrderPriceEt.getText().toString().trim();
        expireDate = expireDateTv.getText().toString().trim();
        if(TextUtils.isEmpty(promoCode)){
            Toast.makeText(this, R.string.insiraumcupo, Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(description)){
            Toast.makeText(this, R.string.insiradescdocup, Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(promoPrice)){
            Toast.makeText(this, R.string.insiravalordodes, Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(minimumOrderPrice)){
            Toast.makeText(this, R.string.insirapedmin, Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(expireDate)){
            Toast.makeText(this, R.string.escolhaexp, Toast.LENGTH_SHORT).show();
            return;
        }
        if(isUpdating){
            updateDataToDb();
        }
        else {
            addDatatoDb();
        }
    }

    private void updateDataToDb() {
        progressDialog.setMessage(getString(R.string.atualizandocup));
        progressDialog.show();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("description",""+description);
        hashMap.put("promoCode",""+promoCode);
        hashMap.put("promoPrice",""+promoPrice);
        hashMap.put("mimnimumOrderPrice",""+minimumOrderPrice);
        hashMap.put("expiredDate",""+expireDate);
        hashMap.put("expiredTimestamp",""+timestampex);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getUid()).child("Promotions").child(promoCode);
        ref
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(ActivityAddPromotionCode.this, R.string.atualizado, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(ActivityAddPromotionCode.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addDatatoDb() {
        progressDialog.setMessage(getString(R.string.adicionando));
        progressDialog.show();
        String timestamp = ""+System.currentTimeMillis();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id",""+timestamp);
        hashMap.put("timestamp",""+timestamp);
        hashMap.put("description",""+description);
        hashMap.put("promoCode",""+promoCode);
        hashMap.put("promoPrice",""+promoPrice);
        hashMap.put("mimnimumOrderPrice",""+minimumOrderPrice);
        hashMap.put("expiredDate",""+expireDate);
        hashMap.put("expiredTimestamp",""+timestampex);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getUid()).child("Promotions").child(promoCode);
        ref
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(ActivityAddPromotionCode.this, R.string.cupomadd, Toast.LENGTH_SHORT).show();
                        Intent intentLoadNewActivity = new Intent(ActivityAddPromotionCode.this, ActivityPromotionCodes.class);
                        startActivity(intentLoadNewActivity);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(ActivityAddPromotionCode.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
