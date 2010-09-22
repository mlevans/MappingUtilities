/
*
@author Michael Lawrence Evans :: michael@longliveman.com
*
/

This program stitches map tiles together to create one unified map.  The map tiles
are in png format; the program outputs a larger png file.

In the input folder, you will want to create subfolders for each zoom level of the map.

A note on the naming convention for the map tiles:

In this sample program, we deal with 4 rows and 4 columns of individual map tiles.
Imagine a 4 by 4 grid.  The first row and first column begin in the upper-left hand
corner of the grid.  The column numbers increase from left to right; the row numbers
increase from top to bottom.

We can call the first row "Row 0" and the first column "Column 0."  Each map tile will follow the general naming convention of "row_col.png."  So we can call the first map tile 00_00.png.

In the input folder, we list the subsequent map tiles.  At the beginning of each row, we move through the columns, left to right.  At the end of a row, we simply move down to the next row.

So if we had 16 tiles in a 4x4 grid, we would have he following list of images.

00_00.png (row_col.png)
00_01.png
00_02.png
00_03.png
01_00.png
01_01.png
01_02.png
01_03.png
02_00.png
02_01.png
02_02.png
02_03.png
03_00.png
03_01.png
03_02.png
03_03.png

It might be confusing that we're using two digits to represent a row and two digits to represent a column.  If we're merely dealing wih 16 tiles, we would not need to use
two digits.  But we need to anticipate that you will be dealing with much greater numbers of map tiles.  For example, you may be working with a 16x16 grid.  In that case, you 
would need two digits to deal with rows 10 and up and columns 10 and up.