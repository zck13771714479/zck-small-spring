package com.zck.context;

import com.zck.beans.factory.HierarchicalBeanFactory;
import com.zck.beans.factory.ListableBeanFactory;
import com.zck.core.io.ResourceLoader;

public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {
}
