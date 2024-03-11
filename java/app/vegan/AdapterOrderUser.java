package app.vegan;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AdapterOrderUser extends RecyclerView.Adapter<AdapterOrderUser.HolderOrderUser> {

    private final Context context;
    private final ArrayList<ModelOrderUser> orderUserList;

    public AdapterOrderUser(Context context, ArrayList<ModelOrderUser> orderUserList) {
        this.context = context;
        this.orderUserList = orderUserList;
    }

    @NonNull
    @Override
    public HolderOrderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.row_order_user, parent, false);
       return new HolderOrderUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderOrderUser holder, int position) {
        ModelOrderUser modelOrderUser = orderUserList.get(position);
        String orderId = modelOrderUser.getOrderId();
        String orderBy = modelOrderUser.getOrderBy();
        String orderStatus = modelOrderUser.getOrderStatus();
        String orderTime = modelOrderUser.getOrderTime();
        String orderTo = modelOrderUser.getOrderTo();
        String total = modelOrderUser.getTotal();
        String troco = modelOrderUser.getTroco();
        String obs = modelOrderUser.getObs();
        String formadepagamento = modelOrderUser.getFormadepagamento();
        String fidelidade = modelOrderUser.getFidelidade();
        String cpm = modelOrderUser.getCpm();
        String codentrega = modelOrderUser.getCodentrega();
        String schedule = modelOrderUser.getSchedule();
        String deliveryFee = modelOrderUser.getDeliveryFee();

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        rootRef.collection("Adm's").document(orderTo).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String fName = document.getString("fName");
                        String brandIcon = document.getString("shopIcon");

                        holder.brand.setText(fName);
                        try {
                            Picasso.get().load(brandIcon).placeholder(R.drawable.loading).into(holder.icon);
                        } catch (Exception e) {
                            if (holder.icon == null)
                                return;
                            else
                                holder.icon.setImageResource(R.drawable.error);
                        }

                    }
                }
            }
        });

        long l = Long.parseLong(orderId);

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(l);
        String date = DateFormat.format("dd/MM/yy", cal).toString();
        String time = DateFormat.format("HH:mm", cal).toString();

        holder.dateTv.setText(date);
        holder.timeTv.setText(time);

        holder.amountTv.setText(total);
        holder.statusTv.setText(orderStatus);
        holder.orderIdTv.setText(("ID: "+orderId));

        if(Objects.equals(orderStatus, context.getResources().getString(R.string.emproducao))) {
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        } else if(Objects.equals(orderStatus, context.getResources().getString(R.string.emanalise))) {
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.blue));
        } else if(Objects.equals(orderStatus, context.getResources().getString(R.string.cancelado))) {
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.red));
        }else if(Objects.equals(orderStatus, "Recebido")) {
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.green));
        }else if(Objects.equals(orderStatus, "Saiu Para Entrega")) {
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.yellow));
        }else if(Objects.equals(orderStatus, "Concluído")) {
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.green));
        }


        holder.clorderuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Espere.class);
                 intent.putExtra("orderId", orderId);
                intent.putExtra("orderBy", orderBy);
                intent.putExtra("userID", orderBy);
                intent.putExtra("price", total);
                intent.putExtra("status", orderStatus);
                intent.putExtra("time", orderTime);
                intent.putExtra("shopUid", orderTo);
                intent.putExtra("troco", troco);
                intent.putExtra("orderTo", orderTo);
                intent.putExtra("pagamento", formadepagamento);
                intent.putExtra("cpm", cpm);
                intent.putExtra("codentrega", codentrega);
                intent.putExtra("fidelidade", fidelidade);
                intent.putExtra("obs", obs);
                intent.putExtra("schedule", schedule);
                intent.putExtra("deliveryFee", deliveryFee);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (orderUserList == null)
            return 0;
        else
            return orderUserList.size();
    }

    class HolderOrderUser extends RecyclerView.ViewHolder {
        private final ShapeableImageView icon;
        private final TextView orderIdTv;
        private final TextView dateTv;
        private final TextView amountTv;
        private final TextView statusTv;
        private final TextView brand;
        private final TextView timeTv;
        private final ConstraintLayout clorderuser;

        public HolderOrderUser(@NonNull View itemView) {
            super(itemView);
            timeTv = itemView.findViewById(R.id.timeTv);
            icon = itemView.findViewById(R.id.imageView21);
            brand = itemView.findViewById(R.id.brand);
            clorderuser = itemView.findViewById(R.id.clorderuser);
            orderIdTv = itemView.findViewById(R.id.orderIdTv);
            dateTv = itemView.findViewById(R.id.dateTv);
            amountTv = itemView.findViewById(R.id.amountTv);
            statusTv = itemView.findViewById(R.id.statusTv);
        }
    }
}
