//
//  ArtworkItem.swift
//  iosApp
//
//  Created by Tendai Bandawa on 2024/07/06.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import data

struct Item: Identifiable, Equatable {
    var uuid = UUID()
    var id: Int
    var title: String
    var artistDisplay: String
    var departmentTitle: String
    var imageId: String
}

extension ArtworksState {
    
    func mapToItems(items: [Artwork]) -> [Item] {
        return items.map(
            {(artwork) -> Item in
                Item(
                    id: artwork.id as! Int,
                    title: artwork.title!,
                    artistDisplay: artwork.artistDisplay!,
                    departmentTitle: artwork.departmentTitle!,
                    imageId: artwork.imageId!
                )
            }
        )
    }
}

