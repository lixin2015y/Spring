package com.compensate.supports;

import com.compensate.annotation.*;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;


public class CompensationAnnoUtil {

    private final static String ERROR_TAG_1 = "基于异常补偿";

    private final static String ERROR_TAG_2 = "基于结果补偿";


    public static CompensationInfo parse(Compensation compensation) {

        if (compensation == null) {
            return null;
        }
        CompensationInfo compensationInfo = new CompensationInfo();
        String code = compensation.code();
        String systemCode = compensation.systemCode();
        String group = compensation.group();
        if (StringUtils.isEmpty(group)) {
            group = systemCode + "_" + code;
        }
        compensationInfo.setCode(code);
        compensationInfo.setName(compensation.name());
        compensationInfo.setSystemCode(systemCode);
        compensationInfo.setGroup(group);
        compensationInfo.setOpen(compensation.open());
        compensationInfo.setLimit(compensation.limit());
        compensationInfo.setLogKeepDays(compensation.logKeepDays());
        compensationInfo.setGapMinuter(compensation.gapMinuter());
        compensationInfo.setGapPolicy(compensation.gapPolicy());
        compensationInfo.setWithResultInfo(parse(compensation.withResult()));
        compensationInfo.setWithExceptionInfo(parse(compensation.withException()));

        String errorTag = "";
        if (StringUtils.isEmpty(compensation.errorTag())) {
            if (compensationInfo.getWithExceptionInfo().getOpen()) {
                errorTag = ERROR_TAG_1;
            }
            if (compensationInfo.getWithResultInfo().getOpen()) {
                errorTag = ERROR_TAG_2;
            }
        } else {
            errorTag = compensation.errorTag();
        }

        compensationInfo.setErrorTag(errorTag);
        compensationInfo.setErrorMessage(compensation.errorMessage());

        return compensationInfo;

    }


    public static WithExceptionInfo parse(WithException withException) {

        WithExceptionInfo withExceptionInfo = new WithExceptionInfo();
        withExceptionInfo.setOpen(withException.open());
        withExceptionInfo.setValue(withException.value());
        withExceptionInfo.setIncludes(withException.includes());
        withExceptionInfo.setExcludes(withException.excludes());
        withExceptionInfo.setThrowExceptionTag(withException.throwExceptionTag());
        return withExceptionInfo;
    }

    public static WithResultInfo parse(WithResult withResult) {

        WithResultInfo withResultInfo = new WithResultInfo();
        withResultInfo.setOpen(withResult.open());
        withResultInfo.setKey(withResult.key());
        withResultInfo.setValueSet(Arrays.stream(withResult.value()).collect(Collectors.toSet()));
        return withResultInfo;
    }


}
