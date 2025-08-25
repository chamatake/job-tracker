package bwg.jobTracker.job_tracker;

import bwg.jobTracker.job_tracker.dto.CompanyDTO;
import bwg.jobTracker.job_tracker.dto.InterviewDTO;
import bwg.jobTracker.job_tracker.dto.JobPostingDTO;
import bwg.jobTracker.job_tracker.dto.ReferralSourceDTO;
import bwg.jobTracker.job_tracker.entity.*;
import bwg.jobTracker.job_tracker.enums.InterviewType;

import java.time.LocalDate;

public class MapperUtil {

    public static Company toCompany(CompanyDTO dto) {
        Company company = new Company();
        company.setId(dto.id());
        company.setName(dto.name());
        return company;
    }

    public static CompanyDTO toCompanyDTO(Company company) {
        return new CompanyDTO(
                company.getId(),
                company.getName()
        );
    }

    public static InterviewDTO toInterviewDTO(Interview interview) {
        return new InterviewDTO(
                interview.getId(),
                interview.getJobApplication(),
                interview.getInterviewType(),
                interview.getInterviewDate(),
                interview.getNotes()
        );
    }

    public static JobPostingDTO toJobPostingDTO(JobPosting jobPosting) {
        return new JobPostingDTO(
            jobPosting.getId(),
            jobPosting.getCompany(),
            jobPosting.getTitle(),
            jobPosting.getRequisitionId(),
            jobPosting.getUrl(),
            jobPosting.getSalaryRangeMin(),
            jobPosting.getSalaryRangeMax(),
            jobPosting.getOfficeSituation(),
            jobPosting.getRequiredTech(),
            jobPosting.getPreferredTech()
        );
    }

    public static ReferralSourceDTO toReferralSourceDTO(ReferralSource referralSource) {
        return new ReferralSourceDTO(
                referralSource.getId(),
                referralSource.getName(),
                referralSource.getReferralSourceType()
        );
    }
}
