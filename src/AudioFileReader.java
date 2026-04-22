/* Audio File Reader Project using Java programing Language */
/* This program reads an audio file and detects its type based on the file header */
/* It supports WAV, MP3, OGG, FLAC, AAC, and WMA formats */
/* It also displays the header in both hexadecimal and text formats */
/* For WAV files, it extracts and displays the number of channels, sample rate, and bits per sample details */

import java.io.FileInputStream;
import java.io.IOException;

public class AudioFileReader {

    public static void main(String[] args) {
        //  path to your audio file - (change as needed)
        String path = "C:\\Users\\PC\\OneDrive\\Desktop\\Multimedia\\lecProject\\SoundProject\\sound.wav";

        try {
            FileInputStream file = new FileInputStream(path);

            byte[] header = new byte[64];
            int bytesRead = file.read(header);

            String type = detectFileType(header);

            // Differentiation btw WAV and other formats
            System.out.println("-----------------------------------");
            if (type.equals("WAV")) {
                System.out.println("This is a WAV file");
            } else {
                System.out.println("This is NOT a WAV file");
                System.out.println("Detected format: " + type);
            }

            //  Display Header
            System.out.println("\n==== HEADER (HEX) ====");
            for (int i = 0; i < bytesRead; i++) {
                System.out.printf("%02X ", header[i]); 
                if ((i + 1) % 16 == 0) System.out.println();
            }

            System.out.println("\n==== HEADER (TEXT) ====");
            for (int i = 0; i < bytesRead; i++) {
                if (header[i] >= 32 && header[i] <= 126)
                    System.out.print((char) header[i]);
                else
                    System.out.print(".");
            }

            // display details for WAV
            if (type.equals("WAV")) {
                System.out.println("\n\n==== WAV DETAILS ====");

                int channels = (header[23] << 8) | (header[22] & 0xff);

                int sampleRate = (header[27] << 24)
                        | ((header[26] & 0xff) << 16)
                        | ((header[25] & 0xff) << 8)
                        | (header[24] & 0xff);

                int bitsPerSample = (header[35] << 8) | (header[34] & 0xff);

                System.out.println("Channels: " + channels);
                System.out.println("Sample Rate: " + sampleRate);
                System.out.println("Bits Per Sample: " + bitsPerSample);
            }
 
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String detectFileType(byte[] h) {  

        // WAV
        if (h[0]=='R' && h[1]=='I' && h[2]=='F' && h[3]=='F' &&  
            h[8]=='W' && h[9]=='A' && h[10]=='V' && h[11]=='E')
            return "WAV";

        // MP3
        if ((h[0]=='I' && h[1]=='D' && h[2]=='3') ||
            ((h[0] & 0xFF)==0xFF && (h[1] & 0xE0)==0xE0))
            return "MP3";

        // OGG
        if (h[0]=='O' && h[1]=='g' && h[2]=='g' && h[3]=='S')
            return "OGG";

        // FLAC
        if (h[0]=='f' && h[1]=='L' && h[2]=='a' && h[3]=='C')
            return "FLAC";

        // AAC
        if ((h[0] & 0xFF)==0xFF && (h[1] & 0xF6)==0xF0)
            return "AAC";

        // WMA
        if ((h[0] & 0xFF)==0x30 &&
            (h[1] & 0xFF)==0x26 &&
            (h[2] & 0xFF)==0xB2 &&
            (h[3] & 0xFF)==0x75)
            return "WMA";

        // Another      
        return "Unknown";
    }
}
