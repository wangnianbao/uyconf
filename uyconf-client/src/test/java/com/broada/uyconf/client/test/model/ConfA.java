package com.broada.uyconf.client.test.model;

import com.broada.uyconf.client.common.annotations.UyconfFile;
import com.broada.uyconf.client.common.annotations.UyconfFileItem;
import org.springframework.stereotype.Service;

/**
 * 1. 分布式配置文件，fileName 是配置文件名<br/>
 * 2. 使用Spring Bean托管方式
 */
@Service
@UyconfFile(filename = "confA.properties")
public class ConfA {

    /**
     * 配置文件中的某Item
     */
    private Long varA = 15L;

    /**
     * 配置文件中的某Item
     */
    private Long varA2 = 25L;

    /**
     * name是配置文件中名字; associateField是此get方法相对应的Field名
     *
     * @return
     */
    @UyconfFileItem(name = "confa.varA", associateField = "varA")
    public Long getVarA() {
        return varA;
    }

    public void setVarA(Long varA) {
        this.varA = varA;
    }

    @UyconfFileItem(name = "confa.varA2")
    public Long getVarA2() {
        return varA2;
    }

    public void setVarA2(Long varA2) {
        this.varA2 = varA2;
    }

}
