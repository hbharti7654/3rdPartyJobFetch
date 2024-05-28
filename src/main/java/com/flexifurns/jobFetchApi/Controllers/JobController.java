package com.flexifurns.jobFetchApi.Controllers;

import com.flexifurns.jobFetchApi.Model.Job;
import com.flexifurns.jobFetchApi.Services.JobService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> getJobsFromApi() throws JSONException {
        System.out.println("Inside");
        String apiUrl = "https://www.themuse.com/api/public/jobs?page=1";
        RestTemplate restTemplate = new RestTemplate();

        List<Job> jobs = new ArrayList<>();
        //jobService.getJobs();
        String jsonResponse = restTemplate.getForObject(apiUrl, String.class);

        // Convert JSON response to JSONArray
        //JSONArray jsonArray = new JSONArray(jsonResponse);
        JSONObject json = new JSONObject(jsonResponse);
        JSONArray jobResObject = (JSONArray) json.get("results");
        //JSONArray jobList = new JSONArray(jobResObject);


        //JS6ONArray jsonArray2 = new JSONArray(jobList);
        // Loop through the JSONArray
        for (int i = 0; i < jobResObject.length(); i++) {
            JSONObject jobObject = jobResObject.getJSONObject(i);

            //Job job = new Job();
            Job outputJob = new Job();
            outputJob.setName(jobObject.getString("name"));
            outputJob.setType(jobObject.getString("type"));
            // jobs.add(job);


            outputJob.setId(Integer.parseInt(jobObject.getString("id")));

            JSONArray locations = new JSONArray(jobObject.getString("locations"));
            JSONObject location = locations.getJSONObject(0);
            outputJob.setLocation(location.getString("name"));

            outputJob.setCategory(jobObject.getString("categories"));
            //outputJob.setTags(jobObject.getString("tags"));

            JSONObject company = new JSONObject(jobObject.getString("company"));

            outputJob.setCompany(company.getString("name"));
            jobs.add(outputJob);
            System.out.println("----------------------------------");
        }
        // You can further process the JSON response here if needed
        //System.out.println(jsonResponse);

        return ResponseEntity.ok(jobs);
    }
}
