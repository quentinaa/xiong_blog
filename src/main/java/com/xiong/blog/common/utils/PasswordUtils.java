package com.xiong.blog.common.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    // 明文  --》加密算法+盐值(唯一)--》密文
    // 123456    MD5---->000000
    // 123456    MD5---->000000

    public static String encode(String pw) {
        return BCrypt.hashpw(pw, BCrypt.gensalt()); // MD5,HASH ,盐值加密
    }

    public static boolean checkpw(String pw, String dbpw) {
        return BCrypt.checkpw(pw, dbpw); // MD5,HASH ,盐值加密
    }

    public static void main(String[] args) {
        String pw = "123456";// 明文

        // 加密
        String gensalt = BCrypt.gensalt();
        String hashpw = BCrypt.hashpw(pw, gensalt);// 随机生成盐值
        System.out.println("盐值:" + gensalt);
        System.out.println("密文:" + hashpw);

        // 认证
        boolean checkpw = BCrypt.checkpw(pw, hashpw);
        System.out.println(checkpw);
    }
}
