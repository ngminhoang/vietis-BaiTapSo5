package com.nqt.cs3.batch;

import com.nqt.cs3.domain.Student;
import com.nqt.cs3.repository.StudentRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.builder.JobFlowBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableBatchProcessing
@Component
public class StudentBatchConfig {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    // Định nghĩa Reader để lấy danh sách sinh viên mới trong tuần
    @Bean
    public RepositoryItemReader<Student> studentItemReader() {
        RepositoryItemReader<Student> reader = new RepositoryItemReader<>();
        reader.setRepository(studentRepository);
        reader.setMethodName("findAllByCreatedAtBetween");

        Instant now = Instant.now();
        Instant lastWeek = now.minus(7, ChronoUnit.DAYS);
        reader.setArguments(List.of(lastWeek, now));

        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("id", Sort.Direction.ASC);
        reader.setSort(sortMap);

        return reader;
    }

    // Processor (có thể bỏ qua nếu không cần xử lý thêm)
    @Bean
    public ItemProcessor<Student, Student> studentItemProcessor() {
        return student -> student; // Không xử lý, trả về chính Student
    }

    // Writer để ghi dữ liệu ra file CSV
    @Bean
    public FlatFileItemWriter<Student> studentItemWriter() {
        return new FlatFileItemWriterBuilder<Student>()
                .name("studentItemWriter")
                .resource(new FileSystemResource("students_weekly.csv"))
                .delimited()
                .delimiter(",")
                .names("id", "fullName", "gender", "email", "dateOfBirth", "createdAt")
                .build();
    }

    // Định nghĩa Step trong Job sử dụng JobRepository và PlatformTransactionManager
    @Bean
    public Step studentStep(ItemReader<Student> reader,
                            ItemProcessor<Student, Student> processor,
                            ItemWriter<Student> writer) {
        return new StepBuilder("studentStep", jobRepository)
                .<Student, Student>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    // Định nghĩa Job chạy Step
    @Bean
    public Job studentJob(Step studentStep) {
        return new JobBuilder("studentJob", jobRepository)
                .start(studentStep)
                .build();
    }
}
