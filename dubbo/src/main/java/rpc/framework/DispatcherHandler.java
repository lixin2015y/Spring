package rpc.framework;

import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DispatcherHandler implements ChannelHandler {

    private ChannelHandler channelHandler;

    private ExecutorService executorService;

    public DispatcherHandler(ChannelHandler channelHandler) {
        this.channelHandler = channelHandler;
        executorService = Executors.newFixedThreadPool(10);

    }

    @Override
    public void handler(ChannelHandlerContext ctx, Invocation invocation) throws Exception {
        System.out.println("线程池执行");
        executorService.execute(new Task(invocation, channelHandler, ctx));
    }
}
