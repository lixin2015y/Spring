package com.filter;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.api.service.ServiceContext;

import java.util.HashMap;
import java.util.Map;

@Activate(group = {"provider"}, order = -20000)
public class ConsumerFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Map<String, String> map = invocation.getAttachments();
        ServiceContext.getContext().addHeaders(map);
        return invoker.invoke(invocation);
    }
}

