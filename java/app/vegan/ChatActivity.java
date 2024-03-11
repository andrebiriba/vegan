package app.vegan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity implements Serializable {

    private GroupAdapter adapter;
    private EditText editChat;
    private String contatoId, userId, contatoPhoto, contatoName, avatar, nameuser, token, ok,sad,afd;
    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private ImageView backBtn;
    private RecyclerView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent data = getIntent();
        contatoId = data.getStringExtra("contatoId");
        contatoName = data.getStringExtra("contatoName");
        contatoPhoto = data.getStringExtra("contatoPhoto");

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userId = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

        editChat = findViewById(R.id.editText);
        rv = findViewById(R.id.recycler_chat);
        TextView ChatName = findViewById(R.id.textView10);
        ImageButton btnChat = findViewById(R.id.imageButton16);
        backBtn = findViewById(R.id.imageView36);

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7 = new Intent(ChatActivity.this, AllChatsActivity.class);
                startActivity(intent7);
                finish();
            }
        });

        ChatName.setText(contatoName);

        adapter = new GroupAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        scrollToBot();
        fetchMessages();
        loadMyInfo();

        Map<String,Object> hashMap = new HashMap<>();
        hashMap.put("neo","false");

        DocumentReference docRef = fStore.collection("Contacts "+fAuth.getCurrentUser().getUid()).document(contatoId);
        docRef.update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void scrollToBot(){
        rv.smoothScrollToPosition(1999999999);
    }

    private void loadMyInfo() {
        String userID;

        FirebaseUser mFirebaseUser = fAuth.getCurrentUser();
        if(mFirebaseUser != null) {
            userID = mFirebaseUser.getUid(); //Do what you need to do with the id
            DocumentReference docRef = fStore.collection("Users").document(userID);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            nameuser = document.getString("fName");
                            avatar = document.getString("avatar");

                            switch (Objects.requireNonNull(avatar)) {
                                case "pig":
                                avatar = "https://firebasestorage.googleapis.com/v0/b/vegan-52d4a.appspot.com/o/avatar%2Fppig.png?alt=media&token=086a99de-0ce4-4157-8cbe-43f80d1395cd";
                                    break;
                                case "cow":
                                    avatar = "https://firebasestorage.googleapis.com/v0/b/vegan-52d4a.appspot.com/o/avatar%2Fpcow.png?alt=media&token=ca74a573-fe03-4ae8-bc58-caace8da050d";
                                    break;
                                case "sheep":
                                    avatar = "https://firebasestorage.googleapis.com/v0/b/vegan-52d4a.appspot.com/o/avatar%2Fpsheep.png?alt=media&token=8231f1ea-9646-4102-bd5a-c68b223210ba";
                                    break;
                                case "fish":
                                avatar = "https://firebasestorage.googleapis.com/v0/b/vegan-52d4a.appspot.com/o/avatar%2Fpfish.png?alt=media&token=98dc76a9-f100-49d1-8910-805eaf3ed7b8";
                                    break;
                                case "goat":
                                    avatar = "https://firebasestorage.googleapis.com/v0/b/vegan-52d4a.appspot.com/o/avatar%2Fpgoat.png?alt=media&token=563a669c-c8bf-40b6-b791-8b345163d8f4";
                                    break;
                                case "horse":
                                    avatar = "https://firebasestorage.googleapis.com/v0/b/vegan-52d4a.appspot.com/o/avatar%2Fphorse.png?alt=media&token=e690660e-3416-4186-b3e5-ac5ac091c46e";
                                    break;
                                case "bee":
                                    avatar = "https://firebasestorage.googleapis.com/v0/b/vegan-52d4a.appspot.com/o/avatar%2Fpbee.png?alt=media&token=007c90ee-6c3c-4900-8cdc-9516d0a3ba87";
                                    break;
                                case "rooster":
                                    avatar = "https://firebasestorage.googleapis.com/v0/b/vegan-52d4a.appspot.com/o/avatar%2Fpchicken.png?alt=media&token=a62aedab-c891-4dbe-b702-da1509effbb6";
                                    break;
                                case "rat":
                                    avatar = "https://firebasestorage.googleapis.com/v0/b/vegan-52d4a.appspot.com/o/avatar%2Fprat.png?alt=media&token=1eb2b6bb-b6ba-4f5e-8269-824a5d2c6f9e";
                                    break;
                                case "rabbit":
                                    avatar = "https://firebasestorage.googleapis.com/v0/b/vegan-52d4a.appspot.com/o/avatar%2Fprabbit.png?alt=media&token=c14292c6-3181-49e5-a563-f62b42e7db37";
                                    break;
                            }
                        }
                    }
                }
            });
        }

        if (Objects.equals(nameuser, "") | nameuser == null) {

            userID = mFirebaseUser.getUid(); //Do what you need to do with the id
            DocumentReference docRef = fStore.collection("Adm's").document(userID);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            nameuser = document.getString("fName");
                            avatar = document.getString("shopIcon");

                            String type = document.getString("type");
                            if(Objects.equals(type, "Services")){
                                nameuser = document.getString("serviceTitle");
                                avatar = document.getString("profilePhoto");
                            }
                        }
                    }
                }
            });
        }
    }


    private void fetchMessages() {
        if(userId != null){
            String fromId = userId;
            String toId = contatoId;

            FirebaseFirestore.getInstance().collection("/conversations")
                    .document(fromId)
                    .collection(toId)
                    .orderBy("timestamp", Query.Direction.ASCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            List<DocumentChange> documentChanges = value.getDocumentChanges();

                            if( documentChanges != null) {
                                for (DocumentChange doc: documentChanges){
                                    if(doc.getType() == DocumentChange.Type.ADDED) {
                                        Message message = doc.getDocument().toObject(Message.class);
                                        adapter.add(new MessageItem(message));
                                        scrollToBot();
                                    }
                                }
                            }
                        }
                    });
            scrollToBot();
        }
    }

    private void sendMessage() {
        String text = editChat.getText().toString();

        Intent data = getIntent();
        contatoId = data.getStringExtra("contatoId");
        contatoName = data.getStringExtra("contatoName");
        contatoPhoto = data.getStringExtra("contatoPhoto");

        editChat.setText(null);

        String fromId = FirebaseAuth.getInstance().getUid();
        String toId = contatoId;
        long timestamp = System.currentTimeMillis();

        Message message = new Message();
        message.setFromId(fromId);
        message.setToId(toId);
        message.setTimestamp(timestamp);
        message.setText(text);
        message.setFromName(nameuser);
        message.setToName(contatoName);
        message.setFromPhoto(avatar);
        message.setToPhoto(contatoPhoto);

        Map<String,Object> contact = new HashMap<>();
        contact.put("contact",toId);
        contact.put("timestamp",timestamp);
        contact.put("username",contatoName);
        contact.put("Photo",contatoPhoto);

        Map<String,Object> contact2 = new HashMap<>();
        contact2.put("contact",fromId);
        contact2.put("neo","true");
        contact2.put("timestamp",timestamp);
        contact2.put("username",nameuser);
        contact2.put("Photo",avatar);

        if(!message.getText().isEmpty()){
            if (fromId == null){fromId = "null";}
            String finalFromId = fromId;
            FirebaseFirestore.getInstance().collection("/conversations")
                    .document(fromId)
                    .collection(toId)
                    .add(message)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Teste", e.getMessage(),e);

                        }
                    });

            FirebaseFirestore.getInstance().collection("/conversations")
                    .document(toId)
                    .collection(fromId)
                    .add(message)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

            DocumentReference docRef = fStore.collection("Contacts "+fromId).document(toId);
            docRef.set(contact).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });

            DocumentReference docRef2 = fStore.collection("Contacts "+ toId).document(fromId);
            docRef2.set(contact2).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
        scrollToBot();

        try {
                DocumentReference docRef2 = fStore.collection("Adm's").document(toId);
                docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                token = document.getString("token");

                                String sad = getApplicationContext().getString(R.string.novamsg);
                                String happy = nameuser + " ";
                                 ok = happy + sad;
                                FCMSend.pushNotification(
                                        ChatActivity.this,
                                        token,
                                        ok,
                                        text);
                            }
                        }
                    }
                });
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
        DocumentReference docRef = fStore.collection("Users").document(toId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        token = document.getString("token");
                        String sad = getApplicationContext().getString(R.string.novamsg);
                        String happy = nameuser + " ";
                         ok = happy + sad;

                        FCMSend.pushNotification(
                                ChatActivity.this,
                                token,
                                ok,
                                text);
                    }

                }
            }
        });
        } catch (Exception e) {
            e.printStackTrace();
        }


        //foreground
        int Emoji = 0x1F49A;

        String stringEmoji = String.valueOf(Emoji);

        stringEmoji = (new String(Character.toChars(Emoji)));

        String timestampush = ""+System.currentTimeMillis();

        long l = Long.parseLong(timestampush);

        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(l);
        String date = DateFormat.format("dd/MM/yy", cal).toString();
        String hour = DateFormat.format("HH:mm", cal).toString();

        afd = hour + " " + date + " " + getString(R.string.nahoralocal);

        final HashMap<String, Object> hashMap1 = new HashMap<>();
        hashMap1.put("title", ""+getApplicationContext().getString(R.string.suaultimano));
        hashMap1.put("text", ""+nameuser+stringEmoji+hour+" "+date+" "+text);

        final DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("ZYPUSH "+toId);
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


    private class MessageItem extends Item<GroupieViewHolder> {

        private final Message message;

    public MessageItem(Message message) {
        this.message = message;
    }

        @Override
    public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
        Button txtMsg = viewHolder.itemView.findViewById(R.id.button9);
        ShapeableImageView imgMessage = viewHolder.itemView.findViewById(R.id.imageView6);

        txtMsg.setText(message.getText());

        try {
            Picasso.get().load(contatoPhoto).placeholder(R.drawable.loading).into(imgMessage);
        } catch (Exception e) {
            if (imgMessage == null)
                return;
            else
                imgMessage.setImageResource(R.drawable.error);
        }

        }

    @Override
    public int getLayout() {
        return message.getFromId().equals(FirebaseAuth.getInstance().getUid()) ? R.layout.item_from_message
                : R.layout.item_to_message;
    }
}

}
