package hackerschool.website.activity.controller;

import hackerschool.website.activity.dto.ActivityRequest;
import hackerschool.website.activity.service.ActivityService;
import hackerschool.website.activity.dto.ActivityResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ActivityController{

    private final ActivityService activityService;

    @GetMapping("/activities")
    public List<ActivityResponse> listPublished() {
        return activityService.listPublishedActivities();}

    @PostMapping("/activities")
    public ActivityResponse createActivity(@Valid @RequestBody ActivityRequest request, @RequestParam Long adminId) {
        return activityService.createActivity(request, adminId);
    }

    @PutMapping("/activities/{id}")
    public ActivityResponse updateActivity(@PathVariable Long id, @Valid @RequestBody ActivityRequest request) {
        return activityService.updateActivity(id, request);}




}