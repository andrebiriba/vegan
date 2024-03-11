package app.vegan;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AdapterCategoriesMoreItens extends RecyclerView.Adapter<AdapterCategoriesMoreItens.ViewHolder> {

    private final Context context;
    public ArrayList<ModelProduct> productList, filterList;
    String size;
    String adc1, adc2, adc3, adc4, adc5, adc6, adc7, adc8, adc9, adc10, adc11, adc12, adc13, adc14, adc15;
    String va1, va2, va3, va4, va5, va6, va7, va8, va9, va10, va11, va12, va13, va14, va15;
    private Double v1, v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15;

    public AdapterCategoriesMoreItens(Context context, ArrayList<ModelProduct> productList) {
        this.context = context;
        this.productList = productList;
        this.filterList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_more_itens, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelProduct modelProduct = productList.get(position);
        String description = modelProduct.getProductDescription();
        String discountAvailable = modelProduct.getDiscountAvailable();
        String discountNote = modelProduct.getDiscountNote();
        String discountPrice = modelProduct.getDiscountPrice();
        String productCategory = modelProduct.getProductCategory();
        String productIcon = modelProduct.getProductIcon();
        String productTitle = modelProduct.getProductTitle();
        String productId = modelProduct.getProductId();
        String originalPrice = modelProduct.getOriginalPrice();
         holder.sizepp = modelProduct.getSizepp();
        holder.sizep = modelProduct.getSizep();
        holder.sizem = modelProduct.getSizem();
        holder.sizeg = modelProduct.getSizeg();
        holder.sizegg = modelProduct.getSizegg();
        holder.sizeggg = modelProduct.getSizeggg();
        String productCountry = modelProduct.getProductCountry();
        String indisponivel = modelProduct.getIndisponivel();

        if(Objects.equals(productCountry, "Brasil")){
            holder.discountedPriceTv.setText("R$");
            holder.originalPriceTv.setText("R$");
        }
        if(Objects.equals(productCountry, "Estados Unidos da América")){
            holder.discountedPriceTv.setText("US$");
            holder.originalPriceTv.setText("US$");
        }
        if(Objects.equals(productCountry, "Inglaterra")){
            holder.discountedPriceTv.setText("£");
            holder.originalPriceTv.setText("£");
        }
        if(Objects.equals(productCountry, "Alemanha")){
            holder.discountedPriceTv.setText("€");
            holder.originalPriceTv.setText("€");
        }
        if(Objects.equals(productCountry, "França")){
            holder.discountedPriceTv.setText("€");
            holder.originalPriceTv.setText("€");
        }
        if(Objects.equals(productCountry, context.getResources().getString(R.string.canada))){
            holder.discountedPriceTv.setText("C$");
        }
        if(Objects.equals(productCountry, "Portugal")){
            holder.discountedPriceTv.setText("€");
            holder.originalPriceTv.setText("€");
        }
        if(Objects.equals(productCountry, "Espanha")){
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

        if(Objects.equals(productCountry, "Brasil")){
            holder.finalcoin = "R$";
        }
        if(Objects.equals(productCountry, "Estados Unidos da América")){
            holder.finalcoin = "US$";
        }
        if(Objects.equals(productCountry, "Inglaterra")){
            holder.finalcoin = "£";
        }
        if(Objects.equals(productCountry, "Alemanha")){
            holder.finalcoin = "€";
        }
        if(Objects.equals(productCountry, "França")){
            holder.finalcoin = "€";
        }
        if(Objects.equals(productCountry, "Portugal")){
            holder.finalcoin = "€";
        }
        if(Objects.equals(productCountry, "Espanha")){
            holder.finalcoin = "€";
        }


        holder.titleTv.setText(productTitle);
        holder.descriptionTv.setText(description);

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
        holder.addToCartIv.setOnClickListener(v -> {
            if(!Objects.equals(indisponivel, "true")) {
                FirebaseAuth fAuth;
                FirebaseFirestore fStore;
                fStore = FirebaseFirestore.getInstance();
                fAuth = FirebaseAuth.getInstance();
                DocumentReference docRef = fStore.collection(productCategory).document(productId);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                holder.sizeggg = document.getString("sizeggg");
                                holder.sizegg = document.getString("sizegg");
                                holder.sizeg = document.getString("sizeg");
                                holder.sizem = document.getString("sizem");
                                holder.sizep = document.getString("sizep");
                                holder.sizepp = document.getString("sizepp");
                                String personalite = document.getString("sizePersonalized");

                                if (!Objects.equals(holder.sizeg, "true") && !Objects.equals(holder.sizegg, "true")
                                        && !Objects.equals(holder.sizeggg, "true") && !Objects.equals(holder.sizem, "true")
                                        && !Objects.equals(holder.sizep, "true") && !Objects.equals(holder.sizepp, "true") && !Objects.equals(personalite, "true")) {
                                    testAditionals(productCategory, productId, modelProduct);
                                } else {
                                    showSizeDialog(modelProduct);
                                }

                            }
                        }
                    }
                });

            }
            else {
                Toast.makeText(context.getApplicationContext(), "Item indisponível temporariamente", Toast.LENGTH_SHORT).show();

            }

        });



    }


    private void testAditionals(String productCategory, String productId, ModelProduct modelProduct) {
        FirebaseAuth fAuth;
        FirebaseFirestore fStore;
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        String userID = fAuth.getUid();

        DocumentReference docRef = fStore.collection(productCategory).document(productId);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String adicional001 = document.getString("adicional001");

                            if(Objects.equals(adicional001, "false") || Objects.equals(adicional001, "")
                                    || adicional001 == null){
                                showQuantityDialog(modelProduct);
                            }
                            else {
                                showAditionalsDialog(modelProduct);
                            }

                        }
                    }
                }
            });



    }

    private void showAditionalsDialog(ModelProduct modelProduct) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_adicional, null);
        Button continueBtn = view.findViewById(R.id.continueBtn);
        ShapeableImageView productIv = view.findViewById(R.id.productIv);
        TextView titleTv = view.findViewById(R.id.titleTv);
        TextView discountedNoteTv = view.findViewById(R.id.discountedNoteTv);
        CheckBox ad1 = view.findViewById(R.id.checkbox);
        CheckBox ad2 = view.findViewById(R.id.checkbox2);
        CheckBox ad3 = view.findViewById(R.id.checkbox3);
        CheckBox ad4 = view.findViewById(R.id.checkbox4);
        CheckBox ad5 = view.findViewById(R.id.checkbox5);
        CheckBox ad6 = view.findViewById(R.id.checkbox6);
        CheckBox ad7 = view.findViewById(R.id.checkbox7);
        CheckBox ad8 = view.findViewById(R.id.checkbox8);
        CheckBox ad9 = view.findViewById(R.id.checkbox9);
        CheckBox ad10 = view.findViewById(R.id.checkbox10);
        CheckBox ad11 = view.findViewById(R.id.checkbox11);
        CheckBox ad12 = view.findViewById(R.id.checkbox12);
        CheckBox ad13 = view.findViewById(R.id.checkbox13);
        CheckBox ad14 = view.findViewById(R.id.checkbox14);
        CheckBox ad15 = view.findViewById(R.id.checkbox15);
        TextView val1 = view.findViewById(R.id.va1);
        TextView val2 = view.findViewById(R.id.va2);
        TextView val3 = view.findViewById(R.id.va3);
        TextView val4 = view.findViewById(R.id.va4);
        TextView val5 = view.findViewById(R.id.va5);
        TextView val6 = view.findViewById(R.id.va6);
        TextView val7 = view.findViewById(R.id.va7);
        TextView val8 = view.findViewById(R.id.va8);
        TextView val9 = view.findViewById(R.id.va9);
        TextView val10 = view.findViewById(R.id.va10);
        TextView val11 = view.findViewById(R.id.va11);
        TextView val12 = view.findViewById(R.id.va12);
        TextView val13 = view.findViewById(R.id.va13);
        TextView val14 = view.findViewById(R.id.va14);
        TextView val15 = view.findViewById(R.id.va15);
        String productTitle = modelProduct.getProductTitle();
        String image = modelProduct.getProductIcon();
        String discountNote = modelProduct.getDiscountNote();

        titleTv.setText(productTitle);
        if(!Objects.equals(discountNote, "")){
            discountedNoteTv.setText(discountNote+"% OFF");
            discountedNoteTv.setVisibility(VISIBLE);}

        try {
            Picasso.get().load(image).placeholder(R.drawable.loading).into(productIv);
        }
        catch(Exception e){
            productIv.setImageResource(R.drawable.error);
        }
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
        String alor1 = modelProduct.getValor1();
        String alor2 = modelProduct.getValor2();
        String alor3 = modelProduct.getValor3();
        String alor4 = modelProduct.getValor4();
        String alor5 = modelProduct.getValor5();
        String alor6 = modelProduct.getValor6();
        String alor7 = modelProduct.getValor7();
        String alor8 = modelProduct.getValor8();
        String alor9 = modelProduct.getValor9();
        String alor10 = modelProduct.getValor10();
        String alor11 = modelProduct.getValor11();
        String alor12 = modelProduct.getValor12();
        String alor13 = modelProduct.getValor13();
        String alor14 = modelProduct.getValor14();
        String alor15 = modelProduct.getValor15();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);

        if(!Objects.equals(adicional1, null)){
            ad1.setVisibility(VISIBLE);
            val1.setVisibility(VISIBLE);
            ad1.setText(adicional1);
            val1.setText("("+alor1+")");
        }
        if(!Objects.equals(adicional2, null)){
            ad2.setVisibility(VISIBLE);
            val2.setVisibility(VISIBLE);
            ad2.setText(adicional2);
            val2.setText("("+alor2+")");
        }
        if(!Objects.equals(adicional3, null)){
            ad3.setVisibility(VISIBLE);
            val3.setVisibility(VISIBLE);
            ad3.setText(adicional3);
            val3.setText("("+alor3+")");
        }
        if(!Objects.equals(adicional4, null)){
            ad4.setVisibility(VISIBLE);
            val4.setVisibility(VISIBLE);
            ad4.setText(adicional4);
            val4.setText("("+alor4+")");
        }
        if(!Objects.equals(adicional5, null)){
            ad5.setVisibility(VISIBLE);
            val5.setVisibility(VISIBLE);
            ad5.setText(adicional5);
            val5.setText("("+alor5+")");
        }
        if(!Objects.equals(adicional6, null)){
            ad6.setVisibility(VISIBLE);
            val6.setVisibility(VISIBLE);
            ad6.setText(adicional6);
            val6.setText("("+alor6+")");
        }
        if(!Objects.equals(adicional7, null)){
            ad7.setVisibility(VISIBLE);
            val7.setVisibility(VISIBLE);
            ad7.setText(adicional7);
            val7.setText("("+alor7+")");
        }
        if(!Objects.equals(adicional8, null)){
            ad8.setVisibility(VISIBLE);
            val8.setVisibility(VISIBLE);
            ad8.setText(adicional8);
            val8.setText("("+alor8+")");
        }
        if(!Objects.equals(adicional9, null)){
            ad9.setVisibility(VISIBLE);
            val9.setVisibility(VISIBLE);
            ad9.setText(adicional9);
            val9.setText("("+alor9+")");
        }
        if(!Objects.equals(adicional10, null)){
            ad10.setVisibility(VISIBLE);
            val10.setVisibility(VISIBLE);
            ad10.setText(adicional10);
            val10.setText("("+alor10+")");
        }
        if(!Objects.equals(adicional11, null)){
            ad11.setVisibility(VISIBLE);
            val11.setVisibility(VISIBLE);
            ad11.setText(adicional11);
            val11.setText("("+alor11+")");
        }
        if(!Objects.equals(adicional12, null)){
            ad12.setVisibility(VISIBLE);
            val12.setVisibility(VISIBLE);
            ad12.setText(adicional12);
            val12.setText("("+alor12+")");
        }
        if(!Objects.equals(adicional13, null)){
            ad13.setVisibility(VISIBLE);
            val13.setVisibility(VISIBLE);
            ad13.setText(adicional13);
            val13.setText("("+alor13+")");
        }
        if(!Objects.equals(adicional14, null)){
            ad14.setVisibility(VISIBLE);
            val14.setVisibility(VISIBLE);
            ad14.setText(adicional14);
            val14.setText("("+alor14+")");
        }
        if(!Objects.equals(adicional15, null)){
            ad15.setVisibility(VISIBLE);
            val15.setVisibility(VISIBLE);
            ad15.setText(adicional15);
            val15.setText("("+alor15+")");
        }


        final AlertDialog dialog = builder.create();
        dialog.show();

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ad1.isChecked()){
                    adc1 = ad1.getText().toString();
                    va1= val1.getText().toString();
                }
                if(ad2.isChecked()){
                    adc2 = ad2.getText().toString();
                    va2= val2.getText().toString();
                }
                if(ad3.isChecked()){
                    adc3 = ad3.getText().toString();
                    va3= val3.getText().toString();
                }
                if(ad4.isChecked()){
                    adc4 = ad4.getText().toString();
                    va4= val4.getText().toString();
                }
                if(ad5.isChecked()){
                    adc5 = ad5.getText().toString();
                    va5= val5.getText().toString();
                }
                if(ad6.isChecked()){
                    adc6 = ad6.getText().toString();
                    va6= val6.getText().toString();
                }
                if(ad7.isChecked()){
                    adc7 = ad7.getText().toString();
                    va7= val7.getText().toString();
                }
                if(ad8.isChecked()){
                    adc8 = ad8.getText().toString();
                    va8= val8.getText().toString();
                }
                if(ad9.isChecked()){
                    adc9 = ad9.getText().toString();
                    va9= val9.getText().toString();
                }
                if(ad10.isChecked()){
                    adc10 = ad10.getText().toString();
                    va10= val10.getText().toString();
                }
                if(ad11.isChecked()){
                    adc11 = ad11.getText().toString();
                    va11= val11.getText().toString();
                }
                if(ad12.isChecked()){
                    adc12 = ad12.getText().toString();
                    va12= val12.getText().toString();
                }
                if(ad13.isChecked()){
                    adc13 = ad13.getText().toString();
                    va13= val13.getText().toString();
                }
                if(ad14.isChecked()){
                    adc14 = ad14.getText().toString();
                    va14= val14.getText().toString();
                }
                if(ad15.isChecked()){
                    adc15 = ad15.getText().toString();
                    va15= val15.getText().toString();
                }

                showQuantityDialog(modelProduct);
                dialog.dismiss();
            }
        });
    }


    private void showSizeDialog(ModelProduct modelProduct) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_size, null);
        ShapeableImageView productIv = view.findViewById(R.id.productIv);
        TextView titleTv = view.findViewById(R.id.titleTv);
        TextView discountedNoteTv = view.findViewById(R.id.discountedNoteTv);
        Button continueBtn = view.findViewById(R.id.continueBtn);
        ImageView incrementBtn = view.findViewById(R.id.incrementBtn);
        ImageView decrementBtn = view.findViewById(R.id.decrementBtn);
        TextView quantityTv = view.findViewById(R.id.quantityTv);

        String productTitle = modelProduct.getProductTitle();
        String image = modelProduct.getProductIcon();
        String originalPrice = modelProduct.getOriginalPrice();
        String productId = modelProduct.getProductId();
        String discountNote = modelProduct.getDiscountNote();
        String productCategory = modelProduct.getProductCategory();

        titleTv.setText(productTitle);
        if(!Objects.equals(discountNote, "")){
            discountedNoteTv.setText(discountNote+"% OFF");
            discountedNoteTv.setVisibility(VISIBLE);}

        try {
            Picasso.get().load(image).placeholder(R.drawable.loading).into(productIv);
        }
        catch(Exception e){
            productIv.setImageResource(R.drawable.error);
        }
        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();

        String sizepp = modelProduct.getSizepp();
        String sizep = modelProduct.getSizep();
        String sizem = modelProduct.getSizem();
        String sizeg = modelProduct.getSizeg();
        String sizegg = modelProduct.getSizegg();
        String sizeggg = modelProduct.getSizeggg();
        String sizePersonalized = modelProduct.getSizePersonalized();

                        if(Objects.equals(sizeggg, "true")){
                            quantityTv.setText("GGG");}
                        if(Objects.equals(sizegg, "true")){
                            quantityTv.setText("GG");}
                        if(Objects.equals(sizeg, "true")){
                            quantityTv.setText("G");}
                        if(Objects.equals(sizem, "true")){
                            quantityTv.setText("M");}
                        if(Objects.equals(sizep, "true")){
                            quantityTv.setText("P");}
                        if(Objects.equals(sizepp, "true")){
                            quantityTv.setText("PP");}

                        if(!Objects.equals(sizePersonalized, "true")){
                            incrementBtn.setVisibility(GONE);
                            decrementBtn.setVisibility(GONE);
                            titleTv.setText(context.getResources().getString(R.string.digiteotamanho));
                            quantityTv.setText(sizePersonalized);}



        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);

        incrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sizep = modelProduct.getSizep();
                String sizem = modelProduct.getSizem();
                String sizeg = modelProduct.getSizeg();
                String sizegg = modelProduct.getSizegg();
                String sizeggg = modelProduct.getSizeggg();
                switch (quantityTv.getText().toString()) {


                    case "PP":

                        if (Objects.equals(sizeggg, "true")) {
                            quantityTv.setText("GGG");
                        }
                        if (Objects.equals(sizegg, "true")) {
                            quantityTv.setText("GG");
                        }
                        if (Objects.equals(sizeg, "true")) {
                            quantityTv.setText("G");
                        }
                        if (Objects.equals(sizem, "true")) {
                            quantityTv.setText("M");
                        }
                        if (Objects.equals(sizep, "true")) {
                            quantityTv.setText("P");
                        }
                        break;

                    case "P":

                        if (Objects.equals(sizeggg, "true")) {
                            quantityTv.setText("GGG");
                        }
                        if (Objects.equals(sizegg, "true")) {
                            quantityTv.setText("GG");
                        }
                        if (Objects.equals(sizeg, "true")) {
                            quantityTv.setText("G");
                        }
                        if (Objects.equals(sizem, "true")) {
                            quantityTv.setText("M");
                        }
                        break;


                    case "M":
                        if (Objects.equals(sizeggg, "true")) {
                            quantityTv.setText("GGG");
                        }
                        if (Objects.equals(sizegg, "true")) {
                            quantityTv.setText("GG");
                        }
                        if (Objects.equals(sizeg, "true")) {
                            quantityTv.setText("G");
                        }
                        break;


                    case "G":
                        if (Objects.equals(sizeggg, "true")) {
                            quantityTv.setText("GGG");
                        }
                        if (Objects.equals(sizegg, "true")) {
                            quantityTv.setText("GG");
                        }
                        break;

                    case "GG":
                        if (Objects.equals(sizeggg, "true")) {
                            quantityTv.setText("GGG");
                        }
                        break;
                }
            }});
        decrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sizepp = modelProduct.getSizepp();
                String sizep = modelProduct.getSizep();
                String sizem = modelProduct.getSizem();
                String sizeg = modelProduct.getSizeg();
                String sizegg = modelProduct.getSizegg();
                switch (quantityTv.getText().toString()) {
                    case "GGG":

                        if (Objects.equals(sizepp, "true")) {
                            quantityTv.setText("PP");
                        }
                        if (Objects.equals(sizep, "true")) {
                            quantityTv.setText("P");
                        }
                        if (Objects.equals(sizem, "true")) {
                            quantityTv.setText("M");
                        }
                        if (Objects.equals(sizeg, "true")) {
                            quantityTv.setText("G");
                        }
                        if (Objects.equals(sizegg, "true")) {
                            quantityTv.setText("GG");
                        }
                        break;


                    case "GG":
                        if (Objects.equals(sizepp, "true")) {
                            quantityTv.setText("PP");
                        }
                        if (Objects.equals(sizep, "true")) {
                            quantityTv.setText("P");
                        }
                        if (Objects.equals(sizem, "true")) {
                            quantityTv.setText("M");
                        }
                        if (Objects.equals(sizeg, "true")) {
                            quantityTv.setText("G");
                        }
                        break;

                    case "G":
                        if (Objects.equals(sizepp, "true")) {
                            quantityTv.setText("PP");
                        }
                        if (Objects.equals(sizep, "true")) {
                            quantityTv.setText("P");
                        }
                        if (Objects.equals(sizem, "true")) {
                            quantityTv.setText("M");
                        }
                        break;

                    case "M":
                        if (Objects.equals(sizepp, "true")) {
                            quantityTv.setText("PP");
                        }
                        if (Objects.equals(sizep, "true")) {
                            quantityTv.setText("P");
                        }
                        break;
                    case "P":
                        if (Objects.equals(sizepp, "true")) {
                            quantityTv.setText("PP");
                        }
                        break;
                }
            }});

        final AlertDialog dialog = builder.create();
        dialog.show();

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                size=quantityTv.getText().toString();
                testAditionals(productCategory, productId, modelProduct);
                dialog.dismiss();
            }
        });
    }


    private void showQuantityDialog(ModelProduct modelProduct) {
    View view = LayoutInflater.from(context).inflate(R.layout.dialog_quantity, null);
    ShapeableImageView productIv = view.findViewById(R.id.productIv);
    final TextView titleTv = view.findViewById(R.id.titleTv);
    final TextView originalPriceTv = view.findViewById(R.id.originalPriceTv);
    TextView priceDiscountedTv = view.findViewById(R.id.priceDiscountedTv);
    TextView discountedNoteTv = view.findViewById(R.id.discountedNoteTv);
    TextView finalPriceTv = view.findViewById(R.id.finalPriceTv);
    Button continueBtn = view.findViewById(R.id.continueBtn);
    continueBtn.setBackgroundResource(R.drawable.gradienttoppurple);
    ImageButton decrementBtn = view.findViewById(R.id.decrementBtn);
    ImageButton incrementBtn = view.findViewById(R.id.incrementBtn);
    TextView quantityTv = view.findViewById(R.id.quantityTv);
    String productTitle = modelProduct.getProductTitle();
    String image = modelProduct.getProductIcon();
    String productIcon = modelProduct.getProductIcon();
    String originalPrice = modelProduct.getOriginalPrice();
    String description = modelProduct.getProductDescription();
    String uid = modelProduct.getUid();
    String productId = modelProduct.getProductId();
    String brandName = modelProduct.getProductBrand();
    String productType = modelProduct.getProductType();
    String discountPrice = modelProduct.getDiscountPrice();
    String discountNote = modelProduct.getDiscountNote();
    String priceOriginal = originalPrice;
    String productCountry = modelProduct.getProductCountry();

        String finalcoin = "";

        v1=0.0;
        v2=0.0;
        v3=0.0;
        v4=0.0;
        v5=0.0;
        v6=0.0;
        v7=0.0;
        v8=0.0;
        v9=0.0;
        v10=0.0;
        v11=0.0;
        v12=0.0;
        v13=0.0;
        v14=0.0;
        v15=0.0;

        if(adc1!=null) {
            v1 = Double.parseDouble(va1.replace(",", ".").replace("(", "").replace(")", ""));
        }
        if(adc2!=null){
            v2 = Double.parseDouble(va2.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc3!=null){
            v3 = Double.parseDouble(va3.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc4!=null){
            v4 = Double.parseDouble(va4.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc5!=null){
            v5 = Double.parseDouble(va5.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc6!=null){
            v6 = Double.parseDouble(va6.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc7!=null){
            v7 = Double.parseDouble(va7.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc8!=null){
            v8 = Double.parseDouble(va8.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc9!=null){
            v9 = Double.parseDouble(va9.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc10!=null){
            v10 = Double.parseDouble(va10.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc11!=null){
            v11 = Double.parseDouble(va11.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc12!=null){
            v12 = Double.parseDouble(va12.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc13!=null){
            v13 = Double.parseDouble(va13.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc14!=null){
            v14 = Double.parseDouble(va14.replace(",",".").replace("(","").replace(")",""));
        }
        if(adc15!=null){
            v15 = Double.parseDouble(va15.replace(",",".").replace("(","").replace(")",""));
        }


        final String price;

    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setView(view);

    try {
        Picasso.get().load(image).placeholder(R.drawable.vegangreenlogo).into(productIv);
    }
    catch (Exception e) {
        productIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.veganlogodraw ));
    }
    titleTv.setText(""+productTitle);

        if(Objects.equals(productCountry, "Brasil")){
            originalPriceTv.setText("R$");
            priceDiscountedTv.setText("R$");
            finalcoin = "R$";
        }
        if(Objects.equals(productCountry, "Estados Unidos da América")){
            originalPriceTv.setText("US$");
            priceDiscountedTv.setText("US$");
            finalcoin = "US$";
        }
        if(Objects.equals(productCountry, "Inglaterra")){
            originalPriceTv.setText("£");
            priceDiscountedTv.setText("£");
            finalcoin = "£";
        }
        if(Objects.equals(productCountry, "Alemanha")){
            originalPriceTv.setText("€");
            priceDiscountedTv.setText("€");
            finalcoin = "€";
        }
        if(Objects.equals(productCountry, "França")){
            originalPriceTv.setText("€");
            priceDiscountedTv.setText("€");
            finalcoin = "€";
        }
        if(Objects.equals(productCountry, "Portugal")){
            originalPriceTv.setText("€");
            priceDiscountedTv.setText("€");
            finalcoin = "€";
        }
        if(Objects.equals(productCountry, "Espanha")){
            originalPriceTv.setText("€");
            priceDiscountedTv.setText("€");
            finalcoin = "€";
        }


    if(!Objects.equals(discountNote, "")){
        discountedNoteTv.setVisibility(View.VISIBLE);
        discountedNoteTv.setText(discountNote+"% OFF");
        originalPriceTv.setVisibility(View.VISIBLE);

        Double totaladicionais = v1+v2+v3+v4+v5+v6+v7+v8+v9+v10+v11+v12+v13+v14+v15;
        Double calculo = Double.parseDouble(discountPrice.replace(",",".").replace(finalcoin,""));
        Double temp = totaladicionais+calculo;
        String result = String.valueOf(temp).replace(".",",");

        price=discountPrice;
        String x11 =  finalcoin+priceOriginal.replace(".",",");
        originalPriceTv.setText(x11);
        String x33 =  finalcoin+discountPrice.replace(".",",");
        priceDiscountedTv.setText(x33);
        originalPriceTv.setPaintFlags(originalPriceTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        finalPriceTv.setText(finalcoin+result.replace(".",","));
    }
    else {
        price=originalPrice;
        Double totaladicionais = v1+v2+v3+v4+v5+v6+v7+v8+v9+v10+v11+v12+v13+v14+v15;
        Double calculo = Double.parseDouble(priceOriginal.replace(",",".").replace(finalcoin,""));
        Double temp = totaladicionais+calculo;
        String result = String.valueOf(temp).replace(".",",");

        discountedNoteTv.setVisibility(View.GONE);
        originalPriceTv.setVisibility(View.GONE);
        String x22 =  finalcoin+priceOriginal.replace(".",",");
        priceDiscountedTv.setText(x22);
        finalPriceTv.setText(finalcoin+result.replace(".",","));
    }

        String finalCoin = finalcoin;
        String novirgula;
        novirgula =finalcoin+finalPriceTv.getText().toString().replace(",",".").replace(finalcoin,"");
        final Double[] finalPrice = {Double.parseDouble(finalPriceTv.getText().toString().replace(",", ".").replace(finalCoin, ""))};

        incrementBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int count= Integer.parseInt(String.valueOf(quantityTv.getText()));
            count++;

            finalPrice[0] = finalPrice[0] + Double.parseDouble(novirgula.replace(finalCoin,""));
            String tempo0 = finalCoin + String.valueOf(finalPrice[0]).replace(".",",");
            finalPriceTv.setText(tempo0);


            quantityTv.setText(String.valueOf(count));

        }
    });
    decrementBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int count= Integer.parseInt(String.valueOf(quantityTv.getText()));
            if(count>1){
                count -= 1;
                Double finalPric = Double.parseDouble(finalPriceTv.getText().toString().replace(",",".").replace(finalCoin,""));
                quantityTv.setText(String.valueOf(count));
                finalPric = finalPric - Double.parseDouble(novirgula.replace(finalCoin,""));
                String tempo1 = finalCoin + String.valueOf(finalPric).replace(".",",");
                finalPriceTv.setText(tempo1);
            }
        }
    });

    final AlertDialog dialog = builder.create();
    dialog.show();
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    fStore = FirebaseFirestore.getInstance();
    fAuth = FirebaseAuth.getInstance();
    String userID = fAuth.getUid();

    continueBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DocumentReference docRef = fStore.collection("Cart "+userID).document(productId);
            Map<String,Object> cart = new HashMap<>();
            cart.put("uid",userID);
            cart.put("id",productId);
            cart.put("name",productTitle);
            cart.put("shopUid",uid);
            cart.put("photo",productIcon);
            cart.put("priceOriginal",originalPrice);
            cart.put("productCountry", productCountry);
            cart.put("price",price);
            cart.put("brand",brandName);
            cart.put("descriptionProduct",description);
            cart.put("quantity",quantityTv.getText());
            cart.put("type",productType);
            if(size!=null){
                cart.put("size",size);}
            if(adc1!=null){
                v1 = Double.parseDouble(va1.replace(",",".").replace("(","").replace(")",""));
                cart.put("adc1",adc1);
                cart.put("va1",va1);}
            if(adc2!=null){
                v2 = Double.parseDouble(va2.replace(",",".").replace("(","").replace(")",""));
                cart.put("adc2",adc2);
                cart.put("va2",va2);}
            if(adc3!=null){
                v3 = Double.parseDouble(va3.replace(",",".").replace("(","").replace(")",""));
                cart.put("adc3",adc3);
                cart.put("va3",va3);}
            if(adc4!=null){
                v4 = Double.parseDouble(va4.replace(",",".").replace("(","").replace(")",""));
                cart.put("adc4",adc4);
                cart.put("va4",va4);}
            if(adc5!=null){
                v5 = Double.parseDouble(va5.replace(",",".").replace("(","").replace(")",""));
                cart.put("adc5",adc5);
                cart.put("va5",va5);}
            if(adc6!=null){
                v6 = Double.parseDouble(va6.replace(",",".").replace("(","").replace(")",""));
                cart.put("adc6",adc6);
                cart.put("va6",va6);}
            if(adc7!=null){
                v7 = Double.parseDouble(va7.replace(",",".").replace("(","").replace(")",""));
                cart.put("adc7",adc7);
                cart.put("va7",va7);}
            if(adc8!=null){
                v8 = Double.parseDouble(va8.replace(",",".").replace("(","").replace(")",""));
                cart.put("adc8",adc8);
                cart.put("va8",va8);}
            if(adc9!=null){
                v9 = Double.parseDouble(va9.replace(",",".").replace("(","").replace(")",""));
                cart.put("adc9",adc9);
                cart.put("va9",va9);}
            if(adc10!=null){
                v10 = Double.parseDouble(va10.replace(",",".").replace("(","").replace(")",""));
                cart.put("adc10",adc10);
                cart.put("va10",va10);}
            if(adc11!=null){
                v11 = Double.parseDouble(va11.replace(",",".").replace("(","").replace(")",""));
                cart.put("adc11",adc11);
                cart.put("va11",va11);}
            if(adc12!=null){
                v12 = Double.parseDouble(va12.replace(",",".").replace("(","").replace(")",""));
                cart.put("adc12",adc12);
                cart.put("va12",va12);}
            if(adc13!=null){
                v13 = Double.parseDouble(va13.replace(",",".").replace("(","").replace(")",""));
                cart.put("adc13",adc13);
                cart.put("va13",va13);}
            if(adc14!=null){
                v14 = Double.parseDouble(va14.replace(",",".").replace("(","").replace(")",""));
                cart.put("adc14",adc14);
                cart.put("va14",va14);}
            if(adc15!=null){
                v15 = Double.parseDouble(va15.replace(",",".").replace("(","").replace(")",""));
                cart.put("adc15",adc15);
                cart.put("va15",va15);}
            Double totaladicionais = v1+v2+v3+v4+v5+v6+v7+v8+v9+v10+v11+v12+v13+v14+v15;

            Double calculo = Double.parseDouble(price.replace(",",".").replace(finalCoin,""));
            Double foi = calculo+totaladicionais;

            Double maiscalculo = Double.parseDouble((String) quantityTv.getText())*foi;
            cart.put("totalvalue",String.valueOf(maiscalculo));
            docRef.set(cart).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Intent intent = new Intent(view.getContext(), Order.class);
                    intent.putExtra("title", productTitle);
                    intent.putExtra("shopUid", uid);
                    intent.putExtra("photo", productIcon);
                    intent.putExtra("priceOriginal", originalPrice);
                    intent.putExtra("productCountry", productCountry);
                    intent.putExtra("price", price);
                    intent.putExtra("brand", brandName);
                    intent.putExtra("descriptionProduct", description);
                    intent.putExtra("quantity", quantityTv.getText());
                    intent.putExtra("type", productType);
                    intent.putExtra("productCountry", productCountry);
                    v.getContext().startActivity(intent);
                    dialog.dismiss();
                    ((Activity)context).finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    });
}
    @Override
    public int getItemCount() {
        if (productList == null)
            return 0;
        else
            return productList.size();    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView productIconIv, fire;
        private final ImageButton addToCartIv;
        private final TextView discountedNoteTv;
        private final TextView titleTv;
        private final TextView descriptionTv;
        private final TextView quantityTv;
        private final TextView discountedPriceTv;
        private TextView originalPriceTv;
        private String sizepp,sizep,sizem,sizeg,sizegg,sizeggg, finalcoin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fire = itemView.findViewById(R.id.fire);
            productIconIv = itemView.findViewById(R.id.iconSearchList);
            discountedNoteTv = itemView.findViewById(R.id.discountedNoteTv);
            titleTv = itemView.findViewById(R.id.titleIconSearchList);
            descriptionTv = itemView.findViewById(R.id.textView69);
            addToCartIv = itemView.findViewById(R.id.imageButtonLoveRow);
            quantityTv = itemView.findViewById(R.id.quantityTv);
            discountedPriceTv = itemView.findViewById(R.id.priceSearchList);
            originalPriceTv = itemView.findViewById(R.id.priceSearchList);
        }
    }
}
