package me.pitok.sharedpreferences.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import me.pitok.androidcore.qulifiers.ApplicationContext
import me.pitok.dependencyinjection.library.LibraryScope
import me.pitok.sharedpreferences.Keys
import me.pitok.sharedpreferences.di.qulifiers.SettingsSP
import me.pitok.sharedpreferences.di.qulifiers.TokenSP
import me.pitok.sharedpreferences.settings.SettingReaderImpl
import me.pitok.sharedpreferences.settings.SettingWriterImpl
import me.pitok.sharedpreferences.tokens.TokenReaderImpl
import me.pitok.sharedpreferences.tokens.TokenWriterImpl
import me.pitok.sharedpreferences.typealiases.SpReader
import me.pitok.sharedpreferences.typealiases.SpWriter

@Module
class SharedPreferencesModule{

    @Provides
    @LibraryScope
    @TokenSP
    fun provideTokenSharedPreferences(@ApplicationContext context: Context): SharedPreferences{
        return context.getSharedPreferences(Keys.TOKENS_SP_NAME,Context.MODE_PRIVATE)
    }

    @Provides
    @LibraryScope
    @TokenSP
    fun provideTokenSharedPreferencesEditor(@TokenSP spToken: SharedPreferences): SharedPreferences.Editor{
        return spToken.edit()
    }

    @Provides
    @LibraryScope
    @SettingsSP
    fun provideSettingsSharedPreferences(@ApplicationContext context: Context): SharedPreferences{
        return context.getSharedPreferences(Keys.SETTINGS_SP_NAME,Context.MODE_PRIVATE)
    }

    @Provides
    @LibraryScope
    @SettingsSP
    fun provideSettingsSharedPreferencesEditor(@SettingsSP spSettings: SharedPreferences): SharedPreferences.Editor{
        return spSettings.edit()
    }

    @Provides
    @LibraryScope
    @TokenSP
    fun provideTokenReaderImpl(@TokenSP spTokens: SharedPreferences): SpReader{
        return TokenReaderImpl(spTokens)
    }

    @Provides
    @LibraryScope
    @TokenSP
    fun provideTokenWriterImpl(@TokenSP tokensEditor: SharedPreferences.Editor): SpWriter {
        return TokenWriterImpl(tokensEditor)
    }

    @Provides
    @LibraryScope
    @SettingsSP
    fun provideSettingReaderImpl(@SettingsSP spSettings: SharedPreferences): SpReader{
        return SettingReaderImpl(spSettings)
    }

    @Provides
    @LibraryScope
    @SettingsSP
    fun provideSettingWriterImpl(@SettingsSP settingsEditor: SharedPreferences.Editor): SpWriter {
        return SettingWriterImpl(settingsEditor)
    }

}