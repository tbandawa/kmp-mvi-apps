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
        
    }
}
