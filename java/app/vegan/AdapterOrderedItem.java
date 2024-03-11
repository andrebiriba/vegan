package app.vegan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class AdapterOrderedItem extends RecyclerView.Adapter<AdapterOrderedItem.HolderOrderedItem> {

    private final Context context;
    private final ArrayList<ModelOrderedItem> orderedItemArrayList;

    public AdapterOrderedItem(Context context, ArrayList<ModelOrderedItem> orderedItemArrayList) {
        this.context = context;
        this.orderedItemArrayList = orderedItemArrayList;
    }

    @NonNull
    @Override
    public HolderOrderedItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_ordereditem, parent, false);
        return new HolderOrderedItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderOrderedItem holder, int position) {
        ModelOrderedItem modelOrderedItem = orderedItemArrayList.get(position);
        String name = modelOrderedItem.getName();
        String price = modelOrderedItem.getPrice();
        String quantity = modelOrderedItem.getQuantity();
        String size = modelOrderedItem.getSize();
        String adc1 = modelOrderedItem.getAdc1();
        String adc2 = modelOrderedItem.getAdc2();
        String adc3 = modelOrderedItem.getAdc3();
        String adc4 = modelOrderedItem.getAdc4();
        String adc5 = modelOrderedItem.getAdc5();
        String adc6 = modelOrderedItem.getAdc6();
        String adc7 = modelOrderedItem.getAdc7();
        String adc8 = modelOrderedItem.getAdc8();
        String adc9 = modelOrderedItem.getAdc9();
        String adc10 = modelOrderedItem.getAdc10();
        String adc11 = modelOrderedItem.getAdc11();
        String adc12 = modelOrderedItem.getAdc12();
        String adc13 = modelOrderedItem.getAdc13();
        String adc14 = modelOrderedItem.getAdc14();
        String adc15 = modelOrderedItem.getAdc15();
        String va1 = modelOrderedItem.getVa1();
        String va2 = modelOrderedItem.getVa2();
        String va3 = modelOrderedItem.getVa3();
        String va4 = modelOrderedItem.getVa4();
        String va5 = modelOrderedItem.getVa5();
        String va6 = modelOrderedItem.getVa6();
        String va7 = modelOrderedItem.getVa7();
        String va8 = modelOrderedItem.getVa8();
        String va9 = modelOrderedItem.getVa9();
        String va10 = modelOrderedItem.getVa10();
        String va11 = modelOrderedItem.getVa11();
        String va12 = modelOrderedItem.getVa12();
        String va13 = modelOrderedItem.getVa13();
        String va14 = modelOrderedItem.getVa14();
        String va15 = modelOrderedItem.getVa15();

        holder.itemTitleTv.setText(name);
        String arrange = price.replace("R$","");
        String temp = "R$"+arrange;
        holder.itemPriceTv.setText(temp.replace(".",","));
        holder.itemQuantityTv.setText("(" + quantity + ")");
        holder.taman.setText(size);
        String ad = adc1+" "+va1+adc2+" "+va2+adc3+" "+va3+adc4+" "+va4+adc5+" "+va5+adc6+" "+va6+adc7+" "+va7
                +adc8+" "+va8+adc9+" "+va9+adc10+" "+va10+adc11+" "+va11+adc12+" "+va12+adc13+" "+va13+adc14+" "+va14+adc15+" "+va15;
        holder.adicionais.setText(ad.replace("null",""));
    }

    @Override
    public int getItemCount() {
        if (orderedItemArrayList == null)
            return 0;
        else
            return orderedItemArrayList.size();
    }

    class HolderOrderedItem extends RecyclerView.ViewHolder {

        private final TextView itemTitleTv;
        private final TextView itemPriceTv;
        private final TextView itemQuantityTv, adicionais, taman;
        public HolderOrderedItem(@NonNull View itemView) {
            super(itemView);
            adicionais = itemView.findViewById(R.id.adicionais);
            taman = itemView.findViewById(R.id.taman);
            itemTitleTv = itemView.findViewById(R.id.itemTitleTv);
            itemPriceTv = itemView.findViewById(R.id.itemPriceTv);
            itemQuantityTv = itemView.findViewById(R.id.itemQuantityTv);
        }
    }
}
