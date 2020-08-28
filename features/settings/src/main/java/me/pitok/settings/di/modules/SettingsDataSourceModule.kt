package me.pitok.settings.di.modules

import dagger.Binds
import dagger.Module
import me.pitok.dependencyinjection.shared.SharedScope
import me.pitok.settings.datasource.NotifsCountSettingReader
import me.pitok.settings.datasource.UISettingReader
import me.pitok.settings.entity.NotifsCount
import me.pitok.settings.entity.UIMode
import me.pitok.datasource.Readable
import me.pitok.dependencyinjection.feature.FeatureScope

@Module
interface SettingsDataSourceModule {

    @Binds
    @FeatureScope
    fun provideUIReadable(uiDataSource: UISettingReader): Readable<UIMode>

    @Binds
    @FeatureScope
    fun provideNotifsCountReadable(notifsCountDataSource: NotifsCountSettingReader): Readable<NotifsCount>

}