******************************************************************************************************************************************
*********************************************************Guidelines to Encode****************************************************************
******************************************************************************************************************************************
1. First of all copy the main folder "Project AMP" to a machine.
2. The folder contains the programs and other directories original, compressed, decompressed, and WinRAR.
    The directory original contains audio.mp3, image.JPG, document.pdf, video.mp4, and word.doc.
    The directories compressed is to store output file of LZWEncoder.
3. If you want to run this program in windows please make the following change in LZWEncoder.
		Line 143:	change "original/" to "original\\"
		Line 145:	change "compressed" to "compressed\\"
4. To compile and run the class LZW manually the following commands should be prompted.
	javac LZWEncoder.java
	java LZWEncoder audio.mp3 audio
	java LZWEncoder image.JPG image
	java LZWEncoder pdf.pdf pdf
	java LZWEncoder video.mp4 video
	java LZWEncoder word.doc word
Finally, see the directory compressed for encoded files