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
F */

class Location(
    val name: String,
    val description: String,
    val noQuestNotes: String,
    val questNotes: List<String> = listOf(),
    val actionText: List<String> = listOf(),
    val rewardItem: String? = null,
    val requiredItem: String? = null,
    val end: Boolean = false
) {
    var currentQuestNote: Int = 0
    val connectLocation = mutableListOf<Location>()
    var questCompleted = false

    fun reset() { // This makes the Note count jump bac down to the starting value when a quest is finished
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

    val townCentre: Location
    val bar: Location
    val store: Location
    val jail: Location
    val alley: Location
    val yard: Location
    val clock: Location
    val spire: Location
    val mayorsMansion: Location

    init {

        val conversation = "You have spoken to Guybrush"
        val coins = "5 coins"
        val cog = "Rusty cog"
        val NOTHING = "Paper plane"
        val Find = "Find Stan"
        val Key = "Useless key"
        val Banana = "Mushy Banana"
        val end = "The end"

        // Initial meeting/note from Guybrush
        townCentre = Location(
            "Town Centre",
            "The centre of town.",
            "Help Guybrush escape the mansion",
            listOf(
                "You wake up with no memory of last night.",
                "A note on the floor reads: Dear entrusted one -G.T.",
                "<html>It is I, the mighty pirate Guybrush Threepwood!\n I know this is a great honour, but please... Calm yourself and listen closely to the quest I have for you.<html>",
                "<html>Guybrush: Elaine has me stuck inside of the mansion... She doesn't seem too pleased with me.<html>",
                "<html>But I'm sure if you take care of the chores around town...\n I'm sure that She'll be slightly happier.<html>",
                "<html>Though, now that I think about it... I'm not too sure what she does...<html>",
                "<html>SYSTEM: Maybe start by visiting the bar? Surely they have something that needs doing...<html>"
            ),
            listOf("Continue", "Read Note", "Next", "Next", "Next", "Next", "Done"),
            conversation,
            null
        )

        bar = Location(
            "Scumm Bar",
            "A noisy pirate bar.",
            "Hello, sailor! Come back another time",
            listOf(
                "You notice the a cook perched atop a table quivering slightly",
                "Cook: Arg! There's a giant, red-eyed rat scurrying about!",
                "<html>Cook: You there! The Cook shouts.\n If you manage rid of it, I'll give ye 5 coins for the trouble!<html>",
                "You kicked the rat.",
                "Cook: Thank you me lad, ere's your money.",
                "SYSTEM: You gained ${coins}. But... What should you do with it?" // coins obviously doesn't do anything. Thought t'was a good use is all.
            ),
            listOf("Continue", "Next", "Kick rat", "Continue", "Next", "Done"),
            coins,
            conversation
        )

        store = Location(
            "General Store",
            "A shop with odd items.",
            "Old man: Outta me store! I've nothin' for ye",
            listOf(
                "Infront of you sist a grumpy looking scruffy old man",
                "Old grump: Wad ye wan?",
                "Old grump: You the handyman for the clock tower?",
                "<html>Old grump: Well why didn't ye say so?\n All the gear you needs already upstairs\n ${cog + "'ll"} be ${coins}<html>",
                "SYSTEM: You got ${cog + "!"}"
            ),
            listOf("Continue", "Stare blankly", "Sure...", "Buy ${cog}", "Done"),
            cog,
            coins
        )

        spire = Location(
            "Spire",
            "The top of the tower.",
            "",
            listOf(
                "<html>You found all the required tools to fix the clock tower conveniently layed out<html>",
                "SYSTEM: You fixed the broken clock tower"
            ),
            listOf("Fix clock", "Done"),
            NOTHING,
            cog
        )

        clock = Location(
            "Clock Tower Base",
            "Base of the old tower.",
            "There is NOTHING to do here.",
            listOf(
                "<html>As you reach the bottom of the stairs you notice a ${NOTHING} infront of you<html>",
                "<html>The plane has the words ${"Read Me"} scribbled on it<html>",
                "You unfold it and it reads...",
                "It's me again, Guybrush!\n Elaine is mildly happier... just bring Stand and I'm sure he'll convince her.",
                "If you haven't already met Stan... and trust me, you'd know...",
                "He's either in the local prison, or He's inside a barrel in the middle of the sea."
            ),
            listOf("Pick up", "Unfold", "Continue", "Next", "Next", "Done"),
            Find,
            NOTHING
        )

        // Route 2 (will fix the schema here a little later on)
        jail = Location(
            "Jail",
            "A small stone jail.",
            "Guard: I'm not in the mood for you right now. Come back later.",
            listOf(
                "Walking past the jail you hear someone call out. It just so happens to be Stan (of course it is).",
                "Stan: Hey!... Hey you there... You've gotta get me outta here.",
                "The Jail Guard sits drunkly on a stool",
                "After talking to the drunk you manage to convince him to wager his keys to the cell for 10 (non existent) coins)",
                "The coin lands... and... it lands heads, and you lose. Buuuut... the guard doesn't know that.",
                "Just as the drunken Guard hands over his keys, Stand opens the door to his cell...\n I-I guess it was never locked, Ha Ha... Ha.",
                "Oh well we have his keys now, lets go and see what they unlock",
                "SYSTEM: Maybe check somewhere you haven't so far"
            ),
            listOf("Talk", "Next", "Next", "Next", "Lie to Guard", "Continue", "Continue", "Done"),
            Key,
            Find
        )

        alley = Location(
            "Alley",
            "A narrow alley with stray dogs.",
            "Might as well be a wasteland",
            listOf(""),
            listOf(""),
            "",
            "",
        )

        yard = Location(
            "Storage Yard",
            "A yard full of crates.",
            "",
            listOf(
                "The gate to the storage yard is locked with a padlock the same colour as your key.",
                "You try it with your key.",
                "The storage yard is unlocked",
                "You find a ${Banana} conveniently placed on a stool",
                "SYSTEM: You gained a ${Banana} for Guybrush"
            ),
            listOf("Continue", "Unlock", "Continue", "Next", "Done"),
            Banana,
            Key
        )

        mayorsMansion = Location(
            "Mayor's Mansion",
            "The mayors grand mansion.", // Can't believe I had it as a 'locked mansion' for so long when it is locked until you are inside of it.

            "The mansion is locked right now",
            listOf(
                "The gates of the mansion slowly open to you...",
                "To be continued"
            ),
            listOf("Continue", "End"),
            end,
            Banana,
            true,

        )

        locations.add(townCentre)
        locations.add(bar)
        locations.add(store)
        locations.add(jail)
        locations.add(alley)
        locations.add(yard)
        locations.add(clock)
        locations.add(spire)
        locations.add(mayorsMansion)


// Ignore the indentation below, they are simply for a better structure.

// Also note that the locations themselves were derrived from chatGPT (I ain't writing all'at) the code was myself from preexisting samples.

        townCentre.connectLocation.add(bar)
        townCentre.connectLocation.add(store)
        townCentre.connectLocation.add(jail)
        townCentre.connectLocation.add(alley)
        townCentre.connectLocation.add(clock)
        townCentre.connectLocation.add(mayorsMansion)

        bar.connectLocation.add(townCentre)

        store.connectLocation.add(townCentre)

        jail.connectLocation.add(townCentre)

        alley.connectLocation.add(yard)
        alley.connectLocation.add(townCentre)

        yard.connectLocation.add(alley)

        clock.connectLocation.add(townCentre)
        clock.connectLocation.add(spire)

        spire.connectLocation.add(clock)

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
    val mapIcon = ImageIcon(ClassLoader.getSystemResource("Map.png"))

    private val titleLabel = JLabel("Meelé island explorer")
    private val infoLabel = JLabel()
    private val notifLabel = JLabel()
    private val dialogLabel = JLabel()
    private val mapLabel = JLabel(mapIcon)

    private var action1Button = JButton("Doing zilch")
//    private var action2Button = JButton("Doing zilch")

    private val centrebutton = JButton("Town Centre")
    private val barbutton = JButton("Scumm Bar")
    private val storebutton = JButton("Store")
    private val jailbutton = JButton("Jail")
    private val alleybutton = JButton("Alley")
    private val yardbutton = JButton("Yard")
    private val clockbutton = JButton("Clock Tower")
    private val spirebutton = JButton("Spire") // Shorter word for the top of a clock tower
    private val mansionbutton = JButton("Mansion")

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
        dialogLabel.setBounds(120, 250, 650, 500)
        mapLabel.setBounds(500, 60, 1100, 750)

        action1Button.setBounds(180, 650, 170, 30)
//        action2Button.setBounds(775, 650, 170, 100)
        centrebutton.setBounds(1000, 530, 120, 30)
        barbutton.setBounds(770, 460, 120, 30)
        storebutton.setBounds(803, 605, 80, 30)
        jailbutton.setBounds(1248, 440, 80, 30)
        alleybutton.setBounds(1248, 565, 80, 30)
        yardbutton.setBounds(1248, 640, 80, 30)
        clockbutton.setBounds(1120, 300, 100, 30)
        spirebutton.setBounds(1130, 220, 80, 30)
        mansionbutton.setBounds(955, 225, 120, 30)

        panel.add(titleLabel)
        panel.add(infoLabel)
        panel.add(notifLabel)
        panel.add(dialogLabel)

        panel.add(action1Button)
//        panel.add(action2Button)
        panel.add(centrebutton)
        panel.add(barbutton)
        panel.add(storebutton)
        panel.add(jailbutton)
        panel.add(alleybutton)
        panel.add(yardbutton)
        panel.add(clockbutton)
        panel.add(spirebutton)
        panel.add(mansionbutton)
        panel.add(mapLabel) // Has to be last bc otherwise it overlays ontop of buttons
    }


    private fun setupStyles() {
        titleLabel.font = Font(Font.SANS_SERIF, Font.BOLD, 18)
        infoLabel.font = Font(Font.SANS_SERIF, Font.BOLD, 14)
        notifLabel.font = Font(Font.SANS_SERIF, Font.BOLD, 20)
        dialogLabel.font = Font(Font.SANS_SERIF, Font.PLAIN, 16)
        action1Button.font = Font(Font.SANS_SERIF, Font.BOLD, 12)
//        action2Button.font = Font(Font.SANS_SERIF, Font.BOLD, 12)
        centrebutton.font = Font(Font.SANS_SERIF, Font.PLAIN, 10)
        barbutton.font = Font(Font.SANS_SERIF, Font.PLAIN, 10)
        storebutton.font = Font(Font.SANS_SERIF, Font.PLAIN, 10)
        jailbutton.font = Font(Font.SANS_SERIF, Font.PLAIN, 10)
        alleybutton.font = Font(Font.SANS_SERIF, Font.PLAIN, 10)
        yardbutton.font = Font(Font.SANS_SERIF, Font.PLAIN, 10)
        clockbutton.font = Font(Font.SANS_SERIF, Font.PLAIN, 10)
        spirebutton.font = Font(Font.SANS_SERIF, Font.PLAIN, 10)
        mansionbutton.font = Font(Font.SANS_SERIF, Font.PLAIN, 10)
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
        barbutton.addActionListener { goLocation(app.bar) }
        storebutton.addActionListener { goLocation(app.store) }
        jailbutton.addActionListener { goLocation(app.jail) }
        alleybutton.addActionListener { goLocation(app.alley) }
        yardbutton.addActionListener { goLocation(app.yard) }
        clockbutton.addActionListener { goLocation(app.clock) }
        spirebutton.addActionListener { goLocation(app.spire) }
        mansionbutton.addActionListener { goLocation(app.mayorsMansion) }

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
        val link = app.currentLocation.connectLocation

        infoLabel.text = "You are at ${name}, ${description}" // was going

//        if (location.requiredItem == null) { // I thought it was ugly to display null as an item. Hence, I did this.
//            notifLabel.text = "You have no items"
//        } else {
//            notifLabel.text = "Item: " + app.itemInHand // Don't need this except for test
//        }
//        if (location.requiredItem == null) {
//        }

        if (app.itemInHand == location.requiredItem) {
            dialogLabel.text = location.questNotes[location.currentQuestNote]
            action1Button.isVisible = true
            action1Button.text = location.actionText[location.currentQuestNote]
        } else {
            dialogLabel.text = location.noQuestNotes
            action1Button.isVisible = false
        }

        // for an ending:
        if (location.end && app.itemInHand == "Mushy Banana") {

        }

        val locationButtons = mapOf( // Dt notes
            centrebutton to app.townCentre,
            barbutton to app.bar,
            storebutton to app.store,
            jailbutton to app.jail,
            alleybutton to app.alley,
            yardbutton to app.yard,
            clockbutton to app.clock,
            spirebutton to app.spire,
            mansionbutton to app.mayorsMansion
        )
        for ((button, loc) in locationButtons) { // Dt notes
            button.isEnabled = location.connectLocation.contains(loc)
        }
    }

    private fun processPlayerAction(action: Int) {

        val location = app.currentLocation

        if (location.questCompleted()) {
            println("DONE")
            app.getItem()
        } else {
            location.nextNote()
        }

        if (location.end && location.questCompleted()) {
            JOptionPane.showMessageDialog(
                frame,
                "Elaine opens the door...\nYou've completed your quest!",
                "The END!",
                JOptionPane.INFORMATION_MESSAGE
            )
            action1Button.isVisible = false
            return
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

        JOptionPane.showMessageDialog( // Dt notes. Game instructions.
            frame,
            "Use the buttons on the map to move between (linked) locations.\nClick the action button on the left to interact with quests.",
            "How to Play",
            JOptionPane.INFORMATION_MESSAGE
        )
    }

}



/**

 * Info UI window is a child dialog and shows how the

 * app state can be shown / updated from multiple places

 *

 * @param owner the parent frame, used to position and layer the dialog correctly

 * @param app the app state object

 */