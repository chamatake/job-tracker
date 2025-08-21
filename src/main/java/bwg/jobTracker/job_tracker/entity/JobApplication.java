package bwg.jobTracker.job_tracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "job_application")
@NoArgsConstructor
@Getter
@Setter
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
    private ApplicationStatus applicationStatus;


    @Column(name = "applied_date")
    private LocalDate appliedDate;

    @Column(name = "resume_filename")
    private String resumeFilename;

    @Column(name = "cover_letter_filename")
    private String coverLetterFilename;


    @Version
    @Column(name = "VERSION")
    private Long version;

    public class Builder {
        private Long id;
        private JobPosting jobPosting;
        private ReferralSource referralSource;
        private ApplicationStatus applicationStatus;
        private LocalDate appliedDate;
        private String resumeFilename;
        private String coverLetterFilename;

        public Builder builder() {
            return new Builder();
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withJobPosting(JobPosting posting) {
            this.jobPosting = posting;
            return this;
        }

        public Builder withReferralSource(ReferralSource referralSource) {
            this.referralSource = referralSource;
            return this;
        }

        public Builder withApplicationStatus(ApplicationStatus status) {
            this.applicationStatus = status;
            return this;
        }

        public Builder withAppliedDate(LocalDate appliedDate) {
            this.appliedDate = appliedDate;
            return this;
        }

        public Builder withResumeFilename(String resumeFilename) {
            this.resumeFilename = resumeFilename;
            return this;
        }

        public Builder withCoverLetterFileName(String coverLetterFilename) {
            this.coverLetterFilename = coverLetterFilename;
            return this;
        }

        public JobApplication build() {
            JobApplication application = new JobApplication();
            application.setId(this.id);
            application.setJobPosting(this.jobPosting);
            application.setReferralSource(this.referralSource);
            application.setApplicationStatus(this.applicationStatus);
            application.setAppliedDate(this.appliedDate);
            application.setResumeFilename(this.resumeFilename);
            application.setCoverLetterFilename(this.coverLetterFilename);

            return application;
        }
    }
}
