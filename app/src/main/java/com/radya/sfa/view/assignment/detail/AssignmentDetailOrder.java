package com.radya.sfa.view.assignment.detail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Data
public class AssignmentDetailOrder implements Serializable{


    /**
     * order_id : string
     * product_code : string
     * product_name : string
     * quantity : 0
     * product_amount : 0
     * discount : 0
     * customer_code : string
     * customerId : string
     * customer_name : string
     */

    @SerializedName("order_id")
    private String orderId;
    @SerializedName("product_code")
    private String productCode;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("product_amount")
    private int productAmount;
    @SerializedName("discount")
    private int discount;
    @SerializedName("customer_code")
    private String customerCode;
    @SerializedName("customerId")
    private String customerId;
    @SerializedName("customer_name")
    private String customerName;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
