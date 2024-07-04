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
    
    let viewModel: ArtworksViewModel = DataHelper().viewModel
    
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
            
            switch state {
                case let success as ArtworksStateSuccess<ArtworkResponse>:
                    self.artwork = success.data?.data
                    self.loading = false
                    self.error = nil
                    print("success.....")
                
                case let error as ArtworksStateError<ErrorResponse>?:
                    self.artwork = nil
                    self.loading = false
                self.error = error?.data?.detail
                    print("error....")
                
                default:
                    break
            }
        }
    }
}