package com.example.accountrecord;


/***
 * 模拟假数据用户的电话号码
 *
 *
 */

public class SelectPhone  {
        private String account_id;
        private String name;
        private String password;
        private String user_type;
        private String token;
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {


            this.password = password;
        }
       public String getAccount_id() {
         return account_id;
       }

        public void setAccount_id(String account_id) {
        this.account_id = account_id;
       }



}
