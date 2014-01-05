TextureAtlas-Creator
====================

A little program to pack images into a single image

This program was created to pack smaller images into a larger image to create a singular texture. This is useful for opengl textures, which must be a power of 2, and can save a bit of space we rendering (by avoiding texture switches). Image packing has other benefits besides opengl applications, as packing images into a singular image can reduce the amount of files you need to manage within an application that uses many images. 

The program generates 2 files, the image file with all the packed images, and an atlas file, which is just a text file in the with the elements formatted as "image_name:x:y:width:height:" where x,y are the position of the sub image within the newly formed image.

The program uses a packing algorithm presented here for c++: http://www.blackpawn.com/texts/lightmaps/

I have provided a jar file which can be run from a terminal with the following usage

java -jar Packer.jar   [input directory of images] [output directory] [file_name] [output_type] [width] [height]

where input directory is a directory of the images
output directory is the destination folder
filename is the name of the final image
output_type is the type of image you want (ie, png, jpeg, tiff)
and width and height are the dimensions of the final image
