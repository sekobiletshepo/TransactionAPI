package domain.model;

import domain.exception.DomainException;
import lombok.Builder;
import lombok.With;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
@With

public class SyncRun {

    private Long syncId;
    private Long accountId;
    private Instant startTime;
    private Instant endTime;
    private Status status;
    private int transactionFetched;
    private String errorMessage;

    public enum Status {IN_PROGRESS, SUCCESS, FAILED}

    public SyncRun () {

        if (accountId == null) {
            throw new DomainException(
                    "ACCOUNT_ID_NOT_FOUND", null + " Account not found");
        }

        if (startTime == null) {
            throw new DomainException(
                    "START_TIME_NOT_FOUND", null + " Start time required");
        }

        if (endTime != null && endTime.isBefore(startTime)) {
            throw new DomainException(
                    "END_TIME_NULL", null + "End time must be before start time");
        }

        if (status == null) {
            throw new DomainException(
                    "STATUS_NOT_FOUND", null + " Status not found");
        }

        if (status == Status.FAILED && errorMessage == null) {
            throw new DomainException(
                    "ERROR_NOT_FOUND", null + " Error message not found");
        }

        if (status == Status.IN_PROGRESS && endTime != null) {
            throw new DomainException(
                    "END_TIME_NULL", null + "IN_PROGRESS must be before end time"
            );
        }
    }
}

