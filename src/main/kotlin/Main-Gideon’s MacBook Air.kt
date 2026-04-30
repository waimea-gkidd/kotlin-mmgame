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

) {
    val connectLocation = mutableListOf<Location>()
}

class App {
    val locations = mutableListOf<Location>()
    var currentLocation: Location

    val townCentre = Location("Town Centre", "The centre of town.")
    val scummBar = Location("Scumm Bar", "A noisy pirate bar.")
    val generalStore = Location("General Store", "A shop with odd items.")
    val jail = Location("Jail", "A small stone jail.")
    val alley = Location("Alley", "A narrow alley with stray dogs.")
    val storageYard = Location("Storage Yard", "A yard full of crates.")
    val clockTowerBase = Location("Clock Tower Base", "Base of the old tower.")
    val topOfClockTower = Location("Top of Clock Tower", "The top of the tower.")
    val mayorsMansion = Location("Mayor's Mansion", "A locked mansion.")
    // having the val's/var's outside of init makes them accesable in other functions.

    var item = "Empty"
    var fixClock = false
    var talkGuybrush = false
    var talkGuard = false
    var storageOpen = false
    var signal = false
    var grog = false
    var key = false

    init {


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
        stuffToDo(app)
        updateUI()
    }

    private fun stuffToDo(app: App) {

        val name = app.currentLocation.name

        if (name == "Scumm Bar" && app.item == "Empty") {
            println("You ask around for some small job to earn money")
            println("The chef offers you $5 to clear tables")
            app.item = "5 Coins"
            notifLabel.text = "You cleaned tables and got 5 coins!"
        }
        if (name == "General Store" && app.item == "5 Coins") {
            app.item = "Rusty Cog"
            notifLabel.text = "You got Rusty Cog!"
        }
        if (name == "Top of Clock Tower" && app.item == "Rusty Cog") {
            app.item = "Nothing"
            app.fixClock = true
            notifLabel.text = "You fixed the broken clock tower"
        }
        if (name == "Jail" && app.item == "Nothing") {
            app.item = "Nothing"
            println("Walking past the jail you hear someone call out.")
            println("It's Stan (of course it is).")
            println("You asked the jailer if he would let Stan out")
            println("You can have the key if you bring me grog, says the guard.")
            app.talkGuard = true
            notifLabel.text = "Bring the guard some grog"
        }
        if (name == "Scumm Bar" && app.talkGuard == true) {
            println("You approach the chef and ask how much grog is")
            println("Grog is $5. But for you, me lad, I'll give you this special one for free")
            app.item = "Grog"
            app.grog = true
            notifLabel.text = "You gained ominous grog"
        }
        if (name == "Jail" && app.item == "Grog") {
            println("You make the trade with the guard")
            println("The grog is gone in seconds, and... ")
            print("the guard fell asleep??")
            println("Whether this was the ominous power of the grog, we'll never know.")
            app.item = "Key"
            app.key = true
            notifLabel.text = "You gained a key"
        }
        if (name == "Jail" && app.item == "Key") {
            println("The key doesnt work. Instead, Stan walks out on his own.")
            println("Hmm, Stan mutters.")
            println("Guess he never locked it.")
            println("That key there must've been for something else.")
            app.item = "Key"
            notifLabel.text = "Maybe something is locked"
        }
        if (name == "Storage Yard" && app.item == "Key") {
            println("The gate to the storage yard is locked")
            println("You try it with your key. And...")
            println("The storage yard is unlocked")
            println("A banana is conveniently placed on a stool")
            app.item = "Banana"
            app.storageOpen = true
            notifLabel.text = "You open the storage yard... and gained a banana!"
        }
    }

    private fun setupLayout() {
        panel.preferredSize = java.awt.Dimension(1400, 800)

        titleLabel.setBounds(30, 30, 340, 30)
        infoLabel.setBounds(30, 90, 340, 30)
        notifLabel.setBounds(30, 90, 1300, 700)
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
        notifLabel.font = Font(Font.SANS_SERIF, Font.PLAIN, 30)
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
        centrebutton.addActionListener { // locations[x] from kotlin for beginners array
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


    fun updateUI() {
        infoLabel.text = "You are at ${app.currentLocation.name}, ${app.currentLocation.description}" // was going

        centrebutton.isVisible = false
        scummbutton.isVisible = false
        generalbutton.isVisible = false
        jailbutton.isVisible = false
        aleybutton.isVisible = false
        storagebutton.isVisible = false
        botOfClockbutton.isVisible = false
        topOfClockbutton.isVisible = false
        mayorbutton.isVisible =
            false  // only want to display the current location links to the options of buttons. Therefore, they are NOT visible by default.
        // Next step is to set the show fun into updateUI

        val location = app.currentLocation

        if (
            location.connectLocation.contains(app.locations[0]) // read the descriptions of the functions (I think the .x's are called) to find that contains was the right thing.
        ) {
            centrebutton.isVisible = true  // go back to town centre. Note: doesnt work at town
        }

        if (
            location.connectLocation.contains(app.locations[1])
        ) {
            scummbutton.isVisible = true
        }

        if (
            location.connectLocation.contains(app.locations[2])
        ) {
            generalbutton.isVisible = true
        }

        if (
            location.connectLocation.contains(app.locations[3])
        ) {
            jailbutton.isVisible = true
        }

        if (
            location.connectLocation.contains(app.locations[4])
        ) {
            aleybutton.isVisible = true
        }

        if (
            location.connectLocation.contains(app.locations[5])
        ) {
            storagebutton.isVisible = true
        }

        if (
            location.connectLocation.contains(app.locations[6])
        ) {
            botOfClockbutton.isVisible = true
        }

        if (
            location.connectLocation.contains(app.locations[7])
        ) {
            topOfClockbutton.isVisible = true
        }

        if (location.connectLocation.contains(app.locations[8])
        ) {
            mayorbutton.isVisible = true
        }

    }

    private fun goLocation(destination: Location) {
        app.currentLocation = destination
        stuffToDo(app)
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
