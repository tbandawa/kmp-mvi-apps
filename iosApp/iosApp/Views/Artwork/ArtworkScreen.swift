//
//  ArtworkScreen.swift
//  iosApp
//
//  Created by Tendai Bandawa on 2024/07/04.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import data

struct ArtworkScreen: View {
    
    @EnvironmentObject var artworkState: ArtworkState
    
    var artworkId: Int32
    
    var body: some View {
        ZStack {
            if let artwork = artworkState.artwork {
                ArtworkContent(artwork: artwork)
            }
            if artworkState.loading {
                LoadingContent()
            }
            if let errorMessage = artworkState.error {
                RetryContent(
                    error: errorMessage,
                    retry: {
                        artworkState.handleIntent(intent: ArtworksIntent.GetArtwork(id: artworkId))
                    }
                )
            }
        }
        .onAppear {
            artworkState.handleIntent(intent: ArtworksIntent.GetArtwork(id: artworkId))
        }
    }
}
