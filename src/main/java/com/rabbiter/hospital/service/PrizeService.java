package com.rabbiter.hospital.service;

import com.rabbiter.hospital.pojo.Checks;
import com.rabbiter.hospital.pojo.Prize;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PrizeService {
    /**
     * 分页模糊查询所有奖品信息
     */
    HashMap<String, Object> findAllPrize(int pageNumber, int size, String query);
    /**
     * 根据id查找奖品
     */
    List<Map<String, Object>> findPrize(int prId);
    /**
     * 增加检查信息
     */
    Boolean addPrize(Prize prize);
    /**
     * 删除检查信息
     */
    Boolean deletePrize(int prId);
    /**
     * 修改检查信息
     */
    Boolean modifyPrize(Prize prize);

    Boolean addPrizeV(int prId, int pId);

    Object selectPrize(int prId);
}
