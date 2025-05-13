import UIKit
import SwiftUI
import ComposeApp

struct MyIosButton: View {
    @EnvironmentObject var composeNav: ComposeNavigation
    var depth:Int32
    var body: some View {
        VStack {
            Text("Hello from swifth at depth \(depth)")
            Button(
                action: {
                    composeNav.platformNavigator.goBack()
                }
            ) {
                Text("go back from ios")
            }
            Button(
                action: {
                    composeNav.platformNavigator.navigateTo(destination: DestinationPlatformWithButton(
                        depth: depth + 1
                    ))
                }
            ) {
                Text("one more ios view")
            }
            Button(
                action: {
                    composeNav.platformNavigator.navigateTo(destination: DestinationLogo(depth: depth + 1))
                }
            ) {
                Text("compose view")
            }

        }

    }
}

struct UndefinedIosView: View {
    var destination: String
    var body: some View {
        Text("Undefined \(destination)")
    }
}

struct ComposeView: UIViewControllerRepresentable {
    @EnvironmentObject var composeNav: ComposeNavigation
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(platformNavigator: composeNav.platformNavigator) { destination in
            print("dest: \(destination)")
            switch destination {
            case let dest as ComposeApp.DestinationPlatformWithButton:
                return UIHostingController(rootView: MyIosButton(
                    depth: dest.depth
                ))
            default:
                return UIHostingController(rootView: UndefinedIosView(
                    destination : "\(destination)"
                ))
            }
        }
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView().installComposeNavigation()
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}


class ComposeNavigation: ObservableObject {
    let platformNavigator = PlatformNavigator()
}

extension View {
    func installComposeNavigation() -> some View {
        self.environmentObject(ComposeNavigation())
    }
}


