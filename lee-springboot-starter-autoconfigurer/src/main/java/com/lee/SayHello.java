package com.lee;

public class SayHello {

    HelloProperties helloProperties;

    public void setHelloProperties(HelloProperties helloProperties) {
        this.helloProperties = helloProperties;
    }

    public String sayHello(){
        return helloProperties.getPrefix() + "hello" + helloProperties.getSuffix();
    }
}
