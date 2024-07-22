package me.tbandawa.android.aic.remote.mapper

import me.tbandawa.android.aic.domain.mapper.ResponseMapper
import me.tbandawa.android.aic.domain.models.Artwork
import me.tbandawa.android.aic.remote.responses.ArtworkResponse
import me.tbandawa.android.aic.remote.responses.ArtworksResponse
import me.tbandawa.android.aic.remote.responses.ArtworkEntity

class ArtworkMapper : ResponseMapper<ArtworkResponse, ArtworksResponse, ArtworkEntity, Artwork> {

    override fun mapResponseToModel(response: ArtworkResponse): Artwork {
        return mapEntityToModel(response.data)
    }

    override fun mapResponseToModels(response: ArtworksResponse): List<Artwork> {
        return response.data.map { mapEntityToModel(it) }
    }

    override fun mapEntityToModel(entity: ArtworkEntity): Artwork {
        return Artwork(
            entity.id,
            entity.title,
            entity.mainReferenceNumber,
            entity.dateDisplay,
            entity.artistDisplay,
            entity.departmentTitle,
            entity.placeOfOrigin,
            entity.artDescription,
            entity.mediumDisplay,
            entity.inscriptions,
            entity.creditLine,
            entity.catalogueDisplay,
            entity.publicationHistory,
            entity.exhibitionHistory,
            entity.provenanceText,
            entity.artistTitle,
            entity.imageId
        )
    }
}