package com.cloudminds.vending.roc;

/**
 * Created by skyliving on 8/19/16.
 *
 * constant class
 */
public class Constant {

    // platform code
    public static final String ROC_SYS = "ROC";

    // common operate code
    public static final int CODE_OPERATE_FAIL = -1; // 返回值 执行失败
    public static final int CODE_OPERATE_SUCCESS = 0; // 返回值 执行成功

    public static final Byte FLAG_NO = 0;
    public static final Byte FLAG_YES = 1;

    // get all page
    public final static int PAGE_GET_ALL_FLAG = -1;
    // page number
    public final static int PAGE_DEFAULT_SIZE = 10;

    public static final String PWD_MASK_WORD = "__________";

    // 系统邮箱配置key
    public static final String SYS_MAIL_PRE_KEY = "sys.mail";
    public static final String SYS_MAIL_SMTP_HOST_KEY = "sys.mail.smtp.host";
    public static final String SYS_MAIL_SMTP_PORT_KEY = "sys.mail.smtp.port";
    public static final String SYS_MAIL_SMTP_USERNAME_KEY = "sys.mail.smtp.username";
    public static final String SYS_MAIL_SMTP_PASSWORD_KEY = "sys.mail.smtp.password";
    public static final String SYS_MAIL_SMTP_DISPLAY_NAME_KEY = "sys.mail.smtp.display.name";
    public static final String SYS_MAIL_USESSL_KEY = "sys.mail.usessl";

    // separator flag
    public final static String FIELD_SEPARATOR_COMMA = ",";
    public final static String FIELD_PARSER_COMMA = "\\,";
    public static final String FIELD_PARSER_HORIZONTAL = "\\_";
    public static final String FIELD_SEPARATOR_HORIZONTAL = "_";
    public static final String FIELD_PARSER_VERTICAL = "\\|";
    public static final String FIELD_SEPARATOR_VERTICAL = "|";
    public static final String FIELD_PARSER_COLON = "\\:";
    public static final String FIELD_SEPARATOR_COLON = ":";
    public static final String FIELD_SEPARATOR_STAR = "*";

    // redis prefix for token
    public static final String PREFIX_TOKEN_OPERATOR = "token_operator_";
    public static final String TOKEN_SEPARATOR = "-_-";
    // redis prefix for kaptcha
    public static final String PREFIX_KAPTCHA = "kaptcha_";
    // redis prefix for activate code
    public static final String PREFIX_ACTIVATION_CODE = "activation_code_";
    // redis prefix for activate code
    public static final String PREFIX_ACTIVATION_CODE_USER = "activation_code_user_";

    /**
     * 公共配置
     */
    // 初识激活时返回rcu的appConfig信息,0-tenantCode,1-robotType
    public static final String REDIS_CONFIG_INITAPPCONFIG = "config:initAppConfig_{0}_{1}";
    // 初识激活时返回rcu的appConfig信息,0-tenantCode,1-robotType
    public static final String REDIS_CONFIG_ROBOTTYPEDEFAULTCONFIG = "config:robotTypeDefaultConfig_{0}_{1}";
    
    /**
     * 数据缓存
     */
    // 缓存租户信息,0-tenantCode,1-rcuId,2-robotId,3-status
    public static final String REDIS_REFENCE_INFO = "refence:{0}_{1}_{2}_{3}";
    // 缓存租户信息,0-tenantCode
    public static final String REDIS_TENANT_CODE = "tenant:{0}";
    // 缓存rcu信息,0-tenantCode,1-rcuId
    public static final String REDIS_RCU_CODE = "rcu:{0}";
    // 缓存用户信息,0-tenantCode,1-userId
    public static final String REDIS_USER_CODE = "user:{0}_{1}";
    // 缓存机器人信息,0-robotId
    public static final String REDIS_ROBOT_CODE = "robot:{0}";
    // 记录坐席对应租户的信息,0-seatId
    public static final String REDIS_SEATID_TENANT = "seatId_tenants:{0}";
    // 报警信息，0-租户
    public static final String REDIS_ALARM_TENANT = "alarm_{0}:all";
    // 报警信息，0-租户，1-unionId，2-报警module，3-报警oid
    public static final String REDIS_ALARM_OID = "alarm_{0}:unionId_{1}:module_{2}_oid_{3}";
    // 报警信息，0-租户，1-unionId
    public static final String REDIS_ALARM_OID_LIKE = "alarm_{0}:unionId_{1}:module_*_oid_*";
    // 告警信息，0-租户，1-unionId，2-报警requestId
    public static final String REDIS_ALARM_REQUESTID = "alarm_{0}:unionId_{1}:requestId_{2}";

    /**
     * resource*
     */
    // 资源管理，0-tenantCode, 1-userId, 2-rcuId, 3-robotId
    public static final String REDIS_RESOURCE_TENANTCODE_USERID_RCUID_ROBOTID = "resource:{0}_{1}_{2}_{3}_";
    // 资源管理，0-tenantCode, 1-userId
    public static final String REDIS_RESOURCE_TENANTCODE_USERID = "resource:{0}_{1}_";
    // 资源管理，0-tenantCode, 1-robotId
    public static final String REDIS_RESOURCE_TENANTCODE_ROBOTID = "resource:{0}_{1}_";
    // 资源管理，0-robotId
    public static final String REDIS_RESOURCE_ROBOTID = "resource:{0}_";

    /**
     * report*
     */
    // 记录report的信息,总的，所有的upload*信息，0-tenantCode，1-rcuId
    public static final String REDIS_REPORT_ALL = "report_{0}:{1}";
    // 记录report的信息,分类别的，所有的report*信息，0-tenantCode，1-rcuId，2-类别，如reportConfig
    public static final String REDIS_REPORT_FEN = "report_{0}:{1}_{2}";
    // 记录report的信息,分类别汇总的，所有的report*信息，0-tenantCode，1-rcuId，2-类别，如reportConfig
    public static final String REDIS_REPORT_MIX = "report_mix_{0}:{1}_{2}";

    /**
     * PUSH
     */
    public final static String REDIS_KEY_PUSH = "CPS.QUEUE.ANDROID";
    // 0-token
    public final static String REDIS_KEY_CACHE_ROBOT_CODE_BY_TOKEN = "CACHE.PUSH.ROBOT.CODE:TOKEN_{0}";
    // 0-robotPk
    public final static String REDIS_KEY_CACHE_ROBOT = "CACHE.PUSH.TOKEN:ROBOT_{0}";
    public final static String REDIS_KEY_CACHE_ROBOT_CODE_BY_ROBOT = "CACHE.PUSH.ROBOT.CODE:ROBOT_{0}";
    // 0-rcuPk
    public final static String REDIS_KEY_CACHE_RCU = "CACHE.PUSH.TOKEN:RCU_{0}";
    public final static String REDIS_KEY_CACHE_ROBOT_CODE_BY_RCU = "CACHE.PUSH.ROBOT.CODE:RCU_{0}";
    // 0-userPk
    public final static String REDIS_KEY_CACHE_USER = "CACHE.PUSH.TOKEN:USER_{0}";
    // 0-tagPk
    public final static String REDIS_KEY_CACHE_TAG = "CACHE.PUSH.TOKEN:TAG_{0}";

    /**
     * session
     */
    // 记录session的信息，0-sid
    public static final String REDIS_SESSION_SID_INFO = "switch_session:sid:info_{0}";
    // 记录session的变化，0-sid
    public static final String REDIS_SESSION_SID_HISTORY = "switch_session:sid:history_{0}";
    // 记录robotId的当前session，0-rcuId
    public static final String REDIS_SESSION_RCUID_SIDINFO = "switch_session:rcuId:info_{0}";
    // 记录robotId的session历史变化，0-rcuId
    public static final String REDIS_SESSION_RCUID_HISTORY = "switch_session:rcuId:history_{0}";

    /**
     * mongodb
     */
    // 0-type, 1-rcuId
    public static final String MONGO_TABLE_TYPE = "{0}_{1}";
    // 0-rcuId
    public static final String MONGO_TABLE_SESSION = "session_{0}";
    // 0-rcuId
    public static final String MONGO_TABLE_ATTRIBUTE = "attribute_{0}";
    // 0-rcuId
    public static final String MONGO_TABLE_CONFIG = "config_{0}";
    // 0-rcuId
    public static final String MONGO_TABLE_CONFIG_STATUS = "config_status_{0}";
    // 0-rcuId
    public static final String MONGO_TABLE_MAP_STATUS = "map_status_{0}";
    // 0-rcuId
    public static final String MONGO_TABLE_APP_CONFIG = "app_{0}";
    // 0-rcuId
    public static final String MONGO_TABLE_APP_INSTALL = "app_install_{0}";
    // 0-rcuId
    public static final String MONGO_TABLE_METRICS = "metrics_{0}";
    // 0-rcuId
    public static final String MONGO_TABLE_STATUS = "status_{0}";
    // 0-rcuId
    public static final String MONGO_TABLE_ONLINEPING = "onlinePing_{0}";
    // 0-rcuId
    public static final String MONGO_TABLE_ALARM = "alarm_{0}";
    // 0-tenantCode
    public static final String MONGO_TABLE_ALARM_INFO = "alarminfo_{0}";
    // 0-tenantCode
    public static final String MONGO_TABLE_ALARM_ALL = "alarminfo";

    // default operator encryption salt
    public static final String DEFAULT_OPERATOR_SALT = "aFjq3itwe32abf";
    // default user encryption salt
    public static final String DEFAULT_USER_SALT = "aFjq3ip9n";

    /**
     * 雄安大屏，HARIswitch认证相关
     */
    public static final String REDIS_HARI_SWITCH_AUTH = "hari_auth_patrol";
    public static final String REDIS_HARI_SWITCH_AUTH_LIST = "hari_auth_patrol:robotId_{0}";
    public static final String REDIS_HARI_SWITCH_AUTH_LIKE = "hari_auth_patrol:robotId_*";
    
    /**
     * 机器人提醒
     */
    public static final String REDIS_REMIND_LIST_ALL = "roc_remind:remind_list";
    public static final String REDIS_REMIND_LIST_TENANT = "roc_remind:remind_list_{0}";
    public static final String REDIS_REMIND_ITEM_INFO = "roc_remind:remind_item_{0}";
    
    /**
     * 机器人类型缓存
     */
    public static final String REDIS_ROBOT_TYPE_HARI_CACHE = "roc_dict:robot_type_hari";
    public static final String REDIS_ROBOT_TYPE_CACHE = "roc_dict:robot_label_type_{0}";
    
    // 默认策略配置,0robotType
    public static final String REDIS_DEFAULT_POLICY_TYPE = "config:default_policy_{0}";
    // 默认策略配置,0robotType, 1tenantCode
    public static final String REDIS_DEFAULT_POLICY_TYPE_TENANT = "config:default_policy_{0}_{1}";
    
    
    /**
     * 推送反馈
     */
    public static final String REDIS_PUSH_SERVER_LOG_ALL = "roc_push_log:push_logs";
    public static final String REDIS_PUSH_SERVER_LOG_TENANT = "roc_push_log:push_log_tenant_{0}";
    public static final String REDIS_PUSH_SERVER_LOG_ROBOT_CODE = "roc_push_log:push_log_root_code_{0}";
    public static final String REDIS_PUSH_SERVER_LOG_INFO = "roc_push_log:push_log_info_{0}";
    public static final String REDIS_PUSH_SERVER_LOG_BACK = "roc_push_log:push_log_back_{0}";
    
    /**
     * 地图
     */
    // 0rcuId
    public static final String REDIS_MAP_RCU = "roc_map:map_of_rcu_{0}";
    // 0mapId
    public static final String REDIS_MAP_INTENTS = "roc_map:intents_of_map_{0}";
    // 0storageId
    public static final String REDIS_MAP_STORAGEID = "roc_map:intents_of_storageid_{0}";
    
    /**
     * kafka
     */
    // ROC内部事件
    public static final String KAFKA_EVENT_TOPIC = "topic_events";
    // 激活
    public static final String KAFKA_EVENT_TYPE_ACTIVATION = "activation";
    // 取消激活
    public static final String KAFKA_EVENT_TYPE_UNACTIVATION = "unactivation";
    // 认证
    public static final String KAFKA_EVENT_TYPE_AUTH = "auth";
    // 获取配置
    public static final String KAFKA_EVENT_TYPE_GETCONF = "getconf";
    // 登录
    public static final String KAFKA_EVENT_TYPE_LOGIN = "login";
    // 退出
    public static final String KAFKA_EVENT_TYPE_LOGOUT = "logout";
    // 激活
    public static final String KAFKA_EVENT_TYPE_PERIPHERAL_ACTIVATION = "peripheral_activation";
    // 取消激活
    public static final String KAFKA_EVENT_TYPE_PERIPHERAL_UNACTIVATION = "peripheral_unactivation";
}
