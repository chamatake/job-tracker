package bwg.jobTracker.job_tracker;

import bwg.jobTracker.job_tracker.dto.request.JobPostingCreateRequest;
import bwg.jobTracker.job_tracker.entity.Company;
import bwg.jobTracker.job_tracker.entity.JobPosting;
import bwg.jobTracker.job_tracker.entity.ReferralSource;
import bwg.jobTracker.job_tracker.enums.OfficeSituation;
import bwg.jobTracker.job_tracker.enums.ReferralSourceType;
import bwg.jobTracker.job_tracker.enums.Technology;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class TestDataUtil {

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

    public static Company makeCompany() {
        Company company = new Company();
        company.setId(225522L);
        company.setCompanyName("Wayne Enterprises");
        return company;
    }

    public static Company copyCompany(Company original) {
        Company copy = new Company();
        copy.setId(original.getId());
        copy.setCompanyName(original.getCompanyName());
        return copy;
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
