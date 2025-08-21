package bwg.jobTracker.job_tracker.controller;

import bwg.jobTracker.job_tracker.dto.CompanyCreateRequest;
import bwg.jobTracker.job_tracker.dto.CompanyDTO;
import bwg.jobTracker.job_tracker.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyDTO add(@RequestBody CompanyCreateRequest request) {
        return this.companyService.add(request);
    }

    @GetMapping("/{name}")
    public CompanyDTO findByName(@RequestParam String name) {
        return this.companyService.findByName(name);
    }

    @GetMapping
    public List<CompanyDTO> findAll() {
       return this.companyService.findAll();
    }
}
