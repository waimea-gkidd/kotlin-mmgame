import com.formdev.flatlaf.themes.FlatMacDarkLaf
import java.awt.Color
import java.awt.Font
import javax.swing.*

/**
 * Application entry point
 */
fun main() {
    FlatMacDarkLaf.setup()          // Initialise the LAF

    val app = App()                 // Get an app state object
    val window = MainWindow(app)    // Spawn the UI, passing in the app state

    SwingUtilities.invokeLater { window.show() }
}


/**
 * Manage app state
 *
 * @property name the user's name
 * @property score the points earned
 */
class Location(
    val name: String,
    val description: String
)
class App {
    val locations = mutableListOf<Location>()
    var currentLocation: Location

    init {
        val townCentre = Location("Town Centre", "The centre of town.")
        val scummBar = Location("Scumm Bar", "A noisy pirate bar.")
        val generalStore = Location("General Store", "A shop with odd items.")
        val jail = Location("Jail", "A small stone jail.")
        val alley = Location("Alley", "A narrow alley with stray dogs.")
        val storageYard = Location("Storage Yard", "A yard full of crates.")
        val clockTowerBase = Location("Clock Tower Base", "The bottom of the old tower.")
        val topOfClockTower = Location("Top of Clock Tower", "The top of the tower.")
        val mayorsMansion = Location("Mayor's Mansion", "A large locked mansion.")

        locations.add(townCentre)
        locations.add(scummBar)
        locations.add(generalStore)
        locations.add(jail)
        locations.add(alley)
        locations.add(storageYard)
        locations.add(clockTowerBase)
        locations.add(topOfClockTower)
        locations.add(mayorsMansion)

        currentLocation = townCentre
    }
}




/**
 * Main UI window, handles user clicks, etc.
 *
 * @param app the app state object
 */
class MainWindow(val app: App) {
    val frame = JFrame("Meelé Island")
    private val panel = JPanel().apply { layout = null }

    private val titleLabel = JLabel("Meelé island explorer")

    private val infoLabel = JLabel()

    init {
        setupLayout()
        setupStyles()
        setupActions()
        setupWindow()
        updateUI()
    }

    private fun setupLayout() {
        panel.preferredSize = java.awt.Dimension(400, 220)

        titleLabel.setBounds(30, 30, 340, 30)
        infoLabel.setBounds(30, 90, 340, 30)

        panel.add(titleLabel)
        panel.add(infoLabel)

    }

    private fun setupStyles() {
        titleLabel.font = Font(Font.SANS_SERIF, Font.BOLD, 32)
        infoLabel.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)

    }

    private fun setupWindow() {
        frame.isResizable = false                           // Can't resize
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE  // Exit upon window close
        frame.contentPane = panel                           // Define the main content
        frame.pack()
        frame.setLocationRelativeTo(null)                   // Centre on the screen
    }

    private fun setupActions() {

    }


    fun updateUI() {

    }

    fun show() {
        frame.isVisible = true
    }
}


/**
 * Info UI window is a child dialog and shows how the
 * app state can be shown / updated from multiple places
 *
 * @param owner the parent frame, used to position and layer the dialog correctly
 * @param app the app state object
 */
