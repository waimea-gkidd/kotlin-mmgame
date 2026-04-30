import com.formdev.flatlaf.themes.FlatMacDarkLaf

import java.awt.Font

import javax.swing.*


/**
 * Application entry point
 */

fun main() {
    FlatMacDarkLaf.setup() // Initialise the LAF
    val app = App() // Get an app state object
    val window = MainWindow(app) // Spawn the UI, passing in the app state
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
    val description: String,

    var isQuest: Boolean = false,

    val notes: List<String> = listOf(),
    var currentNote: Int = 0,

    val rewardItem: String? = null,
    val requiredItem: String? = null

) {
    val connectLocation = mutableListOf<Location>()
}


class App {
    val locations = mutableListOf<Location>()
    var currentLocation: Location
    var itemInHand: String? = null
    var contact = false

    val townCentre: Location
    val scummBar: Location
    val generalStore: Location
    val jail: Location
    val alley: Location
    val storageYard: Location
    val clockTowerBase: Location
    val topOfClockTower: Location
    val mayorsMansion: Location

    init {

        townCentre = Location(
            name = "Town Centre",
            description = "The centre of town.",
            isQuest = true,
            notes = listOf(
                "<html>You wake up with no memory of last night.\n A note on the floor reads: Dear entrusted one\n -G.T.\n</html>",
                "<html>Guybrush: It is I, the mighty pirate Guybrush Threepwood!\n Calm yourself and listen closely to the quest I have for you.\n</html>",
                "<html>Guybrush: I'm stuck in the Mansion. Fix the clock tower and bring me a snack so Elaine lets me out!\n Oh! and bring Stan if you too please.</html>"
            )
        )

        scummBar = Location("Scumm Bar", "A noisy pirate bar.")

        generalStore = Location("General Store", "A shop with odd items.")

        jail = Location("Jail", "A small stone jail.")

        alley = Location("Alley", "A narrow alley with stray dogs.")

        storageYard = Location("Storage Yard", "A yard full of crates.")

        clockTowerBase = Location("Clock Tower Base", "Base of the old tower.")

        topOfClockTower = Location("Top of Clock Tower", "The top of the tower.")

        mayorsMansion = Location("Mayor's Mansion", "A locked mansion.")

        locations.add(townCentre)
        locations.add(scummBar)
        locations.add(generalStore)
        locations.add(jail)
        locations.add(alley)
        locations.add(storageYard)
        locations.add(clockTowerBase)
        locations.add(topOfClockTower)
        locations.add(mayorsMansion)


// Ignore the indentation below, they are simply for a better structure.

// Also note that the locations themselves were derrived from chatGPT (I ain't writing all'at) the code was myself from preexisting samples.

        townCentre.connectLocation.add(scummBar)
        townCentre.connectLocation.add(generalStore)
        townCentre.connectLocation.add(jail)
        townCentre.connectLocation.add(alley)
        townCentre.connectLocation.add(clockTowerBase)
        townCentre.connectLocation.add(mayorsMansion)

        scummBar.connectLocation.add(townCentre)

        generalStore.connectLocation.add(townCentre)

        jail.connectLocation.add(townCentre)

        alley.connectLocation.add(storageYard)
        alley.connectLocation.add(townCentre)

        storageYard.connectLocation.add(alley)

        clockTowerBase.connectLocation.add(townCentre)
        clockTowerBase.connectLocation.add(topOfClockTower)

        topOfClockTower.connectLocation.add(clockTowerBase)

        mayorsMansion.connectLocation.add(townCentre)

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
    private val notifLabel = JLabel()
    private val dialogLabel = JLabel()

    private var action1Button = JButton("Doing zilch")
    private var action2Button = JButton("Doing zilch")

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
        notifLabel.setBounds(380, 450, 650, 200)
        dialogLabel.setBounds(380, 130, 1300, 50)
        action1Button.setBounds(600, 650, 170, 30)
        action2Button.setBounds(775, 650, 170, 30)

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
        panel.add(notifLabel)
        panel.add(dialogLabel)
        panel.add(action1Button)
        panel.add(action2Button)

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
        infoLabel.font = Font(Font.SANS_SERIF, Font.BOLD, 14)
        notifLabel.font = Font(Font.SANS_SERIF, Font.BOLD, 20)
        dialogLabel.font = Font(Font.SANS_SERIF, Font.PLAIN, 16)
        action1Button.font = Font(Font.SANS_SERIF, Font.BOLD, 12)
        action2Button.font = Font(Font.SANS_SERIF, Font.BOLD, 12)
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

        frame.isResizable = false // Can't resize

        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE // Exit upon window close

        frame.contentPane = panel // Define the main content

        frame.pack()

        frame.setLocationRelativeTo(null) // Centre on the screen

    }


    private fun setupActions() {

        centrebutton.addActionListener { goLocation(app.townCentre) }
        scummbutton.addActionListener { goLocation(app.scummBar) }

        // TODO

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



        action1Button.addActionListener {

            processPlayerAction(1)

            updateUI()

        }



        action2Button.addActionListener {

            processPlayerAction(2)

            updateUI()

        }


    }


    fun updateUI() {

        val location = app.currentLocation
        val name = location.name

        infoLabel.text = "You are at ${location.name}, ${location.description}" // was going

        // TODO

        centrebutton.isEnabled = location.connectLocation.contains(app.townCentre)
        scummbutton.isEnabled = location.connectLocation.contains(app.scummBar)
        generalbutton.isEnabled = false
        jailbutton.isEnabled = false
        aleybutton.isEnabled = false
        storagebutton.isEnabled = false
        botOfClockbutton.isEnabled = false
        topOfClockbutton.isEnabled = false
        mayorbutton.isEnabled =
            false // only want to display the current location links to the options of buttons. Therefore, they are NOT visible by default.
    }

    private fun processPlayerAction(action: Int) {

        val location = app.currentLocation

        // first encounter // where you get info on what to do.
        if (app.contact == false) {
            notifLabel.text = location.notes[0] //calls the first note
            action1Button.text = "Read note"
            action2Button.text = "Leave the note"
            return
        }
        if (location == app.scummBar && action == 1) {
            notifLabel.isVisible = false //hides the initial message
            dialogLabel.text = location.notes[1]
            action1Button.text = "Continue reading"
            return
        }
        if (location == app.scummBar && action == 2) {
            dialogLabel.text = location.notes[2]
            app.contact = true
            return
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
