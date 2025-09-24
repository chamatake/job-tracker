package bwg.jobTracker.job_tracker.entity;

import bwg.jobTracker.job_tracker.enums.ApplicationStatusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.*;

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

    @OneToMany(mappedBy = "jobApplication", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    private List<ApplicationStatus> applicationStatuses = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_status_id")
    private ApplicationStatus currentStatus;

    @Column(name = "applied_date")
    private LocalDate appliedDate;

    @Column(name = "resume_filename")
    private String resumeFilename;

    @Column(name = "cover_letter_filename")
    private String coverLetterFilename;


    @Version
    @Column(name = "VERSION")
    private Long version;


    public List<ApplicationStatus> getApplicationStatuses() {
        return List.copyOf(this.applicationStatuses);
    }

    /**
     * Helper method to assign a new status by passing in only the desired status type.
     * Sets inactiveDate on the old status and creates a new status to replace it.
     *
     * @param newStatusType the new status type
     */
    public void updateStatus(ApplicationStatusType newStatusType) {
        LocalDate now = LocalDate.now();
        ApplicationStatus active = this.currentStatus;

        if (active != null && active.getApplicationStatusType() == newStatusType && active.getInactiveDate() == null) {
            return; // no-op, the requested status change matches the current active status
        }

        // close current active status if any active status exists
        if (active != null && active.getInactiveDate() == null) {
            active.setInactiveDate(now);
        }

        // create new status and attach it
        ApplicationStatus next = new ApplicationStatus(this, newStatusType);
        next.setActiveDate(now);
        this.applicationStatuses.add(next);
        this.currentStatus = next;
    }


    public static class Builder {
        private Long id;
        private JobPosting jobPosting;
        private List<ApplicationStatus> applicationStatuses = new ArrayList<>();
        private ApplicationStatus currentStatus;
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

        public Builder withApplicationStatuses(List<ApplicationStatus> statuses) {
            this.applicationStatuses = List.copyOf(statuses);
            return this;
        }

        public Builder withCurrentStatus(ApplicationStatus status) {
            this.currentStatus = status;
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
            application.setApplicationStatuses(List.copyOf(this.applicationStatuses));
            application.setCurrentStatus(this.currentStatus);
            application.setAppliedDate(this.appliedDate);
            application.setResumeFilename(this.resumeFilename);
            application.setCoverLetterFilename(this.coverLetterFilename);

            return application;
        }
    }
}
