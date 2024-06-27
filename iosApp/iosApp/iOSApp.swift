import SwiftUI

@main
struct iOSApp: App {

    init() {
         KoinHelperKt.doInitKoinIOS()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}