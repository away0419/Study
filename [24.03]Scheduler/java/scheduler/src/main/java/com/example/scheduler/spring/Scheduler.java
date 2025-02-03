// package com.example.scheduler.spring;
//
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
//
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Component;
//
// @Component
// public class Scheduler {
//
//     @Scheduled(fixedDelay = 1000) // 1초마다 실행
//     public void fixedDelay() throws InterruptedException {
//         LocalDateTime now = LocalDateTime.now(); // 현재 시간 가져오기
//         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//         String formattedNow = now.format(formatter); // 포맷팅
//
//         System.out.println("fixedDelay Start: " + formattedNow);
//         Thread.sleep(500); // 0.5초 지연
//     }
//
//     @Scheduled(fixedRate = 1000) // 1초마다 실행
//     public void fixedRate() throws InterruptedException {
//         LocalDateTime now = LocalDateTime.now(); // 현재 시간 가져오기
//         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//         String formattedNow = now.format(formatter); // 포맷팅
//
//         Thread.sleep(500); // 0.5초 지연
//         System.out.println("fixedRate Start: " + formattedNow);
//     }
//
//     @Scheduled(fixedRate = 5000, initialDelay = 3000) // 3초 후 5초마다 실행
//     public void initialDelay() throws InterruptedException {
//         LocalDateTime now = LocalDateTime.now(); // 현재 시간 가져오기
//         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//         String formattedNow = now.format(formatter); // 포맷팅
//
//         Thread.sleep(500); // 0.5초 지연
//         System.out.println("initialDelay Start: " + formattedNow);
//     }
//
//     @Scheduled(cron = "*/10 * * * * *") // 10초마다 실행
//     public void cron() throws InterruptedException {
//         LocalDateTime now = LocalDateTime.now(); // 현재 시간 가져오기
//         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//         String formattedNow = now.format(formatter); // 포맷팅
//
//         Thread.sleep(500); // 0.5초 지연
//         System.out.println("cron Start: " + formattedNow);
//     }
//
//
// }
