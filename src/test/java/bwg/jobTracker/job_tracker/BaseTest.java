package bwg.jobTracker.job_tracker;

import bwg.jobTracker.job_tracker.entity.Company;
import bwg.jobTracker.job_tracker.entity.JobPosting;
import bwg.jobTracker.job_tracker.entity.ReferralSource;
import bwg.jobTracker.job_tracker.enums.OfficeSituation;

import java.time.LocalDate;

public abstract class BaseTest {
    protected static final LocalDate DATE_PI = LocalDate.parse("2025-03-14");
    protected static final String COMPANY_NAME = "Stark Industries";
    protected static final String COMPANY_NAME_JUNK = "junky junk";
    protected static final String POSTING_TITLE = "employee of the month job";
    protected static final String REQUISITION_ID = "12345-R";
    protected static final String POSTING_URL = "https://ironmansux.com/jobs";
    protected static final Integer SALARY_MIN = 125000;
    protected static final Integer SALARY_MAX = 250000;
    protected static final String REFERRAL_NAME_BORING = "source name";
    protected static final String REFERRAL_NAME_GANDALF = "Gandalf the Gray";
    protected static final String RESUME_FILENAME = "MyBestResumeEver.docx";
    protected static final String COVER_LETTER_FILENAME = "SomeCoverLetter.pdf";

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
}
