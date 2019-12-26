package com.broada.uyconf.web.web.app.validator;

import com.broada.uyconf.web.service.app.bo.App;
import com.broada.uyconf.web.service.app.form.AppNewForm;
import com.broada.uyconf.web.service.app.service.AppMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.broada.dsp.common.exception.FieldException;

/**
 * 权限验证
 *
 * @author wnb
 * 14-7-2
 */
@Component
public class AppValidator {

    @Autowired
    private AppMgr appMgr;

    /**
     * 验证创建
     */
    public void validateCreate(AppNewForm appNewForm) {

        // trim
        if (appNewForm.getApp() != null) {
            appNewForm.setApp(appNewForm.getApp().trim());
        }
        if (appNewForm.getDesc() != null) {
            appNewForm.setDesc(appNewForm.getDesc().trim());
        }

        App app = appMgr.getByName(appNewForm.getApp());
        if (app != null) {
            throw new FieldException(AppNewForm.APP, "app.exist", null);
        }
    }

}
