package com.weii.pay.common.core.dao.impl;

import com.weii.pay.common.core.dao.BaseDao;
import com.weii.pay.common.core.entity.BaseEntity;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * Created by weics on 2018/5/1.
 */
public abstract class BaseDaoImpl<T extends BaseEntity> extends SqlSessionDaoSupport implements BaseDao {
}
