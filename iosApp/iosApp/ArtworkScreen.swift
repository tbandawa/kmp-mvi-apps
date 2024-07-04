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
            
        }
        .onAppear {
            artworkState.handleIntent(intent: ArtworksIntent.GetArtwork(id: artworkId))
        }
    }
}
