package com.lee.juc.threadlocal;

public class UserContext {
        private static ThreadLocal<UserContext> context = ThreadLocal.withInitial(UserContext::new);
//    private static ThreadLocal<UserContext> context = new ThreadLocal<>();

    public UserContext() {
        System.out.println("123");
    }

    public static UserContext getString() {
        return context.get();
    }

    public static void setString(UserContext string) {
        context.set(string);
    }

    public static void main(String[] args) {
        UserContext userContext = UserContext.getString();
        System.out.println("===" + userContext);
    }
}
