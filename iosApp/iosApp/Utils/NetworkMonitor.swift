//
//  NetworkMonitor.swift
//  AIC-KMP
//
//  Created by Tendai Bandawa on 2024/11/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Network

class NetworkMonitor: ObservableObject {
    
    @Published var isConnected = false
    
    private let networkMonitor = NWPathMonitor()
    private let workerQueue = DispatchQueue(label: "Monitor")

    init() {
        networkMonitor.pathUpdateHandler = { path in
            self.isConnected = path.status == .satisfied
        }
        networkMonitor.start(queue: workerQueue)
    }
}
