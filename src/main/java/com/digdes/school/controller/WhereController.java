package com.digdes.school.controller;

import com.digdes.school.JavaSchoolStarter;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WhereController {

    private List<Map<String, Object>> usersList;
    private CommandsController commandsController;
    private JavaSchoolStarter starter;
    private List<Map<String, Object>> iterTmpIndexUsersMap;
    private List<Map<String, Object>> resultTmpIndexUsersMap;
    private List<Map<String, Object>> iterIndexUsersList;
    private List<Map<String, Object>> resultIndexUsersMap;


    public List<Map<String, Object>> where(List<Map<String, Object>> usersList,
                                           JavaSchoolStarter starter, String request, CommandsController commandsController) throws Exception {

        iterIndexUsersList = usersList;
        resultIndexUsersMap = new ArrayList<>();
        this.starter = starter;
        this.commandsController = commandsController;

        this.usersList = usersList;
        iterTmpIndexUsersMap = usersList;
        resultTmpIndexUsersMap = new ArrayList<>();

        while (starter.getIndexOfChar() < request.length()) {
            whereRequest(request);
        }

        starter.setIndexOfChar(0);

        return resultIndexUsersMap;
    }

    private void whereRequest(String request) throws Exception {

        String columnName = starter.charactersProcessing(request);

        if (!commandsController.columns.contains(columnName)) {
            throw new Exception();
        }

        String operator = starter.charactersProcessing(request).toUpperCase();

        if (operator.equals("=")) {
            equalityOperator(request, columnName);
        } else if (operator.equals("!=")) {
            notEqualityOperator(request, columnName);
        } else if (operator.equals("LIKE")) {
            likeOperator(request, columnName);
        } else if (operator.equals("ILIKE")) {
            ilikeOperator(request, columnName);
        } else if (operator.equals(">=")) {
            greaterOrEqualOperator(request, columnName);
        } else if (operator.equals("<=")) {
            lessOrEqualOperator(request, columnName);
        } else if (operator.equals(">")) {
            greaterOperator(request, columnName);
        } else if (operator.equals("<")) {
            lessOperator(request, columnName);
        }


        if (starter.getIndexOfChar() < request.length()) {
            String orAndOperator = starter.charactersProcessing(request).toUpperCase();
            if (orAndOperator.equals("AND")) {
                iterTmpIndexUsersMap = resultTmpIndexUsersMap;
                resultTmpIndexUsersMap = new ArrayList<>();
                whereRequest(request);
            } else if (orAndOperator.equals("OR")) {
                iterTmpIndexUsersMap = starter.getUsersList();
                putInResultMap();

                whereRequest(request);
            } else {
                throw new Exception();
            }
        }
        putInResultMap();
    }


    private void putInResultMap() {
        resultIndexUsersMap.addAll(resultTmpIndexUsersMap);
    }


    private void equalityOperator(String request,
                                  String columnName) throws Exception {

        Object value = commandsController.getParamValue(starter, request, columnName);

        for (Map<String, Object> entry : iterTmpIndexUsersMap) {
            Map<String, Object> userMap = entry;
            var tmpValue = userMap.get(columnName);

            if (tmpValue.equals(value)) {
                resultTmpIndexUsersMap.add(entry);
            }
        }

    }

    private void notEqualityOperator(String request, String columnName) throws Exception {

        Object value = commandsController.getParamValue(starter, request, columnName);

        for (Map<String, Object> entry : iterTmpIndexUsersMap) {
            Map<String, Object> userMap = entry;
            var tmpValue = userMap.get(columnName);

            if (!tmpValue.equals(value)) {
                resultTmpIndexUsersMap.add(entry);
            }
        }

    }

    private void likeOperator(String request, String columnName) throws Exception {

        String value = (String) commandsController.getParamValue(starter, request, columnName);

        Pattern pattern = Pattern.compile(getStringPattern(value));

        Matcher matcher;

        for (Map<String, Object> entry : iterTmpIndexUsersMap) {

            Map<String, Object> userMap = entry;

            var tmpValue = userMap.get(columnName);

            matcher = pattern.matcher(tmpValue.toString());

            if (matcher.find()) {
                resultTmpIndexUsersMap.add(entry);
            }
        }
    }

    private void ilikeOperator(String request, String columnName) throws Exception {

        String value = (String) commandsController.getParamValue(starter, request, columnName);

        value = value.toLowerCase();

        Pattern pattern = Pattern.compile(getStringPattern(value));

        Matcher matcher;

        for (Map<String, Object> entry : iterTmpIndexUsersMap) {

            Map<String, Object> userMap = entry;

            var tmpValue = userMap.get(columnName);

            matcher = pattern.matcher(tmpValue.toString().toLowerCase());

            if (matcher.find()) {
                resultTmpIndexUsersMap.add(entry);
            }
        }
    }

    private String getStringPattern(String value) {
        StringBuilder resultPattern = new StringBuilder();

        for (int i = 0; i < value.length(); i++) {
            char symbol = value.charAt(i);

            if (symbol == '%') {
                resultPattern.append(".*");
                continue;
            }
            resultPattern.append(symbol);
        }

        return resultPattern.toString();
    }


    private void greaterOrEqualOperator(String request, String columnName) throws Exception {

        Object value = commandsController.getParamValue(starter, request, columnName);

        if (value.getClass() == Long.class) {
            Long valueType = (Long) value;


            for (Map<String, Object> entry : iterTmpIndexUsersMap) {

                Map<String, Object> userMap = entry;

                Long tmpValue = (Long) userMap.get(columnName);

                if (tmpValue >= valueType) {
                    resultTmpIndexUsersMap.add(entry);
                }
            }

        } else if (value.getClass() == Double.class) {
            Double valueType = (Double) value;


            for (Map<String, Object> entry : iterTmpIndexUsersMap) {

                Map<String, Object> userMap = entry;

                Double tmpValue = (Double) userMap.get(columnName);

                if (tmpValue >= valueType) {
                    resultTmpIndexUsersMap.add(entry);
                }
            }
        } else {
            throw new Exception();
        }
    }

    private void lessOrEqualOperator(String request, String columnName) throws Exception {

        Object value = commandsController.getParamValue(starter, request, columnName);

        if (value.getClass() == Long.class) {
            Long valueType = (Long) value;


            for (Map<String, Object> entry : iterTmpIndexUsersMap) {

                Map<String, Object> userMap = entry;

                Long tmpValue = (Long) userMap.get(columnName);

                if (tmpValue <= valueType) {
                    resultTmpIndexUsersMap.add(entry);
                }
            }

        } else if (value.getClass() == Double.class) {
            Double valueType = (Double) value;


            for (Map<String, Object> entry : iterTmpIndexUsersMap) {

                Map<String, Object> userMap = entry;

                Double tmpValue = (Double) userMap.get(columnName);

                if (tmpValue <= valueType) {
                    resultTmpIndexUsersMap.add(entry);
                }
            }
        } else {
            throw new Exception();
        }
    }

    private void greaterOperator(String request, String columnName) throws Exception {

        Object value = commandsController.getParamValue(starter, request, columnName);

        if (value.getClass() == Long.class) {
            Long valueType = (Long) value;


            for (Map<String, Object> entry : iterTmpIndexUsersMap) {

                Map<String, Object> userMap = entry;

                Long tmpValue = (Long) userMap.get(columnName);

                if (tmpValue > valueType) {
                    resultTmpIndexUsersMap.add(entry);
                }
            }

        } else if (value.getClass() == Double.class) {
            Double valueType = (Double) value;


            for (Map<String, Object> entry : iterTmpIndexUsersMap) {

                Map<String, Object> userMap = entry;

                Double tmpValue = (Double) userMap.get(columnName);

                if (tmpValue > valueType) {
                    resultTmpIndexUsersMap.add(entry);
                }
            }
        } else {
            throw new Exception();
        }
    }

    private void lessOperator(String request, String columnName) throws Exception {

        Object value = commandsController.getParamValue(starter, request, columnName);

        if (value.getClass() == Long.class) {
            Long valueType = (Long) value;


            for (Map<String, Object> entry : iterTmpIndexUsersMap) {

                Map<String, Object> userMap = entry;

                Long tmpValue = (Long) userMap.get(columnName);

                if (tmpValue < valueType) {
                    resultTmpIndexUsersMap.add(entry);
                }
            }

        } else if (value.getClass() == Double.class) {
            Double valueType = (Double) value;


            for (Map<String, Object> entry : iterTmpIndexUsersMap) {

                Map<String, Object> userMap = entry;

                Double tmpValue = (Double) userMap.get(columnName);

                if (tmpValue < valueType) {
                    resultTmpIndexUsersMap.add(entry);
                }
            }
        } else {
            throw new Exception();
        }
    }

}
