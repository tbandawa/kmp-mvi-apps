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
                            .onAppear {
                                if (items.last == item) {
                                    
                                }
                            }
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
            .toolbar {
                ToolbarItem(placement: .navigation) {
                    Image("app_logo")
                        .resizable()
                        .scaledToFit()
                        .frame(width: 35, height: 35)
                }
                ToolbarItem(placement: .navigation) {
                    Text("Art Institute of Chicago")
                        .font(.system(size: 22, weight: .bold, design: .rounded))
                        .foregroundColor(.black)
                }
            }
        }
    }
}
