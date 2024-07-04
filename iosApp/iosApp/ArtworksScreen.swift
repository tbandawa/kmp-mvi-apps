//
//  ArtworksScreen.swift
//  iosApp
//
//  Created by Tendai Bandawa on 2024/07/04.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ArtworksScreen: View {
    var body: some View {
        NavigationView {
            VStack {
                Button(action: {
                    
                }) {
                    Text("Get Artwork 14598")
                }
                NavigationLink(destination: ArtworkScreen(artworkId: 14598)){
                    Text("Open Artwork 14598")
                }
            }
        }
    }
}
