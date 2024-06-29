package com.rabbiter.hospital.controller;

import com.rabbiter.hospital.pojo.Prize;
import com.rabbiter.hospital.service.PrizeService;
import com.rabbiter.hospital.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("prize")
public class PrizeController {
    @Autowired
    private PrizeService prizeService;
    /**
     * 分页模糊查询所有奖品信息
     */
    @RequestMapping("findAllPrize")
    public ResponseData findAllPrize(int pageNumber, int size, String query){
        return ResponseData.success("返回所有奖品信息成功", this.prizeService.findAllPrize(pageNumber, size, query));
    }
    /**
     * 根据id查找会员已有奖品
     */
    @RequestMapping("findPrize")
    public ResponseData findPrize(@RequestParam(value = "pId") int pId){
        return ResponseData.success("根据id查找奖品成功", this.prizeService.findPrize(pId));
    }
    /**
     * 根据id查找奖品
     */
    @RequestMapping("selectPrize")
    public ResponseData selectPrize(@RequestParam(value = "prId") int prId){
        return ResponseData.success("根据id查找奖品成功", this.prizeService.selectPrize(prId));
    }
    /**
     * 增加奖品
     */
    @RequestMapping("addPrize")
    @ResponseBody
    public ResponseData addPrize(Prize prize) {
        Boolean bo = this.prizeService.addPrize(prize);
        if (bo) {
            return ResponseData.success("增加奖品成功");
        }
        return ResponseData.fail("增加奖品失败！账号或已被占用");
    }
    /**
     * 增加会员奖品
     */
    @RequestMapping("addPrizeV")
    @ResponseBody
    public ResponseData addPrizeV(@RequestParam(value = "prId") int prId,@RequestParam(value = "pId")int pId) {
        Boolean bo = this.prizeService.addPrizeV(prId,pId);
        if (bo) {
            return ResponseData.success("增加会员奖品成功");
        }
        return ResponseData.fail("增加会员奖品失败");
    }
    /**
     * 删除奖品
     */
    @RequestMapping("deletePrize")
    public ResponseData deletePrize(@RequestParam(value = "prId") int prId) {
        Boolean bo = this.prizeService.deletePrize(prId);
        if (bo){
            return ResponseData.success("删除奖品成功");
        }
        return ResponseData.fail("删除奖品失败");
    }
    /**
     * 修改奖品
     */
    @RequestMapping("modifyPrize")
    @ResponseBody
    public ResponseData modifyPrize(Prize prize) {
        this.prizeService.modifyPrize(prize);
        return ResponseData.success("修改奖品信息成功");
    }

}
