package com.traccapp.demo.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class AbstractGraphQLException extends RuntimeException implements GraphQLError{

    private Map<String,Object> parameters = new HashMap<>();

    public AbstractGraphQLException(String message){
        super(message);
    }

    public AbstractGraphQLException(String message, Map<String,Object> additionParam){
        this(message);
        if(additionParam!=null){
            parameters = additionParam;
        }
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public ErrorType getErrorType() {
        return null;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return this.parameters;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }
}
