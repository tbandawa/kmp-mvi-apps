package me.tbandawa.kmm.aic.domain.mapper

interface ResponseMapper<I, O>  {
    fun mapToModel(entity: I): O
}