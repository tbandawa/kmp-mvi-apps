//
//  ArtworkState.swift
//  iosApp
//
//  Created by Tendai Bandawa on 2024/07/04.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import data

class ArtworkState: ObservableObject {
    
    let viewModel: ArtworkViewModel = DataHelper().artworkViewModel
    
    @Published var loading = true
    @Published var error: String?
    @Published var artwork: Artwork?
    
    func handleIntent(intent: ArtworksIntent) {
        viewModel.handleIntent(intent: intent)
    }
    
    init() {
        viewModel.observeResource { state in
            
            self.loading = true
            self.artwork = nil
            self.error = nil
            
            switch state {
                case let success as ArtworksStateData<Artwork>:
                    self.artwork = success.data!
                    self.loading = false
                    self.error = nil
                
                case let error as ArtworksStateError<ErrorResponse>?:
                    self.artwork = nil
                    self.loading = false
                    self.error = error?.data?.detail
                
                default:
                    break
            }
        }
    }
}
