package com.example.java.api.code;

import lombok.Getter;

@Getter
public enum OmMessage {

    // 1201~1300: 배치 관련 성공 메시지
    QUARTZ_JOB_REGISTERED("I-OM-1201", "Quartz Job 등록 성공"),
    QUARTZ_JOB_PAUSED("I-OM-1202", "Quartz Job 정지 성공"),
    QUARTZ_JOB_RESUMED("I-OM-1203", "Quartz Job 재시작 성공"),
    QUARTZ_JOB_DELETED("I-OM-1204", "Quartz Job 삭제 성공"),
    TRIGGER_REGISTERED("I-OM-1205", "Trigger 등록 성공"),
    TRIGGER_UPDATED("I-OM-1206", "Trigger 수정 성공"),
    TRIGGER_PAUSED("I-OM-1207", "Trigger 정지 성공"),
    TRIGGER_RESUMED("I-OM-1208", "Trigger 재시작 성공"),
    TRIGGER_DELETED("I-OM-1209", "Trigger 삭제 성공"),
    SPRING_BATCH_JOB_SUCCESS("I-OM-1210", "배치 실행 성공");

    private final String code;
    private final String desc;

    OmMessage(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
