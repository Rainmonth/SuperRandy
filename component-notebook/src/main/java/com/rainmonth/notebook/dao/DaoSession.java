package com.rainmonth.notebook.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.rainmonth.notebook.entity.CostEntity;
import com.rainmonth.notebook.entity.JoinProductsWithOrders;
import com.rainmonth.notebook.entity.Order;
import com.rainmonth.notebook.entity.Product;

import com.rainmonth.notebook.dao.CostEntityDao;
import com.rainmonth.notebook.dao.JoinProductsWithOrdersDao;
import com.rainmonth.notebook.dao.OrderDao;
import com.rainmonth.notebook.dao.ProductDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig costEntityDaoConfig;
    private final DaoConfig joinProductsWithOrdersDaoConfig;
    private final DaoConfig orderDaoConfig;
    private final DaoConfig productDaoConfig;

    private final CostEntityDao costEntityDao;
    private final JoinProductsWithOrdersDao joinProductsWithOrdersDao;
    private final OrderDao orderDao;
    private final ProductDao productDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        costEntityDaoConfig = daoConfigMap.get(CostEntityDao.class).clone();
        costEntityDaoConfig.initIdentityScope(type);

        joinProductsWithOrdersDaoConfig = daoConfigMap.get(JoinProductsWithOrdersDao.class).clone();
        joinProductsWithOrdersDaoConfig.initIdentityScope(type);

        orderDaoConfig = daoConfigMap.get(OrderDao.class).clone();
        orderDaoConfig.initIdentityScope(type);

        productDaoConfig = daoConfigMap.get(ProductDao.class).clone();
        productDaoConfig.initIdentityScope(type);

        costEntityDao = new CostEntityDao(costEntityDaoConfig, this);
        joinProductsWithOrdersDao = new JoinProductsWithOrdersDao(joinProductsWithOrdersDaoConfig, this);
        orderDao = new OrderDao(orderDaoConfig, this);
        productDao = new ProductDao(productDaoConfig, this);

        registerDao(CostEntity.class, costEntityDao);
        registerDao(JoinProductsWithOrders.class, joinProductsWithOrdersDao);
        registerDao(Order.class, orderDao);
        registerDao(Product.class, productDao);
    }
    
    public void clear() {
        costEntityDaoConfig.clearIdentityScope();
        joinProductsWithOrdersDaoConfig.clearIdentityScope();
        orderDaoConfig.clearIdentityScope();
        productDaoConfig.clearIdentityScope();
    }

    public CostEntityDao getCostEntityDao() {
        return costEntityDao;
    }

    public JoinProductsWithOrdersDao getJoinProductsWithOrdersDao() {
        return joinProductsWithOrdersDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

}