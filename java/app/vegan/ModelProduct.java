package app.vegan;

import androidx.annotation.Keep;

@Keep
public class ModelProduct {
    private String shopUid, address1, address2, address3, categories, city, country, estPrice, firstBio, secondBio, serviceId, state, suggest1, suggest2,
            suggest3, thirdBio, productId,profilePhoto, serviceTitle, productTitle, productType, productDescription, productCategory, productQuantity,
            productIcon, productBrand, originalPrice, discountPrice, discountNote, discountAvailable, nationalMail, internationalMail, timestamp, uid, productLayout,
            soyseal, recyclingseal, parabenseal, organicseal, nogmoseal, glutenseal, cottonseal, cannaseal, biodeseal, raw, b12, ecoseal, productIcon2,sizepp,
            sizep, sizem, sizeg, sizegg, sizeggg, sizePersonalized, securePolicy, scheduleProduction, quality, onTime, nationalTime, moreText, importedTime,
            deliveryTime, estoque, deliveryOption, localoption, ingredients, adicional001, adicional002, adicional003, adicional004, adicional005, adicional006, adicional007,
            adicional008, adicional009, adicional010, adicional011, adicional012, adicional013, adicional014, adicional015, valor1, valor2, valor3, valor4, valor5, valor6, valor7,
            valor8, valor9, valor10, valor11, valor12, valor13, valor14, valor15, moreInfo, productIcon3, productCountry, cel, indisponivel, correct;

    public ModelProduct() {

    }

    public ModelProduct(String shopUid, String address1, String address2, String address3, String categories, String city, String country, String estPrice, String firstBio,
                        String secondBio, String serviceId, String state, String suggest1, String suggest2, String suggest3, String thirdBio, String productIcon2,
                        String productId, String serviceTitle, String profilePhoto, String biodeseal, String cannaseal, String cottonseal, String glutenseal, String nogmoseal, String organicseal,
                        String parabenseal, String recyclingseal, String soyseal, String productTitle, String productType, String productDescription, String productCategory
            , String productQuantity, String productIcon, String productBrand, String originalPrice, String discountPrice, String discountNote, String sizepp,
                        String discountAvailable, String internationalMail, String nationalMail, String timestamp, String uid, String productLayout, String raw, String b12, String ecoseal,
                        String sizep, String sizem, String sizeg, String sizegg, String sizeggg, String sizePersonalized, String securePolicy, String scheduleProduction,
                        String quality, String onTime, String nationalTime, String moreText, String importedTime, String deliveryOption, String estoque, String deliveryTime, String localoption,
                        String ingredients, String adicional001, String adicional002, String adicional003, String adicional004, String adicional005, String adicional006, String adicional007, String adicional008,
                        String adicional009, String adicional010, String adicional011, String adicional012, String adicional013, String adicional014, String adicional015, String valor1, String valor2,
                        String valor3, String valor4, String valor5, String valor6, String valor7, String valor8, String valor9, String valor10, String valor11, String valor12, String valor13,
                        String valor14, String valor15, String moreInfo, String productIcon3, String productCountry, String cel, String indisponivel, String correct) {

        this.shopUid = shopUid;
        this.correct = correct;
        this.indisponivel= indisponivel;
        this.cel = cel;
        this.productCountry = productCountry;
        this.productIcon3 = productIcon3;
        this.adicional001 = adicional001;
        this.adicional002 = adicional002;
        this.adicional003 = adicional003;
        this.adicional004 = adicional004;
        this.adicional005 = adicional005;
        this.adicional006 = adicional006;
        this.adicional007 = adicional007;
        this.adicional008 = adicional008;
        this.adicional009 = adicional009;
        this.adicional010 = adicional010;
        this.adicional011 = adicional011;
        this.adicional012 = adicional012;
        this.adicional013 = adicional013;
        this.adicional014 = adicional014;
        this.adicional015 = adicional015;
        this.valor1 = valor1;
        this.valor2 = valor2;
        this.valor3 = valor3;
        this.valor4 = valor4;
        this.valor5 = valor5;
        this.valor6 = valor6;
        this.valor7 = valor7;
        this.valor8 = valor8;
        this.valor9 = valor9;
        this.valor10 = valor10;
        this.valor11 = valor11;
        this.valor12 = valor12;
        this.valor13 = valor13;
        this.valor14 = valor14;
        this.valor15 = valor15;
        this.ingredients = ingredients;
        this.sizepp = sizepp;
        this.sizep = sizep;
        this.sizem = sizem;
        this.sizeg = sizeg;
        this.sizegg = sizegg;
        this.sizeggg = sizeggg;
        this.sizePersonalized = sizePersonalized;
        this.securePolicy = securePolicy;
        this.scheduleProduction = scheduleProduction;
        this.quality = quality;
        this.onTime = onTime;
        this.nationalTime = nationalTime;
        this.moreText = moreText;
        this.importedTime = importedTime;
        this.deliveryOption = deliveryOption;
        this.estoque = estoque;
        this.deliveryTime = deliveryTime;
        this.localoption = localoption;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.categories = categories;
        this.city = city;
        this.country = country;
        this.estPrice = estPrice;
        this.firstBio = firstBio;
        this.secondBio = secondBio;
        this.serviceId = serviceId;
        this.state = state;
        this.suggest1 = suggest1;
        this.suggest2 = suggest2;
        this.suggest3 = suggest3;
        this.thirdBio = thirdBio;
        this.productId = productId;
        this.soyseal = soyseal;
        this.serviceTitle = serviceTitle;
        this.profilePhoto = profilePhoto;
        this.recyclingseal = recyclingseal;
        this.parabenseal = parabenseal;
        this.organicseal = organicseal;
        this.nogmoseal = nogmoseal;
        this.glutenseal = glutenseal;
        this.cottonseal = cottonseal;
        this.cannaseal = cannaseal;
        this.biodeseal = biodeseal;
        this.productTitle = productTitle;
        this.productType = productType;
        this.productBrand = productBrand;
        this.productDescription = productDescription;
        this.productCategory = productCategory;
        this.productQuantity = productQuantity;
        this.productIcon = productIcon;
        this.productIcon2 = productIcon2;
        this.originalPrice = originalPrice;
        this.discountPrice = discountPrice;
        this.discountNote = discountNote;
        this.nationalMail = nationalMail;
        this.internationalMail = internationalMail;
        this.discountAvailable = discountAvailable;
        this.timestamp = timestamp;
        this.uid = uid;
        this.productLayout = productLayout;
        this.raw = raw;
        this.b12 = b12;
        this.ecoseal = ecoseal;
        this.moreInfo = moreInfo;
    }

    public String getShopUid() {
        return shopUid;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getIndisponivel() {
        return indisponivel;
    }

    public void setIndisponivel(String indisponivel) {
        this.indisponivel = indisponivel;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setNogmoseal(String nogmoseal) {
        this.nogmoseal = nogmoseal;
    }

    public void setGlutenseal(String glutenseal) {
        this.glutenseal = glutenseal;
    }

    public void setCottonseal(String cottonseal) {
        this.cottonseal = cottonseal;
    }

    public void setCannaseal(String cannaseal) {
        this.cannaseal = cannaseal;
    }

    public void setBiodeseal(String biodeseal) {
        this.biodeseal = biodeseal;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public void setProductIcon2(String productIcon2) {
        this.productIcon2 = productIcon2;
    }

    public String getSizepp() {
        return sizepp;
    }

    public void setSizepp(String sizepp) {
        this.sizepp = sizepp;
    }

    public void setSizep(String sizep) {
        this.sizep = sizep;
    }

    public void setSizem(String sizem) {
        this.sizem = sizem;
    }

    public void setSizeg(String sizeg) {
        this.sizeg = sizeg;
    }

    public void setSizegg(String sizegg) {
        this.sizegg = sizegg;
    }

    public void setSizeggg(String sizeggg) {
        this.sizeggg = sizeggg;
    }

    public void setSizePersonalized(String sizePersonalized) {
        this.sizePersonalized = sizePersonalized;
    }

    public void setSecurePolicy(String securePolicy) {
        this.securePolicy = securePolicy;
    }

    public void setScheduleProduction(String scheduleProduction) {
        this.scheduleProduction = scheduleProduction;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public void setNationalTime(String nationalTime) {
        this.nationalTime = nationalTime;
    }

    public void setImportedTime(String importedTime) {
        this.importedTime = importedTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setLocalOption(String localoption) {
        this.localoption = localoption;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public String getCel() {
        return cel;
    }

    public String getProductCountry() {
        return productCountry;
    }

    public void setProductCountry(String productCountry) {
        this.productCountry = productCountry;
    }

    public String getProductIcon3() {
        return productIcon3;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public String getMoreText() {
        return moreText;
    }

    public void setMoreText(String moreText) {
        this.moreText = moreText;
    }

    public String getProductIcon2() {
        return productIcon2;
    }

    public String getAdicional001() {
        return adicional001;
    }

    public String getAdicional002() {
        return adicional002;
    }

    public String getAdicional003() {
        return adicional003;
    }

    public String getAdicional004() {
        return adicional004;
    }

    public String getAdicional005() {
        return adicional005;
    }

    public String getAdicional006() {
        return adicional006;
    }

    public String getAdicional007() {
        return adicional007;
    }

    public String getAdicional008() {
        return adicional008;
    }

    public String getAdicional009() {
        return adicional009;
    }

    public String getAdicional010() {
        return adicional010;
    }

    public String getAdicional011() {
        return adicional011;
    }

    public String getAdicional012() {
        return adicional012;
    }

    public String getAdicional013() {
        return adicional013;
    }

    public String getAdicional014() {
        return adicional014;
    }

    public String getAdicional015() {
        return adicional015;
    }

    public String getValor1() {
        return valor1;
    }

    public void setValor1(String valor1) {
        this.valor1 = valor1;
    }

    public String getValor2() {
        return valor2;
    }

    public void setValor2(String valor2) {
        this.valor2 = valor2;
    }

    public String getValor3() {
        return valor3;
    }

    public void setValor3(String valor3) {
        this.valor3 = valor3;
    }

    public String getValor4() {
        return valor4;
    }

    public void setValor4(String valor4) {
        this.valor4 = valor4;
    }

    public String getValor5() {
        return valor5;
    }

    public void setValor5(String valor5) {
        this.valor5 = valor5;
    }

    public String getValor6() {
        return valor6;
    }

    public void setValor6(String valor6) {
        this.valor6 = valor6;
    }

    public String getValor7() {
        return valor7;
    }

    public void setValor7(String valor7) {
        this.valor7 = valor7;
    }

    public String getValor8() {
        return valor8;
    }

    public void setValor8(String valor8) {
        this.valor8 = valor8;
    }

    public String getValor9() {
        return valor9;
    }

    public void setValor9(String valor9) {
        this.valor9 = valor9;
    }

    public String getValor10() {
        return valor10;
    }

    public void setValor10(String valor10) {
        this.valor10 = valor10;
    }

    public String getValor11() {
        return valor11;
    }

    public void setValor11(String valor11) {
        this.valor11 = valor11;
    }

    public String getValor12() {
        return valor12;
    }

    public void setValor12(String valor12) {
        this.valor12 = valor12;
    }

    public String getValor13() {
        return valor13;
    }

    public void setValor13(String valor13) {
        this.valor13 = valor13;
    }

    public String getValor14() {
        return valor14;
    }

    public void setValor14(String valor14) {
        this.valor14 = valor14;
    }

    public String getValor15() {
        return valor15;
    }

    public void setValor15(String valor15) {
        this.valor15 = valor15;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getSizep() {
        return sizep;
    }

    public String getSizem() {
        return sizem;
    }

    public String getSizeg() {
        return sizeg;
    }

    public String getSizegg() {
        return sizegg;
    }

    public String getSizeggg() {
        return sizeggg;
    }

    public String getSizePersonalized() {
        return sizePersonalized;
    }

    public String getSecurePolicy() {
        return securePolicy;
    }

    public String getScheduleProduction() {
        return scheduleProduction;
    }

    public String getQuality() {
        return quality;
    }

    public String getOnTime() {
        return onTime;
    }

    public String getNationalTime() {
        return nationalTime;
    }

    public String getImportedTime() {
        return importedTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public String getEstoque() {
        return estoque;
    }

    public String getDeliveryOption() {
        return deliveryOption;
    }

    public String getLocalOption() {
        return localoption;
    }

    public String getRaw() {
        return raw;
    }

    public String getB12() {
        return b12;
    }

    public String getEcoseal() {
        return ecoseal;
    }

    public void setEcoseal(String ecoseal) {
        this.ecoseal = ecoseal;
    }

    public String getAddress3() {
        return address3;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getEstPrice() {
        return estPrice;
    }

    public String getFirstBio() {
        return firstBio;
    }

    public String getSecondBio() {
        return secondBio;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSuggest1() {
        return suggest1;
    }

    public String getSuggest2() {
        return suggest2;
    }

    public String getSuggest3() {
        return suggest3;
    }

    public String getThirdBio() {
        return thirdBio;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public String getSoyseal() {
        return soyseal;
    }

    public String getRecyclingseal() {
        return recyclingseal;
    }

    public String getParabenseal() {
        return parabenseal;
    }

    public void setParabenseal(String parabenseal) {
        this.parabenseal = parabenseal;
    }

    public String getOrganicseal() {
        return organicseal;
    }

    public String getNogmoseal() {
        return nogmoseal;
    }

    public String getGlutenseal() {
        return glutenseal;
    }

    public String getCottonseal() {
        return cottonseal;
    }

    public String getCannaseal() {
        return cannaseal;
    }

    public String getBiodeseal() {
        return biodeseal;
    }

    public String getInternationalMail() {
        return internationalMail;
    }

    public String getProductType() {
        return productType;
    }

    public String getNationalMail() {
        return nationalMail;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
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
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
