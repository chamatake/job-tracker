package bwg.jobTracker.job_tracker.service;

import bwg.jobTracker.job_tracker.MapperUtil;
import bwg.jobTracker.job_tracker.dto.CompanyDTO;
import bwg.jobTracker.job_tracker.dto.JobPostingCreateRequest;
import bwg.jobTracker.job_tracker.dto.JobPostingDTO;
import bwg.jobTracker.job_tracker.entity.Company;
import bwg.jobTracker.job_tracker.entity.JobPosting;
import bwg.jobTracker.job_tracker.exception.JobPostingNotFoundException;
import bwg.jobTracker.job_tracker.repository.JobPostingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobPostingService {
    private final JobPostingRepository jobPostingRepository;
    private final CompanyService companyService;

    public JobPostingService(JobPostingRepository repository, CompanyService companyService) {
        this.jobPostingRepository = repository;
        this.companyService = companyService;
    }

    public JobPostingDTO add(JobPostingCreateRequest request) {
        JobPosting posting = new JobPosting();
        posting.setCompany(request.getCompany());
        posting.setTitle(request.getTitle());
        posting.setRequisitionId(request.getRequisitionId());
        posting.setUrl(request.getUrl());
        posting.setSalaryRangeMin(request.getSalaryRangeMin());
        posting.setSalaryRangeMax(request.getSalaryRangeMax());
        posting.setOfficeSituation(request.getOfficeSituation());
        posting.setRequiredTech(request.getRequiredTech());
        posting.setPreferredTech(request.getPreferredTech());

        JobPosting added = this.jobPostingRepository.save(posting);
        return MapperUtil.toJobPostingDTO(added);
    }

    public List<JobPostingDTO> findAll() {
        return this.jobPostingRepository.findAll().stream()
                .map(MapperUtil::toJobPostingDTO)
                .toList();
    }

    public List<JobPostingDTO> findAllByCompany(Company company) {
        return this.jobPostingRepository.findAllByCompany(company)
                .orElseThrow(() -> new JobPostingNotFoundException("No job postings were found for company = " + company.getName()))
                .stream()
                .map(MapperUtil::toJobPostingDTO)
                .toList();
    }

    public List<JobPostingDTO> findAllByCompanyName(String companyName) {
        CompanyDTO existingDTO = this.companyService.findByName(companyName);
        Company existing = MapperUtil.toCompany(existingDTO);

        return findAllByCompany(existing);
    }
}
