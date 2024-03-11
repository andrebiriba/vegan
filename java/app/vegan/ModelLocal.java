package app.vegan;

import androidx.annotation.Keep;

@Keep
public class ModelLocal {
    private String shopIcon, fName, description, city, state, country, email, neighborhood, serviceTitle,
    number, reference, street, type, uid, doInternational, doMail, firstBio, profilePhoto, address1, address2, address3,
            categories, estPrice, secondBio, serviceId, suggest1, suggest2,suggest3, thirdBio, petfriendly, accessibility, vegan, correct;

    public ModelLocal() {

    }

    public ModelLocal(String fName, String shopIcon, String description, String city, String state, String country, String email, String neighborhood,
                      String number, String reference, String street, String type, String uid, String doInternational, String firstBio, String profilePhoto,
                      String doMail, String serviceTitle, String petfriendly, String accessibility, String vegan, String address1, String address2, String address3,
                      String categories, String estPrice, String suggest1, String secondBio, String serviceId, String suggest2, String suggest3,String correct,
                      String thirdBio) {
        this.correct = correct;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.categories = categories;
        this.estPrice = estPrice;
        this.suggest1 = suggest1;
        this.secondBio = secondBio;
        this.serviceId = serviceId;
        this.suggest2 = suggest2;
        this.suggest3 = suggest3;
        this.thirdBio = thirdBio;
        this.doInternational = doInternational;
        this.doMail = doMail;
        this.shopIcon = shopIcon;
        this.fName = fName;
        this.description = description;
        this.city = city;
        this.state = state;
        this.country = country;
        this.email = email;
        this.neighborhood = neighborhood;
        this.number = number;
        this.reference = reference;
        this.street = street;
        this.type = type;
        this.uid = uid;
        this.firstBio = firstBio;
        this.profilePhoto = profilePhoto;
        this.serviceTitle = serviceTitle;
        this.petfriendly = petfriendly;
        this.accessibility = accessibility;
        this.vegan = vegan;
    }

    public String getCorrect() {
        return correct;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public String getVegan() {
        return vegan;
    }

    public void setVegan(String vegan) {
        this.vegan = vegan;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
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

    public String getEstPrice() {
        return estPrice;
    }

    public String getSecondBio() {
        return secondBio;
    }

    public String getServiceId() {
        return serviceId;
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

    public String getServiceTitle() {
        return serviceTitle;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    public String getFName() {
        return fName;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public String getPetFriendly() {
        return petfriendly;
    }

    public void setPetFriendly(String petFriendly) {
        this.petfriendly = petfriendly;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getShopIcon() {
        return shopIcon;
    }

    public void setShopIcon(String shopIcon) {
        this.shopIcon = shopIcon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirstBio() {
        return firstBio;
    }

}
