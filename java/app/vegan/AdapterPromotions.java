package app.vegan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Objects;

public class AdapterPromotions extends RecyclerView.Adapter<AdapterPromotions.HolderProductUser> implements Filterable {

    private final Context context;
    public ArrayList<ModelProduct> productList, filterList;
    private FilterPromotions filter;

    public AdapterPromotions(Context context, ArrayList<ModelProduct> productList) {
        this.context = context;
        this.productList = productList;
        this.filterList = productList;
    }

    @NonNull
    @Override
    public HolderProductUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_restaurant, parent, false);
        return new HolderProductUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPromotions.HolderProductUser holder, int position) {
        final ModelProduct modelProduct = productList.get(position);
        String indisponivel = modelProduct.getIndisponivel();
        String uid = modelProduct.getUid();
        String localOption = modelProduct.getLocalOption();
        String nationalMail = modelProduct.getNationalMail();
        String internationalMail = modelProduct.getInternationalMail();
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
        String discountAvailable = modelProduct.getDiscountAvailable();
        String discountNote = modelProduct.getDiscountNote();
        String discountPrice = modelProduct.getDiscountPrice();
        String productCategory = modelProduct.getProductCategory();
        String productDescription = modelProduct.getProductDescription();
        String productIcon = modelProduct.getProductIcon();
        String productQuantity = modelProduct.getProductQuantity();
        String productTitle = modelProduct.getProductTitle();
        String productId = modelProduct.getProductId();
        String timestamp = modelProduct.getTimestamp();
        String originalPrice = modelProduct.getOriginalPrice();
        String sizep = modelProduct.getSizep();
        String sizem = modelProduct.getSizem();
        String sizeg = modelProduct.getSizeg();
        String sizegg = modelProduct.getSizegg();
        String sizeggg = modelProduct.getSizeggg();
        String sizePersonalized = modelProduct.getSizePersonalized();
        String securePolicy = modelProduct.getSecurePolicy();
        String scheduleProduction = modelProduct.getScheduleProduction();
        String quality = modelProduct.getQuality();
        String onTime = modelProduct.getOnTime();
        String nationalTime = modelProduct.getNationalTime();
        String moreInfo = modelProduct.getMoreInfo();
        String importedTime = modelProduct.getImportedTime();
        String deliveryOption = modelProduct.getDeliveryOption();
        String estoque = modelProduct.getEstoque();
        String deliveryTime = modelProduct.getDeliveryTime();
        String productIcon2 = modelProduct.getProductIcon2();
        String productIcon3 = modelProduct.getProductIcon3();
        String ingredients = modelProduct.getIngredients();
        String adicional1 = modelProduct.getAdicional001();
        String adicional2 = modelProduct.getAdicional002();
        String adicional3 = modelProduct.getAdicional003();
        String adicional4 = modelProduct.getAdicional004();
        String adicional5 = modelProduct.getAdicional005();
        String adicional6 = modelProduct.getAdicional006();
        String adicional7 = modelProduct.getAdicional007();
        String adicional8 = modelProduct.getAdicional008();
        String adicional9 = modelProduct.getAdicional009();
        String adicional10 = modelProduct.getAdicional010();
        String adicional11 = modelProduct.getAdicional011();
        String adicional12 = modelProduct.getAdicional012();
        String adicional13 = modelProduct.getAdicional013();
        String adicional14 = modelProduct.getAdicional014();
        String adicional15 = modelProduct.getAdicional015();
        String valor1 = modelProduct.getValor1();
        String valor2 = modelProduct.getValor2();
        String valor3 = modelProduct.getValor3();
        String valor4 = modelProduct.getValor4();
        String valor5 = modelProduct.getValor5();
        String valor6 = modelProduct.getValor6();
        String valor7 = modelProduct.getValor7();
        String valor8 = modelProduct.getValor8();
        String valor9 = modelProduct.getValor9();
        String valor10 = modelProduct.getValor10();
        String valor11 = modelProduct.getValor11();
        String valor12 = modelProduct.getValor12();
        String valor13 = modelProduct.getValor13();
        String valor14 = modelProduct.getValor14();
        String valor15 = modelProduct.getValor15();
        String productCountry = modelProduct.getProductCountry();

        if(nationalMail.equals("true")){
            try {
                Picasso.get().load(R.drawable.boxtruck).placeholder(context.getResources().getDrawable(R.drawable.loading)).into(holder.mailSeal);
            } catch (Exception e) {
                if (holder.mailSeal == null)
                    return;
                else
                    holder.mailSeal.setImageResource(R.drawable.error);
            }
        }else {
            holder.mailSeal.setVisibility(View.GONE);
        }

        if(internationalMail.equals("true")){
            try {
                Picasso.get().load(R.drawable.globe).placeholder(R.drawable.loading).into(holder.mailSeal);
            } catch (Exception e) {
                if (holder.mailSeal == null)
                    return;
                else
                    holder.mailSeal.setImageResource(R.drawable.error);
            }
        }

        if(Objects.equals(productCountry,"Brasil")){
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
        if(Objects.equals(productCountry,"França")){
            holder.discountedPriceTv.setText("€");
        }
        if(Objects.equals(productCountry, "Portugal")){
            holder.discountedPriceTv.setText("€");
        }
        if(Objects.equals(productCountry, "Espanha")){
            holder.discountedPriceTv.setText("€");
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.mexico))){
            holder.discountedPriceTv.setText("MEX$");
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.argentina))){
            holder.discountedPriceTv.setText("ARS$");
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.escocia))){
            holder.discountedPriceTv.setText("£");
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.canada))){
            holder.discountedPriceTv.setText("C$");
        }

        holder.item_subtitle.setText(brandName);
        holder.titleTv.setText(productTitle);
       // holder.discountedPriceTv.append(originalPrice.replace(".",","));

        if(discountAvailable.equals("true")){
            holder.discountedPriceTv.setVisibility(View.VISIBLE);
            holder.discountedNoteTv.setVisibility(View.VISIBLE);
            holder.discountedNoteTv.setText(discountNote + "% OFF");
            holder.discountedPriceTv.append(discountPrice.replace(".",","));

        }
        else{
            holder.fire.setVisibility(View.GONE);
            holder.discountedNoteTv.setVisibility(View.GONE);
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
        holder.goBrandPage.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RestaurantDetailsActivity.class);
            intent.putExtra("uid", uid);
            intent.putExtra("shopUid", uid);
            intent.putExtra("brandName", brandName);
            intent.putExtra("type", productType);
            v.getContext().startActivity(intent);
        });

        holder.addToCartIv.setOnClickListener(v -> {
            if(Objects.equals(productType, "Diet")){
                Intent intent = new Intent(v.getContext(), DietDetailsActivity.class);
                intent.putExtra("indisponivel", indisponivel);
                intent.putExtra("adicional1", adicional1);
                intent.putExtra("adicional2", adicional2);
                intent.putExtra("adicional3", adicional3);
                intent.putExtra("adicional4", adicional4);
                intent.putExtra("adicional5", adicional5);
                intent.putExtra("adicional6", adicional6);
                intent.putExtra("adicional7", adicional7);
                intent.putExtra("adicional8", adicional8);
                intent.putExtra("adicional9", adicional9);
                intent.putExtra("adicional10", adicional10);
                intent.putExtra("adicional11", adicional11);
                intent.putExtra("adicional12", adicional12);
                intent.putExtra("adicional13", adicional13);
                intent.putExtra("adicional14", adicional14);
                intent.putExtra("adicional15", adicional15);
                intent.putExtra("productCountry", productCountry);
                intent.putExtra("valor1", valor1);
                intent.putExtra("valor2", valor2);
                intent.putExtra("valor3", valor3);
                intent.putExtra("valor4", valor4);
                intent.putExtra("valor5", valor5);
                intent.putExtra("valor6", valor6);
                intent.putExtra("valor7", valor7);
                intent.putExtra("valor8", valor8);
                intent.putExtra("valor9", valor9);
                intent.putExtra("valor10", valor10);
                intent.putExtra("valor11", valor11);
                intent.putExtra("valor12", valor12);
                intent.putExtra("valor13", valor13);
                intent.putExtra("valor14", valor14);
                intent.putExtra("valor15", valor15);
                intent.putExtra("sizep", sizep);
                intent.putExtra("sizem", sizem);
                intent.putExtra("sizeg", sizeg);
                intent.putExtra("sizegg", sizegg);
                intent.putExtra("sizeggg", sizeggg);
                intent.putExtra("sizePersonalized", sizePersonalized);
                intent.putExtra("securePolicy", securePolicy);
                intent.putExtra("scheduleProduction", scheduleProduction);
                intent.putExtra("quality", quality);
                intent.putExtra("onTime", onTime);
                intent.putExtra("nationalTime", nationalTime);
                intent.putExtra("nationalMail", nationalMail);
                intent.putExtra("moreInfo", moreInfo);
                intent.putExtra("importedTime", importedTime);
                intent.putExtra("deliveryOption", deliveryOption);
                intent.putExtra("estoque", estoque);
                intent.putExtra("deliveryTime", deliveryTime);
                intent.putExtra("localOption", localOption);
                intent.putExtra("ingredients", ingredients);
                intent.putExtra("title", productTitle);
                intent.putExtra("type", productType);
                intent.putExtra("photo", productIcon);
                intent.putExtra("photo2", productIcon2);
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
                intent.putExtra("rawseal", rawseal);
                intent.putExtra("b12seal", b12seal);
                intent.putExtra("ecoseal", ecoseal);
                intent.putExtra("timestamp", timestamp);
                intent.putExtra("categoryProduct", productCategory);
                intent.putExtra("productId", productId);
                intent.putExtra("shopUid", uid);
                v.getContext().startActivity(intent);
            }
            if(Objects.equals(productType, "Others")){
                Intent intent = new Intent(v.getContext(), MoreDetailsActivity.class);
                intent.putExtra("title", productTitle);
                intent.putExtra("indisponivel", indisponivel);
                intent.putExtra("nationalMail", nationalMail);
                intent.putExtra("photo", productIcon);
                intent.putExtra("photo2", productIcon2);
                intent.putExtra("photo3", productIcon3);
                intent.putExtra("type", productType);
                intent.putExtra("productCountry", productCountry);
                intent.putExtra("brandName", brandName);
                intent.putExtra("discountPrice", discountPrice);
                intent.putExtra("discountNote", discountNote);
                intent.putExtra("priceOriginal", originalPrice);
                intent.putExtra("quantityProduct", productQuantity);
                intent.putExtra("descriptionProduct", productDescription);
                intent.putExtra("soyseal", soyseal);
                intent.putExtra("adicional1", adicional1);
                intent.putExtra("adicional2", adicional2);
                intent.putExtra("adicional3", adicional3);
                intent.putExtra("adicional4", adicional4);
                intent.putExtra("adicional5", adicional5);
                intent.putExtra("adicional6", adicional6);
                intent.putExtra("adicional7", adicional7);
                intent.putExtra("adicional8", adicional8);
                intent.putExtra("adicional9", adicional9);
                intent.putExtra("adicional10", adicional10);
                intent.putExtra("adicional11", adicional11);
                intent.putExtra("adicional12", adicional12);
                intent.putExtra("adicional13", adicional13);
                intent.putExtra("adicional14", adicional14);
                intent.putExtra("adicional15", adicional15);
                intent.putExtra("productCountry", productCountry);
                intent.putExtra("valor1", valor1);
                intent.putExtra("valor2", valor2);
                intent.putExtra("valor3", valor3);
                intent.putExtra("valor4", valor4);
                intent.putExtra("valor5", valor5);
                intent.putExtra("valor6", valor6);
                intent.putExtra("valor7", valor7);
                intent.putExtra("valor8", valor8);
                intent.putExtra("valor9", valor9);
                intent.putExtra("valor10", valor10);
                intent.putExtra("valor11", valor11);
                intent.putExtra("valor12", valor12);
                intent.putExtra("valor13", valor13);
                intent.putExtra("valor14", valor14);
                intent.putExtra("valor15", valor15);
                intent.putExtra("sizep", sizep);
                intent.putExtra("sizem", sizem);
                intent.putExtra("sizeg", sizeg);
                intent.putExtra("sizegg", sizegg);
                intent.putExtra("sizeggg", sizeggg);
                intent.putExtra("localOption", localOption);
                intent.putExtra("sizePersonalized", sizePersonalized);
                intent.putExtra("type", productType);
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
                intent.putExtra("photo", productIcon);
                intent.putExtra("localOption", localOption);
                intent.putExtra("productCountry", productCountry);
                intent.putExtra("photo2", productIcon2);
                intent.putExtra("photo3", productIcon3);
                intent.putExtra("brandName", brandName);
                intent.putExtra("nationalMail", nationalMail);
                intent.putExtra("discountPrice", discountPrice);
                intent.putExtra("discountNote", discountNote);
                intent.putExtra("priceOriginal", originalPrice);
                intent.putExtra("quantityProduct", productQuantity);
                intent.putExtra("descriptionProduct", productDescription);
                intent.putExtra("soyseal", soyseal);
                intent.putExtra("parabenseal", parabenseal);
                intent.putExtra("type", productType);
                intent.putExtra("cottonseal", cottonseal);
                intent.putExtra("cannaseal", cannaseal);
                intent.putExtra("glutenseal", glutenseal);
                intent.putExtra("organicseal", organicseal);
                intent.putExtra("recyseal", recyseal);
                intent.putExtra("biodeseal", biodeseal);
                intent.putExtra("nogmoseal", nogmoseal);
                intent.putExtra("rawseal", rawseal);
                intent.putExtra("adicional1", adicional1);
                intent.putExtra("adicional2", adicional2);
                intent.putExtra("adicional3", adicional3);
                intent.putExtra("adicional4", adicional4);
                intent.putExtra("adicional5", adicional5);
                intent.putExtra("adicional6", adicional6);
                intent.putExtra("adicional7", adicional7);
                intent.putExtra("adicional8", adicional8);
                intent.putExtra("adicional9", adicional9);
                intent.putExtra("adicional10", adicional10);
                intent.putExtra("adicional11", adicional11);
                intent.putExtra("adicional12", adicional12);
                intent.putExtra("adicional13", adicional13);
                intent.putExtra("adicional14", adicional14);
                intent.putExtra("adicional15", adicional15);
                intent.putExtra("productCountry", productCountry);
                intent.putExtra("valor1", valor1);
                intent.putExtra("valor2", valor2);
                intent.putExtra("valor3", valor3);
                intent.putExtra("valor4", valor4);
                intent.putExtra("valor5", valor5);
                intent.putExtra("valor6", valor6);
                intent.putExtra("valor7", valor7);
                intent.putExtra("valor8", valor8);
                intent.putExtra("valor9", valor9);
                intent.putExtra("valor10", valor10);
                intent.putExtra("valor11", valor11);
                intent.putExtra("valor12", valor12);
                intent.putExtra("valor13", valor13);
                intent.putExtra("valor14", valor14);
                intent.putExtra("valor15", valor15);
                intent.putExtra("sizep", sizep);
                intent.putExtra("sizem", sizem);
                intent.putExtra("sizeg", sizeg);
                intent.putExtra("sizegg", sizegg);
                intent.putExtra("sizeggg", sizeggg);
                intent.putExtra("sizePersonalized", sizePersonalized);
                intent.putExtra("type", productType);
                intent.putExtra("b12seal", b12seal);
                intent.putExtra("ecoseal", ecoseal);
                intent.putExtra("categoryProduct", productCategory);
                intent.putExtra("shopUid", uid);
                intent.putExtra("productId", productId);
                v.getContext().startActivity(intent);
            }
            if(Objects.equals(productType, "Pharmacy")){
                Intent intent = new Intent(v.getContext(), MedicineDetailsActivity.class);
                intent.putExtra("title", productTitle);
                intent.putExtra("indisponivel", indisponivel);
                intent.putExtra("photo", productIcon);
                intent.putExtra("localOption", localOption);
                intent.putExtra("photo2", productIcon2);
                intent.putExtra("productCountry", productCountry);
                intent.putExtra("brandName", brandName);
                intent.putExtra("discountPrice", discountPrice);
                intent.putExtra("nationalMail", nationalMail);
                intent.putExtra("discountNote", discountNote);
                intent.putExtra("priceOriginal", originalPrice);
                intent.putExtra("quantityProduct", productQuantity);
                intent.putExtra("descriptionProduct", productDescription);
                intent.putExtra("soyseal", soyseal);
                intent.putExtra("parabenseal", parabenseal);
                intent.putExtra("type", productType);
                intent.putExtra("cottonseal", cottonseal);
                intent.putExtra("cannaseal", cannaseal);
                intent.putExtra("glutenseal", glutenseal);
                intent.putExtra("organicseal", organicseal);
                intent.putExtra("recyseal", recyseal);
                intent.putExtra("biodeseal", biodeseal);
                intent.putExtra("nogmoseal", nogmoseal);
                intent.putExtra("rawseal", rawseal);
                intent.putExtra("adicional1", adicional1);
                intent.putExtra("adicional2", adicional2);
                intent.putExtra("adicional3", adicional3);
                intent.putExtra("adicional4", adicional4);
                intent.putExtra("adicional5", adicional5);
                intent.putExtra("adicional6", adicional6);
                intent.putExtra("adicional7", adicional7);
                intent.putExtra("adicional8", adicional8);
                intent.putExtra("adicional9", adicional9);
                intent.putExtra("adicional10", adicional10);
                intent.putExtra("adicional11", adicional11);
                intent.putExtra("adicional12", adicional12);
                intent.putExtra("adicional13", adicional13);
                intent.putExtra("adicional14", adicional14);
                intent.putExtra("adicional15", adicional15);
                intent.putExtra("productCountry", productCountry);
                intent.putExtra("valor1", valor1);
                intent.putExtra("valor2", valor2);
                intent.putExtra("valor3", valor3);
                intent.putExtra("valor4", valor4);
                intent.putExtra("valor5", valor5);
                intent.putExtra("valor6", valor6);
                intent.putExtra("valor7", valor7);
                intent.putExtra("valor8", valor8);
                intent.putExtra("valor9", valor9);
                intent.putExtra("valor10", valor10);
                intent.putExtra("valor11", valor11);
                intent.putExtra("valor12", valor12);
                intent.putExtra("valor13", valor13);
                intent.putExtra("valor14", valor14);
                intent.putExtra("valor15", valor15);
                intent.putExtra("sizep", sizep);
                intent.putExtra("sizem", sizem);
                intent.putExtra("sizeg", sizeg);
                intent.putExtra("sizegg", sizegg);
                intent.putExtra("sizeggg", sizeggg);
                intent.putExtra("sizePersonalized", sizePersonalized);
                intent.putExtra("b12seal", b12seal);
                intent.putExtra("ecoseal", ecoseal);
                intent.putExtra("categoryProduct", productCategory);
                intent.putExtra("shopUid", uid);
                intent.putExtra("productId", productId);
                intent.putExtra("type", productType);
                v.getContext().startActivity(intent);
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
             filter = new FilterPromotions(this, filterList);
        }
        return filter;
    }

    class HolderProductUser extends RecyclerView.ViewHolder{
        private final ImageView productIconIv, fire;
        private final ImageView addToCartIv;
        private final ImageView goBrandPage;
        private final ImageView mailSeal;
        private final TextView discountedNoteTv;
        private final TextView titleTv;
        private final TextView discountedPriceTv;
        private final TextView item_subtitle;

        public HolderProductUser(@NonNull View itemView) {
            super(itemView);

            fire = itemView.findViewById(R.id.fire);
            mailSeal = itemView.findViewById(R.id.imageView84);
            item_subtitle = itemView.findViewById(R.id.item_subtitle);
            productIconIv = itemView.findViewById(R.id.productIconIv);
            discountedNoteTv = itemView.findViewById(R.id.discountedNoteTv);
            titleTv = itemView.findViewById(R.id.titleTv);
            addToCartIv = itemView.findViewById(R.id.clickListener0);
            goBrandPage = itemView.findViewById(R.id.clickListener1);
            discountedPriceTv = itemView.findViewById(R.id.discountedPriceTv);
        }
    }
}
