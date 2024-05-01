package com.example.java.batch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomService {
    public void businessLogic(){
        for(int i =0; i<10; i++){
            log.info(i+": 비즈니스 로직");
        }
    }
}
