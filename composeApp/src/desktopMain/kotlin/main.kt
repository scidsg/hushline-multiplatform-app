import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.koin.core.Koin
import org.scidsg.hushline.App
import org.scidsg.hushline.di.commonModule
import org.scidsg.hushline.di.initKoin
import org.scidsg.hushline.di.platformModule

lateinit var koin: Koin

fun main() = application {
    koin = initKoin()
    koin.loadModules(
        listOf(platformModule(), commonModule()),
    )

    Window(
        onCloseRequest = ::exitApplication,
        title = "HushLine",
    ) {
        App()
    }
}