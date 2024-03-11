package app.vegan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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

public class AdapterServicesList extends RecyclerView.Adapter<AdapterServicesList.HolderProductUser> implements Filterable {

    private final Context context;
    public ArrayList<ModelProduct> productList, filterList;
    private FilterServices filter;

    public AdapterServicesList(Context context, ArrayList<ModelProduct> productList) {
        this.context = context;
        this.productList = productList;
        this.filterList = productList;
    }

    @NonNull
    @Override
    public HolderProductUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_categorydetails, parent, false);
        return new HolderProductUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterServicesList.HolderProductUser holder, int position) {
        final ModelProduct modelService = productList.get(position);
        String uid = modelService.getUid();
        String discountNote = modelService.getDiscountNote();
        String serviceIcon = modelService.getProfilePhoto();
        String serviceTitle = modelService.getServiceTitle();
        String address1 = modelService.getAddress1();
        String address2 = modelService.getAddress2();
        String address3 = modelService.getAddress3();
        String categories = modelService.getCategories();
        String city = modelService.getCity();
        String country = modelService.getCountry();
        String estPrice = modelService.getEstPrice();
        String firstBio = modelService.getFirstBio();
        String secondBio = modelService.getSecondBio();
        String serviceId = modelService.getServiceId();
        String state = modelService.getState();
        String suggest1 = modelService.getSuggest1();
        String suggest2 = modelService.getSuggest2();
        String suggest3 = modelService.getSuggest3();
        String thirdBio = modelService.getThirdBio();
        String correct = modelService.getCorrect();

        holder.fire.setVisibility(View.GONE);
        holder.brandTv.setText(serviceId);
        holder.titleTv.setText(serviceTitle);
        holder.titleTv.setTypeface(Typeface.DEFAULT_BOLD);
        holder.originalPriceTv.setText(firstBio);
        holder.discountedNoteTv.setText(discountNote + "% OFF");
        holder.discountedNoteTv.setVisibility(View.GONE);
        holder.originalPriceTv.setVisibility(View.GONE);
        holder.viewcut.setVisibility(View.GONE);
        holder.discountedPriceTv.setVisibility(View.GONE);
        holder.descriptionTv.setText(firstBio);

        try {
            Picasso.get().load(serviceIcon).placeholder(R.drawable.loading).into(holder.productIconIv);
        }
        catch (Exception e) {
            if (holder.productIconIv == null)
                return;
            else
            holder.productIconIv.setImageResource(R.drawable.error);
        }

        holder.addToCartIv.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ServiceDetailsActivity.class);
            intent.putExtra("name", serviceTitle);
            intent.putExtra("correct", correct);
            intent.putExtra("uid", uid);
            intent.putExtra("photo", serviceIcon);
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
            v.getContext().startActivity(intent);
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
            filter = new FilterServices(this, filterList);
        }
        return filter;
    }

    class HolderProductUser extends RecyclerView.ViewHolder{
        private final ImageView productIconIv, fire;
        private final ImageView addToCartIv;
        private final TextView discountedNoteTv;
        private final TextView titleTv;
        private final TextView descriptionTv;
        private final TextView quantityTv;
        private final TextView discountedPriceTv;
        private TextView originalPriceTv;
        private final TextView brandTv;
        private final View viewcut;

        public HolderProductUser(@NonNull View itemView) {
            super(itemView);

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
