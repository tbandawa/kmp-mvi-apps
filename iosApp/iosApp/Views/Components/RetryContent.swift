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
            Button("Retry") {
                retry()
            }
            .frame(width: 80, height: 35)
            .foregroundColor(Color.white)
            .background(Color.black)
            .cornerRadius(15)
        }
    }
}

struct RetryContent_Previews: PreviewProvider {
    static var previews: some View {
        RetryContent(error: "Error message") { }
    }
}
