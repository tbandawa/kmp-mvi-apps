//
//  ArtworksScreen.swift
//  iosApp
//
//  Created by Tendai Bandawa on 2024/07/04.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import data

struct ArtworksScreen: View {
    
    @EnvironmentObject var artworksState: ArtworksState
    
    var body: some View {
        NavigationView {
            ZStack {
                if let items = artworksState.items {
                    List(items) { item in
                        ItemArtwork(item: item)
                            .listRowSeparator(.hidden)
                            .background(NavigationLink("", destination: ArtworkScreen(artworkId: Int32(item.id))).opacity(0))
                    }
                }
                if artworksState.loading {
                    LoadingContent()
                }
                if let errorMessage = artworksState.error {
                    RetryContent(
                        error: errorMessage,
                        retry: {}
                    )
                }
            }
        }
    }
}
