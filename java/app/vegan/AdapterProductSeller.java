package app.vegan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.text.TextUtils;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static app.vegan.R.drawable.error;

public class AdapterProductSeller extends RecyclerView.Adapter<AdapterProductSeller.HolderProductSeller> implements Filterable {

    private final Context context;
    public ArrayList<ModelProduct> productList, filterList;
    private FilterProduct filter;

    public AdapterProductSeller(Context context, ArrayList<ModelProduct> productList) {
        this.context = context;
        this.productList = productList;
        this.filterList = productList;
    }

    @NonNull
    @Override
    public HolderProductSeller onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_products_seller, parent, false);
        return new HolderProductSeller(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProductSeller holder, int position) {
        ModelProduct modelProduct = productList.get(position);
        String discountAvailable = modelProduct.getDiscountAvailable();
        String discountNote = modelProduct.getDiscountNote();
        String discountPrice = modelProduct.getDiscountPrice();
        String icon = modelProduct.getProductIcon();
        String quantity = modelProduct.getProductQuantity();
        String title = modelProduct.getProductTitle();
        String originalPrice = modelProduct.getOriginalPrice();
        String productCountry = modelProduct.getProductCountry();


        if(Objects.equals(productCountry, "Brasil")){
            holder.discountedPriceTv.setText("R$");
        }
        if(Objects.equals(productCountry, "Estados Unidos da América")){
            holder.discountedPriceTv.setText("US$");
        }
        if(Objects.equals(productCountry, "Inglaterra")){
            holder.discountedPriceTv.setText("£");
        }
        if(Objects.equals(productCountry, "Alemanha")){
            holder.discountedPriceTv.setText("€");
        }
        if(Objects.equals(productCountry, "França")){
            holder.discountedPriceTv.setText("€");
        }
        if(Objects.equals(productCountry, "Portugal")){
            holder.discountedPriceTv.setText("€");
        }
        if(Objects.equals(productCountry, "Espanha")){
            holder.discountedPriceTv.setText("€");
        }

        holder.titleTv.setText(title);
        holder.quantityTv.setText(quantity);
        holder.discountedNoteTv.setText(discountNote + "% OFF");

        if(discountAvailable.equals("true")){
            holder.discountedPriceTv.setVisibility(View.VISIBLE);
            holder.discountedNoteTv.setVisibility(View.VISIBLE);
            holder.discountedPriceTv.append(discountPrice.replace(".",","));
            holder.originalPriceTv.append(originalPrice.replace(".",","));
            holder.originalPriceTv.setPaintFlags(holder.originalPriceTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else{
            holder.originalPriceTv.setVisibility(View.GONE);
            holder.discountedPriceTv.append(originalPrice.replace(".",","));
            holder.discountedPriceTv.setVisibility(View.GONE);
            holder.discountedNoteTv.setVisibility(View.GONE);
        }
        try {
            Picasso.get().load(icon).placeholder(R.drawable.loading).into(holder.productIconIv);
        }
        catch (Exception e) {
            holder.productIconIv.setImageResource(error);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsBottomSheet(modelProduct);
            }
        });

    }

    private void detailsBottomSheet(ModelProduct modelProduct) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.bs_product_details_seller, null);
        bottomSheetDialog.setContentView(view);

        ImageButton backBtn = view.findViewById(R.id.backBtn);
        ImageButton deleteBtn = view.findViewById(R.id.deleteBtn);
        ImageButton editBtn = view.findViewById(R.id.editBtn);
        ImageView productIconIv = view.findViewById(R.id.productIconIv);
        TextView discountNoteTv = view.findViewById(R.id.discountNoteTv);
        TextView titleTv = view.findViewById(R.id.titleTv);
        TextView descriptionTv = view.findViewById(R.id.descriptionTv);
        final TextView categoryTv = view.findViewById(R.id.categoryTv);
        TextView quantityTv = view.findViewById(R.id.quantityTv);
        TextView discountedPriceTv = view.findViewById(R.id.discountedPriceTv);
        TextView originalPriceTv = view.findViewById(R.id.originalPriceTv);

        final String id = modelProduct.getProductId();
        String brandName = modelProduct.getProductBrand();
        String productType = modelProduct.getProductType();
        String nationalMail = modelProduct.getNationalMail();
        String internationalMail = modelProduct.getInternationalMail();
        String uid = modelProduct.getUid();
        String discountAvailable = modelProduct.getDiscountAvailable();
        String discountNote = modelProduct.getDiscountNote();
        String discountPrice = modelProduct.getDiscountPrice();
        String productCategory = modelProduct.getProductCategory();
        String productDescription = modelProduct.getProductDescription();
        String icon = modelProduct.getProductIcon();
        String quantity = modelProduct.getProductQuantity();
        final String title = modelProduct.getProductTitle();
        String originalPrice = modelProduct.getOriginalPrice();
        String productCountry = modelProduct.getProductCountry();

        if(Objects.equals(productCountry, "Brasil")){
            discountedPriceTv.setText("R$");
            originalPriceTv.setText("R$");
        }
        if(Objects.equals(productCountry, "Estados Unidos da América")){
            discountedPriceTv.setText("US$");
            originalPriceTv.setText("US$");
        }
        if(Objects.equals(productCountry, "Inglaterra")){
            discountedPriceTv.setText("£");
            originalPriceTv.setText("£");
        }
        if(Objects.equals(productCountry, "Alemanha")){
            discountedPriceTv.setText("€");
            originalPriceTv.setText("€");
        }
        if(Objects.equals(productCountry, "França")){
            discountedPriceTv.setText("€");
            originalPriceTv.setText("€");
        }
        if(Objects.equals(productCountry, "Portugal")){
            discountedPriceTv.setText("€");
            originalPriceTv.setText("€");
        }
        if(Objects.equals(productCountry, "Espanha")){
            discountedPriceTv.setText("€");
            originalPriceTv.setText("€");
        }

        titleTv.setMaxLines(1);
        titleTv.setEllipsize(TextUtils.TruncateAt.END);
        titleTv.setText(title);
        descriptionTv.setMaxLines(1);
        descriptionTv.setEllipsize(TextUtils.TruncateAt.END);
        descriptionTv.setText(productDescription);
        categoryTv.setText(productCategory);
        quantityTv.setText(quantity);
        discountNoteTv.setText(discountNote + "% OFF");
        discountedPriceTv.append(discountPrice);
        originalPriceTv.append(originalPrice);

        if(discountAvailable.equals("true")){
            discountedPriceTv.setVisibility(View.VISIBLE);
            discountNoteTv.setVisibility(View.VISIBLE);
            originalPriceTv.setVisibility(View.VISIBLE);
            originalPriceTv.setPaintFlags(originalPriceTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else{
            discountNoteTv.setVisibility(View.GONE);
            originalPriceTv.setVisibility(View.GONE);
        }
        try {
            Picasso.get().load(icon).placeholder(R.drawable.loading).into(productIconIv);
        }
        catch (Exception e) {
            productIconIv.setImageDrawable(ContextCompat.getDrawable(context, error ));
        }

        bottomSheetDialog.show();

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                Intent intent = new Intent(context, EditProduct.class);
                intent.putExtra("productId", id);
                intent.putExtra("uid", uid);
                intent.putExtra("productCategory", productCategory);
                intent.putExtra("productType", productType);
                intent.putExtra("productBrand", brandName);
                intent.putExtra("internationalMail", internationalMail);
                intent.putExtra("nationalMail", nationalMail);
                context.startActivity(intent);
            }

        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.deletar)
                        .setMessage(v.getResources().getString(R.string.desejadele)+" "+title+"?")
                        .setPositiveButton(R.string.deletar, new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     FirebaseFirestore fStore = FirebaseFirestore.getInstance();
                     DocumentReference docRef = fStore.collection(productCategory).document(id);

                     String icon = modelProduct.getProductIcon();
                     String icon2 = modelProduct.getProductIcon2();
                     String icon3 = modelProduct.getProductIcon3();

                     FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance().getReference().getStorage();
                     StorageReference photoRef = mFirebaseStorage.getReferenceFromUrl(icon);

                     photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                         @Override
                         public void onSuccess(Void aVoid) {

                             docRef.delete();

                             String numberOfProducts = "0";
                             Double noptempo = 1.0;
                             HashMap<String, Object> hashMap37 = new HashMap<>();
                             Double temp= Double.parseDouble(numberOfProducts)+noptempo;
                             hashMap37.put("numberOfProducts",""+temp);
                             DocumentReference nop = fStore.collection("Adm's").document(uid);
                             nop.update(hashMap37).addOnSuccessListener(new OnSuccessListener<Void>() {
                                 @Override
                                 public void onSuccess(Void aVoid) {
                                 }
                             }).addOnFailureListener(new OnFailureListener() {
                                 @Override
                                 public void onFailure(@NonNull Exception e) {
                                     Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                 }
                             });

                             if(icon2!=null&& !icon2.equals("")){

                                 StorageReference photoRef2 = mFirebaseStorage.getReferenceFromUrl(icon2);
                                 photoRef2.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                     @Override
                                     public void onSuccess(Void aVoid) {
                                     }
                                 }).addOnFailureListener(new OnFailureListener() {
                                     @Override
                                     public void onFailure(@NonNull Exception exception) {
                                     }
                                 });

                             }

                             if(icon3!=null&& !icon3.equals("")){

                                 StorageReference photoRef3 = mFirebaseStorage.getReferenceFromUrl(icon3);
                                 photoRef3.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                     @Override
                                     public void onSuccess(Void aVoid) {
                                     }
                                 }).addOnFailureListener(new OnFailureListener() {
                                     @Override
                                     public void onFailure(@NonNull Exception exception) {
                                     }
                                 });
                             }
                             final Handler handler2 = new Handler();
                             handler2.postDelayed(new Runnable() {
                                 @Override
                                 public void run() {
                                     Toast.makeText(context, R.string.produtodeletado, Toast.LENGTH_SHORT).show();
                                 }
                             }, 3000 );

                         }
                     }).addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception exception) {
                         }
                     });
                 }
                })
                        .setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }

        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
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
                filter = new FilterProduct(this, filterList);
            }
            return filter;
        }

        class HolderProductSeller extends RecyclerView.ViewHolder{

            private final ShapeableImageView productIconIv;
            private final TextView discountedNoteTv;
            private final TextView titleTv;
            private final TextView quantityTv;
            private final TextView discountedPriceTv;
            private final TextView originalPriceTv;

            public HolderProductSeller(@NonNull View itemView) {
                super(itemView);
                productIconIv = itemView.findViewById(R.id.productIconIv);
                discountedNoteTv = itemView.findViewById(R.id.discountedNoteTv);
                titleTv = itemView.findViewById(R.id.titleTv);
                quantityTv = itemView.findViewById(R.id.quantityTv);
                discountedPriceTv = itemView.findViewById(R.id.discountedPriceTv);
                originalPriceTv = itemView.findViewById(R.id.originalPriceTv);
            }
    }
}
