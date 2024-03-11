package app.vegan;

import androidx.annotation.Keep;

@Keep
public class ModelService {
    private String productId, serviceTitle, productDescription, productCategory, productQuantity, profilePhoto, brandname,
            originalPrice, discountPrice, discountNote, discountAvailable, nationalMail, internationalMail, timestamp, uid, productLayout;

    public ModelService() {

    }

    public ModelService(String productId, String serviceTitle, String productDescription, String productCategory
            , String productQuantity, String profilePhoto, String brandname, String originalPrice, String discountPrice, String discountNote,
                        String discountAvailable, String internationalMail, String nationalMail, String timestamp, String uid, String productLayout) {
        this.productId = productId;
        this.serviceTitle = serviceTitle;
        this.brandname = brandname;
        this.productDescription = productDescription;
        this.productCategory = productCategory;
        this.productQuantity = productQuantity;
        this.profilePhoto = profilePhoto;
        this.originalPrice = originalPrice;
        this.discountPrice = discountPrice;
        this.discountNote = discountNote;
        this.nationalMail = nationalMail;
        this.internationalMail = internationalMail;
        this.discountAvailable = discountAvailable;
        this.timestamp = timestamp;
        this.uid = uid;
        this.productLayout = productLayout;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public void setServiceTitle(String productTitle) {
        this.serviceTitle = serviceTitle;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDiscountNote() {
        return discountNote;
    }

    public void setDiscountNote(String discountNote) {
        this.discountNote = discountNote;
    }

    public String getDiscountAvailable() {
        return discountAvailable;
    }

    public void setDiscountAvailable(String discountAvailable) {
        this.discountAvailable = discountAvailable;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
