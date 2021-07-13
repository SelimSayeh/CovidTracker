package com.example.coronadetector.map.Utils;


import com.example.coronadetector.map.Model.ResultModel;

import java.util.List;

public interface OnEventListener<T> {
    public void onListReady(List<ResultModel> ResultList);
}
