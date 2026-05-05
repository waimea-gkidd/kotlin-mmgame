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
    val noQuestNotes: String,
    val questNotes: List<String> = listOf(),
    val actionText: List<String> = listOf(),
    val rewardItem: String? = null,
    val requiredItem: String? = null
) {
    var currentQuestNote: Int = 0
    val connectLocation = mutableListOf<Location>()
    var questCompleted = false

    fun reset() {
        currentQuestNote = 0
    }

    fun questCompleted(): Boolean {
        // Last note?
        return currentQuestNote == questNotes.size - 1
    }

    fun nextNote() {
        if (currentQuestNote < questNotes.size - 1) {
            currentQuestNote++
        }
    }
}


class App {
    val locations = mutableListOf<Location>()
    var currentLocation: Location

    //    var currentQuest: Location
    var itemInHand: String? = null
    var contact = false

    val townCentre: Location
    val scummBar: Location
    val generalStore: Location
    val jail: Location
    val alley: Location
    val storageYard: Location
    val botOfClock: Location
    val topOfClock: Location
    val mayorsMansion: Location

    init {

        val conversation = "You have spoken to Guybrush"
        val coins = "5 coins"
        val cog = "Rusty cog"

        // Initial meeting/note from Guybrush
        townCentre = Location(
            "Town Centre",
            "The centre of town.",
            "The Town centre is empty",
            listOf(
                "<html>You wake up with no memory of last night.<html>",
                "<html>A note on the floor reads: Dear entrusted one -G.T.<html>",
                "<html>It is I, the mighty pirate Guybrush Threepwood!\n I know this is a great honour, but please... Calm yourself and listen closely to the quest I have for you.<html>",
                "<html>Guybrush: Elaine has me stuck inside of the mansion... She doesn't seem too pleased with me.<html>",
                "<html>But I'm sure if you take care of the chores around town...\n I'm sure that She'll be slightly happier.<html>",
                "<html>Though, now that I think about it... I'm not too sure what she does...<html>",
                "<html>Maybe start by visiting the bar? Surely they have something that needs doing...<html>"
            ),
            listOf("Continue","Read Note", "Next", "Next","Next", "Next", "Done"),
            conversation,
            null
        )

        scummBar = Location(
            "Scumm Bar",
            "A noisy pirate bar.",
            "Hello, sailor! Come back later",
            listOf(
                "<html>You notice the a cook perched atop a table quivering slightly<html>",
                "<html>Cook: Arg! There's a giant, red-eyed rat scurrying about!<html>",
                "<html>Cook: You there! The Cook shouts.\n If you manage rid of it, I'll give ye 5 coins for the trouble!<html>",
                "<html>You kicked the rat.<html>",
                "<html>Cook: Thank you me lad, ere's your money.<html>",
                "<html>You gained 5 coins. But... What should you do with it?<html>"
            ),
            listOf("Continue", "Next", "Kick rat", "Continue", "Next", "Done"),
            coins,
            conversation
        )

        generalStore = Location(
            "General Store",
            "A shop with odd items.",
            "G'day, I have nothing for sale right now!",
            listOf(
                "You got Rusty Cog!" // Notif
            ),
            listOf("Read Note", "Next", "Done"),
            cog,
            coins
        )

        botOfClock = Location("Clock Tower Base", "Base of the old tower.", "")

        topOfClock = Location(
            "Top of Clock Tower",
            "The top of the tower.",
            "",
            listOf(
                "You fixed the broken clock tower"
            ),
            listOf("Read Note", "Next", "Done"),
            "Rusty Cog"
        )

        // Route 2 (will fix the schema here a little later on)
        jail = Location(
            "Jail",
            "A small stone jail.",
            "Guard: I'm not in the mood for you right now. Come back later.",
            listOf(
                "Walking past the jail you hear someone call out.\nIt's Stan (of course it is).\nYou asked the jailer if he would let Stan out\nYou can have the key if you bring me grog, says the guard.",
                "You make the trade with the guard\nThe grog is gone in seconds, and... \nthe guard fell asleep??\nWhether this was the ominous power of the grog, we'll never know.",
                "The key doesnt work. Instead, Stan walks out on his own.\nHmm, Stan mutters.\nGuess he never locked it.\nThat key there must've been for something else."
            ),
            listOf("Read Note", "Next", "Done"),
            "Key",
            "Grog"
        )

        alley = Location(
            "Alley",
            "A narrow alley with stray dogs.",
            "Dodgy dealer: Sorry lad, I'm out of Grog for now.",
            listOf(

            ),
            listOf("Read Note", "Next", "Done"),
            "Grog",
        )

        storageYard = Location(
            "Storage Yard",
            "A yard full of crates.",
            "",
            listOf(
                "The gate to the storage yard is locked\nYou try it with your key. And...\nThe storage yard is unlocked\nA banana is conveniently placed on a stool"
            ),
            listOf("Read Note", "Next", "Done"),
            "Banana",
            "Key"
        )

        mayorsMansion = Location(
            "Mayor's Mansion",
            "The mayors grand mansion.", // Can't believe I had it as a 'locked mansion' for so long when it is locked until you are inside of it.

            "")
        locations.add(townCentre)
        locations.add(scummBar)
        locations.add(generalStore)
        locations.add(jail)
        locations.add(alley)
        locations.add(storageYard)
        locations.add(botOfClock)
        locations.add(topOfClock)
        locations.add(mayorsMansion)


// Ignore the indentation below, they are simply for a better structure.

// Also note that the locations themselves were derrived from chatGPT (I ain't writing all'at) the code was myself from preexisting samples.

        townCentre.connectLocation.add(scummBar)
        townCentre.connectLocation.add(generalStore)
        townCentre.connectLocation.add(jail)
        townCentre.connectLocation.add(alley)
        townCentre.connectLocation.add(botOfClock)
        townCentre.connectLocation.add(mayorsMansion)

        scummBar.connectLocation.add(townCentre)

        generalStore.connectLocation.add(townCentre)

        jail.connectLocation.add(townCentre)

        alley.connectLocation.add(storageYard)
        alley.connectLocation.add(townCentre)

        storageYard.connectLocation.add(alley)

        botOfClock.connectLocation.add(townCentre)
        botOfClock.connectLocation.add(topOfClock)

        topOfClock.connectLocation.add(botOfClock)

        mayorsMansion.connectLocation.add(townCentre)

        currentLocation = townCentre
//        currentQuest = townCentre
    }

    fun getItem() {
        itemInHand = currentLocation.rewardItem
    }
}


/**

 * Main UI window, handles user clicks, etc.

 *

 * @param app the app state object

 */

class MainWindow(val app: App) {

    val frame = JFrame("Meelé Island explorer")

    private val panel = JPanel().apply { layout = null }

    private val titleLabel = JLabel("Meelé island explorer")
    private val infoLabel = JLabel()
    private val notifLabel = JLabel()
    private val dialogLabel = JLabel()

    private var action1Button = JButton("Doing zilch")
//    private var action2Button = JButton("Doing zilch")

    private val centrebutton = JButton("To Town Centre")
    private val scummbutton = JButton("To Scumm Bar")
    private val generalbutton = JButton("To General Store")
    private val jailbutton = JButton("To Jail")
    private val alleybutton = JButton("To Alley")
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

        titleLabel.setBounds(600, 30, 340, 30)
        infoLabel.setBounds(550, 90, 340, 30)
        notifLabel.setBounds(380, 450, 650, 200)
        dialogLabel.setBounds(30, 60, 650, 500)

        action1Button.setBounds(600, 650, 170, 30)
//        action2Button.setBounds(775, 650, 170, 100)
        centrebutton.setBounds(30, 650, 170, 30)
        scummbutton.setBounds(30, 690, 170, 30)
        generalbutton.setBounds(30, 730, 170, 30)
        jailbutton.setBounds(210, 650, 170, 30)
        alleybutton.setBounds(210, 690, 170, 30)
        storagebutton.setBounds(210, 730, 170, 30)
        botOfClockbutton.setBounds(390, 650, 170, 30)
        topOfClockbutton.setBounds(390, 690, 170, 30)
        mayorbutton.setBounds(390, 730, 170, 30)

        panel.add(titleLabel)
        panel.add(infoLabel)
        panel.add(notifLabel)
        panel.add(dialogLabel)

        panel.add(action1Button)
//        panel.add(action2Button)
        panel.add(centrebutton)
        panel.add(scummbutton)
        panel.add(generalbutton)
        panel.add(jailbutton)
        panel.add(alleybutton)
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
//        action2Button.font = Font(Font.SANS_SERIF, Font.BOLD, 12)
        centrebutton.font = Font(Font.SANS_SERIF, Font.PLAIN, 10)
        scummbutton.font = Font(Font.SANS_SERIF, Font.PLAIN, 10)
        generalbutton.font = Font(Font.SANS_SERIF, Font.PLAIN, 10)
        jailbutton.font = Font(Font.SANS_SERIF, Font.PLAIN, 10)
        alleybutton.font = Font(Font.SANS_SERIF, Font.PLAIN, 10)
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
        generalbutton.addActionListener { goLocation(app.generalStore) }
        jailbutton.addActionListener { goLocation(app.jail) }
        alleybutton.addActionListener { goLocation(app.alley) }
        storagebutton.addActionListener { goLocation(app.storageYard) }
        botOfClockbutton.addActionListener { goLocation(app.botOfClock) }
        topOfClockbutton.addActionListener { goLocation(app.topOfClock) }
        mayorbutton.addActionListener { goLocation(app.mayorsMansion) }

        action1Button.addActionListener {
            processPlayerAction(1)
            updateUI()
        }
//        action2Button.addActionListener {
//            processPlayerAction(2)
//            updateUI()
//        }
    }


    fun updateUI() {

        val location = app.currentLocation
        val name = location.name
        val description = location.description

        infoLabel.text = "You are at ${name}, ${description}" // was going

//        notifLabel.text = "ITEM: " + app.itemInHand // Don't need this except for test

        if (app.itemInHand == location.requiredItem) {
            dialogLabel.text = location.questNotes[location.currentQuestNote]
            action1Button.isVisible = true
            action1Button.text = location.actionText[location.currentQuestNote]
        } else {
            dialogLabel.text = location.noQuestNotes
            action1Button.isVisible = false
        }

        centrebutton.isEnabled = location.connectLocation.contains(app.townCentre)
        scummbutton.isEnabled = location.connectLocation.contains(app.scummBar)
        generalbutton.isEnabled = location.connectLocation.contains(app.generalStore)
        jailbutton.isEnabled = location.connectLocation.contains(app.jail)
        alleybutton.isEnabled = location.connectLocation.contains(app.alley)
        storagebutton.isEnabled = location.connectLocation.contains(app.storageYard)
        botOfClockbutton.isEnabled = location.connectLocation.contains(app.botOfClock)
        topOfClockbutton.isEnabled = location.connectLocation.contains(app.mayorsMansion)
        mayorbutton.isEnabled = location.connectLocation.contains(app.mayorsMansion)
    }

    private fun processPlayerAction(action: Int) {

        val location = app.currentLocation

        if (location.questCompleted()) {
            println("DONE")
            app.getItem()
        } else {
            location.nextNote()
        }

        updateUI()
    }

    private fun goLocation(destination: Location) {
        app.currentLocation = destination
        destination.reset()
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
