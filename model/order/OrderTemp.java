package com.example.coffee_rubi_ver.model.order;

import com.example.coffee_rubi_ver.model.product.Product;

import java.util.Objects;

public class OrderTemp {
    private Product product;
    private Integer quantity = 1;
    private Integer priceTemp;

    public OrderTemp() {
    }

    public OrderTemp(Product product, Integer quantity, Integer priceTemp) {
        this.product = product;
        this.quantity = quantity;
        this.priceTemp = priceTemp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderTemp o1 = (OrderTemp) o;
        return this.product.getProductName().equalsIgnoreCase(o1.getProduct().getProductName());
    }


    @Override
    public int hashCode() {
        return Objects.hash(product.getProductName());
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPriceTemp() {
        return priceTemp;
    }

    public void setPriceTemp(Integer priceTemp) {
        this.priceTemp = priceTemp;
    }

    @Override
    public String toString() {
        return product +"\t"+
                "SL : " + quantity +"\t";
    }
}
