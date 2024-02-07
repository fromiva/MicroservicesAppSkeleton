package com.example.app.users.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import java.util.Locale

@Configuration
open class JdbcConfiguration: AbstractJdbcConfiguration() {

    override fun userConverters(): List<*> {
        return listOf(LocaleToStringConverter(), StringToLocaleConverter())
    }

    @WritingConverter
    @FunctionalInterface
    private class LocaleToStringConverter: Converter<Locale, String> {
        override fun convert(locale: Locale): String {
            return locale.toLanguageTag()
        }
    }

    @ReadingConverter
    @FunctionalInterface
    private class StringToLocaleConverter: Converter<String, Locale> {
        override fun convert(languageTag: String): Locale {
            return Locale.forLanguageTag(languageTag)
        }
    }
}
