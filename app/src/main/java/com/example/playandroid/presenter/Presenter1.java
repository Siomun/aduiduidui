package com.example.playandroid.presenter;

import android.util.Log;

import com.example.playandroid.model.GetBannerImpl;
import com.example.playandroid.model.GetHomeDataImpl;
import com.example.playandroid.model.GetKnowledgeHierarchyImpl;
import com.example.playandroid.model.GetKnowledgeHierarchyListImpl;
import com.example.playandroid.model.GetLoginResult;
import com.example.playandroid.model.GetProjectDataImpl;
import com.example.playandroid.model.GetProjectListDataImpl;
import com.example.playandroid.model.GetSearchHotWordImpl;
import com.example.playandroid.model.GetSearchReturnResultImpl;
import com.example.playandroid.model.IGetDataIdPage;
import com.example.playandroid.model.IGetLoginData;
import com.example.playandroid.view.IView;
import com.example.playandroid.view.IView2;
import com.example.playandroid.view.IView3;

import java.util.ArrayList;

public class Presenter1 {
    IView3 mView;
    GetProjectListDataImpl getProjectListData;
    GetSearchReturnResultImpl getSearchReturnResult;
    GetKnowledgeHierarchyListImpl getKnowledgeHierarchyList;
    GetLoginResult getLoginResult;
    public Presenter1(IView3 iView3){
        getProjectListData=new GetProjectListDataImpl();
        getSearchReturnResult=new GetSearchReturnResultImpl();
        getKnowledgeHierarchyList=new GetKnowledgeHierarchyListImpl();
        getLoginResult=new GetLoginResult();
        this.mView=iView3;
    }
    public void fetchGetProjectListData(String Id, int page){
        this.getProjectListData.getData(Id, page, new IGetDataIdPage.SuccessReturnDataPageId() {
            @Override
            public void Complete(ArrayList<?> arrayList) {
               mView.showData3(arrayList);
            }
        });
    }
    public void fetchGetSearchReturnResult(String param, int page){
        this.getSearchReturnResult.getData(param, page, new IGetDataIdPage.SuccessReturnDataPageId() {
            @Override
            public void Complete(ArrayList<?> arrayList) {
                mView.showData3(arrayList);
            }
        });
    }
    public void fetchGetKnowledgeHierarchyList(String Id, int page){
        this.getKnowledgeHierarchyList.getData(Id, page, new IGetDataIdPage.SuccessReturnDataPageId() {
            @Override
            public void Complete(ArrayList<?> arrayList) {
                mView.showData3(arrayList);
            }
        });
    }
    public void fetchGetLoginResult(String userName,String password){
        this.getLoginResult.getLoginData(userName, password, new IGetLoginData.SuccessLoginReturnData() {
            @Override
            public void Complete(String result) {
                ArrayList<String> strings=new ArrayList<>();
                strings.add(result);
                mView.showData3(strings);
            }
        });
    }
}
