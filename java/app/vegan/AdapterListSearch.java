package app.vegan;

import static app.vegan.R.drawable.error;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
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

public class AdapterListSearch extends RecyclerView.Adapter<AdapterListSearch.ViewHolder> {

    private final Context context;
    public ArrayList<ModelProduct> productList, filterList;
    private FilterList filter;

    public AdapterListSearch(Context context, ArrayList<ModelProduct> productList) {
        this.context = context;
        this.productList = productList;
        this.filterList = productList;
    }

    @NonNull
    @Override
    public AdapterListSearch.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_vertical, parent, false);
        return new AdapterListSearch.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListSearch.ViewHolder holder, int position) {
        final ModelProduct modelProduct = productList.get(position);
        String productId = modelProduct.getProductId();
        String uid = modelProduct.getUid();
        String discountAvailable = modelProduct.getDiscountAvailable();
        String discountNote = modelProduct.getDiscountNote();
        String discountPrice = modelProduct.getDiscountPrice();
        String productCategory = modelProduct.getProductCategory();
        String productDescription = modelProduct.getProductDescription();
        String productIcon = modelProduct.getProductIcon();
        String productQuantity = modelProduct.getProductQuantity();
        String productTitle = modelProduct.getProductTitle();
        String brandName = modelProduct.getProductBrand();
        String originalPrice = modelProduct.getOriginalPrice();
        String soyseal = modelProduct.getSoyseal();
        String recyseal = modelProduct.getRecyclingseal();
        String nogmoseal = modelProduct.getNogmoseal();
        String organicseal = modelProduct.getOrganicseal();
        String cannaseal = modelProduct.getCannaseal();
        String cottonseal = modelProduct.getCottonseal();
        String parabenseal = modelProduct.getParabenseal();
        String glutenseal = modelProduct.getGlutenseal();
        String biodeseal = modelProduct.getBiodeseal();
        String rawseal = modelProduct.getRaw();
        String b12seal = modelProduct.getB12();
        String ecoseal = modelProduct.getEcoseal();
        String productType = modelProduct.getProductType();
        String international = modelProduct.getInternationalMail();
        String national = modelProduct.getNationalMail();
        String productCountry = modelProduct.getProductCountry();
        String indisponivel = modelProduct.getIndisponivel();
        String nationalMail = modelProduct.getNationalMail();

        if(Objects.equals(productCountry, "Brasil")){
            holder.originalPriceTv.setText("R$");
        }
        if(Objects.equals(productCountry, "Estados Unidos da América")){
            holder.originalPriceTv.setText("US$");
        }
        if(Objects.equals(productCountry, "Inglaterra")){
            holder.originalPriceTv.setText("£");
        }
        if(Objects.equals(productCountry, "Alemanha")){
            holder.originalPriceTv.setText("€");
        }
        if(Objects.equals(productCountry, "França")){
            holder.originalPriceTv.setText("€");
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.canada))){
            holder.originalPriceTv.setText("C$");
        }
        if(Objects.equals(productCountry, "Portugal")){
            holder.originalPriceTv.setText("€");
        }
        if(Objects.equals(productCountry, "Espanha")){
            holder.originalPriceTv.setText("€");
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.mexico))){
            holder.originalPriceTv.setText("MEX$");
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.argentina))){
            holder.originalPriceTv.setText("ARS$");
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.escocia))){
            holder.originalPriceTv.setText("£");
        }

        holder.descript.setText(productDescription);
        holder.titleTv.setText(productTitle);
        holder.brand.setText(brandName);

        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getUid();

        if(Objects.equals(national, "true")) {
            try {
                Picasso.get().load(R.drawable.boxtruck).placeholder(R.drawable.loading).into(holder.mail);
            } catch (Exception e) {
                if (holder.productIconIv == null)
                    return;
                else
                    holder.productIconIv.setImageResource(R.drawable.error);
            }
            holder.mail.setVisibility(View.VISIBLE);
        }
        if (Objects.equals(international, "true")){
            holder.mail.setVisibility(View.VISIBLE);
        }

        if(discountAvailable.equals("true")){
            holder.fire.setVisibility(View.VISIBLE);
            holder.originalPriceTv.setVisibility(View.VISIBLE);
            holder.discountedNoteTv.setVisibility(View.VISIBLE);
            holder.discountedNoteTv.setText(discountNote + "% OFF");
            holder.originalPriceTv.append(discountPrice.replace(".",","));

        }
        else{
            holder.fire.setVisibility(View.GONE);
            holder.discountedNoteTv.setVisibility(View.GONE);
            holder.originalPriceTv.append(originalPrice.replace(".",","));
        }

        try {
            Picasso.get().load(productIcon).placeholder(R.drawable.loading).into(holder.productIconIv);
        }
        catch (Exception e) {
            if (holder.productIconIv == null)
                return;
            else
                holder.productIconIv.setImageResource(R.drawable.error);
        }


        if(userID!= null) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("LovedBy").child(userID).child(productId);
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
                if ( userID!=null ) {
                    if (Objects.equals(holder.isLoved, "false")){
                        holder.isLoved = "true";

                        if(uid != null) {
                            String userID = firebaseAuth.getCurrentUser().getUid();
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
                        String userID = firebaseAuth.getCurrentUser().getUid();
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

        holder.imageclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Objects.equals(productType, "Diet")){
                    Intent intent = new Intent(v.getContext(), DietDetailsActivity.class);
                    intent.putExtra("nationalMail", nationalMail);
                    intent.putExtra("title", productTitle);
                    intent.putExtra("type", productType);
                    intent.putExtra("indisponivel", indisponivel);
                    intent.putExtra("photo", productIcon);
                    intent.putExtra("brandName", brandName);
                    intent.putExtra("discountPrice", discountPrice);
                    intent.putExtra("discountNote", discountNote);
                    intent.putExtra("priceOriginal", originalPrice);
                    intent.putExtra("productCountry", productCountry);
                    intent.putExtra("quantityProduct", productQuantity);
                    intent.putExtra("descriptionProduct", productDescription);
                    intent.putExtra("soyseal", soyseal);
                    intent.putExtra("parabenseal", parabenseal);
                    intent.putExtra("cottonseal", cottonseal);
                    intent.putExtra("cannaseal", cannaseal);
                    intent.putExtra("glutenseal", glutenseal);
                    intent.putExtra("organicseal", organicseal);
                    intent.putExtra("recyseal", recyseal);
                    intent.putExtra("biodeseal", biodeseal);
                    intent.putExtra("nogmoseal", nogmoseal);
                    intent.putExtra("rawseal", rawseal);
                    intent.putExtra("b12seal", b12seal);
                    intent.putExtra("ecoseal", ecoseal);
                    intent.putExtra("categoryProduct", productCategory);
                    intent.putExtra("productId", productId);
                    intent.putExtra("shopUid", uid);
                    v.getContext().startActivity(intent);
                }
                if(Objects.equals(productType, "Others")){
                    Intent intent = new Intent(v.getContext(), MoreDetailsActivity.class);
                    intent.putExtra("nationalMail", nationalMail);
                    intent.putExtra("title", productTitle);
                    intent.putExtra("indisponivel", indisponivel);
                    intent.putExtra("photo", productIcon);
                    intent.putExtra("brandName", brandName);
                    intent.putExtra("discountPrice", discountPrice);
                    intent.putExtra("discountNote", discountNote);
                    intent.putExtra("priceOriginal", originalPrice);
                    intent.putExtra("quantityProduct", productQuantity);
                    intent.putExtra("descriptionProduct", productDescription);
                    intent.putExtra("soyseal", soyseal);
                    intent.putExtra("parabenseal", parabenseal);
                    intent.putExtra("cottonseal", cottonseal);
                    intent.putExtra("cannaseal", cannaseal);
                    intent.putExtra("glutenseal", glutenseal);
                    intent.putExtra("organicseal", organicseal);
                    intent.putExtra("recyseal", recyseal);
                    intent.putExtra("biodeseal", biodeseal);
                    intent.putExtra("nogmoseal", nogmoseal);
                    intent.putExtra("productCountry", productCountry);
                    intent.putExtra("rawseal", rawseal);
                    intent.putExtra("b12seal", b12seal);
                    intent.putExtra("ecoseal", ecoseal);
                    intent.putExtra("categoryProduct", productCategory);
                    intent.putExtra("productId", productId);
                    intent.putExtra("shopUid", uid);
                    v.getContext().startActivity(intent);
                }
                if(Objects.equals(productType, "Closet")){
                    Intent intent = new Intent(v.getContext(), FashionActivity.class);
                    intent.putExtra("nationalMail", nationalMail);
                    intent.putExtra("title", productTitle);
                    intent.putExtra("photo", productIcon);
                    intent.putExtra("indisponivel", indisponivel);
                    intent.putExtra("brandName", brandName);
                    intent.putExtra("discountPrice", discountPrice);
                    intent.putExtra("discountNote", discountNote);
                    intent.putExtra("priceOriginal", originalPrice);
                    intent.putExtra("quantityProduct", productQuantity);
                    intent.putExtra("productCountry", productCountry);
                    intent.putExtra("descriptionProduct", productDescription);
                    intent.putExtra("soyseal", soyseal);
                    intent.putExtra("parabenseal", parabenseal);
                    intent.putExtra("cottonseal", cottonseal);
                    intent.putExtra("cannaseal", cannaseal);
                    intent.putExtra("glutenseal", glutenseal);
                    intent.putExtra("organicseal", organicseal);
                    intent.putExtra("recyseal", recyseal);
                    intent.putExtra("biodeseal", biodeseal);
                    intent.putExtra("nogmoseal", nogmoseal);
                    intent.putExtra("rawseal", rawseal);
                    intent.putExtra("b12seal", b12seal);
                    intent.putExtra("ecoseal", ecoseal);
                    intent.putExtra("categoryProduct", productCategory);
                    intent.putExtra("shopUid", uid);
                    intent.putExtra("productId", productId);
                    v.getContext().startActivity(intent);
                }
                if(Objects.equals(productType, "Pharmacy")) {
                    Intent intent = new Intent(v.getContext(), MedicineDetailsActivity.class);
                    intent.putExtra("nationalMail", nationalMail);
                    intent.putExtra("title", productTitle);
                    intent.putExtra("indisponivel", indisponivel);
                    intent.putExtra("photo", productIcon);
                    intent.putExtra("brandName", brandName);
                    intent.putExtra("discountPrice", discountPrice);
                    intent.putExtra("discountNote", discountNote);
                    intent.putExtra("priceOriginal", originalPrice);
                    intent.putExtra("quantityProduct", productQuantity);
                    intent.putExtra("descriptionProduct", productDescription);
                    intent.putExtra("productCountry", productCountry);
                    intent.putExtra("soyseal", soyseal);
                    intent.putExtra("parabenseal", parabenseal);
                    intent.putExtra("cottonseal", cottonseal);
                    intent.putExtra("cannaseal", cannaseal);
                    intent.putExtra("glutenseal", glutenseal);
                    intent.putExtra("organicseal", organicseal);
                    intent.putExtra("recyseal", recyseal);
                    intent.putExtra("biodeseal", biodeseal);
                    intent.putExtra("nogmoseal", nogmoseal);
                    intent.putExtra("rawseal", rawseal);
                    intent.putExtra("b12seal", b12seal);
                    intent.putExtra("ecoseal", ecoseal);
                    intent.putExtra("categoryProduct", productCategory);
                    intent.putExtra("shopUid", uid);
                    intent.putExtra("productId", productId);
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (productList == null)
            return 0;
        else
            return productList.size();    }

    public Filter getFilter() {
        if(filter==null){
            filter = new FilterList(this, filterList);
        }
        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView productIconIv;
        private final ImageView addToCartIv;
        private final ImageView mail;
        private final ImageView imageclick;
        private final ImageView fire;
        private final TextView discountedNoteTv;
        private final TextView titleTv;
        private final TextView originalPriceTv;
        private final TextView brand;
        private final TextView descript;
        private final ImageButton imageButtonLove;
        private String isLoved;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fire = itemView.findViewById(R.id.fire);
            descript = itemView.findViewById(R.id.textView69);
            imageclick = itemView.findViewById(R.id.imageclick);
            brand = itemView.findViewById(R.id.textView48);
            mail = itemView.findViewById(R.id.imageMailList);
            productIconIv = itemView.findViewById(R.id.iconSearchList);
            discountedNoteTv = itemView.findViewById(R.id.discountedNoteTv);
            titleTv = itemView.findViewById(R.id.titleIconSearchList);
            addToCartIv = itemView.findViewById(R.id.clickListener0);
            originalPriceTv = itemView.findViewById(R.id.priceSearchList);
            imageButtonLove = itemView.findViewById(R.id.imageButtonLoveRow);
        }
    }
}
