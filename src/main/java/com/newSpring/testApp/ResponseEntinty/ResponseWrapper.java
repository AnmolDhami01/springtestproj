package com.newSpring.testApp.ResponseEntinty;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.newSpring.testApp.modal.UserModal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper implements Serializable {

    StatusDescription statusDescriptions;

    List<UserModal> users;

    public ResponseWrapper() {
    }

    public ResponseWrapper(StatusDescription statusDescriptions, List<UserModal> users) {
        this.statusDescriptions = statusDescriptions;
        this.users = users;
    }

    public void setStatusDescriptions(StatusDescription statusDescriptions) {
        this.statusDescriptions = statusDescriptions;
    }

    public void setUsers(List<UserModal> users) {
        this.users = users;
    }

    public StatusDescription getStatusDescriptions() {
        return statusDescriptions;
    }

    public List<UserModal> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "ResponseWrapper [statusDescriptions=" + statusDescriptions + ", users=" + users + "]";
    }
}
