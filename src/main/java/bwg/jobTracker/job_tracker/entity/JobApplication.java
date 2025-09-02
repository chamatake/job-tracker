package bwg.jobTracker.job_tracker.entity;

import bwg.jobTracker.job_tracker.enums.ApplicationStatusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "job_application")
@NoArgsConstructor
@Getter
@Setter
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_posting_id", unique = true)
    private JobPosting jobPosting;

    @OneToMany(mappedBy = "jobApplication", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ApplicationStatus> applicationStatuses = new HashSet<>();

    @Column(name = "current_status_type")
    private ApplicationStatusType currentStatusType;

    @Column(name = "applied_date")
    private LocalDate appliedDate;

    @Column(name = "resume_filename")
    private String resumeFilename;

    @Column(name = "cover_letter_filename")
    private String coverLetterFilename;


    @Version
    @Column(name = "VERSION")
    private Long version;

    public void updateStatus(ApplicationStatus newStatus) {
        if (this.applicationStatuses.stream()
                .map(ApplicationStatus::getApplicationStatusType)
                .noneMatch(type -> type.equals(newStatus.getApplicationStatusType()))
        ) {
            this.applicationStatuses.add(newStatus);
            newStatus.setJobApplication(this);
            this.currentStatusType = newStatus.getApplicationStatusType();
        }
    }

    public Set<ApplicationStatus> getApplicationStatuses() {
        return Set.copyOf(this.applicationStatuses);
    }

    public static class Builder {
        private Long id;
        private JobPosting jobPosting;
        private Set<ApplicationStatus> applicationStatuses = new HashSet<>();
        private ApplicationStatusType currentStatusType;
        private LocalDate appliedDate;
        private String resumeFilename;
        private String coverLetterFilename;

        public Builder create() {
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

        public Builder withApplicationStatuses(Set<ApplicationStatus> statuses) {
            this.applicationStatuses = Set.copyOf(statuses);
            return this;
        }

/*        public Builder withCurrentStatus(ApplicationStatus status) {
            this.currentStatus = status;
            return this;
        }*/

        public Builder withCurrentStatusType(ApplicationStatusType statusType) {
            this.currentStatusType = statusType;
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
            application.setApplicationStatuses(Set.copyOf(this.applicationStatuses));
            application.setCurrentStatusType(this.currentStatusType);
            application.setAppliedDate(this.appliedDate);
            application.setResumeFilename(this.resumeFilename);
            application.setCoverLetterFilename(this.coverLetterFilename);

            return application;
        }
    }
}
