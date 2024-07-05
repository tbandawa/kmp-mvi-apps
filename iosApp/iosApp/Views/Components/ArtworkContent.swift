//
//  ArtworkContent.swift
//  iosApp
//
//  Created by Tendai Bandawa on 2024/07/04.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import data

struct ArtworkContent: View {
    
    var artwork: Artwork
    
    var body: some View {
        ScrollView {
            LazyVStack(alignment: .leading) {
                
                if let imageId = artwork.imageId {
                    AsyncImage(url: URL(string: "https://www.artic.edu/iiif/2/\(imageId)/full/843,/0/default.jpg")) { image in
                        image
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                    } placeholder: {
                        Color.red
                    }
                }
                
                if let title = artwork.title {
                    Text("\(title)")
                        .font(.system(size: 18, weight: .medium, design: .rounded))
                        .foregroundColor(.black)
                        .frame(maxWidth: .infinity, alignment: .topLeading)
                        .padding(.top, 2)
                }
                
                if let dateDisplay = artwork.dateDisplay {
                    Text("\(dateDisplay)")
                        .font(.system(size: 16, weight: .regular, design: .rounded))
                        .foregroundColor(.gray)
                        .frame(maxWidth: .infinity, alignment: .topLeading)
                        .padding(.top, 2)
                }
                
                if let artistDisplay = artwork.artistDisplay {
                    Text("\(artistDisplay)")
                        .font(.system(size: 16, weight: .regular, design: .rounded))
                        .foregroundColor(.gray)
                        .frame(maxWidth: .infinity, alignment: .topLeading)
                        .padding(.top, 2)
                }
                
                if let artDescription = artwork.artDescription {
                    Text("\(artDescription)")
                        .font(.system(size: 16, weight: .regular, design: .rounded))
                        .foregroundColor(.gray)
                        .frame(maxWidth: .infinity, alignment: .topLeading)
                        .padding(.top, 2)
                }
                
                if let artistTitle = artwork.artistTitle {
                    ArtworkDetail(title: "Artist", value: artistTitle)
                }
                
                if let title = artwork.title {
                    ArtworkDetail(title: "Title", value: title)
                }
                
                if let placeOfOrigin = artwork.placeOfOrigin {
                    ArtworkDetail(title: "Place", value: placeOfOrigin)
                }
                
                if let dateDisplay = artwork.dateDisplay {
                    ArtworkDetail(title: "Date", value: dateDisplay)
                }
                
                if let mediumDisplay = artwork.mediumDisplay {
                    ArtworkDetail(title: "Medium", value: mediumDisplay)
                }
                
                if let inscriptions = artwork.inscriptions {
                    ArtworkDetail(title: "Inscriptions", value: inscriptions)
                }
                
                if let creditLine = artwork.creditLine {
                    ArtworkDetail(title: "Credit Line", value: creditLine)
                }
                
                if let mainReferenceNumber = artwork.mainReferenceNumber {
                    ArtworkDetail(title: "Reference No.", value: mainReferenceNumber)
                }
                
                if let publicationHistory = artwork.publicationHistory {
                    ArtworkInfo(title: "PUBLICATION HISTORY", value: publicationHistory)
                }
                
                if let exhibitionHistory = artwork.exhibitionHistory {
                    ArtworkInfo(title: "EXHIBITION HISTORY", value: exhibitionHistory)
                }
                
                if let provenanceText = artwork.provenanceText {
                    ArtworkInfo(title: "PROVENANCE", value: provenanceText)
                }
                
                if let catalogueDisplay = artwork.catalogueDisplay {
                    ArtworkInfo(title: "CATALOGUE RAISONNÉS", value: catalogueDisplay)
                }
                
            }
            .padding(.leading, 16)
            .padding(.trailing, 16)
        }
    }
}
