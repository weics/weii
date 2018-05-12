package com.weii.pay.service.user.entity;

import com.weii.pay.common.core.entity.BaseEntity;

import java.io.Serializable;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 19:47 2018/5/11
 * @Description: 此实体没有关联的表，只作用于序列查找时传参使用
 */
public class SeqBuild extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 序列名称 **/
    private String seqName;

    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName;
    }

}