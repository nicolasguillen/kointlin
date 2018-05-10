package com.nicolasguillen.kointlin.utils

import android.content.res.Configuration
import android.os.Build
import android.util.Log
import androidx.test.InstrumentationRegistry
import java.util.*

object LocaleUtil {
    private val TAG = LocaleUtil::class.java.simpleName

    val testLocale: Locale?
        get() = localeFromInstrumentation("testLocale")

    val endingLocale: Locale?
        get() = localeFromInstrumentation("endingLocale")

    fun changeDeviceLocaleTo(locale: Locale?) {
        if (locale == null) {
            Log.w(TAG, "Skipping setting device locale to null")
        } else {
            try {
                var amnClass = Class.forName("android.app.ActivityManagerNative")
                val methodGetDefault = amnClass.getMethod("getDefault")
                methodGetDefault.isAccessible = true
                val activityManagerNative = methodGetDefault.invoke(amnClass)
                if (Build.VERSION.SDK_INT >= 26) {
                    amnClass = Class.forName(activityManagerNative.javaClass.name)
                }

                val methodGetConfiguration = amnClass.getMethod("getConfiguration")
                methodGetConfiguration.isAccessible = true
                val config = methodGetConfiguration.invoke(activityManagerNative) as Configuration
                config.javaClass.getField("userSetLocale").setBoolean(config, true)
                config.locale = locale
                if (Build.VERSION.SDK_INT >= 17) {
                    config.setLayoutDirection(locale)
                }

                val updateConfigurationMethod = amnClass.getMethod("updateConfiguration", Configuration::class.java)
                updateConfigurationMethod.isAccessible = true
                updateConfigurationMethod.invoke(activityManagerNative, config)
                Log.d(TAG, "Locale changed to $locale")
            } catch (var7: Exception) {
                Log.e(TAG, "Failed to change device locale to $locale", var7)
                throw RuntimeException(var7)
            }

        }
    }

    fun localePartsFrom(localeString: String?): Array<String>? {
        if (localeString == null) {
            return null
        } else {
            val localeParts = localeString.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return if (localeParts.size >= 1 && localeParts.size <= 3) localeParts else null
        }
    }

    fun localeFromParts(localeParts: Array<String>?): Locale? {
        return if (localeParts != null && localeParts.size != 0) {
            if (localeParts.size == 1) {
                Locale(localeParts[0])
            } else {
                if (localeParts.size == 2) Locale(localeParts[0], localeParts[1]) else Locale(localeParts[0], localeParts[1], localeParts[2])
            }
        } else {
            null
        }
    }

    private fun localeFromInstrumentation(key: String): Locale? {
        val localeString = InstrumentationRegistry.getArguments().getString(key)
        return localeFromParts(localePartsFrom(localeString))
    }
}