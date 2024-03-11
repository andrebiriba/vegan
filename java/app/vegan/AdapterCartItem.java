package app.vegan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AdapterCartItem extends RecyclerView.Adapter<AdapterCartItem.HolderCartItem> {

    private final Context context;
    private final ArrayList<ModelCartItem> cartItems;

    public AdapterCartItem(Context context, ArrayList<ModelCartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public HolderCartItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_cartitem, parent, false);
        return new HolderCartItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCartItem.HolderCartItem holder, int position) {
        ModelCartItem modelCartItem = cartItems.get(position);
        String uid = modelCartItem.getUid();
        final String id = modelCartItem.getId();
        String title  = modelCartItem.getName();
        String price = modelCartItem.getPrice();
        String quantity = modelCartItem.getQuantity();
        String foto = modelCartItem.getPhoto();
        String descriptionProduct = modelCartItem.getDescriptionProduct();
        String productCountry = modelCartItem.getProductCountry();
        String va1 = modelCartItem.getVa1();
        String va2 = modelCartItem.getVa2();
        String va3 = modelCartItem.getVa3();
        String va4 = modelCartItem.getVa4();
        String va5 = modelCartItem.getVa5();
        String va6 = modelCartItem.getVa6();
        String va7 = modelCartItem.getVa7();
        String va8 = modelCartItem.getVa8();
        String va9 = modelCartItem.getVa9();
        String va10 = modelCartItem.getVa10();
        String va11 = modelCartItem.getVa11();
        String va12 = modelCartItem.getVa12();
        String va13 = modelCartItem.getVa13();
        String va14 = modelCartItem.getVa14();
        String va15 = modelCartItem.getVa15();

        holder.v1 =0.0;
        holder.v2 =0.0;
        holder.v3 =0.0;
        holder.v4 =0.0;
        holder.v5 =0.0;
        holder.v1 =0.0;
        holder.v6 =0.0;
        holder.v7 =0.0;
        holder.v8 =0.0;
        holder.v9 =0.0;
        holder.v10 =0.0;
        holder.v11 =0.0;
        holder.v12 =0.0;
        holder.v13 =0.0;
        holder.v14 =0.0;
        holder.v15 =0.0;


        if(va1!=null){
         holder.v1 = Double.parseDouble(va1.replace(",",".").replace("(","").replace(")",""));}
        if(va2!=null){
            holder.v2 = Double.parseDouble(va2.replace(",",".").replace("(","").replace(")",""));}
        if(va3!=null){
            holder.v3 = Double.parseDouble(va3.replace(",",".").replace("(","").replace(")",""));}
        if(va4!=null){
            holder.v4 = Double.parseDouble(va4.replace(",",".").replace("(","").replace(")",""));}
        if(va5!=null){
            holder.v5 = Double.parseDouble(va5.replace(",",".").replace("(","").replace(")",""));}
        if(va6!=null){
            holder.v6 = Double.parseDouble(va6.replace(",",".").replace("(","").replace(")",""));}
        if(va7!=null){
            holder.v7 = Double.parseDouble(va7.replace(",",".").replace("(","").replace(")",""));}
        if(va8!=null){
            holder.v8 = Double.parseDouble(va8.replace(",",".").replace("(","").replace(")",""));}
        if(va9!=null){
            holder.v9 = Double.parseDouble(va9.replace(",",".").replace("(","").replace(")",""));}
        if(va10!=null){
            holder.v10 = Double.parseDouble(va10.replace(",",".").replace("(","").replace(")",""));}
        if(va11!=null){
            holder.v11 = Double.parseDouble(va11.replace(",",".").replace("(","").replace(")",""));}
        if(va12!=null){
            holder.v12 = Double.parseDouble(va12.replace(",",".").replace("(","").replace(")",""));}
        if(va13!=null){
            holder.v13 = Double.parseDouble(va13.replace(",",".").replace("(","").replace(")",""));}
        if(va14!=null){
            holder.v14 = Double.parseDouble(va14.replace(",",".").replace("(","").replace(")",""));}
        if(va15!=null){
            holder.v15 = Double.parseDouble(va15.replace(",",".").replace("(","").replace(")",""));}

        Double totaladicionais = holder.v1+holder.v2+holder.v3+holder.v4+holder.v5+holder.v6+holder.v7+holder.v8+holder.v9+holder.v10+holder.v11+holder.v12+
                holder.v13+holder.v14+holder.v15;

        String finalcoin = "";
        if(Objects.equals(productCountry, context.getResources().getString(R.string.brasil))){
            holder.itemPriceEachTv.setText("R$");
            finalcoin = "R$";
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.eua))){
            holder.itemPriceEachTv.setText("US$");
            finalcoin = "US$";
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.inglaterra))){
            holder.itemPriceEachTv.setText("£");
            finalcoin = "£";
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.alemanha))){
            holder.itemPriceEachTv.setText("€");
            finalcoin = "€";
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.franca))){
            holder.itemPriceEachTv.setText("€");
            finalcoin = "€";
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.canada))){
            holder.itemPriceEachTv.setText("C$");
            finalcoin = "C$";
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.portugal))){
            holder.itemPriceEachTv.setText("€");
            finalcoin = "€";
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.espanha))){
            holder.itemPriceEachTv.setText("€");
            finalcoin = "€";
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.mexico))){
            holder.itemPriceEachTv.setText("MEX$");
            finalcoin = "MEX$";
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.argentina))){
            holder.itemPriceEachTv.setText("ARS$");
            finalcoin = "ARS$";
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.escocia))){
            holder.itemPriceEachTv.setText("£");
            finalcoin = "£";
        }

        holder.itemTitleTv.setText(""+title);
        holder.itemQuantityTv.setText(quantity);
        Double somer = Double.parseDouble(price.replace(finalcoin,"").replace(",",".").replace("(","").replace(")",".")) + totaladicionais;
        String tem = String.valueOf(somer);
        holder.itemPriceEachTv.append(tem.replace(".",","));
        holder.brand.setText(descriptionProduct);

        try {
            Picasso.get().load(foto).placeholder(R.drawable.loading).into(holder.image);
        }
        catch(Exception e){
              holder.image.setImageResource(R.drawable.error);
        }
        String novirgula;
        novirgula = price.replace(",",".").replace(finalcoin,"");
        holder.finalPrice = Double.parseDouble(novirgula);

        String finalCoin = finalcoin;
        holder.aumentar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                int count= Integer.parseInt(String.valueOf(holder.itemQuantityTv.getText()));
                count++;
                holder.itemQuantityTv.setText("" + count);
                String id = modelCartItem.getId();
                String uid = modelCartItem.getUid();
                FirebaseFirestore fStore = FirebaseFirestore.getInstance();
                DocumentReference docRef = fStore.collection("Cart "+uid).document(id);
                Map<String,Object> edited = new HashMap<>();
                edited.put("quantity",String. valueOf(count));
                Double calculinho = Double.parseDouble(holder.itemPriceEachTv.getText().toString().replace(",",".").replace(finalCoin,""));
                String maiscal = String. valueOf(count*calculinho);
                edited.put("totalvalue",maiscal);
                docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        ((Order)context).passarCartInfo();
                    }
                });
            }
        });

        holder.diminuir.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                int count= Integer.parseInt(String.valueOf(holder.itemQuantityTv.getText()));
                if(count>1){
                    count -= 1;
                    holder.itemQuantityTv.setText("" + count);
                    String id = modelCartItem.getId();
                    String uid = modelCartItem.getUid();
                    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
                    DocumentReference docRef = fStore.collection("Cart "+uid).document(id);
                    Map<String,Object> edited = new HashMap<>();
                    edited.put("quantity",String. valueOf(count));
                    Double calculinho = Double.parseDouble(holder.itemPriceEachTv.getText().toString().replace(",",".").replace(finalCoin,""));
                    String maiscal = String. valueOf(count*calculinho);
                            edited.put("totalvalue",maiscal);
                    docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            ((Order)context).passarCartInfo();

                        }
                    });
                }
            }
        });
        holder.itemRemoveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                rootRef.collection("Cart "+uid).document(id)
                                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                cartItems.remove(position);
                                notifyItemChanged(position);
                                notifyDataSetChanged();

                                ((Order)context).passarCartInfo();

                                Toast.makeText(context, R.string.itemremovido, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }
    class HolderCartItem extends RecyclerView.ViewHolder {
        private final TextView itemTitleTv;
        private final TextView itemPriceEachTv;
        private final TextView itemQuantityTv;
        private final TextView itemRemoveTv;
        private final TextView brand;
        private final ImageView image;
        private final ImageView diminuir;
        private final ImageView aumentar;
        private Double finalPrice = 1.00;
        private Double v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15;

        public HolderCartItem(@NonNull View itemView) {
            super(itemView);
            brand = itemView.findViewById(R.id.textView25);
            image = itemView.findViewById(R.id.imageView20);
            itemTitleTv = itemView.findViewById(R.id.itemTitleTv);
            itemPriceEachTv = itemView.findViewById(R.id.itemPriceEachTv);
            itemQuantityTv = itemView.findViewById(R.id.itemQuantityTv);
            itemRemoveTv = itemView.findViewById(R.id.itemRemoveTv);
            diminuir = itemView.findViewById(R.id.diminuir);
            aumentar = itemView.findViewById(R.id.aumentar);

        }
    }
}
