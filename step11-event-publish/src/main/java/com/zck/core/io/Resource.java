package com.zck.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源接口
 */
public interface Resource {

    /**
     * 获取输入流
     * @return
     */
    InputStream getInputStream() throws IOException;

}
