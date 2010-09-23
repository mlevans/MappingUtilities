/
*
@author Michael Lawrence Evans :: michael@longliveman.com
*
/

This program converts a large image into smaller tiles.  The height and width of the
large image need to be multiples of 256.  (This is typical for web-based map tiles.)

You will need to create an input folder which contains the image you want to tile.

You will also need to create an output folder before you run the program.  

A note on the naming convention for the map tiles:

Let's say that we want to tile a 1024px by 1024px image.  Since each tile is 256px by 256px, we will have 16 tiles.  We will essentially have a 4 by 4 grid, with 4 rows and 4 columns.  The first row and first column begin in the upper-left hand
corner of the grid.  The column numbers increase from left to right; the row numbers
increase from top to bottom.

We can call the first row "Row 0" and the first column "Column 0."  Each map tile will follow the general naming convention of "row_col.png."  So we can call the first map tile 0_0.png.

The program outputs a series of tiles.  At the beginning of each row, we move through the columns, left to right.  At the end of a row, we simply move down to the next row.

So if we had 16 tiles in a 4x4 grid, we would have the following list of images.

0_0.png (row_col.png)
0_1.png
0_2.png
0_3.png
1_0.png
1_1.png
1_2.png
1_3.png
2_0.png
2_1.png
2_2.png
2_3.png
3_0.png
3_1.png
3_2.png
3_3.png