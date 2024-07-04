import SwiftUI
import data

struct ContentView: View {
    
    @EnvironmentObject var artworksState: ArtworksState
    
	var body: some View {
        ScrollView {
            LazyVStack {
                Button("Retry") {
                    artworksState.viewModel.handleIntent(intent: ArtworksIntent.GetArtwork(id: 229378))
                }
            }
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
            .environmentObject(ArtworksState())
	}
}
