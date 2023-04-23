package com.example.a4corners;

public class GetterSetterOrder {
    String id;
    String items;
    String delivery;
    String ccName;
    String ccNumber;
    String address;
    String total;

    public GetterSetterOrder(String id, String items, String delivery, String ccName, String ccNumber, String address, String total) {
        this.id = id;
        this.items = items;
        this.delivery = delivery;
        this.ccName = ccName;
        this.ccNumber = ccNumber;
        this.address = address;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getCcName() {
        return ccName;
    }

    public void setCcName(String ccName) {
        this.ccName = ccName;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
