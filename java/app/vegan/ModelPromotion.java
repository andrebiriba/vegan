package app.vegan;

import androidx.annotation.Keep;

@Keep
public class ModelPromotion {
    String id, timestamp, description, promoCode, promoPrice, minimunOrderPrice, expiredDate, expiredTimestamp;

    public ModelPromotion() {
    }

    public ModelPromotion(String id, String timestamp, String description, String promoCode, String promoPrice, String mimimunOrderPrice, String expiredDate,
                          String expiredTimestamp) {
        this.id = id;
        this.expiredTimestamp = expiredTimestamp;
        this.timestamp = timestamp;
        this.description = description;
        this.promoCode = promoCode;
        this.promoPrice = promoPrice;
        this.minimunOrderPrice = mimimunOrderPrice;
        this.expiredDate = expiredDate;
    }

    public String getExpiredTimestamp() {
        return expiredTimestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public String getPromoPrice() {
        return promoPrice;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

}
