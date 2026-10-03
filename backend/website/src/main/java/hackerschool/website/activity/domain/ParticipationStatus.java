package hackerschool.website.activity.domain;

public enum ParticipationStatus {
    PENDING_PAYMENT, // participant has registered but not paid yet
    CONFIRMED,            // participant has paid and is confirmed
    CANCELLED        // participant has cancelled their registration
}
