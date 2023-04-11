package com.digdes.school.commands;

import com.digdes.school.JavaSchoolStarter;
import com.digdes.school.controller.CommandsController;
import com.digdes.school.controller.WhereController;

import java.util.*;

public class DeleteCommand {


   private List<Map<String, Object>> indexToDeleteWithWhereMap;

    public void delete(List<Map<String, Object>> usersList, JavaSchoolStarter starter,
                       String request, CommandsController commandsController) throws Exception {


        if (starter.getIndexOfChar() < request.length()) {

            String whereWord = starter.charactersProcessing(request).toUpperCase();

            if (!whereWord.equals("WHERE")) {
                throw new Exception();
            }

            WhereController whereController = new WhereController();
            indexToDeleteWithWhereMap = whereController.where(usersList, starter, request, commandsController);

            deleteValue(indexToDeleteWithWhereMap, usersList);
        } else {
            List<Map<String, Object>> newUserList = new ArrayList<>();
            starter.setUsersList(newUserList);
        }

        starter.setIndexOfChar(0);
    }

    private void deleteValue(List<Map<String, Object>> indexToDeleteMap,
                             List<Map<String, Object>> usersList) {

        for (Map<String, Object> userMap : indexToDeleteMap) {

            usersList.remove(userMap);

        }
    }

}
