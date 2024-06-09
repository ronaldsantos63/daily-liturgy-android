package com.ronaldsantos.catholicliturgy.provider.language

import android.content.Context
import com.ronaldsantos.catholicliturgy.library.framework.pref.CacheManager
import java.util.Locale

class AppLanguageProvider(
    private val cacheManager: CacheManager
) : LanguageProvider {
    override fun saveLanguageCode(languageCode: String) {
        cacheManager.write(key = LANG_CODE, value = languageCode)
    }

    override fun getLanguageCode(): String {
        return cacheManager.read(key = LANG_CODE, defaultValue = DEFAULT_LANG)
    }

    override fun setLocale(language: String, context: Context) {
        updateResources(language, context)
    }

    private fun updateResources(language: String, context: Context) {
        var lang = language
        var country = ""
        if (language.split("-").size > 1) {
            lang = language.split("-")[0]
            country = language.split("-")[1]
        }
        val locale = Locale(lang, country)
        with(context.resources) {
            configuration.setLocale(locale)
            updateConfiguration(configuration, displayMetrics)
        }
    }

    private companion object {
        const val LANG_CODE = "lang_code"
        const val DEFAULT_LANG = "pt-BR"
    }
}
