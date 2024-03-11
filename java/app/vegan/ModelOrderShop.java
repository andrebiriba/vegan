package app.vegan;

import androidx.annotation.Keep;

@Keep
public class ModelOrderShop {
    String orderId, orderTime, orderStatus, orderCost, orderBy, latitude, longitude, deliveryFee, total,
            cliente, troco, formadepagamento, cpm, tipoDeCompra, codentrega, schedule, obs;

    public ModelOrderShop() {
    }

    public ModelOrderShop(String orderId, String orderTime, String orderStatus, String orderCost, String orderBy, String latitude,
                          String longitude, String deliveryFee, String total, String cliente, String troco, String formadepagamento,
                          String cpm, String tipoDeCompra, String codentrega, String schedule, String obs) {
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.orderCost = orderCost;
        this.orderBy = orderBy;
        this.latitude = latitude;
        this.longitude = longitude;
        this.deliveryFee = deliveryFee;
        this.total = total;
        this.cliente = cliente;
        this.troco = troco;
        this.formadepagamento = formadepagamento;
        this.cpm = cpm;
        this.tipoDeCompra = tipoDeCompra;
        this.codentrega = codentrega;
        this.schedule = schedule;
        this.obs = obs;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getCodentrega() {
        return codentrega;
    }

    public String getTipoDeCompra() {
        return tipoDeCompra;
    }

    public String getCpm() {
        return cpm;
    }

    public void setCpm(String cpm) {
        this.cpm = cpm;
    }

    public String getFormadepagamento() {
        return formadepagamento;
    }

    public String getTroco() {
        return troco;
    }

    public String getCliente() {
        return cliente;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
