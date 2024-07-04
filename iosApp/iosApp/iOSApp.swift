import SwiftUI
import data

@main
struct iOSApp: App {
    
    @StateObject var artworksState = ArtworksState()
    @StateObject var artworkState = ArtworkState()
    
    init() {
        DataModuleKt.doInitKoin()
    }
    
	var body: some Scene {
		WindowGroup {
            ContentView()
                .environmentObject(artworksState)
                .environmentObject(artworkState)
		}
	}
}
