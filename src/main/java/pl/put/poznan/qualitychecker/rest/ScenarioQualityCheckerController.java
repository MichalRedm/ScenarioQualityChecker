package pl.put.poznan.qualitychecker.rest;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.qualitychecker.logic.APIInput;
import pl.put.poznan.qualitychecker.logic.ScenarioQualityChecker;
import pl.put.poznan.qualitychecker.logic.ScenarioStepComponent;
import pl.put.poznan.qualitychecker.logic.ScenarioStepComponentDeserializer;

@RestController
@RequestMapping("/")
public class ScenarioQualityCheckerController {

    private static final Logger logger = LoggerFactory.getLogger(ScenarioQualityCheckerController.class);

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> get(@RequestBody String jsonBody) {
        return post(jsonBody);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> post(@RequestBody String jsonBody) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ScenarioStepComponent.class, new ScenarioStepComponentDeserializer());
        Gson gson = gsonBuilder.create();
        APIInput input = gson.fromJson(jsonBody, APIInput.class);

        var qualityChecker = new ScenarioQualityChecker(input.getScenario(), input.getActions().toArray(new String[0]));

        // TODO: return proper response
        return new ResponseEntity<>("{\"message\": \"Received JSON data successfully\"}", HttpStatus.OK);
    }

}


