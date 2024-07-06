//
//  ItemArtwork.swift
//  iosApp
//
//  Created by Tendai Bandawa on 2024/07/06.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ItemArtwork: View {
    
    var item: Item
    
    var body: some View {
        VStack {
            HStack(alignment: VerticalAlignment.top) {
                AsyncImage(url: URL(string: "https://www.artic.edu/iiif/2/\(item.imageId)/full/200,/0/default.jpg")) { image in
                    image
                        .resizable()
                        .aspectRatio(contentMode: .fill)
                        .frame(width: 60, height: 80, alignment: .topLeading)
                        .clipShape(RoundedRectangle(cornerRadius: 4))
                } placeholder: {
                    Image("img_placeholder")
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 60, height: 80, alignment: .topLeading)
                        .clipShape(RoundedRectangle(cornerRadius: 4))
                }
                VStack(alignment: .leading) {
                    Text(item.title)
                        .font(.system(size: 14, design: .rounded))
                        .foregroundColor(.black)
                        .fontWeight(.bold)
                        .padding(.bottom, 1)
                    Text(item.artistDisplay)
                        .font(.system(size: 12, design: .rounded))
                        .foregroundColor(.black)
                        .foregroundColor(Color.white)
                        .padding(.bottom, 1)
                    Text(item.departmentTitle)
                        .font(.system(size: 10, design: .rounded))
                        .foregroundColor(.black)
                        .foregroundColor(Color.white)
                }
            }
        }
    }
}
