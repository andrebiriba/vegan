package app.vegan;

import android.content.Context;
import android.content.Intent;
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

public class AdapterServicesHigh extends RecyclerView.Adapter<AdapterServicesHigh.HolderServicesHigh> implements Filterable {

    private final Context context;
    public ArrayList<ModelProduct> productList, filterList;
    private FilterProductBig filter;

    public AdapterServicesHigh(Context context, ArrayList<ModelProduct> productList) {
        this.context = context;
        this.productList = productList;
        this.filterList = productList;
    }

    @NonNull
    @Override
    public HolderServicesHigh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shops, parent, false);
        return new HolderServicesHigh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterServicesHigh.HolderServicesHigh holder, int position) {
        final ModelProduct modelService = productList.get(position);
        String serviceIcon = modelService.getProfilePhoto();
        String correct = modelService.getCorrect();

        try {
            Picasso.get().load(serviceIcon).placeholder(R.drawable.loading).into(holder.item_icon);
        }
        catch (Exception e) {
            if (holder.item_icon == null)
                return;
            else
                holder.item_icon.setImageResource(R.drawable.error);
        }
        holder.item_icon.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RestaurantDetailsActivity.class);
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
        }
        return filter;
    }

    class HolderServicesHigh extends RecyclerView.ViewHolder{
        private final ImageView item_icon;
        private final ImageView mailSeal;
        public HolderServicesHigh(@NonNull View itemView) {
            super(itemView);
            item_icon = itemView.findViewById(R.id.item_icon);
            mailSeal = itemView.findViewById(R.id.imageView83);
        }
    }
}
