package com.rabbiter.hospital.service;

import com.rabbiter.hospital.pojo.Patient;

import java.util.HashMap;
import java.util.List;

public interface PatientService {
    /**
     * 登录数据校验
     * */
    Patient login(int pId, String pPassword);
    /**
     * 分页模糊查询所有患者信息
     */
    HashMap<String, Object> findPatients(int pageNumber, int size, String query);
    /**
     * 删除患者信息
     */
    Boolean deletePatient(int pId);
    /**
     * 根据患者id查询患者信息
     */
    Patient findPatientById(int pId);
    /**
     * 增加患者信息
     */
    Boolean addPatient(Patient patient);
    /**
     * 统计患者男女人数
     */
    List<Integer> patientAge();

    HashMap<String, Object> findVPatients(int pageNumber, int size, String query);

    Object topUp(int pId, int balance);

    Object findPrice(int dId);
}
