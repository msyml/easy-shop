package com.example.chen.kechensj.bean;

import java.io.Serializable;
import java.util.List;


public class UserInfor {

    private int status;
    private String msg;
    private Date data;

    public UserInfor() {
    }


    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public UserInfor(int status, String msg, Date data) {

        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



    public class Date {
        private int user_id;
        private String user_account;
        private String user_password;
        private String user_phone;
        private String user_sex;
        private String user_last_time;
        private String user_regis_time;
        private String user_birth;
        private double user_sum;

        @Override
        public String toString() {
            return "UserInfor [user_id=" + user_id + ", user_account=" + user_account + ", user_password=" + user_password
                    + ", user_phone=" + user_phone + ", user_sex=" + user_sex + ", user_last_time=" + user_last_time
                    + ", user_regis_time=" + user_regis_time + ", user_birth=" + user_birth + ", user_sum=" + user_sum
                    + "]";
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUser_account() {
            return user_account;
        }

        public void setUser_account(String user_account) {
            this.user_account = user_account;
        }

        public String getUser_password() {
            return user_password;
        }

        public void setUser_password(String user_password) {
            this.user_password = user_password;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getUser_sex() {
            return user_sex;
        }

        public void setUser_sex(String user_sex) {
            this.user_sex = user_sex;
        }

        public String getUser_last_time() {
            return user_last_time;
        }

        public void setUser_last_time(String user_last_time) {
            this.user_last_time = user_last_time;
        }

        public String getUser_regis_time() {
            return user_regis_time;
        }

        public void setUser_regis_time(String user_regis_time) {
            this.user_regis_time = user_regis_time;
        }

        public String getUser_birth() {
            return user_birth;
        }

        public void setUser_birth(String user_birth) {
            this.user_birth = user_birth;
        }

        public double getUser_sum() {
            return user_sum;
        }

        public void setUser_sum(double user_sum) {
            this.user_sum = user_sum;
        }
    }

}
