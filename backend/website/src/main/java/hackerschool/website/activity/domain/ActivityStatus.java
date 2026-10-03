package hackerschool.website.activity.domain;

public enum ActivityStatus {
    DRAFT,      // still being edited, hidden from the public
    PUBLISHED,  // visible and open for registration until the deadline
    CANCELLED   // kept for history; participants should be refunded
}
