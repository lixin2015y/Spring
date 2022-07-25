
--- 定义限流函数
local function acquire(key, apply_num)
    --- 这里是获取当前时间戳（秒）
    local times = redis.call('TIME');
    -- times[1] 秒数   -- times[2] 微秒数
    local curr_mill_second = times[1] * 1000000 + times[2];
    curr_mill_second = curr_mill_second / 1000;

    ---获取令牌通配置，
    local limit_key = redis.call("HMGET", key, "max_permits", "rate", "last_apply_time", "curr_permits");
    local max_permits = limit_key[1];
    local rate = limit_key[2];
    local last_apply_time = limit_key[3];
    local curr_permits = limit_key[4];

    --- 局部变量：本次的令牌数
    local local_curr_permits = 0;
    if(type(last_apply_time) ~= 'boolean' and last_apply_time ~= nil) then
        --- 这里已经初始化过了
        --- 计算这一段时间生成的令牌书
        local reserve_num = ((curr_mill_second - last_apply_time) / 1000) * rate;

        --- 当一段时间未请求的最大请求数量配置起作用
        local_curr_permits = math.min(reserve_num + curr_permits, max_permits);
    else
        --- 第一次初始化
        redis.call("HMSET", key, "last_apply_time", curr_mill_second, "max_permits", 1, "rate", 1);
        local_curr_permits = 1;
    end

    if(tonumber(local_curr_permits) >= tonumber(apply_num)) then
        ---不限流
        redis.call("HMSET", key, "last_apply_time", curr_mill_second, "curr_permits", local_curr_permits - apply_num);
        return 1
    else
        ---限流
        return -1
    end
end

local id = KEYS[1]
local apply_num = ARGV[1]
return acquire(id, tonumber(apply_num));