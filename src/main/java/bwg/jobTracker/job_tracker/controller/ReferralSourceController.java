package bwg.jobTracker.job_tracker.controller;

import bwg.jobTracker.job_tracker.dto.ReferralSourceCreateRequest;
import bwg.jobTracker.job_tracker.dto.ReferralSourceDTO;
import bwg.jobTracker.job_tracker.entity.ReferralSource;
import bwg.jobTracker.job_tracker.enums.ReferralSourceType;
import bwg.jobTracker.job_tracker.exception.InvalidReferralSourceTypeException;
import bwg.jobTracker.job_tracker.service.ReferralSourceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/referralSource")
public class ReferralSourceController {
    private final ReferralSourceService referralSourceService;

    public ReferralSourceController(ReferralSourceService service) {
        this.referralSourceService = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReferralSourceDTO add(@RequestBody ReferralSourceCreateRequest request) {
        return this.referralSourceService.add(request);
    }

    @GetMapping
    public List<ReferralSourceDTO> findAll() {
        return this.referralSourceService.findAll();
    }

    @GetMapping("/{referralSourceType}")
    public List<ReferralSourceDTO> findAllByReferralSourceType(@RequestParam String typeString) {
        try {
            ReferralSourceType referralSourceType = ReferralSourceType.valueOf(typeString.toUpperCase());
            return this.referralSourceService.findAllBySourceType(referralSourceType);
        } catch (IllegalArgumentException ex) {
            throw new InvalidReferralSourceTypeException("invalid referral source type: " + typeString);
        }
    }
}
