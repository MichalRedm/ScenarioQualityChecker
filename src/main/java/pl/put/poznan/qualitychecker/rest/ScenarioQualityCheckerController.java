package pl.put.poznan.qualitychecker.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.qualitychecker.logic.ScenarioQualityChecker;

import java.util.Arrays;


@RestController
@RequestMapping("/{text}")
public class ScenarioQualityCheckerController {

    private static final Logger logger = LoggerFactory.getLogger(ScenarioQualityCheckerController.class);

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public String get(@PathVariable String text,
                              @RequestParam(value="args", defaultValue="upper,escape") String[] args) {

        // log the parameters
        logger.debug(text);
        logger.debug(Arrays.toString(args));

        // perform the transformation, you should run your logic here, below is just a silly example
        ScenarioQualityChecker qualityChecker = new ScenarioQualityChecker(args);
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public String post(@PathVariable String text,
                      @RequestBody String[] args) {

        // log the parameters
        logger.debug(text);
        logger.debug(Arrays.toString(args));

        // perform the transformation, you should run your logic here, below is just a silly example
        ScenarioQualityChecker qualityChecker = new ScenarioQualityChecker(args);
        return null;
    }



}


