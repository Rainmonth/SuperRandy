package com.rainmonth.notebook.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 订单产品join表
 */
@Entity
public class JoinProductsWithOrders {
    @Id
    private Long id;
    private Long productId;
    private Long orderId;
    @Generated(hash = 1474928820)
    public JoinProductsWithOrders(Long id, Long productId, Long orderId) {
        this.id = id;
        this.productId = productId;
        this.orderId = orderId;
    }
    @Generated(hash = 1986378003)
    public JoinProductsWithOrders() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getProductId() {
        return this.productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public Long getOrderId() {
        return this.orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}