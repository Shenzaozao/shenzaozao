package com.rabbiter.hospital.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rabbiter.hospital.pojo.Patient;
import org.apache.ibatis.annotations.Param;

public interface PatientMapper extends BaseMapper<Patient> {
    /**
     * 统计患者男女人数
     */
    Integer patientAge(@Param("startAge") int startAge, @Param("endAge") int endAge);

    void updateIntegral(@Param("pId") int pId,@Param("pIntegral") Integer pIntegral);

    void updatePatient(@Param("pId")int pId, @Param("pIntegral")int pIntegral, @Param("pBalance")int pBalance);

    int selectPrice(int dId);
}
