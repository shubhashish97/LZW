******************************************************************************************************************************************
*******************************************Guidelines to compile and run programs.****************************************************************
******************************************************************************************************************************************
1. First of all copy the main folder "Project AMP" to a machine.
2. The folder contains the programs and other directories original, compressed, decompressed, and WinRAR.
    The directory original contains audio.mp3, image.JPG, document.pdf, video.mp4, and word.doc.
    The directories compressed and decompressed are to store output file of LZWEncoder and LZWDecoder class respectively.
    The files inside directory compressed will have ".lzw" extionsion and those inside directory decompressed will have extension as given by user.
    The directory WinRAR contains the input files compressed with WinRAR. The programs do not manipulate any of these files.
3. If you want to run this program in windows please make the following change.
	a. Class LZW
		Line 143:	change "original/" to "original\\"
		Line 145:	change "compressed" to "compressed\\"
	b. Class Decoder
		Line 95:	change "compressed/" to "compressed\\"
		Line 97	change "decompressed/" to "decompressed\\"
4. To compile and run the class LZW manually the following commands should be prompted.
	javac LZWEncoder.java
	java LZWEncoder audio.mp3 audio
	java LZWEncoder image.JPG image
	java LZWEncoder pdf.pdf pdf
	java LZWEncoder video.mp4 video
	java LZWEncoder word.doc word
or to run automatically the following command should be typed
	sh encoder.sh
5. To compile and run the class Decoder manually the following commands should be prompted.
	javac LZWDecoder.java
	java LZWDecoder audio.lzw audio
	java LZWDecoder image.lzw image
	java LZWDecoder pdf.lzw pdf
	java LZWDecoder video.lzw video
	java LZWDecoder word.lzw word
or to run automatically the following command should be typed
	sh decoder.sh

Finally, see the directory compressed for encoded files and decompressed for decoded files.