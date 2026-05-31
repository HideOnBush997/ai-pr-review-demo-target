package com.example.reviewdemo.order;

public interface InventoryGateway {
    void reserve(String sku, int quantity);

    void release(String sku, int quantity);
}
