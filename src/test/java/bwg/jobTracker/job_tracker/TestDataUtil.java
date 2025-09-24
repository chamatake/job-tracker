package bwg.jobTracker.job_tracker;

import bwg.jobTracker.job_tracker.dto.request.InterviewCreateRequest;
import bwg.jobTracker.job_tracker.dto.request.JobPostingCreateRequest;
import bwg.jobTracker.job_tracker.entity.*;
import bwg.jobTracker.job_tracker.enums.*;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class TestDataUtil {
    private static final LocalDate START_DATE = LocalDate.parse("2025-03-14");

    public static Company copyCompany(Company original) {
        Company copy = new Company();
        copy.setId(original.getId());
        copy.setCompanyName(original.getCompanyName());
        return copy;
    }

    public static JobPosting copyJobPosting(JobPosting original) {
        JobPosting copy = new JobPosting();
        copy.setId(original.getId());
        copy.setCompany(copyCompany(original.getCompany()));
        copy.setTitle(original.getTitle());
        copy.setRequisitionId(original.getRequisitionId());
        copy.setPostingUrl(original.getPostingUrl());
        copy.setSalaryRangeMin(original.getSalaryRangeMin());
        copy.setSalaryRangeMax(original.getSalaryRangeMax());
        copy.setOfficeSituation(original.getOfficeSituation());
        copy.setRequiredTech(Set.copyOf(original.getRequiredTech()));
        copy.setPreferredTech(Set.copyOf(original.getPreferredTech()));
        copy.setReferralSource(original.getReferralSource());
        return copy;
    }

    public static ApplicationStatus makeApplicationStatus(ApplicationStatusType type, JobApplication parent, @Nullable LocalDate activeDate) {
        ApplicationStatus status = new ApplicationStatus();
        status.setId(112233L);
        status.setJobApplication(parent);
        status.setApplicationStatusType(type);
        status.setActiveDate(Objects.isNull(activeDate) ? LocalDate.now() : activeDate);

        return status;
    }

    public static Set<ApplicationStatus> makeApplicationStatusSet(JobApplication parent, int count) {
        Set<ApplicationStatus> statuses = new HashSet<>();
        Random randomInts = new Random();
        ApplicationStatusType[] enumTypes = ApplicationStatusType.values();

        for (int i = 0; i <= count; i++) {
            ApplicationStatus singleStatus = new ApplicationStatus();
            singleStatus.setId(11011L + i);
            singleStatus.setJobApplication(parent);
            singleStatus.setActiveDate(START_DATE.plusDays(1 + i));
            singleStatus.setApplicationStatusType(enumTypes[randomInts.nextInt(enumTypes.length)]);
            statuses.add(singleStatus);
        }
        return statuses;
    }

    public static Company makeCompany() {
        Company company = new Company();
        company.setId(225522L);
        company.setCompanyName("Wayne Enterprises");
        return company;
    }

    public static Interview makeInterview(InterviewType type) {
        Interview interview = new Interview();
        interview.setJobApplication(new JobApplication());
        interview.setInterviewType(type);
        interview.setInterviewDate(START_DATE);
        interview.setNotes("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt");

        return interview;
    }

    public static InterviewCreateRequest makeInterviewRequest(InterviewType type) {
        InterviewCreateRequest request = new InterviewCreateRequest();
        request.setJobApplication(new JobApplication());
        request.setInterviewType(type);
        request.setInterviewDate(START_DATE);
        request.setNotes("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt");

        return request;
    }

    public static JobApplication makeJobApplication(OfficeSituation officeSituation, boolean withTech) {
        JobApplication application = new JobApplication();
        application.setJobPosting(
                withTech ? makeJobPostingWithTech(officeSituation, 3, 1)
                : makeJobPostingNoTech(officeSituation));

        Set<ApplicationStatus> appStatuses = makeApplicationStatusSet(application, 3);
        appStatuses.add(makeApplicationStatus(ApplicationStatusType.INTERVIEWING, application, null));

        application.setApplicationStatuses(appStatuses);

        application.setAppliedDate(START_DATE);
        application.setResumeFilename("MyBestResumeEver.docx");
        application.setCoverLetterFilename("SomeCoverLetter.pdf");
        return application;
    }

    public static JobPosting makeJobPostingNoTech(OfficeSituation officeSituation) {
        JobPosting posting = new JobPosting();
        posting.setCompany(makeCompany());
        posting.setTitle("employee of the month job");
        posting.setRequisitionId("12345-R");
        posting.setPostingUrl("https://ironmansux.com/jobs");
        posting.setSalaryRangeMin(125000);
        posting.setSalaryRangeMax(250000);
        posting.setOfficeSituation(officeSituation);
        posting.setReferralSource(makeReferralSource(ReferralSourceType.INTERNAL));
        return posting;
    }

    public static JobPosting makeJobPostingWithTech(OfficeSituation officeSituation, int reqTechCount, int prefTechCount) {
        JobPosting posting = makeJobPostingNoTech(officeSituation);
        posting.setRequiredTech(makeTechSet(reqTechCount));
        posting.setPreferredTech(makeTechSet(prefTechCount));
        return posting;
    }

    public static JobPostingCreateRequest makeJobPostingRequestNoTech(OfficeSituation officeSituation) {
        JobPostingCreateRequest request = new JobPostingCreateRequest();
        request.setCompany(makeCompany());
        request.setTitle("employee of the month job");
        request.setRequisitionId("12345-R");
        request.setUrl("https://ironmansux.com/jobs");
        request.setSalaryRangeMin(125000);
        request.setSalaryRangeMax(250000);
        request.setOfficeSituation(officeSituation);
        request.setReferralSource(makeReferralSource(ReferralSourceType.INTERNAL));
        return request;
    }

    public static JobPostingCreateRequest makeJobPostingRequestWithTech(OfficeSituation officeSituation, int reqTechCount, int prefTechCount) {
        JobPostingCreateRequest request = makeJobPostingRequestNoTech(officeSituation);
        request.setRequiredTech(makeTechSet(reqTechCount));
        request.setPreferredTech(makeTechSet(prefTechCount));
        return request;
    }

    public static ReferralSource makeReferralSource(ReferralSourceType type) {
        return new ReferralSource("Mx. Insider", type);
    }

    public static Set<Technology> makeTechSet(int count) {
        Set<Technology> techs = new HashSet<>();
        Random randomInts = new Random();
        Technology[] enumTechs = Technology.values();

        for (int i = 0; i <= count; i++) {
            techs.add(enumTechs[randomInts.nextInt(enumTechs.length)]);
        }
        return techs;
    }
}
