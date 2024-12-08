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
    
    let viewModel: ArtworksViewModel = DataHelper().artworksViewModel
    
    @Published var loading = true
    @Published var error: String?
    @Published var items: [Item]? = []
    
    private var currentPage: Int32 = 1
    
    init() {
        viewModel.handleIntent(intent: ArtworksIntent.GetArtworks(page: currentPage))
        viewModel.observeResource { state in
            
            self.loading = true
            self.error = nil
            
            switch state {
                case let success as ArtworksStateData<NSArray>:
                    self.currentPage += 1
                    self.items?.append(contentsOf: self.mapToItems(items: success.data! as! [Artwork]))
                    self.loading = false
                    self.error = nil
                
                case let error as ArtworksStateError<ErrorResponse>?:
                    self.error = error?.data?.detail
                    self.loading = false
                
                default:
                    break
            }
        }
    }
    
    func getArtworks() {
        viewModel.handleIntent(intent: ArtworksIntent.GetArtworks(page: currentPage))
    }
    
    func refreshArtworks() {
        currentPage = 1
        items?.removeAll()
        getArtworks()
    }
}
