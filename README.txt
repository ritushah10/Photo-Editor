README - SIMPLE IMAGE EDITOR PROGRAM - MATTHEW LOVE & RITU SHAH

This program may take in a ".ppm", ".png", ".jpg", or ".bmp" image file, and may conduct certain operations on the image before saving. First, take in the image and load it. Then, conduct any operations of the following on the image: brighten, darken, vertical flip, horizontal flip, value component, intensity component, luma component, red component, green component, blue component, blur, sharpen, greyscale, or sepia. Lastly, save the file either overriding the original file, or creating a new one. The commands for all these operations will be listed in the USEME.txt file in the root folder.

When you load an image, the program stores that image in memory with the specified name. What this means is that multiple images may be stored in memory in one concurrent run, and may have different operations conducted on them.

Design: At a higher level, the program contains interfaces for the ImageEditor controller, model, state, and view. These interfaces are implemented in the SimpleImageEditor controller, model, and view. The model runs the actual operations on the saved images in memory. The controller runs the program by accepting inputs and using the model. The controller also handles all inputs and outputs for the program. The view handles the outputs to the user, such as the text feedback in the command line. The ImageEditorState is used within the SimpleImageView class in order to limit the accessibility of the model when being used by the view. For example, the view does not need to conduct operations on the model, so there is no need to give it access to those methods. The Component class is an enum and used when determining the type of component to show (whether it is the red, green, blue, luma, intensity, or value component). The Pixel class represents one pixel with values of RGB. Lastly, included are test files for all the components of the program. All parts of this program as detailed by the assignment documentation have been completed and work as they are intended to.

Updated in Assignment 6, a user interface (GUI) was added to allow the viewing and editing of images within the program. See below for more information about the classes and updates.

Classes & Interfaces:
Component
ImageEditorController
ImageEditorModel
ImageEditorState
ImageEditorView
Pixel
SimpleEditorController
SimpleEditorModel
SimpleEditorView
PixelTest
SimpleImageControllerTest
SimpleImageEditorTest
SimpleImageViewTest
AColorTransform
ColorTransform
Greyscale
Luma
Sepia
AFilter
Blur
Filter
Sharpen
Flip
DrawHistogram
Histogram
HistogramModel
SimpleEditorGUIController
EditorView
SimpleEditorGUIView
ImageEditorGUIView
ImageEditor - main method file

UPDATES - Assignment 5:
- Combined the code for horizontal and vertical flip to consolidate and abstract the program.
- Moved the inputs and outputs from the model to the controller to follow the MVC structure for the program.

UPDATES - Assignment 6:
- Added a GUI with the ability to view the current image & conduct operations on
- Added a histogram to show the current image's component values & intensity value


Image Citation:
Roche, Daniel S. “Flowers.” IC210: Project1 , Department of Computer Science, https://www.usna.edu/Users/cs/choi/ic210/project/p01/index.html. Accessed 2 Nov. 2021.

Second Image: https://sample-videos.com/download-sample-png-image.php
