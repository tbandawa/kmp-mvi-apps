import SwiftUI
import data

@main
struct iOSApp: App {
    
    @StateObject var artworksState = ArtworksState()
    @StateObject var artworkState = ArtworksState()
    
    init() {
        DataModuleKt.doInitKoin()
    }
    
	var body: some Scene {
		WindowGroup {
			ArtworksScreen()
                .environmentObject(artworksState)
                .environmentObject(artworkState)
		}
	}
}
