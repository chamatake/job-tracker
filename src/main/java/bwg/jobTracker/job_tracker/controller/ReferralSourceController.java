package bwg.jobTracker.job_tracker.controller;

import bwg.jobTracker.job_tracker.dto.request.ReferralSourceCreateRequest;
import bwg.jobTracker.job_tracker.dto.ReferralSourceDTO;
import bwg.jobTracker.job_tracker.enums.ReferralSourceType;
import bwg.jobTracker.job_tracker.service.ReferralSourceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/referral-sources")
public class ReferralSourceController {
    private final ReferralSourceService referralSourceService;

    public ReferralSourceController(ReferralSourceService service) {
        this.referralSourceService = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReferralSourceDTO create(@RequestBody ReferralSourceCreateRequest request) {
        return this.referralSourceService.add(request);
    }

    @GetMapping
    public List<ReferralSourceDTO> getAll() {
        return this.referralSourceService.findAll();
    }

    @GetMapping(params = "referralSourceType")
    public List<ReferralSourceDTO> getAllByReferralSourceType(@RequestParam String typeString) {
        try {
            ReferralSourceType referralSourceType = ReferralSourceType.valueOf(typeString.toUpperCase());
            return this.referralSourceService.findAllBySourceType(referralSourceType);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "invalid referral source type: " + typeString,
                    ex);
        }
    }
}
