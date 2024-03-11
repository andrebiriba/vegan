package app.vegan;

import androidx.annotation.Keep;

@Keep
public class ModelOrderUser {

    String orderId, orderTime, orderStatus, orderCost, orderBy, orderTo, fName, total,
            troco, obs, formadepagamento, fidelidade, cpm, codentrega, schedule, deliveryFee;

    public ModelOrderUser() {}

    public ModelOrderUser(String orderId, String orderTime, String orderStatus, String orderCost, String orderBy, String orderTo,
                          String fName, String total, String troco, String obs, String formadepagamento, String fidelidade,
                          String cpm, String codentrega, String schedule, String deliveryFee) {

        this.obs = obs;
        this.formadepagamento = formadepagamento;
        this.fidelidade = fidelidade;
        this.cpm = cpm;
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.orderCost = orderCost;
        this.orderBy = orderBy;
        this.orderTo = orderTo;
        this.fName = fName;
        this.total = total;
        this.troco = troco;
        this.codentrega = codentrega;
        this.schedule = schedule;
        this.deliveryFee = deliveryFee;
    }

    public String getDeliveryFee() {
        return deliveryFee;
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

    public String getObs() {
        return obs;
    }


    public String getFormadepagamento() {
        return formadepagamento;
    }


    public String getFidelidade() {
        return fidelidade;
    }

    public String getCpm() {
        return cpm;
    }

    public String getTroco() {
        return troco;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
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

    public String getOrderTo() {
        return orderTo;
    }
}
