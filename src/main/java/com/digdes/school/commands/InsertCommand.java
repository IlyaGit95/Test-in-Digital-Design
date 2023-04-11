package com.digdes.school.commands;

import com.digdes.school.JavaSchoolStarter;
import com.digdes.school.controller.CommandsController;

import java.util.Map;

public class InsertCommand {

    public void insert(JavaSchoolStarter starter,
                       String request, CommandsController commandsController) throws Exception {

        Map<String, Object> userMap = commandsController.getUserParamsMap(starter, request);

        starter.getUsersList().add(userMap);

        starter.setIndexOfChar(0);
    }



}

