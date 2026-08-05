package com.ravindra.taskmanager.enums;

public enum TaskSortField {

    ID("id"),
    TITLE("title"),
    DESCRIPTION("description"),
    STATUS("status"),
    CREATED_AT("createdAt");

    private final String fieldName;

    TaskSortField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}