//
//  RetryContent.swift
//  iosApp
//
//  Created by Tendai Bandawa on 2024/07/04.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct RetryContent: View {
    
    var error: String
    var retry: () -> Void
    
    var body: some View {
        VStack {
            Text(error)
                .font(.system(size: 14, weight: .regular, design: .rounded))
                .padding(.horizontal, 10)
            Text("Retry")
                .font(.system(size: 14, weight: .regular, design: .rounded))
                .padding(.top, 2)
                .underline()
                .onTapGesture {
                    retry()
                }
        }
    }
}

struct RetryContent_Previews: PreviewProvider {
    static var previews: some View {
        RetryContent(error: "Error message") { }
    }
}
