


--- 定义限流函数
local function acquire(key, num)
    --- 这里是获取当前时间戳（秒）
    local times = redis.call('TIME');
    -- times[1] 秒数   -- times[2] 微秒数
    local curr_mill_second = times[1] * 1000000 + times[2];
    curr_mill_second = curr_mill_second / 1000;

    ---获取令牌通配置，
    local limitConfig = redis.pcall("HMGET", key, "last_apply_time", "curr_permits", "max_permits", "rate");
    --- 上次申请时间、当前令牌数、最大令牌数、令牌发放速率（每秒发放多少个）
    local lastApplyTime = limitConfig[1];
    local currPermits = limitConfig[2];
    local maxPermits = limitConfig[3];
    local rate = limitConfig[4];

    if(type(lastApplyTime) ~= 'boolean' and lastApplyTime ~= nil) then

    else
        redis.pcall("HSET", key, "last_mill_second", curr_mill_second)
        local_curr_permits = max_permits;
    end

end