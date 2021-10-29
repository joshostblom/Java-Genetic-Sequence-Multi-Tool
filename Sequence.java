import java.io.*;
import java.util.Scanner;

/**
 * @author Joshua Ostblom
 */

public class Sequence {
    //List variables
    private boolean forwardStrand;
    private int readingFrame;
    private String sequenceType;
    private StringBuilder sequenceData;

    /**
     * Default constructor. Creates an empty forward DNA strand.
     */
    public Sequence() {
        //Initialize variables
        this.forwardStrand = true;
        this.readingFrame = 1;
        this.sequenceType = "DNA";
        this.sequenceData = new StringBuilder();
    }

    /**
     * Constructor for file input.
     * @param forwardStrand true if the sequence is 5' to 3'.
     * @param readingFrame the reading frame of the sequence. Non-valid inputs will be defaulted to 1.
     * @param sequenceType DNA or RNA. Non-valid inputs will be defaulted to DNA.
     * @param file FASTA file or any text file.
     */
    public Sequence(boolean forwardStrand, int readingFrame, String sequenceType, File file) throws FileNotFoundException {
        //Initialize variables
        this.setForwardStrand(forwardStrand);
        this.setReadingFrame(readingFrame);
        this.setSequenceType(sequenceType);
        this.sequenceData = new StringBuilder();

        Scanner s = new Scanner(file);

        //Reads the file and adds only the sequence to the sequenceData string
        while (s.hasNextLine())
        {
            //Gets next line and adds it to the sequence
            String line = s.nextLine().trim();
            if (line.charAt(0) != '>')
            {
                this.sequenceData.append(line);
            }
        }

        //This sends it to the set method to check for invalid characters
        this.setSequenceData(this.sequenceData);
    }

    /**
     * Constructor for manual text input.
     * @param forwardStrand true if the sequence is 5' to 3'.
     * @param readingFrame the reading frame of the sequence. Non-valid inputs will be defaulted to 1.
     * @param sequenceType DNA or RNA. Non-valid inputs will be defaulted to DNA.
     * @param sequenceData the nucleotide sequence.
     */

    public Sequence(boolean forwardStrand, int readingFrame, String sequenceType, String sequenceData) {
        //Initialize variables
        this.setForwardStrand(forwardStrand);
        this.setReadingFrame(readingFrame);
        this.setSequenceType(sequenceType);
        this.setSequenceData(new StringBuilder(sequenceData));
    }

    /**
     * Method to set whether the sequence is a forward or reverse strand.
     * @param forwardStrand true if 5' to 3', false if 3' to 5'
     */
    public void setForwardStrand(boolean forwardStrand) {
        this.forwardStrand = forwardStrand;
    }

    /**
     * Method to set the reading frame of the sequence. Non-valid reading frames will be defaulted to 1.
     * @param readingFrame the reading frame of the sequence.
     */
    public void setReadingFrame(int readingFrame) {
        //Checks for valid reading frame and sets reading frame
        if ((readingFrame >= 1 && readingFrame <= 3) || (readingFrame >= -3 && readingFrame <= -1)) {
            this.readingFrame = readingFrame;
        } else {
            //Warns user of an invalid reading frame and defaults it to 1
            System.out.printf("Warning, invalid reading frame \"%s\" entered. Defaulting to 1. Valid reading frames: 1 to 3 or -1 to -3\n", readingFrame);
            this.readingFrame = 1;
        }
    }
    /**
     * Method to set the type of sequence. Non-valid inputs will be defaulted to DNA.
     * @param sequenceType DNA or RNA.
     */
    public void setSequenceType(String sequenceType) {
        //Checks for valid sequence type
        if (sequenceType.equalsIgnoreCase("DNA") || sequenceType.equalsIgnoreCase("RNA")) {
            this.sequenceType = sequenceType.toUpperCase();
        } else {
            //Warns user of an invalid sequence type and defaults to DNA
            this.sequenceType = "DNA";
            System.out.println("Warning, invalid sequence type entered. Defaulting to DNA. Valid sequence types: \"DNA\", \"RNA\"");
        }
    }

    /**
     * Method to set the nucleotide sequence.
     * @param sequenceData the nucleotide sequence.
     */
    public void setSequenceData(String sequenceData) {
        this.sequenceData = new StringBuilder(sequenceData);
    }

    /**
     * Method to set the nucleotide sequence.
     * @param sequenceData the nucleotide sequence.
     */
    public void setSequenceData(StringBuilder sequenceData) {

        //Checks the sequence for non-nucleotide characters
        for(int i = 0; i < sequenceData.length(); i++) {
            if (sequenceData.charAt(i) != 'A' && sequenceData.charAt(i) != 'T' && sequenceData.charAt(i) != 'C' && sequenceData.charAt(i) != 'G' && sequenceData.charAt(i) != 'U') {
                //Warns the user of a non-nucleotide character and provides index
                System.out.println("Warning! A non-nucleotide character, \"" + sequenceData.charAt(i) + "\" was found at index: " + i);
            }
        }
        this.sequenceData = sequenceData;
    }

    /**
     * Method to get whether the sequence is a forward or reverse strand.
     * @return true if sequence is 5' to 3', false if sequence is 3' to 5'.
     */
    public boolean isForwardStrand () {
        return this.forwardStrand;
    }

    /**
     * Method to get the reading frame of the sequence.
     * @return the reading frame of the sequence.
     */
    public int getReadingFrame () {
        return this.readingFrame;
    }

    /**
     * Method to get the type of sequence.
     * @return DNA or RNA
     */
    public String getSequenceType () {
        return this.sequenceType;
    }

    /**
     * Method to get the nucleotide sequence.
     * @return the nucleotide sequence.
     */
    public StringBuilder getSequenceData() {
        return this.sequenceData;
    }

    /**
     * Method to get the sequence in a DNA format.
     * @return the sequence in a DNA format.
     */
    public Sequence getDNA () {
        //Temporary copy of the sequenceData
        StringBuilder output = new StringBuilder(this.sequenceData.toString());

        //Replaces U's with T's
        for(int i = 0; i < output.length(); i++) {
            if(output.charAt(i) == 'U') {
                output.replace(i, i + 1, "T");
            }
        }

        return new Sequence(!this.forwardStrand, computeOtherReadingFrame(), "DNA", output.toString());
    }

    /**
     * Method to set the sequence to a DNA format.
     * @return the sequence in a DNA format.
     */
    public Sequence toDNA () {
        //Replaces U's with T's
        for(int i = 0; i < this.sequenceData.length(); i++) {
            if(this.sequenceData.charAt(i) == 'U') {
                this.sequenceData.replace(i, i + 1, "T");
            }
        }

        //Switches variables to DNA format
        this.setSequenceType("DNA");
        this.readingFrame = computeOtherReadingFrame();
        this.forwardStrand = !this.forwardStrand;

        //Returns sequence
        return new Sequence(!this.forwardStrand, this.readingFrame, "DNA", this.sequenceData.toString());
    }

    /**
     * Method to get the sequence in an RNA format.
     * @return the sequence in an RNA format.
     */
    public Sequence getRNA () {
        //Temporary copy of sequenceData
        StringBuilder output = new StringBuilder(this.sequenceData.toString());

        //Replaces the T's with U's
        for(int i = 0; i < output.length(); i++) {
            if(output.charAt(i) == 'T') {
                output.replace(i, i + 1, "U");
            }
        }

        //Returns sequence
        return new Sequence(!this.forwardStrand, computeOtherReadingFrame(), "RNA", output.toString());
    }

    /**
     * Method to set the sequence to an RNA format.
     * @return the sequence in an RNA format.
     */
    public Sequence toRNA () {
        //Replaces the T's with U's
        for(int i = 0; i < this.sequenceData.length(); i++) {
            if(this.sequenceData.charAt(i) == 'T') {
                this.sequenceData.replace(i, i + 1, "U");
            }
        }

        //Switches variables to RNA format
        this.setSequenceType("RNA");
        this.readingFrame = computeOtherReadingFrame();
        this.forwardStrand = !this.forwardStrand;

        //Returns sequence
        return new Sequence(this.forwardStrand, this.readingFrame, "RNA", this.sequenceData.toString());
    }

    /**
     * Method to get the complimentary strand.
     * @return the complimentary strand.
     */
    public Sequence getComplimentary () {
        //Temporary String to fill in with complimentary strand
        StringBuilder output = new StringBuilder();

        //Goes through each nucleotide and adds base-pair to output
        for (int i = 0; i < this.sequenceData.length(); i++) {
            switch(this.sequenceData.charAt(i)) {
                case 'A':
                    if(this.sequenceType.equalsIgnoreCase("DNA")) {
                        output.append("T");
                    } else if (this.sequenceType.equalsIgnoreCase("RNA")) {
                        output.append("U");
                    }
                    break;
                case 'T', 'U':
                    output.append("A");
                    break;
                case 'C':
                    output.append("G");
                    break;
                case 'G':
                    output.append("C");
                    break;
            }
        }

        //Returns sequence
        return new Sequence(!this.forwardStrand, computeOtherReadingFrame(), this.sequenceType, output.toString());
    }

    /**
     * Method to get the reverse complimentary strand.
     * @return the reverse complimentary strand.
     */
    public Sequence getComplimentaryReverse () {
        //Temporary String to fill in with complimentary strand
        StringBuilder output = new StringBuilder();

        //Goes through each nucleotide and adds base-pair to output
        for (int i = 0; i < this.sequenceData.length(); i++) {
            switch(this.sequenceData.charAt(i)) {
                case 'A':
                    if(this.sequenceType.equalsIgnoreCase("DNA")) {
                        output.append("T");
                    } else if (this.sequenceType.equalsIgnoreCase("RNA")) {
                        output.append("U");
                    }
                    break;
                case 'T', 'U':
                    output.append("A");
                    break;
                case 'C':
                    output.append("G");
                    break;
                case 'G':
                    output.append("C");
                    break;
            }
        }

        //Returns sequence
        return new Sequence(!this.forwardStrand, this.readingFrame, this.sequenceType, output.toString()).getReverse();
    }

    /**
     * Method to get the reverse strand of the sequence.
     * @return the reverse strand.
     */

    public Sequence getReverse () {
        //Creates temporary String to add to
        StringBuilder output = new StringBuilder();

        //Goes through the sequence backwards and adds each nucleotide to the output
        for(int i = this.sequenceData.length() - 1; i >= 0; i--) {
            output.append(this.sequenceData.charAt(i));
        }

        //Returns sequence
        return new Sequence(!this.forwardStrand, computeOtherReadingFrame(), this.sequenceType, output.toString());
    }

    /**
     * Method to get the index where a subsequence appears.
     * @param subsequence the subsequence to search for.
     * @return the index the subsequence appears. -1 if not found.
     */
    public int getIndex (String subsequence) {
        return this.sequenceData.indexOf(subsequence.toUpperCase());
    }

    /**
     * Method to print the nucleotide sequence in the console.
     */
    public void printSequence() {
        System.out.println(this.getSequenceData());
    }

    /**
     * Method to print the amounts of the different types of amino acids in the console.
     */
    public void printAminoAcids() {
        //Gets the DNA format of the sequence to read
        Sequence dnaFormat = this;
        if (this.sequenceType == "RNA") {
            dnaFormat = this.getDNA();
        }

        //Initializes all the amino acids
        int alanine = 0, arginine = 0, aspartic = 0, cysteine = 0, glutamic = 0, glutamine = 0, glycine = 0,
                histadine = 0, isoleucine = 0, leucine = 0, lysine = 0, methionine = 0, phenyalanine = 0, proline = 0,
                serine = 0, asparagine = 0, stop = 0, threonine = 0, tyrosine = 0, tryptophan = 0, valine = 0;

        //Notes the reading frame and goes through the nucleotides by 3s and counts the amino acids that would be produced
        for (int i = dnaFormat.readingFrame - 1; i < dnaFormat.getSequenceData().length(); i += 3)
        {
            if (i + 3 <= dnaFormat.getSequenceData().length())
            {

                switch (dnaFormat.getSequenceData().substring(i, i + 3)) {
                    case "GCT", "GCC", "GCA", "GCG" -> alanine++;
                    case "CGA", "AGA", "AGG", "CGT", "CGC", "CGG" -> arginine++;
                    case "GAT", "GAC" -> aspartic++;
                    case "TGT", "TGC" -> cysteine++;
                    case "GAG", "GAA" -> glutamic++;
                    case "CAA", "CAG" -> glutamine++;
                    case "GGT", "GGC", "GGA" -> glycine++;
                    case "CAC", "CAT" -> histadine++;
                    case "ATA", "ATC", "ATT" -> isoleucine++;
                    case "CTT", "CTG", "CTA", "CTC", "TTA", "TTG" -> leucine++;
                    case "AAG" -> lysine++;
                    case "ATG" -> methionine++;
                    case "TTC" -> phenyalanine++;
                    case "CCT", "CCG", "CCA" -> proline++;
                    case "TCA", "TCC", "TCT", "TCG", "AGT", "AGC" -> serine++;
                    case "AAC", "AAT" -> asparagine++;
                    case "TAA", "TAG", "TGA" -> stop++;
                    case "ACT", "ACA", "ACG", "ACC" -> threonine++;
                    case "TAT", "TAC" -> tyrosine++;
                    case "TGG" -> tryptophan++;
                    case "GTC", "GTA", "GTG", "GTT" -> valine++;
                }
            }
        }

        //Prints the results
        System.out.println("Alanine: " + alanine);
        System.out.println("Arginine: " + arginine);
        System.out.println("Aspartic acid: " + aspartic);
        System.out.println("Cysteine: " + cysteine);
        System.out.println("Glutamic acid: " + glutamic);
        System.out.println("Glutamine: " + glutamine);
        System.out.println("Glycine: " + glycine);
        System.out.println("Histadine: " + histadine);
        System.out.println("Isoleucine: " + isoleucine);
        System.out.println("Leucine: " + leucine);
        System.out.println("Lysine: " + lysine);
        System.out.println("Methionine: " + methionine);
        System.out.println("Phenyalanine: " + phenyalanine);
        System.out.println("Proline: " + proline);
        System.out.println("Serine: " + serine);
        System.out.println("Asparagine: " + asparagine);
        System.out.println("Stop codon: " + stop);
        System.out.println("Threonine: " + threonine);
        System.out.println("Tyrosine: " + tyrosine);
        System.out.println("Tryptophan: " + tryptophan);
        System.out.println("Valine: " + valine);
    }
    /**
     * Method to print the amounts of the different type of nucleotides in console.
     */

    public void printNucleotides () {
        //Initializes the nucleotides
        int adenine = 0, thymine = 0, guanine = 0, cytosine = 0, uracil = 0;

        //Goes through each nucleotide and counts it
        for (int i = 0; i < this.sequenceData.length(); i++) {
            switch (this.sequenceData.charAt(i)) {
                case 'A' -> adenine++;
                case 'T' -> thymine++;
                case 'C' -> cytosine++;
                case 'G' -> guanine++;
                case 'U' -> uracil++;
            }
        }

        //Prints the results
        System.out.println("Adenine: " + adenine);
        System.out.println("Guanine: " + guanine);
        System.out.println("Cytosine: " + cytosine);
        if (this.sequenceType.equalsIgnoreCase("DNA")) {
            System.out.println("Thymine: " + thymine + "\n");
        } else {
            System.out.println("Uracil: " + uracil + "\n");
        }
    }

    private int computeOtherReadingFrame() {
        if (this.forwardStrand) {
            return ((this.sequenceData.length() + this.readingFrame - 1) % 3 + 1) * -1;
        } else {
            return (this.sequenceData.length() + this.readingFrame + 1) % 3 + 1;
        }
    }
}