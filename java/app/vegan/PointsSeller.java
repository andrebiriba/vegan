package app.vegan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class PointsSeller extends AppCompatActivity {

    private EditText regrasEt, expiraEt, premioEt;
    private Button salvar;
    private String premio, expira, regras, shopuid;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private TextView shopName;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_seller);

        back = findViewById(R.id.imageButton12);
        premioEt = findViewById(R.id.premioEt);
        expiraEt = findViewById(R.id.expiraEt);
        regrasEt = findViewById(R.id.regrasEt);
        salvar = findViewById(R.id.salvar);
        shopName = findViewById(R.id.txtPonts);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        shopuid = fAuth.getCurrentUser().getUid();

        Intent data = getIntent();
        String xs = data.getStringExtra("brandName");
        shopName.setText(xs);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                savePoints();
            }
        });

    }

    private void savePoints() {
        if(premioEt.getText().toString().isEmpty() || expiraEt.getText().toString().isEmpty()
                || regrasEt.getText().toString().isEmpty()) {
            Toast.makeText(PointsSeller.this, R.string.umoumaiscam, Toast.LENGTH_SHORT).show();
        } else {
            premio = premioEt.getText().toString();
            regras = regrasEt.getText().toString();
            expira = expiraEt.getText().toString();

            DocumentReference docRef0 = fStore.collection("Adm's").document(shopuid);
            Map<String,Object> edited0 = new HashMap<>();
            edited0.put("possuiFidelidade","true");
            docRef0.update(edited0).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(PointsSeller.this, R.string.normasdefideatua, Toast.LENGTH_SHORT).show();
                }
            });


            DocumentReference docRef = fStore.collection("Points "+shopuid).document("Fidelidade");
            Map<String,Object> edited = new HashMap<>();
            edited.put("premio",premio);
            edited.put("regras",regras);
            edited.put("expira",expira);
            docRef.set(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(PointsSeller.this, R.string.normasdefideatua, Toast.LENGTH_SHORT).show();
                }
            });


        }
    }
}
