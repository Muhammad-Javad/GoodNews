package me.pitok.firebase.di.components

import dagger.Component
import me.pitok.coroutines.di.component.CoroutinesComponent
import me.pitok.firebase.FcmService
import me.pitok.firebase.di.modules.FcmApiModule
import me.pitok.firebase.di.modules.FcmRepositoryModule
import me.pitok.firebase.di.scopes.FcmScope
import me.pitok.networking.di.components.NetworkComponent
import me.pitok.options.di.components.OptionsComponent

@FcmScope
@Component(
    modules = [
        FcmRepositoryModule::class,
        FcmApiModule::class
    ],
    dependencies = [
        CoroutinesComponent::class,
        OptionsComponent::class,
        NetworkComponent::class
    ]
)
interface FcmComponent{
    fun inject(fcmService: FcmService)
}