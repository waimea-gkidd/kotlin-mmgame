import com.formdev.flatlaf.themes.FlatMacDarkLaf
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

    private val scummbutton = JButton("To Scumm Bar")
    private val generalbutton = JButton("To General Store")
    private val jailbutton = JButton("To Jail")
    private val aleybutton = JButton("To Alley")
    private val storagebutton = JButton("To Storage Yard")
    private val botOfClockbutton = JButton("To Clock Tower")
    private val topOfClockbutton = JButton("To Top of Clock Tower")
    private val mayorbutton = JButton("To Mayor's Mansion")


    init {
        setupLayout()
        setupStyles()
        setupActions()
        setupWindow()
        updateUI()
    }

    private fun setupLayout() {
        panel.preferredSize = java.awt.Dimension(1400, 800)

        titleLabel.setBounds(30, 30, 340, 30)
        infoLabel.setBounds(30, 90, 340, 30)
        scummbutton.setBounds(30, 700, 170, 30)


        panel.add(titleLabel)
        panel.add(infoLabel)
        panel.add(scummbutton)

    }

    private fun setupStyles() {
        titleLabel.font = Font(Font.SANS_SERIF, Font.BOLD, 18)
        infoLabel.font = Font(Font.SANS_SERIF, Font.PLAIN, 14)
        scummbutton.font = Font(Font.SANS_SERIF, Font.PLAIN, 10)

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
        infoLabel.text = "You are at ${app.currentLocation.name}, ${app.currentLocation.description}" // was going

        scummbutton.addActionListener {
            goLocation()
        }
    }

    private fun goLocation() {

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
