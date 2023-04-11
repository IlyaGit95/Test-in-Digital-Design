package com.digdes.school;

import com.digdes.school.commands.DeleteCommand;
import com.digdes.school.commands.InsertCommand;
import com.digdes.school.commands.SelectCommand;
import com.digdes.school.commands.UpdateCommand;
import com.digdes.school.controller.CommandsController;

import java.util.*;

public class JavaSchoolStarter {

    private Integer indexOfChar = 0;

    private List<Map<String, Object>> usersList = new ArrayList<>();

    private final CommandsController commandsController = new CommandsController();
    private final InsertCommand insertCommand = new InsertCommand();
    private final UpdateCommand updateCommand = new UpdateCommand();
    private final DeleteCommand deleteCommand = new DeleteCommand();
    private final SelectCommand selectCommand = new SelectCommand();

    public JavaSchoolStarter() {
    }

    public Integer getIndexOfChar() {
        return indexOfChar;
    }

    public void setIndexOfChar(Integer indexOfChar) {
        this.indexOfChar = indexOfChar;
    }

    public List<Map<String, Object>> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Map<String, Object>> usersList) {
        this.usersList = usersList;
    }

    public List<Map<String, Object>> execute(String request) throws Exception {

        String command = charactersProcessing(request).toUpperCase();

        if (command.equals("INSERT")) {
            if (haveValuesWord(request)) {
                insertCommand.insert(this, request, commandsController);
            }
        } else if (command.equals("UPDATE")) {
            if (haveValuesWord(request)) {
                updateCommand.update(usersList, this, request, commandsController);
            }
        } else if (command.equals("DELETE")) {
            deleteCommand.delete(usersList, this, request, commandsController);
        } else if (command.equals("SELECT")) {
            return selectCommand.select(usersList, this, request, commandsController);
        } else {
            throw new Exception();
        }
        return usersList;
    }

    private boolean haveValuesWord(String request) throws Exception {
        String valueWord = charactersProcessing(request).toUpperCase();

        if (!valueWord.equals("VALUES")) {
            throw new Exception();
        }
        return true;
    }


    public String charactersProcessing(String request) {

        StringBuilder resultWord = new StringBuilder();

        for (int i = indexOfChar; i < request.length(); i++) {
            char symbol = request.charAt(i);

            if (symbol == '!') {
                if (resultWord.length() == 0 && request.charAt(i) + 1 == '=') {
                    indexOfChar = indexOfChar + 2;
                    return "!=";
                }
            }

            if (symbol == '>') {
                indexOfChar++;
                resultWord.append(symbol);
                return resultWord.toString();
            }

            if (symbol == '<') {
                indexOfChar++;
                resultWord.append(symbol);
                return resultWord.toString();
            }

            if (symbol == '=') {
                indexOfChar++;
                resultWord.append(symbol);
                return resultWord.toString();
            }

            if (symbol == ',') {
                if (resultWord.length() == 0) {
                    indexOfChar++;
                    resultWord.append(symbol);
                    return resultWord.toString();
                }

                return resultWord.toString();
            }

            if (symbol == '\'') {
                indexOfChar++;
                return nameColumnsProcessing(request);
            }

            if (symbol == ' ') {
                if (resultWord.length() == 0) {
                    indexOfChar++;
                    continue;
                }
                indexOfChar++;
                return resultWord.toString();
            }
            indexOfChar++;
            resultWord.append(symbol);

        }

        return resultWord.toString();
    }


    public String nameColumnsProcessing(String request) {

        StringBuilder resultWord = new StringBuilder();

        for (int i = indexOfChar; i < request.length(); i++) {
            char symbol = request.charAt(i);
            if (symbol == '\'') {
                indexOfChar++;
                return resultWord.toString();
            }

            resultWord.append(symbol);
            indexOfChar++;
        }

        return resultWord.toString();
    }

}
