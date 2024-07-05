//
//  ArtworkDetail.swift
//  iosApp
//
//  Created by Tendai Bandawa on 2024/07/05.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ArtworkDetail: View {
    
    var title: String
    var value: String
    
    var body: some View {
            VStack {
                Divider()
                    .background(.gray)
                    .frame(height: 0.5)
                HStack(alignment: VerticalAlignment.top) {
                    Text(title)
                        .font(.system(size: 16, weight: .bold, design: .rounded))
                        .foregroundColor(.black)
                        .frame(width: 115, alignment: .topLeading)
                        .padding(.vertical, 2)
                    Text(value)
                        .font(.system(size: 16, weight: .regular, design: .rounded))
                        .foregroundColor(.gray)
                        .frame(maxWidth: .infinity, alignment: .topLeading)
                        .padding(.vertical, 2)
                }
            }
        }
}
