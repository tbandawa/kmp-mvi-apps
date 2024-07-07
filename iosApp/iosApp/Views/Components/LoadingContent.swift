//
//  LoadingContent.swift
//  iosApp
//
//  Created by Tendai Bandawa on 2024/07/04.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct LoadingContent: View {
    
    @Environment(\.colorScheme) var colorScheme
    
    var body: some View {
        VStack {
            ProgressView()
        }
    }
}
