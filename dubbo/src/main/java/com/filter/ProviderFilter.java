package com.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.api.service.ServiceContext;

@Activate(group = { Constants.CONSUMER }, order = -4000)
public class ProviderFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        ServiceContext context = ServiceContext.getContext();
        context.setHeader("name", "lixin");
        context.setHeader("name2", "lixin2");
        invocation.getAttachments().putAll(context.getCloneHeader());
        return invoker.invoke(invocation);
    }
}
