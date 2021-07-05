package com.coffeehouse.the.utils.helper;

public interface WaitingHandler {

    /*
    * set content to be gone, progress indicator to visible
    * */
    void invokeWaiting();

    /*
    * set content to be visible, progress indivator to be gone*/
    void dispatchWaiting();
}
