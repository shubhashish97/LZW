/*
			*****************Public Class LZWEncoder that deals with the encoding process***********************
			@Author: Shubhashish Mishra
			L20365063
			Advance Multimedia Processing : Project--LZW Compression
*/
import java.io.*;

public class LZWEncoder {
	
	private static final int EOF			= 	-1;
	private static final int HASHING_SHIFT 	= 	4;
	private static final int BITS 			= 	12;      // for indicating 12 bit codes
	private static final int TABLE_SIZE 	= 	5021;     // maximum number of table codes
	private static final int MAX_VALUE 		= 	(1 << BITS ) - 1;
	private static final int MAX_CODE 		= 	MAX_VALUE - 1;
	
	
	
	private static BufferedInputStream  inputFile  = null;
	private static BufferedOutputStream outputFile = null;
	
	private int output_bit_count  = 0;
	private int output_bit_buffer = 0;
	
	private short[] code_value  		= new short[ TABLE_SIZE ];
	private short[] prefix_code 		= new short[ TABLE_SIZE ];
	private short[] append_character 	= new short[ TABLE_SIZE ];
	

	public void compress()
	{
		short next_code   = 0;
		short character   = 0;
		short string_code = 0;
		short index 	  = 0;
		
		next_code = 256;              			  // Next code is the total number of single byte available 
		
		for ( short i = 0; i < TABLE_SIZE; i++ )  // Removes entry from the code Table 
			code_value[ i ] = -1;
	
		try
		{
			string_code = ( short ) inputFile.read(); // First character and is expected to be one of 256 ASCII character	

			 
			while ( ( character = ( short ) inputFile.read() ) != EOF )  // The main execution loop where compression happens.  
			                                                            //The loop stop when all character are parsed and End Of File is found. 
			{
				index = find_match ( string_code, character );	// Search for the match 
				
				if ( code_value[ index ] != -1 )
			    {            									// Gets the code value if match found   /
					string_code = code_value[ index ];          
			    }
			    else                                    		// If match not found then add it
			    {                                       		
			    	if ( next_code <= MAX_CODE )
			    	{
					    code_value		[ index ] = next_code++;
					    prefix_code		[ index ] = string_code;
					    append_character[ index ] = character;
			    	}
			    	
			        output_code( string_code );  				
			    	string_code = character;           			
			    }												
			}                                     				
			
						
			output_code( string_code ); 						//  The last code is outputted
			output_code( ( short ) MAX_VALUE );   				// The end of buffer code is outputted
			output_code( ( short ) 0 );           				// This code clears the output buffer
			
			//Input and Output files are closed
			outputFile.close();
			inputFile.close();
		}
		catch ( IOException ioe )
		{
			System.out.println( "IOException in compress()" );
			System.exit( 1 );
		}
	}
	
	/*
	** This is the hashing routine.  It tries to find a match for the prefix+char
	** string in the string table.  If it finds it, the index is returned.  If
	** the string is not found, the first available index in the string table is
	** returned instead.
	*/
	private short find_match ( short hash_prefix, short hash_character )
	{
		int index  = 0;
		int offset = 0;
	
		index = ( hash_character << HASHING_SHIFT ) ^ hash_prefix;
		  
		if ( index == 0 )
			offset = 1;
		else
			offset = TABLE_SIZE - index;
		
		while ( true )
		{
			if ( code_value[ index ] == -1 )
				return ( short ) index;
			if ( prefix_code[ index ] == hash_prefix && append_character[ index ] == hash_character )
				return ( short ) index;
		      
			index -= offset;
		    
			if ( index < 0 )
				index += TABLE_SIZE;
		}
	}
	
	private void output_code( short code )
	{
		output_bit_buffer |= code << ( 32 - BITS - output_bit_count );
		output_bit_count += BITS;
		
		while ( output_bit_count >= 8 )
		{
			try
			{
				outputFile.write( output_bit_buffer >> 24 );
			}
			catch( IOException ioe )
			{
				System.out.println( "IOException in output_code()" );
				System.exit( 1 );
			}
			output_bit_buffer <<= 8;
			output_bit_count -= 8;
		}
	}

	public static void main ( String[] args )  throws IOException
	{
	
		String in, out;
        if(args.length >= 1){
            
			in = "original/" + args[0];
            inputFile = new BufferedInputStream(new FileInputStream(in));
            out = "compressed/"+ args[1]+ ".lzw";
            outputFile = new BufferedOutputStream(new FileOutputStream(out));
        }
        else{
            System.out.print("\n!!!WRONG SYNTAX!!!\n\nPLEASE RUN PROGRAM IN FOLLOWING SYNTAX:\n\n\"java LZWEncoder originalfile_with_extension outputFile\"\n");
            System.exit(1);
        }
        
		LZWEncoder sample = new LZWEncoder();
		sample.compress();
			
		try
		{
			inputFile.close();
			outputFile.close();
		}
		catch ( IOException ioe )
		{
			System.out.println( "IOException in main()." );
			System.exit(1);
		}
		
		System.out.println( "\nSUCCESSFUL: COMPRESSION DONE. PLEASE CHECK DIRECTORY COMPRESSED FOR OUTPUT");
	}
}
