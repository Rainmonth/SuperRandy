package com.rainmonth.notebook.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.rainmonth.notebook.dao.DaoSession;
import com.rainmonth.notebook.dao.OrderDao;
import com.rainmonth.notebook.dao.ProductDao;

/**
 * 产品表
 */
@Entity
public class Product {
    @Id
    private Long id;

    @ToMany
    @JoinEntity(
            entity = JoinProductsWithOrders.class,
            sourceProperty = "productId",
            targetProperty = "orderId"
    )
    private List<Order> ordersWithThisProduct;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 694336451)
    private transient ProductDao myDao;

    @Generated(hash = 1488797098)
    public Product(Long id) {
        this.id = id;
    }

    @Generated(hash = 1890278724)
    public Product() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1365647064)
    public List<Order> getOrdersWithThisProduct() {
        if (ordersWithThisProduct == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            OrderDao targetDao = daoSession.getOrderDao();
            List<Order> ordersWithThisProductNew = targetDao
                    ._queryProduct_OrdersWithThisProduct(id);
            synchronized (this) {
                if (ordersWithThisProduct == null) {
                    ordersWithThisProduct = ordersWithThisProductNew;
                }
            }
        }
        return ordersWithThisProduct;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1032865744)
    public synchronized void resetOrdersWithThisProduct() {
        ordersWithThisProduct = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1171535257)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getProductDao() : null;
    }
}
