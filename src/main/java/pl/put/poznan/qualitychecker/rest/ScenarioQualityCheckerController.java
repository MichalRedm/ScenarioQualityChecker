package pl.put.poznan.qualitychecker.rest;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.qualitychecker.logic.*;

/** Class implementing logic for handling requests to the REST API. */
@RestController
@RequestMapping("/")
public class ScenarioQualityCheckerController {

    private static final Logger logger = LoggerFactory.getLogger(ScenarioQualityCheckerController.class);

    /**
     * Handles GET requests to the REST API.
     * @param jsonBody JSON string representing request body.
     * @return Response from the application.
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> get(@RequestBody String jsonBody) {
        return post(jsonBody);
    }

    /**
     * Handles POST requests to the REST API.
     * @param jsonBody JSON string representing request body.
     * @return Response from the application.
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> post(@RequestBody String jsonBody) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ScenarioStepComponent.class, new ScenarioStepComponentAdapter());
        Gson gson = gsonBuilder.create();
        APIInput input = gson.fromJson(jsonBody, APIInput.class);

        var qualityChecker = new ScenarioQualityChecker(input.getScenario());
        try {
            var result = qualityChecker.executeActions(input.getActions());
            return new ResponseEntity<>(gson.toJson(result), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}


