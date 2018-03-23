package com.sz.jjj.gson

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Since
import com.google.gson.annotations.Until

/**
 * Created by jjj on 2018/3/23.
@description:
 */
data class User(
        @Expose(serialize = false, deserialize = true)
        @SerializedName("userName", alternate = arrayOf("mName"))
        val name: String = "",
        @Expose(serialize = true, deserialize = true)
        val age: Int) {

    @Expose(serialize = true, deserialize = false)
    var gender: Boolean? = null

    constructor(a: Int, b: Int, c: Int, d: Int) : this("jjj", 25) {
        this.a = a
        this.b = b
        this.c = c
        this.d = d
    }

    @Since(3.0)
    var a: Int = 0
    @Since(4.0)
    var b: Int = 0
    @Until(1.0)
    var c: Int = 0
    @Until(2.0)
    var d: Int = 0

    override fun toString(): String {
        return name + age + gender + "a=" + a + "b=" + b + "c=" + c + "d=" + d
    }
}