package com.broada.uyconf.web.tools;

import com.broada.uyconf.web.service.role.bo.RoleEnum;
import com.broada.uyconf.web.service.user.dao.UserDao;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 生成测试用户SQL的工具（不会写进数据库里，只是单纯的生成SQL）
 *
 * @author liaoqiqi
 * @version 2014-2-8
 */
public class UserCreateTools {

    private static UserDao userDao;

    /**
     * @param args
     */
    public static void main(String[] args) {

        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();
        ctx.getEnvironment().setActiveProfiles("production");
        ctx.setConfigLocation("applicationContext.xml");
        ctx.refresh();

        userDao = (UserDao) ctx.getBean("userDaoImpl");

        /**
         * 生成测试用户 SQL
         */
        UserCreateCommon.generateCreateTestUserSQL(userDao);

        /**
         * 生成指定用户 SQL
         */
        UserCreateCommon.generateCreateSpecifyUserSQL(userDao, "msoa", "msoaSH", RoleEnum.ADMIN, "");

        System.exit(1);
    }

}
