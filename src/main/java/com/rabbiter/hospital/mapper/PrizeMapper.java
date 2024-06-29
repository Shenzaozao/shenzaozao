package com.rabbiter.hospital.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rabbiter.hospital.pojo.Checks;
import com.rabbiter.hospital.pojo.Prize;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PrizeMapper extends BaseMapper<Prize> {

    List<Map<String,Object>> selectPrizeVByPId(@Param("pId") int pId);

    void addPrizeV(int prId, int pId);
}
