package com.digdes.school.commands;

import com.digdes.school.JavaSchoolStarter;
import com.digdes.school.controller.CommandsController;
import com.digdes.school.controller.WhereController;

import java.util.List;
import java.util.Map;

public class UpdateCommand {

    private Map<String, Object> userParamsToUpdateMap;

    private List<Map<String, Object>> indexToUpdateWithWhereMap;

    public void update(List<Map<String, Object>> usersList, JavaSchoolStarter starter,
                       String request, CommandsController commandsController) throws Exception {

        userParamsToUpdateMap = commandsController.getUserParamsMap(starter, request);

        if (starter.getIndexOfChar() < request.length()) {
            WhereController whereController = new WhereController();
            indexToUpdateWithWhereMap = whereController.where(usersList, starter, request, commandsController);

            updateValue(indexToUpdateWithWhereMap);

        } else {
            updateValue(starter.getUsersList());
        }

        starter.setIndexOfChar(0);
    }

    private void updateValue(List<Map<String, Object>> toUpdateMap) {

        for (Map<String, Object> userMap : toUpdateMap) {

            for (Map.Entry<String, Object> entry : userParamsToUpdateMap.entrySet()) {
                userMap.put(entry.getKey(), entry.getValue());
            }

        }

    }

}
