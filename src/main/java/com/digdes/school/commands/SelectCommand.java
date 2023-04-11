package com.digdes.school.commands;

import com.digdes.school.JavaSchoolStarter;
import com.digdes.school.controller.CommandsController;
import com.digdes.school.controller.WhereController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SelectCommand {

    private List<Map<String, Object>> selectWithWhereList;

    public List<Map<String, Object>> select(List<Map<String, Object>> usersList, JavaSchoolStarter starter,
                                            String request, CommandsController commandsController) throws Exception {

        if (starter.getIndexOfChar() < request.length()) {

            String whereWord = starter.charactersProcessing(request).toUpperCase();

            if (!whereWord.equals("WHERE")) {
                throw new Exception();
            }

            WhereController whereController = new WhereController();
            selectWithWhereList = whereController.where(usersList, starter, request, commandsController);

            if (selectWithWhereList.size() == 0) {
                return new ArrayList<>();
            }
            starter.setIndexOfChar(0);
            return new ArrayList<>(selectWithWhereList);
        } else {
            starter.setIndexOfChar(0);
            return starter.getUsersList();
        }
    }

}
