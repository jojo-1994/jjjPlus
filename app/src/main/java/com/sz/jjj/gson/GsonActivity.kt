package com.sz.jjj.gson

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.sz.jjj.R
import java.lang.reflect.Modifier


/**
 * Created by jjj on 2018/3/23.
@description: gson的系统学习
原文：https://juejin.im/post/5aad29f8518825558453c6c9
 */
class GsonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gson_activity)

        // 生成json
        var jsonObject = JsonObject()
        jsonObject.addProperty("Number_Integer", 23);
        jsonObject.addProperty("Number_Double", 22.9);
        jsonObject.addProperty("Boolean", true);
        jsonObject.addProperty("Char", 'c');
        Log.e("json", jsonObject.toString())
        System.out.println(jsonObject);
        var userObject = JsonObject();
        userObject.addProperty("Name", "jjj")
        userObject.addProperty("Age", 25)
        jsonObject.add("User", userObject);
        Log.e("json", jsonObject.toString())

        // 序列化
        var user = User("jjj", 25)
        user.gender = false
//        var gson = Gson();
        var gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
        var userString = gson.toJson(user)
        Log.e("userString", userString)

        // 反序列化
//        var userJson = "{\"name\":\"leavesC\",\"age\":24}";
//        var userJson = "{\"userName\":\"leavesC\",\"age\":24}";
        var userJson = "{\"mName\":\"jjj\",\"age\":25,\"gender\":true}";
        var user2 = gson.fromJson(userJson, User::class.java)
        Log.e("userString", user2.toString())

        // 基于版本
        var gson2 = GsonBuilder().setVersion(2.0).create()
        var user3 = User(1, 2, 3, 4)
        Log.e("user3", gson2.toJson(user3))

        // 基于访问修饰符, 和kotlin中的修饰符冲突，结果有误。想要正确运行放到java中
        var gson3 = GsonBuilder().excludeFieldsWithModifiers(Modifier.PRIVATE, Modifier.STRICT).create()
        var modifier = ModifierSample()
        Log.e("user4", gson3.toJson(modifier))

        // 格式化输出gson
        var gson4 = GsonBuilder()
                .serializeNulls() // 输出null
                .setPrettyPrinting() // 格式化输出
                .create()
        var user4 = User("kkk", 25)
        Log.e("user4", gson4.toJson(user4))
    }
}