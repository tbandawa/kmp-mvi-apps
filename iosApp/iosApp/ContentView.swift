import SwiftUI

struct ContentView: View {
    
    @State private var isNetworkBanner = false
    
    @EnvironmentObject var networkMonitor: NetworkMonitor
    
    var body: some View {
        ZStack(alignment: .bottom) {
            NavigationView {
                ArtworksScreen()
            }
            if isNetworkBanner {
                Text(networkMonitor.isConnected ? "Internet Connection Available" : "No Internet Connection")
                    .frame(maxWidth: .infinity, alignment: .center)
                    .padding(.top, 10)
                    .padding(.bottom, 10)
                    .font(.system(size: 12, design: .rounded))
                    .fontWeight(.medium)
                    .background(networkMonitor.isConnected ? Color.green : Color.gray)
                    .transition(.move(edge: .bottom).combined(with: .opacity))
                    .zIndex(2)
            }
                
        }
        .animation(.default, value: isNetworkBanner)
        .onChange(of: networkMonitor.isConnected) { value in
            if value {
                Task {
                    await delayHidebanner()
                }
            } else {
                isNetworkBanner = true
            }
        }
	}
    
    private func delayHidebanner() async {
        try? await Task.sleep(nanoseconds: 2_000_000_000)
        isNetworkBanner = false
    }
}
