package app.vegan;

import static app.vegan.R.drawable.error;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class AdapterProductUser extends RecyclerView.Adapter<AdapterProductUser.HolderProductUser> implements Filterable {

    private final Context context;
    public ArrayList<ModelProduct> productList, filterList;
    private FilterProductUser filter;

    public AdapterProductUser(Context context, ArrayList<ModelProduct> productList) {
        this.context = context;
        this.productList = productList;
        this.filterList = productList;
    }

    @NonNull
    @Override
    public HolderProductUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_vertical, parent, false);
        return new HolderProductUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProductUser.HolderProductUser holder, int position) {
        final ModelProduct modelProduct = productList.get(position);
        String discountAvailable = modelProduct.getDiscountAvailable();
        String productDescription = modelProduct.getProductDescription();
        String productIcon = modelProduct.getProductIcon();
        String productTitle = modelProduct.getProductTitle();
        String originalPrice = modelProduct.getOriginalPrice();
        String uid = modelProduct.getUid();
        String productId = modelProduct.getProductId();
        String brandName = modelProduct.getProductBrand();

        holder.imageMailList.setVisibility(View.GONE);
        holder.titleTv.setText(productTitle);
        holder.descript.setText(productDescription);
        holder.originalPriceTv.setText(originalPrice.replace(".",","));

        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();
        String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();


        if(discountAvailable.equals("true")){
            holder.discountedPriceTv.setVisibility(View.VISIBLE);
            holder.discountedNoteTv.setVisibility(View.VISIBLE);
        }
        else{
            holder.discountedNoteTv.setVisibility(View.GONE);
        }

        try {
            Picasso.get().load(productIcon).placeholder(R.drawable.loading).into(holder.productIconIv);
        }
        catch (Exception e) {
            if (holder.productIconIv == null)
                return;
            else
            holder.productIconIv.setImageResource(R.drawable.vegangreenlogo);
        }

        if(uid != null) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("LovedBy").child(uid).child(productId);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String loveDB = ""+snapshot.child("love").getValue();
                    if (loveDB.equals("true")) {
                        holder.isLoved = "true";
                        try {
                            Picasso.get().load(R.drawable.heartgreen).placeholder(R.drawable.loading).into(holder.imageButtonLove);
                        }
                        catch (Exception e) {
                            holder.imageButtonLove.setImageDrawable(ContextCompat.getDrawable(context, error ));
                        }
                    } else {
                        holder.isLoved = "false";
                        try {
                            Picasso.get().load(R.drawable.love).placeholder(R.drawable.loading).into(holder.imageButtonLove);
                        }
                        catch (Exception e) {
                            holder.imageButtonLove.setImageDrawable(ContextCompat.getDrawable(context, error ));
                        }                }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(context, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            holder.isLoved = "false";
            try {
                Picasso.get().load(R.drawable.love).placeholder(R.drawable.loading).into(holder.imageButtonLove);
            }
            catch (Exception e) {
                holder.imageButtonLove.setImageDrawable(ContextCompat.getDrawable(context, error ));
            }
        }


        holder.imageButtonLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if ( userID!= null ) {
                    if (Objects.equals(holder.isLoved, "false")){
                        holder.isLoved = "true";

                        if(uid != null) {
                            HashMap<String, String> hashMap1 = new HashMap<>();
                            hashMap1.put("love", holder.isLoved);
                            hashMap1.put("name", productTitle);
                            hashMap1.put("brand", brandName);
                            hashMap1.put("foto",productIcon );
                            hashMap1.put("id", productId);
                            hashMap1.put("shopuid", uid);
                            hashMap1.put("useruid", userID);
                            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("LovedBy").child(userID).child(productId);
                            ref.setValue(hashMap1)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            try {
                                                Picasso.get().load(R.drawable.heartgreen).placeholder(R.drawable.loading).into(holder.imageButtonLove);
                                            }
                                            catch (Exception e) {
                                                holder.imageButtonLove.setImageDrawable(ContextCompat.getDrawable(context, error ));
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    } else {
                        holder.isLoved = "false";

                        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("LovedBy").child(userID).child(productId);
                        ref.removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        try {
                                            Picasso.get().load(R.drawable.love).placeholder(R.drawable.loading).into(holder.imageButtonLove);
                                        }
                                        catch (Exception e) {
                                            holder.imageButtonLove.setImageDrawable(ContextCompat.getDrawable(context, error ));
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                } else Toast.makeText(v.getContext(), R.string.entreoucadastre, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (productList == null)
            return 0;
        else
        return productList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null){
            filter = new FilterProductUser(this, filterList);
        }
        return filter;
    }

    class HolderProductUser extends RecyclerView.ViewHolder{
        private String isLoved;
        private final ImageButton imageButtonLove;
        private final ImageView imageMailList;
        private final ImageView productIconIv;
        private final TextView discountedNoteTv;
        private final TextView titleTv;
        private final TextView discountedPriceTv;
        private final TextView originalPriceTv;
        private final TextView descript;

        public HolderProductUser(@NonNull View itemView) {
            super(itemView);
            descript = itemView.findViewById(R.id.textView69);
            imageMailList = itemView.findViewById(R.id.imageMailList);
            productIconIv = itemView.findViewById(R.id.iconSearchList);
            discountedNoteTv = itemView.findViewById(R.id.discountedNoteTv);
            titleTv = itemView.findViewById(R.id.titleIconSearchList);
            discountedPriceTv = itemView.findViewById(R.id.priceSearchList);
            originalPriceTv = itemView.findViewById(R.id.priceSearchList);
            imageButtonLove = itemView.findViewById(R.id.imageButtonLoveRow);
        }
    }
}
