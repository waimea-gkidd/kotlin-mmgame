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
        val townCentre = Location("Town Centre",  "The centre of town.")
        val scummBar = Location("Scumm Bar", "A noisy pirate bar.")
        val generalStore = Location("General Store", "A shop with odd items.")
        val jail = Location("Jail", "A small stone jail.")
        val alley = Location("Alley", "A narrow alley with stray dogs.")
        val storageYard = Location("Storage Yard", "A yard full of crates.")
        val clockTowerBase = Location("Clock Tower Base", "Base of the old tower.")
        val topOfClockTower = Location("Top of Clock Tower", "The top of the tower.")
        val mayorsMansion = Location("Mayor's Mansion", "A locked mansion.")

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

    private val centrebutton = JButton("To Town Centre")
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
        centrebutton.setBounds(30, 650, 170, 30)
        scummbutton.setBounds(30, 690, 170, 30)
        generalbutton.setBounds(30, 730, 170, 30)
        jailbutton.setBounds(210, 650, 170, 30)
        aleybutton.setBounds(210, 690, 170, 30)
        storagebutton.setBounds(210, 730, 170, 30)
        botOfClockbutton.setBounds(390, 650, 170, 30)
        topOfClockbutton.setBounds(390, 690, 170, 30)
        mayorbutton.setBounds(390, 730, 170, 30)


        panel.add(titleLabel)
        panel.add(infoLabel)
        panel.add(centrebutton)
        panel.add(scummbutton)
        panel.add(generalbutton)
        panel.add(jailbutton)
        panel.add(aleybutton)
        panel.add(storagebutton)
        panel.add(botOfClockbutton)
        panel.add(topOfClockbutton)
        panel.add(mayorbutton)

    }

    private fun setupStyles() {
        titleLabel.font = Font(Font.SANS_SERIF, Font.BOLD, 18)
        infoLabel.font = Font(Font.SANS_SERIF, Font.PLAIN, 14)
        centrebutton.font = Font(Font.SANS_SERIF, Font.PLAIN, 10)
        scummbutton.font = Font(Font.SANS_SERIF, Font.PLAIN, 10)
        generalbutton.font = Font(Font.SANS_SERIF, Font.PLAIN, 10)
        jailbutton.font = Font(Font.SANS_SERIF, Font.PLAIN, 10)
        aleybutton.font = Font(Font.SANS_SERIF, Font.PLAIN, 10)
        storagebutton.font = Font(Font.SANS_SERIF, Font.PLAIN, 10)
        botOfClockbutton.font = Font(Font.SANS_SERIF, Font.PLAIN, 10)
        topOfClockbutton.font = Font(Font.SANS_SERIF, Font.PLAIN, 10)
        mayorbutton.font = Font(Font.SANS_SERIF, Font.PLAIN, 10)
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

        centrebutton.addActionListener {
            goLocation(app.locations[0])
        }
        scummbutton.addActionListener {
            goLocation(app.locations[1])
        }
        generalbutton.addActionListener {
            goLocation(app.locations[2])
        }
        jailbutton.addActionListener {
            goLocation(app.locations[3])
        }
        aleybutton.addActionListener {
            goLocation(app.locations[4])
        }
        storagebutton.addActionListener {
            goLocation(app.locations[5])
        }
        botOfClockbutton.addActionListener {
            goLocation(app.locations[6])
        }
        topOfClockbutton.addActionListener {
            goLocation(app.locations[7])
        }
        mayorbutton.addActionListener {
            goLocation(app.locations[8])
        }
    }

    private fun goLocation(destination: Location) {
        app.currentLocation = destination
        updateUI()
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
