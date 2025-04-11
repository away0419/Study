package com.example.java.api.code;

import lombok.Getter;

@Getter
public enum OmErrorMessage {

    // 1201~1300 배치
    QUARTZ_JOB_ALREADY_EXISTS("E-OM-1201", "이미 존재하는 Quartz Job"),
    QUARTZ_JOB_NOT_FOUND("E-OM-1202", "존재하지 않는 Quartz Job"),
    QUARTZ_JOB_CHECK_FAILED("E-OM-1203", "Quartz Job 존재 여부 확인 중 오류 발생"),
    QUARTZ_JOB_CREATION_FAILED("E-OM-1204", "Quartz Job 생성 실패"),
    QUARTZ_JOB_PAUSE_FAILED("E-OM-1205", "Quartz Job 정지 실패"),
    QUARTZ_JOB_RESUME_FAILED("E-OM-1206", "Quartz Job 재시작 실패"),
    QUARTZ_JOB_DELETE_FAILED("E-OM-1207", "Quartz Job 삭제 실패"),
    QUARTZ_JOB_EXECUTION_ERROR("E-OM-1208", "Quartz Job 실행 중 오류 발생"),

    TRIGGER_ALREADY_EXISTS("E-OM-1220", "이미 존재하는 Trigger"),
    TRIGGER_NOT_FOUND("E-OM-1221", "존재하지 않는 Trigger"),
    TRIGGER_CREATION_FAILED("E-OM-1222", "Trigger 생성 실패"),
    TRIGGER_PAUSE_FAILED("E-OM-1223", "Trigger 정지 실패"),
    TRIGGER_RESUME_FAILED("E-OM-1224", "Trigger 재시작 실패"),
    TRIGGER_DELETE_FAILED("E-OM-1225", "Trigger 삭제 실패"),
    TRIGGER_UPDATE_FAILED("E-OM-1226", "Trigger 수정 실패"),
    TRIGGER_CHECK_FAILED("E-OM-1227", "Trigger 여부 판별 중 실패"),
    TRIGGER_RETRIEVE_LIST_ERROR("E-OM-1228", "Trigger 목록 조회 중 오류 발생"),

    SPRING_BATCH_JOB_NOT_FOUND("E-OM-1240", "존재하지 않는 Spring Batch Job"),
    SPRING_BATCH_JOB_EXECUTION_ERROR("E-OM-1241", "Spring Batch Job 실행 중 오류 발생"),
    SPRING_BATCH_JOB_RETRIEVE_LIST_ERROR("E-OM-1242", "Spring Batch Job 목록 조회 중 오류 발생"),
    SPRING_BATCH_JOB_BEAN_NOT_FOUND("E-OM-1243", "존재하지 않는 Spring Batch Job Bean");

    private final String code;
    private final String desc;

    OmErrorMessage(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    }
