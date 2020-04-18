package com.orange.orangetvote.response.login;

import com.orange.orangetvote.basic.response.AbstractResponse;

public class LoginResponse extends AbstractResponse {

    public Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public class Response extends AbstractResponse {
        private String orangeId;
        private String username;
        private boolean status;

        public String getOrangeId() {
            return orangeId;
        }

        public String getUsername() {
            return username;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public void setOrangeId(String orangeId) {
            this.orangeId = orangeId;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
