//
//  ArtworksState.swift
//  iosApp
//
//  Created by Tendai Bandawa on 2024/07/03.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import UIKit
import data

class ArtworksState: ObservableObject {
    
    let viewModel: ArtworksViewModel = DataHelper().viewModel
    
    @Published var loading = true
    @Published var error: String?
    @Published var items: [Item]? = []
    
    init() {
        viewModel.handleIntent(intent: ArtworksIntent.GetArtworks(page: 1))
        viewModel.observeResource { state in
            
            switch state {
                case let success as ArtworksStateSuccess<ArtworksResponse>?:
                    //print("=============================================== start")
                    print("")
                    //print("=============================================== end")
                
                case let error as ArtworksStateError<ErrorResponse>?:
                    print("error => \(error)")
                
                default:
                    break
            }
        }
    }
}
