package com.example.mirko.tutorial1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mirko on 19/10/15.
 */
public class CustomerDetail implements Parcelable {
    public static Creator<CustomerDetail> CREATOR = new Creator<CustomerDetail>() {
        @Override
        public CustomerDetail createFromParcel(Parcel source) {
            return new CustomerDetail(source);
        }

        @Override
        public CustomerDetail[] newArray(int size) {
            return new CustomerDetail[size];
        }
    };

    private String firstName;
    private String lastName;

    private List<CreditCard> creditCards;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    @Override
    public int describeContents() {
        return PARCELABLE_WRITE_RETURN_VALUE;
    }

    public CustomerDetail() {}

    public CustomerDetail(Parcel p) {
        final String[] data = new String[2];
        p.readStringArray(data);
        this.firstName = data[0];
        this.lastName = data[1];
        this.creditCards = new ArrayList<>();
        for (Object o : p.readParcelableArray(getClass().getClassLoader())) {
            creditCards.add((CreditCard) o);
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.firstName, this.lastName});

        dest.writeParcelableArray(
                creditCards.toArray(new CreditCard[creditCards.size()]), PARCELABLE_WRITE_RETURN_VALUE);
    }

    public static class CreditCard implements Parcelable {
        public static final Creator<CreditCard> CREATOR = new Creator<CreditCard>() {
            @Override
            public CreditCard createFromParcel(Parcel source) {
                return new CreditCard(source);
            }

            @Override
            public CreditCard[] newArray(int size) {
                return new CreditCard[size];
            }
        };

        private boolean defaultCard;
        private String imageUrl;
        private String cardType;
        private String token;
        private String expiration;

        public boolean isDefaultCard() {
            return defaultCard;
        }

        public void setDefaultCard(boolean defaultCard) {
            this.defaultCard = defaultCard;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getExpiration() {
            return expiration;
        }

        public void setExpiration(String expiration) {
            this.expiration = expiration;
        }

        @Override
        public int describeContents() {
            return PARCELABLE_WRITE_RETURN_VALUE;
        }

        public CreditCard() {

        }

        public CreditCard(Parcel p) {
            final String[] data = new String[5];
            p.readStringArray(data);
            int i = 0;
            this.defaultCard = Boolean.parseBoolean(data[i++]);
            this.imageUrl = data[i++];
            this.cardType = data[i++];
            this.token = data[i++];
            this.expiration = data[i++];
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeStringArray(new String[] {
                    Boolean.toString(this.defaultCard),
                    this.imageUrl, this.cardType,
                    this.token, this.expiration});
        }
    }
}
