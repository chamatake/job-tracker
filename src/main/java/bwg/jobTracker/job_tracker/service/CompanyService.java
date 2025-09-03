package bwg.jobTracker.job_tracker.service;

import bwg.jobTracker.job_tracker.MapperUtil;
import bwg.jobTracker.job_tracker.dto.request.CompanyCreateRequest;
import bwg.jobTracker.job_tracker.dto.CompanyDTO;
import bwg.jobTracker.job_tracker.entity.Company;
import bwg.jobTracker.job_tracker.exception.CompanyNotFoundException;
import bwg.jobTracker.job_tracker.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public CompanyDTO add(CompanyCreateRequest request) {
        Company company = new Company();
        company.setCompanyName(request.getName());
        Company created = this.companyRepository.save(company);

        return MapperUtil.toCompanyDTO(created);
    }

    public CompanyDTO findById(Long id) {
        Company existing = this.companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company search by id failed. id: " + id));

        return MapperUtil.toCompanyDTO(existing);
    }

    public CompanyDTO findByCompanyName(String name) {
        Company existing = this.companyRepository.findByCompanyName(name)
                .orElseThrow(() -> new CompanyNotFoundException("Company search by name failed. name: " + name));

        return MapperUtil.toCompanyDTO(existing);
    }

    public List<CompanyDTO> findAll() {
        return this.companyRepository.findAll().stream()
                .map(MapperUtil::toCompanyDTO)
                .toList();
    }
}
