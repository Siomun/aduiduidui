package com.example.playandroid.model;

import java.util.ArrayList;

public interface IGetDataPage {
    void getData(int page,SuccessReturnDataPage successReturnData);
    interface SuccessReturnDataPage{
        void Complete(ArrayList<?> arrayList);
    }
}
