package io.amuse.codeassignment.injection

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.amuse.codeassignment.domain.api.CatsApi
import io.amuse.codeassignment.domain.impl.CatsApiImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiModule {

    @Binds
    @Singleton
    abstract fun bindCatsApi(impl: CatsApiImpl): CatsApi
}
