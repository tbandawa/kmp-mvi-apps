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
    
    var artworkId: Int32
    
    @EnvironmentObject var artworkState: ArtworkState
    
    var body: some View {
        ZStack {
            
        }
        .onAppear {
            artworkState.viewModel.handleIntent(intent: ArtworksIntent.GetArtwork(id: artworkId))
        }
            
    }
}
