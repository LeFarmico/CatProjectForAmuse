package io.amuse.codeassignment.injection

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.amuse.codeassignment.repository.api.CatsRepository
import io.amuse.codeassignment.repository.impl.CatsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCatsRepository(catsRepositoryImpl: CatsRepositoryImpl): CatsRepository
}
