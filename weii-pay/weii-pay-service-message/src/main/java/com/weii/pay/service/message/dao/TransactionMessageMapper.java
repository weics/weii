package com.weii.pay.service.message.dao;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.message.entity.TransactionMessage;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: weics
 * @Date: Created in 21:26 2018/8/24
 * @Description:
 * @Modified By:
 */
@Repository
public interface TransactionMessageMapper {
    int insert(TransactionMessage message);

    void update(TransactionMessage message);

    TransactionMessage getBy(Map<String,Object> paramMap);

    void delete(Map<String,Object> paramMap);

    List<TransactionMessage> listPage(Map<String,Object> paramMap);
}
