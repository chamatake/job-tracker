package bwg.jobTracker.job_tracker.entity;

import bwg.jobTracker.job_tracker.enums.InterviewType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "interview")
@NoArgsConstructor
@Getter
@Setter
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_application_id")
    private JobApplication jobApplication;


    @Enumerated(value = EnumType.STRING)
    @Column(name = "interview_type")
    private InterviewType interviewType;

    @Column(name = "interview_date")
    private LocalDate interviewDate;

    @Column(name = "notes")
    private String notes;


    @Version
    @Column(name = "VERSION")
    private Long version;
}
