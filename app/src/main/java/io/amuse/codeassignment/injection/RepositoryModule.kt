package io.amuse.codeassignment.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.amuse.codeassignment.domain.api.CatsApi
import io.amuse.codeassignment.repository.CatsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideCatsRepository(api: CatsApi) = CatsRepository(api)
}
