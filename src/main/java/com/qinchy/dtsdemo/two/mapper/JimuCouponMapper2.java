package com.qinchy.dtsdemo.two.mapper;

import com.qinchy.dtsdemo.two.domain.JimuCoupon;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JimuCouponMapper2 {
    int deleteByPrimaryKey(Integer id);

    int insert(JimuCoupon record);

    int insertSelective(JimuCoupon record);

    JimuCoupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JimuCoupon record);

    int updateByPrimaryKey(JimuCoupon record);
}