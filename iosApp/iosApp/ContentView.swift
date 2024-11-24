import SwiftUI

struct ContentView: View {
    
    @State var isNetworkBanner = false
    
    @EnvironmentObject var networkMonitor: NetworkMonitor
    
    var body: some View {
        VStack {
            NavigationView {
                ArtworksScreen()
            }
            if isNetworkBanner {
                VStack(alignment: .center) {
                    Text(networkMonitor.isConnected ? "Internet Connection Available" : "No Internet Connection")
                        .frame(maxWidth: .infinity, idealHeight: 10, alignment: .center)
                        .padding(.top, 10)
                        .font(.system(size: 12, design: .rounded))
                }
                .background(networkMonitor.isConnected ? Color.green : Color.gray)
            }
        }
        .onChange(of: networkMonitor.isConnected) { value in
            if value {
                Task {
                    await delayHidebanner()
                }
            } else {
                withAnimation {
                    isNetworkBanner = true
                }
            }
        }
	}
    
    private func delayHidebanner() async {
        // Delay of 7.5 seconds (1 second = 1_000_000_000 nanoseconds)
        try? await Task.sleep(nanoseconds: 3_000_000_000)
        withAnimation {
            isNetworkBanner = false
        }
    }
}
