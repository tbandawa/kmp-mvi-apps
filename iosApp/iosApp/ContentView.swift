import SwiftUI
import data

struct ContentView: View {
    
    var body: some View {
        NavigationView {
            NavigationLink(destination: ArtworkScreen(artworkId: 14598)){
                Text("Open Artwork 14598")
            }
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
