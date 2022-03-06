package com.rainmonth.notebook.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 订单表
 */
@Entity
public class Order {
    @Id
    private Long id;

    @Generated(hash = 234500349)
    public Order(Long id) {
        this.id = id;
    }

    @Generated(hash = 1105174599)
    public Order() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}