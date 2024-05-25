package com.ronaldsantos.catholicliturgy.library.framework.pref

interface CacheManager {
    fun <T> read(key: String, defaultValue: T): T
    fun <T> write(key: String, value: T)
    fun clear(key: String)
    fun clearEverything(callBack: () -> Unit = {})
    fun writeObject(key: String, value: Any)
}
