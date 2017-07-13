package com.whg.util.platform;

public enum Platform {
	
	//负数不要用来做type，因为需要用作Redis存储的key，负数不好看，例如你是愿意看到key_0还是key_-1？
    local(0), facebook(1);
    
    public final int type;
    
    private Platform(int type) {
        this.type = type;
    }
    
}
