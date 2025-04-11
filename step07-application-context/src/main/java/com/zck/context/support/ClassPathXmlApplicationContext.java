package com.zck.context.support;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {
    private String[] configLocations;

    public ClassPathXmlApplicationContext(String configLocation) {
        this(new String[]{configLocation});
    }

    public ClassPathXmlApplicationContext(String[] configLocations) {
        this.configLocations = configLocations;
    }

    /**
     * 获取配置文件路径数组
     *
     * @return
     */
    @Override
    protected String[] configLocations() {
        return configLocations;
    }
}
