package com.example.kotlin.tasklet

import org.slf4j.LoggerFactory

class MethodInvokingTaskletAdapterService {
    private val log = LoggerFactory.getLogger(this.javaClass)!!
    fun businessLogic(){
        for (i in 1.. 10){
            log.info("MethodInvokingTaskletAdapterService : ${i}번째 비즈니스 로직")
        }
    }
}