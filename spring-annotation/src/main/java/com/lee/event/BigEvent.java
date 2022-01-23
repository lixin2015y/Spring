package com.lee.event;

import org.springframework.context.ApplicationEvent;

/**
 * @className: BigEvent
 * @author: li xin
 * @date: 2022-01-23
 **/
public class BigEvent extends ApplicationEvent {


    private String name;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public BigEvent(Object source, String name) {
        super(source);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
