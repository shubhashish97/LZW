/*
            *****************Public Class LZWDecoder that deals with the decoding process***********************
            @Author: Shubhashish Mishra
            L20365063
            Advance Multimedia Processing : Project--LZW Decompression
*/
import java.io.*;
import java.util.*;

public class LZWDecoder{
	static class Element
	{
		int prefix;
		int suffix;
		public Element(int first, int last)
		{
			prefix = first;
			suffix = last;
		}
	}

    final static int EXCESS = 4;
    final static int MAX_CODES = 4096;   // total number of codes in table
    final static int BYTE_SIZE = 8;
    final static int MASK = 15;
    final static int ALPHA= 256;     // total character
    
    static int [] s;
    static int size;
    static Element [] h;
    static int leftOver;
    static boolean bitsLeftOver;
    static BufferedInputStream in;
    static BufferedOutputStream out;

    private static void output(int code)throws IOException{    // write out the decoded sequence in the file opened
        size = -1;
        while(code>=ALPHA){
            s[++size]=h[code].suffix;
            code = h[code].prefix;
        }
        s[++size]=code;
        for(int i=size; i>=0; i--)
            out.write(s[i]);
    }

    private static int getCode() throws IOException{        // get the code from code table of the sequence
        int c = in.read();
        if(c == -1)return -1;

        int code;
        if(bitsLeftOver)
            code = (leftOver<<BYTE_SIZE)+c;
        else{
            int d = in.read();
            code = (c<<EXCESS)+(d>>EXCESS);
            leftOver = d&MASK;
        }
        bitsLeftOver = !bitsLeftOver;
        return code;
    }

    private static void decompress() throws IOException{    // method that initiates the decompression process
        int codeUsed = ALPHA;
        s = new int[MAX_CODES];
        h = new Element[MAX_CODES];

        int pcode = getCode(), ccode;
        if(pcode>=0){
            s[0] = pcode;
            out.write(s[0]);
            size = 0;

            do{
                ccode = getCode();
                if(ccode<0)break;
                if(ccode<codeUsed){
                    output(ccode);
                    if(codeUsed<MAX_CODES)
                        h[codeUsed++] = new Element(pcode, s[size]);
                }
                else{
                    h[codeUsed++] = new Element(pcode, s[size]);
                    output(ccode);
                }
                pcode = ccode;
            }while(true);
        }
        out.close();
        in.close();
    }

    public static void main(String [] args) throws IOException{          //main method
        
        String inputFile, outputFile;
        if(args.length >= 1){
            
            inputFile = "compressed/" + args[0];
            in = new BufferedInputStream(new FileInputStream(inputFile));
            outputFile = "decompressed/"+ args[1];
            out = new BufferedOutputStream(new FileOutputStream(outputFile));
        }
        else{
            System.out.print("\n!!!WRONG SYNTAX!!!\n\nPLEASE RUN PROGRAM IN FOLLOWING SYNTAX:\n\n\"java LZWDecoder compressed_file_name.lzw outputFile_with_extension\"\n");
            System.exit(1);
        }

        decompress();
		System.out.println("\nSUCCESSFUL: DECOMPRESSION DONE. PLEASE CHECK DIRECTORY DECOMPRESSED FOR OUTPUT");
    }
}
