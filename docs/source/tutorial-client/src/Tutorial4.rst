Tutorial 4 注解式分布式静态配置文件和静态配置项（最佳实践）
===========================================================

配置类
------

定义
~~~~

::

    package com.example.disconf.demo.config;

    import com.broada.uyconf.client.common.annotations.UyconfFile;
    import com.broada.uyconf.client.common.annotations.UyconfFileItem;

    /**
     * 静态 配置文件 示例
     *
     * @author wnb
     *
     */
    @DisconfFile(filename = "static.properties")
    public class StaticConfig {

        private static int staticVar;

        @DisconfFileItem(name = "staticVar", associateField = "staticVar")
        public static int getStaticVar() {
            return staticVar;
        }

        public static void setStaticVar(int staticVar) {
            StaticConfig.staticVar = staticVar;
        }

    }

使用
~~~~

::

    package com.example.disconf.demo.service;

    import com.broada.uyconf.client.common.annotations.UyconfItem;
    import com.example.disconf.demo.config.StaticConfig;

    /**
     * 使用静态配置文件的示例<br/>
     * Plus <br/>
     * 静态配置项 使用示例
     * 
     * @author wnb
     * 14-8-14
     */
    public class SimpleStaticService {

        private static int staticItem = 56;

        /**
         * 
         * @return
         */
        public static int getStaticFileData() {

            return StaticConfig.getStaticVar();
        }
    }

和

::

    LOGGER.info("static file data:"
                            + SimpleStaticService.getStaticFileData());

配置项
------

定义
~~~~

::

    package com.example.disconf.demo.service;

    import com.broada.uyconf.client.common.annotations.UyconfItem;
    import com.example.disconf.demo.config.StaticConfig;

    /**
     * 使用静态配置文件的示例<br/>
     * Plus <br/>
     * 静态配置项 使用示例
     * 
     * @author wnb
     * 14-8-14
     */
    public class SimpleStaticService {

        private static int staticItem = 56;

        /**
         * 
         * @return
         */
        public static int getStaticFileData() {

            return StaticConfig.getStaticVar();
        }

        @DisconfItem(key = "staticItem")
        public static int getStaticItem() {
            return staticItem;
        }

        public static void setStaticItem(int staticItem) {
            SimpleStaticService.staticItem = staticItem;
        }
    }

使用
~~~~

::

    LOGGER.info("static item data:"
                        + SimpleStaticService.getStaticItem());
