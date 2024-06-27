import SwiftUI
import data

@main
struct iOSApp: App {
    
    init() {
        DataModuleKt.doInitKoin()
    }
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
