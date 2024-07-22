package me.tbandawa.android.aic.remote.mapper

import me.tbandawa.android.aic.domain.mapper.ResponseMapper
import me.tbandawa.android.aic.remote.responses.ArtworkResponse
import me.tbandawa.android.aic.remote.responses.ArtworksResponse
import me.tbandawa.kmm.aic.domain.models.Artwork
import me.tbandawa.android.aic.domain.models.Artworks

class ArtworkMapper : ResponseMapper<ArtworkResponse, ArtworksResponse, Artwork, Artworks> {

    override fun mapToModel(entity: ArtworkResponse): Artwork {
        val artworkEntity = entity.data
        return Artwork(
            artworkEntity.id,
            artworkEntity.title,
            artworkEntity.mainReferenceNumber,
            artworkEntity.dateDisplay,
            artworkEntity.artistDisplay,
            artworkEntity.placeOfOrigin,
            artworkEntity.artDescription,
            artworkEntity.mediumDisplay,
            artworkEntity.inscriptions,
            artworkEntity.creditLine,
            artworkEntity.catalogueDisplay,
            artworkEntity.publicationHistory,
            artworkEntity.exhibitionHistory,
            artworkEntity.provenanceText,
            artworkEntity.artistTitle,
            artworkEntity.imageId
        )
    }

    override fun mapToModelList(entity: ArtworksResponse): Artworks {
        return Artworks(
            data = entity.data.map {
                Artwork(
                    it.id,
                    it.title,
                    it.mainReferenceNumber,
                    it.dateDisplay,
                    it.artistDisplay,
                    it.placeOfOrigin,
                    it.artDescription,
                    it.mediumDisplay,
                    it.inscriptions,
                    it.creditLine,
                    it.catalogueDisplay,
                    it.publicationHistory,
                    it.exhibitionHistory,
                    it.provenanceText,
                    it.artistTitle,
                    it.imageId
                )
            }
        )
    }
}