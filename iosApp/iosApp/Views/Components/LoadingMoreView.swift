//
//  LoadingMoreView.swift
//  iosApp
//
//  Created by Tendai Bandawa on 2024/07/07.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct LoadingMoreView: View {
    
    let errorMessage: String?
    let retry: () -> Void
    
    var body: some View {
        
        if errorMessage != nil {
            VStack {
                Text(errorMessage ?? "Error. Please try again")
                    .font(.system(size: 12, weight: .regular, design: .rounded))
                    .padding(.horizontal, 10)
                Text("Retry")
                    .font(.system(size: 12, weight: .regular, design: .rounded))
                    .padding(.top, 2)
                    .underline()
                    .onTapGesture {
                        retry()
                    }
            }
        } else {
            Text("Loading...")
                .frame(maxWidth: .infinity)
                .font(.system(size: 12, weight: .medium, design: .rounded))
                .foregroundColor(.gray)
        }
    }
}

struct LoadingMoreView_Previews: PreviewProvider {
    static var previews: some View {
        LoadingMoreView(
            errorMessage: nil,
            retry: {}
        )
    }
}
