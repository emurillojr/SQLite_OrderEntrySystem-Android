/**
 * Created by Ernesto Murillo on 7/19/2017.
 */
package neit.em_lab1;

import android.os.Parcel;
import android.os.Parcelable;

import static android.R.attr.id;
import static android.R.attr.name;

public class Order implements Parcelable {
    int _id;
    String firstName;
    String lastName;
    String typeOfChocolate;
    int bars;
    Boolean shippingType;
    float price;

    public Order(){

    }
    public Order(int id, String firstName, String lastName, String typeOfChocolate, int bars, Boolean shippingType,float price ){
        this._id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.typeOfChocolate = typeOfChocolate;
        this.bars = bars;
        this.shippingType = shippingType;
        this.price = price;

//        setFirstName(firstName);
//        setLastName(lastName);
//        setTypeOfChocolate(typeOfChocolate);
//        setBars(bars);
//        setShippintType(shippintType);
    }


    public Order(String firstName, String lastName, String typeOfChocolate, int bars, Boolean shippingType, float price ){
        //this._id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.typeOfChocolate = typeOfChocolate;
        this.bars = bars;
        this.shippingType = shippingType;
        this.price = price;

//        setFirstName(firstName);
//        setLastName(lastName);
//        setTypeOfChocolate(typeOfChocolate);
//        setBars(bars);
//        setShippintType(shippintType);
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

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

    public String getTypeOfChocolate() {
        return typeOfChocolate;
    }

    public void setTypeOfChocolate(String typeOfChocolate) {
        this.typeOfChocolate = typeOfChocolate;
    }

    public int getBars() {
        return bars;
    }

    public void setBars(int bars) {
        this.bars = bars;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this._id);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.typeOfChocolate);
        //dest.writeByte(this.shippingType ? (byte) 1 : (byte) 0);
        dest.writeInt(this.bars);
        dest.writeValue(this.shippingType);
        dest.writeFloat(this.price);
    }

    protected Order(Parcel in) {
        this._id = in.readInt();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.typeOfChocolate = in.readString();
        this.bars = in.readInt();
        //this.shippingType = in.readByte() != 0;
        this.shippingType = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.price = in.readFloat();
    }

    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

//    public static final Creator<Order> CREATOR = new Creator<Order>() {
//        @Override
//        public Order createFromParcel(Parcel source) {
//            return new Order(source);
//        }
//
//        @Override
//        public Order[] newArray(int size) {
//            return new Order[size];
//        }
//    };


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Boolean getShippingType() {
        return shippingType;
    }

    public void setShippingType(Boolean shippingType) {
        this.shippingType = shippingType;
    }








}
