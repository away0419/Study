package com.example.java.batch;

import lombok.Getter;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@Getter
@StepScope
public class StepScopeParameter {
    private LocalDate date;

    @Value("${date}")
    public void setDate(String date){
        this.date = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
    }

}
