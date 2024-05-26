package com.ssafy.ssafit.model.dto;

import java.time.LocalDate;

public class Event {

    private String userId;
    private LocalDate receiveTime;

    public Event() {
        // 기본 생성자
    }

    public Event(String userId, LocalDate receiveTime) {
        this.userId = userId;
        this.receiveTime = receiveTime;
    }

    // Getter 및 Setter 메소드
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDate getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(LocalDate receiveTime) {
        this.receiveTime = receiveTime;
    }
}
