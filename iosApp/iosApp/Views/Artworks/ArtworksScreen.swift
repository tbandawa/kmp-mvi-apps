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
        NavigationStack {
            VStack {
                ZStack {
                    if let items = artworksState.items {
                        List {
                            ForEach(artworksState.items!, id: \.id) { item in
                                ItemArtwork(item: item)
                                    .listRowSeparator(.hidden)
                                    .background(NavigationLink("", destination: ArtworkScreen(artworkId: Int32(item.id))).opacity(0))
                                    .onAppear {
                                        if (items.last == item) {
                                            artworksState.getArtworks()
                                        }
                                    }
                            }
                            if ((artworksState.loading && artworksState.items!.count > 0) || (artworksState.error != nil && artworksState.items!.count > 0)) {
                                LoadingMoreView(
                                    errorMessage: artworksState.error,
                                    retry: { artworksState.getArtworks() }
                                )
                            }
                         }
                        .refreshable {
                            if (!artworksState.loading) {
                                artworksState.refreshArtworks()
                            }
                        }
                    }
                    if artworksState.loading {
                        if (artworksState.items?.count == 0) {
                            LoadingContent()
                        }
                    }
                    if let errorMessage = artworksState.error {
                        if (artworksState.items?.count == 0) {
                            RetryContent(
                                error: errorMessage,
                                retry: {
                                    artworksState.getArtworks()
                                }
                            )
                        }
                    }
                }
            }
            .toolbar {
                ToolbarItem(placement: .navigation) {
                    Image("app_logo")
                        .resizable()
                        .scaledToFit()
                        .frame(width: 35, height: 35)
                }
            }
            .navigationTitle("Art Institute of Chicago")
        }
    }
}
