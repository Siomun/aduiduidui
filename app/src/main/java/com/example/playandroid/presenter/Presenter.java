package com.example.playandroid.presenter;

import android.util.Log;

import com.example.playandroid.model.GetBannerImpl;
import com.example.playandroid.model.GetHomeDataImpl;
import com.example.playandroid.model.GetKnowledgeHierarchyImpl;
import com.example.playandroid.model.GetProjectDataImpl;
import com.example.playandroid.model.GetProjectListDataImpl;
import com.example.playandroid.model.GetSearchHotWordImpl;
import com.example.playandroid.model.IGetData;
import com.example.playandroid.model.IGetDataIdPage;
import com.example.playandroid.model.IGetDataPage;
import com.example.playandroid.view.IView;
import com.example.playandroid.view.IView2;

import java.util.ArrayList;

public class Presenter {
    GetHomeDataImpl getHomeData;
    GetKnowledgeHierarchyImpl getKnowledgeHierarchy;
    GetProjectDataImpl getProjectData;
    GetBannerImpl getBanner;
    GetSearchHotWordImpl getSearchHotWord;
    GetProjectListDataImpl getProjectListData;
    IView mView;
    IView2 mView2;
    public Presenter(IView iView,IView2 iView2){
        this.mView=iView;
        this.mView2=iView2;
        getHomeData=new GetHomeDataImpl();
        getKnowledgeHierarchy=new GetKnowledgeHierarchyImpl();
        getProjectData=new GetProjectDataImpl();
        getBanner=new GetBannerImpl();
        getSearchHotWord=new GetSearchHotWordImpl();
        getProjectListData=new GetProjectListDataImpl();
    }
    public void fetchGetHomeData(int page){
        this.getHomeData.getData(page,new IGetDataPage.SuccessReturnDataPage(){

            @Override
            public void Complete(ArrayList<?> arrayList) {
                mView.showData(arrayList);
            }
        });
    }

    public void fetchGetKnowledgeHierarchyData(){
        this.getKnowledgeHierarchy.getData(new IGetData.SuccessReturnData(){

            @Override
            public void Complete(ArrayList<?> arrayList) {
                mView.showData(arrayList);
            }
        });
    }

    public void fetchGetProjectData(){
        this.getProjectData.getData(new IGetData.SuccessReturnData(){

            @Override
            public void Complete(ArrayList<?>dataList) {
                mView.showData(dataList);
            }
        });
    }

    public void fetchGetBannerData(){

        this.getBanner.getData(new IGetData.SuccessReturnData(){
            @Override
            public void Complete(ArrayList<?> dataList) {
                mView2.showData2(dataList);
            }
        });
    }

    public void fetchGetSearchHotWod(){
        this.getSearchHotWord.getData(new IGetData.SuccessReturnData(){

            @Override
            public void Complete(ArrayList<?> arrayList) {
                mView.showData(arrayList);
            }
        });
    }

}
