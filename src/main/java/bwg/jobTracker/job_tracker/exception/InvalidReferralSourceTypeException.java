package bwg.jobTracker.job_tracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidReferralSourceTypeException extends IllegalArgumentException {
    public InvalidReferralSourceTypeException(String message) {
        super(message);
    }
}
