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
            VStack {
                NavigationLink(destination: ArtworkScreen(artworkId: 14598)){
                    Text("Open Artwork 14598")
                }
            }
            .onAppear {
                artworksState.objectWillChange.send()
            }
        }
    }
}
