import SwiftUI
import data

@main
struct iOSApp: App {
    
    @StateObject var artworksState = ArtworksState()
    @StateObject var artworkState = ArtworkState()
    @StateObject var networkMonitor = NetworkMonitor()
    
    init() {
        DataModuleKt.doInitKoin()
    }
    
	var body: some Scene {
		WindowGroup {
            ContentView()
                .environmentObject(artworksState)
                .environmentObject(artworkState)
                .environmentObject(networkMonitor)
		}
	}
}
