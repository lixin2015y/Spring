package com.lee.javaApi;

import com.sun.org.apache.xpath.internal.WhitespaceStrippingElementMatcher;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.security.Key;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @className: SubReator
 * @author: li xin
 * @date: 2022-04-11
 **/
public class SubReactor implements Runnable {

    final Selector selector;

    public SubReactor(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                dispatcher(iterator.next());
            }
            selectionKeys.clear();
        }
    }

    private void dispatcher(SelectionKey selectionKey) {
        Runnable handler = (Runnable) selectionKey.attachment();
        if (handler != null) {
            handler.run();
        }
    }
}
