package com.logic.client;

/**
 * Created by lenovo on 2018/5/8.
 */

public class RxBusEvent {

    Object event;
    Object val;
    public RxBusEvent(Object event, Object val) {
        this.event = event;
        this.val = val;
    }

    public Object getEvent() {
        return event;
    }

    public void setEvent(Object event) {
        this.event = event;
    }

    public Object getVal() {
        return val;
    }

    public void setVal(Object val) {
        this.val = val;
    }
}
