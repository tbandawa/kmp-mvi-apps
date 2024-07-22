package me.tbandawa.android.aic.domain.mapper

interface ResponseMapper<I, J, K, M>  {
    fun mapResponseToModel(entity: I): M
    fun mapResponseToModels(entity: J): List<M>
    fun mapEntityToModel(entity: K): M
}