package app.vegan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class AdapterLove extends RecyclerView.Adapter<AdapterLove.HolderLove> {

    private final Context context;
    private final ArrayList<ModelLove> loveList;

    public AdapterLove(Context context, ArrayList<ModelLove> loveList) {
        this.context = context;
        this.loveList = loveList;
    }

    @NonNull
    @Override
    public HolderLove onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_like, parent, false);
        return new HolderLove(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderLove holder, int position) {
        ModelLove modelLove = loveList.get(position);
        String name = modelLove.getName();
        String foto = modelLove.getFoto();
        String brand = modelLove.getBrand();
        String id = modelLove.getId();
        String useruid = modelLove.getUseruid();
        String shopUid = modelLove.getShopuid();

        holder.loveName.setText(name);

        if(brand==null| Objects.equals(brand, "") | Objects.equals(brand, "false")){
            holder.loveBrand.setVisibility(View.INVISIBLE);
        } else {
            holder.loveBrand.setText(brand);
        }
        try {
            Picasso.get().load(foto).placeholder(R.drawable.loading).into(holder.loveImage);
        } catch (Exception e) {
            if (holder.loveImage == null)
                return;
            else
                holder.loveImage.setImageResource(R.drawable.error);
        }

        Calendar calendar = Calendar.getInstance();

        holder.loveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("LovedBy").child(useruid).child(shopUid);
                        ref.removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        try {
                                            Picasso.get().load(R.drawable.love).placeholder(R.drawable.loading).into(holder.loveIcon);
                                        } catch (Exception e) {
                                            if (holder.loveIcon == null)
                                                return;
                                            else
                                                holder.loveIcon.setImageResource(R.drawable.error);
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                    }
                                });
            }
        });
    }

    @Override
    public int getItemCount() {
        if (loveList == null)
            return 0;
        else
            return loveList.size();
    }

    class HolderLove extends RecyclerView.ViewHolder {
        private final TextView loveName;
        private final TextView loveBrand;
        private final ImageView loveImage;
        private final ImageView loveIcon;

        public HolderLove(@NonNull View itemView) {
            super(itemView);
            loveImage = itemView.findViewById(R.id.loveImage);
            loveIcon = itemView.findViewById(R.id.loveIcon);
            loveBrand = itemView.findViewById(R.id.loveBrand);
            loveName = itemView.findViewById(R.id.loveName);
        }
    }
}
