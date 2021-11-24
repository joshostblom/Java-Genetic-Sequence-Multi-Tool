# Java-Genetic-Sequence-Multi-Tool
------------------------------------
| Java Genetic Sequence Multi-Tool |
------------------------------------

README

The Java Gentetic Sequence Multi-Tool is a tool to easily analyze/modify genetic sequences.
You may use the driver provided or you can use the Sequence class to use sequences within your own program.
The program accepts file input (including FASTA) or manual sequence input.

Program was tested using a batch file a on Windows operating system.
Program was created using IntelliJ integrated development enviroment.
Citation: JetBrains. IntelliJ IDEA. 2021.

Functions include:
- Transfering sequence to RNA from DNA or vice versa.
- Getting the reverse strand, complimentary strand, or reverse complimentary strand.
- Find where in the sequence is a subsequence.
- Count the nucleotides and amino acids.

Documentation for the Sequence class is included in doc folder (look for "Sequence.html").

INSTRUCTIONS FOR DRIVER
-----------------------
Opening batch file: After downloading content, you should just be able to double click on the batch file (JGSMT Driver.bat) to open
the driver. DO NOT TAKE THE DRIVER OUT OF IT'S FOLDER, make a shortcut if you plan on opening it elsewhere,
the driver needs to stay within the "src" folder with the other java files.

- SEQUENCE PROMPT: When you open the driver, it will ask how you want to input your sequence. You have two options:
(1) Enter a file or (2) Enter sequence manually.

If you choose to enter a file: you must provide the directory, which would look something like this if you file
is on the desktop, "C:\Users\username\Desktop\sequenceFile.txt". YOUR DIRECTORY CANNOT HAVE A SPACE WITHIN IT.
So make sure that there are no spaces in the file name or the folders that it's within. Also be sure to add
the file extension such as ".txt". An easy way to input the directory is to just drag the file into the terminal
window and it will type the directory out for you. You file can be any type of text file and the program will
accept FASTA format.

If you choose to enter the sequence manually: Type your sequence out with no spaces, all caps, and on one line.

After submitting your sequence, it will ask for the reading frame of the sequence, whether it's a forwards strand or not,
and what the sequence type is. If a non-valid answer is given, it will ask again.

With either option, if a non-nucleotide character is found in your sequence, a warning will come up providing the
the character and the index it was located in before moving on to the menu.

- MAIN MENU: The menu will provide a line of information about your sequence and provide some options:
(1) Print a version of the sequence - this will take you the the print options.
(2) Transform sequence to RNA (or DNA if your sequence is in RNA format) - this will transcribe the sequence to the
other format and restart the menu.
(3) Search sequence for a subsequence - this will ask you to enter a subsequence and it will provide the index the subsequence
is found at. It will say if the subsequence couldn't be located.
(4) Restart with new sequence - this will restart the program.
(5) Exit - this will stop the program.

- PRINT OPTIONS: The menu will provide a line of information about your sequence and provide some options:
(1) Print sequence - this will provide the sequence information line and print the sequence in the console.
(2) Print reverse - this will provide the reverse sequence information line and print the reverse sequence in the console.
(3) Print complimentary strand - this will provide the complimentary sequence information line and print the complimentary sequence in the console.
(4) Print reverse complimentary strand - this will provide the reverse complimentary sequence information line and print the reverse
complimentary sequence in the console.
(5) Print nucleotide count - this will provide a list of all nucleotides and the integer of each one found in the sequence.
(6) Print amino acid count - this will provide a list of all amino acids and the integer of each one found in the sequence.
(7) Back - this will return to the main menu.

SAMPLE INPUT/OUTPUT
-------------------
Refer to the sample images folder for smaple input/output of driver.
