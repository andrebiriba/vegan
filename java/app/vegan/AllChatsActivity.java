package app.vegan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class AllChatsActivity extends AppCompatActivity implements Serializable {
    private AdapterContacts adapter;
    private ArrayList<ModelContact> contactList;
    FirebaseAuth fAuth;
    FirebaseUser user;
    private String uid;
    private RecyclerView recyclerView;
    private CardView floatingButton;
    private ImageView imageButtonBack;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_chats);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        recyclerView = findViewById(R.id.recycler);
        floatingButton = findViewById(R.id.floatingButton);
        imageButtonBack = findViewById(R.id.imageView40);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        testUserCurrent();

        fetchUsers();

        imageButtonBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        floatingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllChatsActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    public void testUserCurrent() {
        if ( fAuth.getCurrentUser ( ) != null ) {
            floatingButton.setVisibility(View.GONE);
            if(user != null){
                uid = user.getUid();
            }
        } else {
            floatingButton.setVisibility(View.VISIBLE);
        }
    }

    private void fetchUsers() {
        contactList = new ArrayList<>();
        String userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection("Contacts "+userID)
                .orderBy("timestamp")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        contactList.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ModelContact modelcontact = document.toObject(ModelContact.class);
                                contactList.add(modelcontact);}

                            adapter = new AdapterContacts(AllChatsActivity.this, contactList);
                                recyclerView.setAdapter(adapter);
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(AllChatsActivity.this, LinearLayoutManager.VERTICAL, true);
                                    layoutManager.setStackFromEnd(true);
                            recyclerView.setLayoutManager(layoutManager);
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.voltar:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
