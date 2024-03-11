package app.vegan;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AdapterOrderShop extends RecyclerView.Adapter<AdapterOrderShop.HolderOrderShop> implements Filterable {

    private final Context context;
    public ArrayList<ModelOrderShop> orderShopArrayList, filterList;
    private FilterOrderShop filter;

    public AdapterOrderShop(Context context, ArrayList<ModelOrderShop> orderShopArrayList) {
        this.context = context;
        this.orderShopArrayList = orderShopArrayList;
        this.filterList = orderShopArrayList;
    }

    @NonNull
    @Override
    public HolderOrderShop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_order_seller, parent, false);
        return new HolderOrderShop(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderOrderShop holder, int position) {
        ModelOrderShop modelOrderShop = orderShopArrayList.get(position);
        final String orderId = modelOrderShop.getOrderId();
        final String orderBy = modelOrderShop.getOrderBy();
        String orderStatus = modelOrderShop.getOrderStatus();
        String orderTime = modelOrderShop.getOrderTime();
        String total = modelOrderShop.getTotal();
        String cliente = modelOrderShop.getCliente();
        String troco = modelOrderShop.getTroco();
        String formadepagamento = modelOrderShop.getFormadepagamento();
        String cpm = modelOrderShop.getCpm();
        String tipoDeCompra = modelOrderShop.getTipoDeCompra();
        String codentrega = modelOrderShop.getCodentrega();
        String schedule = modelOrderShop.getSchedule();
        String obs = modelOrderShop.getObs();

        String ana =  context.getResources().getString(R.string.emanalise);
        String emp = context.getResources().getString(R.string.emproducao);
        String sai = context.getResources().getString(R.string.saiuparaen);
        String con = context.getResources().getString(R.string.concluido);
        String can = context.getResources().getString(R.string.cancelado);

        if(Objects.equals(orderStatus, ana)) {
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.blue));
        }
        if(Objects.equals(orderStatus, emp)) {
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }
        if(Objects.equals(orderStatus, sai)) {
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.yellow));
        }
        if(Objects.equals(orderStatus, con)) {
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.green));
        }

        if(Objects.equals(orderStatus, "Recebido")) {
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.green));
        }

        if(Objects.equals(orderStatus, can)) {
            holder.statusTv.setTextColor(context.getResources().getColor(R.color.red));
        }

        holder.amountTv.setText(total);
        holder.statusTv.setText(orderStatus);
        holder.orderIdTv.setText(("ID: "+orderId));
        holder.emailTv.setText(cliente);

        long l = Long.parseLong(orderId);

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(l);
        String date = DateFormat.format("dd/MM/yy", cal).toString();
        String hour = DateFormat.format("HH:mm", cal).toString();

        holder.orderDateTv.setText(date);
        holder.orderTimeTv.setText(hour);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailsSellerActivity.class);
                intent.putExtra("orderId", orderId);
                intent.putExtra("orderBy", orderBy);
                intent.putExtra("formadepagamento", formadepagamento);
                intent.putExtra("time", orderTime);
                intent.putExtra("troco", troco);
                intent.putExtra("status", orderStatus);
                intent.putExtra("cost", total);
                intent.putExtra("cliente", cliente);
                intent.putExtra("pagamento", orderBy);
                intent.putExtra("cpm", cpm);
                intent.putExtra("tipoDeCompra", tipoDeCompra);
                intent.putExtra("fidelidade", orderBy);
                intent.putExtra("obs", orderBy);
                intent.putExtra("codentrega", codentrega);
                intent.putExtra("schedule", schedule);
                intent.putExtra("obs", obs);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (orderShopArrayList == null)
            return 0;
        else
            return orderShopArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null) {
            filter = new FilterOrderShop(this, filterList);
        }
        return filter;
    }
    class HolderOrderShop extends RecyclerView.ViewHolder{
        private final TextView orderIdTv;
        private final TextView orderDateTv;
        private final TextView emailTv;
        private final TextView amountTv;
        private final TextView statusTv;
        private final TextView orderTimeTv;

        public HolderOrderShop(@NonNull View itemView) {
            super(itemView);
            orderIdTv = itemView.findViewById(R.id.orderIdTv);
            orderTimeTv = itemView.findViewById(R.id.timeTv);
            orderDateTv = itemView.findViewById(R.id.orderDateTv);
            emailTv = itemView.findViewById(R.id.emailTv);
            amountTv = itemView.findViewById(R.id.amountTv);
            statusTv = itemView.findViewById(R.id.statusTv);
        }
    }
}
