package bwg.jobTracker.job_tracker.entity;

import bwg.jobTracker.job_tracker.enums.InterviewType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "interview")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_application_id")
    @NonNull private JobApplication jobApplication;


    @Enumerated(value = EnumType.STRING)
    @Column(name = "interview_type")
    @NonNull private InterviewType interviewType;

    @Column(name = "interview_date")
    private LocalDate interviewDate;

    @Column(name = "notes")
    private String notes;


    @Version
    @Column(name = "VERSION")
    private Long version;
}
