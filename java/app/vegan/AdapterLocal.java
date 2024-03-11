package app.vegan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Objects;

public class AdapterLocal extends RecyclerView.Adapter<AdapterLocal.HolderLocal>{

    private final Context context;
    public ArrayList<ModelLocal> brandList, filterList;

    public AdapterLocal(Context context, ArrayList<ModelLocal> brandList) {
        this.context = context;
        this.brandList = brandList;
        this.filterList = brandList;
    }

    @NonNull
    @Override
    public HolderLocal onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_brands, parent, false);
        return new HolderLocal(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLocal.HolderLocal holder, int position) {
        final ModelLocal modelLocal = brandList.get(position);
        String brandTitle = modelLocal.getFName();
        String photo = modelLocal.getShopIcon();
        String description = modelLocal.getDescription();
        String type = modelLocal.getType();
        String firstBio = modelLocal.getFirstBio();
        String serviceTitle = modelLocal.getServiceTitle();
        String profilePhoto = modelLocal.getProfilePhoto();
        String petFriendly = modelLocal.getPetFriendly();
        String city = modelLocal.getCity();
        String country = modelLocal.getCountry();
        String state = modelLocal.getState();
        String address1 = modelLocal.getAddress1();
        String address2 = modelLocal.getAddress2();
        String address3 = modelLocal.getAddress3();
        String categories = modelLocal.getCategories();
        String estPrice = modelLocal.getEstPrice();
        String secondBio = modelLocal.getSecondBio();
        String serviceId = modelLocal.getServiceId();
        String suggest1 = modelLocal.getSuggest1();
        String suggest2 = modelLocal.getSuggest2();
        String suggest3 = modelLocal.getSuggest3();
        String thirdBio = modelLocal.getThirdBio();
        String uid = modelLocal.getUid();
        String vegan = modelLocal.getVegan();
        String acessibility = modelLocal.getAccessibility();
        String correctSeal = modelLocal.getCorrect();


        if(Objects.equals(correctSeal, "true")){
            holder.correct.setVisibility(View.VISIBLE);
        }

        if (Objects.equals(type, "Services")){
            holder.title.setText(serviceTitle);
            holder.description.setText(firstBio);
            try {
                Picasso.get().load(profilePhoto).placeholder(R.drawable.loading).into(holder.productIconIv);
            }
            catch (Exception e) {
                if (holder.productIconIv == null)
                    return;
                else
                    holder.productIconIv.setImageResource(R.drawable.error);
            }


        } else {
            holder.title.setText(brandTitle);
            holder.description.setText(description);

            try {
                Picasso.get().load(photo).placeholder(R.drawable.loading).into(holder.productIconIv);
            }
            catch (Exception e) {
                if (holder.productIconIv == null)
                    return;
                else
                    holder.productIconIv.setImageResource(R.drawable.error);
            }
        }

        holder.clickIv.setOnClickListener(v -> {
            if (Objects.equals(type, "Services")){
                Intent intent = new Intent(v.getContext(), ServiceDetailsActivity.class);
            intent.putExtra("name", serviceTitle);
            intent.putExtra("uid", uid);
            intent.putExtra("photo", profilePhoto);
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
            } else {
                Intent intent = new Intent(v.getContext(), RestaurantDetailsActivity.class);
                intent.putExtra("shopUid", uid);
                intent.putExtra("uid", uid);
                intent.putExtra("brandName", brandTitle);
                intent.putExtra("type", type);
                v.getContext().startActivity(intent);
            }
        });
        if(petFriendly == null) {
            petFriendly="false";
        }

            if(petFriendly.equals("true")){
            holder.petfriendly.setVisibility(View.VISIBLE);
        }
        else{
            holder.petfriendly.setVisibility(View.GONE);
        }

        if(vegan == null) {
            vegan="false";
        }

        if(vegan.equals("true")){
            holder.vegan.setVisibility(View.VISIBLE);
        }
        else{
            holder.vegan.setVisibility(View.GONE);
        }

        if(acessibility == null) {
            acessibility="false";
        }

        if(acessibility.equals("true")){
            holder.acess.setVisibility(View.VISIBLE);
        }
        else{
            holder.acess.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (brandList == null)
            return 0;
        else
            return brandList.size();
    }

    class HolderLocal extends RecyclerView.ViewHolder{
        private final TextView title;
        private final TextView description;
        private final ImageView petfriendly;
        private final ImageView productIconIv;
        private final ImageView acess;
        private final ImageView vegan;
        private final ImageView correct;
        private final CardView clickIv;

        public HolderLocal(@NonNull View itemView) {
            super(itemView);


            correct = itemView.findViewById(R.id.imageView37);
            clickIv = itemView.findViewById(R.id.cardviewclick);
            petfriendly = itemView.findViewById(R.id.petfriend);
            acess = itemView.findViewById(R.id.acess);
            vegan = itemView.findViewById(R.id.vega);
            productIconIv = itemView.findViewById(R.id.productIconIv);
            title = itemView.findViewById(R.id.titleTv);
            description = itemView.findViewById(R.id.descriptionTv);
        }
    }
}
