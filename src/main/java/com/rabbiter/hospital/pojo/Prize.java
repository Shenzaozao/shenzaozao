package com.rabbiter.hospital.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
@TableName("prize")
public class Prize {

        @TableId(value = "pr_id")
        @JsonProperty("prId")
        private int prId;
        @JsonProperty("prName")
        private String prName;
        @JsonProperty("prPrice")
        private Integer prPrice;

        public Prize() {
        }

    public Prize(int prId, String prName, Integer prPrice) {
        this.prId = prId;
        this.prName = prName;
        this.prPrice = prPrice;
    }

    public int getPrId() {
        return prId;
    }

    public void setPrId(int prId) {
        this.prId = prId;
    }

    public String getPrName() {
        return prName;
    }

    public void setPrName(String prName) {
        this.prName = prName;
    }

    public Integer getPrPrice() {
        return prPrice;
    }

    public void setPrPrice(Integer prPrice) {
        this.prPrice = prPrice;
    }

    @Override
    public String toString() {
        return "Prize{" +
                "prId=" + prId +
                ", prName='" + prName + '\'' +
                ", prPrice=" + prPrice +
                '}';
    }
}

