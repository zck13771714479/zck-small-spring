package com.zck;

import java.util.ArrayList;
import java.util.List;

/**
 * 属性值列表
 */
public class PropertyValues {
    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    /**
     * 添加获取propertyValue数组
     * @param propertyValue
     */
    public void addPropertyValue(PropertyValue propertyValue) {
        propertyValueList.add(propertyValue);
    }

    /**
     * 获取获取propertyValue数组数组
     * @return
     */
    public PropertyValue[] getPropertyValues() {
        return propertyValueList.toArray(new PropertyValue[0]);
    }

    /**
     * 获取属性值
     * @param propertyName
     * @return
     */
    public Object getPropertyValue(String propertyName) {
        for (PropertyValue propertyValue : propertyValueList) {
            if (propertyValue.getName().equals(propertyName)) {
                return propertyValue.getValue();
            }
        }
        return null;
    }
}
