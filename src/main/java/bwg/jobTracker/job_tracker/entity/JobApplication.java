package bwg.jobTracker.job_tracker.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "job_application")
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "job_posting_id")
    private JobPosting jobPosting;

    @ManyToOne
    @JoinColumn(name = "referral_source_id")
    private ReferralSource referralSource;

    @ManyToOne
    @JoinColumn(name = "application_status_id")
    private ApplicationStatus status;


    @Column(name = "applied_date")
    private LocalDate appliedDate;

    @Column(name = "resume_filename")
    private String resumeFilename;

    @Column(name = "cover_letter_filename")
    private String coverLetterFilename;


    @Version
    @Column(name = "VERSION")
    private Long version;

}
