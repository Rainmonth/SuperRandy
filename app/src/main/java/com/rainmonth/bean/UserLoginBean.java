package com.rainmonth.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by RandyZhang on 2016/10/20.
 */

public class UserLoginBean implements Serializable{

    /**
     * boolen : 1
     * message : 登陆成功
     * data : {"uid":"70522","uname":"u70522","real_name":"孙漂徽","vip_group_id":",,","sex":"0","mobile":"15601949622","person_id":"500114198310254152","is_mobile_auth":"1","is_id_auth":"1","is_active":"1","before_logintime":"1476950126","is_paypwd_edit":"0","is_change_uname":"0","is_set_uname":"0","is_paypwd_mobile_set":"1","is_visible":"1","source":"Your_channel_id","uc_id":"23409","safe_level":60,"safe_level_label":"b","is_set_photo":0,"ava":{"url":"https://escrow.tourongjia.com/public/images/remote/default_03.jpg","url_s700":"https://escrow.tourongjia.com/public/images/remote/default_03.jpg","url_s300":"https://escrow.tourongjia.com/public/images/remote/default_03.jpg","url_s100":"https://escrow.tourongjia.com/public/images/remote/default_03.jpg","url_s50":"https://escrow.tourongjia.com/public/images/remote/default_03.jpg"},"identity_no":"","is_newbie":"0","remindList":[],"is_binding_bank":"0","is_could_invest":"1","is_set_sqa":"0","is_all":"0","is_recharged":"0","user_is_qfx":false,"usc_token":"622ccd1e8a56b4c5b773f4292970074c"}
     */

    private String boolen;
    private String message;
    /**
     * uid : 70522
     * uname : u70522
     * real_name : 孙漂徽
     * vip_group_id : ,,
     * sex : 0
     * mobile : 15601949622
     * person_id : 500114198310254152
     * is_mobile_auth : 1
     * is_id_auth : 1
     * is_active : 1
     * before_logintime : 1476950126
     * is_paypwd_edit : 0
     * is_change_uname : 0
     * is_set_uname : 0
     * is_paypwd_mobile_set : 1
     * is_visible : 1
     * source : Your_channel_id
     * uc_id : 23409
     * safe_level : 60
     * safe_level_label : b
     * is_set_photo : 0
     * ava : {"url":"https://escrow.tourongjia.com/public/images/remote/default_03.jpg","url_s700":"https://escrow.tourongjia.com/public/images/remote/default_03.jpg","url_s300":"https://escrow.tourongjia.com/public/images/remote/default_03.jpg","url_s100":"https://escrow.tourongjia.com/public/images/remote/default_03.jpg","url_s50":"https://escrow.tourongjia.com/public/images/remote/default_03.jpg"}
     * identity_no :
     * is_newbie : 0
     * remindList : []
     * is_binding_bank : 0
     * is_could_invest : 1
     * is_set_sqa : 0
     * is_all : 0
     * is_recharged : 0
     * user_is_qfx : false
     * usc_token : 622ccd1e8a56b4c5b773f4292970074c
     */

    private DataBean data;

    public String getBoolen() {
        return boolen;
    }

    public void setBoolen(String boolen) {
        this.boolen = boolen;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    private static class DataBean {
        private String uid;
        private String uname;
        private String real_name;
        private String vip_group_id;
        private String sex;
        private String mobile;
        private String person_id;
        private String is_mobile_auth;
        private String is_id_auth;
        private String is_active;
        private String before_logintime;
        private String is_paypwd_edit;
        private String is_change_uname;
        private String is_set_uname;
        private String is_paypwd_mobile_set;
        private String is_visible;
        private String source;
        private String uc_id;
        private int safe_level;
        private String safe_level_label;
        private int is_set_photo;
        /**
         * url : https://escrow.tourongjia.com/public/images/remote/default_03.jpg
         * url_s700 : https://escrow.tourongjia.com/public/images/remote/default_03.jpg
         * url_s300 : https://escrow.tourongjia.com/public/images/remote/default_03.jpg
         * url_s100 : https://escrow.tourongjia.com/public/images/remote/default_03.jpg
         * url_s50 : https://escrow.tourongjia.com/public/images/remote/default_03.jpg
         */

        private AvaBean ava;
        private String identity_no;
        private String is_newbie;
        private String is_binding_bank;
        private String is_could_invest;
        private String is_set_sqa;
        private String is_all;
        private String is_recharged;
        private boolean user_is_qfx;
        private String usc_token;
        private List<?> remindList;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getVip_group_id() {
            return vip_group_id;
        }

        public void setVip_group_id(String vip_group_id) {
            this.vip_group_id = vip_group_id;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPerson_id() {
            return person_id;
        }

        public void setPerson_id(String person_id) {
            this.person_id = person_id;
        }

        public String getIs_mobile_auth() {
            return is_mobile_auth;
        }

        public void setIs_mobile_auth(String is_mobile_auth) {
            this.is_mobile_auth = is_mobile_auth;
        }

        public String getIs_id_auth() {
            return is_id_auth;
        }

        public void setIs_id_auth(String is_id_auth) {
            this.is_id_auth = is_id_auth;
        }

        public String getIs_active() {
            return is_active;
        }

        public void setIs_active(String is_active) {
            this.is_active = is_active;
        }

        public String getBefore_logintime() {
            return before_logintime;
        }

        public void setBefore_logintime(String before_logintime) {
            this.before_logintime = before_logintime;
        }

        public String getIs_paypwd_edit() {
            return is_paypwd_edit;
        }

        public void setIs_paypwd_edit(String is_paypwd_edit) {
            this.is_paypwd_edit = is_paypwd_edit;
        }

        public String getIs_change_uname() {
            return is_change_uname;
        }

        public void setIs_change_uname(String is_change_uname) {
            this.is_change_uname = is_change_uname;
        }

        public String getIs_set_uname() {
            return is_set_uname;
        }

        public void setIs_set_uname(String is_set_uname) {
            this.is_set_uname = is_set_uname;
        }

        public String getIs_paypwd_mobile_set() {
            return is_paypwd_mobile_set;
        }

        public void setIs_paypwd_mobile_set(String is_paypwd_mobile_set) {
            this.is_paypwd_mobile_set = is_paypwd_mobile_set;
        }

        public String getIs_visible() {
            return is_visible;
        }

        public void setIs_visible(String is_visible) {
            this.is_visible = is_visible;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getUc_id() {
            return uc_id;
        }

        public void setUc_id(String uc_id) {
            this.uc_id = uc_id;
        }

        public int getSafe_level() {
            return safe_level;
        }

        public void setSafe_level(int safe_level) {
            this.safe_level = safe_level;
        }

        public String getSafe_level_label() {
            return safe_level_label;
        }

        public void setSafe_level_label(String safe_level_label) {
            this.safe_level_label = safe_level_label;
        }

        public int getIs_set_photo() {
            return is_set_photo;
        }

        public void setIs_set_photo(int is_set_photo) {
            this.is_set_photo = is_set_photo;
        }

        public AvaBean getAva() {
            return ava;
        }

        public void setAva(AvaBean ava) {
            this.ava = ava;
        }

        public String getIdentity_no() {
            return identity_no;
        }

        public void setIdentity_no(String identity_no) {
            this.identity_no = identity_no;
        }

        public String getIs_newbie() {
            return is_newbie;
        }

        public void setIs_newbie(String is_newbie) {
            this.is_newbie = is_newbie;
        }

        public String getIs_binding_bank() {
            return is_binding_bank;
        }

        public void setIs_binding_bank(String is_binding_bank) {
            this.is_binding_bank = is_binding_bank;
        }

        public String getIs_could_invest() {
            return is_could_invest;
        }

        public void setIs_could_invest(String is_could_invest) {
            this.is_could_invest = is_could_invest;
        }

        public String getIs_set_sqa() {
            return is_set_sqa;
        }

        public void setIs_set_sqa(String is_set_sqa) {
            this.is_set_sqa = is_set_sqa;
        }

        public String getIs_all() {
            return is_all;
        }

        public void setIs_all(String is_all) {
            this.is_all = is_all;
        }

        public String getIs_recharged() {
            return is_recharged;
        }

        public void setIs_recharged(String is_recharged) {
            this.is_recharged = is_recharged;
        }

        public boolean isUser_is_qfx() {
            return user_is_qfx;
        }

        public void setUser_is_qfx(boolean user_is_qfx) {
            this.user_is_qfx = user_is_qfx;
        }

        public String getUsc_token() {
            return usc_token;
        }

        public void setUsc_token(String usc_token) {
            this.usc_token = usc_token;
        }

        public List<?> getRemindList() {
            return remindList;
        }

        public void setRemindList(List<?> remindList) {
            this.remindList = remindList;
        }

        public static class AvaBean {
            private String url;
            private String url_s700;
            private String url_s300;
            private String url_s100;
            private String url_s50;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getUrl_s700() {
                return url_s700;
            }

            public void setUrl_s700(String url_s700) {
                this.url_s700 = url_s700;
            }

            public String getUrl_s300() {
                return url_s300;
            }

            public void setUrl_s300(String url_s300) {
                this.url_s300 = url_s300;
            }

            public String getUrl_s100() {
                return url_s100;
            }

            public void setUrl_s100(String url_s100) {
                this.url_s100 = url_s100;
            }

            public String getUrl_s50() {
                return url_s50;
            }

            public void setUrl_s50(String url_s50) {
                this.url_s50 = url_s50;
            }
        }
    }
}
