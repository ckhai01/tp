---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# GreyBook User Guide

GreyBook is a **desktop app for managing contacts, optimised for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, GreyBook can get your contact management tasks done faster than traditional GUI apps.

> New to a CLI? It just means that you type short commands (like 'add' or 'list') instead of clicking through menus on a GUI.

<!-- * Table of Contents -->
<page-nav-print />

---

## Quick start

1. **Install Java 17 or newer.**
   - Check your version: open a terminal (Windows: Command Prompt; macOS/Linux: Terminal) and run `java -version`.
   - If you don’t have Java 17+, install the latest Long Term Support (LTS) Java. macOS users can follow this guide: <https://se-education.org/guides/tutorials/javaInstallationMac.html>.

2. **Download GreyBook.**
   - Get the latest `.jar` from the [Releases page](https://github.com/AY2526S1-CS2103T-F13-4/tp/releases).
   - Save it to a folder you’ll remember (this becomes GreyBook’s “home” folder).

3. **Start the app.**
   - In a terminal, run the command `cd` with the GreyBook's "Home" folder, and run GreyBook with Java:
     ```
     cd C:\Users\John\GreyBookFolder
     java -jar GreyBook.jar
     ```

4. **What you’ll see.**
   - A window opens with sample contacts so you can try commands.

5. **Try a few commands:**
    - `help` — open the help window
    - `add n/John Doe p/98765432 e/johnd@example.com i/A0000000Y` — add a person
    - `mark 1 p/` — marks the attendance of the 1st person in the list as `Present`
    - `mark A0000000Y a/` — marks John Doe as `Absent`
    - `unmark 1` — remove the attendance status of the 1st person in the list
    - `unmark A0000000Y` — removes the attendance status of John Doe
    - `unmark all` — removes the attendance status of all people
    - `list` — show all people
    - `delete 3` — delete the 3rd person in the list
    - `clear` — delete all people
    - `exit` — quit the app

> If you see a security prompt on macOS the first time you open the app, right-click the `.jar` and choose **Open**, then confirm.

---

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

- Words in `UPPER_CASE` are called **parameters**, things you replace.<br>
  e.g. in `add n/NAME`, replace `NAME` with a person's name, like `add n/John Doe`.

- Items in square brackets are optional.<br>
  e.g. `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

- Items with `…`​ after them can be used multiple times, including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (0 times), `t/friend` (1 time), `t/friend t/family` (2 times) etc.

- Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

- Extra parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

- If a prefix for the command occurs in the argument, you may use quotation marks `"` to escape it.
  e.g. `SomeCommandName p/"p-Slash t/ contains t-Slash" t/tag`

* If you want to use quotation marks `"` in your argument, you have to escape it with a backslash `\`
  e.g. `SomeCommandName t/Quote: \"`

* Likewise if you wanted to use backslashes `\` in your argument, you have to escape it with a backslash.
  e.g. `SomeCommandName t/Backslash: \\`

- If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application. Type them out manually instead!
  </box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Adding a person: `add`

Adds a person to the GreyBook.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL i/STUDENTID [t/TAG]…​`

<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0)
</box>

Examples:

- `add n/John Doe p/98765432 e/johnd@example.com i/A0000000Y`
- `add n/Betsy Crowe t/friend e/betsycrowe@example.com i/A1111111M p/1234567 t/criminal`

### Listing all persons : `list`

Shows a list of all persons in the GreyBook.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the GreyBook.

Format: `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [i/STUDENTID] [t/TAG]…​`

- Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
- At least one of the optional fields must be provided.
- Existing values will be updated to the new values.
- When editing tags, the existing tags of the person will be removed i.e. adding of tags is not cumulative.
- You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.

Examples:

- `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
- `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Marking a person : `mark`

Marks the attendance of an existing person in the GreyBook.

Format: `mark (INDEX || STUDENTID) (p/ || a/ || l/ || e/)`

* `||` indicates "or", meaning that within the parentheses, the user can provide either one option or the other, but **not both simultaneously**.
* `mark INDEX` marks the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* `mark STUDENTID` marks the person with the specified `STUDENTID`. The person with the student ID must be in the displayed person list.
* One attendance flag must be provided.
  * `p/`: Present
  * `a/`: Absent
  * `l/`: Late
  * `e/`: Excused
* Only one attendance flag can be used at a time. Using multiple flags (e.g. `p/ a/`) will result in an error.
* Attendance flags are **not cumulative**; a new flag replaces the previous status.


Examples:
*  `mark A0000000Y p/` Marks the person with the student ID `A0000000Y` as `Present`.
*  `mark 2 e/` Marks the 2nd person as `Excused`.

### Unmarking a person : `unmark`

Removes the attendance status of an existing person in the GreyBook.

Format: `unmark INDEX` or `unmark STUDENTID` or `unmark all`

* `unmark INDEX` removes the attendance of the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* `unmark STUDENTID` removes the attendance of the person with the specified `STUDENTID`. The person with the student ID must be in the displayed person list.
* `unmark all` removes the attendance of **all persons** in the contact list.

Examples:
*  `unmark A0000000Y` Unmarks the person with the student ID `A0000000Y`.
*  `unmark 2` Unmarks the 2nd person.
*  `unmark all` Unmarks everyone in the current contact list.


### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

- The search is case-insensitive. e.g. `hans` will match `Hans`
- The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
- Only the name is searched.
- Only full words will be matched e.g. `Han` will not match `Hans`
- Persons matching at least one keyword will be returned.
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:

- `find John` returns `john` and `John Doe`
- `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified person from the GreyBook.

Format: `delete INDEX` or `delete STUDENTID`

- Deletes the person at the specified `INDEX` or with the specified `STUDENTID`.
- The index refers to the index number shown in the displayed person list.
- The index **must be a positive integer** 1, 2, 3, …​
- The student ID must follow the format `A0000000Y` where:
  - First character must be 'A'
  - Followed by exactly 7 digits
  - Ending with any English letter (A-Z)

Examples:

- `list` followed by `delete 2` deletes the 2nd person in the GreyBook.
- `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.
- `delete A0123456J` deletes the person with student ID A0123456J from the GreyBook.

### Clearing all entries : `clear`

Deletes **all** entries from GreyBook.

Format: `clear`

> This action cannot be undone!

### Exiting the program : `exit`

Closes GreyBook.

Format: `exit`

### Saving the data

GreyBook data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

GreyBook data is saved automatically as a JSON file at: <br>
`[JAR file location]/data/greybook.json`

<box type="warning" seamless>
**Caution!**
Editing this file is recommended for advanced users only. If your changes to the data file makes it invalid, GreyBook will discard all data and start fresh on the next run. Before you edit, make a backup copy of the file.
Some changes can cause the GreyBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQs

**Q: What operating systems does GreyBook support?**
**A:** Any system that can run Java 17+ (Windows, macOS, Linux). If Java runs, GreyBook runs.

**Q: Do I need to install anything besides GreyBook?**
**A:** Yes, **Java 17 or newer**. Check with `java -version`. If it’s older, install a current Long Term Support Java version.

**Q: How do I update GreyBook to a new version?**
**A:** Download the new `.jar` and run it. Your existing data in `data/greybook.json` will be picked up automatically if you keep it in the same folder.

**Q: Will I lose my data when I update?**
**A:** No. The data file is separate from the app. Keep `data/greybook.json` with the `.jar` and you’re good.

**Q: Where exactly is my data?**
**A:** In `[JAR file location]/data/greybook.json`.

**Q: Can I move GreyBook to another computer (or a USB drive)?**
**A:** Yes. Copy the `.jar` **and** the `data` folder together. On the new computer, double-click the `.jar`.

**Q: Is there an undo command?**
**A:** Not currently. Actions like `delete` and `clear` are immediate and irreversible. Consider regular backups of `data/greybook.json`.

**Q: How do I back up my contacts?**
**A:** Close GreyBook and copy `data/greybook.json` to a safe place (cloud/storage drive).

**Q: I edited the JSON and something broke. What now?**
**A:** Close GreyBook, restore your backup `greybook.json`, then reopen GreyBook. Avoid manual edits unless you’re confident.

**Q: Are name searches case-sensitive?**
**A:** No. `hans` matches `Hans`.

**Q: Why doesn’t `find Han` match `Hans`?**
**A:** `find` matches **full words**. Use the full word or multiple keywords.

**Q: How do tags work?**
**A:** Add any number: `t/friend t/colleague`. Editing tags **replaces** the old set. Use `t/` (empty) to clear all tags.

**Q: Can I store addresses or other fields?**
**A:** Only the fields shown in the command formats are supported (e.g., `n/`, `p/`, `e/`, `i/`, and tags).

**Q: Does GreyBook save automatically?**
**A:** Yes. Changes are saved to `greybook.json` right after each command.

**Q: How many contacts can I store?**
**A:** There’s no hard limit in the app; performance depends on your computer.

**Q: How do I reset GreyBook to factory data?**
**A:** Close the app, delete `data/greybook.json`, and reopen GreyBook (you’ll start fresh with sample data).

---

## Known issues

1. **App opens off-screen after moving between multiple monitors.**
   **Fix:**
   - Close GreyBook.
   - Delete the `preferences.json` file created by GreyBook.
   - Start GreyBook again.
     _(`preferences.json` is in the same folder as your `.jar`, or in your app data folder depending on OS.)_

---

## Command summary

Action | What it does | Format (examples)
---|---|---
**Add** | Create a new contact | `add n/NAME p/PHONE_NUMBER e/EMAIL i/STUDENTID [t/TAG]...`<br>e.g., `add n/James Ho p/22224444 e/jamesho@example.com i/A0000000Y t/friend t/colleague`
**List** | Show all contacts | `list`
**Find** | Search by name (full words) | `find KEYWORD [MORE_KEYWORDS]`<br>e.g., `find James Jake`
**Edit** | Update details | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [i/STUDENTID] [t/TAG]...`<br>e.g., `edit 2 n/James Lee e/jameslee@example.com`
**Mark** | Mark attendance | `mark (INDEX \|\| STUDENTID) (p/ \|\| a/ \|\| l/ \|\| e/)`<br> e.g.,`mark 2 p/`, `mark A0123456J a/`
**Unmark** | Unmark attendance | `unmark INDEX` or `unmark STUDENTID` or `unmark all`<br> e.g.,`unmark 2`, `unmark A0123456J`, `unmark all`
**Delete** | Remove a contact | `delete INDEX` or `delete STUDENTID`<br>e.g., `delete 3`, `delete A0123456J`
**Clear** | Delete **all** contacts | `clear`
**Help** | Open the help window | `help`
**Exit** | Quit the app | `exit`

