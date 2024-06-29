package com.rabbiter.hospital.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.hospital.mapper.PatientMapper;
import com.rabbiter.hospital.mapper.PrizeMapper;
import com.rabbiter.hospital.pojo.Checks;
import com.rabbiter.hospital.pojo.Patient;
import com.rabbiter.hospital.pojo.Prize;
import com.rabbiter.hospital.service.PrizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("PrizeService")
@Slf4j
public class PrizeServiceImpl implements PrizeService {

    @Autowired
    private PrizeMapper prizeMapper;

    @Autowired
    private PatientMapper patientMapper;
    @Override
    public HashMap<String, Object> findAllPrize(int pageNumber, int size, String query) {
        Page<Prize> page = new Page<>(pageNumber, size);
        QueryWrapper<Prize> wrapper = new QueryWrapper<>();
        wrapper.like("pr_name", query);
        IPage<Prize> iPage = this.prizeMapper.selectPage(page, wrapper);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", iPage.getTotal());       //总条数
        hashMap.put("size", iPage.getPages());       //总页数
        hashMap.put("pageNumber", iPage.getCurrent());//当前页
        hashMap.put("prize", iPage.getRecords()); //查询到的记录
        log.info("查到的奖品为："+hashMap);
        return hashMap;
    }

    @Override
    public List<Map<String, Object>> findPrize(int pId) {
        List<Map<String, Object>> prize = new ArrayList<>();
        //查询会员已兑换的奖品
        List<Map<String, Object>> prizev = this.prizeMapper.selectPrizeVByPId(pId);
        //log.info("查询会员已兑换的奖品信息为：" + prizev);
        Map<Object, Long> idCountMap = prizev.stream()
                .collect(Collectors.groupingBy(map -> map.get("prvVid"), Collectors.counting()));
        Integer count = idCountMap.size();
        // log.info(idCountMap.toString());
        for (Object key : idCountMap.keySet()){
            Prize p= this.prizeMapper.selectById((Integer)key) ;
            Map<String, Object> map = new HashMap<>();
            map.put("prId",p.getPrId());
            map.put("prName",p.getPrName());
            map.put("prPrice",p.getPrPrice());
            map.put("prCount",idCountMap.get(key));
            prize.add(map);
        }
        //log.info(prize.toString());

        return prize;
    }

    @Override
    public Boolean addPrize(Prize prize) {
        //如果账号已存在则返回false
        List<Prize> prize1 = this.prizeMapper.selectList(null);
        for (Prize prize2 : prize1) {
            if (prize.getPrId() == prize2.getPrId()) {
                return false;
            }
        }
        this.prizeMapper.insert(prize);
        return true;
    }

    @Override
    public Boolean deletePrize(int prId) {
        this.prizeMapper.deleteById(prId);
        return true;
    }

    @Override
    public Boolean modifyPrize(Prize prize) {
        this.prizeMapper.updateById(prize);
        return true;
    }

    @Override
    public Boolean addPrizeV(int prId, int pId) {
        this.prizeMapper.addPrizeV(prId, pId);
        QueryWrapper<Prize> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pr_id", prId);
        Prize prize = this.prizeMapper.selectOne(queryWrapper);

        QueryWrapper<Patient> queryWrapperP= new QueryWrapper<>();
        queryWrapperP.eq("p_id", pId);
        Patient patient = this.patientMapper.selectOne(queryWrapperP);
        Integer pIntegral = patient.getPIntegral();
        Integer prIntegral =  prize.getPrPrice();
        Integer Integral = pIntegral - prIntegral;
        this.patientMapper.updateIntegral(pId, Integral);
        return true;
    }

    @Override
    public Object selectPrize(int prId) {
        if (prId != 0) {
            return this.prizeMapper.selectById(prId);
        }
        return null;
    }
}
