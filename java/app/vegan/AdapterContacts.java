package app.vegan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Objects;

public class AdapterContacts extends RecyclerView.Adapter<AdapterContacts.HolderContacts>{

    private final Context context;
    public ArrayList<ModelContact> contactList;

    public AdapterContacts(Context context, ArrayList<ModelContact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public HolderContacts onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_chat, parent, false);
        return new HolderContacts(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderContacts holder, int position) {
        ModelContact contact = contactList.get(position);
        String name = contact.getUsername();
        String photo = contact.getPhoto();
        String contato = contact.getContact();
        String neo = contact.getNeo();

        if (Objects.equals(neo, "true")){
            holder.newmessage.setVisibility(View.VISIBLE);
        }

        holder.nameuser.setText(name);

        try {
            Picasso.get().load(photo).placeholder(R.drawable.loading).into(holder.photo);
        }
        catch (Exception e) {
            if (holder.photo == null)
                return;
            else
                holder.photo.setImageResource(R.drawable.error);
        }

        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();

        holder.clickview.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ChatActivity.class);
            intent.putExtra("contatoId", contato);
            intent.putExtra("userId", Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
            intent.putExtra("contatoName", name);
            intent.putExtra("contatoPhoto", photo);
            v.getContext().startActivity(intent);
            ((Activity)context).finish();
        });
    }

    @Override
    public int getItemCount() {
        if (contactList == null)
            return 0;
        else
            return contactList.size();
    }

    class HolderContacts extends RecyclerView.ViewHolder{
        private final TextView nameuser;
        private final TextView newmessage;
        private final ImageView photo;
        private final View clickview;
        public HolderContacts(@NonNull View itemView) {
            super(itemView);

            clickview = itemView.findViewById(R.id.clickview);
            photo = itemView.findViewById(R.id.imageView96);
            nameuser = itemView.findViewById(R.id.textView58);
            newmessage = itemView.findViewById(R.id.newmessage);
        }
    }
}
