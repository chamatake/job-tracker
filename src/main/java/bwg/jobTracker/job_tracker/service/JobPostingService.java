package bwg.jobTracker.job_tracker.service;

import bwg.jobTracker.job_tracker.MapperUtil;
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

    public JobPostingService(JobPostingRepository repository) {
        this.jobPostingRepository = repository;
    }

    public JobPostingDTO add(JobPostingCreateRequest request) {
        JobPosting posting = new JobPosting();
        posting.setCompany(request.getCompany());
        posting.setTitle(request.getTitle());
        posting.setRequisitionId(request.getRequisitionId());
        posting.setPostingUrl(request.getUrl());
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
        return this.jobPostingRepository.findAllByCompany(company).stream()
                .map(MapperUtil::toJobPostingDTO)
                .toList();
    }

    public List<JobPostingDTO> findAllByCompanyName(String companyName) {
        return this.jobPostingRepository.findAllByCompany_name(companyName).stream()
                .map(MapperUtil::toJobPostingDTO)
                .toList();
    }

    public JobPostingDTO findById(Long id) {
        JobPosting existing = this.jobPostingRepository.findById(id)
                .orElseThrow(() -> new JobPostingNotFoundException("No job posting found for id = " + id));

        return MapperUtil.toJobPostingDTO(existing);
    }
}
