package com.example.playandroid.model;

import java.util.ArrayList;

public interface IGetLoginData {
    void getLoginData(String userName,String password,SuccessLoginReturnData successLoginReturnData);
    interface SuccessLoginReturnData{
        void Complete(String result);
    }
}
