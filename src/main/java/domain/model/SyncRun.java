package domain.model;

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

        if (accountId == null) { throw new IllegalArgumentException("accountId is required"); }

        if (startTime == null) { throw new NullPointerException("Start time is required"); }

        if (endTime != null && endTime.isBefore(startTime)) { throw new NullPointerException("endTime must be >= startTime"); }

        if  (status == null) { throw new NullPointerException("Status is required"); }

        if (status == Status.FAILED && errorMessage == null) { throw new NullPointerException("errorMessage is required"); }

        if (status == Status.IN_PROGRESS && endTime != null) { throw new NullPointerException("IN_PROGRESS sync cannot a endTime"); }
    }
}
