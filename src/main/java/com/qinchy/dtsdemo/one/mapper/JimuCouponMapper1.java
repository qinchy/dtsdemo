package com.qinchy.dtsdemo.one.mapper;

import com.qinchy.dtsdemo.one.domain.JimuCoupon;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JimuCouponMapper1 {
    int deleteByPrimaryKey(Integer id);

    int insert(JimuCoupon record);

    int insertSelective(JimuCoupon record);

    JimuCoupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JimuCoupon record);

    int updateByPrimaryKey(JimuCoupon record);
}