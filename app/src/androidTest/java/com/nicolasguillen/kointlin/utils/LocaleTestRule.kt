package com.nicolasguillen.kointlin.utils

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.*

class LocaleTestRule @JvmOverloads constructor(private val testLocale: Locale? = LocaleUtil.testLocale, private val endingLocale: Locale? = LocaleUtil.endingLocale) : TestRule {

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                try {
                    if (this@LocaleTestRule.testLocale != null) {
                        LocaleUtil.changeDeviceLocaleTo(this@LocaleTestRule.testLocale)
                    }

                    base.evaluate()
                } finally {
                    if (this@LocaleTestRule.endingLocale != null) {
                        LocaleUtil.changeDeviceLocaleTo(this@LocaleTestRule.endingLocale)
                    }

                }

            }
        }
    }
}
