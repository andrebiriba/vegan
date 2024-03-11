package app.vegan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Objects;

public class AdapterGridSearch extends RecyclerView.Adapter<AdapterGridSearch.ViewHolder> {

    private final Context context;
    public ArrayList<ModelProduct> productList, filterList;
    private FilterSearchGrid filter;

    public AdapterGridSearch(Context context, ArrayList<ModelProduct> productList) {
        this.context = context;
        this.productList = productList;
        this.filterList = productList;
    }

    @NonNull
    @Override
    public AdapterGridSearch.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_grid, parent, false);
        return new AdapterGridSearch.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGridSearch.ViewHolder holder, int position) {
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

        holder.titleTv.setText(productTitle);
        holder.brand.setText(brandName);

        if(discountAvailable.equals("true")){
            holder.originalPriceTv.append(discountPrice.replace(".",","));
            holder.discountedNoteTv.setText(discountNote + "% OFF");
            holder.originalPriceTv.setVisibility(View.VISIBLE);
            holder.discountedNoteTv.setVisibility(View.VISIBLE);
            holder.discountedNoteTv.setText(discountNote + "% OFF");
        }
        else{
            holder.originalPriceTv.append(originalPrice.replace(".",","));
            holder.fire.setVisibility(View.GONE);
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

        holder.clicklistener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Objects.equals(productType, "Diet")){
                    Intent intent = new Intent(v.getContext(), DietDetailsActivity.class);
                    intent.putExtra("nationalMail", nationalMail);
                    intent.putExtra("title", productTitle);
                    intent.putExtra("indisponivel", indisponivel);
                    intent.putExtra("type", productType);
                    intent.putExtra("photo", productIcon);
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
                    intent.putExtra("productId", productId);
                    intent.putExtra("shopUid", uid);
                    v.getContext().startActivity(intent);
                }
                if(Objects.equals(productType, "Others")){
                    Intent intent = new Intent(v.getContext(), MoreDetailsActivity.class);
                    intent.putExtra("title", productTitle);
                    intent.putExtra("nationalMail", nationalMail);
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
                if(Objects.equals(productType, "Closet")){
                    Intent intent = new Intent(v.getContext(), FashionActivity.class);
                    intent.putExtra("title", productTitle);
                    intent.putExtra("nationalMail", nationalMail);
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
                    intent.putExtra("productCountry", productCountry);
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
                    intent.putExtra("soyseal", soyseal);
                    intent.putExtra("parabenseal", parabenseal);
                    intent.putExtra("cottonseal", cottonseal);
                    intent.putExtra("cannaseal", cannaseal);
                    intent.putExtra("glutenseal", glutenseal);
                    intent.putExtra("productCountry", productCountry);
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
            filter = new FilterSearchGrid(this, filterList);
        }
        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView clicklistener;
        private final ImageView fire;
        private final ImageView productIconIv;
        private final ImageView goToDetails;
        private final ImageView nationalTypeImage;
        private final ImageView internationalTypeImage;
        private final TextView discountedNoteTv;
        private final TextView brand;
        private final TextView titleTv;
        private final TextView descriptionTv;
        private final TextView quantityTv;
        private final TextView originalPriceTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fire = itemView.findViewById(R.id.fire);
            clicklistener = itemView.findViewById(R.id.clickListenercategory);
            brand = itemView.findViewById(R.id.brandName);
            productIconIv = itemView.findViewById(R.id.searchgridicon);
            discountedNoteTv = itemView.findViewById(R.id.discountedNoteTv);
            titleTv = itemView.findViewById(R.id.titlegridsearch);
            descriptionTv = itemView.findViewById(R.id.descriptionTv);
            goToDetails = itemView.findViewById(R.id.clickListener0);
            quantityTv = itemView.findViewById(R.id.quantityTv);
            originalPriceTv = itemView.findViewById(R.id.pricegridsearch);
            nationalTypeImage = itemView.findViewById(R.id.imageNationalGrid);
            internationalTypeImage = itemView.findViewById(R.id.imageInternationalGrid);
        }
    }
}
