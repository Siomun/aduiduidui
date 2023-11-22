package com.example.playandroid.model;

import java.util.ArrayList;

public interface IGetData {
    void getData(SuccessReturnData successReturnData);
    interface SuccessReturnData{
        void Complete(ArrayList<?> arrayList);
    }
}
