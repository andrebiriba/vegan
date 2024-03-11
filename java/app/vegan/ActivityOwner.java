package app.vegan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ActivityOwner extends AppCompatActivity {
    private RecyclerView brandsRv;
    private ArrayList<ModelLocal> localList;
    private AdapterLocalOwner adapterLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

        brandsRv = findViewById(R.id.brandsRv);

        loadAllShops();
    }

    private void loadAllShops() {
        localList = new ArrayList<>();
        localList.clear();
        FirebaseFirestore nearby = FirebaseFirestore.getInstance();
        nearby.collection("Adm's")
                .whereNotEqualTo("type", "Services")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                    adapterLocal = new AdapterLocalOwner(ActivityOwner.this, localList);
                    brandsRv.setAdapter(adapterLocal);

                    brandsRv.getAdapter().notifyDataSetChanged();
                }
            }
        });
    }
}
