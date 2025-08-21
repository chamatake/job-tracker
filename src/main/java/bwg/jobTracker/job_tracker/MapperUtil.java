package bwg.jobTracker.job_tracker;

import bwg.jobTracker.job_tracker.dto.CompanyDTO;
import bwg.jobTracker.job_tracker.entity.Company;

public class MapperUtil {

    public static CompanyDTO toCompanyDTO(Company company) {
        return new CompanyDTO(
                company.getId(),
                company.getName()
        );
    }
}
