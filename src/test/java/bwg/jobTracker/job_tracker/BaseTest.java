package bwg.jobTracker.job_tracker;

import bwg.jobTracker.job_tracker.entity.*;
import bwg.jobTracker.job_tracker.enums.ApplicationStatusType;
import bwg.jobTracker.job_tracker.enums.OfficeSituation;
import bwg.jobTracker.job_tracker.enums.ReferralSourceType;

import java.time.LocalDate;
import java.util.List;

public abstract class BaseTest {
    protected static final Long APPLICATION_STATUS_ID = 6666L;
    protected static final String COMPANY_NAME = "Stark Industries";
    protected static final String COMPANY_NAME_JUNK = "junky junk";
    protected static final String COVER_LETTER_FILENAME = "SomeCoverLetter.pdf";
    protected static final LocalDate DATE_e = LocalDate.parse("2025-02-07");
    protected static final LocalDate DATE_g = LocalDate.parse("2025-09-08");
    protected static final LocalDate DATE_PI = LocalDate.parse("2025-03-14");
    protected static final LocalDate DATE_PLANCK = LocalDate.parse("2026-06-06");
    protected static final Long INTERVIEW_ID = 914L;
    protected static final Long JOB_APPLICATION_ID = 12345L;
    protected static final String POSTING_TITLE = "employee of the month job";
    protected static final String POSTING_URL = "https://ironmansux.com/jobs";
    protected static final String REFERRAL_NAME_BORING = "source name";
    protected static final String REFERRAL_NAME_GANDALF = "Gandalf the Gray";
    protected static final String REQUISITION_ID = "12345-R";
    protected static final String RESUME_FILENAME = "MyBestResumeEver.docx";
    protected static final Integer SALARY_MAX = 250000;
    protected static final Integer SALARY_MIN = 125000;

    protected JobApplication makeJobApplicationDummy(Long id, JobPosting requestPosting) {
        JobApplication added = makeJobApplicationDummyNoStatuses(id, requestPosting);
        ApplicationStatus currentStatus = TestDataUtil.makeApplicationStatus(ApplicationStatusType.APPLIED, added, null);
        List<ApplicationStatus> statuses = TestDataUtil.makeApplicationStatusList(added, 3);
        statuses.add(currentStatus);
        added.setApplicationStatuses(statuses);
        added.setCurrentStatus(currentStatus);

        return added;
    }

    protected JobApplication makeJobApplicationDummyFirstStatus(Long id, JobPosting requestPosting, ApplicationStatusType firstStatusType) {
        JobApplication added = makeJobApplicationDummyNoStatuses(id, requestPosting);
        added.updateStatus(firstStatusType);

        return added;
    }

    protected JobApplication makeJobApplicationDummyNoStatuses(Long id, JobPosting requestPosting) {
        JobApplication added = new JobApplication();
        added.setId(id);
        added.setJobPosting(requestPosting);
        added.setAppliedDate(DATE_PI);
        added.setResumeFilename(RESUME_FILENAME);
        added.setCoverLetterFilename(COVER_LETTER_FILENAME);

        return added;
    }

    protected JobPosting makeJobPostingDummy(Company company, ReferralSource referralSource, OfficeSituation officeSituation) {
        JobPosting posting = new JobPosting();
        posting.setCompany(company);
        posting.setTitle(POSTING_TITLE);
        posting.setRequisitionId(REQUISITION_ID);
        posting.setPostingUrl(POSTING_URL);
        posting.setSalaryRangeMin(SALARY_MIN);
        posting.setSalaryRangeMax(SALARY_MAX);
        posting.setOfficeSituation(officeSituation);
        posting.setReferralSource(referralSource);

        return posting;
    }

    protected JobPosting makeJobPostingDummyGandalf() {
        return makeJobPostingDummy(
                new Company(COMPANY_NAME),
                new ReferralSource(REFERRAL_NAME_GANDALF, ReferralSourceType.INTERNAL),
                OfficeSituation.REMOTE
        );
    }
}
