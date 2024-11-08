package com.nqt.cs3.batch;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class BatchScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job studentJob;

    // Chạy job mỗi tuần (ví dụ: vào Chủ Nhật lúc 12:00 AM)
//    @Scheduled(cron = "0 0 0 * * SUN")
    @Scheduled(cron = "0 * * * * *")
    public void runStudentJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("startAt", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(studentJob, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

