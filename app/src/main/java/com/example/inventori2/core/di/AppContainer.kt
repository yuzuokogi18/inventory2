package com.example.inventori2.core.di

import android.content.Context
import androidx.room.Room
import com.example.inventori2.core.database.AppDatabase
import com.example.inventori2.core.database.dao.ProductDao
import com.example.inventori2.core.database.dao.UserDao
import com.example.inventori2.core.datastore.TokenDataStore
import com.example.inventori2.features.login.data.repositories.LoginRepositoryImpl
import com.example.inventori2.features.login.domain.repositories.LoginRepository
import com.example.inventori2.features.login.domain.usecases.LoginUseCase
import com.example.inventori2.features.register.data.repositories.RegisterRepositoryImpl
import com.example.inventori2.features.register.domain.repositories.RegisterRepository
import com.example.inventori2.features.register.domain.usecase.RegisterUseCase
import com.example.inventori2.features.product_create.data.repositories.CreateProductRepositoryImpl
import com.example.inventori2.features.product_create.domain.repositories.CreateProductRepository
import com.example.inventori2.features.product_create.domain.usecases.CreateProductUseCase
import com.example.inventori2.features.product_list.data.repositories.ProductRepositoryImpl
import com.example.inventori2.features.product_list.domain.repositories.ProductRepository
import com.example.inventori2.features.product_list.domain.usecases.GetAllProductUseCase
import com.example.inventori2.features.product_detail.data.repositories.ProductDetailRepositoryImpl
import com.example.inventori2.features.product_detail.domain.repositories.ProductDetailRepository
import com.example.inventori2.features.product_detail.domain.usecases.GetProductByIdUseCase
import com.example.inventori2.features.product_edit.data.repositories.ProductEditRepositoryImpl
import com.example.inventori2.features.product_edit.domain.repositories.ProductEditRepository
import com.example.inventori2.features.product_edit.domain.usecases.UpdateProductUseCase
import com.example.inventori2.features.product_delete.data.repositories.ProductDeleteRepositoryImpl
import com.example.inventori2.features.product_delete.domain.repositories.ProductDeleteRepository
import com.example.inventori2.features.product_delete.domain.usecases.DeleteProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppContainer { // Cambiado a object para mejor compatibilidad con Hilt

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "inventori_db"
        )
        .fallbackToDestructiveMigration() // Permite cambios en la DB sin crashes (borra datos viejos)
        .build()
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): TokenDataStore {
        return TokenDataStore(context)
    }

    @Provides
    fun provideProductDao(db: AppDatabase): ProductDao = db.productDao()

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    @Singleton
    fun provideLoginRepository(userDao: UserDao, ds: TokenDataStore): LoginRepository = LoginRepositoryImpl(userDao, ds)

    @Provides
    @Singleton
    fun provideRegisterRepository(userDao: UserDao): RegisterRepository = RegisterRepositoryImpl(userDao)

    @Provides
    @Singleton
    fun provideCreateProductRepository(dao: ProductDao, ds: TokenDataStore): CreateProductRepository = CreateProductRepositoryImpl(dao, ds)

    @Provides
    @Singleton
    fun provideProductRepository(dao: ProductDao, ds: TokenDataStore): ProductRepository = ProductRepositoryImpl(dao, ds)

    @Provides
    @Singleton
    fun provideProductDetailRepository(dao: ProductDao): ProductDetailRepository = ProductDetailRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideProductEditRepository(dao: ProductDao): ProductEditRepository = ProductEditRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideProductDeleteRepository(dao: ProductDao): ProductDeleteRepository = ProductDeleteRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideLoginUseCase(repo: LoginRepository): LoginUseCase = LoginUseCase(repo)

    @Provides
    @Singleton
    fun provideRegisterUseCase(repo: RegisterRepository): RegisterUseCase = RegisterUseCase(repo)

    @Provides
    @Singleton
    fun provideCreateProductUseCase(repo: CreateProductRepository): CreateProductUseCase = CreateProductUseCase(repo)

    @Provides
    @Singleton
    fun provideGetAllProductUseCase(repo: ProductRepository): GetAllProductUseCase = GetAllProductUseCase(repo)

    @Provides
    @Singleton
    fun provideGetProductByIdUseCase(repo: ProductDetailRepository): GetProductByIdUseCase = GetProductByIdUseCase(repo)

    @Provides
    @Singleton
    fun provideUpdateProductUseCase(repo: ProductEditRepository): UpdateProductUseCase = UpdateProductUseCase(repo)

    @Provides
    @Singleton
    fun provideDeleteProductUseCase(repo: ProductDeleteRepository): DeleteProductUseCase = DeleteProductUseCase(repo)
}
