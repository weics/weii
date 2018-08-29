package com.weii.pay.service.account.dao;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.account.entity.RpAccount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author: weics
 * @Date: Created in 23:15 2018/8/29
 * @Description:
 * @Modified By:
 */
@Mapper
public interface AccountMapper {
    RpAccount getByAccountNo(String accountNo);

    void update(RpAccount account);

    RpAccount getBy(Map<String,Object> map);

    PageBean listPage(PageParam pageParam, Map<String,Object> params);

    List<RpAccount> listBy(Map<String,Object> paramMap);

    void insert(RpAccount rpAccount);

    RpAccount getById(String id);

    RpAccount getByUserNo(Map<String,Object> map);
}
