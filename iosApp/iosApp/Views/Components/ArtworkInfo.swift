//
//  ArtworkInfo.swift
//  iosApp
//
//  Created by Tendai Bandawa on 2024/07/05.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ArtworkInfo: View {
    
    var title: String
    var value: String
    
    @State private var hide = true
    
    var body: some View {
            VStack {
                Divider()
                    .background(.gray)
                    .frame(height: 0.5)
                HStack {
                    Text(title)
                        .font(.system(size: 16, weight: .bold, design: .rounded))
                        .foregroundColor(.black)
                        .frame(maxWidth: .infinity, alignment: .topLeading)
                        .padding(.vertical, 2)
                    Spacer()
                    Text(toggleCollapse(isHide: hide))
                        .font(.system(size: 20, weight: .bold, design: .rounded))
                        .foregroundColor(.black)
                        .frame(maxWidth: .infinity, alignment: .bottomTrailing)
                        .padding(.vertical, 2)
                }
                if !hide {
                    Text(value)
                        .font(.system(size: 16, weight: .regular, design: .rounded))
                        .foregroundColor(.gray)
                        .frame(maxWidth: .infinity, alignment: .topLeading)
                        .padding(.top, 2)
                }
            }
            .onTapGesture {
                withAnimation{
                    self.hide.toggle()
                }
            }
        }
    
    func toggleCollapse(isHide: Bool) -> String {
        if isHide {
            return "+"
        } else  {
            return "-"
        }
    }
}
