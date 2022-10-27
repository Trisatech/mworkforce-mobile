package com.radya.sfa.view.assignment.product;

import com.google.gson.annotations.SerializedName;
import com.radya.sfa.data.source.remote.BaseModel;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Data
public class AssignmentProduct extends BaseModel implements Serializable {

    @SerializedName("data")
    private List<Product> data;

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }

    @NoArgsConstructor
    @Data
    public static class Product implements Serializable {

        /**
         * product_id : 2a834d21-d641-45cb-b383-c982297ec2bd
         * product_code : PHL
         * product_name : PILIP
         * product_model : TERANG 15 WATT
         * product_image : null
         * product_price : 80000.0
         */

        @SerializedName("product_id")
        private String productId;
        @SerializedName("product_code")
        private String productCode;
        @SerializedName("product_name")
        private String productName;
        @SerializedName("product_model")
        private String productModel;
        @SerializedName("product_image")
        private String productImage;
        @SerializedName("product_price")
        private double productPrice;
        @SerializedName("order_qty")
        private int orderQty;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
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

        public String getProductModel() {
            return productModel;
        }

        public void setProductModel(String productModel) {
            this.productModel = productModel;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public double getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(double productPrice) {
            this.productPrice = productPrice;
        }

        public int getOrderQty() {
            return orderQty;
        }

        public void setOrderQty(int orderQty) {
            this.orderQty = orderQty;
        }
    }

}
