# Plan for Testing the Program

The test plan lays out the actions and data I will use to test the functionality of my program.

Terminology:

- **VALID** data values are those that the program expects
- **BOUNDARY** data values are at the limits of the valid range
- **INVALID** data values are those that the program should reject

---

## Game Setup

When game is launched > Player should start at Town Centre, with no items + the instructions should play before anything happens.

### Test Data To Use

No input is needed as it is supposed to load during launch.

### Expected Test Result

Instructions appear > Player spawns at town centre with no items 
---

## Player Movement

Clicking a location button should move the player there. Locations have to be connected mind you. 

### Test Data To Use

From the Towm Centre I will click the bar (which is connected), and I will also click the Spire (not connected)
### Expected Test Result

I expect to be able to move to the bar without conflict. I expect nothing to happen when I click on the Spire.
---

## Completing a Quest

When a player has correct item > quest should trigger dialog, until ending with a reward

### Test Data To Use

completing town centre letter > unlocks scumm bar dialog/quest

### Expected Test Result

Bar quest works as intended and player gains coins

---

## Visiting Without the Required Item

Visiting without requirements met > no quest dialog to occur

### Test Data To Use

Visiting store without correct quest items

### Expected Test Result

NoQuest dialog to occur

---

## Win

When the final quest is complete at mansion > ending 

### Test Data To Use

Complete all quests > Mushy banana > ending

### Expected Test Result

ending dialog occurs and the action button is hidden 

---




