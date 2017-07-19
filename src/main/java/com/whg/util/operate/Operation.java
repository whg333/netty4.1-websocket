package com.whg.util.operate;

import java.util.concurrent.TimeUnit;

import com.whg.backend.repo.LimitRepo;
import com.whg.util.bit.BitConverter;
import com.whg.util.exception.BusinessException;
import com.whg.util.exception.ErrorCode;
import com.whg.util.time.TimeUtil;

public class Operation {
	
	private static final long ONE_SECOND = TimeUnit.SECONDS.toMillis(1);

    private final long userId;
    private final LimitRepo limitRepo;
    private final long currentTime;
    
    private boolean checked = false;
    
    public Operation(long userId, LimitRepo limitRepo) {
        this.userId = userId;
        this.limitRepo = limitRepo;
        this.currentTime = TimeUtil.currentTimeMillis();
    }

    /** 限制用户1秒内不能同时进行多次操作，结合当前工作线程和数据库实现 */
    public void checkAndLock() {
        if(checked || lockTime()) {
        	return;
        }
        
        byte[] timeBytes = limitRepo.findOperateTime(userId);
        if (timeBytes == null && lockTime()) {
            return;
        } else if (timeBytes != null) {
        	long lastOperateTime = BitConverter.bytes2long(timeBytes);
        	//TODO 所以当用户的某次操作在1秒内未执行完的话，我们是不让他进行下次操作呢？还是缓存在一个操作队列中？
        	//TODO 1秒都未执行完的话，或许可以认为要么业务执行代码写得有问题或者DB层面出问题了导致的响应过慢
        	//System.out.println("currentTime="+DateUtil.f(currentTime, DateUtil.DAY_SECONDS)+", lastOperateTime="+DateUtil.f(lastOperateTime, DateUtil.DAY_SECONDS));
	        boolean currTimeLargeThanLastOpTime = (currentTime > (lastOperateTime + ONE_SECOND));
	        boolean lastOpTimeLargeThanCurrTime = (lastOperateTime > currentTime + ONE_SECOND);
        	if (currTimeLargeThanLastOpTime || lastOpTimeLargeThanCurrTime) {
	            limitRepo.saveOpenrateTime(userId, currentTime);
	            return;
	        }
        }
        
        throw new BusinessException(ErrorCode.COMMON_CAN_NOT_OPERATING_MUTLI_THINGS);
    }

	private boolean lockTime() {
		boolean added = limitRepo.addOperateTime(userId, currentTime);
        if(added){
            checked = true;
            return true;
        }
        return false;
	}

    public void unlock(){
        limitRepo.removeOperateTime(userId);
    }
    
}
