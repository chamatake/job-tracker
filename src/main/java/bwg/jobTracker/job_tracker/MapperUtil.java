package bwg.jobTracker.job_tracker;

import bwg.jobTracker.job_tracker.dto.*;
import bwg.jobTracker.job_tracker.entity.*;

import java.util.Objects;

public class MapperUtil {

    public static ApplicationStatusDTO toApplicationStatusDTO(ApplicationStatus status) {
        return new ApplicationStatusDTO(
                status.getId(),
                status.getJobApplication().getId(),
                status.getApplicationStatusType(),
                status.getActiveDate(),
                status.getInactiveDate()
        );
    }

    public static CompanyDTO toCompanyDTO(Company company) {
        return new CompanyDTO(
                company.getId(),
                company.getCompanyName()
        );
    }

    public static InterviewDTO toInterviewDTO(Interview interview) {
        Objects.requireNonNull(interview.getJobApplication().getCurrentStatus(),
                "Interview.jobApplication must have a non-null currentStatus before converting to DTO");

        return new InterviewDTO(
                interview.getId(),
                toJobApplicationDTO(interview.getJobApplication()),
                interview.getInterviewType(),
                interview.getInterviewDate(),
                interview.getNotes()
        );
    }

    public static JobApplicationDTO toJobApplicationDTO(JobApplication application) {
        Objects.requireNonNull(application.getCurrentStatus(),
                "JobApplication must have a non-null currentStatus before converting to DTO");

        return new JobApplicationDTO(
                application.getId(),
                toJobPostingDTO(application.getJobPosting()),
                application.getApplicationStatuses().stream()
                        .map(MapperUtil::toApplicationStatusDTO)
                        .toList(),
                application.getCurrentStatus().getId(),
                application.getAppliedDate(),
                application.getResumeFilename(),
                application.getCoverLetterFilename()
        );
    }

    public static JobPostingDTO toJobPostingDTO(JobPosting jobPosting) {
        return new JobPostingDTO(
            jobPosting.getId(),
            toCompanyDTO(jobPosting.getCompany()),
            jobPosting.getTitle(),
            jobPosting.getRequisitionId(),
            jobPosting.getPostingUrl(),
            jobPosting.getSalaryRangeMin(),
            jobPosting.getSalaryRangeMax(),
            jobPosting.getOfficeSituation(),
            jobPosting.getRequiredTech(),
            jobPosting.getPreferredTech(),
            toReferralSourceDTO(jobPosting.getReferralSource())
        );
    }

    public static ReferralSourceDTO toReferralSourceDTO(ReferralSource referralSource) {
        return new ReferralSourceDTO(
                referralSource.getId(),
                referralSource.getReferralName(),
                referralSource.getReferralSourceType()
        );
    }
}
