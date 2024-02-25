package com.davidgeorgewilliams.workflow.threads;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.experimental.NonFinal;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

@Accessors(fluent = true, chain = true)
@Builder
@Value
public class ThreadLocalTime {
    LocalDateTime localDateTime;
    ZoneId zoneId;

    public static ThreadLocalTime of() {
        return ThreadLocalTime.of(ZoneId.systemDefault());
    }

    public static ThreadLocalTime of(@NonNull final ZoneId zoneId) {
        return new ThreadLocalTime(LocalDateTime.of(LocalDate.now(zoneId), LocalTime.now(zoneId)), zoneId);
    }

    public double duration(@NonFinal final ThreadLocalTime threadLocalTime) {
        return Math.abs(Duration.between(instant(), threadLocalTime.instant()).toMillis());
    }

    public Instant instant() {
        return localDateTime().toInstant(zoneId().getRules().getOffset(localDateTime()));
    }
}
