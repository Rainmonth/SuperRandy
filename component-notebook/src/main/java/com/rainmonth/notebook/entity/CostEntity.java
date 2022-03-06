package com.rainmonth.notebook.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 花费表对应实体
 *
 * @author RandyZhang
 * @date 2022/3/4 11:38 下午
 */
@Entity(nameInDb = "Cost",
        generateConstructors = true,
        generateGettersSetters = true)
public class CostEntity {
    @Id
    Long id;

    Integer costType;

    Integer costNum;

    Long costTime;

@Generated(hash = 2131268949)
public CostEntity(Long id, Integer costType, Integer costNum, Long costTime) {
    this.id = id;
    this.costType = costType;
    this.costNum = costNum;
    this.costTime = costTime;
}

@Generated(hash = 1244719688)
public CostEntity() {
}

public Long getId() {
    return this.id;
}

public void setId(Long id) {
    this.id = id;
}

public Integer getCostType() {
    return this.costType;
}

public void setCostType(Integer costType) {
    this.costType = costType;
}

public Integer getCostNum() {
    return this.costNum;
}

public void setCostNum(Integer costNum) {
    this.costNum = costNum;
}

public Long getCostTime() {
    return this.costTime;
}

public void setCostTime(Long costTime) {
    this.costTime = costTime;
}
}