package org.launchcode.techjobs.mvc.controllers;

import org.launchcode.techjobs.mvc.models.Job;
import org.launchcode.techjobs.mvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static org.launchcode.techjobs.mvc.controllers.ListController.columnChoices;


/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @GetMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    // TODO #3 - Create a handler to process a search request and render the updated search view.

    @PostMapping(value = "results")
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm) {
        ArrayList<Job> jobs;
        if (!searchType.equals("all") && !searchType.equals(null)) {
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);
            model.addAttribute("jobs", jobs);
            model.addAttribute("title", "Jobs with " + columnChoices.get(searchType) + ": " + searchTerm);
            model.addAttribute("columns", columnChoices);
            return "list-jobs";
        }
        if (searchType.equals("all") && searchTerm.equals("all")) {
            jobs = JobData.findAll();
            model.addAttribute("jobs", jobs);
            model.addAttribute("title", "All jobs" + "Search Term: " + searchTerm);
        } else {
            jobs = JobData.findAll();
            ArrayList<Job> matchingJobs = new ArrayList<>();
            for (int i =0; i<jobs.size(); i++){
                if (jobs.get(i).toString().toUpperCase().contains(searchTerm.toUpperCase())){
                    matchingJobs.add(jobs.get(i));
                }
            model.addAttribute("jobs", matchingJobs);
            model.addAttribute("title", "All jobs" + "Search Term: " + searchTerm);
            }
        }




        return "list-jobs";
    }
}
