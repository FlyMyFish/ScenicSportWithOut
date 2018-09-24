package com.shichen.scenicsport;

public interface BaseView<T> {

    void setPresenter(T presenter);

    void toastMessage(String msg);
}
