package me.tbandawa.android.aic.domain.mapper

import me.tbandawa.android.aic.domain.models.Artworks

interface ResponseMapper<I, L, O, P>  {
    fun mapToModel(entity: I): O
    fun mapToModelList(entity: L): P
}