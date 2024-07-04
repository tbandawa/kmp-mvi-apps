//
//  ArtworkState.swift
//  iosApp
//
//  Created by Tendai Bandawa on 2024/07/04.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import UIKit
import data

class ArtworkState: ObservableObject {
    
    let viewModel: ArtworksViewModel = DataHelper().viewModel
    
}
