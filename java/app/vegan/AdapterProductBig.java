package app.vegan;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Objects;

public class AdapterProductBig extends RecyclerView.Adapter<AdapterProductBig.HolderProductBig> implements Filterable {

    private final Context context;
    public ArrayList<ModelProduct> productList, filterList;
    private FilterProductBig filter;

    public AdapterProductBig(Context context, ArrayList<ModelProduct> productList) {
        this.context = context;
        this.productList = productList;
        this.filterList = productList;
    }

    @NonNull
    @Override
    public HolderProductBig onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shops, parent, false);
        return new HolderProductBig(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProductBig.HolderProductBig holder, int position) {
        final ModelProduct modelProduct = productList.get(position);
        String productIcon = modelProduct.getProductIcon();
        String uid = modelProduct.getUid();
        String brandName = modelProduct.getProductBrand();
        String productType = modelProduct.getProductType();

        String uid2 = modelProduct.getShopUid();
        String serviceIcon = modelProduct.getProfilePhoto();
        String serviceTitle = modelProduct.getServiceTitle();
        String address1 = modelProduct.getAddress1();
        String address2 = modelProduct.getAddress2();
        String address3 = modelProduct.getAddress3();
        String categories = modelProduct.getCategories();
        String city = modelProduct.getCity();
        String country = modelProduct.getCountry();
        String estPrice = modelProduct.getEstPrice();
        String firstBio = modelProduct.getFirstBio();
        String secondBio = modelProduct.getSecondBio();
        String serviceId = modelProduct.getServiceId();
        String state = modelProduct.getState();
        String suggest1 = modelProduct.getSuggest1();
        String suggest2 = modelProduct.getSuggest2();
        String suggest3 = modelProduct.getSuggest3();
        String thirdBio = modelProduct.getThirdBio();
        String moreText = modelProduct.getMoreText();
        String cel = modelProduct.getCel();


        try {
            Picasso.get().load(productIcon).placeholder(R.drawable.loading).into(holder.item_icon);
        }
        catch (Exception e) {
            if (holder.item_icon == null)
                return;
            else
                holder.item_icon.setImageResource(R.drawable.newlogoveganosv);
        }

        holder.item_icon.setOnClickListener(v -> {
            if (!Objects.equals(productType, "Services")){
         //   Intent intent = new Intent(v.getContext(), RestaurantDetailsActivity.class);
           // intent.putExtra("uid", uid2);
           // intent.putExtra("shopUid", uid2);
           // intent.putExtra("brandName", brandName);
           // intent.putExtra("type", productType);
           // v.getContext().startActivity(intent);

            }else {

                Intent intent = new Intent(v.getContext(), ServiceDetailsActivity.class);
                intent.putExtra("name", serviceTitle);
                intent.putExtra("uid", uid2);
                intent.putExtra("photo", serviceIcon);
                intent.putExtra("cel", cel);
                intent.putExtra("address1", address1);
                intent.putExtra("address2", address2);
                intent.putExtra("address3", address3);
                intent.putExtra("categories", categories);
                intent.putExtra("city", city);
                intent.putExtra("country", country);
                intent.putExtra("estPrice", estPrice);
                intent.putExtra("firstBio", firstBio);
                intent.putExtra("secondBio", secondBio);
                intent.putExtra("serviceId", serviceId);
                intent.putExtra("state", state);
                intent.putExtra("suggest1", suggest1);
                intent.putExtra("suggest2", suggest2);
                intent.putExtra("suggest3", suggest3);
                intent.putExtra("thirdBio", thirdBio);
                intent.putExtra("moreText", moreText);
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
            filter = new FilterProductBig(this, filterList);
        }
        return filter;
    }

    class HolderProductBig extends RecyclerView.ViewHolder{
        private final ImageView item_icon;
        private final ImageView mailSeal;
        public HolderProductBig(@NonNull View itemView) {
            super(itemView);

            item_icon = itemView.findViewById(R.id.item_icon);
            mailSeal = itemView.findViewById(R.id.imageView83);
        }
    }
}
