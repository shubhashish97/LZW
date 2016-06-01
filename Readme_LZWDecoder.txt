******************************************************************************************************************************************
*********************************************************Guidelines to Encode****************************************************************
******************************************************************************************************************************************
1. First of all copy the main folder "Project AMP" to a machine.
2. The folder contains the programs and other directories original, compressed, decompressed, and WinRAR.
    The directory compressed contains audio.lzw, image.lzw, document.lzw, video.lzw, and word.lzw.
    The directories decompressed is to store output file of LZWDecoder.
3. If you want to run this program in windows please make the following change in LZWEncoder.
		Line 95:	change "original/" to "original\\"
		Line 97:	change "compressed" to "compressed\\"
4. To compile and run the class Decoder manually the following commands should be prompted.
	javac LZWDecoder.java
	java LZWDecoder audio.lzw audio
	java LZWDecoder image.lzw image
	java LZWDecoder pdf.lzw pdf
	java LZWDecoder video.lzw video
	java LZWDecoder word.lzw word
Finally, see the directory decompressed for decoded files