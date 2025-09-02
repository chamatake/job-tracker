package bwg.jobTracker.job_tracker.controller;

import bwg.jobTracker.job_tracker.dto.CompanyCreateRequest;
import bwg.jobTracker.job_tracker.dto.CompanyDTO;
import bwg.jobTracker.job_tracker.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyDTO create(@RequestBody CompanyCreateRequest request) {
        return this.companyService.add(request);
    }

    @GetMapping("/{id}")
    public CompanyDTO getById(@PathVariable Long id) {
        return this.companyService.findById(id);
    }

    @GetMapping(params = "companyName")
    public CompanyDTO getByCompanyName(@RequestParam String companyName) {
        return this.companyService.findByCompanyName(companyName);
    }

    @GetMapping
    public List<CompanyDTO> getAll() {
       return this.companyService.findAll();
    }
}
