package com.vocation.travel.common.constant;

public class CommonConstant {
    public static final int RESPONSE_SUCCESS = 1;
    public static final int RESPONSE_FAIL = 0;
    public static final String METHOD_CREATE = "create";
    public static final String METHOD_DELETE = "delete";
    public static final String METHOD_READ = "read";
    public static final String METHOD_UPDATE = "update";
    public static class RoleTrip {
        public static final String OWNER = "owner";
        public static final String MEMBER = "member";
    }

    public static class ProcessStatus {
        public static final boolean FAIL = Boolean.FALSE;
        public static final boolean Success = Boolean.TRUE;
    }

    public static class StatusFriend {
        public static final String ACTIVE = "active";
        public static final String PENDING = "pending";
        public static final String DELETE = "delete";
    }

    public static class Notification {
        public static final String NOTI_FRIEND = "NOTI-FRIEND";
        public static final String NOTI_SYSTEM = "NOTI-SYSTEM";
    }
}
