package com.example.playandroid.model;

import java.util.ArrayList;

public interface IGetDataIdPage {
    void getData(String Id,int page,SuccessReturnDataPageId successReturnDataPageId);
    interface SuccessReturnDataPageId{
        void Complete(ArrayList<?> arrayList);
    }
}
