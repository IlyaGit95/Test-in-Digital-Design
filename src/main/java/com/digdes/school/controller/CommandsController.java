package com.digdes.school.controller;

import com.digdes.school.JavaSchoolStarter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CommandsController {

    public Set<String> columns = new HashSet<>();

    {
        columns.add("id");
        columns.add("lastName");
        columns.add("cost");
        columns.add("age");
        columns.add("active");
    }

    private Map<String, Object> userParamsMap;

    public Map<String, Object> getUserParamsMap(JavaSchoolStarter starter, String request) throws Exception {

        userParamsMap = new HashMap<>();

        while (starter.getIndexOfChar() < request.length()) {

            addUserParam(starter, request);

            if (starter.getIndexOfChar() < request.length()) {
                String comma = starter.charactersProcessing(request).toUpperCase();

                if (comma.equals("WHERE")) {
                    return userParamsMap;
                }

                if (!comma.equals(",")) {
                    throw new Exception();
                }
            }
        }

        return userParamsMap;
    }

    private void addUserParam(JavaSchoolStarter starter, String request) throws Exception {

        String columnName = starter.charactersProcessing(request);

        if (!columns.contains(columnName)) {
            throw new Exception();
        }

        String equalityOperator = starter.charactersProcessing(request);

        if (!equalityOperator.equals("=")) {
            throw new Exception();
        }

        userParamsMap.put(columnName, getParamValue(starter, request, columnName));

    }

    public Object getParamValue(JavaSchoolStarter starter, String request, String columnName) throws Exception {
        String value = starter.charactersProcessing(request);

        if (columnName.equals("id")) {
            return Long.parseLong(value);
        } else if (columnName.equals("lastName")) {
            return value;
        } else if (columnName.equals("cost")) {
           return Double.parseDouble(value);
        } else if (columnName.equals("age")) {
            return Long.parseLong(value);
        } else if (columnName.equals("active")) {
            return Boolean.parseBoolean(value);
        } else {
            throw new Exception();
        }
    }

}
