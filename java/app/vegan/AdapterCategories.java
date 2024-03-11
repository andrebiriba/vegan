package app.vegan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Objects;

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.ViewHolder> {

    private final Context context;
    public ArrayList<ModelProduct> productList, filterList;
    private FilterPromotions filter;

    public AdapterCategories(Context context, ArrayList<ModelProduct> productList) {
        this.context = context;
        this.productList = productList;
        this.filterList = productList;
    }

    @Override
    public AdapterCategories.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_categorydetails, parent, false);
        return new AdapterCategories.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCategories.ViewHolder holder, int position) {
        final ModelProduct modelProduct = productList.get(position);
         String productId = modelProduct.getProductId();
          String uid = modelProduct.getUid();
        String discountAvailable = modelProduct.getDiscountAvailable();
        String discountNote = modelProduct.getDiscountNote();
        String brand = modelProduct.getProductBrand();
        String description = modelProduct.getProductDescription();
        String discountPrice = modelProduct.getDiscountPrice();
        String productCategory = modelProduct.getProductCategory();
         String productDescription = modelProduct.getProductDescription();
        String productIcon = modelProduct.getProductIcon();
         String productQuantity = modelProduct.getProductQuantity();
        String productTitle = modelProduct.getProductTitle();
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
        String brandName = modelProduct.getProductBrand();
        String productType = modelProduct.getProductType();
        String productCountry = modelProduct.getProductCountry();
        String indisponivel = modelProduct.getIndisponivel();
        String nationalMail = modelProduct.getNationalMail();

        if(Objects.equals(productCountry, context.getResources().getString(R.string.brasil))){
            holder.discountedPriceTv.setText("R$");
            holder.originalPriceTv.setText("R$");
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.eua))){
            holder.discountedPriceTv.setText("US$");
            holder.originalPriceTv.setText("US$");
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.inglaterra))){
            holder.discountedPriceTv.setText("£");
            holder.originalPriceTv.setText("£");
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.alemanha))){
            holder.discountedPriceTv.setText("€");
            holder.originalPriceTv.setText("€");
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.franca))){
            holder.discountedPriceTv.setText("€");
            holder.originalPriceTv.setText("€");
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.canada))){
            holder.discountedPriceTv.setText("C$");
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.portugal))){
            holder.discountedPriceTv.setText("€");
            holder.originalPriceTv.setText("€");
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.espanha))){
            holder.discountedPriceTv.setText("€");
            holder.originalPriceTv.setText("€");
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.mexico))){
            holder.discountedPriceTv.setText("MEX$");
            holder.originalPriceTv.setText("MEX$");

        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.argentina))){
            holder.discountedPriceTv.setText("ARS$");
            holder.originalPriceTv.setText("ARS$");

        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.escocia))){
            holder.discountedPriceTv.setText("£");
            holder.originalPriceTv.setText("£");
        }

        holder.titleTv.setText(productTitle);
        holder.originalPriceTv.append(originalPrice.replace(".",","));
        holder.discountedNoteTv.setText(discountNote + "% OFF");
        holder.descriptionTv.setText(description);
        holder.brandTv.setText(brand);


        if(discountAvailable.equals("true")){
            holder.discountedPriceTv.setVisibility(View.VISIBLE);
            holder.discountedNoteTv.setVisibility(View.VISIBLE);
            holder.discountedNoteTv.setText(discountNote + "% OFF");
            holder.discountedPriceTv.append(discountPrice.replace(".",","));
        }
        else{
            holder.discountedNoteTv.setVisibility(View.GONE);
            holder.originalPriceTv.setVisibility(View.GONE);
            holder.viewcut.setVisibility(View.GONE);
            holder.fire.setVisibility(View.GONE);
            holder.discountedPriceTv.append(originalPrice.replace(".",","));
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

        holder.addToCartIv.setOnClickListener(v -> {
            if(Objects.equals(productType, "Diet")){
                Intent intent = new Intent(v.getContext(), DietDetailsActivity.class);
                intent.putExtra("indisponivel", indisponivel);
                intent.putExtra("nationalMail", nationalMail);
                intent.putExtra("title", productTitle);
                intent.putExtra("type", productType);
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
                intent.putExtra("productId", productId);
                intent.putExtra("shopUid", uid);
                v.getContext().startActivity(intent);
            }
            if(Objects.equals(productType, "Others")){
                Intent intent = new Intent(v.getContext(), MoreDetailsActivity.class);
                intent.putExtra("indisponivel", indisponivel);
                intent.putExtra("nationalMail", nationalMail);
                intent.putExtra("title", productTitle);
                intent.putExtra("photo", productIcon);
                intent.putExtra("brandName", brandName);
                intent.putExtra("discountPrice", discountPrice);
                intent.putExtra("discountNote", discountNote);
                intent.putExtra("priceOriginal", originalPrice);
                intent.putExtra("quantityProduct", productQuantity);
                intent.putExtra("productCountry", productCountry);
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
                intent.putExtra("productId", productId);
                intent.putExtra("shopUid", uid);
                v.getContext().startActivity(intent);
            }
            if(Objects.equals(productType, "Closet")){
                Intent intent = new Intent(v.getContext(), FashionActivity.class);
                intent.putExtra("title", productTitle);
                intent.putExtra("indisponivel", indisponivel);
                intent.putExtra("nationalMail", nationalMail);
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
            if(Objects.equals(productType, "Pharmacy")) {
                Intent intent = new Intent(v.getContext(), MedicineDetailsActivity.class);
                intent.putExtra("indisponivel", indisponivel);
                intent.putExtra("nationalMail", nationalMail);
                intent.putExtra("title", productTitle);
                intent.putExtra("photo", productIcon);
                intent.putExtra("brandName", brandName);
                intent.putExtra("discountPrice", discountPrice);
                intent.putExtra("discountNote", discountNote);
                intent.putExtra("priceOriginal", originalPrice);
                intent.putExtra("quantityProduct", productQuantity);
                intent.putExtra("productCountry", productCountry);
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
          });
    }

    @Override
    public int getItemCount() {
        if (productList == null)
            //constraintlayout
            return 0;
        else
            return productList.size();    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView productIconIv;
        private ImageView fire;
        private final ImageView addToCartIv;
        private final TextView discountedNoteTv;
        private final TextView titleTv;
        private final TextView descriptionTv;
        private final TextView quantityTv;
        private final TextView discountedPriceTv;
        private TextView originalPriceTv;
        private final TextView brandTv;
        private final View viewcut;
        private ConstraintLayout constraintlayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintlayout = itemView.findViewById(R.id.constraintlayout);
            fire = itemView.findViewById(R.id.fire);
            productIconIv = itemView.findViewById(R.id.circleImageView3);
            discountedNoteTv = itemView.findViewById(R.id.discountedNoteTv);
            titleTv = itemView.findViewById(R.id.textView56);
            descriptionTv = itemView.findViewById(R.id.textView62);
            addToCartIv = itemView.findViewById(R.id.clickListenercategory);
            quantityTv = itemView.findViewById(R.id.quantityTv);
            discountedPriceTv = itemView.findViewById(R.id.textView64);
            originalPriceTv = itemView.findViewById(R.id.priceSearchList);
            brandTv = itemView.findViewById(R.id.textView61);
            originalPriceTv = itemView.findViewById(R.id.originalPriceTv);
            viewcut = itemView.findViewById(R.id.viewcut);
        }
    }
}
