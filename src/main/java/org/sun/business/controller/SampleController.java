package org.sun.business.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by SongX on 2018/2/5
 */
@RestController
public class SampleController {

    Logger logger = LoggerFactory.getLogger(SampleController.class);



    @RequestMapping("/sayHello")
    public String sayHello(@RequestParam String name) {
        return null;
    }
}
