import SwiftUI
import data

struct ContentView: View {
    
    let repo: AicRepository = DataHelper().repo
    
	var body: some View {
        
        
        
		Text("greet")
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
